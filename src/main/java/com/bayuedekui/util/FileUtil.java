package com.bayuedekui.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 获取图片/文件基本路径类,基本都是静态的方法
 */
public class FileUtil {
    private static String separator=System.getProperty("file.separator");   //获得当前系统的文件的分隔符(unix中是'/',window中是'\')
    private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");   //格式化parse方法是格式化成日期对象,format是格式化成string类型
    private static final Random random=new Random();
    
    public static String getImgBasePath(){
        String os=System.getProperty("os.name");    //获取操作系统的名称
//        System.out.println(os);
        String basePath="";
        if(os.toLowerCase().startsWith("win")){
            basePath="C:/dddd/image";   //windows系统
        }else{
            basePath="home/o2oimg";     //unix系统
        }
        basePath=basePath.replace("/",separator);   //根据系统的文件格式替换文件分割符
        return basePath;
    }

    /**
     * 生成随机文件名,当前年月日时分秒毫秒+五位随机数(为了在实际项目中防止文件同名而进行的处理)
     */
    public static String getRandomFileName(){
        int rannum=random.nextInt(10000)+10000;
        String nowTimeStr=sdf.format(new Date());   //format格式化成字符串,parse格式化成日期对象
        return nowTimeStr+rannum;
    }

    /**
     * 返回顶头图片的存储位置
     * @return
     */
    public static String getHeadLineImgPath(){
        String headLineImgPath="upload/images/item/headttitle";
        headLineImgPath=headLineImgPath.replace("/",separator);
        return headLineImgPath;
    }

    /**
     * 返回shopCategory的上传的照片路径
     * @return
     */
    public static String getShopCategoryImgPath(){
        String shopCategoryImgPath="upload/image/item/shopcategory";
        shopCategoryImgPath=shopCategoryImgPath.replace("/",separator);
        return shopCategoryImgPath;
    }

    /**
     * 返回personinfo用户信息上传的图片(主要是头像)路径
     * @return
     */
    public static String getPersonInfoImgPath(){
        String personInfoImgPath="upload/image/item/personinfo";
        personInfoImgPath=personInfoImgPath.replace("/",separator);
        return personInfoImgPath;
    }

    /**
     * 删除当前的文件(为了防止重复)
     * @param storePath
     */
    public static void deleteFile(String storePath){
        File file=new File(getImgBasePath()+storePath);
        if(file.exists()){
            if(file.isDirectory()){
                File[] files=file.listFiles();
                for(int i=0;i<files.length;i++){
                    files[i].delete();
                }
            }
            file.delete();
        }
        
    }
}
