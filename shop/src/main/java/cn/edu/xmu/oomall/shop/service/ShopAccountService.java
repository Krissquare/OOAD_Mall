package cn.edu.xmu.oomall.shop.service;

import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.shop.dao.ShopAccountDao;
import cn.edu.xmu.oomall.shop.model.po.ShopAccountPo;
import cn.edu.xmu.oomall.shop.model.vo.ShopAccountVo;
import cn.edu.xmu.oomall.shop.model.vo.SimpleAdminUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author  Xusheng Wang
 * @date  2021-11-11
 */

@Service
public class ShopAccountService {

    @Autowired
    private ShopAccountDao shopAccountDao;

    public ReturnObject<ShopAccountVo> addShopAccount(ShopAccountVo shopAccountVo, Long shopId,Long loginUserId,String loginUserName) {
        ShopAccountPo shopAccountPo = shopAccountVo.createPo();
        if(shopAccountDao.addShopAccount(shopAccountPo,shopId,loginUserId,loginUserName)){
            ShopAccountVo shopAccountVo1=new ShopAccountVo(shopAccountPo);
            shopAccountVo1.modifiedBy=new SimpleAdminUserVo();
            shopAccountVo1.createdBy=new SimpleAdminUserVo(shopAccountPo.getCreatedBy(),shopAccountPo.getCreateName());
            return new ReturnObject<>(shopAccountVo1);
        }
        else
            return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERR,"添加失败！");
    }

    public ReturnObject<List<ShopAccountVo>> getShopAccounts(Long shopId) {
        List<ShopAccountPo> shopAccountPoList=shopAccountDao.getShopAccounts(shopId);
        List<ShopAccountVo> ret =shopAccountPoList.stream().map(ShopAccountVo::new).collect(Collectors.toList());
        if(ret.get(0).getId()==-1){
            return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERR,"获取失败");
        }
        else
            return new ReturnObject<>(ret);
    }

    public ReturnObject deleteAccount(Long shopId, Long accountId) {
        if(!shopAccountDao.checkShopAccount(shopId,accountId)){
            return new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST,"账户信息有误！");
        }
        if(shopAccountDao.deleteAccount(accountId)){
            return new ReturnObject<>();
        }
        else
            return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERR,"删除失败！");
    }
}
