package com.greathack.homlin.controller.auxiliary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.auxiliary.AuxNdkh;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxNdkhService;
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
 * 年度考核信息
 *
 * @author renTX
 * @date 2020-09-27
 */
@Controller
@RequestMapping(value = "/auxNdkh")
public class AuxNdkhController {

    private static Logger logger = LoggerFactory.getLogger(AuxNdkhController.class);
    private BizException exception = new BizException(200001, "INVALID_PARAMS");

    @Autowired
    private IAuxNdkhService auxNdkhService;

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
        AuxNdkh auxNdkh = JSON.toJavaObject(json, AuxNdkh.class);
        auxNdkh.setCreateBy(admin.getUserId());
        auxNdkh.setCreateTime(Common.getCurrentTime());

        try {
            auxNdkhService.add(auxNdkh);
        } catch (ServiceException e) {
            logger.info("添加年度考核异常");
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
            throw new BizException(ErrCode.AUX_NDKH_ID_IS_REQUIRE, "年度考核ID必填");
        }
        auxNdkhService.delete(id);

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

        AuxNdkh auxNdkhSource = JSON.toJavaObject(json, AuxNdkh.class);
        AuxNdkh auxNdkhTarget = auxNdkhService.get(auxNdkhSource.getId());
        if (auxNdkhTarget == null) {
            logger.info("年度考核不存在");
            throw new BizException(ErrCode.AUX_NDKH_NOT_EXIST, "年度考核不存在");
        }
        BeanUtils.copyProperties(auxNdkhSource, auxNdkhTarget, "id", "instanceId", "idcard", "createBy", "createTime");
        auxNdkhService.update(auxNdkhTarget);

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

        AuxNdkh params = new AuxNdkh();
        params.setInstanceId(json.getString("instanceId"));
        List<AuxNdkh> auxNdkhList = auxNdkhService.findByExample(params);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("amNdkhList", auxNdkhList);
        return result;
    }


}
