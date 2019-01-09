package com.arny.java.data.utils;


import com.arny.java.data.AppConstants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SqliteConnection {

    private static Connection connection = null;

    public static Connection getConnection() {
        return connectDb();
    }

    @NotNull
    private static String getDbName() {
        String appDir = System.getProperty("user.dir");
        String workDir = "files";
        File file = new File(appDir + "/" + workDir);
        boolean exists = file.exists();
        if (!exists) {
            file.mkdirs();
        }
        return appDir + "/" + workDir + "/" + AppConstants.DB.DB_NAME;
    }

    private static Connection connectDb() {
        try {
            if (connection == null) {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:" + getDbName());
                return connection;
            } else {
                return connection;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static int executeSQLUpdate(String query) {
        Connection c = connectDb();
        int res = 0;
        try {
            Statement sta = c.createStatement();
            res = sta.executeUpdate(query);
            sta.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Nullable
    public static ResultSet executeSQLQuery(String query) {
        Connection c = connectDb();
        ResultSet resultSet = null;
        try {
            Statement sta = c.createStatement();
            resultSet = sta.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static boolean executeSQL(String query) {
        Connection connectDb = connectDb();
        if (connectDb != null) {
            return executeSQL(query, connectDb);
        }
        return false;
    }

    public static boolean executeSQL(String query, Connection connection) {
        boolean res = false;
        try {
            Statement sta = connection.createStatement();
            res = sta.execute(query);
            sta.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    public static boolean updateSqliteData(Connection connection, String table, HashMap<String, String> colVals, String cond) {
        StringBuilder values = new StringBuilder();
        int index = 0;
        for (Map.Entry<String, String> entry : colVals.entrySet()) {
            if (index != 0) {
                values.append(",");
            }
            values.append(entry.getKey()).append("=").append(entry.getValue());
            index++;
        }
        String formattedSql = String.format("UPDATE %s SET %s WHERE %s", table, values.toString(), cond);
        return executeSQLUpdate(formattedSql) == 1;
    }

    public static boolean insertSqliteData(String table, HashMap<String, String> colVals) {
        StringBuilder values = new StringBuilder();
        int index = 0;
        values.append("(");
        for (Map.Entry<String, String> keys : colVals.entrySet()) {
            if (index != 0) {
                values.append(",");
            }
            values.append(keys.getKey());
            index++;
        }
        values.append(") values(");
        index = 0;
        for (Map.Entry<String, String> vals : colVals.entrySet()) {
            if (index != 0) {
                values.append(",");
            }
            values.append(vals.getValue());
            index++;
        }
        values.append(")");
        return executeSQL(String.format("INSERT INTO %s%s", table, values.toString()));
    }

}
