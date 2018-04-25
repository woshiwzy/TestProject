package com.wangzy.javalib;

public class MyClass {


    static class MyThread extends Thread {

        @Override
        public void run() {

            System.out.println("I am thread");
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            super.run();
        }
    }


    public static void main(String args[]) throws InterruptedException {

        System.out.println("Hi i am java");

        MyThread mt = new MyThread();


        mt.start();
        mt.join();

        System.out.println("-=======end==========");


    }

}
