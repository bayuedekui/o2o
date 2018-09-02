package com.bayuedekui.enums;


public enum ProductCategoryEnum {
    INNER_ERROR(-1001, "操作失败");

    private int state;
    private String stateInfo;


    private ProductCategoryEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;

    }

    public static ProductCategoryEnum stateOf(int index) {
        for (ProductCategoryEnum state : values()) {
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
