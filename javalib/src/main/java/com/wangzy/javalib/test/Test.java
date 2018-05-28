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


    public static void sort(int[] s,int l,int r){

        if(l<r){

            int i=l,j=r,x=s[i];

            while(i<j){

                while(i<j&&s[j]>=x){
                    j--;
                }
                if(i<j){
                    s[i++]=s[j];
                }

                while(i<j && s[i]<x){
                    i++;
                }
                if(i<j){
                    s[j--]=s[i];
                }
            }
            s[i]=x;
            sort(s,l,i-1);
            sort(s,i+1,r);
        }
    }


    public static void main(String args[]) {
//
        int[] a = {7,7,2, 1, 5, 6, 8, 0};
        sort(a, 0, a.length - 1);
        for (int x : a) {
            System.out.print(" " + x);
        }
//
//        System.out.println("---------");
//
//        float f=0.1111f;
//        byte[] ret=intToByte4(Float.floatToIntBits(f));
//        for(byte x:ret){
//            System.out.print(x);
//        }

    }

}
