package com.wangzy.myactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wangzy.exitappdemo.R;
import com.wangzy.exitappdemo.consts.ConstantKt;
import com.wangzy.exitappdemo.util.LogUtil;
import com.wangzy.work.view.MoveTextView;

public class DragTextActivity extends AppCompatActivity {


    MoveTextView moveTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_text);

        moveTextView = findViewById(R.id.MoveTextView);

        moveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e(ConstantKt.getTAG(), "DragTextActivityDragTextActivityDragTextActivity");
            }
        });
//
//        moveTextView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        System.out.println("action_down");
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        System.out.println("action_move");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        System.out.println("action_up");
//                        break;
//                }
//                return true;
//            }
//        });

    }
}
