/**
 * 
 */
package com.greathack.homlin.qtz;

import com.greathack.homlin.common.SpringContextHolder;
import org.quartz.*;
import org.quartz.impl.StdScheduler;

/**
 * @author greathack
 *
 */
public class QuartzManager {

	
	private static Scheduler sched;
	static {
       sched = (StdScheduler) SpringContextHolder.getBean("schedulerFactory");
    }
  
       
    /**
     * 添加一个任务到定时器
     * @param job 任务pojo
     */
    public static void addJob(JobPojo job) {
        try {
        	// 任务  
            JobDetail jobDetail = JobBuilder
            		.newJob(job.getJobClass())
            		.storeDurably(false)
            		.usingJobData(job.getJobDataMap())
            		.withIdentity(job.getJobKey())
            		.build();
            
            // 触发器  
            Trigger trigger = TriggerBuilder.newTrigger()
            		.startAt(job.getStartTime())
            		.endAt(job.getEndTime())
            		.withIdentity(job.getTriggerKey())
            		.withSchedule(job.getSimpleSchedule())
            		.build();// 触发器  
            sched.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }
    
  
    
    /**
     * 从定时器移除一个任务
     * @param job  任务pojo
     */
    public static void removeJob(JobPojo job) {
    	try {
			sched.pauseTrigger(job.getTriggerKey());
			sched.unscheduleJob(job.getTriggerKey());
			sched.deleteJob(job.getJobKey());
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
    }    
    
  
    
    /**
     * 启动定时器
     */
    public static void startScheduler() {  
        try {
        	// 启动  
            if (sched.isShutdown()) {
                sched.start();  
            } 
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    
    /**
     * 关闭定时器
     */
    public static void shutdownScheduler() {  
        try {
            if (!sched.isShutdown()) {  
                sched.shutdown();  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		JobPojo job = new JobPojo("测试",DemoJob.class);
//		String s = "想传递数据";
//		JobDataMap jobDataMap = new JobDataMap();
//		jobDataMap.put("test", s);
//		job.setJobDataMap(jobDataMap);
//		long time = System.currentTimeMillis()+1000*10;
//		long time2 = System.currentTimeMillis()+1000*60+5000*3;
//		job.setStartTime(new Date(time));
//		job.setEndTime(new Date(time2));
//      addJob(job);
		
	}
	
	

}
