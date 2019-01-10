package com.arny.java.presenters.main

import com.arny.java.data.models.CleanFolder
import com.arny.java.presenters.base.BaseMvpPresenter
import com.arny.java.presenters.base.BaseMvpView

object MainContract {
    interface View : BaseMvpView {
        fun updateTable(folders: ArrayList<CleanFolder>)
        fun clearSelection()
        fun showInfo(msg: String)
    }

    interface Presenter : BaseMvpPresenter<View> {
        fun addFolder(path: String?)
        fun initDB()
        fun loadFolders()
        fun initCurrent(folder: CleanFolder)
        fun removeSelectedFolder()
        fun deleteSelectedFolder()
    }
}