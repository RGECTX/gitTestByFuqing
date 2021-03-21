package com.greathack.homlin.controller.auxiliary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.auxiliary.ReportKqtj;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.utils.validate.JsonParamValidate;
import com.greathack.utils.validate.exception.ValidateException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 统计报表
 *
 * @author renTX
 * @date 2020-09-19
 */
@Controller
@RequestMapping(value = "/amReport")
public class AmReportController {

    private static Logger logger = LoggerFactory.getLogger(AmReportController.class);
    private BizException exception = new BizException(200001, "INVALID_PARAMS");

   /* @ResponseBody
    @RequestMapping(value = "/getKqtj")
    public Object getFjqktj(
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
                .validate();

        ReportKqtj criteria = JSON.parseObject(strJson, ReportKqtj.class);
        List<Long> ryStateList = new ArrayList<>();
        if (json.containsKey("ryStates") && StringUtils.isNotBlank(json.getString("ryStates"))) {
            String[] arr = json.getString("ryStates").split(",");
            for (String str : arr) {
                ryStateList.add(Long.valueOf(str));
            }
            criteria.setRyStateList(ryStateList);
        }
        List<ReportFjqktj> reportFjqktjList = amJbxxService.getFjqktj(criteria);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("reportFjqktjList", reportFjqktjList);
        return result;
    }*/
}
