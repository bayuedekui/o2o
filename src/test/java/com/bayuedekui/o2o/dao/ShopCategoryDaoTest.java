package com.bayuedekui.o2o.dao;

import com.bayuedekui.dao.ShopCategoryDao;
import com.bayuedekui.entity.ShopCategory;
import com.bayuedekui.o2o.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    
    @Test
    public void testQueryCategory(){
        List<ShopCategory> shopCategoryList=shopCategoryDao.queryShopCategory(new ShopCategory());
        ShopCategory testCategory=new ShopCategory();
        ShopCategory parentCategory=new ShopCategory();
        parentCategory.setShopCategoryId(1L);
        testCategory.setParent(parentCategory);
        List<ShopCategory> shopCategoryList1=shopCategoryDao.queryShopCategory(testCategory);
        assertEquals(1,shopCategoryList1.size());
        System.out.println(shopCategoryList1.get(0).getShopCategoryName());
    }
    
    @Test
    public void testQueryCategory_parentIdIsNull(){
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(null);
        System.out.println(shopCategoryList.size());
    }
}
