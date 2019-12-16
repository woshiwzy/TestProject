package com.wangzy.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.wangzy.exitappdemo.R
import com.wangzy.exitappdemo.consts.TAG
import kotlinx.android.synthetic.main.activity_view_stub.*

class ViewStubActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_stub)

//        act_test_viewstub_viewstub

        Log.i(TAG, "veiwstub:" + act_test_viewstub_viewstub)

        act_test_viewstub_tv_show.setOnClickListener {

            var layoutView: View = act_test_viewstub_viewstub.inflate()
            Log.i(TAG, "veiwstub:" + act_test_viewstub_viewstub)
            Log.e(TAG, "layoutView equals finviewbyid(layout): " + layoutView.equals(findViewById(R.id.act_layout_viewstub_new)))
            Log.e(TAG, "layout: " + layoutView)

            if (layoutView != null) {

                // layoutView的root view id 是mViewStub inflatedId指定的ID
                if (layoutView.id == R.id.act_layout_viewstub_new) {
                    Log.e(TAG, "layout root id is act_layout_viewstub_new")
                } else if (layoutView.id == R.id.layout_viewstub_old) {
                    Log.e(TAG, "layout root id is layout_viewstub_old")
                } else {
                    Log.e(TAG, "layout root id is anyone : " + layoutView.id)
                }

                // layoutView的root view布局 和mViewStub的布局保持一致
                var width = layoutView.layoutParams.width

                if (width == ViewGroup.LayoutParams.MATCH_PARENT) {
                    Log.e(TAG, "layout width is MATCH_PARENT")
                } else if (width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    Log.e(TAG, "layout width is WRAP_CONTENT")
                } else {
                    Log.e(TAG, "layout width is anyone : " + width)
                }

            }
        }


    }
}
