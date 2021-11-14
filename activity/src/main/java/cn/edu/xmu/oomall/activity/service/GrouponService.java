package cn.edu.xmu.oomall.activity.service;

import cn.edu.xmu.oomall.activity.dao.GrouponActivityDao;
import cn.edu.xmu.oomall.activity.model.bo.ShopAdministrator;
import cn.edu.xmu.oomall.activity.model.po.GroupOnActivityPo;
import cn.edu.xmu.oomall.activity.model.vo.GrouponUpdateSimpleVo;
import cn.edu.xmu.oomall.activity.model.vo.NewGrouponProductOnSaleVo;
import cn.edu.xmu.oomall.activity.model.vo.OnsaleSimpleVo;
import cn.edu.xmu.oomall.activity.openfeign.OnsalesApi;
import cn.edu.xmu.oomall.activity.util.GrouponProductStatus;
import cn.edu.xmu.oomall.activity.util.GrouponReturnStatus;
import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Transactional
@Service
public class GrouponService {

    @Autowired
    GrouponActivityDao grouponActivityDao;

    @Resource
    OnsalesApi onsalesApi;

    public GrouponReturnStatus onlineGrouponActivity(Integer id, Integer shopId, ShopAdministrator admin){
        try {
            GroupOnActivityPo groupOnActivityPo = grouponActivityDao.getGrouponActivity(Long.parseLong(id.toString()));
            //无此活动
            if (groupOnActivityPo == null) {
                return GrouponReturnStatus.ID_NOT_EXIT;
            }
            //活动id和商铺id不匹配
            if (!groupOnActivityPo.getShopId().equals(Long.parseLong(shopId.toString()))) {
                return GrouponReturnStatus.ID_SHOPID_NOT_MATCH;
            }
            //已经是上线状态
            if (groupOnActivityPo.getState() == 1) {
                return GrouponReturnStatus.ALREADY;
            }
            //下线则不可上线
            if (groupOnActivityPo.getState() != 0) {
                return GrouponReturnStatus.CHANGE_FORBIDDEN;
            }
            //修改因为上线而改变的字段
            groupOnActivityPo.setState((byte) 1);
            LocalDateTime localDateTime = LocalDateTime.now();
            if (groupOnActivityPo.getBeginTime().isBefore(localDateTime)) {
                groupOnActivityPo.setBeginTime(LocalDateTime.now());
            }
            Common.setPoModifiedFields(groupOnActivityPo, admin.getId(), admin.getName());
            grouponActivityDao.updateGrouponActivity(groupOnActivityPo);
            return GrouponReturnStatus.CHANGED;
        } catch (Exception e){
            return GrouponReturnStatus.INTERNAL_FAULT;
        }
    }

    public GrouponReturnStatus offlineGrouponActivity(Integer id, Integer shopId, ShopAdministrator admin){
        try {
            GroupOnActivityPo groupOnActivityPo = grouponActivityDao.getGrouponActivity(Long.parseLong(id.toString()));
            //无此活动
            if (groupOnActivityPo == null) {
                return GrouponReturnStatus.ID_NOT_EXIT;
            }
            //活动id和商铺id不匹配
            if (!groupOnActivityPo.getShopId().equals(Long.parseLong(shopId.toString()))) {
                return GrouponReturnStatus.ID_SHOPID_NOT_MATCH;
            }
            //已经是下线状态
            if (groupOnActivityPo.getState() == 2) {
                return GrouponReturnStatus.ALREADY;
            }
            //草稿态不可下线
            if (groupOnActivityPo.getState() != 1) {
                return GrouponReturnStatus.CHANGE_FORBIDDEN;
            }
            //修改因为下架而改变的字段
            groupOnActivityPo.setState((byte) 2);
            groupOnActivityPo.setEndTime(LocalDateTime.now());
            Common.setPoModifiedFields(groupOnActivityPo, admin.getId(), admin.getName());
            grouponActivityDao.updateGrouponActivity(groupOnActivityPo);
            //调用内部接口，下线所有OnSale
            onsalesApi.setGrouponOnsalesToOffline(id);
            //返回成功给Controller
            return GrouponReturnStatus.CHANGED;
        }catch (Exception e){
            return GrouponReturnStatus.INTERNAL_FAULT;
        }
    }

    public GrouponReturnStatus updateGrouponActivity(Integer id, Integer shopId, GrouponUpdateSimpleVo actUpd, ShopAdministrator admin){
        try {
            GroupOnActivityPo groupOnActivityPo = grouponActivityDao.getGrouponActivity(Long.parseLong(id.toString()));
            //是否存在该活动
            if (groupOnActivityPo == null) {
                return GrouponReturnStatus.ID_NOT_EXIT;
            }
            //检查id和shopId是否匹配
            if (!groupOnActivityPo.getShopId().equals(Long.parseLong(shopId.toString()))) {
                return GrouponReturnStatus.ID_SHOPID_NOT_MATCH;
            }
            //草稿态才可以操作
            if (groupOnActivityPo.getState() != 0) {
                return GrouponReturnStatus.CHANGE_FORBIDDEN;
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
                return GrouponReturnStatus.SALE_TIME_CONFLICT;
            }
            return GrouponReturnStatus.CHANGED;
        }catch (Exception e){
            return GrouponReturnStatus.INTERNAL_FAULT;
        }
    }

    public GrouponReturnStatus deleteGrouponActivity(Integer id, Integer shopId){
        try {
            GroupOnActivityPo groupOnActivityPo = grouponActivityDao.getGrouponActivity(Long.parseLong(id.toString()));
            //是否存在该活动
            if (groupOnActivityPo == null) {
                return GrouponReturnStatus.ID_NOT_EXIT;
            }
            //检查id和shopId是否匹配
            if (!groupOnActivityPo.getShopId().equals(Long.parseLong(shopId.toString()))) {
                return GrouponReturnStatus.ID_SHOPID_NOT_MATCH;
            }
            //草稿态才可以操作
            if (groupOnActivityPo.getState() != 0) {
                return GrouponReturnStatus.CHANGE_FORBIDDEN;
            }
            grouponActivityDao.deleteGrouponActivity(groupOnActivityPo);
            return GrouponReturnStatus.CHANGED;
        }catch (Exception e){
            return GrouponReturnStatus.INTERNAL_FAULT;
        }
    }

    public GrouponProductStatus addGrouponProduct(Integer id, Integer shopId, Integer pid, ShopAdministrator admin, NewGrouponProductOnSaleVo retVO){
        try {
            GroupOnActivityPo groupOnActivityPo = grouponActivityDao.getGrouponActivity(Long.parseLong(id.toString()));
            if (groupOnActivityPo.getState() != 0) {
                return GrouponProductStatus.ADD_FORBIDDEN_STATE;
            }
            //调用内部接口添加onsale项
            ReturnObject<OnsaleSimpleVo> apiRet = onsalesApi.addOneGrouponOnsale(shopId, id, pid);
            //从apiRet中取出返回值Vo需要的信息
            retVO.setId(apiRet.getData().getId());
            retVO.setPrice(apiRet.getData().getPrice());
            retVO.setQuantity(apiRet.getData().getQuantity());
            //从po取属性填充vo
            retVO.setBeginTime(groupOnActivityPo.getBeginTime().toString());
            retVO.setEndTime(groupOnActivityPo.getEndTime().toString());
            return GrouponProductStatus.ADD_SUCCESS;
        }catch (Exception e){
            return GrouponProductStatus.INTERNAL_FAULT;
        }
    }


}
