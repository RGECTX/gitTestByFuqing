package com.greathack.homlin.service.auxiliary;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxDaglDAO;
import com.greathack.homlin.daointerface.auxiliary.IJobResumeDAO;
import com.greathack.homlin.exception.BusinessException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.auxiliary.AuxDagl;
import com.greathack.homlin.pojo.auxiliary.AuxDaglSearchCriteria;
import com.greathack.homlin.pojo.auxiliary.JobResume;
import com.greathack.homlin.pojo.auxiliary.JobResumeImportVo;
import com.greathack.homlin.pojo.dict.Dict;
import com.greathack.homlin.pojo.dict.DictData;
import com.greathack.homlin.serviceinterface.auxiliary.IJobResumeService;
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

public class JobResumeService implements IJobResumeService {

    @Autowired
    private IDictService dictService;
    @Autowired
    private IDictDataService dictDataService;

    private static IAuxDaglDAO auxDaglDAO = (IAuxDaglDAO) DAOFactory.createDAO("IAuxDaglDAO");

    private static IJobResumeDAO jobResumeDAO = (IJobResumeDAO) DAOFactory.createDAO("IJobResumeDAO");


    @Override
    public JobResume add(JobResume jobResume) throws ServiceException {
        if (Validation.isEmpty(jobResume.getAppCode())) {
            throw new ServiceException(ErrCode.APP_CODE_IS_REQUIRE, "APP_CODE_IS_REQUIRE");
        }
        if (jobResume.getInstanceId() == null) {
            throw new ServiceException(ErrCode.INSTANCE_ID_IS_REQUIRE, "INSTANCE_ID_IS_REQUIRE");
        }
        try {
            jobResume.setCreateTime(Common.getCurrentTime());
            jobResume.setId(IdCreator.createId("JobResume"));
            jobResumeDAO.add(jobResume);
            return jobResume;
        } catch (ServiceException e) {
            throw new ServiceException(ErrCode.UNKNOWN_ERROR, "UNKNOWN_ERROR");
        }
    }

    @Override
    public void delete(Long id) {
        jobResumeDAO.delete(id);
    }

    @Override
    public void update(JobResume jobResume) {
        jobResumeDAO.update(jobResume);
    }

    @Override
    public JobResume get(Long id) {
        return jobResumeDAO.findById(id);
    }

    @Override
    public List<JobResume> findByExample(JobResume jobResume) {
        return jobResumeDAO.findByExample(jobResume);
    }

    @Override
    public List<JobResume> findAll() {
        return jobResumeDAO.findAll();
    }

    @Override
    public List<JobResume> findByInstanceId(String instanceId) {
        return  jobResumeDAO.findByInstanceId(instanceId);
    }

    @Override
    public String importJobResume(List<JobResumeImportVo> jobResumeImportList, Map<String, Object> params) {

        if (jobResumeImportList==null || jobResumeImportList.size() == 0)
        {
            throw new BusinessException("导入工作简历信息不能为空！");
        }

        List<Dict> dictList=dictService.getList(SystemConfig.getConfigData("DICT_APP_CODE"));
        Map<String,List<DictData>> dictMap=new HashMap<>();
        for(Dict dict:dictList){
            List<DictData> dictDataList=dictDataService.getList(dict.getDictId());
            dictMap.put(dict.getDictCode()+"",dictDataList);
        }

        Long createBy=Long.parseLong(params.get("createBy").toString());
        String isDelete=params.get("isDelete").toString();//0、直接新增  1、先删除子表信息


        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        JobResume searchJobResume=new JobResume();
        searchJobResume.setCreateTime(Common.getCurrentTime());
        for (JobResumeImportVo jobResumeImportVo : jobResumeImportList){
            try
            {
                if(StringUtils.isEmpty(jobResumeImportVo.getIdcard())){
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、身份证号 " + jobResumeImportVo.getIdcard()+ " 身份证号不能为空");
                    continue;
                }

                if(!DateUtils.isDateString(jobResumeImportVo.getStartDate())){
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、身份证号 " + jobResumeImportVo.getIdcard()+ " 开始日期格式错误");
                    continue;
                }
                if(!DateUtils.isDateString(jobResumeImportVo.getEndDate())){
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、身份证号 " + jobResumeImportVo.getIdcard()+ " 结束日期格式错误");
                    continue;
                }



                // 验证人员基本信息是否存在
                List<AuxDagl> oldAuxDaglList = auxDaglDAO.findIdcard(jobResumeImportVo.getIdcard());
                if(oldAuxDaglList.size()==0){
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、身份证号 " + jobResumeImportVo.getIdcard()+ " 尚未有该人员的基本信息");
                    continue;
                }


                JobResume jobResume =new JobResume();
                jobResume.setCreateTime(Common.getCurrentTime());
                jobResume.setCreateBy(createBy);
                jobResume.setId(IdCreator.createId("JobResume"));
                /*jobResume.setAppCode(SystemConfig.getConfigData("JOB_RESUME_APP_CODE"));*/
                jobResume.setAppCode(SystemConfig.getConfigData("AUXILIARY_APP_CODE"));
                jobResume.setInstanceId(oldAuxDaglList.get(0).getDaId()+"");
                jobResume.setIdcard(jobResumeImportVo.getIdcard());//身份证号

                jobResume.setWorkUnit(jobResumeImportVo.getWorkUnit());//工作单位
                jobResume.setPost(jobResumeImportVo.getPost());//职务
                if(jobResumeImportVo.getStartDate()!=null){
                    String startDate=jobResumeImportVo.getStartDate();
                    startDate = DateUtils.formateDateStrTo8(startDate);
                    if(startDate!=null){
                        jobResume.setStartDate(Integer.parseInt(startDate));//开始日期
                    }
                }
                if(jobResumeImportVo.getEndDate()!=null){
                    String endDate=jobResumeImportVo.getEndDate();
                    endDate = DateUtils.formateDateStrTo8(endDate);
                    if(endDate!=null){
                        jobResume.setEndDate(Integer.parseInt(endDate));//结束日期
                    }
                }
                jobResume.setRemark(jobResumeImportVo.getRemark());//备注

                jobResumeDAO.add(jobResume);

                if("1".equals(isDelete)){//先删除子表信息
                    searchJobResume.setInstanceId(oldAuxDaglList.get(0).getDaId()+"");
                    List<JobResume> resumeList=jobResumeDAO.findByExample(searchJobResume);
                    if(resumeList.size()>0){
                        for(JobResume dJobResume:resumeList){
                            jobResumeDAO.delete(dJobResume.getId());
                        }
                    }
                }


                successNum++;
                successMsg.append("<br/>" + successNum + "、 身份证号" + jobResumeImportVo.getIdcard() + " 导入成功");



            }catch (Exception e){
                e.printStackTrace();
                failureNum++;
                String msg = "<br/>" + failureNum + "、身份证号 " + jobResumeImportVo.getIdcard() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                //log.error(msg, e);
            }
        }
        if (failureNum > 0){
            failureMsg.insert(0, "很抱歉！共 "+successNum+" 条数据导入成功！共 " + failureNum + " 条数据格式不正确，错误如下：");
            return failureMsg.toString();
            //throw new BusinessException(failureMsg.toString());
        }else{
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public List<JobResumeImportVo> searchExport(AuxDaglSearchCriteria criteria) {
        List<JobResumeImportVo> jobResumeList = new ArrayList<JobResumeImportVo>();
        if (criteria == null) {
            return jobResumeList;
        }
        return jobResumeDAO.searchExport(criteria);
    }
}
