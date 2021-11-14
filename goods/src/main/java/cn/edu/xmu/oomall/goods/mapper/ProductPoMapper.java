package cn.edu.xmu.oomall.goods.mapper;

import cn.edu.xmu.oomall.goods.model.po.ProductPo;
import cn.edu.xmu.oomall.goods.model.po.ProductPoExample;
import java.util.List;

public interface ProductPoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product
     *
     * @mbg.generated
     */
    int insert(ProductPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product
     *
     * @mbg.generated
     */
    int insertSelective(ProductPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product
     *
     * @mbg.generated
     */
    List<ProductPo> selectByExample(ProductPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product
     *
     * @mbg.generated
     */
    ProductPo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ProductPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ProductPo record);
}
