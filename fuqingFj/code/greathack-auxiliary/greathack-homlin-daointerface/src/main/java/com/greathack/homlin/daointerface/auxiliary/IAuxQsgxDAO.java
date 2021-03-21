package com.greathack.homlin.daointerface.auxiliary;

import com.greathack.homlin.daointerface.system.IBaseDAO;
import com.greathack.homlin.pojo.auxiliary.AuxDaglSearchCriteria;
import com.greathack.homlin.pojo.auxiliary.AuxQsgx;
import com.greathack.homlin.pojo.auxiliary.AuxQsgxImportVo;

import java.util.List;

public interface IAuxQsgxDAO extends IBaseDAO<AuxQsgx, Long> {

    List<AuxQsgxImportVo> searchExport(AuxDaglSearchCriteria criteria);

}
