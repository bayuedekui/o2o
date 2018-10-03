package com.bayuedekui.o2o.dao;

import com.bayuedekui.dao.HeadLineDao;
import com.bayuedekui.entity.HeadLine;
import com.bayuedekui.o2o.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HeadLineTest extends BaseTest {
    @Autowired
    private HeadLineDao headLineDao;
    
    @Test
    public void testQueryHeadLine(){
        List<HeadLine> headLineDaoList = headLineDao.queryHeadLine(new HeadLine());
        Assert.assertEquals(1,headLineDaoList.size());
    }
}
