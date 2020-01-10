package com.myTechnology.JVM;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-01-10 16:45
 **/
public class ConstClass {
    static {
        System.out.println("ConstClass init!");
    }
    public static final String HELLOWORLD = "hello world!";
}
