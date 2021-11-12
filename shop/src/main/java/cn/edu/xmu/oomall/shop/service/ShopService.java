package cn.edu.xmu.oomall.shop.service;

import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.shop.dao.ShopDao;
import cn.edu.xmu.oomall.shop.model.bo.Shop;
import cn.edu.xmu.oomall.shop.model.po.ShopPo;
import cn.edu.xmu.oomall.shop.model.vo.*;
import cn.edu.xmu.oomall.shop.openfeign.PayApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
@Component
public class ShopService {
    @Autowired
    private ShopDao shopDao;
    @Resource
    private PayApi payApi;


    public ReturnObject<Shop> getShopByShopId(Long ShopId) {
        return shopDao.getShopById(ShopId);
    }

    /**
     * @Author: 蒋欣雨
     * @Sn: 22920192204219
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnObject newShop(ShopVo shopVo, Long loginUser, String loginUsername) {
        ShopPo po = new ShopPo();
        Common.setPoCreatedFields(po,loginUser,loginUsername);
        po.setName(shopVo.getName());
        ReturnObject ret = shopDao.newShop(po);
        Shop shop = new Shop((ShopPo) ret.getData());
        ShopSimpleRetVo vo = shop.createSimpleVo();
        return new ReturnObject(vo);
    }

    /**
     * @Author: 蒋欣雨
     * @Sn: 22920192204219
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnObject getShopStates() {
        return shopDao.getShopState();
    }

    /**
     * @Author: 蒋欣雨
     * @Sn: 22920192204219
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnObject updateShop(Long id, ShopVo shopVo,Long loginUser, String loginUsername) {
        Shop shop = new Shop();
        shop.setId(id);
        shop.setName(shopVo.getName());
        Common.setPoModifiedFields(shop,loginUser,loginUsername);
        ReturnObject ret = shopDao.UpdateShop(shop.getId(), shop);
        return ret;
    }

    /**
     * @Author: 蒋欣雨
     * @Sn: 22920192204219
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnObject deleteShopById(Long id,Long loginUser, String loginUsername) {
        if (!payApi.isSettled(id)) {
            return new ReturnObject(ReturnNo.SHOP_NOT_RECON);
        } else {
            if (payApi.paybackDeposit(id))//退还保证金
            {
            Shop shop = new Shop();
            shop.setId(id.longValue());
            shop.setState(Shop.State.FORBID.getCode().byteValue());
            Common.setPoModifiedFields(shop,loginUser,loginUsername);
            ReturnObject ret = shopDao.updateShopState(shop);
            return ret;
            }
            else
            {
                return new ReturnObject(ReturnNo.SHOP_HASDEPOSIT);
            }
        }


    }

    /**
     * @Author: 蒋欣雨
     * @Sn: 22920192204219
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnObject passShop(Long id, ShopConclusionVo conclusion,Long loginUser, String loginUsername) {
        Shop shop = new Shop();
        shop.setId(id.longValue());
        Common.setPoModifiedFields(shop,loginUser,loginUsername);
        shop.setState(conclusion.getConclusion() == true ? Shop.State.OFFLINE.getCode().byteValue() : Shop.State.EXAME.getCode().byteValue());
        ReturnObject ret = shopDao.updateShopState(shop);
        return ret;
    }

    /**
     * @Author: 蒋欣雨
     * @Sn: 22920192204219
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnObject onShelfShop(Long id,Long loginUser, String loginUsername) {
        Shop shop = new Shop();
        shop.setId(id);
        Common.setPoModifiedFields(shop,loginUser,loginUsername);
        shop.setState(Shop.State.ONLINE.getCode().byteValue());
        var x = getShopByShopId(id).getData();
        if (x.getState() == Shop.State.OFFLINE.getCode().byteValue()) {
            ReturnObject ret = shopDao.updateShopState(shop);
            return ret;
        } else return new ReturnObject(ReturnNo.STATENOTALLOW);
    }

    /**
     * @Author: 蒋欣雨
     * @Sn: 22920192204219
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnObject offShelfShop(Long id,Long loginUser, String loginUsername) {
        Shop shop = new Shop();
        shop.setId(id.longValue());
        Common.setPoModifiedFields(shop,loginUser,loginUsername);
        shop.setState(Shop.State.OFFLINE.getCode().byteValue());
        var x = getShopByShopId(id).getData();
        if (x.getState() == Shop.State.ONLINE.getCode().byteValue()) {
            ReturnObject ret = shopDao.updateShopState(shop);
            return ret;
        } else return new ReturnObject(ReturnNo.STATENOTALLOW);
    }
}