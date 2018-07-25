package com.bayuedekui.webcontroller.shopadmin;

import com.bayuedekui.dto.ShopExecution;
import com.bayuedekui.entity.Area;
import com.bayuedekui.entity.PersonInfo;
import com.bayuedekui.entity.Shop;
import com.bayuedekui.entity.ShopCategory;
import com.bayuedekui.enums.ShopStateEnum;
import com.bayuedekui.service.AreaService;
import com.bayuedekui.service.ShopCategoryService;
import com.bayuedekui.service.ShopService;
import com.bayuedekui.util.HttpServletRequestUtil;
import com.bayuedekui.util.ImageUtil;
import com.bayuedekui.util.PathUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

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


    /**
     * 初始化页面的店铺类别信息和区域信息(主要时调用dao层的查询area和shopCategory方法)
     * @return
     */
    @RequestMapping(value="/getshopinitinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String ,Object> getShopInitInfo(){
        Map<String ,Object> modelMap=new HashMap<String ,Object>();
        List<Area> areaList=new ArrayList<Area>();
        List<ShopCategory> shopCategoryList=new ArrayList<ShopCategory>();
        try {
            areaList=areaService.queryAreaList();
            shopCategoryList=shopCategoryService.queryShopCategoryList(new ShopCategory()); //传入空的ShopCategory获取全部的对象
            modelMap.put("areaList",areaList);
            modelMap.put("shopCategoryList",shopCategoryList); 
            modelMap.put("succcess",true);
        } catch (Exception e) {
            modelMap.put("success:",false);
            modelMap.put("errMsg:",e.getMessage());
        } 
        return modelMap;
    }

    /**
     * 注册店铺,向shop_category表中插入注册的店铺信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(javax.servlet.http.HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
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
            PersonInfo owner = new PersonInfo();
            owner.setUserId(1L);
            shop.setOwnerId(1L);
            File shopImgFile = new File(PathUtil.getImgBasePath()+ImageUtil.getRandomFileName());
            try {
                shopImgFile.createNewFile();
            } catch (IOException e) {
                modelMap.put("success:", false);
                modelMap.put("errMsg", "创建shopImgFile文件报错"+e.getMessage());
                return modelMap;
            }
            try {
                inputStreamToFile(shopImg.getInputStream(), shopImgFile);
            } catch (IOException e) {
                modelMap.put("success:", false);
                modelMap.put("errMsg", "调用getInputStream出错"+e.getMessage());
                return modelMap;
            }
            ShopExecution se = shopService.addShop(shop, shopImgFile);
            if(se.getState()==ShopStateEnum.CHECK.getState()){
                modelMap.put("success",true);
            }else{
                modelMap.put("success",false);
                modelMap.put("errMsg",se.getState());
            }

        } else {
            modelMap.put("success:", false);
            modelMap.put("errMsg", ",店铺信息为空,请输入店铺信息");
            return modelMap;
        }

        //3.返回结果

        return null;
    }

    /**
     * 将输入流转化成为File类型的文件(去规避CommonsMultipartFile类型的文件不能转化为File的问题)
     *
     * @param ins
     * @param file
     */
    private static void inputStreamToFile(InputStream ins, File file) {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = ins.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            throw new RuntimeException("调用inputSteamToFile异常" + e.getMessage());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (ins != null) {
                    ins.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("inputStreamToFile关闭IO异常" + e.getMessage());
            }
        }
    }
}
