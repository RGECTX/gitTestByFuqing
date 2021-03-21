//Source file: G:\\业务逻辑\\权限管理系统\\src\\com\\wbaiju\\approvalProcess\\pojo\\ProcessNodeTemplate.java

package com.greathack.homlin.pojo.process;


/**
 * 流程节点模板
 */
public class ProcessNodeTemplate 
{
   
   /**
    * 流程节点模板ID
    */
   private Long processNodeId;
   
   /**
    * 节点编码
    */
   private String nodeCode;
   
   /**
    * 节点名称
    */
   private String nodeName;
   
   /**
	 * 审批角色ID
	 */
	private String approvalRoleId;
   
   /**
    * 所属流程模板
    */
   private Long processTemplateId;
   
   /**
    * 审批通过条件:1:按票数，2：一票否决
    */
   private int passRequire;
   
   /**
    * 通过票数:用于按票数审批，该节点有几个人通过，就算通过
    */
   private int passNum;

	/**
	 * 审批类型:1:审批（有通过，有不通过），2:采集（只有通过）
	 */
	private int approvalType;
   
   /**
    * 上一节点编码：起始节点用start
    */
   private String preNodeCode;
   
   /**
    * 下一节点编码:结束节点用end
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
    * 排序
    */
   private int sort;
   
   /**
    * @roseuid 571AD4290114
    */
   public ProcessNodeTemplate() 
   {
    
   }

	public Long getProcessNodeId() {
		return processNodeId;
	}
	
	public void setProcessNodeId(Long processNodeId) {
		this.processNodeId = processNodeId;
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

	public Long getProcessTemplateId() {
		return processTemplateId;
	}
	
	public void setProcessTemplateId(Long processTemplateId) {
		this.processTemplateId = processTemplateId;
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

	public int getPassRequire() {
		return passRequire;
	}

	public void setPassRequire(int passRequire) {
		this.passRequire = passRequire;
	}

	public int getPassNum() {
		return passNum;
	}

	public void setPassNum(int passNum) {
		this.passNum = passNum;
	}

	public int getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(int approvalType) {
		this.approvalType = approvalType;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
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

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
   
}
