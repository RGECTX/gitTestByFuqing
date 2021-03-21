package com.greathack.homlin.daointerface.permission;

import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.permission.RoleOfUser;

import java.util.List;


public interface IRoleOfUserDAO extends BaseDAO<RoleOfUser> {
	
	/**
	 * 通过角色ID删除数据
	 * @param roleId 角色ID
	 */
	public void deleteByRoleId(String roleId);
	
	/**
	 * 通过用户ID删除数据
	 * @param userId 用户ID
	 */
	public void deleteByUserId(String userId);
	
	/**
	 * 给用户增加角色
	 * @param roleOfUsers 用户的角色对象List
	 */
	public void addBatch(List<RoleOfUser> roleOfUserList);
	
	/**
	 * 清除用户的所有角色
	 * @param userId 用户Id
	 */
	public void clean(String appCode, String userId);

}
