package com.bayuedekui.webcontroller.shopadmin;

import ch.qos.logback.core.joran.util.StringToObjectConverter;
import com.bayuedekui.dto.ImageHolder;
import com.bayuedekui.dto.ProductExecution;
import com.bayuedekui.entity.Product;
import com.bayuedekui.entity.ProductCategory;
import com.bayuedekui.entity.Shop;
import com.bayuedekui.enums.ProductStateEnum;
import com.bayuedekui.service.ProductCategoryService;
import com.bayuedekui.service.ProductService;
import com.bayuedekui.util.CodeUtil;
import com.bayuedekui.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.multi.MultiInternalFrameUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    //支持上传商品详情图的最大数量
    public static final int IMAGEFILE_SIZE = 6;

    @RequestMapping(value = "/getproductlistbyshop",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getProductListByShop(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        //获取前台传过来的页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        
        //从session获取店铺信息,主要获取shopId(老是出现session拿不到的情形，暂时还没解决,原来是tomcate的问题重启下就好了)
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        
        //空值判断
        if(pageSize>-1&&pageIndex>-1&&(currentShop!=null)&&currentShop.getShopId()!=null){
            //根据传入的查询条件,包括是否需要从某个商品类别以及模糊查询商品的名字去筛选店铺下的商品
            //筛选的条件可进行排列组合
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product productCondition=compactProductCondition(currentShop.getShopId(),productCategoryId,productName);
            
            //传入查询条件以及分页信息进行查询,返回相应商品列表以及总数
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("productList",pe.getProductList());
            modelMap.put("count", pe.getCount());
            modelMap.put("success", true);
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIdex or shopId");
        }
        
        return modelMap;
    }

    /**
     * 为查询设置各种筛选条件的方法
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductCondition(long shopId,long productCategoryId,String productName){
        Product productCondition=new Product();
        Shop shop=new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        productCondition.setShopId(shopId);
        //如果有指定类别的就添加进去
        if(productCategoryId!=-1L){
            ProductCategory pc=new ProductCategory();
            pc.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(pc);
        }
        //若果有商品名称模糊查询则增加进去
        if(productName!=null){
            productCondition.setProductName(productName);
        }
        return productCondition;
    }
    
    /**
     * 增加商品的接口
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
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
        MultipartHttpServletRequest multipartHttpServletRequest = null;   //用于接收图片文件流
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        //转化处理前台传过来的图片文件流
        try {
            //若请求中存在文件流，则取出相关文件
            if (multipartResolver.isMultipart(request)) {
            /*    //将HTTP中的request转化成可以获取文件流的格式（也可以理解成提取出文件流）
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
                } */    //以上备注解的可以提取成一个方法
                handleImage((MultipartHttpServletRequest) request,productImgList);
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
            String productStr = HttpServletRequestUtil.getString(request, "productStr");    //获取前台穿过来的文件流
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
                product.setShopId(currentShop.getShopId());
                //执行添加操作
                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "商品信息不能为空，请输入商品信息");
            return modelMap;
        }
        return modelMap;
    }


    /**
     * 根据product_id查询出商品
     *
     * @return
     */
    @RequestMapping(value = "getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductById(@RequestParam Long productId) {
        Map<String, Object> modelMap = new HashMap<>();
        if (productId > -1) {
            //获取商品信息
            Product product = productService.getProductById(productId);
            //获取该店铺下所有商品类别列表        
            List<ProductCategory> pcList = productCategoryService.getProductCategoryList(productId);
            modelMap.put("product", product);
            modelMap.put("productCategoryList", pcList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty prodcutId");
        }
        return modelMap;
    }


    /**
     * 商品编辑
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/modifyproduct",method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyProduct(HttpServletRequest request) throws IOException {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        //是商品编辑时调用还是上下架时候调用
        //若为编辑则进行验证码判断,如果不是则跳过验证码判断
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        //验证码(不是改变上下架状态,而是是商品编辑)
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误,请重新输入");
        }

        //接收前端变量初始化,包括商品,缩略图,详情图列表实体类
        Product product = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        //处理请求中的文件流
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        
        //若请求中存在文件流,则取出相关的文件(包括详情图二号缩略图)
        if(!statusChange) {
            try {
                if (commonsMultipartResolver.isMultipart(request)) {
                    handleImage((MultipartHttpServletRequest) request, productImgList);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "上传图片不能为空");
                    return modelMap;
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }
        //请求前台传入的商品信息
        try {
            String productStr = HttpServletRequestUtil.getString(request,"productStr");
            //将前台传过来的productStr json字串转化成prouduct类
            product = objectMapper.readValue(productStr, Product.class);
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        
        //如果productStr信息不为空,则开始惊醒修改操作
        if(product!=null){
            try {
                //从session中取出当前店铺的的id并赋值给product,减少对前端数据的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                product.setShopId(currentShop.getShopId());
                //开始对商品信息做变更操作
                ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
                if(pe.getState()==ProductStateEnum.SUCCESS.getState()){
                    modelMap.put("success", true);
                }else{
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            }catch (RuntimeException e){
                modelMap.put("success",false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg","商品信息不能为空,请输入商品信息");
        }

        return modelMap;    
    }

    /**
     * 处理图片的方法(提取出来)
     * @param request
     * @param productImgList
     * @throws IOException
     */
    private void handleImage(MultipartHttpServletRequest request, List<ImageHolder> productImgList) throws IOException {
        ImageHolder thumbnail;//将请求流转化成包含文件的请求流
        MultipartHttpServletRequest multipartHttpServletRequest = request;
        //取出缩略图并构建ImageHolder对象(前台传过来的thumbnail)
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumnail");
        if (thumbnailFile != null) {
            thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
        }

        //取出详情图并构建List<ImageHolder>列表对象,最多支持六张图片上传
        for(int i=0;i<IMAGEFILE_SIZE;i++){
            CommonsMultipartFile productImgFile= (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg"+i);
            if(productImgFile!=null){
                //如果取出的详情图片不为空,则加入详情图列表
                ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                productImgList.add(productImg);
            }else{
                //如果取出的第i个详情土图片文件流为空,则说明到了最后一个(比如有可能只传了三张),则终止循环
                break;
            }
        }
    }


}
