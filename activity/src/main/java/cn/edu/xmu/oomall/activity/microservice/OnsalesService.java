package cn.edu.xmu.oomall.activity.microservice;

import cn.edu.xmu.oomall.activity.microservice.vo.GrouponUpdateSimpleVo;
import cn.edu.xmu.oomall.activity.microservice.vo.OnsaleSimpleVo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Component
@FeignClient(value = "onsales",url = "http://localhost:8081")
public interface OnsalesService {

    //认为需要实现而目前没有的内部api:
    //下线某团购活动的所有onsale项
    @PutMapping("/internal/onsales/groupon/{id}/offline")
    public Object setGrouponOnsalesToOffline(@PathVariable("id") Integer grouponId);

    //新增团购活动到Onsale
    @PutMapping("/internal/shops/{shopId}/groupon/{actId}/products/{pid}/online")
    public ReturnObject<OnsaleSimpleVo> addOneGrouponOnsale(@PathVariable("shopId")Integer shopId,
                                                            @PathVariable("actId")Integer actId,
                                                            @PathVariable("pid")Integer pid);

    //更新团购活动onsale的时间
    @PutMapping("/internal/groupon/{id}/update")
    public Map<String,Object> updateGrouponOnsale(@PathVariable("id") Integer id, @RequestBody GrouponUpdateSimpleVo upd);
}
