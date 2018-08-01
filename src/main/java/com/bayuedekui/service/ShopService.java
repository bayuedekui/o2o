package com.bayuedekui.service;

import com.bayuedekui.dto.ShopExecution;
import com.bayuedekui.entity.Shop;

import java.io.File;
import java.io.InputStream;

public interface ShopService {

    /**
     * 增加店铺信息的接口
     * @param shop
     * @param shopImg
     * @return
     */
     ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName);
}
