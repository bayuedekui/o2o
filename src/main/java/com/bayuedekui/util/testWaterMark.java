package com.bayuedekui.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class testWaterMark {
    public static void main(String[] args) throws IOException {
        String basePath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
        //采用ImageIO.read()读取文件的时候必须采用Windows的文件路径格式如"C:\\dddd\\o2o\\images\\zhengkuinew.jpg",其中\\是为了转义
        Thumbnails.of(new File("C:/dddd/o2o/images/2017060523302118864.jpg"))
                .size(200,200).watermark(Positions.BOTTOM_LEFT,ImageIO.read(new File("C:\\dddd\\o2o\\images\\watermark.jpg")),0.25f)
                .outputQuality(0.8f).toFile("C:/dddd/o2o/images/zhengkuinew.jpg");

    }
}
