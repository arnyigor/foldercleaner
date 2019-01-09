package com.arny.java.presenters.base

interface BaseMvpView {
    fun toast(title: String?, error: String?, success: Boolean = false)
}
