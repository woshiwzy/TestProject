package com.wangzy.exitappdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wangzy.exitappdemo.R;

public class PullRefreshHeaderView extends RelativeLayout implements PullRefreshProgressChanged {

    public PullRefreshHeaderView(Context context) {
        super(context);
        init(context);
    }

    public PullRefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PullRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

    }

    @Override
    public void onProgress(final float progress) {

        final TextView textView = findViewById(R.id.textView);

        if(progress==1){
            textView.setText("松手刷新");
        }else {
            textView.setText(String.valueOf(progress));
        }


        CircleView circleView=findViewById(R.id.circleView);
        circleView.setProcess(progress);


        CircleView circleVieww=findViewById(R.id.circleView2);
        circleVieww.setProcess(-(1-progress));
    }

    @Override
    public void onRefrehing() {

    }

}
