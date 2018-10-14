package com.bayuedekui.webcontroller.frontend;

import com.bayuedekui.dao.AreaDao;
import com.bayuedekui.dto.ShopExecution;
import com.bayuedekui.entity.Area;
import com.bayuedekui.entity.Shop;
import com.bayuedekui.entity.ShopCategory;
import com.bayuedekui.service.AreaService;
import com.bayuedekui.service.ShopCategoryService;
import com.bayuedekui.service.ShopService;
import com.bayuedekui.util.HttpServletRequestUtil;
import com.bayuedekui.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.Request;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopListController {

    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private ShopService shopService;


    /**
     * 返回返回商品列表里的shopCategory列表(二级或者一级),一级区域信息列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshoppageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        if (parentId != -1) {     //如果parentId存在,则取出一级ShopCategory下的二级ShopCategory列表
            try {
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parent);
                shopCategoryList = shopCategoryService.queryShopCategoryList(shopCategoryCondition);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            //如果parentId为空,则取出所有一级ShopCategory
            try {
                shopCategoryList = shopCategoryService.queryShopCategoryList(null);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }
        modelMap.put("shopCategoryList", shopCategoryList);

        //下面开始取出区域信息
        List<Area> areaList = null;
        try {
            areaList = areaService.queryAreaList();
            modelMap.put("success", true);
            modelMap.put("areaList", areaList);

        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        return modelMap;
    }

    @RequestMapping(value = "/listshops", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //获取页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        //获取一页需要展现的条数
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //将前端的页面的逻辑转化为数据库的逻辑
        pageIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        
        //非空判断
        if (pageIndex > -1 && pageSize > -1) {
            //尝试获取一级类别id
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            //尝试获取二级类别id
            long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
            //尝试获取区域id
            long areaId = HttpServletRequestUtil.getLong(request, "areaId");
            //尝试获取模糊查询的名字
            String shopName = HttpServletRequestUtil.getString(request, "shopName");
            //构造组合后的查询条件
            Shop shopCondition = compackShopCondition4Search(parentId,shopCategoryId,areaId,shopName);
          
            //根据查询条件和分页信息获取店铺列表,并返回总数
            ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
            modelMap.put("shopList", se.getShopList());
            modelMap.put("count", se.getCount());
            modelMap.put("success",true);
        }
        return modelMap;
    }   

    /**
     * 为shopCondition设置组合查询条件
     *
     * @param parentId
     * @param parentId
     * @param areaId
     * @param shopName
     * @return
     */
    private Shop compackShopCondition4Search(long parentId, long shopCategoryId, long areaId, String shopName) {
        Shop shopCondition = new Shop();
        if (parentId != -1L) {
            //查询某个一级shopCategory下面的所有的二级shopCategory里面的店铺列表
            ShopCategory parentCategory = new ShopCategory();
            ShopCategory childCategory = new ShopCategory();
            parentCategory.setParentId(parentId);
            childCategory.setParent(parentCategory);
            shopCondition.setShopCategory(childCategory);
        }
        if (shopCategoryId != -1L) {
            //查询某个二级shopCategory下面的列表
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }

        if (areaId != -1L) {
            //查询位于某个区域id下的店铺列表
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
            shopCondition.setAreaId(areaId);
        }

        if (shopName != null) {
            shopCondition.setShopName(shopName);
        }
        //前端展示系统中都是审核成功的店铺
        shopCondition.setEnableStatus(1);
        return shopCondition;
    }
}
