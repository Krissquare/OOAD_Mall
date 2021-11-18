package cn.edu.xmu.oomall.freight.service;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.freight.dao.FreightModelDao;
import cn.edu.xmu.oomall.freight.microservice.ProductService;
import cn.edu.xmu.oomall.freight.model.bo.FreightModel;
import cn.edu.xmu.oomall.freight.model.po.FreightModelPoExample;
import cn.edu.xmu.oomall.freight.model.vo.FreightModelInfo;
import cn.edu.xmu.oomall.freight.model.vo.FreightModelRetVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;

/**
 *
 * @author RenJieZheng 22920192204334
 * @date 2021/11/16
 */
@Service
public class FreightModelService {


    @Autowired
    FreightModelDao freightModelDao;

    @Resource
    ProductService productService;

    /**
     * 管理员定义运费模板
     * @param freightModelInfo 运费模板资料
     * @param userId 操作者id
     * @param userName 操作者姓名
     * @return 运费模板
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnObject<FreightModelRetVo> addFreightModel(FreightModelInfo freightModelInfo,
                                                           Long userId, String userName){
        FreightModel freightModel = (FreightModel) Common.cloneVo(freightModelInfo,FreightModel.class);
        //新建的时候默认是不使用默认模板
        freightModel.setDefaultModel((byte) 0);
        Common.setPoCreatedFields(freightModel,userId,userName);
        return freightModelDao.addFreightModel(freightModel);
    }


    /**
     * 获得商品的运费模板
     * @param name 模板名称
     * @param page 页
     * @param pageSize 页大小
     * @return 运费模板
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ReturnObject<PageInfo<VoObject>> showFreightModel(String name, Integer page, Integer pageSize){
        FreightModelPoExample example = new FreightModelPoExample();
        FreightModelPoExample.Criteria criteria = example.createCriteria();
        if(name!=null&&!"".equals(name)){
            criteria.andNameEqualTo(name);
        }
        return freightModelDao.showFreightModel(example,page,pageSize);
    }

    /**
     * 获得商品的运费模板带product
     * @param productId 商品id
     * @param name 模板名称
     * @param page 页
     * @param pageSize 页大小
     * @return
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ReturnObject showFreightModelWithProductId(Long productId,String name, Integer page, Integer pageSize){
        FreightModelPoExample example = new FreightModelPoExample();
        FreightModelPoExample.Criteria criteria = example.createCriteria();
        if(name!=null){
            criteria.andNameEqualTo(name);
        }
        criteria.andCreateNameEqualTo(name);
        ReturnObject<Long>returnObject = productService.getFreightIdByProductId(productId);
        if(!returnObject.getCode().equals(ReturnNo.OK)){
            return returnObject;
        }
        criteria.andIdEqualTo(returnObject.getData());
        return freightModelDao.showFreightModel(example,page,pageSize);
    }

    /**
     * 管理员克隆运费模板
     * @param id 需要克隆的模板id
     * @param userId 操作者id
     * @param userName 操作者姓名
     * @return 运费模板
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnObject<FreightModelRetVo> cloneFreightModel(Long id, Long userId, String userName){
        return freightModelDao.cloneFreightModel(id,userId,userName);
    }


    /**
     * 获得运费模板详情
     * @param id 运费模板id
     * @param userId 操作者id
     * @param userName 操作者姓名
     * @return 运费模板
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ReturnObject<FreightModelRetVo> showFreightModelById(Long id,Long userId,String userName){
        return freightModelDao.showFreightModelById(id,userId,userName);
    }

    /**
     * 管理员管理运费模板
     * @param freightModelInfo 运费模板资料
     * @param userId 操作者id
     * @param userName 操作者姓名
     * @return 运费模板
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnObject<FreightModelRetVo> updateFreightModel(Long id,FreightModelInfo freightModelInfo,
                                                           Long userId, String userName){
        FreightModel freightModel = (FreightModel) Common.cloneVo(freightModelInfo,FreightModel.class);
        Common.setPoModifiedFields(freightModel,userId,userName);
        return freightModelDao.updateFreightModel(id,freightModel);
    }

    /**
     * 管理员删除运费模板
     * @param id 运费模板id
     * @param userId 操作者id
     * @param userName 操作者姓名
     * @return 删除结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnObject deleteFreightModel(Long id,Long userId, String userName){
        ReturnObject<Boolean>returnObject = productService.isToBeDelete(id);
        if(!returnObject.getCode().equals(ReturnNo.OK)){
            return returnObject;
        }
        //存在上架销售商品，不能删除运费模板
        if(!returnObject.getData()){
            return new ReturnObject(ReturnNo.FREIGHT_NOTDELETED);
        }
        ReturnObject returnObject1 =  freightModelDao.deleteFreightModel(id,userId,userName);
        //如果删除成功还需删除，需同步删除与商品的关系
        if(returnObject1.getCode()==ReturnNo.OK){
            return productService.deleteRelationshipWithFreightModel(id);
        }else{
            return  returnObject1;
        }
    }

    /**
     * 店家或管理员为商铺定义默认运费模板。会将原有的默认运费模板取消
     * @param id 运费模板id
     * @param userId 操作者id
     * @param userName 操作者姓名
     * @return 修改结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnObject<FreightModelRetVo> updateDefaultModel(Long id,Long userId, String userName){
        return freightModelDao.updateDefaultModel(id,userId,userName);
    }
}
