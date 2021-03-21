//Source file: G:\\业务逻辑\\权限管理系统\\src\\com\\wbaiju\\approvalProcess\\pojo\\ProcessNode.java

package com.greathack.homlin.pojo.process;

import java.util.List;


/**
 * 流程节点
 */
public class ProcessNode 
{
   
   /**
    * 流程节点ID
    */
   private Long processNodeId;
   
   /**
    * 所属流程
    */
   private Long processId;
   
   /**
    * 审批通过条件:1:按票数，2：一票否决，3：按票数，且一票否决
    */
   private Integer passRequire;
   
   /**
    * 通过票数:用于按票数审批，该节点有几个人通过，就算通过
    */
   private Integer passNum;

	/**
	 * 审批类型:1:审批（有通过，有不通过），2:采集（只有通过）
	 */
	private int approvalType;
   
   
   /**
    * 节点编码
    */
   private String nodeName;
   
   
   
   /**
    * 节点编码
    */
   private String nodeCode;
   
   /**
	 * 审批角色ID
	 */
	private String approvalRoleId; 
	
   /**
    * 上一节点编码：起始节点用start
    */
   private String preNodeCode;
   
   /**
    * 下一节点编码：结束节点用end
    */
   private String nextNodeCode;


	/**
	 * 节点事件实现类Bean名称
	 */
	private String nodeEventImpl;

	/**
	 * 前置事件参数
	 */
	private String beforeParameter;

	/**
	 * 后置事件参数
	 */
	private String afterParameter;

   /**
    * 节点审批状态
    * 1：未到达，2：等待审批，4：审批通过，8：审批不通过（审批项目中，只要一个不通过，
    * 这个节点就算不通过）
    */
   private Integer nodeState;
   
   /**
    * 到达时间
    */
   private Long fetchTime;
   
   /**
    * 结束时间
    */
   private Long endTime;
   
   /**
    * 排序
    */
   private int sort;
   
   
   /**
	 * 跳转条件添加列表
	 */
  private List<ProcessNodeBranch> processNodeBranchListForAdd;
   
   /**
    * @roseuid 571AD42900FE
    */
   public ProcessNode() 
   {
    
   }
	
	public Long getProcessNodeId() {
		return processNodeId;
	}
	
	public void setProcessNodeId(Long processNodeId) {
		this.processNodeId = processNodeId;
	}
	
	public Long getProcessId() {
		return processId;
	}
	
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	
	public String getPreNodeCode() {
		return preNodeCode;
	}
	
	public void setPreNodeCode(String preNodeCode) {
		this.preNodeCode = preNodeCode;
	}
	
	public String getNextNodeCode() {
		return nextNodeCode;
	}
	
	public void setNextNodeCode(String nextNodeCode) {
		this.nextNodeCode = nextNodeCode;
	}

	public String getNodeEventImpl() {
		return nodeEventImpl;
	}

	public void setNodeEventImpl(String nodeEventImpl) {
		this.nodeEventImpl = nodeEventImpl;
	}

	public String getBeforeParameter() {
		return beforeParameter;
	}

	public void setBeforeParameter(String beforeParameter) {
		this.beforeParameter = beforeParameter;
	}

	public String getAfterParameter() {
		return afterParameter;
	}

	public void setAfterParameter(String afterParameter) {
		this.afterParameter = afterParameter;
	}

	public Integer getNodeState() {
		return nodeState;
	}
	
	public void setNodeState(Integer nodeState) {
		this.nodeState = nodeState;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public String getApprovalRoleId() {
		return approvalRoleId;
	}

	public void setApprovalRoleId(String approvalRoleId) {
		this.approvalRoleId = approvalRoleId;
	}

	public List<ProcessNodeBranch> getProcessNodeBranchListForAdd() {
		return processNodeBranchListForAdd;
	}

	public void setProcessNodeBranchListForAdd(
			List<ProcessNodeBranch> processNodeBranchListForAdd) {
		this.processNodeBranchListForAdd = processNodeBranchListForAdd;
	}

	public Long getFetchTime() {
		return fetchTime;
	}

	public void setFetchTime(Long fetchTime) {
		this.fetchTime = fetchTime;
	}

	public Integer getPassRequire() {
		return passRequire;
	}

	public void setPassRequire(Integer passRequire) {
		this.passRequire = passRequire;
	}

	public Integer getPassNum() {
		return passNum;
	}

	public void setPassNum(Integer passNum) {
		this.passNum = passNum;
	}

	public int getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(int approvalType) {
		this.approvalType = approvalType;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
   
}
