package cn.edu.xmu.oomall.freight.dao;

import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.freight.mapper.FreightModelPoMapper;
import cn.edu.xmu.oomall.freight.model.bo.FreightModel;
import cn.edu.xmu.oomall.freight.model.po.FreightModelPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author ziyi guo
 * @date 2021/11/17
 */
@Repository
public class FreightModelDao {

    @Autowired
    private FreightModelPoMapper freightModelPoMapper;

    public ReturnObject selectFreightModelById(Long id) {
        try {
            FreightModelPo freightModelPo = freightModelPoMapper.selectByPrimaryKey(id);
            return new ReturnObject(Common.cloneVo(freightModelPo, FreightModel.class));
        } catch (Exception e) {
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }

}
