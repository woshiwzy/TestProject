package com.wangzy.exitappdemo.page_timeline;

import android.content.Context;
import android.graphics.Rect;

import com.wangzy.exitappdemo.page_timeline.domain.Timeline;

/**
 * Created by wangzy on 15/9/15.
 */
public class TimeLineSprite {

    private Timeline timeline;

    private Rect rectStart;
    private Rect currentRect;
    private Rect targetLocationRect;

    private Context context;
    private int cellHeight;
    private int w;
    private boolean isMid;

    private TimeLineSprite headerSprite;
    private TimeLineSprite footerSprite;

    public TimeLineSprite(Context context, Timeline timeline) {
        this.context = context;
        this.timeline = timeline;

    }

    public void movetoRect(Rect rect) {
        this.currentRect = rect;
    }
    //=====================

    public void reset2TargetRect() {

        setRectStart(targetLocationRect);
    }

    public Rect getRectStart() {
        return rectStart;
    }

    public void setRectStart(Rect rectStart) {
        this.rectStart = rectStart;
        this.currentRect = rectStart;
    }

    public Rect getTargetLocationRect() {

        return targetLocationRect;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public void setTargetLocationRect(Rect targetLocationRect) {
        this.targetLocationRect = targetLocationRect;
    }

    public boolean isMid() {
        return isMid;
    }

    public void setIsMid(boolean isMid) {
        this.isMid = isMid;
    }

    public TimeLineSprite getHeaderSprite() {
        return headerSprite;
    }

    public void setHeaderSprite(TimeLineSprite headerSprite) {
        this.headerSprite = headerSprite;
    }

    public TimeLineSprite getFooterSprite() {
        return footerSprite;
    }

    public void setFooterSprite(TimeLineSprite footerSprite) {
        this.footerSprite = footerSprite;
    }
}
