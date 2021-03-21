package com.greathack.homlin.pojo.user;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {
	
	
	private static final long serialVersionUID = 8842154356888219142L;
	
	
	public static final int USER_STATE_NORMAL=1;//正常
	public static final int USER_STATE_LOCKED=2;//锁定
	public static final int USER_STATE_DELETED=4;//删除
	public static final int USER_STATE_BAK1=8;//预留状态1
	public static final int USER_STATE_BAK2=16;//预留状态2
	public static final int USER_STATE_BAK3=32;//预留状态3
	public static final int USER_STATE_BAK4=64;//预留状态4

	private Long userId;
	private String appCode;
	private Integer userState;
	private String nickName;
	private String avatar;
	private String userName;
    private String mobile;
    private String tel;
    private String email;
	private Integer sex;
	private String birthday;
	private String chineseBirthday;
	private String companyName;
	private String position;
	private Long createTime;
	private String certificationNo;
	private String outKey1;
	private String outKey2;
	private String bak1;
	private String bak2;

	private String idcard;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(Long userId, String appCode, Integer userState) {
		this.userId = userId;
		this.appCode = appCode;
		this.userState = userState;
	}

	/** full constructor */
	public User(Long userId, String appCode, Integer userState, String nickName, String avatar, String userName, Integer sex, String birthday, String chineseBirthday,
			String companyName, String position, Long createTime, String certificationNo, String outKey1, String outKey2, String bak1, String bak2,String idcard) {
		this.userId = userId;
		this.appCode = appCode;
		this.userState = userState;
		this.nickName = nickName;
		this.avatar = avatar;
		this.userName = userName;
		this.sex = sex;
		this.birthday = birthday;
		this.chineseBirthday = chineseBirthday;
		this.companyName = companyName;
		this.position = position;
		this.createTime = createTime;
		this.certificationNo = certificationNo;
		this.outKey1 = outKey1;
		this.outKey2 = outKey2;
		this.bak1 = bak1;
		this.bak2 = bak2;
		this.idcard = idcard;
	}

	// Property accessors

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAppCode() {
		return this.appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public Integer getUserState() {
		return this.userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getChineseBirthday() {
		return this.chineseBirthday;
	}

	public void setChineseBirthday(String chineseBirthday) {
		this.chineseBirthday = chineseBirthday;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Long getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getCertificationNo() {
		return this.certificationNo;
	}

	public void setCertificationNo(String certificationNo) {
		this.certificationNo = certificationNo;
	}

	public String getOutKey1() {
		return this.outKey1;
	}

	public void setOutKey1(String outKey1) {
		this.outKey1 = outKey1;
	}

	public String getOutKey2() {
		return this.outKey2;
	}

	public void setOutKey2(String outKey2) {
		this.outKey2 = outKey2;
	}

	public String getBak1() {
		return this.bak1;
	}

	public void setBak1(String bak1) {
		this.bak1 = bak1;
	}

	public String getBak2() {
		return this.bak2;
	}

	public void setBak2(String bak2) {
		this.bak2 = bak2;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
}