package cn.edu.xmu.oomall.activity.dao;

import cn.edu.xmu.oomall.activity.mapper.GroupOnActivityPoMapper;
import cn.edu.xmu.oomall.activity.model.bo.GroupOnActivityBo;
import cn.edu.xmu.oomall.activity.model.po.GroupOnActivityPo;
import cn.edu.xmu.oomall.activity.model.po.GroupOnActivityPoExample;
import cn.edu.xmu.oomall.activity.model.vo.GroupOnActivityVo;
import cn.edu.xmu.oomall.activity.model.vo.SimpleGroupOnActivityVo;
import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gao Yanfeng
 * @date 2021/11/11
 */
@Repository
public class GroupOnActivityDao {

    @Autowired
    private GroupOnActivityPoMapper mapper;

    public void insertActivity(GroupOnActivityBo bo, String shopName) {
        GroupOnActivityPo po = bo.createPo();
        po.setShopName(shopName);
        Common.setPoCreatedFields(po, 1L, "admin");
        Common.setPoModifiedFields(po, 1L, "admin");
        mapper.insert(po);
        bo.setId(po.getId());
    }

    public PageInfo<SimpleGroupOnActivityVo> getGroupActivities(GroupOnActivityPoExample example, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<GroupOnActivityPo> poList = mapper.selectByExample(example);
        var voList = new ArrayList<SimpleGroupOnActivityVo>();
        for (var po : poList) {
            voList.add(new GroupOnActivityBo(po).createSimpleVo());
        }
        var pageInfo = new PageInfo<>(voList);
        pageInfo.setPages(PageInfo.of(poList).getPages());
        pageInfo.setTotal(PageInfo.of(poList).getTotal());
        pageInfo.setPageNum(page);
        pageInfo.setPageSize(pageSize);
        return pageInfo;
    }

    public GroupOnActivityBo getGroupOnActivity(Long id) {
        var po = mapper.selectByPrimaryKey(id);
        if (po == null) {
            return null;
        } else {
            return new GroupOnActivityBo(po);
        }
    }
}
