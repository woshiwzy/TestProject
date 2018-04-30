package com.wangzy.exitappdemo.page_timeline.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wangzy on 15/9/10.
 */
public class CocahSession {

    private String id;
    private String userId;
    private String title;
    private String startTime;
    private String location;
    private String description;
    private String sample;

    private SimpleDateFormat simpleDateParser;
    private ArrayList<Topic> topics;

    public CocahSession() {
        topics = new ArrayList<Topic>();
        this.simpleDateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public void addTopic(Topic topic) {
        topics.add(topic);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }


    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    long times = 0;

    public long getStartTimeLong() {
        if (0 != times) {
            return times;
        }

        try {
            Date d = simpleDateParser.parse(getStartTime().replace("T", " "));
            times = d.getTime();
        } catch (Exception e) {
        }
        return times;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Topic> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<Topic> topics) {
        this.topics = topics;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public boolean isSample() {
        if ("1".equals(sample)) {
            return true;
        } else {
            return false;
        }

    }
}
