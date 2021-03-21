package com.greathack.homlin.service.auxiliary;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxSjqkDAO;
import com.greathack.homlin.daointerface.auxiliary.IAuxYdkhDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.auxiliary.AuxSjqk;
import com.greathack.homlin.pojo.auxiliary.AuxYdkh;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxSjqkService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxYdkhService;
import com.greathack.homlin.system.IdCreator;

import java.util.List;

public class AuxSjqkService implements IAuxSjqkService {

    private static IAuxSjqkDAO auxSjqkDAO = (IAuxSjqkDAO) DAOFactory.createDAO("IAuxSjqkDAO");

    @Override
    public AuxSjqk add(AuxSjqk auxSjqk) throws ServiceException {
        if (auxSjqk.getInstanceId() == null) {
            throw new ServiceException(ErrCode.INSTANCE_ID_IS_REQUIRE, "INSTANCE_ID_IS_REQUIRE");
        }
        try {
            auxSjqk.setCreateTime(Common.getCurrentTime());
            auxSjqk.setId(IdCreator.createId("auxSjqk"));
            auxSjqkDAO.add(auxSjqk);
            return auxSjqk;
        } catch (ServiceException e) {
            throw new ServiceException(ErrCode.UNKNOWN_ERROR, "UNKNOWN_ERROR");
        }
    }

    @Override
    public void delete(Long id) {
        auxSjqkDAO.delete(id);
    }

    @Override
    public void update(AuxSjqk auxSjqk) {
        auxSjqkDAO.update(auxSjqk);
    }

    @Override
    public AuxSjqk get(Long id) {
        return auxSjqkDAO.findById(id);
    }

    @Override
    public List<AuxSjqk> findByExample(AuxSjqk auxSjqk) {
        return auxSjqkDAO.findByExample(auxSjqk);
    }

    @Override
    public List<AuxSjqk> findAll() {
        return auxSjqkDAO.findAll();
    }
}


