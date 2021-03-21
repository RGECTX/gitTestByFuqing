package com.greathack.homlin.pojo.login;

/**
 * Logininstances entity. @author MyEclipse Persistence Tools
 */

public class LoginInstance implements java.io.Serializable {

	private static final long serialVersionUID = 5125179192724730112L;
	
	/**
	 * 在线
	 */
	public static final int LOGIN_STATE_ON_LINE = 1;
	
	/**
	 * 离线
	 */
	public static final int LOGIN_STATE_OFF_LINE = 2;
	
	/**
	 * 锁定
	 */
	public static final int LOGIN_STATE_LOCK = 4;
	
	private Long loginInstanceUnique;
	private String appCode;
	private String instanceId;
	private String loginCode;
	private String password;
	private Long loginTime;
	private String loginIp;
	private String loginRegion;
	private Integer loginState;

	// Constructors

	/** default constructor */
	public LoginInstance() {
	}

	/** minimal constructor */
	public LoginInstance(String appCode, String instanceId, Integer loginState) {
		this.appCode = appCode;
		this.instanceId = instanceId;
		this.loginState = loginState;
	}

	/** full constructor */
	public LoginInstance(String appCode, String instanceId, String loginCode, Long loginTime, String loginIp, String loginRegion, Integer loginState) {
		this.appCode = appCode;
		this.instanceId = instanceId;
		this.loginCode = loginCode;
		this.loginTime = loginTime;
		this.loginIp = loginIp;
		this.loginRegion = loginRegion;
		this.loginState = loginState;
	}

	// Property accessors

	public Long getLoginInstanceUnique() {
		return this.loginInstanceUnique;
	}

	public void setLoginInstanceUnique(Long loginInstanceUnique) {
		this.loginInstanceUnique = loginInstanceUnique;
	}

	public String getAppCode() {
		return this.appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getInstanceId() {
		return this.instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getLoginCode() {
		return this.loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public Long getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
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

	public Integer getLoginState() {
		return this.loginState;
	}

	public void setLoginState(Integer loginState) {
		this.loginState = loginState;
	}

}