package com.greathack.homlin.service.expression;

import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.expression.ICriteriaDAO;
import com.greathack.homlin.daointerface.expression.IExpressionDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.expression.Criteria;
import com.greathack.homlin.pojo.expression.CriteriaSearchCriteria;
import com.greathack.homlin.pojo.expression.Expression;
import com.greathack.homlin.serviceinterface.expression.ICriteriaService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.TypeOption;
import com.greathack.utils.tools.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class CriteriaService implements ICriteriaService {

	private static Logger logger = LoggerFactory
			.getLogger(CriteriaService.class);

	// 注入criteriaDAO
	private ICriteriaDAO criteriaDAO = (ICriteriaDAO) DAOFactory.createDAO("ICriteriaDAO");
	private IExpressionDAO expressionDAO = (IExpressionDAO) DAOFactory.createDAO("IExpressionDAO");
	

	@Override
	public Criteria add(Criteria criteria) throws ServiceException {
		if(criteria==null){
			return null;
		}
		if(Validation.isEmpty(criteria.getAppCode())){
			throw new ServiceException(60001,"INVALID_PARAMS");
		}
		if(criteria.getExpressionId()==null){
			throw new ServiceException(60001,"INVALID_PARAMS");
		}
		if(criteria.getCriteriaType()==null){
			throw new ServiceException(60001,"INVALID_PARAMS");
		}
        if(criteria.getParentId()==null){
        	criteria.setParentId(0L);
        }
        Expression expression=expressionDAO.findById(criteria.getExpressionId());
        if(criteria.getExpressionId()!=0 && expression==null){
        	throw new ServiceException(60001,"INVALID_PARAMS");
        }
		
		if(criteria.getParentId()==0){
			criteria.setParents("0");
		}else{
			Criteria parent=criteriaDAO.findById(criteria.getParentId());
			if(parent==null){
				throw new ServiceException(60001,"INVALID_PARAMS");
			}
			criteria.setParents(parent.getParents()+","+parent.getCriteriaId());
		}
		
		if (criteria.getSortNum() == null) {
			criteria.setSortNum(0L);
		}
		if(criteria.getState()==null){
			criteria.setState(Criteria.STATE_ENABLE);
		}
		criteria.setCriteriaId(IdCreator.createId("Criteria"));
		criteriaDAO.add(criteria);
		return criteria;
	}

	@Override
	public void delete(Object criteriaId) {
		Criteria current=get(criteriaId);
		List<Criteria> children=getChildren(current.getExpressionId(),(Long)criteriaId);
		for(com.greathack.homlin.pojo.expression.Criteria Criteria:children){
			criteriaDAO.delete(Criteria.getCriteriaId());
		}
		criteriaDAO.delete(criteriaId);
		
	}

	@Override
	public void modify(Criteria criteria) throws ServiceException {
		if(criteria==null){
			throw new ServiceException(60002, "CRITERIA_DOES_NOT_EXIST");
		}
		if(criteria.getExpressionId()==null){
			throw new ServiceException(60001,"INVALID_PARAMS");
		}
		if(criteria.getCriteriaType()==null){
			throw new ServiceException(60001,"INVALID_PARAMS");
		}
		if(criteria.getParentId()==null){
			criteria.setParentId(0L);
		}
		Criteria temp=criteriaDAO.findById(criteria.getCriteriaId());
		if(temp==null){
			throw new ServiceException(60002, "CRITERIA_DOES_NOT_EXIST");
		}
		if(criteria.getParentId()==0){
			criteria.setParents("0");
		}else{
			Criteria parent=criteriaDAO.findById(criteria.getParentId());
			if(parent==null){
				throw new ServiceException(60001,"INVALID_PARAMS");
			}
			criteria.setParents(parent.getParents()+","+parent.getCriteriaId());
		}	
		
		criteria.setSubCriteriaList(getDescendants(criteria.getCriteriaId()));
		List<Criteria> cates=new ArrayList<Criteria>();
		//父条件块ID变了
		if(!criteria.getParentId().equals(temp.getParentId())){
			for(Criteria item:criteria.getSubCriteriaList()){
				item.setParents(criteria.getParents()+","+criteria.getCriteriaId());
				cates.add(item);
			}
		}
		
		criteria.setSubCriteriaList(cates);	
		
		//appCode不变
		criteria.setAppCode(temp.getAppCode());

		criteriaDAO.update(criteria);
		
	}

	@Override
	public Criteria get(Object criteriaId) {
		return criteriaDAO.findById(criteriaId);
	}

	@Override
	public List<Criteria> getAllList(String appCode) {
		Criteria condition = new Criteria();
		condition.setAppCode(TypeOption.nullToEmpty(appCode));
		return criteriaDAO.findByExample(condition);
	}

	

	/**
	 * 修改排序
	 */
	public void sort(List<Long> criteriaIdList) {
		if(criteriaIdList==null){
			return;
		}
		Long sort = (long) criteriaIdList.size();
		for (int i = 0; i <= criteriaIdList.size() - 1; i++) {
			sort = sort - i;
			Criteria cate = (Criteria) criteriaDAO.findById(criteriaIdList.get(i));
			if (cate == null) {
				continue;
			}
			cate.setSortNum(sort);
			criteriaDAO.update(cate);
		}

	}

	@Override
	public List<Criteria> getParents(long criteriaId) {
		List<Criteria> criteriaList=new ArrayList<Criteria>();
		Criteria criteria = get(criteriaId);
		if (criteria == null) {
			return criteriaList;
		}
		CriteriaSearchCriteria csc=new CriteriaSearchCriteria();
		csc.setAppCode(criteria.getAppCode());
		csc.setParents(criteria.getParents());
		csc.setPageSize(1000000);
		return search(csc);
	}

	@Override
	public List<Criteria> getDescendants(long criteriaId) {
		return criteriaDAO.getDescendants(criteriaId);
	}

	@Override
	public Criteria getParent(long criteriaId) {
		Criteria criteria = get(criteriaId);
		if (criteria == null) {
			return null;
		}
		return get(criteria.getParentId());
	}

	@Override
	public List<Criteria> getChildren(long expressionId, long criteriaId) {
		Criteria condition = new Criteria();
		condition.setParentId(criteriaId);
		condition.setExpressionId(expressionId);
		return criteriaDAO.findByExample(condition);
	}
	

	@Override
    public boolean isLeaf(long criteriaId) {
		Criteria current=get(criteriaId);
	    List<Criteria> children=getChildren(current.getExpressionId(),criteriaId);
        if(children.size()>0){
            return false;
        }
        return true;
    }

    @Override
	public List<Criteria> search(CriteriaSearchCriteria criteria) {
		List<Criteria> criteriaList=new ArrayList<Criteria>();
		if(criteria==null){
			return criteriaList;
		}
		return criteriaDAO.search(criteria);
	}
	
	@Override
	public long getSearchResultCount(CriteriaSearchCriteria criteria) {
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
				count=Long.toString(criteriaDAO.getSearchResultCount(criteria));
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
