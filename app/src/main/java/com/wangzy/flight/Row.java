package com.wangzy.flight;


import java.util.ArrayList;

/**
 * 座位排描述
 */
public class Row {

//    {
//        "rowId": "2",
//            "seatArrange": "1,1,1,A,A,1,1,1",
//            "rangeTitle": "A,B,C,_,_,D,E,F",
//            "seatArrangeNumber": "3-23",
//            "rowNumber": "3-23"
//    }

    private int rowId;
    private String seatArrange;
    private String rangeTitle;//A,B,C,D,E,F
    private String seatArrangeNumber;
    private String rowNumber;


    public ArrayList<RowRange> getRowRanges() {

        ArrayList<RowRange> rowRanges = new ArrayList<>();

        String[] arranges = this.seatArrangeNumber.split(",");
        for (String label : arranges) {

            String[] startEnd = label.trim().split("-");

            int start = Integer.parseInt(startEnd[0]);
            int end = Integer.parseInt(startEnd[1]);

            RowRange rowRange = new RowRange();
            rowRange.start = start;
            rowRange.end = end;
            rowRange.row = this;
            rowRanges.add(rowRange);
        }
        return rowRanges;
    }


    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public String getSeatArrange() {
        return seatArrange;
    }

    public void setSeatArrange(String seatArrange) {
        this.seatArrange = seatArrange;
    }

    public String getRangeTitle() {
        return rangeTitle;
    }

    public void setRangeTitle(String rangeTitle) {
        this.rangeTitle = rangeTitle;
    }

    public String getSeatArrangeNumber() {
        return seatArrangeNumber;
    }

    public void setSeatArrangeNumber(String seatArrangeNumber) {
        this.seatArrangeNumber = seatArrangeNumber;
    }

    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }


    @Override
    public String toString() {
        return "Row{" +
                "rowId='" + rowId + '\'' +
                ", seatArrange='" + seatArrange + '\'' +
                ", rangeTitle='" + rangeTitle + '\'' +
                ", seatArrangeNumber='" + seatArrangeNumber + '\'' +
                ", rowNumber='" + rowNumber + '\'' +
                '}';
    }


    public static class RowRange implements Cloneable {

        private Row row;

        private int start;
        private int end;

        String rowNumber;//座位的排号，可能并不是自然增长

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public Row getRow() {
            return row;
        }

        public void setRow(Row row) {
            this.row = row;
        }

        public String getRowNumber() {
            return rowNumber;
        }

        public void setRowNumber(String rowNumber) {
            this.rowNumber = rowNumber;
        }




        @Override
        public String toString() {
            return "RowRange{" +
                    "row=" + row +
                    ", start=" + start +
                    ", end=" + end +
                    ", rowNumber='" + rowNumber + '\'' +
                    '}';
        }

        @Override
        public RowRange clone() {

            try {
                return (RowRange) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
