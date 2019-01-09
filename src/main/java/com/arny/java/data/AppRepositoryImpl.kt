package com.arny.java.data


class AppRepositoryImpl : AppRepository, DBRepository {
    private object Holder {
        val INSTANCE = AppRepositoryImpl()
    }

    companion object {
        val INSTANCE: AppRepositoryImpl by lazy { Holder.INSTANCE }
    }

}
