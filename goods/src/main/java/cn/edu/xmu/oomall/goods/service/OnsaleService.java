package cn.edu.xmu.oomall.goods.service;

import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.goods.dao.OnSaleDao;
import cn.edu.xmu.oomall.goods.dao.ProductDao;
import cn.edu.xmu.oomall.goods.microservice.ShareService;
import cn.edu.xmu.oomall.goods.microservice.ShopService;
import cn.edu.xmu.oomall.goods.microservice.bo.ActInfo;
import cn.edu.xmu.oomall.goods.microservice.bo.ShopInfo;
import cn.edu.xmu.oomall.goods.model.bo.OnSale;
import cn.edu.xmu.oomall.goods.model.bo.ProductBaseInfo;
import cn.edu.xmu.oomall.goods.model.vo.OnSaleDetailRetVo;
import cn.edu.xmu.oomall.goods.model.vo.OnSaleSimpleRetVo;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;


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
    private ShopService shopService;

    @Resource
    private ShareService shareService;


    @Transactional(rollbackFor = Exception.class)
    public ReturnObject searchOnSaleByProductNorSec(Long productId, Integer page, Integer pageSize) {

        //判断该货品是否存在
        if (!productDao.hasExist(productId)) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "该货品不存在。");
        }

        ReturnObject retOnSales = onsaleDao.searchOnSaleByProductNorSec(productId, page, pageSize);
        PageInfo pageInfo = (PageInfo<OnSale>) retOnSales.getData();
        OnSaleSimpleRetVo info = new OnSaleSimpleRetVo(pageInfo);
        return new ReturnObject(info);


    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject searchOnSaleByProduct(Long productId, Integer page, Integer pageSize) {

        //判断该货品是否存在
        if (!productDao.hasExist(productId)) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "货品id不存在。");
        }

        ReturnObject<PageInfo<OnSale>> retOnSales = onsaleDao.searchOnSaleByProduct(productId, page, pageSize);
        PageInfo<OnSale> pageInfo = retOnSales.getData();
        OnSaleSimpleRetVo info = new OnSaleSimpleRetVo(pageInfo);
        return new ReturnObject(info);

    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject searchOnSaleByActivity(Long actId, LocalDateTime beginTime, LocalDateTime endTime, Integer page, Integer pageSize,
                                               Integer state) {

        ReturnObject retOnSales = onsaleDao.searchOnSaleByActivity(beginTime,endTime,actId, page, pageSize, state);
        PageInfo<OnSale> pageInfo = (PageInfo<OnSale>) (retOnSales.getData());
        OnSaleSimpleRetVo vo=new OnSaleSimpleRetVo(pageInfo);
        return new ReturnObject<>(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject searchOnSaleByShare(Long actId, Integer page, Integer pageSize,
                                            Integer state) {
        ReturnObject retOnSales = onsaleDao.searchOnSaleByShare(actId, page, pageSize, state);
        PageInfo<OnSale> pageInfo = (PageInfo<OnSale>) retOnSales.getData();
        OnSaleSimpleRetVo vo=new OnSaleSimpleRetVo(pageInfo);
        return new ReturnObject<>(vo);
    }


    @Transactional(rollbackFor = Exception.class)
    public ReturnObject getDetailNorSec(Long onSaleId,Long shopId) {

        OnSale onSale = onsaleDao.getOnSaleById(onSaleId);
        //判断存不存在onsale
        if(null==onSale){
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST,"该价格浮动详情不存在");
        }

        //判断是不是该商家的onsale
        if (!onSale.getShopId().equals(shopId)) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_OUTSCOPE, "该价格浮动不属于该商家");
        }

        //判断是否普通秒杀
        if(!(onSale.getType()== OnSale.Type.NOACTIVITY||onSale.getType()== OnSale.Type.SECKILL)){
            return new ReturnObject(ReturnNo.RESOURCE_ID_OUTSCOPE,"只能处理普通或秒杀类型");
        }

        // 根据shopId 查shopName，
        String shopName = ((ShopInfo) shopService.getInfo(shopId).getData()).getName();

        //根据productId 查productName和image
        ProductBaseInfo pInfo = productDao.getBaseInfoById(onSale.getProductId());
        //根据shareActId查name
        Long shareActId = onSale.getShareActId();
        String shareActName;
        if(shareActId==null)shareActName=null;
        else
        {
           shareActName =((ActInfo) shareService.getInfo(shareActId).getData()).getName();
       }


        OnSaleDetailRetVo ret = new OnSaleDetailRetVo(shopName, pInfo, shareActName, onSale);
        return new ReturnObject(ret);
    }

    @Transactional(rollbackFor = Exception.class)
    public ReturnObject getDetail(Long onSaleId) {

        OnSale onSale = onsaleDao.getOnSaleById(onSaleId);
        //判断存不存在onsale
        if(null==onSale){
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST,"该价格浮动详情不存在");
        }

        // 根据shopId 查shopName，
        Long shopId = onSale.getShopId();
        String shopName = ((ShopInfo) shopService.getInfo(shopId).getData()).getName();

        //根据productId 查productName和image
        ProductBaseInfo pInfo = productDao.getBaseInfoById(onSale.getProductId());
        //根据shareActId查name
        Long shareActId = onSale.getShareActId();
        String shareActName = ((ActInfo) shareService.getInfo(shareActId).getData()).getName();

        OnSaleDetailRetVo ret = new OnSaleDetailRetVo(shopName, pInfo, shareActName, onSale);
        return new ReturnObject(ret);
    }



}


