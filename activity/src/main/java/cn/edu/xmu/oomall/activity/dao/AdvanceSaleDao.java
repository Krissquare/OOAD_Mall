package cn.edu.xmu.oomall.activity.dao;

import cn.edu.xmu.oomall.activity.mapper.AdvanceSalePoMapper;
import cn.edu.xmu.oomall.activity.model.bo.AdvanceSale;
import cn.edu.xmu.oomall.activity.model.po.AdvanceSalePo;
import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.RedisUtil;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author GXC 22920192204194
 */
@Repository
@Slf4j
public class AdvanceSaleDao {

    @Autowired
    AdvanceSalePoMapper advanceSalePoMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${oomall.activity.advancesale.expiretime}")
    private long categoryTimeout;

    private static Logger logger = LoggerFactory.getLogger(Common.class);
    /**
     * 根据id查找预售活动
     * @param id
     * @return
     */
    public ReturnObject selectAdvanceSaleByKey(Long id){
        ReturnObject returnObject=null;
        try{
            AdvanceSalePo po=null;
            AdvanceSale bo=(AdvanceSale) redisUtil.get("AdvanceSaleId"+id);
            if(bo!=null) {
                po=(AdvanceSalePo) Common.cloneVo(bo,AdvanceSalePo.class);
            }
            else {
                po=advanceSalePoMapper.selectByPrimaryKey(id);
            }
            returnObject=new ReturnObject(po);
        }catch(Exception e){
            logger.error(e.toString());
            return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
        return returnObject;
    }
    /**
     * 更新预售活动
     * @param po
     * @return
     */
    public ReturnObject updateAdvanceSale(AdvanceSalePo po){
        ReturnObject returnObject=null;
        try{
            redisUtil.del("AdvanceSaleId"+po.getId());
            advanceSalePoMapper.updateByPrimaryKeySelective(po);
            returnObject=new ReturnObject();
        }catch(Exception e){
            logger.error(e.toString());
            return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
        return returnObject;
    }
    /**
     * 物理删除预售活动
     * @param id
     * @return
     */
    public ReturnObject deleteAdvanceSale(Long id){
        ReturnObject returnObject=null;
        try{
            redisUtil.del("AdvanceSaleId"+id);
            advanceSalePoMapper.deleteByPrimaryKey(id);
            returnObject=new ReturnObject();
        }catch(Exception e){
            logger.error(e.toString());
            return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
        return returnObject;
    }

}
