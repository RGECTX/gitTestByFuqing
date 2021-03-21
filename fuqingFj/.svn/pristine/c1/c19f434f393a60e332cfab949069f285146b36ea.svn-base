//Source file: G:\\业务逻辑\\权限管理系统\\src\\com\\wbaiju\\approvalProcess\\pojo\\approvalItem.java

package com.greathack.homlin.pojo.process;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;


/**
 * 节点审批项目
 */
public class ApprovalItem 
{
   
   /**
    * 审批项目ID
    */
   private Long approvalItemId;
   
   /**
    * 所属流程ID
    */
   private Long processId;
   
   /**
    * 所属流程节点ID
    */
   private Long processNodeId;
   
   /**
    * 节点编码
    */
   private String nodeCode;
   
   /**
    * 审批人
    */
   private String userId;
   
   /**
    * 审批内容
    */
   private String itemContent;
   
   /**
    * 审批项目状态
    * 1：未到达，2：等待审批，4：审批通过，8：审批不通过（审批项目中，只要一个不通过，
    * 这个节点就算不通过）
    */
   private int itemState;
   
   /**
    * 审批意见
    */
   private String remark;
   
   /**
    * 创建时间
    */
   private long createTime;
   
   /**
    * 审批时间
    */
   private long updateTime;

	public Long getApprovalItemId() {
		return approvalItemId;
	}
	
	public void setApprovalItemId(Long approvalItemId) {
		this.approvalItemId = approvalItemId;
	}
	
	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
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

	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getItemContent() {
		return itemContent;
	}
	
	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}
	
	public int getItemState() {
		return itemState;
	}
	
	public void setItemState(int itemState) {
		this.itemState = itemState;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public long getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	public long getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
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
