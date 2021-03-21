package com.greathack.homlin.pojo.permission;

/**
 * 用户拥有的权限 在这里没有记录的权限，默认权限级别是1（没有权限）
 */
public class PowerInUser {

	/**
	 * 唯一识别码
	 */
	private Long powerInUserId;
    

    private String appCode;

	/**
	 * 拥有权限的用户
	 */
	private String userId;

	/**
	 * 属于用户的权限编码 尽量保证同一个用户唯一
	 */
	private String powerCode;

	/**
	 * 权限级别 权限判定里，高级别覆盖低级别： 1：没有权限（最低级别，如果角色里拥有权限，最后判定结果会是拥有权限），2：拥有权
	 * 限（第二级别），4：绝对没有权限（最高级别，如果角色里拥有权限，最后判定结果会是 没有权限）
	 */
	private Integer powerLevel;
	

	public Long getPowerInUserId() {
        return powerInUserId;
    }

    public void setPowerInUserId(Long powerInUserId) {
        this.powerInUserId = powerInUserId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPowerCode() {
		return powerCode;
	}

	public void setPowerCode(String powerCode) {
		this.powerCode = powerCode;
	}

	public Integer getPowerLevel() {
		return powerLevel;
	}

	public void setPowerLevel(Integer powerLevel) {
		this.powerLevel = powerLevel;
	}
	
}
