package com.greathack.homlin.controller.auxiliary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.auxiliary.AuxJygl;
import com.greathack.homlin.pojo.auxiliary.AuxJyglSearchCriteria;
import com.greathack.homlin.pojo.auxiliary.AuxZljd;
import com.greathack.homlin.pojo.auxiliary.AuxZljdSearchCriteria;
import com.greathack.homlin.pojo.process.ApprovalItem;
import com.greathack.homlin.pojo.process.Process;
import com.greathack.homlin.pojo.process.ProcessNodeTemplate;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.service.process.ProcessManage;
import com.greathack.homlin.service.process.ProcessTemplateManage;
import com.greathack.homlin.serviceinterface.IAdminService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxJyglService;
import com.greathack.homlin.serviceinterface.process.IProcessHistoryRecordService;
import com.greathack.homlin.utils.UtilDate;
import com.greathack.utils.tools.TypeOption;
import com.greathack.utils.validate.JsonParamValidate;
import com.greathack.utils.validate.ParamterValidate;
import com.greathack.utils.validate.exception.ValidateException;
import com.greathack.utils.validate.rule.LongLeast;
import com.greathack.utils.validate.rule.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author renTX
 * @date 2020-09-09
 */
@Controller
@RequestMapping(value = "/jyglProcess")
public class JyglProcessController {

    private static Logger logger = LoggerFactory.getLogger(JyglProcessController.class);
    private BizException exception = new BizException(210001, "INVALID_PARAMS");

    @Autowired
    private IAuxJyglService auxJyglService;
    @Autowired
    private IAdminService adminService;
    @Autowired
    private IProcessHistoryRecordService processHistoryRecordService;


    //????????????????????????
    @ResponseBody
    @RequestMapping(value = "/apply")
    public Object apply(
            @RequestParam(value = "loginCode") String loginCode,
            @RequestParam(value = "jyId", required = false) Long jyId,
            @RequestParam(value = "processTempleteId", required = false) Long processTempleteId,
            HttpServletRequest request) throws ValidateException {
        new ParamterValidate()
                .addRule(new NotNull(exception))
                .addRule(new LongLeast(1L, true, exception))
                .validate(jyId)
                .validate(processTempleteId);
        AuxJygl auxJygl = auxJyglService.getById(jyId);

        //??????????????????1,8??????????????????????????? 2???????????? 4????????????
        if (Objects.equals(auxJygl.getState(), "2")) {
            throw new BizException(ErrCode.JYGL_PROGRESS_STARTED, "JYGL_PROGRESS_STARTED");
        }
        if (Objects.equals(auxJygl.getState(), "4") || Objects.equals(auxJygl.getState(), "8")) {
            throw new BizException(ErrCode.JYGL_PROGRESS_COMPLETED, "JYGL_PROGRESS_COMPLETED");
        }
        try {
            Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
            Map<String, String> vars = new HashMap<>();
            //??????????????????????????????ID??????????????????ID
            vars.put("applyUserId", String.valueOf(admin.getUserId()));
            //???????????????ID?????????????????????ID???processTempleteId?????????????????????
            Process process = ProcessManage.addProcess(processTempleteId, vars);
            auxJygl.setProcessId(process.getProcessId());

            auxJygl.setState("2");//????????????,FJ_STATE(1???????????????2???????????????4????????????8?????????)????????????????????????????????????????????????
            auxJygl.setProgress("1");//????????????
            auxJyglService.update(auxJygl);
            ProcessManage.ApprovalItemPass(process.getProcessId(), "????????????", String.valueOf(admin.getUserId()));
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }





    //????????????????????????
    @ResponseBody
    @RequestMapping(value = "/getMyhandleList")
    public Object getMyhandleList(@RequestBody String strJson,
                                  @RequestParam(value = "charset", required = false) String charset,
                                  @RequestParam(value = "loginCode", required = true) String loginCode,
                                  HttpServletRequest request) throws ValidateException {
        logger.debug(strJson);

        //3??????json???????????????????????????
        JSONObject json = new JsonParamValidate(strJson, exception)
                .addIntegerRule("myHandleState", true, 1, 2, exception)
                .addIntegerRule("page", false, 1, 1000000, exception)
                .addIntegerRule("pageSize", false, 1, 100000, exception)
                .validate();

        Admin adminLogin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();

        int myHandleState = json.getIntValue("myHandleState");
        AuxJyglSearchCriteria criteria = JSON.parseObject(strJson, AuxJyglSearchCriteria.class);
        if (myHandleState == 1) {//?????????
            criteria.setPendingUserId(String.valueOf(adminLogin.getUserId()));
        }
        if (myHandleState == 2) {//?????????
            criteria.setHandledUserId(String.valueOf(adminLogin.getUserId()));
        }

        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);

        List<AuxJygl> auxJyglList = auxJyglService.search(criteria);
        long recordCount = auxJyglService.getSearchResultCount(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxJyglList", auxJyglList);
        return result;
    }

    //??????????????????????????????
    @ResponseBody
    @RequestMapping(value="/getApprovalItemList")
    public  Object getApprovalItemList(
            @RequestParam(value = "processId", required = true) long processId,
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            HttpServletRequest request) throws ValidateException{

        List<ApprovalItem> approvalItemList= ProcessManage.getApprovalItemListByProcessId(processId);
        JSONArray jsonArray=new JSONArray();
        for(ApprovalItem approvalItem:approvalItemList){
            String jsonStr=JSON.toJSONString(approvalItem);
            JSONObject json=JSON.parseObject(jsonStr);
            Admin admin=adminService.getAdminById(Long.valueOf(approvalItem.getUserId()));
            json.put("name", admin.getName());
            String updateTime= UtilDate.formatMillToDate(approvalItem.getUpdateTime());
            json.put("updateTime", updateTime);
            ProcessNodeTemplate node= ProcessTemplateManage.findByNodeCode(approvalItem.getNodeCode());
            if(node!=null){
                json.put("nodeName", node.getNodeName());
            }
            jsonArray.add(json);
        }

        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("approvalItemList", jsonArray);
        return result;
    }
}
