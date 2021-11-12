package cn.edu.xmu.oomall.freight.mapper;

import cn.edu.xmu.oomall.goods.model.po.RegionPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ziyi guo
 * @date 2021/11/10
 */
@Mapper
public interface RegionPoMapper {

    RegionPo selectById(Long id);

    RegionPo selectParent(RegionPo regionPo);

    Long createRegion(RegionPo regionPo);

    int updateRegion(RegionPo regionPo);

    Byte getStateById(Long id);

    int abandonRegion(RegionPo regionPo);

    int suspendRegion(RegionPo regionPo);

    int resumeRegion(RegionPo regionPo);
}