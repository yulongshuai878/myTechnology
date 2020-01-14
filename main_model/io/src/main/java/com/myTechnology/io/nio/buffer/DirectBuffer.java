package com.myTechnology.io.nio.buffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @program: main_model
 * @description:直接缓冲区
 * @author: ShiYulong
 * @create: 2020-01-14 16:04
 **/
public class DirectBuffer {
    static public void main( String args[] ) throws Exception {

        //首先我们从磁盘上读取刚才我们写出的文件内容
        String infile = "E:/io.txt";
        FileInputStream fin = new FileInputStream( infile );
        FileChannel fcin = fin.getChannel();

        //把刚刚读取的内容写入到一个新的文件中
        String outfile = String.format("E:/iocopy.txt");
        FileOutputStream fout = new FileOutputStream( outfile );
        FileChannel fcout = fout.getChannel();

        // 使用allocateDirect（方法直接调用底层C语言方法），而不是allocate
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        while (true) {
            buffer.clear();

            int r = fcin.read(buffer);

            if (r==-1) {
                break;
            }

            buffer.flip();

            fcout.write(buffer);
        }
    }
}
