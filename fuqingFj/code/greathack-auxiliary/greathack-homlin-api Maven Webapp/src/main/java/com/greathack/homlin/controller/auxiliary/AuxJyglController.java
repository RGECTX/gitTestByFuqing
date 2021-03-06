package com.greathack.homlin.controller.auxiliary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.auxiliary.*;
import com.greathack.homlin.pojo.org.Organization;
import com.greathack.homlin.pojo.permission.PowerItem;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.serviceinterface.am.IAmUnitService;
import com.greathack.homlin.serviceinterface.attachment.IAttachmentService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxJyglService;
import com.greathack.homlin.serviceinterface.org.IOrganizationService;
import com.greathack.utils.tools.TypeOption;
import com.greathack.utils.validate.JsonParamValidate;
import com.greathack.utils.validate.ParamterValidate;
import com.greathack.utils.validate.exception.ValidateException;
import com.greathack.utils.validate.rule.IntegerBetween;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 减员管理
 *
 * @author renTX
 * @date 2020-09-07
 */
@Controller
@RequestMapping(value = "/auxJygl")
public class AuxJyglController {

    private static Logger logger = LoggerFactory.getLogger(AuxJyglController.class);
    private BizException exception = new BizException(200001, "INVALID_PARAMS");

    @Autowired
    private IAuxJyglService auxJyglService;

    @Autowired
    private IOrganizationService organizationService;

    @Autowired
    private IAttachmentService iattachmentService;

    @Autowired
    private IAmUnitService amUnitService;

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
                /*   .addStringRule("xm,orgName,receiveOrgId", true, true, 64, exception)
                   .addStringRule("state", false, false, 64, exception)
                   .addStringRule("idcard", true, true, 30, exception)
                   .addLongRule("orgId", true, 0L, 20L, exception)
                   .addStringRule("remark, outKey1, outKey2", false, false, 255, exception)*/
                .validate();
        //json转成javaBean
        AuxJygl auxJygl = JSON.toJavaObject(json, AuxJygl.class);
        auxJygl.setCreateBy(admin.getUserId());
        auxJygl.setCreateTime(Common.getCurrentTime());
        //通过Id查询部门名
        /*List<Organization> organizationList = organizationService.findOrgName(auxJygl.getReportId());
        Organization org = organizationList.get(0);
        auxJygl.setReportName(org.getOrgName());*/
        auxJygl.setState("1");

        /*Date d = new Date();//当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateNowStr = sdf.format(d);
        auxJygl.setJyrq(dateNowStr);*/

        try {
            auxJygl = auxJyglService.add(auxJygl);
        } catch (ServiceException e) {
            logger.info("添加减员管理信息异常");
            e.printStackTrace();
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }
        String jsonString = JSON.toJSONString(auxJygl);
        JSONObject auxJyglObj = JSON.parseObject(jsonString);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("auxJyglObj", auxJyglObj);
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
            auxJyglService.delete(Long.parseLong(elment));

            iattachmentService.deleteByinstanceId(Long.parseLong(elment));

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
                /*  .addStringRule("xm,orgName,receiveOrgId", true, true, 64, exception)
                  .addStringRule("state", false, false, 64, exception)
                  .addStringRule("idcard", true, true, 30, exception)
                  .addLongRule("orgId", true, 0L, 20L, exception)
                  .addStringRule("remark, outKey1, outKey2", false, false, 255, exception)*/
                .validate();
        Long id = json.getLong("jyId");
        AuxJygl oldAuxJygl = auxJyglService.get(id);
        if (oldAuxJygl == null) {
            logger.info("减员管理信息不存在");
            throw new BizException(400030, "JYGL_NOT_EXIST");
        }
        AuxJygl auxJygl = JSON.toJavaObject(json, AuxJygl.class);
        //通过Id查询部门名
        List<Organization> organizationList = organizationService.findOrgName(auxJygl.getReportId());
        Organization org = organizationList.get(0);
        auxJygl.setReportName(org.getOrgName());

        /*Date d = new Date();//当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(d);
        auxJygl.setJyrq(dateNowStr);*/

        try {
            auxJyglService.update(auxJygl);
        } catch (Exception e) {
            logger.info("修改减员管理信息异常");
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
                    .addRule(new IntegerBetween(1, 15, true, exception))
                    .validate(json.getString("kwFields"));
        }
        AuxJyglSearchCriteria criteria = JSON.parseObject(strJson, AuxJyglSearchCriteria.class);
        //数据权限读取
        List<PowerItem> powerItemList = (List<PowerItem>) request.getAttribute("powerItemList");
        for (PowerItem powerItem : powerItemList) {
            criteria.addFilter(powerItem.getParameter());
        }
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);

        List<AuxJygl> auxJyglList = auxJyglService.search(criteria);

        Long recordCount = auxJyglService.getSearchResultCount(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxJyglList", auxJyglList);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/searchDcl")
    public Object searchDcl(
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
                    .addRule(new IntegerBetween(1, 15, true, exception))
                    .validate(json.getString("kwFields"));
        }
        AuxJyglSearchCriteria criteria = JSON.parseObject(strJson, AuxJyglSearchCriteria.class);

        String jyrqks = json.getString("jyrqks");//减员范围时间（开始）
        String jyrqjs = json.getString("jyrqjs");//减员范围时间（结束）

        if((jyrqks!=null&& !"".equals(jyrqks) &&!"null".equals(jyrqks))&&(jyrqjs!=null&& !"".equals(jyrqjs) &&!"null".equals(jyrqjs))){
            criteria.setBeginTime(Long.parseLong(jyrqks));
            criteria.setEndTime(Long.parseLong(jyrqjs));
        }




        //数据权限读取
        List<PowerItem> powerItemList = (List<PowerItem>) request.getAttribute("powerItemList");
        for (PowerItem powerItem : powerItemList) {
            criteria.addFilter(powerItem.getParameter());
        }
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);

        List<AuxJygl> auxJyglList = auxJyglService.searchDcl(criteria);

        Long recordCount = auxJyglService.getSearchResultCount(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxJyglList", auxJyglList);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/searchYcl")
    public Object searchYcl(
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
                    .addRule(new IntegerBetween(1, 15, true, exception))
                    .validate(json.getString("kwFields"));
        }
        AuxJyglSearchCriteria criteria = JSON.parseObject(strJson, AuxJyglSearchCriteria.class);

        String jyrqks = json.getString("jyrqks");//减员范围时间（开始）
        String jyrqjs = json.getString("jyrqjs");//减员范围时间（结束）

        if((jyrqks!=null&& !"".equals(jyrqks) &&!"null".equals(jyrqks))&&(jyrqjs!=null&& !"".equals(jyrqjs) &&!"null".equals(jyrqjs))){
            criteria.setBeginTime(Long.parseLong(jyrqks));
            criteria.setEndTime(Long.parseLong(jyrqjs));
        }


        //数据权限读取
        List<PowerItem> powerItemList = (List<PowerItem>) request.getAttribute("powerItemList");
        for (PowerItem powerItem : powerItemList) {
            criteria.addFilter(powerItem.getParameter());
        }
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);

        List<AuxJygl> auxJyglList = auxJyglService.searchYcl(criteria);

        Long recordCount = auxJyglService.getSearchResultCount(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxJyglList", auxJyglList);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getObject")
    public Object getObject(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "jyId", required = false) Long jyId,
            @RequestParam(value = "charset", required = false) String charset)
            throws ValidateException {
        if (jyId == null) {
            logger.info("jyId");
            throw new BizException(200045, "ID_IS_REQUIRE");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        AuxJygl auxJygl = auxJyglService.getById(jyId);
        if (auxJygl == null) {
            logger.info("减员管理申请不存在");
            throw new BizException(200046,"JYGL_NOT_EXIST");
        }
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("auxJygl", auxJygl);
        return result;
    }

}
