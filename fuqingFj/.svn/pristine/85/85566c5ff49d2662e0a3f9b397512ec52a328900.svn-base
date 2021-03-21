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
public class LinkItemSearchCriteria extends SearchCriteria {

    /**
     * 内容字段
     */
    public static final int KW_FIELDS_CONTENT = 1;

    /**
     * 备注字段
     */
    public static final int KW_FIELDS_REMARK = 2;    

    /**
     * 所属用户ID,不带代表全部
     */
    private Long userId;

    /**
     * 项目类型ID<br>
     * 不带代表全部
     */
    private Long itemTypeId;

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

    public Long getItemTypeId() {
	return itemTypeId;
    }

    public void setItemTypeId(Long itemTypeId) {
	this.itemTypeId = itemTypeId;
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
    		case LinkItemSearchCriteria.KW_FIELDS_CONTENT:
    			return "content";
    			
    		case LinkItemSearchCriteria.KW_FIELDS_REMARK:
    			return "remark";    		
    			
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
		result = prime * result + ((itemTypeId == null) ? 0 : HashUtils.fnv1_32_hash(itemTypeId.toString()));
		return result;
	}

}
