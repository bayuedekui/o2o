package com.bayuedekui.service.impl;

import com.bayuedekui.dao.ProductCategoryDao;
import com.bayuedekui.dto.ProductCategoryExecution;
import com.bayuedekui.entity.ProductCategory;
import com.bayuedekui.enums.ProductCategoryStateEnum;
import com.bayuedekui.exceptions.ProductCategoryOperationException;
import com.bayuedekui.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;


    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                int effectNum = productCategoryDao.batchInsertProducctCategoryList(productCategoryList);
                if (effectNum <= 00) {
                    throw new ProductCategoryOperationException("店铺创建失败");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new ProductCategoryOperationException("bitchAddProductCategory error:"+e.getMessage());
            }
        }else{
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_lIST);
        }
    }


}
