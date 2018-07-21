package com.bayuedekui.service;

import com.bayuedekui.dto.ShopExecution;
import com.bayuedekui.entity.Shop;

import java.io.File;

public interface ShopService {

    /**
     * 增加店铺信息的接口
     * @param shop
     * @param shopImg
     * @return
     */
     ShopExecution addShop(Shop shop, File shopImg);
}
