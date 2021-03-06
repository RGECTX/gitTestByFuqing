package com.greathack.homlin.pojo.auxiliary;

import com.greathack.homlin.annotation.Excel;

import java.io.Serializable;

/**
 * @Description
 * @Author renTX
 * @Date 2020-10-12
 */
public class AuxDaglImportVo implements Serializable {

	private static final long serialVersionUID = -5320194002684434225L;

	//注：combo不能与prompt同时使用，否则combo会失效

	private Long orgId;
	private String state;


	@Excel(name = "*姓名")
	private String xm;
	@Excel(name = "性别",combo={"男","女"})
	private String xb;//性别,字典sex（1、男，2、女）
	@Excel(name = "*工作单位编码",prompt = "如：65230155XXXX",type= Excel.Type.IMPORT)
	private String orgCode;

	@Excel(name = "*身份证号")
	private String idcard;// 身份证号码
	@Excel(name = "辅警编号")
	private String fjbh;
	@Excel(name = "联系方式")
	private String lxfs;
	@Excel(name = "工作单位" ,type= Excel.Type.EXPORT)
	private String orgName;
	@Excel(name = "辅警类别", combo={"专项辅警","联防辅警","普通辅警"})
	private String fjType;
	/*@Excel(name = "*状态", combo={"在岗","离职"})
	private String state;//人员状态，字典：在岗1，离职2*/
	@Excel(name = "备注")
	private String remark;
	@Excel(name = "驾照" ,combo = {"A1","A2","A3","B1","B2","C1","C2","C3","D","E","F","M","N","P","C4"})
	private String jz;//驾照，字典
	@Excel(name = "是否退伍军人", combo={"是","否"})
	private String fby;//服兵役 ：1、是 0、否
	@Excel(name = "民族",combo={"汉族","蒙古族","回族","藏族","维吾尔族","苗族","彝族","壮族","布依族","朝鲜族","满族","侗族","瑶族","白族","土家族","哈尼族","哈萨克族",
			"傣族","黎族","傈僳族","佤族","畲族","高山族","拉祜族","水族","东乡族","纳西族","景颇族","柯尔克孜族","土族","达斡尔族","仫佬族","羌族","布朗族","撒拉族","毛难族",
			"仡佬族","锡伯族","阿昌族","普米族","塔吉克族","怒族","乌孜别克族","俄罗斯族","鄂温克族","崩龙族",
			"保安族","裕固族","京族","塔塔尔族","独龙族","鄂伦春族","赫哲族","门巴族","珞巴族","基诺族","其他","外国血统"})
	private String mz;//民族，字典mz
	@Excel(name = "籍贯所在省市")
	private String jgszss;
	@Excel(name = "婚姻状况" , combo={"已婚","未婚","离异","丧偶"})
	private String hyzk;//婚姻状况，字典，1、已婚、2、未婚、4、离异、8、丧偶
	@Excel(name = "政治面貌", combo={"中共党员","预备党员","共青团员","群众"} )
	private String zzmm;//政治面貌,字典，1、中共党员，2、预备党员，4、共青团员，8、群众
	@Excel(name = "学历",combo={"全日制本科","非全日制本科","全日制大专","非全日制大专","中专","职高","高中","初中","小学","无学历"})
	private String xl;//学历，字典
	@Excel(name = "毕业院校")
	private String byys;
	@Excel(name = "专业")
	private String zy;
	@Excel(name = "毕业日期", prompt = "格式：2020/01/03或2020-01-03")
	private String byrq;
	@Excel(name = "爱好")
	private String aihao;
	@Excel(name = "特长")
	private String techang;
	@Excel(name = "现家庭住址")
	private String xjtzz;
	/*@Excel(name = "户籍地类型", combo={"疆内","疆外"})
	private String hjdlx;//户籍地类型，字典，1、疆内 ，2、疆外*/

	@Excel(name = "户籍地址")
	private String hjdz;

	@Excel(name = "是否党员", combo={"是","否"})
	private String isdy;

	@Excel(name = "入职日期", prompt = "格式：2020/01/03或2020-01-03")
	private String rzrq;

	@Excel(name = "现具体职责")
	private String jtzz;

	@Excel(name = "由局购买保险", combo={"是","否"})
	private String isbx;

	public AuxDaglImportVo() {
		super();
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

	public String getAihao() {
		return aihao;
	}

	public void setAihao(String aihao) {
		this.aihao = aihao;
	}

	public String getTechang() {
		return techang;
	}

	public void setTechang(String techang) {
		this.techang = techang;
	}

	public String getXjtzz() {
		return xjtzz;
	}

	public void setXjtzz(String xjtzz) {
		this.xjtzz = xjtzz;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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

	public String getFjbh() {
		return fjbh;
	}

	public void setFjbh(String fjbh) {
		this.fjbh = fjbh;
	}

	/*@Excel(name = "工作单位编码",prompt = "如：65230155XXXX",type= Excel.Type.IMPORT)
	private String orgCode;
	@Excel(name = "上级工作单位" ,type= Excel.Type.EXPORT)
	private String parentName;
	@Excel(name = "出生年月", prompt = "格式：2020/01/03或2020-01-03")
	private String csrq;
	*//*@Excel(name = "照片地址")*//*
	private String zp;
	@Excel(name = "人员类别", combo={"辅警","事业性岗位","巡逻队员","视频监看员","交通协管员","转岗辅警"})
	private String rylb;
	@Excel(name = "辅警类型", combo={"内勤辅警","交通辅警","警务站辅警"})
	private Integer fjlx;//辅警类型:1、内勤辅警，2、交通辅警，4、警务站辅警
	*//*@Excel(name = "家庭情况类别",prompt = "填：双警家庭、夫妻工作异地、夫妻两地分居、子女本局工作、父母在本局工作、亲属为科级以上干部")*//*
	private String jtqklb;//家庭情况类别:1、双警家庭，2、夫妻工作异地，4、夫妻两地分居，8、子女本局工作，16、父母在本局工作，32、亲属为科级以上干部
	@Excel(name = "借调部门编码",prompt = "如：65230155XXXX，非借调则不填",type= Excel.Type.IMPORT)
	private String jdOrgCode;
	@Excel(name = "借调部门",type= Excel.Type.EXPORT)
	private String jdOrgName;
	//户籍信息
	@Excel(name = "户籍所在省市")
	private String hjdszss;
	@Excel(name = "户籍详细地址")
	private String hjdxxdz;
	@Excel(name = "现住址所在省市")
	private String xzzszss;
	@Excel(name = "现住址详细地址")
	private String xzzxxdz;
	@Excel(name = "现住址所属派出所")
	private String xzzsspcs;

	//工作信息
	@Excel(name = "职务名称")
	private String zwmc;
	@Excel(name = "正副级", combo={"正职","副职"})
	private String zfz;//正副级
	*//*@Excel(name = "岗位类别", combo={"B门岗","安全隐患岗","案件岗","保障岗","财务岗","查验员岗","车辆管理岗","车辆审验岗","持枪岗","窗口岗","档案岗","党建岗",
			"电子物证勘验岗","法制岗","非税票据核销、食堂管理岗","管教岗","后勤岗","户籍岗","会计岗","基建项目岗","技术岗","检查站岗",
			"交通秩序岗","接处警岗","接警岗","禁毒类岗","局司机岗","考核岗","考试预约岗","库管员岗","快赔岗","两个教育学习岗","两客一危岗",
			"流口岗","门岗","内勤岗","情报信息研判岗","人事岗","社区警务室岗","涉案物品管理岗","市局办案中心管理员岗","视频监看员岗","收发文岗",
			"数据岗","司机岗","提讯岗","提押岗","外勤岗","外勤秩序岗","网络维护岗","违法处理岗","文秘岗","信息系统维护岗","刑侦岗","宣传岗","巡逻防控岗",
			"延伸点负责人岗","业务内勤岗","一体化岗","医务岗","侦查岗","指挥调度岗","治安管理岗","装备、被装管理岗","咨询岗","综合内勤岗","网络监控岗"})*//*
	@Excel(name = "岗位类别")
	private String gwlb;
	@Excel(name = "岗位级别", combo={"管理九级","管理十级"})
	private String gwjb;
	*//*@Excel(name = "薪级", combo={"一级","二级","三级","四级","五级","六级","七级","八级","九级","十级","十一级","无高定三级","无高定四级","无高定五级","无高定六级",
			"无高定七级","无高定八级","无高定九级","十二级","十三级","十四级","十五级","十六级","十七级","十八级","十九级","二十级","二十一级","二十二级","二十三级",
			"二十四级","二十五级","二十六级","二十七级","二十八级","二十九级","三十级","三十一级","三十二级","三十三级","三十四级","三十五级","三十六级","三十七级",
			"三十八级","三十九级","四十级","四十一级","四十二级","四十三级","四十四级","四十五级","四十六级","四十七级","四十八级","四十九级","五十级","无高定十级",
			"无高定十一级","无高定十二级","无高定十三级","无高定十四级","无高定十五级","无高定十六级","无高定十七级","无高定十八级","无高定十九级","无高定二十级",
			"无高定二十一","无高定二十二级","无高定二十三级","无高定二十四级","无高定二十五级","无高定二十二六级","无高定二十六级","无高定二十七级","无高定二十八级",
			"无高定二十九级","无高定三十级","无高定三十一级","无高定三十二级"})*//*
	@Excel(name = "薪级")
	private String xj;
	@Excel(name = "辅警编号")
	private String jh;
//	@Excel(name = "警衔", combo={"不定等","一级辅警","二级辅警","三级辅警","四级辅警","五级辅警","六级辅警","六级优秀辅警"})
	private String jx;
	@Excel(name = "入党(团)日期", prompt = "格式：2020/01/03或2020-01-03")
	private String jrdtrq;
	@Excel(name = "参加工作日期", prompt = "格式：2020/01/03或2020-01-03")
	private String cjgzrq;
	@Excel(name = "参加公安工作日期", prompt = "格式：2020/01/03或2020-01-03")
	private String cjgarq;
	@Excel(name = "任职日期", prompt = "格式：2020/01/03或2020-01-03")
	private String rzrq;
	@Excel(name = "职级起算日期", prompt = "格式：2020/01/03或2020-01-03")
	private String zjqsrq;
	@Excel(name = "岗位调整记录")
	private String gwtzjl;
	@Excel(name = "工资卡号")
	private String gzkh;
	@Excel(name = "开户行名称")
	private String khhmc;
	@Excel(name = "基本工资", prompt = "填：纯数字")
	private String sbjf;
	@Excel(name = "特殊人才岗位补贴", prompt = "填：纯数字")
	private String zfgjjjf;
	@Excel(name = "招聘方式", combo={"局统一招聘","用人单位自行招聘"} )
	private String zpfs;//招聘方式,字典，1、中共局统一招聘党员，2、用人单位自行招聘
	@Excel(name = "经费来源", combo={"市财政","单位自筹","乡镇财政"} )
	private String jfly;//经费来源,字典，1、市财政，2、单位自筹，4、乡镇财政

	//身体情况
	@Excel(name = "血型", combo={"A","B","AB","O"})
	private String xx;
	@Excel(name = "身高(cm)")
	private String sg;
	@Excel(name = "体重(kg)")
	private String tz;
	@Excel(name = "年度体检情况")
	private String ndtjqk;
	@Excel(name = "重大疾病")
	private String zdjb;
	@Excel(name = "伤亡统计")
	private String swtj;


	//家庭情况
	@Excel(name = "家庭年收入")
	private String jtnsr;
	*//*@Excel(name = "家庭类型")*//*
	private String jtlx;
	@Excel(name = "亲属提拔为科级以上干部")
	private String qstbwkjysgb;
	@Excel(name = "直系亲属在同一单位的")
	private String zxqsztydw;
	@Excel(name = "亲属出国出境留学的")
	private String qscgcjlx;
	@Excel(name = "亲属被司法机关追究刑事责任的")
	private String qsbsfjgzjxszr;
	@Excel(name = "亲属被收押收教的")
	private String qsbsysj;
	@Excel(name = "亲属被治安刑事处罚处理过的")
	private String qsbzaxscfclg;
	@Excel(name = "有无人户分离情况")
	private String ywrhflqk;

	//其他信息
	@Excel(name = "紧急联系人电话")
	private String jjlxr;
	@Excel(name = "档案编号")
	private String dabh;
	@Excel(name = "出入境证件持有情况")
	private String crjzjqyqk;
	@Excel(name = "各类培训情况")
	private String glpxqk;*/



}
