package cn.edu.xmu.oomall.freight.dao;

import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.freight.mapper.WeightFreightPoMapper;
import cn.edu.xmu.oomall.freight.model.bo.WeightFreight;
import cn.edu.xmu.oomall.freight.model.po.WeightFreightPo;
import cn.edu.xmu.oomall.freight.model.po.WeightFreightPoExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ziyi guo
 * @date 2021/11/16
 */
@Repository
public class WeightFreightDao {

    @Autowired
    private WeightFreightPoMapper weightFreightPoMapper;

    /**
     * 创建地区
     * @param weightFreightPo,userId,userName
     * @return ReturnObject
     */
    public ReturnObject addWeightItems(WeightFreightPo weightFreightPo, Long userId, String userName){

        try {
            Common.setPoCreatedFields(weightFreightPo, userId, userName);

            WeightFreightPo freightModelPo = weightFreightPoMapper.selectByPrimaryKey(weightFreightPo.getFreightModelId());
            if (freightModelPo == null) {
                ReturnObject retObj = new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
                return retObj;
            }
            WeightFreightPoExample example = new WeightFreightPoExample();
            WeightFreightPoExample.Criteria criteria = example.createCriteria();
            criteria.andRegionIdEqualTo(weightFreightPo.getRegionId());
            List<WeightFreightPo> list = weightFreightPoMapper.selectByExample(example);
            if (list!=null && list.size() > 0) {
                return new ReturnObject(ReturnNo.FREIGHT_REGIONEXIST);
            }

            int ret = weightFreightPoMapper.insertSelective(weightFreightPo);
            WeightFreightPo newWeightFreightPo = weightFreightPoMapper.selectByPrimaryKey(Long.valueOf(ret));
            return new ReturnObject(Common.cloneVo(newWeightFreightPo, WeightFreight.class));
        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }

    /**
     * 店家或管理员查询某个重量运费模板的明细
     *
     * @param id
     * @return ReturnObject
     */
    public ReturnObject getWeightItems(Long id, Integer page, Integer pageSize) {
        try {
            PageHelper.startPage(page, pageSize);

            WeightFreightPo weightFreightPo = new WeightFreightPo();
            weightFreightPo.setId(id);
            weightFreightPo = weightFreightPoMapper.selectByPrimaryKey(id);
            if (weightFreightPo == null) {
                ReturnObject retObj = new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
                return retObj;
            }

            WeightFreightPoExample example=new WeightFreightPoExample();
            WeightFreightPoExample.Criteria criteria=example.createCriteria();
            criteria.andFreightModelIdEqualTo(id);
            List<WeightFreightPo> weightFreightPoList = weightFreightPoMapper.selectByExample(example);
            List<WeightFreight> weightFreightBoList = new ArrayList<>();
            for (WeightFreightPo wfPo : weightFreightPoList) {
                weightFreightBoList.add((WeightFreight) Common.cloneVo(wfPo,WeightFreight.class));
            }

            PageInfo<WeightFreight> pageInfo = PageInfo.of(weightFreightBoList);
            return new ReturnObject(pageInfo);
        } catch (Exception e) {
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR,e.getMessage());
        }
    }

    /**
     * 店家或管理员修改重量运费模板明细
     * @param weightFreightPo,userId,userName
     * @return ReturnObject
     */
    public ReturnObject updateWeightItems(WeightFreightPo weightFreightPo, Long userId, String userName){

        try {
            Common.setPoModifiedFields(weightFreightPo,userId,userName);

            WeightFreightPo wp = weightFreightPoMapper.selectByPrimaryKey(weightFreightPo.getId());
            if (wp == null) {
                ReturnObject retObj = new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
                return retObj;
            }
            if(weightFreightPo.getRegionId()!=null) {
                WeightFreightPoExample example = new WeightFreightPoExample();
                WeightFreightPoExample.Criteria criteria = example.createCriteria();
                criteria.andRegionIdEqualTo(weightFreightPo.getRegionId());
                List<WeightFreightPo> list = weightFreightPoMapper.selectByExample(example);
                if (list!=null && list.size() > 0) {
                    if (!list.get(0).getId().equals(weightFreightPo.getId())) {
                        ReturnObject retObj = new ReturnObject(ReturnNo.FREIGHT_REGIONSAME);
                        return retObj;
                    }
                }
            }

            weightFreightPoMapper.updateByPrimaryKeySelective(weightFreightPo);
            return new ReturnObject();
        } catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }

    /**
     * 店家或管理员删除重量运费模板明细
     * @param id
     * @return ReturnObject
     */
    public ReturnObject deleteWeightItems(Long id){
        try {
            int ret = weightFreightPoMapper.deleteByPrimaryKey(id);
            if (ret == 0) {
                return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
            } else {
                return new ReturnObject(ReturnNo.OK);
            }
        } catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }

}
