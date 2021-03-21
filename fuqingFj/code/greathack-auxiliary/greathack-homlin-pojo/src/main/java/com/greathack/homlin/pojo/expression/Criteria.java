package com.greathack.homlin.pojo.expression;

import java.io.Serializable;
import java.util.List;

public class Criteria implements Serializable {
	public static final int OPERATOR_EQUAL=1;
	public static final int OPERATOR_UNEQUAL=2;
	public static final int OPERATOR_LESS_THAN=3;
	public static final int OPERATOR_LESS_THAN_OR_EQUAL=4;
	public static final int OPERATOR_GREATER_THAN=5;
	public static final int OPERATOR_GREATER_THAN_OR_EQUAL=6;
	public static final int OPERATOR_IS_NULL=7;
	public static final int OPERATOR_IS_NOT_NULL=8;
	public static final int OPERATOR_IS_BLANK=9;
	public static final int OPERATOR_IS_NOT_BLANK=10;
	public static final int OPERATOR_LIKE=11;
	public static final int OPERATOR_NOT_LIKE=12;
	public static final int OPERATOR_BETWEEN=13;
	public static final int OPERATOR_NOT_BETWEEN=14;
	public static final int OPERATOR_IN=15;
	public static final int OPERATOR_NOT_IN=16;
	
	public static final int STATE_ENABLE=1;
	
	public static final int STATE_DISABLE=2;
	
	public static final int TYPE_CONDITION=1;//条件
	
	public static final int TYPE_AND_BLOCK=2;//且条件块
	
	public static final int TYPE_OR_BLOCK=4;//或条件块
	
	public static final int TYPE_NOT_BLOCK=8;//非条件块

	/**
	 * 条件ID 唯一识别码
	 */
	private Long criteriaId;

	/**
	 * 应用编码
	 */
	private String appCode;
	/**
	 * 所属表达式ID
	 */
	private Long expressionId;

	/**
	 * 条件类型
	 */
	private Integer criteriaType;
	
	/**
	 * 父条件ID 唯一识别码
	 */
	private Long parentId;

	/**
	 * 直系上级ID串 格式，0，1,...
	 */
	private String parents;

	/**
	 * 字段中文名称
	 */
	private String fieldName;

	/**
	 * 字段
	 */
	private String field;

    /**
     * 比较运算符
     */
    private Integer comparisonOperator;

	/**
	 * 条件1
	 */
	private String criteria1;

	/**
	 * 条件2
	 */
	private String criteria2;

	/**
	 * 排序数字
	 */
	private Long sortNum;

    /**
     * 状态
     */
    private Integer state;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 外键1
	 */
	private String outKey1;

	/**
	 * 外键2
	 */
	private String outKey2;

	/**
	 * 备用1
	 */
	private String bak1;

	/**
	 * 备用2
	 */
	private String bak2;

	/**
	 * 子条件列表
	 */
	private List<Criteria> subCriteriaList;

	public Long getCriteriaId() {
		return criteriaId;
	}

	public void setCriteriaId(Long criteriaId) {
		this.criteriaId = criteriaId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public Long getExpressionId() {
		return expressionId;
	}

	public void setExpressionId(Long expressionId) {
		this.expressionId = expressionId;
	}

	public Integer getCriteriaType() {
		return criteriaType;
	}

	public void setCriteriaType(Integer criteriaType) {
		this.criteriaType = criteriaType;
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

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Integer getComparisonOperator() {
		return comparisonOperator;
	}

	public void setComparisonOperator(Integer comparisonOperator) {
		this.comparisonOperator = comparisonOperator;
	}

	public String getCriteria1() {
		return criteria1;
	}

	public void setCriteria1(String criteria1) {
		this.criteria1 = criteria1;
	}

	public String getCriteria2() {
		return criteria2;
	}

	public void setCriteria2(String criteria2) {
		this.criteria2 = criteria2;
	}

	public Long getSortNum() {
		return sortNum;
	}

	public void setSortNum(Long sortNum) {
		this.sortNum = sortNum;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOutKey1() {
		return outKey1;
	}

	public void setOutKey1(String outKey1) {
		this.outKey1 = outKey1;
	}

	public String getOutKey2() {
		return outKey2;
	}

	public void setOutKey2(String outKey2) {
		this.outKey2 = outKey2;
	}

	public String getBak1() {
		return bak1;
	}

	public void setBak1(String bak1) {
		this.bak1 = bak1;
	}

	public String getBak2() {
		return bak2;
	}

	public void setBak2(String bak2) {
		this.bak2 = bak2;
	}

	public List<Criteria> getSubCriteriaList() {
		return subCriteriaList;
	}

	public void setSubCriteriaList(List<Criteria> subCriteriaList) {
		this.subCriteriaList = subCriteriaList;
	}	

}
