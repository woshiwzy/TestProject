package com.wangzy.exitappdemo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.wangzy.exitappdemo.R;
import com.wangzy.exitappdemo.consts.ConstantKt;

public class MyPullRefresh extends ViewGroup {


    private int width;
    private int height;
    private View child;
    private PullRefreshHeaderView headerView;
    private int MAX_PULL_PIX = 250;
    private OnPullProgressChangeListener onPullProgressChangeListener;
    private int childFirstVisible = 0;

    private PointF point = new PointF();
    private int delta = 0;
    private boolean isPullDown = false;
    private boolean isAnimate=false;


    public MyPullRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        if (null == headerView) {
            headerView = (PullRefreshHeaderView) View.inflate(context, R.layout.header_view, null);
            addView(headerView, width, MAX_PULL_PIX);//never forget this
        }
        MAX_PULL_PIX = getDisplayMetrics().y / 8;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        headerView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(MAX_PULL_PIX, MeasureSpec.EXACTLY));

        child = getChildAt(1);
        child.measure(widthMeasureSpec, heightMeasureSpec);
    }


    public Point getDisplayMetrics() {
        DisplayMetrics dm;
        dm = getContext().getApplicationContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        return new Point(screenWidth, screenHeight);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(ConstantKt.getTAG(), "===========>onInterceptTouchEvent");
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        int action = event.getAction();

        switch (action) {

            case MotionEvent.ACTION_DOWN: {
                childFirstVisible = getFirstListVisible("dispatchTouchEvent ACTION_DOWN");
                isPullDown = (event.getY() - point.y) > 0;
                point.x = event.getX();
                point.y = event.getY();
            }

            case MotionEvent.ACTION_MOVE: {

                isPullDown = (event.getY() - point.y) > 0;

                int tempDelta = (int) (event.getY() - point.y);
                if ((delta + tempDelta) > 0) {
                    if ((delta + tempDelta) > MAX_PULL_PIX) {
                        delta = MAX_PULL_PIX;
                    } else {
                        delta += tempDelta;
                    }
                    if (0 == getFirstListVisible("actionmove")) {
                        updateContentLayout(delta);
                        point.x = event.getX();
                        point.y = event.getY();
                    }

                } else {
                    delta = 0;
                }

                childFirstVisible = getFirstListVisible("dispatchTouchEvent ACTION_MOVE delta:" + delta);
            }


            break;

            case MotionEvent.ACTION_UP:
                isPullDown = (event.getY() - point.y) > 0;

                point.x = event.getX();
                point.y = event.getY();
                Log.i(ConstantKt.getTAG(), " 1.delta:" + delta + " is pull down:" + isPullDown);

                if (getFirstListVisible("actionUP") == 0) {
                    smmonthBack(delta);//自动弹回
                } else {
                    delta = 0;
                    isAnimate=false;
                }

                childFirstVisible = getFirstListVisible("dispatchTouchEvent ACTION_UP");
                break;

        }

        return super.dispatchTouchEvent(event);
    }


    private int getFirstListVisible(String metodName) {

        RecyclerView recyclerView = (RecyclerView) child;


        recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                childFirstVisible = getFirstListVisible("OnFlingListener");
                if (0 == childFirstVisible) {


                }
                return false;
            }
        });

        LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();

        int fpp = lm.findFirstVisibleItemPosition();

        Log.e(ConstantKt.getTAG(), metodName + ":fist see:" + fpp + " ispullDown:" + isPullDown);

        return fpp;
    }


    private void smmonthBack(final int deltaX) {
        if (delta == 0) {
            return;
        }

        final ValueAnimator va = ValueAnimator.ofInt(deltaX, 0);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (Integer) animation.getAnimatedValue();
                delta = val;
                updateContentLayout(val);
            }
        });
        va.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                isAnimate=true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                delta = 0;
                isAnimate=false;
            }
        });

        va.setDuration(300);
        va.start();
    }

    private void updateContentLayout(int topDelta) {
//        Log.i(ConstantKt.getTAG(), "top delta:" + topDelta+ " header height:"+headerView.getHeight());

        final float progress = topDelta * 1.0f / MAX_PULL_PIX;
//        delta = MAX_PULL_PIX;

        headerView.layout(0, -MAX_PULL_PIX + topDelta, width, -MAX_PULL_PIX + topDelta + MAX_PULL_PIX);
        child.layout(0, topDelta, width, topDelta + height);


        if (null != onPullProgressChangeListener) {
            onPullProgressChangeListener.onProgressChanged(progress);
        }

        if (null != headerView) {
            headerView.onProgress(progress);
        }

    }

    public void autoRefresh() {

        final ValueAnimator va = ValueAnimator.ofInt(0, MAX_PULL_PIX);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (Integer) animation.getAnimatedValue();
                updateContentLayout(val);

            }
        });
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });

        va.setDuration(300);
        va.start();

    }

    public void stopRefresh() {
        final ValueAnimator va = ValueAnimator.ofInt(MAX_PULL_PIX, 0);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (Integer) animation.getAnimatedValue();
                updateContentLayout(val);
            }
        });
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });

        va.setDuration(300);
        va.start();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        child.layout(0, delta, width, delta + height);
    }

    public OnPullProgressChangeListener getOnPullProgressChangeListener() {
        return onPullProgressChangeListener;
    }

    public void setOnPullProgressChangeListener(OnPullProgressChangeListener onPullProgressChangeListener) {
        this.onPullProgressChangeListener = onPullProgressChangeListener;
    }

    public boolean isAnimate() {
        return isAnimate;
    }

}
