package com.greathack.homlin.pojo.innerMessage;

import com.greathack.homlin.pojo.system.SearchCriteria;
import com.greathack.utils.tools.HashUtils;

public class ReceiveSearchCriteria extends SearchCriteria {



	/**
	 * 标题
	 */
	public static final int KW_FIELDS_TITLE=1;
	/**
	 * 内容
	 */
	public static final int KW_FIELDS_CONTENT=2;
	/**
	 * 附件字段
	 */
	public static final int KW_FIELDS_ATTACHMENTS =4;

	/**
	 * 备注字段
	 */
	public static final int KW_FIELDS_REMARK =8;

	


	private Integer receiveType;
	private Long innerMessageId;
	private String recipient;
	private Integer receiveState;
	private String sender;
	private String msgType;
	private Integer msgState;
	


	public Integer getReceiveType() {
		return receiveType;
	}



	public void setReceiveType(Integer receiveType) {
		this.receiveType = receiveType;
	}



	public Long getInnerMessageId() {
		return innerMessageId;
	}



	public void setInnerMessageId(Long innerMessageId) {
		this.innerMessageId = innerMessageId;
	}



	public String getRecipient() {
		return recipient;
	}



	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}



	public Integer getReceiveState() {
		return receiveState;
	}



	public void setReceiveState(Integer receiveState) {
		this.receiveState = receiveState;
	}

	public String getSender() {
		return sender;
	}



	public void setSender(String sender) {
		this.sender = sender;
	}



	public String getMsgType() {
		return msgType;
	}



	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}



	public Integer getMsgState() {
		return msgState;
	}



	public void setMsgState(Integer msgState) {
		this.msgState = msgState;
	}



	/**
	 * 通过字段常量，获取字段名称
	 * @param field
	 * @return 字段名称
	 */
	protected String getKwFieldName(int field){
		switch(field){

			case InnerMessageSearchCriteria.KW_FIELDS_TITLE:
				return "innerMessage.title";
			case InnerMessageSearchCriteria.KW_FIELDS_CONTENT:
				return "innerMessage.content";
			case InnerMessageSearchCriteria.KW_FIELDS_ATTACHMENTS :
				return "innerMessage.attachments";
			case InnerMessageSearchCriteria.KW_FIELDS_REMARK:
				return "innerMessage.remark";
    			
			default:
				return "";
		}
	}
	

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((receiveType == null) ? 0 : HashUtils.fnv1_32_hash(receiveType.toString()));
		result = prime * result + ((innerMessageId == null) ? 0 : HashUtils.fnv1_32_hash(innerMessageId.toString()));
		result = prime * result + ((recipient == null) ? 0 : HashUtils.fnv1_32_hash(recipient));
		result = prime * result + ((receiveState == null) ? 0 : HashUtils.fnv1_32_hash(receiveState.toString()));
		result = prime * result + ((sender == null) ? 0 : HashUtils.fnv1_32_hash(sender));
		result = prime * result + ((msgType == null) ? 0 : HashUtils.fnv1_32_hash(msgType));
		result = prime * result + ((msgState == null) ? 0 : HashUtils.fnv1_32_hash(msgState.toString()));
		
		return result;
	}	

}
