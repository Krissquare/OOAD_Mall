package cn.edu.xmu.oomall.activity.model.vo;

import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Gao Yanfeng
 * @date 2021/11/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfoVo<T> {
    List<T> list;
    Long total;
    Integer page;
    Integer pageSize;
    Integer pages;

    public PageInfoVo(PageInfo<T> pageInfo) {
        list = pageInfo.getList();
        total = pageInfo.getTotal();
        page = pageInfo.getPageNum();
        pageSize = pageInfo.getPageSize();
        pages = pageInfo.getPages();
    }
}
