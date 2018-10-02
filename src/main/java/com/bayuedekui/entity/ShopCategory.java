package com.bayuedekui.entity;


import lombok.Data;

import java.util.Date;

/**
 * 商品类别实体(商品类别id,商品类别名,商品类别描述,商品类别图片,商品权限,创建时间,最后修改时间,商品所属类别id)
 */
@Data
public class ShopCategory {
    private Long shopCategoryId;
    private String shopCategoryName;
    private String shopCategoryDesc;
    private String shopCategoryImg;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    private Long parentId;
    private ShopCategory parent;

   
}
