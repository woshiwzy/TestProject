package com.wangzy.work.util;

public class Main {

    public static String dec(String output) {
        char[] xss = output.toCharArray();
        StringBuffer sbf = new StringBuffer();
        for (char x : xss) {
            int xi = Integer.parseInt(x + "");
            if (xi > 4) {
                int xs = xi - 4;
                sbf.append(xs);
            } else {
                int xs = 9 - xi;
                sbf.append(xs);
            }
        }
        return sbf.toString();
    }

    public static void main(String[] args) {

        String x = "567843210";
        System.out.print(dec(x));


    }
}