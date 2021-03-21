package com.greathack.homlin.pojo.category;

import com.greathack.homlin.pojo.system.SearchCriteria;
import com.greathack.utils.tools.HashUtils;

import java.io.Serializable;

public class CategorySearchCriteria extends SearchCriteria implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3233597692519183704L;

	/**
	 * 类别名称字段
	 */
	private static final int KW_FIELDS_CATEGORY_NAME = 1;

	/**
	 * 备用1字段
	 */
	private static final int KW_FIELDS_BAK1 = 2;

	/**
	 * 备用2字段
	 */
	private static final int KW_FIELDS_BAK2 = 4;

	/**
	 * 父类ID
	 */
	private Long parentId;
	
	private String parents;

	/**
	 * 类别编码
	 */
	private String categoryCode;

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

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	@Override
	protected String getKwFieldName(int field) {
		switch (field) {
		case CategorySearchCriteria.KW_FIELDS_CATEGORY_NAME:
			return "categoryName";

		case CategorySearchCriteria.KW_FIELDS_BAK1:
			return "bak1";

		case CategorySearchCriteria.KW_FIELDS_BAK2:
			return "bak2";

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
		result = prime * result + ((categoryCode == null) ? 0 : HashUtils.fnv1_32_hash(categoryCode));
		return result;
	}	
	
}
