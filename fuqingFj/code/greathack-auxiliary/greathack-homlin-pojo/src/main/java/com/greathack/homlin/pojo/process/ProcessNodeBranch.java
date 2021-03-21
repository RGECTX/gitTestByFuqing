package com.greathack.homlin.pojo.process;

/**
 * 流程节点分支条件
 * @author greathack
 *
 */
public class ProcessNodeBranch {

   /**
    * 分支ID
    */
   private long branchId;
   
   /**
    * 所属流程节点ID
    */
   private long processNodeId;
   
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

	public long getBranchId() {
		return branchId;
	}
	
	public void setBranchId(long branchId) {
		this.branchId = branchId;
	}
	
	public long getProcessNodeId() {
		return processNodeId;
	}
	
	public void setProcessNodeId(long processNodeId) {
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
