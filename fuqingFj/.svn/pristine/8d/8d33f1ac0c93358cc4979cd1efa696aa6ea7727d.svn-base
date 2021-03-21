package com.greathack.homlin.serviceinterface.auxiliary;

import com.greathack.homlin.pojo.auxiliary.AuxDaglSearchCriteria;
import com.greathack.homlin.pojo.auxiliary.EduResume;
import com.greathack.homlin.pojo.auxiliary.EduResumeImportVo;
import com.greathack.homlin.serviceinterface.system.IBaseService;

import java.util.List;
import java.util.Map;

public interface IEduResumeService extends IBaseService<EduResume, Long> {
    String importEduResume(List<EduResumeImportVo> eduResumeImportList, Map<String, Object> params);

    List<EduResumeImportVo> searchExport(AuxDaglSearchCriteria criteria);
    /*public String importEduResume(List<EduResumeImportVo> eduResumeImportList, Map<String, Object> params);

    //导出查询
    List<EduResumeImportVo> searchExport(AmJbxxSearchCriteria criteria);*/
}
