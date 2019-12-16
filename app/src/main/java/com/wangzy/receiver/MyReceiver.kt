package com.wangzy.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.wangzy.exitappdemo.consts.TAG

class MyReceiver: BroadcastReceiver() {




    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e(TAG,"MyReceiver.onReceive:"+Thread.currentThread().name+" action:"+intent!!.action)
    }

}