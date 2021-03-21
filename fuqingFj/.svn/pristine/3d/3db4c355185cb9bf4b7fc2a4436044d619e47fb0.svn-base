/**
 * 
 */
package com.greathack.homlin.exception;

import com.greathack.utils.validate.exception.ValidateException;

/**
 * @author greathack
 *
 */
public class BizException extends ValidateException {
	
	/**
	 * 错误码
	 */
	private int errCode;
	
	/**
	 * 错误信息
	 */
	private String errMsg;

	/**
	 * @param errCode 错误码
	 * @param errMsg 错误信息
	 */
	public BizException(int errCode,String errMsg) {
		super(errMsg);
		this.errCode=errCode;
		this.errMsg=errMsg;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	

}
