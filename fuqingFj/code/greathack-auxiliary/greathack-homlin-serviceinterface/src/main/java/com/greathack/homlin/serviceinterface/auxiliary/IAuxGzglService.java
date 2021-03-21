package com.greathack.homlin.serviceinterface.auxiliary;

import com.greathack.homlin.pojo.auxiliary.AuxGzgl;
import com.greathack.homlin.pojo.auxiliary.AuxGzglSearchCriteria;
import com.greathack.homlin.serviceinterface.system.IBaseService;

import java.util.List;

public interface IAuxGzglService extends IBaseService<AuxGzgl, Long> {

    List<AuxGzgl> search(AuxGzglSearchCriteria criteria);

    long getSearchResultCount(AuxGzglSearchCriteria criteria);

    List<AuxGzgl> searchGzglSp(AuxGzglSearchCriteria criteria);

    List<AuxGzgl> searchGzmx(AuxGzglSearchCriteria criteria);

    List<AuxGzglSearchCriteria> getFjqktj(AuxGzglSearchCriteria criteria);
}
