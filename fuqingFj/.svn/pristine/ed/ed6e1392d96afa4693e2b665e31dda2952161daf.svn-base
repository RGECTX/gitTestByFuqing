package com.greathack.homlin.controller.innerMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.innerMessage.Receive;
import com.greathack.homlin.pojo.innerMessage.ReceiveSearchCriteria;
import com.greathack.homlin.serviceinterface.innerMessage.IReceiveService;
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
import com.greathack.utils.validate.rule.Options;

/**
 *
 * @author Admin
 *
 */
@Controller
@RequestMapping(value="/receive")
public class ReceiveController {

	private static Logger logger = LoggerFactory.getLogger(ReceiveController.class);
	private BizException exception=new BizException(10003,"INVALID_PARAMS");

	@Autowired
	private IReceiveService receiveService;

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
				.addStringRule("recipient,receiveState",64, exception)
				.validate();

		json.remove("receiveId");
		json.remove("createTime");
		//json转成javaBean
		Receive receive = JSON.toJavaObject(json, Receive.class);
		try {
			receive.setAppCode(SystemConfig.getConfigData("HOMLIN_INNER_MESSAGE_APP_CODE"));
			receive=receiveService.addReceive(receive);
		} catch (ServiceException e) {
			throw new BizException(e.getErrCode(),e.getMessage());
		}
		String ReceiveJsonStr=JSON.toJSONString(receive);
		json=JSON.parseObject(ReceiveJsonStr);
		json.remove("appCode");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("receive", json);
		return result;
	}

	/**
	 *
	 * @Title: delete
	 * @Description: 删除
	 * @param charset
	 * @param loginCode
	 * @param receiveId
	 * @return
	 * @throws ValidateException
	 * @author greathack
	 */
	@ResponseBody
	@RequestMapping(value="/delete")
	public Object delete(
			@RequestParam(value = "charset", required = false) String charset,
			@RequestParam(value = "loginCode", required = true) String loginCode ,
			@RequestParam(value="receiveId", required=false)Long receiveId) throws ValidateException{
		if(receiveId==null){
			logger.info("ReceiveId必填");
			throw new BizException(240003, "ADDRESS_ID_REQUIRED");
		}
		receiveService.delReceive(receiveId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		return result;
	}

	/**
	 *
	 * @Title: read
	 * @Description: 读
	 * @param charset
	 * @param loginCode
	 * @param receiveId
	 * @return
	 * @throws ValidateException
	 * @author greathack
	 */
	@ResponseBody
	@RequestMapping(value="/read")
	public Object read(
			@RequestParam(value = "charset", required = false) String charset,
			@RequestParam(value = "loginCode", required = true) String loginCode ,
			@RequestParam(value="receiveId", required=false)Long receiveId) throws ValidateException{
		if(receiveId==null){
			logger.info("ReceiveId必填");
			throw new BizException(240003, "ADDRESS_ID_REQUIRED");
		}
		receiveService.read(receiveId);
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
				.addIntegerRule("receiveId",true,0,null,exception)
				.addStringRule("recipient,receiveState",64, exception)
				.addIntegerRule("receiveType",true,1,2, exception)
				.validate();
		Receive receive=receiveService.getReceive(json.getIntValue("receiveId"));
		if(receive==null){
			throw new BizException(240002, "ADDRESS_NOT_EXIST");
		}

		if(json.containsKey("recipient")){
			receive.setRecipient(json.getString("recipient"));
		}
		if(json.containsKey("receiveState")){
			receive.setReceiveState(json.getInteger("receiveState"));
		}

		try {
			receiveService.mdfyReceive(receive);
		} catch (ServiceException e) {
			throw new BizException(e.getErrCode(),e.getMessage());
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		return result;
	}



	@ResponseBody
	@RequestMapping(value="/getReceive")
	public Object getReceive(
			@RequestParam(value = "charset", required = false) String charset,
			@RequestParam(value = "loginCode", required = true) String loginCode ,
			@RequestParam(value="receiveId", required=false)Long receiveId) throws ValidateException{
		if(receiveId==null){
			logger.info("ReceiveId必填");
			throw new BizException(240003, "ADDRESS_ID_REQUIRED");
		}
		Receive receive=receiveService.getReceive(receiveId);
		String ReceiveJsonStr=JSON.toJSONString(receive);
		JSONObject json=JSON.parseObject(ReceiveJsonStr);
		json.remove("appCode");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("receive", json);
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

		ReceiveSearchCriteria criteria =JSON.parseObject(strJson, ReceiveSearchCriteria.class);
		criteria.setAppCode(SystemConfig.getConfigData("HOMLIN_INNER_MESSAGE_APP_CODE"));
		//为空设初值
		int page = TypeOption.nullToValue(json.getString("page"), 1);
		int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
		criteria.setStartLine((page-1) * pageSize);
		criteria.setPageSize(pageSize);
		//查询
		List<Receive> receiveList= receiveService.search(criteria);
		//统计记录条数
		long recordCount=receiveService.getSearchResultCount(criteria);
		List<JSONObject> receiveJsonList=new ArrayList<JSONObject>();
		for(Receive receive:receiveList){
			String receiveJsonStr=JSON.toJSONString(receive);
			JSONObject receiveJson=JSON.parseObject(receiveJsonStr);
			receiveJson.remove("appCode");
			receiveJsonList.add(receiveJson);
		}
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("recordCount", recordCount);
		result.put("receiveList", receiveJsonList);
		return result;
	}
}
