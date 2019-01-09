package com.arny.java.presenters.main

import com.arny.java.presenters.base.BaseMvpPresenter
import com.arny.java.presenters.base.BaseMvpView

object MainContract {
    interface View : BaseMvpView {
    }

    interface Presenter : BaseMvpPresenter<View> {
        fun addFolder(path: String?)
        fun initDB()
        fun loadFolders()
    }
}