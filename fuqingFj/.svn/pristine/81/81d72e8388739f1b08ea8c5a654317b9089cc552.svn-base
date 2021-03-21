package com.greathack.homlin.service.process;

import com.alibaba.druid.support.json.JSONUtils;
import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.common.SpringContextHolder;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.process.*;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.process.Process;
import com.greathack.homlin.pojo.process.*;
import com.greathack.homlin.serviceinterface.process.IApproval;
import com.greathack.homlin.serviceinterface.process.INodeEvent;
import com.greathack.homlin.system.IdCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName 类名：ProcessManage
 * @Description 功能说明：审批流程管理
 */
public class ProcessManage {
    public final static Integer PROCESS_NODE_APPROVAL_TYPE_BOTH = 1;
    public final static Integer PROCESS_NODE_APPROVAL_TYPE_ONLY_PASS = 2;

    private static Logger logger = LoggerFactory.getLogger(ProcessManage.class);
    private static IProcessDAO processDAO = (IProcessDAO) DAOFactory.createDAO("IProcessDAO");
    private static IProcessNodeDAO processNodeDAO = (IProcessNodeDAO) DAOFactory.createDAO("IProcessNodeDAO");
    private static IApprovalItemDAO approvalItemDAO = (IApprovalItemDAO) DAOFactory.createDAO("IApprovalItemDAO");
    private static IProcessNodeBranchDAO processNodeBranchDAO = (IProcessNodeBranchDAO) DAOFactory.createDAO("IProcessNodeBranchDAO");
    private static IProcessNodeBranchTemplateDAO tempBranchDAO = (IProcessNodeBranchTemplateDAO) DAOFactory.createDAO("IProcessNodeBranchTemplateDAO");
    private static IProcessNodeTemplateDAO processNodeTemplateDAO = (IProcessNodeTemplateDAO) DAOFactory.createDAO("IProcessNodeTemplateDAO");
    private static IProcessTemplateDAO processTemplateDAO = (IProcessTemplateDAO) DAOFactory.createDAO("IProcessTemplateDAO");

    /**
     * 获取审批流程详情
     *
     * @param processId 审批流程ID
     * @return 审批流程详情对象
     */
    public static ProcessDetail getProcessDetail(long processId) {
        ProcessDetail processDetail = new ProcessDetail();
        Process process = processDAO.findById(processId);
        processDetail.setProcessId(process.getProcessId());
        processDetail.setProcessTemplateId(process.getProcessTemplateId());
        processDetail.setProcessState(process.getProcessState());
        processDetail.setCreateTime(process.getCreateTime());
        processDetail.setEndTime(process.getEndTime());
        processDetail.setVars(process.getVars());
        processDetail.setPendingUserIds(process.getPendingUserIds());
        processDetail.setHandledUserIds(process.getHandledUserIds());
        processDetail.setApprovalImpl(process.getApprovalImpl());


        ProcessNode condition = new ProcessNode();
        condition.setProcessId(processId);
        List<ProcessNode> processNodes = processNodeDAO.findByExample(condition);


        List<ProcessNodeDetail> processNodeDetails = new ArrayList<ProcessNodeDetail>();
        for (ProcessNode processNode : processNodes) {
            ProcessNodeDetail processNodeDetail = new ProcessNodeDetail();
            ApprovalItem aiInstance = new ApprovalItem();
            aiInstance.setProcessNodeId(processNode.getProcessNodeId());
            List<ApprovalItem> approvalItems = approvalItemDAO.findByExample(aiInstance);
            processNodeDetail.setApprovalItemList(approvalItems);
            processNodeDetail.setApprovalRoleId(processNode.getApprovalRoleId());
            processNodeDetail.setFetchTime(processNode.getFetchTime());
            processNodeDetail.setEndTime(processNode.getEndTime());
            processNodeDetail.setNextNodeCode(processNode.getNextNodeCode());
            processNodeDetail.setNodeCode(processNode.getNodeCode());
            processNodeDetail.setNodeState(processNode.getNodeState());
            processNodeDetail.setPassNum(processNode.getPassNum());
            processNodeDetail.setApprovalType(processNode.getApprovalType());
            processNodeDetail.setPassRequire(processNode.getPassRequire());
            processNodeDetail.setPreNodeCode(processNode.getPreNodeCode());
            processNodeDetail.setNodeEventImpl(processNode.getNodeEventImpl());
            processNodeDetail.setBeforeParameter(processNode.getBeforeParameter());
            processNodeDetail.setAfterParameter(processNode.getAfterParameter());
            processNodeDetail.setProcessId(processId);
            processNodeDetail.setProcessNodeId(processNode.getProcessNodeId());
            processNodeDetails.add(processNodeDetail);
        }
        processDetail.setProcessNodeDetailList(processNodeDetails);
        return processDetail;
    }

    /**
     * <p>函数名称：      addProcess  </p>
     * <p>功能说明：应用审批流程模板
     * <p>参数说明：</p>
     *
     * @param processTemplateId 流程模板ID
     * @return vars  变量
     */
    public static Process addProcess(long processTemplateId, Map<String, String> vars) throws ServiceException {
        ProcessTemplate processTemplate = processTemplateDAO.findById(processTemplateId);
        ProcessNode startProcessNode = null;
        Process process = new Process();
        process.setProcessId(IdCreator.createId("Process"));
        process.setProcessTemplateId(processTemplateId);
        process.setProcessState(1);
        process.setCreateTime(System.currentTimeMillis());
        if (vars != null) {
            process.setVars(JSONUtils.toJSONString(vars));
        }
        process.setApprovalImpl(processTemplate.getApprovalImpl());
        //新增审批流程
        //int processId = processDAO.saveProcess(process);
        //List<ProcessNode> processNodeListForAdd=new ArrayList<ProcessNode>();
        ProcessNodeTemplate pntInstance = new ProcessNodeTemplate();
        pntInstance.setProcessTemplateId(processTemplateId);
        List<ProcessNodeTemplate> nodeList = processNodeTemplateDAO.findByExample(pntInstance);
        if (nodeList.size() == 0) {
            throw new ServiceException(200002, "流程不存在");
        }
        //复制新增流程节点模板到流程节点
        for (ProcessNodeTemplate node : nodeList) {
            ProcessNode processNode = new ProcessNode();
            processNode.setProcessNodeId(IdCreator.createId("processNode"));
            processNode.setProcessId(process.getProcessId());
            processNode.setNodeCode(node.getNodeCode());
            processNode.setApprovalRoleId(node.getApprovalRoleId());
            processNode.setPreNodeCode(node.getPreNodeCode());
            processNode.setNextNodeCode(node.getNextNodeCode());
            processNode.setPassRequire(node.getPassRequire());
            processNode.setPassNum(node.getPassNum());
            processNode.setApprovalType(node.getApprovalType());
            processNode.setNodeEventImpl(node.getNodeEventImpl());
            processNode.setBeforeParameter(node.getBeforeParameter());
            processNode.setAfterParameter(node.getAfterParameter());
            processNode.setSort(node.getSort());
            //起始节点审核状态设为 2 等待审批    其他节点为 1 未到达
            if ("start".equals(node.getPreNodeCode())) {
                processNode.setNodeState(2);
                processNode.setFetchTime(System.currentTimeMillis());
                startProcessNode = processNode;
            } else {
                processNode.setNodeState(1);
            }
            //添加分支条件
            ProcessNodeBranchTemplate pnbtInstance = new ProcessNodeBranchTemplate();
            pnbtInstance.setProcessNodeId(node.getProcessNodeId());
            List<ProcessNodeBranchTemplate> nodeBranchList = tempBranchDAO.findByExample(pnbtInstance);
            for (ProcessNodeBranchTemplate nodeBranch : nodeBranchList) {
                ProcessNodeBranch processNodeBranch = new ProcessNodeBranch();
                processNodeBranch.setBranchId(IdCreator.createId("ProcessNodeBranch"));
                processNodeBranch.setProcessNodeId(processNode.getProcessNodeId());
                processNodeBranch.setCond(nodeBranch.getCond());
                processNodeBranch.setNextNodeCode(nodeBranch.getNextNodeCode());
                processNodeBranch.setSort(nodeBranch.getSort());
                processNodeBranchDAO.add(processNodeBranch);
            }
            processNodeDAO.add(processNode);
        }

        processDAO.add(process);
        /*getApproval(process.getApprovalImpl()).eventProcessStart(process.getProcessId(), startProcessNode.getProcessNodeId());*/
        try {
            getApproval(process.getApprovalImpl()).eventProcessStart(process.getProcessId(), startProcessNode.getProcessNodeId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        INodeEvent nodeEvent = getNodeEvent(startProcessNode.getNodeEventImpl());
        if (nodeEvent != null) {
            nodeEvent.before(startProcessNode.getBeforeParameter(), startProcessNode);
        }
        return process;
    }

    //获取审批实体类
    private static IApproval getApproval(String approvalBeanName) {
        if (approvalBeanName == null) {
            return (IApproval) SpringContextHolder.getBean("DefaultApproval");
        }
        try {
            return (IApproval) SpringContextHolder.getBean(approvalBeanName);
        } catch (NoSuchBeanDefinitionException e) {
            return (IApproval) SpringContextHolder.getBean("DefaultApproval");
        }
    }

    //获取节点事件实体类
    private static INodeEvent getNodeEvent(String nodeEventBeanName) {
        if (nodeEventBeanName == null) {
            return null;
        }
        try {
            return (INodeEvent) SpringContextHolder.getBean(nodeEventBeanName);
        } catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }


    /**
     * <p>函数名称：      addProcess  </p>
     * <p>功能说明：应用审批流程模板
     * <p>参数说明：</p>
     *
     * @param templateCode 流程模板编码
     * @return vars  变量
     */
    public static Process addProcess(String templateCode, Map<String, String> vars) throws ServiceException {
        ProcessTemplate processTemplate = processTemplateDAO.findByCode(templateCode);
        return addProcess(processTemplate.getProcessTemplateId(), vars);
    }

    /**
     * <p>函数名称：       审批人对审批项目审批通过 </p>
     * </p>
     * <p>参数说明：</p>
     *
     * @param processId 审批流程ID
     * @param remark    审批说明
     */
    public static void ApprovalItemPass(long processId, String remark, String userId) throws ServiceException {
        ProcessNode processNode = getCurrentProcessNode(processId);//获取当前正在审批的节点
        if (processNode == null) {//审批已通过或审批流程不存在
            throw new ServiceException(200004, "审批已通过或审批流程不存在");
            //return;
        }

		if (!getProcessNodePendingUsers(processNode.getProcessNodeId()).contains(userId)) {//没权限审批
            throw new ServiceException(200005, "没权限审批");
            //return;
        }

        //创建节点审批项目，并设为审批通过
        ApprovalItem approvalItem = new ApprovalItem();
        approvalItem.setApprovalItemId(IdCreator.createId("ApprovalItem"));
        approvalItem.setProcessId(processId);
        approvalItem.setProcessNodeId(processNode.getProcessNodeId());
        approvalItem.setNodeCode(processNode.getNodeCode());
        approvalItem.setUserId(userId);
        approvalItem.setRemark(remark);
        approvalItem.setCreateTime(System.currentTimeMillis());
        approvalItem.setUpdateTime(System.currentTimeMillis());
        approvalItem.setItemState(4);//设为审批通过
        approvalItemDAO.add(approvalItem);

        Process process = processDAO.findById(processId);
        IApproval approval = getApproval(process.getApprovalImpl());
        approval.processNodeItemPass(processId, processNode.getProcessNodeId(), approvalItem.getApprovalItemId());//节点项目审批通过处理


        //System.out.println(isProcessNodePass(processNode.getProcessNodeId(),userId));
        if (isProcessNodePass(processNode.getProcessNodeId(), userId)) {    //流程节点审核完全通过
            processNode.setNodeState(4);//流程节点设为审批通过
            processNode.setEndTime(System.currentTimeMillis());
            //approvalItemStateData.setProcessNode(processNode);
            processNodeDAO.update(processNode);
            //通过分支条件设置下一个节点--开始
            ProcessNodeBranch pnbInstance = new ProcessNodeBranch();
            pnbInstance.setProcessNodeId(processNode.getProcessNodeId());
            List<ProcessNodeBranch> processNodeBranchs = processNodeBranchDAO.findByExample(pnbInstance);
            for (ProcessNodeBranch processNodeBranch : processNodeBranchs) {
                if (approval.conditionAnalyze(processId, processNodeBranch)) {
                    if (processNodeBranch.getNextNodeCode().equals("end")) {
                        break;
                    }
                    processNode.setNextNodeCode(processNodeBranch.getNextNodeCode());//在本节点中设置下一个节点编码
                    //approvalItemStateData.setProcessNode(processNode);
                    processNodeDAO.update(processNode);

                    ProcessNode instance = new ProcessNode();//查询条件
                    instance.setProcessId(processNode.getProcessId());
                    instance.setNodeCode(processNode.getNextNodeCode());
                    ProcessNode nextProcessNode = processNodeDAO.findByExample(instance).get(0);//获取下一个节点
                    nextProcessNode.setPreNodeCode(processNode.getNodeCode());//设置下个节点的前节点
                    //approvalItemStateData.setNextProcessNode(nextProcessNode);
                    processNodeDAO.update(nextProcessNode);
                    break;//系统只选择第一个符合条件的分支,所有设置分支的时候要设置不会同时为真的分支
                }
            }
            //通过分支条件设置下一个节点--结束

            if (processNode.getNextNodeCode().equals("end")) {//如果该流程节点是最后一个节点     审核通过   则整个审批流程审核通过
                process.setProcessState(2);//审批流程通过
                process.setEndTime(System.currentTimeMillis());
                //approvalItemStateData.setProcess(process);
                processDAO.update(process);//整个流程设为审批通过
                approval.processPass(processNode.getProcessId());//流程审批通过处理

            } else {//如果存在下个流程节点，则下个流程节点进入待审核状态
                ProcessNode instance = new ProcessNode();//查询条件
                instance.setProcessId(processNode.getProcessId());
                instance.setNodeCode(processNode.getNextNodeCode());
                ProcessNode nextProcessNode = processNodeDAO.findByExample(instance).get(0);//获取下一个节点
                nextProcessNode.setNodeState(2);//下个流程节点设为待审批
                nextProcessNode.setFetchTime(Common.getCurrentTime());//设置当前时间为到达时间
                //approvalItemStateData.setNextProcessNode(nextProcessNode);
                processNodeDAO.update(nextProcessNode);
                INodeEvent nodeEvent = getNodeEvent(nextProcessNode.getNodeEventImpl());
                if (nodeEvent != null) {
                    nodeEvent.before(nextProcessNode.getBeforeParameter(), nextProcessNode);
                }
            }

            approval.processNodePass(processId, processNode.getProcessNodeId());//节点审批通过处理
            INodeEvent nodeEvent = getNodeEvent(processNode.getNodeEventImpl());
            if (nodeEvent != null) {
                nodeEvent.after(processNode.getAfterParameter(), processNode, true);
            }
        }
    }

    /**
     * <p>函数名称：       审批人对审批项目审批不通过 </p>
     * </p>
     * <p>参数说明：</p>
     *
     * @param processId 审批流程ID
     * @param remark    审批说明
     */
    public static void ApprovalItemNoPass(long processId, String remark, String userId) throws ServiceException {
        ProcessNode processNode = getCurrentProcessNode(processId);//获取当前正在审批的节点
        if(processId==0){
            throw new ServiceException(200006, "未提交申请无法进行审批驳回操作");
        }
        if (processNode == null) {//审批已通过或审批流程不存在
            throw new ServiceException(200004, "审批已通过或审批流程不存在");
            //return;
        }
        if (processNode.getApprovalType() == PROCESS_NODE_APPROVAL_TYPE_ONLY_PASS) {
            throw new ServiceException(200006, "采集节点不能做不通过操作");
        }
        List<String> pendingUsers = getProcessNodePendingUsers(processNode.getProcessNodeId());//未审批用户
        if (!pendingUsers.contains(userId)) {//没权限审批
            throw new ServiceException(200005, "没权限审批");
            //return;
        }
        ApprovalItem approvalItem = new ApprovalItem();
        approvalItem.setApprovalItemId(IdCreator.createId("ApprovalItem"));
        approvalItem.setProcessId(processId);
        approvalItem.setProcessNodeId(processNode.getProcessNodeId());
        approvalItem.setNodeCode(processNode.getNodeCode());
        approvalItem.setUserId(userId);
        approvalItem.setRemark(remark);
        approvalItem.setCreateTime(System.currentTimeMillis());
        approvalItem.setUpdateTime(System.currentTimeMillis());
        approvalItem.setItemState(8);//审批项目审批不通过
        approvalItemDAO.add(approvalItem);

        Process process = processDAO.findById(processId);
        IApproval approval = getApproval(process.getApprovalImpl());
        approval.processNodeItemNoPass(processId, processNode.getProcessNodeId(), approvalItem.getApprovalItemId());//节点项目审批不通过处理
        INodeEvent nodeEvent = getNodeEvent(processNode.getNodeEventImpl());
        if (nodeEvent != null) {
            nodeEvent.after(processNode.getAfterParameter(), processNode, false);
        }


        if (processNode.getPassRequire() == 3 || processNode.getPassRequire() == 2 || (pendingUsers.size() == 1 && pendingUsers.get(0).equals(userId))) {
            //一票否决 （2和3的状态）
            //或者 如果只剩一个人未审批，这个人还恰好是正在审批不通过，那就整个节点不通过
            //因为如果审批人数达到了要求，审批流程就直接通过了，不会再给这个人审批机会


            //流程节点审核不通过
            processNode.setNodeState(8);
            processNode.setEndTime(System.currentTimeMillis());
            //approvalItemStateData.setProcessNode(processNode);
            processNodeDAO.update(processNode);
            approval.processNodeNoPass(processId, processNode.getProcessNodeId());//节点审批不通过处理
            //审批流程不通过
            process.setProcessState(4);
            process.setEndTime(System.currentTimeMillis());
            processDAO.update(process);
            approval.processNoPass(processNode.getProcessId());//流程审批不通过处理
        }
    }

    /**
     * 获取审批流程当前正在审批的审批节点对象
     *
     * @param processId 审批流程ID
     * @return 返回当前审批节点对象，审批已通过或审批流程不存在或审批流程已结束返回null
     */
    public static ProcessNode getCurrentProcessNode(long processId) {
        ProcessNode condition = new ProcessNode();//查询条件
        condition.setProcessId(processId);
        condition.setNodeState(2);//节点状态为等待审批
        List<ProcessNode> processNodes = processNodeDAO.findByExample(condition);
        if (processNodes.size() == 1) {
            return processNodes.get(0);
        }
        return null;
    }

    /**
     * 获取审批节点已审批过的用户数组（这些人不一定是审批通过）
     *
     * @param processNodeId 审批节点ID
     * @return 返回节点已审批过的用户，审批节点还未有人审批或审批节点不存在返回null
     */
    public static ArrayList<String> getProcessNodeApprovalUsers(long processNodeId) {
        ApprovalItem aiInstance = new ApprovalItem();
        aiInstance.setProcessNodeId(processNodeId);
        List<ApprovalItem> approvalItems = approvalItemDAO.findByExample(aiInstance);
        ArrayList<String> a1 = new ArrayList<String>();
        if (approvalItems.size() > 0) {
            for (int i = 0; i < approvalItems.size(); i++) {
                a1.add(approvalItems.get(i).getUserId());
            }
        }
        return a1;
    }

    /**
     * 获取审批节点已审批通过的用户数组（一定是已审批通过）
     *
     * @param processNodeId 审批节点ID
     * @return 返回节点已审批通过的用户，审批节点还未有审批通过的用户或审批节点不存在返回null
     */
    public static ArrayList<String> getProcessNodePassUsers(long processNodeId) {
        ApprovalItem approvalItem = new ApprovalItem();
        approvalItem.setProcessNodeId(processNodeId);
        approvalItem.setItemState(4);//审批通过
        List<ApprovalItem> approvalItems = approvalItemDAO.findByExample(approvalItem);
        ArrayList<String> a1 = new ArrayList<String>();
        if (approvalItems.size() > 0) {
            for (int i = 0; i < approvalItems.size(); i++) {
                a1.add(approvalItems.get(i).getUserId());
            }
        }
        return a1;
    }

    /**
     * 通过流程节点ID获取流程节点对象
     *
     * @param processNodeId 流程节点ID
     * @return 流程节点对象
     */
    public static ProcessNode getProcessNode(long processNodeId) {
        return processNodeDAO.findById(processNodeId);
    }


    /**
     * 通过节点审批项目ID获取节点审批项目对象
     *
     * @param approvalItemId 审批项目ID
     * @return 流程节点对象
     */
    public static ApprovalItem getApprovalItem(long approvalItemId) {
        return approvalItemDAO.findById(approvalItemId);
    }

    /**
     * 审批节点在当前用户审批通过后是否完成并通过
     *
     * @param processNodeId 审批节点ID
     * @param userId        正在审批的用户ID
     * @return 审批通过返回true，审批还未完成或未通过返回false
     */
    private static boolean isProcessNodePass(long processNodeId, String userId) throws ServiceException {
        ProcessNode processNode = processNodeDAO.findById(processNodeId);
        List<String> pendingUsers = getProcessNodePendingUsers(processNodeId);//还未审批的用户
        Process process = processDAO.findById(processNode.getProcessId());
        IApproval approval = getApproval(process.getApprovalImpl());
        if (processNode.getPassRequire() == 2) {//一票否决
            if (pendingUsers.size() == 1 && pendingUsers.get(0).equals(userId)) {    //流程节点审核完全通过
                //未审批的用户只有一个，而且这个人还审批通过
                return true;
            } else {
                return false;
            }
        } else {//按票数多少通过
            List<String> a1 = approval.getUserList(processNodeId);//有权限审批该节点的用户Id的数组
            if (processNode.getPassNum() > a1.size()) {//如果需要通过票数设得比审批人数多，那么就按一票否决来处理
                if (pendingUsers.size() == 1 && pendingUsers.get(0).equals(userId)) {    //流程节点审核完全通过
                    return true;
                } else {
                    return false;
                }
            } else {
                ArrayList<String> a2 = getProcessNodePassUsers(processNodeId);
                if (processNode.getPassNum() <= a2.size() + 1) {//通过人数达到了，直接就通过了
                    return true;
                } else {
                    return false;
                }
            }
			
			/*ArrayList<String> a2=getProcessNodePassUsers(processNodeId);
			a2.add(userId);
			if(processNode.getPassNum()<=a2.size()){//通过人数达到了，直接就通过了
				return true;
			}else{//通过人数没达到，就看是不是所有有权限审批的人是不是都审批通过
				IApproval approval = (IApproval) ApprovalFactory.create("IApproval");
				ArrayList<String> a1=approval.getUserList(processNodeId);//有权限审批该节点的用户Id的数组
				for(String userId1:a1){//所有有权限审批的人，在审批项目通过的人里都找得到，这个节点就算审批通过
					boolean isEquals=false;
					for(String userId2:a2){
						if(userId1.equals(userId2)){//这个有权限审批的人，在审批节点项目通过的人里能找到
							isEquals=true;
							break;//有相同的退出这一轮循环
						}
					}
					if(isEquals==false){//这一轮没有相同的，意味着还有人没审批
						return false;
					}
				}
				return true;
			}*/
        }
    }

    /**
     * 审批节点是否完成并通过
     * @param processNodeId 审批节点ID
     * @return 审批通过返回true，审批还未完成返回false
     */
	/*private static boolean isProcessNodePass(long processNodeId) throws ProcessNodeNotExistException{
		IProcessNodeDAO processNodeDAO = (IProcessNodeDAO) ApprovalProcessDAOFactory.createDAO("IProcessNodeDAO");
		ProcessNode processNode=processNodeDAO.findById(processNodeId);
		if(processNode==null){
			throw new ProcessNodeNotExistException();
		}else{
			if(processNode.getNodeState()==4){
				return true;
			}
		}
		IApprovalItemDAO approvalItemDAO = (IApprovalItemDAO) ApprovalProcessDAOFactory.createDAO("IApprovalItemDAO");
		IApproval approval = (IApproval) ApprovalFactory.create("IApproval");
		String[] a1=approval.getUserList(processNodeId);//有权限审批该节点的用户Id的数组
		List<ApprovalItem> approvalItems=approvalItemDAO.findByProcessNodeId(processNodeId);
		if(approvalItems.size()>0){
			String[] a2=new String[approvalItems.size()];//本节点已经审批通过的用户Id的数组
			for(int i=0;i<approvalItems.size();i++){
				a2[i]=approvalItems.get(i).getUserId();
			}
			for(String userId1:a1){//所有有权限审批的人，在审批项目通过里都找得到，这个节点就算审批通过
				boolean isEquals=false;
				for(String userId2:a2){
					if(userId1.equals(userId2)){//这个有权限审批的人，在审批节点项目通过里能找到
						isEquals=true;
						break;//有相同的退出这一轮循环
					}
				}
				if(isEquals==false){//这一轮没有相同的，意味着审批节点还没完全通过
					return false;
				}
			}
			return true;
		}else{
			return false;
		}
	}*/

    /**
     * 获取审批节点还未审批的用户ID数组
     *
     * @param processNodeId 审批节点ID
     * @return 审批节点还未审批的用户ID数组
     */
    public static List<String> getProcessNodePendingUsers(long processNodeId) throws ServiceException {
        ProcessNode processNode = processNodeDAO.findById(processNodeId);
        Process process = processDAO.findById(processNode.getProcessId());
        IApproval approval = getApproval(process.getApprovalImpl());
        ArrayList<String> pendingUsers = new ArrayList<String>();//未审批用户列表
        List<String> a1 = approval.getUserList(processNodeId);//有权限审批该节点的用户Id的数组
        if (a1.size() == 0) {
            return a1;
        }

        ArrayList<String> a2 = getProcessNodeApprovalUsers(processNodeId);//本节点已经审批过的用户Id的数组
        if (a2.size() > 0) {
            for (String userId1 : a1) {//所有有权限审批的人，在审批过的人里都找得到，这个节点就算审批通过
                if (!a2.contains(userId1)) {//userId1没有被包含，意味着审批节点还没被所有的人审批过
                    pendingUsers.add(userId1);
                }
				/*boolean isEquals=false;
				for(String userId2:a2){
					if(userId1.equals(userId2)){//这个有权限审批的人，在审批过的人里能找到
						isEquals=true;
						break;//有相同的退出这一轮循环
					}
				}
				if(isEquals==false){//这一轮没有相同的，意味着审批节点还没被所有的人审批过
					pendingUsers.add(userId1);
				}*/
            }
            return pendingUsers;
        } else {
            return a1;
        }
    }


    /**
     * 获取指定模板用户当前正在审批的流程列表
     *
     * @param processTemplateId 指定的模板ID
     * @param userId            审批用户
     * @return 流程列表
     */
    public static List<Process> getProcessListByProcessTemplateId(long processTemplateId, String userId) throws ServiceException {
        List<Process> processes = new ArrayList<Process>();
        Process condition = new Process();
        condition.setProcessTemplateId(processTemplateId);
        condition.setProcessState(1);//审批中
        List<Process> result = processDAO.findByExample(condition);//获取到审批中的流程
        for (Process process : result) {
            ProcessNode currentProcessNode = getCurrentProcessNode(process.getProcessId());
            if (currentProcessNode != null) {
                IApproval approval = getApproval(process.getApprovalImpl());
                if (approval.getUserList(currentProcessNode.getProcessNodeId()).contains(userId)) {
                    processes.add(process);
                }
            }
        }
        return processes;
    }

    /**
     * 通过流程节点ID获取该节点的审批项目对象列表
     *
     * @param processNodeId 流程节点ID
     * @return 该节点的审批项目对象列表
     */
    public static List<ApprovalItem> getApprovalItemListByProcessNodeId(long processNodeId) {
        ApprovalItem condition = new ApprovalItem();
        condition.setProcessNodeId(processNodeId);
        return approvalItemDAO.findByExample(condition);
    }

    /**
     * 获取审批流程模板列表
     *
     * @return
     */
    public static List<ProcessTemplate> getProcessTemplateList() {
        return processTemplateDAO.findAll();

    }

    /**
     * 获取用户已审批过的流程列表
     *
     * @param userId 用户ID
     * @return
     */
    public static List<Long> getUserHandledProcessIds(String userId) {
        //TODO:去重
        ApprovalItem condition = new ApprovalItem();
        condition.setUserId(userId);
        List<ApprovalItem> approvalItemList = approvalItemDAO.findByExample(condition);
        List<Long> processIdList = new ArrayList<Long>();
        for (ApprovalItem approvalItem : approvalItemList) {
            processIdList.add(approvalItem.getProcessId());
        }
        return processIdList;
    }


    /**
     * 获取流程已审批用户列表
     *
     * @param processId 流程ID
     * @return
     */
    public static List<String> getUserOfProcessIdHandled(long processId) {
        ApprovalItem condition = new ApprovalItem();
        condition.setProcessId(processId);
        List<ApprovalItem> approvalItemList = approvalItemDAO.findByExample(condition);
        List<String> userIdList = new ArrayList<String>();
        for (ApprovalItem approvalItem : approvalItemList) {
            userIdList.add(approvalItem.getUserId());
        }
        return userIdList;
    }

    /**
     * 通过流程ID获取该流程的审批项目对象列表
     *
     * @param processId 流程ID
     * @return 该节点的审批项目对象列表
     */
    public static List<ApprovalItem> getApprovalItemListByProcessId(long processId) {
        ApprovalItem condition = new ApprovalItem();
        condition.setProcessId(processId);
        return approvalItemDAO.findByExample(condition);
    }


    /**
     * 设置待审批人
     *
     * @param processId         流程ID
     * @param pendingUserIdList 审批用户ID列表
     */
    public static void setApprovalUser(long processId, List<String> pendingUserIdList) {
        //待审核用户
        StringBuffer pendingUserIds = new StringBuffer();
        pendingUserIds.append("_");
        for (String userId : pendingUserIdList) {
            pendingUserIds.append(userId);
            pendingUserIds.append("_");
        }
        //根据流程ID获取流程对象
        Process process = processDAO.findById(processId);
        process.setPendingUserIds(pendingUserIds.toString());
        processDAO.update(process);
    }
}