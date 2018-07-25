package com.wangzy.javalib;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestIPP {


    static class TestThread extends Thread {

        Integer j = 0;

        public TestThread(Integer j) {
            this.j = j;
        }

        @Override
        public void run() {

            for (int i = 0; i < 10; i++) {
                this.j++;
            }

            System.out.println(this.j);
        }
    }


    public static void test(Integer j) {

        TestThread testThread = new TestThread(j);
        testThread.start();


        TestThread testThread2 = new TestThread(j);
        testThread2.start();


    }

    public static void main(String[] args) {
//        TestIPP.echo("I like ...");


//        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
//
//        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).map(it -> it + " endx").filter(x -> x.startsWith("a")).limit(1).collect(Collectors.toList());
//
//        filtered.forEach(System.out::println);


//        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
//        long count = strings.parallelStream().filter(string -> string.isEmpty()).count();
//        System.out.println(count);



        List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());

        System.out.println("筛选列表: " + filtered);
        String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining("--"));
        System.out.println("合并字符串: " + mergedString);

    }

    public static void echo(String wordYouSay) {
        class Person {
            public void say() {
                System.out.println(wordYouSay);
            }
        }
        Person person = new Person();
        person.say();
    }


    interface A {
        void test();
    }
}
