package com.wangzy.flight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.wangzy.exitappdemo.R;
import com.wangzy.exitappdemo.consts.ConstantKt;
import com.wangzy.exitappdemo.util.LogUtil;
import com.wangzy.exitappdemo.util.Tool;

import java.util.ArrayList;

public class AutoScaleTextView extends android.support.v7.widget.AppCompatTextView {
    private static float DEFAULT_MIN_TEXT_SIZE = 0;
    private static float DEFAULT_MAX_TEXT_SIZE = 48;
    private Paint testPaint;
    private float minTextSize;
    private float maxTextSize;

    public AutoScaleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialise();
    }

    private void initialise() {
        testPaint = new Paint();
        testPaint.set(this.getPaint());
        maxTextSize = this.getTextSize();
        if (maxTextSize <= DEFAULT_MIN_TEXT_SIZE) {
            maxTextSize = DEFAULT_MAX_TEXT_SIZE;
        }
        minTextSize = DEFAULT_MIN_TEXT_SIZE;
    }
    
    private void refitText(String text, int textWidth) {
        if (textWidth > 0) {
            int availableWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();
            float trySize = maxTextSize;
            testPaint.setTextSize(trySize);
            while ((trySize > minTextSize) && (testPaint.measureText(text) > availableWidth)) {
                trySize -= 1;
                if (trySize <= minTextSize) {
                    trySize = minTextSize;
                    break;
                }
                testPaint.setTextSize(trySize);
            }
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);//TypedValue.COMPLEX_UNIT_PX不可少，将单位设置为像素
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        super.onTextChanged(text, start, before, after);
        refitText(text.toString(), this.getWidth());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw) {
            refitText(this.getText().toString(), w);
        }
    }

    public static class FlightSeatActivity extends Activity {


        private RecyclerView recyclerView;
        public static FlightTemplate flightTemplate;
        private LinearLayout lineraLayoutPosition;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_flight_seat);
            recyclerView = findViewById(R.id.recyclerView);
            lineraLayoutPosition = findViewById(R.id.lineraLayoutPosition);

            buildAdapter();

        }

        private void buildAdapter() {

            //加载座位图文件
            FlightSeat flightSeat = FlightSeat.loadFlightSeat(this, "data/" + flightTemplate.getConfigFile() + ".json");

            setTitle(flightTemplate.getConfigFile());

            String validate = flightSeat.selfValidate();
            if (null != validate) {
                Tool.ToastShow(this, flightTemplate.getConfigFile() + ":" + validate);
                return;
            }


            //添加座位图顶部Title
            char[] titles = flightSeat.getListHeaderTitle().toCharArray();
            LinearLayout.LayoutParams lpp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            lpp.weight = 1;
            for (char title : titles) {
                TextView text = new TextView(this);
                text.setText(String.valueOf(title).replace("_", " "));
                text.setGravity(Gravity.CENTER);
                lineraLayoutPosition.addView(text, lpp);
            }

            //设置adapter
            Display display = getWindowManager().getDefaultDisplay();

            Point p = new Point();
            display.getSize(p);
            int width = p.x;

            FlightSeatAdapter flightSeatAdapter = new FlightSeatAdapter(this, flightSeat, width);

            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(flightSeatAdapter);
            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(1, 3, 1, 3);//设置itemView中内容相对边框左，上，右，下距离
                }
            });

            LogUtil.i(ConstantKt.getTAG(), "FlightSeatActivity.onCreate");
        }

        class FlightSeatAdapter extends RecyclerView.Adapter<FlightHolder> {

            private Context context;

            private FlightSeat flightSeat;

            private ArrayList<Row.RowRange> allrows;

            private int recyclerViewWidth;
            private LayoutInflater layoutInflater;

            public FlightSeatAdapter(Context context, FlightSeat flightSeat, int recyclerViewWidth) {

                this.context = context;
                this.flightSeat = flightSeat;
                this.allrows = flightSeat.generateRealRow();
                this.recyclerViewWidth = recyclerViewWidth;
                this.layoutInflater = LayoutInflater.from(context);
            }

            @NonNull
            @Override
            public FlightHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType_fromRowId) {

                LinearLayout rootItem = (LinearLayout) layoutInflater.inflate(R.layout.item_row, parent, false);

                Row row = flightSeat.getRowById(viewType_fromRowId);

                ViewGroup.LayoutParams lpp = rootItem.getLayoutParams();

                int cw = recyclerViewWidth / row.getRangeTitle().length();

                rootItem.setLayoutParams(lpp);

                FlightHolder flightHolder = new FlightHolder(rootItem, layoutInflater, cw);

                flightHolder.buildRowView(row);

                return flightHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull FlightHolder holder, int position) {

                Row.RowRange rowrange = allrows.get(position);
                holder.range = rowrange;
                holder.textViewRowNumber.setText(String.valueOf(rowrange.getRowNumber()));
            }

            @Override
            public int getItemViewType(int position) {
                Row row = allrows.get(position).getRow();
                return row.getRowId();
            }


            @Override
            public int getItemCount() {
                return this.allrows.size();
            }
        }

        class FlightHolder extends RecyclerView.ViewHolder {


            TextView textViewRowNumber;
            LinearLayout lineraLayoutPosition;
            LayoutInflater layoutInflater;
            int cellWidth = 0;
            Row.RowRange range;
            String[] titles;


            public FlightHolder(View itemView, LayoutInflater layoutInflater, int cellWidth) {
                super(itemView);
                this.layoutInflater = layoutInflater;
                this.textViewRowNumber = itemView.findViewById(R.id.textViewRowNumber);
                this.lineraLayoutPosition = itemView.findViewById(R.id.lineraLayoutPosition);
                this.cellWidth = cellWidth;
            }

            public void buildRowView(final Row row) {
    //            "seatArrange": "1,1,1,A,A,1,1,1",//1代表一个位置，A代表过道,0代表没有安装座椅
                final String[] poses = row.getSeatArrange().split(",");
                LinearLayout.LayoutParams lpp = new LinearLayout.LayoutParams(cellWidth, ViewGroup.LayoutParams.MATCH_PARENT);
                lpp.width = 0;
                lpp.weight = 1;

                titles = row.getRangeTitle().split(",");

                for (int i = 0, isize = poses.length; i < isize; i++) {

                    String pos = poses[i];

                    switch (pos) {
                        case "1": {// "seatArrange": "1,1,1,A,A,1,1,1",//1代表一个位置，A代表过道,0代表没有安装座椅

                            ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.item_seat_1, null);
                            ((TextView) viewGroup.findViewById(R.id.textViewSeatFamilyName)).setText(titles[i].replace("_", ""));

                            this.lineraLayoutPosition.addView(viewGroup, lpp);
                            final int finalI = i;
                            viewGroup.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (null != range && null != titles) {
                                        Tool.ToastShow(FlightSeatActivity.this, range.getRowNumber() + " " + titles[finalI]);
                                    }
                                }
                            });
                        }
                        break;
                        case "0": {
                            Space space = (Space) layoutInflater.inflate(R.layout.item_seat_0, null);
                            this.lineraLayoutPosition.addView(space, lpp);
                        }

                        break;
                        case "A": {
                            Space space = (Space) layoutInflater.inflate(R.layout.item_seat_a, null);
                            this.lineraLayoutPosition.addView(space, lpp);
                        }
                        break;

                    }
                }

                this.lineraLayoutPosition.post(new Runnable() {
                    @Override
                    public void run() {
                        ViewGroup.LayoutParams lpp2 = lineraLayoutPosition.getLayoutParams();
                        lpp2.height = (int) (Math.max(lineraLayoutPosition.getChildAt(0).getWidth(), lineraLayoutPosition.getChildAt(0).getHeight()) * 1.2);
                        lineraLayoutPosition.setLayoutParams(lpp2);
                    }
                });


            }

        }
    }
}