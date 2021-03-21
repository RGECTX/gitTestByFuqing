package com.greathack.homlin.daointerface.permission;

import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.permission.PowerInRole;

import java.util.List;

public interface IPowerInRoleDAO extends BaseDAO<PowerInRole> {
	
	/**
	 * 通过角色ID删除数据
	 * @param roleId 角色ID
	 */
	public void deleteByRoleId(String roleId);
	
	/**
	 * 给角色添加权限
	 * @param powerInRoles 用户的权限对象List
	 */
	public void addBatch(List<PowerInRole> powerInRoleList);
	
	/**
	 * 清除角色的所有权限
	 * @param roleId 角色ID
	 */
	public void clean(String appCode, String roleId);
}
