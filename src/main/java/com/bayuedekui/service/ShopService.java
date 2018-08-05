package com.bayuedekui.service;

import com.bayuedekui.dto.ShopExecution;
import com.bayuedekui.entity.Shop;
import com.bayuedekui.exceptions.ShopOperationException;

import java.io.File;
import java.io.InputStream;

public interface ShopService {

    /**
     * 增加店铺信息的接口
     * @param shop
     * @param fileName
     * @return
     */
     ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName);

    /**
     * 通过店铺的id获取店铺的信息
     * @param shopId
     * @return
     */
     Shop getByShopId(long shopId);

    /**
     * 更新店铺信息,包括对图片进行处理
     * @return
     */
     ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;
}
