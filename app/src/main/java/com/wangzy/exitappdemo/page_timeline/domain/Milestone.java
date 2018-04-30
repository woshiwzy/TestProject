package com.wangzy.exitappdemo.page_timeline.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wangzy on 15/9/10.
 */
public class Milestone {

    private String id;
    private String userId;
    private String finishdate;
    private String title;
    private String description;

    private ArrayList<Objective> objectives;
    private SimpleDateFormat simpleDateParser;


    public Milestone() {
        objectives = new ArrayList<Objective>();
        this.simpleDateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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

    public String getFinishdate() {

        return finishdate;
    }


     long times = 0;

    public long getFinishTimeMils() {
        if (0 != times) {
            return times;
        }
        try {
            String timeText = getFinishdate().substring(0, 10);
            Date d = simpleDateParser.parse(timeText + " 00:00:00");
            times = d.getTime();
        } catch (Exception e) {
//            LogUtil.i(App.tag, "format:error:" + getFinishdate());
        }

        return times;
    }

    public String getFinishDay() {
        String timeText = getFinishdate().substring(0, 10);
        return timeText;

    }

    public void setFinishdate(String finishdate) {
        this.finishdate = finishdate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Objective> getObjectives() {
        return objectives;
    }

    public void setObjectives(ArrayList<Objective> objectives) {
        this.objectives = objectives;
    }


    public void addObjective(Objective objective) {
        this.objectives.add(objective);
    }


    public void copyfromEditStone(Milestone editStone) {
        if (null == editStone) {
            return;
        }

        this.setTitle(editStone.getTitle());
        this.setDescription(editStone.getDescription());
        this.setFinishdate(editStone.getFinishdate());

        this.getObjectives().clear();
        this.getObjectives().addAll(editStone.getObjectives());

    }

}
