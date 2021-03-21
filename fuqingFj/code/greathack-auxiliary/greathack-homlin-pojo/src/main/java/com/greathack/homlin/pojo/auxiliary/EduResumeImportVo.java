package com.greathack.homlin.pojo.auxiliary;

import com.greathack.homlin.annotation.Excel;

import java.io.Serializable;

/**
 * @Description
 * @Author renTX
 * @Date 2020-10-12
 */
public class EduResumeImportVo implements Serializable {

	private static final long serialVersionUID = 1838460727209019119L;

	@Excel(name = "本人姓名",type = Excel.Type.EXPORT)

	private String xm;
	/**
	 * 身份证号码
	 */
	@Excel(name = "*身份证号码")
	private String idcard;

	@Excel(name = "*开始日期", prompt = "格式：2020/01/03或2020-01-03")
	private String startDate;//开始日期

	@Excel(name = "*结束日期", prompt = "格式：2020/01/03或2020-01-03")
	private String endDate;//结束日期

	/**
	 * 毕业院校
	 */
	@Excel(name = "毕业院校")
	private String university;

	/**
	 * 专业
	 */
	@Excel(name = "专业")
	private String specialitie;

	/**
	 * 学历
	 */
	@Excel(name = "学历",combo={"全日制本科","非全日制本科","全日制大专","非全日制大专","中专","职高","高中","初中","小学","无学历"})
	private String eduLevel;

	/**
	 * 学位
	 */
	@Excel(name = "学位",combo={"学士","硕士","博士"})
	private String degree;


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

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getSpecialitie() {
		return specialitie;
	}

	public void setSpecialitie(String specialitie) {
		this.specialitie = specialitie;
	}

	public String getEduLevel() {
		return eduLevel;
	}

	public void setEduLevel(String eduLevel) {
		this.eduLevel = eduLevel;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
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
