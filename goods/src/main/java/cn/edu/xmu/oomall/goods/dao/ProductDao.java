package cn.edu.xmu.oomall.goods.dao;

import cn.edu.xmu.oomall.goods.mapper.ProductPoMapper;
import cn.edu.xmu.oomall.goods.model.bo.ProductBaseInfo;
import cn.edu.xmu.oomall.goods.model.po.ProductPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author yujie lin
 * @date 2021/11/11
 */
@Repository
public class ProductDao {
    private Logger logger = LoggerFactory.getLogger(OnSaleDao.class);

    @Autowired
    private ProductPoMapper productMapper;

    public boolean hasExist(Long productId) {
        return null != productMapper.selectByPrimaryKey(productId);
    }


    public ProductBaseInfo getBaseInfoById(Long productId){
        ProductPo productPo= productMapper.selectByPrimaryKey(productId);
        return new ProductBaseInfo(productPo);
    }

    public boolean matchProductShop(Long productId, Long shopId) {
        ProductPo productPo=productMapper.selectByPrimaryKey(productId);
        return shopId.equals(productPo.getShopId());
    }

    public Long getShopIdById(Long id){
        return productMapper.getShopIdById(id);
    }
}
