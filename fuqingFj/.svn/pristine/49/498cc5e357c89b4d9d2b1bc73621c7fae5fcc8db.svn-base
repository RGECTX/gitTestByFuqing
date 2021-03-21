package com.greathack.homlin.serviceinterface.scheduletask;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.scheduletask.ScheduleTask;
import com.greathack.homlin.pojo.scheduletask.ScheduleTaskSearchCriteria;

import java.util.List;

/**
 * @Date 2020-01-19
 * @Author huangjh
 */
public interface IScheduleTaskService {

    /**
     * 搜索定时任务列表
     * @param criteria
     * @return
     */
    List<ScheduleTask> search(ScheduleTaskSearchCriteria criteria);

    /**
     * 搜索定时任务总条数
     * @param criteria
     * @return
     */
    Long getSearchResultCount(ScheduleTaskSearchCriteria criteria);

    /**
     * 添加定时任务
     * @param scheduleTask
     * @return
     */
    ScheduleTask addScheduleTask(ScheduleTask scheduleTask) throws ServiceException;

    /**
     * 删除定时任务
     * @param id
     */
    void delTask(long id);

    /**
     * 根据ID搜索定时任务
     * @param id
     * @return
     */
    ScheduleTask getScheduleTask(Long id);

    /**
     * 修改定时任务
     * @param scheduleTask
     */
    void editTask(ScheduleTask scheduleTask);

    void changeState(ScheduleTask scheduleTask);

    /**
     * 启动定时任务
     * @param scheduleTask 要启动的定时任务
     */
    void start(ScheduleTask scheduleTask) throws ServiceException;

    /**
     * 停止定时任务
     * @param scheduleTask 要停止的定时任务
     */
    void stop(ScheduleTask scheduleTask)  throws ServiceException;
}
