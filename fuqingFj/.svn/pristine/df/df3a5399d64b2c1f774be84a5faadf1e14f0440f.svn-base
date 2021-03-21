/**
 * 
 */
package com.greathack.homlin.service.login;

import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.login.ILoginInstancesDAO;
import com.greathack.homlin.daointerface.login.ILoginTypesDAO;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.login.LoginInstance;
import com.greathack.homlin.pojo.login.LoginType;
import com.greathack.homlin.serviceinterface.login.ILoginInstanceService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.utils.security.MD5;
import com.greathack.utils.tools.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author greathack
 *
 */
public class LoginInstanceService implements ILoginInstanceService {
	private static Logger logger = LoggerFactory.getLogger(LoginInstanceService.class);
	
	//private SessionFactory sessionFactory=(SessionFactory)SystemConfig.getApplicationContext().getBean("sessionFactory");
	/**
	 * 添加登录主体<br>
	 * @param appCode 应用编码
	 * @param instanceId 登录对象ID
	 * @return 注册好的登录主体
	 * @throws BizException
	 */
	public LoginInstance addLoginInstance(String appCode, String instanceId) throws ServiceException {
		
		if(Validation.isEmpty(appCode)){//appCode不能为空
			throw new ServiceException(10001,"INVALID_APP_CODE");
		}
		if(Validation.isEmpty(instanceId)){//登录对象ID不能为空
			throw new ServiceException(20001,"INVALID_INSTANCE_ID");
		}
		LoginInstance loginInstance=new LoginInstance();
		loginInstance.setAppCode(appCode);
		loginInstance.setInstanceId(instanceId);
		ILoginInstancesDAO loginInstancesDAO=(ILoginInstancesDAO) DAOFactory.createDAO("ILoginInstancesDAO");
		if(loginInstancesDAO.findByExample(loginInstance).size()>0){
			throw new ServiceException(20003,"LOGIN_INSTANCE_EXIST");
		}
		
		loginInstance.setLoginTime(0L);
		loginInstance.setLoginState(LoginInstance.LOGIN_STATE_OFF_LINE);//默认设置为离线状态
		loginInstance.setLoginInstanceUnique(IdCreator.createId("LoginInstance"));
		loginInstancesDAO.add(loginInstance);
		return loginInstance;
	}
	
	/**
     * 删除登录主体<br>
     * @param appCode 应用编码
     * @param instanceId 登录对象ID
     * @return 注册好的登录主体
     * @throws BizException
     */
    public void delLoginInstance(String appCode,String instanceId) throws ServiceException {
        
        if(Validation.isEmpty(appCode)){//appCode不能为空
            throw new ServiceException(10001,"INVALID_APP_CODE");
        }
        if(Validation.isEmpty(instanceId)){//登录对象ID不能为空
            throw new ServiceException(20001,"INVALID_INSTANCE_ID");
        }
        LoginInstance loginInstance=new LoginInstance();
        loginInstance.setAppCode(appCode);
        loginInstance.setInstanceId(instanceId);
        ILoginInstancesDAO loginInstancesDAO=(ILoginInstancesDAO) DAOFactory.createDAO("ILoginInstancesDAO");
        
        //删除登录方式开始
        LoginType condition=new LoginType();
        condition.setAppCode(appCode);
        condition.setInstanceId(instanceId);
        ILoginTypesDAO loginTypesDAO=(ILoginTypesDAO) DAOFactory.createDAO("ILoginTypesDAO");
        List<LoginType> loginTypeList=loginTypesDAO.findByExample(condition);
        for(LoginType loginType:loginTypeList){
            loginTypesDAO.delete(loginType.getLoginTypeUnique());
        } 
        //删除登录方式结束
        
        List<LoginInstance> loginInstanceList=loginInstancesDAO.findByExample(loginInstance);
        for(LoginInstance temp:loginInstanceList){
            loginInstancesDAO.delete(temp.getLoginInstanceUnique());
        }  
    }
	
	
	/**
	 * 添加登录方式<br>
	 * 添加时，会把最后一个非空的密码作为统一密码
	 * @param loginInstanceUnique 登录实体ID
	 * @param loginName 登录名
	 * @param password 密码
	 * @return 登录方式
	 * @throws BizException
	 */
	public LoginType addLoginType(long loginInstanceUnique, String appCode, String instanceId, String loginName, String password) throws ServiceException {
		if(Validation.isEmpty(loginName)){//登录名不能为空
			throw new ServiceException(20002,"LOGIN_NAME_EMPTY");
		}
		if(loginInstanceUnique==0L){
			throw new ServiceException(20004,"INVALID_LOGIN_INSTANCE");
		}
		LoginType logintype=new LoginType();
		logintype.setAppCode(appCode);
		logintype.setLoginName(loginName);
		ILoginTypesDAO loginTypesDAO=(ILoginTypesDAO) DAOFactory.createDAO("ILoginTypesDAO");
		List<LoginType> logintypes=loginTypesDAO.findByExample(logintype);
		logger.error(logintypes.size()+"");
		if(logintypes.size()>0){
			throw new ServiceException(20005,"LOGIN_TYPE_EXIST");
		}
		logintype.setInstanceId(instanceId);
		logintype.setLoginInstanceUnique(loginInstanceUnique);
		if(Validation.isEmpty(password)){
			logintype.setPassword(null);
		}else{
			logintype.setPassword(MD5.createMD5(password));
		}
		logintype.setLoginTypeUnique(IdCreator.createId("LoginType"));
		loginTypesDAO.add(logintype);
		return logintype;
	}
	
	/**
	 * 删除登录方式
	 * @param appCode 应用编码
	 * @param instanceId 登录主体ID
	 * @param loginName 登录名
	 */
	public void deleteLoginType(String appCode,String instanceId,String loginName){
		LoginType condition=new LoginType();
		condition.setAppCode(appCode);
		condition.setInstanceId(instanceId);
		condition.setLoginName(loginName);
		ILoginTypesDAO loginTypesDAO=(ILoginTypesDAO) DAOFactory.createDAO("ILoginTypesDAO");
		loginTypesDAO.delete(condition.getLoginTypeUnique());
	}
	
	/**
	 * 注册<br>
	 * 注册时，登录类型的密码必须有一个是非空的，且会把最后一个非空的密码作为统一密码<br>
	 * 如果用微信openid等作为唯一登录标识，则请把密码设为该openid
	 * @param appCode 应用编码
	 * @param instanceId 登录对象ID
	 * @param loginTypes 登录类型列表
	 * @throws BizException
	 */
	public void registerTx(String appCode,String instanceId,List<LoginType> loginTypes) throws ServiceException {
		LoginInstance loginInstance=addLoginInstance(appCode, instanceId);
		int intEmptyCount=0;
		for(LoginType loginType:loginTypes){
			if(!Validation.isEmpty(loginType.getPassword())){
				loginInstance.setPassword(MD5.createMD5(loginType.getPassword()));
				intEmptyCount++;
			}
		}
		if(intEmptyCount==0){//密码不能全部为空
			logger.error("PASSWORD_EMPTY");
			throw new ServiceException(20015,"PASSWORD_EMPTY");
		}
		ILoginInstancesDAO loginInstancesDAO=(ILoginInstancesDAO) DAOFactory.createDAO("ILoginInstancesDAO");
		loginInstancesDAO.update(loginInstance);
		for(LoginType loginType:loginTypes){//添加登录类型
			addLoginType(loginInstance.getLoginInstanceUnique(),appCode,instanceId,loginType.getLoginName(),loginType.getPassword());
		}
		/*ILoginInstancesDAO loginInstancesDAO=(ILoginInstancesDAO)DAOFactory.createDAO("ILoginInstancesDAO");
		loginInstancesDAO.register(appCode, instanceId, loginTypes);*/
	}
	
	
	/**
	 * 修改登录名
	 * @param appCode 应用编码
	 * @param oldLoginName 原登录名
	 * @param newLoginName 新登录名
	 * @throws BizException
	 */
	public void mdfyLoginName(String appCode,String oldLoginName,String newLoginName) throws ServiceException {
		if(Validation.isEmpty(newLoginName)){//登录名不能为空
			throw new ServiceException(20002,"LOGIN_NAME_EMPTY");
		}
		if(oldLoginName.equals(newLoginName)){
		    return;
		}
		LoginType condition=new LoginType();
		condition.setAppCode(appCode);
		ILoginTypesDAO loginTypesDAO=(ILoginTypesDAO) DAOFactory.createDAO("ILoginTypesDAO");
		condition.setLoginName(newLoginName);
		List<LoginType> types=loginTypesDAO.findByExample(condition);
		if(types.size()>0){
			throw new ServiceException(20005,"LOGIN_TYPE_EXIST");
		}
		condition.setLoginName(oldLoginName);
		types=loginTypesDAO.findByExample(condition);
		if(types.size()==0){
			throw new ServiceException(20006,"LOGIN_TYPE_NOT_EXIST");
		}
		LoginType logintypes=types.get(0);
		logintypes.setLoginName(newLoginName);
		loginTypesDAO.update(logintypes);
	}
	
	
	/**
	 * 修改登录密码<br>
	 * 修改密码时，如果新密码为空，则只会修改本登录名的独立密码。否则不只会修改本登录名的密码，而且同时会修改统一密码
	 * @param appCode 登录实体ID
	 * @param loginName 登录名
	 * @param newPassword 新密码
	 * @throws BizException
	 */
	public void mdfyPassword(String appCode,String loginName,String newPassword) throws ServiceException {
		LoginType logintypes=new LoginType();
		logintypes.setAppCode(appCode);
		logintypes.setLoginName(loginName);
		ILoginTypesDAO loginTypesDAO=(ILoginTypesDAO) DAOFactory.createDAO("ILoginTypesDAO");
		List<LoginType> types=loginTypesDAO.findByExample(logintypes);
		if(types.size()==0){
			throw new ServiceException(20006,"LOGIN_TYPE_NOT_EXIST");
		}
		logintypes=types.get(0);
		logintypes.setPassword(MD5.createMD5(newPassword));
		loginTypesDAO.update(logintypes);
		if(!Validation.isEmpty(newPassword)){
			ILoginInstancesDAO loginInstancesDAO=(ILoginInstancesDAO) DAOFactory.createDAO("ILoginInstancesDAO");
			LoginInstance loginInstance=loginInstancesDAO.findById(logintypes.getLoginInstanceUnique());
			loginInstance.setPassword(MD5.createMD5(newPassword));
			loginInstancesDAO.update(loginInstance);
		}
	}


	/**
	 * 通过appCode和instanceId查找唯一的登录主体
	 * @param appCode
	 * @param instanceId
	 * @return 登录主体
	 */
	public LoginInstance getLoginInstance(String appCode, String instanceId) {
		LoginInstance condition=new LoginInstance();
		condition.setAppCode(appCode);
		condition.setInstanceId(instanceId);
		ILoginInstancesDAO loginInstancesDAO=(ILoginInstancesDAO) DAOFactory.createDAO("ILoginInstancesDAO");
		List<LoginInstance> loginInstances=loginInstancesDAO.findByExample(condition);
		if(loginInstances.size()>0){
			return loginInstances.get(0);
		}
		return null;
	}
	

	/**
	 * 锁定登录对象
	 * @param appCode 所属登录主体
	 */
	public void lock(String appCode, String instanceId)  throws ServiceException {
		ILoginInstancesDAO loginInstancesDAO=(ILoginInstancesDAO) DAOFactory.createDAO("ILoginInstancesDAO");
		LoginInstance loginInstance=getLoginInstance(appCode, instanceId);
		if(loginInstance==null){//无效的登录主体
			throw new ServiceException(20004,"INVALID_LOGIN_INSTANCE");
		}
		loginInstance.setLoginState(LoginInstance.LOGIN_STATE_LOCK);
		loginInstancesDAO.update(loginInstance);
	}


	@Override
    public void unlock(String appCode, String instanceId) throws ServiceException {
	    ILoginInstancesDAO loginInstancesDAO=(ILoginInstancesDAO) DAOFactory.createDAO("ILoginInstancesDAO");
        LoginInstance loginInstance=getLoginInstance(appCode, instanceId);
        if(loginInstance==null){//无效的登录主体
            throw new ServiceException(20004,"INVALID_LOGIN_INSTANCE");
        }
        loginInstance.setLoginState(LoginInstance.LOGIN_STATE_OFF_LINE);
        loginInstancesDAO.update(loginInstance);
        
    }


    /**
	 * 获取登录对象ID
	 * @param loginCode 登录码
	 * @return 登录对象ID
	 */
	public String getInstanceId(String loginCode) throws ServiceException {
		ILoginInstancesDAO loginInstancesDAO=(ILoginInstancesDAO) DAOFactory.createDAO("ILoginInstancesDAO");
		LoginInstance loginInstance=loginInstancesDAO.findByLoginCodeOnly(loginCode);
		if(loginInstance==null){//登录主体不在线
			throw new ServiceException(20014,"INSTANCE_OFF_LINE");
		}
		return loginInstance.getInstanceId();
	}

}
