package com.greathack.homlin.controller.auxiliary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.pojo.Admin;

import com.greathack.homlin.pojo.auxiliary.JobResume;
import com.greathack.homlin.service.AdminLoginSessionService;

import com.greathack.homlin.serviceinterface.auxiliary.IJobResumeService;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.homlin.utils.UtilDate;
import com.greathack.utils.validate.JsonParamValidate;
import com.greathack.utils.validate.exception.ValidateException;
import org.apache.commons.lang.StringUtils;
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
 * 工作简历
 *
 * @author renTX
 * @date 2020-09-26
 */
@Controller
@RequestMapping(value = "/jobResume")
public class JobResumeController {

    private static Logger logger = LoggerFactory.getLogger(JobResumeController.class);
    private BizException exception = new BizException(200001, "INVALID_PARAMS");

    @Autowired
    private IJobResumeService jobResumeService;

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
        JobResume jobResume = JSON.toJavaObject(json, JobResume.class);
        if (StringUtils.isNotEmpty(jobResume.getStartDateStr())) {
            String startDateStr = UtilDate.formatDate2YMD(jobResume.getStartDateStr());
            Integer startDate = Integer.parseInt(startDateStr);
            jobResume.setStartDate(startDate);
        }
        if (StringUtils.isNotEmpty(jobResume.getEndDateStr())) {
            String endDateStr = UtilDate.formatDate2YMD(jobResume.getEndDateStr());
            Integer endDate = Integer.parseInt(endDateStr);
            jobResume.setEndDate(endDate);
        }

        jobResume.setCreateBy(admin.getUserId());
        /*jobResume.setAppCode(SystemConfig.getConfigData("JOB_RESUME_APP_CODE"));*/
        jobResume.setAppCode(SystemConfig.getConfigData("AUXILIARY_APP_CODE"));

        try {
            jobResumeService.add(jobResume);
        } catch (Exception e) {
            logger.info("添加工作简历信息异常");
            e.printStackTrace();
        }

        String jsonString = JSON.toJSONString(jobResume);
        JSONObject jobResumeJsObj = JSON.parseObject(jsonString);

        Map<String, Object> result = new HashMap<String, Object>();

        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("jobResume", jobResumeJsObj);
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
        jobResumeService.delete(id);

        Map<String, Object> result = new HashMap<String, Object>();
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

        JobResume jobResumeSource = JSON.toJavaObject(json, JobResume.class);
        if (StringUtils.isNotEmpty(jobResumeSource.getStartDateStr())) {
            String startDateStr = UtilDate.formatDate2YMD(jobResumeSource.getStartDateStr());
            jobResumeSource.setStartDate(Integer.parseInt(startDateStr));
        }
        if (StringUtils.isNotEmpty(jobResumeSource.getEndDateStr())) {
            String endDateStr = UtilDate.formatDate2YMD(jobResumeSource.getEndDateStr());
            jobResumeSource.setEndDate(Integer.parseInt(endDateStr));
        }

        JobResume jobResumeTarget = jobResumeService.get(json.getLong("id"));
        if (jobResumeTarget == null) {
            logger.info("工作简历信息不存在");
            throw new BizException(200046, "JOB_RESUME_NOT_EXIST");
        }

        BeanUtils.copyProperties(jobResumeSource, jobResumeTarget, "id", "appCode", "instanceId", "idcard", "createBy", "createTime");
        jobResumeService.update(jobResumeTarget);

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
        JobResume jobResume = new JobResume();
        jobResume.setInstanceId(instanceId);
        jobResume.setAppCode(SystemConfig.getConfigData("JOB_RESUME_APP_CODE"));
        List<JobResume> jobResumeList = jobResumeService.findByExample(jobResume);

        /*List<JobResume> jobResumeList = jobResumeService.findByInstanceId(jobResume.getInstanceId());*/


        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("jobResumeList", jobResumeList);
        return result;
    }


}
