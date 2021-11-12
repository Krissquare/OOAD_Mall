package cn.edu.xmu.oomall.freight.service;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.freight.dao.RegionDao;
import cn.edu.xmu.oomall.goods.model.bo.Region;
import cn.edu.xmu.oomall.goods.model.po.RegionPo;
import cn.edu.xmu.oomall.goods.model.vo.RegionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ziyi guo
 * @date 2021/11/10
 */
@Service
public class RegionService {

    private Logger logger = LoggerFactory.getLogger(RegionService.class);

    @Autowired
    private RegionDao regionDao;

    public ReturnObject<List<Region>> getParentRegionById(Long id) {
        RegionPo queryObj = new RegionPo();
        queryObj.setId(id);

        ReturnObject<List<Region>> retRegion = null;

        ReturnObject<List<Region>> returnObject = regionDao.getParentRegion(queryObj);

        if (returnObject.getCode().equals(ReturnNo.OK)) {
            if (returnObject.getData().size() == 0) {
                retRegion = new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
            }else{
                retRegion = new ReturnObject<>(returnObject.getData());
            }
        }else{
            retRegion = new ReturnObject<>(returnObject.getCode(), returnObject.getErrmsg());
        }
        return retRegion;
    }

    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<VoObject> createRegion(RegionVo regionVo, Long pid, Long userId, String userName) {
        logger.info("createRegion: regionVo = " + regionVo);
        Region region = regionVo.createRegion();
        region.setPid(pid);
        region.setState(Byte.valueOf("0"));
        region.setCreatedBy(userId);
        region.setCreateName(userName);
        logger.info("createRegion: region = " + region);
        ReturnObject<Region> retObj = regionDao.createRegion(region);
        ReturnObject<VoObject> retRegion = null;
        if (retObj.getCode().equals(ReturnNo.OK)) {
            retRegion = new ReturnObject<>(retObj.getData());
        }else{
            retRegion = new ReturnObject<>(retObj.getCode(), retObj.getErrmsg());
        }
        logger.info("createRegion: retRegion = " + retRegion.getData());
        return retRegion;
    }

    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<Object> modifyRegion(Long id, RegionVo regionVo, Long userId, String userName) {
        Region region = regionVo.createRegion();
        region.setId(id);
        region.setModifiedBy(userId);
        region.setModiName(userName);
        return regionDao.modiRegion(region);
    }

    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<Object> abandonRegion(Long id, Long userId, String userName) {
        Region region=new Region();
        region.setId(id);
        region.setModifiedBy(userId);
        region.setModiName(userName);
        return regionDao.abandonRegion(region);
    }

    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<Object> suspendRegion(Long id, Long userId, String userName) {
        Region region=new Region();
        region.setId(id);
        region.setModifiedBy(userId);
        region.setModiName(userName);
        return regionDao.suspendRegion(region);
    }

    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<Object> resumeRegion(Long id, Long userId, String userName) {
        Region region=new Region();
        region.setId(id);
        region.setModifiedBy(userId);
        region.setModiName(userName);
        return regionDao.resumeRegion(region);
    }

}
