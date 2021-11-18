package cn.edu.xmu.oomall.freight.service;

import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.freight.dao.FreightModelDao;
import cn.edu.xmu.oomall.freight.dao.WeightFreightDao;
import cn.edu.xmu.oomall.freight.model.bo.FreightModel;
import cn.edu.xmu.oomall.freight.model.bo.WeightFreight;
import cn.edu.xmu.oomall.freight.model.po.WeightFreightPo;
import cn.edu.xmu.oomall.freight.model.vo.WeightFreightVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ziyi guo
 * @date 2021/11/16
 */
@Service
public class WeightFreightService {

    private static final Byte TYPE_WEIGHT=0;
    private static final Byte TYPE_PIECE=1;

    @Autowired
    private WeightFreightDao weightFreightDao;

    @Autowired
    private FreightModelDao freightModelDao;

    /**
     * 管理员新增重量模板明细
     * @param weightFreightVo,freightModelId,userId,userName
     * @return ReturnObject
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject addWeightItems(WeightFreightVo weightFreightVo, Long freightModelId, Long userId, String userName) {

        WeightFreight weightFreight = (WeightFreight) Common.cloneVo(weightFreightVo,WeightFreight.class);
        weightFreight.setFreightModelId(freightModelId);

        FreightModel freightModel = (FreightModel) freightModelDao.selectFreightModelById(freightModelId).getData();
        if (freightModel==null) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
        }
        if(!freightModel.getType().equals(TYPE_WEIGHT)){
            return new ReturnObject(ReturnNo.FREIGHT_WRONGTYPE);
        }

        ReturnObject returnObject = weightFreightDao.addWeightItems( (WeightFreightPo) Common.cloneVo(weightFreight, WeightFreightPo.class), userId, userName);

        return returnObject;
    }

    /**
     * 店家或管理员查询某个重量运费模板的明细
     * @param freightModelId,page,pageSize
     * @return ReturnObject
     */
    @Transactional(rollbackFor=Exception.class, readOnly = true)
    public ReturnObject getWeightItems(Long freightModelId, Integer page, Integer pageSize) {

        FreightModel freightModel = (FreightModel) freightModelDao.selectFreightModelById(freightModelId).getData();
        if (freightModel==null) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
        }

        ReturnObject returnObject = weightFreightDao.getWeightItems(freightModelId, page, pageSize);

        return returnObject;
    }

    /**
     * 店家或管理员修改重量运费模板明细
     * @param weightFreightVo,id,userId,userName
     * @return ReturnObject
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject updateWeightItems(WeightFreightVo weightFreightVo, Long id, Long userId, String userName) {

        WeightFreight weightFreight = (WeightFreight) Common.cloneVo(weightFreightVo, WeightFreight.class);
        weightFreight.setId(id);

        return weightFreightDao.updateWeightItems((WeightFreightPo) Common.cloneVo(weightFreight, WeightFreightPo.class),userId,userName);
    }

    /**
     * 店家或管理员删掉重量运费模板明细
     * @param id
     * @return ReturnObject
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject deleteWeightItems(Long id) {

        ReturnObject returnObject = weightFreightDao.deleteWeightItems(id);

        return returnObject;
    }
}
