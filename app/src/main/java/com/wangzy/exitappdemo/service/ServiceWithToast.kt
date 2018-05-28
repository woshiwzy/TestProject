package com.wangzy.exitappdemo.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.wangzy.exitappdemo.consts.TAG
//import com.wangzy.exitappdemo.util.DkToast
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.CountDownLatch


class ServiceWithToast : Service() {

    lateinit var handler: Handler

     var  binder= MyBinder()

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "ServiceWithToast.onCreate:" + Thread.currentThread().name)
        handler = Handler(Looper.getMainLooper())
    }

    override fun onBind(intent: Intent): IBinder {
        Log.i(TAG, "ServiceWithToast.onBind")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(TAG,"ServiceWithToast.onUnbind")
        return super.onUnbind(intent)
    }



     fun excute(){

        Log.i(TAG,"excute=========>>>>")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "ServiceWithToast.onStartCommand")


        return super.onStartCommand(intent, flags, startId)
    }


    inner class MyBinder : Binder() {
        fun getService(): ServiceWithToast {
            return this@ServiceWithToast
        }
    }


    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        Log.i(TAG, "ServiceWithToast.onDestroy")
        super.onDestroy()
    }


}
