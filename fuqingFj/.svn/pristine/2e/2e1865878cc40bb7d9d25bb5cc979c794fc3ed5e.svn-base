package com.greathack.homlin.common;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyCORSFilter implements Filter {

    public void destroy() {
        // TODO Auto-generated method stub

    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,x-requested-with,Authorization");
        response.setHeader("Access-Control-Allow-Credentials","true");
        chain.doFilter(req, res);
    }

    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

}
