package com.greathack.homlin.daointerface.login;

import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.login.LoginType;
import com.greathack.homlin.pojo.login.LoginTypeInfo;

import java.util.List;


public interface ILoginTypesDAO extends BaseDAO<LoginType> {
	
	List<LoginTypeInfo> findLoginTypeInfoByExample(LoginTypeInfo instance);

}