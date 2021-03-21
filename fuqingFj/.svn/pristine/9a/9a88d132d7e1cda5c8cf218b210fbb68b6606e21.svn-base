package com.greathack.homlin.pojo.org;

import com.greathack.homlin.pojo.am.AmQuotas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Organization implements Serializable {


    /**
     * 描述   (@author: greathack)
     */

    private static final long serialVersionUID = 5636668651205526168L;

    public static final int ORG_STATE_NORMAL = 1;//正常

    public static final int ORG_STATE_STOPED = 2;//停用


    /**
     * 类别ID 唯一识别码
     */
    private Long orgId;

    /**
     * 应用编码
     */
    private String appCode;
    /**
     * 父类ID
     */
    private Long parentId;

    /**
     * 父类名称
     */
    private String parentName;

    /**
     * 直系上级ID串 格式，0，1,...
     */
    private String parents;

    private String orgType;

    private String orgRank;

    private String userId;

    private String orgCode;
    private String orgName;
    private String shortName;

    private String areaId;

    private String address;

    private String tel;

    private String remark;

    private Integer orgState;//1:正常，2：停用，4：删除

    private Integer orgLevel;

    private String createBy;

    /**
     * 排序数字
     */
    private Long sortNum;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 外键1
     */
    private String outKey1;

    /**
     * 外键2
     */
    private String outKey2;

    /**
     * 备用1
     */
    private String bak1;

    /**
     * 备用2
     */
    private String bak2;

    private Integer numb;//人员分布统计各部门人员数量使用
    private Integer rows;//人员分布统计使用
    private List<Organization> unitList;
    private AmQuotas quotas;//编制数

    private List<Object> numbList = new ArrayList<Object>();//2019年辅警辞职情况明细表
    private List<Object> countList = new ArrayList<Object>();//2019年辅警辞职情况明细表总计

    private Integer countOrg;
    private Integer countLevel1;
    private Integer countLevel2;
    private Integer countQuotasNum;
    private Integer countAllocateNum;
    private Integer countGapNum;
    private Integer countFujing;//辅警，AM_RYLB，1
    private Integer countSyxgw;//事业性岗位，AM_RYLB，2
    private Integer countXldy;//巡逻队员，AM_RYLB，4
    private Integer countSpjky;//视频监看员，AM_RYLB，8
    private Integer countJtxgy;//交通协管员，AM_RYLB，16
    private Integer countZgfj;//转岗辅警，AM_RYLB，32
    private Integer countNan;
    private Integer countNv;

    /**
     * 类别子结点列表
     */
    private List<Organization> subOrgList;

    public Integer getCountLevel1() {
        return countLevel1;
    }

    public Integer getCountOrg() {
        return countOrg;
    }

    public void setCountOrg(Integer countOrg) {
        this.countOrg = countOrg;
    }

    public void setCountLevel1(Integer countLevel1) {
        this.countLevel1 = countLevel1;
    }

    public Integer getCountLevel2() {
        return countLevel2;
    }

    public void setCountLevel2(Integer countLevel2) {
        this.countLevel2 = countLevel2;
    }

    public Integer getCountFujing() {
        return countFujing;
    }

    public void setCountFujing(Integer countFujing) {
        this.countFujing = countFujing;
    }

    public Integer getCountSyxgw() {
        return countSyxgw;
    }

    public void setCountSyxgw(Integer countSyxgw) {
        this.countSyxgw = countSyxgw;
    }

    public Integer getCountXldy() {
        return countXldy;
    }

    public void setCountXldy(Integer countXldy) {
        this.countXldy = countXldy;
    }

    public Integer getCountSpjky() {
        return countSpjky;
    }

    public void setCountSpjky(Integer countSpjky) {
        this.countSpjky = countSpjky;
    }

    public Integer getCountZgfj() {
        return countZgfj;
    }

    public void setCountZgfj(Integer countZgfj) {
        this.countZgfj = countZgfj;
    }

    public Integer getCountNan() {
        return countNan;
    }

    public void setCountNan(Integer countNan) {
        this.countNan = countNan;
    }

    public Integer getCountNv() {
        return countNv;
    }

    public void setCountNv(Integer countNv) {
        this.countNv = countNv;
    }

    public Integer getCountAllocateNum() {
        return countAllocateNum;
    }

    public void setCountAllocateNum(Integer countAllocateNum) {
        this.countAllocateNum = countAllocateNum;
    }

    public Integer getCountGapNum() {
        return countGapNum;
    }

    public void setCountGapNum(Integer countGapNum) {
        this.countGapNum = countGapNum;
    }

    public Integer getCountQuotasNum() {
        return countQuotasNum;
    }

    public void setCountQuotasNum(Integer countQuotasNum) {
        this.countQuotasNum = countQuotasNum;
    }

    public List<Organization> getSubOrgList() {
        return subOrgList;
    }

    public void setSubOrgList(List<Organization> subOrgList) {
        this.subOrgList = subOrgList;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParents() {
        return parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgRank() {
        return orgRank;
    }

    public void setOrgRank(String orgRank) {
        this.orgRank = orgRank;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOrgState() {
        return orgState;
    }

    public void setOrgState(Integer orgState) {
        this.orgState = orgState;
    }

    public String getOutKey1() {
        return outKey1;
    }

    public void setOutKey1(String outKey1) {
        this.outKey1 = outKey1;
    }

    public String getOutKey2() {
        return outKey2;
    }

    public void setOutKey2(String outKey2) {
        this.outKey2 = outKey2;
    }

    public String getBak1() {
        return bak1;
    }

    public void setBak1(String bak1) {
        this.bak1 = bak1;
    }

    public String getBak2() {
        return bak2;
    }

    public void setBak2(String bak2) {
        this.bak2 = bak2;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }


    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(Integer orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Integer getNumb() {
        return numb;
    }

    public void setNumb(Integer numb) {
        this.numb = numb;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public List<Organization> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<Organization> unitList) {
        this.unitList = unitList;
    }

    public List<Object> getNumbList() {
        return numbList;
    }

    public void setNumbList(List<Object> numbList) {
        this.numbList = numbList;
    }

    public AmQuotas getQuotas() {
        return quotas;
    }

    public void setQuotas(AmQuotas quotas) {
        this.quotas = quotas;
    }

    public List<Object> getCountList() {
        return countList;
    }

    public void setCountList(List<Object> countList) {
        this.countList = countList;
    }

    public Integer getCountJtxgy() {
        return countJtxgy;
    }

    public void setCountJtxgy(Integer countJtxgy) {
        this.countJtxgy = countJtxgy;
    }
}
