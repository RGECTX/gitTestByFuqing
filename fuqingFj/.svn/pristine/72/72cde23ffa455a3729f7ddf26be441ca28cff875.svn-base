/**
 * 
 */
package com.greathack.homlin.service.process;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.process.IApprovalItemDAO;
import com.greathack.homlin.daointerface.process.IProcessDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.process.ApprovalItem;
import com.greathack.homlin.pojo.process.Process;
import com.greathack.homlin.pojo.process.ProcessNode;
import com.greathack.homlin.pojo.process.ProcessNodeBranch;
import com.greathack.homlin.service.AdminPermissionService;
import com.greathack.homlin.serviceinterface.process.IApproval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author greathack
 *
 */
public class DefaultApproval implements IApproval {

    private static Logger logger = LoggerFactory.getLogger(DefaultApproval.class);

	//private IQjsqDAO qjsqDAO = (IQjsqDAO) DAOFactory.createDAO("IQjsqDAO");

	//private IProcessDAO processDAO = (IProcessDAO) DAOFactory.createDAO("IProcessDAO");
	
	//private IApprovalItemDAO approvalItemDAO = (IApprovalItemDAO) DAOFactory.createDAO("IApprovalItemDAO");

	//@Autowired
	//private IAdminPermissionService adminPermissionService;
	
	/* （非 Javadoc）
	 * @see com.greathack.police.service.IApproval#eventProcessStart(long, long)
	 */
	@Override
	public void eventProcessStart(long processId, long processNodeId) throws ServiceException {
		IProcessDAO processDAO = (IProcessDAO) DAOFactory.createDAO("IProcessDAO");
		//待审核用户
		List<String> pendingUserIdList= ProcessManage.getProcessNodePendingUsers(processNodeId);
		StringBuffer pendingUserIds=new StringBuffer();
		pendingUserIds.append("_");
		for(String userId:pendingUserIdList){
			pendingUserIds.append(userId);
			pendingUserIds.append("_");
		}
		//根据流程ID获取流程对象
		Process process=processDAO.findById(processId);
		process.setHandledUserIds("_");
		process.setPendingUserIds(pendingUserIds.toString());
		processDAO.update(process);
	}

	/* （非 Javadoc）
	 * @see com.greathack.police.service.IApproval#conditionAnalyze(long, com.greathack.police.approval.pojo.ProcessNodeBranch)
	 */
	@Override
	public boolean conditionAnalyze(long processId, ProcessNodeBranch processNodeBranch) {
		return true;
	}

	/* （非 Javadoc）
	 * @see com.greathack.police.service.IApproval#getUserList(long)
	 */
	@Override
	public List<String> getUserList(long processNodeId) {
		IProcessDAO processDAO = (IProcessDAO) DAOFactory.createDAO("IProcessDAO");
		List<String> userIdList=new ArrayList<String>();
		ProcessNode processNode= ProcessManage.getProcessNode(processNodeId);
		Process process=processDAO.findById(processNode.getProcessId());
		JSONObject varsJson=JSON.parseObject(process.getVars());
		String applyUserId=varsJson.getString("applyUserId");
		String orgCode=varsJson.getString("orgCode");
		String approvalRoleId=processNode.getApprovalRoleId();
		JSONObject json=JSON.parseObject(approvalRoleId);
		String roleId=json.getString("approvalRoleId");		

		AdminPermissionService adminPermissionService=new AdminPermissionService();
		if(roleId.equals("self")){//自己
			userIdList.add(applyUserId);
		}else{//角色
			try {
				userIdList=adminPermissionService.getUserIdsByRoleId(roleId);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}

		
		return userIdList;
	}

	/* （非 Javadoc）
	 * @see com.greathack.police.service.IApproval#processNodeItemPass(long, long, long)
	 */
	@Override
	public void processNodeItemPass(long processId, long processNodeId,
			long approvalItemId) throws ServiceException {
		IProcessDAO processDAO = (IProcessDAO) DAOFactory.createDAO("IProcessDAO");
		ProcessNode nextProcessNode= ProcessManage.getCurrentProcessNode(processId);
		if(nextProcessNode!=null){
			//已审核用户
			List<String> handledUserIdList= ProcessManage.getUserOfProcessIdHandled(processId);
			StringBuffer handledUserIds=new StringBuffer();
			handledUserIds.append("_");
			for(String userId:handledUserIdList){
				handledUserIds.append(userId);
				handledUserIds.append("_");
			}
			//待审核用户
			long nextProcessNodeId=nextProcessNode.getProcessNodeId();
			List<String> pendingUserIdList= ProcessManage.getProcessNodePendingUsers(nextProcessNodeId);
			StringBuffer pendingUserIds=new StringBuffer();
			pendingUserIds.append("_");
			for(String userId:pendingUserIdList){
				pendingUserIds.append(userId);
				pendingUserIds.append("_");
			}
			//根据流程ID获取流程对象
			Process process=processDAO.findById(processId);
			process.setHandledUserIds(handledUserIds.toString());
			process.setPendingUserIds(pendingUserIds.toString());
			processDAO.update(process);
		}
		
	}

	/* （非 Javadoc）
	 * @see com.greathack.police.service.IApproval#processNodeItemNoPass(long, long, long)
	 */
	@Override
	public void processNodeItemNoPass(long processId, long processNodeId,
			long approvalItemId) throws ServiceException {
		IProcessDAO processDAO = (IProcessDAO) DAOFactory.createDAO("IProcessDAO");
		ProcessNode nextProcessNode= ProcessManage.getCurrentProcessNode(processId);
		if(nextProcessNode!=null){
			//已审核用户
			List<String> handledUserIdList= ProcessManage.getUserOfProcessIdHandled(processId);
			StringBuffer handledUserIds=new StringBuffer();
			handledUserIds.append("_");
			for(String userId:handledUserIdList){
				handledUserIds.append(userId);
				handledUserIds.append("_");
			}
			//待审核用户
			long nextProcessNodeId=nextProcessNode.getProcessNodeId();
			List<String> pendingUserIdList= ProcessManage.getProcessNodePendingUsers(nextProcessNodeId);
			StringBuffer pendingUserIds=new StringBuffer();
			pendingUserIds.append("_");
			for(String userId:pendingUserIdList){
				pendingUserIds.append(userId);
				pendingUserIds.append("_");
			}
			//根据流程ID获取流程对象
			Process process=processDAO.findById(processId);
			process.setHandledUserIds(handledUserIds.toString());
			process.setPendingUserIds(pendingUserIds.toString());
			processDAO.update(process);
		}
	}

	/* （非 Javadoc）
	 * @see com.greathack.police.service.IApproval#processNodePass(long, long)
	 */
	@Override
	public void processNodePass(long processId, long processNodeId) throws ServiceException {
		IProcessDAO processDAO = (IProcessDAO) DAOFactory.createDAO("IProcessDAO");
		ProcessNode nextProcessNode= ProcessManage.getCurrentProcessNode(processId);
		if(nextProcessNode!=null){
			//已审核用户
			List<String> handledUserIdList= ProcessManage.getUserOfProcessIdHandled(processId);
			StringBuffer handledUserIds=new StringBuffer();
			handledUserIds.append("_");
			for(String userId:handledUserIdList){
				handledUserIds.append(userId);
				handledUserIds.append("_");
			}
			//待审核用户
			long nextProcessNodeId=nextProcessNode.getProcessNodeId();
			List<String> pendingUserIdList= ProcessManage.getProcessNodePendingUsers(nextProcessNodeId);
			StringBuffer pendingUserIds=new StringBuffer();
			pendingUserIds.append("_");
			for(String userId:pendingUserIdList){
				pendingUserIds.append(userId);
				pendingUserIds.append("_");
			}
			//根据流程ID获取流程对象
			Process process=processDAO.findById(processId);
			process.setHandledUserIds(handledUserIds.toString());
			process.setPendingUserIds(pendingUserIds.toString());
			processDAO.update(process);
		}
	}

	/* （非 Javadoc）
	 * @see com.greathack.police.service.IApproval#processNodeNoPass(long, long)
	 */
	@Override
	public void processNodeNoPass(long processId, long processNodeId) throws ServiceException {
		IProcessDAO processDAO = (IProcessDAO) DAOFactory.createDAO("IProcessDAO");
		ProcessNode nextProcessNode= ProcessManage.getCurrentProcessNode(processId);
		if(nextProcessNode!=null){
			//已审核用户
			List<String> handledUserIdList= ProcessManage.getUserOfProcessIdHandled(processId);
			StringBuffer handledUserIds=new StringBuffer();
			handledUserIds.append("_");
			for(String userId:handledUserIdList){
				handledUserIds.append(userId);
				handledUserIds.append("_");
			}
			//待审核用户
			long nextProcessNodeId=nextProcessNode.getProcessNodeId();
			List<String> pendingUserIdList= ProcessManage.getProcessNodePendingUsers(nextProcessNodeId);
			StringBuffer pendingUserIds=new StringBuffer();
			pendingUserIds.append("_");
			for(String userId:pendingUserIdList){
				pendingUserIds.append(userId);
				pendingUserIds.append("_");
			}
			//根据流程ID获取流程对象
			Process process=processDAO.findById(processId);
			process.setHandledUserIds(handledUserIds.toString());
			process.setPendingUserIds(pendingUserIds.toString());
			processDAO.update(process);
		}
	}

	/* （非 Javadoc）
	 * @see com.greathack.police.service.IApproval#processPass(long)
	 */
	@Override
	public void processPass(long processId) {
		IApprovalItemDAO approvalItemDAO = (IApprovalItemDAO) DAOFactory.createDAO("IApprovalItemDAO");
		IProcessDAO processDAO = (IProcessDAO) DAOFactory.createDAO("IProcessDAO");
		
		List<ApprovalItem> approvalItemList= ProcessManage.getApprovalItemListByProcessId(processId);
		ApprovalItem approvalItem=approvalItemList.get(approvalItemList.size()-1);
		if("同意上报审批".equals(approvalItem.getRemark()) || "".equals(approvalItem.getRemark())){
			approvalItem.setRemark("同意");
		}
		approvalItemDAO.update(approvalItem);
		
		//已审核用户
		List<String> handledUserIdList= ProcessManage.getUserOfProcessIdHandled(processId);
		StringBuffer handledUserIds=new StringBuffer();
		handledUserIds.append("_");
		for(String userId:handledUserIdList){
			handledUserIds.append(userId);
			handledUserIds.append("_");
		}
		//根据流程ID获取流程对象
		Process process=processDAO.findById(processId);
		process.setHandledUserIds(handledUserIds.toString());
		process.setPendingUserIds("_");
		processDAO.update(process);

	}

	/* （非 Javadoc）
	 * @see com.greathack.police.service.IApproval#processNoPass(long)
	 */
	@Override
	public void processNoPass(long processId) {
		IProcessDAO processDAO = (IProcessDAO) DAOFactory.createDAO("IProcessDAO");
		
		//已审核用户
		List<String> handledUserIdList= ProcessManage.getUserOfProcessIdHandled(processId);
		StringBuffer handledUserIds=new StringBuffer();
		handledUserIds.append("_");
		for(String userId:handledUserIdList){
			handledUserIds.append(userId);
			handledUserIds.append("_");
		}
		//根据流程ID获取流程对象
		Process process=processDAO.findById(processId);
		process.setHandledUserIds(handledUserIds.toString());
		process.setPendingUserIds("_");
		processDAO.update(process);

	}
	
	

}
