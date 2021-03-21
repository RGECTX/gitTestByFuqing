package com.greathack.homlin.daointerface.auxiliary;

import com.greathack.homlin.daointerface.system.IBaseDAO;
import com.greathack.homlin.pojo.auxiliary.*;

import java.util.List;

public interface IAuxQjsqDAO extends IBaseDAO<AuxQjsq, Long> {
    List<AuxQjsq> search(AuxQjsqSearchCriteria criteria);

    long getSearchResultCount(AuxQjsqSearchCriteria criteria);

    List<AuxQjsq> searchQjsh(AuxQjsqSearchCriteria criteria);

}
