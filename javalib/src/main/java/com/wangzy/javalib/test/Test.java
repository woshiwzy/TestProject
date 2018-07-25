package com.wangzy.javalib.test;

public class Test {


//    public static void sort(int[] s, int l, int r) {
//
//        if(l<r){
//
//            int i=l,j=r,x=s[i];
//
//            while(i<j){
//
//                while(i<j& s[j]<=x){
//                    j--;
//                }
//
//                if(i<j){
//                    s[i++]=s[j];
//                }
//
//                while(i<j&&s[i]>x){
//                    i++;
//                }
//
//                if(i<j){
//                    s[j--]=s[i];
//                }
//
//            }
//
//
//
//            s[i]=x;
//            sort(s,l,i-1);
//            sort(s,i+1,r);
//        }
//
//    }

    public static byte[] intToByte4(int sum) {
        byte[] arr = new byte[4];
        arr[0] = (byte) (sum >> 24);
        arr[1] = (byte) (sum >> 16);
        arr[2] = (byte) (sum >> 8);
        arr[3] = (byte) (sum & 0xff);
        return arr;
    }


    public static void sort(int[] s, int l, int r) {

        if (l < r) {

            int i = l, j = r, x = s[i];

            while (i < j) {

                while (i < j && s[j] >= x) {
                    j--;
                }
                if (i < j) {
                    s[i++] = s[j];
                }

                while (i < j && s[i] < x) {
                    i++;
                }
                if (i < j) {
                    s[j--] = s[i];
                }
            }
            s[i] = x;
            sort(s, l, i - 1);
            sort(s, i + 1, r);
        }
    }


    public static void main(String args[]) {
//
//        int[] a = {7, 7, 2, 1, 5, 6, 8, 0};
//        sort(a, 0, a.length - 1);
//        for (int x : a) {
//            System.out.print(" " + x);
//        }
//
//        System.out.println("---------");
//
//        float f=0.1111f;
//        byte[] ret=intToByte4(Float.floatToIntBits(f));
//        for(byte x:ret){
//            System.out.print(x);
//        }


//        HashMap<String, String> hashMap = new HashMap<>();
//        LinkedHashMap<String, String> linkmap = new LinkedHashMap<>();
//
//        int count = 100000;
//
//
//        long start2 = System.currentTimeMillis();
//
//        for (int i = 0; i < count; i++) {
//            linkmap.put(String.valueOf(i), String.valueOf(i));
//        }
//        long end2 = System.currentTimeMillis();
//
//        System.out.println("插入LinkedMap cost time2:" + (end2 - start2));
//
//
//        long start1 = System.currentTimeMillis();
//        for (int i = 0; i < count; i++) {
//            hashMap.put(String.valueOf(i), String.valueOf(i));
//        }
//        long end1 = System.currentTimeMillis();
//
//        System.out.println("插入HashMap：cost time:" + (end1 - start1));
//
//
//
//
//        //===================================================================================================
//
//        System.out.println("=================================================================================");
//
//
//
//        long start4 = System.currentTimeMillis();
//
//        for (Map.Entry<String, String> entry : linkmap.entrySet()) {
//
//        }
//
//        long end4 = System.currentTimeMillis();
//
//        System.out.println("迭代linkedhashmap:" + (end4 - start4));
//
//
//
//        long satart3 = System.currentTimeMillis();
//
//        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
//
//        }
//
//        long end3 = System.currentTimeMillis();
//
//
//        System.out.println("迭代Hashmap:" + (end3 - satart3));
//
//
//
//        System.out.println("=================================");
//
//        long getStart2 = System.currentTimeMillis();
//
//        for (int i = 0; i < count; i++) {
//            linkmap.get(String.valueOf(i));
//        }
//
//        long getEnd2 = System.currentTimeMillis();
//        System.out.println("link get cost:"+(getEnd2-getStart2));
//
//
//        long getStart = System.currentTimeMillis();
//
//        for (int i = 0; i < count; i++) {
//            hashMap.get(String.valueOf(i));
//        }
//
//        long getEnd = System.currentTimHumaneMillis();
//        System.out.println("hashmap get cost:"+(getEnd-getStart));
//        System.out.println("=================================================================================");



        System.out.println(getHuman());

    }

    public static Human getHuman(){

        return new Human();
    }

    static class Human {

        public Human() {
            System.out.println("Human init:"+toString());
        }


        @Override
        public String toString() {
            return super.toString();
        }
    }

}
