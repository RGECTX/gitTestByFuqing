package com.greathack.homlin.daointerface.permission;


import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.permission.PowerInUser;

import java.util.List;


public interface IPowerInUserDAO extends BaseDAO<PowerInUser> {
	
	/**
	 * 通过用户ID删除数据
	 * @param userId 用户ID
	 */
	public void deleteByUserId(String userId);

	
	/**
	 * 给用户添加权限
	 * @param powerInUsers 用户的权限对象List
	 */
	public void addBatch(List<PowerInUser> powerInUserList);
	
	/**
	 * 清除用户的所有权限
	 * @param userId 用户ID
	 */
	public void clean(String appCode, String userId);

}
