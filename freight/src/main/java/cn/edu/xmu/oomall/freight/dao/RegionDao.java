package cn.edu.xmu.oomall.freight.dao;

import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.freight.mapper.RegionPoMapper;
import cn.edu.xmu.oomall.freight.util.RedisUtil;
import cn.edu.xmu.oomall.goods.model.bo.Region;
import cn.edu.xmu.oomall.goods.model.po.RegionPo;
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

    private static final Byte STATE_EFFCTIVE=0;

    private static final Byte STATE_SUSPENDED=1;

    private static final Byte STATE_ABANDONED=2;

    @Autowired
    private RegionPoMapper regionPoMapper;

    @Autowired
    private RedisUtil redisUtil;

    public ReturnObject<List<Region>> getParentRegion(RegionPo regionPo) {
        try {
            String key = null;
            if (regionPo.getId() != null) {
                key = "region_" + regionPo.getId();
                List<Region> redisRegions = (List<Region>) redisUtil.get(key);
                if (redisRegions != null) {
                    return new ReturnObject<>(redisRegions);
                }
            }

            List<Region> retRegions = new ArrayList<>(5);
            regionPo = regionPoMapper.selectByPrimaryKey(regionPo.getId());
            while (regionPo != null && regionPo.getPid() != 0) {
                regionPo = regionPoMapper.selectByPrimaryKey(regionPo.getPid());
                if (regionPo != null) {
                    retRegions.add(0, new Region(regionPo));
                }
            }

            if (retRegions.size() != 0) {
                redisUtil.set(key, (Serializable) retRegions, 600);
            }

            return new ReturnObject<>(retRegions);
        } catch (Exception e) {
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    public ReturnObject<Region> createRegion(Region region){

        try {
            RegionPo regionPo = region.gotRegionPo();

            RegionPo parentRegionPo = regionPoMapper.selectByPrimaryKey(regionPo.getPid());
            if (parentRegionPo == null) {
                ReturnObject<Region> retObj = new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
                return retObj;
            }

            if (parentRegionPo.getState().equals(STATE_ABANDONED)) {
                ReturnObject<Region> retObj = new ReturnObject<>(ReturnNo.FREIGHT_REGIONOBSOLETE);
                return retObj;
            }

            regionPoMapper.insertSelective(regionPo);

            return new ReturnObject<>();

        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    public ReturnObject<Object> modiRegion(Region region){

        try {
            RegionPo regionPo = region.gotRegionPo();

            RegionPo rp = regionPoMapper.selectByPrimaryKey(region.getId());
            if (rp == null) {
                ReturnObject<Object> retObj = new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
                return retObj;
            }

            regionPoMapper.insertSelective(regionPo);

            return new ReturnObject<>();

        } catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    public ReturnObject<Object> abandonRegion(Region region) {

        try {
            ReturnObject<Object> retObj;
            RegionPo regionPo = region.gotRegionPo();

            RegionPo rp=regionPoMapper.selectByPrimaryKey(region.getId());
            if (rp == null) {
                retObj = new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
                return retObj;
            }
            if (rp.getState().equals(STATE_EFFCTIVE)) {
                retObj = new ReturnObject<>(ReturnNo.STATENOTALLOW);
                return retObj;
            }

            regionPoMapper.updateByPrimaryKeySelective(regionPo);
            redisUtil.del("region_" + region.getId());

            return new ReturnObject<>();

        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    public ReturnObject<Object> suspendRegion(Region region) {

        try {
            ReturnObject<Object> retObj;
            RegionPo regionPo = region.gotRegionPo();

            RegionPo rp=regionPoMapper.selectByPrimaryKey(region.getId());
            if (rp == null) {
                retObj = new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
                return retObj;
            }
            if (rp.getState().equals(STATE_ABANDONED)) {
                retObj = new ReturnObject<>(ReturnNo.STATENOTALLOW);
                return retObj;
            }

            regionPoMapper.updateByPrimaryKeySelective(regionPo);
            redisUtil.del("region_" + region.getId());

            return new ReturnObject<>();

        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    public ReturnObject<Object> resumeRegion(Region region) {

        try {
            ReturnObject<Object> retObj;
            RegionPo regionPo = region.gotRegionPo();

            RegionPo rp=regionPoMapper.selectByPrimaryKey(region.getId());
            if (rp == null) {
                retObj = new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
                return retObj;
            }
            if (rp.getState().equals(STATE_ABANDONED)) {
                retObj = new ReturnObject<>(ReturnNo.STATENOTALLOW);
                return retObj;
            }

            regionPoMapper.updateByPrimaryKeySelective(regionPo);

            return new ReturnObject<>();

        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }
}
