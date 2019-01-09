package com.arny.java.data

object AppConstants {
    object DB {
        const val DB_NAME = "AppCleaner.db"
        const val DB_TABLE_MAIN = "main"
        const val SQL_CREATE_MAIN_TABLE = "CREATE TABLE IF NOT EXISTS main ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, path TEXT UNIQUE)"
        const val SQL_SELECT_ALL_FOLDERS = "SELECT * FROM main"
    }
}