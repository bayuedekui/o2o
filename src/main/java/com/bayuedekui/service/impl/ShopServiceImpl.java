package com.bayuedekui.service.impl;

import com.bayuedekui.dao.ShopDao;
import com.bayuedekui.dto.ImageHolder;
import com.bayuedekui.dto.ShopExecution;
import com.bayuedekui.entity.Shop;
import com.bayuedekui.enums.ShopStateEnum;
import com.bayuedekui.exceptions.ShopOperationException;
import com.bayuedekui.service.ShopService;
import com.bayuedekui.util.ImageUtil;
import com.bayuedekui.util.PageCalculator;
import com.bayuedekui.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    /**
     * 获取shopList
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex=PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Shop> shopList=shopDao.queryShopList(shopCondition,pageIndex,pageSize);
        int count=shopDao.queryShopCount(shopCondition);
        ShopExecution se=new ShopExecution();
        if(shopList!=null ){
            se.setShopList(shopList);            
            se.setCount(count);
        }else{
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    /**
     * 向数据库中增加店铺信息(注册店铺)
     * @param shop
     * @param thumbnail
     * @return
     */
    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {
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
            System.out.println(thumbnail.getImage());
            System.out.println(effectedNum);

            if (effectedNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            } else {

                if (thumbnail.getImage() != null) {
                    //存储图片
                    try {
                        addShopImg(shop, thumbnail);
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
     * @param thumbnail
     */
    private void addShopImg(Shop shop,ImageHolder thumbnail) {
        //获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());    //获取相对路径
        String shopImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);   //通过调用generateThumbnail方法构造出全部的存储路径,其中的方法里面还包括给图片加上水印
        shop.setShopImg(shopImgAddr);
    }

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
        if (shop == null && shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        //1.判断是否需要处理图片
        try {
            if (thumbnail.getImage() != null && thumbnail.getImageName() != null && !thumbnail.getImageName().equals("")) {
                Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                if (tempShop.getShopImg() != null) {
                    ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                }
                addShopImg(shop, thumbnail);
            }
            //2.更新店铺信息
            shop.setLastEditTime(new Date());
            int effectNum = shopDao.updateShop(shop);
            if (effectNum <= 0) {
                return new ShopExecution(ShopStateEnum.INNER_ERROR);
            } else {
                shop = shopDao.queryByShopId(shop.getShopId());
                return new ShopExecution(ShopStateEnum.SUCCESS, shop);
            }
        }catch(Exception e){
            throw new ShopOperationException("modifyError:"+e.getMessage());
        }
    }
}
