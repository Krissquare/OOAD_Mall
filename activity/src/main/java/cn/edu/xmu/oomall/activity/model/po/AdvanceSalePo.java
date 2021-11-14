package cn.edu.xmu.oomall.activity.model.po;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AdvanceSalePo implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_advance_sale.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_advance_sale.shop_id
     *
     * @mbg.generated
     */
    private Long shopId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_advance_sale.shop_name
     *
     * @mbg.generated
     */
    private String shopName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_advance_sale.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_advance_sale.pay_time
     *
     * @mbg.generated
     */
    private LocalDateTime payTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_advance_sale.advance_pay_price
     *
     * @mbg.generated
     */
    private Long advancePayPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_advance_sale.state
     *
     * @mbg.generated
     */
    private Byte state;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_advance_sale.created_by
     *
     * @mbg.generated
     */
    private Long createdBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_advance_sale.create_name
     *
     * @mbg.generated
     */
    private String createName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_advance_sale.modified_by
     *
     * @mbg.generated
     */
    private Long modifiedBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_advance_sale.modi_name
     *
     * @mbg.generated
     */
    private String modiName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_advance_sale.gmt_create
     *
     * @mbg.generated
     */
    private LocalDateTime gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_advance_sale.gmt_modified
     *
     * @mbg.generated
     */
    private LocalDateTime gmtModified;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_advance_sale.id
     *
     * @return the value of oomall_advance_sale.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_advance_sale.id
     *
     * @param id the value for oomall_advance_sale.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_advance_sale.shop_id
     *
     * @return the value of oomall_advance_sale.shop_id
     *
     * @mbg.generated
     */
    public Long getShopId() {
        return shopId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_advance_sale.shop_id
     *
     * @param shopId the value for oomall_advance_sale.shop_id
     *
     * @mbg.generated
     */
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_advance_sale.shop_name
     *
     * @return the value of oomall_advance_sale.shop_name
     *
     * @mbg.generated
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_advance_sale.shop_name
     *
     * @param shopName the value for oomall_advance_sale.shop_name
     *
     * @mbg.generated
     */
    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_advance_sale.name
     *
     * @return the value of oomall_advance_sale.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_advance_sale.name
     *
     * @param name the value for oomall_advance_sale.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_advance_sale.pay_time
     *
     * @return the value of oomall_advance_sale.pay_time
     *
     * @mbg.generated
     */
    public LocalDateTime getPayTime() {
        return payTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_advance_sale.pay_time
     *
     * @param payTime the value for oomall_advance_sale.pay_time
     *
     * @mbg.generated
     */
    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_advance_sale.advance_pay_price
     *
     * @return the value of oomall_advance_sale.advance_pay_price
     *
     * @mbg.generated
     */
    public Long getAdvancePayPrice() {
        return advancePayPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_advance_sale.advance_pay_price
     *
     * @param advancePayPrice the value for oomall_advance_sale.advance_pay_price
     *
     * @mbg.generated
     */
    public void setAdvancePayPrice(Long advancePayPrice) {
        this.advancePayPrice = advancePayPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_advance_sale.state
     *
     * @return the value of oomall_advance_sale.state
     *
     * @mbg.generated
     */
    public Byte getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_advance_sale.state
     *
     * @param state the value for oomall_advance_sale.state
     *
     * @mbg.generated
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_advance_sale.created_by
     *
     * @return the value of oomall_advance_sale.created_by
     *
     * @mbg.generated
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_advance_sale.created_by
     *
     * @param createdBy the value for oomall_advance_sale.created_by
     *
     * @mbg.generated
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_advance_sale.create_name
     *
     * @return the value of oomall_advance_sale.create_name
     *
     * @mbg.generated
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_advance_sale.create_name
     *
     * @param createName the value for oomall_advance_sale.create_name
     *
     * @mbg.generated
     */
    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_advance_sale.modified_by
     *
     * @return the value of oomall_advance_sale.modified_by
     *
     * @mbg.generated
     */
    public Long getModifiedBy() {
        return modifiedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_advance_sale.modified_by
     *
     * @param modifiedBy the value for oomall_advance_sale.modified_by
     *
     * @mbg.generated
     */
    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_advance_sale.modi_name
     *
     * @return the value of oomall_advance_sale.modi_name
     *
     * @mbg.generated
     */
    public String getModiName() {
        return modiName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_advance_sale.modi_name
     *
     * @param modiName the value for oomall_advance_sale.modi_name
     *
     * @mbg.generated
     */
    public void setModiName(String modiName) {
        this.modiName = modiName == null ? null : modiName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_advance_sale.gmt_create
     *
     * @return the value of oomall_advance_sale.gmt_create
     *
     * @mbg.generated
     */
    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_advance_sale.gmt_create
     *
     * @param gmtCreate the value for oomall_advance_sale.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_advance_sale.gmt_modified
     *
     * @return the value of oomall_advance_sale.gmt_modified
     *
     * @mbg.generated
     */
    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_advance_sale.gmt_modified
     *
     * @param gmtModified the value for oomall_advance_sale.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }
}