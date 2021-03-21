//Source file: G:\\业务逻辑\\权限管理系统\\src\\com\\wbaiju\\approvalProcess\\pojo\\ProcessTemplate.java

package com.greathack.homlin.pojo.process;


/**
 * 审批流程模板
 */
public class ProcessTemplate 
{
   
   /**
    * 流程模板ID
    */
   private Long processTemplateId;
   
   /**
    * 模板编码
    */
   private String templateCode;
   
   /**
    * 模板类别
    */
   private String category;
   
   /**
    * 流程名称
    */
   private String processName;
   
   /**
    * 审批实现类Bean名称
    */
   private String approvalImpl;
   
   /**
    * 创建时间
    */
   private long createTime;
   
   /**
    * @roseuid 571AD429012D
    */
   public ProcessTemplate() 
   {
    
   }

	public Long getProcessTemplateId() {
		return processTemplateId;
	}
	
	public void setProcessTemplateId(Long processTemplateId) {
		this.processTemplateId = processTemplateId;
	}
	
	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProcessName() {
		return processName;
	}
	
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	public String getApprovalImpl() {
		return approvalImpl;
	}

	public void setApprovalImpl(String approvalImpl) {
		this.approvalImpl = approvalImpl;
	}

	public long getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}
