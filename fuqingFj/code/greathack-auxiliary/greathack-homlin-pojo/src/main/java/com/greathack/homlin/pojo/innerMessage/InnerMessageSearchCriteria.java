package com.greathack.homlin.pojo.innerMessage;

import com.greathack.homlin.pojo.system.SearchCriteria;
import com.greathack.utils.tools.HashUtils;

public class InnerMessageSearchCriteria extends SearchCriteria {

	

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
	
	/**
	 * 备用1字段
	 */
	public static final int KW_FIELDS_BAK1=16;
	
	/**
	 * 备用2字段
	 */
	public static final int KW_FIELDS_BAK2=32;
	
	
	private String sender;
	private String msgType;
	private Integer msgState;
	



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
				return "title";
    		case InnerMessageSearchCriteria.KW_FIELDS_CONTENT:
    			return "content";
    		case InnerMessageSearchCriteria.KW_FIELDS_ATTACHMENTS :
    			return "attachments";
    		case InnerMessageSearchCriteria.KW_FIELDS_REMARK:
    			return "remark";
    			
    		case InnerMessageSearchCriteria.KW_FIELDS_BAK1:
    			return "bak1";
    			
    		case InnerMessageSearchCriteria.KW_FIELDS_BAK2:
    			return "bak2";
    			
			default:
				return "";
		}
	}
	

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((sender == null) ? 0 : HashUtils.fnv1_32_hash(sender));
		result = prime * result + ((msgType == null) ? 0 : HashUtils.fnv1_32_hash(msgType));
		result = prime * result + ((msgState == null) ? 0 : HashUtils.fnv1_32_hash(msgState.toString()));
	
		return result;
	}	

}
