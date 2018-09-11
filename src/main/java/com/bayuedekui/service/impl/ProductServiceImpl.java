package com.bayuedekui.service.impl;

import com.bayuedekui.dao.ProductDao;
import com.bayuedekui.dto.ImageHolder;
import com.bayuedekui.dto.ProductExcution;
import com.bayuedekui.entity.Product;
import com.bayuedekui.exceptions.ProductOperationException;
import com.bayuedekui.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;



    @Override
    public ProductExcution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException {
        return null;
    }
}
