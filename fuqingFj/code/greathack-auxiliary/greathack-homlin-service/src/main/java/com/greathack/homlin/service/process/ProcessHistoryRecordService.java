package com.greathack.homlin.service.process;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.process.IProcessHistoryRecordDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.process.ProcessHistoryRecord;
import com.greathack.homlin.serviceinterface.process.IProcessHistoryRecordService;
import com.greathack.homlin.system.IdCreator;

import java.util.List;

public class ProcessHistoryRecordService implements IProcessHistoryRecordService {

    private static IProcessHistoryRecordDAO processHistoryRecordDAO = (IProcessHistoryRecordDAO) DAOFactory.createDAO("IProcessHistoryRecordDAO");

    @Override
    public ProcessHistoryRecord add(ProcessHistoryRecord instance) throws ServiceException {
        try {
            instance.setCreateTime(Common.getCurrentTime());
            instance.setId(IdCreator.createId("ProcessHistoryRecord"));
            processHistoryRecordDAO.add(instance);
            return instance;
        } catch (ServiceException e) {
            throw new ServiceException(ErrCode.UNKNOWN_ERROR, "UNKNOWN_ERROR");
        }
    }

    @Override
    public void delete(Long id) {
        processHistoryRecordDAO.delete(id);
    }

    @Override
    public void update(ProcessHistoryRecord instance) {
        processHistoryRecordDAO.update(instance);
    }

    @Override
    public ProcessHistoryRecord get(Long id) {
        return processHistoryRecordDAO.findById(id);
    }

    @Override
    public List<ProcessHistoryRecord> findByExample(ProcessHistoryRecord instance) {
        return processHistoryRecordDAO.findByExample(instance);
    }

    @Override
    public List<ProcessHistoryRecord> findAll() {
        return processHistoryRecordDAO.findAll();
    }
}
