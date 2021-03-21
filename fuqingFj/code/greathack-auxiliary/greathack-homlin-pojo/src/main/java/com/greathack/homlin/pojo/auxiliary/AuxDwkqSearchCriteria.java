package com.greathack.homlin.pojo.auxiliary;

import com.greathack.homlin.pojo.system.SearchCriteria;

import java.io.Serializable;

public class AuxDwkqSearchCriteria extends SearchCriteria implements Serializable {

    private static final long serialVersionUID = 3121109863815310860L;
    /**
     * 年度
     */
    private Integer nd;
    /**
     * 月度
     */
    private Integer yd;
    /**
     * 工作单位ID
     */
    private Long orgId;
    /**
     * 单位名称
     */
    private String orgName;

    public AuxDwkqSearchCriteria() {
        super();
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

    protected String getKwFieldName(int field) {
        switch (field) {
            default:
                return "";
        }
    }
}
