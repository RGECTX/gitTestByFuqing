package com.greathack.homlin.pojo.auxiliary;

import com.greathack.homlin.pojo.system.SearchCriteria;

import java.io.Serializable;

public class AuxBzglSearchCriteria extends SearchCriteria implements Serializable {

    private static final long serialVersionUID = 667362359278550849L;
    /**
     * 工作单位ID
     */
    private String orgId;

    /**
     * 工作单位名称
     */
    private String orgName;

    /**
     * 品名
     */
    private String pm;

    /**
     * 状态
     */
    private String state;

    private String pendingUserId;//查询未审批用户

    private String handledUserId;//查询已审批用户

    public String getPendingUserId() {
        return pendingUserId;
    }

    public void setPendingUserId(String pendingUserId) {
        this.pendingUserId = pendingUserId;
    }

    public String getHandledUserId() {
        return handledUserId;
    }

    public void setHandledUserId(String handledUserId) {
        this.handledUserId = handledUserId;
    }

    public AuxBzglSearchCriteria() {
        super();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    protected String getKwFieldName(int field) {
        switch (field) {
            default:
                return "";
        }
    }
}
