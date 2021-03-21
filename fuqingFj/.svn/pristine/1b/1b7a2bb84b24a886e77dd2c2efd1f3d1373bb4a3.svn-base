package com.greathack.homlin.pojo.system;

import com.greathack.utils.tools.HashUtils;

import java.io.Serializable;

public class MenuSearchCriteria extends SearchCriteria implements Serializable {

    /**
     * 组织机构编码字段
     */
	public static final int KW_FIELDS_MENU_CODE = 1;
	/**
	 * 组织机构名称字段
	 */
	public static final int KW_FIELDS_MENU_NAME = 2;

	/**
	 * 备注字段
	 */
	public static final int KW_FIELDS_REMARK = 4;

	/**
	 * 父类ID
	 */
	private Long parentId;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}


	@Override
	protected String getKwFieldName(int field) {
		switch (field) {
        case MenuSearchCriteria.KW_FIELDS_MENU_CODE:
            return "categoryCode";
            
		case MenuSearchCriteria.KW_FIELDS_MENU_NAME:
			return "categoryName";

		case MenuSearchCriteria.KW_FIELDS_REMARK:
			return "bak1";

		default:
			return "";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((parentId == null) ? 0 : HashUtils.fnv1_32_hash(parentId.toString()));
		return result;
	}	
	
}
