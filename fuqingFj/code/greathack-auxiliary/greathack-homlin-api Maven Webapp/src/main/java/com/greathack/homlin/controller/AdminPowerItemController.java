package com.greathack.homlin.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.pojo.system.Menu;
import com.greathack.homlin.pojo.system.MenuSearchCriteria;
import com.greathack.homlin.serviceinterface.system.IMenuService;
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
import com.greathack.homlin.pojo.SearchResult;
import com.greathack.homlin.pojo.permission.PowerItem;
import com.greathack.homlin.pojo.permission.PowerItemSearchCriteria;
import com.greathack.homlin.serviceinterface.IAdminPermissionService;
import com.greathack.homlin.serviceinterface.IAdminPowerItemService;


@Controller
@RequestMapping(value = "/powerItem")
public class AdminPowerItemController {

	private static Logger logger = LoggerFactory.getLogger(AdminPowerItemController.class);
	private BizException exception=new BizException(200001,"INVALID_PARAMS");
	
	@Autowired
    private IAdminPermissionService adminPermissionService;
	@Autowired
    private IAdminPowerItemService adminPowerItemService;
    @Autowired
    private IMenuService menuService;
	
	
	@ResponseBody
    @RequestMapping(value = "/add")
    public Object add( @RequestParam(value = "charset", required = false) String charset,
    		//@RequestParam(value = "loginCode", required = true) String loginCode ,
    		@RequestBody String strJson,HttpServletRequest request ) throws ValidateException {

        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
        .addIntegerRule("itemType",false,1, 2, exception)
        .addStringRule("referer,resource,parameter",false,false, 1024, exception)
        .addStringRule("itemName,remark", 255, exception)
        .addStringRule("itemName", true,true,255, exception)
        .validate();        

        PowerItem powerItem = JSON.toJavaObject(json, PowerItem.class); 
        try {
        	powerItem=adminPowerItemService.add(powerItem);
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }
       
//        AdminLoginSession admLogin  = AdminLoginSessionService.getLoginSession(loginCode);
//        addOperateLog(request, admLogin.getAdmin(),"添加权限项目");
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("powerItem", powerItem);
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete(
            @RequestParam(value = "charset", required = false) String charset,
           // @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "powerItemId", required = false) Long powerItemId,HttpServletRequest request) throws ValidateException {
        if(powerItemId==null){
            logger.info("powerItemId必填");
            throw new BizException(200066, "POWERITEMID_IS_REQUIRE");
        }

       
        try {
            adminPowerItemService.delete(powerItemId);
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }
       //添加日志
//       AdminLoginSession loginSession = AdminLoginSessionService.getLoginSession(loginCode);
//        addOperateLog(request, loginSession.getAdmin(),"删除权限项目");
      
        Map<String, Object> result = new HashMap<String, Object>();        
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Object update(
            @RequestParam(value = "charset", required = false) String charset,
            //@RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestBody String strJson,HttpServletRequest request) throws ValidateException {

    	//3、对json里面的参数进行校验
    	JSONObject json = new JsonParamValidate(strJson,exception)
    	.addStringRule("itemType,powerCode",false,false, 64, exception)
        .addStringRule("referer,resource", 1024, exception)
        .addStringRule("itemName,remark", 255, exception)
        .validate();     

    	Long powerItemId=json.getLong("powerItemId");
    	if(powerItemId==null){
    		logger.info("powerItemId不能为空");
    		throw new BizException(200076, "POWERITEMID_IS_REQUIRE");
    	}
    	
    	PowerItem adminPowerItem=null;
		try {
			adminPowerItem = adminPowerItemService.getPowerItem(powerItemId);
		} catch (ServiceException e1) {
			logger.info("获取权限项目失败");
			e1.printStackTrace();
		}     
    	
    	if(adminPowerItem==null){
    		logger.info("权限项目不存在");
    		throw new BizException(200077,"ADMINPOWERITEM_NOT_EXIST");
    	}
        String powerCode=json.getString("powerCode");
        if(powerCode!=null){
            adminPowerItem.setPowerCode(powerCode);
        }
    	String itemType=json.getString("itemType");
    	if(itemType!=null){
    		adminPowerItem.setItemType(itemType);
    	}
    	String referer = json.getString("referer");
    	if(referer!=null){
    		adminPowerItem.setReferer(referer);
    	}
    	String resource = json.getString("resource");
    	if(resource!=null){
    		adminPowerItem.setResource(resource);
    	}
        String parameter = json.getString("parameter");
        if(parameter!=null){
            adminPowerItem.setParameter(parameter);
        }
    	String itemName = json.getString("itemName");
    	if(referer!=null){
    		adminPowerItem.setItemName(itemName);
    	}
    	String remark = json.getString("remark");
    	if(remark!=null){
    		adminPowerItem.setRemark(remark);
    	}

    
    	try {
    		adminPowerItemService.mdfy(adminPowerItem);
    	} catch (ServiceException e) {
    		throw new BizException(e.getErrCode(), e.getMessage());
    	}       
//    	AdminLoginSession loginAdmin=AdminLoginSessionService.getLoginSession(loginCode);
//    	addOperateLog(request, loginAdmin.getAdmin(), "修改权限项目");
    	Map<String, Object> result = new HashMap<String, Object>();
    	result.put("errCode", 0);
    	result.put("errMsg", "SUCCESS");

    	return result;


    }
    
    @ResponseBody
    @RequestMapping(value = "/getPowerItem")
    public Object getPowerItem(
            @RequestParam(value = "powerItemId", required = false) Long powerItemId,
            @RequestParam(value = "charset", required = false) String charset
            //@RequestParam(value = "loginCode", required = true) String loginCode
            )
            throws ValidateException {
        if(powerItemId==null){
            logger.info("powerItemId必填");
            throw new BizException(300014, "POWERITEMID_IS_REQUIRE");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        PowerItem adminPowerItem=null;
		try {
			adminPowerItem = adminPowerItemService.getPowerItem(powerItemId);
		} catch (ServiceException e) {
			logger.info("获取权限项目失败");
			e.printStackTrace();
		}        
        if(adminPowerItem==null){
            logger.info("权限项目不存在");
            throw new BizException(300015,"ADMINPOWERITEM_NOT_EXIST");
        } 
        
        String jsonStr=JSON.toJSONString(adminPowerItem);
        JSONObject json=JSON.parseObject(jsonStr);
           
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("adminPowerItem", json);
        return result;
    }   
    
    @ResponseBody
    @RequestMapping(value = "/search")
    public Object search(
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson
           // @RequestParam(value = "loginCode", required = true) String loginCode
            ) throws ValidateException {
        
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
        .addIntegerRule("adminState", false, 1,3, exception)
        .addIntegerRule("sortField", false, 1,2, exception)
        .addIntegerRule("page", false, 1,1000000, exception)
        .addIntegerRule("pageSize", false, 1,10000, exception)
        .validate();
        
        if(json.containsKey("kwFields") && json.containsKey("keyword")){
            new ParamterValidate()
            .addRule(new IsInteger(exception))
            .addRule(new com.greathack.utils.validate.rule.IntegerBetween(1, 15, true, exception))
            .validate(json.getString("kwFields"));
        }
        PowerItemSearchCriteria criteria =JSON.parseObject(strJson, PowerItemSearchCriteria.class);
        
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 10000);
        criteria.setStartLine((page-1) * pageSize);
        criteria.setPageSize(pageSize);
        
        SearchResult<PowerItem> searchResult=adminPowerItemService.search(criteria);
        List<PowerItem> adminList = searchResult.getRecords();
        Long recordCount = searchResult.getRecordCount();  
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("adminPowerItemList", adminList);
        return result;
    }


    /**
     * @Title : getFunPowerTree
     * @Description: 获取功能权限树
     * @version
     * @return
     * @throws ValidateException
     * @throws
     */
    @ResponseBody
    @RequestMapping(value = "/getFunPowerTree")
    public Object getFunPowerTree(
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
            menuTree.add(getFunTree(orgList,menu.getMenuId()));
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("funPowerTree", menuTree);
        return result;
    }


    //递归形成功能树
    private Map<String,Object> getFunTree(List<Menu> menuList,long menuId){
        Map<String,Object> item=new HashMap<String, Object>();
        Menu menu=menuService.getMenus().get(menuId);
        item.put("title",menu==null?"根节点":menu.getMenuName());
        item.put("id",menu.getMenuId());
        item.put("powerCode",menu.getMenuCode());
        item.put("spread",true);
        List<Menu> children = getChildren(menuList,menuId);
        if(children.size()>0){
            List<Map<String,Object>> childrenTree=new ArrayList<Map<String, Object>>();
            for(Menu child:children){
                childrenTree.add(getFunTree(menuList,child.getMenuId()));
            }
            item.put("children",childrenTree);
        }else{
            PowerItemSearchCriteria criteria=new PowerItemSearchCriteria();
            criteria.setOutKey1(String.valueOf(menu.getMenuId()));
            criteria.setPageSize(10000);
            SearchResult<PowerItem> powerItemResult=adminPowerItemService.search(criteria);
            if(powerItemResult.getRecordCount()>0){
                List<Map<String,Object>> childrenTree=new ArrayList<Map<String, Object>>();
                for(PowerItem powerItem:powerItemResult.getRecords()){
                    Map<String,Object> powerItemMap=new HashMap<String, Object>();
                    powerItemMap.put("title",powerItem.getItemName());
                    powerItemMap.put("id",powerItem.getPowerCode());
                    powerItemMap.put("powerCode",powerItem.getPowerCode());
                    powerItemMap.put("spread",true);
                    childrenTree.add(powerItemMap);
                }
                item.put("children",childrenTree);
            }
        }
        return item;
    }


    /**
     * @Title : getMenuPowerTree
     * @Description: 获取菜单权限树
     * @version
     * @return
     * @throws ValidateException
     * @throws
     */
    @ResponseBody
    @RequestMapping(value = "/getMenuPowerTree")
    public Object getMenuPowerTree(
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
            menuTree.add(getMenuTree(orgList,menu.getMenuId()));
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("menuPowerTree", menuTree);
        return result;
    }


    //递归形成菜单树
    private Map<String,Object> getMenuTree(List<Menu> menuList,long menuId){
        Map<String,Object> item=new HashMap<String, Object>();
        Menu menu=menuService.getMenus().get(menuId);
        item.put("title",menu==null?"根节点":menu.getMenuName());
        item.put("id",menu.getMenuCode());
        item.put("powerCode",menu.getMenuCode());
        item.put("spread",true);
        List<Menu> children = getChildren(menuList,menuId);
        if(children.size()>0){
            List<Map<String,Object>> childrenTree=new ArrayList<Map<String, Object>>();
            for(Menu child:children){
                childrenTree.add(getMenuTree(menuList,child.getMenuId()));
            }
            item.put("children",childrenTree);
        }
        return item;
    }

    //在已知的列表中，获取下级菜单列表
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
