package com.bayuedekui.o2o.dao;

import com.bayuedekui.dao.AreaDao;
import com.bayuedekui.entity.Area;
import com.bayuedekui.o2o.BaseTest;
import org.apache.ibatis.annotations.Param;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AreaTest extends BaseTest {
    
    @Autowired
    private AreaDao areaDao;
    
    @Test
    public void testQueryArea(){
        List<Area> areaList=areaDao.queryArea(); 
//        assertEquals(2,areaList.size());
        System.out.println(areaList.get(0).getPriority());
    }
    
    @Test
    public void testInsertArea(){
        Area area = new Area();
        area.setAreaName("测试tb_area2");
        area.setPriority(0);
        int effectNum=areaDao.insertArea(area);
        assertEquals(1,effectNum);
    }
}
