package com.wangzy.exitappdemo.activity

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.*
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.KeyEvent
import com.wangzy.exitappdemo.R
import com.wangzy.exitappdemo.adapter.RAdapter
import com.wangzy.exitappdemo.consts.TAG
import com.wangzy.exitappdemo.receiver.HomeReceiver
import com.wangzy.exitappdemo.service.MyJobService
import com.wangzy.exitappdemo.service.ServiceWithToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import android.widget.Toast
import android.view.KeyEvent.KEYCODE_HOME
import android.view.KeyEvent.KEYCODE_BACK




class MainActivity : Activity() {

//    lateinit var screenManager:ScreenManager

    var serviceWithToast: ServiceWithToast? = null

    lateinit var homeReceiver: HomeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        var x:Int= 0x80000000.toInt()
        var y:Int= 0x80000000.toInt()
        this.window.setFlags(x, y)

        setContentView(R.layout.activity_main)
        homeReceiver= HomeReceiver()


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


        buttonStopService.setOnClickListener {

            if (null != serviceWithToast) {
                unbindService(serviceConnect)
            } else {
                var intent = Intent(this@MainActivity, ServiceWithToast::class.java)


                bindService(intent, serviceConnect, Context.BIND_AUTO_CREATE)
            }


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

   override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this@MainActivity, "返回键无效", Toast.LENGTH_SHORT).show()
            return true//return true;拦截事件传递,从而屏蔽back键。
        }
        return super.onKeyDown(keyCode, event)
    }




    override fun onResume() {
        super.onResume()
//        screenManager.finishActivity()

        val fileter=IntentFilter()
        fileter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        registerReceiver(homeReceiver,fileter)
    }


    override fun onPause() {
        super.onPause()
        Log.i(com.wangzy.exitappdemo.consts.TAG, "AonPause")
        unregisterReceiver(homeReceiver)
//        screenManager.startActivity()
    }



    override fun onStop() {
        super.onStop()
        Log.i(com.wangzy.exitappdemo.consts.TAG, "AonStop")
    }

    override fun onNewIntent(intent: Intent?) {
        Log.i(com.wangzy.exitappdemo.consts.TAG, "on new intent")
    }


    var serviceConnect = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName?) {
            serviceWithToast = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

            var myBinder = service as ServiceWithToast.MyBinder

            serviceWithToast = myBinder.getService()
            serviceWithToast?.excute()
        }
    }

    override fun onDestroy() {
        if (null != serviceWithToast) {
            unbindService(serviceConnect)
        }
        super.onDestroy()
    }
}
