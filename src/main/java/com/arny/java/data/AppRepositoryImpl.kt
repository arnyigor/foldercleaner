package com.arny.java.data

import com.arny.java.data.utils.launchAsync
import java.io.File


class AppRepositoryImpl : AppRepository, DBRepository {
    private object Holder {
        val INSTANCE = AppRepositoryImpl()
    }

    companion object {
        val INSTANCE: AppRepositoryImpl by lazy { Holder.INSTANCE }
    }

    fun cleanFolders(currentPath: String? = null, onComplete: (Boolean) -> Unit = {}, onError: (Throwable) -> Unit = {}) {
        launchAsync({
            var res = true
            if (currentPath != null) {
                val file = File(currentPath)
                for (listFile in file.listFiles()) {
                    val currentRemove = when {
                        listFile.isDirectory -> listFile.deleteRecursively()
                        listFile.isFile -> listFile.delete()
                        else -> false
                    }
                    if (!currentRemove) {
                        res = false
                        break
                    }
                }
            }
            res
        }, {
            onComplete.invoke(it)
        }, {
            onError.invoke(it)
        })
    }

}
