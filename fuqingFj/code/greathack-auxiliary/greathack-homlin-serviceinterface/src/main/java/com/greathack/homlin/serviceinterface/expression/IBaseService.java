package com.greathack.homlin.serviceinterface.expression;

import com.greathack.homlin.exception.ServiceException;

import java.util.List;


public interface IBaseService<T> {
	/**
	 * 添加实例
	 * @param instance 实例对象
	 * @return 添加后的实例对象
	 */
	public T add(T instance) throws ServiceException;
	
	/**
	 * 删除实例
	 * @param instanceId 实例ID
	 */
	void delete(Object instanceId);
	
	/**
	 * 修改实例
	 * @param instance 实例对象
	 * @throws ServiceException
	 */
	void modify(T instance) throws ServiceException;
	
	/**
	 * 获取实例对象
	 * @param instanceId 实例ID
	 * @return 实例对象
	 */
	T get(Object instanceId);
	
	/**
	 * 获取所有实例对象列表
	 * @param appCode 应用编码
	 * @return 所有实例对象列表
	 */
	List<T> getAllList(String appCode);
}
