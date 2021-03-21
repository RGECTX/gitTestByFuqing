package com.greathack.homlin.daointerface.auxiliary;

import com.greathack.homlin.daointerface.system.IBaseDAO;
import com.greathack.homlin.pojo.auxiliary.AuxDagl;
import com.greathack.homlin.pojo.auxiliary.AuxJygl;
import com.greathack.homlin.pojo.auxiliary.AuxZljd;
import com.greathack.homlin.pojo.auxiliary.AuxZljdSearchCriteria;

import java.util.List;

public interface IAuxZljdDAO extends IBaseDAO<AuxZljd, Long> {
    List<AuxZljd> search(AuxZljdSearchCriteria criteria);

    long getSearchResultCount(AuxZljdSearchCriteria criteria);

    void zlAdd(Long zlId, Long userId);

    AuxZljd getById(Long zlId);

    void zlAddById(Long zlId);

    //查询审批通过的招录建档列表
    List<AuxZljd> searchAprove(AuxZljdSearchCriteria criteria);

    List<AuxZljd> findIdcard(String idcard);

    void updateFjlb(AuxZljd auxZljd);

    void updateDwjs(AuxZljd auxZljd);

    List<AuxZljd> searchAproveOrg(AuxZljdSearchCriteria criteria);

    List<AuxZljd> searchDcl(AuxZljdSearchCriteria criteria);

    List<AuxZljd> searchYcl(AuxZljdSearchCriteria criteria);

    void updateWork(AuxZljd auxZljd);

    void updateNewWork(AuxZljd auxZljd);

    void updateRystate(AuxZljd auxZljd);

}
