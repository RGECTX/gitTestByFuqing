package com.greathack.homlin.pojo.scheduletask;

import java.io.Serializable;

/**
 * @Date 2020-01-19
 * @Author huangjh
 */
public class ScheduleTask implements Serializable {
    /**
     * 定时任务id
     */
    private Long id;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务编码
     */
    private String taskCode;

    /**
     * 任务类别
     */
    private String taskType;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;

    /**
     * 执行次数：0 不限次数
     */
    private Integer times;

    /**
     * 执行间隔：毫秒数
     */
    private Long interval1;

    /**
     * 任务类
     */
    private String taskClass;

    /**
     * 状态：1、启用，2、停用
     */
    private Integer state;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间：1970年以来的毫秒数
     */
    private Long createTime;

    /**
     * 备注
     */
    private String remark;


    public ScheduleTask() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Long getInterval1() {
        return interval1;
    }

    public void setInterval1(Long interval1) {
        this.interval1 = interval1;
    }

    public String getTaskClass() {
        return taskClass;
    }

    public void setTaskClass(String taskClass) {
        this.taskClass = taskClass;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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
