package com.bayuedekui.o2o.dao;

import com.bayuedekui.dao.ProductDao;
import com.bayuedekui.entity.Product;
import com.bayuedekui.o2o.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class ProductDaoTest extends BaseTest {
    @Autowired
    private ProductDao productDao;

    @Test
    public void test_productDao(){
        Product product1=new Product();
        product1.setProductName("测试商品1");
        product1.setProductDesc("测试商品描述1");
        product1.setCreateTime(new Date());
        product1.setProductDesc("测试商品描述1");
        product1.setPriority(1);
        product1.setEnableStatus(1);
        product1.setProductCategoryId(1);
        product1.setShopId(1);

        Product product2=new Product();
        product2.setProductName("测试商品2");
        product2.setProductDesc("测试商品描述2");
        product2.setCreateTime(new Date());
        product2.setProductDesc("测试商品描述2");
        product2.setPriority(1);
        product2.setEnableStatus(1);
        product2.setProductCategoryId(1);
        product2.setShopId(1);

        int effectNum1=productDao.insertProduct(product1);
        int effectNum2=productDao.insertProduct(product2);
        Assert.assertEquals(1,effectNum1);
        Assert.assertEquals(1,effectNum2);

    }


}
