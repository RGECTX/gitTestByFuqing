package com.greathack.homlin.pojo.login;

/**
 * Loginlogs entity. @author MyEclipse Persistence Tools
 */

public class LoginLog implements java.io.Serializable {

	private static final long serialVersionUID = 5951816084985807700L;
	
	/**
	 * 登录
	 */
	public static final int OPT_TYPE_LOGIN = 1;
	
	/**
	 * 退出登录
	 */
	public static final int OPT_TYPE_LOGOUT = 2;
	
	/**
	 * 超时退出
	 */
	public static final int OPT_TYPE_EXPIRE_LOGOUT = 4;
	
	/**
	 * 强制退出
	 */
	public static final int OPT_TYPE__FORCE_LOGOUT = 8;
	
	private Long logUnique;
	private Long loginInstanceUnique;
	private Integer optType;
	private String loginCode;
	private String loginIp;
	private String loginRegion;
	private Long createTime;
	private String remark;

	// Constructors

	/** default constructor */
	public LoginLog() {
	}

	/** minimal constructor */
	public LoginLog(Long loginInstanceUnique, Integer optType, Long createTime) {
		this.loginInstanceUnique = loginInstanceUnique;
		this.optType = optType;
		this.createTime = createTime;
	}

	/** full constructor */
	public LoginLog(Long loginInstanceUnique, Integer optType, String loginCode, String loginIp, String loginRegion, Long createTime, String remark) {
		this.loginInstanceUnique = loginInstanceUnique;
		this.optType = optType;
		this.loginCode = loginCode;
		this.loginIp = loginIp;
		this.loginRegion = loginRegion;
		this.createTime = createTime;
		this.remark = remark;
	}

	// Property accessors

	public Long getLogUnique() {
		return this.logUnique;
	}

	public void setLogUnique(Long logUnique) {
		this.logUnique = logUnique;
	}

	public Long getLoginInstanceUnique() {
		return this.loginInstanceUnique;
	}

	public void setLoginInstanceUnique(Long loginInstanceUnique) {
		this.loginInstanceUnique = loginInstanceUnique;
	}

	public Integer getOptType() {
		return this.optType;
	}

	public void setOptType(Integer optType) {
		this.optType = optType;
	}

	public String getLoginCode() {
		return this.loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public String getLoginIp() {
		return this.loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginRegion() {
		return this.loginRegion;
	}

	public void setLoginRegion(String loginRegion) {
		this.loginRegion = loginRegion;
	}

	public Long getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}