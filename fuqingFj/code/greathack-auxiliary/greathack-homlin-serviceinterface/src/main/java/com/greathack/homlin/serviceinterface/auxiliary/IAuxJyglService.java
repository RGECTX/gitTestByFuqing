package com.greathack.homlin.serviceinterface.auxiliary;

import com.greathack.homlin.pojo.auxiliary.AuxJygl;
import com.greathack.homlin.pojo.auxiliary.AuxJyglSearchCriteria;
import com.greathack.homlin.serviceinterface.system.IBaseService;

import java.util.List;

public interface IAuxJyglService extends IBaseService<AuxJygl, Long> {
    List<AuxJygl> search(AuxJyglSearchCriteria criteria);

    long getSearchResultCount(AuxJyglSearchCriteria criteria);

    AuxJygl getById(Long jyId);

    AuxJygl getByProcessId(long processId);

    List<AuxJygl> searchDcl(AuxJyglSearchCriteria criteria);

    List<AuxJygl> searchYcl(AuxJyglSearchCriteria criteria);
}
