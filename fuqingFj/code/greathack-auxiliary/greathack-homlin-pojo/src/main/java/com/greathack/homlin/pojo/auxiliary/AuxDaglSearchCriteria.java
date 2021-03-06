package com.greathack.homlin.pojo.auxiliary;

import com.greathack.homlin.pojo.system.SearchCriteria;

import java.io.Serializable;

public class AuxDaglSearchCriteria extends SearchCriteria implements Serializable {

    private static final long serialVersionUID = 5504740432573402549L;
    /**
     * 工作单位ID
     */
    private Long orgId;

    /**
     * 工作单位名称
     */
    private String orgName;

    /**
     * 辅警类别
     */
    private String fjType;

    /**
     * 状态
     */
    private String state;

    /**
     * 姓名
     */
    private String xm;

    /**
     * 姓名
     */
    private String xb;

    /**
     * 辅警编号
     */
    private String fjbh;

    /**
     * 身份证号
     */
    private String idcard;

    private Integer countorgId;

    private Integer countisbx;

    private Integer countxb;

    private Integer bzrs;

    private Integer zzfjs;//在职辅警数

    private Integer ptfjrs;//普通辅警人数

    private Integer ptfjbzs;//普通辅警编制数

    private Integer lffjbzs;//联防辅警编制数

    private Integer lffjrs;//联防辅警人数

    private Integer zxfjbzs;//专项辅警编制数

    private Integer zxfjrs;//专项辅警人数

    public String getFjbh() {
        return fjbh;
    }

    public void setFjbh(String fjbh) {
        this.fjbh = fjbh;
    }

    public Integer getPtfjrs() {
        return ptfjrs;
    }

    public void setPtfjrs(Integer ptfjrs) {
        this.ptfjrs = ptfjrs;
    }

    public Integer getPtfjbzs() {
        return ptfjbzs;
    }

    public void setPtfjbzs(Integer ptfjbzs) {
        this.ptfjbzs = ptfjbzs;
    }

    public Integer getLffjbzs() {
        return lffjbzs;
    }

    public void setLffjbzs(Integer lffjbzs) {
        this.lffjbzs = lffjbzs;
    }

    public Integer getLffjrs() {
        return lffjrs;
    }

    public void setLffjrs(Integer lffjrs) {
        this.lffjrs = lffjrs;
    }

    public Integer getZxfjbzs() {
        return zxfjbzs;
    }

    public void setZxfjbzs(Integer zxfjbzs) {
        this.zxfjbzs = zxfjbzs;
    }

    public Integer getZxfjrs() {
        return zxfjrs;
    }

    public void setZxfjrs(Integer zxfjrs) {
        this.zxfjrs = zxfjrs;
    }

    public Integer getZzfjs() {
        return zzfjs;
    }

    public void setZzfjs(Integer zzfjs) {
        this.zzfjs = zzfjs;
    }

    public Integer getBzrs() {
        return bzrs;
    }

    public void setBzrs(Integer bzrs) {
        this.bzrs = bzrs;
    }

    public Integer getCountorgId() {
        return countorgId;
    }

    public void setCountorgId(Integer countorgId) {
        this.countorgId = countorgId;
    }

    public Integer getCountisbx() {
        return countisbx;
    }

    public void setCountisbx(Integer countisbx) {
        this.countisbx = countisbx;
    }

    public Integer getCountxb() {
        return countxb;
    }

    public void setCountxb(Integer countxb) {
        this.countxb = countxb;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public AuxDaglSearchCriteria() {
        super();
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

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    protected String getKwFieldName(int field) {
        switch (field) {
            default:
                return "";
        }
    }
}
