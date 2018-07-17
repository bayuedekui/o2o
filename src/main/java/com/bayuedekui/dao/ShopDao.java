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
     * 
     */
    int updateShop();
}
