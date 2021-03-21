package com.greathack.homlin.controller;

import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.auxiliary.*;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.service.auxiliary.AuxZljdService;
import com.greathack.homlin.serviceinterface.auxiliary.*;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.validate.exception.ValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 首页接口
 *
 * @author zhanghb
 * @date 2017-07-17
 */
@Controller
@RequestMapping(value = "/console")
public class ConsoleController {

    private static Logger logger = LoggerFactory.getLogger(ConsoleController.class);
    private String AUX_APP_CODE = SystemConfig.getConfigData("AUX_APP_CODE");

    @Autowired
    private IAuxZljdService auxZljdService;
    @Autowired
    private IAuxJyglService auxJyglService;
    @Autowired
    private IAuxGzglService auxGzglService;
    @Autowired
    private IAuxGsglService auxGsglService;
    @Autowired
    private IAuxDaglService auxDaglService;
    /**
     * 待办事项
     *
     * @param loginCode
     * @return
     * @throws ValidateException
     */
    @ResponseBody
    @RequestMapping(value = "/toDo")
    public Object toDo(
            @RequestParam(value = "loginCode") String loginCode)
            throws ValidateException {
        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        if (admin == null) {
            logger.info("无效的loginCode");
            throw new BizException(200003, "INVALID_LOGINCODE");
        }

        Map<Object, Object> map = new HashMap<>();
        int page = 1;
        int pageSize = 2147483647;

        //单位接收
        AuxZljdSearchCriteria auxZljdSearchCriteria = new AuxZljdSearchCriteria();
        auxZljdSearchCriteria.setStartLine((page-1)*pageSize);
        auxZljdSearchCriteria.setPageSize(pageSize);
        auxZljdSearchCriteria.setAppCode(AUX_APP_CODE);
        auxZljdSearchCriteria.setState("2");
        map.put("dwjs",auxZljdService.searchAproveOrg(auxZljdSearchCriteria).size());

        //减员待处理
        AuxJyglSearchCriteria auxJyglSearchCriteria = new AuxJyglSearchCriteria();
        auxJyglSearchCriteria.setStartLine((page-1)*pageSize);
        auxJyglSearchCriteria.setPageSize(pageSize);
        auxJyglSearchCriteria.setAppCode(AUX_APP_CODE);
        auxJyglSearchCriteria.setState("2");
        map.put("jydcl",auxJyglService.searchDcl(auxJyglSearchCriteria).size());

        //工资审核
        AuxGzglSearchCriteria auxGzglSearchCriteria = new AuxGzglSearchCriteria();
        auxGzglSearchCriteria.setStartLine((page-1)*pageSize);
        auxGzglSearchCriteria.setPageSize(pageSize);
        auxGzglSearchCriteria.setAppCode(AUX_APP_CODE);
        auxGzglSearchCriteria.setSbState("2");
        map.put("gzsh",auxGzglService.searchGzglSp(auxGzglSearchCriteria).size());

        //工伤审核
        AuxGsglSearchCriteria auxGsglSearchCriteria = new AuxGsglSearchCriteria();
        auxGsglSearchCriteria.setStartLine((page-1)*pageSize);
        auxGsglSearchCriteria.setPageSize(pageSize);
        auxGsglSearchCriteria.setAppCode(AUX_APP_CODE);
        map.put("gssh",auxGsglService.searchGssh(auxGsglSearchCriteria).size());


        String amUnitId = admin.getAmUnitId();

        //人员统计
        AuxDaglSearchCriteria auxDaglSearchCriteria = new AuxDaglSearchCriteria();
        auxDaglSearchCriteria.setStartLine((page-1)*pageSize);
        auxDaglSearchCriteria.setPageSize(pageSize);
        auxDaglSearchCriteria.setAppCode(AUX_APP_CODE);

        auxDaglSearchCriteria.setOrgId(Long.valueOf(amUnitId));

        if(amUnitId.equals("9")){
            map.put("rytj",auxDaglService.getSearchResultCount3(auxDaglSearchCriteria));
        }else{
            map.put("rytj",auxDaglService.getSearchResultCount4(auxDaglSearchCriteria));
        }

        return map;
    }


}
