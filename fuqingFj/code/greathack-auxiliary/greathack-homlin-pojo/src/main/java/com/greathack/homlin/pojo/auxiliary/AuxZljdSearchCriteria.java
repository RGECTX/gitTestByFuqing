package com.greathack.homlin.pojo.auxiliary;

import com.greathack.homlin.pojo.system.SearchCriteria;

import java.io.Serializable;

public class AuxZljdSearchCriteria extends SearchCriteria implements Serializable {

    private static final long serialVersionUID = 8798560354878321656L;
    /**
     * 工作单位ID
     */
    private String orgId;

    /**
     * 工作单位名称
     */
    private String orgName;

    /**
     * 状态
     */
    private String state;

    /**
     * 辅警类别
     */
    private String fjType;

    /**
     * 接收单位（分配）
     */
    private String receiveOrgId;

    /**
     * 人员分配状态
     */
    private String RyfpState;

    /**
     * 姓名
     */
    private String xm;

    private String pendingUserId;//查询未审批用户

    private String handledUserId;//查询已审批用户

    /**
     * 新分配状态
     */
    private String newReceiveOrgId;

    public AuxZljdSearchCriteria() {
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

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

    public String getFjType() {
        return fjType;
    }

    public void setFjType(String fjType) {
        this.fjType = fjType;
    }

    public String getReceiveOrgId() {
        return receiveOrgId;
    }

    public void setReceiveOrgId(String receiveOrgId) {
        this.receiveOrgId = receiveOrgId;
    }

    public String getRyfpState() {
        return RyfpState;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public void setRyfpState(String ryfpState) {
        RyfpState = ryfpState;
    }

    public String getNewReceiveOrgId() {
        return newReceiveOrgId;
    }

    public void setNewReceiveOrgId(String newReceiveOrgId) {
        this.newReceiveOrgId = newReceiveOrgId;
    }

    protected String getKwFieldName(int field) {
        switch (field) {
            default:
                return "";
        }
    }
}
