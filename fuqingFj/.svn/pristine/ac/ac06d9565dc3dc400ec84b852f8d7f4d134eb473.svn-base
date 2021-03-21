package com.greathack.homlin.controller.area;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.greathack.utils.tools.TypeOption;
import com.greathack.utils.tools.Validation;
import com.greathack.utils.validate.JsonParamValidate;
import com.greathack.utils.validate.ParamterValidate;
import com.greathack.utils.validate.exception.ValidateException;
import com.greathack.utils.validate.rule.IntegerBetween;
import com.greathack.utils.validate.rule.IsInteger;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.pojo.area.Area;
import com.greathack.homlin.pojo.area.AreaSearchCriteria;
import com.greathack.homlin.service.system.TokenService;
import com.greathack.homlin.serviceinterface.area.IAreaService;
import com.greathack.homlin.system.SystemConfig;

@Controller
@RequestMapping(value = "/area")
public class AreaController {

    private static Logger logger = LoggerFactory.getLogger(AreaController.class);
    private BizException exception=new BizException(200001,"INVALID_PARAMS");

    @Autowired
    private IAreaService areaService;


    @ResponseBody
    @RequestMapping(value = "/getArea")
    public Object getArea(
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "areaId", required = false) Long areaId) throws ValidateException {
        if(areaId==null){
            logger.info("区域ID必填");
            throw new BizException(200068, "AREAID_IS_REQUIRE");
        }
        Area area=areaService.getArea(areaId);

        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("area", area);
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/getAreaByAdcode")
    public Object getAreaByAdcode(
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "adcode", required = false) String adcode) throws ValidateException {
        if(Validation.isEmpty(adcode)){
            logger.info("adcode必填");
            throw new BizException(200069, "ADCODE_IS_REQUIRE");
        }
        Area area=areaService.getAreaByAdcode(adcode);

        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("area", area);
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/search")
    public Object search(
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson) throws ValidateException {
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
                .addIntegerRule("level", false, 1,7, exception)
                .addIntegerRule("sortField", false, 1,2, exception)
                .addIntegerRule("page", false, 1,1000000, exception)
                .addIntegerRule("pageSize", false, 1,1000, exception)
                .validate();

        if(json.containsKey("kwFields") && json.containsKey("keyword")){
            new ParamterValidate()
                    .addRule(new IsInteger(exception))
                    .addRule(new IntegerBetween(1,63,true,exception))
                    .validate(json.getString("kwFields"));
        }
        AreaSearchCriteria criteria =JSON.parseObject(strJson, AreaSearchCriteria.class);


        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page-1) * pageSize);
        criteria.setPageSize(pageSize);
        List<Area> areaList=areaService.search(criteria);
        long recordCount = areaList.size();
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("areaList", areaList);
        return result;
    }

}
