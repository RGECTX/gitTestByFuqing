/**
 * 
 */
package com.greathack.homlin.common;

import com.greathack.utils.tools.Base64Utils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author greathack
 *
 */
public class ImageFilter implements Filter {

	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO 自动生成的方法存根

	}

	
	//将对图片的请求返回图片的base64编码
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		String img="";
		try {
			String imgPath=req.getSession().getServletContext().getRealPath(req.getRequestURI().replace(req.getContextPath(), ""));
			img = Base64Utils.encodeFile(imgPath);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		response.getWriter().write("data:image/jpeg;base64,"+img);

	}

	
	@Override
	public void destroy() {
		// TODO 自动生成的方法存根

	}

}
