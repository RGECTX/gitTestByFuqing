/**
 * 
 */
package com.greathack.homlin.daointerface.expression;

import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.expression.Expression;
import com.greathack.homlin.pojo.expression.ExpressionSearchCriteria;

import java.util.List;

/**
 * @author greathack
 * 
 */
public interface IExpressionDAO extends BaseDAO<Expression> {

	void deleteAllCriteria(long expressionId);
	
	List<Expression> search(ExpressionSearchCriteria expressionSearchCriteria);
	
	long getSearchResultCount(ExpressionSearchCriteria expression);
	
}
