package com.greathack.homlin.pojo.auxiliary;

import java.io.Serializable;

public class AuxDwkq implements Serializable {

    private static final long serialVersionUID = -7811286970026279019L;
    /**
     * 主键
     */
    private Long dwkqId;

    /**
     * 工作单位ID
     */
    private Long orgId;

    /**
     * 单位名称
     */
    private String orgName;

    /**
     * 年度
     */
    private Integer nd;

    /**
     * 月度
     */
    private Integer yd;

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

    public Long getDwkqId() {
        return dwkqId;
    }

    public void setDwkqId(Long dwkqId) {
        this.dwkqId = dwkqId;
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

    public Integer getNd() {
        return nd;
    }

    public void setNd(Integer nd) {
        this.nd = nd;
    }

    public Integer getYd() {
        return yd;
    }

    public void setYd(Integer yd) {
        this.yd = yd;
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
}
