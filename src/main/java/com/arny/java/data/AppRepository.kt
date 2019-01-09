package com.arny.java.data

import com.arny.java.data.utils.Config


interface AppRepository {
    fun putConfig(key: String, value: String){
        Config.getInstance().put(key, value)
    }

    fun removeConfig(key: String){
        Config.getInstance().remove(key)
    }
}
