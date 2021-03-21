package com.greathack.homlin.pojo.permission;

public class RoleOfUser {
	private long roleOfUserId;
	private String appCode;
	private String userId;//用户Id
	private String roleId;//角色ID
	
	public long getRoleOfUserId() {
		return roleOfUserId;
	}
	public void setRoleOfUserId(long roleOfUserId) {
		this.roleOfUserId = roleOfUserId;
	}
	public String getAppCode() {
        return appCode;
    }
    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
    public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
