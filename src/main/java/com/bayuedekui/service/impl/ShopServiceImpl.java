package com.bayuedekui.service.impl;

import com.bayuedekui.dao.ShopDao;
import com.bayuedekui.dto.ShopExecution;
import com.bayuedekui.entity.Shop;
import com.bayuedekui.enums.ShopStateEnum;
import com.bayuedekui.exceptions.ShopOperationException;
import com.bayuedekui.service.ShopService;
import com.bayuedekui.util.ImageUtil;
import com.bayuedekui.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.Date;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, File shopImg) {
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }

        try {
            //给店铺信息付初始值
            shop.setEnableStatus(10);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //向库中插入数据库
            int effectedNum = shopDao.insertShop(shop);
            System.out.println(shopImg);
            System.out.println(effectedNum);
            
            if (effectedNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            } else {
               
                if (shopImg != null) {
                    //存储图片
                    try {
                        addShopImg(shop, shopImg);
                    } catch (Exception e) {
                        throw new ShopOperationException("addShopImg errror:" + e.getMessage());
                    }

                    //更新店铺图片地址
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {
                        throw new ShopOperationException("插入图片地址失败:");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("add shop error:" + e.getMessage());
        } finally {

        }
        //一切操作没有问题后返回一个审核中的装填信息

        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    private void addShopImg(Shop shop,File shopImg){
        //获取shop图片目录的相对值路径
        String dest=PathUtil.getShopImagePath(shop.getShopId());    //获取相对路径
        String shopImgAddr=ImageUtil.generateThumbnail(shopImg,dest);   //通过调用generateThumbnail方法构造出全部的存储路径
        shop.setShopImg(shopImgAddr);
    }
}
