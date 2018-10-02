package com.bayuedekui.entity;

import lombok.Data;

import java.util.Date;

/**
 * 区域实体(区域id,区域名,优先级,创建时间,最后修改时间)
 */
@Data
public class Area {
    private Long areaId;
    private String areaName;
    private String areaDesc;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;

   
}
