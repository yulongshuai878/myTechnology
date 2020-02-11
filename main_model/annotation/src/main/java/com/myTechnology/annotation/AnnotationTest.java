package com.myTechnology.annotation;

import java.util.Calendar;
import java.util.Date;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-02-11 16:55
 **/
public class AnnotationTest {
    @Deprecated
    private static void getString1(){
        System.out.println("Deprecated Method");
    }
    private static void getString2() {
        System.out.println(" Normal Method");
    }
    private static void getYear(){
        Date date = new Date(113,8,20);
        System.out.println(date.getYear());
    }
    private static void testCalendar(){
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.YEAR));
    }
    public static void main(String[] args) {
        getString1();
        getString2();
        getYear();
        testCalendar();
    }
}
