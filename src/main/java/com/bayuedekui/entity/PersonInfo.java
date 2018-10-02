package com.bayuedekui.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户信息实体(用户id,名字,生日,性别,手机,邮箱,头像地址,消费者标记,店铺拥有标记,管理员标记,创建时间,最后修改时间,是否可以使用标记)
 */
@Data
public class PersonInfo {
    private Long userId;
    private String name;
    private Date brithday;
    private String gender;
    private String phone;
    private String email;
    private String profileImg;
    private Integer customerFlag;
    private Integer shopOwnerFlag;
    private Integer adminFlag;
    private Date createTime;
    private Date lastEditTime;
    private Integer enableStatus;

 
}
