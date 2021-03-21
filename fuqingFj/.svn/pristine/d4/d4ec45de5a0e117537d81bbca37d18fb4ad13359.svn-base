package com.greathack.homlin.serviceinterface.category;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.category.Category;
import com.greathack.homlin.pojo.category.CategorySearchCriteria;

import java.util.List;


public interface ICategoryService {

	/**
	 * @Title : 获取类别信息
	 * @Description: 获取类别信息
	 * @version
	 * @param categoryId
	 * @return
	 * @throws
	 * @author wbc
	 * @date 2017-3-20
	 */
	Category getCategory(long categoryId);

	/**
	 * @Title : getCategoryList
	 * @Description: 获取所有类别列表
	 * @version
	 * @param appCode
	 * @return
	 * @throws
	 * @author wbc
	 * @date 2017-3-20
	 */
	List<Category> getCategoryList(String appCode);
	
	/**
	 * @Title : 获取直系上级类别列表
	 * @Description: 获取直系上级类别列表
	 * @version
	 * @param categoryId
	 * @return
	 * @throws
	 * @author wbc
	 * @date 2017-3-20
	 */
	List<Category> getParents(long categoryId);

	/**
	 * @Title : 获取子孙类别列表
	 * @Description: 获取子孙类别列表
	 * @version
	 * @param categoryId
	 * @return
	 * @throws
	 * @author wbc
	 * @date 2017-3-20
	 */
	List<Category> getDescendants(long categoryId);

	/**
	 * @Title : 获取父类别
	 * @Description: 获取父类别
	 * @version
	 * @param categoryId
	 * @return
	 * @throws
	 * @author wbc
	 * @date 2017-3-20
	 */
	Category getParent(long categoryId);

	/**
	 * @Title : 获取子类别列表(按sortNum倒序排序)
	 * @Description: 获取子类别列表
	 * @version
	 * @param categoryId
	 * @return
	 * @throws
	 * @author wbc
	 * @date 2017-3-20
	 */
	List<Category> getChildren(long categoryId);

	
	/**
	 * @Title : 搜索
	 * @Description: 搜索
	 * @version
	 * @param categorySearchCriteria
	 * @return
	 * @throws
	 * @author wbc
	 * @date 2017-3-20
	 */
	List<Category> search(CategorySearchCriteria categorySearchCriteria);
	
	long getSearchResultCount(CategorySearchCriteria criteria);
	/**
	 * 添加类别
	 * @param category
	 * @param Category
	 * @return
	 * @throws ServiceException
	 * @author lbb
	 * @date 2017-3-20
	 */
	Category addCategory(Category category)throws ServiceException;
	/**
	 * 删除类别
	 * @param categoryId
	 * @throws ServiceException
	 * @author lbb
	 * @date 2017-3-22
	 */
	void delCategory(long categoryId) throws ServiceException;
	/**
	 * 修改类别
	 * @param category
	 */
	void mdfyCategory(Category category)throws ServiceException;
	/**
	 *	修改类别排序
	 * @param category
	 */
	void sort(List<Long> categoryIdList);
	
	/**
	 * 
	 * @Title: 是否叶子节点
	 * @param categoryId
	 * @return
	 * @author Administrator
	 * @date 2017年11月8日
	*/
	boolean isLeaf(long categoryId);

	/**
	 * @Title : 通过类别编码获取类别信息
	 * @Description: 通过类别编码获取类别信息
	 * @version
	 * @param appCode
	 * @param categoryCode
	 * @return
	 */
	Category getCategoryByCode(String appCode, String categoryCode);
}
