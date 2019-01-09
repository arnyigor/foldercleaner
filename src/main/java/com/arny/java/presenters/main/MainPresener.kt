package com.arny.java.presenters.main

import com.arny.java.data.AppRepositoryImpl
import com.arny.java.presenters.base.BaseMvpPresenterImpl

class MainPresener : BaseMvpPresenterImpl<MainContract.View>(), MainContract.Presenter {
    private val appRepository = AppRepositoryImpl.INSTANCE

    override fun addFolder(path: String) {

    }
}