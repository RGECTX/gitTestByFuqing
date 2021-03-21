package com.greathack.homlin.pojo.auxiliary;

import com.greathack.homlin.annotation.Excel;

import java.io.Serializable;

/**
 * @Description
 * @Author renTX
 * @Date 2020-10-12
 */
public class JobResumeImportVo implements Serializable {

	private static final long serialVersionUID = 1614323442153006081L;

	@Excel(name = "本人姓名",type = Excel.Type.EXPORT)
	private String xm;
	/**
	 * 身份证号码
	 */
	@Excel(name = "*身份证号码")
	private String idcard;

	/**
	 * 工作单位
	 */
	@Excel(name = "工作单位")
	private String workUnit;

	/**
	 * 职务
	 */
	@Excel(name = "职务")
	private String post;

	/**
	 * 开始日期
	 */
	@Excel(name = "*开始日期", prompt = "格式：2020/01/03或2020-01-03")
	private String startDate;

	/**
	 * 结束日期
	 */
	@Excel(name = "*结束日期", prompt = "格式：2020/01/03或2020-01-03")
	private String endDate;

	/**
	 * 备注
	 */
	@Excel(name = "备注")
	private String remark;


	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}
}
