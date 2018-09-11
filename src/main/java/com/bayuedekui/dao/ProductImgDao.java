package com.bayuedekui.dao;

import com.bayuedekui.entity.ProductImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductImgDao {
    /**
     * 批量添加商品图片
     * @param productImgList
     * @return
     */
    //其中却不能加注解@Param("productImgList"),加了之后包list什么的非参数,可以暂时理解成批量插入时
    //没有用到@Param("")中设置的代替量,当是一个单独的变量的时候可以用注解,是一个对象就会出现代替不了的情况
    int batchInsertProductImg( List<ProductImg> productImgList);
}
