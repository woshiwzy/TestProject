package com.wangzy.javalib.proxy;

import java.util.HashMap;

public class ProxyTest {

    public static void main(String[] args) {



        CallBack callBack=(CallBack)DynamicProxyHandler.newProxyInstance(new CallBackImple1());

        callBack.onStart();
        callBack.onEnd();


        HashMap hmap=new HashMap(100);


    }


}
