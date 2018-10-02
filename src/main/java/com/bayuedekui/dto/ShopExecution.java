package com.bayuedekui.dto;

import com.bayuedekui.entity.Shop;
import com.bayuedekui.enums.ShopStateEnum;
import lombok.Data;

import java.util.List;

/**
 * 操作店铺的一些状态
 */
@Data
public class ShopExecution {
    //结果状态
    private int state;
    //状态标识
    private String stateInfo;
    //店铺数量
    private int count;
    //操作的shop(增删改店铺的时候用)
    private Shop shop;
    //获取店铺列表(查询店铺的时候用)
    private List<Shop> shopList;

    public ShopExecution() {
    }

    //失败的构造器
    public ShopExecution(ShopStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //操作shop成功的构造器
    public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shop = shop;
    }

    //查询shopList成功的构造器
    public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopList = shopList;
    }

    
}
