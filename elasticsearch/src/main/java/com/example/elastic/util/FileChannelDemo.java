package com.example.elastic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo {

    public static void main(String[] args) {
        Long s = System.currentTimeMillis();
        File file = new File("E:\\git\\jdk1.8\\jdk8新特性.mp4");
        File out1 = new File("C:\\Users\\zengxin\\Desktop\\jdk8新特性.mp4");
        try {
            FileChannel channelIn = new FileInputStream(file).getChannel();
            FileChannel channelOut = new FileOutputStream(out1).getChannel();
            //
//            channelIn.transferTo(0,channelIn.size(),channelOut);
            ByteBuffer buffer = ByteBuffer.allocate(10240);
            while(channelIn.read(buffer)!=-1){
                buffer.flip();//表示要准备写了，将limit定位到position，将position设置为0
                channelOut.write(buffer);
                buffer.compact();//将position跳过写过的部分，limit设置为capacity的大小
            }
            long e = System.currentTimeMillis();
            System.out.println("channel:"+(e-s));
            channelOut.close();
            channelIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
