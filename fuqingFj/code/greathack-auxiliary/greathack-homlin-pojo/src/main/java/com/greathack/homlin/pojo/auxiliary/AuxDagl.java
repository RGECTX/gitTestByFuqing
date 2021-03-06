package com.greathack.homlin.pojo.auxiliary;

import java.io.Serializable;

/**
 * 建档管理
 *
 * @author renTX
 * @Description
 * @date 2020-09-07
 */
public class AuxDagl implements Serializable {

    private static final long serialVersionUID = 4666066551478518118L;

    /**
     * 主键
     */
    private Long daId;

    /**
     * 姓名
     */
    private String xm;

    /**
     * 性别
     */
    private String xb;


    /**
     * 身份证号
     */
    private String idcard;

    /**
     * 联系方式
     */
    private String lxfs;


    /**
     * 工作单位ID
     */
    private Long orgId;

    /**
     * 工作单位名称
     */
    private String orgName;

    /**
     *辅警类别
     */
    private String fjType;

    /**
     *辅警类别
     */
    private String state;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间 1970年以来毫秒数
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
     * 外键2
     */
    private String bak2;

    /**
     * 驾照
     */
    private String jz;

    /**
     * 服兵役
     */
    private String fby;


    /**
     * 民族，字典mz
     */
    private String mz;

    /**
     * 籍贯所在省市
     */
    private String jgszss;

    /**
     * 婚姻状况，字典，1、已婚、2、未婚、4、离异、8、丧偶
     */
    private String hyzk;

    /**
     * 政治面貌,字典，1、中共党员，2、预备党员，4、共青团员，8、群众
     */
    private String zzmm;

    /**
     * 学历，字典
     */
    private String xl;

    /**
     * 毕业院校
     */
    private String byys;

    /**
     * 专业
     */
    private String zy;

    /**
     * 毕业日期
     */
    private String byrq;

    /**
     * 特长
     */
    private String techang;

    /**
     * 爱好
     */
    private String aihao;

    /**
     * 现家庭住址
     */
    private String xjtzz;

    /**
     * 辅警编号
     */
    private String fjbh;

    /**
     * 户籍地址
     */
    private String hjdz;

    /**
     * 是否党员
     */
    private String isdy;

    /**
     * 入职日期
     */
    private String rzrq;

    /**
     * 现具体职责
     */
    private String jtzz;

    /**
     * 是否由局购买保险
     */
    private String isbx;

    /**
     * 应发工资
     */
    private String yfgz;


    //辅助字段

    private Integer countorgId;

    private Integer countisbx;

    private Integer countxb;

    private Integer bzrs;

    private Integer zzfjs;//在职辅警数

    private Integer ptfjrs;//普通辅警人数

    private Integer ptfjbzs;//普通辅警编制数

    private Integer lffjbzs;//联防辅警编制数

    private Integer lffjrs;//联防辅警人数

    private Integer zxfjbzs;//专项辅警编制数

    private Integer zxfjrs;//专项辅警人数

    public Integer getPtfjrs() {
        return ptfjrs;
    }

    public void setPtfjrs(Integer ptfjrs) {
        this.ptfjrs = ptfjrs;
    }

    public Integer getPtfjbzs() {
        return ptfjbzs;
    }

    public void setPtfjbzs(Integer ptfjbzs) {
        this.ptfjbzs = ptfjbzs;
    }

    public Integer getLffjbzs() {
        return lffjbzs;
    }

    public void setLffjbzs(Integer lffjbzs) {
        this.lffjbzs = lffjbzs;
    }

    public Integer getLffjrs() {
        return lffjrs;
    }

    public void setLffjrs(Integer lffjrs) {
        this.lffjrs = lffjrs;
    }

    public Integer getZxfjbzs() {
        return zxfjbzs;
    }

    public void setZxfjbzs(Integer zxfjbzs) {
        this.zxfjbzs = zxfjbzs;
    }

    public Integer getZxfjrs() {
        return zxfjrs;
    }

    public void setZxfjrs(Integer zxfjrs) {
        this.zxfjrs = zxfjrs;
    }

    public Integer getZzfjs() {
        return zzfjs;
    }

    public void setZzfjs(Integer zzfjs) {
        this.zzfjs = zzfjs;
    }

    public Integer getCountorgId() {
        return countorgId;
    }

    public void setCountorgId(Integer countorgId) {
        this.countorgId = countorgId;
    }

    public Integer getCountisbx() {
        return countisbx;
    }

    public void setCountisbx(Integer countisbx) {
        this.countisbx = countisbx;
    }

    public Integer getCountxb() {
        return countxb;
    }

    public void setCountxb(Integer countxb) {
        this.countxb = countxb;
    }

    public Integer getBzrs() {
        return bzrs;
    }

    public void setBzrs(Integer bzrs) {
        this.bzrs = bzrs;
    }

    public String getYfgz() {
        return yfgz;
    }

    public void setYfgz(String yfgz) {
        this.yfgz = yfgz;
    }
    public AuxDagl() {
        super();
    }

    public Long getDaId() {
        return daId;
    }

    public void setDaId(Long daId) {
        this.daId = daId;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getLxfs() {
        return lxfs;
    }

    public void setLxfs(String lxfs) {
        this.lxfs = lxfs;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getJz() {
        return jz;
    }

    public void setJz(String jz) {
        this.jz = jz;
    }

    public String getFby() {
        return fby;
    }

    public void setFby(String fby) {
        this.fby = fby;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getJgszss() {
        return jgszss;
    }

    public void setJgszss(String jgszss) {
        this.jgszss = jgszss;
    }

    public String getHyzk() {
        return hyzk;
    }

    public void setHyzk(String hyzk) {
        this.hyzk = hyzk;
    }

    public String getZzmm() {
        return zzmm;
    }

    public void setZzmm(String zzmm) {
        this.zzmm = zzmm;
    }

    public String getXl() {
        return xl;
    }

    public void setXl(String xl) {
        this.xl = xl;
    }

    public String getByys() {
        return byys;
    }

    public void setByys(String byys) {
        this.byys = byys;
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy;
    }

    public String getByrq() {
        return byrq;
    }

    public void setByrq(String byrq) {
        this.byrq = byrq;
    }

    public String getTechang() {
        return techang;
    }

    public void setTechang(String techang) {
        this.techang = techang;
    }

    public String getAihao() {
        return aihao;
    }

    public void setAihao(String aihao) {
        this.aihao = aihao;
    }

    public String getXjtzz() {
        return xjtzz;
    }

    public void setXjtzz(String xjtzz) {
        this.xjtzz = xjtzz;
    }

    public String getFjbh() {
        return fjbh;
    }

    public void setFjbh(String fjbh) {
        this.fjbh = fjbh;
    }

    public String getHjdz() {
        return hjdz;
    }

    public void setHjdz(String hjdz) {
        this.hjdz = hjdz;
    }

    public String getIsdy() {
        return isdy;
    }

    public void setIsdy(String isdy) {
        this.isdy = isdy;
    }

    public String getRzrq() {
        return rzrq;
    }

    public void setRzrq(String rzrq) {
        this.rzrq = rzrq;
    }

    public String getJtzz() {
        return jtzz;
    }

    public void setJtzz(String jtzz) {
        this.jtzz = jtzz;
    }

    public String getIsbx() {
        return isbx;
    }

    public void setIsbx(String isbx) {
        this.isbx = isbx;
    }

    /*public String getHjdlx() {
        return hjdlx;
    }

    public void setHjdlx(String hjdlx) {
        this.hjdlx = hjdlx;
    }*/


}
