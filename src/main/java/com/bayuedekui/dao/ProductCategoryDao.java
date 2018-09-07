package com.bayuedekui.dao;

import com.bayuedekui.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized;

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

    /**
     * 根据商品类别的id删除库中的类别记录
     * @param productCategoryId
     * @return
     */
     int deleteProductCategory(@Param("productCategoryId") Long productCategoryId,@Param("shopId") Long shopId);
}
