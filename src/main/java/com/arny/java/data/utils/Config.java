package com.arny.java.data.utils;

import sun.awt.shell.ShellFolder;

import java.io.*;
import java.util.Date;
import java.util.Properties;

public class Config {
    private String propertiesFilePath;
    private Properties properties;
    private static Config mInstance = null;
    private static final Object LOCK = new Object();

    public static synchronized Config getInstance() {
        if (mInstance == null) {
            synchronized (LOCK) {
                if (mInstance == null) {
                    mInstance = new Config(System.getProperty("user.dir") + ShellFolder.pathSeparator + "config.xml");
                }
            }
        }
        return mInstance;
    }

    private Config(String filePath) {
        propertiesFilePath = filePath;
        properties = new Properties();
        File file = new File(propertiesFilePath);
        try {
            File folder = new File(FileUtils.getFilePath(filePath));
            if (!folder.exists()) {
                folder.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.length() > 0) {
                properties.loadFromXML(new FileInputStream(propertiesFilePath));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void put(String key, String value) {
        properties.setProperty(key, value);
        try {
            store();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(String key) {
        properties.remove(key);
        try {
            store();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    private void store() throws FileNotFoundException, IOException {
        String commentText = "Last update:" + new Date();
        properties.storeToXML(new FileOutputStream(propertiesFilePath), commentText);
    }
}
