package com.greathack.homlin.pojo.area;

/**
 * Circle entity. @author MyEclipse Persistence Tools
 */

public class Circle implements java.io.Serializable {

	// Fields

	public static final int CIRCLE_STATE_NORMAL=1;//正常
	public static final int CIRCLE_STATE_LOCKED=2;//停用
	public static final int CIRCLE_STATE_DELETED=4;//删除
	
		/**  描述   (@author: greathack) */      
	    
	private static final long serialVersionUID = 4379882610348675894L;
	private Long circleId;
	private String appCode;
	private Integer areaId;
	private String circleName;
	private Integer circleState;
	private String remark;
	private Long createTime;
	private String outKey1;
	private String outKey2;
	private String bak1;
	private String bak2;

	// Constructors

	/** default constructor */
	public Circle() {
	}

	/** minimal constructor */
	public Circle(Long circleId, String appCode, Integer areaId, String circleName, Integer circleState, Long createTime) {
		this.circleId = circleId;
		this.appCode = appCode;
		this.areaId = areaId;
		this.circleName = circleName;
		this.circleState = circleState;
		this.createTime = createTime;
	}

	/** full constructor */
	public Circle(Long circleId, String appCode, Integer areaId, String circleName, Integer circleState, String remark, Long createTime, String outKey1, String outKey2, String bak1, String bak2) {
		this.circleId = circleId;
		this.appCode = appCode;
		this.areaId = areaId;
		this.circleName = circleName;
		this.circleState = circleState;
		this.remark = remark;
		this.createTime = createTime;
		this.outKey1 = outKey1;
		this.outKey2 = outKey2;
		this.bak1 = bak1;
		this.bak2 = bak2;
	}

	// Property accessors

	public Long getCircleId() {
		return this.circleId;
	}

	public void setCircleId(Long circleId) {
		this.circleId = circleId;
	}

	public String getAppCode() {
		return this.appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public Integer getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getCircleName() {
		return this.circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public Integer getCircleState() {
		return this.circleState;
	}

	public void setCircleState(Integer circleState) {
		this.circleState = circleState;
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

}