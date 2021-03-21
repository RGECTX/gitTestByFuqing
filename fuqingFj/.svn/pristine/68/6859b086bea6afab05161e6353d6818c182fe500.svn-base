package com.greathack.homlin.controller.auxiliary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.common.Common;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.auxiliary.*;
import com.greathack.homlin.pojo.org.Organization;
import com.greathack.homlin.pojo.permission.PowerItem;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.serviceinterface.am.IAmUnitService;
import com.greathack.homlin.serviceinterface.attachment.IAttachmentService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxDaglService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxGzglService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxKqtjService;
import com.greathack.homlin.serviceinterface.org.IOrganizationService;
import com.greathack.homlin.utils.UtilDate;
import com.greathack.utils.tools.TypeOption;
import com.greathack.utils.validate.JsonParamValidate;
import com.greathack.utils.validate.ParamterValidate;
import com.greathack.utils.validate.exception.ValidateException;
import com.greathack.utils.validate.rule.IntegerBetween;
import com.greathack.utils.validate.rule.IsInteger;
import org.apache.commons.lang.StringUtils;
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

/**
 * 工资管理
 *
 * @author renTX
 * @date 2020-09-24
 */
@Controller
@RequestMapping(value = "/auxGzgl")
public class AuxGzglController {

    private static Logger logger = LoggerFactory.getLogger(AuxGzglController.class);
    private BizException exception = new BizException(200001, "INVALID_PARAMS");

    @Autowired
    private IAuxGzglService auxGzglService;

    @Autowired
    private IOrganizationService organizationService;

    @Autowired
    private IAttachmentService iattachmentService;

    @Autowired
    private IAuxDaglService iAuxDaglService;

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
        AuxGzgl auxGzgl = JSON.toJavaObject(json, AuxGzgl.class);
        auxGzgl.setCreateBy(admin.getUserId());
        auxGzgl.setCreateTime(Common.getCurrentTime());
        auxGzgl.setSbDate(UtilDate.getCurrentDate());
        //通过Id查询部门名
        List<Organization> organizationList = organizationService.findOrgName(auxGzgl.getOrgId());
        Organization org = organizationList.get(0);
        auxGzgl.setOrgName(org.getOrgName());


        //判断工资管理是否重复提交
        List<AuxGzgl> auxGzglList = auxGzglService.findAll();
        for (AuxGzgl gzgl : auxGzglList) {
            if (gzgl.getIdcard().substring(0, 17).equals(auxGzgl.getIdcard().substring(0, 17))) {
                logger.info("该人员已有重复的身份证信息提交或者正在审批中，请重新添加正确的身份证信息");
                throw new BizException(400030, "该人员已有重复的身份证信息提交或者正在审批中，请重新添加正确的身份证信息");
                /*if (gzgl.getSbState().equals("1")||gzgl.getSbState().equals("2")) {
                    logger.info("该人员已有重复的身份证信息提交或者正在审批中，请重新添加正确的身份证信息");
                    throw new BizException(400030, "该人员已有重复的身份证信息提交或者正在审批中，请重新添加正确的身份证信息");
                }*/
            }
        }


        try {
            auxGzgl = auxGzglService.add(auxGzgl);
        } catch (ServiceException e) {
            logger.info("添加工资名细异常");
            e.printStackTrace();
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }
        String jsonString = JSON.toJSONString(auxGzgl);
        JSONObject auxGzglObj = JSON.parseObject(jsonString);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("auxGzglObj", auxGzglObj);
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
            auxGzglService.delete(Long.parseLong(elment));

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
        Long id = json.getLong("gzglId");
        AuxGzgl oldAuxGzgl = auxGzglService.get(id);
        if (oldAuxGzgl == null) {
            logger.info("工资管理信息不存在");
            throw new BizException(400030, "SALARY_NOT_EXIST");
        }
        AuxGzgl auxGzgl = JSON.toJavaObject(json, AuxGzgl.class);
        //通过Id查询部门名
        List<Organization> organizationList = organizationService.findOrgName(auxGzgl.getOrgId());
        Organization org = organizationList.get(0);
        auxGzgl.setOrgName(org.getOrgName());
        auxGzgl.setSbState("1");

        try {
            auxGzglService.update(auxGzgl);
        } catch (Exception e) {
            logger.info("修改工资信息异常");
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
        AuxGzglSearchCriteria criteria = JSON.parseObject(strJson, AuxGzglSearchCriteria.class);
        //数据权限读取
        List<PowerItem> powerItemList = (List<PowerItem>) request.getAttribute("powerItemList");
        for (PowerItem powerItem : powerItemList) {
            criteria.addFilter(powerItem.getParameter());
        }
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);

        List<AuxGzgl> auxGzglList = auxGzglService.search(criteria);

        Long recordCount = auxGzglService.getSearchResultCount(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxGzglList", auxGzglList);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/searchGzglSp")
    public Object searchGzglSp(
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
        AuxGzglSearchCriteria criteria = JSON.parseObject(strJson, AuxGzglSearchCriteria.class);
        //数据权限读取
        List<PowerItem> powerItemList = (List<PowerItem>) request.getAttribute("powerItemList");
        for (PowerItem powerItem : powerItemList) {
            criteria.addFilter(powerItem.getParameter());
        }
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);

        List<AuxGzgl> auxGzglList = auxGzglService.searchGzglSp(criteria);

        Long recordCount = auxGzglService.getSearchResultCount(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxGzglList", auxGzglList);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/searchGzmx")
    public Object searchGzmx(
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
        AuxGzglSearchCriteria criteria = JSON.parseObject(strJson, AuxGzglSearchCriteria.class);
        //数据权限读取
        List<PowerItem> powerItemList = (List<PowerItem>) request.getAttribute("powerItemList");
        for (PowerItem powerItem : powerItemList) {
            criteria.addFilter(powerItem.getParameter());
        }
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);

        List<AuxGzgl> auxGzglList = auxGzglService.searchGzmx(criteria);

        Long recordCount = auxGzglService.getSearchResultCount(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxGzglList", auxGzglList);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/updateState")
    public Object updateState(
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
        Long id = json.getLong("gzglId");
        AuxGzgl oldAuxGzgl = auxGzglService.get(id);
        if (oldAuxGzgl == null) {
            logger.info("工资信息不存在");
            throw new BizException(400030, "SALARY_NOT_EXIST");
        }
        AuxGzgl auxGzgl = JSON.toJavaObject(json, AuxGzgl.class);
        //通过Id查询部门名
        List<Organization> organizationList = organizationService.findOrgName(auxGzgl.getOrgId());
        Organization org = organizationList.get(0);
        auxGzgl.setOrgName(org.getOrgName());
        auxGzgl.setSbState("2");

        try {
            auxGzglService.update(auxGzgl);
        } catch (Exception e) {
            logger.info("修改工资信息异常");
            e.printStackTrace();
        }
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    /**
     * 工资管理审批通过
     * @param loginCode
     * @param charset
     * @param strJson
     * @return
     * @throws ValidateException
     */
    @ResponseBody
    @RequestMapping(value = "/updateGzsp")
    public Object updateGzsp(
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
        Long id = json.getLong("gzglId");
        AuxGzgl oldAuxGzgl = auxGzglService.get(id);
        Long daId = oldAuxGzgl.getDaId();//获取旧的档案id
        if (oldAuxGzgl == null) {
            logger.info("工资信息不存在");
            throw new BizException(400030, "SALARY_NOT_EXIST");
        }
        AuxGzgl auxGzgl = JSON.toJavaObject(json, AuxGzgl.class);
        //通过Id查询部门名
        List<Organization> organizationList = organizationService.findOrgName(auxGzgl.getOrgId());
        Organization org = organizationList.get(0);
        auxGzgl.setOrgName(org.getOrgName());
        auxGzgl.setSbState("4");


        AuxDagl auxDagl = new AuxDagl();//实例化档案管理对象
        auxDagl.setDaId(daId);
        //查询该人员档案
        AuxDagl auxDagl1 = iAuxDaglService.get(daId);//daId唯一 此人员档案也唯一
        String yfgz = auxGzgl.getYfgz();
        auxDagl1.setYfgz(yfgz);

        iAuxDaglService.updateYfgz(auxDagl1);//档案管理更新应发工资

        try {
            auxGzglService.update(auxGzgl);
        } catch (Exception e) {
            logger.info("修改工资信息异常");
            e.printStackTrace();
        }
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }


    //批量审批通过
    @ResponseBody
    @RequestMapping(value = "/updatePlsp")
    public Object updatePlsp(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "ids", required = false) String ids) throws ValidateException {
        if (ids == null) {
            logger.info("id必填");
            throw new BizException(200045, "GZSP_ID_IS_REQUIRE");
        }
        Map<String, Object> result = new HashMap<String, Object>();

        String[] idArray = ids.split(",");
        for (String elment : idArray) {
            AuxGzgl oldAuxGzgl = auxGzglService.get(Long.parseLong(elment));//获得修改前的工资
            if (oldAuxGzgl == null) {
                logger.info("工资信息不存在");
                throw new BizException(400030, "SALARY_NOT_EXIST");
            }
            oldAuxGzgl.setSbState("4");
            auxGzglService.update(oldAuxGzgl);


            Long daId = oldAuxGzgl.getDaId();//获取旧的档案id
            //查询该人员档案
            AuxDagl auxDagl1 = iAuxDaglService.get(daId);//daId唯一 此人员档案也唯一
            String yfgz = oldAuxGzgl.getYfgz();
            auxDagl1.setYfgz(yfgz);

            iAuxDaglService.updateYfgz(auxDagl1);//档案管理更新应发工资
        }
/*
        Long id = json.getLong("gzglId");
        AuxGzgl oldAuxGzgl = auxGzglService.get(id);
        if (oldAuxGzgl == null) {
            logger.info("工资信息不存在");
            throw new BizException(400030, "SALARY_NOT_EXIST");
        }
        AuxGzgl auxGzgl = JSON.toJavaObject(json, AuxGzgl.class);
        //通过Id查询部门名
        List<Organization> organizationList = organizationService.findOrgName(auxGzgl.getOrgId());
        Organization org = organizationList.get(0);
        auxGzgl.setOrgName(org.getOrgName());
        auxGzgl.setSbState("4");

        try {
            auxGzglService.update(auxGzgl);
        } catch (Exception e) {
            logger.info("修改工资信息异常");
            e.printStackTrace();
        }*/
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/updateGztz")
    public Object updateGztz(
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
        Long id = json.getLong("gzglId");
        AuxGzgl oldAuxGzgl = auxGzglService.get(id);
        if (oldAuxGzgl == null) {
            logger.info("工资信息不存在");
            throw new BizException(400030, "SALARY_NOT_EXIST");
        }
        AuxGzgl auxGzgl = JSON.toJavaObject(json, AuxGzgl.class);
        //通过Id查询部门名
        List<Organization> organizationList = organizationService.findOrgName(auxGzgl.getOrgId());
        Organization org = organizationList.get(0);
        auxGzgl.setOrgName(org.getOrgName());
        auxGzgl.setSbState("2");
        auxGzgl.setGztzState("1");

        try {
            auxGzglService.update(auxGzgl);
        } catch (Exception e) {
            logger.info("调整工资信息异常");
            e.printStackTrace();
        }
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    //调整后工资审核
    @ResponseBody
    @RequestMapping(value = "/tzhgzTj")
    public Object tzhgzTj(
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
        Long id = json.getLong("gzglId");
        AuxGzgl oldAuxGzgl = auxGzglService.get(id);
        if (oldAuxGzgl == null) {
            logger.info("工资信息不存在");
            throw new BizException(400030, "SALARY_NOT_EXIST");
        }
        AuxGzgl auxGzgl = JSON.toJavaObject(json, AuxGzgl.class);
        //通过Id查询部门名
        List<Organization> organizationList = organizationService.findOrgName(auxGzgl.getOrgId());
        Organization org = organizationList.get(0);
        auxGzgl.setOrgName(org.getOrgName());
        auxGzgl.setSbState("4");
        auxGzgl.setGztzState("0");

        auxGzgl.setYfgz(auxGzgl.getTzhgz());
        auxGzgl.setTzhgz("");


        Long daId = oldAuxGzgl.getDaId();//获取旧的档案id
        //查询该人员档案
        AuxDagl auxDagl1 = iAuxDaglService.get(daId);//daId唯一 此人员档案也唯一
        String yfgz = auxGzgl.getYfgz();
        auxDagl1.setYfgz(yfgz);

        iAuxDaglService.updateYfgz(auxDagl1);//档案管理更新应发工资

        try {
            auxGzglService.update(auxGzgl);
        } catch (Exception e) {
            logger.info("修改工资信息异常");
            e.printStackTrace();
        }
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    //工资审核不通过
    @ResponseBody
    @RequestMapping(value = "/check")
    public Object check(
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
        Long id = json.getLong("gzglId");
        AuxGzgl oldAuxGzgl = auxGzglService.get(id);
        if (oldAuxGzgl == null) {
            logger.info("工资信息不存在");
            throw new BizException(400030, "SALARY_NOT_EXIST");
        }
        AuxGzgl auxGzgl = JSON.toJavaObject(json, AuxGzgl.class);
        //通过Id查询部门名
        List<Organization> organizationList = organizationService.findOrgName(auxGzgl.getOrgId());
        Organization org = organizationList.get(0);
        auxGzgl.setOrgName(org.getOrgName());
        auxGzgl.setSbState("8");

        try {
            auxGzglService.update(auxGzgl);
        } catch (Exception e) {
            logger.info("修改工资信息异常");
            e.printStackTrace();
        }
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getFjqktj")
    public Object getFjqktj(
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
        AuxGzglSearchCriteria criteria = JSON.parseObject(strJson, AuxGzglSearchCriteria.class);
        //数据权限读取
        List<PowerItem> powerItemList = (List<PowerItem>) request.getAttribute("powerItemList");
        for (PowerItem powerItem : powerItemList) {
            criteria.addFilter(powerItem.getParameter());
        }
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);


        List<Long> orgIdList = new ArrayList<>();
        if (json.containsKey("orgIds") && StringUtils.isNotBlank(json.getString("orgIds"))) {
            String[] arr = json.getString("orgIds").split(",");
            for (String str : arr) {
                orgIdList.add(Long.valueOf(str));
            }
            criteria.setOrgIdList(orgIdList);
        }
        /*List<Long> ryStateList = new ArrayList<>();
        if (json.containsKey("ryStates") && StringUtils.isNotBlank(json.getString("ryStates"))) {
            String[] arr = json.getString("ryStates").split(",");
            for (String str : arr) {
                ryStateList.add(Long.valueOf(str));
            }
            criteria.setRyStateList(ryStateList);
        }*/

        List<AuxGzglSearchCriteria> auxGzglSearchCriteriaList = auxGzglService.getFjqktj(criteria);

        Long recordCount = auxGzglService.getSearchResultCount(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxGzglSearchCriteriaList", auxGzglSearchCriteriaList);
        return result;
    }



}
