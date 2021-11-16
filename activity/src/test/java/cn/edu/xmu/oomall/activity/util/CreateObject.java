package cn.edu.xmu.oomall.activity.util;
import cn.edu.xmu.oomall.activity.microservice.vo.SimpleSaleInfoVO;
import cn.edu.xmu.oomall.activity.microservice.vo.ShopInfoVO;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiuchen lang 22920192204222
 * @date 2021/11/13 15:00
 */
public class CreateObject {
    public static ReturnObject<PageInfo<SimpleSaleInfoVO>> createOnSaleInfoDTO(Long id) {
        if(id<=0){
            return new ReturnObject(new PageInfo<>());
        }
        List<SimpleSaleInfoVO> list = new ArrayList<>();
        SimpleSaleInfoVO simpleSaleInfoVO = new SimpleSaleInfoVO();
        simpleSaleInfoVO.setShareActId(1l);
        list.add(simpleSaleInfoVO);
        SimpleSaleInfoVO simpleSaleInfoVO2 = new SimpleSaleInfoVO();
        simpleSaleInfoVO2.setShareActId(2l);
        list.add(simpleSaleInfoVO2);
        SimpleSaleInfoVO simpleSaleInfoVO3 = new SimpleSaleInfoVO();
        simpleSaleInfoVO3.setShareActId(3l);
        list.add(simpleSaleInfoVO3);
        //模拟不是share活动
        SimpleSaleInfoVO simpleSaleInfoVO5 = new SimpleSaleInfoVO();
        list.add(simpleSaleInfoVO5);
        SimpleSaleInfoVO simpleSaleInfoVO4 = new SimpleSaleInfoVO();
        simpleSaleInfoVO4.setShareActId(4l);
        list.add(simpleSaleInfoVO4);
        PageInfo pageInfo = new PageInfo(list);
        pageInfo.setTotal(10);
        return new ReturnObject(pageInfo);
    }

    public static ReturnObject<ShopInfoVO> createShopInfoDTO(Long id) {
        if(id<=0){
            return new ReturnObject();
        }
        return new ReturnObject<>(new ShopInfoVO(id,"良耳的商铺"));
    }

}
