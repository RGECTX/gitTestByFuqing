package com.greathack.homlin.pojo.area;

/**
 * Area entity. @author MyEclipse Persistence Tools
 */

public class Area implements java.io.Serializable {

	// Fields

	
		/**  描述   (@author: greathack) */      
	    
	private static final long serialVersionUID = 1999423692959560969L;
	private Integer areaId;
	private Integer parentId;
	private String adcode;
	private String citycode;
	private Integer level;
	private String provinceName;
	private String cityName;
	private String districtName;
	private String areaName;
	private String initial;
	private String parentIds;
	private String fullPinyin;

	// Constructors

	/** default constructor */
	public Area() {
	}

	/** minimal constructor */
	public Area(Integer areaId, Integer parentId, String adcode, Integer level, String areaName, String initial, String parentIds, String fullPinyin) {
		this.areaId = areaId;
		this.parentId = parentId;
		this.adcode = adcode;
		this.level = level;
		this.areaName = areaName;
		this.initial = initial;
		this.parentIds = parentIds;
		this.fullPinyin = fullPinyin;
	}

	/** full constructor */
	public Area(Integer areaId, Integer parentId, String adcode, String citycode, Integer level, String provinceName, String cityName, String districtName, String areaName, String initial,
			String parentIds, String fullPinyin) {
		this.areaId = areaId;
		this.parentId = parentId;
		this.adcode = adcode;
		this.citycode = citycode;
		this.level = level;
		this.provinceName = provinceName;
		this.cityName = cityName;
		this.districtName = districtName;
		this.areaName = areaName;
		this.initial = initial;
		this.parentIds = parentIds;
		this.fullPinyin = fullPinyin;
	}

	// Property accessors

	public Integer getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getAdcode() {
		return this.adcode;
	}

	public void setAdcode(String adcode) {
		this.adcode = adcode;
	}

	public String getCitycode() {
		return this.citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistrictName() {
		return this.districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getInitial() {
		return this.initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

	public String getParentIds() {
		return this.parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getFullPinyin() {
		return this.fullPinyin;
	}

	public void setFullPinyin(String fullPinyin) {
		this.fullPinyin = fullPinyin;
	}

}