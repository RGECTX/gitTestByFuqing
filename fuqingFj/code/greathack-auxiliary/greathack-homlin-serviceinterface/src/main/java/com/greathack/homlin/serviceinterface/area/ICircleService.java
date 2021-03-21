/** 
* @文件名：ICircleService.java  
* @类路径：com.greathack.area.serviceinterface  
* @描述：TODO
* @作者：greathack 
* @日期：2017年5月3日 
* @版本：V1.0  
*/
package com.greathack.homlin.serviceinterface.area;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.area.Circle;
import com.greathack.homlin.pojo.area.CircleSearchCriteria;

import java.util.List;


/**
 * 
 * @Description: TODO
 * @author greathack
 * @date 2017年5月3日
 * @version V1.0
 */
public interface ICircleService {
	
	Circle addCircle(Circle circle) throws ServiceException;
	
	void delCircle(long circleId);
	
	void disableCircle(long circleId) throws ServiceException;
	
	void enableCircle(long circleId) throws ServiceException;
	
	void mdfyCircle(Circle circle) throws ServiceException;
	
	Circle getCircle(long circleId);
	
	List<Circle> search(CircleSearchCriteria circleSearchCriteria);
	
	long getSearchResultCount(CircleSearchCriteria criteria);

}
