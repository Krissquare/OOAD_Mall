package cn.edu.xmu.oomall.goods.mapper;

import cn.edu.xmu.oomall.goods.model.po.ProductDraftPo;
import cn.edu.xmu.oomall.goods.model.po.ProductDraftPoExample;
import java.util.List;

public interface ProductDraftPoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product_draft
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product_draft
     *
     * @mbg.generated
     */
    int insert(ProductDraftPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product_draft
     *
     * @mbg.generated
     */
    int insertSelective(ProductDraftPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product_draft
     *
     * @mbg.generated
     */
    List<ProductDraftPo> selectByExample(ProductDraftPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product_draft
     *
     * @mbg.generated
     */
    ProductDraftPo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product_draft
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ProductDraftPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product_draft
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ProductDraftPo record);
}