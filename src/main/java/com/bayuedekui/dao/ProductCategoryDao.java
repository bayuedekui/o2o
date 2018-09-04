package com.bayuedekui.dao;

import com.bayuedekui.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {
    /**
     * 根据shopId查出商品所属类别信息
     * @param shopId
     * @return
     */
     List<ProductCategory> queryProductCategoryList(@Param("shopId") Long shopId);


    /**
     * 批量增加商品分类
     * @param productCategoryList
     * @return
     */
     int batchInsertProducctCategoryList(List<ProductCategory> productCategoryList);
}
