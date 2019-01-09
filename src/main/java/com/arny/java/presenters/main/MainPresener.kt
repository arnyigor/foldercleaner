package com.arny.java.presenters.main

import com.arny.java.data.AppRepositoryImpl
import com.arny.java.presenters.base.BaseMvpPresenterImpl

class MainPresener : BaseMvpPresenterImpl<MainContract.View>(), MainContract.Presenter {
    private val appRepository = AppRepositoryImpl.INSTANCE

    override fun addFolder(path: String?) {
        if (path.isNullOrBlank()) {
            mView?.toast("Ошибка добавления папки", "Некорректный путь")
            return
        }
        appRepository.addFolder(path, {
            if (it) {
                mView?.toast("Добавлено!", "Папка $path добавлена", true)
            } else {
                mView?.toast("Не добавлено!", "Папка $path не добавлена")
            }
        }, {
            mView?.toast("Ошибка добавления папки", "Ошибка добавления папки $path:" + it.message)
        })
    }

    override fun loadFolders() {
        appRepository.loadFolders({
            println(it)
        }, {
            mView?.toast("Ошибка загрузки папок", it.message)
        })
    }

    override fun initDB() {
        appRepository.initDB()
    }
}