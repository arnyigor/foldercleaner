package com.arny.java.data

import com.arny.java.data.utils.SqliteConnection
import com.arny.java.data.utils.launchAsync
import java.sql.Connection


interface DBRepository {
    fun getDB(): Connection? {
        return SqliteConnection.getConnection()
    }

    fun initDB(onComplete: (Boolean) -> Unit = {}, onError: (Throwable) -> Unit = {}) {
        launchAsync({
            SqliteConnection.executeSQL(AppConstants.DB.CREATE_MAIN_TABLE, getDB())
        }, {
            onComplete.invoke(it)
        }, {
            onError.invoke(it)
        })
    }

    fun addFolder(){

    }
}
