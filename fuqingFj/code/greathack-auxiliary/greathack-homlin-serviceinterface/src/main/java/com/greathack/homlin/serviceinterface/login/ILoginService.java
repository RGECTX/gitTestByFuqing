package com.greathack.homlin.serviceinterface.login;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.login.LoginInstance;
import com.greathack.homlin.pojo.login.LoginTypeInfo;


public interface ILoginService {

	/**
	 * 登录<br>
	 * 先验证独立密码，如果独立密码为空则验证统一密码
	 * @param appCode 应用编码
	 * @param loginName 登录名
	 * @param password 密码，可为空
	 * @param loginIp 登录地IP地址，可为空
	 * @return 返回登录主体
	 * @throws BizException
	 */
	public LoginInstance login(String appCode, String loginName, String password, String loginIp) throws ServiceException;

	/**
	 * 退出登录
	 * @param loginCode 登录码
	 * @param optType 退出类型，2：退出登录，4：超时退出，8：强制退出（被新的登录踢掉）
	 */
	public void logout(String loginCode, int optType);

	/**
	 * 创建退出登录日志
	 * @param loginInstanceUnique 所属登录主体
	 * @param loginCode 登录码
	 * @param optType 退出登录类型
	 */
	public void createLogoutLog(long loginInstanceUnique, String loginCode, int optType);

	/**
	 * 生成登录码
	 * @param loginTypeInfo 登录方式信息
	 * @return 登录码
	 */
	public String createLoginCode(LoginTypeInfo loginTypeInfo);	


}