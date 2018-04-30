package com.wangzy.exitappdemo.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.animation.Interpolator
import com.wangzy.exitappdemo.R
import kotlinx.android.synthetic.main.activity_animate.*

class AnimateActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animate)
        val display = windowManager.defaultDisplay
        val refreshRate = display.refreshRate

        Log.i(com.wangzy.exitappdemo.consts.TAG, "refresh rate:" + refreshRate)

        buttonStartAnimation.setOnClickListener {

            val animator = ObjectAnimator.ofObject(textViewAnimate, "translationX", MyEvalator(), 0f, 300f)

            animator.interpolator = MyInterpolater()
            animator.setDuration(2000)
            animator.addListener(object : AnimatorListenerAdapter() {

                @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
                override fun onAnimationEnd(animation: Animator?) {

                    var interpolater = animation?.interpolator as MyInterpolater
                    Log.i(com.wangzy.exitappdemo.consts.TAG, "after execute :" + interpolater.count)

                }

            })
            animator.start()

        }
    }


    class MyInterpolater : Interpolator {

        var count = 0

        override fun getInterpolation(input: Float): Float {
            Log.i(com.wangzy.exitappdemo.consts.TAG, "intercolpation:" + input)
            count++
            return input
        }
    }

    class MyEvalator : TypeEvaluator<Float> {

        override fun evaluate(fraction: Float, startValue: Float?, endValue: Float?): Float {
            val startFloat = (startValue as Number).toFloat()
//            Log.i(com.wangzy.exitappdemo.consts.TAG, "startfloat:" + startFloat+" fraction:"+fraction+" end:"+endValue)
            var ret=startFloat + fraction * ((endValue as Number).toFloat() - startFloat)
            return ret
        }

    }
}
