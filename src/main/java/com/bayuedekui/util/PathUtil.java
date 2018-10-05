package com.bayuedekui.util;

public class PathUtil {
    private static String separator=System.getProperty("file.separator");
    
    public static String getImgBasePath(){
        String os=System.getProperty("os.name");
        String basePath="";
        if(os.toLowerCase().startsWith("win")){
            basePath="D:/EEEEEEEEEEEEEEEEEEEEEEEEEE/o2o/images";
        }else{
            basePath="/home/o2o/images";
        }
        basePath=basePath.replace("/",separator);
        
        return basePath;
    }
    
    public static String getShopImagePath(Long shopId){
        String imagePath="/upload/item/shop"+shopId+"/";
        return imagePath.replace("/",separator);
    }
}
