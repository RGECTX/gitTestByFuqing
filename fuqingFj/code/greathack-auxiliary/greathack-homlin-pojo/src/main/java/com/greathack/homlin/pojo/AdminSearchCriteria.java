/**
 * 
 */
package com.greathack.homlin.pojo;

import com.greathack.homlin.pojo.system.SearchCriteria;

/**
 * @author greathack
 *
 */
public class AdminSearchCriteria extends SearchCriteria {		

	/**
	 * 姓名字段
	 */
	public static final int KW_FIELDS_NAME=1;

	/**
	 * 登录名字段
	 */
	public static final int KW_FIELDS_LOGIN_NAME=2;	
	
	/**
	 * 备用1字段
	 */
	public static final int KW_FIELDS_BAK1=4;
	
	/**
	 * 备用2字段
	 */
	public static final int KW_FIELDS_BAK2=8;
	
	/**
	 * 用户状态，不带代表全部,1:正常,2：禁用,4：删除,可叠加
	 */
	private Integer state=3;
	
	private String amUnitId;

	private String orgCode;


    public Integer getState() {
        return state;
    }


    public void setState(Integer state) {
        this.state = state;
    }


	public String getAmUnitId() {
		return amUnitId;
	}


	public void setAmUnitId(String amUnitId) {
		this.amUnitId = amUnitId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 通过字段常量，获取字段名称
	 * @param field
	 * @return 字段名称
	 */
	@Override 
	protected String getKwFieldName(int field){
		switch(field){
                
    		case AdminSearchCriteria.KW_FIELDS_LOGIN_NAME:
    			return "nickName";
    			
    		case AdminSearchCriteria.KW_FIELDS_NAME:
    			return "userName";
    			
    		case AdminSearchCriteria.KW_FIELDS_BAK1:
    			return "bak1";
    			
    		case AdminSearchCriteria.KW_FIELDS_BAK2:
    			return "bak2";
    			
			default:
				return "";
		}
	}

}
