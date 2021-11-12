package cn.edu.xmu.oomall.freight.dao;

import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.freight.mapper.RegionPoMapper;
import cn.edu.xmu.oomall.freight.util.RedisUtil;
import cn.edu.xmu.oomall.goods.model.bo.Region;
import cn.edu.xmu.oomall.goods.model.po.RegionPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ziyi guo
 * @date 2021/11/10
 */
@Repository
public class RegionDao {

    private final Byte STATE_EFFCTIVE=0;

    private final Byte STATE_SUSPENDED=1;

    private final Byte STATE_ABANDONED=2;

    private Logger logger = LoggerFactory.getLogger(RegionDao.class);

    @Autowired
    private RegionPoMapper regionPoMapper;

    @Autowired
    private RedisUtil redisUtil;

    public ReturnObject<List<Region>> getParentRegion(RegionPo regionPo){

        logger.info("findParentRegion: RegionPo =" + regionPo);

        String key = null;
        if (regionPo.getId() != null ){
            key = "region_"+regionPo.getId();
            List<Region> redisRegions = (List<Region>) redisUtil.get(key);
            if (redisRegions!=null){
                logger.info("findParentRegion: hit redis cache, key = "+key);
                return new ReturnObject<>(redisRegions);
            }
        }

        List<Region> retRegions = new ArrayList<>();
        regionPo=regionPoMapper.selectById(regionPo.getId());
        while(true)
        {
            if(regionPo.getPid()==0) {
                break;
            }
            regionPo=regionPoMapper.selectParent(regionPo);
            retRegions.add(0,new Region(regionPo));
        }
        logger.info("findParentRegion: retRegions =" + retRegions);

        if (retRegions.size()!=0){
            logger.info("findParentRegion: put into redis cache, key = "+key);
            redisUtil.set(key, (Serializable) retRegions, 600);
        }

        return new ReturnObject<>(retRegions);
    }

    public ReturnObject<Region> createRegion(Region region){

        RegionPo regionPo = region.gotRegionPo();
        Byte state=regionPoMapper.getStateById(region.getPid());
        if(state.equals(STATE_ABANDONED))
        {
            ReturnObject<Region> retObj = new ReturnObject<>(ReturnNo.FREIGHT_REGIONOBSOLETE);
            return retObj;
        }
        Long ret = regionPoMapper.createRegion(regionPo);
        ReturnObject<Region> retObj = null;
        if (ret == 0 ){
            retObj = new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
        } else {
            retObj = new ReturnObject<>();
        }
        return retObj;
    }

    public ReturnObject<Object> modiRegion(Region region){
        RegionPo regionPo = region.gotRegionPo();
        ReturnObject<Object> retObj = null;
        int ret = regionPoMapper.updateRegion(regionPo);
        if (ret == 0 ){
            retObj = new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
        } else {
            retObj = new ReturnObject<>();
        }
        return retObj;
    }

    public ReturnObject<Object> abandonRegion(Region region) {
        ReturnObject<Object> retObj = null;
        RegionPo regionPo = region.gotRegionPo();
        Byte state=regionPoMapper.getStateById(region.getId());
        if(state==0)
        {
            retObj = new ReturnObject<>(ReturnNo.STATENOTALLOW);
            return retObj;
        }
        int ret = regionPoMapper.abandonRegion(regionPo);
        if (ret == 0) {
            retObj = new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
        } else {
            redisUtil.del("region_"+region.getId());
            retObj = new ReturnObject<>();
        }
        return retObj;
    }

    public ReturnObject<Object> suspendRegion(Region region) {
        ReturnObject<Object> retObj;
        RegionPo regionPo = region.gotRegionPo();
        Byte state=regionPoMapper.getStateById(region.getId());
        if(state.equals(STATE_ABANDONED))
        {
            retObj = new ReturnObject<>(ReturnNo.STATENOTALLOW);
            return retObj;
        }
        int ret = regionPoMapper.suspendRegion(regionPo);
        if (ret == 0) {
            retObj = new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
        } else {
            redisUtil.del("region_"+region.getId());
            retObj = new ReturnObject<>();
        }
        return retObj;
    }

    public ReturnObject<Object> resumeRegion(Region region) {
        ReturnObject<Object> retObj = null;
        RegionPo regionPo = region.gotRegionPo();
        Byte state=regionPoMapper.getStateById(region.getId());
        if(state.equals(STATE_ABANDONED))
        {
            retObj = new ReturnObject<>(ReturnNo.STATENOTALLOW);
            return retObj;
        }
        int ret = regionPoMapper.resumeRegion(regionPo);
        if (ret == 0) {
            retObj = new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
        } else {
            retObj = new ReturnObject<>();
        }
        return retObj;
    }
}
