package com.greathack.homlin.controller.auxiliary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.auxiliary.AuxNdkh;
import com.greathack.homlin.pojo.auxiliary.AuxQsgx;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxNdkhService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxQsgxService;
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
 * 亲属关系信息
 *
 * @author renTX
 * @date 2020-10-09
 */
@Controller
@RequestMapping(value = "/auxQsgx")
public class AuxQsgxController {

    private static Logger logger = LoggerFactory.getLogger(AuxQsgxController.class);
    private BizException exception = new BizException(200001, "INVALID_PARAMS");

    @Autowired
    private IAuxQsgxService auxQsgxService;

    @ResponseBody
    @RequestMapping(value = "/add")
    public Object add(@RequestParam(value = "charset", required = false) String charset,
                      @RequestParam(value = "loginCode") String loginCode,
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
        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson, exception)
                /*  .addLongRule("instanceId", true, 0L, null, exception)
                  .addStringRule("idcard", true, true, 30, exception)
                  .addIntegerRule("khnd,khjl", true, 0, null, exception)
                  .addStringRule("remark", false, false, 255, exception)*/
                .validate();
        //json转成javaBean
        AuxQsgx auxQsgx = JSON.toJavaObject(json, AuxQsgx.class);
        auxQsgx.setCreateBy(admin.getUserId());
        auxQsgx.setCreateTime(Common.getCurrentTime());

        try {
            auxQsgxService.add(auxQsgx);
        } catch (ServiceException e) {
            logger.info("添加亲属关系信息异常");
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete(
            @RequestParam(value = "loginCode") String loginCode,
            @RequestParam(value = "id", required = false) Long id) throws ValidateException {
        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        if (admin == null) {
            logger.info("无效的loginCode");
            throw new BizException(200003, "INVALID_LOGINCODE");
        }
        if (id == null) {
            logger.info("id必填");
            throw new BizException(ErrCode.AUX_QSGX_ID_IS_REQUIRE, "亲属关系ID必填");
        }
        auxQsgxService.delete(id);

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
        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        if (admin == null) {
            logger.info("无效的loginCode");
            throw new BizException(200003, "INVALID_LOGINCODE");
        }

        //2、对传进来的参数进行转码
        try {
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson, exception)
                /*  .addIntegerRule("khnd,khjl", true, 0, null, exception)
                  .addStringRule("remark", false, false, 255, exception)*/
                .validate();

        AuxQsgx auxQsgxSource = JSON.toJavaObject(json, AuxQsgx.class);
        AuxQsgx auxQsgxTarget = auxQsgxService.get(auxQsgxSource.getId());
        if (auxQsgxTarget == null) {
            logger.info("亲属关系不存在");
            throw new BizException(ErrCode.AUX_QSGX_NOT_EXIST, "亲属关系不存在");
        }
        BeanUtils.copyProperties(auxQsgxSource, auxQsgxTarget, "id", "instanceId", "idcard", "createBy", "createTime");
        auxQsgxService.update(auxQsgxTarget);

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
        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        if (admin == null) {
            logger.info("无效的loginCode");
            throw new BizException(200003, "INVALID_LOGINCODE");
        }

        //2、对传进来的参数进行转码
        try {
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        JSONObject json = new JsonParamValidate(strJson, exception).validate();

        AuxQsgx params = new AuxQsgx();
        params.setInstanceId(json.getString("instanceId"));
        List<AuxQsgx> auxQsgxList = auxQsgxService.findByExample(params);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("auxQsgxList", auxQsgxList);
        return result;
    }


}
