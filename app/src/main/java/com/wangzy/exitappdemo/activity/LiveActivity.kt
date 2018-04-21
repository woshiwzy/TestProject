package com.wangzy.exitappdemo.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import com.wangzy.exitappdemo.R
import com.wangzy.exitappdemo.consts.TAG
import kotlinx.android.synthetic.main.activity_main.*
import android.app.job.JobInfo
import com.wangzy.exitappdemo.service.MyJobService
import android.content.ComponentName
import android.app.job.JobScheduler
import android.os.Build
import android.support.annotation.RequiresApi


class LiveActivity : Activity() {


    companion object {

        fun actionToLiveActivity(pContext: Context) {
            val intent = Intent(pContext, LiveActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            pContext.startActivity(intent)

            Log.i(TAG,"启动单像素activity")
        }
    }


    override fun onResume() {
        super.onResume()

        Log.e(TAG,"单像素Activity onResume")
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        return false
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.singla_pix)



        val window = window
        //放在左上角
        window.setGravity(Gravity.START or Gravity.TOP)
        val attributes = window.attributes
        //宽高设计为1个像素
        attributes.width = 100
        attributes.height = 100

        //起始坐标
        attributes.x = 0
        attributes.y = 0
        window.attributes = attributes


        window.decorView.isClickable=false

        ScreenManager.getInstance(this).setActivity(this)

        buttonStartJob.setOnClickListener {

            val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            val jobInfo = JobInfo.Builder(1, ComponentName(packageName, MyJobService::class.java.name))
                    .setPeriodic(2000)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .build()
            jobScheduler.schedule(jobInfo)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "单像素activity关闭")
    }
}
