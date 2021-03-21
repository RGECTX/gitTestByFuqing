
package com.greathack.homlin.controller.auxiliary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.auxiliary.AuxFjbzfp;
import com.greathack.homlin.pojo.auxiliary.AuxFjbzfpSearchCriteria;
import com.greathack.homlin.pojo.org.Organization;
import com.greathack.homlin.pojo.permission.PowerItem;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxDaglService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxFjbzfpService;
import com.greathack.homlin.serviceinterface.org.IOrganizationService;
import com.greathack.utils.tools.TypeOption;
import com.greathack.utils.validate.JsonParamValidate;
import com.greathack.utils.validate.ParamterValidate;
import com.greathack.utils.validate.exception.ValidateException;
import com.greathack.utils.validate.rule.IntegerBetween;
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

@Controller
@RequestMapping(value = "/auxFjbzfp")
public class AuxFjbzfpController {
    private static Logger logger = LoggerFactory.getLogger(AuxFjbzfp.class);
    private BizException bizException = new BizException(200001, "INVALID_PARAMS");

    @Autowired
    private IOrganizationService organizationService;
    @Autowired
    private IAuxFjbzfpService auxFjbzfpService;
    @Autowired
    private IAuxDaglService auxDaglService;

    @ResponseBody
    @RequestMapping("/add")
    public Object add(@RequestParam(value = "loginCode", required = true) String loginCode,
                      @RequestParam(value = "charset", required = false) String charset,
                      @RequestBody String strJson, HttpServletRequest httpServletRequest) throws ValidateException {
        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        //对传进来的参数进行转码
        try {
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e) {
            logger.info("无效的charSet");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        //对Json里面的参数进行校验
        JSONObject jsonObject = new JsonParamValidate(strJson, bizException).validate();

        //json转化为javaBean
        AuxFjbzfp auxFjbzfp = JSON.toJavaObject(jsonObject, AuxFjbzfp.class);
        //通过Id查询部门名
        List<Organization> organizationList = organizationService.findOrgName(auxFjbzfp.getOrgId());
        Organization org = organizationList.get(0);
        auxFjbzfp.setOrgName(org.getOrgName());
        Integer count = auxDaglService.findByOrgId(auxFjbzfp.getOrgId(), auxFjbzfp.getFjType());
        auxFjbzfp.setExistingPeople(count + "");
        auxFjbzfp.setState("1");
        /*if (count >= 1) {
            auxFjbzfp.setState("1");
        } else {
            auxFjbzfp.setExistingPeople("0");
        }*/
        List<AuxFjbzfp> fjbzfps=auxFjbzfpService.findAll();
        for(AuxFjbzfp fjbzfp:fjbzfps){
            if(auxFjbzfp.getOrgId()==fjbzfp.getOrgId()&&auxFjbzfp.getFjType().equals(fjbzfp.getFjType())){
                logger.info("该单位下的辅警类型已经存在");
                throw new BizException(400030, "该单位下的辅警类型已经存在");
            }
        }
        try {
            auxFjbzfpService.add(auxFjbzfp);
        } catch (ServiceException e) {
            logger.info("添加辅警编制分配情况异常");
            e.printStackTrace();
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }

        String jsonString = JSON.toJSONString(auxFjbzfp);
        JSONObject parseObject = JSON.parseObject(jsonString);
        Map<String, Object> result = new HashMap<>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("auxFjbzfp", parseObject);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete(@RequestParam(value = "loginCode", required = true) String loginCode,
                         @RequestParam(value = "charset", required = false) String charset,
                         @RequestParam(value = "ids", required = true) String id) throws ValidateException {
        if (id == null) {
            logger.info("id必填");
            throw new BizException(20045, "RELATION_ID_IS_REQUIRE");
        }
        Map<String, Object> result = new HashMap<>();

        String[] idArray = id.split(",");
        for (String element : idArray) {
            auxFjbzfpService.delete(Long.parseLong(element));
        }
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Object update(@RequestParam(value = "loginCode", required = true) String loginCode,
                         @RequestParam(value = "charset", required = false) String charset,
                         @RequestBody String strJosn, HttpServletRequest httpServletRequest) throws ValidateException {
        //对传进来的参数进行转码
        try {
            strJosn = URLDecoder.decode(strJosn, charset);
        } catch (UnsupportedEncodingException e) {
            logger.info("无效的charSet");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        //对JSON里面的参数进行校验
        JSONObject validate = new JsonParamValidate(strJosn, bizException).validate();

        Long fjbzfpId = validate.getLong("fjbzfpId");
        AuxFjbzfp auxFjbzfp1 = auxFjbzfpService.get(fjbzfpId);
        if (auxFjbzfp1 == null) {
            logger.info("辅警编制分配情况不存在");
            throw new BizException(40030, "FJBZFP_NOT_EXIST");
        }

        //将JSON转化为JavaBean
        AuxFjbzfp auxFjbzfp = JSON.toJavaObject(validate, AuxFjbzfp.class);
        Integer count = auxDaglService.findByOrgId(auxFjbzfp.getOrgId(), auxFjbzfp.getFjType());
        if (count >= 1) {
            auxFjbzfp.setExistingPeople(count + "");
        } else {
            auxFjbzfp.setExistingPeople("0");
        }
        try {
            auxFjbzfpService.update(auxFjbzfp);
        } catch (Exception e) {
            logger.info("修改辅警编制分配情况异常");
            e.printStackTrace();
        }
        Map<String, Object> result = new HashMap<>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");

        return result;
    }

    @ResponseBody
    @RequestMapping("/search")
    public Object search(@RequestParam(value = "loginCode", required = true) String loginCode,
                         @RequestParam(value = "charset", required = false) String charset,
                         @RequestBody String strJson, HttpServletRequest httpServletRequest) throws ValidateException {
        JSONObject jsonObject = new JsonParamValidate(strJson, bizException)
                .addIntegerRule("sortField", false, 1, 2, bizException)
                .addIntegerRule("page", false, 1, 1000000, bizException)
                .addIntegerRule("pageSize", false, 1, 1000, bizException)
                .validate();


        if (jsonObject.containsKey("kwFields") && jsonObject.containsKey("keyword")) {
            new ParamterValidate()
                    .addRule(new IsInteger(bizException))
                    .addRule(new IntegerBetween(1, 15, true, bizException))
                    .validate(jsonObject.getString("kwFields"));
        }
        AuxFjbzfpSearchCriteria auxFjbzfpSearchCriteria = JSON.parseObject(strJson, AuxFjbzfpSearchCriteria.class);

        //数据权限读取
        List<PowerItem> powerItemList = (List<PowerItem>) httpServletRequest.getAttribute("powerItemList");
        for (PowerItem powerItem : powerItemList) {
            auxFjbzfpSearchCriteria.addFilter(powerItem.getParameter());
        }
        Integer page = TypeOption.nullToValue(jsonObject.getString("page"), 1);
        Integer pageSize = TypeOption.nullToValue(jsonObject.getString("pageSize"), 20);
        auxFjbzfpSearchCriteria.setStartLine((page - 1) * pageSize);
        auxFjbzfpSearchCriteria.setPageSize(pageSize);

        List<AuxFjbzfp> auxFjbzfps=new ArrayList<>();

        List<AuxFjbzfp> auxFjbzfpList = auxFjbzfpService.search(auxFjbzfpSearchCriteria);
        for (AuxFjbzfp auxFjbzfp : auxFjbzfpList) {
            Integer count = auxDaglService.findByOrgId(auxFjbzfp.getOrgId(), auxFjbzfp.getFjType());
            if (count >= 1) {
                auxFjbzfp.setExistingPeople(count + "");
            } else {
                auxFjbzfp.setExistingPeople("0");
            }
            auxFjbzfps.add(auxFjbzfp);
        }
        Long recordCount = auxFjbzfpService.getSearchResultCount(auxFjbzfpSearchCriteria);




        Map<String, Object> result = new HashMap<>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxFjbzfpList", auxFjbzfps);
        return result;
    }

    @ResponseBody
    @RequestMapping("/searchTab")
    public Object searchTab(@RequestParam(value = "loginCode", required = true) String loginCode,
                         @RequestParam(value = "charset", required = false) String charset,
                         @RequestBody String strJson, HttpServletRequest httpServletRequest) throws ValidateException {
        JSONObject jsonObject = new JsonParamValidate(strJson, bizException)
                .addIntegerRule("sortField", false, 1, 2, bizException)
                .addIntegerRule("page", false, 1, 1000000, bizException)
                .addIntegerRule("pageSize", false, 1, 1000, bizException)
                .validate();

        if (jsonObject.containsKey("kwFields") && jsonObject.containsKey("keyword")) {
            new ParamterValidate()
                    .addRule(new IsInteger(bizException))
                    .addRule(new IntegerBetween(1, 15, true, bizException))
                    .validate(jsonObject.getString("kwFields"));
        }
        AuxFjbzfpSearchCriteria auxFjbzfpSearchCriteria = JSON.parseObject(strJson, AuxFjbzfpSearchCriteria.class);
        //数据权限读取
        List<PowerItem> powerItemList = (List<PowerItem>) httpServletRequest.getAttribute("powerItemList");
        for (PowerItem powerItem : powerItemList) {
            auxFjbzfpSearchCriteria.addFilter(powerItem.getParameter());
        }
        Integer page = TypeOption.nullToValue(jsonObject.getString("page"), 1);
        Integer pageSize = TypeOption.nullToValue(jsonObject.getString("pageSize"), 20);
        auxFjbzfpSearchCriteria.setStartLine((page - 1) * pageSize);
        auxFjbzfpSearchCriteria.setPageSize(pageSize);

        List<AuxFjbzfp> auxFjbzfps = new ArrayList<>();
        List<AuxFjbzfpSearchCriteria> searchCriterias = new ArrayList<AuxFjbzfpSearchCriteria>();
        List<AuxFjbzfp> auxFjbzfpList = auxFjbzfpService.search(auxFjbzfpSearchCriteria);//查询所有数据

       /* for (AuxFjbzfp auxFjbzfp : auxFjbzfpList) {
            Integer count = auxDaglService.findByOrgId(auxFjbzfp.getOrgId(), auxFjbzfp.getFjType());//判断现有人数
            if (count >= 1) {
                auxFjbzfp.setExistingPeople(count + "");
            } else {
                auxFjbzfp.setExistingPeople("0");
            }
            auxFjbzfps.add(auxFjbzfp);

        }*/
        List<AuxFjbzfp> fjType1 = new ArrayList<>();
        List<AuxFjbzfp> fjType2 = new ArrayList<>();
        List<AuxFjbzfp> fjType4 = new ArrayList<>();

        List<AuxFjbzfpSearchCriteria> criteriaList = copy(auxFjbzfpList, AuxFjbzfpSearchCriteria.class);

        for (AuxFjbzfpSearchCriteria searchCriteria : criteriaList) {
            System.out.println(searchCriteria.toString());

                if (searchCriteria.getFjType().equals("1")) {
                    List<AuxFjbzfp> fjType11 = auxFjbzfpService.findFjType1(searchCriteria);
                   fjType1.add(fjType11.get(0));
                }
                if (searchCriteria.getFjType().equals("2")) {
                    List<AuxFjbzfp> fjType21 = auxFjbzfpService.findFjType2(searchCriteria);
                   fjType2.add(fjType21.get(0));
                }
                if (searchCriteria.getFjType().equals("4")) {
                    List<AuxFjbzfp> fjType41 = auxFjbzfpService.findFjType4(searchCriteria);
                   fjType4.add(fjType41.get(0));
                }
        }
        Long recordCount = auxFjbzfpService.getSearchResultCount(auxFjbzfpSearchCriteria);
        List<AuxFjbzfpSearchCriteria> copy1 = copy(fjType1, AuxFjbzfpSearchCriteria.class);
        List<AuxFjbzfpSearchCriteria> copy2 = copy(fjType2, AuxFjbzfpSearchCriteria.class);
        List<AuxFjbzfpSearchCriteria> copy4 = copy(fjType4, AuxFjbzfpSearchCriteria.class);
        /*if (copy1 != null) {*/
            for (AuxFjbzfpSearchCriteria searchCriteria : copy1) {
                if (null != searchCriteria) {
                    searchCriteria.setExistingPeople1(searchCriteria.getExistingPeople());
                    searchCriteria.setFormationPeople1(searchCriteria.getFormationPeople());


                    /*if (copy2 != null) {*/
                    for (AuxFjbzfpSearchCriteria fjbzfpSearchCriteria : copy2) {
                        if (null != fjbzfpSearchCriteria) {
                            if (searchCriteria.getOrgId() == fjbzfpSearchCriteria.getOrgId()) {
                                searchCriteria.setExistingPeople2(fjbzfpSearchCriteria.getExistingPeople());
                                searchCriteria.setFormationPeople2(fjbzfpSearchCriteria.getFormationPeople());
                            }
                        }
                    }
                    /*if (copy4 != null) {*/
                    for (AuxFjbzfpSearchCriteria criteria : copy4) {
                        if (null != criteria) {
                            if (searchCriteria.getOrgId() == criteria.getOrgId()) {
                                searchCriteria.setExistingPeople4(criteria.getExistingPeople());
                                searchCriteria.setFormationPeople4(criteria.getFormationPeople());
                            }
                        }
                    }

                    searchCriterias.add(searchCriteria);
                }
            }
                if (copy2!=null){
                        for (AuxFjbzfpSearchCriteria fjbzfpSearchCriteria : copy2) {
                            if (null != fjbzfpSearchCriteria) {
                                fjbzfpSearchCriteria.setExistingPeople2(fjbzfpSearchCriteria.getExistingPeople());
                                fjbzfpSearchCriteria.setFormationPeople2(fjbzfpSearchCriteria.getFormationPeople());
                                for (AuxFjbzfpSearchCriteria criteria : copy4) {
                                    if (null != criteria) {
                                        if (fjbzfpSearchCriteria.getOrgId() == criteria.getOrgId()) {
                                            fjbzfpSearchCriteria.setExistingPeople4(criteria.getExistingPeople());
                                            fjbzfpSearchCriteria.setFormationPeople4(criteria.getFormationPeople());
                                        }
                                    }
                                }
                            }
                           searchCriterias.add(fjbzfpSearchCriteria);
                        }
                    }
                   if (copy4!=null){
                       for (AuxFjbzfpSearchCriteria criteria : copy4) {
                           if (null != criteria) {

                               criteria.setExistingPeople4(criteria.getExistingPeople());
                               criteria.setFormationPeople4(criteria.getFormationPeople());
                           }
                           searchCriterias.add(criteria);
                       }
                   }
        for (int i = 0; i < searchCriterias.size()-1; i++) {
            for (int j = searchCriterias.size()-1; j > i; j--) {
                if (searchCriterias.get(j).getOrgId()==searchCriterias.get(i).getOrgId()) {
                    searchCriterias.remove(j);
                }
            }
        }


        Map<String, Object> result = new HashMap<>();
                result.put("errCode", 0);
                result.put("errMsg", "SUCCESS");
                result.put("recordCount", recordCount);
                result.put("searchCriterias", searchCriterias);
                return result;

    }
    public static <T> List<T> copy(List<?> list,Class<T> clazz){
        String oldOb = JSON.toJSONString(list);
        return JSON.parseArray(oldOb, clazz);
    }

}
