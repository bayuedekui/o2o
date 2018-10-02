package com.bayuedekui.entity;


import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 商品实体(商品id,商品名称,商品描述,商品图片地址,正常价格,打折后价格,优先级,创建时间,最后修改时间,能用状态,指针)
 */
@Data
public class Product {
    private Long productId;
    private String productName;
    private String productDesc;
    private String imgAddr;// 简略图
    private String normalPrice;
    private String promotionPrice;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    //0.下架,1.在前端显示系统显示
    private Integer enableStatus;
    private Integer point;
    private Long productCategoryId;
    private Long shopId;

    private List<ProductImg> productImgList;
    private ProductCategory productCategory;
    private Shop shop;

}
