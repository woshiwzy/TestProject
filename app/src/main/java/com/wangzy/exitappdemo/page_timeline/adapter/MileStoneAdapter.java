package com.wangzy.exitappdemo.page_timeline.adapter;

import android.animation.AnimatorInflater;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangzy.exitappdemo.R;
import com.wangzy.exitappdemo.page_timeline.domain.Milestone;
import com.wangzy.exitappdemo.page_timeline.domain.Objective;
import com.wangzy.exitappdemo.util.ListUtiles;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by wangzy on 15/9/10.
 */
public class MileStoneAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Milestone> milestones;
    private int listheight;
    private LayoutInflater layoutInflater;

    private SimpleDateFormat simpleDateParser;
    private SimpleDateFormat simpleDateFormater;
    private static int selectIndex = -1;
    public MileStoneAdapter(Context context, ArrayList<Milestone> milestones, int listheight) {
        this.context = context;
        this.milestones = milestones;
        this.listheight = listheight;
        this.layoutInflater = LayoutInflater.from(context);
        this.simpleDateParser = new SimpleDateFormat("yyyy-MM-dd");
        this.simpleDateFormater = new SimpleDateFormat("dd/MM/yyyy");
    }


    public void selectIndex(int index) {
        this.selectIndex = index;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return milestones.size();
    }

    @Override
    public Object getItem(int position) {
        return milestones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (null == convertView) {
            convertView = layoutInflater.inflate(R.layout.item_miles_stone, null);
            viewHolder = new ViewHolder(convertView);

            android.widget.AbsListView.LayoutParams vlp = new android.widget.AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)(listheight/2));
            convertView.setLayoutParams(vlp);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Milestone milestone = milestones.get(position);

        viewHolder.textViewMilestoneTitle.setText(milestone.getTitle());

        ArrayList<Objective> objects = milestone.getObjectives();

        viewHolder.viewObject1.setVisibility(View.INVISIBLE);
        viewHolder.viewObject2.setVisibility(View.INVISIBLE);
        viewHolder.viewObject3.setVisibility(View.INVISIBLE);

        if (!ListUtiles.isEmpty(objects)) {

            int osize = objects.size();
            viewHolder.textViewObject1.setText(objects.get(0).getTitle());
            viewHolder.viewObject1.setVisibility(View.VISIBLE);

            if (osize >= 2) {
                viewHolder.textViewObject2.setText(objects.get(1).getTitle());
                viewHolder.viewObject2.setVisibility(View.VISIBLE);
            }
            if (osize >= 3) {
                viewHolder.textViewObject3.setText(objects.get(2).getTitle());
                viewHolder.viewObject3.setVisibility(View.VISIBLE);
            }

        }


        int color = ((ColorDrawable) convertView.getBackground()).getColor();

        if (position == selectIndex) {

            if (!Integer.toHexString(color).equals("ffffffff")) {

                ObjectAnimator objectAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.item_color_gray_to_white);

                objectAnimator.setDuration(200);
                objectAnimator.setEvaluator(new ArgbEvaluator());
                objectAnimator.setTarget(convertView);
                objectAnimator.start();
            }

        } else {

            if (!Integer.toHexString(color).equals("ffd7d7db")) {
                ObjectAnimator objectAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.item_color_white_to_gray);

                objectAnimator.setDuration(200);
                objectAnimator.setEvaluator(new ArgbEvaluator());
                objectAnimator.setTarget(convertView);
                objectAnimator.start();
            }

        }

        try {
            Date d = simpleDateParser.parse(milestone.getFinishdate().replace("T", " "));
            viewHolder.textViewTimeStamp.setText(simpleDateFormater.format(d));

            if (d.getTime() < now.getTime()) {
                viewHolder.imageViewDone.setVisibility(View.VISIBLE);
            } else {
                viewHolder.imageViewDone.setVisibility(View.INVISIBLE);
            }

        } catch (Exception e) {
            viewHolder.textViewTimeStamp.setText(milestone.getFinishdate());
        }


        return convertView;
    }


    private Date now = new Date();

    class ViewHolder {

        @InjectView(R.id.textViewMilestoneTitle)
        TextView textViewMilestoneTitle;


        @InjectView(R.id.viewObject1)
        View viewObject1;

        @InjectView(R.id.viewObject2)
        View viewObject2;

        @InjectView(R.id.viewObject3)
        View viewObject3;

        @InjectView(R.id.textViewObject1)
        TextView textViewObject1;

        @InjectView(R.id.textViewObject2)
        TextView textViewObject2;

        @InjectView(R.id.textViewObject3)
        TextView textViewObject3;

        @InjectView(R.id.imageViewDone)
        ImageView imageViewDone;

        @InjectView(R.id.textViewTimeStamp)
        TextView textViewTimeStamp;


        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }


    }

}
