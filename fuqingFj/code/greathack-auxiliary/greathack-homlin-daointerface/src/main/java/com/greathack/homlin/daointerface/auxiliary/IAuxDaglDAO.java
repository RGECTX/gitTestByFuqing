package com.greathack.homlin.daointerface.auxiliary;

import com.greathack.homlin.daointerface.system.IBaseDAO;
import com.greathack.homlin.pojo.auxiliary.AuxDagl;
import com.greathack.homlin.pojo.auxiliary.AuxDaglImportVo;
import com.greathack.homlin.pojo.auxiliary.AuxDaglSearchCriteria;

import java.util.List;

public interface IAuxDaglDAO extends IBaseDAO<AuxDagl, Long> {
    List<AuxDagl> search(AuxDaglSearchCriteria criteria);

    long getSearchResultCount(AuxDaglSearchCriteria criteria);

    List<AuxDagl> findIdcard(String idcard);

    void updateFjlb(AuxDagl auxDagl);

    void updateDwjs(AuxDagl auxDagl);

    void updateState(AuxDagl auxDagl);

    List<AuxDaglImportVo> searchExport(AuxDaglSearchCriteria criteria);

    void updatePart(AuxDagl auxDagl);

    Integer findByOrgId(Long orgId,String fjType);

    void addZl(AuxDagl auxDagl);

    void updateYfgz(AuxDagl auxDagl);

    List<AuxDagl> rytj2(AuxDaglSearchCriteria criteria);

    List<AuxDagl> rytj(AuxDaglSearchCriteria criteria);

    /*List<AuxDagl> rytj2();

    List<AuxDagl> rytj();*/

    long getSearchResultCount2(AuxDaglSearchCriteria criteria);

    List<AuxDagl> rytj4(AuxDaglSearchCriteria criteria);
    List<AuxDagl> rytj3(AuxDaglSearchCriteria criteria);

    long getSearchResultCount3(AuxDaglSearchCriteria criteria);


    long getSearchResultCount4(AuxDaglSearchCriteria criteria);

    List<AuxDagl> rytj7(AuxDaglSearchCriteria criteria);

    List<AuxDagl> rytj5(AuxDaglSearchCriteria criteria);
}
