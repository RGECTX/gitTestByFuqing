package com.greathack.homlin.serviceinterface.system;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.SearchResult;
import com.greathack.homlin.pojo.system.Menu;
import com.greathack.homlin.pojo.system.MenuSearchCriteria;

import java.util.List;
import java.util.Map;


public interface IMenuService {

	Map<Long, Menu> getMenus();

	/**
	 * @Title : 获取菜单信息
	 * @Description: 获取菜单信息
	 * @version
	 * @param menuId
	 * @return
	 * @throws
	 */
	Menu getMenu(long menuId);

	/**
	 * @Title : getMenuList
	 * @Description: 获取所有菜单列表
	 * @version
	 * @return
	 * @throws
	 */
	List<Menu> getMenuList();

	/**
	 * @Title : 获取直系上级菜单列表
	 * @Description: 获取直系上级菜单列表
	 * @version
	 * @param menuId
	 * @return
	 * @throws
	 */
	List<Menu> getParents(long menuId);

	/**
	 * @Title : 获取子孙菜单列表
	 * @Description: 获取子孙菜单列表
	 * @version
	 * @param menuId
	 * @return
	 * @throws
	 */
	List<Menu> getDescendants(long menuId);

	/**
	 * @Title : 获取父菜单
	 * @Description: 获取父菜单
	 * @version
	 * @param menuId
	 * @return
	 * @throws
	 */
	Menu getParent(long menuId);

	/**
	 * @Title : 获取子菜单列表(按sortNum倒序排序)
	 * @Description: 获取子菜单列表
	 * @version
	 * @param menuId
	 * @return
	 * @throws
	 */
	List<Menu> getChildren(long menuId);


	/**
	 * @Title : 搜索
	 * @Description: 搜索
	 * @version
	 * @param menuSearchCriteria
	 * @return
	 * @throws
	 */
	SearchResult<Menu> search(MenuSearchCriteria menuSearchCriteria);

	/**
	 * 添加菜单
	 * @param menu
	 * @return
	 * @throws ServiceException
	 */
	Menu addMenu(Menu menu)throws ServiceException;
	/**
	 * 删除菜单
	 * @param menuId
	 * @throws ServiceException
	 */
	void delMenu(long menuId) throws ServiceException;
	/**
	 * 修改菜单
	 * @param menu
	 */
	void mdfyMenu(Menu menu)throws ServiceException;
	/**
	 *	修改菜单排序
	 * @param menuIdList
	 */
	void sort(List<Long> menuIdList);

	/**
	 *
	 * @Title: 是否叶子节点
	 * @param menuId
	 * @return
	*/
	boolean isLeaf(long menuId);

	/**
	 * @Title : 通过菜单编码获取菜单信息
	 * @Description: 通过菜单编码获取菜单信息
	 * @version
	 * @param appCode
	 * @param menuCode
	 * @return
	 */
	Menu getMenuByCode(String appCode, String menuCode);
}
