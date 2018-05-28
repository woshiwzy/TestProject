package com.wangzy.login.presenter;

import com.wangzy.login.contract.LoginContract;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View loginView;


    public LoginPresenter(){

    }


    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail(String msg) {

    }
}
