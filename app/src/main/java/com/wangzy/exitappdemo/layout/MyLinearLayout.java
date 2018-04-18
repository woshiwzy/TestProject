package com.wangzy.exitappdemo.layout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.wangzy.exitappdemo.consts.ConstantKt;

public class MyLinearLayout extends LinearLayout {


    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.i(ConstantKt.getTAG(), "MyLinearLayout.dispatchTouchEvent:" + ConstantKt.getActionName(ev.getAction()));

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Log.i(ConstantKt.getTAG(), "MyLinearLayout.onInterceptTouchEvent");
        int action = ev.getAction();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                Log.i(ConstantKt.getTAG(), "MyLinearLayout.onInterceptTouchEvent.ACTION_DOWN");
//                return true;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(ConstantKt.getTAG(), "MyLinearLayout.onInterceptTouchEvent.ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(ConstantKt.getTAG(), "MyLinearLayout.onInterceptTouchEvent.ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(ConstantKt.getTAG(), "MyLinearLayout.onInterceptTouchEvent.ACTION_CANCEL");
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(ConstantKt.getTAG(), "MyLinearLayout.onTouchEvent:" + ConstantKt.getActionName(event.getAction()));

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:

//                return true;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }

        return super.onTouchEvent(event);
    }
}
