package com.bayuedekui.service.impl;

import com.bayuedekui.dao.ShopCategoryDao;
import com.bayuedekui.entity.ShopCategory;
import com.bayuedekui.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCategoryImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    
    @Override
    public List<ShopCategory> queryShopCategoryList(ShopCategory shopCategoryCondition) {
        return shopCategoryDao.queryShopCategory(shopCategoryCondition);
    }
}
