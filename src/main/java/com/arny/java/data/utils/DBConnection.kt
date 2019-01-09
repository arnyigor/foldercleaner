package com.arny.java.data.utils

import com.arny.java.data.AppConstants
import sun.awt.shell.ShellFolder
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.util.*

class DBConnection {
    private var connection: Connection? = null

    private object Holder {
        val INSTANCE = DBConnection()
    }

    companion object {
        val instance: DBConnection by lazy { Holder.INSTANCE }
    }

    private fun connectDb(): Connection? {
        return if (connection == null) {
            Class.forName("org.sqlite.JDBC")
            connection = DriverManager.getConnection("jdbc:sqlite:" + initDbName())
            connection
        } else {
            connection
        }
    }

    private fun initDbName(): String {
        val appDir = System.getProperty("user.dir")
        val workDir = "files"
        val pathname = appDir + ShellFolder.separator + workDir
        val file = File(pathname)
        val exists = file.exists()
        if (!exists) {
            file.mkdirs()
        }
        return pathname + ShellFolder.separator + AppConstants.DB.DB_NAME
    }

    fun executeSQLUpdate(query: String): Int {
        val c = connectDb()
        val res: Int
        val sta = c?.createStatement()
        res = sta?.executeUpdate(query) ?: 0
        sta?.close()
        return res
    }

    fun executeSQLQuery(query: String): ResultSet? {
        val c = connectDb()
        val resultSet: ResultSet?
        val sta = c?.createStatement()
        resultSet = sta?.executeQuery(query)
        return resultSet
    }

    fun executeSQL(query: String): Boolean {
        val connectDb = connectDb()
        var res: Boolean
        val sta = connectDb?.createStatement()
        res = sta?.execute(query) ?: false
        val updateCount = sta?.updateCount ?: 0
        if (updateCount > 0) {
            res = true
        }
        sta?.close()
        return res
    }

    fun updateSqliteData(table: String, colVals: HashMap<String, String>, cond: String): Boolean {
        val values = StringBuilder()
        var index = 0
        for ((key, value) in colVals) {
            if (index != 0) {
                values.append(",")
            }
            values.append(key).append("=").append(value)
            index++
        }
        val formattedSql = String.format("UPDATE %s SET %s WHERE %s", table, values.toString(), cond)
        return executeSQLUpdate(formattedSql) == 1
    }

    fun insertSqliteData(table: String, colVals: HashMap<String, String>): Boolean {
        val values = StringBuilder()
        var index = 0
        values.append("(")
        for ((key, _) in colVals) {
            if (index != 0) {
                values.append(",")
            }
            values.append("`")
            values.append(key)
            values.append("`")
            index++
        }
        values.append(") VALUES (")
        index = 0
        for ((_, value) in colVals) {
            if (index != 0) {
                values.append(",")
            }
            values.append("'")
            values.append(value)
            values.append("'")
            index++
        }
        values.append(")")
        val executeSQLQuery = executeSQL(String.format("INSERT INTO %s%s", table, values.toString()))
        return executeSQLQuery
    }

}