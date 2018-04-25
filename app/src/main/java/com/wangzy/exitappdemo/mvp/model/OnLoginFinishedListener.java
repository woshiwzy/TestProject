package com.wangzy.exitappdemo.mvp.model;

public interface OnLoginFinishedListener {

    void onUsernameError();

    void onPasswordError();

    void onSuccess();
}