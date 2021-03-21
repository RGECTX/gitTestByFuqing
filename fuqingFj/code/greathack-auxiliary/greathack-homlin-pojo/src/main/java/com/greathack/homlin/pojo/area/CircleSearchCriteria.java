/**
 * 
 */
package com.greathack.homlin.pojo.area;

import com.greathack.homlin.pojo.system.SearchCriteria;
import com.greathack.utils.tools.HashUtils;
import com.greathack.utils.tools.Utils;

import java.util.List;

/**
 * @author greathack
 *
 */
public class CircleSearchCriteria extends SearchCriteria {

	/**
	 * 商圈名称字段
	 */
	public static final int KW_FIELDS_CIRCLE_NAME=1;	

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
	
	/**
	 * 区域ID
	 */
	private Integer areaId;
	
	private Integer circleState;
	
	private String outKey1;
	
	private String outKey2;
	
	private List<Integer> circleStateList;
	
	

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getCircleState() {
		return circleState;
	}

	public void setCircleState(Integer circleState) {
		this.circleState = circleState;
	}

	public String getOutKey1() {
		return outKey1;
	}

	public void setOutKey1(String outKey1) {
		this.outKey1 = outKey1;
	}

	public String getOutKey2() {
		return outKey2;
	}

	public void setOutKey2(String outKey2) {
		this.outKey2 = outKey2;
	}

	public List<Integer> getCircleStateList() {
		if(this.circleState!=null){
			circleStateList=Utils.recountToArrayList(this.circleState);
		}
		return circleStateList;
	}

	public void setCircleStateList(List<Integer> circleStateList) {
		this.circleStateList = circleStateList;
	}

	/**
	 * 通过字段常量，获取字段名称
	 * @param field
	 * @return 字段名称
	 */
	protected String getKwFieldName(int field){
		switch(field){
    		case CircleSearchCriteria.KW_FIELDS_CIRCLE_NAME:
    			return "circleName";
    			
    		case CircleSearchCriteria.KW_FIELDS_REMARK:
    			return "remark";
    			
    		case CircleSearchCriteria.KW_FIELDS_BAK1:
    			return "bak1";
    			
    		case CircleSearchCriteria.KW_FIELDS_BAK2:
    			return "bak2";
    			
			default:
				return "";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((areaId == null) ? 0 : HashUtils.fnv1_32_hash(areaId.toString()));
		result = prime * result + ((circleState == null) ? 0 : HashUtils.fnv1_32_hash(circleState.toString()));
		result = prime * result + ((outKey1 == null) ? 0 : HashUtils.fnv1_32_hash(outKey1));
		result = prime * result + ((outKey2 == null) ? 0 : HashUtils.fnv1_32_hash(outKey2));
		return result;
	}
}
