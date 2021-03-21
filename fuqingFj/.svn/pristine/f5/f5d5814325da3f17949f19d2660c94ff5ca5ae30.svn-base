package com.greathack.homlin.pojo;

import java.io.Serializable;

public class Admin implements Serializable{
	
	
	public enum State{
        /**
         * 正常
         */
        normal(1),
        /**
         * 已锁定
         */
        locked(2),
        /**
         * 已删除
         */
        deleted(4);
        private int value;
        private State(int value){
            this.value=value;
        }
        public int value(){
            return value;
        }
        public static State getState(int value){
            switch(value){
                case 1:
                    return normal;
                case 2:
                    return locked;
                case 4:
                    return deleted;
                default: 
                    return normal;
            }
        }
    }

    public static final int USER_STATE_NORMAL=1;//正常
    public static final int USER_STATE_LOCKED=2;//锁定
    public static final int USER_STATE_DELETED=4;//删除

	/**
	 * 
	 */
	private static final long serialVersionUID = -1514189320178749901L;

	
	private Long userId;
	private String amUnitId;
	private String loginName;
	private String name;
	private String password;
	private Integer sex;
	private Long createTime;
	private Long lastLoginTime;
	private Integer state;
	private String idcard;
	private String mobile;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAmUnitId() {
		return amUnitId;
	}
	public void setAmUnitId(String amUnitId) {
		this.amUnitId = amUnitId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
