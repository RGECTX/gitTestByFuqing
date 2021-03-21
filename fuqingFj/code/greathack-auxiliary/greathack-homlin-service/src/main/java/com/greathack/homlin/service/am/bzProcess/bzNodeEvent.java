package com.greathack.homlin.service.am.bzProcess;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.pojo.auxiliary.AuxBzgl;
import com.greathack.homlin.pojo.auxiliary.AuxJygl;
import com.greathack.homlin.pojo.process.ProcessNode;
import com.greathack.homlin.service.auxiliary.AuxBzglService;
import com.greathack.homlin.service.auxiliary.AuxJyglService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxBzglService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxJyglService;
import com.greathack.homlin.serviceinterface.process.INodeEvent;

/**
 * 加分到达节点改变流程进度
 */
public class bzNodeEvent implements INodeEvent {
    @Override
    public void before(String beforeParameter, ProcessNode processNode) {
        JSONObject json = JSON.parseObject(beforeParameter);
        long processId = processNode.getProcessId();
        IAuxBzglService auxBzglService = new AuxBzglService();
        AuxBzgl auxBzgl = auxBzglService.getByProcessId(processId);
        if (auxBzgl != null) {
            auxBzgl.setProgress(json.getString("bzProgress"));
            auxBzglService.update(auxBzgl);
        }
    }

    @Override
    public void after(String afterParameter, ProcessNode processNode, boolean isPass) {

    }
}
