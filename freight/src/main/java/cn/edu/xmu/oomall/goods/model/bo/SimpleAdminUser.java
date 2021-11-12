package cn.edu.xmu.oomall.goods.model.bo;

/**
 * @author ziyi guo
 * @date 2021/11/10
 */

public class SimpleAdminUser {

    private Long id;

    private String userName;

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

    public SimpleAdminUser(Long id, String userName)
    {
        this.id=id;
        this.userName=userName;
    }
}
