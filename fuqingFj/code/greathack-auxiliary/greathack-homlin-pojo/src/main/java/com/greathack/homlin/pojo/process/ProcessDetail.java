package com.greathack.homlin.pojo.process;

import java.util.List;

/**
 * 流程详情
 * @author greathack
 *
 */
public class ProcessDetail extends Process {

	/**
	 * 流程节点详情列表
	 */
	private List<ProcessNodeDetail> processNodeDetailList;

	public List<ProcessNodeDetail> getProcessNodeDetailList() {
		return processNodeDetailList;
	}

	public void setProcessNodeDetailList(
			List<ProcessNodeDetail> processNodeDetailList) {
		this.processNodeDetailList = processNodeDetailList;
	}
}
