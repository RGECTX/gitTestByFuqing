package com.greathack.homlin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.AmUnit;
import com.greathack.homlin.pojo.AmUnitSearchCriteria;
import com.greathack.homlin.pojo.SearchResult;
import com.greathack.homlin.pojo.permission.PowerItem;
import com.greathack.homlin.serviceinterface.am.IAmUnitService;
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
import java.util.*;


@Controller
@RequestMapping(value = "/amUnit")
public class AmUnitController {

    private static Logger logger = LoggerFactory.getLogger(AmUnitController.class);
    private BizException exception=new BizException(200001,"INVALID_PARAMS");

    @Autowired
    private IAmUnitService amUnitService;

    @ResponseBody
    @RequestMapping(value = "/add")
    public Object add(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson,
            HttpServletRequest request) throws ValidateException {

        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
                .addStringRule("parentId,orgType,orgRank", 64, exception)
                .addStringRule("orgCode", true,true,64, exception)
                .addStringRule("orgName,remark", 255, exception)
                .addIntegerRule("orgState", true, 1, 2, exception)
                .validate();

        //json转成javaBean
        json.remove("orgId");
        json.remove("createTime");
        logger.debug(json.toJSONString());
        AmUnit org = JSON.toJavaObject(json, AmUnit.class);
        org=amUnitService.add(org);

        json.put("orgId", org.getOrgId());
        json.put("orgState", org.getOrgState());
        json.put("createTime", org.getCreateTime());
        json.remove("parentId");

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("org", json);
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/update")
    public Object update(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson) throws ValidateException {

        Map<String, Object> result = new HashMap<String, Object>();
        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
                .addStringRule("orgId", true, true, 64, exception)
                .addStringRule("orgCode,parentId,orgType,orgRank", 64, exception)
                .addStringRule("orgName,remark", 255, exception)
                .validate();

        Long orgId=json.getLong("orgId");
        AmUnit org;
        try {
            org = amUnitService.findById(orgId);
        } catch (ServiceException e1) {
            throw new BizException(e1.getErrCode(), e1.getMessage());
        }
        if(org==null){
            logger.info("组织机构不存在");
            throw new BizException(200020,"ORG_NOT_EXIST");
        }

        String orgCode=json.getString("orgCode");
        if(orgCode!=null){
            org.setOrgCode(orgCode);
        }
        Long parentId=json.getLong("parentId");
        if(parentId!=null){
            org.setParentId(parentId);
        }
        String orgName=json.getString("orgName");
        if(orgName!=null){
            org.setOrgName(orgName);
        }
        String shortName=json.getString("shortName");
        if(shortName!=null){
            org.setShortName(shortName);
        }
        String orgGroup=json.getString("orgGroup");
        if(orgGroup!=null){
            org.setOrgGroup(orgGroup);
        }
        String orgType=json.getString("orgType");
        if(orgType!=null){
            org.setOrgType(orgType);
        }
        String orgRank=json.getString("orgRank");
        if(orgRank!=null){
            org.setOrgRank(orgRank);
        }
        String remark=json.getString("remark");
        if(remark!=null){
            org.setRemark(remark);
        }
        try {
            amUnitService.update(org);
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getMessage());
        }

        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getAmUnit")
    public Object getOrg(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "orgId", required = false) Long orgId,
            @RequestParam(value = "charset", required = false) String charset)
            throws ValidateException {
        if(orgId==null){
            logger.info("orgId必填");
            throw new BizException(200019, "ORGID_IS_REQUIRE");
        }

        AmUnit org;
        try {
            org = amUnitService.findById(orgId);
            if(org==null){
                logger.info("组织机构不存在");
                throw new BizException(200020,"ORG_NOT_EXIST");
            }
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getMessage());
        }

        String jsonStr=JSON.toJSONString(org);
        JSONObject json=JSON.parseObject(jsonStr);
        //json.remove("orgId");

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("org", json);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/disable")
    public Object disable(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "orgId", required = false) Long orgId,
            @RequestParam(value = "charset", required = false) String charset)
            throws ValidateException {
        if(orgId==null){
            logger.info("orgId必填");
            throw new BizException(200019, "ORGID_IS_REQUIRE");
        }

        try {
            amUnitService.disable(orgId);
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/enable")
    public Object enable(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "orgId", required = false) Long orgId,
            @RequestParam(value = "charset", required = false) String charset)
            throws ValidateException {
        if(orgId==null){
            logger.info("orgId必填");
            throw new BizException(200019, "ORGID_IS_REQUIRE");
        }

        try {
            amUnitService.enable(orgId);
        } catch (ServiceException e) {
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/search")
    public Object search(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson,
            HttpServletRequest request)
            throws ValidateException {


        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
                .addIntegerRule("sortField", false, 1,2, exception)
                .addIntegerRule("page", false, 1,1000000, exception)
                .addIntegerRule("pageSize", false, 1,100000, exception)
                .validate();

        if(json.containsKey("kwFields") && json.containsKey("keyword")){
            new ParamterValidate()
                    .addRule(new IsInteger(exception))
                    .addRule(new com.greathack.utils.validate.rule.IntegerBetween(1, 15, true, exception))
                    .validate(json.getString("kwFields"));
        }
        AmUnitSearchCriteria criteria =JSON.parseObject(strJson, AmUnitSearchCriteria.class);


        List<PowerItem> powerItemList= (List<PowerItem>) request.getAttribute("powerItemList");
        for(PowerItem powerItem:powerItemList){
            criteria.addFilter(powerItem.getParameter());
        }

        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page-1) * pageSize);
        criteria.setPageSize(pageSize);
        criteria.setOrderBy("orgLevel asc,orgGroup asc");

        SearchResult<AmUnit> searchResult=amUnitService.search(criteria);
        List<AmUnit> orgList = searchResult.getRecords();
        Long recordCount = searchResult.getRecordCount();
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("orgList", orgList);
        return result;
    }

    //选择单位控件
    @ResponseBody
    @RequestMapping(value = "/selectOrg")
    public Object selectOrg(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson,
            HttpServletRequest request)
            throws ValidateException {

        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
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
        AmUnitSearchCriteria criteria =JSON.parseObject(strJson, AmUnitSearchCriteria.class);

        List<PowerItem> powerItemList= (List<PowerItem>) request.getAttribute("powerItemList");
        for(PowerItem powerItem:powerItemList){
            criteria.addFilter(powerItem.getParameter());
        }

        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page-1) * pageSize);
        criteria.setPageSize(pageSize);
        criteria.setOrderBy("orgLevel asc,orgGroup asc");

        SearchResult<AmUnit> searchResult=amUnitService.search(criteria);
        List<AmUnit> orgList = searchResult.getRecords();
        Long recordCount = searchResult.getRecordCount();
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("list", orgList);
        return result;
    }



    //获取辅警单位对象集
    @ResponseBody
    @RequestMapping(value = "/getAmUnitObject")
    public Object getAmUnitObject(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset)
            throws ValidateException {

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("amUnitObject", amUnitService.getOrgs());
        return result;
    }


    //选择控件获取树形结构
    @ResponseBody
    @RequestMapping(value = "/getAmUnitTreeForSelect")
    public Object getAmUnitTreeForSelect(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson,
            HttpServletRequest request)
            throws ValidateException {
        return getAmUnitTree(loginCode ,charset,strJson,request);
    }


    //获取树形结构
    @ResponseBody
    @RequestMapping(value = "/getAmUnitTree")
    public Object getAmUnitTree(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson,
            HttpServletRequest request)
            throws ValidateException {


        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
                .addIntegerRule("sortField", false, 1,2, exception)
                .addIntegerRule("page", false, 1,1000000, exception)
                .addIntegerRule("pageSize", false, 1,1000, exception)
                .addBooleanRule("isOpen",false,true,null,exception)
                .validate();

        if(json.containsKey("kwFields") && json.containsKey("keyword")){
            new ParamterValidate()
                    .addRule(new IsInteger(exception))
                    .addRule(new com.greathack.utils.validate.rule.IntegerBetween(1, 15, true, exception))
                    .validate(json.getString("kwFields"));
        }
        boolean isOpen=false;
        if(json.containsKey("isOpen") && json.getBooleanValue("isOpen")){
            isOpen=true;
        }

        AmUnitSearchCriteria criteria =JSON.parseObject(strJson, AmUnitSearchCriteria.class);

        //数据权限读取
        List<PowerItem> powerItemList= (List<PowerItem>) request.getAttribute("powerItemList");
        for(PowerItem powerItem:powerItemList){
            criteria.addFilter(powerItem.getParameter());
        }

        int page = 1;
        int pageSize = 100000;
        criteria.setStartLine((page-1) * pageSize);
        criteria.setPageSize(pageSize);
        criteria.setOrderBy("orgLevel asc,orgGroup asc");

        SearchResult<AmUnit> searchResult=amUnitService.search(criteria);
        List<AmUnit> orgList = searchResult.getRecords();
        List<AmUnit> roots = getRoots(orgList);
        List<Map<String,Object>> orgTree=new ArrayList<Map<String, Object>>();
        for(AmUnit amUnit:roots){
            orgTree.add(getTree(orgList,amUnit.getOrgId(),isOpen));
        }
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("orgTree", orgTree);
        return result;
    }

    //递归形成单位树
    private Map<String,Object> getTree(List<AmUnit> amUnitList,long amUnitId,boolean isOpen){
        Map<String,Object> item=new HashMap<String, Object>();
        AmUnit amUnit=amUnitService.getOrgs().get(amUnitId);
        item.put("title",amUnit==null?"根节点":amUnit.getOrgName());
        item.put("id",amUnitId);
        item.put("parents",amUnit.getParents());//父节点集合2019-10-13
        item.put("orgCode",amUnit.getOrgCode());//增加单位编码2019-10-13
        if(isOpen && amUnit.getParentId()!=0){
            item.put("spread",true);
        }else if(amUnit.getParentId()==0) {
            item.put("spread",true);
        }else{
            item.put("spread",false);
        }
        List<AmUnit> children = getChildren(amUnitList,amUnitId);
        if(children.size()>0){
            List<Map<String,Object>> childrenTree=new ArrayList<Map<String, Object>>();
            for(AmUnit child:children){
                childrenTree.add(getTree(amUnitList,child.getOrgId(),isOpen));
            }
            item.put("children",childrenTree);
        }
        return item;
    }

    //在已知的列表中，获取下级单位列表
    private List<AmUnit> getChildren(List<AmUnit> amUnitList,long parentId){
        List<AmUnit> children=new ArrayList<AmUnit>();
        for(AmUnit amUnit:amUnitList){
            if(Objects.equals(amUnit.getParentId(),parentId)){
                children.add(amUnit);
            }
        }
        return children;
    }

    //在已知的列表中，获取最上级单位列表，既在本列表中找不到上级
    private List<AmUnit> getRoots(List<AmUnit> amUnitList){
        List<AmUnit> roots=new ArrayList<AmUnit>();
        Map<Long,AmUnit> amUnitMap=new HashMap<Long, AmUnit>();
        for(AmUnit amUnit:amUnitList){
            amUnitMap.put(amUnit.getOrgId(),amUnit);
        }
        for(AmUnit amUnit:amUnitList){
            if(!amUnitMap.containsKey(amUnit.getParentId())){
                roots.add(amUnit);
            }
        }
        return roots;
    }



    @ResponseBody
    @RequestMapping(value = "/searchAll")
    public Object searchAll(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson,
            HttpServletRequest request)
            throws ValidateException {


        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson,exception)
                .addIntegerRule("sortField", false, 1,2, exception)
                .addIntegerRule("page", false, 1,1000000, exception)
                .addIntegerRule("pageSize", false, 1,100000, exception)
                .validate();

        if(json.containsKey("kwFields") && json.containsKey("keyword")){
            new ParamterValidate()
                    .addRule(new IsInteger(exception))
                    .addRule(new com.greathack.utils.validate.rule.IntegerBetween(1, 15, true, exception))
                    .validate(json.getString("kwFields"));
        }
        AmUnitSearchCriteria criteria =JSON.parseObject(strJson, AmUnitSearchCriteria.class);

        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page-1) * pageSize);
        criteria.setPageSize(pageSize);
        criteria.setOrderBy("orgLevel asc,orgGroup asc");

        SearchResult<AmUnit> searchResult=amUnitService.search(criteria);
        List<AmUnit> orgList = searchResult.getRecords();
        Long recordCount = searchResult.getRecordCount();
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("orgList", orgList);
        return result;
    }

}
