package com.greathack.homlin.daointerface.auxiliary;

import com.greathack.homlin.daointerface.system.IBaseDAO;
import com.greathack.homlin.pojo.auxiliary.AuxDaglSearchCriteria;
import com.greathack.homlin.pojo.auxiliary.EduResume;
import com.greathack.homlin.pojo.auxiliary.EduResumeImportVo;

import java.util.List;

public interface IEduResumeDAO extends IBaseDAO<EduResume, Long> {

    List<EduResumeImportVo> searchExport(AuxDaglSearchCriteria criteria);
}
