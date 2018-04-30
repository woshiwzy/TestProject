package com.wangzy.exitappdemo.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.wangzy.exitappdemo.page_timeline.PageMileStone

class TimeLineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var pageMileStone=PageMileStone(this)
        pageMileStone.onShow()

        setContentView(pageMileStone.rootView)
    }
}
