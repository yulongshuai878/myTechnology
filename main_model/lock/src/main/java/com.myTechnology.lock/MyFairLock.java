package com.myTechnology.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: main_model
 * @description:测试公平锁
 * @author: ShiYulong
 * @create: 2020-01-19 14:43
 **/
public class MyFairLock {
    /**
     *     true 表示 ReentrantLock 的公平锁
     */
    private  ReentrantLock lock = new ReentrantLock(true);

    public   void testFail(){
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() +"获得了锁");
        }finally {
            lock.unlock();
        }
    }
    public static void main(String[] args) {
        MyFairLock fairLock = new MyFairLock();
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName()+"启动");
            fairLock.testFail();
        };
        Thread[] threadArray = new Thread[10];
        for (int i=0; i<10; i++) {
            threadArray[i] = new Thread(runnable);
        }
        for (int i=0; i<10; i++) {
            threadArray[i].start();
        }
    }
}
