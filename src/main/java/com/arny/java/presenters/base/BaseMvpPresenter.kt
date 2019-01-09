package com.arny.java.presenters.base

interface BaseMvpPresenter<in V : BaseMvpView> {
    fun attachView(mvpView: V)
    fun detachView()
}