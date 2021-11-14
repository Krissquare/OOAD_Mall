package cn.edu.xmu.oomall.activity.util;
import cn.edu.xmu.oomall.activity.mirrorService.vo.goods.SimpleSaleInfoDTO;
import cn.edu.xmu.oomall.activity.mirrorService.vo.shop.ShopInfoDTO;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiuchen lang 22920192204222
 * @date 2021/11/13 15:00
 */
public class CreateObject {
    public static ReturnObject<PageInfo<SimpleSaleInfoDTO>> createOnSaleInfoDTO(Long id) {
        if(id<=0){
            return new ReturnObject(new PageInfo<>());
        }
        List<SimpleSaleInfoDTO> list = new ArrayList<>();
        SimpleSaleInfoDTO simpleSaleInfoDTO = new SimpleSaleInfoDTO();
        simpleSaleInfoDTO.setShareActId(1l);
        list.add(simpleSaleInfoDTO);
        SimpleSaleInfoDTO simpleSaleInfoDTO2 = new SimpleSaleInfoDTO();
        simpleSaleInfoDTO2.setShareActId(2l);
        list.add(simpleSaleInfoDTO2);
        SimpleSaleInfoDTO simpleSaleInfoDTO3 = new SimpleSaleInfoDTO();
        simpleSaleInfoDTO3.setShareActId(3l);
        list.add(simpleSaleInfoDTO3);
        //模拟不是share活动
        SimpleSaleInfoDTO simpleSaleInfoDTO5= new SimpleSaleInfoDTO();
        list.add(simpleSaleInfoDTO5);
        SimpleSaleInfoDTO simpleSaleInfoDTO4 = new SimpleSaleInfoDTO();
        simpleSaleInfoDTO4.setShareActId(4l);
        list.add(simpleSaleInfoDTO4);
        PageInfo pageInfo = new PageInfo(list);
        pageInfo.setTotal(10);
        return new ReturnObject(pageInfo);
    }

    public static ReturnObject<ShopInfoDTO> createShopInfoDTO(Long id) {
        if(id<=0){
            return new ReturnObject();
        }
        return new ReturnObject<>(new ShopInfoDTO(id,"良耳的商铺"));
    }

}
