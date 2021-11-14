package cn.edu.xmu.oomall.goods.dao;

import cn.edu.xmu.oomall.core.util.RedisUtil;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.goods.mapper.OnSalePoMapper;
import cn.edu.xmu.oomall.goods.model.bo.OnSale;
import cn.edu.xmu.oomall.goods.model.po.OnSalePo;
import cn.edu.xmu.oomall.goods.model.po.OnSalePoExample;
import com.alibaba.druid.sql.ast.statement.SQLForeignKeyImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
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
        try{
            OnSalePo onsalePo = (OnSalePo) cloneVo(onSale, OnSalePo.class);
            setPoCreatedFields(onsalePo, userId, userName);
            onSalePoMapper.insert(onsalePo);
            return new ReturnObject((OnSale) cloneVo(onsalePo, OnSale.class));
        }
        catch(Exception e){
            logger.error(e.getMessage());
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }


    public ReturnObject onlineOrOfflineOnSale(OnSale onsale, Long userId, String userName) {
        try{
            // delete redis if exist
            redisUtil.del("o_"+onsale.getId());

            OnSalePo po = (OnSalePo) cloneVo(onsale, OnSalePo.class);
            setPoModifiedFields(po, userId, userName);
            onSalePoMapper.updateByPrimaryKeySelective(po);
            return new ReturnObject();
        }
        catch(Exception e){
            logger.error(e.getMessage());
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }

    }

    public OnSale getOnSaleById(Long id) {
        try{
            // 先从redis中取
            String key="o_"+id;
            OnSale retO= (OnSale) redisUtil.get(key);
            if(null!=retO){
                return retO;
            }

            OnSalePo po = onSalePoMapper.selectByPrimaryKey(id);
            if(po == null) {
                return null;
            }
            // 存入redis
            OnSale ret=(OnSale) cloneVo(po, OnSale.class);
            redisUtil.set("o_"+ret.getId(),ret,onSaleTimeout);

            return ret;
        }
        catch(Exception e){
            logger.error(e.getMessage());
            return null;
        }

    }


    public ReturnObject searchOnSaleByProductNorSec(Long productId, Integer page, Integer pageSize) {
        try{
            PageHelper.startPage(page, pageSize);

            OnSalePoExample oe=new OnSalePoExample();
            OnSalePoExample.Criteria cr=oe.createCriteria();
            cr.andProductIdEqualTo(productId);
            Byte t1=(byte)(0XFF&OnSale.Type.NOACTIVITY.getCode());
            Byte t2=(byte)(0XFF&OnSale.Type.SECKILL.getCode());
            List<Byte> target= new ArrayList<>();
            target.add(t1);
            target.add(t2);
            cr.andTypeIn(target);
            List<OnSalePo> pos = onSalePoMapper.selectByExample(oe);

            return getBoAndReturn(pos);
        }
        catch(Exception e){
            logger.error(e.getMessage());
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }

    }

    public ReturnObject searchOnSaleByProduct(Long productId, Integer page, Integer pageSize) {
        try{
            PageHelper.startPage(page, pageSize);


            OnSalePoExample oe=new OnSalePoExample();
            OnSalePoExample.Criteria cr=oe.createCriteria();
            cr.andProductIdEqualTo(productId);

            List<OnSalePo> pos = onSalePoMapper.selectByExample(oe);

            return getBoAndReturn(pos);
        }
        catch(Exception e){
            logger.error(e.getMessage());
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }

    }

    public ReturnObject searchOnSaleByActivity(Long actId, Integer page, Integer pageSize, Integer state, OnSale.Type type) {
        try{
            PageHelper.startPage(page, pageSize);

            OnSalePoExample oe=new OnSalePoExample();
            OnSalePoExample.Criteria cr=oe.createCriteria();
            cr.andActivityIdEqualTo(actId);
            if(state!=null) {
                Byte s1;
                s1=(byte)(0XFF&state);
                cr.andStateEqualTo(s1);
            }

            List<OnSalePo> pos = onSalePoMapper.selectByExample(oe);
            return getBoAndReturn(pos);
        }
        catch(Exception e){
            logger.error(e.getMessage());
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    public ReturnObject searchOnSaleByShare(Long actId, Integer page, Integer pageSize, Integer state) {
        try{
            PageHelper.startPage(page, pageSize);

            OnSalePoExample oe=new OnSalePoExample();
            OnSalePoExample.Criteria cr=oe.createCriteria();
            cr.andShareActIdEqualTo(actId);

            if(state!=null) {
                Byte s1;
                s1=(byte)(0XFF&state);
                cr.andStateEqualTo(s1);
            }

            List<OnSalePo> pos = onSalePoMapper.selectByExample(oe);

            return getBoAndReturn(pos);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }

    }

    private ReturnObject getBoAndReturn(List<OnSalePo> pos) {
        try{
            List<OnSale> onSales = new ArrayList<>();
            for (OnSalePo onSalePo : pos) {
                onSales.add((OnSale) cloneVo(onSalePo,OnSale.class));
            }
            PageInfo<OnSale> pageInfo = PageInfo.of(onSales);
            return new ReturnObject(pageInfo);
        }
        catch(Exception e){
            logger.error(e.getMessage());
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }

    }


    public boolean timeCollided(OnSale onsale) {
        try{

            OnSalePoExample oe=new OnSalePoExample();

            OnSalePoExample.Criteria cr=oe.createCriteria();
            cr.andProductIdEqualTo(onsale.getProductId());
            List<OnSalePo> l1= onSalePoMapper.selectByExample(oe);
            for(OnSalePo op:l1){
                if(!(op.getEndTime().isBefore(onsale.getBeginTime())
                    ||op.getBeginTime().isAfter(onsale.getEndTime()))){
                    return true;
                }
            }
            return false;
        }
        catch(Exception e){
            logger.error(e.getMessage());
            return true;
        }

    }
}
