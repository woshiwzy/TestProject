package com.wangzy.activity

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import com.wangzy.activiti2.consts.TAG
import com.wangzy.exitappdemo.R


class ActivityLifeTest : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_test)
        Log.e(TAG, "onCreate======================>")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            Log.i(TAG, "当前屏幕为横屏")
        } else {
            Log.i(TAG, "当前屏幕为竖屏")
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }


    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

}
