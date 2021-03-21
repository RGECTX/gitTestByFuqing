/** 
* @文件名：IAreaDAO.java  
* @类路径：com.greathack.area.daointerface  
* @描述：TODO
* @作者：greathack 
* @日期：2017年5月3日 
* @版本：V1.0  
*/
package com.greathack.homlin.daointerface.area;

import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.area.Area;
import com.greathack.homlin.pojo.area.AreaSearchCriteria;

import java.util.List;


/**
 * 
 * @Description: TODO
 * @author greathack
 * @date 2017年5月3日
 * @version V1.0
 */
public interface IAreaDAO extends BaseDAO<Area> {
    
    Area findByAdcode(String adcode);

	/**
	 * 根据关键字查找区域的信息
	 * 2017.2.23
	 * @param criteria
	 * @author lbb
	 */	
	List<Area> search(AreaSearchCriteria criteria);
	
	/**
	 * 
	 * @Title: getSearchRecordCount
	 * @Description: 获取搜索结果的总记录数
	 * @param criteria 搜索条件
	 * @return 总记录数
	 * @author greathack
	 * @date 2017年4月26日
	*/
	long getSearchResultCount(AreaSearchCriteria criteria);
}
