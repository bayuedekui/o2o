package com.bayuedekui.service;

import com.bayuedekui.dto.ImageHolder;
import com.bayuedekui.dto.ProductExecution;
import com.bayuedekui.entity.Product;
import com.bayuedekui.exceptions.ProductCategoryOperationException;
import com.bayuedekui.exceptions.ProductOperationException;

import java.util.List;

public interface ProductService {

    /**
     * 添加商品信息以及图片处理
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException;

    /**
     * 根据商品的id
     * @param productId
     * @return
     */
    Product getProductById(long productId);
    
    /**
     * 修改商品的信息,商品图片以及最多六个的缩略图
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductCategoryOperationException;
}
