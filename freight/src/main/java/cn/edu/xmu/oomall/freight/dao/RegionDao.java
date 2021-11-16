package cn.edu.xmu.oomall.freight.dao;

import cn.edu.xmu.oomall.core.util.*;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.freight.mapper.RegionPoMapper;
import cn.edu.xmu.oomall.core.util.RedisUtil;
import cn.edu.xmu.oomall.freight.model.bo.Region;
import cn.edu.xmu.oomall.freight.model.po.RegionPo;
import cn.edu.xmu.oomall.freight.model.po.RegionPoExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private String redisKeyForParentRegion = "parentRegion_";
    private String redisKeyForSubList = "subRegion_";
    private String redisKeyForChildRegion = "childRegion_";

    @Value("${oomall.freight.region.expiretime}")
    private long regionRedisTimeout;

    @Autowired
    private RegionPoMapper regionPoMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 通过id查找所有上级地区
     * @param id
     * @return ReturnObject
     */
    public ReturnObject getParentRegion(Long id) {
        try {
            RegionPo regionPo = new RegionPo();
            regionPo.setId(id);

            //查redis
            String key = redisKeyForParentRegion + id;
            List<Region> redisRegions = (List<Region>) redisUtil.get(key);
            if (null != redisRegions) {
                return new ReturnObject(redisRegions);
            }

            List<Region> retRegions = new ArrayList<>(5);
            regionPo = regionPoMapper.selectByPrimaryKey(id);
            if (regionPo == null) {
                ReturnObject<List<Region>> retObj = new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
                return retObj;
            }

            while (null != regionPo && regionPo.getPid() != 0) {
                regionPo = regionPoMapper.selectByPrimaryKey(regionPo.getPid());
                if (null != regionPo) {
                    retRegions.add(0, (Region) Common.cloneVo(regionPo,Region.class) );
                }
            }

            //存redis
            if (retRegions.size() != 0) {
                redisUtil.set(key, (Serializable) retRegions, regionRedisTimeout);
            }

            return new ReturnObject(retRegions);

        } catch (Exception e) {
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }

    /**
     * 创建地区
     * @param regionPo,userId,userName
     * @return ReturnObject
     */
    public ReturnObject createRegion(RegionPo regionPo, Long userId, String userName){

        try {
            Common.setPoCreatedFields(regionPo, userId, userName);

            RegionPo parentRegionPo = regionPoMapper.selectByPrimaryKey(regionPo.getPid());
            if (parentRegionPo == null) {
                ReturnObject retObj = new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
                return retObj;
            }
            if (parentRegionPo.getState().equals(STATE_ABANDONED)) {
                ReturnObject retObj = new ReturnObject(ReturnNo.FREIGHT_REGIONOBSOLETE);
                return retObj;
            }

            regionPoMapper.insertSelective(regionPo);

            return new ReturnObject();

        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }

    /**
     * 管理员根据id查询子地区
     * @param id
     * @return ReturnObject
     */
    public ReturnObject adminGetChildRegion(Long id) {
        try {
            RegionPo regionPo = new RegionPo();
            regionPo.setId(id);
            List<Region> retRegions = new ArrayList<>();
            regionPo = regionPoMapper.selectByPrimaryKey(id);

            if (regionPo == null) {
                ReturnObject<List> retObj = new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
                return retObj;
            }

            RegionPoExample example=new RegionPoExample();
            RegionPoExample.Criteria criteria=example.createCriteria();
            criteria.andPidEqualTo(regionPo.getId());

            List<RegionPo> regionPos=regionPoMapper.selectByExample(example);
            for(RegionPo rp:regionPos){
                Region r = (Region) Common.cloneVo(rp,Region.class);
                retRegions.add(r);
            }

            return new ReturnObject(retRegions);

        } catch (Exception e) {
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }

    /**
     * 根据id查询子地区(只返回有效地区)
     * @param id
     * @return ReturnObject
     */
    public ReturnObject getChildRegion(Long id) {
        try {
            RegionPo regionPo = new RegionPo();
            regionPo.setId(id);
            List<Region> retRegions = new ArrayList<>();
            regionPo = regionPoMapper.selectByPrimaryKey(id);

            if (regionPo == null) {
                ReturnObject retObj = new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
                return retObj;
            }
            if (regionPo.getState().equals(STATE_ABANDONED)) {
                ReturnObject retObj = new ReturnObject(ReturnNo.FREIGHT_REGIONOBSOLETE);
                return retObj;
            }

            //redis
            String key = redisKeyForChildRegion + id;
            String subKey;
            List<Region> redisRegions = (List<Region>) redisUtil.get(key);
            if (redisRegions != null) {
                return new ReturnObject(redisRegions);
            }

            RegionPoExample example=new RegionPoExample();
            RegionPoExample.Criteria criteria=example.createCriteria();
            criteria.andPidEqualTo(regionPo.getId());
            criteria.andStateEqualTo(STATE_EFFCTIVE);

            List<RegionPo> regionPos=regionPoMapper.selectByExample(example);
            for(RegionPo rp:regionPos){
                subKey = redisKeyForSubList + rp.getId();
                redisUtil.set(subKey,id,regionRedisTimeout);
                Region r = (Region) Common.cloneVo(rp,Region.class);
                retRegions.add(r);
            }
            redisUtil.set(key, (Serializable)retRegions,regionRedisTimeout);

            return new ReturnObject(retRegions);

        } catch (Exception e) {
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }

    /**
     * 管理员修改地区
     * @param regionPo,userId,userName
     * @return ReturnObject
     */
    public ReturnObject modiRegion(RegionPo regionPo, Long userId, String userName){

        try {
            Common.setPoModifiedFields(regionPo,userId,userName);

            RegionPo rp = regionPoMapper.selectByPrimaryKey(regionPo.getId());
            if (rp == null) {
                ReturnObject retObj = new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
                return retObj;
            }

            regionPoMapper.updateByPrimaryKeySelective(regionPo);
            deleteRedis(regionPo.getId());

            return new ReturnObject();

        } catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }

    /**
     * 管理员废弃地区
     * @param regionPo,userId,userName
     * @return ReturnObject
     */
    public ReturnObject abandonRegion(RegionPo regionPo, Long userId, String userName) {

        try {
            Common.setPoModifiedFields(regionPo,userId,userName);

            RegionPo rp=regionPoMapper.selectByPrimaryKey(regionPo.getId());
            if (rp == null) {
                ReturnObject retObj = new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
                return retObj;
            }
            if (rp.getState().equals(STATE_EFFCTIVE)) {
                ReturnObject retObj = new ReturnObject(ReturnNo.STATENOTALLOW);
                return retObj;
            }

            regionPoMapper.updateByPrimaryKeySelective(regionPo);
            redisUtil.del(redisKeyForChildRegion + regionPo.getId());
            deleteRedis(regionPo.getId());

            return new ReturnObject();

        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }

    /**
     * 管理员停用或恢复地区
     * @param regionPo,userId,userName
     * @return ReturnObject
     */
    public ReturnObject modiStateRegion(RegionPo regionPo, Long userId, String userName) {

        try {
            Common.setPoModifiedFields(regionPo,userId,userName);

            RegionPo rp=regionPoMapper.selectByPrimaryKey(regionPo.getId());
            if (rp == null) {
                ReturnObject retObj = new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
                return retObj;
            }
            if (rp.getState().equals(STATE_ABANDONED)) {
                ReturnObject retObj = new ReturnObject(ReturnNo.STATENOTALLOW);
                return retObj;
            }

            regionPoMapper.updateByPrimaryKeySelective(regionPo);
            deleteRedis(regionPo.getId());

            return new ReturnObject();

        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }

    private void deleteRedis(Long id){
        String subKey = redisKeyForSubList + id;
        // 若对应父id有记录，去redis中删除那条记录
        if (redisUtil.get(subKey)!=null){
            redisUtil.del(redisKeyForChildRegion + redisUtil.get(subKey) );
        }
        redisUtil.del(subKey);
    }

}
