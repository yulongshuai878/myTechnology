package com.myTechnology.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-01-14 15:15
 **/
public class BIOServer {
    ServerSocket serverSocket;

    /**
     * 服务器
     * @param port
     */
    public BIOServer(int port){
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("BIO服务已启动，端口为："+port);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启监听，并处理逻辑
     * @throws IOException
     */
    public void listener() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            InputStream is = socket.getInputStream();
            byte[] buff = new byte[1024];
            int len = is.read(buff);
            if (len > 0 ) {
                String msg = new String(buff,0,len);
                System.out.println("收到：" + msg );
            }

        }
    }

    public static void main(String[] args) throws IOException {
        new BIOServer(8888).listener();
    }

}
