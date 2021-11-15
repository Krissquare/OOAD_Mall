package cn.edu.xmu.oomall.activity.model.po;

import java.time.LocalDateTime;

public class GroupOnActivityPo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_groupon_activity.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_groupon_activity.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_groupon_activity.shop_id
     *
     * @mbg.generated
     */
    private Long shopId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_groupon_activity.shop_name
     *
     * @mbg.generated
     */
    private String shopName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_groupon_activity.strategy
     *
     * @mbg.generated
     */
    private String strategy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_groupon_activity.begin_time
     *
     * @mbg.generated
     */
    private LocalDateTime beginTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_groupon_activity.end_time
     *
     * @mbg.generated
     */
    private LocalDateTime endTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_groupon_activity.state
     *
     * @mbg.generated
     */
    private Byte state;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_groupon_activity.created_by
     *
     * @mbg.generated
     */
    private Long createdBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_groupon_activity.create_name
     *
     * @mbg.generated
     */
    private String createName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_groupon_activity.modified_by
     *
     * @mbg.generated
     */
    private Long modifiedBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_groupon_activity.modi_name
     *
     * @mbg.generated
     */
    private String modiName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_groupon_activity.gmt_create
     *
     * @mbg.generated
     */
    private LocalDateTime gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_groupon_activity.gmt_modified
     *
     * @mbg.generated
     */
    private LocalDateTime gmtModified;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_groupon_activity.id
     *
     * @return the value of oomall_groupon_activity.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_groupon_activity.id
     *
     * @param id the value for oomall_groupon_activity.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_groupon_activity.name
     *
     * @return the value of oomall_groupon_activity.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_groupon_activity.name
     *
     * @param name the value for oomall_groupon_activity.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_groupon_activity.shop_id
     *
     * @return the value of oomall_groupon_activity.shop_id
     *
     * @mbg.generated
     */
    public Long getShopId() {
        return shopId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_groupon_activity.shop_id
     *
     * @param shopId the value for oomall_groupon_activity.shop_id
     *
     * @mbg.generated
     */
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_groupon_activity.shop_name
     *
     * @return the value of oomall_groupon_activity.shop_name
     *
     * @mbg.generated
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_groupon_activity.shop_name
     *
     * @param shopName the value for oomall_groupon_activity.shop_name
     *
     * @mbg.generated
     */
    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_groupon_activity.strategy
     *
     * @return the value of oomall_groupon_activity.strategy
     *
     * @mbg.generated
     */
    public String getStrategy() {
        return strategy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_groupon_activity.strategy
     *
     * @param strategy the value for oomall_groupon_activity.strategy
     *
     * @mbg.generated
     */
    public void setStrategy(String strategy) {
        this.strategy = strategy == null ? null : strategy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_groupon_activity.begin_time
     *
     * @return the value of oomall_groupon_activity.begin_time
     *
     * @mbg.generated
     */
    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_groupon_activity.begin_time
     *
     * @param beginTime the value for oomall_groupon_activity.begin_time
     *
     * @mbg.generated
     */
    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_groupon_activity.end_time
     *
     * @return the value of oomall_groupon_activity.end_time
     *
     * @mbg.generated
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_groupon_activity.end_time
     *
     * @param endTime the value for oomall_groupon_activity.end_time
     *
     * @mbg.generated
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_groupon_activity.state
     *
     * @return the value of oomall_groupon_activity.state
     *
     * @mbg.generated
     */
    public Byte getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_groupon_activity.state
     *
     * @param state the value for oomall_groupon_activity.state
     *
     * @mbg.generated
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_groupon_activity.created_by
     *
     * @return the value of oomall_groupon_activity.created_by
     *
     * @mbg.generated
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_groupon_activity.created_by
     *
     * @param createdBy the value for oomall_groupon_activity.created_by
     *
     * @mbg.generated
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_groupon_activity.create_name
     *
     * @return the value of oomall_groupon_activity.create_name
     *
     * @mbg.generated
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_groupon_activity.create_name
     *
     * @param createName the value for oomall_groupon_activity.create_name
     *
     * @mbg.generated
     */
    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_groupon_activity.modified_by
     *
     * @return the value of oomall_groupon_activity.modified_by
     *
     * @mbg.generated
     */
    public Long getModifiedBy() {
        return modifiedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_groupon_activity.modified_by
     *
     * @param modifiedBy the value for oomall_groupon_activity.modified_by
     *
     * @mbg.generated
     */
    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_groupon_activity.modi_name
     *
     * @return the value of oomall_groupon_activity.modi_name
     *
     * @mbg.generated
     */
    public String getModiName() {
        return modiName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_groupon_activity.modi_name
     *
     * @param modiName the value for oomall_groupon_activity.modi_name
     *
     * @mbg.generated
     */
    public void setModiName(String modiName) {
        this.modiName = modiName == null ? null : modiName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_groupon_activity.gmt_create
     *
     * @return the value of oomall_groupon_activity.gmt_create
     *
     * @mbg.generated
     */
    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_groupon_activity.gmt_create
     *
     * @param gmtCreate the value for oomall_groupon_activity.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_groupon_activity.gmt_modified
     *
     * @return the value of oomall_groupon_activity.gmt_modified
     *
     * @mbg.generated
     */
    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_groupon_activity.gmt_modified
     *
     * @param gmtModified the value for oomall_groupon_activity.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }
}