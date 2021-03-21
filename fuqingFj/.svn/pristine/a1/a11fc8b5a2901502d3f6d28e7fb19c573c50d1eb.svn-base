package com.greathack.homlin.serviceinterface.am;

import com.greathack.homlin.pojo.am.AmQuotas;
import com.greathack.homlin.pojo.am.AmQuotasSearchCriteria;
import com.greathack.homlin.serviceinterface.system.IBaseService;

import java.util.List;

public interface IAmQuotasService extends IBaseService<AmQuotas, Long> {
    AmQuotas getByUnitId(long unitId);
    List<AmQuotas> search(AmQuotasSearchCriteria criteria);
    long getSearchResultCount(AmQuotasSearchCriteria criteria);

}
