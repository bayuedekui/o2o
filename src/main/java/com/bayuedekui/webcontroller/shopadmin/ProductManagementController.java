package com.bayuedekui.webcontroller.shopadmin;

import com.bayuedekui.dto.ImageHolder;
import com.bayuedekui.dto.ProductExecution;
import com.bayuedekui.entity.Product;
import com.bayuedekui.entity.Shop;
import com.bayuedekui.enums.ProductStateEnum;
import com.bayuedekui.service.ProductService;
import com.bayuedekui.util.CodeUtil;
import com.bayuedekui.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
    @Autowired
    private ProductService productService;

    //支持上传商品详情图的最大数量
    public static final int IMAGEFILE_SIZE = 6;

    
    @RequestMapping(value="/addproduct",method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        //验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误，请重输！");
            return modelMap;
        }

        //初始化接收前端参数变量，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();   //用来接收前台传过来的string，并且将string转化成对应的类
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request, "productStr");    //获取前台穿过来的文件流
        MultipartHttpServletRequest multipartHttpServletRequest = null;   //用于接收图片文件流
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        //转化处理前台传过来的图片文件流
        try {
            //若请求中存在文件流，则取出相关文件
            if (multipartResolver.isMultipart(request)) {
                //将HTTP中的request转化成可以获取文件流的格式（也可以理解成提取出文件流）
                multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                //取出商品缩略图（一个）并构建ImageHolder对象
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");   //是和前端约定好的thumbnail
                thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
                //取出详情图列表（最多是六个）并构建List<ImageHolder>列表对象，最多支持上传6张图片
                for (int i = 0; i < IMAGEFILE_SIZE; i++) {
                    CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i); //是和前端约定好的额productImg1/2/3...
                    if (productImgFile != null) {
                        //如果取出的第i个商品详情图不为空，则将它添入详情图列表
                        ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                        productImgList.add(productImg);
                    } else {
                        //若取出的图片文件流为空，则终止循环
                        break;
                    }
                }
            } else {
                //如果请求中不包含图片文件流，则像前台返回图片不能为空的提示
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        //获取的缩略图和详情图的文件流后，再去生成组装product的实例
        try {
            //尝试获取前台传过来的表单string转化成product实体类
            product = mapper.readValue(productStr, Product.class);

        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }


        //如果Product信息，缩略图以及详情图列表为非空，则开始进行商品添加操作
        if (product != null && thumbnail != null && productImgList.size() > 0) {
            try {
                //从session中获取shopId然后赋值给product,减少对前端数据的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                product.setShopId(Integer.parseInt(currentShop.getShopId().toString()));
                //执行添加操作
                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            }catch (Exception e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "商品信息不能为空，请输入商品信息");
            return modelMap;
        }
        return modelMap;
    }
}
