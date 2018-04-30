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
import com.wangzy.exitappdemo.page_timeline.domain.CocahSession;
import com.wangzy.exitappdemo.page_timeline.domain.Topic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by wangzy on 15/9/10.
 */
public class CocahAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CocahSession> milestones;
    private int listheight;
    private LayoutInflater layoutInflater;

    private SimpleDateFormat simpleDateParser;
    private SimpleDateFormat simpleDateFormater;

    private Date now = new Date();
    private int selectIndex = -1;
    private OnInnerItemClickListener onInnerItemClickListener;


    public CocahAdapter(Context context, ArrayList<CocahSession> milestones, int listheight) {
        this.context = context;
        this.milestones = milestones;
        this.listheight = listheight;
        this.layoutInflater = LayoutInflater.from(context);

        this.simpleDateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.simpleDateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (null == convertView) {
            convertView = layoutInflater.inflate(R.layout.item_session_cocah, null);
            viewHolder = new ViewHolder(convertView);

            android.widget.AbsListView.LayoutParams vlp = new android.widget.AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)(listheight /2));
            convertView.setLayoutParams(vlp);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final CocahSession cocahSession = milestones.get(position);

        viewHolder.textViewMilestoneTitle.setText(cocahSession.getTitle());


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

            Date d = simpleDateParser.parse(cocahSession.getStartTime().replace("T", " "));
            viewHolder.textViewTimeStamp.setText(simpleDateFormater.format(d));
            if (d.getTime() < now.getTime()) {
                viewHolder.imageViewDone.setVisibility(View.VISIBLE);
            } else {
                viewHolder.imageViewDone.setVisibility(View.INVISIBLE);
            }

        } catch (Exception e) {
            viewHolder.textViewTimeStamp.setText(cocahSession.getStartTime());
        }

        String target = "";
        int qcount = 0;

        try {
            Topic topic = cocahSession.getTopics().get(0);
            target = topic.getTitle();
        } catch (Exception e) {
        }

//        viewHolder.textViewTrust.setText((StringUtils.isEmpty(target) ? context.getResources().getString(R.string.mile_stone_coaching_no_topic) : target));
        viewHolder.textViewPrepareText.setText(qcount + " Prepare questions");





        viewHolder.imageViewCoachingDelete.setVisibility(View.GONE);

//
//        if(cocahSession.isSample()){
//
//            viewHolder.imageViewCoachingDelete.setVisibility(View.VISIBLE);
//            viewHolder.imageViewCoachingDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    LogUtil.e(App.tag, "delete coachession=======");
//
//                    if(null!=onInnerItemClickListener){
//
//                        onInnerItemClickListener.onDelete(position,cocahSession);
//                    }
//
//                }
//            });
//
//        }else {
//            viewHolder.imageViewCoachingDelete.setVisibility(View.GONE);
//        }



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (null != onInnerItemClickListener) {
                    onInnerItemClickListener.onClick(position, cocahSession);
                }
            }
        });

        return convertView;
    }


    private Date date = new Date();

    class ViewHolder {

        @InjectView(R.id.textViewCoachingSession)
        TextView textViewMilestoneTitle;

        @InjectView(R.id.textViewTrust)
        TextView textViewTrust;

        @InjectView(R.id.textViewPrepareText)
        TextView textViewPrepareText;

        @InjectView(R.id.imageViewHand)
        ImageView imageViewHand;

        @InjectView(R.id.textViewTimeStamp)
        TextView textViewTimeStamp;

        @InjectView(R.id.imageViewDone)
        ImageView imageViewDone;

        @InjectView(R.id.imageViewCoachingDelete)
        ImageView imageViewCoachingDelete;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

    }


    public OnInnerItemClickListener getOnInnerItemClickListener() {
        return onInnerItemClickListener;
    }

    public void setOnInnerItemClickListener(OnInnerItemClickListener onInnerItemClickListener) {
        this.onInnerItemClickListener = onInnerItemClickListener;
    }


    public static interface OnInnerItemClickListener {
        public void onClick(int pos, CocahSession cocahSession);
        public void onDelete(int pos, CocahSession cocahSession);
    }
}
