/**
 *
 */
package com.greathack.homlin.controller.system;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author greathack
 *
 */
@Controller
@RequestMapping(value="/error")
public class ErrorController {

	@ResponseBody
	@RequestMapping(value="/500")
	public Object error_500(){
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("errCode", 500);
		result.put("errMsg","UNKNOW_ERROR");
		return result;
	}

	@ResponseBody
	@RequestMapping(value="/404")
	public Object error_404(){
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("errCode", 404);
		result.put("errMsg","RESOURCES_NOT_EXIST");
		return result;
	}


	@ResponseBody
	@RequestMapping(value="/400")
	public Object error_400(){
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("errCode", 400);
		result.put("errMsg","BAD_REQUEST");
		return result;
	}


	@ResponseBody
	@RequestMapping(value="/405")
	public Object error_405(){
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("errCode", 405);
		result.put("errMsg","REQUEST_METHOD_ERROR");
		return result;
	}

}
