package cn.edu.xmu.oomall.goods.model.bo;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.goods.model.vo.OnSaleSimpleRetVo;
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

    public OnSaleSimpleInfo(Integer page, Integer pageSize,Integer total,
                          List<OnSale> onSales) {
        this.list=new ArrayList<OnSale>();
        this.page=page;
        this.pageSize=pageSize;
        this.total=total;
        this.pages=(int)Math.ceil(total.doubleValue()/pageSize.doubleValue());

        for(OnSale onsale:onSales){
            list.add(onsale);
        }
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
