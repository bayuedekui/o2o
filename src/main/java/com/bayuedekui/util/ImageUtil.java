package com.bayuedekui.util;

import com.bayuedekui.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class ImageUtil {
    static Logger logger=LoggerFactory.getLogger(ImageUtil.class);
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random random = new Random();

    /**
     * 处理详情图，并返回新生成图片的的相对路径
     * @param thumbnail
     * @param targetAddr
     */
    public static String generateNormalImg(ImageHolder thumbnail,String targetAddr){    
        //获取随机不重复的文件名
        String realFileName=getRandomFileName();
        //获取文件的扩展名，如png,jpg等
        String extendFileName=getFileExtension(thumbnail.getImageName());
        //如果文件目录不存在就创建出来,创建根本路径加上传进来的额外路径
        makeDirPath(targetAddr);
        //获取文件的相对路径
        String relativeAddr=targetAddr+realFileName+extendFileName;
        //将获取的文件保存到指定的位置
        File dest=new File(PathUtil.getImgBasePath()+relativeAddr);
        logger.debug("current complete adddr is:"+PathUtil.getImgBasePath()+relativeAddr);
        //调用Thumbnail的jar包中的方法，为图片增加水印
        try {
            Thumbnails.of(thumbnail.getImage())
                    .size(337,640)
                    .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File("D:\\EEEEEEEEEEEEEEEEEEEEEEEEEE\\o2o\\images\\watermark.jpg")),0.25f)
                    .outputQuality(0.9f).toFile(dest);
        }catch (Exception e){
            logger.error("add waterMark error:"+e.getMessage());
            throw new RuntimeException("创建缩略图失败："+e.getMessage());
        }
        
        return PathUtil.getImgBasePath()+relativeAddr;
    }
    
    /**
     * 将上传的图片存到某个目录下,将图片路径记录到数据库中
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr){
        String realFileName=getRandomFileName();    //获取真实文件名(即具体上传文件名)
        String extension=getFileExtension(thumbnail.getImageName());   //获取上传文案后缀名(是jpg结尾还是png结尾)
        makeDirPath(targetAddr);    //创建目录路径
        String realtiveAddr=targetAddr+realFileName+extension;  //获取相对路径,加上basePath就是绝对路径
        File dest=new File(PathUtil.getImgBasePath()+realtiveAddr); //新建绝对路径的文件
        
        //调用Thumbnail.of开始为图片加上水印
        try {
            Thumbnails.of(thumbnail.getImage()).size(200,200).
                    watermark(Positions.BOTTOM_CENTER,ImageIO.read(new File("D:\\EEEEEEEEEEEEEEEEEEEEEEEEEE\\o2o\\images\\watermark.jpg")),0.25f).
                    outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
           logger.error(e.toString());
           throw new RuntimeException("创建缩略图失败："+e.toString());
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
     * @param fileName
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

    /**
     * storePath如果是文件路径还是目录路径,如果是文件路径则删除文件,如果是目录则删除该目录下的所有文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath){
        File fileOrPath=new File(PathUtil.getImgBasePath()+storePath);
        if(fileOrPath.exists()){
            if(fileOrPath.isDirectory()){
                File[] files=fileOrPath.listFiles();    //将目录下的内容统统放到File数组里
                for(int i=0;i<files.length;i++){
                    files[i].delete();
                }
            }else {
                fileOrPath.delete();
            }
        }
    }
}