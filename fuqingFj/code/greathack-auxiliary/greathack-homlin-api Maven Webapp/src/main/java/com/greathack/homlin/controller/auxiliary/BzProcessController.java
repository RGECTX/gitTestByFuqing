package com.greathack.homlin.controller.auxiliary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.auxiliary.AuxBzgl;
import com.greathack.homlin.pojo.auxiliary.AuxBzglSearchCriteria;
import com.greathack.homlin.pojo.auxiliary.AuxJygl;
import com.greathack.homlin.pojo.auxiliary.AuxJyglSearchCriteria;
import com.greathack.homlin.pojo.process.ApprovalItem;
import com.greathack.homlin.pojo.process.Process;
import com.greathack.homlin.pojo.process.ProcessNodeTemplate;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.service.process.ProcessManage;
import com.greathack.homlin.service.process.ProcessTemplateManage;
import com.greathack.homlin.serviceinterface.IAdminService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxBzglService;
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
 * @date 2020-09-27
 */
@Controller
@RequestMapping(value = "/bzProcess")
public class BzProcessController {

    private static Logger logger = LoggerFactory.getLogger(BzProcessController.class);
    private BizException exception = new BizException(210001, "INVALID_PARAMS");

    @Autowired
    private IAuxBzglService auxBzglService;
    @Autowired
    private IAdminService adminService;
    @Autowired
    private IProcessHistoryRecordService processHistoryRecordService;


    //开始被装申请流程
    @ResponseBody
    @RequestMapping(value = "/apply")
    public Object apply(
            @RequestParam(value = "loginCode") String loginCode,
            @RequestParam(value = "bzglId", required = false) Long bzglId,
            @RequestParam(value = "processTempleteId", required = false) Long processTempleteId,
            HttpServletRequest request) throws ValidateException {

        new ParamterValidate()
                .addRule(new NotNull(exception))
                .addRule(new LongLeast(1L, true, exception))
                .validate(bzglId)
                .validate(processTempleteId);
        AuxBzgl auxBzgl = auxBzglService.getById(bzglId);

        //只有未上报（1,8）可以进行减员申报 2已经开始 4通过完成
        if (Objects.equals(auxBzgl.getState(), "2")) {
            throw new BizException(ErrCode.BZGL_PROGRESS_STARTED, "BZGL_PROGRESS_STARTED");
        }
        if (Objects.equals(auxBzgl.getState(), "4") || Objects.equals(auxBzgl.getState(), "8")) {
            throw new BizException(ErrCode.BZGL_PROGRESS_COMPLETED, "BZGL_PROGRESS_COMPLETED");
        }
        try {
            Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
            Map<String, String> vars = new HashMap<>();
            //获取当前登录人的用户ID作为用户申请ID
            vars.put("applyUserId", String.valueOf(admin.getUserId()));
            //将用户申请ID和所属流程模板ID（processTempleteId）添加至流程中
            Process process = ProcessManage.addProcess(processTempleteId, vars);
            auxBzgl.setProcessId(process.getProcessId());

            auxBzgl.setState("2");//减员状态,FJ_STATE(1：未上报；2：进行中；4：通过；8：驳回)，当通过时将档案管理状态改为离职
            auxBzgl.setProgress("1");//流程进度
            auxBzglService.update(auxBzgl);
            ProcessManage.ApprovalItemPass(process.getProcessId(), "开始申请", String.valueOf(admin.getUserId()));
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }





    //获取经办事宜列表
    @ResponseBody
    @RequestMapping(value = "/getMyhandleList")
    public Object getMyhandleList(@RequestBody String strJson,
                                  @RequestParam(value = "charset", required = false) String charset,
                                  @RequestParam(value = "loginCode", required = true) String loginCode,
                                  HttpServletRequest request) throws ValidateException {
        logger.debug(strJson);

        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson, exception)
                .addIntegerRule("myHandleState", true, 1, 2, exception)
                .addIntegerRule("page", false, 1, 1000000, exception)
                .addIntegerRule("pageSize", false, 1, 100000, exception)
                .validate();

        Admin adminLogin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();

        int myHandleState = json.getIntValue("myHandleState");
        AuxBzglSearchCriteria criteria = JSON.parseObject(strJson, AuxBzglSearchCriteria.class);
        if (myHandleState == 1) {//待处理
            criteria.setPendingUserId(String.valueOf(adminLogin.getUserId()));
        }
        if (myHandleState == 2) {//已处理
            criteria.setHandledUserId(String.valueOf(adminLogin.getUserId()));
        }

        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);

        List<AuxBzgl> auxBzglList = auxBzglService.search(criteria);
        long recordCount = auxBzglService.getSearchResultCount(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxBzglList", auxBzglList);
        return result;
    }

    //获取流程审批意见列表
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
