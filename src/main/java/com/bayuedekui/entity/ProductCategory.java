package com.bayuedekui.entity;

import lombok.Data;

import java.util.Date;

/**
 * 商品类目实体(商品类目id,商店id,商品类目名称,商品类目描述,优先级,创建时间,最后修改时间)
 */
@Data
public class ProductCategory {
    private Long productCategoryId;
    private Long shopId;
    private String productCategoryName;
    private String productCategoryDesc;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;

}
