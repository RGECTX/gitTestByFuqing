package com.greathack.homlin.controller.auxiliary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.auxiliary.AuxDagl;
import com.greathack.homlin.pojo.auxiliary.AuxHmd;
import com.greathack.homlin.pojo.auxiliary.AuxZljd;
import com.greathack.homlin.pojo.auxiliary.AuxZljdSearchCriteria;
import com.greathack.homlin.pojo.org.Organization;
import com.greathack.homlin.pojo.permission.PowerItem;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.serviceinterface.am.IAmUnitService;
import com.greathack.homlin.serviceinterface.attachment.IAttachmentService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxDaglService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxHmdService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxZljdService;
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
import javax.xml.transform.stream.StreamResult;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 招录建档
 *
 * @author renTX
 * @date 2020-09-04
 */
@Controller
@RequestMapping(value = "/auxZljd")
public class AuxZljdController {

    private static Logger logger = LoggerFactory.getLogger(AuxZljdController.class);
    private BizException exception = new BizException(200001, "INVALID_PARAMS");

    @Autowired
    private IAuxZljdService auxZljdService;

    @Autowired
    private IAuxHmdService auxHmdService;

    @Autowired
    private IAuxDaglService auxDaglService;

    @Autowired
    private IOrganizationService organizationService;

    @Autowired
    private IAttachmentService iattachmentService;

    @Autowired
    private IAmUnitService amUnitService;

    //开始招录（未使用）
    @ResponseBody
    @RequestMapping(value = "/zlAdd")
    public Object zlAdd(@RequestParam(value = "zlId", required = true) Long zlId,
                        @RequestParam(value = "charset", required = false) String charset,
                        @RequestParam(value = "loginCode", required = true) String loginCode,
                        HttpServletRequest request) throws ValidateException, ServiceException {

        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();

        try {
            auxZljdService.zlAdd(zlId, admin.getUserId());
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("sbState", 1);
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    //新增建档
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
                 .addStringRule("xm", true, true, 64, exception)
                 .addStringRule("state", true, false, 64, exception)
                 .addStringRule("idcard", true, true, 30, exception)
                 .addStringRule("remark, outKey1, outKey2", false, false, 255, exception)
                .validate();
        //json转成javaBean
        AuxZljd auxZljd = JSON.toJavaObject(json, AuxZljd.class);
        auxZljd.setCreateBy(admin.getUserId());
        auxZljd.setCreateTime(Common.getCurrentTime());
        //通过Id查询部门名
        List<Organization> organizationList = organizationService.findOrgName(auxZljd.getOrgId());
        Organization org = organizationList.get(0);
        auxZljd.setOrgName(org.getOrgName());

        //通过Id查询用人部门名
        List<Organization> organizationList1 = organizationService.findOrgName(auxZljd.getYrdwId());
        Organization org1 = organizationList1.get(0);
        auxZljd.setYrdw(org1.getOrgName());


        auxZljd.setState("1");
        auxZljd.setRyfpState("1");
        //判断招录建档是否在黑名单里
        List<AuxHmd> auxHmdList = auxHmdService.findAll();
        for (AuxHmd auxHmd : auxHmdList) {
            if(auxHmd.getIdcard().length()==18) {
                if (auxHmd.getIdcard().substring(0, 17).equals(auxZljd.getIdcard().substring(0, 17))) {
                    logger.info("黑名单禁止添加招录建档");
                    throw new BizException(400030, "黑名单禁止添加招录建档");
                }
            }
        }
        //判断招录建档是否重复提交
        List<AuxZljd> auxZljdList = auxZljdService.findAll();
        for (AuxZljd Zljd : auxZljdList) {
            if(Zljd.getIdcard().length()==18){
                if (Zljd.getIdcard().substring(0, 17).equals(auxZljd.getIdcard().substring(0, 17))) {
                    if (Zljd.getState().equals("1") || Zljd.getState().equals("2")) {
                        logger.info("招录建档已有重复的身份证信息提交或者正在审批中，请重新添加正确的身份证信息");
                        throw new BizException(400030, "招录建档已有重复的身份证信息提交或者正在审批中，请重新添加正确的身份证信息");
                    }
                }
            }
        }
        //判断档案管理数据是否在招录建档里重复提交
        List<AuxDagl> auxDaglList = auxDaglService.findAll();
        for (AuxDagl auxDagl : auxDaglList) {
            if(auxDagl.getIdcard().length()==18){
                if (auxDagl.getIdcard().substring(0, 17).equals(auxZljd.getIdcard().substring(0, 17))) {
                    if (auxDagl.getState().equals("1")) {
                        logger.info("档案管理已有相同身份证信息的人员在岗，请勿重复提交建档信息");
                        throw new BizException(400030, "档案管理已有相同身份证信息的人员在岗，请勿重复提交建档信息");
                    }else if(auxDagl.getState().equals("2")){
                        logger.info("已离岗辅警不得重新招录！");
                        throw new BizException(400030, "已离岗辅警不得重新招录！");
                    }
                }
            }

        }



        try {
            auxZljd = auxZljdService.add(auxZljd);
        } catch (ServiceException e) {
            logger.info("添加招录建档异常");
            e.printStackTrace();
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }
        String jsonString = JSON.toJSONString(auxZljd);
        JSONObject auxZljdObj = JSON.parseObject(jsonString);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("auxZljdObj", auxZljdObj);
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
            auxZljdService.delete(Long.parseLong(elment));
            iattachmentService.deleteByinstanceId(Long.parseLong(elment));

        }
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    //编辑招录建档
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
                .addStringRule("xm", true, true, 64, exception)
                .addStringRule("state", true, false, 64, exception)
                .addStringRule("idcard", true, true, 30, exception)
                .addStringRule("remark, outKey1, outKey2", false, false, 255, exception)
                .validate();
        Long id = json.getLong("zlId");
        AuxZljd oldAuxZljd = auxZljdService.get(id);
        if (oldAuxZljd == null) {
            logger.info("招录建档信息不存在");
            throw new BizException(400030, "ZLJD_NOT_EXIST");
        }
        AuxZljd auxZljd = JSON.toJavaObject(json, AuxZljd.class);
        //通过Id查询部门名
        List<Organization> organizationList = organizationService.findOrgName(auxZljd.getOrgId());
        Organization org = organizationList.get(0);
        auxZljd.setOrgName(org.getOrgName());

        //通过Id查询用人部门名
        List<Organization> organizationList1 = organizationService.findOrgName(auxZljd.getYrdwId());
        Organization org1 = organizationList1.get(0);
        auxZljd.setYrdw(org1.getOrgName());

       /* //判断招录建档是否在黑名单里
        List<AuxHmd> auxHmdList = auxHmdService.findAll();
        for (AuxHmd auxHmd : auxHmdList) {
            if (auxHmd.getIdcard().substring(0, 17).equals(auxZljd.getIdcard().substring(0, 17))) {
                logger.info("黑名单禁止添加招录建档");
                throw new BizException(400030, "黑名单禁止添加招录建档");
            }
        }
        //判断招录建档是否重复提交
        List<AuxZljd> auxZljdList = auxZljdService.findAll();
        for (AuxZljd Zljd : auxZljdList) {
            if (Zljd.getIdcard().substring(0, 17).equals(auxZljd.getIdcard().substring(0, 17))) {
                if (Zljd.getState().equals("2") || Zljd.getState().equals("1")) {
                    logger.info("招录建档已有重复的身份证信息提交或者正在审批中，请重新添加正确的身份证信息");
                    throw new BizException(400030, "招录建档已有重复的身份证信息提交或者正在审批中，请重新添加正确的身份证信息");
                }
            }
        }*/
        try {
            auxZljdService.update(auxZljd);
        } catch (Exception e) {
            logger.info("修改招录建档信息异常");
            e.printStackTrace();
        }
        //通过身份证ID查询已插入的招录登记记录（未使用）
        /*List<AuxZljd> auxZljdList = auxZljdService.findIdcard(auxZljd.getIdcard());
        List<AuxDagl> auxDaglList = auxDaglService.findIdcard(auxZljd.getIdcard());*/
        //只更新人员分配类别
        /*String fjType = auxZljdList.get(0).getFjType();
        auxDaglList.get(0).setFjType(fjType);
        auxDaglService.update(auxDaglList.get(0));*/
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    //人员分配
    @ResponseBody
    @RequestMapping(value = "/updateFjlb")
    public Object updateFjlb(
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
                /*    .addStringRule("xm,orgName,receiveOrgId", true, true, 64, exception)
                    .addStringRule("state", false, false, 64, exception)
                    .addStringRule("idcard", true, true, 30, exception)
                    .addLongRule("orgId", true, 0L, 20L, exception)
                    .addStringRule("remark, outKey1, outKey2", false, false, 255, exception)*/
                .validate();
        Long id = json.getLong("zlId");
        AuxZljd oldAuxZljd = auxZljdService.get(id);
        if (oldAuxZljd == null) {
            logger.info("招录建档信息不存在");
            throw new BizException(400030, "ZLJD_NOT_EXIST");
        }
        AuxZljd auxZljd = JSON.toJavaObject(json, AuxZljd.class);
        //通过Id查询部门名 自己上报的单位部门名称
        List<Organization> organizationList = organizationService.findOrgName(auxZljd.getOrgId());
        Organization org = organizationList.get(0);
        auxZljd.setOrgName(org.getOrgName());
        try {

            auxZljd.setRyfpState("2");
            auxZljd.setNewFjType(auxZljd.getFjType());//新的辅警类别
            auxZljd.setNewReceiveOrgId(auxZljd.getReceiveOrgId());//新的接收单位

            auxZljdService.updateNewWork(auxZljd);

            //只修改辅警类别 先不修改
            /*auxZljdService.updateFjlb(auxZljd);*/
        } catch (Exception e) {
            logger.info("修改招录建档信息异常");
            e.printStackTrace();
        }


        /*//通过身份证ID查询已插入的招录登记记录
        //获取唯一身份证ID的招录建档记录
        List<AuxZljd> auxZljdList = auxZljdService.findIdcard(auxZljd.getIdcard());
        //获取唯一身份证ID的档案信息记录
        List<AuxDagl> auxDaglList = auxDaglService.findIdcard(auxZljd.getIdcard());
        //获取招录建档唯一的辅警类别
        String fjType = auxZljdList.get(0).getFjType();
        auxDaglList.get(0).setFjType(fjType);

        auxDaglService.updateFjlb(auxDaglList.get(0));*/

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
        AuxZljdSearchCriteria criteria = JSON.parseObject(strJson, AuxZljdSearchCriteria.class);
        //数据权限读取
        List<PowerItem> powerItemList = (List<PowerItem>) request.getAttribute("powerItemList");
        for (PowerItem powerItem : powerItemList) {
            criteria.addFilter(powerItem.getParameter());
        }
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);

        List<AuxZljd> auxZljdList = auxZljdService.search(criteria);

        Long recordCount = auxZljdService.getSearchResultCount(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxZljdList", auxZljdList);
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
        AuxZljdSearchCriteria criteria = JSON.parseObject(strJson, AuxZljdSearchCriteria.class);
        //数据权限读取
        List<PowerItem> powerItemList = (List<PowerItem>) request.getAttribute("powerItemList");
        for (PowerItem powerItem : powerItemList) {
            criteria.addFilter(powerItem.getParameter());
        }
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);

        List<AuxZljd> auxZljdList = auxZljdService.searchDcl(criteria);

        Long recordCount = auxZljdService.getSearchResultCount(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxZljdList", auxZljdList);
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
        AuxZljdSearchCriteria criteria = JSON.parseObject(strJson, AuxZljdSearchCriteria.class);
        //数据权限读取
        List<PowerItem> powerItemList = (List<PowerItem>) request.getAttribute("powerItemList");
        for (PowerItem powerItem : powerItemList) {
            criteria.addFilter(powerItem.getParameter());
        }
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);

        List<AuxZljd> auxZljdList = auxZljdService.searchYcl(criteria);

        Long recordCount = auxZljdService.getSearchResultCount(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxZljdList", auxZljdList);
        return result;
    }

    //查询审批通过的人员分配列表
    @ResponseBody
    @RequestMapping(value = "/searchAprove")
    public Object searchAprove(
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
        AuxZljdSearchCriteria criteria = JSON.parseObject(strJson, AuxZljdSearchCriteria.class);
        //数据权限读取
        List<PowerItem> powerItemList = (List<PowerItem>) request.getAttribute("powerItemList");
        for (PowerItem powerItem : powerItemList) {
            criteria.addFilter(powerItem.getParameter());
        }
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);

        List<AuxZljd> auxZljdList = auxZljdService.searchAprove(criteria);

        Long recordCount = auxZljdService.getSearchResultCount(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxZljdList", auxZljdList);
        return result;
    }

    //查询审批进行及通过的单位接收列表
    @ResponseBody
    @RequestMapping(value = "/searchAproveOrg")
    public Object searchAproveOrg(
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
        AuxZljdSearchCriteria criteria = JSON.parseObject(strJson, AuxZljdSearchCriteria.class);
        //数据权限读取
        List<PowerItem> powerItemList = (List<PowerItem>) request.getAttribute("powerItemList");
        for (PowerItem powerItem : powerItemList) {
            criteria.addFilter(powerItem.getParameter());
        }
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);

        List<AuxZljd> auxZljdList = auxZljdService.searchAproveOrg(criteria);

        Long recordCount = auxZljdService.getSearchResultCount(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxZljdList", auxZljdList);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getObject")
    public Object getObject(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "zlId", required = false) Long zlId,
            @RequestParam(value = "charset", required = false) String charset)
            throws ValidateException {
        if (zlId == null) {
            logger.info("zlId");
            throw new BizException(200045, "ID_IS_REQUIRE");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        AuxZljd auxZljd = auxZljdService.getById(zlId);
        if (auxZljd == null) {
            logger.info("招录建档申请不存在");
            throw new BizException(200046, "ZLJD_NOT_EXIST");
        }
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("auxZljd", auxZljd);
        return result;
    }

   /* //开始招录
    @ResponseBody
    @RequestMapping(value = "/updateDwjs")
    public Object updateDwjs(@RequestParam(value = "zlId", required = true) Long zlId,
                        @RequestParam(value = "charset", required = false) String charset,
                        @RequestParam(value = "loginCode", required = true) String loginCode,
                        HttpServletRequest request) throws ValidateException, ServiceException {

        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();

        AuxZljd auxZljd = auxZljdService.get(zlId);//查询页面数据
        auxZljd.setRyfpState(auxZljd.getNewFjType());
        auxZljd.setReceiveOrgId(auxZljd.getNewReceiveOrgId());
        auxZljd.setRyfpState("4");

        auxZljdService.updateWork(auxZljd);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("sbState",1);
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }*/

    //只修改接收单位
    @ResponseBody
    @RequestMapping(value = "/updateDwjs")
    public Object updateDwjs(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson) throws ValidateException, ServiceException {
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
        Long id = json.getLong("zlId");
        AuxZljd oldAuxZljd = auxZljdService.get(id);
        if (oldAuxZljd == null) {
            logger.info("招录建档信息不存在");
            throw new BizException(400030, "ZLJD_NOT_EXIST");
        }
        AuxZljd auxZljd = JSON.toJavaObject(json, AuxZljd.class);
        //通过Id查询部门名
        List<Organization> organizationList = organizationService.findOrgName(auxZljd.getOrgId());
        Organization org = organizationList.get(0);
        auxZljd.setOrgName(org.getOrgName());

        auxZljd.setFjType(auxZljd.getNewFjType());
        auxZljd.setReceiveOrgId(auxZljd.getNewReceiveOrgId());
        auxZljd.setRyfpState("4");

        try {
            //只修改招录建档接收单位
            auxZljdService.updateWork(auxZljd);
        } catch (Exception e) {
            logger.info("修改招录建档信息异常");
            e.printStackTrace();
        }

        //获取唯一身份证ID的招录建档记录
        List<AuxZljd> auxZljdList = auxZljdService.findIdcard(auxZljd.getIdcard());
        //获取唯一身份证ID的档案信息记录
        List<AuxDagl> auxDaglList = auxDaglService.findIdcard(auxZljd.getIdcard());

        //档案管理页面没有数据 则进行数据插入
        if (auxDaglList.size() == 0) {
            Long zlId = auxZljd.getZlId();
            auxZljdService.zlAddById(zlId);
        } else if (!auxZljd.getIdcard().substring(0,17).equals(auxDaglList.get(0).getIdcard().substring(0,17))) {
            Long zlId = auxZljd.getZlId();
            auxZljdService.zlAddById(zlId);
        } else {
            if(auxDaglList.get(0).getIdcard().substring(0,17).equals(auxZljdList.get(0).getIdcard().substring(0,17))){
                //此时显示是驳回，无法重复提交
                auxZljdList.get(0).setRyfpState("8");
                auxZljdService.updateRystate(auxZljdList.get(0));
                logger.info("档案管理已存在相同的登记信息");
                throw new BizException(ErrCode.THERE_ARE_THE_SAME_DATA_IN_RECORD, "档案管理已存在相同的登记信息,不能重复建档！");
            }
        }

        //获取招录建档唯一的单位分配
        /*String orgName = auxZljdList.get(0).getReceiveOrgId();
        auxDaglList.get(0).setOrgName(orgName);
        auxDaglService.updateDwjs(auxDaglList.get(0));*/
        //获取招录建档唯一的辅警类别
        /*String fjType = auxZljdList.get(0).getFjType();
        auxDaglList.get(0).setFjType(fjType);
        auxDaglService.updateFjlb(auxDaglList.get(0));*/
       /* String orgId = auxZljdList.get(0).getReceiveOrgId();
        auxDaglList.get(0).setOrgId(Long.parseLong(orgId));
        auxDaglService.updateDwjs(auxDaglList.get(0));*/

        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/check")
    public Object check(
            @RequestParam(value = "loginCode") String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson) throws ValidateException {

        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        if (admin == null) {
            logger.info("无效的loginCode");
            throw new BizException(200003, "INVALID_LOGINCODE");
        }

        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        Map<String, Object> result = new HashMap<String, Object>();
        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson, exception)
                /*.addLongRule("id", true, 0L, null, exception)*/
                .validate();

        /*AuxZljd auxZljd = JSON.toJavaObject(json, AuxZljd.class);
        List<AuxZljd> auxZljdList = auxZljdService.findIdcard(auxZljd.getIdcard());
        auxZljdList.get(0).setRyfpState("8");

        auxZljdService.updateRystate(auxZljdList.get(0));*/

        AuxZljd auxZljd = JSON.toJavaObject(json, AuxZljd.class);
        AuxZljd auxZljdById = auxZljdService.getById(auxZljd.getZlId());

        auxZljdById.setRyfpState("8");

        auxZljdService.updateRystate(auxZljdById);

        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }


}
