package com.greathack.homlin.daointerface.auxiliary;

import com.greathack.homlin.daointerface.system.IBaseDAO;
import com.greathack.homlin.pojo.auxiliary.AuxDaglSearchCriteria;
import com.greathack.homlin.pojo.auxiliary.JobResume;
import com.greathack.homlin.pojo.auxiliary.JobResumeImportVo;

import java.util.List;

public interface IJobResumeDAO extends IBaseDAO<JobResume, Long> {

    List<JobResume> findByInstanceId(String instanceId);

    List<JobResumeImportVo> searchExport(AuxDaglSearchCriteria criteria);

}
