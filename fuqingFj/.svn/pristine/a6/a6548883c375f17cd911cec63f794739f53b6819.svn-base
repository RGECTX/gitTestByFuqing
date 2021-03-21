/**
*@文件名：AreaService.java
*@类路径：com.greathack.area.service
*@描述：
*@作者：greathack
*@日期：2017年5月4日
*@版本：V1.0
*/
package com.greathack.homlin.service.area;

import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.area.IAreaDAO;
import com.greathack.homlin.pojo.area.Area;
import com.greathack.homlin.pojo.area.AreaSearchCriteria;
import com.greathack.homlin.serviceinterface.area.IAreaService;
import com.greathack.homlin.system.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @Description: 区域管理接口
 * @author greathack
 * @date 2017年5月4日
 * @version V1.0
 */
public class AreaService implements IAreaService {
	
	private static Logger logger = LoggerFactory.getLogger(AreaService.class);
	private static IAreaDAO areaDAO = (IAreaDAO) DAOFactory.createDAO("IAreaDAO");

	@Override
	public Area getArea(long areaId) {
		return areaDAO.findById(areaId);
	}

    @Override
    public Area getAreaByAdcode(String adcode) {
        return areaDAO.findByAdcode(adcode);
    }

	/* (non-Javadoc)
	 * @see com.greathack.area.serviceinterface.IAreaService#search(com.greathack.area.pojo.AreaSearchCriteria)
	 */
	@Override
	public List<Area> search(AreaSearchCriteria criteria) {
		List<Area> areaList=new ArrayList<Area>();
		if(criteria==null){
			return areaList;
		}
		return areaDAO.search(criteria);
	}
	
	public long getSearchResultCount(AreaSearchCriteria criteria) {
		if(criteria==null){
			return 0;
		}
		String key=Integer.toString(criteria.hashCode());
		String count="0";
		Jedis jedis=null;
		try {
			jedis= SystemConfig.getJedisPool().getResource();
			if(jedis.exists(key)){
				count=jedis.get(key);
				//重新设置超时
				jedis.expire(key, Integer.parseInt(SystemConfig.getConfigData("RESULT_COUNT_EXPIRE")));
			}else{
				count=Long.toString(areaDAO.getSearchResultCount(criteria));
				jedis.set(key, count);
				//设置超时
				jedis.expire(key, Integer.parseInt(SystemConfig.getConfigData("RESULT_COUNT_EXPIRE")));
			}			
		} catch (Exception e) {
			logger.debug("redis有异常");
		}finally{
			if(jedis!=null){
				jedis.close();
			}			
		}	
		return Long.parseLong(count);
	}

}
