/**
 * 
 */
package com.greathack.homlin.pojo.process;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 流程节点详情
 * @author greathack
 *
 */
public class ProcessNodeDetail {

	/**
	    * 流程节点ID
	    */
	   private long processNodeId;
	   
	   /**
	    * 所属流程
	    */
	   private long processId;
	   
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
	   private int nodeState;
	   
	   /**
	    * 到达时间
	    */
	   private long fetchTime;
	   

	   /**
	    * 结束时间
	    */
	   private long endTime;
	   
	   /**
		 * 节点项目列表
		 */
	  private List<ApprovalItem> approvalItemList;

	public long getProcessNodeId() {
		return processNodeId;
	}

	public void setProcessNodeId(long processNodeId) {
		this.processNodeId = processNodeId;
	}

	public long getProcessId() {
		return processId;
	}

	public void setProcessId(long processId) {
		this.processId = processId;
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

	public int getNodeState() {
		return nodeState;
	}

	public void setNodeState(int nodeState) {
		this.nodeState = nodeState;
	}

	public long getFetchTime() {
		return fetchTime;
	}

	public void setFetchTime(long fetchTime) {
		this.fetchTime = fetchTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public List<ApprovalItem> getApprovalItemList() {
		return approvalItemList;
	}

	public void setApprovalItemList(List<ApprovalItem> approvalItemList) {
		this.approvalItemList = approvalItemList;
	}
	
	@Override 
	public String toString(){
		JSONObject json=new JSONObject();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                	json.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json.toString();
	}
}
