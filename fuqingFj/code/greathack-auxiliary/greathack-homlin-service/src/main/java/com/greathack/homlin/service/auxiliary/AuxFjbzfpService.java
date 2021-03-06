
package com.greathack.homlin.service.auxiliary;

import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxFjbzfpDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.auxiliary.AuxFjbzfp;
import com.greathack.homlin.pojo.auxiliary.AuxFjbzfpSearchCriteria;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxFjbzfpService;
import com.greathack.homlin.system.IdCreator;

import java.util.ArrayList;
import java.util.List;

public class AuxFjbzfpService implements IAuxFjbzfpService {
    private static IAuxFjbzfpDAO auxFjbzfpDAO = (IAuxFjbzfpDAO) DAOFactory.createDAO("IAuxFjbzfpDAO");



    @Override
    public AuxFjbzfp add(AuxFjbzfp auxFjbzfp) throws ServiceException {
        try{
            auxFjbzfp.setFjbzfpId(IdCreator.createId("fjbzfpId"));
            auxFjbzfpDAO.add(auxFjbzfp);
            return auxFjbzfp;
        }catch (ServiceException e){
            throw new ServiceException(ErrCode.UNKNOWN_ERROR,e.getErrMsg());
        }

    }

    @Override
    public void delete(Long fjbzfpId) {
        auxFjbzfpDAO.delete(fjbzfpId);
    }

    @Override
    public void update(AuxFjbzfp auxFjbzfp) {
        auxFjbzfpDAO.update(auxFjbzfp);
    }

    @Override
    public AuxFjbzfp get(Long fjbzfpId) {
        return auxFjbzfpDAO.findById(fjbzfpId);
    }

    @Override
    public List<AuxFjbzfp> findByExample(AuxFjbzfp instance) {

        return auxFjbzfpDAO.findByExample(instance);
    }

    @Override
    public List<AuxFjbzfp> findAll() {
        return auxFjbzfpDAO.findAll();
    }

    @Override
    public List<AuxFjbzfp> search(AuxFjbzfpSearchCriteria auxFjbzfpSearchCriteria) {
        List<AuxFjbzfp> fjbzfpList = new ArrayList<>();
        if (auxFjbzfpSearchCriteria==null){
            return fjbzfpList;
        }
        return auxFjbzfpDAO.search(auxFjbzfpSearchCriteria);
    }

    @Override
    public long getSearchResultCount(AuxFjbzfpSearchCriteria auxFjbzfpSearchCriteria) {
        if (auxFjbzfpSearchCriteria==null){
            return 0;
        }
        System.out.println(auxFjbzfpSearchCriteria.toString());
        String count="0";
        count=Long.toString(auxFjbzfpDAO.getSearchResultCount(auxFjbzfpSearchCriteria));

        return Long.parseLong(count);
    }

    @Override
    public List<AuxFjbzfp> findFjType1(AuxFjbzfpSearchCriteria auxFjbzfpSearchCriteria) {
        List<AuxFjbzfp> fjbzfpList = new ArrayList<>();
        if (auxFjbzfpSearchCriteria==null){
            return fjbzfpList;
        }
        return auxFjbzfpDAO.findFjType1(auxFjbzfpSearchCriteria);
    }

    @Override
    public List<AuxFjbzfp> findFjType2(AuxFjbzfpSearchCriteria auxFjbzfpSearchCriteria) {
        List<AuxFjbzfp> fjbzfpList = new ArrayList<>();
        if (auxFjbzfpSearchCriteria==null){
            return fjbzfpList;
        }
        return auxFjbzfpDAO.findFjType2(auxFjbzfpSearchCriteria);
    }

    @Override
    public List<AuxFjbzfp> findFjType4(AuxFjbzfpSearchCriteria auxFjbzfpSearchCriteria) {
        List<AuxFjbzfp> fjbzfpList = new ArrayList<>();
        if (auxFjbzfpSearchCriteria==null){
            return fjbzfpList;
        }
        return auxFjbzfpDAO.findFjType4(auxFjbzfpSearchCriteria);
    }

    /*@Override
    public List<AuxFjbzfp> findFjType2(String fjType) {
        return auxFjbzfpDAO.findFjType2(fjType);
    }

    @Override
    public List<AuxFjbzfp> findFjType4(String fjType) {
        return auxFjbzfpDAO.findFjType4(fjType);
    }*/


}
