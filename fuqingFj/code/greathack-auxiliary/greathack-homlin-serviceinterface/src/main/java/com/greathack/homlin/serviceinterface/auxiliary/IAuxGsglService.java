package com.greathack.homlin.serviceinterface.auxiliary;

import com.greathack.homlin.pojo.auxiliary.AuxGsgl;
import com.greathack.homlin.pojo.auxiliary.AuxGsglSearchCriteria;
import com.greathack.homlin.pojo.auxiliary.AuxKqtj;
import com.greathack.homlin.pojo.auxiliary.AuxKqtjSearchCriteria;
import com.greathack.homlin.serviceinterface.system.IBaseService;

import java.util.List;

public interface IAuxGsglService extends IBaseService<AuxGsgl, Long> {

    List<AuxGsgl> search(AuxGsglSearchCriteria criteria);

    long getSearchResultCount(AuxGsglSearchCriteria criteria);

    List<AuxGsgl> searchGssh(AuxGsglSearchCriteria criteria);

    AuxGsgl getById(Long gsglId);
}
