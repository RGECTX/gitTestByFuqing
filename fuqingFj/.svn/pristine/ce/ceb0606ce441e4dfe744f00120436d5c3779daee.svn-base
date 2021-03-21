package com.greathack.homlin.controller.auxiliary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.auxiliary.AuxSjqk;
import com.greathack.homlin.pojo.auxiliary.EduResume;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.serviceinterface.attachment.IAttachmentService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxSjqkService;
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
 * 授奖情况信息
 *
 * @author renTX
 * @date 2020-11-02
 */
@Controller
@RequestMapping(value = "/auxSjqk")
public class AuxSjqkController {

    private static Logger logger = LoggerFactory.getLogger(AuxSjqkController.class);
    private BizException exception = new BizException(200001, "INVALID_PARAMS");

    @Autowired
    private IAuxSjqkService auxSjqkService;
    @Autowired
    private IAttachmentService iattachmentService;

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
        AuxSjqk auxSjqk = JSON.toJavaObject(json, AuxSjqk.class);
        auxSjqk.setCreateBy(admin.getUserId());
        auxSjqk.setCreateTime(Common.getCurrentTime());

        try {
            auxSjqkService.add(auxSjqk);
        } catch (ServiceException e) {
            logger.info("添加奖惩情况信息异常");
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }

        String jsonString = JSON.toJSONString(auxSjqk);
        JSONObject auxSjqkObj = JSON.parseObject(jsonString);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("auxSjqkObj", auxSjqkObj);
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
            throw new BizException(ErrCode.AUX_SJQK_ID_IS_REQUIRE, "授奖情况信息id必填");
        }
        auxSjqkService.delete(id);
        iattachmentService.deleteByinstanceId(id);

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

        AuxSjqk auxSjqkSource = JSON.toJavaObject(json, AuxSjqk.class);
        AuxSjqk auxSjqkTarget = auxSjqkService.get(json.getLong("id"));

        /*AuxSjqk auxSjqkTarget = auxSjqkService.get(auxSjqkSource.getId());*/
        if (auxSjqkTarget == null) {
            logger.info("授奖情况不存在");
            throw new BizException(ErrCode.AUX_SJQK_NOT_EXIST, "授奖情况不存在");
        }
        BeanUtils.copyProperties(auxSjqkSource, auxSjqkTarget, "id", "instanceId", "idcard", "createBy", "createTime");
        auxSjqkService.update(auxSjqkTarget);

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

        AuxSjqk params = new AuxSjqk();
        params.setInstanceId(json.getString("instanceId"));
        List<AuxSjqk> auxSjqkList = auxSjqkService.findByExample(params);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("auxSjqkList", auxSjqkList);
        return result;
    }


}
