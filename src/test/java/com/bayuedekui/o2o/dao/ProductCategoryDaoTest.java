package com.bayuedekui.o2o.dao;

import com.bayuedekui.dao.ProductCategoryDao;
import com.bayuedekui.entity.ProductCategory;
import com.bayuedekui.o2o.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Date;
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


    @Test
    public void test_batchInsertProductCateggory(){
        ProductCategory productCategory=new ProductCategory();
        productCategory.setProductCategoryName("测试商品类别1");
        productCategory.setPriority(3);
        productCategory.setCreateTime(new Date());
        productCategory.setShopId(1L);

        ProductCategory productCategory1=new ProductCategory();
        productCategory1.setProductCategoryName("测试商品类别2");
        productCategory1.setPriority(3);
        productCategory1.setCreateTime(new Date());
        productCategory1.setShopId(1L);

        ProductCategory productCategory2=new ProductCategory();
        productCategory2.setProductCategoryName("测试商品类别3");
        productCategory2.setPriority(3);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(1L);

        List<ProductCategory> productCategoryList=new ArrayList<ProductCategory>();
        productCategoryList.add(productCategory);
        productCategoryList.add(productCategory1);
        productCategoryList.add(productCategory2);

        int effectNum=productCategoryDao.batchInsertProducctCategoryList(productCategoryList);
        Assert.assertEquals(3,effectNum);
    }




















}
