package com.greathack.homlin.service.auxiliary;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxDaglDAO;
import com.greathack.homlin.daointerface.auxiliary.IAuxGzglDAO;
import com.greathack.homlin.daointerface.auxiliary.IAuxPayDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.auxiliary.AuxPay;
import com.greathack.homlin.pojo.auxiliary.AuxPaySearchCriteria;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxPayService;
import com.greathack.homlin.system.IdCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AuxPayService implements IAuxPayService {

    private static Logger logger = LoggerFactory.getLogger(AuxPayService.class);
    private static IAuxPayDAO auxPayDAO = (IAuxPayDAO) DAOFactory.createDAO("IAuxPayDAO");
    private static IAuxGzglDAO auxGzglDAO = (IAuxGzglDAO) DAOFactory.createDAO("IAuxGzglDAO");
    private static IAuxDaglDAO auxDaglDAO = (IAuxDaglDAO) DAOFactory.createDAO("IAuxDaglDAO");


    @Override
    public List<AuxPay> search(AuxPaySearchCriteria criteria) {
        List<AuxPay> auxPayList = new ArrayList<AuxPay>();
        if (criteria == null) {
            return auxPayList;
        }
        return auxPayDAO.search(criteria);
    }

    @Override
    public long getSearchResultCount(AuxPaySearchCriteria criteria) {
        if (criteria == null) {
            return 0;
        }
        String count = "0";
        count = Long.toString(auxPayDAO.getSearchResultCount(criteria));

        return Long.parseLong(count);
    }

    @Override
    public AuxPay add(AuxPay auxPay) throws ServiceException {
        try {
            auxPay.setCreateTime(Common.getCurrentTime());
            auxPay.setId(IdCreator.createId("id"));
            auxPayDAO.add(auxPay);
            return auxPay;
        } catch (ServiceException e) {
            throw new ServiceException(ErrCode.UNKNOWN_ERROR, e.getErrMsg());
        }
    }

    @Override
    public void delete(Long id) {
        auxPayDAO.delete(id);
    }

    @Override
    public void update(AuxPay auxPay) {
        auxPayDAO.update(auxPay);

    }

    @Override
    public AuxPay get(Long id) {
        return auxPayDAO.findById(id);
    }

    @Override
    public List<AuxPay> findByExample(AuxPay auxPay) {
        return auxPayDAO.findByExample(auxPay);
    }

    @Override
    public List<AuxPay> findAll() {
        return auxPayDAO.findAll();
    }
}
