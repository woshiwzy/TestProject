package com.wangzy.javalib.proxy;

public class CallBackImple1 implements CallBack {


    @Override
    public void onStart() {
        System.out.println("onStart");
    }

    @Override
    public void onEnd() {
        System.out.println("onEnd");
    }
}
