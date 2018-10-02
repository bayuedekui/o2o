package com.bayuedekui.entity;


import lombok.Data;

import java.util.Date;

/**
 * 微信认证实体(微信认真id,用户id,打开id,创建时间,用户信息[外键])
 */
@Data
public class WeChatAuth {
    private Long wechatAuthId;
    private Long userId;
    private String openId;
    private Date createTime;
    private PersonInfo personInfo;

}
