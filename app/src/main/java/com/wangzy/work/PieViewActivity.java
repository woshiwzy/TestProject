package com.wangzy.work;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.wangzy.exitappdemo.R;
import com.wangzy.work.view.MyPie;

public class PieViewActivity extends Activity {

	private MyPie myPie;
	private Button buttonPre;
	private Button buttonNxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_work_pie_view);
		myPie = (MyPie) findViewById(R.id.myPie);
		buttonPre = (Button) findViewById(R.id.buttonPre);
		buttonPre.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myPie.pre();
			}
		});
		buttonNxt = (Button) findViewById(R.id.buttonNxt);
		buttonNxt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myPie.next();
			}
		});
	}

}
