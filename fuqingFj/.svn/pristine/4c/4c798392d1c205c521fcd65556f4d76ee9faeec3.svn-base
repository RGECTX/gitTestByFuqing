package com.greathack.homlin.serviceinterface.process;

import com.greathack.homlin.pojo.process.ProcessNode;

public interface INodeEvent {

    /**
     * 节点前置事件
     * @param beforeParameter 参数
     * @param processNode 节点
     */
    void before(String beforeParameter, ProcessNode processNode);

    /**
     * 节点前置事件
     * @param afterParameter 参数
     * @param processNode 节点
     * @param isPass 节点是否审批通过
     */
    void after(String afterParameter, ProcessNode processNode, boolean isPass);
}
