package cn.edu.xmu.oomall.goods.dao;

import cn.edu.xmu.oomall.core.util.RedisUtil;
import cn.edu.xmu.oomall.goods.mapper.ProductPoMapper;
import cn.edu.xmu.oomall.goods.model.bo.OnSale;
import cn.edu.xmu.oomall.goods.model.bo.Product;
import cn.edu.xmu.oomall.goods.model.bo.ProductBaseInfo;
import cn.edu.xmu.oomall.goods.model.po.OnSalePoExample;
import cn.edu.xmu.oomall.goods.model.po.ProductPo;
import cn.edu.xmu.oomall.goods.model.po.ProductPoExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static cn.edu.xmu.oomall.core.util.Common.cloneVo;

/**
 * @author yujie lin
 * @date 2021/11/11
 */
@Repository
public class ProductDao {
    private Logger logger = LoggerFactory.getLogger(OnSaleDao.class);

    @Autowired
    private ProductPoMapper productMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${oomall.goods.product.expiretime}")
    private long productTimeout;

    public boolean hasExist(Long productId) {
        return null != productMapper.selectByPrimaryKey(productId);
    }


    public ProductBaseInfo getBaseInfoById(Long productId){
        try{

            Product ret=(Product) redisUtil.get("p_"+productId);
            if(null!=ret){
                return (ProductBaseInfo) cloneVo(ret,ProductBaseInfo.class);
            }

            ProductPo po= productMapper.selectByPrimaryKey(productId);

            if(po == null) {
                return null;
            }
            Product pro=(Product)cloneVo(po,Product.class);
            redisUtil.set("p_"+pro.getId(),pro,productTimeout);

            return (ProductBaseInfo) cloneVo(pro,ProductBaseInfo.class);
        }
        catch(Exception e){
            return null;
        }

    }

    public boolean matchProductShop(Long productId, Long shopId) {

        ProductPo productPo=productMapper.selectByPrimaryKey(productId);
        return shopId.equals(productPo.getShopId());
    }

    public Long getShopIdById(Long id){
        try{
            Product ret=(Product) redisUtil.get("p_"+id);
            if(null!=ret){
                return ret.getId();
            }

            ProductPo po= productMapper.selectByPrimaryKey(id);

            if(po == null) {
                return null;
            }
            Product pro=(Product)cloneVo(po,Product.class);
            redisUtil.set("p_"+pro.getId(),pro,productTimeout);

            return pro.getId();
        }
        catch(Exception e){
            return null;
        }


    }
}
