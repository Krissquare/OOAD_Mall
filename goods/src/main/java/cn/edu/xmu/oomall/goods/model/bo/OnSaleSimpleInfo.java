package cn.edu.xmu.oomall.goods.model.bo;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.goods.model.vo.OnSaleSimpleRetVo;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yujie lin
 * @date 2021/11/11
 */
@Data
public class OnSaleSimpleInfo  implements VoObject {

    private Integer page;
    private Integer pageSize;
    private Integer total;
    private Integer pages;

    private List<OnSale> list;


    public OnSaleSimpleInfo(PageInfo<OnSale> pageInfo) {
        this.list=pageInfo.getList();
        this.total= Math.toIntExact(pageInfo.getTotal());
        this.pages=pageInfo.getPages();
        this.pageSize=pageInfo.getPageSize();
        this.page=pageInfo.getPageNum();
    }

    @Override
    public Object createVo() {
        return new OnSaleSimpleRetVo(this);
    }

    @Override
    public Object createSimpleVo() {
        return null;
    }
}
