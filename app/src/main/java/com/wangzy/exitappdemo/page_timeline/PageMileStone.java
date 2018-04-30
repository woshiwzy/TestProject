package com.wangzy.exitappdemo.page_timeline;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.wangzy.exitappdemo.R;
import com.wangzy.exitappdemo.page_timeline.adapter.CocahAdapter;
import com.wangzy.exitappdemo.page_timeline.adapter.MileStoneAdapter;
import com.wangzy.exitappdemo.page_timeline.domain.CocahSession;
import com.wangzy.exitappdemo.page_timeline.domain.Milestone;
import com.wangzy.exitappdemo.page_timeline.domain.Timeline;
import com.wangzy.exitappdemo.util.DateTool;
import com.wangzy.exitappdemo.util.ListUtiles;
import com.wangzy.exitappdemo.util.StringUtils;
import com.wangzy.exitappdemo.util.Tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by wangzy on 15/9/16.
 */
public class PageMileStone extends BasePage {


    private MilestoneView milestoneView;

    private MyListView listViewLeft;
    private MyListView listViewRight;

    private TimeLineView timeLineView;


    private ArrayList<Milestone> arrayListMilestones;
    private ArrayList<CocahSession> arrayListCocahSessions;


    private int intLastMilestonePos = 0;
    private int intLastCocahPos = 0;


    private ArrayList<Timeline> timelines = null;

    public boolean isShowLeft = true;


    public PageMileStone(Activity mainActivity) {
        this.mainActivity = mainActivity;
        this.rootView = View.inflate(mainActivity, R.layout.page_main, null);


        initView();
    }


    public void initView() {


        milestoneView = (MilestoneView) findViewById(R.id.milestoneView);
        timeLineView = milestoneView.getViewTimeLine();

        milestoneView.setOnActionDownUpListener(new MilestoneView.OnActionDownUpListener() {
            @Override
            public void onActionUpDown(boolean upDown) {
//                mainActivity.getSlidingMenu().setSlidingEnabled(upDown);
            }
        });

        milestoneView.setOnAnimationDownListener(new MilestoneView.OnAnimationDownListener() {
            @Override
            public void onAnimationDown(boolean leftRight) {


            }
        });


//        MyListView.OnListViewTouchListener listViewTouchListener = new MyListView.OnListViewTouchListener() {
//            @Override
//            public void onTouchListener(boolean touchDown) {
//                mainActivity.getSlidingMenu().setSlidingEnabled(!touchDown);//when touch down set slide disable;
//
//            }
//        };


        listViewLeft = milestoneView.getListViewLeft();
//        listViewLeft.setOnListViewTouchListener(listViewTouchListener);
        listViewLeft.setOnScrollListener(listViewScrollListener);
        listViewLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 || position == parent.getCount()) {
                    return;
                }

                Milestone mileStone = (Milestone) parent.getAdapter().getItem(position);


            }
        });

        listViewRight = milestoneView.getListViewRight();
//        listViewRight.setOnListViewTouchListener(listViewTouchListener);
        listViewRight.setOnScrollListener(listViewScrollListener);

        //==============

        final ArrayList<Milestone> milestones = new ArrayList<>();

        final ArrayList<CocahSession> sessions = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-DD-mm HH:mm:ss");

        for (int i = 0; i < 10; i++) {

            String finisDate = "2013-10-18T17:30:29+00:00";

            Milestone milestone = new Milestone();
            milestone.setDescription("desc:" + i);
            milestone.setId(String.valueOf(i));
            milestone.setUserId(String.valueOf(i));
            milestone.setTitle("title " + String.valueOf(i));
            milestone.setFinishdate(finisDate);


            milestones.add(milestone);


            CocahSession cocahSession = new CocahSession();

            cocahSession.setStartTime(finisDate);
            cocahSession.setDescription("desc:" + String.valueOf(i));
            cocahSession.setId(String.valueOf(i));
            cocahSession.setTitle("title" + String.valueOf(i));
            cocahSession.setLocation("localtion");

            sessions.add(cocahSession);
        }

        //必须要等待 milestone view 第一次draw完成之后才能往里面填写数据，不然会报错
        milestoneView.postDelayed(new Runnable() {
            @Override
            public void run() {
                addTestData(milestones, sessions, String.valueOf(3));
            }
        }, 1000)
        ;
    }


    @Override
    public void onShow() {
        super.onShow();

    }


    public void addTestData(ArrayList<Milestone> milesList, ArrayList<CocahSession> sessionCocahs, final String locationId) {


        buildList(milesList, sessionCocahs, locationId);

    }


    private void buildList(ArrayList<Milestone> milestones, ArrayList<CocahSession> cocahSessions, String id) {

        sortMilestones(milestones);
        sortCocahes(cocahSessions);

        //========
        timelines = new ArrayList<Timeline>();

        arrayListMilestones = milestones;
        arrayListCocahSessions = cocahSessions;


        for (int i = 0, isize = arrayListMilestones.size(); i < isize; i++) {
            Milestone stone = arrayListMilestones.get(i);
            Timeline timeline = new Timeline();
            timeline.setId(stone.getId());
            timeline.setType(TimeLineView.INT_DRAWABLE_TYPE_STONE);
            timeline.setTime(stone.getFinishTimeMils());
            timeline.setTitle(stone.getTitle());
            timelines.add(timeline);
        }

        for (int i = 0, isize = arrayListCocahSessions.size(); i < isize; i++) {
            CocahSession cocahSession = arrayListCocahSessions.get(i);
            Timeline timeline = new Timeline();
            timeline.setId(cocahSession.getId());
            timeline.setType(TimeLineView.INT_DRAWABLE_TYPE_COCARH);
            timeline.setTime(cocahSession.getStartTimeLong());
            timeline.setTitle(cocahSession.getTitle());
            timelines.add(timeline);
        }

        Collections.sort(timelines, new Comparator<Timeline>() {
            @Override
            public int compare(Timeline lhs, Timeline rhs) {
                return compareWithLong(lhs.getTime(), rhs.getTime());
            }
        });

        for (int i = 0, isize = timelines.size(); i < isize; i++) {
            timelines.get(i).setPosInList(i);
        }

        timeLineView.initData(timelines);


        MileStoneAdapter mileStoneAdapter = new MileStoneAdapter(mainActivity, milestones, listViewLeft.getHeight());
        listViewLeft.setAdapter(mileStoneAdapter);

        mileStoneAdapter.selectIndex(0);
        //=============================


        CocahAdapter cocahAdapter = new CocahAdapter(mainActivity, cocahSessions, listViewRight.getHeight());

        listViewRight.setAdapter(cocahAdapter);


        cocahAdapter.setOnInnerItemClickListener(new CocahAdapter.OnInnerItemClickListener() {
            @Override
            public void onDelete(int pos, final CocahSession cocahSession) {


            }

            @Override
            public void onClick(int pos, CocahSession cocahSession) {

//                Intent intent = new Intent();
//                intent.putExtra("cocahSession", cocahSession);
//
//
//                Tool.startActivity(mainActivity, CoachSessionDetailActivity.class, intent);


            }
        });

        cocahAdapter.selectIndex(0);

        //===========================
        Object[] ret = findNextStoneAndCountFinishDone(milestones);

        int finisedCount = (Integer) ret[0];

        String finishDoneText = "";
        if (finisedCount > 1) {
            finishDoneText = String.valueOf(finisedCount + " Milestones Finished");
        } else {
            finishDoneText = String.valueOf(finisedCount + " Milestone Finished");
        }


        int pos = -1;

        if (StringUtils.isEmpty(id)) {

            if ((pos = findNextStonePostion(arrayListMilestones)) != -1) {
//                listViewLeft.smoothScrollToPositionFromTop(pos + 2, listViewLeft.getHeight() / 4, 200);
                smoothScrollToPositionFromTopWithBugWorkAround(listViewLeft, pos + 1, listViewLeft.getHeight() / 4, 200, true);
            }

        } else {
            if ((pos = findIndexByStoneId(id)) != -1) {
                listViewLeft.smoothScrollToPositionFromTop(pos + 1, listViewLeft.getHeight() / 4, 200);
                smoothScrollToPositionFromTopWithBugWorkAround(listViewLeft, pos + 1, listViewLeft.getHeight() / 4, 200, true);
            }
        }
        //=============================
        Object[] nextCoaching = findNextCoachFinishDone(cocahSessions);
        if (null != nextCoaching && -1 != ((Integer) nextCoaching[0])) {
            smoothScrollToPositionFromTopWithBugWorkAround(listViewRight, (Integer) nextCoaching[0] + 1, listViewRight.getHeight() / 4, 200, !isShowLeft);
        }
    }


    private void smoothScrollToPositionFromTopWithBugWorkAround(final AbsListView listView,
                                                                final int position,
                                                                final int offset,
                                                                final int duration, final boolean isCenterList) {

        //the bug workaround involves listening to when it has finished scrolling, and then
        //firing a new scroll to the same position.
        //the bug is the case that sometimes smooth Scroll To Position sort of misses its intended position.
        //more info here : https://code.google.com/p/android/issues/detail?id=36062
        listView.smoothScrollToPositionFromTop(position, offset, duration);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {

                    listView.setOnScrollListener(null);
                    listView.smoothScrollToPositionFromTop(position, offset, duration);

                    Message msg = new Message();
                    msg.obj = listView;
                    handler.sendMessageDelayed(msg, duration - 50);

                    Message msg2 = new Message();
                    msg2.obj = listView;

                    if (isCenterList) {
                        delayCenterHandler.sendMessageDelayed(msg2, duration);
                    }
//                    centerAtitemPosition((MyListView)listView);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }


    private Handler delayCenterHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            MyListView listView = (MyListView) msg.obj;
            centerAtitemPosition(listView);
        }
    };

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            ListView listView = (ListView) msg.obj;
            listView.setOnScrollListener(listViewScrollListener);
        }
    };


    private long computeDayFromNow(String text) {

        try {

            SimpleDateFormat ymdf = new SimpleDateFormat("yyyy-MM-dd");

            Date thatDay = ymdf.parse(text);
            Date nowDay = ymdf.parse(DateTool.getNowTime("yyyy-MM-dd"));

            long distance = Math.abs(thatDay.getTime() - nowDay.getTime());

            return distance / (1000 * 60 * 60 * 24);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }


    private int findIndexByStoneId(String id) {

        int index = -1;
        for (int i = 0, isize = arrayListMilestones.size(); i < isize; i++) {
            String tid = arrayListMilestones.get(i).getId();
            if (tid.equals(id)) {
                index = i;
                break;
            }
        }

        return index;
    }


    private Object[] findNextStoneAndCountFinishDone(ArrayList<Milestone> milestones) {

        if (ListUtiles.isEmpty(milestones)) {
            return new Object[]{0, DateTool.getNowTime("yyyy-MM-dd")};
        }

        Date now = new Date();
        int doneCount = 0;
        String nextTargetTime = "";


        for (int i = milestones.size() - 1; i >= 0; i--) {

            if (milestones.get(i).getFinishTimeMils() < now.getTime()) {
                doneCount++;
            }

            if (milestones.get(i).getFinishTimeMils() > now.getTime()) {
                if (StringUtils.isEmpty(nextTargetTime)) {
                    nextTargetTime = milestones.get(i).getFinishDay();
                }
            }
        }

        return new Object[]{doneCount, nextTargetTime};
    }


    private Object[] findNextCoachFinishDone(ArrayList<CocahSession> sessions) {
        if (ListUtiles.isEmpty(sessions)) {
            return new Object[]{-1, DateTool.getNowTime("yyyy-MM-dd HH:mm:ss")};
        }

        Date now = new Date();
        int doneCount = -1;
        String nextTargetTime = "";

        for (int i = 0, isize = sessions.size(); i < isize; i++) {
            CocahSession session = sessions.get(i);

            if (session.getStartTimeLong() < now.getTime()) {
                doneCount = i - 1;
                break;
            }
        }

        if (doneCount == -1) {
            doneCount = sessions.size();
        }

//        for (int i = sessions.size() - 1; i >= 0; i--) {
//            if (sessions.get(i).getStartTimeLong() < now.getTime()) {
//                doneCount++;
//            }
//
//            if (sessions.get(i).getStartTimeLong() > now.getTime()) {
//                if (StringUtils.isEmpty(nextTargetTime)) {
//                    nextTargetTime = sessions.get(i).getStartTime();
//                }
//            }
//        }
//        if (doneCount == -1) {
//            doneCount = sessions.size() - 1;
//        }

        return new Object[]{doneCount, nextTargetTime};
    }


    private int findNextStonePostion(ArrayList<Milestone> milestones) {
        if (ListUtiles.isEmpty(milestones)) {
            return -1;
        }

        Date now = new Date();
        String nextTargetTime = "";
        for (int i = milestones.size() - 1; i >= 0; i--) {

            if (milestones.get(i).getFinishTimeMils() > now.getTime()) {
                if (StringUtils.isEmpty(nextTargetTime)) {
                    return i;
                }
            }
        }
        return 0;
    }


    private int findNextStonePostion(ArrayList<Milestone> milestones, String id) {
        if (ListUtiles.isEmpty(milestones)) {
            return -1;
        }

        for (int i = milestones.size() - 1; i >= 0; i--) {
            if (milestones.get(i).getId().equals(id)) {
                return i;
            }

        }
        return 0;
    }

    /**
     * center what you have selected item
     *
     * @param listView
     */
    private void centerAtitemPosition(MyListView listView) {

        if (null == listView.getAdapter() || listView.getAdapter().getCount() == 0) {
            return;
        }

        int fistVisiblePosition = listView.getFirstVisiblePosition();
        int lastVisiblePosition = listView.getLastVisiblePosition();

        int wantChildPos = 0;

        if ((lastVisiblePosition - fistVisiblePosition) == 2) {//had show 3 visibile items so show center mid item
            wantChildPos = (fistVisiblePosition + 1);
        } else {//just show 2 item so judge，who is most should show center item
            int childCount = listView.getChildCount();
            View fistChild = listView.getChildAt(0);
            if (null != fistChild) {
                int ftop = fistChild.getTop();
                if (childCount > 0 && ftop >= 0) {
                    wantChildPos = fistVisiblePosition;
                } else {
                    wantChildPos = fistVisiblePosition + 1;
                }
            }
        }

        View childView = Tool.getChildViwFromList((ListView) listView, wantChildPos - 1);

        if (null != childView) {
            scrollListView(listView, childView, wantChildPos);
        }
    }


    private void scrollListView(ListView listView, View childView, int wantChildPos) {

        int offset = (int) (childView.getHeight() / 2);

        listView.smoothScrollToPositionFromTop(wantChildPos, offset, 200);

        //==============
        BaseAdapter adapter = (BaseAdapter) listView.getAdapter();
//        ListAdapter adapter =(BaseAdapter) headerListAda();

//        int scrollPostion = wantChildPos - 1;
        int scrollPostion = wantChildPos ;

        if (adapter instanceof MileStoneAdapter) {

            ((MileStoneAdapter) adapter).selectIndex(scrollPostion);

            scrollMilestonePos(intLastMilestonePos, scrollPostion, arrayListMilestones);
            intLastMilestonePos = scrollPostion;

        } else if (adapter instanceof CocahAdapter) {
            ((CocahAdapter) adapter).selectIndex(scrollPostion);

            scrollCocahPos(intLastCocahPos, scrollPostion, arrayListCocahSessions);
            intLastCocahPos = scrollPostion;
        }

    }


    /**
     * scroll time timer shaft
     *
     * @param oldPos
     * @param newPos
     */
    private void scrollMilestonePos(int oldPos, int newPos, ArrayList<Milestone> milestones) {
        timeLineView.moveToLocation(newPos, TimeLineView.INT_DRAWABLE_TYPE_STONE);
    }

    private void scrollCocahPos(int oldPos, int newPos, ArrayList<CocahSession> cocahSessions) {
        timeLineView.moveToLocation(newPos, TimeLineView.INT_DRAWABLE_TYPE_COCARH);
    }

    AbsListView.OnScrollListener listViewScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView listView, int scrollState) {

            switch (scrollState) {
                case SCROLL_STATE_IDLE:
                    centerAtitemPosition((MyListView) listView);
                    break;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    };


    //======================

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private void sortMilestones(ArrayList<Milestone> milestones) {

        Collections.sort(milestones, new Comparator<Milestone>() {
                    @Override
                    public int compare(Milestone lhs, Milestone rhs) {
                        //  "2013-10-18T17:30:29+00:00"

                        String timestap1 = lhs.getFinishdate().substring(0, 10) + " 00:00:00";

                        String timestamp2 = rhs.getFinishdate().substring(0, 10) + " 00:00:00";

                        return compareTimeText(timestap1, timestamp2);
                    }
                }

        );

    }

    private void sortCocahes(ArrayList<CocahSession> cocahSessions) {

        Collections.sort(cocahSessions, new Comparator<CocahSession>() {

            @Override
            public int compare(CocahSession lhs, CocahSession rhs) {

                String timestap1 = lhs.getStartTime().replace("T", " ");
                String timestamp2 = rhs.getStartTime().replace("T", " ");

                return compareTimeText(timestap1, timestamp2);

            }
        });

    }


    private int compareTimeText(String timestap1, String timeStamp2) {

        try {

            Date date1 = simpleDateFormat.parse(timestap1);
            Date date2 = simpleDateFormat.parse(timeStamp2);

            return compareWithLong(date1.getTime(), date2.getTime());

        } catch (Exception e) {
        }

        return 0;
    }


    private int compareWithLong(long t1, long t2) {
        if (t1 < t2) {
            return 1;
        }

        if (t1 == t2) {
            return 0;
        }
        return -1;
    }

    @Override
    public void onDestroy() {
        arrayListCocahSessions = null;
        arrayListMilestones = null;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


    }
}
