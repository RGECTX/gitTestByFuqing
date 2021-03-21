package com.greathack.homlin.pojo.auxiliary;

import com.greathack.homlin.pojo.system.SearchCriteria;

import java.io.Serializable;

public class AuxJyglSearchCriteria extends SearchCriteria implements Serializable {

    private static final long serialVersionUID = 6574773612187641437L;

    /**
     * 减员原因
     */
    private String reason;

    /**
     * 减员状态
     */
    private String state;

    /**
     * 上报单位
     */
    private Long reportId;

    /**
     * 身份证
     */
    private String idcard;

    /**
     * 姓名
     */
    private String xm;

    /**
     * 减员日期
     */
    private String jyrq;

    private String pendingUserId;//查询未审批用户

    private String handledUserId;//查询已审批用户

    private Long beginTime;
    private Long endTime;

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getJyrq() {
        return jyrq;
    }

    public void setJyrq(String jyrq) {
        this.jyrq = jyrq;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    protected String getKwFieldName(int field) {
        switch (field) {
            default:
                return "";
        }
    }
}
