package com.greathack.homlin.service.auxiliary;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxGzglDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.auxiliary.AuxAttendancdSearchCriteria;
import com.greathack.homlin.pojo.auxiliary.AuxGzgl;
import com.greathack.homlin.pojo.auxiliary.AuxGzglSearchCriteria;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxGzglService;
import com.greathack.homlin.system.IdCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AuxGzglService implements IAuxGzglService {

    private static Logger logger = LoggerFactory.getLogger(AuxGzglService.class);
    private static IAuxGzglDAO auxGzglDAO = (IAuxGzglDAO) DAOFactory.createDAO("IAuxGzglDAO");

    @Override
    public List<AuxGzgl> search(AuxGzglSearchCriteria criteria) {
        List<AuxGzgl> auxGzglList = new ArrayList<AuxGzgl>();
        if (criteria == null) {
            return auxGzglList;
        }
        return auxGzglDAO.search(criteria);
    }

    @Override
    public long getSearchResultCount(AuxGzglSearchCriteria criteria) {
        if (criteria == null) {
            return 0;
        }
        String count = "0";
        count = Long.toString(auxGzglDAO.getSearchResultCount(criteria));

        return Long.parseLong(count);
    }

    @Override
    public List<AuxGzgl> searchGzglSp(AuxGzglSearchCriteria criteria) {
        List<AuxGzgl> auxGzglList = new ArrayList<AuxGzgl>();
        if (criteria == null) {
            return auxGzglList;
        }
        return auxGzglDAO.searchGzglSp(criteria);
    }

    @Override
    public List<AuxGzgl> searchGzmx(AuxGzglSearchCriteria criteria) {
        List<AuxGzgl> auxGzglList = new ArrayList<AuxGzgl>();
        if (criteria == null) {
            return auxGzglList;
        }
        return auxGzglDAO.searchGzmx(criteria);
    }

    @Override
    public List<AuxGzglSearchCriteria> getFjqktj(AuxGzglSearchCriteria criteria) {
        List<AuxGzglSearchCriteria> auxGzglSearchCriteriaList = new ArrayList<AuxGzglSearchCriteria>();
        if (criteria == null) {
            return auxGzglSearchCriteriaList;
        }
        return auxGzglDAO.getFjqktj(criteria);
    }

    @Override
    public AuxGzgl add(AuxGzgl auxGzgl) throws ServiceException {
        try {
            auxGzgl.setCreateTime(Common.getCurrentTime());
            auxGzgl.setGzglId(IdCreator.createId("gzglId"));
            auxGzglDAO.add(auxGzgl);
            return auxGzgl;
        } catch (ServiceException e) {
            throw new ServiceException(ErrCode.UNKNOWN_ERROR, e.getErrMsg());
        }
    }

    @Override
    public void delete(Long gzglId) {
        auxGzglDAO.delete(gzglId);
    }

    @Override
    public void update(AuxGzgl auxGzgl) {
        auxGzglDAO.update(auxGzgl);
    }

    @Override
    public AuxGzgl get(Long gzglId) {
        return auxGzglDAO.findById(gzglId);
    }

    @Override
    public List<AuxGzgl> findByExample(AuxGzgl auxGzgl) {
        return auxGzglDAO.findByExample(auxGzgl);
    }

    @Override
    public List<AuxGzgl> findAll() {
        return auxGzglDAO.findAll();
    }
}
