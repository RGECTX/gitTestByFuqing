package com.greathack.homlin.serviceinterface.dict;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.dict.DictData;

import java.util.List;


public interface IDictDataService {

    DictData addDictData(DictData dictData) throws ServiceException;
    void delDictData(long dictDataId);
    void mdfyDictData(DictData dictData) throws ServiceException;
    DictData getDictData(long dictDataId);
    DictData getDictData(String appCode, String dictCode, String dataCode);
    List<DictData> getList(long dictId);
    List<DictData> getListByDictCode(String appCode, String dictCode);
    
}
