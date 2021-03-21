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
public class AddressItemSearchCriteria extends SearchCriteria {
	
	
	/**
	 * 省字段
	 */
	public static final int KW_FIELDS_PROVINCE=1;
	
	/**
	 * 市字段
	 */
	public static final int KW_FIELDS_CITY=2;
	
	/**
	 * 县字段
	 */
	public static final int KW_FIELDS_DISTRICT=4;
	
	/**
	 * 联系地址字段
	 */
	public static final int KW_FIELDS_LINK_ADDRESS=8;
	
	/**
	 * 备注字段
	 */
	public static final int KW_FIELDS_REMARK=16;
	
	/**
	 * 备用1字段
	 */
	public static final int KW_FIELDS_BAK1=32;
	
	/**
	 * 备用2字段
	 */
	public static final int KW_FIELDS_BAK2=64;
	
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
    		case AddressItemSearchCriteria.KW_FIELDS_PROVINCE:
    			return "province";
    			
    		case AddressItemSearchCriteria.KW_FIELDS_CITY:
    			return "city";
    			
    		case AddressItemSearchCriteria.KW_FIELDS_DISTRICT:
    			return "district";
    			
    		case AddressItemSearchCriteria.KW_FIELDS_LINK_ADDRESS:
    			return "linkAddress";
    			
    		case AddressItemSearchCriteria.KW_FIELDS_REMARK:
    			return "remark";
    			
    		case AddressItemSearchCriteria.KW_FIELDS_BAK1:
    			return "bak1";
    			
    		case AddressItemSearchCriteria.KW_FIELDS_BAK2:
    			return "bak2";
    			
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
