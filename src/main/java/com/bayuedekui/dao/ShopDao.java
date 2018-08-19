package com.bayuedekui.dao;

import com.bayuedekui.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {

    /**
     * 分页查询店铺列表,可输入的条件有:店铺名(模糊查询),店铺状态,店铺类别,店铺类别,区域ID,owner
     * @param shopCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
                             @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 查询当前所有的店铺个数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);
    
    
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
     * 根据shopId查询店铺
     * @param shopId
     * @return
     */
    Shop queryByShopId(Long shopId);
}
