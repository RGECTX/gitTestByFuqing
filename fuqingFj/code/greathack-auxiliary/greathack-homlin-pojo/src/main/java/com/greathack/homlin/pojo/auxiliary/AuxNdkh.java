package com.greathack.homlin.pojo.auxiliary;

import java.io.Serializable;

/**
 * @Description
 * @Author renTX
 * @Date 20120-09-27
 */
public class AuxNdkh implements Serializable {

    private static final long serialVersionUID = -6416185485252363816L;
    /**
     * ID
     */
    private Long id;

    /**
     * 所属主体ID
     */
    private String instanceId;

    /**
     * 身份证号码
     */
    private String idcard;

    /**
     * 考核年度
     */
    private Integer khnd;

    /**
     * 考核结论
     */
    private String khjl;

    /**
     * 备注
     */
    private String remark;


    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 外键1
     */
    private String outkey1;

    /**
     * 外键2
     */
    private String outkey2;

    /**
     * 备用1
     */
    private String bak1;

    /**
     * 备用2
     */
    private String bak2;

    public AuxNdkh() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getKhnd() {
        return khnd;
    }

    public void setKhnd(Integer khnd) {
        this.khnd = khnd;
    }

    public String getKhjl() {
        return khjl;
    }

    public void setKhjl(String khjl) {
        this.khjl = khjl;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOutkey1() {
        return outkey1;
    }

    public void setOutkey1(String outkey1) {
        this.outkey1 = outkey1;
    }

    public String getOutkey2() {
        return outkey2;
    }

    public void setOutkey2(String outkey2) {
        this.outkey2 = outkey2;
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

}
