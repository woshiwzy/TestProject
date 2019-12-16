package com.wangzy.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wangzy.exitappdemo.R
import com.wangzy.service.MyAccService
import kotlinx.android.synthetic.main.activity_accessbilify_demo.*

class AccessbilifyActivityDemo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accessbilify_demo)


        buttonStart.setOnClickListener({


            val serverviceIntent = Intent(this@AccessbilifyActivityDemo, MyAccService::class.java)
            startService(serverviceIntent)



        })

    }
}
