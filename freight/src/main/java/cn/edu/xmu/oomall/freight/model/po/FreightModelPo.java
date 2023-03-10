package cn.edu.xmu.oomall.freight.model.po;

import java.time.LocalDateTime;

public class FreightModelPo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_freight_model.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_freight_model.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_freight_model.default_model
     *
     * @mbg.generated
     */
    private Byte defaultModel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_freight_model.type
     *
     * @mbg.generated
     */
    private Byte type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_freight_model.unit
     *
     * @mbg.generated
     */
    private Integer unit;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_freight_model.creator_id
     *
     * @mbg.generated
     */
    private Long creatorId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_freight_model.creator_name
     *
     * @mbg.generated
     */
    private String creatorName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_freight_model.modifier_id
     *
     * @mbg.generated
     */
    private Long modifierId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_freight_model.modifier_name
     *
     * @mbg.generated
     */
    private String modifierName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_freight_model.gmt_create
     *
     * @mbg.generated
     */
    private LocalDateTime gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oomall_freight_model.gmt_modified
     *
     * @mbg.generated
     */
    private LocalDateTime gmtModified;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_freight_model.id
     *
     * @return the value of oomall_freight_model.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_freight_model.id
     *
     * @param id the value for oomall_freight_model.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_freight_model.name
     *
     * @return the value of oomall_freight_model.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_freight_model.name
     *
     * @param name the value for oomall_freight_model.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_freight_model.default_model
     *
     * @return the value of oomall_freight_model.default_model
     *
     * @mbg.generated
     */
    public Byte getDefaultModel() {
        return defaultModel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_freight_model.default_model
     *
     * @param defaultModel the value for oomall_freight_model.default_model
     *
     * @mbg.generated
     */
    public void setDefaultModel(Byte defaultModel) {
        this.defaultModel = defaultModel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_freight_model.type
     *
     * @return the value of oomall_freight_model.type
     *
     * @mbg.generated
     */
    public Byte getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_freight_model.type
     *
     * @param type the value for oomall_freight_model.type
     *
     * @mbg.generated
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_freight_model.unit
     *
     * @return the value of oomall_freight_model.unit
     *
     * @mbg.generated
     */
    public Integer getUnit() {
        return unit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_freight_model.unit
     *
     * @param unit the value for oomall_freight_model.unit
     *
     * @mbg.generated
     */
    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_freight_model.creator_id
     *
     * @return the value of oomall_freight_model.creator_id
     *
     * @mbg.generated
     */
    public Long getCreatorId() {
        return creatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_freight_model.creator_id
     *
     * @param creatorId the value for oomall_freight_model.creator_id
     *
     * @mbg.generated
     */
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_freight_model.creator_name
     *
     * @return the value of oomall_freight_model.creator_name
     *
     * @mbg.generated
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_freight_model.creator_name
     *
     * @param creatorName the value for oomall_freight_model.creator_name
     *
     * @mbg.generated
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName == null ? null : creatorName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_freight_model.modifier_id
     *
     * @return the value of oomall_freight_model.modifier_id
     *
     * @mbg.generated
     */
    public Long getModifierId() {
        return modifierId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_freight_model.modifier_id
     *
     * @param modifierId the value for oomall_freight_model.modifier_id
     *
     * @mbg.generated
     */
    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_freight_model.modifier_name
     *
     * @return the value of oomall_freight_model.modifier_name
     *
     * @mbg.generated
     */
    public String getModifierName() {
        return modifierName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_freight_model.modifier_name
     *
     * @param modifierName the value for oomall_freight_model.modifier_name
     *
     * @mbg.generated
     */
    public void setModifierName(String modifierName) {
        this.modifierName = modifierName == null ? null : modifierName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_freight_model.gmt_create
     *
     * @return the value of oomall_freight_model.gmt_create
     *
     * @mbg.generated
     */
    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_freight_model.gmt_create
     *
     * @param gmtCreate the value for oomall_freight_model.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oomall_freight_model.gmt_modified
     *
     * @return the value of oomall_freight_model.gmt_modified
     *
     * @mbg.generated
     */
    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oomall_freight_model.gmt_modified
     *
     * @param gmtModified the value for oomall_freight_model.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }
}