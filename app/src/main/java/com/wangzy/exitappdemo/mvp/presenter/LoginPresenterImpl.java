package com.wangzy.exitappdemo.mvp.presenter;

import com.wangzy.exitappdemo.mvp.model.LoginModel;
import com.wangzy.exitappdemo.mvp.model.LoginModelImpl;
import com.wangzy.exitappdemo.mvp.model.OnLoginFinishedListener;
import com.wangzy.exitappdemo.mvp.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter, OnLoginFinishedListener{


    private LoginView loginView;
    private LoginModel loginModel;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginModel = new LoginModelImpl();
    }


    @Override
    public void validateCredentials(String username, String password) {
        if (loginView != null) {
            loginView.showProgress();
        }

        loginModel.login(username, password, this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onUsernameError() {
        if (loginView != null) {
            loginView.setUsernameError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if (loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if (loginView != null) {
            loginView.navigateToHome();
        }
    }
}