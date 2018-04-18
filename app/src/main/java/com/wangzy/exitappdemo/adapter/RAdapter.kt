package com.wangzy.exitappdemo.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wangzy.exitappdemo.EventStudyActivity
import com.wangzy.exitappdemo.ImageActivity
import com.wangzy.exitappdemo.R
import com.wangzy.exitappdemo.ViewStubActivity

class RAdapter(private val context: Context) : RecyclerView.Adapter<RAdapter.TViewHolder>() {


    private val datas = arrayOf("Pull Refresh", "Event", "ViewStub", "Image", "BB", "CC", "AA", "BB", "CC", "AA", "BB", "CC", "AA", "BB", "CC", "AA", "BB", "CC")


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
                3->{
                    gotoActivity(ImageActivity::class.java)
                }

            }

        }
        holder.tv.text = datas[position]
    }


    fun gotoActivity(claz:Class<*>){
        val intent = Intent(context,ImageActivity::class.java)
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
