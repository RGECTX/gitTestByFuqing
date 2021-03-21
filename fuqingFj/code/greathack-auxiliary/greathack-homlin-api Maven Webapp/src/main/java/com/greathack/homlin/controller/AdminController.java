/**
 * 
 */
package com.greathack.homlin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.common.Common;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.*;
import com.greathack.homlin.pojo.login.LoginInstance;
import com.greathack.homlin.pojo.permission.PowerItem;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.service.RoleService;
import com.greathack.homlin.serviceinterface.IAdminPermissionService;
import com.greathack.homlin.serviceinterface.IAdminService;
import com.greathack.homlin.serviceinterface.IRoleService;
import com.greathack.homlin.serviceinterface.am.IAmUnitService;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.TypeOption;
import com.greathack.utils.tools.Validation;
import com.greathack.utils.validate.JsonParamValidate;
import com.greathack.utils.validate.ParamterValidate;
import com.greathack.utils.validate.exception.ValidateException;
import com.greathack.utils.validate.rule.IsInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author greathack
 *
 */
@Controller
@RequestMapping(value = "/user")
public class AdminController {

	private static Logger logger = LoggerFactory.getLogger(AdminController.class);
	private BizException exception=new BizException(200001,"INVALID_PARAMS");
	
	@Autowired
	private IAdminService adminService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IAmUnitService amUnitService;
    
	@Autowired
    private IAdminPermissionService adminPermissionService;
    
    @ResponseBody
    @RequestMapping(value = "/add")
    public Object add( @RequestParam(value = "charset", required = false) String charset,
    		@RequestParam(value = "loginCode", required = true) String loginCode ,
    		@RequestBody String strJson,HttpServletRequest request ) throws ValidateException {
        
        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson=URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
        .addStringRule("loginName,password,amUnitId,name",true,true, 64, exception)
        .addIntegerRule("sex", false, 1, 2, exception)
        .addStringRule("idcard",false,false, 30, exception)
        .addStringRule("mobile",false,false, 255, exception)
        .validate();
        //json转成javaBean
        json.remove("userId");
        json.remove("createTime");
        Admin admin = JSON.toJavaObject(json, Admin.class);          
      	  
        try {
            admin=adminService.add(admin);
        } catch (ServiceException e) {
        	logger.info(e.getErrMsg());
        	e.printStackTrace();
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }       
        
        String jsonString = JSON.toJSONString(admin);
        JSONObject adminJsObj = JSON.parseObject(jsonString);
        String amUnitId=admin.getAmUnitId();
        if(Validation.isEmpty(amUnitId)){
            amUnitId="0";
        }
        AmUnit org=amUnitService.getOrgs().get(Long.valueOf(amUnitId));
        if(org!=null){
	        adminJsObj.put("unitName", org.getOrgName());
	        adminJsObj.put("unitCode", org.getOrgCode());
        }
		JSONArray roleArray=new JSONArray();
		try {
			List<String> roleIdList=adminPermissionService.getRoleIdsByUserId(String.valueOf(admin.getUserId()));
			for(String roleId:roleIdList){
				JSONObject roleJson=new JSONObject();
				roleJson.put("roleId", roleId);
				roleJson.put("roleName", RoleService.getRoles().get(Long.parseLong(roleId)));
				roleArray.add(roleJson);
			}
			adminJsObj.put("roleList", roleArray);			
			
		} catch (ServiceException e) {
			logger.info("errCode:"+e.getErrCode()+",errMsg:"+e.getErrMsg());
		}
        adminJsObj.remove("password");
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("user", adminJsObj);
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete(
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "userId", required = false) Long userId,HttpServletRequest request) throws ValidateException {
        if(userId==null){
            logger.info("userId必填");
            throw new BizException(300011, "USERID_IS_REQUIRED");
        }
        Admin adminById = adminService.getAdminById(userId);
        try {
            adminService.delete(userId);
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }
       
        String content = "【删除用户】:";
        if(adminById!=null){
        	content = content+ adminById.getLoginName();
        }
        
        Map<String, Object> result = new HashMap<String, Object>();      
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");

        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/unlock")
    public Object unlock(
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "adminId", required = false) Long adminId) throws ValidateException {
        if(adminId==null){
            logger.info("adminId必填");
            throw new BizException(300011, "ADMINID_IS_REQUIRE");
        }

        Map<String, Object> result = new HashMap<String, Object>();
        try {
            adminService.mdfyState(adminId, Admin.State.normal);
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");

        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/lock")
    public Object lock(
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "adminId", required = false) Long adminId) throws ValidateException {
        if(adminId==null){
            logger.info("adminId必填");
            throw new BizException(300011, "ADMINID_IS_REQUIRE");
        }

        Map<String, Object> result = new HashMap<String, Object>();
        try {
            adminService.mdfyState(adminId, Admin.State.locked);
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");

        return result;
    }
    
    /**
     * 
     * 修改自己的资料
     * @param charset
     * @param loginCode
     * @param strJson
     * @param request
     * @return
     * @throws ValidateException
     */
    @ResponseBody
    @RequestMapping(value = "/updateSelf")
    public Object updateSelf(
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestBody String strJson,HttpServletRequest request) throws ValidateException {

    	//2、对传进来的参数进行转码
    	try {
    		//对strJson进行转码
    		strJson=URLDecoder.decode(strJson, charset);
    	} catch (UnsupportedEncodingException e1) {
    		logger.info("无效的charset");
    		throw new BizException(10013, "INVALID_CHARSET");
    	}

        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
        .addStringRule("name",64, exception)
        .addIntegerRule("sex", false, 1, 2, exception)
        .validate();

        Admin admin=AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
    	admin=adminService.getAdminById(admin.getUserId());
    	if(admin==null || admin.getState()==Admin.State.deleted.value()){
    		logger.info("用户不存在");
    		throw new BizException(200067,"ADMIN_NOT_EXIST");
    	}
    	
    	if(json.containsKey("name")){
    		admin.setName(json.getString("name"));
    	}
    	
    	if(json.containsKey("sex")){
    		admin.setSex(json.getInteger("sex"));
    	}

    	try {
    		adminService.mdfy(admin);
    	} catch (ServiceException e) {
    		throw new BizException(e.getErrCode(), e.getMessage());
    	}       
    	Map<String, Object> result = new HashMap<String, Object>();
    	result.put("errCode", 0);
    	result.put("errMsg", "SUCCESS");

    	return result;
    }



    /**
     *
     * 修改别人的资料
     * @param charset
     * @param loginCode
     * @param strJson
     * @param request
     * @return
     * @throws ValidateException
     */
    @ResponseBody
    @RequestMapping(value = "/update")
    public Object update(
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestBody String strJson,HttpServletRequest request) throws ValidateException {

        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson=URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
                .addStringRule("userId", true, true, 64, exception)
                .addStringRule("amUnitId,name",64, exception)
                .addIntegerRule("sex", false, 1, 2, exception)
                .addStringRule("password",255, exception)
                .addStringRule("idcard",false,false, 30, exception)
                .addStringRule("mobile",false,false, 255, exception)
                .validate();

        Long adminId=json.getLong("userId");
        if(adminId==null){
            logger.info("userId不能为空");
            throw new BizException(300011, "USERID_IS_REQUIRE");
        }
        Admin admin=adminService.getAdminById(adminId);
        if(admin==null || admin.getState()==Admin.State.deleted.value()){
            logger.info("用户不存在");
            throw new BizException(200067,"ADMIN_NOT_EXIST");
        }

        if(json.containsKey("amUnitId")){
            admin.setAmUnitId(json.getString("amUnitId"));
        }

        if(json.containsKey("name")){
            admin.setName(json.getString("name"));
        }

        if(json.containsKey("sex")){
            admin.setSex(json.getInteger("sex"));
        }
        if(json.containsKey("idcard")){
            admin.setIdcard(json.getString("idcard"));
        }
        if(json.containsKey("mobile")){
            admin.setMobile(json.getString("mobile"));
        }
        try {
            adminService.mdfy(admin);
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getMessage());
        }

        if(json.containsKey("password") && !Validation.isEmpty(json.getString("password"))){
            try {
                adminService.mdfyPwd(admin.getLoginName(), json.getString("password"));
            } catch (ServiceException e) {
                throw new BizException(e.getErrCode(), e.getErrMsg());
            }
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");

        return result;


    }


    @ResponseBody
    @RequestMapping(value = "/getUser")
    public Object getUser(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "loginCode", required = true) String loginCode)
            throws ValidateException {
        if(userId==null){
            logger.info("userId必填");
            throw new BizException(300011, "USERID_IS_REQUIRED");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        Admin admin=adminService.getAdminById(userId);        
        if(admin==null || admin.getState()==Admin.State.deleted.value()){
            logger.info("用户不存在");
            throw new BizException(300012,"USER_NOT_EXIST");
        }

		String jsonString = JSON.toJSONString(admin);
		JSONObject parseObject = JSON.parseObject(jsonString);
        String amUnitId=admin.getAmUnitId();
        if(Validation.isEmpty(amUnitId)){
            amUnitId="0";
        }
        AmUnit org=amUnitService.getOrgs().get(Long.valueOf(amUnitId));
        if(org!=null){
	        parseObject.put("unitName", org.getOrgName());
	        parseObject.put("unitCode", org.getOrgCode());
        }
		Map<Long,String> map=RoleService.getRoles();		
		JSONArray roleArray=new JSONArray();
		try {
			List<String> roleIdList=adminPermissionService.getRoleIdsByUserId(String.valueOf(admin.getUserId()));
			for(String roleId:roleIdList){
				JSONObject roleJson=new JSONObject();
				roleJson.put("roleId", roleId);
				roleJson.put("roleName", map.get(Long.parseLong(roleId)));
				roleArray.add(roleJson);
			}
			parseObject.put("roleList", roleArray);			
			
		} catch (ServiceException e) {
			logger.info("errCode:"+e.getErrCode()+",errMsg:"+e.getErrMsg());
		}
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("user", parseObject);
        return result;
    }   
    
    @ResponseBody
    @RequestMapping(value = "/search")
    public Object search(
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson,
            @RequestParam(value = "loginCode", required = true) String loginCode,
            HttpServletRequest request) throws ValidateException {
        
        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson=URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
        .addIntegerRule("sortField", false, 1,2, exception)
        .addIntegerRule("page", false, 1,1000000, exception)
        .addIntegerRule("pageSize", false, 1,1000, exception)
        .validate();
        
        if(json.containsKey("kwFields") && !Validation.isEmpty(json.getString("keyword"))){
            new ParamterValidate()
            .addRule(new IsInteger(exception))
            .addRule(new com.greathack.utils.validate.rule.IntegerBetween(1, 255, true, exception))
            .validate(json.getString("kwFields"));
        }
        if(json.getString("keyword")==null){
        	json.put("keyword","-----");
        }
        
        AdminSearchCriteria criteria =JSON.parseObject(json.toJSONString(), AdminSearchCriteria.class);

        List<PowerItem> powerItemList= (List<PowerItem>) request.getAttribute("powerItemList");
        for(PowerItem powerItem:powerItemList){
            criteria.addFilter(powerItem.getParameter());
        }
        
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page-1) * pageSize);
        criteria.setPageSize(pageSize);
        
        SearchResult<Admin> searchResult=adminService.searchAdmins(criteria);
        List<Admin> adminList = searchResult.getRecords();
        JSONArray adminArray=new JSONArray();
        for(Admin admin:adminList){
        	String jsonString = JSON.toJSONString(admin);
    		JSONObject parseObject = JSON.parseObject(jsonString);
    		String amUnitId=admin.getAmUnitId();
    		if(Validation.isEmpty(amUnitId)){
                amUnitId="0";
            }
            AmUnit org=amUnitService.getOrgs().get(Long.valueOf(amUnitId));
            if(org!=null){
	            parseObject.put("unitName", org.getOrgName());
	            parseObject.put("unitCode", org.getOrgCode());
            }
    		JSONArray roleArray=new JSONArray();
    		try {
    			List<String> roleIdList=adminPermissionService.getRoleIdsByUserId(String.valueOf(admin.getUserId()));
    			for(String roleId:roleIdList){
    				JSONObject roleJson=new JSONObject();
    				roleJson.put("roleId", roleId);
    				roleJson.put("roleName", RoleService.getRoles().get(Long.parseLong(roleId)));
    				roleArray.add(roleJson);
    			}
    			parseObject.put("roleList", roleArray);			
    			
    		} catch (ServiceException e) {
    			logger.info("errCode:"+e.getErrCode()+",errMsg:"+e.getErrMsg());
    		}
    		adminArray.add(parseObject);
        }
        Long recordCount = searchResult.getRecordCount();  
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("userList", adminArray);
        return result;
    }

    /**
     * 选择用户弹出框
     * @param charset
     * @param strJson
     * @param loginCode
     * @return
     * @throws ValidateException
     */
    @ResponseBody
    @RequestMapping(value = "/selectUser")
    public Object selectUser(
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson,
            @RequestParam(value = "loginCode", required = true) String loginCode) throws ValidateException {

        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson=URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
                .addIntegerRule("sortField", false, 1,2, exception)
                .addIntegerRule("page", false, 1,1000000, exception)
                .addIntegerRule("pageSize", false, 1,1000, exception)
                .validate();

        if(json.containsKey("kwFields") && !Validation.isEmpty(json.getString("keyword"))){
            new ParamterValidate()
                    .addRule(new IsInteger(exception))
                    .addRule(new com.greathack.utils.validate.rule.IntegerBetween(1, 255, true, exception))
                    .validate(json.getString("kwFields"));
        }
        if(json.getString("keyword")==null){
            json.put("keyword","-----");
        }

        AdminSearchCriteria criteria =JSON.parseObject(json.toJSONString(), AdminSearchCriteria.class);

        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page-1) * pageSize);
        criteria.setPageSize(pageSize);

        SearchResult<Admin> searchResult=adminService.searchAdmins(criteria);
        List<Admin> adminList = searchResult.getRecords();
        JSONArray adminArray=new JSONArray();
        for(Admin admin:adminList){
            String jsonString = JSON.toJSONString(admin);
            JSONObject parseObject = JSON.parseObject(jsonString);
            String amUnitId=admin.getAmUnitId();
            if(Validation.isEmpty(amUnitId)){
                amUnitId="0";
            }
            AmUnit org=amUnitService.getOrgs().get(Long.valueOf(amUnitId));
            if(org!=null){
                parseObject.put("unitName", org.getOrgName());
                parseObject.put("unitCode", org.getOrgCode());
            }
            JSONArray roleArray=new JSONArray();
            try {
                List<String> roleIdList=adminPermissionService.getRoleIdsByUserId(String.valueOf(admin.getUserId()));
                for(String roleId:roleIdList){
                    JSONObject roleJson=new JSONObject();
                    roleJson.put("roleId", roleId);
                    roleJson.put("roleName", RoleService.getRoles().get(Long.parseLong(roleId)));
                    roleArray.add(roleJson);
                }
                parseObject.put("roleList", roleArray);

            } catch (ServiceException e) {
                logger.info("errCode:"+e.getErrCode()+",errMsg:"+e.getErrMsg());
            }
            adminArray.add(parseObject);
        }
        Long recordCount = searchResult.getRecordCount();
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("list", adminArray);
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/getUserListByRoleId")
    public Object getUserListByRoleId(
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "loginCode", required = true) String loginCode) throws ValidateException {
    	List<String> userIdList;
		try {
			userIdList = adminPermissionService.getUserIdsByRoleId(roleId);
		} catch (ServiceException e1) {
			throw new BizException(500,"UNKNOW ERROR");
		}
    	List<Admin> adminList=adminService.getListByUserIds(StringListToLongList(userIdList)).getUserList();
        
        JSONArray adminArray=new JSONArray();
        for(Admin admin:adminList){
        	String jsonString = JSON.toJSONString(admin);
    		JSONObject parseObject = JSON.parseObject(jsonString);
            String amUnitId=admin.getAmUnitId();
            if(Validation.isEmpty(amUnitId)){
                amUnitId="0";
            }
            AmUnit org=amUnitService.getOrgs().get(Long.valueOf(amUnitId));
            if(org!=null){
                parseObject.put("unitName", org.getOrgName());
                parseObject.put("unitCode", org.getOrgCode());
            }
    		JSONArray roleArray=new JSONArray();
    		try {
    			List<String> roleIdList=adminPermissionService.getRoleIdsByUserId(String.valueOf(admin.getUserId()));
    			for(String roleId1:roleIdList){
    				JSONObject roleJson=new JSONObject();
    				roleJson.put("roleId", roleId1);
    				roleJson.put("roleName", RoleService.getRoles().get(Long.parseLong(roleId1)));
    				roleArray.add(roleJson);
    			}
    			parseObject.put("roleList", roleArray);			
    			
    		} catch (ServiceException e) {
    			logger.info("errCode:"+e.getErrCode()+",errMsg:"+e.getErrMsg());
    		}
    		adminArray.add(parseObject);
        }
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("userList", adminArray);
        return result;
    }
	
	@ResponseBody
    @RequestMapping(value = "/login")
    public Object login(            
    		  @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson, HttpServletRequest request
            ) throws ValidateException {
        
		
		//request.get
        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson=URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
        .addStringRule("loginName,password",true,true, null, exception)
        .validate();
        
        String loginName=json.getString("loginName");
        String password=json.getString("password");
        LoginInstance loginInstance;
        try {
            loginInstance = adminService.login(loginName,password);
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }  
        if(loginInstance==null){
        	 Map<String, Object> result = new HashMap<String, Object>();
        	 result.put("errCode", 20009);
             result.put("errMsg", "LOGIN_FAILURE");
             return result;
        }
        Admin admin=adminService.getAdminById(Long.valueOf(loginInstance.getInstanceId()));
        if(admin==null || !admin.getState().equals(Admin.State.normal.value())){
            logger.info("登录失败");
            throw new BizException(200004, "LOGIN_FAIL");
        }
        
        AdminLoginSession adminLoginSession=new AdminLoginSession();
        adminLoginSession.setAdmin(admin);
        adminLoginSession.setExpireTime(Common.getLoginCodeExpireTime());
        AdminLoginSessionService.saveLoginSession(loginInstance.getLoginCode(), adminLoginSession);
        String adminJsonStr=JSON.toJSONString(admin);
        JSONObject adminJson=JSON.parseObject(adminJsonStr);
        adminJson.put("loginCode", loginInstance.getLoginCode());       
        

        AmUnitSearchCriteria criteria=new AmUnitSearchCriteria();
    	criteria.setPageSize(10000);
        String amUnitId=admin.getAmUnitId();
        if(Validation.isEmpty(amUnitId)){
            amUnitId="0";
        }
        AmUnit org=amUnitService.getOrgs().get(Long.valueOf(amUnitId));
        if(org!=null){
            adminJson.put("unitName", org.getOrgName());
            adminJson.put("unitCode", org.getOrgCode());
        }
        JSONArray roleArray=new JSONArray();
		try {
			List<String> roleIdList=adminPermissionService.getRoleIdsByUserId(String.valueOf(admin.getUserId()));
			for(String roleId:roleIdList){
				JSONObject roleJson=new JSONObject();
				roleJson.put("roleId", roleId);
				roleJson.put("roleName", RoleService.getRoles().get(Long.parseLong(roleId)));
				roleArray.add(roleJson);
			}
			adminJson.put("roleList", roleArray);			
			
		} catch (ServiceException e) {
			logger.info("errCode:"+e.getErrCode()+",errMsg:"+e.getErrMsg());
		}
        //返回权限
        Map<String, Integer> allPowers = adminPermissionService.getAllPowers(String.valueOf(admin.getUserId()));
        adminJson.put("powers", allPowers);
        //adminJson.remove("lastDevice");
        adminJson.remove("lastIP");
      //  adminJson.remove("lastLoginTime");
        //adminJson.remove("adminId");
        adminJson.remove("password");
        adminJson.remove("roleName");
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("admin", adminJson);
        return result;
	}

    @ResponseBody
    @RequestMapping(value = "/getLoginInfo")
    public Object getLoginInfo(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset) throws ValidateException {
        
        Admin admin=AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        if(admin==null){
            logger.info("无效的loginCode");
            throw new BizException(200003, "INVALID_LOGINCODE");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        String adminJsonStr=JSON.toJSONString(admin);
        JSONObject adminJson=JSON.parseObject(adminJsonStr);
        String amUnitId=admin.getAmUnitId();
        if(Validation.isEmpty(amUnitId)){
            amUnitId="0";
        }
        AmUnit org=amUnitService.getOrgs().get(Long.valueOf(amUnitId));
        if(org!=null){
            adminJson.put("unitName", org.getOrgName());
            adminJson.put("unitCode", org.getOrgCode());
        }
        JSONArray roleArray=new JSONArray();
		try {
			List<String> roleIdList=adminPermissionService.getRoleIdsByUserId(String.valueOf(admin.getUserId()));
			for(String roleId:roleIdList){
				JSONObject roleJson=new JSONObject();
				roleJson.put("roleId", roleId);
				roleJson.put("roleName", RoleService.getRoles().get(Long.parseLong(roleId)));
				roleArray.add(roleJson);
			}
			adminJson.put("roleList", roleArray);			
			
		} catch (ServiceException e) {
			logger.info("errCode:"+e.getErrCode()+",errMsg:"+e.getErrMsg());
		}
        adminJson.put("loginCode", loginCode);
        //返回权限
        Map<String, Integer> allPowers = adminPermissionService.getAllPowers(String.valueOf(admin.getUserId()));
        adminJson.put("powers", allPowers);
       // adminJson.remove("lastDevice");
        adminJson.remove("lastIP");
        //adminJson.remove("lastLoginTime");
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("admin", adminJson);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/logout")
    public Object logout(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset) throws ValidateException {
        
        try {
            adminService.logout(loginCode);
            AdminLoginSessionService.deleteLoginSession(loginCode);
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getMessage());
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
        
    }

    //修改自己的密码
    @ResponseBody
    @RequestMapping(value = "/mdfyOwnPwd")
    public Object mdfyOwnPwd(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson,HttpServletRequest request) throws ValidateException {

        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson=URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
                .addStringRule("newPassword,password",true,true, 255, exception)
                .validate();

        Admin admin=AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        //验证原密码
        try {
            adminService.login(admin.getLoginName(), json.getString("password"));
        } catch (ServiceException e1) {
            logger.info("原密码有问题");
            throw new BizException(e1.getErrCode(), e1.getErrMsg());
        }

        try {
            adminService.mdfyPwd(admin.getLoginName(), json.getString("newPassword"));
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    //修改别人的密码
    @ResponseBody
    @RequestMapping(value = "/mdfyPwd")
    public Object mdfyPwd(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson,HttpServletRequest request) throws ValidateException {

        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson=URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
                .addStringRule("loginName,password",true,true, 255, exception)
                .validate();

        Admin admin=AdminLoginSessionService.getLoginSession(loginCode).getAdmin();

        try {
            adminService.mdfyPwd(json.getString("loginName"), json.getString("password"));
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    //重置密码
    @ResponseBody
    @RequestMapping(value = "/resetPwd")
    public Object resetPwd(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson,HttpServletRequest request) throws ValidateException {

        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson=URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        JSONArray jsonArray=JSONArray.parseArray(strJson);

        try {
            for(Object obj:jsonArray){
                adminService.mdfyPwd(obj.toString(), SystemConfig.getConfigData("DEFAULT_PASSWORD"));
            }
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }
    

	
	private List<Long> StringListToLongList(List<String> strList){
		List<Long> longList=new ArrayList<Long>();
		for(String str:strList){
			longList.add(Long.valueOf(str));
		}
		return longList;		
	}



    @ResponseBody
    @RequestMapping(value = "/selectAdmin")
    public Object selectAdmin(
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson,
            @RequestParam(value = "loginCode", required = true) String loginCode) throws ValidateException {

        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson=URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
                .addIntegerRule("sortField", false, 1,2, exception)
                .addIntegerRule("page", false, 1,1000000, exception)
                .addIntegerRule("pageSize", false, 1,100000, exception)
                .validate();

        if(json.containsKey("kwFields") && !Validation.isEmpty(json.getString("keyword"))){
            new ParamterValidate()
                    .addRule(new IsInteger(exception))
                    .addRule(new com.greathack.utils.validate.rule.IntegerBetween(1, 511, true, exception))
                    .validate(json.getString("kwFields"));
        }
        if(json.getString("keyword")==null){
            json.put("keyword","-----");
        }

        AdminSearchCriteria criteria =JSON.parseObject(json.toJSONString(), AdminSearchCriteria.class);

        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 100000);
        criteria.setStartLine((page-1) * pageSize);
        criteria.setPageSize(pageSize);

        criteria.setAmUnitId(json.getString("orgId"));
        SearchResult<Admin> searchResult=adminService.searchAdmins(criteria);
        List<Admin> adminList = searchResult.getRecords();
        JSONArray adminArray=new JSONArray();
        for(Admin admin:adminList){
            String jsonString = JSON.toJSONString(admin);
            JSONObject parseObject = JSON.parseObject(jsonString);
            String amUnitId=admin.getAmUnitId();
            if(Validation.isEmpty(amUnitId)){
                amUnitId="0";
            }
            AmUnit org=amUnitService.getOrgs().get(Long.valueOf(amUnitId));
            parseObject.put("orgName",org.getOrgName());
            parseObject.put("orgId",amUnitId);
            parseObject.put("xm",admin.getName());
            parseObject.put("xb",admin.getSex());
            parseObject.put("idcard",admin.getIdcard());
            parseObject.put("loginName",admin.getLoginName());

            adminArray.add(parseObject);
        }
        Long recordCount = searchResult.getRecordCount();
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("list", adminArray);
        return result;
    }


}
