package com.wangzy.work;

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

public class WorkMainActivity extends Activity {

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);



		setContentView(R.layout.activity_work_main);


		ArrayMap<String,String> arrayMap=new ArrayMap<>();

		SparseArray sparseArray=new SparseArray();

		HashMap map=new HashMap(40);


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
	}
}
