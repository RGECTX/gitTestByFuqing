package com.greathack.homlin.pojo.auxiliary;

import java.io.Serializable;

/**
 * 招录建档
 *
 * @author renTX
 * @Description
 * @date 2020-09-04
 */
public class AuxZljd implements Serializable {

    private static final long serialVersionUID = -5464638469957526145L;
    /**
     * 主键
     */
    private Long zlId;

    /**
     * 姓名
     */
    private String xm;

    /**
     * 身份证号
     */
    private String idcard;

    /**
     * 上报单位ID
     */
    private Long orgId;

    /**
     * 上报单位名称
     */
    private String orgName;

    /**
     * 状态
     */
    private String state;

    /**
     * 接收单位
     */
    private String receiveOrgId;

    /**
     * 备注
     */
    private String remark;

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
     * 流程进度
     */
    private String progress;

    /**
     * 流程审批ID
     */
    private Long processId;

    /**
     * 流程审批ID
     */
    private String fjType;

    /**
     * 人员分配状态
     */
    private String RyfpState;

    /**
     * 新接收单位
     */
    private String newReceiveOrgId;

    /**
     * 新辅警类别
     */
    private String newFjType;

    /**
     * 用人单位ID
     */
    private Long yrdwId;

    /**
     * 用人单位名
     */
    private String yrdw;

    public AuxZljd() {
        super();
    }

    public Long getZlId() {
        return zlId;
    }

    public void setZlId(Long zlId) {
        this.zlId = zlId;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReceiveOrgId() {
        return receiveOrgId;
    }

    public void setReceiveOrgId(String receiveOrgId) {
        this.receiveOrgId = receiveOrgId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public String getFjType() {
        return fjType;
    }

    public void setFjType(String fjType) {
        this.fjType = fjType;
    }

    public String getRyfpState() {
        return RyfpState;
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

    public String getNewFjType() {
        return newFjType;
    }

    public void setNewFjType(String newFjType) {
        this.newFjType = newFjType;
    }

    public Long getYrdwId() {
        return yrdwId;
    }

    public void setYrdwId(Long yrdwId) {
        this.yrdwId = yrdwId;
    }

    public String getYrdw() {
        return yrdw;
    }

    public void setYrdw(String yrdw) {
        this.yrdw = yrdw;
    }
}
