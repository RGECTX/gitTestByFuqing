package com.greathack.homlin.daointerface.user;

import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.user.AddressItem;
import com.greathack.homlin.pojo.user.AddressItemSearchCriteria;

import java.util.List;


/**
 * 
* <p>Title: IAddressItemDAO</p>
* <p>Description: </p>
* <p>Company: </p> 
* @author zjb
* @date 2017年2月17日
 */
public interface IAddressItemDAO extends BaseDAO<AddressItem> {
	
	/**
	 * 
	* @Title: deleteByUserId
	* @Description: (删除用户全部地址项目)
	* @param userId
	* @return    设定文件
	* @return int    返回类型
	* @throws
	* @author zjb
	* @2017年2月25日
	 */
	int deleteByUserId(Long userId);
	
	
	/**
	 * 
	* @Title: setDefault
	* @Description: (设为默认)
	* @param id
	* @param @return    设定文件
	* @return int    返回类型
	* @throws
	 */
	int setDefault(Long id);
	/**
	 * 
	* @Title: setNotDefault
	* @Description: (清除默认)
	* @param  userId 所属用户ID
	* @return int    返回类型
	* @throws
	 */
	int setNotDefault(Long userId);
	
	/**
	 * 
	* @Title: search
	* @Description: (搜索)
	* @param  criteria 搜索条件
	* @return int    返回类型
	* @throws
	 */
	List<AddressItem> search(AddressItemSearchCriteria criteria);
	
	/**
	 * 
	 * @Title: getSearchRecordCount
	 * @Description: 获取搜索结果的总记录数
	 * @param criteria 搜索条件
	 * @return 总记录数
	 * @author greathack
	 * @date 2017年4月26日
	*/
	long getSearchResultCount(AddressItemSearchCriteria criteria);
}
