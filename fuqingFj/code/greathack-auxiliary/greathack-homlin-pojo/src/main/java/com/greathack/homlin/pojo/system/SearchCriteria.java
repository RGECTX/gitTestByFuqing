/**
 * 
 */
package com.greathack.homlin.pojo.system;

import com.greathack.utils.tools.HashUtils;
import com.greathack.utils.tools.Utils;
import com.greathack.utils.tools.Validation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author greathack
 * 
 */
public abstract class SearchCriteria {
    
    /**
     * 应用编码
     */
    private String appCode;

    /**
     * 外键1,不带代表全部
     */
    private String outKey1;

    /**
     * 外键2,不带代表全部
     */
    private String outKey2;

    /**
     * 要通过关键词搜索的字段，不带代表全部。
     */
    private Integer kwFields;
	
	/**
	 * 搜索字段名列表
	 */
	private List<String> kwFieldList;

    /**
     * 搜索关键词，不带代表全部。
     */
    private String keyword;

    /**
     * 排序字段,1：createTimeDesc（创建时间倒序）,2：createTimeAsc（创建时间顺序），不带代表createTimeDesc
     */
    private int sortField = 1;    
    
    /**
     * 开始行号，从第几行开始，第一行行号为0
     */
    private int startLine=0;

    /**
     * 每页记录数，用于分页，不带默认为20，范围1-1000
     */
    private int pageSize = 20;

	/**
	 * 排序
	 */
    private String orderBy;

    //过滤条件列表
    private List<String> filterList=new ArrayList<String>();

    public String getAppCode() {
	return appCode;
    }

    public void setAppCode(String appCode) {
	this.appCode = appCode;
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

    public void setKwFields(Integer kwFields) {
	this.kwFields = kwFields;
    }

    public String getKeyword() {
	return keyword;
    }

    public void setKeyword(String keyword) {
	this.keyword = keyword;
    }

    public int getSortField() {
	return sortField;
    }

    public void setSortField(int sortField) {
	this.sortField = sortField;
    }    

    public int getPageSize() {
	return pageSize;
    }

    public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
    }

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public int getStartLine() {
		return startLine;
	}

	public void setStartLine(int startLine) {
		this.startLine = startLine;
	}

	public List<String> getFilterList() {
		return filterList;
	}

	public void addFilter(String filter) {
    	if(!Validation.isEmpty(filter)){
			this.filterList.add(filter);
		}
	}

	public void removeFilter(String filter) {
		if(!Validation.isEmpty(filter)){
			this.filterList.remove(filter);
		}
	}

	public void setFilterList(List<String> filterList) {
		this.filterList = filterList;
	}

	public List<String> getKwFieldList() {
		if(kwFieldList!=null){
			return kwFieldList;
		}
		if(kwFields!=null && getKeyword()!=null){
			kwFieldList=new ArrayList<String>(); 
			List<Integer> kwList=Utils.recountToArrayList(kwFields);
			for(Integer kw:kwList){
				kwFieldList.add(getKwFieldName(kw));
			}
		}
		return kwFieldList;
	}

	public void setKwFieldList(List<String> kwFieldList) {
		this.kwFieldList = kwFieldList;
	}
	
	/**
	 * 根据字段常量获取字段名
	 * @param field 字段常量
	 * @return
	 */
	protected abstract String getKwFieldName(int field);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appCode == null) ? 0 : HashUtils.fnv1_32_hash(appCode));
		result = prime * result + ((keyword == null) ? 0 : HashUtils.fnv1_32_hash(keyword));
		result = prime * result + ((kwFields == null) ? 0 : HashUtils.fnv1_32_hash(kwFields.toString()));
		result = prime * result + ((outKey1 == null) ? 0 : HashUtils.fnv1_32_hash(outKey1));
		result = prime * result + ((outKey2 == null) ? 0 : HashUtils.fnv1_32_hash(outKey2));
		for(String filter:filterList){
			result = prime * result + ((filter == null) ? 0 : HashUtils.fnv1_32_hash(filter));
		}
		return result;
	}

}
