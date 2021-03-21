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
import com.greathack.homlin.serviceinterface.auxiliary.IAuxKqtjService;
import com.greathack.homlin.serviceinterface.org.IOrganizationService;
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
 * 考勤统计
 *
 * @author renTX
 * @date 2020-09-18
 */
@Controller
@RequestMapping(value = "/auxKqtj")
public class AuxKqtjController {

    private static Logger logger = LoggerFactory.getLogger(AuxKqtjController.class);
    private BizException exception = new BizException(200001, "INVALID_PARAMS");

    @Autowired
    private IAuxKqtjService auxKqtjService;

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
        AuxKqtj auxKqtj = JSON.toJavaObject(json, AuxKqtj.class);
        auxKqtj.setCreateBy(admin.getUserId());
        auxKqtj.setCreateTime(Common.getCurrentTime());
        //通过Id查询部门名
        List<Organization> organizationList = organizationService.findOrgName(auxKqtj.getOrgId());
        Organization org = organizationList.get(0);
        auxKqtj.setOrgName(org.getOrgName());

        if(!auxKqtj.getQqTs().equals("0")){
            auxKqtj.setIsWorkFullHours("2");
        }else{
            auxKqtj.setIsWorkFullHours("1");
        }

        //查询考勤统计所有数据
        List<AuxKqtj> all = auxKqtjService.findAll();
        for (AuxKqtj aK : all) {
            if (aK.getIdcard().substring(0, 17).equals(auxKqtj.getIdcard().substring(0, 17))&& aK.getNd().equals(auxKqtj.getNd()) && aK.getYd().equals(auxKqtj.getYd())) {
                logger.info("同一个人在同一月份只能添加一条考勤记录！");
                throw new BizException(400030, "同一个人在同一月份只能添加一条考勤记录");
            }
        }


        try {
            auxKqtj = auxKqtjService.add(auxKqtj);
        } catch (ServiceException e) {
            logger.info("添加考勤信息异常");
            e.printStackTrace();
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }
        String jsonString = JSON.toJSONString(auxKqtj);
        JSONObject auxKqtjObj = JSON.parseObject(jsonString);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("auxKqtjObj", auxKqtjObj);
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
            auxKqtjService.delete(Long.parseLong(elment));

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
        Long id = json.getLong("kqtjId");
        AuxKqtj oldAuxKqtj = auxKqtjService.get(id);
        if (oldAuxKqtj == null) {
            logger.info("考勤信息不存在");
            throw new BizException(400030, "KQXX_NOT_EXIST");
        }
        AuxKqtj auxKqtj = JSON.toJavaObject(json, AuxKqtj.class);
        //通过Id查询部门名
        List<Organization> organizationList = organizationService.findOrgName(auxKqtj.getOrgId());
        Organization org = organizationList.get(0);
        auxKqtj.setOrgName(org.getOrgName());

        if(!auxKqtj.getQqTs().equals("0")){
            auxKqtj.setIsWorkFullHours("2");
        }else{
            auxKqtj.setIsWorkFullHours("1");
        }

        try {
            auxKqtjService.update(auxKqtj);
        } catch (Exception e) {
            logger.info("修改考勤信息异常");
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
        AuxKqtjSearchCriteria criteria = JSON.parseObject(strJson, AuxKqtjSearchCriteria.class);
        //数据权限读取
        List<PowerItem> powerItemList = (List<PowerItem>) request.getAttribute("powerItemList");
        for (PowerItem powerItem : powerItemList) {
            criteria.addFilter(powerItem.getParameter());
        }
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);

        List<AuxKqtj> auxKqtjList = auxKqtjService.search(criteria);

        Long recordCount = auxKqtjService.getSearchResultCount(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxKqtjList", auxKqtjList);
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
        AuxAttendancdSearchCriteria criteria = JSON.parseObject(strJson, AuxAttendancdSearchCriteria.class);
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
        List<Long> ryStateList = new ArrayList<>();
        if (json.containsKey("ryStates") && StringUtils.isNotBlank(json.getString("ryStates"))) {
            String[] arr = json.getString("ryStates").split(",");
            for (String str : arr) {
                ryStateList.add(Long.valueOf(str));
            }
            criteria.setRyStateList(ryStateList);
        }

        List<AuxAttendancdSearchCriteria> auxAttendanceList = auxKqtjService.getFjqktj(criteria);

        Long recordCount = auxKqtjService.getSearchResultCounts(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("amAttendanceSearchCriteriaList", auxAttendanceList);
        return result;
    }

}
