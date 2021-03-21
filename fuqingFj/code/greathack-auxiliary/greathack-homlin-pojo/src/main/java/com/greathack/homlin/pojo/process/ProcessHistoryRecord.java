package com.greathack.homlin.pojo.process;

/**
 * 流程历史记录
 * @author zhanghb
 */
public class ProcessHistoryRecord {

    /**
     * 流程历史记录Id
     */
    private Long id;

    /**
     * 所属实体类型
     */
    private String instanceType;

    /**
     * 所属实体ID
     */
    private Long instanceId;

    /**
     * 流程ID
     */
    private Long processId;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 备注
     */
    private String remark;

    public ProcessHistoryRecord() {
    }

    public ProcessHistoryRecord(Long id, String instanceType, Long instanceId, Long processId, Long createBy, Long createTime, String remark) {
        this.id = id;
        this.instanceType = instanceType;
        this.instanceId = instanceId;
        this.processId = processId;
        this.createBy = createBy;
        this.createTime = createTime;
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
