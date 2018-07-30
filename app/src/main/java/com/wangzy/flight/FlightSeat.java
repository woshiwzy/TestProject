package com.wangzy.flight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
     * 座位图自动检查
     * @return
     */
    public String  selfValidate(){


        return "";
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
}
