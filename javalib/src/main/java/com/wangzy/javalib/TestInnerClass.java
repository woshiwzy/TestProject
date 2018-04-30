package com.wangzy.javalib;


public class TestInnerClass {

   static class A {


        public void sayA() {
            System.out.print("A");
        }


        class B {

            public void sayB() {
                System.out.print("b");
            }
        }
    }


    public static void main(String args[]) {

        A.B b=new A().new B();

    }

}
