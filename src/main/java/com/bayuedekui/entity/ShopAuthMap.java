package com.bayuedekui.entity;


import lombok.Data;

import java.util.Date;


/**
 * 商铺认证map实体(商点认证信息id,雇员id,商店id(外键),商铺认证的名字,标题,标题标记,是否能用状态,创建时间,最后修改时间,店铺雇员信息,店铺) 
 */
@Data
public class ShopAuthMap {
    private Long shopAuthId;
    private Long employeeId;
    private Long shopId;
    private String name;
    private String title;
    private Integer titleFlag;
    private Integer enableStatus;
    private Date createTime;
    private Date lastEditTime;
    private PersonInfo employee;
    private Shop shop;

}
