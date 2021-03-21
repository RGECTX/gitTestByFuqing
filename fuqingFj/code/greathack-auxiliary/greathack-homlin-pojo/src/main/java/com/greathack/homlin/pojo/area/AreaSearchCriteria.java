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
public class AreaSearchCriteria extends SearchCriteria {

	/**
	 * 城市编码字段
	 */
	public static final int KW_FIELDS_CITY_CODE=1;	

	/**
	 * 所在省名称字段
	 */
	public static final int KW_FIELDS_PROVINCE_NAME=2;
	
	/**
	 * 所在地级市名称字段
	 */
	public static final int KW_FIELDS_CITY_NAME=4;
	
	/**
	 * 所在区/县名称字段
	 */
	public static final int KW_FIELDS_DISTRICT_NAME=8;
	
	/**
	 * 区域名称字段
	 */
	public static final int KW_FIELDS_AREA_NAME=16;
	
	/**
	 * 全拼字段
	 */
	public static final int KW_FIELDS_FULL_PING_YIN=32;
	
	/**
	 * 父级区域ID
	 */
	private Integer parentId;
	
	private Integer level;
	
	private String adcode;
	
	private String citycode;
	
	private String initial;
	
	private List<Integer> levelList;
	
	

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getAdcode() {
		return adcode;
	}

	public void setAdcode(String adcode) {
		this.adcode = adcode;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

	public List<Integer> getLevelList() {
		if(this.level!=null){
			levelList=Utils.recountToArrayList(this.level);
		}
		return levelList;
	}

	public void setLevelList(List<Integer> levelList) {
		this.levelList = levelList;
	}

	/**
	 * 通过字段常量，获取字段名称
	 * @param field
	 * @return 字段名称
	 */
	protected String getKwFieldName(int field){
		switch(field){
    		case AreaSearchCriteria.KW_FIELDS_CITY_CODE:
    			return "citycode";
    			
    		case AreaSearchCriteria.KW_FIELDS_PROVINCE_NAME:
    			return "provinceName";
    			
    		case AreaSearchCriteria.KW_FIELDS_CITY_NAME:
    			return "cityName";
    			
    		case AreaSearchCriteria.KW_FIELDS_DISTRICT_NAME:
    			return "districtName";
    			
    		case AreaSearchCriteria.KW_FIELDS_AREA_NAME:
    			return "areaName";
    			
    		case AreaSearchCriteria.KW_FIELDS_FULL_PING_YIN:
    			return "fullPingyin";
    			
			default:
				return "";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((adcode == null) ? 0 : HashUtils.fnv1_32_hash(adcode));
		result = prime * result + ((citycode == null) ? 0 : HashUtils.fnv1_32_hash(citycode));
		result = prime * result + ((initial == null) ? 0 : HashUtils.fnv1_32_hash(initial));
		result = prime * result + ((level == null) ? 0 : HashUtils.fnv1_32_hash(level.toString()));
		result = prime * result + ((parentId == null) ? 0 : HashUtils.fnv1_32_hash(parentId.toString()));
		return result;
	}
}
