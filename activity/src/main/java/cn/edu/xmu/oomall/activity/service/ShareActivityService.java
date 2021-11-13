package cn.edu.xmu.oomall.activity.service;

import cn.edu.xmu.oomall.activity.dao.ShareActivityDao;
import cn.edu.xmu.oomall.activity.model.vo.ShareActivityDTO;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author: xiuchen lang 22920192204222
 * @Date: 2021/11/12 12:55
 */
@Service
public class ShareActivityService {

    @Autowired
    private ShareActivityDao shareActivityDao;

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
     * @param shopId 店铺Id
     * @param shareId 相关的分享活动id
     * @param beginTime 晚于此开始时间
     * @param endTime 早于此结束时间
     * @param state 分享活动状态
     * @param page 页码
     * @param pageSize 每页数目
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject getShareByShopId(Long shopId, Long shareId, LocalDateTime beginTime,
                                         LocalDateTime endTime, Byte state, Integer page, Integer pageSize) {
        return shareActivityDao.getShareByShopId(shopId, shareId, beginTime, endTime, state, page, pageSize);
    }

    /**
     *管理员新增分享活动
     * @param createName 创建者姓名
     * @param createId  创建者id
     * @param shopName 商铺名字
     * @param shopId 商铺id
     * @param shareActivityDTO 新增商铺内容
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject addShareAct(String createName, Long createId, String shopName,
                                    Long shopId, ShareActivityDTO shareActivityDTO) {
        return shareActivityDao.addShareAct(createName, createId, shopName, shopId, shareActivityDTO);
    }

    /**
     * 查询分享活动 只显示上线状态的分享活动
     * @param shopId 店铺Id
     * @param shareId 分享活动id
     * @param beginTime 晚于此开始时间
     * @param endTime 早于此结束时间
     * @param page 页码
     * @param pageSize 每页数目
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject getShareActivity(Long shopId, Long shareId, LocalDateTime beginTime,
                                         LocalDateTime endTime, Integer page, Integer pageSize) {
        return shareActivityDao.getShareActivity(shopId, shareId, beginTime, endTime, page, pageSize);
    }

    /**
     *查看分享活动详情 只显示上线状态的分享活动
     * @param id 分享活动Id
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject getShareActivityById(Long id){
        return shareActivityDao.getShareActivityById(id);
    }

    /**
     * 查看特定分享活动详情,显示所有状态的分享活动
     * @param shopId 店铺Id
     * @param id 分享活动Id
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject getShareActivityByShopIdAndId(Long shopId,Long id){
        return shareActivityDao.getShareActivityByShopIdAndId(shopId,id);
    }

}
