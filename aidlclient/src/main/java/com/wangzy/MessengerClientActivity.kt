package com.wangzy

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import com.wangzy.aidlclient.R

class MessengerClientActivity : AppCompatActivity() {

    val ACTIVITYID = 0X0002
    val TAG = "MainActivity"
    var sMessenger: Messenger? = null
    var aMessenger = Messenger(object : Handler() {

        override fun handleMessage(msg: Message?) {
            if (msg?.arg1 == ACTIVITYID) {
                Log.d(TAG, "服务端传来了消息=====>>>>>>>")
                var str = msg.data.get("content")
                Log.d(TAG, "content:" + str)
            }
        }
    })


    var serviceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            sMessenger = Messenger(service)
            val message = Message.obtain()
            message.arg1 = 0x0001
            message.replyTo = aMessenger
            val bundle = Bundle()
            bundle.putString("content", "我就是Activity传过来的字符串")
            message.data=bundle
            //消息从客户端发出
            sMessenger?.send(message)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.e(TAG, "连接Service失败")
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messenger_client)
        findViewById<Button>(R.id.buttonClient).setOnClickListener {
            startAndBindService()
        }
    }


    fun startAndBindService() {
        var serviceIntent = Intent()
        serviceIntent.setClassName("com.wangzy.demo", "com.wangzy.exitappdemo.service.MessengerService")
        startService(serviceIntent)
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

}
