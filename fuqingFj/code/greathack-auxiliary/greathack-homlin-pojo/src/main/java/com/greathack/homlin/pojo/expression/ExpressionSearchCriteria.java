package com.greathack.homlin.pojo.expression;

import com.greathack.homlin.pojo.system.SearchCriteria;
import com.greathack.utils.tools.HashUtils;

/**
 * @author greathack
 *
 */

/**
 * @author greathack
 *
 */
public class ExpressionSearchCriteria extends SearchCriteria {
	/**
	 * 备注字段
	 */
	private static final int KW_FIELDS_REMARK = 1;
	/**
	 * 备用1字段
	 */
	private static final int KW_FIELDS_BAK1 = 2;

	/**
	 * 备用2字段
	 */
	private static final int KW_FIELDS_BAK2 = 4;

	/**
	 * 字段中文名称
	 */
	private static final int KW_FIELDS_EXPRESSION_NAME = 8;
	
	/**
	 * 表达式类型
	 */
	private String expressionType;	
	
	/**
	 * 状态
	 */
	private Integer state;

	/**
	 * 创建人
	 */
	private Long createBy;



	public String getExpressionType() {
		return expressionType;
	}

	public void setExpressionType(String expressionType) {
		this.expressionType = expressionType;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	@Override
	protected String getKwFieldName(int field) {
		switch (field) {
		case ExpressionSearchCriteria.KW_FIELDS_REMARK:
			return "categoryName";

		case ExpressionSearchCriteria.KW_FIELDS_BAK1:
			return "bak1";

		case ExpressionSearchCriteria.KW_FIELDS_BAK2:
			return "bak2";
			
		case ExpressionSearchCriteria.KW_FIELDS_EXPRESSION_NAME:
			return "expressionName";

		default:
			return "";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((expressionType == null) ? 0 : HashUtils.fnv1_32_hash(expressionType));
		result = prime * result + ((state == null) ? 0 : HashUtils.fnv1_32_hash(state.toString()));
		return result;
	}	
	
}
