package com.greathack.homlin.serviceinterface.process;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.process.ProcessNodeBranch;

import java.util.List;

public interface IApproval {

	/**
	 * 流程开始时对数据进行处理
	 * @param processId 流程ID
	 */
	public void eventProcessStart(long processId, long processNodeId) throws ServiceException;

	/**
	 * 判定条件解析
	 * @param processId 流程ID
	 * @param processNodeBranch 流程节点分支条件ID
	 * @return 判定结果
	 */
	public boolean conditionAnalyze(long processId,
                                    ProcessNodeBranch processNodeBranch);

	/**
	 * 获取有权限审批该节点的用户
	 * 根据节点ID获取审批该节点的角色，进而获取角色用户
	 * 再根据用户的身份，如从属于哪个分公司，来筛选出能审批该节点的用户
	 * @param processNodeId 流程节点ID
	 * @return 返回有权限审批该节点的用户Id的数组
	 */
	public List<String> getUserList(long processNodeId) throws ServiceException;

	/**
	 * 节点项目审批通过后，对数据进行处理
	 * @param approvalItemId 审批流程节点项目ID
	 */
	public void processNodeItemPass(long processId, long processNodeId,
                                    long approvalItemId) throws ServiceException;

	/**
	 * 节点项目审批不通过后，对数据进行处理
	 * @param processId 流程ID
	 * @param approvalItemId 审批流程节点项目ID
	 */
	public void processNodeItemNoPass(long processId, long processNodeId,
                                      long approvalItemId) throws ServiceException;

	/**
	 * 节点审批通过后，对数据进行处理
	 * @param processId 流程ID
	 * @param processNodeId 审批流程节点ID
	 */
	public void processNodePass(long processId, long processNodeId) throws ServiceException;

	/**
	 * 节点审批不通过后，对数据进行处理
	 * @param processId 流程ID
	 * @param processNodeId 审批流程节点ID
	 */
	public void processNodeNoPass(long processId, long processNodeId) throws ServiceException;

	/**
	 * 审批通过后，对数据进行处理
	 * @param processId 审批流程ID
	 */
	public void processPass(long processId) throws ServiceException;

	/**
	 * 审批不通过后，对数据进行处理
	 * @param processId 审批流程ID
	 */
	public void processNoPass(long processId) throws ServiceException;

}