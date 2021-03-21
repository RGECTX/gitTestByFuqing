package com.greathack.homlin.controller.am;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.controller.system.BaseController;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.pojo.am.AmQuotas;
import com.greathack.homlin.pojo.am.AmQuotasSearchCriteria;
import com.greathack.homlin.pojo.permission.PowerItem;
import com.greathack.homlin.service.schedule.AmQuotasAllocateJob;
import com.greathack.homlin.serviceinterface.am.IAmQuotasService;
import com.greathack.utils.tools.TypeOption;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 辅警编制
 * @author linhz
 * @date 2019-10-10
 *
 */
@Controller
@RequestMapping(value = "/amQuotas")
public class AmQuotasController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AmQuotasController.class);
    private BizException exception=new BizException(200001,"INVALID_PARAMS");    

    @Autowired
    public IAmQuotasService amQuotasService;



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
        .addStringRule("xm",true,true, 64, exception)
        .addStringRule("sex", 255, exception)
        .validate();

        Long id=json.getLong("id");
        AmQuotas oldQuotas = amQuotasService.get(id);

        if(oldQuotas==null){
            logger.info("辅警编制信息不存在");
            throw new BizException(200046,"QUOTAS_NOT_EXIST");
        }

        AmQuotas amQuotas = JSON.toJavaObject(json, AmQuotas.class);

        oldQuotas.setQuotasNum(amQuotas.getQuotasNum());
        oldQuotas.setAllocateNum(amQuotas.getAllocateNum());


        try {
            amQuotasService.update(oldQuotas);
        } catch (Exception e) {
            logger.info("修改报名信息异常");
            e.printStackTrace();
        }
        
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/search")
    public Object search(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson,
            HttpServletRequest request)
            throws ValidateException {


        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
                .addIntegerRule("sortField", false, 1,2, exception)
                .addIntegerRule("page", false, 1,1000000, exception)
                .addIntegerRule("pageSize", false, 1,1000, exception)
                .validate();

        if(json.containsKey("kwFields") && json.containsKey("keyword")){
            new ParamterValidate()
                    .addRule(new IsInteger(exception))
                    .addRule(new com.greathack.utils.validate.rule.IntegerBetween(1, 3, true, exception))
                    .validate(json.getString("kwFields"));
        }
        AmQuotasSearchCriteria criteria =JSON.parseObject(strJson, AmQuotasSearchCriteria.class);


        List<PowerItem> powerItemList= (List<PowerItem>) request.getAttribute("powerItemList");
        for(PowerItem powerItem:powerItemList){
            criteria.addFilter(powerItem.getParameter());
        }

        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page-1) * pageSize);
        criteria.setPageSize(pageSize);

        List<AmQuotas> amQuotasList=amQuotasService.search(criteria);
        Long recordCount=amQuotasService.getSearchResultCount(criteria);

        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("amQuotasList", amQuotasList);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/batchSetQuotasAllocateNum")
    public Object batchSetQuotasAllocateNum(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset)
            throws ValidateException {
        AmQuotasAllocateJob amQuotasAllocateJob=new AmQuotasAllocateJob();
        amQuotasAllocateJob.batchSetQuotasAllocateNum();

        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

}
