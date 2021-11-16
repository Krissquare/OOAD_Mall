package cn.edu.xmu.oomall.activity.service;

import cn.edu.xmu.oomall.activity.dao.GrouponActivityDao;
import cn.edu.xmu.oomall.activity.model.bo.ShopAdministrator;
import cn.edu.xmu.oomall.activity.model.po.GroupOnActivityPo;
import cn.edu.xmu.oomall.activity.microservice.vo.GrouponUpdateSimpleVo;
import cn.edu.xmu.oomall.activity.model.vo.NewGrouponProductOnSaleVo;
import cn.edu.xmu.oomall.activity.microservice.vo.OnsaleSimpleVo;
import cn.edu.xmu.oomall.activity.microservice.OnsalesService;
import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class GrouponService {

    @Autowired
    GrouponActivityDao grouponActivityDao;

    @Resource
    OnsalesService onsalesApi;

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject onlineGrouponActivity(Integer id, Integer shopId, ShopAdministrator admin){
        ReturnObject<GroupOnActivityPo> returnObject = grouponActivityDao.getGrouponActivity(Long.parseLong(id.toString()));
        if (returnObject.getCode()== ReturnNo.INTERNAL_SERVER_ERR){
            return returnObject;
        }
        GroupOnActivityPo groupOnActivityPo = returnObject.getData();
        //无此活动 活动id和商铺id不匹配 状态错
        if (groupOnActivityPo == null
                || !groupOnActivityPo.getShopId().equals(Long.parseLong(shopId.toString()))
                || groupOnActivityPo.getState() != 0) {
            return new ReturnObject(ReturnNo.STATENOTALLOW);
        }
        //修改因为上线而改变的字段
        groupOnActivityPo.setState((byte) 1);
        LocalDateTime localDateTime = LocalDateTime.now();
        if (groupOnActivityPo.getBeginTime().isBefore(localDateTime)) {
            groupOnActivityPo.setBeginTime(LocalDateTime.now());
        }
        Common.setPoModifiedFields(groupOnActivityPo, admin.getId(), admin.getName());
        grouponActivityDao.updateGrouponActivity(groupOnActivityPo);
        return returnObject;
    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject offlineGrouponActivity(Integer id, Integer shopId, ShopAdministrator admin){
        ReturnObject<GroupOnActivityPo> returnObject = grouponActivityDao.getGrouponActivity(Long.parseLong(id.toString()));
        if (returnObject.getCode()== ReturnNo.INTERNAL_SERVER_ERR){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
        GroupOnActivityPo groupOnActivityPo = returnObject.getData();
        if (groupOnActivityPo == null
                ||!groupOnActivityPo.getShopId().equals(Long.parseLong(shopId.toString()))
                || groupOnActivityPo.getState() != 1) {
            return new ReturnObject(ReturnNo.STATENOTALLOW);
        }
        //修改因为下架而改变的字段
        groupOnActivityPo.setState((byte) 2);
        groupOnActivityPo.setEndTime(LocalDateTime.now());
        Common.setPoModifiedFields(groupOnActivityPo, admin.getId(), admin.getName());
        grouponActivityDao.updateGrouponActivity(groupOnActivityPo);
        //调用内部接口，下线所有OnSale
        onsalesApi.setGrouponOnsalesToOffline(id);
        //返回成功给Controller
        return returnObject;
    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject updateGrouponActivity(Integer id, Integer shopId, GrouponUpdateSimpleVo actUpd, ShopAdministrator admin){
        ReturnObject<GroupOnActivityPo> returnObject = grouponActivityDao.getGrouponActivity(Long.parseLong(id.toString()));
        if (returnObject.getCode() == ReturnNo.INTERNAL_SERVER_ERR){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
        GroupOnActivityPo groupOnActivityPo = returnObject.getData();
        if (groupOnActivityPo == null
                ||!groupOnActivityPo.getShopId().equals(Long.parseLong(shopId.toString()))
                ||groupOnActivityPo.getState() != 0) {
            return new ReturnObject(ReturnNo.STATENOTALLOW);
        }
        //设置po并储存
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        groupOnActivityPo.setStrategy(actUpd.getStrategy());
        groupOnActivityPo.setBeginTime(LocalDateTime.parse(actUpd.getBeginTime(), df));
        groupOnActivityPo.setEndTime(LocalDateTime.parse(actUpd.getEndTime(), df));
        Common.setPoModifiedFields(groupOnActivityPo, admin.getId(), admin.getName());
        grouponActivityDao.updateGrouponActivity(groupOnActivityPo);
        //同时修改所有涉及的onsale，调用内部api
        Map<String, Object> ret = onsalesApi.updateGrouponOnsale(id, actUpd);
        if (ret.get("errno").equals(0) == false) {
            return new ReturnObject(ReturnNo.GOODS_PRICE_CONFLICT);
        }
        return returnObject;
    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject deleteGrouponActivity(Integer id, Integer shopId){
        ReturnObject<GroupOnActivityPo> returnObject = grouponActivityDao.getGrouponActivity(Long.parseLong(id.toString()));
        if (returnObject.getCode()== ReturnNo.INTERNAL_SERVER_ERR){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
        GroupOnActivityPo groupOnActivityPo = returnObject.getData();
        if (groupOnActivityPo == null
                ||!groupOnActivityPo.getShopId().equals(Long.parseLong(shopId.toString()))
                ||groupOnActivityPo.getState() != 0) {
            return new ReturnObject(ReturnNo.STATENOTALLOW);
        }
        grouponActivityDao.deleteGrouponActivity(groupOnActivityPo);
        return returnObject;
    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject addGrouponProduct(Integer id, Integer shopId, Integer pid, ShopAdministrator admin, NewGrouponProductOnSaleVo retVO){
        ReturnObject<GroupOnActivityPo> returnObject = grouponActivityDao.getGrouponActivity(Long.parseLong(id.toString()));
        if (returnObject.getCode()== ReturnNo.INTERNAL_SERVER_ERR){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
        GroupOnActivityPo groupOnActivityPo = returnObject.getData();
        if (groupOnActivityPo.getState() != 0) {
            return new ReturnObject(ReturnNo.STATENOTALLOW);
        }
        //调用内部接口添加onsale项
        ReturnObject<OnsaleSimpleVo> apiRet = onsalesApi.addOneGrouponOnsale(shopId, id, pid);
        if (apiRet.getCode() != ReturnNo.OK){
            return new ReturnObject(ReturnNo.GOODS_PRICE_CONFLICT);
        }
        //从apiRet中取出返回值Vo需要的信息
        retVO.setId(apiRet.getData().getId());
        retVO.setPrice(apiRet.getData().getPrice());
        retVO.setQuantity(apiRet.getData().getQuantity());
        //从po取属性填充vo
        retVO.setBeginTime(groupOnActivityPo.getBeginTime().toString());
        retVO.setEndTime(groupOnActivityPo.getEndTime().toString());
        return returnObject;
    }


}
