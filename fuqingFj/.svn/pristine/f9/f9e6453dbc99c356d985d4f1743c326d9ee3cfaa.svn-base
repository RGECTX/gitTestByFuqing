package com.greathack.homlin.service.auxiliary;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxJyglDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.auxiliary.AuxDagl;
import com.greathack.homlin.pojo.auxiliary.AuxJygl;
import com.greathack.homlin.pojo.auxiliary.AuxJyglSearchCriteria;
import com.greathack.homlin.pojo.auxiliary.AuxZljd;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxJyglService;
import com.greathack.homlin.system.IdCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AuxJyglService implements IAuxJyglService {

    private static Logger logger = LoggerFactory.getLogger(AuxJyglService.class);
    private static IAuxJyglDAO auxJyglDAO = (IAuxJyglDAO) DAOFactory.createDAO("IAuxJyglDAO");

    @Override
    public List<AuxJygl> search(AuxJyglSearchCriteria criteria) {
        List<AuxJygl> auxJyglList = new ArrayList<AuxJygl>();
        if (criteria == null) {
            return auxJyglList;
        }
        return auxJyglDAO.search(criteria);
    }

    @Override
    public long getSearchResultCount(AuxJyglSearchCriteria criteria) {
        if (criteria == null) {
            return 0;
        }
        String count = "0";
        count = Long.toString(auxJyglDAO.getSearchResultCount(criteria));

        return Long.parseLong(count);
    }

    @Override
    public AuxJygl getById(Long jyId) {
        return auxJyglDAO.findById(jyId);
    }

    @Override
    public AuxJygl getByProcessId(long processId) {
        AuxJygl cond = new AuxJygl();
        cond.setProcessId(processId);
        List<AuxJygl> auxJyglList = findByExample(cond);
        if (auxJyglList.size() > 0) {
            return auxJyglList.get(0);
        }
        return null;
    }

    @Override
    public List<AuxJygl> searchDcl(AuxJyglSearchCriteria criteria) {
        List<AuxJygl> auxJyglList = new ArrayList<AuxJygl>();
        if (criteria == null) {
            return auxJyglList;
        }
        return auxJyglDAO.searchDcl(criteria);
    }

    @Override
    public List<AuxJygl> searchYcl(AuxJyglSearchCriteria criteria) {
        List<AuxJygl> auxJyglList = new ArrayList<AuxJygl>();
        if (criteria == null) {
            return auxJyglList;
        }
        return auxJyglDAO.searchYcl(criteria);
    }

    @Override
    public AuxJygl add(AuxJygl auxJygl) throws ServiceException {
        try {
            auxJygl.setCreateTime(Common.getCurrentTime());
            auxJygl.setJyId(IdCreator.createId("jyId"));
            auxJyglDAO.add(auxJygl);
            return auxJygl;
        } catch (ServiceException e) {
            throw new ServiceException(ErrCode.UNKNOWN_ERROR, e.getErrMsg());
        }
    }

    @Override
    public void delete(Long auxJyglId) {
        auxJyglDAO.delete(auxJyglId);
    }

    @Override
    public void update(AuxJygl auxJygl) {
        auxJyglDAO.update(auxJygl);
    }

    @Override
    public AuxJygl get(Long auxJyglId) {
        return auxJyglDAO.findById(auxJyglId);
    }

    @Override
    public List<AuxJygl> findByExample(AuxJygl auxJygl) {
        return auxJyglDAO.findByExample(auxJygl);
    }

    @Override
    public List<AuxJygl> findAll() {
        return auxJyglDAO.findAll();
    }

}
