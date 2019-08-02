package com.xueqiu.butler

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import java.lang.ref.WeakReference

object Butler : Application.ActivityLifecycleCallbacks {

    private var mAppContext: Context? = null

    fun init(application: Application) {
        mAppContext = application.applicationContext
    }

    @Throws(IllegalStateException::class)
    fun getAppContext(): Context {
        val appContext = mAppContext
        if (null == appContext) {
            throw IllegalStateException("Butler has not init!!")
        } else {
            return appContext
        }
    }

    private var mCurrentActivityWeak: WeakReference<Activity?>? = null
    private val mLoaders: MutableMap<String, BaseLoader> = HashMap()
    private val mFinishedLoader: MutableList<String> = ArrayList()

    fun register(loader: BaseLoader) {
        val name = loader.javaClass.getAnnotation(AppLoader::class.java)?.name ?: return
        if (mLoaders.containsKey(name) || mFinishedLoader.contains(name)) return
        mLoaders[name] = loader
    }

    @Synchronized
    fun executeLoader(name: String, callback: LoaderCallback? = null) {
        val context = mAppContext
        if (null == context) {
            callback?.onFail(IllegalStateException("Butler has not init!!"))
            return
        }
        if (mFinishedLoader.contains(name)) {
            callback?.onFail(IllegalStateException("The $name loader has already executed"))
            return
        }
        if (!mLoaders.containsKey(name)) {
            callback?.onFail(IllegalArgumentException("The $name loader not exist"))
            return
        }
        val loader = mLoaders[name] ?: return
        loader.prepare(context, object : LoaderCallback {

            override fun onSuccess() {

                mFinishedLoader.add(name)
                mLoaders.remove(name)
                callback?.onSuccess()
            }

            override fun onFail(e: Exception) {
                callback?.onFail(e)
            }

        })
        loader.run()
    }

    fun isLoaderFinished(name: String) = mFinishedLoader.contains(name)

    fun getCurrentActivity(): Activity? = mCurrentActivityWeak?.get()

    override fun onActivityPaused(activity: Activity?) {}

    override fun onActivityResumed(activity: Activity?) {
        mCurrentActivityWeak = WeakReference(activity)
    }

    override fun onActivityStarted(activity: Activity?) {}

    override fun onActivityDestroyed(activity: Activity?) {}

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}

    override fun onActivityStopped(activity: Activity?) {}

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        mCurrentActivityWeak = WeakReference(activity)
    }

}