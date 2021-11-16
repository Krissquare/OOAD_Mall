package cn.edu.xmu.oomall.goods.model.vo;

import cn.edu.xmu.oomall.goods.model.bo.OnSale;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static cn.edu.xmu.oomall.core.util.Common.cloneVo;

/**
 * @author yujie lin
 * @date 2021/11/11
 */
@Data
public class OnSaleSimpleRetVo {
    private Integer page;
    private Integer pageSize;
    private Integer total;
    private Integer pages;

    private List<NewOnSaleRetVo> list;


    public OnSaleSimpleRetVo(PageInfo<OnSale> info) {
        this.list= new ArrayList<>();
        this.page=info.getPageNum();
        this.pageSize=info.getPageSize();
        this.total= Math.toIntExact(info.getTotal());
        this.pages=info.getPages();

        for(OnSale onsale:info.getList()){
            System.out.println(onsale.getId());
            NewOnSaleRetVo ret=(NewOnSaleRetVo) cloneVo(onsale,NewOnSaleRetVo.class);
            System.out.println(ret.getId()+"a");
            list.add(ret);
        }
    }


}
