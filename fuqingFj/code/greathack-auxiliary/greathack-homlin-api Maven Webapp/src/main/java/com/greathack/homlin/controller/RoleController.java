package com.greathack.homlin.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.serviceinterface.IAdminService;
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
import com.greathack.utils.validate.JsonParamValidate;
import com.greathack.utils.validate.ParamterValidate;
import com.greathack.utils.validate.exception.ValidateException;
import com.greathack.utils.validate.rule.IsInteger;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Role;
import com.greathack.homlin.pojo.RoleSearchCriteria;
import com.greathack.homlin.serviceinterface.IAdminPermissionService;
import com.greathack.homlin.serviceinterface.IAdminPowerItemService;
import com.greathack.homlin.serviceinterface.IRoleService;

/**
 *
 * @author Admin
 *
 */
@Controller
@RequestMapping(value="/role")
public class RoleController {

	private static Logger logger = LoggerFactory.getLogger(RoleController.class);
	private BizException exception=new BizException(210001,"INVALID_PARAMS");
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IAdminPermissionService adminPermissionService;
	@Autowired
	private IAdminPowerItemService adminPowerItemService;
	@Autowired
	private IAdminService adminService;



	@ResponseBody
	@RequestMapping(value="/add")
	public  Object add(@RequestBody String strJson,@RequestParam(value = "charset", required = false) String charset,
					   @RequestParam(value = "loginCode", required = true) String loginCode ,
					   HttpServletRequest request) throws ValidateException{


		//2、对传进来的参数进行转码
		try {
			//对strJson进行转码
			strJson=URLDecoder.decode(strJson, charset);
		} catch (UnsupportedEncodingException e1) {
			logger.info("无效的charset");
			throw new BizException(10013, "INVALID_CHARSET");
		}



		JSONObject json = new JsonParamValidate(strJson,exception)
				.addStringRule("roleName,roleType", 64, exception)
				.addStringRule("roleDescription", 255, exception)
				.validate();

		//json转成javaBean
		Role role = JSON.toJavaObject(json, Role.class);
		try {
			role=roleService.addRole(role);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		//分配权限给角色
//		JSONArray jsonArray = json.getJSONArray("powerItemIds");
//		if(jsonArray!=null &&jsonArray.size()>0){
//
//			List<AdminPower> list = new ArrayList<AdminPower>();
//			for(Object o : jsonArray){
//				String outPowerItemId = o.toString();
//				AdminPowerItem powerItem=null;
//				try {
//					powerItem = adminPowerItemService.getPowerItem(outPowerItemId.toString());
//				} catch (ServiceException e) {
//					logger.info("根据权限项目Id获取权限项目出现异常");
//					throw new BizException(e.getErrCode(), e.getErrMsg());
//				}
//				AdminPower adminPower = new AdminPower();
//				adminPower.setPowerCode(powerItem.getPowerCode());
//				list.add(adminPower);
//			}
//
//
//			try {
//				adminPermissionService.assignPowersToRole(Common.idToOutId(role.getRoleId(), "wxserviceapp"), list);
//			} catch (ServiceException e) {
//				logger.info("分配权限给角色出现异常");
//				throw new BizException(e.getErrCode(), e.getErrMsg());
//
//			}
//		}
//
		String roleJsonStr=JSON.toJSONString(role);
		json=JSON.parseObject(roleJsonStr);
		//json.put("roleId", Common.idToOutId(role.getRoleId(), "wxserviceapp"));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("role", json);
		return result;
	}

	/**
	 *
	 * @Title: delete
	 * @Description: 删除角色
	 * @param roleId
	 * @param loginCode
	 * @return
	 * @throws ValidateException
	 * @author greathack
	 * @date 2017年4月29日
	 */
	@ResponseBody
	@RequestMapping(value="/delete")
	public Object delete(
			@RequestParam(value="roleId", required=false)Long roleId,
			@RequestParam(value = "loginCode", required = true) String loginCode ,
			HttpServletRequest request) throws ValidateException{

		Role findById = roleService.findById(roleId);
		roleService.deleteRole(roleId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		return result;
	}

	@ResponseBody
	@RequestMapping(value="/update")
	public  Object update(@RequestBody String strJson,@RequestParam(value = "charset", required = false) String charset,
						  @RequestParam(value = "loginCode", required = true) String loginCode ,
						  HttpServletRequest request) throws ValidateException{
		//2、对传进来的参数进行转码
		try {
			//对strJson进行转码
			strJson=URLDecoder.decode(strJson, charset);
		} catch (UnsupportedEncodingException e1) {
			logger.info("无效的charset");
			throw new BizException(10013, "INVALID_CHARSET");
		}


		JSONObject json = new JsonParamValidate(strJson,exception)
				.addLongRule("roleId", true, 0L, null, exception)
				.addStringRule("roleName,roleType", 64, exception)
				.addStringRule("roleDescription", 255, exception)
				.addStringRule("description", 2048, exception)
				.addIntegerRule("roleState,isSys", false, 1, 2, exception)
				.validate();


		Long roleId = json.getLongValue("roleId");
		Role role = roleService.findById(roleId);
		if(role == null){
			throw new BizException(300010,"ROLE_NOT_EXIST");
		}
		if(json.containsKey("roleName")){
			role.setRoleName(json.getString("roleName"));
		}
		if(json.containsKey("roleType")){
			role.setRoleType(json.getString("roleType"));
		}
		if(json.containsKey("roleDescription")){
			role.setRoleDescription(json.getString("roleDescription"));
		}
		if(json.containsKey("roleState")){
			role.setRoleState(json.getInteger("roleState"));
		}
		if(json.containsKey("isSys")){
			role.setIsSys(json.getInteger("isSys"));
		}
		roleService.updateRole(role);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		//				result.put("news", json);
		return result;
	}







	@ResponseBody
	@RequestMapping(value="/getRole")
	public Object getOneRole(
			@RequestParam(value="roleId", required=false)Long roleId) throws ValidateException{

		Role findById = roleService.findById(roleId);
		String jsonString = JSON.toJSONString(findById);
		JSONObject parseObject = JSON.parseObject(jsonString);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("role", parseObject);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/search")
	public Object search(
			@RequestBody String strJson,@RequestParam(value = "charset", required = false) String charset) throws ValidateException {


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
				.addIntegerRule("pageSize", false, 1,10000, exception)
				.validate();

		if(json.containsKey("kwFields") && json.containsKey("keyword")){
			new ParamterValidate().addRule(new IsInteger(exception)).validate(json.getString("kwFields"));
			if(json.getIntValue("kwFields")<1 || json.getIntValue("kwFields")> 3){
				logger.info("kwFields必须在区间1--3");
				throw new BizException(10003, "INVALID_PARAMS");
			}
		}
		RoleSearchCriteria criteria =JSON.parseObject(strJson, RoleSearchCriteria.class);

		int page = TypeOption.nullToValue(json.getString("page"), 1);
		int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
		criteria.setStartLine((page-1) * pageSize);
		criteria.setPageSize(pageSize);


		List<Role> roleList= roleService.search(criteria);
		Long recordCount=roleService.getSearchResultCount(criteria);
		List<JSONObject> roleJsonList=new ArrayList<JSONObject>();
		for(Role role:roleList){
			String roleJsonStr=JSON.toJSONString(role);
			JSONObject roleJson=JSON.parseObject(roleJsonStr);
			roleJson.remove("appCode");

			//获取用户列表
			List<String> userIdList=new ArrayList<>();
			try {
				userIdList = adminPermissionService.getUserIdsByRoleId(role.getRoleId().toString());
			} catch (ServiceException e1) {
				throw new BizException(e1.getErrCode(),e1.getErrMsg());
			}
			List<Admin> adminList=adminService.getListByUserIds(StringListToLongList(userIdList)).getUserList();
			JSONArray adminArray=new JSONArray();
			for(Admin admin:adminList) {
				String jsonString = JSON.toJSONString(admin);
				JSONObject parseObject = JSON.parseObject(jsonString);
				adminArray.add(parseObject);
			}
			roleJson.put("userList",adminArray);

			roleJsonList.add(roleJson);
		}
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("recordCount", recordCount);
		result.put("roleList", roleJsonList);
		return result;
	}


	private List<Long> StringListToLongList(List<String> strList){
		List<Long> longList=new ArrayList<Long>();
		for(String str:strList){
			longList.add(Long.valueOf(str));
		}
		return longList;
	}
}
