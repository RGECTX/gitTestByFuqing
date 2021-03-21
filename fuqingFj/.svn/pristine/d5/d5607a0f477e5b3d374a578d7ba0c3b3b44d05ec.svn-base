package com.greathack.homlin.service.auxiliary;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxDwkqDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.auxiliary.AuxDwkq;
import com.greathack.homlin.pojo.auxiliary.AuxDwkqSearchCriteria;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxDwkqService;
import com.greathack.homlin.system.IdCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AuxDwkqService implements IAuxDwkqService {

    private static Logger logger = LoggerFactory.getLogger(AuxDwkqService.class);
    private static IAuxDwkqDAO auxDwkqDAO = (IAuxDwkqDAO) DAOFactory.createDAO("IAuxDwkqDAO");

    @Override
    public List<AuxDwkq> search(AuxDwkqSearchCriteria criteria) {
        List<AuxDwkq> auxDwkqList = new ArrayList<AuxDwkq>();
        if (criteria == null) {
            return auxDwkqList;
        }
        return auxDwkqDAO.search(criteria);
    }

    @Override
    public long getSearchResultCount(AuxDwkqSearchCriteria criteria) {
        if (criteria == null) {
            return 0;
        }
        String count = "0";
        count = Long.toString(auxDwkqDAO.getSearchResultCount(criteria));

        return Long.parseLong(count);
    }

    @Override
    public AuxDwkq add(AuxDwkq auxDwkq) throws ServiceException {
        try {
            auxDwkq.setCreateTime(Common.getCurrentTime());
            auxDwkq.setDwkqId(IdCreator.createId("dwkqId"));
            auxDwkqDAO.add(auxDwkq);
            return auxDwkq;
        } catch (ServiceException e) {
            throw new ServiceException(ErrCode.UNKNOWN_ERROR, e.getErrMsg());
        }
    }

    @Override
    public void delete(Long id) {
        auxDwkqDAO.delete(id);
    }

    @Override
    public void update(AuxDwkq auxDwkq) {
        auxDwkqDAO.update(auxDwkq);
    }

    @Override
    public AuxDwkq get(Long id) {
        return auxDwkqDAO.findById(id);
    }

    @Override
    public List<AuxDwkq> findByExample(AuxDwkq auxDwkq) {
        return auxDwkqDAO.findByExample(auxDwkq);
    }

    @Override
    public List<AuxDwkq> findAll() {
        return auxDwkqDAO.findAll();
    }
}
