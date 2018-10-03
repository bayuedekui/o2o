package com.bayuedekui.service.impl;

import com.bayuedekui.dao.HeadLineDao;
import com.bayuedekui.dao.ShopCategoryDao;
import com.bayuedekui.entity.HeadLine;
import com.bayuedekui.entity.Shop;
import com.bayuedekui.entity.ShopCategory;
import com.bayuedekui.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;


    /**
     * 查询HeadLine列表
     * @param headLineCondition
     * @return
     * @throws IOException
     */
    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
        return headLineDao.queryHeadLine(headLineCondition);
    }
}
