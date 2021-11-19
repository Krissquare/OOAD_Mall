package cn.edu.xmu.oomall.activity.service;

import cn.edu.xmu.oomall.activity.dao.AdvanceSaleDao;
import cn.edu.xmu.oomall.activity.microsservice.GoodsService;
import cn.edu.xmu.oomall.activity.model.bo.AdvanceSale;
import cn.edu.xmu.oomall.activity.model.po.AdvanceSalePo;
import cn.edu.xmu.oomall.activity.model.vo.*;
import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author GXC 22920192204194
 */
@Service
public class AdvanceSaleService {
    @Autowired
    AdvanceSaleDao advanceSaleDao;
    @Autowired
    GoodsService goodsService;
    /**
     * 商铺管理员上线预售活动
     * @param shopId 商铺id
     * @param advancesaleId 预售活动id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnObject onlineAdvancesale(Long adminId, String adminName,Long shopId, Long advancesaleId) {
        ReturnObject returnObject=null;
        AdvanceSalePo po=null;
        po=(AdvanceSalePo) advanceSaleDao.selectAdvanceSaleByKey(advancesaleId).getData();
        if(po==null){
            returnObject=new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "目标预售活动不存在");
        }else if(!po.getShopId().equals(shopId)){
            returnObject=new ReturnObject(ReturnNo.RESOURCE_ID_OUTSCOPE,"管理员和预售活动不属于同一个商铺，无权限");
        }else{
            if(po.getState()==(byte)1){
                returnObject=new ReturnObject(ReturnNo.STATENOTALLOW,"当前状态禁止此操作");
            }else{
                po.setState((byte) 1);
                Common.setPoModifiedFields(po,adminId,adminName);
                advanceSaleDao.updateAdvanceSale(po);
                SimpleReturnObject retObject=goodsService.onlineOnsale(advancesaleId);
                //抛出异常是为了回滚
                if(retObject.getErrno()!=0){
                    returnObject=new ReturnObject(ReturnNo.getByCode(retObject.getErrno()),retObject.getErrmsg());
                }else{
                    returnObject=new ReturnObject();
                }
            }
        }
        return returnObject;
    }

    /**
     * 商铺管理员下线预售活动
     * @param shopId 商铺id
     * @param advancesaleId 预售活动id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnObject offlineAdvancesale(Long adminId,String adminName,Long shopId, Long advancesaleId)  {
        ReturnObject returnObject=null;
        AdvanceSalePo po=null;
        po=(AdvanceSalePo) advanceSaleDao.selectAdvanceSaleByKey(advancesaleId).getData();
        if(po==null){
            returnObject=new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "目标预售活动不存在");
        }else if(!po.getShopId().equals(shopId)){
            returnObject=new ReturnObject(ReturnNo.RESOURCE_ID_OUTSCOPE,"管理员和预售活动不属于同一个商铺，无权限");
        }else{
            if(po.getState()!=1){
                returnObject=new ReturnObject(ReturnNo.STATENOTALLOW,"当前状态禁止此操作");
            }else{
                po.setState((byte) 2);
                Common.setPoModifiedFields(po,adminId,adminName);
                advanceSaleDao.updateAdvanceSale(po);
                SimpleReturnObject retObject=goodsService.offlineOnsale(advancesaleId);
                if(retObject.getErrno()!=0){
                    returnObject=new ReturnObject(ReturnNo.getByCode(retObject.getErrno()),retObject.getErrmsg());
                }else{
                    returnObject=new ReturnObject();
                }
            }
        }
        return returnObject;
    }

    /**
     * @param adminId
     * @param shopId
     * @param advancesaleId
     * @param advanceSaleModifyVo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnObject modifyAdvancesale(Long adminId, Long shopId, String adminName,Long advancesaleId, AdvanceSaleModifyVo advanceSaleModifyVo) {
        ReturnObject returnObject=null;
        AdvanceSalePo po=(AdvanceSalePo) advanceSaleDao.selectAdvanceSaleByKey(advancesaleId).getData();
        if(po!=null){
            if(po.getShopId().equals(shopId)){
                if(po.getState()==0){
                    po=(AdvanceSalePo) Common.cloneVo(advanceSaleModifyVo,po.getClass());
                    Common.setPoModifiedFields(po,adminId,adminName);
                    advanceSaleDao.updateAdvanceSale(po);

                    //调用内部API，查onsale信息
                    SimpleReturnObject<PageVo<OnsaleVo>> retObj = goodsService.getOnsale(advancesaleId,1,1,10);
                    Long onsaleId=null;
                    //确定有需要修改的onsale目标
                    if(retObj.getErrno()==0&&retObj.getData().getTotal()>0){
                        onsaleId=retObj.getData().getList().get(0).getId();
                        OnsaleModifyVo onsaleModifyVo=(OnsaleModifyVo)Common.cloneVo(advanceSaleModifyVo,OnsaleModifyVo.class);
                        SimpleReturnObject result=goodsService.modifyOnsale(onsaleId,onsaleModifyVo);
                        if(result.getErrno()!=0){
                            returnObject=new ReturnObject(ReturnNo.getByCode(result.getErrno()),result.getErrmsg());
                        }else{
                            returnObject=new ReturnObject();
                        }
                    }else if(retObj.getErrno()!=0){
                        //查询就出错了
                        returnObject=new ReturnObject(ReturnNo.getByCode(retObj.getErrno()),retObj.getErrmsg());
                    }else{
                        //查询无结果
                        returnObject=new ReturnObject();
                    }
                }else{
                    returnObject=new ReturnObject(ReturnNo.STATENOTALLOW,"当前状态禁止此操作");
                }
            }else{
                returnObject=new ReturnObject(ReturnNo.RESOURCE_ID_OUTSCOPE,"管理员和预售活动不属于同一个商铺，无权限");
            }
        }else{
            returnObject=new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST,"该预售活动不存在");
        }
        return returnObject;
    }

    /**
     *
     * @param adminId
     * @param shopId
     * @param advancesaleId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnObject deleteAdvancesale(Long adminId, Long shopId, Long advancesaleId) {
        ReturnObject returnObject=null;
        AdvanceSalePo po=(AdvanceSalePo) advanceSaleDao.selectAdvanceSaleByKey(advancesaleId).getData();
        if(po!=null){
            if(po.getShopId().equals(shopId)){
                if(po.getState()==0){
                    advanceSaleDao.deleteAdvanceSale(advancesaleId);
                    //内部API物理删除onsale
                    SimpleReturnObject retObj=goodsService.deleteOnsale(advancesaleId);
                    //预售活动草稿态，那么onsale不是草稿态就是系统的问题，失败只有一种可能就是onsale服务没有运行
                    if(retObj.getErrno()!=0){
                        returnObject=new ReturnObject(ReturnNo.getByCode(retObj.getErrno()),retObj.getErrmsg());
                    }else{
                        returnObject=new ReturnObject();
                    }
                }else{
                    returnObject=new ReturnObject(ReturnNo.STATENOTALLOW,"当前状态禁止此操作");
                }
            }else{
                returnObject=new ReturnObject(ReturnNo.RESOURCE_ID_OUTSCOPE,"管理员和预售活动不属于同一个商铺，无权限");
            }
        }else{
            returnObject=new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST,"该预售活动不存在");
        }
        return returnObject;
    }
}
