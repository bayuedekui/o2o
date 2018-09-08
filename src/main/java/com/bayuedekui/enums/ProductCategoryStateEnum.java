package com.bayuedekui.enums;


public enum ProductCategoryStateEnum {
    SUCCESS(1,"操作成功"),INNER_ERROR(-1001, "操作失败"),EMPTY_lIST(-1002,"添加为空");

    private int state;
    private String stateInfo;


    private ProductCategoryStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;

    }

    public static ProductCategoryStateEnum stateOf(int index) {
        for (ProductCategoryStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }


    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

}
