package com.wangzy.exitappdemo.page_timeline.domain;

/**
 * Created by wangzy on 15/9/14.
 */
public class Timeline {


    private int type;
    private int posInList;

    private String id;
    private long time;
    private String title;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getPosInList() {
        return posInList;
    }

    public void setPosInList(int posInList) {
        this.posInList = posInList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
