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
public class CriteriaSearchCriteria extends SearchCriteria {
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
	private static final int KW_FIELDS_FIELD_NAME = 8;

	/**
	 * 字段
	 */
	private static final int KW_FIELDS_FIELD = 16;


	/**
	 * 表达式ID
	 */
	private Long expressionId;
	/**
	 * 父条件块ID
	 */
	private Long parentId;
	
	/**
	 * 父类ID
	 */
	private String parents;	
	
	/**
	 * 状态
	 */
	private Integer state;

	public Long getExpressionId() {
		return expressionId;
	}

	public void setExpressionId(Long expressionId) {
		this.expressionId = expressionId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParents() {
		return parents;
	}

	public void setParents(String parents) {
		this.parents = parents;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	protected String getKwFieldName(int field) {
		switch (field) {
		case CriteriaSearchCriteria.KW_FIELDS_REMARK:
			return "remark";

		case CriteriaSearchCriteria.KW_FIELDS_BAK1:
			return "bak1";

		case CriteriaSearchCriteria.KW_FIELDS_BAK2:
			return "bak2";
			
		case CriteriaSearchCriteria.KW_FIELDS_FIELD_NAME:
			return "fieldName";
			
		case CriteriaSearchCriteria.KW_FIELDS_FIELD:
			return "field";

		default:
			return "";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((parentId == null) ? 0 : HashUtils.fnv1_32_hash(parentId.toString()));
		result = prime * result + ((parents == null) ? 0 : HashUtils.fnv1_32_hash(parents));
		result = prime * result + ((state == null) ? 0 : HashUtils.fnv1_32_hash(state.toString()));
		return result;
	}	
	
}
