/**
 * 
 */
package com.greathack.homlin.daointerface.category;

import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.category.Category;
import com.greathack.homlin.pojo.category.CategorySearchCriteria;

import java.util.List;


/**
 * @author greathack
 * 
 */
public interface ICategoryDAO extends BaseDAO<Category> {

	/**
	 * @Title : 搜索
	 * @Description: 搜索
	 * @version
	 * @param categorySearchCriteria
	 * @return
	 * @throws
	 * @author wbc
	 * @date 2017-3-21
	 */
	List<Category> search(CategorySearchCriteria categorySearchCriteria);
	
	long getSearchResultCount(CategorySearchCriteria criteria);

	/**
	 * @Title : getDescendants
	 * @Description: 根据ID 获取子孙类别列表
	 * @version
	 * @param categoryId
	 * @return
	 * @throws
	 * @author wbc
	 * @date 2017-3-21
	 */
	List<Category> getDescendants(long categoryId);

}
