package com.greathack.homlin.controller.dict;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.dict.Dict;
import com.greathack.homlin.pojo.dict.DictData;
import com.greathack.homlin.pojo.dict.DictSearchCriteria;
import com.greathack.homlin.serviceinterface.dict.IDictDataService;
import com.greathack.homlin.serviceinterface.dict.IDictService;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.TypeOption;
import com.greathack.utils.tools.Validation;
import com.greathack.utils.validate.JsonParamValidate;
import com.greathack.utils.validate.ParamterValidate;
import com.greathack.utils.validate.exception.ValidateException;
import com.greathack.utils.validate.rule.IsInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/dict")
public class DictController {

    private static Logger logger = LoggerFactory.getLogger(DictController.class);
    private BizException exception=new BizException(200001,"INVALID_PARAMS");    

    @Autowired
    private IDictService dictService;

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
        .addStringRule("dictName,dictCode",true,true, 255, exception)
        .validate();
        
        //json转成javaBean
        json.remove("dictId");
        Dict dict = JSON.toJavaObject(json, Dict.class);
        try {
            dict.setAppCode(SystemConfig.getConfigData("DICT_APP_CODE"));
            dict=dictService.addDict(dict);
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }
        
        json.put("dictId", dict.getDictId());
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("dict", json);
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "dictId", required = false) String dictId) throws ValidateException {
        if(Validation.isEmpty(dictId)){
            logger.info("dictId必填");
            throw new BizException(200045, "DICT_ID_IS_REQUIRE");
        }
        //2、对传进来的参数进行转码
        try {
            //对loginCode进行转码
        	loginCode=URLDecoder.decode(loginCode, charset);
            //对dictId进行转码
            dictId=URLDecoder.decode(dictId, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        Map<String, Object> result = new HashMap<String, Object>();  
        dictService.delDict(Long.parseLong(dictId));
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
        .addStringRule("dictId",true,true, 64, exception)
        .addStringRule("dictName,dictCode", 255, exception)
        .validate();        
        
        String dictId=json.getString("dictId");
        Dict dict = dictService.getDict(Long.parseLong(dictId));
        if(dict==null){
            logger.info("字典不存在");
            throw new BizException(200046,"DICT_NOT_EXIST");
        }
        String dictCode=json.getString("dictCode");
        if(dictCode!=null){
            dict.setDictCode(dictCode);
        }
        String dictName=json.getString("dictName");
        if(dictName!=null){
            dict.setDictName(dictName);
        }
        
        try {
            dictService.mdfyDict(dict);
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getMessage());
        }
        
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");

        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/getDict")
    public Object getDict(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "dictId", required = false) String dictId,
            @RequestParam(value = "charset", required = false) String charset)
            throws ValidateException {
        if(Validation.isEmpty(dictId)){
            logger.info("dictId必填");
            throw new BizException(200045, "DICT_ID_IS_REQUIRE");
        }
        //2、对传进来的参数进行转码
        try {
            //对loginCode进行转码
            loginCode=URLDecoder.decode(loginCode, charset);
            //对dictId进行转码
            dictId=URLDecoder.decode(dictId, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        Dict dict = dictService.getDict(Long.parseLong(dictId));
        if(dict==null){
            logger.info("字典不存在");
            throw new BizException(200046,"DICT_NOT_EXIST");
        } 
           
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("dict", dict);
        return result;
    }   
    
    
   
    @ResponseBody
    @RequestMapping(value = "/getList")
    public Object getList(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset) throws ValidateException {


        //2、对传进来的参数进行转码
        try {
            //对loginCode进行转码
            loginCode=URLDecoder.decode(loginCode, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        List<Dict> dictList=dictService.getList(SystemConfig.getConfigData("DICT_APP_CODE"));
        
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("dictList", dictList);
        return result;
    }


    //获取所有的字典对象，包含字典数据
    @ResponseBody
    @RequestMapping(value = "/getAllDict")
    public Object getAllDict(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset) throws ValidateException {
        JSONObject dictJson=new JSONObject();
        List<Dict> dictList=dictService.getList(SystemConfig.getConfigData("DICT_APP_CODE"));
        for(Dict dict:dictList){
            JSONObject dataJson=new JSONObject();
            List<DictData> dictDataList=dictDataService.getList(dict.getDictId());
            for(DictData dictData:dictDataList){
                String jsonStr=JSON.toJSONString(dictData);
                JSONObject json=JSON.parseObject(jsonStr);
                json.remove("appCode");
                json.remove("sortNum");
                json.remove("createTime");
                json.remove("outKey1");
                json.remove("outKey2");
                json.remove("bak1");
                json.remove("bak2");
                dataJson.put(dictData.getDataCode(),json);
            }
            dictJson.put(dict.getDictCode(),dataJson);
        }

        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("dict", dictJson);
        return result;
    }


    //获取所有的字典对象，包含字典数据列表
    @ResponseBody
    @RequestMapping(value = "/getAllDictList")
    public Object getAllDictList(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset) throws ValidateException {
        JSONObject dictJson=new JSONObject();
        List<Dict> dictList=dictService.getList(SystemConfig.getConfigData("DICT_APP_CODE"));
        for(Dict dict:dictList){
            JSONObject dataJson=new JSONObject();
            List<DictData> dictDataList=dictDataService.getList(dict.getDictId());
            dictJson.put(dict.getDictCode(),dictDataList);
        }

        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("dict", dictJson);
        return result;
    }




    @ResponseBody
    @RequestMapping(value = "/search")
    public Object search(
            @RequestBody String strJson,@RequestParam(value = "charset", required = false) String charset) throws ValidateException {


        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson=URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
                .addIntegerRule("sortField", false, 1,2, exception)
                .addIntegerRule("page", false, 1,1000000, exception)
                .addIntegerRule("pageSize", false, 1,1000, exception)
                .validate();

        if(json.containsKey("kwFields") && json.containsKey("keyword")){
            new ParamterValidate().addRule(new IsInteger(exception)).validate(json.getString("kwFields"));
            if(json.getIntValue("kwFields")<1 || json.getIntValue("kwFields")> 3){
                logger.info("kwFields必须在区间1--3");
                throw new BizException(10003, "INVALID_PARAMS");
            }
        }
        DictSearchCriteria criteria =JSON.parseObject(strJson, DictSearchCriteria.class);
        criteria.setAppCode(SystemConfig.getConfigData("DICT_APP_CODE"));

        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page-1) * pageSize);
        criteria.setPageSize(pageSize);


        List<Dict> dictList= dictService.search(criteria);
        Long recordCount=dictService.getSearchResultCount(criteria);
        List<JSONObject> dictJsonList=new ArrayList<JSONObject>();
        for(Dict dict:dictList){
            String dictJsonStr=JSON.toJSONString(dict);
            JSONObject dictJson=JSON.parseObject(dictJsonStr);
            dictJson.remove("appCode");
            dictJsonList.add(dictJson);
        }
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("dictList", dictJsonList);
        return result;
    }

}
