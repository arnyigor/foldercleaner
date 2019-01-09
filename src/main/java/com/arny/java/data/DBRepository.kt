package com.arny.java.data

import com.arny.java.data.models.CleanFolder
import com.arny.java.data.utils.DBConnection
import com.arny.java.data.utils.FileUtils
import com.arny.java.data.utils.launchAsync
import java.io.File


interface DBRepository {
    fun getDB(): DBConnection {
        return DBConnection.instance
    }

    fun initDB() {
        launchAsync({
            getDB().executeSQL(AppConstants.DB.SQL_CREATE_MAIN_TABLE)
        }, {
            println("Init db main:$it")
        }, {
            it.printStackTrace()
        })
    }

    fun addFolder(path: String, onComplete: (Boolean) -> Unit = {}, onError: (Throwable) -> Unit = {}) {
        val dbValues = hashMapOf<String, String>()
        dbValues["path"] = path
        launchAsync({
            getDB().insertSqliteData(AppConstants.DB.DB_TABLE_MAIN, dbValues)
        }, {
            onComplete.invoke(it)
        }, {
            onError.invoke(it)
        })
    }

    fun loadFolders(onComplete: (ArrayList<CleanFolder>) -> Unit = {}, onError: (Throwable) -> Unit = {}) {
        launchAsync({
            val resultSet = getDB().executeSQLQuery(AppConstants.DB.SQL_SELECT_ALL_FOLDERS)
            val list = arrayListOf<CleanFolder>()
            while (resultSet?.next() == true) {
                val id = resultSet.getLong("_id")
                val path = resultSet.getString("path")
                val folderSize = FileUtils.getFolderSize(File(path))
                val folder = CleanFolder(id, path, folderSize)
                list.add(folder)
            }
            list
        }, {
            onComplete.invoke(it)
        }, {
            onError.invoke(it)
        })
    }

    fun deleteFolder(id: Long, onComplete: (Boolean) -> Unit = {}, onError: (Throwable) -> Unit = {}) {
        launchAsync({
            getDB().executeSQL(AppConstants.DB.SQL_DELETE_FROM_MAIN_WHERE_ID + id)
        },{
            onComplete.invoke(it)
        },{
            onError.invoke(it)
        })
    }

}
