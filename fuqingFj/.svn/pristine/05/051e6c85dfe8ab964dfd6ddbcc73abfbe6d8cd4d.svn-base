/**
 *
 */
package com.greathack.homlin.pojo.auxiliary;

import com.greathack.homlin.pojo.system.SearchCriteria;

import java.util.List;

/**
 * @author renTX
 *
 */
public class AuxPaySearchCriteria extends SearchCriteria {

    /**
     * 辅警工资月报id
     */
    public Long gzybId;

    /**
     * 辅警工资月报月份 201910
     */
    public Integer month;

    /**
     * 身份证号码
     */
    public String idcard;

    /**
     * 姓名
     */
    public String xm;

    /**
     * 开户行名称
     */
    public String khhmc;

    /**
     * 单位id
     */
    private Long orgId;

    /**
     * 单位
     */
    private List<Long> orgIdList;

    /**
     * 备用1字段
     */
    public static final int KW_FIELDS_BAK1 = 4;

    /**
     * 备用2字段
     */
    public static final int KW_FIELDS_BAK2 = 8;

    /**
     * 通过字段常量，获取字段名称
     * @param field
     * @return 字段名称
     */
    @Override
    protected String getKwFieldName(int field) {
        switch (field) {

            case AuxPaySearchCriteria.KW_FIELDS_BAK1:
                return "bak1";

            case AuxPaySearchCriteria.KW_FIELDS_BAK2:
                return "bak2";

            default:
                return "";
        }
    }

    public Long getGzybId() {
        return gzybId;
    }

    public void setGzybId(Long gzybId) {
        this.gzybId = gzybId;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getKhhmc() {
        return khhmc;
    }

    public void setKhhmc(String khhmc) {
        this.khhmc = khhmc;
    }

    public List<Long> getOrgIdList() {
        return orgIdList;
    }

    public void setOrgIdList(List<Long> orgIdList) {
        this.orgIdList = orgIdList;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
