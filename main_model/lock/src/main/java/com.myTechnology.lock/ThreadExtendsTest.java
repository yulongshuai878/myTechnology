package com.myTechnology.lock;

import org.springframework.util.StopWatch;
/**
 * @program: main_model
 * @description: 测试 synchronize
 * @author: ShiYulong
 * @create: 2020-01-19 16:48
 **/
public class ThreadExtendsTest extends Thread{

    private volatile static Integer num = 0;

    public static void print() {
        System.out.println(num);
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        for(int i=0;i<10;i++) {
            synchronized (this) {
                num+=i;
            }
             System.out.println(name + ":" + i);
        }
    }

    /**
     * i        执行时间(秒)   执行num结果
     * i<100    0.017           4500
     * i<1000    0.125           45000
     * i<10000      1.61           450000
     * i<100000      10.812           4500000
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        StopWatch clock = new StopWatch();
        clock.start();
        for (int i = 0;i<100000;i++) {
            ThreadExtendsTest thread = new ThreadExtendsTest();
            thread.start();
            thread.join();
        }
        clock.stop();
        System.out.println(clock.getTotalTimeSeconds());
        ThreadExtendsTest.print();
    }
}
