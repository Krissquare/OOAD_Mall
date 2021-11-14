package cn.edu.xmu.oomall.goods.model.vo;

import cn.edu.xmu.oomall.goods.model.bo.OnSale;
import cn.edu.xmu.oomall.goods.model.bo.OnSaleSimpleInfo;
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

    public OnSaleSimpleRetVo(OnSaleSimpleInfo info) {
        this.list= new ArrayList<>();
        this.page=info.getPage();
        this.pageSize=info.getPageSize();
        this.total= info.getTotal();
        this.pages=info.getPages();

        for(OnSale onsale:info.getList()){
            NewOnSaleRetVo ret= new NewOnSaleRetVo(onsale);
            list.add(ret);
        }
    }


}
