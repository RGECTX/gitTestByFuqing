package com.greathack.homlin.service.auxiliary;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxDaglDAO;
import com.greathack.homlin.daointerface.auxiliary.IEduResumeDAO;
import com.greathack.homlin.exception.BusinessException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.auxiliary.*;
import com.greathack.homlin.pojo.dict.Dict;
import com.greathack.homlin.pojo.dict.DictData;
import com.greathack.homlin.serviceinterface.auxiliary.IEduResumeService;
import com.greathack.homlin.serviceinterface.dict.IDictDataService;
import com.greathack.homlin.serviceinterface.dict.IDictService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.homlin.utils.DateUtils;
import com.greathack.utils.tools.Validation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EduResumeService implements IEduResumeService {

    @Autowired
    private IDictService dictService;
    @Autowired
    private IDictDataService dictDataService;
    private static IAuxDaglDAO auxDaglDAO = (IAuxDaglDAO) DAOFactory.createDAO("IAuxDaglDAO");
    private static IEduResumeDAO eduResumeDAO = (IEduResumeDAO) DAOFactory.createDAO("IEduResumeDAO");


    @Override
    public EduResume add(EduResume eduResume) throws ServiceException {

        /*return null;*/

        if (Validation.isEmpty(eduResume.getAppCode())) {
            throw new ServiceException(ErrCode.APP_CODE_IS_REQUIRE, "APP_CODE_IS_REQUIRE");
        }
        if (eduResume.getInstanceId() == null) {
            throw new ServiceException(ErrCode.INSTANCE_ID_IS_REQUIRE, "INSTANCE_ID_IS_REQUIRE");
        }

        //找出该人员的最高学历
      /*  AuxDagl auxDagl = auxDaglDAO.findById(Long.parseLong(eduResume.getInstanceId()));
        String xl = jbxx.getXl();
        if (eduResume.getEduLevel() != null && !"".equals(eduResume.getEduLevel()) && xl != null && !"".equals(xl)) {
            if (Integer.parseInt(eduResume.getEduLevel()) < Integer.parseInt(xl)) {
                throw new ServiceException(ErrCode.EDUCATIONAL_MISMATCH, "学历不可高于最高学历");
            }
        }*/

        try {
            eduResume.setId(IdCreator.createId("EduResume"));
            eduResumeDAO.add(eduResume);
            return eduResume;
        } catch (ServiceException e) {
            throw new ServiceException(ErrCode.UNKNOWN_ERROR, "UNKNOWN_ERROR");
        }
    }

    @Override
    public void delete(Long id) {
        eduResumeDAO.delete(id);
    }

    @Override
    public void update(EduResume eduResume) {
        eduResumeDAO.update(eduResume);
    }

    @Override
    public EduResume get(Long id) {
        return eduResumeDAO.findById(id);
    }

    @Override
    public List<EduResume> findByExample(EduResume eduResume) {
        return eduResumeDAO.findByExample(eduResume);
    }

    @Override
    public List<EduResume> findAll() {
        return eduResumeDAO.findAll();
    }

    @Override
    public String importEduResume(List<EduResumeImportVo> eduResumeImportList, Map<String, Object> params) {
        if (eduResumeImportList == null || eduResumeImportList.size() == 0) {
            throw new BusinessException("导入学历简历信息不能为空！");
        }

        List<Dict> dictList = dictService.getList(SystemConfig.getConfigData("DICT_APP_CODE"));
        Map<String, List<DictData>> dictMap = new HashMap<>();
        for (Dict dict : dictList) {
            List<DictData> dictDataList = dictDataService.getList(dict.getDictId());
            dictMap.put(dict.getDictCode() + "", dictDataList);
        }

        Long createBy = Long.parseLong(params.get("createBy").toString());
        String isDelete = params.get("isDelete").toString();//0、直接新增  1、先删除子表信息


        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        EduResume searchEduResume = new EduResume();
        searchEduResume.setCreateTime(Common.getCurrentTime());
        for (EduResumeImportVo eduResumeImportVo : eduResumeImportList) {
            try {
                if (StringUtils.isEmpty(eduResumeImportVo.getIdcard())) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、身份证号 " + eduResumeImportVo.getIdcard() + " 身份证号不能为空");
                    continue;
                }

                if (!DateUtils.isDateString(eduResumeImportVo.getStartDate())) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、身份证号 " + eduResumeImportVo.getIdcard() + " 开始日期格式错误");
                    continue;
                }
                if (!DateUtils.isDateString(eduResumeImportVo.getEndDate())) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、身份证号 " + eduResumeImportVo.getIdcard() + " 结束日期格式错误");
                    continue;
                }


                // 验证人员基本信息是否存在
                List<AuxDagl> oldAuxDaglList = auxDaglDAO.findIdcard(eduResumeImportVo.getIdcard());
                if (oldAuxDaglList.size()==0) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、身份证号 " + eduResumeImportVo.getIdcard() + " 尚未有该人员的基本信息");
                    continue;
                }


                EduResume eduResume = new EduResume();
                eduResume.setCreateTime(Common.getCurrentTime());
                eduResume.setCreateBy(createBy);
                eduResume.setId(IdCreator.createId("JobResume"));
                eduResume.setAppCode(SystemConfig.getConfigData("AUXILIARY_APP_CODE"));
                eduResume.setInstanceId(oldAuxDaglList.get(0).getDaId() + "");
                eduResume.setIdcard(eduResumeImportVo.getIdcard());//身份证号

                eduResume.setUniversity(eduResumeImportVo.getUniversity());//毕业院校
                eduResume.setSpecialitie(eduResumeImportVo.getSpecialitie());//专业
                if (eduResumeImportVo.getStartDate() != null) {
                    String startDate = eduResumeImportVo.getStartDate();
                    startDate = DateUtils.formateDateStrTo8(startDate);
                    if (startDate != null) {
                        eduResume.setStartDate(Integer.parseInt(startDate));//开始日期
                    }
                }
                if (eduResumeImportVo.getEndDate() != null) {
                    String endDate = eduResumeImportVo.getEndDate();
                    endDate = DateUtils.formateDateStrTo8(endDate);
                    if (endDate != null) {
                        eduResume.setEndDate(Integer.parseInt(endDate));//结束日期
                    }
                }
                eduResume.setEduLevel(getDictLabel(eduResumeImportVo.getEduLevel(), "AM_XL", "", dictMap));//学历
                eduResume.setDegree(getDictLabel(eduResumeImportVo.getDegree(), "AM_XW", "", dictMap));//学位
                eduResume.setRemark(eduResumeImportVo.getRemark());//备注

                eduResumeDAO.add(eduResume);

                if ("1".equals(isDelete)) {//先删除子表信息
                    searchEduResume.setInstanceId(oldAuxDaglList.get(0).getDaId()+"");
                    List<EduResume> resumeList = eduResumeDAO.findByExample(searchEduResume);
                    if (resumeList.size() > 0) {
                        for (EduResume dEduResume : resumeList) {
                            eduResumeDAO.delete(dEduResume.getId());
                        }
                    }
                }


                successNum++;
                successMsg.append("<br/>" + successNum + "、 身份证号" + eduResumeImportVo.getIdcard() + " 导入成功");


            } catch (Exception e) {
                e.printStackTrace();
                failureNum++;
                String msg = "<br/>" + failureNum + "、身份证号 " + eduResumeImportVo.getIdcard() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                //log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉！共 " + successNum + " 条数据导入成功！共 " + failureNum + " 条数据格式不正确，错误如下：");
            return failureMsg.toString();
            //throw new BusinessException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public List<EduResumeImportVo> searchExport(AuxDaglSearchCriteria criteria) {
        List<EduResumeImportVo> eduResumeImportVoList = new ArrayList<EduResumeImportVo>();
        if (criteria == null) {
            return eduResumeImportVoList;
        }
        return eduResumeDAO.searchExport(criteria);
    }

    /**
     * 通过类型及字典中文名获得code
     *
     * @param value        值如：在岗（正式辅警）
     * @param type         类型如AM_STATE
     * @param defaultLabel 如：1
     * @param dictMap
     * @return
     */
    public static String getDictLabel(String value, String type, String defaultLabel, Map<String, List<DictData>> dictMap) {
        List<DictData> dictDataList = dictMap.get(type);
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)) {
            for (DictData dictData : dictDataList) {
                if (value.equals(dictData.getDataName())) {
                    return dictData.getDataCode();
                }
            }
        }
        return defaultLabel;
    }
}
