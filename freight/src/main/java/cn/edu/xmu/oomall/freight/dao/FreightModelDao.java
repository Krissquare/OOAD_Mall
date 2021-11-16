package cn.edu.xmu.oomall.freight.dao;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.freight.mapper.FreightModelPoMapper;
import cn.edu.xmu.oomall.freight.model.bo.FreightModel;
import cn.edu.xmu.oomall.freight.model.po.FreightModelPo;
import cn.edu.xmu.oomall.freight.model.po.FreightModelPoExample;
import cn.edu.xmu.oomall.freight.model.vo.FreightModelRetVo;
import cn.edu.xmu.oomall.freight.model.vo.SimpleUserRetVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RenJieZheng 22920192204334
 * @date 2021/11/16
 */
@Repository
public class FreightModelDao {
    @Autowired
    FreightModelPoMapper freightModelPoMapper;


    /**
     * 管理员定义运费模板
     * @param freightModel 运费模板
     * @return 运费模板
     */
    public ReturnObject<FreightModelRetVo> addFreightModel(FreightModel freightModel){
        try{
            FreightModelPo freightModelPo = (FreightModelPo)Common.cloneVo(freightModel, FreightModelPo.class);
            freightModelPo.setGmtCreate(LocalDateTime.now());
            int ret = freightModelPoMapper.insert(freightModelPo);
            if (ret == 0) {
                return new ReturnObject<>(ReturnNo.FIELD_NOTVALID);
            } else {
                FreightModelRetVo freightModelRetVo = (FreightModelRetVo) Common.cloneVo(freightModelPo,FreightModelRetVo.class);
                freightModelRetVo.setCreateBy(new SimpleUserRetVo(freightModelPo.getCreatedBy(),freightModelPo.getCreateName()));
                // 因为新建是没有修改者id和修改者姓名的，所以不管，直接返回
                return new ReturnObject<>(freightModelRetVo);
            }
        }catch(Exception e){
            return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERR);
        }

    }

    /**
     * 获得商品的运费模板
     * @param example 查询条件
     * @param page 页
     * @param pageSize 页大小
     * @return
     */
    public ReturnObject<PageInfo<VoObject>> showFreightModel(FreightModelPoExample example, Integer page, Integer pageSize){
        try {
            PageHelper.startPage(page,pageSize);
            List<FreightModelPo> list = freightModelPoMapper.selectByExample(example);
            List<VoObject>list1 = new ArrayList<>();
            for(FreightModelPo freightModelPo:list){
                FreightModelRetVo freightModelRetVo = (FreightModelRetVo) Common.cloneVo(freightModelPo,FreightModelRetVo.class);
                //因为FreightModelRetVo结构与FreightModelPo不同，所以需要添加一下为赋值的数据
                freightModelRetVo.setCreateBy(new SimpleUserRetVo(freightModelPo.getCreatedBy(),freightModelPo.getCreateName()));
                freightModelRetVo.setModifiedBy(new SimpleUserRetVo(freightModelPo.getModifiedBy(),freightModelPo.getModiName()));
                list1.add(freightModelRetVo);
            }
            PageInfo<VoObject>pageInfo = new PageInfo<>(list1);
            return new ReturnObject<>(pageInfo);
        }catch (Exception e){
            return new ReturnObject<>(ReturnNo.IMG_FORMAT_ERROR);
        }
    }

    /**
     * 管理员克隆运费模板
     * @param id 需要克隆的模板id
     * @param userId 操作者id
     * @param userName 操作者姓名
     * @return 运费模板
     */
    public ReturnObject<FreightModelRetVo> cloneFreightModel(Long id,Long userId,String userName){
        try{
            FreightModelPo freightModelPo = freightModelPoMapper.selectByPrimaryKey(id);
            if(freightModelPo==null){
                return new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
            }
            if(!freightModelPo.getCreatedBy().equals(userId)){
                return new ReturnObject<>(ReturnNo.RESOURCE_ID_OUTSCOPE);
            }
            Common.setPoCreatedFields(freightModelPo,userId,userName);
            //将id置空,不影响mysql对id的赋值
            freightModelPo.setId(null);
            freightModelPo.setGmtCreate(LocalDateTime.now());
            int ret = freightModelPoMapper.insert(freightModelPo);
            if (ret == 0) {
                return new ReturnObject<>(ReturnNo.FIELD_NOTVALID);
            } else {
                FreightModelRetVo freightModelRetVo = (FreightModelRetVo) Common.cloneVo(freightModelPo,FreightModelRetVo.class);
                freightModelRetVo.setCreateBy(new SimpleUserRetVo(freightModelPo.getCreatedBy(),freightModelPo.getCreateName()));
                freightModelRetVo.setModifiedBy(new SimpleUserRetVo(freightModelPo.getModifiedBy(),freightModelPo.getModiName()));
                return new ReturnObject<>(freightModelRetVo);
            }
        }catch(Exception e){
            return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    /**
     * 获得运费模板详情
     * @param id 运费模板id
     * @param userId 操作者id
     * @param userName 操作者姓名
     * @return 运费模板
     */
    public ReturnObject<FreightModelRetVo> showFreightModelById(Long id,Long userId,String userName){
        try{
            FreightModelPo freightModelPo = freightModelPoMapper.selectByPrimaryKey(id);
            if(freightModelPo==null){
                return new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
            }
            if(!freightModelPo.getCreatedBy().equals(userId)){
                return new ReturnObject<>(ReturnNo.RESOURCE_ID_OUTSCOPE);
            }
            FreightModelRetVo freightModelRetVo = (FreightModelRetVo) Common.cloneVo(freightModelPo,FreightModelRetVo.class);
            freightModelRetVo.setCreateBy(new SimpleUserRetVo(freightModelPo.getCreatedBy(),freightModelPo.getCreateName()));
            freightModelRetVo.setModifiedBy(new SimpleUserRetVo(freightModelPo.getModifiedBy(),freightModelPo.getModiName()));
            return new ReturnObject<>(freightModelRetVo);
        }catch(Exception e){
            return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }


    /**
     * 管理员修改运费模板
     * @param id 模板id
     * @param freightModel 运费模板
     * @return 运费模板
     */
    public ReturnObject<FreightModelRetVo> updateFreightModel(Long id,FreightModel freightModel){
        try{
            FreightModelPo freightModelPo = freightModelPoMapper.selectByPrimaryKey(id);
            if(freightModelPo==null){
                return new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
            }
            if(!freightModelPo.getCreatedBy().equals(freightModel.getModifiedBy())){
                return new ReturnObject<>(ReturnNo.RESOURCE_ID_OUTSCOPE);
            }
            FreightModelPo freightModelPo1 = (FreightModelPo) Common.cloneVo(freightModel,FreightModelPo.class);
            freightModelPo1.setId(id);
            freightModelPo.setGmtModified(LocalDateTime.now());
            int ret = freightModelPoMapper.updateByPrimaryKeySelective(freightModelPo);
            if (ret == 0) {
                return new ReturnObject<>(ReturnNo.FIELD_NOTVALID);
            } else {
                return new ReturnObject<>(ReturnNo.OK);
            }
        }catch(Exception e){
            return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    /**
     * 管理员删除运费模板
     * @param id 运费模板id
     * @param userId 操作者id
     * @param userName 操作者姓名
     * @return 删除结果
     */
    public ReturnObject<FreightModelRetVo> deleteFreightModel(Long id,Long userId, String userName){
        try{
            FreightModelPo freightModelPo = freightModelPoMapper.selectByPrimaryKey(id);
            if(freightModelPo==null){
                return new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
            }
            if(!freightModelPo.getCreatedBy().equals(userId)){
                return new ReturnObject<>(ReturnNo.RESOURCE_ID_OUTSCOPE);
            }
            freightModelPoMapper.deleteByPrimaryKey(id);
            return new ReturnObject<>(ReturnNo.OK);
        }catch(Exception e){
            return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }


    /**
     * 店家或管理员为商铺定义默认运费模板。会将原有的默认运费模板取消
     * @param id 运费模板id
     * @param userId 操作者id
     * @param userName 操作者姓名
     * @return 修改结果
     */
    public ReturnObject<FreightModelRetVo> updateDefaultModel(Long id,Long userId,String userName){
        try{
            FreightModelPo freightModelPo = freightModelPoMapper.selectByPrimaryKey(id);
            if(freightModelPo==null){
                return new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
            }
            if(!freightModelPo.getCreatedBy().equals(userId)){
                return new ReturnObject<>(ReturnNo.RESOURCE_ID_OUTSCOPE);
            }
            FreightModelPo freightModelPo1 = new FreightModelPo();
            freightModelPo1.setId(id);
            freightModelPo.setDefaultModel((byte) 1);
            freightModelPo.setGmtModified(LocalDateTime.now());
            int ret = freightModelPoMapper.updateByPrimaryKeySelective(freightModelPo);
            if (ret == 0) {
                return new ReturnObject<>(ReturnNo.FIELD_NOTVALID);
            } else {
                return new ReturnObject<>(ReturnNo.OK);
            }
        }catch(Exception e){
            return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }
}
