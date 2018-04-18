package com.wangzy.exitappdemo.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

open class CircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {


    var process: Float = 0.0f
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas?) {
        var w = width.toFloat()
        var h = height.toFloat()
        var r = Math.min(w, h) / 2-5
        canvas?.drawColor(Color.TRANSPARENT)

        val p = Paint()
        p.isAntiAlias = true

        p.color = Color.BLUE
        p.style = Paint.Style.STROKE
        p.strokeWidth = 5.0f
        canvas?.drawCircle(w / 2, h / 2, r, p)


        if(Math.abs(process)==1.0f){


        }else{



            var rectF = RectF()
            rectF.left = w / 2 - r
            rectF.top = h / 2 - r

            rectF.right = w / 2 + r
            rectF.bottom = h / 2 + r

            val p2 = Paint()
            p2.isAntiAlias = true
            p2.color = Color.RED

            canvas?.drawArc(rectF, 0.0f, 360 * process, true, p2)

        }

    }

    open fun animation(){



    }

}