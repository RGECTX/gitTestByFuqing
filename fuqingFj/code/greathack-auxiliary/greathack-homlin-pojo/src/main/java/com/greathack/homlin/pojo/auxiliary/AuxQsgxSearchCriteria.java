package com.greathack.homlin.pojo.auxiliary;

import com.greathack.homlin.pojo.system.SearchCriteria;

import java.io.Serializable;

/**
 * @Author renTX
 *  * @Date 2020-10-12
 */
public class AuxQsgxSearchCriteria extends SearchCriteria implements Serializable {
    /**
     * 姓名字段
     */
    public static final int KW_FIELDS_NAME = 1;

    /**
     * 备注字段
     */
    public static final int KW_FIELDS_REMARK = 2;

    /**
     * 姓名
     */
    private String qsname;

    /**
     * 所属主体ID
     */
    private String instanceId;

    /**
     * 关联人身份证号码
     */
    private String idcard;

    /**
     * 亲属身份证号码
     */
    private String ownIdcard;

    public String getQsname() {
        return qsname;
    }

    public void setQsname(String qsname) {
        this.qsname = qsname;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getOwnIdcard() {
        return ownIdcard;
    }

    public void setOwnIdcard(String ownIdcard) {
        this.ownIdcard = ownIdcard;
    }

    @Override
    protected String getKwFieldName(int field) {
        switch (field) {

            case AuxQsgxSearchCriteria.KW_FIELDS_NAME:
                return "b.xm";

            case AuxQsgxSearchCriteria.KW_FIELDS_REMARK:
                return "b.remark";

            default:
                return "";
        }
    }
}
