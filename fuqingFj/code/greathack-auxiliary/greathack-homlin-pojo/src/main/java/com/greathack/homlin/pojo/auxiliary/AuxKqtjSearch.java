package com.greathack.homlin.pojo.auxiliary;

import java.io.Serializable;

public class AuxKqtjSearch implements Serializable {

    private static final long serialVersionUID = 3915731061784218017L;
    /**
     * 主键
     */
    private Long kqtjId;

    /**
     * 姓名
     */
    private String xm;

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
     * 缺勤天数
     */
    private String qqTs;

    /**
     * 是否满勤,1、满勤，2、未满勤（当缺勤天数为0时满勤，大于0则未满勤）
     */
    private String isWorkFullHours;

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
     * 身份证
     */
    private String idcard;

    /**
     * 主键
     */
    private Long daId;

    /**
     * 性别
     */
    private String xb;

    /**
     *辅警类别
     */
    private String fjType;

    /**
     *辅警类别
     */
    private String state;

    public AuxKqtjSearch() {
        super();
    }

    public Long getKqtjId() {
        return kqtjId;
    }

    public void setKqtjId(Long kqtjId) {
        this.kqtjId = kqtjId;
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

    public String getQqTs() {
        return qqTs;
    }

    public void setQqTs(String qqTs) {
        this.qqTs = qqTs;
    }

    public String getIsWorkFullHours() {
        return isWorkFullHours;
    }

    public void setIsWorkFullHours(String isWorkFullHours) {
        this.isWorkFullHours = isWorkFullHours;
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

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public Long getDaId() {
        return daId;
    }

    public void setDaId(Long daId) {
        this.daId = daId;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getFjType() {
        return fjType;
    }

    public void setFjType(String fjType) {
        this.fjType = fjType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
