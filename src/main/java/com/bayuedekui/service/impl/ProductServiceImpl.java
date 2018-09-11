package com.bayuedekui.service.impl;

import com.bayuedekui.dao.ProductDao;
import com.bayuedekui.dto.ImageHolder;
import com.bayuedekui.dto.ProductExcution;
import com.bayuedekui.entity.Product;
import com.bayuedekui.entity.ProductImg;
import com.bayuedekui.enums.ProductStateEnum;
import com.bayuedekui.exceptions.ProductOperationException;
import com.bayuedekui.service.ProductService;
import com.bayuedekui.util.ImageUtil;
import com.bayuedekui.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImg productImg;


    /**
     * 调用dao层增加商品,包括上传商品图片
     *
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductOperationException
     */
    @Override
    @Transactional
    public ProductExcution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException {
        //1.处理缩略图,获取缩略图的相对路径并赋值给product
        //2.往tb_product表中写入商品信息,并获取product_id
        //3.结合product_id批量处理商品详情图
        //4.将商品详情图列表批量插入tb_product_img中
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            //给商品设置上默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            //默认上架状态
            product.setEnableStatus(1);
            //如果上传商品缩略图不为空则添加
            if (null != thumbnail) {
                addThumbnail(product, thumbnail);
            }

            //开始创建商品信息
            try {
                int effNum = productDao.insertProduct(product);
                if (effNum <= 0) {
                    throw new ProductOperationException("商品创建失败:");
                }
            } catch (Exception e) {
                throw new ProductOperationException("商品创建失败:" + e.getMessage());
            }

            //若商品详情图不为空则添加
            if (null != productImgHolderList && productImgHolderList.size() > 0) {
                addProductImgList(product, productImgHolderList);

            }

            return new ProductExcution(ProductStateEnum.SUCCESS, product);
        } else {
            return new ProductExcution(ProductStateEnum.EMPTY);
        }
    }


    /**
     * 添加缩略图
     *
     * @param product
     * @param thumbnail
     */
    public void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }

    /**
     * 向Ttb_product_image表中添加缩略图数据
     *
     * @param product
     * @param productImgHolderList
     */
    public void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
        //获取图片存储的相对路径,这里直接放放到店铺的文件夹底下
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());

        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        //遍历图片一次去处理,并添加到ProductImg实体类里面
        for (ImageHolder productImgHolder : productImgHolderList) {
            

        }

    }


}
