package com.greathack.homlin.pojo.auxiliary;

import com.greathack.homlin.pojo.system.SearchCriteria;

import java.io.Serializable;

public class AuxQjsqSearchCriteria extends SearchCriteria implements Serializable {

    private static final long serialVersionUID = 4375806994430428184L;
    /**
     * 工作单位ID
     */
    private Long orgId;
    /**
     * 单位名称
     */
    private String orgName;

    public AuxQjsqSearchCriteria() {
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

    protected String getKwFieldName(int field) {
        switch (field) {
            default:
                return "";
        }
    }
}
