package com.wangzy.exitappdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.wangzy.exitappdemo.consts.ConstantKt;

public class BActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);


        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(BActivity.this,CActivity.class);

                startActivity(intent);

                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        setTitle("hello");
                    }
                });
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ConstantKt.getTAG(),"BonPause");
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ConstantKt.getTAG(),"BonStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ConstantKt.getTAG(),"BonStop");
    }
}
