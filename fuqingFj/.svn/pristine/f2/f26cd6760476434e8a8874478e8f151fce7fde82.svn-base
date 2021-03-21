package com.greathack.homlin.pojo;

import com.greathack.homlin.pojo.system.SearchCriteria;
import com.greathack.utils.tools.HashUtils;

import java.io.Serializable;

public class AmUnitSearchCriteria extends SearchCriteria implements Serializable {

    /**
     * 组织机构编码字段
     */
	public static final int KW_FIELDS_ORG_CODE = 1;
	/**
	 * 组织机构名称字段
	 */
	public static final int KW_FIELDS_ORG_NAME = 2;

	/**
	 * 备注字段
	 */
	public static final int KW_FIELDS_REMARK = 4;

	/**
	 * 组织机构简称
	 */
	private static final int KW_FIELDS_SHORT_NAME = 8;

	/**
	 * 父类ID
	 */
	private Long parentId;
	/**
	 * 单位ID，用于获取本单位及其以下单位的列表
	 */
	private Long orgId;
	
	private String orgType;

	private String orgRank;
	
	private String orgGroup;
	
	private Integer orgLevel;
	
	private String createBy;

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	
	
	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgRank() {
		return orgRank;
	}

	public void setOrgRank(String orgRank) {
		this.orgRank = orgRank;
	}

	public String getOrgGroup() {
		return orgGroup;
	}

	public void setOrgGroup(String orgGroup) {
		this.orgGroup = orgGroup;
	}

	public Integer getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Override
	protected String getKwFieldName(int field) {
		switch (field) {
        case AmUnitSearchCriteria.KW_FIELDS_ORG_CODE:
            return "orgCode";
            
		case AmUnitSearchCriteria.KW_FIELDS_ORG_NAME:
			return "orgName";

		case AmUnitSearchCriteria.KW_FIELDS_REMARK:
			return "remark";

		case AmUnitSearchCriteria.KW_FIELDS_SHORT_NAME:
			return "shortName";

		default:
			return "";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((parentId == null) ? 0 : HashUtils.fnv1_32_hash(parentId.toString()));
		result = prime * result + ((orgType == null) ? 0 : HashUtils.fnv1_32_hash(orgType));
		result = prime * result + ((orgGroup == null) ? 0 : HashUtils.fnv1_32_hash(orgGroup));
		result = prime * result + ((orgLevel == null) ? 0 : HashUtils.fnv1_32_hash(orgLevel.toString()));
		result = prime * result + ((createBy == null) ? 0 : HashUtils.fnv1_32_hash(createBy));
		return result;
	}	
	
}
