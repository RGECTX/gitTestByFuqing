/**
 * 
 */
package com.greathack.homlin.dao.factory;

import com.greathack.homlin.common.SpringContextHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author greathack
 *
 */
public class DAOFactory {
	
	private static Map<String,Object> daoMap=new HashMap<String,Object>();
	
	/**
	 * 创建DAO
	 * @param interfaceName DAO接口名称
	 * @return 按DAO接口生产的DAO对象
	 */
	public static Object createDAO(String interfaceName){
		if(!daoMap.containsKey(interfaceName)){
			//daoMap.put(interfaceName, SystemConfig.getApplicationContext().getBean(interfaceName));
			daoMap.put(interfaceName, SpringContextHolder.getBean(interfaceName));
		}
		return daoMap.get(interfaceName);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		IUrlLinkDAO DAO = (IUrlLinkDAO) DAOFactory.createDAO("IUrlLinkDAO");
//		UrlLink link = DAO.findById(1);
//		System.out.println(link.getLinkName());

	}

}
