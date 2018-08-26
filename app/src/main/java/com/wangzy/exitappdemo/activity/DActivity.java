package com.wangzy.exitappdemo.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.App;
import com.wangzy.exitappdemo.R;

import java.io.IOException;

public class DActivity extends AppCompatActivity {

    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d);


        findViewById(R.id.buttonLeak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DActivity.this,LeakActivity.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {


////                Intent intent = new Intent(Intent.ACTION_MAIN);
////                intent.addCategory(Intent.CATEGORY_HOME);
////                startActivity(intent);
////
////                int pid = android.os.Process.myPid();
////                android.os.Process.killProcess(pid);
//
////                finishAffinity();
//
//                moveTaskToBack(true);
//                finish();

//                android.os.Process.killProcess(android.os.Process.myPid());

//                System.exit(0);

//                finishAffinity();
//                killSelf();

//                thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                setTitle("hhhh");
//                            }
//                        });
//                    }
//                });
//                thread.start();
            }
        });

        Button btnexit = findViewById(R.id.buttonExit);
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(DActivity.this, AActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();


                App.Companion.getInstance().exitApp();

            }
        });


    }


    void killSelf(){
        int pid = android.os.Process.myPid();
        String command = "kill -9 "+ pid;

        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
