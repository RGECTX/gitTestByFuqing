/**
 * 
 */
package com.greathack.homlin.daointerface.dict;

import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.dict.Dict;
import com.greathack.homlin.pojo.dict.DictSearchCriteria;

import java.util.List;

/**
 * @author greathack
 *
 */
public interface IDictDAO extends BaseDAO<Dict> {

    List<Dict> search(DictSearchCriteria criteria);
    long getSearchResultCount(DictSearchCriteria criteria);
}
