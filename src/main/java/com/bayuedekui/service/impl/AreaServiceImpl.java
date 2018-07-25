package com.bayuedekui.service.impl;

import com.bayuedekui.dao.AreaDao;
import com.bayuedekui.entity.Area;
import com.bayuedekui.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    
    @Autowired
    private AreaDao areaDao; 
    
    @Override
    public List<Area> queryAreaList() {
        return areaDao.queryArea();
    }
}
