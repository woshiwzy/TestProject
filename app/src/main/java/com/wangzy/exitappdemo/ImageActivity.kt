package com.wangzy.exitappdemo

import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import com.wangzy.exitappdemo.consts.TAG
import kotlinx.android.synthetic.main.activity_image.*
import android.util.DisplayMetrics



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
        imageView2.setImageBitmap(bm)
    }
}
