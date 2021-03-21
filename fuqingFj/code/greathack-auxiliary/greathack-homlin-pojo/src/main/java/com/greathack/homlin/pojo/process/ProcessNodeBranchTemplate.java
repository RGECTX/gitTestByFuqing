package com.greathack.homlin.pojo.process;

/**
 * 流程节点分支条件模板
 * @author greathack
 *
 */
public class ProcessNodeBranchTemplate {

   /**
    * 分支ID
    */
   private Long branchId;
   
   /**
    * 所属流程节点模板ID
    */
   private Long processNodeId;
   
   /**
    * 条件
    */
   private String cond;
   
   /**
    * 排序
    */
   private int sort;
   
   /**
    * 下一节点编码
    */
   private String nextNodeCode;

	public Long getBranchId() {
		return branchId;
	}
	
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	
	public Long getProcessNodeId() {
		return processNodeId;
	}
	
	public void setProcessNodeId(Long processNodeId) {
		this.processNodeId = processNodeId;
	}
	
	public String getCond() {
		return cond;
	}
	
	public void setCond(String cond) {
		this.cond = cond;
	}
	
	public String getNextNodeCode() {
		return nextNodeCode;
	}
	
	public void setNextNodeCode(String nextNodeCode) {
		this.nextNodeCode = nextNodeCode;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
