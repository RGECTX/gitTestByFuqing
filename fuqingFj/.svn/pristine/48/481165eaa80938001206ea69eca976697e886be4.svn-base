package com.greathack.homlin.controller.innerMessage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.AdminLoginSession;
import com.greathack.homlin.pojo.innerMessage.InnerMessage;
import com.greathack.homlin.pojo.innerMessage.InnerMessageSearchCriteria;
import com.greathack.homlin.pojo.innerMessage.Receive;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.serviceinterface.innerMessage.IInnerMessageService;
import com.greathack.homlin.system.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.utils.tools.TypeOption;
import com.greathack.utils.tools.Validation;
import com.greathack.utils.validate.JsonParamValidate;
import com.greathack.utils.validate.ParamterValidate;
import com.greathack.utils.validate.exception.ValidateException;
import com.greathack.utils.validate.rule.IsInteger;

/**
 *
 * @author Admin
 *
 */
@Controller
@RequestMapping(value="/innerMessage")
public class InnerMessageController {

	private static Logger logger = LoggerFactory.getLogger(InnerMessageController.class);
	private BizException exception=new BizException(10003,"INVALID_PARAMS");

	@Autowired
	private IInnerMessageService innerMessageService;

	/**
	 *
	 * @Title: add
	 * @Description: 添加
	 * @param charset
	 * @param loginCode
	 * @param strJson
	 * @return
	 * @throws ValidateException
	 * @author greathack
	 */
	@ResponseBody
	@RequestMapping(value="/add")
	public  Object add(
			@RequestParam(value = "charset", required = false) String charset,
			@RequestParam(value = "loginCode", required = true) String loginCode ,
			@RequestBody String strJson) throws ValidateException{
		//3、对json里面的参数进行校验
		JSONObject json = new JsonParamValidate(strJson,exception)
				.addStringRule("msgType",64, exception)
				.addStringRule("remark,outKey1,outKey2,title",255, exception)
				.addStringRule("bak1,bak2,attachments,content", 65535, exception)
				.validate();

		json.remove("innerMessageId");
		json.remove("createTime");
		//json转成javaBean
		InnerMessage innerMessage = JSON.toJavaObject(json, InnerMessage.class);
		try {
			Admin admin=AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
			innerMessage.setSender(admin.getUserId().toString());
			innerMessage.setSenderName(admin.getName());
			innerMessage.setAppCode(SystemConfig.getConfigData("HOMLIN_INNER_MESSAGE_APP_CODE"));
			innerMessage=innerMessageService.addInnerMessage(innerMessage);
		} catch (ServiceException e) {
			throw new BizException(e.getErrCode(),e.getMessage());
		}
		String InnerMessageJsonStr=JSON.toJSONString(innerMessage);
		json=JSON.parseObject(InnerMessageJsonStr);
		json.remove("appCode");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("innerMessage", json);
		return result;
	}

	/**
	 *
	 * @Title: delete
	 * @Description: 删除
	 * @param charset
	 * @param loginCode
	 * @param innerMessageId
	 * @return
	 * @throws ValidateException
	 * @author greathack
	 */
	@ResponseBody
	@RequestMapping(value="/delete")
	public Object delete(
			@RequestParam(value = "charset", required = false) String charset,
			@RequestParam(value = "loginCode", required = true) String loginCode ,
			@RequestParam(value="innerMessageId", required=false)Long innerMessageId) throws ValidateException{
		if(innerMessageId==null){
			logger.info("InnerMessageId必填");
			throw new BizException(240003, "ADDRESS_ID_REQUIRED");
		}
		innerMessageService.delInnerMessage(innerMessageId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		return result;
	}

	/**
	 *
	 * @Title: send
	 * @Description: 发送
	 * @param charset
	 * @param loginCode
	 * @param innerMessageId
	 * @return
	 * @throws ValidateException
	 * @author greathack
	 */
	@ResponseBody
	@RequestMapping(value="/send")
	public Object send(
			@RequestParam(value = "charset", required = false) String charset,
			@RequestParam(value = "loginCode", required = true) String loginCode ,
			@RequestParam(value="innerMessageId", required=false)Long innerMessageId) throws ValidateException{
		if(innerMessageId==null){
			logger.info("InnerMessageId必填");
			throw new BizException(240003, "ADDRESS_ID_REQUIRED");
		}

		try {
			InnerMessage innerMessage=innerMessageService.getInnerMessage(innerMessageId);
			innerMessage.setMsgState(2);
			innerMessageService.mdfyInnerMessage(innerMessage);
		} catch (ServiceException e) {
			throw new BizException(e.getErrCode(),e.getErrMsg());
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		return result;
	}



	/**
	 *
	 * @Title: update
	 * @Description: 修改
	 * @param charset
	 * @param loginCode
	 * @param strJson
	 * @return
	 * @throws ValidateException
	 * @author greathack
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public Object update(
			@RequestParam(value = "charset", required = false) String charset,
			@RequestParam(value = "loginCode", required = true) String loginCode ,
			@RequestBody String strJson) throws ValidateException{
		//3、对json里面的参数进行校验
		JSONObject json = new JsonParamValidate(strJson,exception)
				.addIntegerRule("innerMessageId",true,0,null,exception)
				.addStringRule("sender,msgType",64, exception)
				.addStringRule("remark,outKey1,outKey2,title",255, exception)
				.addStringRule("bak1,bak2,attachments,content", 65535, exception)
				.validate();
		InnerMessage innerMessage=innerMessageService.getInnerMessage(json.getIntValue("innerMessageId"));
		if(innerMessage==null){
			throw new BizException(240002, "ADDRESS_NOT_EXIST");
		}

		if(json.containsKey("receiveList")){
			innerMessage.setReceiveList(JSON.parseArray(json.getJSONArray("receiveList").toString(), Receive.class));
		}
		if(json.containsKey("msgState")){
			innerMessage.setMsgState(json.getInteger("msgState"));
		}
		if(json.containsKey("msgType")){
			innerMessage.setMsgType(json.getString("msgType"));
		}
		if(json.containsKey("attachments")){
			innerMessage.setAttachments(json.getString("attachments"));
		}
		if(json.containsKey("content")){
			innerMessage.setContent(json.getString("content"));
		}
		if(json.containsKey("remark")){
			innerMessage.setRemark(json.getString("remark"));
		}
		if(json.containsKey("title")){
			innerMessage.setTitle(json.getString("title"));
		}
		if(json.containsKey("outKey1")){
			innerMessage.setOutKey1(json.getString("outKey1"));
		}
		if(json.containsKey("outKey2")){
			innerMessage.setOutKey2(json.getString("outKey2"));
		}
		if(json.containsKey("bak1")){
			innerMessage.setBak1(json.getString("bak1"));
		}
		if(json.containsKey("bak2")){
			innerMessage.setBak2(json.getString("bak2"));
		}
		try {
			innerMessageService.mdfyInnerMessage(innerMessage);
		} catch (ServiceException e) {
			throw new BizException(e.getErrCode(),e.getMessage());
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		return result;
	}



	@ResponseBody
	@RequestMapping(value="/getInnerMessage")
	public Object getInnerMessage(
			@RequestParam(value = "charset", required = false) String charset,
			@RequestParam(value = "loginCode", required = true) String loginCode ,
			@RequestParam(value="innerMessageId", required=false)Long innerMessageId) throws ValidateException{
		if(innerMessageId==null){
			logger.info("InnerMessageId必填");
			throw new BizException(240003, "ADDRESS_ID_REQUIRED");
		}
		InnerMessage innerMessage=innerMessageService.getInnerMessage(innerMessageId);
		String InnerMessageJsonStr=JSON.toJSONString(innerMessage);
		JSONObject json=JSON.parseObject(InnerMessageJsonStr);
		json.remove("appCode");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("innerMessage", json);
		return result;
	}


	@ResponseBody
	@RequestMapping(value = "/search")
	public Object search(
			@RequestParam(value = "loginCode", required = true) String loginCode ,
			@RequestParam(value = "charset", required = false) String charset,
			@RequestBody String strJson) throws ValidateException {
		//3、对json里面的参数进行校验
		JSONObject json = new JsonParamValidate(strJson,exception)
				.addIntegerRule("sortField", false, 1,2, exception)
				.addIntegerRule("page", false, 1,1000000, exception)
				.addIntegerRule("pageSize", false, 1,10000, exception)
				.validate();

		if(json.containsKey("kwFields") && json.containsKey("keyword")){
			new ParamterValidate().addRule(new IsInteger(exception)).validate(json.getString("kwFields"));
			if(json.getIntValue("kwFields")<1 || json.getIntValue("kwFields")> 64){
				logger.info("kwFields必须在区间1--64");
				throw new BizException(10003, "INVALID_PARAMS");
			}
		}
		InnerMessageSearchCriteria criteria =JSON.parseObject(strJson, InnerMessageSearchCriteria.class);
		criteria.setAppCode(SystemConfig.getConfigData("HOMLIN_INNER_MESSAGE_APP_CODE"));
		//为空设初值
		int page = TypeOption.nullToValue(json.getString("page"), 1);
		int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
		criteria.setStartLine((page-1) * pageSize);
		criteria.setPageSize(pageSize);
		//查询
		List<InnerMessage> innerMessageList= innerMessageService.search(criteria);
		//统计记录条数
		long recordCount=innerMessageService.getSearchResultCount(criteria);
		List<JSONObject> innerMessageJsonList=new ArrayList<JSONObject>();
		for(InnerMessage innerMessage:innerMessageList){
			String innerMessageJsonStr=JSON.toJSONString(innerMessage);
			JSONObject innerMessageJson=JSON.parseObject(innerMessageJsonStr);
			innerMessageJson.remove("appCode");
			innerMessageJsonList.add(innerMessageJson);
		}
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("recordCount", recordCount);
		result.put("innerMessageList", innerMessageJsonList);
		return result;
	}
}
