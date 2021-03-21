package com.greathack.homlin.pojo.user;

import java.io.Serializable;

/**
 * @author zjb
 * @version 创建时间：2017年3月8日 上午10:43:47
 * 类说明
 */
public class CustomItem implements Serializable{

	/**
	
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	
	*/
	private static final long serialVersionUID = 8842154356888219142L;

	/**
	 * 自定义项目ID
	 */
	private Long customItemId;
	/**
	 * 应用编码
	 */
	private String appCode;
	/**
	 * 所属用户ID
	 */
	private Long userId;
	/**
	 * 项目名称
	 */
	private String itemName;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Long createTime;
	
	
	public Long getCustomItemId() {
		return customItemId;
	}
	public void setCustomItemId(Long customItemId) {
		this.customItemId = customItemId;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}	
	
}
