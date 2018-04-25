package com.wangzy.exitappdemo.mvp.model;

public interface LoginModel {
    public void login(final String userName, final String password, final OnLoginFinishedListener listener) ;
}
