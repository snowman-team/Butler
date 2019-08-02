package com.xueqiu.butler

import android.app.ActivityManager
import android.content.Context

abstract class BaseLoader {

    private var mLoaderCallback: LoaderCallback? = null

    protected var mContext: Context? = null

    protected var isMainProcess = false

    fun prepare(context: Context, callback: LoaderCallback) {
        mContext = context
        mLoaderCallback = callback
    }

    fun run() {
        if (null == mContext) {
            mLoaderCallback?.onFail(IllegalArgumentException("Base loader get context return null"))
        } else {
            try {
                isMainProcess = isOnMainProcess(mContext)
                execute()
                finish()
            } catch (e: Exception) {
                if (mLoaderCallback != null) {
                    mLoaderCallback?.onFail(e)
                }
            }
        }
    }

    protected abstract fun execute()

    protected fun finish() {
        mLoaderCallback?.onSuccess()
    }

    private fun isOnMainProcess(context: Context?): Boolean {
        if (null == context) return false
        try {
            val pid = android.os.Process.myPid()
            val mActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (appProcess in mActivityManager.runningAppProcesses) {
                if (appProcess.pid == pid) {
                    return appProcess.processName == context.packageName
                }
            }
        } catch (e: Exception) {
            return false
        }
        return false
    }
}