package com.greathack.homlin.serviceinterface.auxiliary;

import com.greathack.homlin.pojo.auxiliary.AuxPay;
import com.greathack.homlin.pojo.auxiliary.AuxPaySearchCriteria;
import com.greathack.homlin.serviceinterface.system.IBaseService;

import java.util.List;

public interface IAuxPayService extends IBaseService<AuxPay, Long> {

    List<AuxPay> search(AuxPaySearchCriteria criteria);

    long getSearchResultCount(AuxPaySearchCriteria criteria);

    /*List<AuxPay> searchGzb(AuxPaySearchCriteria criteria);*/

    /**
     * 一键更新维稳考核工资(字段变更导致计算逻辑错误)
     *
     * @param gzybId
     *//*
    void updateWwkhgz(Long gzybId);

    *//**
     * 工资各项合计
     * @param gzybIdList
     * @return
     *//*
    AmGzbb getTotalByGzybIdList(List<Long> gzybIdList);

    *//**
     * 根据工资月报id查询维稳考核工资为空的数据
     *
     * @param gzybId
     *//*
    List<AmPayroll> findWwkhgzIsNullByGzybId(Long gzybId);

    String importData(List<AmPayrollImportVo> amPayrollImportVoList, Map<String, Object> params);*/

}
