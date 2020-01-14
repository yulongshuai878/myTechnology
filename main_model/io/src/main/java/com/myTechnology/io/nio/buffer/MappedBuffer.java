package com.myTechnology.io.nio.buffer;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-01-14 16:07
 **/
public class MappedBuffer {
    static private final int start = 0;
    static private final int size = 1024;

    static public void main( String args[] ) throws Exception {
        RandomAccessFile raf = new RandomAccessFile( "e:\\io.txt", "rw" );
        FileChannel fc = raf.getChannel();

        //把缓冲区跟文件系统进行一个映射关联
        //只要操作缓冲区里面的内容，文件内容也会跟着改变
        MappedByteBuffer mbb = fc.map( FileChannel.MapMode.READ_WRITE,start, size );

        mbb.put( 0, (byte)98 );
        mbb.put( 1023, (byte)120 );


        raf.close();
    }
}
