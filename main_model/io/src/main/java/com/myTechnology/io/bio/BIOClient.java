package com.myTechnology.io.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-01-14 15:08
 **/
public class BIOClient {
    public static void main(String[] args) throws UnknownHostException, IOException {
        int count = 100;
        //100个线程的数据服务端不会全部接收，这个问题不管
        final CountDownLatch latch = new CountDownLatch(count);

        for(int i = 0 ; i < count; i ++){

            new Thread(){
                @Override
                public void run() {
                    try{
                        latch.await();

                        Socket client = new Socket("localhost", 8888);

                        OutputStream os = client.getOutputStream();

                        String name = UUID.randomUUID().toString();

                        os.write(name.getBytes());
                        os.close();
                        client.close();

                    }catch(Exception e){

                    }
                }

            }.start();

            latch.countDown();
        }


    }
}
