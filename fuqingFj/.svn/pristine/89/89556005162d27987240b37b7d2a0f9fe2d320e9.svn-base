package com.greathack.homlin.controller.process;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.process.ApprovalItem;
import com.greathack.homlin.pojo.process.ProcessNode;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.service.process.ProcessManage;
import com.greathack.homlin.serviceinterface.IAdminService;
import com.greathack.utils.tools.Validation;
import com.greathack.utils.validate.JsonParamValidate;
import com.greathack.utils.validate.exception.ValidateException;
import com.greathack.utils.validate.rule.Options;
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
 * @author greathack
 */
@Controller
@RequestMapping(value = "/process")
public class ProcessController {

    private static Logger logger = LoggerFactory.getLogger(ProcessController.class);
    private BizException exception = new BizException(210001, "INVALID_PARAMS");

    @Autowired
    private IAdminService adminService;

    //提交审批意见
    @ResponseBody
    @RequestMapping(value = "/approval")
    public Object approval(@RequestBody String strJson,
                           @RequestParam(value = "charset", required = false) String charset,
                           @RequestParam(value = "loginCode", required = true) String loginCode,
                           HttpServletRequest request) throws ValidateException {

        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        JSONObject json = new JsonParamValidate(strJson, exception)
                .addLongRule("processId", true, 0L, null, exception)
                .addIntegerRule("itemState", true, new Options("4,8"), exception)
                .addStringRule("remark", 255, exception)
                .validate();

        long processId = json.getLongValue("processId");
        int itemState = json.getIntValue("itemState");
        String remark = json.getString("remark");
        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        try {
            if (itemState == 4) {
                ProcessManage.ApprovalItemPass(processId, remark, String.valueOf(admin.getUserId()));
            }
            if (itemState == 8) {
                ProcessManage.ApprovalItemNoPass(processId, remark, String.valueOf(admin.getUserId()));
            }
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    //批量审批
    @ResponseBody
    @RequestMapping(value = "/batchApproval")
    public Object batchApproval(@RequestBody String strJson,
                                @RequestParam(value = "charset", required = false) String charset,
                                @RequestParam(value = "loginCode", required = true) String loginCode,
                                HttpServletRequest request) throws ValidateException {

        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        JSONObject json = new JsonParamValidate(strJson, exception)
                .addLongRule("processId", true, 0L, null, exception)
                .addIntegerRule("itemState", true, new Options("4,8"), exception)
                .addStringRule("remark", 255, exception)
                .validate();

        String processIds = json.getString("processIds");
        String[] pids = processIds.split(",");

        int itemState = json.getIntValue("itemState");
        String remark = json.getString("remark");
        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        try {
            for (String pid : pids) {
                long processId = Long.parseLong(pid);

                if (itemState == 4) {
                    ProcessManage.ApprovalItemPass(processId, remark, String.valueOf(admin.getUserId()));
                }
                if (itemState == 8) {
                    ProcessManage.ApprovalItemNoPass(processId, remark, String.valueOf(admin.getUserId()));
                }
            }

        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    //获取流程审批意见列表
    @ResponseBody
    @RequestMapping(value = "/getApprovalItemList")
    public Object getApprovalItemList(
            @RequestParam(value = "processId", required = true) long processId,
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            HttpServletRequest request) throws ValidateException {

        List<ApprovalItem> approvalItemList = ProcessManage.getApprovalItemListByProcessId(processId);
        JSONArray jsonArray = new JSONArray();
        for (ApprovalItem approvalItem : approvalItemList) {
            String jsonStr = JSON.toJSONString(approvalItem);
            JSONObject json = JSON.parseObject(jsonStr);
            Admin admin = adminService.getAdminById(Long.valueOf(approvalItem.getUserId()));
            json.put("name", admin.getName());
            jsonArray.add(json);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("approvalItemList", jsonArray);
        return result;
    }

    //获取当前待审批用户列表列表
    @ResponseBody
    @RequestMapping(value = "/getCurrentPendingUserList")
    public Object getCurrentPendingUserList(
            @RequestParam(value = "processId", required = true) long processId,
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            HttpServletRequest request) throws ValidateException, ServiceException {
        ProcessNode processNode = ProcessManage.getCurrentProcessNode(processId);
        List<String> userIdList = ProcessManage.getProcessNodePendingUsers(processNode.getProcessNodeId());
        List<Admin> userList = new ArrayList<Admin>();
        for (String userId : userIdList) {
            if (!Validation.isEmpty(userId)) {
                Admin user = adminService.getAdminById(Long.valueOf(userId));
                if (user != null) {
                    userList.add(user);
                }
            }
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("userList", userList);
        return result;
    }
}
