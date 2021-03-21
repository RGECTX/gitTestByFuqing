package com.greathack.homlin.daointerface.scheduletask;

import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.scheduletask.ScheduleTask;
import com.greathack.homlin.pojo.scheduletask.ScheduleTaskSearchCriteria;

import java.util.List;

/**
 * @Date 2020-01-19
 * @Author huangjh
 */
public interface IScheduleTaskDAO extends BaseDAO<ScheduleTask> {

    List<ScheduleTask> search(ScheduleTaskSearchCriteria criteria);

    Long getSearchResultCount(ScheduleTaskSearchCriteria criteria);

    void changeState(ScheduleTask scheduleTask);
}
