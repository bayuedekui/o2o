package com.bayuedekui.dao;

import com.bayuedekui.entity.Shop;
import org.apache.ibatis.annotations.Param;

public interface ShopDao {
    /**
     * 新增店铺
     * @param shop
     * @return
     */
     int insertShop(Shop shop);

    /**
     * 更新店铺
     */
    int updateShop(Shop shop);

    /**
     * 查询店铺
     * @param shopId
     * @return
     */
    Shop queryShop(Long shopId);
}
