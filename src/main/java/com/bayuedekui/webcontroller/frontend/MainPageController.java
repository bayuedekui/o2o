package com.bayuedekui.webcontroller.frontend;

import com.bayuedekui.entity.HeadLine;
import com.bayuedekui.entity.ShopCategory;
import com.bayuedekui.service.HeadLineService;
import com.bayuedekui.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class MainPageController {
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private HeadLineService headLineService;
    

    @RequestMapping(value = "/listmainpageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listMainPageInfo(){
        Map<String,Object> modelMap=new HashMap<>();
        List<ShopCategory> shopCategoryList=new ArrayList<>();    //返回一级列表
        try {
            //查询一级店铺分类列表,即parent_id为空的shopCategory
            shopCategoryList=shopCategoryService.queryShopCategoryList(null);
            modelMap.put("shopCategoryList", shopCategoryList);
        }catch(Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        List<HeadLine> headLineList = new ArrayList<>();
        try {
            //获取状态为可用(1)的头条列表
            HeadLine headLineCondition=new HeadLine();
            headLineCondition.setEnableStatus(1);
            headLineList = headLineService.getHeadLineList(headLineCondition);
            modelMap.put("headLineList", headLineList);
        }catch(Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        modelMap.put("success", true);
        return modelMap;
    }
}
