package com.bayuedekui.webcontroller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin", method = {RequestMethod.GET})
public class ShopAdminController {
    @RequestMapping("/shopoperation")
    public String shopOperation() {
        return "shop/shopoperation";
    }   //通过后台的控制返回的访问的路径(html文件)

    @RequestMapping("/shoplist")
    //跳转到商铺列表页面
    public String shopList() {
        return "shop/shoplist";
    }

    @RequestMapping("/shopmanagement")
    //跳转到商店管理页面
    public String shopManagement() {
        return "shop/shopmanagement";
    }

    @RequestMapping("/productcategorymanagement")
    //跳转到商品类别管理页面
    public String productCategoryManagement(){
        return "shop/productcategorytmanagement";
    }
    
    @RequestMapping("/productoperation")
    //跳转到商品操作页面(包括更新和增加)
    public String productOperation(){
        return "shop/productoperation";
    }

    @RequestMapping("/productmanagement")
    //跳转到商品管理页面
    public String productManagement(){
        return "shop/productmanagement";
    }
}

