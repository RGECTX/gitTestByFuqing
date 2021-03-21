package com.greathack.homlin.controller.auxiliary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.auxiliary.AuxZljd;
import com.greathack.homlin.pojo.auxiliary.AuxZljdSearchCriteria;
import com.greathack.homlin.pojo.process.ApprovalItem;
import com.greathack.homlin.pojo.process.Process;
import com.greathack.homlin.pojo.process.ProcessNodeTemplate;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.service.process.ProcessManage;
import com.greathack.homlin.service.process.ProcessTemplateManage;
import com.greathack.homlin.serviceinterface.IAdminService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxZljdService;
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
 * 招录审批流程
 */
@Controller
@RequestMapping(value = "/zlProcess")
public class ZlProcessController {

    private static Logger logger = LoggerFactory.getLogger(ZlProcessController.class);
    private BizException exception = new BizException(210001, "INVALID_PARAMS");

    @Autowired
    private IAuxZljdService auxZljdService;
    @Autowired
    private IAdminService adminService;
    /*@Autowired
    private IProcessHistoryRecordService processHistoryRecordService;*/


    //开始招录申请流程
    @ResponseBody
    @RequestMapping(value = "/apply")
    public Object apply(
            @RequestParam(value = "loginCode") String loginCode,
            @RequestParam(value = "zlId", required = false) Long zlId,
            @RequestParam(value = "processTempleteId", required = false) Long processTempleteId,
            HttpServletRequest request) throws ValidateException {
        new ParamterValidate()
                .addRule(new NotNull(exception))
                .addRule(new LongLeast(1L, true, exception))
                .validate(zlId)
                .validate(processTempleteId);
        AuxZljd auxZljd = auxZljdService.getById(zlId);

        //只有未上报（1,8）可以进行减员流程 2已经开始 4通过完成
        if (Objects.equals(auxZljd.getState(), "2")) {
            throw new BizException(ErrCode.ZLJD_PROGRESS_STARTED, "ZLJD_PROGRESS_STARTED");
        }
        if (Objects.equals(auxZljd.getState(), "4") || Objects.equals(auxZljd.getState(), "8")) {
            throw new BizException(ErrCode.ZLJD_PROGRESS_COMPLETED, "ZLJD_PROGRESS_COMPLETED");
        }
        try {
            Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
            Map<String, String> vars = new HashMap<>();
            //获取当前登录人的用户ID作为用户申请ID
            vars.put("applyUserId", String.valueOf(admin.getUserId()));
            //将用户申请ID和所属流程模板ID（processTempleteId）添加至流程中
            Process process = ProcessManage.addProcess(processTempleteId, vars);
            auxZljd.setProcessId(process.getProcessId());

            auxZljd.setState("2");//状态,FJ_STATE（1未上报、2进行中、4通过、8驳回）
            auxZljd.setProgress("1");//流程进度
            auxZljdService.update(auxZljd);
            ProcessManage.ApprovalItemPass(process.getProcessId(), "开始申请", String.valueOf(admin.getUserId()));//审批人对审批项目审批通过
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
        AuxZljdSearchCriteria criteria = JSON.parseObject(strJson, AuxZljdSearchCriteria.class);
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

        List<AuxZljd> auxZljdList = auxZljdService.search(criteria);
        long recordCount = auxZljdService.getSearchResultCount(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxZljdList", auxZljdList);
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
