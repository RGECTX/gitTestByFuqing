package com.greathack.homlin.daointerface.auxiliary;

import com.greathack.homlin.daointerface.system.IBaseDAO;
import com.greathack.homlin.pojo.auxiliary.AuxPay;
import com.greathack.homlin.pojo.auxiliary.AuxPaySearchCriteria;

import java.util.List;

public interface IAuxPayDAO extends IBaseDAO<AuxPay, Long> {

    /**
     * 根据关键字查找信息
     *
     * @param criteria
     */
    List<AuxPay> search(AuxPaySearchCriteria criteria);

    /**
     * 获取搜索结果的总记录数
     *
     * @param criteria 搜索条件
     * @return 总记录数
     */
    long getSearchResultCount(AuxPaySearchCriteria criteria);

    /**
     * 查询工资表
     * @param criteria
     * @return
     *//*
    List<AmPayroll> searchGzb(AmPayrollSearchCriteria criteria);

    void deleteByGzybId(Long gzybId);

    *//**
     * 工资各项合计
     * @param gzybIdList
     * @return
     *//*
    AmGzbb getTotalByGzybIdList(List<Long> gzybIdList);

    *//**
     * 根据工资月报id查询维稳考核工资为空的数据
     * @param gzybId
     * @return
     *//*
    List<AmPayroll> findWwkhgzIsNullByGzybId(Long gzybId);*/
}
