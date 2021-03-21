package com.greathack.homlin.service.auxiliary;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxYdkhDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.auxiliary.AuxYdkh;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxYdkhService;
import com.greathack.homlin.system.IdCreator;

import java.util.List;

public class AuxYdkhService implements IAuxYdkhService {

    private static IAuxYdkhDAO auxYdkhDAO = (IAuxYdkhDAO) DAOFactory.createDAO("IAuxYdkhDAO");

    @Override
    public AuxYdkh add(AuxYdkh auxYdkh) throws ServiceException {
        if (auxYdkh.getInstanceId() == null) {
            throw new ServiceException(ErrCode.INSTANCE_ID_IS_REQUIRE, "INSTANCE_ID_IS_REQUIRE");
        }
        try {
            auxYdkh.setCreateTime(Common.getCurrentTime());
            auxYdkh.setId(IdCreator.createId("auxYdkh"));
            auxYdkhDAO.add(auxYdkh);
            return auxYdkh;
        } catch (ServiceException e) {
            throw new ServiceException(ErrCode.UNKNOWN_ERROR, "UNKNOWN_ERROR");
        }
    }

    @Override
    public void delete(Long id) {
        auxYdkhDAO.delete(id);
    }

    @Override
    public void update(AuxYdkh auxYdkh) {
        auxYdkhDAO.update(auxYdkh);
    }

    @Override
    public AuxYdkh get(Long id) {
        return auxYdkhDAO.findById(id);
    }

    @Override
    public List<AuxYdkh> findByExample(AuxYdkh auxYdkh) {
        return auxYdkhDAO.findByExample(auxYdkh);
    }

    @Override
    public List<AuxYdkh> findAll() {
        return auxYdkhDAO.findAll();
    }
}


