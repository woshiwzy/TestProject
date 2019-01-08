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
import com.wangzy.activity.GreenDaoActivity
import com.wangzy.exitappdemo.R
import com.wangzy.exitappdemo.activity.*
import com.wangzy.exitappdemo.consts.TAG
import com.wangzy.exitappdemo.mvp.MVPActivity
import com.wangzy.exitappdemo.service.GrayService
import com.wangzy.exitappdemo.service.ServiceWithToast
import com.wangzy.exitappdemo.util.LogUtil
import com.wangzy.exitappdemo.widget.MyPullRefresh
import com.wangzy.flight.FlightSeatListActivity
import com.wangzy.myactivity.DragTextActivity
import com.wangzy.myactivity.WorkMainActivity

class RAdapter(private val context: Context, val myPullRefresh: MyPullRefresh) : RecyclerView.Adapter<RAdapter.TViewHolder>() {


    private val datas = arrayOf("Pull Refresh",
            "Event",
            "ViewStub",
            "Image",
            "GOTO MODEL",
            "ActivityLife",
            "LiveService",
            "BackService",
            "MVPACtivity",
            "Animate",
            "TimeLine",
            "WorkActivity",
            "Trace",
            "Rx",
            "Accessbility",
            "飞机图",
            "拖动TextView",
            "GreenDao")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_adater, parent, false)

        return TViewHolder(view)
    }


    override fun onBindViewHolder(holder: TViewHolder, position: Int) {


        holder.tv.setOnClickListener {

            Log.i(TAG, "isanimate:" + myPullRefresh.isAnimate)
            if (myPullRefresh.isAnimate) {

            } else {
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
                    5 -> {
                        gotoActivity(ActivityLifeTest::class.java)
                    }
                    6 -> {
                        Log.i(TAG, "clicked live service")
                        var intent = Intent(context, GrayService::class.java)
                        context.startService(intent)
                    }
                    7 -> {
                        Log.i(TAG, "clicked ServiceWithToast")
                        var intent = Intent(context, ServiceWithToast::class.java)
                        context.startService(intent)
                    }
                    8 -> {
                        gotoActivity(MVPActivity::class.java)
                    }
                    9 -> {
                        gotoActivity(AnimateActivity::class.java)
                    }
                    10 -> {
                        gotoActivity(TimeLineActivity::class.java)
                    }
                    11 -> {
                        gotoActivity(WorkMainActivity::class.java)
                    }
                    12 -> {
                        startTracetest()
                    }
                    13 -> {
                        gotoActivity(RxActivity::class.java)
                    }
                    14 -> {
                        //to accessbility
                        gotoActivity(AccessbilifyActivityDemo::class.java)

                    }
                    15 -> {
//                        gotoActivity(FlightSeatActivity::class.java)
                        gotoActivity(FlightSeatListActivity::class.java)
                    }
                    16 -> {
                        gotoActivity(DragTextActivity::class.java)
                    }

                    17 -> {
                        gotoActivity(GreenDaoActivity::class.java)
                    }


                }
            }

        }
        holder.tv.text = datas[position]
    }


    fun startTracetest() {

        fun test1() {

            var total: Long = 0
            for (i in 1..100000) {
                total += i
            }
        }

        fun test2() {
            var total: Int = 0
            for (i in 1..1000) {
                total *= i
            }
        }


        test1()
        test2()
        LogUtil.e(TAG, "done===============")
    }


    fun gotoModelBActivity(classPath: String) {

//        var claz=Class.forName(classPath)
//        var intent=Intent(context,claz)
//        context.startActivity(intent)

        var intent = Intent()
//        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        var cn = ComponentName("com.wangzy.testmode", (classPath))
        intent.component = cn
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
