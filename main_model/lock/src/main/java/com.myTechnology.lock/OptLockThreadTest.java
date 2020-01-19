package com.myTechnology.lock;

import org.springframework.util.StopWatch;

import java.util.Date;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @program: main_model
 * @description: 测试乐观锁
 * @author: ShiYulong
 * @create: 2020-01-19 16:52
 **/
public class OptLockThreadTest extends Thread {
    private volatile static AtomicStampedReference<Integer> sum = new AtomicStampedReference(0,new Date().toInstant().getNano());

    @Override
    public void run() {
         String name = Thread.currentThread().getName();
        for(int i=0;i<10;i++) {
            Integer m = sum.getReference();
            m+=i;
            boolean is = sum.compareAndSet(sum.getReference(),m,sum.getStamp(),new Date().toInstant().getNano());
            if (!is) {
                System.out.println("叠加失败");
            }
             System.out.println(name + ":" + i);
        }
    }

    /**
     * i        执行时间(秒)   执行num结果
     * i<100    0.022           4500
     * i<1000    0.137           45000
     * i<10000    1.378           450000
     * i<100000    10.638           4500000
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        StopWatch clock = new StopWatch();
        clock.start();
        for (int i = 0;i<100000;i++) {
            OptLockThreadTest thread = new OptLockThreadTest();
            thread.start();
            thread.join();
        }
        clock.stop();
        System.out.println(clock.getTotalTimeSeconds());
        System.out.println(sum.getReference());
    }
}
