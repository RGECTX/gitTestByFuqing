package com.greathack.homlin.pojo.permission;

/**
 * 角色拥有的权限 在这里没有记录的权限，默认权限级别是1（没有权限）
 */
public class PowerInRole {

	/**
	 * 唯一识别码
	 */
	private Long powerInRoleId;
    

    private String appCode;

	/**
	 * 拥有权限的角色
	 */
	private String roleId;

	/**
	 * 属于用户的权限编码 尽量保证同一个角色唯一
	 */
	private String powerCode;

	/**
	 * 权限级别 权限判定里，高级别覆盖低级别： 1：没有权限（最低级别，如果用户里拥有权限，最后判定结果会是拥有权限），2：拥有权
	 * 限（第二级别），4：绝对没有权限（最高级别，如果用户里拥有权限，最后判定结果会是 没有权限）
	 */
	private Integer powerLevel;

    public Long getPowerInRoleId() {
        return powerInRoleId;
    }

    public void setPowerInRoleId(Long powerInRoleId) {
        this.powerInRoleId = powerInRoleId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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
