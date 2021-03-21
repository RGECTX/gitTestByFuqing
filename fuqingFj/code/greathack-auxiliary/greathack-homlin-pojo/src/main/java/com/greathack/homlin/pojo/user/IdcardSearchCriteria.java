/**
 * 
 */
package com.greathack.homlin.pojo.user;

import com.greathack.homlin.pojo.system.SearchCriteria;
import com.greathack.utils.tools.HashUtils;

/**
 * @author greathack
 *
 */
public class IdcardSearchCriteria extends SearchCriteria {
	/**
	 * 证件号码字段
	 */
	public static final int KW_FIELDS_IDCARD_NAME=1;
	
	/**
	 * 证件号码字段
	 */
	public static final int KW_FIELDS_IDCARD_NO=2;
	
	/**
	 * 所属用户ID,不带代表全部
	 */
	private Long userId;
	
	
	/**
	 * 是否默认,不带代表全部,true：默认，false：非默认
	 */
	private Boolean isDefault;	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	/**
	 * 通过字段常量，获取字段名称
	 * @param field
	 * @return 字段名称
	 */
	protected String getKwFieldName(int field){
		switch(field){
    		case IdcardSearchCriteria.KW_FIELDS_IDCARD_NAME:
    			return "idcardName";
    			
    		case IdcardSearchCriteria.KW_FIELDS_IDCARD_NO:
    			return "idcardNo";    		
    			
			default:
				return "";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((isDefault == null) ? 0 : HashUtils.fnv1_32_hash(isDefault.toString()));
		result = prime * result + ((userId == null) ? 0 : HashUtils.fnv1_32_hash(userId.toString()));
		return result;
	}

}
