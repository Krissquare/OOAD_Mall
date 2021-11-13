package cn.edu.xmu.oomall.goods.service;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.goods.dao.OnSaleDao;
import cn.edu.xmu.oomall.goods.dao.ProductDao;
import cn.edu.xmu.oomall.goods.model.bo.OnSale;
import cn.edu.xmu.oomall.goods.model.bo.OnSaleDetailInfo;
import cn.edu.xmu.oomall.goods.model.bo.OnSaleSimpleInfo;
import cn.edu.xmu.oomall.goods.model.bo.ProductBaseInfo;
import cn.edu.xmu.oomall.goods.model.vo.NewOnSaleVo;
import cn.edu.xmu.oomall.goods.openfeign.ShareActApi;
import cn.edu.xmu.oomall.goods.openfeign.ShopApi;
import com.github.pagehelper.PageInfo;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    @Resource
    private ShopApi shopApi;

    @Resource
    private ShareActApi shareActApi;


    @Transactional(rollbackFor = Exception.class)
    public ReturnObject createOnSale(Long shopId, Long productId, NewOnSaleVo newOnSaleVO, Long userId, String userName) {

        //判断该货品是否存在
        if (!productDao.hasExist(productId)) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "商品id不存在。");
        }

        // 判断该货品是否该商家的
        if (!productDao.matchProductShop(productId, shopId)) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_OUTSCOPE, "该货品不属于该商铺。");
        }

        OnSale bo =newOnSaleVO.createOnsale(shopId,productId);
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
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "商品id不存在。");
        }
        Long shopId = productDao.getShopIdById(productId);
        OnSale bo = newOnSaleVO.createOnsale(shopId,productId);
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
        if (null == onsale.gotOnSalePo()) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "不存在该浮动价格");
        }

        //限定只能处理普通和秒杀，其他类型返回403错误
        if (onsale.getType() != OnSale.Type.NOACTIVITY
                && onsale.getType() != OnSale.Type.SECKILL) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_OUTSCOPE, "只能处理普通和秒杀订单");
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
    public ReturnObject onlineOrOfflineOnSaleGroupPre(Long onsaleId, Long userId, String userName, OnSale.State finalState) {

        //判断OnSale是否存在
        OnSale onsale = onsaleDao.getOnSaleById(onsaleId);
        if (null == onsale.gotOnSalePo()) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "不存在该浮动价格");
        }

        //限定只能处理团购和预售，其他类型返回403错误
        if (onsale.getType() != OnSale.Type.GROUPON
                && onsale.getType() != OnSale.Type.PRESALE) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_OUTSCOPE, "只能处理团购和预售订单");
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
                return new ReturnObject<>(ReturnNo.STATENOTALLOW, "非草稿态无法上线");
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
    public ReturnObject searchOnSaleByProductNorSec(Long productId, Integer page, Integer pageSize) {

        //判断该货品是否存在
        if (!productDao.hasExist(productId)) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "货品id不存在。");
        }

        ReturnObject retOnSales = onsaleDao.searchOnSaleByProductNorSec(productId, page, pageSize);
        PageInfo<OnSale> pageInfo = (PageInfo<OnSale>) retOnSales.getData();
        OnSaleSimpleInfo info = new OnSaleSimpleInfo(page, pageSize, Math.toIntExact(pageInfo.getTotal()), pageInfo.getList());
        return new ReturnObject(info);


    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject searchOnSaleByProduct(Long productId, Integer page, Integer pageSize) {

        //判断该货品是否存在
        if (!productDao.hasExist(productId)) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "货品id不存在。");
        }

        ReturnObject<Object> retOnSales = onsaleDao.searchOnSaleByProduct(productId, page, pageSize);
        PageInfo<OnSale> pageInfo = (PageInfo<OnSale>) retOnSales.getData();
        OnSaleSimpleInfo info = new OnSaleSimpleInfo(page, pageSize, Math.toIntExact(pageInfo.getTotal()), pageInfo.getList());
        return new ReturnObject<>(info);


    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject searchOnSaleByActivity(Long actId, Integer page, Integer pageSize,
                                               Integer state, OnSale.Type type) {

        ReturnObject retOnSales = onsaleDao.searchOnSaleByActivity(actId, page, pageSize, state, type);
        PageInfo<OnSale> pageInfo = (PageInfo<OnSale>) retOnSales.getData();
        OnSaleSimpleInfo info = new OnSaleSimpleInfo(page, pageSize, Math.toIntExact(pageInfo.getTotal()), pageInfo.getList());
        return new ReturnObject<>(info);
    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject searchOnSaleByShare(Long actId, Integer page, Integer pageSize,
                                            Integer state) {

        ReturnObject retOnSales = onsaleDao.searchOnSaleByShare(actId, page, pageSize, state);
        PageInfo<OnSale> pageInfo = (PageInfo<OnSale>) retOnSales.getData();
        OnSaleSimpleInfo info = new OnSaleSimpleInfo(page, pageSize, Math.toIntExact(pageInfo.getTotal()), pageInfo.getList());
        return new ReturnObject<>(info);
    }


    @Transactional(rollbackFor = Exception.class)
    public ReturnObject<VoObject> getDetail(Long onSaleId, boolean normalAndSecking) {

        OnSale onSale = onsaleDao.getOnSaleById(onSaleId);

        if (normalAndSecking && onSale.getType() != OnSale.Type.NOACTIVITY && onSale.getType() != OnSale.Type.SECKILL) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_OUTSCOPE, "该订单非普通或秒杀类型。");
        }

        // 根据shopId 查shopName，
        Long shopId = onSale.getShopId();
        String shopName;
        try {
            shopName = shopApi.getName(shopId);
        } catch (FeignException e) {
            shopName = "mock-shop-name";
            logger.error(e.getMessage());
        }

        //根据productId 查productName和image
        ProductBaseInfo pInfo = productDao.getBaseInfoById(onSale.getProductId());
        //根据shareActId查name
        Long shareActId = onSale.getShareActId();
        String shareActName;
        try {
            shareActName = shareActApi.getName(shareActId);
        } catch (FeignException e) {
            shareActName = "mock-act-name";
            logger.error(e.getMessage());
        }
        OnSaleDetailInfo info= (new OnSaleDetailInfo(shopName,pInfo,shareActName,onSale));
        return new ReturnObject(info);
    }


}


