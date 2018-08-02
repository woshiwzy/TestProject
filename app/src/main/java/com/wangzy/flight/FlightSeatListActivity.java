package com.wangzy.flight;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.wangzy.exitappdemo.R;
import com.wangzy.exitappdemo.consts.ConstantKt;
import com.wangzy.exitappdemo.util.LogUtil;
import com.wangzy.exitappdemo.util.Tool;
import com.wangzy.utils.AssertTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class FlightSeatListActivity extends AppCompatActivity {


    ExpandableListView expandableListView;

    EditText editTextSearch;
    MyExpandListAdapter myExpandListAdapter = null;
    ArrayList<FlightGraphicsTemplate> flightTemplates = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_seat_list);
        expandableListView = findViewById(R.id.expandListView);
        editTextSearch = findViewById(R.id.editTextSearch);

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String re = editTextSearch.getText().toString();
                for (int i = 0, isize = flightTemplates.size(); i < isize; i++) {
                    FlightGraphicsTemplate flightGraphicsTemplate = flightTemplates.get(i);
                    ArrayList<String> childs = flightGraphicsTemplate.getChild();
                    for (String str : childs) {
                        if (str.equals(re)) {
                            myExpandListAdapter.setSelect(i);
                            expandableListView.setSelection(i);
                            expandableListView.expandGroup(i);
                            return;
                        }
                    }
                }

            }
        });

        initListView();
    }

    private void initListView() {

        findViewById(R.id.buttonValidate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        loadAdapterData();
    }


    /**
     * 加载adapter数据
     */
    private void loadAdapterData() {

        ArrayList<String> files = AssertTool.listAssertFiles(this, "data");

        Set<String> configTemplate = new HashSet<>();

        for (String data : files) {

            String jsonFile = data;
            String jsonFileName = jsonFile.replace(".json", "");
            configTemplate.add(jsonFileName);
        }

        flightTemplates = FlightGraphicsTemplateTool.getAllFlightTemplate(this);

        int totalFlihtNumber = 0;
        int successNumber = 0;

        for (FlightGraphicsTemplate flightTemplate : flightTemplates) {

            ArrayList<String> childs = flightTemplate.getChild();
            totalFlihtNumber += childs.size();

            for (String childFlightNumber : childs) {

                if (configTemplate.contains(childFlightNumber)) {
                    successNumber++;
                    flightTemplate.setConfigFile(childFlightNumber);
                    break;
                }
            }
        }

        Collections.sort(flightTemplates, new Comparator<FlightGraphicsTemplate>() {
            @Override
            public int compare(FlightGraphicsTemplate o1, FlightGraphicsTemplate o2) {

                boolean onLine1 = o1.existConfig();
                boolean onLine2 = o2.existConfig();
                if (onLine1 ^ onLine2) {
                    return onLine1 ? 1 : -1;
                } else {
                    return 0;
                }
            }
        });

        ((TextView) findViewById(R.id.textViewTitle)).setText("总飞机图布局：" + flightTemplates.size() + "个缺少" + (flightTemplates.size() - successNumber) + "个布局图,对应飞机号：" + totalFlihtNumber + "个");

        myExpandListAdapter = new MyExpandListAdapter(this, flightTemplates);

        expandableListView.setAdapter(myExpandListAdapter);
        expandableListView.setGroupIndicator(null);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String childFlight = (String) parent.getExpandableListAdapter().getChild(groupPosition, childPosition);

                FlightGraphicsSeat seat = FlightGraphicsTemplateTool.findFlightSetByFlightNumber(FlightSeatListActivity.this, childFlight);

                LogUtil.i(ConstantKt.getTAG(), "child flight:" + childFlight + " template:" + seat.getRangeTitle());


                Intent i = new Intent();
                FlightSeatActivity.flightTemplate = (FlightGraphicsTemplate) parent.getExpandableListAdapter().getGroup(groupPosition);

                if (FlightSeatActivity.flightTemplate.existConfig()) {
                    Tool.startActivity(FlightSeatListActivity.this, FlightSeatActivity.class, i);
                } else {
                    Tool.ToastShow(FlightSeatListActivity.this, "没有座位图配置文件");
                }
                return false;
            }
        });

    }


    /**
     * 验证配置文件是否有误
     */
    private void validate() {

        String jsonContent = AssertTool.getAssertStringContent(this, "flightmap.json");

        try {

            JSONArray jsonArray = new JSONArray(jsonContent);

            Set<String> templateFiles = new HashSet<String>();//所有座位图布局
            Set<String> flightNumbers = new HashSet<String>();//所有飞机号

            for (int i = 0, isize = jsonArray.length(); i < isize; i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String template = jsonObject.getString("template");

                if (templateFiles.contains(template)) {
                    Tool.ToastShow(this, "航班号模版有重复：" + template);
                    break;
                } else {
                    templateFiles.add(template);
                }

                //验证飞机号配置是否正确
                JSONArray childAray = jsonObject.getJSONArray("child");
                boolean validate = false;

                for (int j = 0, jsize = childAray.length(); j < jsize; j++) {
                    String child = childAray.getString(j);
                    if (flightNumbers.contains(child)) {
                        Tool.ToastShow(this, "飞机号：" + child + "被包含在多个模版中，请检查！");
                        return;
                    } else {
                        flightNumbers.add(child);
                    }
                    if (child.equals(template) && validate == false) {
                        validate = true;
                    }
                }

                if (!validate) {
                    Tool.ToastShow(this, template + " Template没有被包含在Child中，检查是否有误！");
                    return;
                }
                LogUtil.i(ConstantKt.getTAG(), "tempate:" + template + " child:" + childAray.length() + " validate:" + validate);
            }
            Tool.ToastShow(this, "Success：共" + jsonArray.length() + "个（模版/座位布局图）配置" + "匹配:" + flightNumbers.size() + "个飞机号");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    class MyExpandListAdapter extends BaseExpandableListAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private int select = -1;

        private ArrayList<FlightGraphicsTemplate> getGroupDatas = new ArrayList<>();

        public MyExpandListAdapter(Context context, ArrayList<FlightGraphicsTemplate> getGroupDatas) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
            this.getGroupDatas = getGroupDatas;
        }


        @Override
        public int getGroupCount() {
            return getGroupDatas.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {

            return getGroupDatas.get(groupPosition).getChild().size();

        }

        @Override
        public Object getGroup(int groupPosition) {
            return getGroupDatas.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {

            return getGroupDatas.get(groupPosition).getChild().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        public void setSelect(int index) {

            this.select = index;
            this.notifyDataSetChanged();

        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if (null == convertView) {
                convertView = layoutInflater.inflate(R.layout.item_flight_group, null);
            }

            boolean configed = getGroupDatas.get(groupPosition).existConfig();

            ((TextView) convertView.findViewById(R.id.textViewTotalChild)).setText("飞机数：" + getGroupDatas.get(groupPosition).getChild().size() + " config:" + configed);

            if (configed) {
                ((TextView) convertView.findViewById(R.id.textViewTotalChild)).setTextColor(Color.parseColor("#ff000000"));
            } else {
                ((TextView) convertView.findViewById(R.id.textViewTotalChild)).setTextColor(Color.parseColor("#ffff0000"));
            }

            ((TextView) convertView.findViewById(R.id.textViewFlightNumberGroup)).setText("Template飞机号：" + getGroupDatas.get(groupPosition).getTemplate());

            if (select == groupPosition) {
                convertView.setBackgroundColor(Color.MAGENTA);
            } else {
                convertView.setBackgroundColor(Color.WHITE);
            }

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            if (null == convertView) {
                convertView = layoutInflater.inflate(R.layout.item_flight_child, null);
            }

            String var = getGroupDatas.get(groupPosition).getChild().get(childPosition) + " 文件名：" + getGroupDatas.get(groupPosition).getConfigFile();
            ((TextView) convertView.findViewById(R.id.textViewFlightNumberChild)).setText(var);

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }
}
