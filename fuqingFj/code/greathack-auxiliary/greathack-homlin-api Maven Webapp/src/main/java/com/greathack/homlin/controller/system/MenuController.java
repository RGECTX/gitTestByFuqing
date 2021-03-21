package com.greathack.homlin.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Role;
import com.greathack.homlin.pojo.RoleSearchCriteria;
import com.greathack.homlin.pojo.SearchResult;
import com.greathack.homlin.pojo.permission.PowerItem;
import com.greathack.homlin.pojo.system.Menu;
import com.greathack.homlin.pojo.system.MenuSearchCriteria;
import com.greathack.homlin.serviceinterface.IAdminPermissionService;
import com.greathack.homlin.serviceinterface.IAdminPowerItemService;
import com.greathack.homlin.serviceinterface.IRoleService;
import com.greathack.homlin.serviceinterface.system.IMenuService;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.TypeOption;
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
import java.util.*;

/**
 *
 * @author Admin
 *
 */
@Controller
@RequestMapping(value="/menu")
public class MenuController {

	private static Logger logger = LoggerFactory.getLogger(MenuController.class);
	private BizException exception=new BizException(210001,"INVALID_PARAMS");
	private String ADMIN_PERMISSION_APP_CODE=SystemConfig.getConfigData("ADMIN_PERMISSION_APP_CODE");
	@Autowired
	private IMenuService menuService;
	@Autowired
	private IAdminPermissionService adminPermissionService;
	@Autowired
	private IAdminPowerItemService adminPowerItemService;



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
				.addLongRule("sortNum", false, 0l,null, exception)
				.addLongRule("parentId", true, 0L, null, exception)
				.addStringRule("menuName,menuCode", true,true,255, exception)
				.validate();

		//json转成javaBean
		Menu menu = JSON.toJavaObject(json, Menu.class);
		try {
			menu=menuService.addMenu(menu);
			PowerItem powerItem=new PowerItem();
			powerItem.setPowerCode(menu.getMenuCode());
			powerItem.setItemName(menu.getMenuName());
			powerItem.setItemType(String.valueOf(menu.getMenuId()));
			adminPowerItemService.add(powerItem);
		} catch (ServiceException e) {
			throw new BizException(e.getErrCode(),e.getErrMsg());
		}

		String menuJsonStr=JSON.toJSONString(menu);
		json=JSON.parseObject(menuJsonStr);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("role", json);
		return result;
	}

	@ResponseBody
	@RequestMapping(value="/delete")
	public Object delete(
			@RequestParam(value="menuId", required=false)Long menuId,
			@RequestParam(value = "loginCode", required = true) String loginCode ,
			HttpServletRequest request) throws ValidateException{

		try {
			Menu menu = menuService.getMenu(menuId);
			if(menu!=null){
				PowerItem powerItem=adminPowerItemService.getPowerItem(menu.getMenuCode());
				if(powerItem!=null){
					adminPowerItemService.delete(powerItem.getPowerItemId());
				}
			}
			menuService.delMenu(menuId);
		} catch (ServiceException e) {
			throw new BizException(e.getErrCode(),e.getErrMsg());
		}
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
				.addLongRule("menuId", true, 0L, null, exception)
				.addLongRule("sortNum,parentId", false, 0l,null, exception)
				.addStringRule("menuName,menuCode", 255, exception)
				.validate();



		Long menuId = json.getLongValue("menuId");
		Menu menu = menuService.getMenu(menuId);
		if(menu == null){
			throw new BizException(ErrCode.MENU_NOT_EXIST,"MENU_NOT_EXIST");
		}
		if(json.containsKey("menuName")){
			menu.setMenuName(json.getString("menuName"));
		}
		if(json.containsKey("menuCode")){
			menu.setMenuCode(json.getString("menuCode"));
		}
		if(json.containsKey("parentId")){
			menu.setParentId(json.getLong("parentId"));
		}
		if(json.containsKey("remark")){
			menu.setRemark(json.getString("remark"));
		}
		try {
			menuService.mdfyMenu(menu);
		} catch (ServiceException e) {
			throw new BizException(e.getErrCode(),e.getErrMsg());
		}

		try {
			PowerItem powerItem=adminPowerItemService.getPowerItem(menu.getMenuCode());
			if(powerItem==null){
				powerItem=new PowerItem();
				powerItem.setPowerCode(menu.getMenuCode());
				powerItem.setItemName(menu.getMenuName());
				powerItem.setItemType(String.valueOf(menu.getMenuId()));
				adminPowerItemService.add(powerItem);
			}else {
				powerItem.setPowerCode(menu.getMenuCode());
				powerItem.setItemName(menu.getMenuName());
				adminPowerItemService.mdfy(powerItem);
			}
		} catch (ServiceException e) {
			throw new BizException(e.getErrCode(),e.getErrMsg());
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		return result;
	}

	@ResponseBody
	@RequestMapping(value="/getMenu")
	public Object getMenu(
			@RequestParam(value="menuId", required=false)Long menuId) throws ValidateException{
		Menu menu = menuService.getMenu(menuId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("menu", menu);
		return result;
	}

	/**
	 * 修改菜单排序
	 *
	 * @param strJson
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping(value = "/updateSort")
	public Object updateSort(
			@RequestParam(value = "charset", required = false) String charset,
			@RequestBody String strJson) throws ValidateException {

		//2、对传进来的参数进行转码
		try {
			//对strJson进行转码
			strJson=URLDecoder.decode(strJson, charset);
		} catch (UnsupportedEncodingException e1) {
			logger.info("无效的charset");
			throw new BizException(10013, "INVALID_CHARSET");
		}


		JSONArray jsonArray = null;
		try {
			jsonArray = JSON.parseArray(strJson);
		} catch (Exception e) {
			logger.info("参数不是json类型");
			throw new BizException(ErrCode.INVALID_PARAMS, "INVALID_PARAMS");
		}
		List<Long> menuIdList = new ArrayList<Long>();
		for (Object obj : jsonArray) {
			menuIdList.add(Long.valueOf(obj.toString()));
		}
		menuService.sort(menuIdList);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		return result;
	}

	/**
	 * @Title : getMenuList
	 * @Description: 获取所有菜单列表
	 * @version
	 * @return
	 * @throws ValidateException
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/getMenuList")
	public Object getMenuList(
			@RequestParam(value = "charset", required = false) String charset) throws ValidateException {
		List<Menu> menuList=menuService.getMenuList();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("menuList", menuList);
		return result;
	}

	/**
	 * @Title : getChildrenList
	 * @Description: 获取子菜单列表
	 * @version
	 * @return
	 * @throws ValidateException
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/getChildrenList")
	public Object getChildrenList(
			@RequestParam(value = "charset", required = false) String charset,
			@RequestParam(value = "menuId", required = false) Long menuId) throws ValidateException {
		List<Menu> menuList= menuService.getChildren(menuId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("menuList", menuList);
		return result;
	}

	/**
	 * @Title : search
	 * @Description: 搜索菜单列表
	 * @version
	 * @return
	 * @throws ValidateException
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/search")
	public Object search(
			@RequestParam(value = "charset", required = false) String charset,
			@RequestBody String strJson) throws ValidateException {

		//3、对json里面的参数进行校验
		JSONObject json = new JsonParamValidate(strJson,exception)
				.addIntegerRule("sortField", false, 1,2, exception)
				.addIntegerRule("page", false, 1,1000000, exception)
				.addIntegerRule("page", false, 1,10000, exception)
				.validate();

		if(json.containsKey("kwFields") && json.containsKey("keyword")){
			new ParamterValidate().addRule(new IsInteger(exception)).validate(json.getString("kwFields"));
			if(json.getIntValue("kwFields")<1 || json.getIntValue("kwFields")> 7){
				logger.info("kwFields必须在区间1--7");
				throw new BizException(ErrCode.INVALID_PARAMS, "INVALID_PARAMS");
			}
		}
		MenuSearchCriteria criteria =JSON.parseObject(strJson, MenuSearchCriteria.class);

		int page = TypeOption.nullToValue(json.getString("page"), 1);
		int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
		criteria.setStartLine((page-1) * pageSize);
		criteria.setPageSize(pageSize);
		SearchResult<Menu> searchResult = menuService.search(criteria);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("recordCount", searchResult.getRecordCount());
		result.put("menuList", searchResult.getRecords());
		return result;
	}


	@ResponseBody
	@RequestMapping(value = "/isLeaf")
	public Object isLeaf(
			@RequestParam(value = "charset", required = false) String charset,
			@RequestParam(value = "menuId", required = false) Long menuId) throws ValidateException {
		boolean isLeaf=menuService.isLeaf(menuId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("isLeaf", isLeaf);
		return result;

	}




	/**
	 * @Title : getMenuTree
	 * @Description: 获取菜单树
	 * @version
	 * @return
	 * @throws ValidateException
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/getMenuTree")
	public Object getMenuTree(
			@RequestParam(value = "charset", required = false) String charset,
			@RequestBody String strJson) throws ValidateException {

		//3、对json里面的参数进行校验
		JSONObject json = new JsonParamValidate(strJson,exception)
				.addIntegerRule("sortField", false, 1,2, exception)
				.addIntegerRule("page", false, 1,1000000, exception)
				.addIntegerRule("page", false, 1,1000, exception)
				.validate();

		if(json.containsKey("kwFields") && json.containsKey("keyword")){
			new ParamterValidate().addRule(new IsInteger(exception)).validate(json.getString("kwFields"));
			if(json.getIntValue("kwFields")<1 || json.getIntValue("kwFields")> 7){
				logger.info("kwFields必须在区间1--7");
				throw new BizException(ErrCode.INVALID_PARAMS, "INVALID_PARAMS");
			}
		}
		MenuSearchCriteria criteria =JSON.parseObject(strJson, MenuSearchCriteria.class);

		int page = 1;
		int pageSize = 10000;
		criteria.setStartLine((page-1) * pageSize);
		criteria.setPageSize(pageSize);
		SearchResult<Menu> searchResult = menuService.search(criteria);

		List<Menu> orgList = searchResult.getRecords();
		List<Menu> roots = getRoots(orgList);
		List<Map<String,Object>> menuTree=new ArrayList<Map<String, Object>>();
		for(Menu menu:roots){
			menuTree.add(getTree(orgList,menu.getMenuId()));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("menuTree", menuTree);
		return result;
	}

	/**
	 * @Title : getPowerTree
	 * @Description: 获取权限树
	 * @version
	 * @return
	 * @throws ValidateException
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/getPowerTree")
	public Object getPowerTree(
			@RequestParam(value = "charset", required = false) String charset,
			@RequestBody String strJson) throws ValidateException {

		//3、对json里面的参数进行校验
		JSONObject json = new JsonParamValidate(strJson,exception)
				.addIntegerRule("sortField", false, 1,2, exception)
				.addIntegerRule("page", false, 1,1000000, exception)
				.addIntegerRule("page", false, 1,1000, exception)
				.validate();

		if(json.containsKey("kwFields") && json.containsKey("keyword")){
			new ParamterValidate().addRule(new IsInteger(exception)).validate(json.getString("kwFields"));
			if(json.getIntValue("kwFields")<1 || json.getIntValue("kwFields")> 7){
				logger.info("kwFields必须在区间1--7");
				throw new BizException(ErrCode.INVALID_PARAMS, "INVALID_PARAMS");
			}
		}
		MenuSearchCriteria criteria =JSON.parseObject(strJson, MenuSearchCriteria.class);

		int page = 1;
		int pageSize = 10000;
		criteria.setStartLine((page-1) * pageSize);
		criteria.setPageSize(pageSize);
		SearchResult<Menu> searchResult = menuService.search(criteria);

		List<Menu> orgList = searchResult.getRecords();
		List<Menu> roots = getRoots(orgList);
		List<Map<String,Object>> menuTree=new ArrayList<Map<String, Object>>();
		for(Menu menu:roots){
			menuTree.add(getTree(orgList,menu.getMenuId()));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("menuTree", menuTree);
		return result;
	}



	//递归形成菜单树
	private Map<String,Object> getTree(List<Menu> menuList,long menuId){
		Map<String,Object> item=new HashMap<String, Object>();
		Menu menu=menuService.getMenus().get(menuId);
		item.put("title",menu==null?"根节点":menu.getMenuName());
		item.put("id",menuId);
		item.put("menuCode",menu.getMenuCode());
		item.put("menuName",menu.getMenuName());
		item.put("spread",true);
		List<Menu> children = getChildren(menuList,menuId);
		if(children.size()>0){
			List<Map<String,Object>> childrenTree=new ArrayList<Map<String, Object>>();
			for(Menu child:children){
				childrenTree.add(getTree(menuList,child.getMenuId()));
			}
			item.put("children",childrenTree);
		}
		return item;
	}

	//在已知的列表中，获取下级单列表
	private List<Menu> getChildren(List<Menu> menuList,long parentId){
		List<Menu> children=new ArrayList<Menu>();
		for(Menu menu:menuList){
			if(Objects.equals(menu.getParentId(),parentId)){
				children.add(menu);
			}
		}
		return children;
	}

	//在已知的列表中，获取最上级单位列表，既在本列表中找不到上级
	private List<Menu> getRoots(List<Menu> menuList){
		List<Menu> roots=new ArrayList<Menu>();
		Map<Long,Menu> menuMap=new HashMap<Long, Menu>();
		for(Menu menu:menuList){
			menuMap.put(menu.getMenuId(),menu);
		}
		for(Menu menu:menuList){
			if(!menuMap.containsKey(menu.getParentId())){
				roots.add(menu);
			}
		}
		return roots;
	}

}
