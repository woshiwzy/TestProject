package com.wangzy.service

import android.accessibilityservice.AccessibilityService
import android.annotation.TargetApi
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.accessibility.AccessibilityEvent
import com.wangzy.exitappdemo.consts.TAG
import com.wangzy.exitappdemo.util.LogUtil

class MyAccService : AccessibilityService() {


    override fun onServiceConnected() {
        super.onServiceConnected()
    }

    override fun onCreate() {
        super.onCreate()
        LogUtil.d(TAG, "TYPE_VIEW_TEXT_CHANGED")
    }


    override fun onInterrupt() {
        LogUtil.d(TAG, "onInterrupt")
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val eventType = event?.eventType


        var childcount=rootInActiveWindow.childCount

        LogUtil.e(TAG,"window?"+rootInActiveWindow.window?.title+"child countL:"+childcount)


        var sourc = event?.source
        var win = event?.source?.window


//        LogUtil.d(TAG, "source:" + sourc)
//        LogUtil.d(TAG, " name:" + event?.className)
//        LogUtil.d(TAG, " itemcount:" + event?.itemCount)

        when (eventType) {
            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                LogUtil.d(TAG, "--->TYPE_VIEW_CLICKED:" + event.text)
            }
            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                LogUtil.d(TAG, "--->TYPE_VIEW_TEXT_CHANGED:")
            }
            AccessibilityEvent.TYPE_VIEW_SCROLLED -> {
                LogUtil.d(TAG, "--->TYPE_VIEW_SCROLLED:")
            }
        }
    }

}
