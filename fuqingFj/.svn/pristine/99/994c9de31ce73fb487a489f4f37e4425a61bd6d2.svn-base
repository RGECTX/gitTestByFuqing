package com.greathack.homlin.service.auxiliary;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxKqtjDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.auxiliary.AuxAttendancdSearchCriteria;
import com.greathack.homlin.pojo.auxiliary.AuxAttendance;
import com.greathack.homlin.pojo.auxiliary.AuxKqtj;
import com.greathack.homlin.pojo.auxiliary.AuxKqtjSearchCriteria;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxKqtjService;
import com.greathack.homlin.system.IdCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AuxKqtjService implements IAuxKqtjService {

    private static Logger logger = LoggerFactory.getLogger(AuxKqtjService.class);
    private static IAuxKqtjDAO auxKqtjDAO = (IAuxKqtjDAO) DAOFactory.createDAO("IAuxKqtjDAO");


    @Override
    public List<AuxKqtj> search(AuxKqtjSearchCriteria criteria) {
        List<AuxKqtj> auxKqtjList = new ArrayList<AuxKqtj>();
        if (criteria == null) {
            return auxKqtjList;
        }
        return auxKqtjDAO.search(criteria);
    }

    @Override
    public long getSearchResultCount(AuxKqtjSearchCriteria criteria) {
        if (criteria == null) {
            return 0;
        }
        String count = "0";
        count = Long.toString(auxKqtjDAO.getSearchResultCount(criteria));

        return Long.parseLong(count);
    }

    /*@Override
    public long getSearchResultCounts(AuxAttendance criteria) {
        if (criteria == null) {
            return 0;
        }
        String count = "0";
        count = Long.toString(auxKqtjDAO.getSearchResultCounts(criteria));

        return Long.parseLong(count);
    }*/

    @Override
    public List<AuxAttendancdSearchCriteria> getFjqktj(AuxAttendancdSearchCriteria criteria) {
        List<AuxAttendancdSearchCriteria> auxAttendanceList = new ArrayList<AuxAttendancdSearchCriteria>();
        if (criteria == null) {
            return auxAttendanceList;
        }
        return auxKqtjDAO.getFjqktj(criteria);
    }

    @Override
    public long getSearchResultCounts(AuxAttendancdSearchCriteria criteria) {
        if (criteria == null) {
            return 0;
        }
        String count = "0";
        count = Long.toString(auxKqtjDAO.getSearchResultCounts(criteria));

        return Long.parseLong(count);
    }


    /*@Override
    public List<AuxAttendance> getFjqktj(AuxAttendance criteria) {
        List<AuxAttendance> auxAttendanceList = new ArrayList<AuxAttendance>();
        if (criteria == null) {
            return auxAttendanceList;
        }
        return auxKqtjDAO.getFjqktj(criteria);
    }*/

    @Override
    public AuxKqtj add(AuxKqtj auxKqtj) throws ServiceException {
        try {
            auxKqtj.setCreateTime(Common.getCurrentTime());
            auxKqtj.setKqtjId(IdCreator.createId("kqtjId"));
            auxKqtjDAO.add(auxKqtj);
            return auxKqtj;
        } catch (ServiceException e) {
            throw new ServiceException(ErrCode.UNKNOWN_ERROR, e.getErrMsg());
        }
    }

    @Override
    public void delete(Long kqtjId) {
        auxKqtjDAO.delete(kqtjId);
    }

    @Override
    public void update(AuxKqtj auxKqtj) {
        auxKqtjDAO.update(auxKqtj);
    }

    @Override
    public AuxKqtj get(Long kqtjId) {
        return auxKqtjDAO.findById(kqtjId);
    }

    @Override
    public List<AuxKqtj> findByExample(AuxKqtj auxKqtj) {
        return auxKqtjDAO.findByExample(auxKqtj);
    }

    @Override
    public List<AuxKqtj> findAll() {
        return auxKqtjDAO.findAll();
    }
}
