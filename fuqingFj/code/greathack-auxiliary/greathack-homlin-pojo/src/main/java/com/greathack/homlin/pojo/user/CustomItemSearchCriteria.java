package com.greathack.homlin.pojo.user;

import com.greathack.homlin.pojo.system.SearchCriteria;
import com.greathack.utils.tools.HashUtils;

/**
 * @author zjb
 * @version 创建时间：2017年3月8日 上午10:58:47
 * 类说明
 */
public class CustomItemSearchCriteria extends SearchCriteria {

	/**
	 * 内容字段
	 */
	public static final int KW_FIELDS_CONTENT = 1; 
	/**
	 * 备注字段
	 */
	public static final int KW_FIELDS_REMARK = 2;
	/**
	 * 项目名称字段
	 */
	public static final int KW_FIELDS_ITEM_NAME = 4;
	/**
	 * 所属用户ID
	 */
	private Long userId;
	
	
	
	/**
	 * 通过字段常量，获取字段名称
	 * @param field
	 * @return 字段名称
	 */
	protected String getKwFieldName(int field) {
		switch (field) {
		case CustomItemSearchCriteria.KW_FIELDS_CONTENT:
			return "content";
		case CustomItemSearchCriteria.KW_FIELDS_REMARK:
			return "remark";
		case CustomItemSearchCriteria.KW_FIELDS_ITEM_NAME:
			return "itemName";
		default:
			return "";
			
		}
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((userId == null) ? 0 : HashUtils.fnv1_32_hash(userId.toString()));
		return result;
	}

}
