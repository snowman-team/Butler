package com.xueqiu.butler

import java.lang.Exception

interface LoaderCallback {

    fun onSuccess()

    fun onFail(e: Exception)
}