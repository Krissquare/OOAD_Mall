package cn.edu.xmu.oomall.activity.dao;

import cn.edu.xmu.oomall.activity.mapper.GroupOnActivityPoMapper;
import cn.edu.xmu.oomall.activity.model.po.GroupOnActivityPo;
import cn.edu.xmu.oomall.core.util.RedisUtil;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class GrouponActivityDao {

    @Autowired
    private GroupOnActivityPoMapper groupOnActivityPoMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${oomall.activity.groupon.expiretime}")
    private long timeout;

    public ReturnObject<GroupOnActivityPo> getGrouponActivity(Long id) {
        try {
            //规定团购活动在redis里key的存储形式为"groupon_"+id
            GroupOnActivityPo groupOnActivityPo = (GroupOnActivityPo) redisUtil.get("groupon_" + id.toString());
            if (groupOnActivityPo == null) {
                groupOnActivityPo = groupOnActivityPoMapper.selectByPrimaryKey(id);
                if (groupOnActivityPo != null) {
                    redisUtil.set("groupon_" + groupOnActivityPo.getId().toString(), groupOnActivityPo, timeout);
                }
            }
            return new ReturnObject<GroupOnActivityPo>(groupOnActivityPo);
        } catch (Exception e){
            return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERR,e.getMessage());
        }
    }

    public ReturnObject<Object> updateGrouponActivity(GroupOnActivityPo groupOnActivityPo) {
        try {//同时更新数据库和Redis缓存
            redisUtil.del("groupon_" + groupOnActivityPo.getId().toString());
            groupOnActivityPoMapper.updateByPrimaryKey(groupOnActivityPo);
            return new ReturnObject();
        }catch (Exception e){
            return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERR,e.getMessage());
        }
    }

//    public void insertGrouponActivity(GroupOnActivityPo groupOnActivityPo){
//        redisUtil.set("groupon_"+groupOnActivityPo.getId().toString(),groupOnActivityPo,timeout);
//        groupOnActivityPoMapper.insert(groupOnActivityPo);
//    }

    public ReturnObject<Object> deleteGrouponActivity(GroupOnActivityPo groupOnActivityPo) {
        try {
            redisUtil.del("groupon_" + groupOnActivityPo.getId().toString());
            groupOnActivityPoMapper.deleteByPrimaryKey(groupOnActivityPo.getId());
            return new ReturnObject<>();
        }catch (Exception e){
            return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERR,e.getMessage());
        }
    }

}
