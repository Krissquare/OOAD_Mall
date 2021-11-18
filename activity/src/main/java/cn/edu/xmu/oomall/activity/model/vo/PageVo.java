package cn.edu.xmu.oomall.activity.model.vo;

import lombok.Data;

import java.util.List;
@Data
public class PageVo<T> {
    private Integer page;
    private Integer pageSize;
    private Integer total;
    private Integer pages;
    private List<T> list;

    public PageVo(Integer page, Integer pageSize, Integer total, Integer pages, List<T> list) {
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.pages = pages;
        this.list = list;
    }

    public PageVo() {
    }
}
