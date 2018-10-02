package com.bayuedekui.entity;

import lombok.Data;

import java.util.Date;

/**
 * 本地认证实体(本地id,用户名,密码,用户id,创建时间,最后修改时间,个人信息类)
 */
@Data
public class LocalAuth {
    private Long localAuthId;
    private String userName;
    private String password;
    private Long userId;
    private Date createTime;
    private Date lastEditTime;
    private PersonInfo personInfo;

    
}
