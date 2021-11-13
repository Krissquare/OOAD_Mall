package cn.edu.xmu.oomall.shop.service;

import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.shop.dao.ShopDao;
import cn.edu.xmu.oomall.shop.microservice.ReconciliationService;
import cn.edu.xmu.oomall.shop.microservice.vo.RefundDepositVo;
import cn.edu.xmu.oomall.shop.model.bo.Shop;
import cn.edu.xmu.oomall.shop.model.po.ShopAccountPo;
import cn.edu.xmu.oomall.shop.model.po.ShopPo;
import cn.edu.xmu.oomall.shop.model.vo.*;
import cn.edu.xmu.oomall.shop.microservice.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopService {
    @Autowired
    private ShopDao shopDao;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ReconciliationService reconciliationService;


    public ReturnObject<Shop> getShopByShopId(Long ShopId) {
        return shopDao.getShopById(ShopId);
    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject newShop(ShopVo shopVo, Long loginUser, String loginUsername) {
        ShopPo po = new ShopPo();
        po.setName(shopVo.getName());
        Common.setPoCreatedFields(po, loginUser, loginUsername);
        ReturnObject ret = shopDao.newShop(po);
        if (ret.getCode().equals(0)) {
            Shop shop = new Shop((ShopPo) ret.getData());
            ShopSimpleRetVo vo = shop.createSimpleVo();
            ret = new ReturnObject(vo);
        }
        return ret;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ReturnObject getShopStates() {
        return shopDao.getShopState();
    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject updateShop(Long id, ShopVo shopVo, Long loginUser, String loginUsername) {
        Shop shop = new Shop();
        shop.setId(id);
        shop.setName(shopVo.getName());
        Common.setPoModifiedFields(shop,  loginUser, loginUsername);

        ReturnObject ret = shopDao.UpdateShop(shop.getId(), shop);
        return ret;
    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject deleteShopById(Long id, Long loginUser, String loginUsername) {
        ReturnObject ret = reconciliationService.isClean(id);
        if (! ret.getCode().equals(0)) {
            return ret;
        }

        Boolean result = (Boolean) ret.getData();
        if (!result) {
            //商铺尚未完成清算
            return new ReturnObject(ReturnNo.SHOP_NOT_RECON);
        }

        //商铺已完成清算
        /*****************************************/
        //TODO:需要调用Shp[AccountDao获得
        ShopAccountPo accountPo = new ShopAccountPo();
        accountPo.setAccount("11111111");
        accountPo.setType((byte) 0);
        accountPo.setName("测试");
        /*******************************************/
        RefundDepositVo depositVo = (RefundDepositVo) Common.cloneVo(accountPo, RefundDepositVo.class);
        ReturnObject refundRet = paymentService.refund(depositVo);
        if (! refundRet.getCode().equals(0)){
            return refundRet;
        }

        //退还保证金
        Shop shop = new Shop();
        shop.setId(id.longValue());
        shop.setState(Shop.State.FORBID.getCode().byteValue());
        shop.setModifiedBy(loginUser);
        shop.setModiName(loginUsername);
        ReturnObject retUpdate = shopDao.updateShopState(shop);
        return retUpdate;

    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject passShop(Long id, ShopConclusionVo conclusion, Long loginUser, String loginUsername) {
        Shop shop = new Shop();
        shop.setId(id.longValue());
        Common.setPoModifiedFields(shop, loginUser, loginUsername);
        shop.setState(conclusion.getConclusion() == true ? Shop.State.OFFLINE.getCode().byteValue() : Shop.State.EXAME.getCode().byteValue());
        ReturnObject ret = shopDao.updateShopState(shop);
        return ret;
    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject onShelfShop(Long id, Long loginUser, String loginUsername) {
        Shop shop = new Shop();
        shop.setId(id);
        Common.setPoModifiedFields(shop, loginUser, loginUsername);
        shop.setState(Shop.State.ONLINE.getCode().byteValue());
        var x = getShopByShopId(id).getData();
        if (x.getState() == Shop.State.OFFLINE.getCode().byteValue()) {
            ReturnObject ret = shopDao.updateShopState(shop);
            return ret;
        } else {
            return new ReturnObject(ReturnNo.STATENOTALLOW);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject offShelfShop(Long id, Long loginUser, String loginUsername) {
        Shop shop = new Shop();
        shop.setId(id.longValue());
        Common.setPoModifiedFields(shop, loginUser, loginUsername);
        shop.setState(Shop.State.OFFLINE.getCode().byteValue());
        var x = getShopByShopId(id).getData();
        if (x.getState() == Shop.State.ONLINE.getCode().byteValue()) {
            ReturnObject ret = shopDao.updateShopState(shop);
            return ret;
        } else {
            return new ReturnObject(ReturnNo.STATENOTALLOW);
        }
    }
}