/**
 * 
 */
package com.greathack.homlin.service.login;

import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.login.ILoginInstancesDAO;
import com.greathack.homlin.daointerface.login.ILoginLogsDAO;
import com.greathack.homlin.daointerface.login.ILoginTypesDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.login.LoginInstance;
import com.greathack.homlin.pojo.login.LoginLog;
import com.greathack.homlin.pojo.login.LoginTypeInfo;
import com.greathack.homlin.serviceinterface.login.ILoginService;
import com.greathack.utils.security.MD5;
import com.greathack.utils.tools.TypeOption;
import com.greathack.utils.tools.Utils;
import com.greathack.utils.tools.Validation;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author greathack
 *
 */
public class LoginService implements ILoginService {
	
	/* （非 Javadoc）
	 * @see com.greathack.loginManager.service.ILoginService#login(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    //独立密码非空，就验证独立密码。独立密码为空，验证统一密码
	public LoginInstance login(String appCode, String loginName, String password, String loginIp) throws ServiceException {
		LoginTypeInfo condition=new LoginTypeInfo();
		condition.setAppCode(appCode);
		condition.setLoginName(loginName);
		if(!Validation.isEmpty(password)){//独立密码非空
			condition.setPassword(MD5.createMD5(password));
		}else{//独立密码为空，验证统一密码
			condition.setUnifiedPassword(MD5.createMD5(password));
		}
		ILoginTypesDAO loginTypesDAO=(ILoginTypesDAO) DAOFactory.createDAO("ILoginTypesDAO");
		List<LoginTypeInfo> loginTypeInfos=loginTypesDAO.findLoginTypeInfoByExample(condition);
		if(loginTypeInfos.size()==0){//登录名或密码错误
			throw new ServiceException(20009,"LOGIN_FAILURE");
		}
		LoginTypeInfo loginTypeInfo=loginTypeInfos.get(0);
		if(Objects.equals(loginTypeInfo.getLoginState(), LoginInstance.LOGIN_STATE_LOCK)){//被锁定的用户不能登录
			throw new ServiceException(20011,"LOGIN_STATE_LOCK");
		}
		
		//登录成功--开始		
		String loginCode=createLoginCode(loginTypeInfo);
		long lngLoginTime=System.currentTimeMillis();
		
		if(Objects.equals(loginTypeInfo.getLoginState(), LoginInstance.LOGIN_STATE_ON_LINE)){//如果是登录状态，就先强制退出登录
			logout(loginCode, LoginLog.OPT_TYPE__FORCE_LOGOUT);//强制退出
		}
		
		//设置登录主体信息
		ILoginInstancesDAO loginInstancesDAO=(ILoginInstancesDAO) DAOFactory.createDAO("ILoginInstancesDAO");
		LoginInstance logininstances=loginInstancesDAO.findById(loginTypeInfo.getLoginInstanceUnique());
		logininstances.setLoginCode(loginCode);
		if(!Validation.isEmpty(loginIp)){
			logininstances.setLoginRegion(Utils.getRegionByIPInTaobao(loginIp));
		}
		logininstances.setLoginTime(lngLoginTime);
		logininstances.setLoginState(LoginInstance.LOGIN_STATE_ON_LINE);
		loginInstancesDAO.update(logininstances);
		
		//登录日志
		ILoginLogsDAO loginLogsDAO=(ILoginLogsDAO) DAOFactory.createDAO("ILoginLogsDAO");
		LoginLog loginlog=new LoginLog();
		loginlog.setCreateTime(lngLoginTime);
		loginlog.setLoginCode(loginCode);
		loginlog.setLoginInstanceUnique(logininstances.getLoginInstanceUnique());
		loginlog.setLoginIp(loginIp);
		loginlog.setLoginRegion(logininstances.getLoginRegion());
		loginlog.setOptType(LoginLog.OPT_TYPE_LOGIN);//登录
		loginlog.setRemark(TypeOption.formatDateTime(new Date(lngLoginTime), "yyyy-MM-dd HH:mm:ss.SSS")+" 登录成功");
		loginLogsDAO.add(loginlog);
		//登录成功--结束
		return logininstances;
	}
	
	/* （非 Javadoc）
	 * @see com.greathack.loginManager.service.ILoginService#logout(java.lang.String, int)
	 */
	public void logout(String loginCode,int optType){
		if(Validation.isEmpty(loginCode)){
			return;
		}
		ILoginInstancesDAO loginInstancesDAO=(ILoginInstancesDAO) DAOFactory.createDAO("ILoginInstancesDAO");
		LoginInstance logininstances=loginInstancesDAO.findByLoginCodeOnly(loginCode);
		if(logininstances==null){
			return;
		}
		logininstances.setLoginCode("");//清除登录码
		logininstances.setLoginState(LoginInstance.LOGIN_STATE_OFF_LINE);//离线状态
		loginInstancesDAO.update(logininstances);
		//退出登录日志
		createLogoutLog(logininstances.getLoginInstanceUnique(),loginCode,optType);
	}
	
	/* （非 Javadoc）
	 * @see com.greathack.loginManager.service.ILoginService#createLogoutLog(long, java.lang.String, int)
	 */
	public void createLogoutLog(long loginInstanceUnique,String loginCode,int optType){
		long lngLoginTime=System.currentTimeMillis();
		ILoginLogsDAO loginLogsDAO=(ILoginLogsDAO) DAOFactory.createDAO("ILoginLogsDAO");
		LoginLog loginlog=new LoginLog();
		loginlog.setCreateTime(lngLoginTime);
		loginlog.setLoginCode(loginCode);
		loginlog.setLoginInstanceUnique(loginInstanceUnique);
		loginlog.setOptType(optType);//退出登录
		loginlog.setRemark(TypeOption.formatDateTime(new Date(lngLoginTime), "yyyy-MM-dd HH:mm:ss.SSS")+" 退出登录");
		loginLogsDAO.add(loginlog);
	}
	
	/* （非 Javadoc）
	 * @see com.greathack.loginManager.service.ILoginService#createLoginCode(com.greathack.loginManager.pojo.LoginTypeInfo)
	 */
	public String createLoginCode(LoginTypeInfo loginTypeInfo){
		return MD5.createMD5(loginTypeInfo.getAppCode()+loginTypeInfo.getLoginName()+System.currentTimeMillis());
	}

}
