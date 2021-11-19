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
 * @studentId 34520192201587
 */

@Data
@NoArgsConstructor
public class ShopAccountVo implements VoObject {
    private long id;

    @ApiModelProperty(value = "支付渠道")
    Byte type;

    @ApiModelProperty(value = "账户号")
    String account;

    @ApiModelProperty(value = "账户名称")
    String name;

    @ApiModelProperty(value = "汇入优先级")
    Byte priority;


    public SimpleAdminUserVo creator;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime gmtCreate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime gmtModified;
    public SimpleAdminUserVo modifier;


    /**
     * @author  Xusheng Wang
     * @date  2021-11-11
     * @studentId 34520192201587
     */
    public ShopAccountPo createPo(){
        ShopAccountPo shopAccountPo=new ShopAccountPo();
        shopAccountPo.setType(type);
        shopAccountPo.setAccount(account);
        shopAccountPo.setName(name);
        shopAccountPo.setPriority(priority);
        return shopAccountPo;
    }

    /**
     * @author  Xusheng Wang
     * @date  2021-11-11
     * @studentId 34520192201587
     */
    public ShopAccountVo(ShopAccountPo shopAccountPo){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        id=shopAccountPo.getId();
        type=shopAccountPo.getType();
        account=shopAccountPo.getAccount();
        name=shopAccountPo.getName();
        priority=shopAccountPo.getPriority();
        creator=new SimpleAdminUserVo(shopAccountPo.getCreatorId(),shopAccountPo.getCreatorName());
        gmtCreate=shopAccountPo.getGmtCreate();
        gmtModified=shopAccountPo.getGmtModified();
        modifier=new SimpleAdminUserVo(shopAccountPo.getModifierId(),shopAccountPo.getModifierName());
    }

    /**
     * @author  Xusheng Wang
     * @date  2021-11-11
     * @studentId 34520192201587
     */
    @Override
    public Object createVo() {
        return this;
    }

    /**
     * @author  Xusheng Wang
     * @date  2021-11-11
     * @studentId 34520192201587
     */
    @Override
    public Object createSimpleVo() {
        return this;
    }
}
