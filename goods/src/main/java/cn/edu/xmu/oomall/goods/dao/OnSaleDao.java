package cn.edu.xmu.oomall.goods.dao;

import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.goods.mapper.OnSalePoMapper;
import cn.edu.xmu.oomall.goods.model.bo.OnSale;
import cn.edu.xmu.oomall.goods.model.po.OnSalePo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static cn.edu.xmu.oomall.core.util.Common.setPoCreatedFields;
import static cn.edu.xmu.oomall.core.util.Common.setPoModifiedFields;

/**
 * @author yujie lin 22920192204242
 * @date 2021/11/10
 */
@Repository
public class OnSaleDao {
    private Logger logger = LoggerFactory.getLogger(OnSaleDao.class);

    @Autowired
    private OnSalePoMapper onSalePoMapper;

    /**
     * 创建Onsale对象
     *
     * @param onSale 传入的Onsale对象
     * @return 返回对象ReturnObj
     */
    public ReturnObject createOnSale(OnSale onSale, Long userId, String userName) {
        OnSalePo onsalePo = onSale.gotOnSalePo();
        setPoCreatedFields(onsalePo, userId, userName);
        onSalePoMapper.insert(onsalePo);
        return new ReturnObject(new OnSale(onsalePo));
    }


    public ReturnObject onlineOrOfflineOnSale(OnSale onsale, Long userId, String userName) {
        OnSalePo po = onsale.gotOnSalePo();
        setPoModifiedFields(po, userId, userName);
        onSalePoMapper.onlineOrOfflineOnSale(po);
        return new ReturnObject();
    }

    public OnSale getOnSaleById(Long id) {
        return new OnSale(onSalePoMapper.selectByPrimaryKey(id));
    }


    public ReturnObject searchOnSaleByProductNorSec(Long productId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<OnSalePo> pos = onSalePoMapper.getOnSaleByProductIdNorSec(productId);
        return getBoAndReturn(pos);
    }

    public ReturnObject searchOnSaleByProduct(Long productId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<OnSalePo> pos = onSalePoMapper.getOnSaleByProductId(productId);
        return getBoAndReturn(pos);
    }

    public ReturnObject searchOnSaleByActivity(Long actId, Integer page, Integer pageSize, Integer state, OnSale.Type type) {
        PageHelper.startPage(page, pageSize);
        List<OnSalePo> pos= onSalePoMapper.getOnSaleByActivityId(actId, state, type.getCode());
        return getBoAndReturn(pos);
    }

    public ReturnObject searchOnSaleByShare(Long actId, Integer page, Integer pageSize, Integer state) {
        PageHelper.startPage(page, pageSize);
        List<OnSalePo> pos= onSalePoMapper.getOnSaleByShare(actId, state);
        return getBoAndReturn(pos);
    }

    private ReturnObject getBoAndReturn(List<OnSalePo> pos) {
        List<OnSale> onSales = new ArrayList<>(pos.size());
        for (OnSalePo onSalePo : pos) {
            OnSale onsale = new OnSale(onSalePo);
            onSales.add(onsale);
        }
        PageInfo<OnSale> pageInfo = PageInfo.of(onSales);
        return new ReturnObject(pageInfo);
    }


    public boolean timeCollided(OnSale onsale) {
        OnSalePo po = onsale.gotOnSalePo();
        return onSalePoMapper.getTimeCollidedCount(po) > 0;
    }
}
