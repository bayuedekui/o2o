package com.bayuedekui.dto;

import com.bayuedekui.entity.ProductCategory;
import com.bayuedekui.enums.ProductCategoryStateEnum;
import lombok.Data;

import java.util.List;

@Data
public class ProductCategoryExecution {
    private int state;

    private String stateInfo;

    private ProductCategory productCategory;

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
     * 造作成功返回商品类别列表的构造器
     */
    public void setProductCategoryList(ProductCategoryStateEnum stateEnum,List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
        this.stateInfo=stateEnum.getStateInfo();
        this.state=stateEnum.getState();
    }

    /**
     * 造作成功返回单个商品的构造器
     */
    public void setProductCategoryList(ProductCategoryStateEnum stateEnum,ProductCategory productCategory) {
        this.productCategory = productCategory;
        this.stateInfo=stateEnum.getStateInfo();
        this.state=stateEnum.getState();
    }

   
}
