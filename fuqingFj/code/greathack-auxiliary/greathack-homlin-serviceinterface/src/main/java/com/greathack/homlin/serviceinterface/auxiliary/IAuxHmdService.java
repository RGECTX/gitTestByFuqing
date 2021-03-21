package com.greathack.homlin.serviceinterface.auxiliary;

import com.greathack.homlin.pojo.auxiliary.AuxHmd;
import com.greathack.homlin.pojo.auxiliary.AuxHmdSearchCriteria;
import com.greathack.homlin.serviceinterface.system.IBaseService;

import java.util.List;

public interface IAuxHmdService extends IBaseService<AuxHmd, Long> {
    List<AuxHmd> search(AuxHmdSearchCriteria criteria);

    long getSearchResultCount(AuxHmdSearchCriteria criteria);

}
