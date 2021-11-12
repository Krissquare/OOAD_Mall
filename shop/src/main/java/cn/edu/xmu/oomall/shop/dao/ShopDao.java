package cn.edu.xmu.oomall.shop.dao;


import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.shop.mapper.ShopPoMapper;
import cn.edu.xmu.oomall.shop.model.bo.Shop;
import cn.edu.xmu.oomall.shop.model.po.ShopPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ShopDao {
    @Autowired
    ShopPoMapper shopPoMapper;
    private  static  final Logger logger = LoggerFactory.getLogger(ShopDao.class);


    public ReturnObject<Shop> getShopById(Long id){
        ShopPo shopPo;
        try {
            shopPo = shopPoMapper.selectByPrimaryKey(id);
        }catch (Exception e){
//            logger.error("selectShopDetails: DataAccessException:" + e.getMessage());
            return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERR);
        }
//        if(shopPo==null){
//            return new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
//        }
        Shop shop=new Shop(shopPo);
        return new ReturnObject<>(shop);
    }


    public ReturnObject getShopState()
    {
        List<Map<String, Object>> stateList=new ArrayList<>();
        for (Shop.State states : Shop.State.values()) {
            Map<String,Object> temp=new HashMap<>();
            temp.put("code",states.getCode());
            temp.put("name",states.getDescription());
            stateList.add(temp);
        }
        return new ReturnObject<>(stateList);
    }


    public ReturnObject newShop(ShopPo po)
    {
            int ret;
            po.setDeposit(Long.valueOf(0));
            po.setGmtCreate(LocalDateTime.now());
            po.setState(Shop.State.EXAME.getCode().byteValue());
            ret=shopPoMapper.insertSelective(po);
            if(ret==0){
                return new ReturnObject(ReturnNo.FIELD_NOTVALID);
            }else{
                return new ReturnObject(po);
            }

    }
    /**
     * 功能描述: 商家修改店铺信息
     * @Param: [po]

     */
    public ReturnObject UpdateShop(Long id, Shop shop)
    {
        int ret;
        ShopPo shopPo=shopPoMapper.selectByPrimaryKey(id);
//        if(shopPo == null){
//            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
//        }
        shopPo.setName(shop.getName());
        if(shopPo.getState()==Shop.State.FORBID.getCode().byteValue())
        {
            return new ReturnObject(ReturnNo.STATENOTALLOW,"商铺处于关闭态");
        }
        else
        {
            try{
                shopPo.setGmtModified(LocalDateTime.now());
                ret=shopPoMapper.updateByPrimaryKeySelective(shopPo);
            }catch (Exception e){
//                logger.error("updateShop: DataAccessException:" + e.getMessage());
                return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERR);
            }
            if (ret == 0) {
//                logger.debug("updateShop: update fail. shop id: " + shopPo.getId());
                return new ReturnObject(ReturnNo.FIELD_NOTVALID);
            } else {
//                logger.debug("updateShop: update shop success : " + shopPo.toString());
                return new ReturnObject();
            }
        }
    }


    public ReturnObject updateShopState(Shop shop)
    {
        ShopPo shopPo=shop.createPo();
        int ret;
        try {
            ret = shopPoMapper.updateByPrimaryKeySelective(shopPo);
            shopPo.setGmtModified(LocalDateTime.now());
        } catch (Exception e) {
//            logger.error("updateShopState: DataAccessException:" + e.getMessage());
            return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERR);
        }
        if (ret == 0) {
//            logger.debug("updateShop: update fail ! " + shopPo.toString());
            return new ReturnObject(ReturnNo.FIELD_NOTVALID);
        } else {
//            logger.debug("updateShop: update shop success ! " + shopPo.toString());
            return new ReturnObject();
        }
    }
}
