package com.bayuedekui.o2o.service;

import com.bayuedekui.dto.ImageHolder;
import com.bayuedekui.dto.ProductExecution;
import com.bayuedekui.entity.Product;
import com.bayuedekui.entity.ProductCategory;
import com.bayuedekui.entity.Shop;
import com.bayuedekui.enums.ProductStateEnum;
import com.bayuedekui.o2o.BaseTest;
import com.bayuedekui.service.ProductService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;

    @Test
    @Ignore
    public void setProductServiceTest() throws FileNotFoundException {
        //创建shopId=1且shopCategoryId-=1的商品实例并给其成员便来变量赋值
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(1L);
        product.setShop(shop);
        product.setShopId(1L);
        product.setProductCategory(pc);
        product.setProductCategoryId(1L);
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品描述1");
        product.setPriority(1);
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        //创建主图缩略图文件流
        File thumbnailFile = new File("D:\\EEEEEEEEEEEEEEEEEEEEEEEEEE\\o2o\\images\\2017060609084595067.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
        
        //创建两个商品详情图片流并将它们添加到详情图列表中
        File productImg1 = new File("D:\\EEEEEEEEEEEEEEEEEEEEEEEEEE\\o2o\\images\\2017060609084595067.jpg");
        InputStream is1 = new FileInputStream(productImg1);
        File productImg2=new File("D:\\EEEEEEEEEEEEEEEEEEEEEEEEEE\\o2o\\images\\2017060609084595067.jpg");
        InputStream is2=new FileInputStream(productImg2);
        

        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(productImg1.getName(),is1));
        productImgList.add(new ImageHolder(productImg2.getName(),is2));
        //添加商品并且验证（包括往productImg数据表里面添加内容）
        ProductExecution pe = productService.addProduct(product,thumbnail,productImgList);
        Assert.assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());
    }
    
    @Test
    public void testModifyProduct() throws FileNotFoundException {
        Product product = new Product();
        Shop shop=new Shop();
        shop.setShopId(1L);
        ProductCategory pc=new ProductCategory();
        pc.setProductCategoryId(1L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductId(1L);
        product.setProductName("测试修改商品名称");
        product.setProductDesc("测试修改商品描述");
        //创建缩略图文件流
        File thumbnailFile = new File("D:\\EEEEEEEEEEEEEEEEEEEEEEEEEE\\o2o\\images\\house.jpg");
        InputStream is = new FileInputStream(thumbnailFile);

        ImageHolder thumbnail=new ImageHolder(thumbnailFile.getName(),is);
        //创建两个商品详情图片流添加到商品详情图片中
        File productImg1=new File("D:\\EEEEEEEEEEEEEEEEEEEEEEEEEE\\o2o\\images\\house.jpg");
        InputStream is1 = new FileInputStream(productImg1);
        File productImg2 = new File("D:\\EEEEEEEEEEEEEEEEEEEEEEEEEE\\o2o\\images\\house.jpg");
        InputStream is2 = new FileInputStream(productImg2);
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(productImg1.getName(), is1));
        productImgList.add(new ImageHolder(productImg2.getName(), is2));
        
        //添加商品并验证
        ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
        Assert.assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());
    }
}
