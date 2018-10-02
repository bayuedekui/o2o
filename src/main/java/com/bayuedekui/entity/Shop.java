package com.bayuedekui.entity;


import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * 店铺实体(店铺id,拥有者id,店铺类别id,店铺名,店铺描述,店铺地址,店铺电话,店铺图片,店铺经度,店铺权限,店铺创建时间,店铺最后修改时间,店铺能使用状态,价格//货架列表,店铺区域,店铺类别,店铺所属类别)
 */
@Data
public class Shop {
    private Long shopId;
    private Long ownerId;
    private Long areaId;
    private Long shopCategoryId;
    private String shopName;
    private String shopDesc;
    private String shopAddr;
    private String phone;
    private String shopImg;
    private Double longitude;
    private Double latitude;
    private Integer priority;
    private Date createTime;
    private Date  lastEditTime;
    private Integer enableStatus;
    private String advice;
    
    private Area area;
    private PersonInfo owner;
    private List<ShopAuthMap> staffList;
    private ShopCategory shopCategory;
    private ShopCategory parentCategory;
    
}
