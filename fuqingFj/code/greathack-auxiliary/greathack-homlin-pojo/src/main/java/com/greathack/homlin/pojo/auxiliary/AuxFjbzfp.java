
package com.greathack.homlin.pojo.auxiliary;

import java.io.Serializable;

public class AuxFjbzfp implements Serializable {
    //辅警编制分配情况ID
    private Long fjbzfpId;
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

    private Integer countformationPeople;

    public Integer getCountformationPeople() {
        return countformationPeople;
    }

    public void setCountformationPeople(Integer countformationPeople) {
        this.countformationPeople = countformationPeople;
    }

    public Long getFjbzfpId() {
        return fjbzfpId;
    }

    public void setFjbzfpId(Long fjbzfpId) {
        this.fjbzfpId = fjbzfpId;
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
