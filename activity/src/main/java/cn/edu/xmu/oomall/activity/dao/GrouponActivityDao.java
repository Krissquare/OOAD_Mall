package cn.edu.xmu.oomall.activity.dao;

import cn.edu.xmu.oomall.activity.mapper.GroupOnActivityPoMapper;
import cn.edu.xmu.oomall.activity.model.po.GroupOnActivityPo;
import cn.edu.xmu.oomall.activity.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class GrouponActivityDao {

    @Autowired
    private GroupOnActivityPoMapper groupOnActivityPoMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${redis-para.groupon-activity.expiretime}")
    private long timeout;

    public GroupOnActivityPo getGrouponActivity(Long id){
        //带Redis缓存的查询
        //规定团购活动在redis里key的存储形式为"groupon_"+id
        GroupOnActivityPo groupOnActivityPo = (GroupOnActivityPo) redisUtil.get("groupon_"+id.toString());
        if (groupOnActivityPo == null){
            groupOnActivityPo = groupOnActivityPoMapper.selectByPrimaryKey(id);
            if (groupOnActivityPo != null){
                redisUtil.set("groupon_"+groupOnActivityPo.getId().toString(),groupOnActivityPo,timeout);
            }
        }
        return groupOnActivityPo;
    }

    public void updateGrouponActivity(GroupOnActivityPo groupOnActivityPo){
        //同时更新数据库和Redis缓存
        redisUtil.set("groupon_"+groupOnActivityPo.getId().toString(),groupOnActivityPo,timeout);
        groupOnActivityPoMapper.updateByPrimaryKey(groupOnActivityPo);
    }

//    public void insertGrouponActivity(GroupOnActivityPo groupOnActivityPo){
//        redisUtil.set("groupon_"+groupOnActivityPo.getId().toString(),groupOnActivityPo,timeout);
//        groupOnActivityPoMapper.insert(groupOnActivityPo);
//    }

    public void deleteGrouponActivity(GroupOnActivityPo groupOnActivityPo){
        redisUtil.del("groupon_"+groupOnActivityPo.getId().toString());
        groupOnActivityPoMapper.deleteByPrimaryKey(groupOnActivityPo.getId());
    }

}
