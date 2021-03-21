package com.greathack.homlin.pojo.user;

/**
 * Addressitem entity. @author MyEclipse Persistence Tools
 */

public class AddressItem implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5941360497564427158L;
	
	private Long addressItemId;
	/** 所属用户ID*/
	private Long userId;
	/** 应用编码*/
	private String appCode;
    /** 姓名*/
    private String name;
    /** 手机*/
    private String mobile;
    /** 电话*/
    private String tel;
	/** 省*/
	private String province;
	/** 市*/
	private String city;
	/** 县*/
	private String district;
	/** 坐标lat值*/
	private String lat;
	/** 坐标lng值*/
	private String lng;
	/** 联系地址*/
	private String linkAddress;
	/** 是否默认:true：默认，false：非默认，同一个用户只能有一个默认*/
	private Boolean isDefault;
	/** 备注*/
	private String remark;
	/** 创建时间*/
	private Long createTime;
	/** 外键1:备用*/
	private String outKey1;
	/** 外键2:备用*/
	private String outKey2;
	/** 备用1*/
	private String bak1;
	/** 备用2*/
	private String bak2;

	// Constructors

	/** default constructor */
	public AddressItem() {
	}

	/** minimal constructor */
	public AddressItem(Long addressItemId, Long userId, Boolean isDefault, Long createTime) {
		this.addressItemId = addressItemId;
		this.userId = userId;
		this.isDefault = isDefault;
		this.createTime = createTime;
	}

	/** full constructor */
	public AddressItem(Long addressItemId, Long userId, String province, String city, String district, String lat, String lng, String linkAddress, Boolean isDefault, String remark,
			Long createTime, String outKey1, String outKey2, String bak1, String bak2) {
		this.addressItemId = addressItemId;
		this.userId = userId;
		this.province = province;
		this.city = city;
		this.district = district;
		this.lat = lat;
		this.lng = lng;
		this.linkAddress = linkAddress;
		this.isDefault = isDefault;
		this.remark = remark;
		this.createTime = createTime;
		this.outKey1 = outKey1;
		this.outKey2 = outKey2;
		this.bak1 = bak1;
		this.bak2 = bak2;
	}

	// Property accessors

	public Long getAddressItemId() {
		return this.addressItemId;
	}

	public void setAddressItemId(Long addressItemId) {
		this.addressItemId = addressItemId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getLat() {
		return this.lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return this.lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLinkAddress() {
		return this.linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
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

	public String getOutKey1() {
		return this.outKey1;
	}

	public void setOutKey1(String outKey1) {
		this.outKey1 = outKey1;
	}

	public String getOutKey2() {
		return this.outKey2;
	}

	public void setOutKey2(String outKey2) {
		this.outKey2 = outKey2;
	}

	public String getBak1() {
		return this.bak1;
	}

	public void setBak1(String bak1) {
		this.bak1 = bak1;
	}

	public String getBak2() {
		return this.bak2;
	}

	public void setBak2(String bak2) {
		this.bak2 = bak2;
	}

	public String getAppCode() {
		return this.appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

}