package com.wangzy.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wangzy.exitappdemo.R;
import com.wangzy.exitappdemo.consts.ConstantKt;
import com.wangzy.exitappdemo.util.LogUtil;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;


public class RxActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);

        findViewById(R.id.buttonGo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] contents = {"1x", "2y", "3m", "4n"};


                Observable.from(contents).filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {

//                        return !s.equals("");
                        return s.startsWith("3");

                    }
                }).map(new Func1<String, String>() {

                    @Override
                    public String call(String s) {

                        return s+"  121";
                    }
                }).doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        LogUtil.i(ConstantKt.getTAG(), "doOnext:" + s);

                    }
                }).subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {


                        LogUtil.i(ConstantKt.getTAG(), "result:" + s);
                    }
                });


                //=============================================================
                Observable.create(new Observable.OnSubscribe<Bitmap>() {

                    @Override
                    public void call(Subscriber<? super Bitmap> subscriber) {

                    }
                }).subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {

                    }
                });

//                //============================================================





//                Observable.just("Hello")
//                        .filter(new Func1<String, Boolean>() {
//                            @Override
//                            public Boolean call(String s) {
//                                return null;
//                            }
//                        }).map()

            }
        });
    }


}
