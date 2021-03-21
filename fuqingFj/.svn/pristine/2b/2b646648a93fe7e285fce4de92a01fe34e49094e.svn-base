package com.greathack.homlin.controller.scheduletask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.controller.dict.DictController;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.scheduletask.ScheduleTask;
import com.greathack.homlin.pojo.scheduletask.ScheduleTaskSearchCriteria;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.serviceinterface.scheduletask.IScheduleTaskService;
import com.greathack.utils.tools.TypeOption;
import com.greathack.utils.tools.Validation;
import com.greathack.utils.validate.JsonParamValidate;
import com.greathack.utils.validate.exception.ValidateException;
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
 * @Date 2020-01-19
 * @Author huangjh
 */
@Controller
@RequestMapping(value = "/scheduletask")
public class ScheduleTaskController {
    private static Logger logger = LoggerFactory.getLogger(DictController.class);
    private BizException exception = new BizException(200001, "INVALID_PARAMS");

    @Autowired
    private IScheduleTaskService scheduleTaskService;

    @ResponseBody
    @RequestMapping(value = "/add")
    public Object add(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson,
            HttpServletRequest request) throws ValidateException {
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
        JSONObject json = new JsonParamValidate(strJson, exception).validate();
        //json转成javaBean
        logger.debug(json.toJSONString());
        ScheduleTask scheduleTask = JSON.toJavaObject(json, ScheduleTask.class);
        scheduleTask.setCreateBy(admin.getUserId());
        try {

            scheduleTask = scheduleTaskService.addScheduleTask(scheduleTask);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        json.put("id", scheduleTask.getId());

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("scheduleTask", json);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "id", required = false) String id) throws ValidateException {
        if(Validation.isEmpty(id)){
            logger.info("id必填");
            throw new BizException(200045, "TASK_ID_IS_REQUIRE");
        }
        //2、对传进来的参数进行转码
        try {
            //对loginCode进行转码
            loginCode=URLDecoder.decode(loginCode, charset);
            //对id进行转码
            id=URLDecoder.decode(id, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        Map<String, Object> result = new HashMap<String, Object>();
        scheduleTaskService.delTask(Long.parseLong(id));
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

        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson=URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
                .addStringRule("id",true,true, 64, exception)
                .validate();

        Long id=json.getLong("id");
        ScheduleTask scheduleTaskOld = scheduleTaskService.getScheduleTask(id);
        if(scheduleTaskOld==null){
            logger.info("定时任务不存在");
            throw new BizException(200046,"TASK_NOT_EXIST");
        }
        ScheduleTask scheduleTask = JSON.toJavaObject(json, ScheduleTask.class);

        try {
            scheduleTaskService.editTask(scheduleTask);
        } catch (Exception e) {
            logger.info("修改定时任务信息异常");
            e.printStackTrace();
        }

        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/search")
    public Object search(
            @RequestBody String strJson, @RequestParam(value = "charset", required = false) String charset) throws ValidateException {

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
                .addIntegerRule("page", false, 1, 1000000, exception)
                .addIntegerRule("pageSize", false, 1, 100000, exception)
                .validate();

        ScheduleTaskSearchCriteria criteria = JSON.parseObject(strJson, ScheduleTaskSearchCriteria.class);

        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);

        List<ScheduleTask> scheduleTaskList = scheduleTaskService.search(criteria);
        Long recordCount = scheduleTaskService.getSearchResultCount(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("scheduleTaskList", scheduleTaskList);
        return result;
    }

    /**
     *
     * @param loginCode
     * @param charset
     * @param state
     * @param id
     * @return
     * @throws ValidateException
     */
    @ResponseBody
    @RequestMapping(value = "/changeState")
    public Object changeState(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "id", required = false) Long id) throws ValidateException {

        ScheduleTask scheduleTask = new ScheduleTask();
        scheduleTask.setId(id);

        try {
            if ("true".equals(state)) {
                scheduleTaskService.start(scheduleTask);
            } else {
                scheduleTaskService.stop(scheduleTask);
            }
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(),e.getErrMsg());
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }
}
