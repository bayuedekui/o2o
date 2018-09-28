package com.bayuedekui.o2o.dao;

import com.bayuedekui.dao.ProductImgDao;
import com.bayuedekui.entity.ProductImg;
import com.bayuedekui.o2o.BaseTest;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@FixMethodOrder
public class ProductImgDaoTest extends BaseTest {

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testABathchInsertProductImg() {
        //productId=1的两个商品添加两个详情图片记录
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片1的地址");
        productImg1.setImgDesc("图片1的描述");
        productImg1.setCreateTime(new Date());
        productImg1.setPriority(1);
        productImg1.setProductId(1L);

        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2的地址");
        productImg2.setImgDesc("图片2的描述");
        productImg2.setCreateTime(new Date());
        productImg2.setPriority(1);
        productImg2.setProductId(1L);

        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);

        int i = productImgDao.batchInsertProductImg(productImgList);
        Assert.assertEquals(2, i);

    }
    
    @Test
    public void testQueryProductImgList(){
        long productId=5L;
        List<ProductImg> productImgList=productImgDao.queryProductImgListByProductId(productId);
        Assert.assertEquals(2,productImgList.size());
        
    }
    
    @Test
    public void testDeleteProcudtImg(){
        long productId=1;
        int effNum=productImgDao.deleteProductImgByProductId(productId);
        Assert.assertEquals(1,effNum);
    }


}
