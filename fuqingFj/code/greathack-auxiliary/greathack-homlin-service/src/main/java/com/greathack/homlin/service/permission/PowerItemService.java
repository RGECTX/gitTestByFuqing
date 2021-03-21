package com.greathack.homlin.service.permission;

import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.permission.IPowerItemDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.permission.PowerItem;
import com.greathack.homlin.pojo.permission.PowerItemSearchCriteria;
import com.greathack.homlin.serviceinterface.permission.IPowerItemService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PowerItemService implements IPowerItemService {
    
    private static Logger logger = LoggerFactory.getLogger(PowerItemService.class);
    private IPowerItemDAO powerItemDAO = (IPowerItemDAO) DAOFactory.createDAO("IPowerItemDAO");
    

	/* (non-Javadoc)
     * @see com.greathack.permission.service.IPowerItemService#add(com.greathack.permission.pojo.PowerItem)
     */
	@Override
    public PowerItem add(PowerItem powerItem) throws ServiceException {
		if(Validation.isEmpty(powerItem.getPowerCode())){//权限编码不能为空
		    logger.info("权限编码不能为空");
			throw new ServiceException(230002,"POWER_CODE_REQUIRED");
		}
		PowerItem condition= new PowerItem();
		condition.setAppCode(powerItem.getAppCode());
		condition.setPowerCode(powerItem.getPowerCode());
		if(powerItemDAO.findByExample(condition).size()>0){//权限编码已经存在
		    logger.info("权限编码已经存在");
			throw new ServiceException(230003,"POWER_CODE_EXIST");
		}
		powerItem.setPowerItemId(IdCreator.createId("PowerItem"));
		powerItemDAO.add(powerItem);
		return powerItem;
	}

	
	/* (non-Javadoc)
     * @see com.greathack.permission.service.IPowerItemService#update(com.greathack.permission.pojo.PowerItem)
     */
	@Override
    public void mdfy(PowerItem powerItem) throws ServiceException {
		PowerItem temp=powerItemDAO.findById(powerItem.getPowerItemId());
		if(temp==null){//要修改的权限项目不存在
		    logger.info("要修改的权限项目不存在");
		    throw new ServiceException(230004,"POWER_ITEM_NOT_EXIST");
		}
		if(Validation.isEmpty(powerItem.getPowerCode())){//权限编码不能为空
		    logger.info("权限编码不能为空");
		    throw new ServiceException(230002,"POWER_CODE_REQUIRED");
		}
		PowerItem condition=new PowerItem();
		condition.setAppCode(temp.getAppCode());
		condition.setPowerCode(powerItem.getPowerCode());
		List<PowerItem> powerItems=powerItemDAO.findByExample(condition);
		if(powerItems.size()>0 && !Objects.equals(powerItems.get(0).getPowerItemId(),powerItem.getPowerItemId())){//权限编码已经存在
		    logger.info("权限编码已经存在");
		    throw new ServiceException(230003,"POWER_CODE_EXIST");
		}
		powerItemDAO.update(powerItem);
	}

	
	/* (non-Javadoc)
     * @see com.greathack.permission.service.IPowerItemService#delete(int)
     */
	@Override
    public void delete(long powerItemId){
	    powerItemDAO.delete(powerItemId);
	}

	
	@Override
    public PowerItem getPowerItem(long powerItemId) {
	    return powerItemDAO.findById(powerItemId);
    }


	@Override
	public PowerItem getPowerItem(String appCode, String powerCode) {
		PowerItem instance=new PowerItem();
		instance.setAppCode(appCode);
		instance.setPowerCode(powerCode);
		List<PowerItem> powerItemList=powerItemDAO.findByExample(instance);
		if(powerItemList.size()>0){
			return powerItemList.get(0);
		}
		return null;
	}

    /* (non-Javadoc)
     * @see com.greathack.permission.service.IPowerItemService#findAll(java.lang.String)
     */
	@Override
    public List<PowerItem> getAllList(String appCode){
		return powerItemDAO.findAll(appCode);
	}


    @Override
    public List<PowerItem> search(PowerItemSearchCriteria criteria) {
        List<PowerItem> powerItemList=new ArrayList<PowerItem>();
        if(criteria==null){
            return powerItemList;
        }       
        return powerItemDAO.search(criteria);
    }


    @Override
    public long getSearchResultCount(PowerItemSearchCriteria criteria) {
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
                count=Long.toString(powerItemDAO.getSearchResultCount(criteria));
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
