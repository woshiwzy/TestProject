package com.wangzy.flight;

import android.content.Context;

import com.wangzy.utils.AssertTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 座位图和飞机号查询工具
 */
public class FlightGraphicsTemplateTool {


    public static ArrayList<FlightGraphicsTemplate> flightTemplates;

    public static void initdata(Context context) {
        if (null == flightTemplates) {
            flightTemplates = loadData(context);
        }
    }


    public static ArrayList<FlightGraphicsTemplate> getAllFlightTemplate(Context context) {
        initdata(context);
        return flightTemplates;
    }


    /**
     * 根据飞机号查询座位图
     *
     * @param context
     * @param planeCode
     * @return
     */
    public static FlightGraphicsSeat findFlightSetByFlightNumber(Context context, String planeCode) {
        initdata(context);
        if (null == flightTemplates) {
            flightTemplates = loadData(context);
        }

        FlightGraphicsTemplate targetTemplate = null;

        for (FlightGraphicsTemplate flightTemplate : flightTemplates) {

            if (targetTemplate != null) {
                break;
            }

            if (flightTemplate.getTemplate().equals(planeCode)) {
                targetTemplate = flightTemplate;
                break;
            } else {
                ArrayList<String> childs = flightTemplate.getChild();
                for (String child : childs) {
                    if (child.equals(planeCode)) {
                        targetTemplate = flightTemplate;
                        break;
                    }
                }
            }
        }

        FlightGraphicsSeat flightSeat = FlightGraphicsSeat.loadFlightSeat(context, "data/" + targetTemplate.getConfigFile() + ".json");

        return flightSeat;

    }


    /**
     * 加载映射文件
     *
     * @return
     */
    private static ArrayList<FlightGraphicsTemplate> loadData(Context context) {

        //加载所有存在的文件
        ArrayList<String> files = AssertTool.listAssertFiles(context, "data");
        Set<String> configTemplate = new HashSet<>();
        for (String data : files) {
            String jsonFile = data;
            String jsonFileName = jsonFile.replace(".json", "");
            configTemplate.add(jsonFileName);
        }


        String jsonContent = AssertTool.getAssertStringContent(context, "flightmap.json");

        ArrayList<FlightGraphicsTemplate> flightTemplates = new ArrayList<>();

        try {

            JSONArray jsonArray = new JSONArray(jsonContent);
            for (int i = 0, isize = jsonArray.length(); i < isize; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String template = jsonObject.getString("template");
                FlightGraphicsTemplate flightTemplate = new FlightGraphicsTemplate();
                flightTemplate.setTemplate(template);
                JSONArray childAray = jsonObject.getJSONArray("child");
                for (int j = 0, jsize = childAray.length(); j < jsize; j++) {
                    String child = childAray.getString(j);
                    flightTemplate.addChild(child);
                    if (files.contains(child)) {
                        flightTemplate.setConfigFile(child);
                    }
                }
                flightTemplates.add(flightTemplate);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return flightTemplates;
    }

}
