package com.greathack.homlin.pojo.org;

import com.greathack.homlin.pojo.system.SearchCriteria;
import com.greathack.utils.tools.HashUtils;
import com.greathack.utils.tools.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrganizationSearchCriteria extends SearchCriteria implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8270733397832698279L;
    /**
     * 组织机构编码字段
     */
    private static final int KW_FIELDS_ORG_CODE = 1;
	/**
	 * 组织机构名称字段
	 */
	private static final int KW_FIELDS_ORG_NAME = 2;

	/**
	 * 备注字段
	 */
	private static final int KW_FIELDS_REMARK = 4;

	/**
	 * 备用1字段
	 */
	private static final int KW_FIELDS_BAK1 = 8;

	/**
	 * 备用2字段
	 */
	private static final int KW_FIELDS_BAK2 = 16;

	/**
	 * 组织机构简称
	 */
	private static final int KW_FIELDS_SHORT_NAME = 32;

	/**
	 * 父类ID
	 */
	private Long parentId;
	/**
	 * 单位ID，用于获取本单位及其以下单位的列表
	 */
	private Long orgId;
	
	private String parents;
	
	private String orgType;

	private String orgRank;
	
	private String userId;
	
	private Integer orgLevel;
	
	private String createBy;

	private String outKey1;

	private Integer orgState;

	private List<String> nameLikeList;		// 单位名称like搜索

	private List<String> nameNotLikeList;		// 单位名称not like搜索

	public List<Integer> getOrgLeaveList() {
		if(orgLevel!=null){
			return Utils.recountToArrayList(orgLevel);
		}
		return new ArrayList<Integer>();
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getParents() {
		return parents;
	}

	public void setParents(String parents) {
		this.parents = parents;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getOutKey1() {
		return outKey1;
	}

	@Override
	public void setOutKey1(String outKey1) {
		this.outKey1 = outKey1;
	}

	public Integer getOrgState() {
		return orgState;
	}

	public void setOrgState(Integer orgState) {
		this.orgState = orgState;
	}

	public List<String> getNameLikeList() {
		return nameLikeList;
	}

	public void setNameLikeList(List<String> nameLikeList) {
		this.nameLikeList = nameLikeList;
	}

	public List<String> getNameNotLikeList() {
		return nameNotLikeList;
	}

	public void setNameNotLikeList(List<String> nameNotLikeList) {
		this.nameNotLikeList = nameNotLikeList;
	}

	@Override
	protected String getKwFieldName(int field) {
		switch (field) {
        case OrganizationSearchCriteria.KW_FIELDS_ORG_CODE:
            return "orgCode";
            
		case OrganizationSearchCriteria.KW_FIELDS_ORG_NAME:
			return "orgName";

		case OrganizationSearchCriteria.KW_FIELDS_REMARK:
			return "remark";

		case OrganizationSearchCriteria.KW_FIELDS_BAK1:
			return "bak1";

		case OrganizationSearchCriteria.KW_FIELDS_BAK2:
			return "bak2";

		case OrganizationSearchCriteria.KW_FIELDS_SHORT_NAME:
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
		result = prime * result + ((parents == null) ? 0 : HashUtils.fnv1_32_hash(parents));
		result = prime * result + ((orgType == null) ? 0 : HashUtils.fnv1_32_hash(orgType));
		result = prime * result + ((orgRank == null) ? 0 : HashUtils.fnv1_32_hash(orgRank));
		result = prime * result + ((parents == null) ? 0 : HashUtils.fnv1_32_hash(parents));
		result = prime * result + ((userId == null) ? 0 : HashUtils.fnv1_32_hash(userId));
		result = prime * result + ((orgLevel == null) ? 0 : HashUtils.fnv1_32_hash(orgLevel.toString()));
		result = prime * result + ((createBy == null) ? 0 : HashUtils.fnv1_32_hash(createBy));
		return result;
	}	
	
}
