/** 
* @文件名：IHolidayService.java  
* @类路径：com.greathack.holiday.serviceinterface  
* @描述：TODO
* @作者：greathack 
* @日期：2017年5月4日 
* @版本：V1.0  
*/
package com.greathack.homlin.serviceinterface.holiday;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.holiday.Holiday;
import com.greathack.homlin.pojo.holiday.HolidaySearchCriteria;

import java.util.List;


/**
 * 
 * @Description: TODO
 * @author greathack
 */
public interface IHolidayService {

    /**
     * 新增假日
     * @param holiday 要新增的假日
     */
    Holiday add(Holiday holiday) throws ServiceException;


    /**
     * 修改假日
     * @param holiday 修改的假日
     */
    void mdfy(Holiday holiday) throws ServiceException;


    /**
     * 删除假日
     * @param holidayId 假日ID
     */
    void delete(long holidayId);
	
	Holiday getHoliday(long holidayId);
    
    Holiday getHolidayByDate(Integer date);
	
	List<Holiday> search(HolidaySearchCriteria holidaySearchCriteria);
	
	long getSearchResultCount(HolidaySearchCriteria criteria);

}
