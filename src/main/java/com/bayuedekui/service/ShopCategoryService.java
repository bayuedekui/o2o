package com.bayuedekui.service;

import com.bayuedekui.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    List<ShopCategory> queryShopCategoryList(ShopCategory shopCategoryCondition);
}
