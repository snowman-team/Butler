package com.xueqiu.butler.app

import android.app.Application
import com.xueqiu.butler.Butler

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Butler.init(this)

        Butler.register(ApplicationLoader())
        Butler.register(MainLoader())

        Butler.executeLoader(ApplicationLoader.LOADER_NAME)
    }
}