package com.wangzy.exitappdemo

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.squareup.leakcanary.LeakCanary
import java.lang.ref.WeakReference

class App : Application() {





    var list = mutableListOf<WeakReference<Activity>>()
        get() = field
        set(value) {
            field = value
        }

    override fun onCreate() {
        super.onCreate()
        instance=this

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            override fun onActivityPaused(p0: Activity?) {
            }

            override fun onActivityResumed(p0: Activity?) {
            }

            override fun onActivityStarted(p0: Activity?) {
            }

            override fun onActivityDestroyed(p0: Activity?) {
            }

            override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
            }

            override fun onActivityStopped(p0: Activity?) {
            }

            override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
                list.add(WeakReference(p0!!))
            }
        })



        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }

    fun exitApp() {

        for (activity in list) {
            activity.get()?.finish()
        }
    }

    companion object {

        lateinit var instance:App
    }
}