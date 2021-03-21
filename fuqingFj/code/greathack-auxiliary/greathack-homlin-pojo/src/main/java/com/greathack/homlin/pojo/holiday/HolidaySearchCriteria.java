/**
 * 
 */
package com.greathack.homlin.pojo.holiday;

import com.greathack.homlin.pojo.system.SearchCriteria;
import com.greathack.utils.tools.HashUtils;
import com.greathack.utils.tools.Utils;

import java.util.List;

/**
 * @author greathack
 *
 */
public class HolidaySearchCriteria extends SearchCriteria {

	/**
	 * 假日名称字段
	 */
	public static final int KW_FIELDS_HOLIDAY_NAME=1;	

	/**
	 * 备注字段
	 */
	public static final int KW_FIELDS_REMARK=2;
	
	/**
	 * 假日日期
	 */
	private Integer holidayDate;
	
	/**
	 * 开始日期
	 */
	private Integer beginDate;
	
	/**
	 * 结束日期
	 */
	private Integer endDate;
	
	/**
	 * 
	 */
	private Integer holidayType;
	
	private Integer isRest;
	
	private List<Integer> holidayTypeList;
	
	private List<Integer> isRestList;
	

	public Integer getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(Integer holidayDate) {
		this.holidayDate = holidayDate;
	}

	public Integer getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Integer beginDate) {
		this.beginDate = beginDate;
	}

	public Integer getEndDate() {
		return endDate;
	}

	public void setEndDate(Integer endDate) {
		this.endDate = endDate;
	}

	public Integer getHolidayType() {
		return holidayType;
	}

	public void setHolidayType(Integer holidayType) {
		this.holidayType = holidayType;
	}

	public Integer getIsRest() {
		return isRest;
	}

	public void setIsRest(Integer isRest) {
		this.isRest = isRest;
	}

	public List<Integer> getHolidayTypeList() {
		if(this.holidayType!=null){
			holidayTypeList=Utils.recountToArrayList(this.holidayType);
		}
		return holidayTypeList;
	}

	public List<Integer> getIsRestList() {
		if(this.isRest!=null){
			isRestList=Utils.recountToArrayList(this.isRest);
		}
		return isRestList;
	}

	/**
	 * 通过字段常量，获取字段名称
	 * @param field
	 * @return 字段名称
	 */
	protected String getKwFieldName(int field){
		switch(field){
    		case HolidaySearchCriteria.KW_FIELDS_HOLIDAY_NAME:
    			return "holidayName";
    			
    		case HolidaySearchCriteria.KW_FIELDS_REMARK:
    			return "remark";
    			
			default:
				return "";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((holidayDate == null) ? 0 : HashUtils.fnv1_32_hash(holidayDate.toString()));
		result = prime * result + ((beginDate == null) ? 0 : HashUtils.fnv1_32_hash(beginDate.toString()));
		result = prime * result + ((endDate == null) ? 0 : HashUtils.fnv1_32_hash(endDate.toString()));
		result = prime * result + ((holidayType == null) ? 0 : HashUtils.fnv1_32_hash(holidayType.toString()));
		result = prime * result + ((isRest == null) ? 0 : HashUtils.fnv1_32_hash(isRest.toString()));
		return result;
	}
}
