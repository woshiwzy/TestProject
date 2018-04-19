package com.wangzy.exitappdemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.WindowManager
import com.wangzy.exitappdemo.adapter.RAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class AActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_main)


        recycleView.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        recycleView.adapter=RAdapter(this@AActivity)


        with(pullRefresh){
            post { autoRefresh() }
        }


        launch (CommonPool){
            delay(3000)


            launch(UI){
                pullRefresh.stopRefresh()
            }
        }


    }



    override fun onPause() {
        super.onPause()
        Log.i(com.wangzy.exitappdemo.consts.TAG, "AonPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(com.wangzy.exitappdemo.consts.TAG, "AonStop")
    }

    override fun onNewIntent(intent: Intent?) {
        Log.i(com.wangzy.exitappdemo.consts.TAG, "on new intent")
        finish()
    }
}
