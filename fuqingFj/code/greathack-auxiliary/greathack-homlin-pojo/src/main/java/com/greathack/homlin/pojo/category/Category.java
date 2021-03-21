package com.greathack.homlin.pojo.category;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5168696254613943889L;

	/**
	 * 类别ID 唯一识别码
	 */
	private Long categoryId;

	/**
	 * 应用编码
	 */
	private String appCode;
	/**
	 * 父类ID
	 */
	private Long parentId;

	/**
	 * 父类名称
	 */
	private String parentName;

	/**
	 * 直系上级ID串 格式，0，1,...
	 */
	private String parents;

	/**
	 * 类别名称
	 */
	private String categoryName;

	/**
	 * 类别编码
	 */
	private String categoryCode;

    /**
     * 图标地址
     */
    private String icons;

	/**
	 * 排序数字
	 */
	private Long sortNum;

	/**
	 * 外键1
	 */
	private String outKey1;

	/**
	 * 外键2
	 */
	private String outKey2;

	/**
	 * 备用1
	 */
	private String bak1;

	/**
	 * 备用2
	 */
	private String bak2;

	/**
	 * 类别子结点列表
	 */
	private List<Category> subCategoryList;

	public List<Category> getSubCategoryList() {
		return subCategoryList;
	}

	public void setSubCategoryList(List<Category> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParents() {
		return parents;
	}

	public void setParents(String parents) {
		this.parents = parents;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getIcons() {
        return icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }

    public Long getSortNum() {
		return sortNum;
	}

	public void setSortNum(Long sortNum) {
		this.sortNum = sortNum;
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

	public String getBak1() {
		return bak1;
	}

	public void setBak1(String bak1) {
		this.bak1 = bak1;
	}

	public String getBak2() {
		return bak2;
	}

	public void setBak2(String bak2) {
		this.bak2 = bak2;
	}

}
