package com.myTechnology.JVM;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author YLsTone
 * @version 1.0
 * @description
 * @date 2021/4/14 15:07
 **/
public class OOMTest {

    public static List<Object> list = new ArrayList<>();

    // JVM设置    
    // -Xms10M -Xmx10M -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:\jvm.dump
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (true) {
            list.add(new User(i++,"aaa" + UUID.randomUUID().toString()));
            new User(j--, "bbb" + UUID.randomUUID().toString());
        }
    }
}
