/**
 *
 */
package com.greathack.homlin.controller.auxiliary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.auxiliary.AuxPay;
import com.greathack.homlin.pojo.auxiliary.AuxPaySearchCriteria;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxPayService;
import com.greathack.utils.tools.TypeOption;
import com.greathack.utils.validate.JsonParamValidate;
import com.greathack.utils.validate.ParamterValidate;
import com.greathack.utils.validate.exception.ValidateException;
import com.greathack.utils.validate.rule.IsInteger;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author renTX
 *
 */
@Controller
@RequestMapping(value = "/auxPay")
public class AuxPayController {

    private static Logger logger = LoggerFactory.getLogger(AuxPayController.class);
    private BizException exception = new BizException(200001, "INVALID_PARAMS");

    @Autowired
    private IAuxPayService auxPayService;


    @ResponseBody
    @RequestMapping(value = "/add")
    public Object add(@RequestParam(value = "loginCode") String loginCode,
                      @RequestParam(value = "charset", required = false) String charset,
                      @RequestBody String strJson) throws ValidateException {

        Map<String, Object> result = new HashMap<String, Object>();
        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        if (admin == null) {
            logger.info("无效的loginCode");
            throw new BizException(200003, "INVALID_LOGINCODE");
        }

        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        JSONArray jsonArray = JSONArray.parseArray(strJson);
        if (jsonArray.size() > 0) {
            Boolean flag = false;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                AuxPay auxPay = JSON.toJavaObject(json, AuxPay.class);
                AuxPay params = new AuxPay();
                params.setGzybId(auxPay.getGzybId());
                params.setJbxxId(auxPay.getJbxxId());
                if (auxPayService.findByExample(params).size() > 0) {
                    /*sb.append(auxPay.getXm()+"("+auxPay.getIdcard()+")<br>");*/
                    /*sb.append("("+auxPay.getIdcard()+")<br>");*/
                    flag=true;
                    continue;
                }
                auxPay.setByrybd(1);
                auxPay.setCreateBy(admin.getUserId());
                try {
                    auxPayService.add(auxPay);
                } catch (ServiceException e) {
                    throw new BizException(e.getErrCode(), e.getErrMsg());
                }
            }
            if(flag) {
                result.put("errCode", ErrCode.AMPAYROLL_EXISTED);
                result.put("errMsg", "以下辅警工资记录已存在:<br>"+sb);
                return result;
            }

        }
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

   /* @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete(
            @RequestParam(value = "loginCode") String loginCode,
            @RequestParam(value = "id") String id) throws ValidateException {
        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        if (admin == null) {
            logger.info("无效的loginCode");
            throw new BizException(200003, "INVALID_LOGINCODE");
        }
        if (StringUtils.isBlank(id)) {
            logger.info("辅警工资记录id必填");
            throw new BizException(ErrCode.AMPAYROLLID_IS_REQUIRED, "AMPAYROLLID_IS_REQUIRED");
        }
        amPayrollService.delete(Long.valueOf(id));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Object update(
            @RequestParam(value = "loginCode") String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson) throws ValidateException {

        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        if (admin == null) {
            logger.info("无效的loginCode");
            throw new BizException(200003, "INVALID_LOGINCODE");
        }

        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        Map<String, Object> result = new HashMap<String, Object>();
        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson, exception)
                .addLongRule("id", true, 0L, null, exception)
                .addStringRule("bfsyzkxlqjgz,bfgz,qtkk,sfgz,wwkhgz", 10, exception)
                .addStringRule("remark", 255, exception)
                .validate();

        AmPayroll amPayroll = JSON.toJavaObject(json, AmPayroll.class);
        AmPayroll amPayroll1 = amPayrollService.get(amPayroll.getId());
        if (amPayroll1 == null) {
            logger.info("辅警工资记录不存在");
            throw new BizException(ErrCode.AMPAYROLL_NOT_EXIST, "AMPAYROLL_NOT_EXIST");
        }
        BeanUtils.copyProperties(amPayroll, amPayroll1, new String[]{"id", "createBy", "createTime", "gzybId",
                "jbxxId", "orgId", "idcard", "orgName", "mobile", "khhmc", "gzkh", "glgdj", "gwgz", "xj", "xjgz",
                "sbjf", "zfgjjjf"});
        amPayrollService.update(amPayroll1);

        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }*/

    @ResponseBody
    @RequestMapping(value = "/get")
    public Object get(
            @RequestParam(value = "loginCode") String loginCode,
            @RequestParam(value = "id", required = false) String id)
            throws ValidateException {
        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        if (admin == null) {
            logger.info("无效的loginCode");
            throw new BizException(200003, "INVALID_LOGINCODE");
        }

        if (StringUtils.isBlank(id)) {
            logger.info("辅警工资记录id必填");
            throw new BizException(ErrCode.AMPAYROLLID_IS_REQUIRED, "AMPAYROLLID_IS_REQUIRED");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        AuxPay auxPay = auxPayService.get(Long.valueOf(id));
        if (auxPay == null) {
            logger.info("辅警工资记录不存在");
            throw new BizException(ErrCode.AMPAYROLL_NOT_EXIST, "AMPAYROLL_NOT_EXIST");
        }

        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("auxPay", auxPay);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/search")
    public Object search(
            @RequestParam(value = "loginCode") String loginCode,
            @RequestBody String strJson,
            @RequestParam(value = "charset", required = false) String charset) throws ValidateException {

        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        if (admin == null) {
            logger.info("无效的loginCode");
            throw new BizException(200003, "INVALID_LOGINCODE");
        }

        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson, exception)
                .addLongRule("gzybId", true, 0L, null, exception)
                .addIntegerRule("sortField", false, 1, 2, exception)
                .addIntegerRule("page", false, 1, 1000, exception)
                .addIntegerRule("pageSize", false, 1, 1000000, exception)
                .validate();

        if (json.containsKey("kwFields") && json.containsKey("keyword")) {
            new ParamterValidate().addRule(new IsInteger(exception)).validate(json.getString("kwFields"));
            if (json.getIntValue("kwFields") < 1 || json.getIntValue("kwFields") > 8) {
                logger.info("kwFields必须在区间1--32");
                throw new BizException(10003, "INVALID_PARAMS");
            }
        }
        AuxPaySearchCriteria criteria = JSON.parseObject(strJson, AuxPaySearchCriteria.class);
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 20);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);

        List<Long> orgIdList = new ArrayList<>();
        if (json.containsKey("orgIds") && StringUtils.isNotBlank(json.getString("orgIds"))) {
            String[] arr = json.getString("orgIds").split(",");
            for (String str : arr) {
                orgIdList.add(Long.valueOf(str));
            }
            criteria.setOrgIdList(orgIdList);
        }

        List<AuxPay> auxPayList = auxPayService.search(criteria);
        Long recordCount = auxPayService.getSearchResultCount(criteria);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("recordCount", recordCount);
        result.put("auxPayList", auxPayList);
        return result;
    }

    /* *//**
     * 一键更新维稳考核工资(字段变更导致计算逻辑错误)
     * @param loginCode
     * @return
     * @throws ValidateException
     *//*
    @ResponseBody
    @RequestMapping(value = "/updateWwkhgz")
    public Object updateWwkhgz(
            @RequestParam(value = "loginCode") String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestBody String strJson) throws ValidateException {

        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        if (admin == null) {
            logger.info("无效的loginCode");
            throw new BizException(200003, "INVALID_LOGINCODE");
        }

        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson, exception)
                .addLongRule("gzybId", true, 0L, null, exception)
                .validate();

        Long gzybId = json.getLongValue("gzybId");
        amPayrollService.updateWwkhgz(gzybId);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/searchGzb")
    public Object searchGzb(
            @RequestParam(value = "loginCode") String loginCode,
            @RequestBody String strJson,
            @RequestParam(value = "charset", required = false) String charset) throws ValidateException {

        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        if (admin == null) {
            logger.info("无效的loginCode");
            throw new BizException(200003, "INVALID_LOGINCODE");
        }

        //2、对传进来的参数进行转码
        try {
            //对strJson进行转码
            strJson = URLDecoder.decode(strJson, charset);
        } catch (UnsupportedEncodingException e1) {
            logger.info("无效的charset");
            throw new BizException(10013, "INVALID_CHARSET");
        }

        //3、对json里面的参数进行校验
        JSONObject json = new JsonParamValidate(strJson, exception)
                .addLongRule("gzybId", true, 0L, null, exception)
                .validate();

        if (json.containsKey("kwFields") && json.containsKey("keyword")) {
            new ParamterValidate().addRule(new IsInteger(exception)).validate(json.getString("kwFields"));
            if (json.getIntValue("kwFields") < 1 || json.getIntValue("kwFields") > 8) {
                logger.info("kwFields必须在区间1--32");
                throw new BizException(10003, "INVALID_PARAMS");
            }
        }
        AmPayrollSearchCriteria criteria = JSON.parseObject(strJson, AmPayrollSearchCriteria.class);
        int page = TypeOption.nullToValue(json.getString("page"), 1);
        int pageSize = TypeOption.nullToValue(json.getString("pageSize"), 1000000);
        criteria.setStartLine((page - 1) * pageSize);
        criteria.setPageSize(pageSize);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("amPayrollVoList", amPayrollService.searchGzb(criteria));
        return result;
    }

    *//**
     * 工资各项合计
     * @param loginCode
     * @param gzybIds
     * @return
     * @throws ValidateException
     *//*
    @ResponseBody
    @RequestMapping(value = "/getTotalByGzybId")
    public Object getTotalByGzybId(
            @RequestParam(value = "loginCode") String loginCode,
            @RequestParam(value = "gzybIds") String gzybIds)
            throws ValidateException {
        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        if (admin == null) {
            logger.info("无效的loginCode");
            throw new BizException(200003, "INVALID_LOGINCODE");
        }

        if (StringUtils.isBlank(gzybIds)) {
            logger.info("辅警工资月报id必填");
            throw new BizException(ErrCode.AMGZYBID_IS_REQUIRED, "AMGZYBID_IS_REQUIRED");
        }
        List <Long> gzybIdList = new ArrayList<>();
        for (String id : gzybIds.split(",")) {
            gzybIdList.add(Long.valueOf(id));
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("total", amPayrollService.getTotalByGzybIdList(gzybIdList));
        return result;
    }

    *//**
     * 根据工资月报id查询维稳考核工资为空的数据
     * @param loginCode
     * @return
     * @throws ValidateException
     *//*
    @ResponseBody
    @RequestMapping(value = "/findWwkhgzIsNullByGzybId")
    public Object findWwkhgzIsNullByGzybId(
            @RequestParam(value = "loginCode") String loginCode,
            @RequestParam(value = "gzybId") Long gzybId) throws ValidateException {

        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        if (admin == null) {
            logger.info("无效的loginCode");
            throw new BizException(200003, "INVALID_LOGINCODE");
        }
        if (gzybId == null) {
            logger.info("辅警工资月报id必填");
            throw new BizException(ErrCode.AMGZYBID_IS_REQUIRED, "AMGZYBID_IS_REQUIRED");
        }
        Boolean flag = false;
        StringBuilder sb = new StringBuilder();
        for (AmPayroll amPayroll : amPayrollService.findWwkhgzIsNullByGzybId(gzybId)) {
            sb.append(amPayroll.getXm()+"("+amPayroll.getIdcard()+")<br>");
            flag=true;
            continue;
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if(flag) {
            result.put("errCode", ErrCode.AMPAYROLL_EXISTED);
            result.put("errMsg", "以下人员维稳考核工资为空:<br>"+sb);
            return result;
        }
        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        return result;
    }

    *//**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     *//*
    @RequestMapping(value = "/download")
    public void fileDownload(
            @RequestParam(value = "loginCode") String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            AjaxResult ajaxResult;
            ExcelUtil<AmPayrollImportVo> util = new ExcelUtil<>(AmPayrollImportVo.class);
            ajaxResult = util.importTemplateExcel("工资记录");

            fileName = ajaxResult.get("msg").toString();
            delete = true;

            if (!FileUtils.isValidFilename(fileName)) {
                throw new Exception(com.greathack.homlin.utils.poi.StringUtils.format(" 文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = Global.getDownloadPath() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + CommonUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //log.error("下载文件失败", e);
        }
    }

    *//**
     * 导入
     *//*
    @RequestMapping("/import")
    @ResponseBody
    public Object importData(
            @RequestParam(value = "loginCode", required = true) String loginCode,
            @RequestParam(value = "charset", required = false) String charset,
            @RequestParam(value = "gzybId", required = true) Long gzybId,
            @RequestParam(value = "isUpdate", required = false) String isUpdate,
            MultipartFile file) throws Exception {
        Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
        Map<String, Object> params = new HashMap<>();
        params.put("createBy", admin.getUserId());
        params.put("isUpdate", isUpdate);
        params.put("gzybId", gzybId);

        Map<String, Object> result = new HashMap<>();
        if (file == null) {
            result.put("errCode", 1);
            result.put("errMsg", "请选择文件");
            return result;
        }
        if (gzybId == null) {
            result.put("errCode", 1);
            result.put("errMsg", "工资月报id不能为空");
            return result;
        }
        ExcelUtil<AmPayrollImportVo> util = new ExcelUtil<>(AmPayrollImportVo.class);
        List<AmPayrollImportVo> amPayrollImportVoList = util.importExcel(file.getInputStream());
        String msg = amPayrollService.importData(amPayrollImportVoList, params);

        result.put("errCode", 0);
        result.put("errMsg", "SUCCESS");
        result.put("msg", msg);
        return result;
    }*/

}
