package com.greathack.homlin.daointerface.auxiliary;

import com.greathack.homlin.daointerface.system.IBaseDAO;
import com.greathack.homlin.pojo.auxiliary.AuxNotice;
import com.greathack.homlin.pojo.auxiliary.AuxNoticeSearchCriteria;

import java.util.List;

/**
 * @Description
 *
 * @author huangmh
 * @date 2020-08-03 17:35:28
 */
public interface IAuxNoticeDAO extends IBaseDAO<AuxNotice, Long> {

    List<AuxNotice> search(AuxNoticeSearchCriteria criteria);

    long getSearchResultCount(AuxNoticeSearchCriteria criteria);

}
