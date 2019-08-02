package com.xueqiu.butler.app

import android.util.Log
import com.xueqiu.butler.AppLoader
import com.xueqiu.butler.BaseLoader

@AppLoader(MainLoader.LOADER_NAME)
class MainLoader : BaseLoader() {

    companion object {
        const val LOADER_NAME = "main"
    }

    override fun execute() {
        Log.i("main loader", "execute loader")
    }

}