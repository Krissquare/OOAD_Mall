package cn.edu.xmu.oomall.freight.dao;

import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.freight.mapper.RegionPoMapper;
import cn.edu.xmu.oomall.freight.util.RedisUtil;
import cn.edu.xmu.oomall.goods.model.bo.Region;
import cn.edu.xmu.oomall.goods.model.po.RegionPo;
import cn.edu.xmu.oomall.goods.model.po.RegionPoExample;
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

    public ReturnObject<List<Region>> getParentRegion(Long id) {
        try {
            RegionPo regionPo = new RegionPo();
            regionPo.setId(id);

            String key = null;
            if (regionPo.getId() != null) {
                key = "parent_region_" + regionPo.getId();
                List<Region> redisRegions = (List<Region>) redisUtil.get(key);
                if (redisRegions != null) {
                    return new ReturnObject<>(redisRegions);
                }
            }

            List<Region> retRegions = new ArrayList<>(5);
            regionPo = regionPoMapper.selectByPrimaryKey(regionPo.getId());

            if (regionPo == null) {
                ReturnObject<List<Region>> retObj = new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
                return retObj;
            }

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

    public ReturnObject<Region> createRegion(Region region, Long userId, String userName){

        try {
            RegionPo regionPo = region.gotRegionPo();
            Common.setPoCreatedFields(regionPo, userId, userName);

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

    public ReturnObject<List<Region>> adminGetChildRegion(Long id) {
        try {
            RegionPo regionPo = new RegionPo();
            regionPo.setId(id);
            List<Region> retRegions = new ArrayList<>();
            regionPo = regionPoMapper.selectByPrimaryKey(regionPo.getId());

            if (regionPo == null) {
                ReturnObject<List<Region>> retObj = new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
                return retObj;
            }

            RegionPoExample example=new RegionPoExample();
            RegionPoExample.Criteria criteria=example.createCriteria();
            criteria.andPidEqualTo(regionPo.getId());

            List<RegionPo> regionPos=regionPoMapper.selectByExample(example);

            for(RegionPo rp:regionPos){
                Region r=new Region(rp);
                retRegions.add(r);
            }

            return new ReturnObject<>(retRegions);

        } catch (Exception e) {
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    public ReturnObject<List<Region>> getChildRegion(Long id) {
        try {
            RegionPo regionPo = new RegionPo();
            regionPo.setId(id);
            List<Region> retRegions = new ArrayList<>();
            regionPo = regionPoMapper.selectByPrimaryKey(regionPo.getId());
            String key="child_region_"+regionPo.getId();
            String subKey;

            if (regionPo == null) {
                ReturnObject<List<Region>> retObj = new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
                return retObj;
            }

            if (regionPo.getState().equals(STATE_ABANDONED)) {
                ReturnObject<List<Region>> retObj = new ReturnObject<>(ReturnNo.FREIGHT_REGIONOBSOLETE);
                return retObj;
            }

            //redis
            List<Region> redisRegions = (List<Region>) redisUtil.get(key);
            if (redisRegions != null) {
                return new ReturnObject<>(redisRegions);
            }

            RegionPoExample example=new RegionPoExample();
            RegionPoExample.Criteria criteria=example.createCriteria();
            criteria.andPidEqualTo(regionPo.getId());
            criteria.andStateEqualTo(STATE_EFFCTIVE);

            List<RegionPo> regionPos=regionPoMapper.selectByExample(example);

            for(RegionPo rp:regionPos){
                subKey="sub_"+rp.getId();
                redisUtil.set(subKey,regionPo.getId(),600);
                Region r=new Region(rp);
                retRegions.add(r);
            }
            redisUtil.set(key, (Serializable)retRegions,600);

            return new ReturnObject<>(retRegions);

        } catch (Exception e) {
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    public ReturnObject<Object> modiRegion(Region region, Long userId, String userName){

        try {
            RegionPo regionPo = region.gotRegionPo();
            Common.setPoModifiedFields(regionPo,userId,userName);

            RegionPo rp = regionPoMapper.selectByPrimaryKey(region.getId());
            if (rp == null) {
                ReturnObject<Object> retObj = new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
                return retObj;
            }

            regionPoMapper.insertSelective(regionPo);

            deleteRedis(region.getId());

            return new ReturnObject<>();

        } catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    public ReturnObject<Object> abandonRegion(Region region, Long userId, String userName) {

        try {
            ReturnObject<Object> retObj;
            RegionPo regionPo = region.gotRegionPo();
            Common.setPoModifiedFields(regionPo,userId,userName);

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
            redisUtil.del("child_region_" + region.getId());
            deleteRedis(region.getId());

            return new ReturnObject<>();

        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    public ReturnObject<Object> suspendRegion(Region region, Long userId, String userName) {

        try {
            ReturnObject<Object> retObj;
            RegionPo regionPo = region.gotRegionPo();
            Common.setPoModifiedFields(regionPo,userId,userName);

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
            deleteRedis(region.getId());

            return new ReturnObject<>();

        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    public ReturnObject<Object> resumeRegion(Region region, Long userId, String userName) {

        try {
            ReturnObject<Object> retObj;
            RegionPo regionPo = region.gotRegionPo();
            Common.setPoModifiedFields(regionPo,userId,userName);

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
            deleteRedis(region.getId());

            return new ReturnObject<>();

        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    private void deleteRedis(Long id){
        String subKey="sub_"+id;
        // 若对应父id有记录，去redis中删除那条记录
        if (redisUtil.get(subKey)!=null){
            redisUtil.del("child_region_"+redisUtil.get(subKey));
        }
        redisUtil.del(subKey);
    }

}
