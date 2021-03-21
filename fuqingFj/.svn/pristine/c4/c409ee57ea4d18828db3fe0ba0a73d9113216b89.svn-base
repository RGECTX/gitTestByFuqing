/**
 *
 */
package com.greathack.homlin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.greathack.utils.tools.Validation;
import com.greathack.homlin.common.Common;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.pojo.AdminLoginSession;
import com.greathack.homlin.service.AdminLoginSessionService;

/**
 * @author greathack
 *
 */
public class LoginCodeCheckInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(LoginCodeCheckInterceptor.class);

    //  @Autowired
    // private IUserService userService;

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

        //* 为了开发方便先注释掉，后面得开起来验证登录码
        String loginCode=request.getParameter("loginCode");
        if(Validation.isEmpty(loginCode)){//accessToken参数为空
            logger.info("loginCode参数为空");
            throw new BizException(200062,"LOGINCODE_IS_REQUIRE");
        }
        AdminLoginSession loginSession=AdminLoginSessionService.getLoginSession(loginCode);
        if(loginSession==null || loginSession.getAdmin()==null){//无效的登录码
            logger.info("请先登录");
            throw new BizException(200061,"PLEASE_LOGIN");
        }
        long expireTime=loginSession.getExpireTime();
        if(Common.getCurrentTime()>expireTime){//令牌过期
            logger.info("请先登录");
            throw new BizException(200061,"PLEASE_LOGIN");
        }
        String adminLoginCode=AdminLoginSessionService.getLoginCode(loginSession.getAdmin().getLoginName());
        if(adminLoginCode==null){
            AdminLoginSessionService.deleteLoginSession(loginCode);
            logger.info("请先登录");
            throw new BizException(200061,"PLEASE_LOGIN");
        }
        if(!loginCode.equals(adminLoginCode)){
            AdminLoginSessionService.deleteLoginSession(loginCode);
            logger.info("您的帐号在其他地方登录");
            throw new BizException(200063,"ADMIN_LOGIN_IN_OTHER_PLACE");
        }
        //验证通过，重新计算超时时间
        loginSession.setExpireTime(Common.getLoginCodeExpireTime());
        AdminLoginSessionService.saveLoginSession(loginCode, loginSession);
        /*LoginSession userLogin=SystemConfig.getUserLogin(loginCode);
        if(userLogin==null || userLogin.getUser()==null){//无效的登录码
            logger.info("请先登录");
            throw new BizException(200061,"PLEASE_LOGIN");
        }
        long expireTime=userLogin.getExpireTime();
        if(Common.getCurrentTime()>expireTime){//令牌过期
            SystemConfig.delUserLogin(loginCode);
            logger.info("请先登录");
            throw new BizException(200061,"PLEASE_LOGIN");
        }
        //验证通过，重新计算超时时间
        userLogin.setExpireTime(Common.getLoginCodeExpireTime());
        SystemConfig.setUserLogin(loginCode, userLogin);*/
        logger.info("loginCode验证通过");
        return true;
    }
}
