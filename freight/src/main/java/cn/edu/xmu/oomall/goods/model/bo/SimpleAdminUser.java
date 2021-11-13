package cn.edu.xmu.oomall.goods.model.bo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author ziyi guo
 * @date 2021/11/10
 */

public class SimpleAdminUser {

    @ApiModelProperty(value = "管理员id")
    private Long id;

    @ApiModelProperty(value = "管理员名")
    private String userName;

    public SimpleAdminUser(Long id, String userName)
    {
        this.id=id;
        this.userName=userName;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName=userName;
    }

}
