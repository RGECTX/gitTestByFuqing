package com.greathack.homlin.controller.auxiliary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.common.Common;
import com.greathack.homlin.config.Global;
import com.greathack.homlin.core.domain.AjaxResult;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxFjbzfpDAO;
import com.greathack.homlin.daointerface.auxiliary.IAuxGzglDAO;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.file.FileUtils;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.auxiliary.*;
import com.greathack.homlin.pojo.dict.Dict;
import com.greathack.homlin.pojo.dict.DictData;
import com.greathack.homlin.pojo.org.Organization;
import com.greathack.homlin.pojo.permission.PowerItem;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.serviceinterface.am.IAmUnitService;
import com.greathack.homlin.serviceinterface.attachment.IAttachmentService;
import com.greathack.homlin.serviceinterface.auxiliary.*;
import com.greathack.homlin.serviceinterface.dict.IDictDataService;
import com.greathack.homlin.serviceinterface.dict.IDictService;
import com.greathack.homlin.serviceinterface.org.IOrganizationService;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.homlin.utils.poi.ExcelUtil;
import com.greathack.homlin.utils.poi.StringUtils;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.greathack.homlin.utils.poi.CommonUtils.getDictValue;

/**
 * ????????????
 *
 * @author renTX
 * @date 2020-09-07
 */
@Controller
@RequestMapping(value = "/auxDagl")
public class AuxDaglController {

    private static Logger logger = LoggerFactory.getLogger(AuxDaglController.class);
    private BizException exception = new BizException(200001, "INVALID_PARAMS");
    private static IAuxGzglDAO auxGzglDAO = (IAuxGzglDAO) DAOFactory.createDAO("IAuxGzglDAO");
    private static IAuxFjbzfpDAO auxFjbzfpDAO = (IAuxFjbzfpDAO) DAOFactory.createDAO("IAuxFjbzfpDAO");

    @Autowired
    private IAuxDaglService auxDaglService;

    @Autowired
    private IAuxHmdService auxHmdService;

    @Autowired
    private IOrganizationService organizationService;

    @Autowired
    private IAttachmentService iattachmentService;

    @Autowired
    private IJobResumeService jobResumeService;

    @Autowired
    private IEduResumeService eduResumeService;

    @Autowired
    private IAuxQsgxService auxQsgxService;

    @Autowired
    private IAmUnitService amUnitService;

    @Autowired
    private IDictService dictService;

    @Autowired
    private IDictDataService dictDataService;


    @ResponseBody
    @RequestMapping(value = "/add")
    public Object add(@RequestParam(value = "charset", required = false) String charset,
                      @RequestParam(value = "loginCode", required = true) String loginCode,
                      @RequestBody String strJson, HttpServletRequest request) throws ValidateException {
        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        //?????????????????????????????????
        try {
            //???strJson????????????
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("?????????charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        //???json???????????????????????????
        JSONObject json = new JsonParamValidate(strJson, exception)
                /*   .addStringRule("xm,orgName,receiveOrgId", true, true, 64, exception)
                   .addStringRule("state", false, false, 64, exception)
                   .addStringRule("idcard", true, true, 30, exception)
                   .addLongRule("orgId", true, 0L, 20L, exception)
                   .addStringRule("remark, outKey1, outKey2", false, false, 255, exception)*/
                .validate();
        //json??????javaBean
        AuxDagl auxDagl = JSON.toJavaObject(json, AuxDagl.class);
        auxDagl.setCreateBy(admin.getUserId());
        auxDagl.setCreateTime(Common.getCurrentTime());
        //??????Id???????????????
        List<Organization> organizationList = organizationService.findOrgName(auxDagl.getOrgId());
        Organization org = organizationList.get(0);
        auxDagl.setOrgName(org.getOrgName());
        auxDagl.setState("1");

        String patternMainLand = "/^\\d{6}(18|19|20)?\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|[xX])$/";//??????
        String patternHongkong = "/^((\\s?[A-Za-z])|([A-Za-z]{2}))\\d{6}(\\([0???9aA]\\)|[0-9aA])$/";//??????
        String patternTaiwan = "/^[a-zA-Z][0-9]{9}$/";//??????
        String patternMacao = "/^[1|5|7][0-9]{6}\\([0-9Aa]\\)/";//??????


        //???????????????????????????????????????
        List<AuxHmd> auxHmdList = auxHmdService.findAll();

        /*for(AuxHmd auxHmd : auxHmdList){
            if(Pattern.matches(patternMainLand, auxHmd.getIdcard())){//??????????????????????????????????????????
                if (auxHmd.getIdcard().substring(0, 17).equals(auxDagl.getIdcard().substring(0, 17))) {//????????????
                    logger.info("?????????????????????????????????");
                    throw new BizException(400030, "?????????????????????????????????");
                }
            }else if(Pattern.matches(patternHongkong, auxHmd.getIdcard())){//??????????????????????????????????????????
                if (auxHmd.getIdcard().substring(0, 6).equals(auxDagl.getIdcard().substring(0, 6))) {//????????????
                    logger.info("?????????????????????????????????");
                    throw new BizException(400030, "?????????????????????????????????");
                }
            }else if(Pattern.matches(patternTaiwan, auxHmd.getIdcard())){//??????????????????????????????????????????
                if (auxHmd.getIdcard().substring(0, 9).equals(auxDagl.getIdcard().substring(0, 9))) {//????????????
                    logger.info("?????????????????????????????????");
                    throw new BizException(400030, "?????????????????????????????????");
                }
            }else if(Pattern.matches(patternMacao, auxHmd.getIdcard())){//??????????????????????????????????????????
                if (auxHmd.getIdcard().substring(0, 8).equals(auxDagl.getIdcard().substring(0, 8))) {//????????????
                    logger.info("?????????????????????????????????");
                    throw new BizException(400030, "?????????????????????????????????");
                }
            }
        }*/

        for (AuxHmd auxHmd : auxHmdList) {
            if (!auxHmd.getIdcard().equals("")) {
                if (auxHmd.getIdcard().length() == 18) {
                    if (auxHmd.getIdcard().substring(0, 17).equals(auxDagl.getIdcard().substring(0, 17))) {
                        logger.info("?????????????????????????????????");
                        throw new BizException(400030, "?????????????????????????????????");
                    }
                }
            }
        }

        List<AuxDagl> auxDaglList = auxDaglService.findAll();
        for (AuxDagl dagl : auxDaglList) {
            if (!dagl.getIdcard().equals("")) {
                if (dagl.getIdcard().length() == 18) {
                    String substring = dagl.getIdcard().substring(0, 17);
                    boolean isTrue = substring.matches("[0-9]+");
                    if (isTrue) {
                        if (dagl.getIdcard().substring(0, 17).equals(auxDagl.getIdcard().substring(0, 17))) {
                            if(!dagl.getState().equals("2")){//?????????????????? ????????????????????????????????????
                                logger.info("????????????????????????????????????????????????????????????");
                                throw new BizException(400030, "????????????????????????????????????????????????????????????");
                            }

                        }

                    }
                }
            }
        }

        try {
            auxDagl = auxDaglService.add(auxDagl);
        } catch (ServiceException e) {
            logger.info("????????????????????????");
            e.printStackTrace();
            throw new BizException(e.getErrCode(), e.getErrMsg());
        }
        String jsonString = JSON.toJSONString(auxDagl);
        JSONObject auxDaglObj = JSON.parseObject(jsonString);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("auxDaglObj", auxDaglObj);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "ids", required = false) String id) throws ValidateException {
        if (id == null) {
            logger.info("id??????");
            throw new BizException(200045, "RELATION_ID_IS_REQUIRE");
        }
        Map<String, Object> result = new HashMap<String, Object>();

        String[] idArray = id.split(",");
        for (String elment : idArray) {
            auxDaglService.delete(Long.parseLong(elment));

            iattachmentService.deleteByinstanceId(Long.parseLong(elment));

        }
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");

        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/update")
    public Object update(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson) throws ValidateException {
        //?????????????????????????????????
        try {
            //??????strJson????????????
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("?????????charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        //3??????json???????????????????????????
        JSONObject json = new JsonParamValidate(strJson, exception)
                /*  .addStringRule("xm,orgName,receiveOrgId", true, true, 64, exception)
                  .addStringRule("state", false, false, 64, exception)
                  .addStringRule("idcard", true, true, 30, exception)
                  .addLongRule("orgId", true, 0L, 20L, exception)
                  .addStringRule("remark, outKey1, outKey2", false, false, 255, exception)*/
                .validate();
        Long id = json.getLong("daId");
        AuxDagl oldAuxDagl = auxDaglService.get(id);
        if (oldAuxDagl == null) {
            logger.info("???????????????????????????");
            throw new BizException(400030, "DAGL_NOT_EXIST");
        }
        AuxDagl auxDagl = JSON.toJavaObject(json, AuxDagl.class);
        //??????Id???????????????
     /*   List<Organization> organizationList = organizationService.findOrgName(auxDagl.getOrgId());
        Organization org = organizationList.get(0);
        auxDagl.setOrgName(org.getOrgName());*/

        String yfgz = auxDagl.getYfgz();
        if(!yfgz.equals("")){
            AuxGzgl auxGzgl = new AuxGzgl();
            auxGzgl.setYfgz(yfgz);
            auxGzgl.setDaId(auxDagl.getDaId());
            auxGzglDAO.updateByYfgz(auxGzgl);
        }


        try {
            auxDaglService.update(auxDagl);
        } catch (Exception e) {
            logger.info("??????????????????????????????");
            e.printStackTrace();
        }
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/search")
    public Object search(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson,
            HttpServletRequest request)
            throws ValidateException {
        //3??????json???????????????????????????
        JSONObject json = new JsonParamValidate(strJson, exception)
                .addIntegerRule("sortField", false, 1, 2, exception)
                .addIntegerRule("page", false, 1, 1000000, exception)
                .addIntegerRule("pageSize", false, 1, 1000, exception)
                .validate();

        if (json.containsKey("kwFields") && json.containsKey("keyword")) {
            new ParamterValidate()
                    .addRule(new IsInteger(exception))
                    .addRule(new IntegerBetween(1, 15, true, exception))
                    .validate(json.getString("kwFields"));
        }
        AuxDaglSearchCriteria criteria = JSON.parseObject(strJson, AuxDaglSearchCriteria.class);
        //??????????????????
        List<PowerItem> powerItemList = (List<PowerItem>) request.getAttribute("powerItemList");
        for (PowerItem powerItem : powerItemList) {
            criteria.addFilter(powerItem.getParameter());
        }
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);

        List<AuxDagl> auxDaglList = auxDaglService.search(criteria);

        Long recordCount = auxDaglService.getSearchResultCount(criteria);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxDaglList", auxDaglList);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/rytj")
    public Object rytj(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson,
            HttpServletRequest request)
            throws ValidateException {
        //3??????json???????????????????????????
        JSONObject json = new JsonParamValidate(strJson, exception)
                .addIntegerRule("sortField", false, 1, 2, exception)
                .addIntegerRule("page", false, 1, 1000000, exception)
                .addIntegerRule("pageSize", false, 1, 1000, exception)
                .validate();

        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();

        if (json.containsKey("kwFields") && json.containsKey("keyword")) {
            new ParamterValidate()
                    .addRule(new IsInteger(exception))
                    .addRule(new IntegerBetween(1, 15, true, exception))
                    .validate(json.getString("kwFields"));
        }
        AuxDaglSearchCriteria criteria = JSON.parseObject(strJson, AuxDaglSearchCriteria.class);
        criteria.setOrgId(Long.valueOf(admin.getAmUnitId()));
        //??????????????????
        List<PowerItem> powerItemList = (List<PowerItem>) request.getAttribute("powerItemList");
        for (PowerItem powerItem : powerItemList) {
            criteria.addFilter(powerItem.getParameter());
        }
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);


        if(criteria.getOrgId()==9){//????????????????????????
            List<AuxDagl> auxDaglList2 = auxDaglService.rytj4(criteria);//???auxDaglList2??????
            List<AuxDagl> auxDaglList = auxDaglService.rytj3(criteria);

            List<AuxDagl> auxDaglList3 = auxDaglService.rytj7(criteria);//??????????????????

            List<AuxFjbzfp> auxFjbzfpList = auxFjbzfpDAO.rytj();

            List<AuxFjbzfp> auxFjbzfpListcp = auxFjbzfpDAO.rytjcp();//??????
            List<AuxFjbzfp> auxFjbzfpListcl = auxFjbzfpDAO.rytjcl();//??????
            List<AuxFjbzfp> auxFjbzfpListcz = auxFjbzfpDAO.rytjcz();//??????

            for (AuxDagl auxDagl2:auxDaglList2) {
                for (AuxDagl auxDagl:auxDaglList) {
                    if(auxDagl2.getOrgId().equals(auxDagl.getOrgId())){
                        auxDagl2.setCountxb(auxDagl.getCountxb());
                    }

                }
            }
            for (AuxDagl auxDagl2:auxDaglList2) {
                for (AuxFjbzfp auxFjbzfp:auxFjbzfpList) {
                    if(auxDagl2.getOrgId().equals(auxFjbzfp.getOrgId())){
                        auxDagl2.setBzrs(auxFjbzfp.getCountformationPeople());
                    }
                }
            }
            for (AuxDagl auxDagl2:auxDaglList2) {//?????????????????? ??????
                for (AuxDagl auxDagl:auxDaglList3) {
                    if(auxDagl2.getOrgId().equals(auxDagl.getOrgId())){
                        auxDagl2.setZzfjs(auxDagl.getZzfjs());
                    }
                }
            }

            for (AuxDagl auxDagl2:auxDaglList2) {
                for (AuxFjbzfp auxFjbzfp:auxFjbzfpListcp) {
                    if(auxDagl2.getOrgId().equals(auxFjbzfp.getOrgId())){
                        auxDagl2.setPtfjbzs(Integer.valueOf(auxFjbzfp.getFormationPeople()));//?????????????????????
                        auxDagl2.setPtfjrs(Integer.valueOf(auxFjbzfp.getExistingPeople()));//??????????????????
                    }
                }
            }
            for (AuxDagl auxDagl2:auxDaglList2) {
                for (AuxFjbzfp auxFjbzfp:auxFjbzfpListcl) {
                    if(auxDagl2.getOrgId().equals(auxFjbzfp.getOrgId())){
                        auxDagl2.setLffjbzs(Integer.valueOf(auxFjbzfp.getFormationPeople()));//?????????????????????
                        auxDagl2.setLffjrs(Integer.valueOf(auxFjbzfp.getExistingPeople()));//??????????????????
                    }
                }
            }
            for (AuxDagl auxDagl2:auxDaglList2) {
                for (AuxFjbzfp auxFjbzfp:auxFjbzfpListcz) {
                    if(auxDagl2.getOrgId().equals(auxFjbzfp.getOrgId())){
                        auxDagl2.setZxfjbzs(Integer.valueOf(auxFjbzfp.getFormationPeople()));//?????????????????????
                        auxDagl2.setZxfjrs(Integer.valueOf(auxFjbzfp.getExistingPeople()));//??????????????????
                    }
                }
            }

            for (AuxDagl auxDagl2:auxDaglList2) {
                if(auxDagl2.getBzrs()==null){
                    auxDagl2.setBzrs(0);
                }
                if(auxDagl2.getCountxb()==null){
                    auxDagl2.setCountxb(0);
                }
                if(auxDagl2.getCountisbx()==null){
                    auxDagl2.setCountisbx(0);
                }
                if(auxDagl2.getZzfjs()==null){//????????????????????????
                    auxDagl2.setZzfjs(0);
                }

                if(auxDagl2.getPtfjbzs()==null){
                    auxDagl2.setPtfjbzs(0);
                }
                if(auxDagl2.getPtfjrs()==null){
                    auxDagl2.setPtfjrs(0);
                }
                if(auxDagl2.getZxfjbzs()==null){
                    auxDagl2.setZxfjbzs(0);
                }
                if(auxDagl2.getZxfjrs()==null){
                    auxDagl2.setZxfjrs(0);
                }
                if(auxDagl2.getLffjbzs()==null){
                    auxDagl2.setLffjbzs(0);
                }
                if(auxDagl2.getLffjrs()==null){
                    auxDagl2.setLffjrs(0);
                }

            }
            Long recordCount = auxDaglService.getSearchResultCount3(criteria);//???????????? ????????????????????????

            Map<String, Object> result = new HashMap<String, Object>();
            result.put("errCode", 0);
            result.put("errMsg", "SUCCESS");
            result.put("recordCount", recordCount);
            result.put("auxDaglList", auxDaglList2);
            return result;
        }else{
            List<AuxDagl> auxDaglList2 = auxDaglService.rytj2(criteria);//???auxDaglList2??????
            List<AuxDagl> auxDaglList = auxDaglService.rytj(criteria);

            List<AuxDagl> auxDaglList3 = auxDaglService.rytj5(criteria);//??????????????????

            List<AuxFjbzfp> auxFjbzfpList = auxFjbzfpDAO.rytj();

            List<AuxFjbzfp> auxFjbzfpListfp = auxFjbzfpDAO.rytjfp();//??????
            List<AuxFjbzfp> auxFjbzfpListfl = auxFjbzfpDAO.rytjfl();//??????
            List<AuxFjbzfp> auxFjbzfpListfz = auxFjbzfpDAO.rytjfz();//??????


            for (AuxDagl auxDagl2:auxDaglList2) {
                for (AuxDagl auxDagl:auxDaglList) {
                    if(auxDagl2.getOrgId().equals(auxDagl.getOrgId())){
                        auxDagl2.setCountxb(auxDagl.getCountxb());
                    }

                }
            }
            for (AuxDagl auxDagl2:auxDaglList2) {
                for (AuxFjbzfp auxFjbzfp:auxFjbzfpList) {
                    if(auxDagl2.getOrgId().equals(auxFjbzfp.getOrgId())){
                        auxDagl2.setBzrs(auxFjbzfp.getCountformationPeople());
                    }
                }
            }
            for (AuxDagl auxDagl2:auxDaglList2) {//?????????????????? ??????
                for (AuxDagl auxDagl:auxDaglList3) {
                    if(auxDagl2.getOrgId().equals(auxDagl.getOrgId())){
                        auxDagl2.setZzfjs(auxDagl.getZzfjs());
                    }
                }
            }

            for (AuxDagl auxDagl2:auxDaglList2) {
                for (AuxFjbzfp auxFjbzfp:auxFjbzfpListfp) {
                    if(auxDagl2.getOrgId().equals(auxFjbzfp.getOrgId())){
                        auxDagl2.setPtfjbzs(Integer.valueOf(auxFjbzfp.getFormationPeople()));//?????????????????????
                        auxDagl2.setPtfjrs(Integer.valueOf(auxFjbzfp.getExistingPeople()));//??????????????????
                    }
                }
            }
            for (AuxDagl auxDagl2:auxDaglList2) {
                for (AuxFjbzfp auxFjbzfp:auxFjbzfpListfl) {
                    if(auxDagl2.getOrgId().equals(auxFjbzfp.getOrgId())){
                        auxDagl2.setLffjbzs(Integer.valueOf(auxFjbzfp.getFormationPeople()));//?????????????????????
                        auxDagl2.setLffjrs(Integer.valueOf(auxFjbzfp.getExistingPeople()));//??????????????????
                    }
                }
            }
            for (AuxDagl auxDagl2:auxDaglList2) {
                for (AuxFjbzfp auxFjbzfp:auxFjbzfpListfz) {
                    if(auxDagl2.getOrgId().equals(auxFjbzfp.getOrgId())){
                        auxDagl2.setZxfjbzs(Integer.valueOf(auxFjbzfp.getFormationPeople()));//?????????????????????
                        auxDagl2.setZxfjrs(Integer.valueOf(auxFjbzfp.getExistingPeople()));//??????????????????
                    }
                }
            }

            for (AuxDagl auxDagl2:auxDaglList2) {
                if(auxDagl2.getBzrs()==null){
                    auxDagl2.setBzrs(0);
                }
                if(auxDagl2.getCountxb()==null){
                    auxDagl2.setCountxb(0);
                }
                if(auxDagl2.getCountisbx()==null){
                    auxDagl2.setCountisbx(0);
                }
                if(auxDagl2.getZzfjs()==null){//????????????????????????
                    auxDagl2.setZzfjs(0);
                }

                if(auxDagl2.getPtfjbzs()==null){
                    auxDagl2.setPtfjbzs(0);
                }
                if(auxDagl2.getPtfjrs()==null){
                    auxDagl2.setPtfjrs(0);
                }
                if(auxDagl2.getZxfjbzs()==null){
                    auxDagl2.setZxfjbzs(0);
                }
                if(auxDagl2.getZxfjrs()==null){
                    auxDagl2.setZxfjrs(0);
                }
                if(auxDagl2.getLffjbzs()==null){
                    auxDagl2.setLffjbzs(0);
                }
                if(auxDagl2.getLffjrs()==null){
                    auxDagl2.setLffjrs(0);
                }
            }
            Long recordCount = auxDaglService.getSearchResultCount2(criteria);//??????????????? ????????????????????????

            Map<String, Object> result = new HashMap<String, Object>();
            result.put("errCode", 0);
            result.put("errMsg", "SUCCESS");
            result.put("recordCount", recordCount);
            result.put("auxDaglList", auxDaglList2);
            return result;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getDagl")
    public Object getJbxx(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "daId", required = false) Long id,
            @RequestParam(value = "charset", required = false) String charset)
            throws ValidateException {
        if (id == null) {
            logger.info("id??????");
            throw new BizException(400030, "DAGL_DATAID_IS_REQUIRED");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        AuxDagl auxDagl = auxDaglService.get(id);

        if (auxDagl == null) {
            logger.info("?????????????????????");
            throw new BizException(400030, "DAGL_NOT_EXIST");
        }

        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("auxDagl", auxDagl);
        return result;
    }

    /**
     * ??????????????????
     *
     * @param fileName ????????????
     * @param delete   ????????????
     */
    @RequestMapping(value = "/download")
    public void fileDownload(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "downloadType", required = false) String downloadType,
            String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            AjaxResult ajaxResult = null;
            if ("0".equals(downloadType)) {
                ExcelUtil<AuxDaglImportVo> util = new ExcelUtil<AuxDaglImportVo>(AuxDaglImportVo.class);
                ajaxResult = util.importTemplateExcel("??????????????????");
            } else if ("1".equals(downloadType)) {
                ExcelUtil<JobResumeImportVo> util = new ExcelUtil<JobResumeImportVo>(JobResumeImportVo.class);
                ajaxResult = util.importTemplateExcel("????????????");
            } else if ("2".equals(downloadType)) {
                ExcelUtil<EduResumeImportVo> util = new ExcelUtil<EduResumeImportVo>(EduResumeImportVo.class);
                ajaxResult = util.importTemplateExcel("????????????");
            } else if ("3".equals(downloadType)) {
                ExcelUtil<AuxQsgxImportVo> util = new ExcelUtil<AuxQsgxImportVo>(AuxQsgxImportVo.class);
                ajaxResult = util.importTemplateExcel("????????????");
            }/*else if("4".equals(downloadType)){
                ExcelUtil<ContractImportVo> util = new ExcelUtil<ContractImportVo>(ContractImportVo.class);
                ajaxResult=util.importTemplateExcel("????????????");
            }*/


            fileName = ajaxResult.get("msg").toString();
            delete = true;

            if (!FileUtils.isValidFilename(fileName)) {
                throw new Exception(StringUtils.format(" ????????????({})??????????????????????????? ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = Global.getDownloadPath() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            //log.error("??????????????????", e);
        }
    }

    public String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE?????????
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // ???????????????
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google?????????
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // ???????????????
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    @ResponseBody
    @RequestMapping(value = "/export")
    public Object export(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "exportType", required = false) String exportType,
            @RequestBody String strJson,
            HttpServletRequest request)
            throws ValidateException {


        //3??????json???????????????????????????
        JSONObject json = new JsonParamValidate(strJson, exception)
                .addIntegerRule("sortField", false, 1, 2, exception)
                .validate();

        if (json.containsKey("kwFields") && json.containsKey("keyword")) {
            new ParamterValidate()
                    .addRule(new IsInteger(exception))
                    .addRule(new com.greathack.utils.validate.rule.IntegerBetween(1, 15, true, exception))
                    .validate(json.getString("kwFields"));
        }
        AuxDaglSearchCriteria criteria = JSON.parseObject(strJson, AuxDaglSearchCriteria.class);

        //??????????????????
        List<PowerItem> powerItemList = (List<PowerItem>) request.getAttribute("powerItemList");
        for (PowerItem powerItem : powerItemList) {
            criteria.addFilter(powerItem.getParameter());
        }


        //???????????????????????????
        List<Dict> dictList = dictService.getList(SystemConfig.getConfigData("DICT_APP_CODE"));
        Map<String, List<DictData>> dictMap = new HashMap<>();
        for (Dict dict : dictList) {
            List<DictData> dictDataList = dictDataService.getList(dict.getDictId());
            dictMap.put(dict.getDictCode() + "", dictDataList);
        }

        if ("0".equals(exportType)) {//????????????
            List<AuxDaglImportVo> list = auxDaglService.searchExport(criteria);//????????????????????????????????????
            for (AuxDaglImportVo auxDaglImportVo : list) {
                //????????????
                List<Organization> orgName = organizationService.findOrgName(auxDaglImportVo.getOrgId());//????????????ID???????????????
                auxDaglImportVo.setOrgName(orgName.get(0).getOrgName());//???????????????

                auxDaglImportVo.setXm(auxDaglImportVo.getXm());//??????
                auxDaglImportVo.setXb(getDictValue(auxDaglImportVo.getXb(), "SEX", "", dictMap));//??????
                auxDaglImportVo.setIdcard(auxDaglImportVo.getIdcard());//????????????
                auxDaglImportVo.setLxfs(auxDaglImportVo.getLxfs());//????????????
                if ("1".equals(auxDaglImportVo.getFby())) {
                    auxDaglImportVo.setFby("???");//?????????
                } else if ("0".equals(auxDaglImportVo.getFby())) {
                    auxDaglImportVo.setFby("???");//?????????
                }
                auxDaglImportVo.setByrq(auxDaglImportVo.getByrq());//????????????
                auxDaglImportVo.setHyzk(getDictValue(auxDaglImportVo.getHyzk(), "HYZK", "", dictMap));//????????????
                auxDaglImportVo.setZzmm(getDictValue(auxDaglImportVo.getZzmm(), "AM_ZZMM", "", dictMap));//????????????
                auxDaglImportVo.setXl(getDictValue(auxDaglImportVo.getXl(), "AM_XL", "", dictMap));//??????

                auxDaglImportVo.setFjbh(auxDaglImportVo.getFjbh());//????????????
                auxDaglImportVo.setHjdz(auxDaglImportVo.getHjdz());//????????????
                if ("1".equals(auxDaglImportVo.getIsdy())) {
                    auxDaglImportVo.setIsdy("???");//??????????????????
                } else if ("0".equals(auxDaglImportVo.getIsdy())) {
                    auxDaglImportVo.setIsdy("???");//??????????????????

                    auxDaglImportVo.setMz(getDictValue(auxDaglImportVo.getMz(), "MZ", "", dictMap));//??????
                    auxDaglImportVo.setJz(getDictValue(auxDaglImportVo.getJz(), "DRIVER_CARD", "", dictMap));//??????
                }
                auxDaglImportVo.setRzrq(auxDaglImportVo.getRzrq());//????????????
                auxDaglImportVo.setJtzz(auxDaglImportVo.getJtzz());//????????????
                if ("1".equals(auxDaglImportVo.getIsbx())) {
                    auxDaglImportVo.setIsbx("???");//????????????
                } else if ("0".equals(auxDaglImportVo.getIsbx())) {
                    auxDaglImportVo.setIsbx("???");//????????????
                }

                /*auxDaglImportVo.setHjdlx(getDictValue(auxDaglImportVo.getHjdlx(), "AM_HJDLX", "", dictMap));//???????????????*/
                auxDaglImportVo.setState(getDictValue(auxDaglImportVo.getState(), "AUX_DASTATE", "", dictMap));//??????????????????
                auxDaglImportVo.setFjType(getDictValue(auxDaglImportVo.getFjType(), "AUX_FJLB", "", dictMap));//????????????

            }
            ExcelUtil<AuxDaglImportVo> util = new ExcelUtil<AuxDaglImportVo>(AuxDaglImportVo.class);
            AjaxResult ajaxResult = util.exportExcel(list, "??????????????????");
            return ajaxResult;
        } else if ("1".equals(exportType)) {//????????????
            criteria.setAppCode(SystemConfig.getConfigData("AUXILIARY_APP_CODE"));
            List<AuxQsgxImportVo> list = auxQsgxService.searchExport(criteria);

            for (AuxQsgxImportVo auxQsgxImportVo : list) {
                auxQsgxImportVo.setKinshipTerm(getDictValue(auxQsgxImportVo.getKinshipTerm(), "APPELLATION", "", dictMap));//??????
                auxQsgxImportVo.setNowStatus(getDictValue(auxQsgxImportVo.getNowStatus(), "AM_REALTION_NOW_STATUS", "", dictMap));//??????
                auxQsgxImportVo.setHealthState(getDictValue(auxQsgxImportVo.getHealthState(), "AM_HEALTH_STATE", "", dictMap));//????????????
                auxQsgxImportVo.setEduLevel(getDictValue(auxQsgxImportVo.getEduLevel(), "AM_XL", "", dictMap));//??????
                auxQsgxImportVo.setKjysgbFlag(getDictValue(auxQsgxImportVo.getKjysgbFlag(), "KJYSGB_FLAG", "", dictMap));//??????????????????
                /*auxQsgxImportVo.setIdcard(auxQsgxImportVo.getIdcard());//???????????????*/

                if ("1".equals(auxQsgxImportVo.getCgjFlag())) {//?????????
                    auxQsgxImportVo.setCgjFlag("???");
                } else if ("2".equals(auxQsgxImportVo.getCgjFlag())) {
                    auxQsgxImportVo.setCgjFlag("???");
                }
                if ("1".equals(auxQsgxImportVo.getFzFlag())) {//??????
                    auxQsgxImportVo.setFzFlag("???");
                } else if ("2".equals(auxQsgxImportVo.getFzFlag())) {
                    auxQsgxImportVo.setFzFlag("???");
                }
                if ("1".equals(auxQsgxImportVo.getSysjFlag())) {//????????????
                    auxQsgxImportVo.setSysjFlag("???");
                } else if ("2".equals(auxQsgxImportVo.getSysjFlag())) {
                    auxQsgxImportVo.setSysjFlag("???");
                }
                if ("1".equals(auxQsgxImportVo.getRhflFlag())) {//????????????
                    auxQsgxImportVo.setRhflFlag("???");
                } else if ("2".equals(auxQsgxImportVo.getRhflFlag())) {
                    auxQsgxImportVo.setRhflFlag("???");
                }
                auxQsgxImportVo.setBjgzFlag(getDictValue(auxQsgxImportVo.getBjgzFlag(), "BJGZ_FLAG", "", dictMap));//????????????????????????
                auxQsgxImportVo.setGzddssfw(getDictValue(auxQsgxImportVo.getGzddssfw(), "DDSSFW", "", dictMap));//????????????????????????
                auxQsgxImportVo.setGzdwxz(getDictValue(auxQsgxImportVo.getGzdwxz(), "GZDWXZ", "", dictMap));//??????????????????
                auxQsgxImportVo.setJzddssfw(getDictValue(auxQsgxImportVo.getJzddssfw(), "DDSSFW", "", dictMap));//????????????????????????

            }
            ExcelUtil<AuxQsgxImportVo> util = new ExcelUtil<AuxQsgxImportVo>(AuxQsgxImportVo.class);
            AjaxResult ajaxResult = util.exportExcel(list, "????????????????????????");
            return ajaxResult;
        } else if ("2".equals(exportType)) {//??????????????????
            criteria.setAppCode(SystemConfig.getConfigData("AUXILIARY_APP_CODE"));
            List<JobResumeImportVo> list = jobResumeService.searchExport(criteria);
            for (JobResumeImportVo jobResumeImportVo : list) {

            }
            ExcelUtil<JobResumeImportVo> util = new ExcelUtil<JobResumeImportVo>(JobResumeImportVo.class);
            AjaxResult ajaxResult = util.exportExcel(list, "????????????????????????");
            return ajaxResult;
        } else if ("3".equals(exportType)) {//????????????
            criteria.setAppCode(SystemConfig.getConfigData("AUXILIARY_APP_CODE"));
            List<EduResumeImportVo> list = eduResumeService.searchExport(criteria);
            for (EduResumeImportVo eduResumeImportVo : list) {
                eduResumeImportVo.setEduLevel(getDictValue(eduResumeImportVo.getEduLevel(), "AM_XL", "", dictMap));//??????
                eduResumeImportVo.setDegree(getDictValue(eduResumeImportVo.getDegree(), "AM_XW", "", dictMap));//??????
            }
            ExcelUtil<EduResumeImportVo> util = new ExcelUtil<EduResumeImportVo>(EduResumeImportVo.class);
            AjaxResult ajaxResult = util.exportExcel(list, "????????????????????????");
            return ajaxResult;
        }/*else if("4".equals(exportType)){//????????????
            List<ContractImportVo> list = contractService.searchExport(criteria);
            for(ContractImportVo contractImportVo:list){
                contractImportVo.setCategory(getDictValue(contractImportVo.getCategory(),"CONTRACT_CATEGORY","",dictMap));//????????????
                contractImportVo.setState(getDictValue(contractImportVo.getState(),"CONTRACT_STATE","",dictMap));//????????????
            }
            ExcelUtil<ContractImportVo> util = new ExcelUtil<ContractImportVo>(ContractImportVo.class);
            AjaxResult ajaxResult= util.exportExcel(list, "??????????????????");
            return  ajaxResult;
        }*/


        return null;
    }

    /**
     * ??????
     */
    @RequestMapping("/import")
    @ResponseBody
    public Object importData(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "isJbxxCover", required = false) String isJbxxCover,
            @RequestParam(value = "importType", required = false) String importType,
            @RequestParam(value = "isDelete", required = false) String isDelete,
            MultipartFile file) throws Exception {
        //goodsService.unSale(id);
        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        Map<String, Object> params = new HashMap<>();
        params.put("createBy", admin.getUserId());
        params.put("isJbxxCover", isJbxxCover);
        params.put("isDelete", isDelete);

        Map<String, Object> result = new HashMap<String, Object>();
        if (file == null) {
            result.put("errCode", 1);
            result.put("errMsg", "???????????????");
            return result;
        }
        String msg = "";
        if ("0".equals(importType)) {//????????????
            ExcelUtil<AuxDaglImportVo> util = new ExcelUtil<AuxDaglImportVo>(AuxDaglImportVo.class);
            List<AuxDaglImportVo> amJbxxImportList = util.importExcel(file.getInputStream());
            msg = auxDaglService.importJbxx(amJbxxImportList, params);
        } else if ("2".equals(importType)) {//????????????
            ExcelUtil<JobResumeImportVo> util = new ExcelUtil<JobResumeImportVo>(JobResumeImportVo.class);
            List<JobResumeImportVo> jobResumeImportList = util.importExcel(file.getInputStream());
            msg = jobResumeService.importJobResume(jobResumeImportList, params);
        } else if ("3".equals(importType)) {//????????????
            ExcelUtil<EduResumeImportVo> util = new ExcelUtil<EduResumeImportVo>(EduResumeImportVo.class);
            List<EduResumeImportVo> eduResumeImportList = util.importExcel(file.getInputStream());
            msg = eduResumeService.importEduResume(eduResumeImportList, params);
        } else if ("1".equals(importType)) {//????????????
            ExcelUtil<AuxQsgxImportVo> util = new ExcelUtil<AuxQsgxImportVo>(AuxQsgxImportVo.class);
            List<AuxQsgxImportVo> auxQsgxImportVoList = util.importExcel(file.getInputStream());
            msg = auxQsgxService.importAuxQsgx(auxQsgxImportVoList, params);
        }/*else if("4".equals(importType)){//????????????
            ExcelUtil<ContractImportVo> util = new ExcelUtil<ContractImportVo>(ContractImportVo.class);
            List<ContractImportVo> contractImportList = util.importExcel(file.getInputStream());
            msg=contractService.importContract(contractImportList,params);
        }*/


        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("msg", msg);
        return result;
    }


    /**
     * ?????????????????????
     * @param loginCode
     * @param daId
     * @param charset
     * @return
     * @throws ValidateException
     */
    @ResponseBody
    @RequestMapping(value = "/exportJbxxCard")
    public Object exportJbxxCard(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "daId", required = false) Long daId,
            @RequestParam(value = "charset", required = false) String charset)
            throws ValidateException {
        if(daId==null){
            logger.info("id??????");
            throw new BizException(200045, "JBXX_ID_IS_REQUIRE");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        //Dict dict = dictService.getDict(Integer.parseInt(id));
        AuxDagl auxDagl=auxDaglService.get(daId);
       /* AmHjxx amHjxx=amHjxxService.findByJbxxId(amJbxx.getId());
        AmGzxx amGzxx=amGzxxService.findByJbxxId(amJbxx.getId());
        AmJtqk amJtqk=amJtqkService.findByJbxxId(amJbxx.getId());
        AmStqk amStqk=amStqkService.findByJbxxId(amJbxx.getId());*/
        Organization org=organizationService.getOrg(auxDagl.getOrgId());
        String orgName="";
        if(org!=null){
            orgName=org.getOrgName();
        }

        //????????????
        JobResume searchJobResume=new JobResume();
        searchJobResume.setInstanceId(auxDagl.getDaId()+"");
        List<JobResume> jobResumeList=jobResumeService.findByExample(searchJobResume);

        //????????????
        EduResume searchRealation=new EduResume();
        searchRealation.setInstanceId(auxDagl.getDaId()+"");
        List<EduResume> eduResumeList=eduResumeService.findByExample(searchRealation);

        if(auxDagl==null){
            logger.info("?????????????????????");
            throw new BizException(200046,"DICT_NOT_EXIST");
        }

        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("auxDagl", auxDagl);
        /*result.put("amHjxx", amHjxx);
        result.put("amGzxx", amGzxx);
        result.put("amJtqk", amJtqk);
        result.put("amStqk", amStqk);*/
        result.put("orgName", orgName);
        result.put("jobResumeList", jobResumeList);
        result.put("eduResumeList", eduResumeList);
        return result;
    }

   /* @ResponseBody
    @RequestMapping(value = "/export")
    public Object export(
            @RequestParam(value = "loginCode", required = true) String loginCode ,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "exportType", required = false) String exportType,
            @RequestBody String strJson,
            HttpServletRequest request)
            throws ValidateException {
        //3??????json???????????????????????????
        JSONObject json = new JsonParamValidate(strJson,exception)
                .addIntegerRule("sortField", false, 1,2, exception)
                .validate();

        if(json.containsKey("kwFields") && json.containsKey("keyword")){
            new ParamterValidate()
                    .addRule(new IsInteger(exception))
                    .addRule(new com.greathack.utils.validate.rule.IntegerBetween(1, 15, true, exception))
                    .validate(json.getString("kwFields"));
        }
        AuxDaglSearchCriteria criteria = JSON.parseObject(strJson, AuxDaglSearchCriteria.class);
        //??????????????????
        List<PowerItem> powerItemList= (List<PowerItem>) request.getAttribute("powerItemList");
        for(PowerItem powerItem:powerItemList){
            criteria.addFilter(powerItem.getParameter());
        }
        //???????????????????????????
        List<Dict> dictList=dictService.getList(SystemConfig.getConfigData("DICT_APP_CODE"));
        Map<String,List<DictData>> dictMap=new HashMap<>();
        for(Dict dict:dictList){
            List<DictData> dictDataList=dictDataService.getList(dict.getDictId());
            dictMap.put(dict.getDictCode()+"",dictDataList);
        }
        if("0".equals(exportType)){//????????????

            List<AuxDagl> auxDaglList = auxDaglService.search(criteria);//????????????????????????????????????

            //????????????
            List<AuxDaglImportVo> auxDaglImportVos = new ArrayList<>();

            for(AuxDagl auxDagl:auxDaglList){
                //????????????
                AuxDaglImportVo auxDaglImportVo = new AuxDaglImportVo();
                auxDaglImportVo.setXm(auxDagl.getXm());//??????
                auxDaglImportVo.setXb(getDictValue(auxDagl.getXb(),"SEX","",dictMap));//??????
                auxDaglImportVo.setIdcard(auxDagl.getIdcard());//????????????
                auxDaglImportVo.setLxfs(auxDagl.getLxfs());//????????????
                auxDaglImportVo.setMz(getDictValue(auxDagl.getMz(),"MZ","",dictMap));//??????
                auxDaglImportVo.setJz(getDictValue(auxDagl.getJz(),"DRIVER_CARD","",dictMap));//??????
                if("1".equals(auxDaglImportVo.getFby())){
                    auxDaglImportVo.setFby("???");//?????????
                }else if ("0".equals(auxDaglImportVo.getFby())){
                    auxDaglImportVo.setFby("???");//?????????
                }
                auxDaglImportVo.setHyzk(getDictValue(auxDagl.getHyzk(),"HYZK","",dictMap));//????????????
                auxDaglImportVo.setZzmm(getDictValue(auxDagl.getZzmm(),"AM_ZZMM","",dictMap));//????????????
                auxDaglImportVo.setXl(getDictValue(auxDagl.getXl(),"AM_XL","",dictMap));//??????
                auxDaglImportVo.setHjdlx(getDictValue(auxDagl.getHjdlx(),"AM_HJDLX","",dictMap));//???????????????
                auxDaglImportVo.setState(getDictValue(auxDagl.getState(),"AM_STATE","",dictMap));//??????????????????
                auxDaglImportVo.setFjType(getDictValue(auxDagl.getFjType(),"AUX_FJLB","",dictMap));//????????????
                auxDaglImportVos.add(auxDaglImportVo);
            }
            ExcelUtil<AuxDaglImportVo> util = new ExcelUtil<AuxDaglImportVo>(AuxDaglImportVo.class);
            AjaxResult ajaxResult= util.exportExcel(auxDaglImportVos, "??????????????????");
            return  ajaxResult;
        }*//*else if("1".equals(exportType)){//????????????
            criteria.setAppCode(SystemConfig.getConfigData("RELATION_APP_CODE"));
            List<RelationImportVo> list = relationService.searchExport(criteria);
            for(RelationImportVo relationImportVo:list){
                relationImportVo.setKinshipTerm(getDictValue(relationImportVo.getKinshipTerm(),"APPELLATION","",dictMap));//??????
                relationImportVo.setNowStatus(getDictValue(relationImportVo.getNowStatus(),"AM_REALTION_NOW_STATUS","",dictMap));//??????
                relationImportVo.setHealthState(getDictValue(relationImportVo.getHealthState(),"AM_HEALTH_STATE","",dictMap));//????????????
                relationImportVo.setEduLevel(getDictValue(relationImportVo.getEduLevel(),"AM_XL","",dictMap));//??????
                relationImportVo.setKjysgbFlag(getDictValue(relationImportVo.getKjysgbFlag(),"KJYSGB_FLAG","",dictMap));//??????????????????

                if("1".equals(relationImportVo.getCgjFlag())){//?????????
                    relationImportVo.setCgjFlag("???");
                }else if ("2".equals(relationImportVo.getCgjFlag())){
                    relationImportVo.setCgjFlag("???");
                }
                if("1".equals(relationImportVo.getFzFlag())){//??????
                    relationImportVo.setFzFlag("???");
                }else if ("2".equals(relationImportVo.getFzFlag())){
                    relationImportVo.setFzFlag("???");
                }
                if("1".equals(relationImportVo.getSysjFlag())){//????????????
                    relationImportVo.setSysjFlag("???");
                }else if ("2".equals(relationImportVo.getSysjFlag())){
                    relationImportVo.setSysjFlag("???");
                }
                if("1".equals(relationImportVo.getRhflFlag())){//????????????
                    relationImportVo.setRhflFlag("???");
                }else if ("2".equals(relationImportVo.getRhflFlag())){
                    relationImportVo.setRhflFlag("???");
                }
                relationImportVo.setBjgzFlag(getDictValue(relationImportVo.getBjgzFlag(),"BJGZ_FLAG","",dictMap));//????????????????????????
                relationImportVo.setGzddssfw(getDictValue(relationImportVo.getGzddssfw(),"DDSSFW","",dictMap));//????????????????????????
                relationImportVo.setGzdwxz(getDictValue(relationImportVo.getGzdwxz(),"GZDWXZ","",dictMap));//??????????????????
                relationImportVo.setJzddssfw(getDictValue(relationImportVo.getJzddssfw(),"DDSSFW","",dictMap));//????????????????????????

            }
            ExcelUtil<RelationImportVo> util = new ExcelUtil<RelationImportVo>(RelationImportVo.class);
            AjaxResult ajaxResult= util.exportExcel(list, "????????????????????????");
            return  ajaxResult;
        }else if("2".equals(exportType)){//??????????????????
            criteria.setAppCode(SystemConfig.getConfigData("JOB_RESUME_APP_CODE"));
            List<JobResumeImportVo> list = jobResumeService.searchExport(criteria);
            for(JobResumeImportVo jobResumeImportVo:list){

            }
            ExcelUtil<JobResumeImportVo> util = new ExcelUtil<JobResumeImportVo>(JobResumeImportVo.class);
            AjaxResult ajaxResult= util.exportExcel(list, "????????????????????????");
            return  ajaxResult;
        }else if("3".equals(exportType)){//????????????
            criteria.setAppCode(SystemConfig.getConfigData("EDU_RESUME_APP_CODE"));
            List<EduResumeImportVo> list = eduResumeService.searchExport(criteria);
            for(EduResumeImportVo eduResumeImportVo:list){
                eduResumeImportVo.setEduLevel(getDictValue(eduResumeImportVo.getEduLevel(),"AM_XL","",dictMap));//??????
                eduResumeImportVo.setDegree(getDictValue(eduResumeImportVo.getDegree(),"AM_XW","",dictMap));//??????
            }
            ExcelUtil<EduResumeImportVo> util = new ExcelUtil<EduResumeImportVo>(EduResumeImportVo.class);
            AjaxResult ajaxResult= util.exportExcel(list, "????????????????????????");
            return  ajaxResult;
        }else if("4".equals(exportType)){//????????????
            List<ContractImportVo> list = contractService.searchExport(criteria);
            for(ContractImportVo contractImportVo:list){
                contractImportVo.setCategory(getDictValue(contractImportVo.getCategory(),"CONTRACT_CATEGORY","",dictMap));//????????????
                contractImportVo.setState(getDictValue(contractImportVo.getState(),"CONTRACT_STATE","",dictMap));//????????????
            }
            ExcelUtil<ContractImportVo> util = new ExcelUtil<ContractImportVo>(ContractImportVo.class);
            AjaxResult ajaxResult= util.exportExcel(list, "??????????????????");
            return  ajaxResult;
        }*//*


        return null;
    }*/



}
