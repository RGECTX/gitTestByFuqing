package com.greathack.homlin.service.process;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.process.IProcessNodeBranchTemplateDAO;
import com.greathack.homlin.daointerface.process.IProcessNodeTemplateDAO;
import com.greathack.homlin.daointerface.process.IProcessTemplateDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.process.ProcessNodeBranchTemplate;
import com.greathack.homlin.pojo.process.ProcessNodeTemplate;
import com.greathack.homlin.pojo.process.ProcessTemplate;
import com.greathack.homlin.pojo.process.ProcessTemplateTemp;
import com.greathack.homlin.system.IdCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;


/** 
 *
 * @ClassName   类名：ProcessTemplateMannage 
 * @Description 功能说明：审批流程模板管理
 */
public class ProcessTemplateManage {
	
	private static Logger logger = LoggerFactory.getLogger(ProcessTemplateManage.class);
	private static IProcessTemplateDAO processTemplateDAO = (IProcessTemplateDAO) DAOFactory.createDAO("IProcessTemplateDAO");
	private static IProcessNodeTemplateDAO processNodeTemplateDAO = (IProcessNodeTemplateDAO) DAOFactory.createDAO("IProcessNodeTemplateDAO");
	private static IProcessNodeBranchTemplateDAO processNodeBranchTemplateDAO = (IProcessNodeBranchTemplateDAO) DAOFactory.createDAO("IProcessNodeBranchTemplateDAO");
	
	/**
     * <p>函数名称：      addProcessTemplate  </p>
     * <p>功能说明：新增审批流程模板
     *<p>参数说明：</p>
     * @param processName  模板名称
     * @param templateCode  模板编码
     * @return
     * 
     */
	public static ProcessTemplate addProcessTemplate(String processName, String templateCode, String category, String approvalImpl) throws ServiceException {
		if(processName==null || "".equals(processName)){
			throw new ServiceException(100073,"TEMPLATENAME_IS_EMPTY");
		}
		if(templateCode==null || "".equals(templateCode)){
			throw new ServiceException(100072,"TEMPLATECODE_IS_EMPTY");
		}
		ProcessTemplate processTemplate=new ProcessTemplate();
		processTemplate.setProcessTemplateId(IdCreator.createId("ProcessTemplate"));
		ProcessTemplate temp=processTemplateDAO.findByCode(templateCode);
		if(temp!=null){
			throw new ServiceException(100070,"TEMPLATECODE_EXIST");
		}
		processTemplate.setTemplateCode(templateCode);
		processTemplate.setProcessName(processName);
		processTemplate.setCategory(category);
		processTemplate.setApprovalImpl(approvalImpl);
		processTemplate.setCreateTime(Common.getCurrentTime());
		processTemplateDAO.add(processTemplate);
		return processTemplate;
	}

	/**
	 * <p>函数名称：      deleteProcessTemplate  </p>
	 * <p>功能说明：删除审批流程模板
	 *<p>参数说明：</p>
	 * @param processTemplateId  模板ID
	 * @return
	 *
	 */
	public static void deleteProcessTemplate(long processTemplateId){
		processTemplateDAO.delete(processTemplateId);
	}
	
	/**
	 * 修改审批流程模板
	 * @param processTemplate processTemplate对象
	 * @throws ServiceException
	 */
	public static void updateProcessTemplate(ProcessTemplate processTemplate) throws ServiceException {
		ProcessTemplate temp1=processTemplateDAO.findById(processTemplate.getProcessTemplateId());
		if(temp1==null){
			throw new ServiceException(100071,"PROCESS_TEMPLATE_NOT_EXIST");
		}
		if(processTemplate.getProcessName()==null || "".equals(processTemplate.getProcessName())){
			throw new ServiceException(100073,"TEMPLATENAME_IS_EMPTY");
		}
		if(processTemplate.getTemplateCode()==null || "".equals(processTemplate.getTemplateCode())){
			throw new ServiceException(100072,"TEMPLATECODE_IS_EMPTY");
		}
		temp1.setProcessName(processTemplate.getProcessName());
		if(!temp1.getTemplateCode().equals(processTemplate.getTemplateCode())){
			ProcessTemplate temp2=processTemplateDAO.findByCode(processTemplate.getTemplateCode());
			if(temp2!=null){
				throw new ServiceException(100070,"TEMPLATECODE_EXIST");
			}
			temp1.setTemplateCode(processTemplate.getTemplateCode());
		}
		temp1.setApprovalImpl(processTemplate.getApprovalImpl());
		temp1.setCategory(processTemplate.getCategory());
		processTemplateDAO.update(temp1);
		
	}
	
	/**
	 * 获取流程模板
	 * @return
	 */
	public static ProcessTemplate getProcessTemplate(Long id){
		return processTemplateDAO.findById(id);
	}
	
	/**
	 * 获取所有的流程模板列表
	 * @return
	 */
	public static List<ProcessTemplate> getAllProcessTemplate(){
		return processTemplateDAO.findAll();
	}
    
	/**
	 * 新增审批流程节点模板
	 * @param processTemplateId 流程模板Id
	 * @param passRequire 审批通过条件:1:按票数，2：一票否决
	 * @param passNum 通过票数:用于按票数审批，该节点有几个人通过，就算通过，如果票数设得比审批人数多，那么就按一票否决来处理
	 * @param approvalRoleId 审批角色ID
	 * @param nodeName 节点名称
	 * @param nodeEventImpl 节点事件实现类Bean名称
	 * @param beforeParameter 前置事件参数
	 * @param afterParameter 后置事件参数
	 */
	public static ProcessNodeTemplate addProcessNodeTemplate(long processTemplateId, int passRequire, int passNum, int approvalType, String approvalRoleId, String nodeName, String nodeEventImpl, String beforeParameter, String afterParameter ) throws ServiceException {
		ProcessTemplateTemp processTemplateTemp=new ProcessTemplateTemp();
		processTemplateTemp.setProcessTemplateId(processTemplateId);
		
		ProcessNodeTemplate pntInstance=new ProcessNodeTemplate();
		pntInstance.setProcessTemplateId(processTemplateId);
		List<ProcessNodeTemplate> processNodeTemplates=processNodeTemplateDAO.findByExample(pntInstance);
		
		ProcessNodeTemplate processNodeTemplate=new ProcessNodeTemplate();//新的节点模板
		processNodeTemplate.setProcessNodeId(IdCreator.createId("ProcessNodeTemplate"));
		processNodeTemplate.setProcessTemplateId(processTemplateId);
		processNodeTemplate.setApprovalRoleId(approvalRoleId);
		processNodeTemplate.setPassRequire(passRequire);
		processNodeTemplate.setPassNum(passNum);
		processNodeTemplate.setApprovalType(approvalType);
		processNodeTemplate.setNodeName(nodeName);
		processNodeTemplate.setNodeCode(UUID.randomUUID().toString());
		if(processNodeTemplates.size()==0){//还没添加过节点	
			processNodeTemplate.setPreNodeCode("start");//第一个加的节点的开始结点
		}else{
			processNodeTemplate.setPreNodeCode("");//除了第一个节点，其他都不用设置前节点
		}		
		processNodeTemplate.setNextNodeCode("end");//每个节点新增加的时候都是结束节点
		processNodeTemplate.setNodeEventImpl(nodeEventImpl);
		processNodeTemplate.setBeforeParameter(beforeParameter);
		processNodeTemplate.setAfterParameter(afterParameter);
		processNodeTemplateDAO.add(processNodeTemplate);//新增节点模板
		return processNodeTemplate;
		
	}


	/**
	 * <p>函数名称：      deleteProcessNodeTemplate  </p>
	 * <p>功能说明：删除流程节点模板
	 *<p>参数说明：</p>
	 * @param processNodeTemplateId  流程节点模板ID
	 * @return
	 *
	 */
	public static void deleteProcessNodeTemplate(long processNodeTemplateId){
		processNodeTemplateDAO.delete(processNodeTemplateId);
	}
	
	/**
	 * 获取的流程节点模板列表
	 * @return
	 */
	public static List<ProcessNodeTemplate> getProcessNodeTemplateList(long processTemplateId){
		ProcessNodeTemplate cond=new ProcessNodeTemplate();
		cond.setProcessTemplateId(processTemplateId);
		return processNodeTemplateDAO.findByExample(cond);
	}
	
	/**
	 * 添加流程节点分支条件模板
	 * @param processNodeTemplateId 节点模板ID
	 * @param condition 条件
	 * @param nextNodeCode 下一节点编码
	 */
	public static ProcessNodeBranchTemplate addProcessNodeBranchTemplate(long processNodeTemplateId, String condition, String nextNodeCode) throws ServiceException {
		ProcessNodeBranchTemplate processNodeBranchTemplate=new ProcessNodeBranchTemplate();
		processNodeBranchTemplate.setBranchId(IdCreator.createId("ProcessNodeBranchTemplate"));
		processNodeBranchTemplate.setCond(condition);
		processNodeBranchTemplate.setNextNodeCode(nextNodeCode);
		processNodeBranchTemplate.setProcessNodeId(processNodeTemplateId);
		
		processNodeBranchTemplateDAO.add(processNodeBranchTemplate);
		return processNodeBranchTemplate;
	}


	/**
	 * <p>函数名称：      deleteProcessNodeTemplate  </p>
	 * <p>功能说明：删除流程节点分支模板
	 *<p>参数说明：</p>
	 * @param processNodeBranchTemplateId  流程节点分支模板ID
	 * @return
	 *
	 */
	public static void deleteProcessNodeBranchTemplate(long processNodeBranchTemplateId){
		processNodeBranchTemplateDAO.delete(processNodeBranchTemplateId);
	}

	
	/**
	 * 获取的流程节点分支模板列表
	 * @return
	 */
	public static List<ProcessNodeBranchTemplate> getProcessNodeBranchTemplateList(long processNodeId){
		ProcessNodeBranchTemplate cond=new ProcessNodeBranchTemplate();
		cond.setProcessNodeId(processNodeId);
		return processNodeBranchTemplateDAO.findByExample(cond);
	}

	/**
	 * 通过nodeCode获得节点名称对象
	 * @return
	 */
	public static ProcessNodeTemplate findByNodeCode(String nodeCode){
		return processNodeTemplateDAO.findByNodeCode(nodeCode);
	}

	/**
	 * 获取的流程节点分支模板列表
	 * @return
	 */
	public static ProcessNodeBranchTemplate getProcessNodeBranchTemplate(Long processNodeBranchTemplateId) {
		return processNodeBranchTemplateDAO.findById(processNodeBranchTemplateId);
	}

	/**
	 * 修改的流程节点分支模板列表
	 * @return
	 */
	public static void updateProcessNodeBranchTemplate(ProcessNodeBranchTemplate processNodeBranchTemplate) {
		processNodeBranchTemplateDAO.update(processNodeBranchTemplate);
	}

	/**
	 * 修改的流程节点模板
	 * @return
	 */
	public static void updateProcessNodeTemplate(ProcessNodeTemplate processNodeTemplate){
		processNodeTemplateDAO.update(processNodeTemplate);
	}

	/**
	 * 获取的流程节点模板
	 * @return
	 */
	public static ProcessNodeTemplate getProcessNodeTemplate(long processNodeId){
		return processNodeTemplateDAO.findById(processNodeId);
	}
}
