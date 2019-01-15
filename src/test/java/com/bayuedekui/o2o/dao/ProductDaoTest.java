package com.bayuedekui.o2o.dao;

import com.bayuedekui.dao.ProductDao;
import com.bayuedekui.dao.ProductImgDao;
import com.bayuedekui.entity.Product;
import com.bayuedekui.entity.ProductCategory;
import com.bayuedekui.entity.ProductImg;
import com.bayuedekui.entity.Shop;
import com.bayuedekui.o2o.BaseTest;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDaoTest extends BaseTest {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testAInsertProduct() {
        Shop shop1=new Shop();
        shop1.setShopId(1L);
        ProductCategory pc1=new ProductCategory();
        pc1.setProductCategoryId(1L);
        
        Product product1 = new Product();
        product1.setProductName("0115测试商品1");
        product1.setProductDesc("0115测试商品描述1");
        product1.setImgAddr("0115");
        product1.setPriority(1);
        product1.setEnableStatus(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShopId(1L);
        product1.setShop(shop1);
        product1.setProductCategory(pc1);

        Product product2 = new Product();
        product2.setProductName("测试商品2");
        product2.setProductDesc("测试商品描述2");
        product2.setPriority(2);
        product2.setEnableStatus(0);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setShopId(1L);
        product2.setShop(shop1);
        product2.setProductCategory(pc1);

        int effectNum1 = productDao.insertProduct(product1);
        int effectNum2 = productDao.insertProduct(product2);
        Assert.assertEquals(1, effectNum1);
        Assert.assertEquals(1, effectNum2);

    }

    @Test
    public void testQueryProduct() {
        long productId = 1;
        //初始化两个商品详情图实例作为productId为1的商品下的详情图片
        // 批量插入到商品详情图中
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片地址1");
        productImg1.setImgDesc("测试图片描述1");
        productImg1.setPriority(10);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(productId);

        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片测试地址2");
        productImg2.setImgDesc("测试图片描述2");
        productImg2.setPriority(11);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(productId);

        List<ProductImg> productImgList = new ArrayList<>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);

        int effectNum = productImgDao.batchInsertProductImg(productImgList);
        Assert.assertEquals(2, effectNum);

        //查询productId为1的商品信息并校验返回的详情图实例列表的size
        Product product = productDao.queryProductById(productId);
        Assert.assertEquals(2, product.getProductImgList().size());
        System.out.println("详情图的列表大小为:" + product.getProductImgList().size());

        //删除两个新增的商品详情图实例
        effectNum = productImgDao.deleteProductImgByProductId(productId);
        Assert.assertEquals(2, effectNum);
        System.out.println(effectNum);

    }

    @Test
    @Ignore
    public void testUpdateProduct() {
        Product product = new Product();
        Shop shop = new Shop();

        shop.setShopId(1L);
        product.setShop(shop);
        product.setShopId(1L);
        product.setProductCategoryId(1L);
        product.setProductId(2L);
        product.setProductName("第二个产品");
        int effectNum = productDao.updataProduct(product);
        Assert.assertEquals(1, effectNum);
    }

    @Test
    public void testBQueryProductList() {
        Product productCondition = new Product();
        //分页查询,预计查出5条结果
        List<Product> productList = productDao.queryProductList(productCondition, 0, 10);
        Assert.assertEquals(5, productList.size());

        //使用商品名称模糊查询,以及返回三条结果
        productCondition.setProductName("测试");
        productList = productDao.queryProductList(productCondition, 0, 3);
//        System.out.println("根据名称模糊查询的结果数为:"+productList.size());
        Assert.assertEquals(2,productList.size());

    }
    
    @Test
    public void testCQueryProductCount(){
        Product productCondition=new Product();
        productCondition.setProductName("测试");
        int count = productDao.queryProductCount(productCondition);
        Assert.assertEquals(2,count);
    }

    @Test
    public void testUpdateProductCategoryToNull(){
        int effectNum=productDao.updateProductCategoryToNull(3L);
        Assert.assertEquals(1,effectNum);
        
    }
}
