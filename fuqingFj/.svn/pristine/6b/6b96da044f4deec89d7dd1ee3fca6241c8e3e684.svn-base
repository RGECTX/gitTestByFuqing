/**
 *
 */
package com.greathack.homlin.common;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.greathack.utils.tools.Validation;
import com.greathack.utils.validate.ParamterValidate;
import com.greathack.utils.validate.rule.NotEmpty;
import com.greathack.utils.validate.rule.NotNull;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.service.system.TokenService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author greathack
 *
 */
public class TokenCheckInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(TokenCheckInterceptor.class);

	/**
	 * 在业务处理器处理请求之前被调用
	 * 如果返回false
	 *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 如果返回true
	 *    执行下一个拦截器,直到所有的拦截器都执行完毕
	 *    再执行被拦截的Controller
	 *    然后进入拦截器链,
	 *    从最后一个拦截器往回执行所有的postHandle()
	 *    接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) throws Exception {
		JSONObject json=new JSONObject();
		String accessToken=request.getParameter("accessToken");
		if(accessToken==null){//accessToken参数为空
			logger.info("accessToken参数为空");
			throw new BizException(10006,"INVALID_ACCESS_TOKEN");
		}
		String appCode=TokenService.getAppCode(accessToken);
		if(appCode==null){//无效的令牌
			logger.info("无效的令牌");
			throw new BizException(10006,"INVALID_ACCESS_TOKEN");
		}

		//校验charset必填
		String charset=request.getParameter("charset");
		if(Validation.isEmpty(charset)){
			throw new BizException(10012,"CHARSET_IS_REQUIRED");
		}

		logger.info("accessToken验证通过");
		return true;
	}
}
