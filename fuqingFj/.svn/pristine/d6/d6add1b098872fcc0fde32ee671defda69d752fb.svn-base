package com.greathack.homlin.service.scheduletask;

import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.scheduletask.IScheduleTaskDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.scheduletask.ScheduleTask;
import com.greathack.homlin.pojo.scheduletask.ScheduleTaskSearchCriteria;
import com.greathack.homlin.qtz.JobPojo;
import com.greathack.homlin.qtz.QuartzManager;
import com.greathack.homlin.serviceinterface.scheduletask.IScheduleTaskService;
import com.greathack.homlin.system.IdCreator;
import org.quartz.JobDataMap;
import org.quartz.SimpleScheduleBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * @Date 2020-01-19
 * @Author huangjh
 */
public class ScheduleTaskService implements IScheduleTaskService {

    private static Logger logger = LoggerFactory.getLogger(ScheduleTaskService.class);

    private IScheduleTaskDAO scheduleTaskDAO = (IScheduleTaskDAO) DAOFactory.createDAO("IScheduleTaskDAO");

    @Override
    public List<ScheduleTask> search(ScheduleTaskSearchCriteria criteria) {
        return scheduleTaskDAO.search(criteria);
    }

    @Override
    public Long getSearchResultCount(ScheduleTaskSearchCriteria criteria) {
        return scheduleTaskDAO.getSearchResultCount(criteria);
    }

    @Override
    public ScheduleTask addScheduleTask(ScheduleTask scheduleTask) throws ServiceException {
        scheduleTask.setId(IdCreator.createId("ScheduleTask"));
        scheduleTask.setCreateTime(System.currentTimeMillis());
        if(scheduleTask.getState()==null){
            scheduleTask.setState(2);
        }
        scheduleTaskDAO.add(scheduleTask);
        return scheduleTask;
    }

    @Override
    public void delTask(long id) {
        scheduleTaskDAO.delete(id);
    }

    @Override
    public ScheduleTask getScheduleTask(Long id) {
        return scheduleTaskDAO.findById(id);
    }

    @Override
    public void editTask(ScheduleTask scheduleTask) {
        scheduleTaskDAO.update(scheduleTask);
    }

    @Override
    public void changeState(ScheduleTask scheduleTask) {
        scheduleTaskDAO.changeState(scheduleTask);
    }

    @Override
    public void start(ScheduleTask scheduleTask)  throws ServiceException {
        scheduleTask=scheduleTaskDAO.findById(scheduleTask.getId());
        if(scheduleTask==null){
            throw new ServiceException(ErrCode.SCHEDULE_TASK_NOT_EXIST,"SCHEDULE_TASK_NOT_EXIST");
        }
        try {
            JobPojo job = new JobPojo(scheduleTask.getTaskCode(),Class.forName(scheduleTask.getTaskClass()));
            JobDataMap jobDataMap=new JobDataMap();
            job.setJobDataMap(jobDataMap);
            scheduleTask.setState(1);//启用
            scheduleTaskDAO.changeState(scheduleTask);

            job.setStartTime(new Date(scheduleTask.getStartTime()));
            job.setEndTime(new Date(scheduleTask.getEndTime()));
            SimpleScheduleBuilder simpleSchedule=SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInMilliseconds(scheduleTask.getInterval1())
                    .withRepeatCount(scheduleTask.getTimes());
                    //.withMisfireHandlingInstructionNextWithRemainingCount();
            job.setSimpleSchedule(simpleSchedule);
            QuartzManager.addJob(job);
            QuartzManager.startScheduler();
        } catch (ClassNotFoundException e) {
            throw new ServiceException(ErrCode.CLASS_NOT_FOUND, "CLASS_NOT_FOUND");
        }
    }

    @Override
    public void stop(ScheduleTask scheduleTask) throws ServiceException {
        scheduleTask=scheduleTaskDAO.findById(scheduleTask.getId());
        if(scheduleTask==null){
            throw new ServiceException(ErrCode.SCHEDULE_TASK_NOT_EXIST,"SCHEDULE_TASK_NOT_EXIST");
        }
        try {
            JobPojo job = new JobPojo(scheduleTask.getTaskCode(),Class.forName(scheduleTask.getTaskClass()));
            scheduleTask.setState(2);//停用
            scheduleTaskDAO.changeState(scheduleTask);
            QuartzManager.removeJob(job);
        } catch (ClassNotFoundException e) {
            throw new ServiceException(ErrCode.CLASS_NOT_FOUND, "CLASS_NOT_FOUND");
        }
    }
}
