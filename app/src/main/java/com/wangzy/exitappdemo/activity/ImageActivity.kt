package com.wangzy.exitappdemo.activity

import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import com.wangzy.exitappdemo.consts.TAG
import android.util.DisplayMetrics
import com.wangzy.exitappdemo.R


class ImageActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        Log.i(TAG,"density:"+dm.density+" widthPix:"+" dpi:"+dm.densityDpi)

//        pixelSize = scaledPixelSize as Int * dm.scaledDensity
//

        var bm = BitmapFactory.decodeResource(resources, R.drawable.test)


        Log.i(TAG,"bytecount:"+bm.byteCount)
//        Log.i(TAG,"allow byte:"+bm.allocationByteCount)
        Log.i(TAG,"allow M:"+bm.byteCount/1024/1024)
        Log.i(TAG,"width:"+bm.width+" height:"+bm.height)
//        imageView2.setImageBitmap(bm)
    }
}
