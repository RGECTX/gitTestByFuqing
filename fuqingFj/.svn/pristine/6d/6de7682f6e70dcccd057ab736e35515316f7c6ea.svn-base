package com.greathack.homlin.service.auxiliary;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxNdkhDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.auxiliary.AuxNdkh;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxNdkhService;
import com.greathack.homlin.system.IdCreator;

import java.util.List;

public class AuxNdkhService implements IAuxNdkhService {

    private static IAuxNdkhDAO auxNdkhDAO = (IAuxNdkhDAO) DAOFactory.createDAO("IAuxNdkhDAO");


    @Override
    public AuxNdkh add(AuxNdkh auxNdkh) throws ServiceException {
        if (auxNdkh.getInstanceId() == null) {
            throw new ServiceException(ErrCode.INSTANCE_ID_IS_REQUIRE, "INSTANCE_ID_IS_REQUIRE");
        }
        try {
            auxNdkh.setCreateTime(Common.getCurrentTime());
            auxNdkh.setId(IdCreator.createId("auxNdkh"));
            auxNdkhDAO.add(auxNdkh);
            return auxNdkh;
        } catch (ServiceException e) {
            throw new ServiceException(ErrCode.UNKNOWN_ERROR, "UNKNOWN_ERROR");
        }
    }

    @Override
    public void delete(Long id) {
        auxNdkhDAO.delete(id);
    }

    @Override
    public void update(AuxNdkh auxNdkh) {
        auxNdkhDAO.update(auxNdkh);
    }

    @Override
    public AuxNdkh get(Long id) {
        return auxNdkhDAO.findById(id);
    }

    @Override
    public List<AuxNdkh> findByExample(AuxNdkh auxNdkh) {
        return auxNdkhDAO.findByExample(auxNdkh);
    }

    @Override
    public List<AuxNdkh> findAll() {
        return auxNdkhDAO.findAll();
    }
}
