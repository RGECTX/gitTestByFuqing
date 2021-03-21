/**
 * 
 */
package com.greathack.homlin.pojo.user;

import com.greathack.homlin.pojo.system.SearchCriteria;
import com.greathack.utils.tools.HashUtils;
import com.greathack.utils.tools.Utils;

import java.util.List;

/**
 * @author greathack
 *
 */
public class UserSearchCriteria extends SearchCriteria {

    /**
     * 手机字段
     */
    public static final int KW_FIELDS_MOBILE=1;      

    /**
     * 电话字段
     */
    public static final int KW_FIELDS_TEL=2;      

    /**
     * 邮箱字段
     */
    public static final int KW_FIELDS_EMAIL=4;  

	/**
	 * 昵称字段
	 */
	public static final int KW_FIELDS_NICK_NAME=8;	

	/**
	 * 姓名字段
	 */
	public static final int KW_FIELDS_USER_NAME=16;
	
	/**
	 * 单位名称字段
	 */
	public static final int KW_FIELDS_COMPANY_NAME=32;
	
	/**
	 * 备用1字段
	 */
	public static final int KW_FIELDS_BAK1=64;
	
	/**
	 * 备用2字段
	 */
	public static final int KW_FIELDS_BAK2=128;
	
	/**
	 * 用户状态，不带代表全部,1:正常,2：锁定,2^n,n>=0
	 */
	private Integer userState;
	
	private List<Integer> userStateList;

	private String orgId;

	private String orgCode;
	

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	
	public List<Integer> getUserStateList() {
		if(this.userState!=null){
			userStateList=Utils.recountToArrayList(this.userState);
		}
		return userStateList;
	}

	public void setUserStateList(List<Integer> userStateList) {
		this.userStateList = userStateList;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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
	protected String getKwFieldName(int field){
		switch(field){
        
            case UserSearchCriteria.KW_FIELDS_MOBILE:
                return "mobile";
                
            case UserSearchCriteria.KW_FIELDS_TEL:
                return "tel";
                
            case UserSearchCriteria.KW_FIELDS_EMAIL:
                return "email";
                
    		case UserSearchCriteria.KW_FIELDS_NICK_NAME:
    			return "nickName";
    			
    		case UserSearchCriteria.KW_FIELDS_USER_NAME:
    			return "userName";
    			
    		case UserSearchCriteria.KW_FIELDS_COMPANY_NAME:
    			return "companyName";
    			
    		case UserSearchCriteria.KW_FIELDS_BAK1:
    			return "bak1";
    			
    		case UserSearchCriteria.KW_FIELDS_BAK2:
    			return "bak2";
    			
			default:
				return "";
		}
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((userState == null) ? 0 : HashUtils.fnv1_32_hash(Integer.toString(userState)));
		result = prime * result + ((orgId == null) ? 0 : HashUtils.fnv1_32_hash(orgId));
		result = prime * result + ((orgCode == null) ? 0 : HashUtils.fnv1_32_hash(orgCode));
		return result;
	}	

}
