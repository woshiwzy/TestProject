package com.wangzy.myactivity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.wangzy.aidlclient.R
import com.wangzy.exitappdemo.IImmocAIDL
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {


    private val CONTENT_URI = Uri.parse("content://com.wangzy.provider.permission/query")

    val TAG = "test"


    var iImmocAIDL: IImmocAIDL? = null

    var conn = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            iImmocAIDL = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iImmocAIDL = IImmocAIDL.Stub.asInterface(service)
        }
    }


    fun bindService() {
        var intent = Intent()
        intent.action = ("com.mecury.aidltest.IRomoteService")
        intent.component = (ComponentName("com.wangzy.demo", "com.wangzy.exitappdemo.service.AIDLService"))
        bindService(intent, conn, Context.BIND_AUTO_CREATE)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindService()

        buttonCallAIDL.setOnClickListener {

            var ret = iImmocAIDL!!.add(30, 40)
            var label = "result exit:" + ret + " " + iImmocAIDL!!.getBook("Think in Java 2")
            textView.text = label
            toast(label)
        }

        buttonCallMessgener.setOnClickListener {

            var inent = Intent(this@MainActivity, MessengerClientActivity::class.java)
            startActivity(inent)
        }

        buttonQuery.setOnClickListener {

            Log.i(TAG,"buttonQuerybuttonQuerybuttonQuerybuttonQuery--->")
            var cur = contentResolver.query(CONTENT_URI, null, null, null, null)
            cur?.close()
        }

    }
}
