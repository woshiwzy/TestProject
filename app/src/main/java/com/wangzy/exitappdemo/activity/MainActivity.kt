package com.wangzy.exitappdemo.activity

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.wangzy.exitappdemo.R
import com.wangzy.exitappdemo.adapter.RAdapter
import com.wangzy.exitappdemo.consts.TAG
import com.wangzy.exitappdemo.service.MyJobService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch


class MainActivity : Activity() {

//    lateinit var screenManager:ScreenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_main)

        var msg = intent?.getStringExtra("msg")
        Log.i(TAG, "我是被唤醒的:" + msg)

//        screenManager = ScreenManager.getInstance(this@MainActivity)


        recycleView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycleView.adapter = RAdapter(this@MainActivity)


        with(pullRefresh) {
            post { autoRefresh() }
        }


        launch(CommonPool) {
            delay(3000)


            launch(UI) {
                pullRefresh.stopRefresh()
            }
        }



        buttonStartJob.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
                val jobInfo = JobInfo.Builder(1, ComponentName(packageName, MyJobService::class.java.name))
                        .setPeriodic(2000)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .build()
                jobScheduler.schedule(jobInfo)
            }
        }

        buttonAlarm.setOnClickListener {


            val intent = Intent("ELITOR_CLOCK")
            intent.putExtra("msg", "  alarm 你该打酱油了")
            intent.setClass(this, MainActivity::class.java)
            val pi = PendingIntent.getActivity(this, 0, intent, 0)
            val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (5 * 1000).toLong(), pi)

        }

//        //启动单像素activity保持进程优先级
//        val screenManager = ScreenManager.getInstance(this@MainActivity)
//        val listener = ScreenBroadcastListener(this)
//        listener.registerListener(object : ScreenBroadcastListener.ScreenStateListener {
//            override fun onScreenOn() {
//                screenManager.finishActivity()
//            }
//
//            override fun onScreenOff() {
//                screenManager.startActivity()
//            }
//        })

    }

//    override fun onBackPressed() {
//        moveTaskToBack(true)
//    }

    override fun onResume() {
        super.onResume()


//        screenManager.finishActivity()
    }


    override fun onPause() {
        super.onPause()
        Log.i(com.wangzy.exitappdemo.consts.TAG, "AonPause")

//        screenManager.startActivity()
    }

    override fun onStop() {
        super.onStop()
        Log.i(com.wangzy.exitappdemo.consts.TAG, "AonStop")
    }

    override fun onNewIntent(intent: Intent?) {
        Log.i(com.wangzy.exitappdemo.consts.TAG, "on new intent")
    }
}
