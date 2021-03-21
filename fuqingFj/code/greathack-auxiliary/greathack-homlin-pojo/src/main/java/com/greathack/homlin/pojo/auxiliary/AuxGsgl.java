package com.greathack.homlin.pojo.auxiliary;

import java.io.Serializable;

public class AuxGsgl implements Serializable {

    private static final long serialVersionUID = 5265555925354421740L;

    /**
     * 主键
     */
    private Long gsglId;

    /**
     * 姓名
     */
    private String xm;

    /**
     * 工作单位ID
     */
    private Long orgId;

    /**
     * 工作单位名称
     */
    private String orgName;

    /**
     * 工伤发生日期
     */
    private String gsfsDate;

    /**
     * 工伤情况描述
     */
    private String gsqkms;

    /**
     * 上报日期
     */
    private Integer sbDate;

    /**
     * 上报状态,1、未上报，2、已上报
     */
    private Integer sbState;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间 1970年以来毫秒数
     */
    private Long createTime;

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
     * 外键2
     */
    private String bak2;

    /**
     * 性别
     */
    private String xb;

    /**
     * 身份证号
     */
    private String idcard;

    /**
     * 工伤审核状态
     */
    private String state;

    public AuxGsgl() {
        super();
    }

    public Long getGsglId() {
        return gsglId;
    }

    public void setGsglId(Long gsglId) {
        this.gsglId = gsglId;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getGsfsDate() {
        return gsfsDate;
    }

    public void setGsfsDate(String gsfsDate) {
        this.gsfsDate = gsfsDate;
    }

    public String getGsqkms() {
        return gsqkms;
    }

    public void setGsqkms(String gsqkms) {
        this.gsqkms = gsqkms;
    }

    public Integer getSbDate() {
        return sbDate;
    }

    public void setSbDate(Integer sbDate) {
        this.sbDate = sbDate;
    }

    public Integer getSbState() {
        return sbState;
    }

    public void setSbState(Integer sbState) {
        this.sbState = sbState;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
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

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
