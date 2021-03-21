package com.greathack.homlin.serviceinterface.auxiliary;

import com.greathack.homlin.pojo.auxiliary.AuxBzgl;
import com.greathack.homlin.pojo.auxiliary.AuxBzglSearchCriteria;
import com.greathack.homlin.pojo.auxiliary.AuxJygl;
import com.greathack.homlin.pojo.auxiliary.AuxJyglSearchCriteria;
import com.greathack.homlin.serviceinterface.system.IBaseService;

import java.util.List;

public interface IAuxBzglService extends IBaseService<AuxBzgl, Long> {

    AuxBzgl getById(Long bzglId);

    AuxBzgl getByProcessId(long processId);

    List<AuxBzgl> search(AuxBzglSearchCriteria criteria);

    long getSearchResultCount(AuxBzglSearchCriteria criteria);

    List<AuxBzgl> searchDcl(AuxBzglSearchCriteria criteria);

    List<AuxBzgl> searchYcl(AuxBzglSearchCriteria criteria);
}
