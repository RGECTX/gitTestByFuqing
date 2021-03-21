package com.greathack.homlin.pojo.am;

import com.greathack.utils.tools.Utils;
import com.greathack.utils.tools.Validation;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author zhanghb
 * @Date 2019-10-01 
 */
public class AmQuotas implements Serializable {

	private static final long serialVersionUID =  6640243133970034708L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 组织机构ID
	 */
	private Long orgId;

	/**
	 * 编制数
	 */
	private Integer quotasNum;

	/**
	 * 实配数
	 */
	private Integer allocateNum;

	/**
	 * 借调人数
	 */
	private Integer jdNum;

	/**
	 * 实习人数
	 */
	private Integer sxNum;

	/**
	 * 文号
	 */
	private String docNo;

	/**
	 * 批准日期
	 */
	private Integer pzrq;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建人
	 */
	private Long createBy;

	/**
	 * 创建时间
	 */
	private Long createTime;

	/**
	 * 外键1
	 */
	private String outkey1;

	/**
	 * 外键2
	 */
	private String outkey2;

	/**
	 * 备用1
	 */
	private String bak1;

	/**
	 * 备用2
	 */
	private String bak2;

	private String orgCode;//单位编码，非数据库字段
	private String orgName;//单位名称，非数据库字段
	private String orgType;//单位类别，非数据库字段
	private String orgGroup;//单位组别，非数据库字段

	private Integer gapNum;//缺口数，非数据库字段

	private String state;//人员状态，非数据库字段

	public List<Integer> getStateList() {
		if(state!=null && Validation.isInteger(state)){
			return Utils.recountToArrayList(Integer.parseInt(state));
		}
		return null;
	}


	public  AmQuotas(){super();}

	public AmQuotas(Integer quotasNum, Integer allocateNum, Integer gapNum) {
		this.quotasNum = quotasNum;
		this.allocateNum = allocateNum;
		this.gapNum = gapNum;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Integer getQuotasNum() {
		return this.quotasNum;
	}

	public void setQuotasNum(Integer quotasNum) {
		this.quotasNum = quotasNum;
	}

	public Integer getAllocateNum() {
		return this.allocateNum;
	}

	public void setAllocateNum(Integer allocateNum) {
		this.allocateNum = allocateNum;
	}

	public Integer getJdNum() {
		return jdNum;
	}

	public void setJdNum(Integer jdNum) {
		this.jdNum = jdNum;
	}

	public Integer getSxNum() {
		return sxNum;
	}

	public void setSxNum(Integer sxNum) {
		this.sxNum = sxNum;
	}

	public String getDocNo() {
		return this.docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public Integer getPzrq() {
		return this.pzrq;
	}

	public void setPzrq(Integer pzrq) {
		this.pzrq = pzrq;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Long getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getOutkey1() {
		return this.outkey1;
	}

	public void setOutkey1(String outkey1) {
		this.outkey1 = outkey1;
	}

	public String getOutkey2() {
		return this.outkey2;
	}

	public void setOutkey2(String outkey2) {
		this.outkey2 = outkey2;
	}

	public String getBak1() {
		return this.bak1;
	}

	public void setBak1(String bak1) {
		this.bak1 = bak1;
	}

	public String getBak2() {
		return this.bak2;
	}

	public void setBak2(String bak2) {
		this.bak2 = bak2;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgGroup() {
		return orgGroup;
	}

	public void setOrgGroup(String orgGroup) {
		this.orgGroup = orgGroup;
	}

	public Integer getGapNum() {
		return gapNum;
	}

	public void setGapNum(Integer gapNum) {
		this.gapNum = gapNum;
	}
}
