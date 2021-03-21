package com.greathack.homlin.controller.auxiliary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.common.Common;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxDaglDAO;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.auxiliary.EduResume;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.serviceinterface.attachment.IAttachmentService;
import com.greathack.homlin.serviceinterface.auxiliary.IEduResumeService;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.validate.JsonParamValidate;
import com.greathack.utils.validate.exception.ValidateException;
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
 * 学历简历
 *
 * @author renTX
 * @date 2020-09-27
 */
@Controller
@RequestMapping(value = "/eduResume")
public class EduResumeController {

    private static Logger logger = LoggerFactory.getLogger(EduResumeController.class);
    private BizException exception = new BizException(200001, "INVALID_PARAMS");

    @Autowired
    private IEduResumeService eduResumeService;
    @Autowired
    private IAttachmentService iattachmentService;
    private static IAuxDaglDAO auxDaglDAO = (IAuxDaglDAO) DAOFactory.createDAO("IAuxDaglDAO");

    @ResponseBody
    @RequestMapping(value = "/add")
    public Object add(@RequestParam(value = "charset", required = false) String charset,
                      @RequestParam(value = "loginCode") String loginCode,
                      @RequestBody String strJson) throws ValidateException {
        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
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
                /*.addStringRule("name", true, true, 64, exception)*/
                .validate();
        //json转成javaBean
        EduResume eduResume = JSON.toJavaObject(json, EduResume.class);
        eduResume.setCreateBy(admin.getUserId());
        eduResume.setCreateTime(Common.getCurrentTime());
        eduResume.setAppCode(SystemConfig.getConfigData("AUXILIARY_APP_CODE"));

        try {
            eduResumeService.add(eduResume);
        } catch (ServiceException e) {
            logger.info("添加学历简历信息异常");
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }

        String jsonString = JSON.toJSONString(eduResume);
        JSONObject eduResumeJsObj = JSON.parseObject(jsonString);

        Map<String, Object> result = new HashMap<String, Object>();

        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("eduResume", eduResumeJsObj);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete(
            @RequestParam(value = "loginCode") String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "id", required = false) Long id) throws ValidateException {
        if (id == null) {
            logger.info("id必填");
            throw new BizException(200045, "RELATION_ID_IS_REQUIRE");
        }

        Map<String, Object> result = new HashMap<String, Object>();
        eduResumeService.delete(id);
        iattachmentService.deleteByinstanceId(id);

        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");

        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/update")
    public Object update(
            @RequestParam(value = "loginCode") String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson) throws ValidateException {

        //2、对传进来的参数进行转码
        try {
            //对loginCode进行转码
            loginCode = URLDecoder.decode(loginCode, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson, exception)
                .addStringRule("xm", true, true, 64, exception)
                .addStringRule("sex", 255, exception)
                .validate();

        EduResume eduResumeSource = JSON.toJavaObject(json, EduResume.class);

        EduResume eduresumeTarget = eduResumeService.get(json.getLong("id"));
        if (eduresumeTarget == null) {
            logger.info("学历信息不存在");
            throw new BizException(200046, "RELATION_NOT_EXIST");
        }
        /*//找出该人员的最高学历
        AmJbxx jbxx = amJbxxDAO.findByJbxxId(Long.parseLong(eduresumeTarget.getInstanceId()));
        String xl = jbxx.getXl();
        if (eduResumeSource.getEduLevel() != null && !"".equals(eduResumeSource.getEduLevel()) && xl != null && !"".equals(xl)) {
            if (Integer.parseInt(eduResumeSource.getEduLevel()) < Integer.parseInt(xl)) {
                throw new BizException(ErrCode.EDUCATIONAL_MISMATCH, "学历不可高于最高学历");
            }
        }*/

        BeanUtils.copyProperties(eduResumeSource, eduresumeTarget, "id", "appCode", "instanceId", "idcard", "createBy", "createTime");
        eduResumeService.update(eduresumeTarget);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/getList")
    public Object getList(
            @RequestParam(value = "loginCode") String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson) throws ValidateException {
        JSONObject json = new JsonParamValidate(strJson, exception).validate();
        String instanceId = json.getString("instanceId");
        //2、对传进来的参数进行转码
        try {
            //对loginCode进行转码
            loginCode = URLDecoder.decode(loginCode, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        EduResume eduResume = new EduResume();
        eduResume.setInstanceId(instanceId);
        eduResume.setAppCode(SystemConfig.getConfigData("EDU_RESUME_APP_CODE"));
        List<EduResume> eduResumeList = eduResumeService.findByExample(eduResume);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("eduResumeList", eduResumeList);
        return result;
    }


}
