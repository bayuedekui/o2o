package com.bayuedekui.o2o.dao;

import com.bayuedekui.dao.ProductCategoryDao;
import com.bayuedekui.entity.ProductCategory;
import com.bayuedekui.o2o.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;


    @Test
    public void getProductCategoryTest(){
        List<ProductCategory> productCategoryList=productCategoryDao.queryProductCategoryList(1L);
        for(ProductCategory productCategory:productCategoryList){
            System.out.println("id:"+productCategory.getProductCategoryId());
            System.out.println("name:"+productCategory.getProductCategoryName());
            System.out.println("priority:"+productCategory.getPriority());

        }
    }
}
