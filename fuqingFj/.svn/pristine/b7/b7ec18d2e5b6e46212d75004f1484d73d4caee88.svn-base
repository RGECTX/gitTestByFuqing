package com.greathack.homlin.service.auxiliary;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxGsglDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.auxiliary.AuxGsgl;
import com.greathack.homlin.pojo.auxiliary.AuxGsglSearchCriteria;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxGsglService;
import com.greathack.homlin.system.IdCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AuxGsglService implements IAuxGsglService {

    private static Logger logger = LoggerFactory.getLogger(AuxGsglService.class);
    private static IAuxGsglDAO auxGsglDAO = (IAuxGsglDAO) DAOFactory.createDAO("IAuxGsglDAO");

    @Override
    public List<AuxGsgl> search(AuxGsglSearchCriteria criteria) {
        List<AuxGsgl> auxGsglList = new ArrayList<AuxGsgl>();
        if (criteria == null) {
            return auxGsglList;
        }
        return auxGsglDAO.search(criteria);
    }

    @Override
    public long getSearchResultCount(AuxGsglSearchCriteria criteria) {
        if (criteria == null) {
            return 0;
        }
        String count = "0";
        count = Long.toString(auxGsglDAO.getSearchResultCount(criteria));

        return Long.parseLong(count);
    }

    @Override
    public List<AuxGsgl> searchGssh(AuxGsglSearchCriteria criteria) {
        List<AuxGsgl> auxGsglList = new ArrayList<AuxGsgl>();
        if (criteria == null) {
            return auxGsglList;
        }
        return auxGsglDAO.searchGssh(criteria);
    }

    @Override
    public AuxGsgl getById(Long gsglId) {
        return auxGsglDAO.findById(gsglId);
    }

    @Override
    public AuxGsgl add(AuxGsgl auxGsgl) throws ServiceException {
        try {
            auxGsgl.setCreateTime(Common.getCurrentTime());
            auxGsgl.setGsglId(IdCreator.createId("gsglId"));
            auxGsglDAO.add(auxGsgl);
            return auxGsgl;
        } catch (ServiceException e) {
            throw new ServiceException(ErrCode.UNKNOWN_ERROR, e.getErrMsg());
        }
    }

    @Override
    public void delete(Long gsglId) {
        auxGsglDAO.delete(gsglId);
    }

    @Override
    public void update(AuxGsgl auxGsgl) {
        auxGsglDAO.update(auxGsgl);
    }

    @Override
    public AuxGsgl get(Long gsglId) {
        return auxGsglDAO.findById(gsglId);
    }

    @Override
    public List<AuxGsgl> findByExample(AuxGsgl auxGsgl) {
        return auxGsglDAO.findByExample(auxGsgl);
    }

    @Override
    public List<AuxGsgl> findAll() {
        return auxGsglDAO.findAll();
    }
}
