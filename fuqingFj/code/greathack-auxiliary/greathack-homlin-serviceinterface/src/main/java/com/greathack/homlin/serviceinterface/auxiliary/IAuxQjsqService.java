package com.greathack.homlin.serviceinterface.auxiliary;

import com.greathack.homlin.pojo.auxiliary.AuxKqtj;
import com.greathack.homlin.pojo.auxiliary.AuxKqtjSearchCriteria;
import com.greathack.homlin.pojo.auxiliary.AuxQjsq;
import com.greathack.homlin.pojo.auxiliary.AuxQjsqSearchCriteria;
import com.greathack.homlin.serviceinterface.system.IBaseService;

import java.util.List;

public interface IAuxQjsqService extends IBaseService<AuxQjsq, Long> {

    List<AuxQjsq> search(AuxQjsqSearchCriteria criteria);

    long getSearchResultCount(AuxQjsqSearchCriteria criteria);

    List<AuxQjsq> searchQjsh(AuxQjsqSearchCriteria criteria);
}
