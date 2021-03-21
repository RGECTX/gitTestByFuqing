package com.greathack.homlin.daointerface.login;

import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.login.LoginInstance;


public interface ILoginInstancesDAO extends BaseDAO<LoginInstance> {

	public LoginInstance findByLoginCodeOnly(Object loginCode);
	
	//public void register(String appCode,String instanceId,List<LoginType> loginTypes) throws BizException;

}