package com.bayuedekui.entity;


import lombok.Data;

import java.util.Date;

/**
 * 头条实体(头条id,头条名称,头条链接,头条链接图片地址,优先级,是否能用状态,创建时间,最后修改时间)
 */
@Data
public class HeadLine {
    private Long lineId;
    private String lineName;
    private String lineLink;
    private String lineImg;
    private Integer priority;
    private Integer enableStatus;
    private Date createTime;
    private Date lastEditTime;

}
