package com.greathack.homlin.service.dict;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.dict.IDictDAO;
import com.greathack.homlin.daointerface.dict.IDictDataDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.dict.Dict;
import com.greathack.homlin.pojo.dict.DictData;
import com.greathack.homlin.serviceinterface.dict.IDictDataService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.utils.tools.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictDataService implements IDictDataService {

    private static Logger logger = LoggerFactory.getLogger(IDictDataService.class);
    private static IDictDataDAO dictDataDAO = (IDictDataDAO) DAOFactory.createDAO("IDictDataDAO");
    private static IDictDAO dictDAO = (IDictDAO) DAOFactory.createDAO("IDictDAO");
    private static Map<String, Map<String, DictData>> dictMap=null;

    public static Map<String, Map<String, DictData>> getDictMap(String appCode){
        if(dictMap==null){
            dictMap=new HashMap<>();
            Dict cond = new Dict();
            cond.setAppCode(appCode);
            List<Dict> dictList=dictDAO.findByExample(cond);
            for(Dict dict:dictList){
                Map<String, DictData> dictDataMap=new HashMap<>();
                DictData condition = new DictData();
                condition.setDictId(dict.getDictId());
                List<DictData> dictDataList=dictDataDAO.findByExample(condition);
                for(DictData dictData:dictDataList){
                    dictDataMap.put(dictData.getDataCode(),dictData);
                }
                dictMap.put(dict.getDictCode(),dictDataMap);
            }
        }
        return dictMap;
    }

    @Override
    public DictData addDictData(DictData dictData) throws ServiceException {
        if(dictData==null){
            return null;
        }
        //????????????????????????
        if(Validation.isEmpty(dictData.getAppCode())){
            logger.info("??????????????????");
            throw new ServiceException(120001,"INVALID_PARAMS");
        }

        //????????????????????????
        if(dictData.getDictId()==null){
            logger.info("??????????????????");
            throw new ServiceException(120001,"INVALID_PARAMS");
        }
        
        //?????????????????????
        Dict dict=dictDAO.findById(dictData.getDictId());
        if(dict==null){
            logger.info("?????????????????????");
            throw new ServiceException(120007,"DICT_NOT_EXIST");
        }

        //????????????????????????
        if(Validation.isEmpty(dictData.getDataName())){
            logger.info("??????????????????");
            throw new ServiceException(120001,"INVALID_PARAMS");
        }
        
        //????????????????????????
        DictData condition = new DictData();
        condition.setAppCode(dictData.getAppCode());
        condition.setDictId(dict.getDictId());
        condition.setDataCode(dictData.getDataCode());
        List<DictData> dictDataList=dictDataDAO.findByExample(condition);
        if(dictDataList.size()>0){
            logger.info("??????????????????");
            throw new ServiceException(120008,"DICTDATA_CODE_EXIST");
        }
        dictData.setDictDataId(IdCreator.createId("DictData"));
        dictData.setCreateTime(Common.getCurrentTime());
        dictDataDAO.add(dictData);
        return dictData;
    }

    @Override
    public void delDictData(long dictDataId) {
        dictDataDAO.delete(dictDataId);

    }

    @Override
    public void mdfyDictData(DictData dictData) throws ServiceException {
        if(dictData==null || dictData.getDictDataId()==null){
            throw new ServiceException(120001,"INVALID_PARAMS");
        }

        //????????????????????????
        if(Validation.isEmpty(dictData.getDataName())){
            logger.info("??????????????????");
            throw new ServiceException(120001,"INVALID_PARAMS");
        }

        //????????????ID????????????
        if(dictData.getDictId()==null){
            logger.info("????????????ID??????");
            throw new ServiceException(120001,"INVALID_PARAMS");
        }
        
        //?????????????????????
        Dict dict=dictDAO.findById(dictData.getDictId());
        if(dict==null){
            logger.info("?????????????????????");
            throw new ServiceException(120007,"DICT_NOT_EXIST");
        }
        
        DictData temp=dictDataDAO.findById(dictData.getDictDataId());
        if(temp==null){
            logger.info("?????????????????????");
            throw new ServiceException(120009,"DICTDATA_NOT_EXIST");
        }
        //?????????????????????????????????????????????????????????
        if(temp.getDataCode().equals(dictData.getDataCode())){
            dictData.setAppCode(temp.getAppCode());
            dictData.setCreateTime(temp.getCreateTime());
            dictDataDAO.update(dictData);
            return;
        }
        
        //????????????????????????
        DictData condition = new DictData();
        condition.setAppCode(dictData.getAppCode());
        condition.setDictId(dict.getDictId());
        condition.setDataCode(dictData.getDataCode());
        List<DictData> dictDataList=dictDataDAO.findByExample(condition);
        if(dictDataList.size()>0){
            logger.info("??????????????????");
            throw new ServiceException(120006,"DICTDATA_CODE_EXIST");
        }
        dictData.setAppCode(temp.getAppCode());
        dictData.setCreateTime(temp.getCreateTime());
        dictDataDAO.update(dictData);

    }

    @Override
    public DictData getDictData(long dictDataId) {
        return dictDataDAO.findById(dictDataId);
    }

    @Override
	public DictData getDictData(String appCode, String dictCode, String dataCode) {
        Dict dictCondition = new Dict();
        dictCondition.setAppCode(appCode);
        dictCondition.setDictCode(dictCode);
        List<Dict> dictList=dictDAO.findByExample(dictCondition);
        if(dictList.size()>0){
            DictData condition = new DictData();
            condition.setAppCode(appCode);
            condition.setDictId(dictList.get(0).getDictId());
            condition.setDataCode(dataCode);
            List<DictData> dictDataList=dictDataDAO.findByExample(condition);
            if(dictDataList.size()>0){
                return dictDataList.get(0);
            }
        }
        return null;
	}

	@Override
    public List<DictData> getList(long dictId) {
        DictData condition = new DictData();
        condition.setDictId(dictId);
        List<DictData> dictDataList=dictDataDAO.findByExample(condition);
        return dictDataList;
    }

    @Override
    public List<DictData> getListByDictCode(String appCode, String dictCode) {
        Dict condition = new Dict();
        condition.setAppCode(appCode);
        condition.setDictCode(dictCode);
        List<Dict> dictList=dictDAO.findByExample(condition);
        if(dictList!=null && dictList.size()>0){
            return getList(dictList.get(0).getDictId());
        }
        return new ArrayList<DictData>();
    }

}
