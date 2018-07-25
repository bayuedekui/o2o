package com.bayuedekui.o2o.service;

import com.bayuedekui.entity.Area;
import com.bayuedekui.o2o.BaseTest;
import com.bayuedekui.service.AreaService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaServiceTest extends BaseTest{
    @Autowired
    private AreaService areaService;
    
    @Test
    public void getAreaList(){
        List<Area> areaList=areaService.queryAreaList();
        assertEquals("测试地址",areaList.get(0).getAreaName());
    }
    
}

