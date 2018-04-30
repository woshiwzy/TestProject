package com.wangzy.exitappdemo.page_timeline;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.wangzy.exitappdemo.R;
import com.wangzy.exitappdemo.page_timeline.domain.Timeline;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by wangzy on 15/9/8.
 */
public class TimeLineView extends View {


    public static int INT_DRAWABLE_TYPE_STONE = 0;
    public static int INT_DRAWABLE_TYPE_COCARH = 1;
    public static int INT_DRAWABLE_TYPE_NONE = -1;

    private Paint paint;

    private int strokeWidth = 6;
    private float percent;
    private int lastPostion = -1;

    private int type;
    private int cellWidth;
    private int cellHeight;

    private String yellowLineColor = "#EACD89";

    private Bitmap bitmapStone;
    private Bitmap bitmapCocoah;

    private Context context;

    private Rect rectCenter;

    private ArrayList<Rect> topRects;
    private ArrayList<Rect> bottomRects;
    private ArrayList<Rect> rects;

    private ArrayList<TimeLineSprite> arrayListAllTimeSprite;


    private Rect rectTop1, rectTop2, rectTop3, rectBottom1, rectBottom2, rectBottom3;
    private Canvas canvas;

    private volatile boolean isAnimate;

    private int h;
    private int w;

    private boolean isTest = false;//help location rect
    private ArrayList<Timeline> timelines;
    private int leftOrightSpace = 5;
    private boolean isDrawContant = true;

    private Vector<MoveEvent> vectorEvents;


    public TimeLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);//harware support hardware acceleration
        arrayListAllTimeSprite = new ArrayList<TimeLineSprite>();
        rects = new ArrayList<Rect>();
        vectorEvents = new Vector<MoveEvent>();

        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        w = getWidth();
        h = getHeight();

        int rw = w / 2;

        int itemHeight3 = h / 6;
        cellHeight = itemHeight3 / 3;
        cellWidth = w;

        paint.setStyle(Paint.Style.FILL);
        canvas.drawColor(Color.TRANSPARENT);

        Rect r = new Rect(w / 3 - strokeWidth / 2, 0, w * 2 / 3 - strokeWidth / 2, h);
        paint.setColor(Color.parseColor("#15000000"));
        canvas.drawRect(r, paint);

        //=================draw dash line
        paint.setPathEffect(new DashPathEffect(new float[]{20, 10}, 0));
        paint.setColor(Color.parseColor(yellowLineColor));

        int lineStartX = w / 2;//-strokeWidth/2;
        canvas.drawLine(lineStartX - strokeWidth / 2, 0, lineStartX - strokeWidth / 2, h, paint);
        //================
        //===compute segment=======

        paint.setPathEffect(null);
        paint.setStyle(Paint.Style.STROKE);

        //=============

        rectCenter = new Rect(0, h / 2 - cellHeight / 2, w, h / 2 - cellHeight / 2 + cellHeight);

        //==============================


        rectTop1 = new Rect(0, 0, w, cellHeight);
        rectTop2 = new Rect(0, cellHeight, w, cellHeight * 2);
        rectTop3 = new Rect(0, cellHeight * 2, w, cellHeight * 3);


        topRects.clear();
        topRects.add(rectTop1);
        topRects.add(rectTop2);
        topRects.add(rectTop3);


        //==============================

        rectBottom1 = new Rect(0, h - cellHeight * 3, w, h - cellHeight * 2);
        rectBottom2 = new Rect(0, h - cellHeight * 2, w, h - cellHeight);
        rectBottom3 = new Rect(0, h - cellHeight * 1, w, h);


        bottomRects.clear();


        bottomRects.add(rectBottom1);
        bottomRects.add(rectBottom2);
        bottomRects.add(rectBottom3);

        rects.clear();
        rects.add(rectTop1);
        rects.add(rectTop2);
        rects.add(rectTop3);

        rects.add(rectCenter);

        rects.add(rectBottom1);
        rects.add(rectBottom2);
        rects.add(rectBottom3);

        if (isTest) {

            canvas.drawRect(rectTop1, paint);
            canvas.drawRect(rectTop2, paint);
            canvas.drawRect(rectTop3, paint);

            canvas.drawRect(rectCenter, paint);

            canvas.drawRect(rectBottom1, paint);
            canvas.drawRect(rectBottom2, paint);
            canvas.drawRect(rectBottom3, paint);
        }


        //==================================================================


        if (isDrawContant == false) {
            return;

        }


        for (int i = 0, isize = arrayListAllTimeSprite.size(); i < isize; i++) {

            if (i == 0 || i == (arrayListAllTimeSprite.size() - 1)) {
                continue;
            }

            TimeLineSprite ts = arrayListAllTimeSprite.get(i);

            Bitmap bitmap = ts.getTimeline().getType() == INT_DRAWABLE_TYPE_STONE ? bitmapStone : bitmapCocoah;

            if (null != ts.getTargetLocationRect()) {

                Rect rectStart = ts.getRectStart();
                Rect rectTarget = ts.getTargetLocationRect();

                Rect nRect = copyRect(rectStart);
                float topStart = (rectTarget.top - rectStart.top) * percent;

                nRect.top = nRect.top + (int) topStart;
                nRect.bottom = nRect.top + cellHeight;

                drawBitmap(bitmap, nRect, paint, i);
            } else {
                drawBitmap(bitmap, ts.getRectStart(), paint, i);
            }
        }


    }


    private void drawOutLine() {


    }


    private void drawBitmap(Bitmap bitmap, Rect rect, Paint paint, int index) {

        Rect okRect = copyRect(rect);

        int oldHeight = rect.bottom - rect.top;

        int bw = bitmapCocoah.getWidth();
        int bh = bitmapCocoah.getHeight();

        float maxWidth = w * 2 / 3;
        float maxHeight = bh * (maxWidth / (float) bw);

        float okLeft = w / 2 - maxWidth / 2;//-strokeWidth/5.0f;
        float okTop = rect.top + (oldHeight / 2 - maxHeight / 2);

        float okRight = okLeft + maxWidth;
        float okBottom = okTop + maxHeight;

        okRect.left = (int) okLeft;
        okRect.top = (int) okTop;

        okRect.right = (int) okRight;
        okRect.bottom = (int) okBottom;


        if (index == lastPostion) {
            paint.setAlpha(255);
        } else {
            paint.setAlpha(130);
        }

        if (okRect.bottom < 0 || okRect.top > h) {
            //beyond of visible
        } else {
            canvas.drawBitmap(bitmap, null, okRect, paint);
        }
        paint.setAlpha(255);

    }


    private void init() {
        paint = new Paint();
        this.strokeWidth = context.getResources().getInteger(R.integer.line_stroke);
        paint.setStrokeWidth(strokeWidth);
        paint.setAntiAlias(true);

        topRects = new ArrayList<Rect>();
        bottomRects = new ArrayList<Rect>();

        bitmapStone = BitmapFactory.decodeResource(getResources(), R.drawable.icon_time_line_flag);
        bitmapCocoah = BitmapFactory.decodeResource(getResources(), R.drawable.icon_time_line_book);
    }

    public void initData(ArrayList<Timeline> timeLines) {
        this.type = INT_DRAWABLE_TYPE_STONE;
        this.timelines = timeLines;

        this.arrayListAllTimeSprite = covertTimeLine2Sprite(timeLines);

        TimeLineSprite timeLineSpriteHeader = new TimeLineSprite(context, null);
        TimeLineSprite timeLineSpriteTail = new TimeLineSprite(context, null);

        this.arrayListAllTimeSprite.add(0, timeLineSpriteHeader);//add snake header

        this.arrayListAllTimeSprite.add(timeLineSpriteTail);//add snake tail


        int rpos = getRealPosByType(0, INT_DRAWABLE_TYPE_STONE);
        if (-1 != rpos) {
            isDrawContant = true;
            this.lastPostion = rpos;
            initWithPostion(this.lastPostion);
        } else {
            isDrawContant = false;
            int rposCoachsesion = getRealPosByType(0, INT_DRAWABLE_TYPE_COCARH);
            if (rposCoachsesion != -1) {
                this.lastPostion = rposCoachsesion;
//                this.type = INT_DRAWABLE_TYPE_COCARH;
                initWithPostion(rposCoachsesion);
            }
        }

//        int rposCoachsesion = getRealPosByType(0, INT_DRAWABLE_TYPE_COCARH);
//        if(-1==rposCoachsesion){
//            return;
//        }else{
//            this.lastPostion = rpos;
//            initWithPostion(this.lastPostion);
//        }

        invalidate();

    }

    private void initWithPostion(int postion) {

        //=======================
        int currentRealPotion = postion;

        TimeLineSprite currentPotionSprite = this.arrayListAllTimeSprite.get(currentRealPotion);
        currentPotionSprite.setRectStart(rectCenter);


        for (int i = currentRealPotion - 1; i >= 0; i--) {

            if (i == (currentRealPotion - 1)) {
                this.arrayListAllTimeSprite.get(i).setRectStart(rectTop3);
            } else {

                Rect rectFront = this.arrayListAllTimeSprite.get(i + 1).getRectStart();

                Rect rectStart = copyRect(rectFront);

                rectStart.top = rectFront.top - cellHeight;
                rectStart.bottom = rectStart.top + cellHeight;

                this.arrayListAllTimeSprite.get(i).setRectStart(rectStart);
            }
        }
        //=============

        for (int i = currentRealPotion + 1; i < this.arrayListAllTimeSprite.size(); i++) {

            if (i == (currentRealPotion + 1)) {
                this.arrayListAllTimeSprite.get(i).setRectStart(rectBottom1);
            } else {

                Rect rectFront = this.arrayListAllTimeSprite.get(i - 1).getRectStart();
                Rect rectStart = copyRect(rectFront);
                rectStart.top = rectFront.top + cellHeight;
                rectStart.bottom = rectStart.top + cellHeight;

                this.arrayListAllTimeSprite.get(i).setRectStart(rectStart);

            }

        }

    }


    private int getRealPosByType(int rawPos, int type) {
        if (arrayListAllTimeSprite.size() == 2) {
            return -1;
        }

        int tcount = 0;

        for (int i = 1, isize = arrayListAllTimeSprite.size(); i < isize; i++) {

            Timeline tl = arrayListAllTimeSprite.get(i).getTimeline();
            if (null == tl) {
                tcount++;
            } else if (tl.getType() == type && tcount == rawPos) {
                return (i);
            } else if (tl.getType() == type) {
                tcount++;
            }
        }

        return -1;
    }


    public void startStepAll() {

        ValueAnimator valuanimator = ValueAnimator.ofInt(0, 100);
        valuanimator.setDuration(300);
        valuanimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                percent = 0.f;
                isAnimate = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                for (TimeLineSprite timeLineSprite : arrayListAllTimeSprite) {
                    if(null!=timeLineSprite.getTargetLocationRect()){
                        timeLineSprite.setRectStart(copyRect(timeLineSprite.getTargetLocationRect()));
                    }

                }
                isAnimate = false;
                if (!vectorEvents.isEmpty()) {

                    MoveEvent lastEvent = vectorEvents.get(vectorEvents.size() - 1);
                    vectorEvents.clear();
                    execMoveEvent(lastEvent);

                }
            }
        });
        valuanimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int per = (int) animation.getAnimatedValue();
                percent = (float) per / 100;
                invalidate();
            }
        });

        valuanimator.start();
    }


    public void moveToLocation(int pos, int type) {

        if (isAnimate) {
            vectorEvents.add(new MoveEvent(pos, type));
        } else {
            execMoveEvent(new MoveEvent(pos, type));
        }
    }


    private void execMoveEvent(MoveEvent moveEvent) {

        int pos = moveEvent.getLocation();
        int type = moveEvent.getType();
        int moveTo = getRealPosByType(pos, type);

        if (-1 == moveTo) {
            isDrawContant = false;
            invalidate();
            return;
        }
        this.isDrawContant = true;
        this.type = type;

        if (moveTo == lastPostion) {
            invalidate();
            return;
        }

        //===================================================================================================
        int offset = moveTo - lastPostion;

        if (offset > 0) {//up

            for (int i = 0, isize = arrayListAllTimeSprite.size(); i < isize; i++) {
                TimeLineSprite ts = arrayListAllTimeSprite.get(i);
                if (i == 0) {
                    Rect rstart = ts.getRectStart();
                    Rect target = copyRect(rstart);
                    target.top = rstart.top - cellHeight * offset;
                    target.bottom = rstart.top + cellHeight;
                    ts.setTargetLocationRect(target);

                } else {
                    int lastIndex = i - 1;

                    Rect rectFront = arrayListAllTimeSprite.get(lastIndex).getTargetLocationRect();

                    if (lastIndex == (moveTo - 1) || lastIndex == moveTo) {

                        Rect rectN = copyRect(rectFront);
                        rectN.top = rectFront.top + (rectCenter.top - rectTop3.top);
                        rectN.bottom = rectN.top + cellHeight;
                        ts.setTargetLocationRect(rectN);
                    } else {

                        Rect rectN = copyRect(rectFront);
                        rectN.top = rectFront.top + cellHeight;
                        rectN.bottom = rectN.top + cellHeight;
                        ts.setTargetLocationRect(rectN);
                    }
                }
            }
        } else {//down==========================

            for (int isize = arrayListAllTimeSprite.size(), i = isize - 1; i >= 0; i--) {

                TimeLineSprite ts = arrayListAllTimeSprite.get(i);

                if (i == (isize - 1)) {

                    Rect rstart = ts.getRectStart();

                    Rect target = copyRect(rstart);

                    target.top = rstart.top + cellHeight * Math.abs(offset);
                    target.bottom = target.top + cellHeight;

                    ts.setTargetLocationRect(target);
                } else {
//                    ts.setTargetLocationRect(copyRect(arrayListAllTimeSprite.get(i + 1).getRectStart()));

                    int lastIndex = i + 1;
                    Rect rectFront = arrayListAllTimeSprite.get(lastIndex).getTargetLocationRect();

                    if (lastIndex == (moveTo + 1) || lastIndex == moveTo) {

                        Rect rectN = copyRect(rectFront);
                        rectN.top = rectFront.top - (rectCenter.top - rectTop3.top);
                        rectN.bottom = rectN.top + cellHeight;
                        ts.setTargetLocationRect(rectN);

                    } else {

                        Rect rectN = copyRect(rectFront);

                        rectN.top = rectFront.top - cellHeight;
                        rectN.bottom = rectN.top + cellHeight;
                        ts.setTargetLocationRect(rectN);

                    }

                }
            }

        }

        lastPostion = moveTo;
        startStepAll();
    }


    private Rect copyRect(Rect rect) {
        Rect newRect = new Rect(rect.left, rect.top, rect.right, rect.bottom);
        return newRect;
    }


    private ArrayList<TimeLineSprite> covertTimeLine2Sprite(ArrayList<Timeline> timeLines) {

        ArrayList<TimeLineSprite> timeLineSprites = new ArrayList<TimeLineSprite>();
        for (int i = 0, isize = timeLines.size(); i < isize; i++) {
            Timeline timeline = timeLines.get(i);
            TimeLineSprite ts = new TimeLineSprite(context, timeline);

            if (i < rects.size()) {
                ts.setRectStart(rects.get(i));
            } else {
                int topStart = timeLineSprites.get(rects.size() - 1).getRectStart().top;
                int top = (i + 1 - rects.size()) * cellHeight + topStart;
                Rect rect = new Rect(0, top, w, top + cellHeight);
                ts.setRectStart(rect);
            }

            timeLineSprites.add(ts);
        }

        return timeLineSprites;
    }

    private TimeLineSprite covertTimeLine2Sprite(Timeline timeline) {
        Bitmap bitmap = (timeline.getType() == INT_DRAWABLE_TYPE_STONE ? bitmapStone : bitmapCocoah);
        return new TimeLineSprite(context, timeline);
    }


    private ArrayList<TimeLineSprite> getTimeLineSpritByMid(int mid, int type) {


        ArrayList<TimeLineSprite> timelines = new ArrayList<TimeLineSprite>();

        int realPos = -1;
        int count = 0;

        for (int i = 0, isize = arrayListAllTimeSprite.size(); i < isize; i++) {
            Timeline timeLine = arrayListAllTimeSprite.get(i).getTimeline();
            if (timeLine.getType() == type) {
                if (count == mid) {
                    realPos = i;
                    break;
                }
                count++;
            }
        }


        int start = realPos - 3;
        start = start < 0 ? 0 : start;

        int end = realPos + 3;
        end = (end > arrayListAllTimeSprite.size() - 1) ? arrayListAllTimeSprite.size() - 1 : end;
        for (int i = start; i <= end; i++) {
            timelines.add(arrayListAllTimeSprite.get(i));
        }


        return timelines;
    }


    private class MoveEvent {

        private int location;
        private int type;

        public MoveEvent(int location, int type) {
            this.location = location;
            this.type = type;
        }

        public int getLocation() {
            return location;
        }

        public void setLocation(int location) {
            this.location = location;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
