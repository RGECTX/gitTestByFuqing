package com.greathack.homlin.controller.process;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.greathack.utils.validate.JsonParamValidate;
import com.greathack.utils.validate.exception.ValidateException;
import com.greathack.utils.validate.rule.Options;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.process.ProcessNodeBranchTemplate;
import com.greathack.homlin.pojo.process.ProcessNodeTemplate;
import com.greathack.homlin.pojo.process.ProcessTemplate;
import com.greathack.homlin.service.process.ProcessTemplateManage;

/**
 *
 * @author Admin
 *
 */
@Controller
@RequestMapping(value="/processTemplate")
public class ProcessTemplatController {

	private static Logger logger = LoggerFactory.getLogger(ProcessTemplatController.class);
	private BizException exception=new BizException(210001,"INVALID_PARAMS");


	//新增审批流程模板
	@ResponseBody
	@RequestMapping(value="/addProcessTemplate")
	public  Object addProcessTemplate(@RequestBody String strJson,
									  @RequestParam(value = "charset", required = false) String charset,
									  @RequestParam(value = "loginCode", required = true) String loginCode ,
									  HttpServletRequest request) throws ValidateException{


		//2、对传进来的参数进行转码
		try {
			//对strJson进行转码
			strJson=URLDecoder.decode(strJson, charset);
		} catch (UnsupportedEncodingException e1) {
			logger.info("无效的charset");
			throw new BizException(10013, "INVALID_CHARSET");
		}

		JSONObject json = new JsonParamValidate(strJson,exception)
				.addStringRule("templateCode", true,true,64, exception)
				.addStringRule("category",64, exception)
				.addStringRule("processName", true,true,255, exception)
				.addStringRule("approvalImpl", 255, exception)
				.validate();

		ProcessTemplate processTemplate;
		try {
			processTemplate = ProcessTemplateManage.addProcessTemplate(json.getString("processName"),json.getString("templateCode"),json.getString("category"),json.getString("approvalImpl"));
		} catch (ServiceException e) {
			throw new BizException(e.getErrCode(),e.getErrMsg());
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("processTemplate", processTemplate);
		return result;
	}

	//删除审批流程模板
	@ResponseBody
	@RequestMapping(value="/deleteProcessTemplate")
	public  Object deleteProcessTemplate(
			@RequestParam(value = "charset", required = false) String charset,
			@RequestParam(value = "loginCode", required = true) String loginCode ,
			@RequestParam(value = "processTemplateId", required = true) long processTemplateId ,
			HttpServletRequest request) throws ValidateException{

		ProcessTemplateManage.deleteProcessTemplate(processTemplateId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		return result;
	}

	//修改审批流程模板
	@ResponseBody
	@RequestMapping(value="/updateProcessTemplate")
	public  Object updateProcessTemplate(@RequestBody String strJson,
										 @RequestParam(value = "charset", required = false) String charset,
										 @RequestParam(value = "loginCode", required = true) String loginCode ,
										 HttpServletRequest request) throws ValidateException{


		//2、对传进来的参数进行转码
		try {
			//对strJson进行转码
			strJson=URLDecoder.decode(strJson, charset);
		} catch (UnsupportedEncodingException e1) {
			logger.info("无效的charset");
			throw new BizException(10013, "INVALID_CHARSET");
		}

		JSONObject json = new JsonParamValidate(strJson,exception)
				.addLongRule("processTemplateId", true, 0L, null, exception)
				.addStringRule("templateCode,category", 64, exception)
				.addStringRule("processName,approvalImpl", 255, exception)
				.validate();

		ProcessTemplate processTemplate=ProcessTemplateManage.getProcessTemplate(json.getLong("processTemplateId"));
		if(json.containsKey("processName")){
			processTemplate.setProcessName(json.getString("processName"));
		}
		if(json.containsKey("templateCode")){
			processTemplate.setTemplateCode(json.getString("templateCode"));
		}
		if(json.containsKey("category")){
			processTemplate.setCategory(json.getString("category"));
		}
		if(json.containsKey("approvalImpl")){
			processTemplate.setApprovalImpl(json.getString("approvalImpl"));
		}
		try {
			ProcessTemplateManage.updateProcessTemplate(processTemplate);
		} catch (ServiceException e) {
			throw new BizException(e.getErrCode(),e.getErrMsg());
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		return result;
	}

	//新增审批流程节点模板
	@ResponseBody
	@RequestMapping(value="/addProcessNodeTemplate")
	public  Object addProcessNodeTemplate(@RequestBody String strJson,
										  @RequestParam(value = "charset", required = false) String charset,
										  @RequestParam(value = "loginCode", required = true) String loginCode ,
										  HttpServletRequest request) throws ValidateException{


		//2、对传进来的参数进行转码
		try {
			//对strJson进行转码
			strJson=URLDecoder.decode(strJson, charset);
		} catch (UnsupportedEncodingException e1) {
			logger.info("无效的charset");
			throw new BizException(10013, "INVALID_CHARSET");
		}

		JSONObject json = new JsonParamValidate(strJson,exception)
				.addLongRule("processTemplateId", true, 0L, null, exception)
				.addIntegerRule("passRequire", true, new Options("1,2,3"), exception)
				.addIntegerRule("passNum", true, 1,null, exception)
				.addStringRule("approvalRoleId", true,true,1024, exception)
				.addStringRule("nodeName", true,true,64, exception)
				.addStringRule("nodeEventImpl", 255, exception)
				.validate();

		long processTemplateId=json.getLongValue("processTemplateId");
		int passRequire=json.getIntValue("passRequire");
		int passNum=json.getIntValue("passNum");
		int approvalType=json.getIntValue("approvalType");
		String approvalRoleId=json.getString("approvalRoleId");
		String nodeName=json.getString("nodeName");
		String nodeEventImpl=json.getString("nodeEventImpl");
		String beforeParameter=json.getString("beforeParameter");
		String afterParameter=json.getString("afterParameter");
		ProcessNodeTemplate processNodeTemplate;
		try {
			processNodeTemplate = ProcessTemplateManage.addProcessNodeTemplate(processTemplateId, passRequire, passNum, approvalType,approvalRoleId, nodeName,nodeEventImpl,beforeParameter,afterParameter);
		} catch (ServiceException e) {
			throw new BizException(e.getErrCode(),e.getErrMsg());
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("processNodeTemplate", processNodeTemplate);
		return result;
	}

	//删除流程节点模板
	@ResponseBody
	@RequestMapping(value="/deleteProcessNodeTemplate")
	public  Object deleteProcessNodeTemplate(
			@RequestParam(value = "charset", required = false) String charset,
			@RequestParam(value = "loginCode", required = true) String loginCode ,
			@RequestParam(value = "processNodeTemplateId", required = true) long processNodeTemplateId ,
			HttpServletRequest request) throws ValidateException{

		ProcessTemplateManage.deleteProcessNodeTemplate(processNodeTemplateId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		return result;
	}


	//新增审批流程节点分支模板
	@ResponseBody
	@RequestMapping(value="/addProcessNodeBranchTemplate")
	public  Object addProcessNodeBranchTemplate(@RequestBody String strJson,
												@RequestParam(value = "charset", required = false) String charset,
												@RequestParam(value = "loginCode", required = true) String loginCode ,
												HttpServletRequest request) throws ValidateException{


		//2、对传进来的参数进行转码
		try {
			//对strJson进行转码
			strJson=URLDecoder.decode(strJson, charset);
		} catch (UnsupportedEncodingException e1) {
			logger.info("无效的charset");
			throw new BizException(10013, "INVALID_CHARSET");
		}

		JSONObject json = new JsonParamValidate(strJson,exception)
				.addLongRule("processNodeTemplateId", true, 0L, null, exception)
				.addStringRule("condition", false,false,255, exception)
				.addStringRule("nextNodeCode", true,true,64, exception)
				.validate();

		long processNodeTemplateId=json.getLongValue("processNodeTemplateId");
		String condition=json.getString("condition");
		String nextNodeCode=json.getString("nextNodeCode");
		ProcessNodeBranchTemplate processNodeBranchTemplate;
		try {
			processNodeBranchTemplate = ProcessTemplateManage.addProcessNodeBranchTemplate(processNodeTemplateId, condition, nextNodeCode);
		} catch (ServiceException e) {
			throw new BizException(e.getErrCode(),e.getErrMsg());
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("processNodeBranchTemplate", processNodeBranchTemplate);
		return result;
	}

	//删除流程节点分支模板
	@ResponseBody
	@RequestMapping(value="/deleteProcessNodeBranchTemplate")
	public  Object deleteProcessNodeBranchTemplate(
			@RequestParam(value = "charset", required = false) String charset,
			@RequestParam(value = "loginCode", required = true) String loginCode ,
			@RequestParam(value = "processNodeBranchTemplateId", required = true) long processNodeBranchTemplateId ,
			HttpServletRequest request) throws ValidateException{

		ProcessTemplateManage.deleteProcessNodeBranchTemplate(processNodeBranchTemplateId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		return result;
	}

	//获取审批流程模板列表
	@ResponseBody
	@RequestMapping(value="/getProcessTemplateList")
	public  Object getProcessTemplateList(
			@RequestParam(value = "charset", required = false) String charset,
			@RequestParam(value = "loginCode", required = true) String loginCode ,
			HttpServletRequest request) throws ValidateException{

		List<ProcessTemplate> processTemplateList=ProcessTemplateManage.getAllProcessTemplate();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("processTemplateList", processTemplateList);
		return result;
	}

	//获取审批流程节点模板列表
	@ResponseBody
	@RequestMapping(value="/getProcessNodeTemplateList")
	public  Object getProcessNodeTemplateList(
			@RequestParam(value = "charset", required = false) String charset,
			@RequestParam(value = "loginCode", required = true) String loginCode ,
			@RequestParam(value = "processTemplateId", required = true) long processTemplateId ,
			HttpServletRequest request) throws ValidateException{

		List<ProcessNodeTemplate> processNodeTemplateList=ProcessTemplateManage.getProcessNodeTemplateList(processTemplateId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("processNodeTemplateList", processNodeTemplateList);
		return result;
	}

	//获取审批流程节点分支模板列表
	@ResponseBody
	@RequestMapping(value="/getProcessNodeBranchTemplateList")
	public  Object getProcessNodeBranchTemplateList(
			@RequestParam(value = "charset", required = false) String charset,
			@RequestParam(value = "loginCode", required = true) String loginCode ,
			@RequestParam(value = "processNodeTemplateId", required = false) Long processNodeTemplateId ,
			HttpServletRequest request) throws ValidateException{
		if(processNodeTemplateId==null){
			processNodeTemplateId=0L;
		}
		List<ProcessNodeBranchTemplate> processNodeBranchTemplateList=ProcessTemplateManage.getProcessNodeBranchTemplateList(processNodeTemplateId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("processNodeBranchTemplateList", processNodeBranchTemplateList);
		return result;
	}

	//修改流程节点分支模板
	@ResponseBody
	@RequestMapping(value="/updateProcessNodeBranchTemplate")
	public  Object updateProcessNodeBranchTemplate(@RequestBody String strJson,
												   @RequestParam(value = "charset", required = false) String charset,
												   @RequestParam(value = "loginCode", required = true) String loginCode ,
												   HttpServletRequest request) throws ValidateException{

		JSONObject json = new JsonParamValidate(strJson,exception)
				.addLongRule("branchId", true, 0L, null, exception)
				.addStringRule("cond", 1024, exception)
				.addStringRule("nextNodeCode", 255, exception)
				.addIntegerRule("sort",false,0,null,exception)
				.validate();

		ProcessNodeBranchTemplate processNodeBranchTemplate= ProcessTemplateManage.getProcessNodeBranchTemplate(json.getLong("branchId"));
		if(json.containsKey("condition")){
			processNodeBranchTemplate.setCond(json.getString("condition"));
		}
		if(json.containsKey("nextNodeCode")){
			processNodeBranchTemplate.setNextNodeCode(json.getString("nextNodeCode"));
		}
		if(json.containsKey("sort")){
			processNodeBranchTemplate.setSort(json.getInteger("sort"));
		}
		ProcessTemplateManage.updateProcessNodeBranchTemplate(processNodeBranchTemplate);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		return result;
	}

	//修改审批流程节点模板
	@ResponseBody
	@RequestMapping(value="/updateProcessNodeTemplate")
	public  Object updateProcessTemplateNode(@RequestBody String strJson,
											 @RequestParam(value = "charset", required = false) String charset,
											 @RequestParam(value = "loginCode", required = true) String loginCode ,
											 HttpServletRequest request) throws ValidateException{

		JSONObject json = new JsonParamValidate(strJson,exception)
				.addLongRule("processNodeId", true, 0L, null, exception)
				.addStringRule("nodeName,nodeCode,approvalRoleId,nodeEventImpl", 255, exception)
				.addIntegerRule("passRequire",false,1,3,exception)
				.addIntegerRule("approvalType",false,1,2,exception)
				.addIntegerRule("passNum,sort",false,0,null,exception)
				.validate();

		ProcessNodeTemplate processNodeTemplate= ProcessTemplateManage.getProcessNodeTemplate(json.getLong("processNodeId"));
		if(json.containsKey("nodeName")){
			processNodeTemplate.setNodeName(json.getString("nodeName"));
		}
		if(json.containsKey("nodeCode")){
			processNodeTemplate.setNodeCode(json.getString("nodeCode"));
		}
		if(json.containsKey("approvalRoleId")){
			processNodeTemplate.setApprovalRoleId(json.getString("approvalRoleId"));
		}
		if(json.containsKey("nodeEventImpl")){
			processNodeTemplate.setNodeEventImpl(json.getString("nodeEventImpl"));
		}
		if(json.containsKey("beforeParameter")){
			processNodeTemplate.setBeforeParameter(json.getString("beforeParameter"));
		}
		if(json.containsKey("afterParameter")){
			processNodeTemplate.setAfterParameter(json.getString("afterParameter"));
		}
		if(json.containsKey("passRequire")){
			processNodeTemplate.setPassRequire(json.getInteger("passRequire"));
		}
		if(json.containsKey("approvalType")){
			processNodeTemplate.setApprovalType(json.getInteger("approvalType"));
		}
		if(json.containsKey("passNum")){
			processNodeTemplate.setPassNum(json.getInteger("passNum"));
		}
		if(json.containsKey("sort")){
			processNodeTemplate.setSort(json.getInteger("sort"));
		}
		ProcessTemplateManage.updateProcessNodeTemplate(processNodeTemplate);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		return result;
	}

}
