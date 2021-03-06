package com.bayuedekui.service.impl;

import com.bayuedekui.dao.ProductDao;
import com.bayuedekui.dao.ProductImgDao;
import com.bayuedekui.dto.ImageHolder;
import com.bayuedekui.dto.ProductExecution;
import com.bayuedekui.entity.Product;
import com.bayuedekui.entity.ProductImg;
import com.bayuedekui.enums.ProductStateEnum;
import com.bayuedekui.exceptions.ProductCategoryOperationException;
import com.bayuedekui.exceptions.ProductOperationException;
import com.bayuedekui.service.ProductService;
import com.bayuedekui.util.ImageUtil;
import com.bayuedekui.util.PageCalculator;
import com.bayuedekui.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;


    /**
     * 查寻商品(product)列表
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        //转化成数据库能理解的逻辑的行码,并调用dao层取回指定页码的商品列表
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        //基于同样的查询条件返回该条件下的商品总数
        int count = productDao.queryProductCount(productCondition);

        ProductExecution pe = new ProductExecution();
        pe.setProductList(productList); 
        pe.setCount(count);
        return pe;
    }

    /**
     * 调用dao层增加商品,包括上传商品图片
     *
     * @param product
     * @param thumbnail
     * @param productImgHolderList
     * @return
     * @throws ProductOperationException
     */
    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException {
        //1.处理缩略图,获取缩略图的相对路径并赋值给product
        //2.往tb_product表中写入商品信息,并获取product_id
        //3.结合product_id批量处理商品详情图
        //4.将商品详情图列表批量插入tb_product_img中
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            //给商品设置上默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            //默认上架状态
            product.setEnableStatus(1);
            //如果上传商品缩略图不为空则添加
            if (null != thumbnail) {
                addThumbnail(product, thumbnail);
            }

            //开始创建商品信息
            try {
                int effNum = productDao.insertProduct(product);
                if (effNum <= 0) {
                    throw new ProductOperationException("商品创建失败:");
                }
            } catch (Exception e) {
                throw new ProductOperationException("商品创建失败:" + e.getMessage());
            }

            //若商品详情图不为空则添加
            if (null != productImgHolderList && productImgHolderList.size() > 0) {
                addProductImgList(product, productImgHolderList);

            }

            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    /**
     * 根据商品id查询商品
     * @param productId
     * @return
     */
    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }

    
    /**
     * 对商品进行修改
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductCategoryOperationException
     */
    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductCategoryOperationException {
        //1.若缩略图参数有值,则处理缩略图
        //若原先存在缩略图,则先删除再添加新图,之后去缩略图相对路径并赋值给product
        //2.若商品详情图有值,则对商品详情图做同样的操作
        //3.若详情图和缩略图其中一个有变化,则将tb_product_img表中记录全部清除
        //4.更新tb_product_img表记录
        
        //空值判断
        if(product!=null&&product.getShop()!=null&&product.getShop().getShopId()!=null){
            //给商品设置最后修改时间
//            product.setLastEditTime(new Date());  //为什么报设置值有问题的错误
            //如果商品缩略图不为空且原有缩略图不为空则删除原有图并添加
            if(thumbnail!=null){
                //先获取一遍原来的信息,因为原来的存在图片地址
                Product tempProduct = productDao.queryProductById(product.getProductId());
                if(tempProduct.getImgAddr()!=null){
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(product,thumbnail);
            }
            //如果有新存入的商品详情图,则将原来删除,并添加新的图片
            if(productImgList!=null&&productImgList.size()>0){
                deleteProductImgList(product.getProductId());
                addProductImgList(product,productImgList);
            }
            
            try {
                //开始更新商品信息
                int effectNum = productDao.updataProduct(product);
                if(effectNum<=0){
                    throw new ProductOperationException("更新商品信息失败");
            }
                return new ProductExecution(ProductStateEnum.SUCCESS,product);
            }catch(Exception e){
                throw new ProductOperationException("更新商品信息失败"+e.getMessage());
            }
        }else{
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    /**
     * 删除tb_product_img表中的属于一个商品的数据
     * @param productId
     */
    private void deleteProductImgList(long productId){
        //根据productId获取原来的图片
        List<ProductImg> productImgList = productImgDao.queryProductImgListByProductId(productId);
        //干掉原来的图片
        for(ProductImg productImg:productImgList){
            //将之前每一张的图片或者路径删除掉
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        //将tb_product_img中的相关图片记录删掉
        productImgDao.deleteProductImgByProductId(productId);
    }

    /**
     * 添加缩略图
     *
     * @param product
     * @param thumbnail
     */
    public void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }

    /**
     * 向Ttb_product_image表中添加缩略图数据
     *
     * @param product
     * @param productImgHolderList
     */
    public void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
        //获取图片存储的相对路径,这里直接放放到店铺的文件夹底下
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());

        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        //遍历图片一次去处理,并添加到ProductImg实体类里面
        for (ImageHolder productImgHolder : productImgHolderList) {
            String imgAddr=ImageUtil.generateNormalImg(productImgHolder,dest);
            ProductImg productImg=new ProductImg();
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImg.setImgAddr(imgAddr);
            productImgList.add(productImg);
        }
        
        //如果确实有图片需要添加,就执行批量添加操作
        if(productImgList.size()>0){
            try {
                int effectNum=productImgDao.batchInsertProductImg(productImgList);
                if(effectNum<0){
                    throw new ProductOperationException("创建商品详情图失败");
                }
            }catch(Exception e){
                throw new ProductOperationException("创建商品详情图失败"+e.toString());
            }
            
        }
        

    }


}
