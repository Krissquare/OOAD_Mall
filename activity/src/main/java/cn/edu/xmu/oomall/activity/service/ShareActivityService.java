package cn.edu.xmu.oomall.activity.service;

import cn.edu.xmu.oomall.activity.dao.ShareActivityDao;
import cn.edu.xmu.oomall.activity.mirrorservice.GoodsService;
import cn.edu.xmu.oomall.activity.mirrorservice.ShopService;
import cn.edu.xmu.oomall.activity.mirrorservice.vo.SimpleSaleInfoVO;
import cn.edu.xmu.oomall.activity.mirrorservice.vo.ShopInfoVO;
import cn.edu.xmu.oomall.activity.model.vo.ShareActivityDTO;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiuchen lang 22920192204222
 * @date 2021/11/12 12:55
 */
@Service
public class ShareActivityService {

    @Autowired
    private ShareActivityDao shareActivityDao;

    @Autowired
    private GoodsService goodsService;

    @Resource
    private ShopService shopService;

    /**
     * 获得分享活动的所有状态
     *
     * @return ReturnObject
     */
    public ReturnObject getShareState() {
        return shareActivityDao.getShareState();
    }

    /**
     * 显示所有状态的分享活动
     *
     * @param shopId    店铺Id
     * @param productId 货品
     * @param beginTime 晚于此开始时间
     * @param endTime   早于此结束时间
     * @param state     分享活动状态
     * @param page      页码
     * @param pageSize  每页数目
     * @return
     */
    @Transactional(readOnly = true)
    public ReturnObject getShareByShopId(Long shopId, Long productId, LocalDateTime beginTime,
                                         LocalDateTime endTime, Byte state, Integer page, Integer pageSize) {
        List<Long> shareActivityIds = new ArrayList<>();
        if (productId != null) {
            //TODO:openfeign获得分享活动id
            ReturnObject<PageInfo<SimpleSaleInfoVO>> onSalesByProductId = goodsService.getOnSalesByProductId(productId, 1, 10);
            if (onSalesByProductId != null) {
                long total = onSalesByProductId.getData().getTotal();
                ReturnObject<PageInfo<SimpleSaleInfoVO>> onSalesByProductId2 = goodsService.getOnSalesByProductId(productId, 1, (int) total);
                List<SimpleSaleInfoVO> list = onSalesByProductId2.getData().getList();
                for (SimpleSaleInfoVO simpleSaleInfoVO : list) {
                    if (simpleSaleInfoVO.getShareActId() != null) {
                        shareActivityIds.add(simpleSaleInfoVO.getShareActId());
                    }
                }
            }
        }
        return shareActivityDao.getShareByShopId(shopId, shareActivityIds, beginTime, endTime, state, page, pageSize);
    }

    /**
     * 管理员新增分享活动
     *
     * @param createName       创建者姓名
     * @param createId         创建者id
     * @param shopId           商铺id
     * @param shareActivityDTO 新增商铺内容
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnObject addShareAct(String createName, Long createId,
                                    Long shopId, ShareActivityDTO shareActivityDTO) {
        //TODO:通过商铺id弄到商铺名称
        String shopName = new String();
        if (shopId != null) {
            ReturnObject<ShopInfoVO> shop = shopService.getShop(shopId);
            if (shop == null) {
                return new ReturnObject<>(ReturnNo.FIELD_NOTVALID, "不存在该商铺");
            }
            shopName = shop.getData().getName();
        }
        return shareActivityDao.addShareAct(createName, createId, shopName, shopId, shareActivityDTO);
    }

    /**
     * 查询分享活动 只显示上线状态的分享活动
     *
     * @param shopId    店铺Id
     * @param productId 商铺Id
     * @param beginTime 晚于此开始时间
     * @param endTime   早于此结束时间
     * @param page      页码
     * @param pageSize  每页数目
     * @return
     */
    @Transactional(readOnly = true)
    public ReturnObject getShareActivity(Long shopId, Long productId, LocalDateTime beginTime,
                                         LocalDateTime endTime, Integer page, Integer pageSize) {
        List<Long> shareActivityIds = new ArrayList<>();
        if (productId != null) {
            //TODO:openfeign获得分享活动id
            ReturnObject<PageInfo<SimpleSaleInfoVO>> onSalesByProductId = goodsService.getOnSalesByProductId(productId, 1, 10);
            if (onSalesByProductId != null) {
                long total = onSalesByProductId.getData().getTotal();
                ReturnObject<PageInfo<SimpleSaleInfoVO>> onSalesByProductId2 = goodsService.getOnSalesByProductId(productId, 1, (int) total);
                List<SimpleSaleInfoVO> list = onSalesByProductId2.getData().getList();
                for (SimpleSaleInfoVO simpleSaleInfoVO : list) {
                    if (simpleSaleInfoVO.getShareActId() != null) {
                        shareActivityIds.add(simpleSaleInfoVO.getShareActId());
                        shareActivityIds.add(simpleSaleInfoVO.getShareActId());
                    }
                }
            }
        }
        return shareActivityDao.getShareActivity(shopId, shareActivityIds, beginTime, endTime, page, pageSize);
    }

    /**
     * 查看分享活动详情 只显示上线状态的分享活动
     *
     * @param id 分享活动Id
     * @return
     */
    @Transactional(readOnly = true)
    public ReturnObject getShareActivityById(Long id) {
        return shareActivityDao.getShareActivityById(id);
    }

    /**
     * 查看特定分享活动详情,显示所有状态的分享活动
     *
     * @param shopId 店铺Id
     * @param id     分享活动Id
     * @return
     */
    @Transactional(readOnly = true)
    public ReturnObject getShareActivityByShopIdAndId(Long shopId, Long id) {
        return shareActivityDao.getShareActivityByShopIdAndId(shopId, id);
    }

}
