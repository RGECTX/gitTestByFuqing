package com.greathack.homlin.serviceinterface.auxiliary;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.auxiliary.AuxJygl;
import com.greathack.homlin.pojo.auxiliary.AuxZljd;
import com.greathack.homlin.pojo.auxiliary.AuxZljdSearchCriteria;
import com.greathack.homlin.serviceinterface.system.IBaseService;

import java.util.List;

public interface IAuxZljdService extends IBaseService<AuxZljd, Long> {
    List<AuxZljd> search(AuxZljdSearchCriteria criteria);

    long getSearchResultCount(AuxZljdSearchCriteria criteria);

    void zlAdd(Long zlId, Long userId) throws ServiceException;

    AuxZljd getById(Long zlId);

    AuxZljd getByProcessId(long processId);

    void zlAddById(Long zlId) throws ServiceException;

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
