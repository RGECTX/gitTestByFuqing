package com.greathack.homlin.service.am.jyProcess;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.pojo.auxiliary.AuxJygl;
import com.greathack.homlin.pojo.process.ProcessNode;
import com.greathack.homlin.service.auxiliary.AuxJyglService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxJyglService;
import com.greathack.homlin.serviceinterface.process.INodeEvent;

/**
 * 加分到达节点改变流程进度
 */
public class JyglNodeEvent implements INodeEvent {
    @Override
    public void before(String beforeParameter, ProcessNode processNode) {
        JSONObject json = JSON.parseObject(beforeParameter);
        long processId = processNode.getProcessId();
        IAuxJyglService auxJyglService = new AuxJyglService();
        AuxJygl auxJygl = auxJyglService.getByProcessId(processId);
        if (auxJygl != null) {
            auxJygl.setProgress(json.getString("zlProgress"));
            auxJyglService.update(auxJygl);
        }
    }

    @Override
    public void after(String afterParameter, ProcessNode processNode, boolean isPass) {

    }
}
