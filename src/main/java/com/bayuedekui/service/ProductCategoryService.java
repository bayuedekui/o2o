package com.bayuedekui.service;

import com.bayuedekui.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryService {

    /**
     * 调用dao层,获取商品种类列表
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(@Param("shopId") long shopId);

}
