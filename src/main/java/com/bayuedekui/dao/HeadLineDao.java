package com.bayuedekui.dao;

import com.bayuedekui.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeadLineDao {
    /**
     * 根据传入的头条名返回HeadLine列表
     * @param headLineDaoConditio
     * @return
     */
    List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineDaoConditio);
}
