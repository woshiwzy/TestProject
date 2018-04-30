package com.wangzy.javalib.proxy;

public class ProxyTest {

    public static void main(String[] args) {



        CallBack callBack=(CallBack)DynamicProxyHandler.newProxyInstance(new CallBackImple1());

        callBack.onStart();
        callBack.onEnd();



    }


}
