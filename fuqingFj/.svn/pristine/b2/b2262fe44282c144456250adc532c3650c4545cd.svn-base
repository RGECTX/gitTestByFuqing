package com.greathack.homlin.service.am.zlProcess;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.pojo.auxiliary.AuxZljd;
import com.greathack.homlin.pojo.process.ProcessNode;
import com.greathack.homlin.service.auxiliary.AuxZljdService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxZljdService;
import com.greathack.homlin.serviceinterface.process.INodeEvent;

/**
 * 招录到达节点改变流程进度
 */
public class ZlglNodeEvent implements INodeEvent {
    @Override
    public void before(String beforeParameter, ProcessNode processNode) {
        JSONObject json = JSON.parseObject(beforeParameter);
        long processId = processNode.getProcessId();
        IAuxZljdService auxZljdService = new AuxZljdService();
        AuxZljd auxZljd = auxZljdService.getByProcessId(processId);
        if (auxZljd != null) {
            auxZljd.setProgress(json.getString("zlProgress"));
            auxZljdService.update(auxZljd);
        }
    }

    @Override
    public void after(String afterParameter, ProcessNode processNode, boolean isPass) {

    }
}
