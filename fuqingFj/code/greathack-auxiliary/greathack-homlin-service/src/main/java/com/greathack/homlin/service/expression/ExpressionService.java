/**
 * 
 */
package com.greathack.homlin.service.expression;

import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.expression.ICriteriaDAO;
import com.greathack.homlin.daointerface.expression.IExpressionDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.expression.Criteria;
import com.greathack.homlin.pojo.expression.Expression;
import com.greathack.homlin.pojo.expression.ExpressionSearchCriteria;
import com.greathack.homlin.serviceinterface.expression.IExpressionService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.TypeOption;
import com.greathack.utils.tools.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * @author greathack
 *
 */
public class ExpressionService implements IExpressionService {

	private static Logger logger = LoggerFactory.getLogger(ExpressionService.class);
	

	private ICriteriaDAO criteriaDAO = (ICriteriaDAO) DAOFactory.createDAO("ICriteriaDAO");
	private IExpressionDAO expressionDAO = (IExpressionDAO) DAOFactory.createDAO("IExpressionDAO");
	private CriteriaService criteriaService= new CriteriaService();
	

	/* （非 Javadoc）
	 * @see com.greathack.expression.serviceinterface.IBaseService#add(java.lang.Object)
	 */
	@Override
	public Expression add(Expression expression) throws ServiceException {
		if(expression==null){
			return null;
		}
		if(Validation.isEmpty(expression.getAppCode())){
			throw new ServiceException(60001,"INVALID_PARAMS");
		}
		if(Validation.isEmpty(expression.getExpressionName())){
			throw new ServiceException(60001,"INVALID_PARAMS");
		}
		
		if (expression.getSortNum() == null) {
			expression.setSortNum(0L);
		}
		if(expression.getState()==null){
			expression.setState(1);
		}
		expression.setExpressionId(IdCreator.createId("Expression"));
		expressionDAO.add(expression);
		return expression;
	}

	/* （非 Javadoc）
	 * @see com.greathack.expression.serviceinterface.IBaseService#delete(java.lang.Object)
	 */
	@Override
	public void delete(Object instanceId) {
		Criteria instance=new Criteria();
		instance.setExpressionId((Long)instanceId);
		List<Criteria> criteriaList=criteriaDAO.findByExample(instance);
		for(Criteria criteria:criteriaList){
			criteriaDAO.delete(criteria.getCriteriaId());
		}
		expressionDAO.delete(instanceId);

	}

	/* （非 Javadoc）
	 * @see com.greathack.expression.serviceinterface.IBaseService#modify(java.lang.Object)
	 */
	@Override
	public void modify(Expression expression) throws ServiceException {
		if(Validation.isEmpty(expression.getExpressionName())){
			throw new ServiceException(60001,"INVALID_PARAMS");
		}
		if(expression.getState()==null){
			throw new ServiceException(60001,"INVALID_PARAMS");
		}
		
		if (expression.getSortNum() == null) {
			expression.setSortNum(0L);
		}
		expressionDAO.update(expression);

	}

	/* （非 Javadoc）
	 * @see com.greathack.expression.serviceinterface.IBaseService#get(java.lang.Object)
	 */
	@Override
	public Expression get(Object instanceId) {
		return expressionDAO.findById(instanceId);
	}

	/* （非 Javadoc）
	 * @see com.greathack.expression.serviceinterface.IBaseService#getAllList(java.lang.String)
	 */
	@Override
	public List<Expression> getAllList(String appCode) {
		Expression condition = new Expression();
		condition.setAppCode(TypeOption.nullToEmpty(appCode));
		return expressionDAO.findByExample(condition);
	}

	@Override
	public void deleteAllCriteria(long expressionId) {
		expressionDAO.deleteAllCriteria(expressionId);
	}

	@Override
	public void sort(List<Long> expressionIdList) {
		if(expressionIdList==null || expressionIdList.size()==0){
			return;
		}

		Long sort = (long) expressionIdList.size();
		for (int i = 0; i <= expressionIdList.size() - 1; i++) {
			sort = sort - i;
			Expression expression = (Expression) expressionDAO.findById(expressionIdList.get(i));
			if (expression == null) {
				continue;
			}
			expression.setSortNum(sort);
			expressionDAO.update(expression);
		}
		
	}

	/* （非 Javadoc）
	 * @see com.greathack.expression.serviceinterface.IExpressionService#search(com.greathack.expression.pojo.ExpressionSearchCriteria)
	 */
	@Override
	public List<Expression> search(ExpressionSearchCriteria expressionSearchCriteria) {
		List<Expression> expressionList=new ArrayList<Expression>();
		if(expressionSearchCriteria==null){
			return expressionList;
		}
		return expressionDAO.search(expressionSearchCriteria);
	}

	/* （非 Javadoc）
	 * @see com.greathack.expression.serviceinterface.IExpressionService#getSearchResultCount(com.greathack.expression.pojo.ExpressionSearchCriteria)
	 */
	@Override
	public long getSearchResultCount(ExpressionSearchCriteria expressionSearchCriteria) {
		if(expressionSearchCriteria==null){
			return 0;
		}
		String key=Integer.toString(expressionSearchCriteria.hashCode());
		String count="0";
		Jedis jedis=null;
		try {
			jedis= SystemConfig.getJedisPool().getResource();
			if(jedis.exists(key)){
				count=jedis.get(key);
				//重新设置超时
				jedis.expire(key, Integer.parseInt(SystemConfig.getConfigData("RESULT_COUNT_EXPIRE")));
			}else{
				count=Long.toString(expressionDAO.getSearchResultCount(expressionSearchCriteria));
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

	@Override
	public String createExpression(long expressionId) {
		Criteria instance=new Criteria();
		instance.setExpressionId(expressionId);
		List<Criteria> criteriaList=criteriaDAO.findByExample(instance);
		Map<Long, Criteria> criteriaMap=new HashMap<Long, Criteria>();
		long parentId=0L;
		for(Criteria criteria:criteriaList){
			criteriaMap.put(criteria.getCriteriaId(), criteria);
			if(criteria.getParentId()==0){
				parentId=criteria.getCriteriaId();
			}
		}
		return getExpression(parentId,criteriaMap);
	}

	@Override
	public String createSampleExpression(long expressionId) {
		String expression="(";
		Criteria instance=new Criteria();
		instance.setExpressionId(expressionId);
		List<Criteria> criteriaList=criteriaDAO.findByExample(instance);
		for(Criteria criteria:criteriaList){
			expression+=getLogicalOperator(criteria.getCriteriaType())+getCriteriaStr(criteria);
		}
		expression+=")";
		if(Objects.equals(expression,"()")){
			expression="(1=1)";
		}
		return  expression;
	}

	@Override
	public Criteria getCriteria(long expressionId){
		Criteria instance=new Criteria();
		instance.setExpressionId(expressionId);
		List<Criteria> criteriaList=criteriaDAO.findByExample(instance);
		Criteria parent=null;
		for(Criteria criteria:criteriaList){
			if("0".equals(criteria.getParentId())){
				parent=criteria;
			}
		}
		return getCriteria(parent);
	}

	private String getExpression(long parentId,Map<Long, Criteria> criteriaMap){
		int criteriaType=0;
		String expression="(";
		Criteria parent=criteriaMap.get(parentId);
		if(parent!=null){
			criteriaType=parent.getCriteriaType();
		}
		if(criteriaType== Criteria.TYPE_NOT_BLOCK){
			expression=" NOT "+expression;
		}
		List<Criteria> currentList=criteriaService.getChildren(parent.getExpressionId(),parentId);
		for(Criteria criteria:currentList){

			if(criteria.getCriteriaType()== Criteria.TYPE_CONDITION){//条件
				if(criteria.getCriteria1().equals("")){
					expression+=getLogicalOperator(criteriaType)+"(1=1)";
				}else{
					expression+=getLogicalOperator(criteriaType)+getCriteriaStr(criteria);
				}
			}else{//条件块
				expression+=getLogicalOperator(criteriaType)+getExpression(criteria.getCriteriaId(),criteriaMap);
			}
		}
		//expression=expression.substring(getLogicalOperator(criteriaType).length());
		expression=expression.replace("( AND ","(");
		expression=expression.replace("( OR ","(");
		expression+=")";
		return  expression;

	}

	private Criteria getCriteria(Criteria parent){
		List<Criteria> currentList=criteriaService.getChildren(parent.getExpressionId(),parent.getCriteriaId());
		for(Criteria criteria:currentList){
			criteria=getCriteria(criteria);
		}
		return parent;
	}

	private String getLogicalOperator(int criteriaType){
		switch(criteriaType){
			case 1://条件
				return "";
			case 2://且条件块
				return " AND ";
			case 4://或条件块
				return " OR ";
			case 8://非条件块
				return "";
		}
		return "";
	}

	private String getCriteriaStr(Criteria criteria){
		switch(criteria.getComparisonOperator()){
			case 1://等于
				return criteria.getField()+" = '"+criteria.getCriteria1() + "'";
			case 2://不等于
				return criteria.getField()+" <> '"+criteria.getCriteria1() + "'";
			case 3://小于
				return criteria.getField()+" < "+criteria.getCriteria1();
			case 4://小于等于
				return criteria.getField()+" <= "+criteria.getCriteria1();
			case 5://大于
				return criteria.getField()+" > "+criteria.getCriteria1();
			case 6://大于等于
				return criteria.getField()+" >= "+criteria.getCriteria1();
			case 7://是null
				return criteria.getField()+" IS NULL";
			case 8://不是null
				return criteria.getField()+" IS NOT NULL";
			case 9://是空值
				return criteria.getField()+" = ''";
			case 10://不是空值
				return criteria.getField()+" <> ''";
			case 11://包含
				return criteria.getField()+" LIKE '%"+criteria.getCriteria1()+"%'";
			case 12://不包含
				return criteria.getField()+" NOT LIKE '%"+criteria.getCriteria1()+"%'";
			case 13://介于
				return criteria.getField()+"  BETWEEN "+criteria.getCriteria1()+" AND "+criteria.getCriteria2();
			case 14://不介于
				return criteria.getField()+"  NOT BETWEEN "+criteria.getCriteria1()+" AND "+criteria.getCriteria2();
			case 15://在列表中
				return criteria.getField()+" IN ("+criteria.getCriteria1()+")";
			case 16://不在列表中
				return criteria.getField()+" NOT IN ("+criteria.getCriteria1()+")";
		}
		return "";

	}

}
