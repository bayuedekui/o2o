package com.bayuedekui.enums;

/**
 * 使用枚举表述常量数据字典
 */
public enum ShopStateEnum {
    CHECK(0,"审核中"),OFFINE(-1,"非法商铺"),SUCCESS(1,"操作成功"),PASS(2,"通过认证"),INNER_ERROR(-1001,"操作失败"),
    NULL_SHOPID(-1002,"ShopId为空"),NULL_SHOP_INFO(-1003,"传入了空的信息"),NULL_SHOP(-1003,"shop信息为空");
    
    private int state;
    private String stateInfo;

    ShopStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
    
    /**
     * @description: 依据传入的index返回对应的enum值
     * @author: zhengkui
     * @date: 10:32 2019-5-30
     * @param: [index]
     */
    public static  ShopStateEnum stateOf(int index){
        for(ShopStateEnum state :values()){
            if(state.getState()==index){
                return state;   
            }
        }
        return null;
    }
  
}
