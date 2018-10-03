package com.bayuedekui.service;

import com.bayuedekui.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    /**
     * 获取商品种类查询列表
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> queryShopCategoryList(ShopCategory shopCategoryCondition);
}
