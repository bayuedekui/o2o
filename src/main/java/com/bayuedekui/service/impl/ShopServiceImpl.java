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

import java.io.File;
import java.io.InputStream;
import java.util.Date;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) {
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
            System.out.println(shopImgInputStream);
            System.out.println(effectedNum);

            if (effectedNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            } else {

                if (shopImgInputStream != null) {
                    //存储图片
                    try {
                        addShopImg(shop, shopImgInputStream, fileName);
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

        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    /**
     * 将上传的图片的路径设置好,并将路径存入shop表中的字段中去
     * @param shop
     * @param shopImgInputStream
     * @param fileName
     */
    private void addShopImg(Shop shop, InputStream shopImgInputStream, String fileName) {
        //获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());    //获取相对路径
        String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream, fileName, dest);   //通过调用generateThumbnail方法构造出全部的存储路径,其中的方法里面还包括给图片加上水印
        shop.setShopImg(shopImgAddr);
    }

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryShop(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException {
        if (shop == null && shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        //1.判断是否需要处理图片
        try {
            if (shopImgInputStream != null && fileName != null && !fileName.equals("")) {
                Shop tempShop = shopDao.queryShop(shop.getShopId());
                if (tempShop.getShopImg() != null) {
                    ImageUtil.deleteFilePath(tempShop.getShopImg());
                }
                addShopImg(shop, shopImgInputStream, fileName);
            }
            //2.跟新店铺信息
            shop.setLastEditTime(new Date());
            int effectNum = shopDao.updateShop(shop);
            if (effectNum <= 0) {
                return new ShopExecution(ShopStateEnum.INNER_ERROR);
            } else {
                shop = shopDao.queryShop(shop.getShopId());
                return new ShopExecution(ShopStateEnum.SUCCESS, shop);
            }
        }catch(Exception e){
            throw new ShopOperationException("modifyError:"+e.getMessage());
        }
    }
}
