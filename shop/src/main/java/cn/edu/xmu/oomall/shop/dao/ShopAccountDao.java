package cn.edu.xmu.oomall.shop.dao;

import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.shop.mapper.ShopAccountPoMapper;
import cn.edu.xmu.oomall.shop.model.po.ShopAccountPo;
import cn.edu.xmu.oomall.shop.model.po.ShopAccountPoExample;
import cn.edu.xmu.oomall.shop.model.vo.ShopAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author  Xusheng Wang
 * @date  2021-11-11
 * @studentId 34520192201587
 */

@Repository
public class ShopAccountDao {

    @Autowired(required = false)
    public ShopAccountPoMapper shopAccountPoMapper;

    /**
     * @author  Xusheng Wang
     * @date  2021-11-11
     * @studentId 34520192201587
     */
    public ReturnObject<List<ShopAccountVo>> getShopAccounts(Long shopId) {
        List<ShopAccountPo> accountPoList= new ArrayList<>();
        ShopAccountPoExample shopAccountPoExample=new ShopAccountPoExample();
        ShopAccountPoExample.Criteria criteria=shopAccountPoExample.createCriteria();
        if (shopId!=null){
            criteria.andShopIdEqualTo(shopId);
        }
        try {
            accountPoList = shopAccountPoMapper.selectByExample(shopAccountPoExample);
            List<ShopAccountVo> ret=accountPoList.stream().map(ShopAccountVo::new).collect(Collectors.toList());
            return new ReturnObject<>(ret);
        }
        catch (Exception exception){
            return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERR,exception.getMessage());
        }
    }

    /**
     * @author  Xusheng Wang
     * @date  2021-11-11
     * @studentId 34520192201587
     */
    public boolean addShopAccount(ShopAccountPo shopAccountPo,Long shopId,Long loginUserId,String loginUserName) {
        //Po添加设置基本信息
        shopAccountPo.setShopId(shopId);
        Common.setPoCreatedFields(shopAccountPo,loginUserId,loginUserName);
        ShopAccountPoExample shopAccountPoExample=new ShopAccountPoExample();
        ShopAccountPoExample.Criteria criteria=shopAccountPoExample.createCriteria();
        criteria.andPriorityEqualTo(shopAccountPo.getPriority());
        if(shopId!=null){
            criteria.andShopIdEqualTo(shopId);
        }
        try {
            //当优先级没有相同的时候直接插入
            if (shopAccountPoMapper.selectByExample(shopAccountPoExample).isEmpty())
                return shopAccountPoMapper.insertSelective(shopAccountPo) == 1;
                //优先级相同时，使优先级小于等于插入账户的账户的优先级都降低一级
            else {
                ShopAccountPoExample shopAccountPoExample1 = new ShopAccountPoExample();
                ShopAccountPoExample.Criteria criteria1 = shopAccountPoExample1.createCriteria();
                criteria1.andPriorityGreaterThanOrEqualTo(shopAccountPo.getPriority());
                if (shopId != null) {
                    criteria1.andShopIdEqualTo(shopId);
                }
                //选出优先级小于等于插入账户的账户列表
                List<ShopAccountPo> lowerPriorityShopAccountPo = shopAccountPoMapper.selectByExample(shopAccountPoExample1);
                lowerPriorityShopAccountPo.stream().forEach(shopAccountPo1 -> {
                    shopAccountPo1.setPriority((byte) (shopAccountPo1.getPriority() + 1));//使其priority+1,即使其优先级降低
                    shopAccountPoMapper.updateByPrimaryKey(shopAccountPo1);
                });
                return shopAccountPoMapper.insertSelective(shopAccountPo) == 1;
            }
        }
        catch (Exception exception){
            return false;
        }
    }

    /**
     * @author  Xusheng Wang
     * @date  2021-11-11
     * @studentId 34520192201587
     */
    public boolean deleteAccount(Long accountId) {
        try {
            return shopAccountPoMapper.deleteByPrimaryKey(accountId)==1;
        }
        catch (Exception exception){
            return false;
        }
    }

    /**
     * @author  Xusheng Wang
     * @date  2021-11-11
     * @studentId 34520192201587
     */
    public boolean checkShopAccount(Long shopId, Long accountId) {
        try {
            return shopId==shopAccountPoMapper.selectByPrimaryKey(accountId).getShopId();
        }
        catch (Exception exception){
            return false;
        }
    }
}
