package com.greathack.homlin.controller.dict;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.greathack.homlin.system.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.utils.tools.Validation;
import com.greathack.utils.validate.JsonParamValidate;
import com.greathack.utils.validate.exception.ValidateException;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.dict.DictData;
import com.greathack.homlin.serviceinterface.dict.IDictDataService;

@Controller
@RequestMapping(value = "/dictData")
public class DictDataController {

    private static Logger logger = LoggerFactory.getLogger(DictDataController.class);
    private BizException exception=new BizException(200001,"INVALID_PARAMS");    

    @Autowired
    private IDictDataService dictDataService;
    
    @ResponseBody
    @RequestMapping(value = "/add")
    public Object add(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson,
            HttpServletRequest request) throws ValidateException {
        
        //2、对传进来的参数进行转码
        try {
            //对loginCode进行转码
            loginCode=URLDecoder.decode(loginCode, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
        .addStringRule("dictId",true,true, 64, exception)
        .addStringRule("dataName",true,true, 255, exception)
        .addStringRule("content", 255, exception)
        .validate();
        
        //json转成javaBean
        json.remove("dictDataId");
        DictData dictData = JSON.toJavaObject(json, DictData.class);
        try {
            dictData.setAppCode(SystemConfig.getConfigData("DICT_APP_CODE"));
            dictData=dictDataService.addDictData(dictData);
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }
        
        json.put("dictDataId", dictData.getDictDataId());
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("dictData", json);
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "dictDataId", required = false) String dictDataId) throws ValidateException {
        if(Validation.isEmpty(dictDataId)){
            logger.info("dictDataId必填");
            throw new BizException(200047, "DICTDATA_ID_IS_REQUIRE");
        }
        //2、对传进来的参数进行转码
        try {
            //对loginCode进行转码
            loginCode=URLDecoder.decode(loginCode, charset);
            //对dictDataId进行转码
            dictDataId=URLDecoder.decode(dictDataId, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        Map<String, Object> result = new HashMap<String, Object>();  
        dictDataService.delDictData(Long.parseLong(dictDataId));
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");

        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/update")
    public Object update(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson) throws ValidateException {

        //2、对传进来的参数进行转码
        try {
            //对loginCode进行转码
            loginCode=URLDecoder.decode(loginCode, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
        .addStringRule("dictDataId",true,true, 64, exception)
        .addStringRule("dataCode,dataName,content", 255, exception)
        .validate();        
        
        String dictDataId=json.getString("dictDataId");
        DictData dictData = dictDataService.getDictData(Long.parseLong(dictDataId));
        if(dictData==null){
            logger.info("字典数据不存在");
            throw new BizException(200048,"DICTDATA_NOT_EXIST");
        }
        String dataCode=json.getString("dataCode");
        if(dataCode!=null){
            dictData.setDataCode(dataCode);
        }
        String dataName=json.getString("dataName");
        if(dataName!=null){
            dictData.setDataName(dataName);
        }
        String content=json.getString("content");
        if(content!=null){
            dictData.setContent(content);
        }
        
        try {
            dictDataService.mdfyDictData(dictData);
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getMessage());
        }
        
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");

        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/getDictData")
    public Object getDictData(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "dictDataId", required = false) String dictDataId,
            @RequestParam(value = "charset", required = false) String charset)
            throws ValidateException {
        if(Validation.isEmpty(dictDataId)){
            logger.info("dictDataId必填");
            throw new BizException(200047, "DICTDATA_ID_IS_REQUIRE");
        }
        //2、对传进来的参数进行转码
        try {
            //对loginCode进行转码
            loginCode=URLDecoder.decode(loginCode, charset);
            //对dictDataId进行转码
            dictDataId=URLDecoder.decode(dictDataId, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        DictData dictData = dictDataService.getDictData(Long.parseLong(dictDataId));
        if(dictData==null){
            logger.info("字典数据不存在");
            throw new BizException(200048,"DICTDATA_NOT_EXIST");
        } 
           
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("dictData", dictData);
        return result;
    }   
    
    @ResponseBody
    @RequestMapping(value = "/getDictDataByDataCode")
    public Object getDictDataByDataCode(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "dictCode", required = false) String dictCode,
            @RequestParam(value = "dataCode", required = false) String dataCode,
            @RequestParam(value = "charset", required = false) String charset)
            throws ValidateException {
        if(Validation.isEmpty(dataCode)){
            logger.info("dataCode必填");
            throw new BizException(200047, "DATA_CODE_IS_REQUIRE");
        }
        //2、对传进来的参数进行转码
        try {
            //对loginCode进行转码
            loginCode=URLDecoder.decode(loginCode, charset);
            //对dictDataId进行转码
            dataCode=URLDecoder.decode(dataCode, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        DictData dictData = dictDataService.getDictData(SystemConfig.getConfigData("DICT_APP_CODE"),dictCode,dataCode);
        if(dictData==null){
            logger.info("字典数据不存在");
            throw new BizException(200048,"DICTDATA_NOT_EXIST");
        } 
           
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("dictData", dictData);
        return result;
    }   
    
    
   
    @ResponseBody
    @RequestMapping(value = "/getList")
    public Object getList(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "dictId", required = false) String dictId) throws ValidateException {


        //2、对传进来的参数进行转码
        try {
            //对loginCode进行转码
            loginCode=URLDecoder.decode(loginCode, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        List<DictData> dictDataList=dictDataService.getList(Long.parseLong(dictId));
        
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("dictDataList", dictDataList);
        return result;
    }
    
    
   
    @ResponseBody
    @RequestMapping(value = "/getListByDictCode")
    public Object getListByDictCode(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "dictCode", required = false) String dictCode) throws ValidateException {


        //2、对传进来的参数进行转码
        try {
            //对loginCode进行转码
            loginCode=URLDecoder.decode(loginCode, charset);
            //对dictCode进行转码
            dictCode=URLDecoder.decode(dictCode, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        List<DictData> dictDataList=dictDataService.getListByDictCode("am-dict",dictCode);
        
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("dictDataList", dictDataList);
        return result;
    }

}
