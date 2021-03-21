package com.greathack.homlin.service.dict;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.dict.IDictDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.dict.Dict;
import com.greathack.homlin.pojo.dict.DictSearchCriteria;
import com.greathack.homlin.serviceinterface.dict.IDictService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class DictService implements IDictService {

    private static Logger logger = LoggerFactory.getLogger(IDictService.class);
    private static IDictDAO dictDAO = (IDictDAO) DAOFactory.createDAO("IDictDAO");


    @Override
    public Dict addDict(Dict dict) throws ServiceException {
        if(dict==null){
            return null;
        }
        //应用编码不能为空
        if(Validation.isEmpty(dict.getAppCode())){
            throw new ServiceException(120001,"INVALID_PARAMS");
        }

        //字典名称不能为空
        if(Validation.isEmpty(dict.getDictName())){
            throw new ServiceException(120001,"INVALID_PARAMS");
        }
        //字典编码是否重复
        Dict condition = new Dict();
        condition.setAppCode(dict.getAppCode());
        condition.setDictCode(dict.getDictCode());
        List<Dict> dictList=dictDAO.findByExample(condition);
        if(dictList.size()>0){
            throw new ServiceException(120006,"DICT_CODE_EXIST");
        }
        Long ll= IdCreator.createId("Dict");
        dict.setDictId(IdCreator.createId("Dict"));
        dict.setCreateTime(Common.getCurrentTime());
        dictDAO.add(dict);
        return dict;
    }

    @Override
    public void delDict(long dictId) {
        dictDAO.delete(dictId);
    }

    @Override
    public void mdfyDict(Dict dict) throws ServiceException {
        if(dict==null || dict.getDictId()==null){
            throw new ServiceException(120001,"INVALID_PARAMS");
        }

        //字典名称不能为空
        if(Validation.isEmpty(dict.getDictName())){
            throw new ServiceException(120001,"INVALID_PARAMS");
        }
        
        Dict temp=dictDAO.findById(dict.getDictId());
        if(temp==null){
            throw new ServiceException(120007,"DICT_NOT_EXIST");
        }
        //字典编码是否跟自己一样，一样就直接返回
        if(temp.getDictCode().equals(dict.getDictCode())){
            dict.setAppCode(temp.getAppCode());
            dict.setCreateTime(temp.getCreateTime());
            dictDAO.update(dict);
            return;
        }
        //字典编码是否重复
        Dict condition = new Dict();
        condition.setAppCode(dict.getAppCode());
        condition.setDictCode(dict.getDictCode());
        List<Dict> dictList=dictDAO.findByExample(condition);
        if(dictList.size()>0){
            throw new ServiceException(120006,"DICT_CODE_EXIST");
        }
        dict.setAppCode(temp.getAppCode());
        dict.setCreateTime(temp.getCreateTime());
        dictDAO.update(dict);

    }

    @Override
    public Dict getDict(long dictId) {
        return dictDAO.findById(dictId);
    }

    @Override
	public Dict getDictByDictCode(String appCode, String dictCode) {
    	Dict condition = new Dict();
        condition.setAppCode(appCode);
        condition.setDictCode(dictCode);
        List<Dict> dictList=dictDAO.findByExample(condition);
        if(dictList.size()>0){
        	return dictList.get(0);
        }
        return null;
	}

	@Override
    public List<Dict> getList(String appCode) {
        Dict condition = new Dict();
        condition.setAppCode(appCode);
        return dictDAO.findByExample(condition);
    }

    @Override
    public List<Dict> search(DictSearchCriteria criteria) {
        List<Dict> dictList=new ArrayList<Dict>();
        if(criteria==null){
            return dictList;
        }
        return dictDAO.search(criteria);
    }

    @Override
    public long getSearchResultCount(DictSearchCriteria criteria) {
        if(criteria==null){
            return 0;
        }
        String key=Integer.toString(criteria.hashCode());
        String count="0";
        Jedis jedis=null;
        try {
            jedis= SystemConfig.getJedisPool().getResource();
            if(jedis.exists(key)){
                count=jedis.get(key);
                //重新设置超时
                jedis.expire(key, Integer.parseInt(SystemConfig.getConfigData("RESULT_COUNT_EXPIRE")));
            }else{
                count=Long.toString(dictDAO.getSearchResultCount(criteria));
                jedis.set(key, count);
                //设置超时
                jedis.expire(key, Integer.parseInt(SystemConfig.getConfigData("RESULT_COUNT_EXPIRE")));
            }           
        } catch (Exception e) {
            logger.debug("redis有异常");
        }finally{
            if(jedis!=null){
                jedis.close();
            }           
        }   
        return Long.parseLong(count);
    }

}
