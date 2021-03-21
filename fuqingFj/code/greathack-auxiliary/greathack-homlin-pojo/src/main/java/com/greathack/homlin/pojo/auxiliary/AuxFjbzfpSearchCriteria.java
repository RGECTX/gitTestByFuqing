package com.greathack.homlin.pojo.auxiliary;

import com.greathack.homlin.pojo.system.SearchCriteria;

import java.io.Serializable;

public class AuxFjbzfpSearchCriteria extends SearchCriteria implements Serializable {
    private static final long serialVersionUID = 5821177528698404876L;
    //工作单位ID
    private Long orgId;
    //工作单位名称
    private String orgName;
    //辅警类别
    private String fjType;
    //状态
    private String state;
    //现有人数
    private String existingPeople;
    //编制人数
    private String formationPeople;
    //现有人数2
    private String existingPeople1;
    //编制人数2
    private String formationPeople1;
    //现有人数2
    private String existingPeople2;
    //编制人数2
    private String formationPeople2;
    //现有人数4
    private String existingPeople4;
    //编制人数4
    private String formationPeople4;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getFjType() {
        return fjType;
    }

    public void setFjType(String fjType) {
        this.fjType = fjType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getExistingPeople() {
        return existingPeople;
    }

    public void setExistingPeople(String existingPeople) {
        this.existingPeople = existingPeople;
    }

    public String getFormationPeople() {
        return formationPeople;
    }

    public void setFormationPeople(String formationPeople) {
        this.formationPeople = formationPeople;
    }

    public String getExistingPeople1() {
        return existingPeople1;
    }

    public void setExistingPeople1(String existingPeople1) {
        this.existingPeople1 = existingPeople1;
    }

    public String getFormationPeople1() {
        return formationPeople1;
    }

    public void setFormationPeople1(String formationPeople1) {
        this.formationPeople1 = formationPeople1;
    }

    public String getExistingPeople2() {
        return existingPeople2;
    }

    public void setExistingPeople2(String existingPeople2) {
        this.existingPeople2 = existingPeople2;
    }

    public String getFormationPeople2() {
        return formationPeople2;
    }

    public void setFormationPeople2(String formationPeople2) {
        this.formationPeople2 = formationPeople2;
    }

    public String getExistingPeople4() {
        return existingPeople4;
    }

    public void setExistingPeople4(String existingPeople4) {
        this.existingPeople4 = existingPeople4;
    }

    public String getFormationPeople4() {
        return formationPeople4;
    }

    public void setFormationPeople4(String formationPeople4) {
        this.formationPeople4 = formationPeople4;
    }

    @Override
    protected String getKwFieldName(int field) {
        switch (field){
            default:
                return "";
        }
    }

    @Override
    public String toString() {
        return "AuxFjbzfpSearchCriteria{" +
                "orgId=" + orgId +
                ", orgName='" + orgName + '\'' +
                ", fjType='" + fjType + '\'' +
                ", state='" + state + '\'' +
                ", existingPeople='" + existingPeople + '\'' +
                ", formationPeople=" + formationPeople +
                ", existingPeople1='" + existingPeople1 + '\'' +
                ", formationPeople1='" + formationPeople1 + '\'' +
                ", existingPeople2='" + existingPeople2 + '\'' +
                ", formationPeople2='" + formationPeople2 + '\'' +
                ", existingPeople4='" + existingPeople4 + '\'' +
                ", formationPeople4='" + formationPeople4 + '\'' +
                '}';
    }
}
