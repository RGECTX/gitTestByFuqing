package com.greathack.homlin.serviceinterface.auxiliary;

import com.greathack.homlin.pojo.auxiliary.AuxDagl;
import com.greathack.homlin.pojo.auxiliary.AuxDaglImportVo;
import com.greathack.homlin.pojo.auxiliary.AuxDaglSearchCriteria;
import com.greathack.homlin.serviceinterface.system.IBaseService;

import java.util.List;
import java.util.Map;

public interface IAuxDaglService extends IBaseService<AuxDagl, Long> {
    List<AuxDagl> search(AuxDaglSearchCriteria criteria);

    long getSearchResultCount(AuxDaglSearchCriteria criteria);

    List<AuxDagl> findIdcard(String idcard);

    void updateFjlb(AuxDagl auxDagl);

    void updateDwjs(AuxDagl auxDagl);

    void updateState(AuxDagl auxDagl);

    String importJbxx(List<AuxDaglImportVo> amJbxxImportList, Map<String, Object> params);

    List<AuxDaglImportVo> searchExport(AuxDaglSearchCriteria criteria);

    Integer findByOrgId(Long orgId,String fjType);

    void addZl(AuxDagl auxDagl);

    void updateYfgz(AuxDagl auxDagl);


    List<AuxDagl> rytj2(AuxDaglSearchCriteria criteria);

    List<AuxDagl> rytj(AuxDaglSearchCriteria criteria);

    long getSearchResultCount2(AuxDaglSearchCriteria criteria);

    List<AuxDagl> rytj4(AuxDaglSearchCriteria criteria);
    List<AuxDagl> rytj3(AuxDaglSearchCriteria criteria);

    long getSearchResultCount3(AuxDaglSearchCriteria criteria);

    long getSearchResultCount4(AuxDaglSearchCriteria criteria);

    List<AuxDagl> rytj7(AuxDaglSearchCriteria criteria);//管理员 查询在职单位人总数

    List<AuxDagl> rytj5(AuxDaglSearchCriteria criteria);//非管理员 查询在职单位人总数


    /*List<AuxDagl> rytj2();

    List<AuxDagl> rytj();*/
}
