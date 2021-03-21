package com.greathack.homlin.serviceinterface.auxiliary;

import com.greathack.homlin.pojo.auxiliary.AuxDaglSearchCriteria;
import com.greathack.homlin.pojo.auxiliary.JobResume;
import com.greathack.homlin.pojo.auxiliary.JobResumeImportVo;
import com.greathack.homlin.serviceinterface.system.IBaseService;

import java.util.List;
import java.util.Map;

public interface IJobResumeService extends IBaseService<JobResume, Long> {

    List<JobResume> findByInstanceId(String instanceId);

    String importJobResume(List<JobResumeImportVo> jobResumeImportList, Map<String, Object> params);

    List<JobResumeImportVo> searchExport(AuxDaglSearchCriteria criteria);
    /*public String importJobResume(List<JobResumeImportVo> jobResumeImportList, Map<String, Object> params);

    //导出查询
    List<JobResumeImportVo> searchExport(AmJbxxSearchCriteria criteria);*/
}
