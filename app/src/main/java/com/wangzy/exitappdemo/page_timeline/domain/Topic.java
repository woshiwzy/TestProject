package com.wangzy.exitappdemo.page_timeline.domain;

/**
 * Created by wangzy on 15/9/10.
 */
public class Topic{

    private String id;
    private String cocahsessionId;
    private String title;
    private String image;
    private String description;


    public Topic(){
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCocahsessionId() {
        return cocahsessionId;
    }

    public void setCocahsessionId(String cocahsessionId) {
        this.cocahsessionId = cocahsessionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
