package com.wangzy.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.wangzy.domain.Device;
import com.wangzy.exitappdemo.R;
import com.wangzy.exitappdemo.databinding.ActivityBindDemoBinding;

public class BindDemoActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bind_demo);

        ActivityBindDemoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_bind_demo);

        binding.setDevice(new Device("绑定的输出对象"));
    }
}
