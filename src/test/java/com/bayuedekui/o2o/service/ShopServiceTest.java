package com.bayuedekui.o2o.service;

import com.bayuedekui.dao.ShopDao;
import com.bayuedekui.dto.ShopExecution;
import com.bayuedekui.entity.Area;
import com.bayuedekui.entity.Shop;
import com.bayuedekui.entity.ShopCategory;
import com.bayuedekui.enums.ShopStateEnum;
import com.bayuedekui.o2o.BaseTest;
import com.bayuedekui.service.AreaService;
import com.bayuedekui.service.ShopService;
import com.dyuproject.protostuff.LimitedInputStream;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {
    //刚开始问题出在shopService注入不进来
    @Autowired
    private ShopService shopService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopDao shopDao;
    
    @Test
    public void testGetShopList(){
        Shop shop=new Shop();
        ShopCategory sc=new ShopCategory();
    }
    
    
    
    @Test
    @Ignore
    public void testShopService() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setOwnerId(1L);
        Area area = new Area();
        area.setAreaId(1L);
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(1L);
        shop.setShopName("mytest3");
        shop.setShopDesc("mytest3");
        shop.setShopAddr("testaddr3");
        shop.setPhone("13810524526");
        
        shop.setLongitude(1D);
        shop.setLatitude(1D);
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        shop.setArea(area);
        shop.setShopCategory(sc);
        File shopImg=new File("C:\\dddd\\o2o\\images\\2017060523302118864.jpg");
        InputStream is=new FileInputStream(shopImg);
        ShopExecution se = shopService.addShop(shop, is,shopImg.getName());
        assertEquals(ShopStateEnum.CHECK.getState(),se.getState());
//        shopDao.insertShop(shop);
        
//        shop.setShopImg("0722");
//        shopDao.updateShop(shop);
    }
    
  
}
