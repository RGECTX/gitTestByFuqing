package com.greathack.homlin.service.auxiliary;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxHmdDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.auxiliary.AuxHmd;
import com.greathack.homlin.pojo.auxiliary.AuxHmdSearchCriteria;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxHmdService;
import com.greathack.homlin.system.IdCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AuxHmdService implements IAuxHmdService {

    private static Logger logger = LoggerFactory.getLogger(AuxHmdService.class);
    private static IAuxHmdDAO auxHmdDAO = (IAuxHmdDAO) DAOFactory.createDAO("IAuxHmdDAO");

    @Override
    public List<AuxHmd> search(AuxHmdSearchCriteria criteria) {
        List<AuxHmd> auxHmdList = new ArrayList<AuxHmd>();
        if (criteria == null) {
            return auxHmdList;
        }
        return auxHmdDAO.search(criteria);
    }

    @Override
    public long getSearchResultCount(AuxHmdSearchCriteria criteria) {
        if (criteria == null) {
            return 0;
        }
        String count = "0";
        count = Long.toString(auxHmdDAO.getSearchResultCount(criteria));

        return Long.parseLong(count);
    }

    @Override
    public AuxHmd add(AuxHmd auxHmd) throws ServiceException {
        try {
            auxHmd.setCreateTime(Common.getCurrentTime());
            auxHmd.setHmdId(IdCreator.createId("AuxHmd"));
            auxHmdDAO.add(auxHmd);
            return auxHmd;
        } catch (ServiceException e) {
            throw new ServiceException(ErrCode.UNKNOWN_ERROR, e.getErrMsg());
        }
    }

    @Override
    public void delete(Long auxHmdId) {
        auxHmdDAO.delete(auxHmdId);

    }

    @Override
    public void update(AuxHmd auxHmd) {
        auxHmdDAO.update(auxHmd);

    }

    @Override
    public AuxHmd get(Long auxHmdId) {
        return auxHmdDAO.findById(auxHmdId);
    }

    @Override
    public List<AuxHmd> findByExample(AuxHmd auxHmd) {
        return auxHmdDAO.findByExample(auxHmd);
    }

    @Override
    public List<AuxHmd> findAll() {
        return auxHmdDAO.findAll();
    }
}
