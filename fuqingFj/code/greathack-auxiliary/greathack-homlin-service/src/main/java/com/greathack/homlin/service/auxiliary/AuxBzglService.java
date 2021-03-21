package com.greathack.homlin.service.auxiliary;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxBzglDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.auxiliary.AuxBzgl;
import com.greathack.homlin.pojo.auxiliary.AuxBzglSearchCriteria;
import com.greathack.homlin.pojo.auxiliary.AuxJygl;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxBzglService;
import com.greathack.homlin.system.IdCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AuxBzglService implements IAuxBzglService {

    private static Logger logger = LoggerFactory.getLogger(AuxBzglService.class);
    private static IAuxBzglDAO auxBzglDAO = (IAuxBzglDAO) DAOFactory.createDAO("IAuxBzglDAO");


    @Override
    public AuxBzgl getById(Long bzglId) {
        return auxBzglDAO.findById(bzglId);
    }

    @Override
    public AuxBzgl getByProcessId(long processId) {
        AuxBzgl cond = new AuxBzgl();
        cond.setProcessId(processId);
        List<AuxBzgl> auxBzglList = findByExample(cond);
        if (auxBzglList.size() > 0) {
            return auxBzglList.get(0);
        }
        return null;
    }

    @Override
    public List<AuxBzgl> search(AuxBzglSearchCriteria criteria) {
        List<AuxBzgl> auxBzglList = new ArrayList<AuxBzgl>();
        if (criteria == null) {
            return auxBzglList;
        }
        return auxBzglDAO.search(criteria);
    }

    @Override
    public long getSearchResultCount(AuxBzglSearchCriteria criteria) {
        if (criteria == null) {
            return 0;
        }
        String count = "0";
        count = Long.toString(auxBzglDAO.getSearchResultCount(criteria));

        return Long.parseLong(count);
    }

    @Override
    public List<AuxBzgl> searchDcl(AuxBzglSearchCriteria criteria) {
        List<AuxBzgl> auxBzglList = new ArrayList<AuxBzgl>();
        if (criteria == null) {
            return auxBzglList;
        }
        return auxBzglDAO.searchDcl(criteria);
    }

    @Override
    public List<AuxBzgl> searchYcl(AuxBzglSearchCriteria criteria) {
        List<AuxBzgl> auxBzglList = new ArrayList<AuxBzgl>();
        if (criteria == null) {
            return auxBzglList;
        }
        return auxBzglDAO.searchYcl(criteria);
    }

    @Override
    public AuxBzgl add(AuxBzgl auxBzgl) throws ServiceException {
        try {
            auxBzgl.setCreateTime(Common.getCurrentTime());
            auxBzgl.setBzglId(IdCreator.createId("bzglId"));
            auxBzglDAO.add(auxBzgl);
            return auxBzgl;
        } catch (ServiceException e) {
            throw new ServiceException(ErrCode.UNKNOWN_ERROR, e.getErrMsg());
        }
    }

    @Override
    public void delete(Long bzglId) {
        auxBzglDAO.delete(bzglId);
    }

    @Override
    public void update(AuxBzgl auxBzgl) {
        auxBzglDAO.update(auxBzgl);
    }

    @Override
    public AuxBzgl get(Long bzglId) {
        return auxBzglDAO.findById(bzglId);
    }

    @Override
    public List<AuxBzgl> findByExample(AuxBzgl auxBzgl) {
        return auxBzglDAO.findByExample(auxBzgl);
    }

    @Override
    public List<AuxBzgl> findAll() {
        return auxBzglDAO.findAll();
    }
}
