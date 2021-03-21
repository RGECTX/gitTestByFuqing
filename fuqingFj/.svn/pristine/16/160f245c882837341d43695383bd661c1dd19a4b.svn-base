package com.greathack.homlin.serviceinterface.login;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.login.LoginInstance;
import com.greathack.homlin.pojo.login.LoginType;

import java.util.List;


public interface ILoginInstanceService {
	
	/**
	 * 注册<br>
	 * 注册时，登录类型的密码必须有一个是非空的，且会把最后一个非空的密码作为统一密码
	 * @param appCode 应用编码
	 * @param instanceId 登录对象ID
	 * @param loginTypes 登录类型列表
	 * @throws ServiceException
	 */
	public void registerTx(String appCode, String instanceId, List<LoginType> loginTypes) throws ServiceException;

	/**
	 * 添加登录主体<br>
	 * @param appCode 应用编码
	 * @param instanceId 登录对象ID
	 * @return 注册好的登录主体
	 * @throws ServiceException
	 */
	public LoginInstance addLoginInstance(String appCode, String instanceId) throws ServiceException;
    
    /**
     * 删除登录主体<br>
     * @param appCode 应用编码
     * @param instanceId 登录对象ID
     * @return 注册好的登录主体
     * @throws ServiceException
     */
    public void delLoginInstance(String appCode, String instanceId) throws ServiceException;

	/**
	 * 添加登录方式<br>
	 * 添加时，会把最后一个非空的密码作为统一密码
	 * @param loginInstanceUnique 登录实体ID
	 * @param loginName 登录名
	 * @param password 密码
	 * @return 登录方式
	 * @throws ServiceException
	 */
	public LoginType addLoginType(long loginInstanceUnique, String appCode, String instanceId, String loginName, String password) throws ServiceException;
	
	/**
	 * 删除登录方式
	 * @param appCode 应用编码
	 * @param instanceId 登录主体ID
	 * @param loginName 登录名
	 */
	public void deleteLoginType(String appCode, String instanceId, String loginName);
	

	/**
	 * 修改登录名
	 * @param appCode 应用编码
	 * @param oldLoginName 原登录名
	 * @param newLoginName 新登录名
	 * @throws ServiceException
	 */
	public void mdfyLoginName(String appCode, String oldLoginName, String newLoginName) throws ServiceException;

	/**
	 * 修改登录密码<br>
	 * 修改密码时，如果新密码为空，则只会修改本登录名的独立密码。否则不只会修改本登录名的密码，而且同时会修改统一密码
	 * @param appCode 登录实体ID
	 * @param loginName 登录名
	 * @param newPassword 新密码
	 * @throws ServiceException
	 */
	public void mdfyPassword(String appCode, String loginName, String newPassword) throws ServiceException;

	/**
	 * 通过appCode和instanceId查找唯一的登录主体
	 * @param appCode
	 * @param instanceId
	 * @return 登录主体
	 */
	public LoginInstance getLoginInstance(String appCode, String instanceId);

	/**
	 * 锁定登录对象
	 * @param appCode 所属登录主体
	 */
	public void lock(String appCode, String instanceId) throws ServiceException;

    /**
     * 解锁登录对象
     * @param appCode 所属登录主体
     */
    public void unlock(String appCode, String instanceId) throws ServiceException;
	
	/**
	 * 获取登录对象ID
	 * @param loginCode 登录码
	 * @return 登录对象ID
	 */
	public String getInstanceId(String loginCode) throws ServiceException;
}