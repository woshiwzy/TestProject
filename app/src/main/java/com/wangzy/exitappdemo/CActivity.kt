package com.wangzy.exitappdemo

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import org.jetbrains.anko.longToast
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class CActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        findViewById<View>(R.id.button3).setOnClickListener {
            val intent = Intent(this@CActivity, DActivity::class.java)
            startActivity(intent)
        }


        var pool = Executors.newFixedThreadPool(3)


        pool.submit(object : Runnable {
            override fun run() {
                Log.i(com.wangzy.exitappdemo.consts.TAG, "gagagagaga:" + this@CActivity.javaClass.toString())
            }
        })


        thread {



            runOnUiThread(object :Runnable{
                override fun run() {


                    longToast("hello This is a test")


                }
            })
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.RED   //这里动态修改颜色

        }

    }
}
