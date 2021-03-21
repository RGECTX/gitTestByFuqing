package com.greathack.homlin.serviceinterface.auxiliary;

import com.greathack.homlin.pojo.auxiliary.AuxDaglSearchCriteria;
import com.greathack.homlin.pojo.auxiliary.AuxQsgx;
import com.greathack.homlin.pojo.auxiliary.AuxQsgxImportVo;
import com.greathack.homlin.serviceinterface.system.IBaseService;

import java.util.List;
import java.util.Map;

public interface IAuxQsgxService extends IBaseService<AuxQsgx, Long> {
    String importAuxQsgx(List<AuxQsgxImportVo> auxQsgxImportVoList, Map<String, Object> params);

    List<AuxQsgxImportVo> searchExport(AuxDaglSearchCriteria criteria);
    /*public String importEduResume(List<EduResumeImportVo> eduResumeImportList, Map<String, Object> params);

    //导出查询
    List<EduResumeImportVo> searchExport(AmJbxxSearchCriteria criteria);*/
}
