package com.greathack.homlin.controller.auxiliary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.common.Common;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.auxiliary.AuxHmd;
import com.greathack.homlin.pojo.auxiliary.AuxHmdSearchCriteria;
import com.greathack.homlin.pojo.permission.PowerItem;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxHmdService;
import com.greathack.homlin.serviceinterface.org.IOrganizationService;
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
 * 黑名单
 *
 * @author renTX
 * @date 2020-09-04
 */
@Controller
@RequestMapping(value = "/auxHmd")
public class AuxHmdController {

    private static Logger logger = LoggerFactory.getLogger(AuxHmdController.class);
    private BizException exception = new BizException(200001, "INVALID_PARAMS");

    @Autowired
    private IAuxHmdService auxHmdService;

    @Autowired
    private IOrganizationService organizationService;

    @ResponseBody
    @RequestMapping(value = "/add")
    public Object add(@RequestParam(value = "charset", required = false) String charset,
                      @RequestParam(value = "loginCode", required = true) String loginCode,
                      @RequestBody String strJson, HttpServletRequest request) throws ValidateException {
        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        //对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        //对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson, exception)
                .addStringRule("xm,orgName", true, true, 64, exception)
                .addStringRule("idcard", true, true, 30, exception)
                .addStringRule("outKey1, outKey2", false, false, 255, exception)
                .validate();
        //json转成javaBean
        AuxHmd auxHmd = JSON.toJavaObject(json, AuxHmd.class);
        auxHmd.setCreateBy(admin.getUserId());
        auxHmd.setCreateTime(Common.getCurrentTime());
        try {
            auxHmd = auxHmdService.add(auxHmd);
        } catch (ServiceException e) {
            logger.info("添加黑名单异常");
            e.printStackTrace();
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }
        String jsonString = JSON.toJSONString(auxHmd);
        JSONObject auxHmdObj = JSON.parseObject(jsonString);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("auxHmdObj", auxHmdObj);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "ids", required = false) String id) throws ValidateException {
        if (id == null) {
            logger.info("id必填");
            throw new BizException(200045, "RELATION_ID_IS_REQUIRE");
        }
        Map<String, Object> result = new HashMap<String, Object>();

        String[] idArray = id.split(",");
        for (String elment : idArray) {
            auxHmdService.delete(Long.parseLong(elment));
        }
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");

        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/update")
    public Object update(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson) throws ValidateException {
        //对传进来的参数进行转码
        try {
            //对对strJson进行转码
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson, exception)
                .addStringRule("xm,orgName", true, true, 64, exception)
                .addStringRule("idcard", true, true, 30, exception)
                .addStringRule("outKey1, outKey2", false, false, 255, exception)
                .validate();
        Long id = json.getLong("hmdId");
        AuxHmd oldAuxHmd = auxHmdService.get(id);
        if (oldAuxHmd == null) {
            logger.info("黑名单信息不存在");
            throw new BizException(400030, "HMD_NOT_EXIST");
        }
        AuxHmd auxHmd = JSON.toJavaObject(json, AuxHmd.class);
        try {
            auxHmdService.update(auxHmd);
        } catch (Exception e) {
            logger.info("修改黑名单信息异常");
            e.printStackTrace();
        }
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/search")
    public Object search(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson,
            HttpServletRequest request)
            throws ValidateException {
        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson, exception)
                .addIntegerRule("sortField", false, 1, 2, exception)
                .addIntegerRule("page", false, 1, 1000000, exception)
                .addIntegerRule("pageSize", false, 1, 1000, exception)
                .validate();

        if (json.containsKey("kwFields") && json.containsKey("keyword")) {
            new ParamterValidate()
                    .addRule(new IsInteger(exception))
                    .addRule(new com.greathack.utils.validate.rule.IntegerBetween(1, 15, true, exception))
                    .validate(json.getString("kwFields"));
        }
        AuxHmdSearchCriteria criteria = JSON.parseObject(strJson, AuxHmdSearchCriteria.class);
        //数据权限读取
        List<PowerItem> powerItemList = (List<PowerItem>) request.getAttribute("powerItemList");
        for (PowerItem powerItem : powerItemList) {
            criteria.addFilter(powerItem.getParameter());
        }
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);

        List<AuxHmd> auxHmdList = auxHmdService.search(criteria);
        Long recordCount = auxHmdService.getSearchResultCount(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxHmdList", auxHmdList);
        return result;
    }

}
