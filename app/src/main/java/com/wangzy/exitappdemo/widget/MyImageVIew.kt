package com.wangzy.exitappdemo.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import com.wangzy.exitappdemo.consts.TAG
import com.wangzy.exitappdemo.consts.getActionName

class MyImageVIew(context: Context, attrs: AttributeSet?) : android.support.v7.widget.AppCompatImageView(context, attrs) {


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }


    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {

        Log.i(TAG, "MyImageVIew dispatchTouchEvent:"+ getActionName(event!!.action))
        return super.dispatchTouchEvent(event)
    }



    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val action = event?.action


        when (action) {

            MotionEvent.ACTION_DOWN -> {
                Log.i(TAG, "MyImageVIew.onTouchEvent.ACTION_DOWN")
                return true
            }


            MotionEvent.ACTION_MOVE -> {
                Log.i(TAG, "MyImageVIew.onTouchEvent.ACTION_MOVE")
//                return true
            }

            MotionEvent.ACTION_UP -> Log.i(TAG, "MyImageVIew.onTouchEvent.ACTION_UP")

            MotionEvent.ACTION_CANCEL -> Log.i(TAG, "MyImageVIew.onTouchEvent.ACTION_CANCEL")

        }
        return super.onTouchEvent(event)
    }


}
