package com.bayuedekui.service.impl;

import com.bayuedekui.dao.ProductCategoryDao;
import com.bayuedekui.dao.ProductDao;
import com.bayuedekui.dto.ProductCategoryExecution;
import com.bayuedekui.entity.ProductCategory;
import com.bayuedekui.enums.ProductCategoryStateEnum;
import com.bayuedekui.exceptions.ProductCategoryOperationException;
import com.bayuedekui.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductDao productDao;


    /**
     * 查询商品类别列表
     * @param shopId
     * @return
     */
    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    /**
     * 批量插入商品类别
     * @param productCategoryList
     * @return
     * @throws ProductCategoryOperationException
     */
    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                int effectNum = productCategoryDao.batchInsertProducctCategoryList(productCategoryList);
                if (effectNum <= 00) {
                    throw new ProductCategoryOperationException("店铺创建失败");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new ProductCategoryOperationException("bitchAddProductCategory error:" + e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_lIST);
        }
    }

    /**
     * 删除某个商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationException
     */
    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(Long productCategoryId, Long shopId) throws ProductCategoryOperationException {
        //TODO将商品下的该商品的类别id设为空,目的是为了解决tb_product中的商品和tb_product_category的类别关联
        try {
            int effecrNum = productDao.updateProductCategoryToNull(productCategoryId);
            if(effecrNum<0){
                throw new RuntimeException("商品类别更新失败");
            }
        }catch (Exception e){
            throw new RuntimeException("deleteProductCategory error:" + e.toString());
        }
        //进行商品类别的的删除操作
        try {
            int i = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (i <= 0) {
                throw new ProductCategoryOperationException("商品类别删除失败");
            } else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new ProductCategoryOperationException("delete productCategory failed  : " + e.getMessage());
        }
    }


}
