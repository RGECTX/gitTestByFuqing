package com.greathack.homlin.pojo.auxiliary;

import com.greathack.homlin.pojo.system.SearchCriteria;
import com.greathack.utils.tools.Utils;

import java.io.Serializable;
import java.util.List;

public class AuxAttendancdSearchCriteria extends SearchCriteria implements Serializable {

    private static final long serialVersionUID = -2889666424775813758L;
    /**
     * 工作单位ID
     */
    private String orgId;

    /**
     * 工作单位名称
     */
    private String orgName;

    /**
     *辅警类别
     */
    private String state;

    /**
     * 年度
     */
    private Integer nd;

    /**
     * 月度
     */
    private Integer yd;

    private List<Long> orgIdList;
    private List<Long> ryStateList;


    public List<Integer> getStateList() {
        if(state!=null){
            return Utils.recountToArrayList(Integer.parseInt(state));
        }
        return null;
    }

    public List<Long> getOrgIdList() {
        return orgIdList;
    }

    public void setOrgIdList(List<Long> orgIdList) {
        this.orgIdList = orgIdList;
    }

    public List<Long> getRyStateList() {
        return ryStateList;
    }

    public void setRyStateList(List<Long> ryStateList) {
        this.ryStateList = ryStateList;
    }

    public AuxAttendancdSearchCriteria() {
        super();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getNd() {
        return nd;
    }

    public void setNd(Integer nd) {
        this.nd = nd;
    }

    public Integer getYd() {
        return yd;
    }

    public void setYd(Integer yd) {
        this.yd = yd;
    }

    protected String getKwFieldName(int field) {
        switch (field) {
            default:
                return "";
        }
    }
}
