package com.bayuedekui.service.impl;

import com.bayuedekui.dao.ProductCategoryDao;
import com.bayuedekui.entity.ProductCategory;
import com.bayuedekui.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;


    @Override
    public List<ProductCategory> getProductCategory(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }
}
