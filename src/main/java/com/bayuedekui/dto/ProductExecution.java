package com.bayuedekui.dto;

import com.bayuedekui.entity.Product;
import com.bayuedekui.enums.ProductStateEnum;
import lombok.Data;

import java.util.List;
@Data
public class ProductExecution {

    private int state;
    private String stateInfo;
    private Product product;
    private List<Product> productList;
    private int count;

    

    /**
     * 无参构造
     
     */
    public ProductExecution(){}


    /**
     * 店铺操作成功返回商品列表的构造器
     * @return
     */
    public ProductExecution(ProductStateEnum stateEnum, List<Product> productList){
        this.productList=productList;
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();

    }

    /**
     * 店铺操作成功返回单个商品的构造器
     * @return
     */
    public ProductExecution(ProductStateEnum stateEnum, Product product){
        this.product=product;
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();

    }
    
    /**
     * 操作失败的构造器
     * @return
     */
    public ProductExecution(ProductStateEnum stateEnum){
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
    }


}
