package com.arny.java.presenters.main

import com.arny.java.data.AppRepositoryImpl
import com.arny.java.data.models.CleanFolder
import com.arny.java.data.utils.empty
import com.arny.java.presenters.base.BaseMvpPresenterImpl

class MainPresener : BaseMvpPresenterImpl<MainContract.View>(), MainContract.Presenter {
    private val appRepository = AppRepositoryImpl.INSTANCE
    private var folders = arrayListOf<CleanFolder>()
    private var current: CleanFolder? = null
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
            if (it.isNotEmpty()) {
                folders = it
                mView?.updateTable(folders)
            }
        }, {
            mView?.toast("Ошибка загрузки папок", it.message)
        })
    }

    override fun initDB() {
        appRepository.initDB()
    }

    override fun initCurrent(folder: CleanFolder) {
        if (current == null) {
            current = folder
        } else {
            if (current == folder) {
                mView?.clearSelection()
                current = null
            } else {
                current = folder
            }
        }
        println("current:$current")
    }

    override fun removeSelectedFolder() {
        appRepository.cleanFolders(current?.path, {
            if (it) {
                mView?.toast("Очистка завершена", "Папка ${current?.path} очищена", true)
            } else {
                mView?.toast("Очистка не завершена", "Папка ${current?.path} не очищена")
            }
        }, {
            mView?.toast("Ошибка очистки", "Папка ${current?.path} не очищена:" + it.message)
        })
    }

    override fun deleteSelectedFolder() {
        val id = current?.id
        if (id != null) {
            appRepository.deleteFolder(id, {
                if (it) {
                    mView?.toast("Папка удалена", "Папка ${current?.path} удалена из списка", true)
                } else {
                    mView?.toast("Папка не удалена", "Папка ${current?.path} не удалена из списка")
                }
            }, {
                mView?.toast("Ошибка удаления", "Папка ${current?.path} не удалена из списка:" + it.message)
            })
        }
    }
}