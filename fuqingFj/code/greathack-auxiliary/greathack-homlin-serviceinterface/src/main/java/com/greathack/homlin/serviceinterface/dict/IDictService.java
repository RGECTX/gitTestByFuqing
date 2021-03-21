package com.greathack.homlin.serviceinterface.dict;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.dict.Dict;
import com.greathack.homlin.pojo.dict.DictSearchCriteria;

import java.util.List;


public interface IDictService {
    Dict addDict(Dict dict) throws ServiceException;
    void delDict(long dictId);
    void mdfyDict(Dict dict) throws ServiceException;
    Dict getDict(long dictId);
    Dict getDictByDictCode(String appCode, String dictCode);
    List<Dict> getList(String appCode);
    List<Dict> search(DictSearchCriteria criteria);
    long getSearchResultCount(DictSearchCriteria criteria);
}
