package com.greathack.homlin.daointerface.auxiliary;

import com.greathack.homlin.daointerface.system.IBaseDAO;
import com.greathack.homlin.pojo.auxiliary.AuxJygl;
import com.greathack.homlin.pojo.auxiliary.AuxJyglSearchCriteria;

import java.util.List;

public interface IAuxJyglDAO extends IBaseDAO<AuxJygl, Long> {
    List<AuxJygl> search(AuxJyglSearchCriteria criteria);

    long getSearchResultCount(AuxJyglSearchCriteria criteria);

    AuxJygl getById(Long jyId);

    List<AuxJygl> searchDcl(AuxJyglSearchCriteria criteria);

    List<AuxJygl> searchYcl(AuxJyglSearchCriteria criteria);

}
