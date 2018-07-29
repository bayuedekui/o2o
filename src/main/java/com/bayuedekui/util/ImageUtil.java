package com.bayuedekui.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class ImageUtil {
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random random = new Random();

    /**
     * 将上传的图片存起来,将图片路径记录到数据库中
     * @param thumbnailInputStream
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(InputStream thumbnailInputStream, String fileName, String targetAddr){
        String realFileName=getRandomFileName();    //获取真实文件名(即具体上传文件名)
        String extension=getFileExtension(fileName);   //获取上传文案后缀名(是jpg结尾还是png结尾)
        makeDirPath(targetAddr);    //创建目录路径
        String realtiveAddr=targetAddr+realFileName+extension;  //获取相对路径,加上basePath就是绝对路径
        File dest=new File(PathUtil.getImgBasePath()+realtiveAddr); //新建绝对路径的文件
        
        //下面开始为图片加上水印
        try {
            Thumbnails.of(thumbnailInputStream).size(200,200).
                    watermark(Positions.BOTTOM_LEFT,ImageIO.read(new File("C:\\dddd\\o2o\\images\\watermark.jpg")),0.25f).
                    outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        
        return PathUtil.getImgBasePath()+realtiveAddr;
    }

    /**
     * 获取随机的文件名
     * @return
     */
    public static String  getRandomFileName(){
        //真是文件的名字喂年月日十分秒+五位随机数
        int randomNum=random.nextInt(89999)+10000;
         String realFileName=simpleDateFormat.format(new Date());   
           return realFileName+randomNum; 
    }

    /**
     * 获取上传的文件的拓展名(jpg/png)
     * @param cFile
     * @return
     */
    public static String getFileExtension(String  fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 
     */
    public static void makeDirPath(String targetAddr){
        String realFileParentPath=PathUtil.getImgBasePath()+targetAddr;
        File dirPath=new File(realFileParentPath);
        if(!dirPath.exists()){   //如果对应的目录不存在,则创建
            dirPath.mkdirs();       //将一路的文件夹都创建出来
        }
        
    }
}