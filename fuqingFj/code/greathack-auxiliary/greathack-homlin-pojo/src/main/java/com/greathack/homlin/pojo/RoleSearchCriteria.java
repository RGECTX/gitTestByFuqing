package com.greathack.homlin.pojo;

import com.greathack.homlin.pojo.system.SearchCriteria;
import com.greathack.utils.tools.HashUtils;


public class RoleSearchCriteria extends SearchCriteria {
	/**
	 * 角色名称字段
	 */
	public static final int KW_FIELDS_ROLE_NAME=1;

	/**
	 * 描述字段
	 */
	public static final int KW_FIELDS_ROLEDESCRIPTION=2;
	
	private String roleType;
	private Integer roleState;
	private Integer isSys;	//是否系统内置，1：是，2：否

	public String getRoleType() {
		return roleType;
	}



	public void setRoleType(String roleType) {
		this.roleType = roleType;
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

	/**
	 * 通过字段常量，获取字段名称
	 * @param field
	 * @return 字段名称
	 */
	@Override 
	protected String getKwFieldName(int field){
		switch(field){

    		case RoleSearchCriteria.KW_FIELDS_ROLEDESCRIPTION:
    			return "roleDescription";
    			
    		case RoleSearchCriteria.KW_FIELDS_ROLE_NAME:
    			return "roleName";
    			
			default:
				return "";
		}
	}
	

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((roleType == null) ? 0 : HashUtils.fnv1_32_hash(roleType));
		result = prime * result + ((roleState == null) ? 0 : HashUtils.fnv1_32_hash(roleState.toString()));
		result = prime * result + ((isSys == null) ? 0 : HashUtils.fnv1_32_hash(isSys.toString()));
		return result;
	}	



}
