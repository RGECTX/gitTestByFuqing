package com.greathack.homlin.pojo.user;

/**
 * Idcard entity. @author MyEclipse Persistence Tools
 */

public class Idcard implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 91333725866236598L;
	private Long idcardId;
	private String appCode;
	private Long userId;
	private String idcardNo;
	private String pics;
    private String beginValidityDate;
    private String endValidityDate;
	private Boolean isDefault;
	private Long createTime;
	private String idcardName;

	// Property accessors

	public Long getIdcardId() {
		return this.idcardId;
	}

	public void setIdcardId(Long idcardId) {
		this.idcardId = idcardId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getIdcardNo() {
		return this.idcardNo;
	}

	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}

	public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getBeginValidityDate() {
        return beginValidityDate;
    }

    public void setBeginValidityDate(String beginValidityDate) {
        this.beginValidityDate = beginValidityDate;
    }

    public String getEndValidityDate() {
        return endValidityDate;
    }

    public void setEndValidityDate(String endValidityDate) {
        this.endValidityDate = endValidityDate;
    }

    public Boolean getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
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

	public String getIdcardName() {
		return idcardName;
	}

	public void setIdcardName(String idcardName) {
		this.idcardName = idcardName;
	}

}