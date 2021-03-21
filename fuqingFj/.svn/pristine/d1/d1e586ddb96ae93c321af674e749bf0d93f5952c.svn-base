/** 
* @文件名：IHolidayDAO.java  
* @类路径：com.greathack.holiday.daointerface  
* @描述：TODO
* @作者：greathack 
*/
package com.greathack.homlin.daointerface.holiday;

import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.holiday.Holiday;
import com.greathack.homlin.pojo.holiday.HolidaySearchCriteria;

import java.util.List;


/**
 * 
 * @author greathack
 */
public interface IHolidayDAO extends BaseDAO<Holiday> {
    
    /**
     * @param date 日期，格式yyyyMMdd
     * @return 如果是假日返回假日信息，否则返回null
     */
    Holiday findByDate(Integer date);

	/**
	 * 根据关键字查找假日的信息
	 * @param criteria
	 */	
	List<Holiday> search(HolidaySearchCriteria criteria);
	
	/**
	 * 
	 * @Title: getSearchRecordCount
	 * @Description: 获取搜索结果的总记录数
	 * @param criteria 搜索条件
	 * @return 总记录数
	*/
	long getSearchResultCount(HolidaySearchCriteria criteria);
}
