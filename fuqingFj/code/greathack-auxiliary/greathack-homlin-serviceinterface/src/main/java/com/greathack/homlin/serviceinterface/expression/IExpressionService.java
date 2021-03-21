/**
 * 
 */
package com.greathack.homlin.serviceinterface.expression;

import com.greathack.homlin.pojo.expression.Criteria;
import com.greathack.homlin.pojo.expression.Expression;
import com.greathack.homlin.pojo.expression.ExpressionSearchCriteria;

import java.util.List;


/**
 * @author greathack
 *
 */
public interface IExpressionService extends IBaseService<Expression> {

	void deleteAllCriteria(long expressionId);
	/**
	 *	修改表达式排序
	 * @param expressionIdList 表达式ID列表
	 */
	void sort(List<Long> expressionIdList);

	List<Expression> search(ExpressionSearchCriteria criteria);
	
	long getSearchResultCount(ExpressionSearchCriteria criteria);
	
	String createExpression(long expressionId);

	String createSampleExpression(long expressionId);

	Criteria getCriteria(long expressionId);

}
