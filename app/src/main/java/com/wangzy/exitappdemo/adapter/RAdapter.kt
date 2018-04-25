package com.wangzy.exitappdemo.adapter

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wangzy.exitappdemo.activity.EventStudyActivity
import com.wangzy.exitappdemo.activity.ImageActivity
import com.wangzy.exitappdemo.R
import com.wangzy.exitappdemo.activity.ActivityLifeTest
import com.wangzy.exitappdemo.activity.ViewStubActivity
import com.wangzy.exitappdemo.consts.TAG
import com.wangzy.exitappdemo.mvp.MVPActivity
import com.wangzy.exitappdemo.service.GrayService
import com.wangzy.exitappdemo.service.ServiceWithToast

class RAdapter(private val context: Context) : RecyclerView.Adapter<RAdapter.TViewHolder>() {


    private val datas = arrayOf("Pull Refresh", "Event", "ViewStub", "Image", "GOTO MODEL", "ActivityLife", "LiveService", "BackService", "MVPACtivity", "AA", "BB", "CC", "AA", "BB", "CC", "AA", "BB", "CC")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_adater, parent, false)

        return TViewHolder(view)
    }


    override fun onBindViewHolder(holder: TViewHolder, position: Int) {

        holder.tv.setOnClickListener {


            when (position) {
                1 -> {
                    gotoActivity(EventStudyActivity::class.java)
                }
                2 -> {
                    gotoActivity(ViewStubActivity::class.java)
                }
                3 -> {
                    gotoActivity(ImageActivity::class.java)
                }
                4 -> {
                    gotoModelBActivity("com.wangzy.testmode.MainActivity")
                }
                5->{
                    gotoActivity(ActivityLifeTest::class.java)
                }
                6->{
                    Log.i(TAG,"clicked live service")
                    var intent=Intent(context,GrayService::class.java)
                    context.startService(intent)
                }
                7->{
                    Log.i(TAG,"clicked ServiceWithToast")
                    var intent=Intent(context,ServiceWithToast::class.java)
                    context.startService(intent)
                }
                8->{
                    gotoActivity(MVPActivity::class.java)
                }

            }

        }
        holder.tv.text = datas[position]
    }

    fun gotoModelBActivity(classPath: String) {

//        var claz=Class.forName(classPath)
//        var intent=Intent(context,claz)
//        context.startActivity(intent)

        var intent = Intent()
//        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        var cn = ComponentName("com.wangzy.testmode", (classPath))
        intent.component=cn
        context.startActivity(intent)

    }

    fun gotoActivity(claz: Class<*>) {
        val intent = Intent(context, claz)
        context.startActivity(intent)
    }


    override fun getItemCount(): Int {
        return datas.size
    }

    inner class TViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var tv: TextView

        init {
            tv = itemView.findViewById(R.id.textViewItemLabel)
        }
    }
}
