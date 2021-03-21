package com.greathack.homlin.service.auxiliary;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxQjsqDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.auxiliary.AuxQjsq;
import com.greathack.homlin.pojo.auxiliary.AuxQjsqSearchCriteria;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxQjsqService;
import com.greathack.homlin.system.IdCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AuxQjsqService implements IAuxQjsqService {

    private static Logger logger = LoggerFactory.getLogger(AuxQjsqService.class);
    private static IAuxQjsqDAO auxQjsqDAO = (IAuxQjsqDAO) DAOFactory.createDAO("IAuxQjsqDAO");


    @Override
    public List<AuxQjsq> search(AuxQjsqSearchCriteria criteria) {
        List<AuxQjsq> auxQjsqList = new ArrayList<AuxQjsq>();
        if (criteria == null) {
            return auxQjsqList;
        }
        return auxQjsqDAO.search(criteria);
    }

    @Override
    public long getSearchResultCount(AuxQjsqSearchCriteria criteria) {
        if (criteria == null) {
            return 0;
        }
        String count = "0";
        count = Long.toString(auxQjsqDAO.getSearchResultCount(criteria));

        return Long.parseLong(count);
    }

    @Override
    public List<AuxQjsq> searchQjsh(AuxQjsqSearchCriteria criteria) {
        List<AuxQjsq> auxQjsqList = new ArrayList<AuxQjsq>();
        if (criteria == null) {
            return auxQjsqList;
        }
        return auxQjsqDAO.searchQjsh(criteria);
    }

    @Override
    public AuxQjsq add(AuxQjsq auxQjsq) throws ServiceException {
        try {
            auxQjsq.setCreateTime(Common.getCurrentTime());
            auxQjsq.setQjsqId(IdCreator.createId("qjsqId"));
            auxQjsqDAO.add(auxQjsq);
            return auxQjsq;
        } catch (ServiceException e) {
            throw new ServiceException(ErrCode.UNKNOWN_ERROR, e.getErrMsg());
        }
    }

    @Override
    public void delete(Long qjsqId) {
        auxQjsqDAO.delete(qjsqId);
    }

    @Override
    public void update(AuxQjsq auxQjsq) {
        auxQjsqDAO.update(auxQjsq);
    }

    @Override
    public AuxQjsq get(Long qjsqId) {
        return auxQjsqDAO.findById(qjsqId);
    }

    @Override
    public List<AuxQjsq> findByExample(AuxQjsq auxQjsq) {
        return auxQjsqDAO.findByExample(auxQjsq);
    }

    @Override
    public List<AuxQjsq> findAll() {
        return auxQjsqDAO.findAll();
    }
}
