package com.wangzy.exitappdemo.activity

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import com.wangzy.exitappdemo.R
import com.wangzy.exitappdemo.consts.TAG
import com.wangzy.exitappdemo.util.Tool
import kotlinx.android.synthetic.main.activity_image.*


class ImageActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        Log.i(TAG, "density:" + dm.density + " widthPix:" + " dpi:" + dm.densityDpi)

//        pixelSize = scaledPixelSize as Int * dm.scaledDensity
        var p = Tool.getDisplayMetrics(this)
        Log.i(TAG, "density:" + dm.density + " widthPix:" + " dpi:" + dm.densityDpi)
        textViewScreenInfo.text = "density:" + dm.density + " widthPix:" + " dpi:" + dm.densityDpi + " width:height" + p.x + " " + p.y

//

//        var bm = BitmapFactory.decodeResource(resources, R.drawable.test)


    }
}
