package com.greathack.homlin.pojo.auxiliary;

import java.io.Serializable;

public class AuxGzgl implements Serializable {

    private static final long serialVersionUID = 7051476463300402953L;
    /**
     * 主键
     */
    private Long gzglId;


    /**
     * 工作单位ID
     */
    private Long orgId;

    /**
     * 工作单位名称
     */
    private String orgName;

    /**
     * 上报状态
     */
    private String sbState;

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
     * 姓名
     */
    private String xm;

    /**
     * 应发工资
     */
    private String yfgz;

    /**
     * 录入日期
     */
    private String sbDate;

    /**
     * 调整后工资
     */
    private String tzhgz;

    /**
     * 工资调整状态 0，未提交；1，已提交
     */
    private String gztzState;

    /**
     * 身份证号
     */
    private String idcard;

    /**
     * 档案id
     */
    private Long daId;

    public Long getDaId() {
        return daId;
    }

    public void setDaId(Long daId) {
        this.daId = daId;
    }

    public AuxGzgl() {
        super();
    }

    public Long getGzglId() {
        return gzglId;
    }

    public void setGzglId(Long gzglId) {
        this.gzglId = gzglId;
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


    public String getSbState() {
        return sbState;
    }

    public void setSbState(String sbState) {
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

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getYfgz() {
        return yfgz;
    }

    public void setYfgz(String yfgz) {
        this.yfgz = yfgz;
    }

    public String getSbDate() {
        return sbDate;
    }

    public void setSbDate(String sbDate) {
        this.sbDate = sbDate;
    }

    public String getTzhgz() {
        return tzhgz;
    }

    public void setTzhgz(String tzhgz) {
        this.tzhgz = tzhgz;
    }

    public String getGztzState() {
        return gztzState;
    }

    public void setGztzState(String gztzState) {
        this.gztzState = gztzState;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
}
