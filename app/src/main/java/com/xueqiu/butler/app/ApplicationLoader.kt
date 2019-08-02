package com.xueqiu.butler.app

import android.util.Log
import com.xueqiu.butler.AppLoader
import com.xueqiu.butler.BaseLoader

@AppLoader(ApplicationLoader.LOADER_NAME)
class ApplicationLoader : BaseLoader() {

    companion object {
        const val LOADER_NAME = "application"
    }

    override fun execute() {
        Log.i("application loader", "execute loader")
    }


}