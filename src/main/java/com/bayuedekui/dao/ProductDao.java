package com.bayuedekui.dao;

import com.bayuedekui.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {

    /**
     * 查询某个店铺的商品列表
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition,@Param("rowIndex")
            int rowIndex,@Param("pageSize") int pageSize);

    /**
     * 增加商品入库
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /**
     * 更新商品
     * @param product
     * @return
     */
    int updataProduct( Product product);

    /**
     * 根据商品id查询整个商品信息
     * @param productId
     * @return
     */
    Product queryProductById(long productId);

    /**
     * 查询共有多少个商品
     * @return
     */
    int queryProductCount(@Param("productCondition") Product productCondition);
    /**
     * 根据传入的producId删除数据库中的商品记录
     * @param productId
     * @return
     */
    int deleteProduct(long productId);

    /**
     * 删除商品类别之前,将商品类别id置为空
     * @param productCategory
     * @return
     */
    int updateProductCategoryToNull(long productCategory);
}
