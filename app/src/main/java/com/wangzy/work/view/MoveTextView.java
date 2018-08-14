package com.wangzy.work.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.wangzy.exitappdemo.consts.ConstantKt;
import com.wangzy.exitappdemo.util.LogUtil;


public class MoveTextView extends android.support.v7.widget.AppCompatTextView {


    private float lastX = 0;
    private float lastY = 0;

    public MoveTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                LogUtil.d(ConstantKt.getTAG(), "111move:" + lastX + " " + lastY);
                return true;

            case MotionEvent.ACTION_MOVE:

                lastX = event.getX();
                lastY = event.getY();

                LogUtil.d(ConstantKt.getTAG(), "11move:" + lastX + " " + lastY);

//                invalidate();

                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        LogUtil.d(ConstantKt.getTAG(), "11move:==================");
        canvas.translate(lastX, lastY);
    }
}

