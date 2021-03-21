package com.greathack.homlin.pojo.permission;

import com.greathack.homlin.pojo.system.SearchCriteria;
import com.greathack.utils.tools.HashUtils;

public class PowerItemSearchCriteria extends SearchCriteria {	

	

    /**
     * 权限名称字段
     */
    public static final int KW_FIELDS_ITEM_NAME=1;
    
	/**
	 * 备注字段
	 */
	public static final int KW_FIELDS_REMARK=2;	
	
	/**
	 * 备用1字段
	 */
	public static final int KW_FIELDS_BAK1=4;
	
	/**
	 * 备用2字段
	 */
	public static final int KW_FIELDS_BAK2=8;
	
	
	private String itemType;
	private String powerCode;
    private String referer;
    private String resource;

	private String itemName;



	public String getItemType() {
        return itemType;
    }



    public void setItemType(String itemType) {
        this.itemType = itemType;
    }



    public String getPowerCode() {
        return powerCode;
    }



    public void setPowerCode(String powerCode) {
        this.powerCode = powerCode;
    }



    public String getReferer() {
        return referer;
    }



    public void setReferer(String referer) {
        this.referer = referer;
    }



    public String getResource() {
        return resource;
    }



    public void setResource(String resource) {
        this.resource = resource;
    }

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * 通过字段常量，获取字段名称
	 * @param field
	 * @return 字段名称
	 */
	protected String getKwFieldName(int field){
		switch(field){

            case PowerItemSearchCriteria.KW_FIELDS_ITEM_NAME:
                return "itemName";

    		case PowerItemSearchCriteria.KW_FIELDS_REMARK:
    			return "remark";
    			
    		case PowerItemSearchCriteria.KW_FIELDS_BAK1:
    			return "bak1";
    			
    		case PowerItemSearchCriteria.KW_FIELDS_BAK2:
    			return "bak2";
    			
			default:
				return "";
		}
	}
	

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((itemType == null) ? 0 : HashUtils.fnv1_32_hash(itemType));
		result = prime * result + ((powerCode == null) ? 0 : HashUtils.fnv1_32_hash(powerCode));
		result = prime * result + ((referer == null) ? 0 : HashUtils.fnv1_32_hash(referer));
        result = prime * result + ((resource == null) ? 0 : HashUtils.fnv1_32_hash(resource));
		return result;
	}

}
