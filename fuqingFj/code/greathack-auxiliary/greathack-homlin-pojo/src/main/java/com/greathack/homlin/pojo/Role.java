package com.greathack.homlin.pojo;

import java.io.Serializable;

public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4114892619250351031L;

	private Long roleId;
	private String roleType;	//角色类型	String
	private String roleName;	//角色名称	String
	private String roleDescription;//	角色描述	String
	private Integer roleState;	//角色状态	Integer
	private Integer isSys;	//是否系统内置，1：是，2：否

	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	public Integer getRoleState() {
		return roleState;
	}
	public void setRoleState(Integer roleState) {
		this.roleState = roleState;
	}

	public Integer getIsSys() {
		return isSys;
	}

	public void setIsSys(Integer isSys) {
		this.isSys = isSys;
	}
}
