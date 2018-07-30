package com.wangzy.flight;

import android.content.Context;

import com.wangzy.exitappdemo.consts.ConstantKt;
import com.wangzy.exitappdemo.util.LogUtil;
import com.wangzy.utils.AssertTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FlightHelper {

    public static FlightSeat loadFlightSeat(Context context, String absFilePath) {

        String json = AssertTool.getAssertStringContent(context, absFilePath);

        LogUtil.d(ConstantKt.getTAG(), "contnets:" + json);

        try {
            JSONObject jsonObject = new JSONObject(json);

            FlightSeat flightSeat = new FlightSeat();

            flightSeat.setPlaneNumber("310");
            flightSeat.setListHeaderTitle(jsonObject.getString("listHeaderTitle"));
            
            flightSeat.setRangeTitle(jsonObject.getString("rangeTitle"));
//            flightSeat.setTotalRow(jsonObject.getInt("total_row"));

            JSONArray array = jsonObject.getJSONArray("rows");
            if (null != array && array.length() > 0) {
                for (int i = 0, isize = array.length(); i < isize; i++) {

                    JSONObject rowObject = array.getJSONObject(i);

                    Row row = new Row();

                    row.setRowId(rowObject.getInt("rowId"));
                    row.setSeatArrange(rowObject.getString("seatArrange"));
                    row.setRangeTitle(rowObject.getString("rangeTitle"));
                    row.setSeatArrangeNumber(rowObject.getString("seatArrangeNumber"));
                    flightSeat.addRow(row);
                }
            }

            return flightSeat;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
