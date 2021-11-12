package cn.edu.xmu.oomall.shop.service;

import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.shop.dao.ShopDao;
import cn.edu.xmu.oomall.shop.model.bo.Shop;
import cn.edu.xmu.oomall.shop.model.po.ShopPo;
import cn.edu.xmu.oomall.shop.model.vo.*;
import cn.edu.xmu.oomall.shop.openfeign.PayApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
@Component
public class ShopService {
    private static final Logger logger = LoggerFactory.getLogger(ShopService.class);
    @Autowired
    private ShopDao shopDao;
    @Resource
    private PayApi payApi;


    public ReturnObject<Shop> getShopByShopId(Long ShopId) {
        return shopDao.getShopById(ShopId);
    }

    public ReturnObject newShop(ShopVo shopVo, Long loginUser, String loginUsername) {
        ShopPo po = new ShopPo();
        po.setName(shopVo.getName());
        po.setCreatedBy(loginUser);
        po.setCreateName(loginUsername);
        ReturnObject ret = shopDao.newShop(po);
        Shop shop = new Shop((ShopPo) ret.getData());
        ShopSimpleRetVo vo = shop.createSimpleVo();
        return new ReturnObject(vo);
    }


    public ReturnObject getShopStates() {
        return shopDao.getShopState();
    }


    public ReturnObject updateShop(Long id, ShopVo shopVo,Long loginUser, String loginUsername) {
        Shop shop = new Shop();
        shop.setId(id);
        shop.setName(shopVo.getName());
        shop.setModifiedBy(loginUser);
        shop.setModiName(loginUsername);

        ReturnObject ret = shopDao.UpdateShop(shop.getId(), shop);
        return ret;
    }


    public ReturnObject deleteShopById(Long id,Long loginUser, String loginUsername) {
        if (!payApi.isSettled(id)) {
            return new ReturnObject(ReturnNo.SHOP_NOT_RECON);
        } else {
//            if (payApi.paybackDeposit(id))//退还保证金
//            {
            payApi.paybackDeposit(id);
            Shop shop = new Shop();
            shop.setId(id.longValue());
            shop.setState(Shop.State.FORBID.getCode().byteValue());
            shop.setModifiedBy(loginUser);
            shop.setModiName(loginUsername);
            ReturnObject ret = shopDao.updateShopState(shop);
            return ret;
//            }
        }


    }


    public ReturnObject passShop(Long id, ShopConclusionVo conclusion,Long loginUser, String loginUsername) {
        Shop shop = new Shop();
        shop.setId(id.longValue());
        shop.setModifiedBy(loginUser);
        shop.setModiName(loginUsername);
        shop.setState(conclusion.getConclusion() == true ? Shop.State.OFFLINE.getCode().byteValue() : Shop.State.EXAME.getCode().byteValue());
        ReturnObject ret = shopDao.updateShopState(shop);
        return ret;
    }


    public ReturnObject onShelfShop(Long id,Long loginUser, String loginUsername) {
        Shop shop = new Shop();
        shop.setId(id);
        shop.setModifiedBy(loginUser);
        shop.setModiName(loginUsername);
        shop.setState(Shop.State.ONLINE.getCode().byteValue());
        var x = getShopByShopId(id).getData();
//        if (x == null) {
//            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
//        }
        if (x.getState() == Shop.State.OFFLINE.getCode().byteValue()) {
            ReturnObject ret = shopDao.updateShopState(shop);
            return ret;
        } else return new ReturnObject(ReturnNo.STATENOTALLOW);
    }


    public ReturnObject offShelfShop(Long id,Long loginUser, String loginUsername) {
        Shop shop = new Shop();
        shop.setId(id.longValue());
        shop.setModifiedBy(loginUser);
        shop.setModiName(loginUsername);
        shop.setState(Shop.State.OFFLINE.getCode().byteValue());
        var x = getShopByShopId(id).getData();
//        if (x == null) {
//            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
//        }
        if (x.getState() == Shop.State.ONLINE.getCode().byteValue()) {
            ReturnObject ret = shopDao.updateShopState(shop);
            return ret;
        } else return new ReturnObject(ReturnNo.STATENOTALLOW);
    }
}