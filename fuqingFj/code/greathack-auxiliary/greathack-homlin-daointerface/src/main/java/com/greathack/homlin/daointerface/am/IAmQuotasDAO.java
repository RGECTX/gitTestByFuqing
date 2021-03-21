package com.greathack.homlin.daointerface.am;

import com.greathack.homlin.daointerface.system.IBaseDAO;
import com.greathack.homlin.pojo.am.AmQuotas;
import com.greathack.homlin.pojo.am.AmQuotasSearchCriteria;

import java.util.List;

public interface IAmQuotasDAO extends IBaseDAO<AmQuotas, Long> {
    List<AmQuotas> search(AmQuotasSearchCriteria criteria);
    long getSearchResultCount(AmQuotasSearchCriteria criteria);

    /*统计二级辅警编制数量*/
    AmQuotas countSecondFjbz(AmQuotas amQuotas);

    /*统计三级辅警编制数量*/
    AmQuotas countThirdFjbz(AmQuotas amQuotas);
	
}
