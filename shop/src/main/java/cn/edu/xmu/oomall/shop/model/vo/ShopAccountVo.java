package cn.edu.xmu.oomall.shop.model.vo;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.shop.model.po.ShopAccountPo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author  Xusheng Wang
 * @date  2021-11-11
 */

@Data
@NoArgsConstructor
public class ShopAccountVo implements VoObject {
    @ApiModelProperty(value = "支付渠道")
    Byte type;

    @ApiModelProperty(value = "账户号")
    String account;

    @ApiModelProperty(value = "账户名称")
    String name;

    @ApiModelProperty(value = "汇入优先级")
    Byte priority;

    private long id;
    private long shopId;
    public SimpleAdminUserVo createdBy;
    public SimpleAdminUserVo modifiedBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime gmtCreate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime gmtModified;

    public ShopAccountPo createPo(){
        ShopAccountPo shopAccountPo=new ShopAccountPo();
        shopAccountPo.setType(type);
        shopAccountPo.setAccount(account);
        shopAccountPo.setName(name);
        shopAccountPo.setPriority(priority);
        return shopAccountPo;
    }

    public ShopAccountVo(ShopAccountPo shopAccountPo){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        id=shopAccountPo.getId();
        shopId=shopAccountPo.getShopId();
        type=shopAccountPo.getType();
        account=shopAccountPo.getAccount();
        name=shopAccountPo.getName();
        priority=shopAccountPo.getPriority();
        gmtCreate=shopAccountPo.getGmtCreate();
        gmtModified=shopAccountPo.getGmtModified();
        createdBy=new SimpleAdminUserVo(shopAccountPo.getCreatedBy(),shopAccountPo.getCreateName());
        modifiedBy=new SimpleAdminUserVo(shopAccountPo.getModifiedBy(),shopAccountPo.getModiName());
    }

    @Override
    public Object createVo() {
        return this;
    }

    @Override
    public Object createSimpleVo() {
        return this;
    }
}
