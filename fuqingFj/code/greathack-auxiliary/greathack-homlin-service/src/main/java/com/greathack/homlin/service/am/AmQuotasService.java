package com.greathack.homlin.service.am;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.am.IAmQuotasDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.am.AmQuotas;
import com.greathack.homlin.pojo.am.AmQuotasSearchCriteria;
import com.greathack.homlin.serviceinterface.am.IAmQuotasService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.system.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class AmQuotasService implements IAmQuotasService {

	private static Logger logger = LoggerFactory.getLogger(AmQuotasService.class);
	private static IAmQuotasDAO amQuotasDAO = (IAmQuotasDAO) DAOFactory.createDAO("IAmQuotasDAO");

	@Override
	public AmQuotas add(AmQuotas amQuotas) throws ServiceException {
		try {
			amQuotas.setCreateTime(Common.getCurrentTime());
			amQuotas.setId(IdCreator.createId("AmQuotas"));
			amQuotasDAO.add(amQuotas);
			return amQuotas;
		} catch (ServiceException e) {
			throw new ServiceException(ErrCode.UNKNOWN_ERROR,"UNKNOWN_ERROR");
		}
	}

	@Override
	public void update(AmQuotas amQuotas) {
		amQuotasDAO.update(amQuotas);
		
	}

	@Override
	public void delete(Long amQuotasId) {
		amQuotasDAO.delete(amQuotasId);

	}

	@Override
	public AmQuotas get(Long amQuotasId) {
		return amQuotasDAO.findById(amQuotasId);
	}

	@Override
	public List<AmQuotas> findByExample(AmQuotas amQuotas) {
		return amQuotasDAO.findByExample(amQuotas);
	}

	@Override
	public List<AmQuotas> findAll() {
		return amQuotasDAO.findAll();
	}

	@Override
	public AmQuotas getByUnitId(long unitId) {
		AmQuotas amQuotas= new AmQuotas();
		amQuotas.setOrgId(unitId);
		List<AmQuotas> amQuotasList=amQuotasDAO.findByExample(amQuotas);
		if(amQuotasList.size()>0){
			return  amQuotasList.get(0);
		}
		return null;
	}

	@Override
	public List<AmQuotas> search(AmQuotasSearchCriteria criteria) {
		List<AmQuotas> quotasList=new ArrayList<AmQuotas>();
		if(criteria==null){
			return quotasList;
		}
		return amQuotasDAO.search(criteria);
	}

	@Override
	public long getSearchResultCount(AmQuotasSearchCriteria criteria) {
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
				count=Long.toString(amQuotasDAO.getSearchResultCount(criteria));
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
