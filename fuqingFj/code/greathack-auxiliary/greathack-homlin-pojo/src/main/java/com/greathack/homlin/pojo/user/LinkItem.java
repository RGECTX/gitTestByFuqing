package com.greathack.homlin.pojo.user;

/**
 * Linkitem entity. @author MyEclipse Persistence Tools
 */

public class LinkItem implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4582332699532989899L;
	private Long linkItemId;
	private String appCode;
	private Long userId;
	private Long itemTypeId;
	private String content;
	private Boolean isDefault;
	private String remark;
	private Long createTime;

	// Constructors

	/** default constructor */
	public LinkItem() {
	}

	/** minimal constructor */
	public LinkItem(Long linkItemId, Long userId, Long itemTypeId, Boolean isDefault, Long createTime) {
		this.linkItemId = linkItemId;
		this.userId = userId;
		this.itemTypeId = itemTypeId;
		this.isDefault = isDefault;
		this.createTime = createTime;
	}

	/** full constructor */
	public LinkItem(Long linkItemId, Long userId, Long itemTypeId, String content, Boolean isDefault, String remark, Long createTime) {
		this.linkItemId = linkItemId;
		this.userId = userId;
		this.itemTypeId = itemTypeId;
		this.content = content;
		this.isDefault = isDefault;
		this.remark = remark;
		this.createTime = createTime;
	}

	// Property accessors

	public Long getLinkItemId() {
		return this.linkItemId;
	}

	public void setLinkItemId(Long linkItemId) {
		this.linkItemId = linkItemId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getItemTypeId() {
		return this.itemTypeId;
	}

	public void setItemTypeId(Long itemTypeId) {
		this.itemTypeId = itemTypeId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getAppCode() {
		return this.appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

}