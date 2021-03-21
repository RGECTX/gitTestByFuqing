package com.greathack.homlin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.permission.HasPower;
import com.greathack.homlin.pojo.permission.Power;
import com.greathack.homlin.serviceinterface.IAdminPermissionService;
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
import java.util.*;

@Controller
@RequestMapping("/permission")
public class AdminPermissionController {

    private static Logger logger = LoggerFactory.getLogger(AdminPermissionController.class);
    private BizException exception = new BizException(200001, "INVALID_PARAMS");

    @Autowired
    private IAdminPermissionService adminPermissionService;

    @ResponseBody
    @RequestMapping("/hasPower")
    public Object hasPower(@RequestParam(value = "charset", required = false) String charset,
                           @RequestParam(value = "userId", required = true) String userId,
                           @RequestParam(value = "powerCode", required = true) String powerCode,
                           @RequestParam(value = "loginCode", required = true) String loginCode
    ) throws ValidateException {

        HasPower power = new HasPower();
        power.setHasPower(false);
        try {
            power = adminPermissionService.hasPower(userId, powerCode);
        } catch (ServiceException e) {
            logger.info("判断单项权限失败");
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("hasPower", power.getHasPower());
        return result;

    }

    @ResponseBody
    @RequestMapping("/hasResourcePower")
    public Object hasResourcePower(@RequestParam(value = "charset", required = false) String charset,
                                   @RequestParam(value = "userId", required = true) String outId,
                                   @RequestParam(value = "referer", required = true) String referer,
                                   @RequestParam(value = "resource", required = true) String resource,
                                   @RequestParam(value = "loginCode", required = true) String loginCode
    ) throws ValidateException {

        HasPower power = new HasPower();
        power.setHasPower(false);
        try {
            power = adminPermissionService.hasResourceAccessPower(outId, referer, resource);
        } catch (ServiceException e) {
            logger.info("判断资源权限失败");
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("hasPower", power.getHasPower());
        return result;
    }

    @ResponseBody
    @RequestMapping("/getAllPowerList")
    public Object getAllPowerList(@RequestParam(value = "charset", required = false) String charset,
                                  @RequestParam(value = "userId", required = true) String userId,
                                  @RequestParam(value = "loginCode", required = true) String loginCode
    ) {

        Map<String, Integer> power = adminPermissionService.getAllPowers(userId);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("permission", power);
        return result;

    }

    //获取角色权限
    @ResponseBody
    @RequestMapping("/getRolePowerList")
    public Object getRolePowerList(@RequestParam(value = "charset", required = false) String charset,
                                   @RequestParam(value = "roleId", required = true) String roleId,
                                   @RequestParam(value = "loginCode", required = true) String loginCode) {

        Map<String, Integer> power = adminPermissionService.getPowersForRole(roleId);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("permission", power);
        return result;

    }

    @ResponseBody
    @RequestMapping("/assignRolesToUser")
    public Object assignRolesToUser(@RequestParam(value = "charset", required = false) String charset,
                                    @RequestBody String strJson,
                                    HttpServletRequest request,
                                    @RequestParam(value = "loginCode", required = true) String loginCode
    ) throws ValidateException {

        //1、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        JSONObject json = new JsonParamValidate(strJson, exception)
                .addStringRule("userId", true, true, 64, exception)
                .validate();

        JSONArray jsArr = json.getJSONArray("roleIdList");
        if (jsArr == null) {
            throw new BizException(200001, "INVALID_PARAMS");
        }
        String[] roleIds = new String[jsArr.size()];
        String[] array = jsArr.toArray(roleIds);
        List<String> roleIdList = Arrays.asList(array);
        try {
            adminPermissionService.assignRolesToUser(json.getString("userId"), roleIdList);
        } catch (ServiceException e) {
            logger.info("给用户分配角色出现异常");
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }


        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;

    }


    @ResponseBody
    @RequestMapping("/assignsUsersToRole")
    public Object assignsUsersToRole(@RequestParam(value = "charset", required = false) String charset,
                                     @RequestBody String strJson,
                                     HttpServletRequest request,
                                     @RequestParam(value = "loginCode", required = true) String loginCode
    ) throws ValidateException {

        //1、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        JSONObject json = new JsonParamValidate(strJson, exception)
                .addStringRule("roleId", true, true, 64, exception)
                .validate();

        JSONArray jsArr = json.getJSONArray("userIdList");
        if (jsArr == null) {
            throw new BizException(200001, "INVALID_PARAMS");
        }
        List<String> userIdList = new ArrayList<>();
        for (Object obj : jsArr) {
            userIdList.add(obj.toString());
        }
        adminPermissionService.assignUsersToRole(json.getString("roleId"), userIdList);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;

    }

    @ResponseBody
    @RequestMapping("/assignPowersToUser")
    public Object assignPowersToUser(@RequestParam(value = "charset", required = false) String charset,
                                     HttpServletRequest request,
                                     @RequestParam(value = "loginCode", required = true) String loginCode,
                                     @RequestBody String strJson
    ) throws ValidateException {

        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        JSONObject json = new JsonParamValidate(strJson, exception)
                .addStringRule("userId", true, true, 64, exception)
                .validate();

        JSONArray jsArr = json.getJSONArray("powerList");
        if (jsArr == null || jsArr.size() == 0) {
            throw new BizException(200001, "INVALID_PARAMS");
        }
        List<Power> adminPowerList = new ArrayList<Power>();
        for (Object o : jsArr) {
            String jsonString = JSON.toJSONString(o);
            JSONObject parseObject = JSON.parseObject(jsonString);
            Power adminPower = JSON.toJavaObject(parseObject, Power.class);
            adminPowerList.add(adminPower);
        }
        try {
            adminPermissionService.assignPowersToUser(json.getString("userId"), adminPowerList);
        } catch (ServiceException e) {
            logger.info("给用户分配权限出现异常");
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }


        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;

    }

    @ResponseBody
    @RequestMapping("/assignPowersToRole")
    public Object assignPowersToRole(@RequestParam(value = "charset", required = false) String charset,
                                     HttpServletRequest request,
                                     @RequestParam(value = "loginCode", required = true) String loginCode,
                                     @RequestBody String strJson
    ) throws ValidateException {

        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        JSONObject json = new JsonParamValidate(strJson, exception)
                .addStringRule("roleId", true, true, 64, exception)
                .validate();

        JSONArray jsArr = json.getJSONArray("powerList");
        if (jsArr == null || jsArr.size() == 0) {
            throw new BizException(200001, "INVALID_PARAMS");
        }
        List<Power> adminPowerList = new ArrayList<Power>();
        for (Object o : jsArr) {
            String jsonString = JSON.toJSONString(o);
            JSONObject parseObject = JSON.parseObject(jsonString);
            Power adminPower = JSON.toJavaObject(parseObject, Power.class);
            adminPowerList.add(adminPower);
        }
        try {
            adminPermissionService.assignPowersToRole(json.getString("roleId"), adminPowerList);
        } catch (ServiceException e) {
            logger.info("给角色分配权限出现异常");
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;

    }

}
