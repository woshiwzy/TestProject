package com.wangzy.login.contract;

public interface LoginContract {

    interface Model {

    }

    interface View {


        void showProgress();

        void hideProgresss();

        String getName();

        String getPassWord();


    }

    interface Presenter {

        void onSuccess();
        void onFail(String msg);

    }
}
