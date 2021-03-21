//Source file: G:\\业务逻辑\\权限管理系统\\src\\com\\wbaiju\\approvalProcess\\pojo\\Process.java

package com.greathack.homlin.pojo.process;

import java.util.List;


/**
 * 审批流程
 */
public class Process 
{
   
   /**
    * 流程ID
    */
   private Long processId;
   
   /**
    * 所应用的流程模板ID
    */
   private Long processTemplateId;
   /**
    * 流程审批状态
    * 1：等待审批，2：审批通过，4：审批不通过（审批节点中，只要一个不通过，这个流程就算不通过）
    */
   private int processState;
   
   /**
    * 创建时间
    * 
    */
   private long createTime;
   
   /**
    * 结束时间
    * 
    */
   private long endTime;
   

	private String pendingUserIds;
	private String handledUserIds;
	private String vars;
	private String approvalImpl;
      
   /**
	 * 节点添加列表
	 */
   private List<ProcessNode> processNodeList;   
   
   
   /**
    * @roseuid 571AD42900EA
    */
   public Process() 
   {
    
   }

	public Long getProcessId() {
		return processId;
	}
	
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	
	public Long getProcessTemplateId() {
		return processTemplateId;
	}
	
	public void setProcessTemplateId(Long processTemplateId) {
		this.processTemplateId = processTemplateId;
	}

	public int getProcessState() {
		return processState;
	}

	public void setProcessState(int processState) {
		this.processState = processState;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getPendingUserIds() {
		return pendingUserIds;
	}

	public void setPendingUserIds(String pendingUserIds) {
		this.pendingUserIds = pendingUserIds;
	}

	public String getHandledUserIds() {
		return handledUserIds;
	}

	public void setHandledUserIds(String handledUserIds) {
		this.handledUserIds = handledUserIds;
	}

	public List<ProcessNode> getProcessNodeList() {
		return processNodeList;
	}

	public void setProcessNodeList(List<ProcessNode> processNodeList) {
		this.processNodeList = processNodeList;
	}

	public String getVars() {
		return vars;
	}

	public void setVars(String vars) {
		this.vars = vars;
	}

	public String getApprovalImpl() {
		return approvalImpl;
	}

	public void setApprovalImpl(String approvalImpl) {
		this.approvalImpl = approvalImpl;
	}
	
	
   
}
