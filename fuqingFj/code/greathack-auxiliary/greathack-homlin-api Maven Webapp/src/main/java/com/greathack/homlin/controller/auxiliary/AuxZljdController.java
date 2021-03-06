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
 * ????????????
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

    //???????????????????????????
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

    //????????????
    @ResponseBody
    @RequestMapping(value = "/add")
    public Object add(@RequestParam(value = "charset", required = false) String charset,
                      @RequestParam(value = "loginCode", required = true) String loginCode,
                      @RequestBody String strJson, HttpServletRequest request) throws ValidateException {
        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        //?????????????????????????????????
        try {
            //???strJson????????????
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("?????????charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        //???json???????????????????????????
        JSONObject json = new JsonParamValidate(strJson, exception)
                 .addStringRule("xm", true, true, 64, exception)
                 .addStringRule("state", true, false, 64, exception)
                 .addStringRule("idcard", true, true, 30, exception)
                 .addStringRule("remark, outKey1, outKey2", false, false, 255, exception)
                .validate();
        //json??????javaBean
        AuxZljd auxZljd = JSON.toJavaObject(json, AuxZljd.class);
        auxZljd.setCreateBy(admin.getUserId());
        auxZljd.setCreateTime(Common.getCurrentTime());
        //??????Id???????????????
        List<Organization> organizationList = organizationService.findOrgName(auxZljd.getOrgId());
        Organization org = organizationList.get(0);
        auxZljd.setOrgName(org.getOrgName());

        //??????Id?????????????????????
        List<Organization> organizationList1 = organizationService.findOrgName(auxZljd.getYrdwId());
        Organization org1 = organizationList1.get(0);
        auxZljd.setYrdw(org1.getOrgName());


        auxZljd.setState("1");
        auxZljd.setRyfpState("1");
        //???????????????????????????????????????
        List<AuxHmd> auxHmdList = auxHmdService.findAll();
        for (AuxHmd auxHmd : auxHmdList) {
            if(auxHmd.getIdcard().length()==18) {
                if (auxHmd.getIdcard().substring(0, 17).equals(auxZljd.getIdcard().substring(0, 17))) {
                    logger.info("?????????????????????????????????");
                    throw new BizException(400030, "?????????????????????????????????");
                }
            }
        }
        //????????????????????????????????????
        List<AuxZljd> auxZljdList = auxZljdService.findAll();
        for (AuxZljd Zljd : auxZljdList) {
            if(Zljd.getIdcard().length()==18){
                if (Zljd.getIdcard().substring(0, 17).equals(auxZljd.getIdcard().substring(0, 17))) {
                    if (Zljd.getState().equals("1") || Zljd.getState().equals("2")) {
                        logger.info("???????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                        throw new BizException(400030, "???????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                    }
                }
            }
        }
        //????????????????????????????????????????????????????????????
        List<AuxDagl> auxDaglList = auxDaglService.findAll();
        for (AuxDagl auxDagl : auxDaglList) {
            if(auxDagl.getIdcard().length()==18){
                if (auxDagl.getIdcard().substring(0, 17).equals(auxZljd.getIdcard().substring(0, 17))) {
                    if (auxDagl.getState().equals("1")) {
                        logger.info("???????????????????????????????????????????????????????????????????????????????????????");
                        throw new BizException(400030, "???????????????????????????????????????????????????????????????????????????????????????");
                    }else if(auxDagl.getState().equals("2")){
                        logger.info("????????????????????????????????????");
                        throw new BizException(400030, "????????????????????????????????????");
                    }
                }
            }

        }



        try {
            auxZljd = auxZljdService.add(auxZljd);
        } catch (ServiceException e) {
            logger.info("????????????????????????");
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
            logger.info("id??????");
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

    //??????????????????
    @ResponseBody
    @RequestMapping(value = "/update")
    public Object update(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson) throws ValidateException {
        //?????????????????????????????????
        try {
            //??????strJson????????????
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("?????????charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        //3??????json???????????????????????????
        JSONObject json = new JsonParamValidate(strJson, exception)
                .addStringRule("xm", true, true, 64, exception)
                .addStringRule("state", true, false, 64, exception)
                .addStringRule("idcard", true, true, 30, exception)
                .addStringRule("remark, outKey1, outKey2", false, false, 255, exception)
                .validate();
        Long id = json.getLong("zlId");
        AuxZljd oldAuxZljd = auxZljdService.get(id);
        if (oldAuxZljd == null) {
            logger.info("???????????????????????????");
            throw new BizException(400030, "ZLJD_NOT_EXIST");
        }
        AuxZljd auxZljd = JSON.toJavaObject(json, AuxZljd.class);
        //??????Id???????????????
        List<Organization> organizationList = organizationService.findOrgName(auxZljd.getOrgId());
        Organization org = organizationList.get(0);
        auxZljd.setOrgName(org.getOrgName());

        //??????Id?????????????????????
        List<Organization> organizationList1 = organizationService.findOrgName(auxZljd.getYrdwId());
        Organization org1 = organizationList1.get(0);
        auxZljd.setYrdw(org1.getOrgName());

       /* //???????????????????????????????????????
        List<AuxHmd> auxHmdList = auxHmdService.findAll();
        for (AuxHmd auxHmd : auxHmdList) {
            if (auxHmd.getIdcard().substring(0, 17).equals(auxZljd.getIdcard().substring(0, 17))) {
                logger.info("?????????????????????????????????");
                throw new BizException(400030, "?????????????????????????????????");
            }
        }
        //????????????????????????????????????
        List<AuxZljd> auxZljdList = auxZljdService.findAll();
        for (AuxZljd Zljd : auxZljdList) {
            if (Zljd.getIdcard().substring(0, 17).equals(auxZljd.getIdcard().substring(0, 17))) {
                if (Zljd.getState().equals("2") || Zljd.getState().equals("1")) {
                    logger.info("???????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                    throw new BizException(400030, "???????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                }
            }
        }*/
        try {
            auxZljdService.update(auxZljd);
        } catch (Exception e) {
            logger.info("??????????????????????????????");
            e.printStackTrace();
        }
        //???????????????ID???????????????????????????????????????????????????
        /*List<AuxZljd> auxZljdList = auxZljdService.findIdcard(auxZljd.getIdcard());
        List<AuxDagl> auxDaglList = auxDaglService.findIdcard(auxZljd.getIdcard());*/
        //???????????????????????????
        /*String fjType = auxZljdList.get(0).getFjType();
        auxDaglList.get(0).setFjType(fjType);
        auxDaglService.update(auxDaglList.get(0));*/
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    //????????????
    @ResponseBody
    @RequestMapping(value = "/updateFjlb")
    public Object updateFjlb(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson) throws ValidateException {
        //?????????????????????????????????
        try {
            //??????strJson????????????
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("?????????charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        //3??????json???????????????????????????
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
            logger.info("???????????????????????????");
            throw new BizException(400030, "ZLJD_NOT_EXIST");
        }
        AuxZljd auxZljd = JSON.toJavaObject(json, AuxZljd.class);
        //??????Id??????????????? ?????????????????????????????????
        List<Organization> organizationList = organizationService.findOrgName(auxZljd.getOrgId());
        Organization org = organizationList.get(0);
        auxZljd.setOrgName(org.getOrgName());
        try {

            auxZljd.setRyfpState("2");
            auxZljd.setNewFjType(auxZljd.getFjType());//??????????????????
            auxZljd.setNewReceiveOrgId(auxZljd.getReceiveOrgId());//??????????????????

            auxZljdService.updateNewWork(auxZljd);

            //????????????????????? ????????????
            /*auxZljdService.updateFjlb(auxZljd);*/
        } catch (Exception e) {
            logger.info("??????????????????????????????");
            e.printStackTrace();
        }


        /*//???????????????ID????????????????????????????????????
        //?????????????????????ID?????????????????????
        List<AuxZljd> auxZljdList = auxZljdService.findIdcard(auxZljd.getIdcard());
        //?????????????????????ID?????????????????????
        List<AuxDagl> auxDaglList = auxDaglService.findIdcard(auxZljd.getIdcard());
        //???????????????????????????????????????
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
        //3??????json???????????????????????????
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
        //??????????????????
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
        //3??????json???????????????????????????
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
        //??????????????????
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
        //3??????json???????????????????????????
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
        //??????????????????
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

    //???????????????????????????????????????
    @ResponseBody
    @RequestMapping(value = "/searchAprove")
    public Object searchAprove(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson,
            HttpServletRequest request)
            throws ValidateException {
        //3??????json???????????????????????????
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
        //??????????????????
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

    //????????????????????????????????????????????????
    @ResponseBody
    @RequestMapping(value = "/searchAproveOrg")
    public Object searchAproveOrg(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson,
            HttpServletRequest request)
            throws ValidateException {
        //3??????json???????????????????????????
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
        //??????????????????
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
            logger.info("???????????????????????????");
            throw new BizException(200046, "ZLJD_NOT_EXIST");
        }
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("auxZljd", auxZljd);
        return result;
    }

   /* //????????????
    @ResponseBody
    @RequestMapping(value = "/updateDwjs")
    public Object updateDwjs(@RequestParam(value = "zlId", required = true) Long zlId,
                        @RequestParam(value = "charset", required = false) String charset,
                        @RequestParam(value = "loginCode", required = true) String loginCode,
                        HttpServletRequest request) throws ValidateException, ServiceException {

        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();

        AuxZljd auxZljd = auxZljdService.get(zlId);//??????????????????
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

    //?????????????????????
    @ResponseBody
    @RequestMapping(value = "/updateDwjs")
    public Object updateDwjs(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson) throws ValidateException, ServiceException {
        //?????????????????????????????????
        try {
            //??????strJson????????????
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("?????????charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        //3??????json???????????????????????????
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
            logger.info("???????????????????????????");
            throw new BizException(400030, "ZLJD_NOT_EXIST");
        }
        AuxZljd auxZljd = JSON.toJavaObject(json, AuxZljd.class);
        //??????Id???????????????
        List<Organization> organizationList = organizationService.findOrgName(auxZljd.getOrgId());
        Organization org = organizationList.get(0);
        auxZljd.setOrgName(org.getOrgName());

        auxZljd.setFjType(auxZljd.getNewFjType());
        auxZljd.setReceiveOrgId(auxZljd.getNewReceiveOrgId());
        auxZljd.setRyfpState("4");

        try {
            //?????????????????????????????????
            auxZljdService.updateWork(auxZljd);
        } catch (Exception e) {
            logger.info("??????????????????????????????");
            e.printStackTrace();
        }

        //?????????????????????ID?????????????????????
        List<AuxZljd> auxZljdList = auxZljdService.findIdcard(auxZljd.getIdcard());
        //?????????????????????ID?????????????????????
        List<AuxDagl> auxDaglList = auxDaglService.findIdcard(auxZljd.getIdcard());

        //?????????????????????????????? ?????????????????????
        if (auxDaglList.size() == 0) {
            Long zlId = auxZljd.getZlId();
            auxZljdService.zlAddById(zlId);
        } else if (!auxZljd.getIdcard().substring(0,17).equals(auxDaglList.get(0).getIdcard().substring(0,17))) {
            Long zlId = auxZljd.getZlId();
            auxZljdService.zlAddById(zlId);
        } else {
            if(auxDaglList.get(0).getIdcard().substring(0,17).equals(auxZljdList.get(0).getIdcard().substring(0,17))){
                //??????????????????????????????????????????
                auxZljdList.get(0).setRyfpState("8");
                auxZljdService.updateRystate(auxZljdList.get(0));
                logger.info("??????????????????????????????????????????");
                throw new BizException(ErrCode.THERE_ARE_THE_SAME_DATA_IN_RECORD, "??????????????????????????????????????????,?????????????????????");
            }
        }

        //???????????????????????????????????????
        /*String orgName = auxZljdList.get(0).getReceiveOrgId();
        auxDaglList.get(0).setOrgName(orgName);
        auxDaglService.updateDwjs(auxDaglList.get(0));*/
        //???????????????????????????????????????
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
            logger.info("?????????loginCode");
            throw new BizException(200003, "INVALID_LOGINCODE");
        }

        //2????????????????????????????????????
        try {
            //???strJson????????????
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("?????????charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        Map<String, Object> result = new HashMap<String, Object>();
        //3??????json???????????????????????????
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
