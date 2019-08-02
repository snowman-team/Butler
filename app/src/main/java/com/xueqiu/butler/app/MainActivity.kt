package com.xueqiu.butler.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.xueqiu.butler.Butler
import com.xueqiu.butler.LoaderCallback

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Butler.executeLoader(MainLoader.LOADER_NAME, object : LoaderCallback {

            override fun onSuccess() {
                Log.i("execute main loader", "success")
            }

            override fun onFail(e: Exception) {
                Log.e("execute main loader", "fail", e)
            }
        })

        Butler.getCurrentActivity()

        val context = Butler.getAppContext()
    }
}
