package com.wangzy.exitappdemo.page_timeline;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by wangzy on 15/9/11.
 */
public class MyListView extends ListView {

    public OnListViewTouchListener onListViewTouchListener;
    private LayoutDoneListener layoutDoneListener;

    public MyListView(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (null != onListViewTouchListener) {
                    onListViewTouchListener.onTouchListener(true);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (null != onListViewTouchListener) {
                    onListViewTouchListener.onTouchListener(false);
                }
                break;

        }

        return super.onTouchEvent(ev);
    }


    public interface OnListViewTouchListener {

        public void onTouchListener(boolean touchDown);
    }


    public OnListViewTouchListener getOnListViewTouchListener() {
        return onListViewTouchListener;
    }

    public void setOnListViewTouchListener(OnListViewTouchListener onListViewTouchListener) {
        this.onListViewTouchListener = onListViewTouchListener;
    }

    public LayoutDoneListener getLayoutDoneListener() {
        return layoutDoneListener;
    }

    public void setLayoutDoneListener(LayoutDoneListener layoutDoneListener) {
        this.layoutDoneListener = layoutDoneListener;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        super.onLayout(changed, l, t, r, b);

        if(null!=layoutDoneListener){
            layoutDoneListener.onLayoutDone(this);
        }

    }

    public static abstract class LayoutDoneListener {

        public abstract void onLayoutDone(MyListView listView);

    }
}
