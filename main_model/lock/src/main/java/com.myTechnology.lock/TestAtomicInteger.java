package com.myTechnology.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: main_model
 * @description: 测试乐观锁和悲观锁
 * @author: ShiYulong
 * @create: 2020-01-19 16:40
 **/
public class TestAtomicInteger {
    //value1：线程不安全
    private static int value1 = 0;
    //value2：使用乐观锁
    private static AtomicInteger value2 = new AtomicInteger(0);
    //value3：使用悲观锁
    private  static int value3 = 0;

    private static synchronized void increaseValue3() {
        value3++;
    }

    public static void main(String[] args) throws Exception {
        //开启1000个线程，并执行自增操作
        for (int i = 0; i < 100000; ++i) {
            new Thread(()->{
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                value1++;
                value2.getAndIncrement();
                increaseValue3();
            }).start();
        }
        //查看活跃线程 ,因守护线程的原因[基于工具问题windows:idea run 启动用 >2,debug 用>1]
        while (Thread.activeCount() > 2) {
            //Thread.currentThread().getThreadGroup().list();
            Thread.yield();//让出cpu
        }

        //打印结果
        Thread.sleep(1000);
        System.out.println("线程不安全：" + value1);
        System.out.println("乐观锁(AtomicInteger)：" + value2);
        System.out.println("悲观锁(synchronized)：" + value3);
    }
}
