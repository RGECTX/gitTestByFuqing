package com.greathack.homlin.pojo.auxiliary;

import java.io.Serializable;

/**
 * @Description
 * @Author renTX
 * @Date 2020-09-27
 */
public class EduResume implements Serializable {

	private static final long serialVersionUID = -7272412151939500448L;
	/**
	 * ID
	 */
	private Long id;

	/**
	 * 应用编码
	 */
	private String appCode;

	/**
	 * 所属主体ID
	 */
	private String instanceId;

	/**
	 * 身份证号码
	 */
	private String idcard;

	private Integer startDate;//开始日期

	private Integer endDate;//结束日期

	/**
	 * 毕业院校
	 */
	private String university;

	/**
	 * 专业
	 */
	private String specialitie;

	/**
	 * 学历
	 */
	private String eduLevel;

	/**
	 * 学位
	 */
	private String degree;

	/**
	 * 创建人
	 */
	private Long createBy;

	/**
	 * 创建时间
	 */
	private Long createTime;

	/**
	 * 备注
	 */
	private String remark;

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

	public EduResume(){super();}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppCode() {
		return this.appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getInstanceId() {
		return this.instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getUniversity() {
		return this.university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getSpecialitie() {
		return this.specialitie;
	}

	public void setSpecialitie(String specialitie) {
		this.specialitie = specialitie;
	}

	public String getEduLevel() {
		return this.eduLevel;
	}

	public void setEduLevel(String eduLevel) {
		this.eduLevel = eduLevel;
	}

	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
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

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getStartDate() {
		return startDate;
	}

	public void setStartDate(Integer startDate) {
		this.startDate = startDate;
	}

	public Integer getEndDate() {
		return endDate;
	}

	public void setEndDate(Integer endDate) {
		this.endDate = endDate;
	}
}
