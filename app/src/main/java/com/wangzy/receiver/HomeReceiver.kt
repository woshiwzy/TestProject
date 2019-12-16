package com.wangzy.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.wangzy.exitappdemo.consts.TAG
import android.widget.Toast



class HomeReceiver : BroadcastReceiver() {

    val SYSTEM_DIALOG_REASON_KEY = "reason"
    val SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions"
    val SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps"
    val SYSTEM_DIALOG_REASON_HOME_KEY = "homekey"
    val SYSTEM_DIALOG_REASON_ASSIST = "assist"

    override fun onReceive(arg0: Context, arg1: Intent) {
        val action = arg1.action
        //按下Home键会发送ACTION_CLOSE_SYSTEM_DIALOGS的广播
        if (action == Intent.ACTION_CLOSE_SYSTEM_DIALOGS) {

            val reason = arg1.getStringExtra(SYSTEM_DIALOG_REASON_KEY)
            if (reason != null) {
                if (reason == SYSTEM_DIALOG_REASON_HOME_KEY) {
                    // 短按home键
                    Toast.makeText(arg0, "短按Home键", Toast.LENGTH_SHORT).show()
                    Log.i(TAG,"端按Home")
                } else if (reason == SYSTEM_DIALOG_REASON_RECENT_APPS) {
                    // RECENT_APPS键
                    Toast.makeText(arg0, "RECENT_APPS", Toast.LENGTH_SHORT).show()
                    Log.i(TAG,"RECENT_APPS键")

                }
            }
        }
    }
}
