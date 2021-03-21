package com.greathack.homlin.pojo.am;

import com.greathack.homlin.pojo.system.SearchCriteria;
import com.greathack.utils.tools.HashUtils;
import com.greathack.utils.tools.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AmQuotasSearchCriteria extends SearchCriteria implements Serializable {

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


	private String orgName;//单位名称
	private String orgCode;//单位编码
	private Long orgType;//单位类别
	private Long orgGroup;//单位组别
	private String parents;//单位id集合
	private String orgId;//单位ID
	private Integer orgLevel;

	public List<Integer> getOrgLeaveList() {
		if(orgLevel!=null){
			return Utils.recountToArrayList(orgLevel);
		}
		return new ArrayList<Integer>();
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Long getOrgType() {
		return orgType;
	}

	public void setOrgType(Long orgType) {
		this.orgType = orgType;
	}

	public Long getOrgGroup() {
		return orgGroup;
	}

	public void setOrgGroup(Long orgGroup) {
		this.orgGroup = orgGroup;
	}

	public String getParents() {
		return parents;
	}

	public void setParents(String parents) {
		this.parents = parents;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Integer getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	@Override
	protected String getKwFieldName(int field) {
		switch (field) {
		case AmQuotasSearchCriteria.KW_FIELDS_ORG_CODE:
				return "org.orgCode";

		case AmQuotasSearchCriteria.KW_FIELDS_ORG_NAME:
			return "org.orgName";

		case AmQuotasSearchCriteria.KW_FIELDS_REMARK:
			return "remark";

		default:
			return "";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((orgName == null) ? 0 : HashUtils.fnv1_32_hash(orgName.toString()));
		result = prime * result + ((orgCode == null) ? 0 : HashUtils.fnv1_32_hash(orgCode));
		result = prime * result + ((orgType == null) ? 0 : HashUtils.fnv1_32_hash(orgType.toString()));
		result = prime * result + ((orgGroup == null) ? 0 : HashUtils.fnv1_32_hash(orgGroup.toString()));
		result = prime * result + ((parents == null) ? 0 : HashUtils.fnv1_32_hash(parents.toString()));
		result = prime * result + ((orgLevel == null) ? 0 : HashUtils.fnv1_32_hash(orgLevel.toString()));
		result = prime * result + ((orgId == null) ? 0 : HashUtils.fnv1_32_hash(orgId.toString()));
		return result;
	}
	
}
