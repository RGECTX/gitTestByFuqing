package com.greathack.homlin.pojo.auxiliary;

import com.greathack.homlin.pojo.system.SearchCriteria;

import java.io.Serializable;

public class AuxHmdSearchCriteria extends SearchCriteria implements Serializable {

    private static final long serialVersionUID = -5931608091592049496L;
    /**
     * 姓名
     */
    private String xm;

    /**
     * 创建人
     */
    private Long createBy;

    public AuxHmdSearchCriteria() {
        super();
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    protected String getKwFieldName(int field) {
        switch (field) {
            default:
                return "";
        }
    }
}
