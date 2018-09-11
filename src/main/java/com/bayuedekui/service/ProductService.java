package com.bayuedekui.service;

import com.bayuedekui.dto.ImageHolder;
import com.bayuedekui.dto.ProductExcution;
import com.bayuedekui.entity.Product;
import com.bayuedekui.exceptions.ProductOperationException;

import java.io.InputStream;
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
    ProductExcution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException;
}
