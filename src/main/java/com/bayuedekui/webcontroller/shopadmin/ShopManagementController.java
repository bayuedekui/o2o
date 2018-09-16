package com.bayuedekui.webcontroller.shopadmin;

import com.bayuedekui.dto.ImageHolder;
import com.bayuedekui.dto.ShopExecution;
import com.bayuedekui.entity.Area;
import com.bayuedekui.entity.PersonInfo;
import com.bayuedekui.entity.Shop;
import com.bayuedekui.entity.ShopCategory;
import com.bayuedekui.enums.ShopStateEnum;
import com.bayuedekui.exceptions.ShopOperationException;
import com.bayuedekui.service.AreaService;
import com.bayuedekui.service.ShopCategoryService;
import com.bayuedekui.service.ShopService;
import com.bayuedekui.util.CodeUtil;
import com.bayuedekui.util.HttpServletRequestUtil;
import com.bayuedekui.util.ImageUtil;
import com.bayuedekui.util.PathUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import sun.misc.Request;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/getshopmanagementinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getManagementInfo(HttpServletRequest request) {
        Map<String,Object> modelMap=new HashMap<>();
        long shopId=HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId<=0){
            Object currentShop = request.getSession().getAttribute("curerntShop");
            if(currentShop==null){
                modelMap.put("redirect",true);
                modelMap.put("url","/o2o/shopadmin/shoplist");
            }else{
                Shop current=(Shop)currentShop;
                modelMap.put("redirect",false);
                modelMap.put("shopId",((Shop) currentShop).getShopId());
            }
        }else{
            Shop currentShop=new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop",currentShop);
            modelMap.put("redirect",false);
        }
        return modelMap;
    }


    @RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        PersonInfo user = new PersonInfo();
        user.setUserId(1L);
        user.setName("小明");
        request.getSession().setAttribute("user", user); //再服务器端设置session,记录用户信息
        user = (PersonInfo) request.getSession().getAttribute("user");
        List<Shop> shopList = new ArrayList<>();
        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
            modelMap.put("shopList", se.getShopList());
            modelMap.put("user", user);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;

    }


    /**
     * 通过shopId来获取shop的信息,回显到前端,共前端显示然后修改
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId > -1) {  //当请求中有shopId时候,开始可以修改地区,位置等信息
            try {
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.queryAreaList();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }


    /**
     * 初始化页面的店铺类别信息和区域信息(主要时调用dao层的查询area和shopCategory方法)
     *
     * @return
     */
    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<Area> areaList = new ArrayList<Area>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        try {
            areaList = areaService.queryAreaList();
            shopCategoryList = shopCategoryService.queryShopCategoryList(new ShopCategory()); //传入空的ShopCategory获取全部的对象
            modelMap.put("areaList", areaList);
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success:", false);
            modelMap.put("errMsg:", e.getMessage());
        }
        return modelMap;
    }

    /**
     * 注册店铺,向shop_category表中插入注册的店铺信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        //先进性验证码的验证
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误");
            return modelMap;
        }

        //1.接受并转化相应的参数,包括店铺信息以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);  //通过jackson的依赖,将传来的以Shop实体对象传出
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //上传图片
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());   //文件上传解析器,将request中的图片信息解析出来
        if (commonsMultipartResolver.isMultipart(request)) {  //判断请求中是不是含有上传的文件流
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request; //转化成文件上传轮流的类型
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");     //保存上传文件流
        }
        //2.修改店铺
        if (shop != null && shop.getShopId() != null) {
            PersonInfo owner = new PersonInfo();
            //到时候要通过session来获取,现在只是暂时写死
            owner.setUserId(1L);
            shop.setOwnerId(1L);

            ShopExecution se;  //电泳service层向数据库中插入店铺信息
            try {
                if (shopImg == null) {
                    se = shopService.modifyShop(shop, null);
                } else {
                    
                    se = shopService.modifyShop(shop, new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream()));
                }
                if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);   //返回注册成功的标记
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (ShopOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("success:", false);
            modelMap.put("errMsg", "请输入店铺id");
            return modelMap;
        }

        //3.返回结果
    }

    private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        //先进性验证码的验证
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误");
            return modelMap;
        }

        //1.接受并转化相应的参数,包括店铺信息以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);  //通过jackson的依赖,将传来的以Shop实体对象传出
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //上传图片
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());   //文件上传解析器,将request中的图片信息解析出来
        if (commonsMultipartResolver.isMultipart(request)) {  //判断请求中是不是含有上传的文件流
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request; //转化成文件上传轮流的类型
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");     //保存上传文件流
        } else {
            modelMap.put("success:", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }
        //2.注册店铺
        if (shop != null && shopImg != null) {
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
            //到时候要通过session来获取,现在只是暂时写死
            shop.setOwner(owner);

            ShopExecution se;  //电泳service层向数据库中插入店铺信息
            try {
                se = shopService.addShop(shop, new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream()));
                if (se.getState() == ShopStateEnum.CHECK.getState()) {
                    modelMap.put("success", true);   //返回注册成功的标记
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (ShopOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("success:", false);
            modelMap.put("errMsg", ",店铺信息为空,请输入店铺信息");
            return modelMap;
        }

        //3.返回结果


    }

    /**
     * 将输入流转化成为File类型的文件(去规避CommonsMultipartFile类型的文件不能转化为File的问题)
     *
     * @param ins
     * @param file
     */
//    private static void inputStreamToFile(InputStream ins, File file) {
//        FileOutputStream os = null;
//        try {
//            os = new FileOutputStream(file);
//            int bytesRead = 0;
//            byte[] buffer = new byte[1024];
//            while ((bytesRead = ins.read(buffer)) != -1) {
//                os.write(buffer, 0, bytesRead);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("调用inputSteamToFile异常" + e.getMessage());
//        } finally {
//            try {
//                if (os != null) {
//                    os.close();
//                }
//                if (ins != null) {
//                    ins.close();
//                }
//            } catch (IOException e) {
//                throw new RuntimeException("inputStreamToFile关闭IO异常" + e.getMessage());
//            }
//        }
//    }
}
