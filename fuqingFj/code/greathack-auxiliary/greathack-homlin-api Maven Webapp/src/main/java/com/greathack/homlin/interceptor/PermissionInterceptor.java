/**
 *
 */
package com.greathack.homlin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.greathack.homlin.pojo.permission.PowerItem;
import com.greathack.utils.tools.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.pojo.AdminLoginSession;
import com.greathack.homlin.pojo.permission.HasPower;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.serviceinterface.IAdminPermissionService;
import com.greathack.homlin.system.SystemConfig;

/**
 * @author greathack
 *
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);

    @Autowired
    private IAdminPermissionService adminPermissionService;

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
        String loginCode=request.getParameter("loginCode");
        AdminLoginSession loginSession=AdminLoginSessionService.getLoginSession(loginCode);
        if(loginSession==null || loginSession.getAdmin()==null){//无效的登录码
            logger.info("请先登录");
            throw new BizException(200061,"PLEASE_LOGIN");
        }
        String referer=null;
        /*String referer=request.getHeader("Referer");
        if(referer!=null){
            referer=referer.substring(referer.lastIndexOf("/"));
        }*/

        String contextPath=SystemConfig.getConfigData("CONTEXT_PATH");//上下文地址
        String resource=request.getRequestURI();
        //System.out.println(resource);
        resource=resource.replaceFirst(contextPath, "");
        //System.out.println(referer);
        //System.out.println(resource);

        String userId=String.valueOf(loginSession.getAdmin().getUserId());
        String orgId=String.valueOf(loginSession.getAdmin().getAmUnitId());
        HasPower hasPower=adminPermissionService.hasResourceAccessPower(userId, referer, resource);
        if(hasPower.getHasPower()){
            PowerItem att=new PowerItem();
            att.setParameter("1<>1");
            hasPower.getPowerItemList().add(att);
            for(PowerItem powerItem:hasPower.getPowerItemList()){
                String parameter=powerItem.getParameter();
                if(!Validation.isEmpty(powerItem.getParameter())){
                    powerItem.setParameter(powerItem.getParameter().replaceAll("#\\{currentUser\\.userId\\}",userId));
                    powerItem.setParameter(powerItem.getParameter().replaceAll("#\\{currentUser\\.orgId\\}",orgId));
                    powerItem.setParameter("("+powerItem.getParameter()+")");
                }
            }
            request.setAttribute("powerItemList", hasPower.getPowerItemList());
            return true;
        }else{
            logger.info("没权限");
            throw new BizException(200063,"NO_PERMISSION");

        }
        //return false;
    }
}
