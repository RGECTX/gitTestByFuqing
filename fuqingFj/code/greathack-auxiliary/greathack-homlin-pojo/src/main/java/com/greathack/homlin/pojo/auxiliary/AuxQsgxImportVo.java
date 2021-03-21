package com.greathack.homlin.pojo.auxiliary;

import com.greathack.homlin.annotation.Excel;

import java.io.Serializable;

/**
 * @Description
 * @Author renTX
 * @Date 2020-10-13
 */
public class AuxQsgxImportVo implements Serializable {

    private static final long serialVersionUID = -5905311907931823309L;

    @Excel(name = "本人姓名", type = Excel.Type.EXPORT)
    private String xm;
    @Excel(name = "亲属姓名")
    private String qsname;

    @Excel(name = "称谓")
/*
    @Excel(name = "称谓", combo = {"配偶", "子", "独生子", "长子", "次子", "三子", "四子", "五子", "养子或继子", "女婿", "其他儿子", "女", "独生女", "长女", "次女", "三女", "四女", "五女", "养女或继女", "儿媳", "其他女儿", "孙子、孙女或外孙子、外孙女", "孙子", "孙女", "外孙子", "外孙女", "孙媳妇或外孙媳妇", "孙女婿或外孙女婿", "曾孙子或外曾孙子", "曾孙女或外曾孙女", "其他孙子、孙女或外孙子、外孙女","父亲", "母亲", "公公", "婆婆", "岳父", "岳母", "继父或养父", "继母或养母", "其他父母关系", "祖父母或外祖父母", "祖父", "祖母", "外祖父", "外祖母", "配偶的祖父母或外祖父母", "曾祖父","曾祖母", "配偶的曾祖父母或外曾祖父母", "其他祖父母或外祖父母关系", "兄", "嫂", "弟", "弟媳", "姐姐", "姐夫", "妹妹", "妹夫", "其他兄弟姐妹", "其他", "伯父", "伯母", "叔父","婶母", "舅父", "舅母", "姨父", "姨母", "姑父", "姑母", "堂兄弟、堂姐妹", "表兄弟、表姐妹", "侄子", "侄女", "外甥", "外甥女", "其他亲属", "非亲属"})
*/
/*
    @Excel(name = "称谓", prompt = "配偶, 子, 独生子, 长子, 次子, 三子, 四子, 五子, 养子或继子, 女婿, 其他儿子, 女, 独生女, 长女, 次女, 三女, 四女, 五女, 养女或继女, 儿媳, 其他女儿, 孙子、孙女或外孙子、外孙女, 孙子, 孙女, 外孙子, 外孙女, 孙媳妇或外孙媳妇, 孙女婿或外孙女婿, 曾孙子或外曾孙子, 曾孙女或外曾孙女, 其他孙子、孙女或外孙子、外孙女,父亲, 母亲, 公公, 婆婆, 岳父, 岳母, 继父或养父, 继母或养母, 其他父母关系, 祖父母或外祖父母, 祖父, 祖母, 外祖父, 外祖母, 配偶的祖父母或外祖父母, 曾祖父,曾祖母, 配偶的曾祖父母或外曾祖父母, 其他祖父母或外祖父母关系, 兄, 嫂, 弟, 弟媳, 姐姐, 姐夫, 妹妹, 妹夫, 其他兄弟姐妹, 其他, 伯父, 伯母, 叔父,婶母, 舅父, 舅母, 姨父, 姨母, 姑父, 姑母, 堂兄弟、堂姐妹, 表兄弟、表姐妹, 侄子, 侄女, 外甥, 外甥女, 其他亲属, 非亲属")
*/

    private String kinshipTerm;
    @Excel(name = "亲属身份证号码")
    private String ownIdcard;
    @Excel(name = "*出生日期", prompt = "格式：2020/01/03或2020-01-03")
    private String birthdayStr;
    @Excel(name = "工作单位")
    private String workUnit;
    @Excel(name = "职务")
    private String post;
    @Excel(name = "学历", combo = {"全日制本科", "非全日制本科", "全日制大专", "非全日制大专", "中专", "职高", "高中", "初中", "小学", "无学历"})
    private String eduLevel;
    @Excel(name = "现状", combo = {"在业", "无业", "上学", "学龄前", "离退", "已故"})
    private String nowStatus;
    @Excel(name = "健康状况", combo = {"健康", "残疾", "重大疾病", "一般疾病"})
    private String healthState;
    @Excel(name = "健康说明")
    private String healthDetail;
    @Excel(name = "科级以上干部", combo = {"非科级以上干部", "不确定级别", "乡科级副职", "乡科级正职", "县处级副级", "县处级正职",
            "厅局级副职", "厅局级正职", "省部级副职", "省部级正职", "国家级副职", "国家级正职"})
    private String kjysgbFlag;
    @Excel(name = "出国境", combo = {"无", "有"})
    private String cgjFlag;//出国境标识，1、无，2、有

    @Excel(name = "犯罪", combo = {"无", "有"})
    private String fzFlag;

    @Excel(name = "收押收教", combo = {"无", "有"})
    private String sysjFlag;

    @Excel(name = "人户分离", combo = {"无", "有"})
    private String rhflFlag;

    @Excel(name = "本局人员编制类别", combo = {"非本局工作", "民警", "辅警", "事业编", "工勤编"})
    private String bjgzFlag;

    @Excel(name = "*本人身份证号码")
    private String idcard;

    @Excel(name = "本局工作部门编码", prompt = "如：65230155XXXX", type = Excel.Type.IMPORT)
    private String bjOrgCode;

    @Excel(name = "本局工作部门", type = Excel.Type.EXPORT)
    private String bjOrgName;

    //工作地省
    @Excel(name = "工作地省")
    private String gzddSheng;

    //工作地市
    @Excel(name = "工作地市")
    private String gzddShi;

    //工作地县
    @Excel(name = "工作地县")
    private String gzddXian;

    //工作地详细地址
    @Excel(name = "工作地详细地址")
    private String gzddAddress;


    @Excel(name = "工作地点所属范围", combo = {"国外", "国内", "疆外", "疆内", "昌吉州外", "昌吉州内", "昌吉市外", "昌吉市内"})
    private String gzddssfw;

    @Excel(name = "工作单位性质", combo = {"公务员单位", "事业单位", "国企", "私企", "个体"})
    private String gzdwxz;

    //居住地省
    @Excel(name = "居住地省")
    private String jzddSheng;

    //居住地市
    @Excel(name = "居住地市")
    private String jzddShi;

    //居住地县
    @Excel(name = "居住地县")
    private String jzddXian;

    //居住地详细地址
    @Excel(name = "居住地详细地址")
    private String jzddAddress;

    @Excel(name = "居住地点所属范围", combo = {"国外", "国内", "疆外", "疆内", "昌吉州外", "昌吉州内", "昌吉市外", "昌吉市内"})
    private String jzddssfw;

    @Excel(name = "其他")
    private String others;

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getQsname() {
        return qsname;
    }

    public void setQsname(String qsname) {
        this.qsname = qsname;
    }

    public String getOwnIdcard() {
        return ownIdcard;
    }

    public void setOwnIdcard(String ownIdcard) {
        this.ownIdcard = ownIdcard;
    }

    public String getKinshipTerm() {
        return kinshipTerm;
    }

    public void setKinshipTerm(String kinshipTerm) {
        this.kinshipTerm = kinshipTerm;
    }

    public String getBirthdayStr() {
        return birthdayStr;
    }

    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getNowStatus() {
        return nowStatus;
    }

    public void setNowStatus(String nowStatus) {
        this.nowStatus = nowStatus;
    }

    public String getHealthState() {
        return healthState;
    }

    public void setHealthState(String healthState) {
        this.healthState = healthState;
    }

    public String getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel;
    }

    public String getHealthDetail() {
        return healthDetail;
    }

    public void setHealthDetail(String healthDetail) {
        this.healthDetail = healthDetail;
    }

    public String getKjysgbFlag() {
        return kjysgbFlag;
    }

    public void setKjysgbFlag(String kjysgbFlag) {
        this.kjysgbFlag = kjysgbFlag;
    }

    public String getCgjFlag() {
        return cgjFlag;
    }

    public void setCgjFlag(String cgjFlag) {
        this.cgjFlag = cgjFlag;
    }

    public String getFzFlag() {
        return fzFlag;
    }

    public void setFzFlag(String fzFlag) {
        this.fzFlag = fzFlag;
    }

    public String getSysjFlag() {
        return sysjFlag;
    }

    public void setSysjFlag(String sysjFlag) {
        this.sysjFlag = sysjFlag;
    }

    public String getRhflFlag() {
        return rhflFlag;
    }

    public void setRhflFlag(String rhflFlag) {
        this.rhflFlag = rhflFlag;
    }

    public String getBjgzFlag() {
        return bjgzFlag;
    }

    public void setBjgzFlag(String bjgzFlag) {
        this.bjgzFlag = bjgzFlag;
    }

    public String getBjOrgCode() {
        return bjOrgCode;
    }

    public void setBjOrgCode(String bjOrgCode) {
        this.bjOrgCode = bjOrgCode;
    }

    public String getGzddSheng() {
        return gzddSheng;
    }

    public void setGzddSheng(String gzddSheng) {
        this.gzddSheng = gzddSheng;
    }

    public String getGzddShi() {
        return gzddShi;
    }

    public void setGzddShi(String gzddShi) {
        this.gzddShi = gzddShi;
    }

    public String getGzddXian() {
        return gzddXian;
    }

    public void setGzddXian(String gzddXian) {
        this.gzddXian = gzddXian;
    }

    public String getGzddAddress() {
        return gzddAddress;
    }

    public void setGzddAddress(String gzddAddress) {
        this.gzddAddress = gzddAddress;
    }

    public String getGzddssfw() {
        return gzddssfw;
    }

    public void setGzddssfw(String gzddssfw) {
        this.gzddssfw = gzddssfw;
    }

    public String getGzdwxz() {
        return gzdwxz;
    }

    public void setGzdwxz(String gzdwxz) {
        this.gzdwxz = gzdwxz;
    }

    public String getJzddSheng() {
        return jzddSheng;
    }

    public void setJzddSheng(String jzddSheng) {
        this.jzddSheng = jzddSheng;
    }

    public String getJzddShi() {
        return jzddShi;
    }

    public void setJzddShi(String jzddShi) {
        this.jzddShi = jzddShi;
    }

    public String getJzddXian() {
        return jzddXian;
    }

    public void setJzddXian(String jzddXian) {
        this.jzddXian = jzddXian;
    }

    public String getJzddAddress() {
        return jzddAddress;
    }

    public void setJzddAddress(String jzddAddress) {
        this.jzddAddress = jzddAddress;
    }

    public String getJzddssfw() {
        return jzddssfw;
    }

    public void setJzddssfw(String jzddssfw) {
        this.jzddssfw = jzddssfw;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getBjOrgName() {
        return bjOrgName;
    }

    public void setBjOrgName(String bjOrgName) {
        this.bjOrgName = bjOrgName;
    }
}
