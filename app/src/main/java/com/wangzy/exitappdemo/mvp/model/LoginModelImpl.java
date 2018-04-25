package com.wangzy.exitappdemo.mvp.model;

import android.os.Handler;
import android.text.TextUtils;

public class LoginModelImpl implements LoginModel {

    @Override
    public void login(final String userName, final String password, final OnLoginFinishedListener listener) {


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                boolean error = false;

                if (TextUtils.isEmpty(userName)){
                    listener.onUsernameError();//model层里面回调listener
                    error = true;
                }

                if (TextUtils.isEmpty(password)){
                    listener.onPasswordError();
                    error = true;
                }
                if (!error){
                    listener.onSuccess();
                }


            }

        },2000);



    }
}
