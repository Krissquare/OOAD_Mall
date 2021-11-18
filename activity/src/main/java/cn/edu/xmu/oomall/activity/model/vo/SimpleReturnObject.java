package cn.edu.xmu.oomall.activity.model.vo;

import lombok.Data;

@Data
public class SimpleReturnObject<T>{
    private Integer errno=0;
    private String errmsg="成功";
    private T data;
    public SimpleReturnObject(Integer errno, String errmsg) {
        this.errno = errno;
        this.errmsg = errmsg;
    }

    public SimpleReturnObject(Integer errno, String errmsg,T data) {
        this.errno = errno;
        this.errmsg = errmsg;
        this.data=data;
    }
    public SimpleReturnObject(T data) {
        this.data=data;
    }

    public SimpleReturnObject() {
    }
}
