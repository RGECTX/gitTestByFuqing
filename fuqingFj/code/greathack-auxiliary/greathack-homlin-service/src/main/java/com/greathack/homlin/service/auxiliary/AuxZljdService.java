package com.greathack.homlin.service.auxiliary;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxDaglDAO;
import com.greathack.homlin.daointerface.auxiliary.IAuxZljdDAO;
import com.greathack.homlin.daointerface.org.IOrganizationDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.auxiliary.AuxDagl;
import com.greathack.homlin.pojo.auxiliary.AuxZljd;
import com.greathack.homlin.pojo.auxiliary.AuxZljdSearchCriteria;
import com.greathack.homlin.pojo.org.Organization;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxZljdService;
import com.greathack.homlin.serviceinterface.org.IOrganizationService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.utils.IdCardNumberVerify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class AuxZljdService implements IAuxZljdService {

    private static Logger logger = LoggerFactory.getLogger(AuxZljdService.class);
    private static IAuxZljdDAO auxZljdDAO = (IAuxZljdDAO) DAOFactory.createDAO("IAuxZljdDAO");
    private static IAuxDaglDAO auxDaglDAO = (IAuxDaglDAO) DAOFactory.createDAO("IAuxDaglDAO");
    private static IOrganizationDAO organizationDAO = (IOrganizationDAO) DAOFactory.createDAO("IOrganizationDAO");

    @Autowired
    private AuxDaglService auxDaglService;
    @Autowired
    private IOrganizationService organizationService;


    @Override
    public List<AuxZljd> search(AuxZljdSearchCriteria criteria) {
        List<AuxZljd> auxZljdList = new ArrayList<AuxZljd>();
        if (criteria == null) {
            return auxZljdList;
        }
        return auxZljdDAO.search(criteria);
    }

    @Override
    public long getSearchResultCount(AuxZljdSearchCriteria criteria) {
        if (criteria == null) {
            return 0;
        }
        String count = "0";
        count = Long.toString(auxZljdDAO.getSearchResultCount(criteria));

        return Long.parseLong(count);
    }

    @Override
    public void zlAdd(Long zlId, Long userId) throws ServiceException {
        AuxZljd auxZljd = auxZljdDAO.findById(zlId);
        //??????????????????????????????ID?????????????????????????????????
        List<Organization> org = organizationDAO.findOrgName(Long.valueOf(auxZljd.getReceiveOrgId()));//???????????????????????????
        if(org.size()!=0){
            auxZljd.setOrgName(org.get(0).getOrgName());
        }

        auxZljd.setState("1");
        //????????????
        int sexByCertId = IdCardNumberVerify.getSexByCertId(auxZljd.getIdcard());

        AuxDagl auxDagl = new AuxDagl();
        auxDagl.setDaId(IdCreator.createId("daId"));
        auxDagl.setXm(auxZljd.getXm());
        auxDagl.setIdcard(auxZljd.getIdcard());
        auxDagl.setCreateBy(userId);
        auxDagl.setCreateTime(Common.getCurrentTime());
        auxDagl.setState(auxZljd.getState());
        auxDagl.setXb(String.valueOf(sexByCertId));
        auxDaglService.addZl(auxDagl);

    }

    @Override
    public AuxZljd getById(Long zlId) {
        return auxZljdDAO.findById(zlId);
    }

    @Override
    public AuxZljd getByProcessId(long processId) {
        AuxZljd cond = new AuxZljd();
        cond.setProcessId(processId);
        List<AuxZljd> auxZljdList = findByExample(cond);
        if (auxZljdList.size() > 0) {
            return auxZljdList.get(0);
        }
        return null;
    }

    //?????????????????????????????????
    @Override
    public void zlAddById(Long zlId) throws ServiceException {
        AuxZljd auxZljd = auxZljdDAO.findById(zlId);

        //??????????????????????????????ID?????????????????????????????????
        List<Organization> org = organizationDAO.findOrgName(Long.valueOf(auxZljd.getReceiveOrgId()));//???????????????????????????
        if(org.size()!=0){
            auxZljd.setOrgName(org.get(0).getOrgName());
        }

        auxZljd.setState("1");
        //????????????
        int sexByCertId = IdCardNumberVerify.getSexByCertId(auxZljd.getIdcard());

        AuxDagl auxDagl = new AuxDagl();
        auxDagl.setDaId(IdCreator.createId("daId"));
        auxDagl.setXm(auxZljd.getXm());
        auxDagl.setIdcard(auxZljd.getIdcard());
        auxDagl.setLxfs("");
        /*auxDagl.setOrgId(Long.parseLong(auxZljd.getReceiveOrgId()));
        auxDagl.setOrgName(auxZljd.getOrgName());*/
/*        auxDagl.setCreateBy(userId);*/

        //??????
        auxDagl.setFjType(auxZljd.getFjType());
        auxDagl.setOrgId(Long.parseLong(auxZljd.getReceiveOrgId()));


        auxDagl.setCreateTime(Common.getCurrentTime());
        auxDagl.setState(auxZljd.getState());
        auxDagl.setXb(String.valueOf(sexByCertId));
        auxDagl.setRemark(auxZljd.getRemark());
        auxDagl.setOrgName(auxZljd.getOrgName());
        auxDaglDAO.add(auxDagl);
    }

    @Override
    public List<AuxZljd> searchAprove(AuxZljdSearchCriteria criteria) {
        List<AuxZljd> auxZljdList = new ArrayList<AuxZljd>();
        if (criteria == null) {
            return auxZljdList;
        }
        return auxZljdDAO.searchAprove(criteria);
    }

    @Override
    public List<AuxZljd> findIdcard(String idcard) {
        return auxZljdDAO.findIdcard(idcard);
    }

    @Override
    public void updateFjlb(AuxZljd auxZljd) {
        auxZljdDAO.updateFjlb(auxZljd);
    }

    @Override
    public void updateDwjs(AuxZljd auxZljd) {
        auxZljdDAO.updateDwjs(auxZljd);
    }

    @Override
    public List<AuxZljd> searchAproveOrg(AuxZljdSearchCriteria criteria) {
        List<AuxZljd> auxZljdList = new ArrayList<AuxZljd>();
        if (criteria == null) {
            return auxZljdList;
        }
        return auxZljdDAO.searchAproveOrg(criteria);
    }

    @Override
    public List<AuxZljd> searchDcl(AuxZljdSearchCriteria criteria) {
        List<AuxZljd> auxZljdList = new ArrayList<AuxZljd>();
        if (criteria == null) {
            return auxZljdList;
        }
        return auxZljdDAO.searchDcl(criteria);
    }

    @Override
    public List<AuxZljd> searchYcl(AuxZljdSearchCriteria criteria) {
        List<AuxZljd> auxZljdList = new ArrayList<AuxZljd>();
        if (criteria == null) {
            return auxZljdList;
        }
        return auxZljdDAO.searchYcl(criteria);
    }

    @Override
    public void updateWork(AuxZljd auxZljd) {
        auxZljdDAO.updateWork(auxZljd);
    }

    @Override
    public void updateNewWork(AuxZljd auxZljd) {
        auxZljdDAO.updateNewWork(auxZljd);
    }

    @Override
    public void updateRystate(AuxZljd auxZljd) {
        auxZljdDAO.updateRystate(auxZljd);
    }


    @Override
    public AuxZljd add(AuxZljd auxZljd) throws ServiceException {
        try {
            auxZljd.setCreateTime(Common.getCurrentTime());
            auxZljd.setZlId(IdCreator.createId("zlId"));
            auxZljdDAO.add(auxZljd);
            return auxZljd;
        } catch (ServiceException e) {
            throw new ServiceException(ErrCode.UNKNOWN_ERROR, e.getErrMsg());
        }
    }

    @Override
    public void delete(Long auxZljdId) {
        auxZljdDAO.delete(auxZljdId);
    }

    @Override
    public void update(AuxZljd auxZljd) {
        auxZljdDAO.update(auxZljd);
    }

    @Override
    public AuxZljd get(Long auxZljd) {
        return auxZljdDAO.findById(auxZljd);
    }

    @Override
    public List<AuxZljd> findByExample(AuxZljd auxZljd) {
        return auxZljdDAO.findByExample(auxZljd);
    }

    @Override
    public List<AuxZljd> findAll() {
        return auxZljdDAO.findAll();
    }
}
