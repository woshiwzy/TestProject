package com.wangzy.exitappdemo.service

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import com.wangzy.exitappdemo.consts.TAG

class MessengerService : Service() {


    val SERVICEID=0x0001


    var messenger= Messenger(object : Handler() {

        override fun handleMessage(msg: Message?) {

            when(msg?.arg1){

                SERVICEID->{


                    val str=msg.data.get("content")
                    Log.i(TAG,"客户端传来消息："+str)

                    val msgto=Message.obtain()
                    msgto.arg1=0x0002

                    val bundle=Bundle()
                    bundle.putString("content","我是服务器字符串")
                    msgto.data=bundle
                    msg.replyTo.send(msgto)

                }

            }

        }

    })


    override fun onBind(intent: Intent?): IBinder {
        return messenger.binder
    }

}
