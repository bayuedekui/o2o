package com.bayuedekui.o2o.util;

import com.bayuedekui.util.FileUtil;
import org.junit.Test;

public class FileUtilTest {
    
    @Test
    public void testFileUtil(){
        FileUtil fileUtil=new FileUtil();
        fileUtil.getImgBasePath();
        System.out.println(fileUtil.getRandomFileName());
    }
}
