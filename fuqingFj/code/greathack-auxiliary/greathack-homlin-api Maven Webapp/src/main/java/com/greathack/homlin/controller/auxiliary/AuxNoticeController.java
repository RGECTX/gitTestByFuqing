package com.greathack.homlin.controller.auxiliary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.auxiliary.AuxNotice;
import com.greathack.homlin.pojo.auxiliary.AuxNoticeSearchCriteria;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxNoticeService;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.TypeOption;
import com.greathack.utils.validate.JsonParamValidate;
import com.greathack.utils.validate.ParamterValidate;
import com.greathack.utils.validate.exception.ValidateException;
import com.greathack.utils.validate.rule.IsInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通知通告
 */
@Controller
@RequestMapping(value = "/auxNotice")
public class AuxNoticeController {

    private static Logger logger = LoggerFactory.getLogger(AuxNoticeController.class);
    private BizException exception = new BizException(200001, "INVALID_PARAMS");
    private String AUX_APP_CODE = SystemConfig.getConfigData("AUX_APP_CODE");

    @Autowired
    private IAuxNoticeService auxNoticeService;

    @ResponseBody
    @RequestMapping(value = "/add")
    public Object add(@RequestParam(value = "charset", required = false) String charset,
                      @RequestParam(value = "loginCode") String loginCode,
                      @RequestBody String strJson) throws ValidateException {

        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson, exception)
                .addStringRule("noticeTitle", true, true, 255, exception)
                .addStringRule("remark", false, false, 255, exception)
                .validate();
        //json转成javaBean
        AuxNotice auxNotice = JSON.toJavaObject(json, AuxNotice.class);

        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        auxNotice.setCreateBy(admin.getUserId());
        auxNotice.setAppCode(AUX_APP_CODE);
        auxNotice.setState(1);

        try {
            auxNotice = auxNoticeService.add(auxNotice);
        } catch (ServiceException e) {
            logger.info("添加通知通告异常");
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }

        Map<String, Object> result = new HashMap<>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("auxNotice", auxNotice);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Object update(@RequestBody String strJson,
                         @RequestParam(value = "charset", required = false) String charset,
                         @RequestParam(value = "loginCode") String loginCode) throws ValidateException {
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

        JSONObject json = new JsonParamValidate(strJson, exception)
                .addStringRule("noticeTitle", true, true, 255, exception)
                .addStringRule("remark", false, false, 255, exception)
                .validate();

        AuxNotice auxNoticeSource = JSON.toJavaObject(json, AuxNotice.class);
        if (auxNoticeSource.getNoticeId() == null) {
            logger.info("通知通告id必填");
            throw new BizException(ErrCode.AUX_NOTICEID_IS_REQUIRED, "通知通告id必填");
        }
        AuxNotice auxNoticeTarget = auxNoticeService.get(auxNoticeSource.getNoticeId());
        if (auxNoticeTarget == null) {
            logger.info("通知通告不存在");
            throw new BizException(ErrCode.AUX_NOTICE_NOT_EXIST, "通知通告不存在");
        }
        BeanUtils.copyProperties(auxNoticeSource, auxNoticeTarget, "noticeId",
                "appCode", "state", "createBy", "createTime");
        auxNoticeService.update(auxNoticeTarget);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("auxNotice", auxNoticeTarget);
        return result;

    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete(
            @RequestParam(value = "loginCode") String loginCode,
            @RequestParam(value = "noticeId") Long noticeId) throws ValidateException {
        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        if (admin == null) {
            logger.info("无效的loginCode");
            throw new BizException(200003, "INVALID_LOGINCODE");
        }
        if (noticeId == null) {
            logger.info("通知通告id必填");
            throw new BizException(ErrCode.AUX_NOTICEID_IS_REQUIRED, "通知通告id必填");
        }
        auxNoticeService.delete(noticeId);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/search")
    public Object search(
            @RequestParam(value = "loginCode") String loginCode,
            @RequestBody String strJson,
            @RequestParam(value = "charset", required = false) String charset) throws ValidateException {

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

        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson, exception)
                .addIntegerRule("sortField", false, 1, 2, exception)
                .addIntegerRule("page", false, 1, 1000000, exception)
                .addIntegerRule("pageSize", false, 1, 1000, exception)
                .validate();

        if (json.containsKey("kwFields") && json.containsKey("keyword")) {
            new ParamterValidate().addRule(new IsInteger(exception)).validate(json.getString("kwFields"));
            if (json.getIntValue("kwFields") < 1 || json.getIntValue("kwFields") > 7) {
                logger.info("kwFields必须在区间1--7");
                throw new BizException(10003, "INVALID_PARAMS");
            }
        }
        AuxNoticeSearchCriteria criteria = JSON.parseObject(strJson, AuxNoticeSearchCriteria.class);
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);
        criteria.setAppCode(AUX_APP_CODE);

        List<AuxNotice> auxNoticeList = auxNoticeService.search(criteria);
        Long recordCount = auxNoticeService.getSearchResultCount(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxNoticeList", auxNoticeList);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/state")
    public Object state(@RequestBody String strJson,
                        @RequestParam(value = "charset", required = false) String charset,
                        @RequestParam(value = "loginCode") String loginCode) throws ValidateException {
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

        JSONObject json = new JsonParamValidate(strJson, exception)
                .addLongRule("noticeId", true, 0L, null, exception)
                .addIntegerRule("state", true, 0, null, exception)
                .validate();

        AuxNotice auxNoticeSource = JSON.toJavaObject(json, AuxNotice.class);
        if (auxNoticeSource.getNoticeId() == null) {
            logger.info("通知通告id必填");
            throw new BizException(ErrCode.AUX_NOTICEID_IS_REQUIRED, "通知通告id必填");
        }
        AuxNotice auxNoticeTarget = auxNoticeService.get(auxNoticeSource.getNoticeId());
        if (auxNoticeTarget == null) {
            logger.info("通知通告不存在");
            throw new BizException(ErrCode.AUX_NOTICE_NOT_EXIST, "通知通告不存在");
        }
        auxNoticeTarget.setState(auxNoticeSource.getState());
        auxNoticeService.update(auxNoticeTarget);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;

    }

}
