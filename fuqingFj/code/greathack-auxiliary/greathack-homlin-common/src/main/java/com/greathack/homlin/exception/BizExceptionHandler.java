/**
 *
 */
package com.greathack.homlin.exception;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.exception.BizException;

/**
 * 异常统一处理
 * @author greathack
 *
 */
public class BizExceptionHandler implements HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
		JSONObject json=new JSONObject();
		if(exception instanceof BizException){
			BizException e=(BizException)exception;
			json.put("errCode", e.getErrCode());
			json.put("errMsg", e.getMessage());
		}else if(exception instanceof DataIntegrityViolationException){
			json.put("errCode", 10003);
			json.put("errMsg", "INVALID_PARAMS");
		}else {
			System.out.println(exception.getMessage());
			json.put("errCode", 500);
			json.put("errMsg", "UNKNOW_ERROR");
		}
		exception.printStackTrace();
		/*if(exception instanceof ServiceException){
			
		}*/

		try {
			response.setStatus(HttpStatus.OK.value()); //设置状态码
			response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
			response.setCharacterEncoding(request.getParameter("charset")); //避免乱码
			response.setHeader("Cache-Control", "no-cache, must-revalidate");
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return null;
		return  new ModelAndView();
	}

}
