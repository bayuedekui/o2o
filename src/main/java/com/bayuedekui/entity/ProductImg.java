package com.bayuedekui.entity;


import lombok.Data;

import java.util.Date;

/**
 * 商品图片实体(商品图片id,图片路径,图片描述,优先级,创建时间,商品id)
 */
@Data
public class ProductImg {
    private Long productImgId;
    private String imgAddr;
    private String imgDesc;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    private Long productId;

    private Product product;

   
}
