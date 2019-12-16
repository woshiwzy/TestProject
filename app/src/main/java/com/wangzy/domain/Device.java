package com.wangzy.domain;

/**
 * Created by wangzy on 2019/4/16
 * description:
 */
public class Device {

    public  String deviceWidth="Hello WTF";

    public Device(String label) {
        this.deviceWidth=label;
    }

    public String getDeviceWidth() {
        return deviceWidth;
    }

    public void setDeviceWidth(String deviceWidth) {
        this.deviceWidth = deviceWidth;
    }
}
