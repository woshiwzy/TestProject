package com.wangzy.work.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;

import com.wangzy.exitappdemo.R;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;

public class WorkMainActivity extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_work_main);


        ArrayMap<String, String> arrayMap = new ArrayMap<>();

        SparseArray sparseArray = new SparseArray();

        HashMap map = new HashMap(40);


        findViewById(R.id.buttonSimpleGraphics).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WorkMainActivity.this, SimpleActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.buttonTrendId).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WorkMainActivity.this, TrendViewActivity.class);

                startActivity(i);
            }
        });
        findViewById(R.id.buttonRangeGraphics).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WorkMainActivity.this, TrendViewMarkActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.buttonPie).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(WorkMainActivity.this, PieViewActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.buttonMap).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//				Intent i = new Intent(WorkMainActivity.this, MapActivity.class);
//				startActivity(i);
            }
        });


        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };


        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };


        Observable observable = Observable.create(new Observable.OnSubscribe() {

            @Override
            public void call(Object o) {


            }
        });


        Observable.just(1).lift(new Observable.Operator<String, Integer>() {


            @Override
            public Subscriber<? super Integer> call(Subscriber<? super String> subscriber) {


                return new Subscriber<Integer>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }
                };
            }
        }).compose(new Observable.Transformer<String, Integer>() {

            @Override
            public Observable<Integer> call(Observable<String> stringObservable) {


                return null;
            }
        }).lift(new Observable.Operator<Integer, Integer>() {

            @Override
            public Subscriber<? super Integer> call(Subscriber<? super Integer> subscriber) {

                return null;
            }
        }).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {


                return null;
            }
        }).subscribe(new Observer<Integer>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {

            }


        });


    }
}
