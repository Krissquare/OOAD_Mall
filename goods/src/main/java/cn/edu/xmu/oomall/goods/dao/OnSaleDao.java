package cn.edu.xmu.oomall.goods.dao;

import cn.edu.xmu.oomall.core.util.RedisUtil;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.goods.mapper.OnSalePoMapper;
import cn.edu.xmu.oomall.goods.model.bo.OnSale;
import cn.edu.xmu.oomall.goods.model.po.OnSalePo;
import cn.edu.xmu.oomall.goods.model.po.OnSalePoExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static cn.edu.xmu.oomall.core.util.Common.*;

/**
 * @author yujie lin 22920192204242
 * @date 2021/11/10
 */
@Repository
public class OnSaleDao {
    private Logger logger = LoggerFactory.getLogger(OnSaleDao.class);

    @Autowired
    private OnSalePoMapper onSalePoMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${oomall.goods.onsale.expiretime}")
    private long onSaleTimeout;


    /**
     * 创建Onsale对象
     *
     * @param onSale 传入的Onsale对象
     * @return 返回对象ReturnObj
     */
    public ReturnObject createOnSale(OnSale onSale, Long userId, String userName) {
        try {
            OnSalePo onsalePo = (OnSalePo) cloneVo(onSale, OnSalePo.class);
            setPoCreatedFields(onsalePo, userId, userName);
            onSalePoMapper.insert(onsalePo);
            return new ReturnObject((OnSale) cloneVo(onsalePo, OnSale.class));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }


    public ReturnObject onlineOrOfflineOnSale(OnSale onsale, Long userId, String userName) {
        try {
            // delete redis if exist
            redisUtil.del("o_" + onsale.getId());

            OnSalePo po = (OnSalePo) cloneVo(onsale, OnSalePo.class);
            setPoModifiedFields(po, userId, userName);
            onSalePoMapper.updateByPrimaryKeySelective(po);
            return new ReturnObject();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }

    }

    public ReturnObject onlineOrOfflineOnSaleAct(Long actId, Long userId, String userName, OnSale.State cntState, OnSale.State finalState) {
        try {

            OnSalePoExample oe = new OnSalePoExample();
            OnSalePoExample.Criteria cr = oe.createCriteria();
            cr.andActivityIdEqualTo(actId);
            Byte s1 = cntState.getCode().byteValue();
            cr.andStateEqualTo(s1);

            Byte s2 = finalState.getCode().byteValue();
            List<OnSalePo> pos = onSalePoMapper.selectByExample(oe);

            for (OnSalePo po : pos) {
                // delete redis if exist
                redisUtil.del("o_" + po.getId());
                po.setState(s2);
                setPoModifiedFields(po, userId, userName);

                if(finalState== OnSale.State.OFFLINE){
                    //如果结束时间晚于当前时间且开始时间早于当前时间，修改结束时间为当前时间
                    if (po.getEndTime().isAfter(LocalDateTime.now()) && po.getBeginTime().isBefore(LocalDateTime.now())) {
                        po.setEndTime(LocalDateTime.now());
                    }
                }
                else if(finalState== OnSale.State.ONLINE) {
                    //如果开始时间早于当前时间且结束时间晚于当前时间，修改开始时间为当前时间
                    if (po.getBeginTime().isBefore(LocalDateTime.now()) && po.getEndTime().isAfter(LocalDateTime.now())) {
                        po.setBeginTime(LocalDateTime.now());
                    }
                }

                onSalePoMapper.updateByPrimaryKeySelective(po);
            }
            return new ReturnObject();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }

    }

    public OnSale getOnSaleById(Long id) {
        try {
            // 先从redis中取
            String key = "o_" + id;
            OnSale retO = (OnSale) redisUtil.get(key);
            if (null != retO) {
                return retO;
            }

            OnSalePo po = onSalePoMapper.selectByPrimaryKey(id);
            if (po == null) {
                return null;
            }
            // 存入redis
            OnSale ret = (OnSale) cloneVo(po, OnSale.class);
            redisUtil.set("o_" + ret.getId(), ret, onSaleTimeout);

            return ret;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

    }

    public ReturnObject deleteOnSale(Long id) {
        try {
            // delete redis if exist
            redisUtil.del("o_" + id);
            onSalePoMapper.deleteByPrimaryKey(id);
            return new ReturnObject(ReturnNo.OK);
        } catch (Exception e) {
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }


    public ReturnObject deleteOnSaleAct(Long actId) {
        try {
            OnSalePoExample oe = new OnSalePoExample();
            OnSalePoExample.Criteria cr = oe.createCriteria();
            cr.andActivityIdEqualTo(actId);
            cr.andStateEqualTo(OnSale.State.DRAFT.getCode().byteValue());
            List<OnSalePo> pos = onSalePoMapper.selectByExample(oe);
            for (OnSalePo po : pos) {
                redisUtil.del("o_" + po.getId());
                onSalePoMapper.deleteByPrimaryKey(po.getId());
            }
            return new ReturnObject(ReturnNo.OK);
        } catch (Exception e) {
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }

    public boolean onSaleShopMatch(Long id, Long shopId) {
        try {

            OnSalePoExample oe = new OnSalePoExample();

            OnSalePoExample.Criteria cr = oe.createCriteria();
            cr.andIdEqualTo(id);
            cr.andShopIdEqualTo(shopId);
            List<OnSalePo> l1 = onSalePoMapper.selectByExample(oe);
            if (l1.size() > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }


    public boolean timeCollided(OnSale onsale) {
        try {

            OnSalePoExample oe = new OnSalePoExample();

            OnSalePoExample.Criteria cr = oe.createCriteria();
            cr.andProductIdEqualTo(onsale.getProductId());
            List<OnSalePo> l1 = onSalePoMapper.selectByExample(oe);
            for (OnSalePo op : l1) {
                if (!(op.getEndTime().isBefore(onsale.getBeginTime())
                        || op.getBeginTime().isAfter(onsale.getEndTime()))) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return true;
        }

    }


    public ReturnObject updateOnSale(OnSale onsale, Long userId, String userName) {
        try {
            // delete redis if exist
            redisUtil.del("o_" + onsale.getId());

            OnSalePo po = (OnSalePo) cloneVo(onsale, OnSalePo.class);
            setPoModifiedFields(po, userId, userName);
            onSalePoMapper.updateByPrimaryKey(po);
            return new ReturnObject(ReturnNo.OK);
        } catch (Exception e) {
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }
}
