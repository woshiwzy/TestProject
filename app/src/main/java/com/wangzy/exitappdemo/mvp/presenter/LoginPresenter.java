package com.wangzy.exitappdemo.mvp.presenter;

public interface LoginPresenter {



    public void validateCredentials(String username, String password);
    public void onDestroy();
}
