package com.greathack.homlin.service.schedule;

import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.am.IAmQuotasDAO;
import com.greathack.homlin.pojo.am.AmQuotas;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AmQuotasAllocateJob implements Job {

    private static Logger logger = LoggerFactory.getLogger(AmQuotasAllocateJob.class);
    private static IAmQuotasDAO amQuotasDAO = (IAmQuotasDAO) DAOFactory.createDAO("IAmQuotasDAO");
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        batchSetQuotasAllocateNum();
    }

    public void batchSetQuotasAllocateNum(){

    }
}
