package com.wangzy.exitappdemo.page_timeline;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.LinearLayout;

import com.wangzy.exitappdemo.R;
import com.wangzy.exitappdemo.util.Tool;

/**
 * Created by wangzy on 15/9/8.
 */
public class MilestoneView extends LinearLayout {

    private Context context;
    private View contentView;

    private MyListView listViewLeft;
    private MyListView listViewRight;
    private TimeLineView viewTimeLine;

    public static int timeline_width = 70;

    private int SCREEN_WIDTH = 0;
    private int ROOTVIEW_WIDTH = 0;

    private boolean isLeftRightEvent = false;

    private OnActionDownUpListener onActionDownUpListener;
    private OnAnimationDownListener onAnimationDownListener;
    private VelocityTracker vt = null;

    public MilestoneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        layoutViews();
    }


    protected void layoutViews() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        contentView = View.inflate(context, R.layout.page_milestone, null);

        SCREEN_WIDTH = Tool.getDisplayMetrics(context).x;

//        LogUtil.i(App.tag, "screen_width:" + SCREEN_WIDTH);

        ROOTVIEW_WIDTH = (SCREEN_WIDTH - timeline_width) + timeline_width;

//        LogUtil.i(App.tag, "rootWidth:" + ROOTVIEW_WIDTH);

        int listWidth = (ROOTVIEW_WIDTH - timeline_width);

        //====

        int lrMargin = (int) (timeline_width / 4.0);

        listViewLeft = contentView.findViewById(R.id.listViewLeft);
        LinearLayout.LayoutParams lpleft = new LinearLayout.LayoutParams(listWidth - lrMargin, -1);
        lpleft.leftMargin = lrMargin;
        listViewLeft.setLayoutParams(lpleft);

        //===
        LinearLayout.LayoutParams lpRight = new LinearLayout.LayoutParams(listWidth - lrMargin, -1);
        listViewRight = contentView.findViewById(R.id.listViewRight);
        lpRight.rightMargin = lrMargin;
        listViewRight.setLayoutParams(lpRight);

        //====
        viewTimeLine = contentView.findViewById(R.id.timeLineView);
        LinearLayout.LayoutParams lpTimes = new LinearLayout.LayoutParams(timeline_width, -1);
        viewTimeLine.setLayoutParams(lpTimes);


        addView(contentView, layoutParams);
    }

    private Point point = new Point();
    private final static int TEST_DX = 20;
    private boolean isTestComplete = false;

    private boolean isListTouchDown = true;


    float x = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (vt == null) {
                vt = VelocityTracker.obtain();
            } else {
                vt.clear();
            }
            vt.addMovement(ev);
            x = ev.getX();
        }

        if (!isTestComplete) {
            getEventType(ev);
        }

        if (!isLeftRightEvent) {
            isLeftRightEvent = false;
            isTestComplete = false;
            return super.dispatchTouchEvent(ev);//dispatch listview ScrollView
        }


        if (isLeftRightEvent) {

            switch (ev.getActionMasked()) {

                case MotionEvent.ACTION_MOVE:
                    int curScrollX = getScrollX();
                    int dis_x = (int) (ev.getX() - point.x);

                    int expectX = -dis_x + curScrollX;
                    int finalX = 0;

                    if (expectX < 0) {
                        finalX = Math.max(expectX, -listViewLeft.getWidth());
                    } else {
                        finalX = Math.min(expectX, listViewRight.getWidth());
                    }

                    if (finalX <= 0) {
                        finalX = 0;
                    }

                    vt.addMovement(ev);
                    //代表的是监测每1000毫秒手指移动的距离（像素）即m/px，这是用来控制vt的单位，若括号内为1，则代表1毫秒手指移动过的像素数即ms/px
                    vt.computeCurrentVelocity(1000);

                    scrollTo(finalX, 0);
                    point.x = (int) ev.getX();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    isLeftRightEvent = false;
                    isTestComplete = false;
                    float nx = ev.getX();
                    int vx = (int) Math.abs(vt.getXVelocity());
                    if(vx>1000){
                        if(nx>x){
                           showLeft();
                        }else{
                            showRight();
                        }
                    }else{
                        autoScroView();
                    }

                    if (nx > x) {
                    } else {
                    }
                    vt.clear();
//                    autoScroView();
                    break;

            }
            return false;
        } else {
            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_UP:
                    isLeftRightEvent = false;
                    isTestComplete = false;
                    break;
            }
            return true;
        }


    }


    private void getEventType(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:

                point.x = (int) event.getX();
                point.y = (int) event.getY();

                if (null != onActionDownUpListener) {
                    onActionDownUpListener.onActionUpDown(false);
                }
                break;
            case MotionEvent.ACTION_MOVE:

                int dx = Math.abs((int) event.getX() - point.x);
                int dy = Math.abs((int) event.getY() - point.y);

                if (dx >= TEST_DX && dx > dy) {//left or right move
                    isLeftRightEvent = true;
                    isTestComplete = true;
                } else {
                    isLeftRightEvent = false;
                    isTestComplete = false;
                }

                point.x = (int) event.getX();
                point.y = (int) event.getY();

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (null != onActionDownUpListener) {
                    onActionDownUpListener.onActionUpDown(true);
                }
                break;

        }

    }

    private void autoScroView() {
        int cux = getScrollX() < 0 ? 0 : getScrollX();
        if (cux > (SCREEN_WIDTH / 2)) {
            showRight();
        } else {
            showLeft();
        }

    }


    public void showLeft() {
        int cux = getScrollX() < 0 ? 0 : getScrollX();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(cux, 0);
        valueAnimator.setDuration(2 * 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                scrollTo(value, 0);
                if (value == 0 && null != onAnimationDownListener) {
                    onAnimationDownListener.onAnimationDown(true);
                }
            }
        });
        valueAnimator.start();


    }

    public void showRight() {
        int cux = getScrollX() < 0 ? 0 : getScrollX();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(cux, SCREEN_WIDTH - timeline_width);
        valueAnimator.setDuration(2 * 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                scrollTo(value, 0);
                if (value == (SCREEN_WIDTH - timeline_width) && null != onAnimationDownListener) {
                    onAnimationDownListener.onAnimationDown(false);
                }
            }
        });
        valueAnimator.start();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(SCREEN_WIDTH, heightMeasureSpec);
        contentView.measure(ROOTVIEW_WIDTH, heightMeasureSpec);
    }

    private void init(Context context) {
        this.context = context;
        this.timeline_width = (int) context.getResources().getDimension(R.dimen.time_line_width);
    }

    public MilestoneView.OnActionDownUpListener getOnActionDownUpListener() {
        return onActionDownUpListener;
    }

    public void setOnActionDownUpListener(MilestoneView.OnActionDownUpListener onActionDownUpListener) {
        this.onActionDownUpListener = onActionDownUpListener;
    }

    public OnAnimationDownListener getOnAnimationDownListener() {
        return onAnimationDownListener;
    }

    public void setOnAnimationDownListener(OnAnimationDownListener onAnimationDownListener) {
        this.onAnimationDownListener = onAnimationDownListener;
    }


    public static interface OnActionDownUpListener {
        public void onActionUpDown(boolean upDown);
    }

    public static interface OnAnimationDownListener {
        public void onAnimationDown(boolean leftRight);
    }

    public MyListView getListViewLeft() {
        return listViewLeft;
    }


    public MyListView getListViewRight() {
        return listViewRight;
    }


    public boolean isListTouchDown() {
        return isListTouchDown;
    }

    public void setIsListTouchDown(boolean isListTouchDown) {
        this.isListTouchDown = isListTouchDown;
    }

    public TimeLineView getViewTimeLine() {
        return viewTimeLine;
    }

}
