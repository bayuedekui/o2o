package com.bayuedekui.dao;

import com.bayuedekui.entity.Area;

import java.util.List;

public interface AreaDao {
    /**
     * 列出区域列表
     * @return areaList
     */
    List<Area> queryArea();

    /**
     * 插入区域
     * @return
     */
    int insertArea(Area area);
 }
