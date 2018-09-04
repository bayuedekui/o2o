package com.bayuedekui.dto;

import com.bayuedekui.entity.ProductCategory;
import com.bayuedekui.enums.ProductCategoryStateEnum;

import java.util.List;

public class ProductCategoryExecution {
    private int state;

    private String stateInfo;

    private List<ProductCategory> productCategoryList;

    /**
     * 无参构造
     */
    public ProductCategoryExecution() {

    }

    /**
     * 操作失败的构造器
     */
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum){
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
    }

    /**
     * 造作成功返回的构造器
     */
    public void setProductCategoryList(ProductCategoryStateEnum stateEnum,List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
        this.stateInfo=stateEnum.getStateInfo();
        this.state=stateEnum.getState();
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }
}
