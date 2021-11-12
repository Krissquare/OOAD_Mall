package cn.edu.xmu.oomall.freight.service;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.freight.dao.RegionDao;
import cn.edu.xmu.oomall.goods.model.bo.Region;
import cn.edu.xmu.oomall.goods.model.po.RegionPo;
import cn.edu.xmu.oomall.goods.model.vo.RegionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ziyi guo
 * @date 2021/11/10
 */
@Service
public class RegionService {

    private static final Byte STATE_EFFCTIVE=0;

    private static final Byte STATE_SUSPENDED=1;

    private static final Byte STATE_ABANDONED=2;

    @Autowired
    private RegionDao regionDao;

    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<List<Region>> getParentRegion(Long id) {

        RegionPo regionPo = new RegionPo();
        regionPo.setId(id);

        ReturnObject<List<Region>> retRegion;

        ReturnObject<List<Region>> returnObject = regionDao.getParentRegion(regionPo);

        if (returnObject.getCode().equals(ReturnNo.OK)) {
            retRegion = new ReturnObject<>(returnObject.getData());
        }else{
            retRegion = new ReturnObject<>(returnObject.getCode(), returnObject.getErrmsg());
        }
        return retRegion;
    }

    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<VoObject> createRegion(RegionVo regionVo, Long pid, Long userId, String userName) {

        Region region = regionVo.createRegion();
        region.setPid(pid);
        region.setState(STATE_EFFCTIVE);
        Common.setPoCreatedFields(region.gotRegionPo(), userId, userName);

        ReturnObject<Region> retObj = regionDao.createRegion(region);
        
        ReturnObject<VoObject> retRegion;
        if (retObj.getCode().equals(ReturnNo.OK)) {
            retRegion = new ReturnObject<>(retObj.getData());
        }else{
            retRegion = new ReturnObject<>(retObj.getCode(), retObj.getErrmsg());
        }

        return retRegion;
    }

    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<List<Region>> adminGetChildRegion(Long id) {

        RegionPo regionPo = new RegionPo();
        regionPo.setId(id);

        ReturnObject<List<Region>> retRegion;

        ReturnObject<List<Region>> returnObject = regionDao.adminGetChildRegion(regionPo);

        if (returnObject.getCode().equals(ReturnNo.OK)) {
            retRegion = new ReturnObject<>(returnObject.getData());
        }else{
            retRegion = new ReturnObject<>(returnObject.getCode(), returnObject.getErrmsg());
        }
        return retRegion;
    }

    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<List<Region>> getChildRegion(Long id) {

        RegionPo regionPo = new RegionPo();
        regionPo.setId(id);

        ReturnObject<List<Region>> retRegion;

        ReturnObject<List<Region>> returnObject = regionDao.getChildRegion(regionPo);

        if (returnObject.getCode().equals(ReturnNo.OK)) {
            retRegion = new ReturnObject<>(returnObject.getData());
        }else{
            retRegion = new ReturnObject<>(returnObject.getCode(), returnObject.getErrmsg());
        }
        return retRegion;
    }

    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<Object> modifyRegion(Long id, RegionVo regionVo, Long userId, String userName) {

        Region region = regionVo.createRegion();
        region.setId(id);
        Common.setPoModifiedFields(region.gotRegionPo(),userId,userName);

        return regionDao.modiRegion(region);
    }

    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<Object> abandonRegion(Long id, Long userId, String userName) {

        Region region=new Region();
        region.setId(id);
        region.setState(STATE_ABANDONED);
        Common.setPoModifiedFields(region.gotRegionPo(),userId,userName);

        return regionDao.abandonRegion(region);
    }

    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<Object> suspendRegion(Long id, Long userId, String userName) {

        Region region=new Region();
        region.setId(id);
        region.setState(STATE_SUSPENDED);
        Common.setPoModifiedFields(region.gotRegionPo(),userId,userName);

        return regionDao.suspendRegion(region);
    }

    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<Object> resumeRegion(Long id, Long userId, String userName) {

        Region region=new Region();
        region.setId(id);
        region.setState(STATE_EFFCTIVE);
        Common.setPoModifiedFields(region.gotRegionPo(),userId,userName);

        return regionDao.resumeRegion(region);
    }

}
