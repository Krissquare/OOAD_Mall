package cn.edu.xmu.oomall.goods.service;

import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.goods.dao.OnSaleDao;
import cn.edu.xmu.oomall.goods.dao.ProductDao;

import cn.edu.xmu.oomall.goods.model.bo.OnSale;
import cn.edu.xmu.oomall.goods.model.vo.NewOnSaleVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static cn.edu.xmu.oomall.core.util.Common.cloneVo;


/**
 * @author yujie lin
 * @date 2021/11/10
 */
@Service
@Component
public class OnsaleService {

    private Logger logger = LoggerFactory.getLogger(OnsaleService.class);

    @Autowired
    private OnSaleDao onsaleDao;

    @Autowired
    private ProductDao productDao;


    @Transactional(rollbackFor = Exception.class)
    public ReturnObject createOnSale(Long shopId, Long productId, NewOnSaleVo newOnSaleVO, Long userId, String userName) {

        //判断该货品是否存在
        if (!productDao.hasExist(productId)) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "货品id不存在。");
        }

        // 判断该货品是否该商家的
        if (!productDao.matchProductShop(productId, shopId)) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_OUTSCOPE, "该货品不属于该商铺。");
        }


        OnSale bo = (OnSale) cloneVo(newOnSaleVO,OnSale.class);
        bo.setShopId(shopId);
        bo.setProductId(productId);

        // 判断是否有冲突的销售情况
        if (onsaleDao.timeCollided(bo)) {
            return new ReturnObject(ReturnNo.GOODS_PRICE_CONFLICT, "商品销售时间冲突。");
        }
        return onsaleDao.createOnSale(bo, userId, userName);
    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject createOnSaleWithoutShopId(Long productId, NewOnSaleVo newOnSaleVO, Long userId, String userName) {

        //判断该货品是否存在
        if (!productDao.hasExist(productId)) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "货品id不存在。");
        }
        Long shopId = productDao.getShopIdById(productId);

        OnSale bo = (OnSale) cloneVo(newOnSaleVO,OnSale.class);
        bo.setShopId(shopId);
        bo.setProductId(productId);

        // 判断是否有冲突的销售情况
        if (onsaleDao.timeCollided(bo)) {
            return new ReturnObject(ReturnNo.GOODS_PRICE_CONFLICT, "商品销售时间冲突。");
        }
        return onsaleDao.createOnSale(bo, userId, userName);
    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject onlineOrOfflineOnSale(Long shopId, Long onsaleId, Long userId, String userName, OnSale.State finalState) {
        //判断OnSale是否存在
        OnSale onsale = onsaleDao.getOnSaleById(onsaleId);
        if (null == onsale) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "不存在该价格浮动");
        }

        //判断是否该商家的onsale
        if(!onsaleDao.onSaleShopMatch(onsaleId,shopId)){
            return new ReturnObject(ReturnNo.RESOURCE_ID_OUTSCOPE,"该价格浮动不属于该商铺");
        }

        //限定只能处理普通和秒杀，其他类型返回403错误
        if (onsale.getType() != OnSale.Type.NOACTIVITY
                && onsale.getType() != OnSale.Type.SECKILL) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_OUTSCOPE, "只能处理普通和秒杀类型");
        }

        if (finalState == OnSale.State.OFFLINE) {
            //只有上线态才能下线， 否则出507错误
            if (onsale.getState() != OnSale.State.ONLINE) {
                return new ReturnObject(ReturnNo.STATENOTALLOW, "非上线态无法下线");
            }
            //如果结束时间晚于当前时间且开始时间早于当前时间，修改结束时间为当前时间
            if (onsale.getEndTime().isAfter(LocalDateTime.now()) && onsale.getBeginTime().isBefore(LocalDateTime.now())) {
                onsale.setEndTime(LocalDateTime.now());
            }
        } else if (finalState == OnSale.State.ONLINE) {
            //只有草稿态才能上线， 否则出507错误
            if (onsale.getState() != OnSale.State.DRAFT) {
                return new ReturnObject(ReturnNo.STATENOTALLOW, "非草稿态无法上线");
            }
            //如果开始时间早于当前时间且结束时间晚于当前时间，修改开始时间为当前时间
            if (onsale.getBeginTime().isBefore(LocalDateTime.now()) && onsale.getEndTime().isAfter(LocalDateTime.now())) {
                onsale.setBeginTime(LocalDateTime.now());
            }
        }
        onsale.setState(finalState);
        return onsaleDao.onlineOrOfflineOnSale(onsale, userId, userName);
    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject onlineOrOfflineOnSaleGroupPre(Long actId, Long userId, String userName, OnSale.State cntState, OnSale.State finalState) {

        return onsaleDao.onlineOrOfflineOnSaleAct(actId, userId, userName, cntState, finalState);
    }






    @Transactional(rollbackFor = Exception.class)
    public ReturnObject deleteOnSaleNorSec(Long shopId, Long id) {

        //判断OnSale是否存在
        OnSale onsale = onsaleDao.getOnSaleById(id);
        if (null == onsale) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "不存在该价格浮动");
        }

        //判断是否该商家的onsale
        if(!onsaleDao.onSaleShopMatch(id,shopId)){
            return new ReturnObject(ReturnNo.RESOURCE_ID_OUTSCOPE,"该价格浮动不属于该商铺");
        }

        //限定只能处理普通和秒杀，其他类型返回403错误
        if (onsale.getType() != OnSale.Type.NOACTIVITY
                && onsale.getType() != OnSale.Type.SECKILL) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_OUTSCOPE, "只能处理普通和秒杀类型");
        }


        //只有草稿态才能删除， 否则出507错误
        if (onsale.getState() != OnSale.State.DRAFT) {
            return new ReturnObject(ReturnNo.STATENOTALLOW, "非草稿态无法删除");
        }

        return onsaleDao.deleteOnSale(id);

    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject deleteOnSaleGroPre(Long actId) {

        return onsaleDao.deleteOnSaleAct(actId);
    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject updateOnSale(OnSale bo,Long userId,String userName) {

        //判断OnSale是否存在
        OnSale onsale = onsaleDao.getOnSaleById(bo.getId());
        if (null == onsale) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "不存在该价格浮动");
        }

        //只有草稿态或下线态才能修改， 否则出507错误
        if (onsale.getState() != OnSale.State.DRAFT
                &&onsale.getState() != OnSale.State.OFFLINE) {
            return new ReturnObject(ReturnNo.STATENOTALLOW, "非草稿态或下线态无法修改");
        }
        return onsaleDao.updateOnSale(bo,userId, userName);

    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject updateOnSaleNorSec(OnSale bo,Long shopId,Long userId,String userName) {

        //判断OnSale是否存在
        OnSale onsale = onsaleDao.getOnSaleById(bo.getId());
        if (null == onsale) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "不存在该价格浮动");
        }

        //限定只能处理普通和秒杀，其他类型返回403错误
        if (onsale.getType() != OnSale.Type.NOACTIVITY
                && onsale.getType() != OnSale.Type.SECKILL) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_OUTSCOPE, "限定处理普通或秒杀。");
        }


        //只有草稿态或下线态才能修改， 否则出507错误
        if (onsale.getState() != OnSale.State.DRAFT
                &&onsale.getState() != OnSale.State.OFFLINE) {
            return new ReturnObject(ReturnNo.STATENOTALLOW, "非草稿态或下线态无法修改");
        }

        bo.setProductId(onsale.getProductId());
        // 判断是否有冲突的销售情况
        if (onsaleDao.timeCollided(bo)) {
            return new ReturnObject(ReturnNo.GOODS_PRICE_CONFLICT, "商品销售时间冲突。");
        }


        return onsaleDao.updateOnSale(bo,userId, userName);

    }


}


