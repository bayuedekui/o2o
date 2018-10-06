package com.bayuedekui.o2o.dao;

import com.bayuedekui.dao.ShopDao;
import com.bayuedekui.dto.ImageHolder;
import com.bayuedekui.dto.ShopExecution;
import com.bayuedekui.entity.Area;
import com.bayuedekui.entity.PersonInfo;
import com.bayuedekui.entity.Shop;
import com.bayuedekui.entity.ShopCategory;
import com.bayuedekui.exceptions.ShopOperationException;
import com.bayuedekui.o2o.BaseTest;
import com.bayuedekui.service.ShopService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private ShopService shopService;
    
    @Test
    public void testQueryShopList(){
        Shop shopCondition=new Shop();
        PersonInfo owner=new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        List<Shop> shopList=shopDao.queryShopList(shopCondition,0,5);
        System.out.println("店铺列表的大小为:"+shopList.size());
    }

    @Test
    @Ignore
    public void testModifyShop() throws ShopOperationException,FileNotFoundException {
        Shop shop=new Shop();
        shop.setShopId(1L);
        shop.setShopName("0805修改后端而店铺的名字");
        File shopImg=new File("C:\\dddd\\o2o\\images\\dabai.jpg");
        InputStream is=new FileInputStream(shopImg);
        ShopExecution shopExecution=shopService.modifyShop(shop,new ImageHolder("dabai.jpg",is));
        System.out.println("新的图片的地址为:"+shopExecution.getShop().getShopImg());
    }
    
    @Test
    @Ignore
    public void testQueryShopId(){
        long shopId=1;
        Shop shop=shopDao.queryByShopId(shopId);
        System.out.println(shop.getArea().getAreaName());
        System.out.println(shop.getArea().getAreaId());
    }
    
    
    @Test
    @Ignore
    public void testInsertShop() {
        Shop shop = new Shop();
        shop.setOwnerId(1L);
        Area area = new Area();
        area.setAreaId(1L);
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(1L);
        shop.setShopName("mytest1");
        shop.setShopDesc("mytest1");
        shop.setShopAddr("testaddr1");
        shop.setPhone("13810524526");
//        shop.setShopImg("test1");
        shop.setLongitude(1D);
        shop.setLatitude(1D);
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(0);
        shop.setAdvice("审核中");
        shop.setArea(area);
        shop.setShopCategory(sc);
        shopDao.insertShop(shop);
        
        shop.setShopImg("0721");

        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1, effectedNum);
    }
    
    @Test
    public void testUpdateShop(){
        Shop shop = new Shop();
       
       
//        shop.setShopId(47L);
        shop.setShopImg("0721");
       
        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1, effectedNum);
    }
    
    @Test
    public void testQueryShopListAndCount(){
        Shop shopCondition=new Shop();
        ShopCategory childCategory=new ShopCategory();
        ShopCategory parentCategory=new ShopCategory();
        parentCategory.setShopCategoryId(7L);
        childCategory.setParent(parentCategory);
        shopCondition.setShopCategory(childCategory);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 10);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("查出来的商品列表数:"+shopList.size());
        System.out.println("店铺总数:"+count);
    }
}
