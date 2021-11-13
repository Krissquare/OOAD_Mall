package cn.edu.xmu.oomall.freight.service;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.freight.dao.RegionDao;
import cn.edu.xmu.oomall.goods.model.bo.Region;
import cn.edu.xmu.oomall.goods.model.vo.RegionVo;
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

    private static final Byte STATE_EFFCTIVE=0;

    private static final Byte STATE_SUSPENDED=1;

    private static final Byte STATE_ABANDONED=2;

    @Autowired
    private RegionDao regionDao;

    /**
     * 通过id查找所有上级地区
     * @param id
     * @return ReturnObject
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<List<Region>> getParentRegion(Long id) {

        ReturnObject<List<Region>> retRegion;

        ReturnObject<List<Region>> returnObject = regionDao.getParentRegion(id);

        if (returnObject.getCode().equals(ReturnNo.OK)) {
            retRegion = new ReturnObject<>(returnObject.getData());
        }else{
            retRegion = new ReturnObject<>(returnObject.getCode(), returnObject.getErrmsg());
        }
        return retRegion;
    }

    /**
     * 创建地区
     * @param regionVo,userId,userName
     * @return ReturnObject
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<VoObject> createRegion(RegionVo regionVo, Long pid, Long userId, String userName) {

        Region region=regionVo.createRegion();
        region.setPid(pid);
        region.setState(STATE_EFFCTIVE);

        ReturnObject<Region> retObj = regionDao.createRegion(region.gotRegionPo(),userId,userName);
        
        ReturnObject<VoObject> retRegion;
        if (retObj.getCode().equals(ReturnNo.OK)) {
            retRegion = new ReturnObject<>(retObj.getData());
        }else{
            retRegion = new ReturnObject<>(retObj.getCode(), retObj.getErrmsg());
        }

        return retRegion;
    }

    /**
     * 管理员根据id查询子地区
     * @param id
     * @return ReturnObject
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<List<Region>> adminGetChildRegion(Long id) {

        ReturnObject<List<Region>> retRegion;

        ReturnObject<List<Region>> returnObject = regionDao.adminGetChildRegion(id);

        if (returnObject.getCode().equals(ReturnNo.OK)) {
            retRegion = new ReturnObject<>(returnObject.getData());
        }else{
            retRegion = new ReturnObject<>(returnObject.getCode(), returnObject.getErrmsg());
        }
        return retRegion;
    }

    /**
     * 根据id查询子地区(只返回有效地区)
     * @param id
     * @return ReturnObject
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<List<Region>> getChildRegion(Long id) {

        ReturnObject<List<Region>> retRegion;

        ReturnObject<List<Region>> returnObject = regionDao.getChildRegion(id);

        if (returnObject.getCode().equals(ReturnNo.OK)) {
            retRegion = new ReturnObject<>(returnObject.getData());
        }else{
            retRegion = new ReturnObject<>(returnObject.getCode(), returnObject.getErrmsg());
        }
        return retRegion;
    }

    /**
     * 管理员修改地区
     * @param regionVo,userId,userName
     * @return ReturnObject
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<Object> modifyRegion(RegionVo regionVo, Long id, Long userId, String userName) {

        Region region=regionVo.createRegion();
        region.setId(id);

        return regionDao.modiRegion(region.gotRegionPo(),userId,userName);
    }

    /**
     * 管理员废弃地区
     * @param id,userId,userName
     * @return ReturnObject
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<Object> abandonRegion(Long id, Long userId, String userName) {

        Region region=new Region();
        region.setId(id);
        region.setState(STATE_ABANDONED);

        return regionDao.abandonRegion(region.gotRegionPo(),userId,userName);
    }

    /**
     * 管理员停用地区
     * @param id,userId,userName
     * @return ReturnObject
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<Object> suspendRegion(Long id, Long userId, String userName) {

        Region region=new Region();
        region.setId(id);
        region.setState(STATE_SUSPENDED);

        return regionDao.suspendRegion(region.gotRegionPo(),userId,userName);
    }

    /**
     * 管理员恢复地区
     * @param id,userId,userName
     * @return ReturnObject
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject<Object> resumeRegion(Long id, Long userId, String userName) {

        Region region=new Region();
        region.setId(id);
        region.setState(STATE_EFFCTIVE);

        return regionDao.resumeRegion(region.gotRegionPo(),userId,userName);
    }

}
