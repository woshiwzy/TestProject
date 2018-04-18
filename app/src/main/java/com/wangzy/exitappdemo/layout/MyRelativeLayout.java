package com.wangzy.exitappdemo.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.wangzy.exitappdemo.consts.ConstantKt;

public class MyRelativeLayout extends RelativeLayout {
    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(ConstantKt.getTAG(), "MyRelativeLayout.dispatchTouchEvent:" + ConstantKt.getActionName(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(ConstantKt.getTAG(), "MyRelativeLayout.onInterceptTouchEvent");


//        getParent().requestDisallowInterceptTouchEvent(true);

//        int action = ev.getAction();
//        switch (action) {
//
//            case MotionEvent.ACTION_DOWN:
//                Log.i(ConstantKt.getTAG(), "MyRelativeLayout.onInterceptTouchEvent.ACTION_DOWN");
//                return true;
////                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.i(ConstantKt.getTAG(), "MyRelativeLayout.onInterceptTouchEvent.ACTION_MOVE");
//                return true;
////                break;
//            case MotionEvent.ACTION_UP:
//                Log.i(ConstantKt.getTAG(), "MyRelativeLayout.onInterceptTouchEvent.ACTION_UP");
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                Log.i(ConstantKt.getTAG(), "MyRelativeLayout.onInterceptTouchEvent.ACTION_CANCEL");
//                break;
//        }
//
        return super.onInterceptTouchEvent(ev);

//        return true;
    }


    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(ConstantKt.getTAG(), "MyRelativeLayout.onTouchEvent:" + ConstantKt.getActionName(event.getAction()));

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
