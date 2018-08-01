package com.wangzy.flight;

import android.content.Context;

import com.wangzy.utils.AssertTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 飞机座位布局
 */
public class FlightSeat {

    private String version;//版本
    private String listHeaderTitle;
    private String rangeTitle;

    private String planeNumber;//飞机号

    private ArrayList<Row> rows;//座位布局种类和分布

    /**
     * 座位图自检查
     *
     * @return
     */
    public String selfValidate() {

        String headerTitle = this.listHeaderTitle.replace("_", "");
        String rangeTitle = this.rangeTitle;

        //检查Title
        if (!headerTitle.equals(rangeTitle)) {
            return "listHeaderTitle 和 rangeTitle 不相同。";
        }

        List<String> titlelist = new ArrayList<>();
        char[] ary = headerTitle.toCharArray();
        for (char ar : ary) {
            titlelist.add(String.valueOf(ar));
        }
        Set<String> titles = new HashSet<>(titlelist);

        //检查布局种类
        Set<Integer> rowIds = new HashSet<>();
        for (Row row : rows) {
            rowIds.add(row.getRowId());
        }
        if (rowIds.size() != rows.size()) {
            return "rowId 可能有重复，请注意！";
        }
        //检测布局内容是否正确
        for (Row row : rows) {

            int rowId = row.getRowId();

            String[] seatsType = row.getSeatArrange().split(",");
            String[] title = row.getRangeTitle().split(",");

            if (seatsType.length != title.length) {
                return "rowId:" + rowId + "seatArrange 和 rangeTitle 长度不一样";
            }

            for (int i = 0; i < seatsType.length; i++) {

                String seat = seatsType[i];
                if (!seat.equals("1") && !seat.equals("0") && !seat.equals("A")) {
                    return "seatArrange 只能为1，0，A";
                }
                String titleLabel = title[i];
                if (!titles.contains(titleLabel) && !"_".equals(titleLabel)) {
                    return rowId + " rangeTitle出现了错误，请核对rangeTitle";
                }
            }

            String[] seatArrangeNumbers = row.getSeatArrangeNumber().split(",");

            for (String seatNumberRange : seatArrangeNumbers) {

                String[] seatNumbers = seatNumberRange.split("-");

                if (seatNumbers.length != 2) {
                    return rowId + " seatArrangeNumber 格式应该是：开始数字-结束数字";
                }
                try {

                    int start = Integer.parseInt(seatNumbers[0]);
                    int end = Integer.parseInt(seatNumbers[1]);

                    if (start > end) {
                        return rowId + " seatArrangeNumber start 应该小于等于 end";
                    }
                } catch (Exception e) {
                    return rowId + " seatArrangeNumber 格式应该是：开始数字-结束数字";
                }
            }
        }


        //检测 seatArrangeNumber(开始和结束都是唯一的)，是否有重复

        ArrayList<Row.RowRange> realrows = generateRealRow();

        Set<Integer> rearowStart = new HashSet<>();
        Set<Integer> rearowEnd = new HashSet<>();

        int totalRange = 0;
        for (Row row : rows) {

            String[] seatRange = row.getSeatArrangeNumber().split(",");


            for (String range : seatRange) {

                totalRange++;

                String[] r = range.split("-");

                int start = Integer.parseInt(r[0]);
                rearowStart.add(start);

                int end = Integer.parseInt(r[1]);
                rearowEnd.add(end);

            }
        }

        if (rearowStart.size() != totalRange) {
            return " seatArrangeNumber start 排列有重复";
        }

        if (rearowEnd.size() != totalRange) {
            return " seatArrangeNumber end 排列有重复";
        }


        return null;
    }

    public String getListHeaderTitle() {
        return listHeaderTitle;
    }

    public void setListHeaderTitle(String listHeaderTitle) {
        this.listHeaderTitle = listHeaderTitle;
    }

    public Row getRowById(int rowId) {
        for (Row row : rows) {
            if (row.getRowId() == rowId) {
                return row;
            }
        }
        return null;
    }


    /**
     * 获取座位布局,按开始的排从小到大排序
     *
     * @return
     */
    public ArrayList<Row.RowRange> getAllRow() {

        ArrayList<Row.RowRange> arranges = new ArrayList<>();
        for (Row row : rows) {
            arranges.addAll(row.getRowRanges());
        }
        Collections.sort(arranges, new Comparator<Row.RowRange>() {
            @Override
            public int compare(Row.RowRange o1, Row.RowRange o2) {
                return o1.getStart() > o2.getStart() ? 1 : -1;
            }
        });
        return arranges;
    }


    /**
     * 生成可以用于adapter的数据
     *
     * @return
     */
    public ArrayList<Row.RowRange> generateRealRow() {

        ArrayList<Row.RowRange> arranges = new ArrayList<>();

        ArrayList<Row.RowRange> sortRowTypes = getAllRow();

        for (Row.RowRange rowRange : sortRowTypes) {

            for (int i = rowRange.getStart(), isize = rowRange.getEnd(); i <= isize; i++) {


                Row.RowRange newRange = rowRange.clone();

                newRange.setRowNumber(String.valueOf(i));

                arranges.add(newRange);

            }
        }

        return arranges;
    }


    public FlightSeat() {
        rows = new ArrayList<>();
    }

    public void addRow(Row row) {
        this.rows.add(row);
    }

    public String getPlaneNumber() {
        return planeNumber;
    }

    public void setPlaneNumber(String planeNumber) {
        this.planeNumber = planeNumber;
    }


    public ArrayList<Row> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Row> rows) {
        this.rows = rows;
    }


    public Row getRowByRowNumber(int row) {

        for (Row temRow : rows) {
            String[] numers = temRow.getSeatArrangeNumber().split(",");
            if (isInNumber(row, numers)) {
                return temRow;
            }
        }
        return null;
    }

    private boolean isInNumber(int row, String[] numbers) {

        for (String numberrange : numbers) {
            String[] sart_end = numberrange.split("-");
            int start = Integer.parseInt(sart_end[0].trim());
            int end = Integer.parseInt(sart_end[1].trim());
            if (row >= start && row <= end) {
                return true;
            }
        }
        return false;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRangeTitle() {
        return rangeTitle;
    }

    public void setRangeTitle(String rangeTitle) {
        this.rangeTitle = rangeTitle;
    }




    public static FlightSeat loadFlightSeat(Context context, String absFilePath) {

        String json = AssertTool.getAssertStringContent(context, absFilePath);

        try {
            JSONObject jsonObject = new JSONObject(json);

            FlightSeat flightSeat = new FlightSeat();

            flightSeat.setVersion(jsonObject.getString("version"));
            flightSeat.setListHeaderTitle(jsonObject.getString("listHeaderTitle"));
            flightSeat.setRangeTitle(jsonObject.getString("rangeTitle"));

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
