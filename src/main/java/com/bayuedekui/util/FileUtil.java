package com.bayuedekui.util;

import java.text.SimpleDateFormat;
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
        String basePath="";
        if(os.toLowerCase().startsWith("win")){
            basePath="C:/dddd/image";   //windows系统
        }else{
            basePath="home/o2oimg";     //unix系统
        }
        basePath=basePath.replace("/",separator);   //根据系统的文件格式替换文件分割符
        return basePath;
    }
    
}
