package com.wangzy.activiti2.consts

import android.view.MotionEvent


const val TAG = "test"

fun getActionName(action: Int): String {

    when (action) {
        MotionEvent.ACTION_CANCEL -> return "ACTION_CANCEL"
        MotionEvent.ACTION_DOWN -> return "ACTION_DOWN"
        MotionEvent.ACTION_MOVE -> return "ACTION_MOVE"
        MotionEvent.ACTION_UP -> return "ACTION_UP"
    }

    return "---"
}