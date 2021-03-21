/**
 * 
 */
package com.greathack.homlin.qtz;

import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.TriggerKey;

import java.util.Date;

/**
 * @author greathack
 *
 */
public class JobPojo {
	
	
	/**
	 * @param jobName 任务名称（任务的唯一标识）
	 * @param jobClass 任务执行对象的Class
	 */
	public JobPojo(String jobName, Class jobClass) {
		this.jobName = jobName+"_JOB_NAME";
		this.jobGroupName= jobName+"_JOB_GROUP_NAME";
		this.triggerGroupName= jobName+"_TRIGGER_GROUP_NAME";
		this.jobClass = jobClass;
	}

	/**
	 * 任务名称
	 */
	private String jobName;
	
	/**
	 * 任务组名称
	 */
	private String jobGroupName;
	
	
	/**
	 * 触发器组名称
	 */
	private String triggerGroupName;
	
	/**
	 * 任务类
	 */
	private Class jobClass;
	
	
	/**
	 * 任务参数
	 */
	private JobDataMap jobDataMap = new JobDataMap();
	
	/**
	 * 任务开始时间
	 */
	private Date startTime;
	
	/**
	 * 任务结束时间
	 */
	private Date endTime;
	
	/**
	 * 计划任务时间表
	 */
	private SimpleScheduleBuilder simpleSchedule;

	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroupName() {
		return jobGroupName;
	}
	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}
	public Class getJobClass() {
		return jobClass;
	}
	public void setJobClass(Class jobClass) {
		this.jobClass = jobClass;
	}
	public JobKey getJobKey() {
		return new JobKey(getJobName(),getJobGroupName());
	}
	public JobDataMap getJobDataMap() {
		return jobDataMap;
	}
	public void setJobDataMap(JobDataMap jobDataMap) {
		this.jobDataMap = jobDataMap;
	}
	
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public SimpleScheduleBuilder getSimpleSchedule() {
		return simpleSchedule;
	}
	public void setSimpleSchedule(SimpleScheduleBuilder simpleSchedule) {
		this.simpleSchedule = simpleSchedule;
	}
	
	
	public String getTriggerName() {
		return jobName;
	}
	public String getTriggerGroupName() {
		return triggerGroupName;
	}
	public TriggerKey getTriggerKey() {
		return new TriggerKey(getTriggerName(),getTriggerGroupName());
	}
	/**
	 * @param args
	 */	
	public static void main(String[] args) {
		// TODO 自动生成的方法存根

	}
	
	

}
