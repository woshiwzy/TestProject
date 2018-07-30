package com.wangzy.flight;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

public class FlightSeatItemLayout extends ConstraintLayout {

    public Row.RowRange rowRange;


    public FlightSeatItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Row.RowRange getRowRange() {
        return rowRange;
    }

    public void setRowRange(Row.RowRange rowRange) {
        this.rowRange = rowRange;
    }

    //
//    public RectLinearLayout(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        int max=Math.max(widthMeasureSpec,heightMeasureSpec);
//        super.onMeasure(max, max);
//
////        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
//
//    }
}
