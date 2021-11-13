package cn.edu.xmu.oomall.activity.util;
import cn.edu.xmu.oomall.activity.openfeign.vo.goods.OnSaleInfoDTO;
import cn.edu.xmu.oomall.activity.openfeign.vo.goods.ShareAct;
import cn.edu.xmu.oomall.activity.openfeign.vo.shop.ShopInfoDTO;
import cn.edu.xmu.oomall.core.util.ReturnObject;

/**
 * @author: xiuchen lang 22920192204222
 * @Date: 2021/11/13 15:00
 */
public class CreateObject {
    public static ReturnObject<OnSaleInfoDTO> createOnSaleInfoDTO(Long id) {
        if(id<=0){
            return new ReturnObject<>();
        }
        OnSaleInfoDTO onSaleInfoDTO = new OnSaleInfoDTO();
        onSaleInfoDTO.setId(id);
        ShareAct shareAct = new ShareAct();
        shareAct.setId(50L);
        shareAct.setName("分享活动");
        onSaleInfoDTO.setShareAct(shareAct);
        return new ReturnObject<>(onSaleInfoDTO);
    }

    public static ReturnObject<ShopInfoDTO> createShopInfoDTO(Long id) {
        if(id<=0){
            return new ReturnObject();
        }
        return new ReturnObject<>(new ShopInfoDTO(id,"良耳的商铺"));
    }
}
