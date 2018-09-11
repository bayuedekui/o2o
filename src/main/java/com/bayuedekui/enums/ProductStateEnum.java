package com.bayuedekui.enums;

public enum ProductStateEnum {
    SUCCESS(1, "操作成功"), INNER_ERROR(-1001, "操作失败");

    private int state;
    private String stateInfo;

    ProductStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public ProductStateEnum stateOf(int index) {
        for(ProductStateEnum state: values()){
            if(state.getState()==index){
                return state;
            }
        }
        return null;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }
}
