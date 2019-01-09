package com.arny.java.data

object AppConstants {
    object DB {
        const val DB_NAME = "AppCleaner.db"
        const val CREATE_MAIN_TABLE = "CREATE TABLE IF NOT EXISTS main ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT, path TEXT)"
    }
}