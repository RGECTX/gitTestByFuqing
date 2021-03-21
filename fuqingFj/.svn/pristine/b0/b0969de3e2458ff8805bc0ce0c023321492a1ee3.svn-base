/**
 *
 */
package com.greathack.homlin.service.auxiliary.bzProcess;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.process.IProcessDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.AdminListResult;
import com.greathack.homlin.pojo.AmUnit;
import com.greathack.homlin.pojo.auxiliary.AuxBzgl;
import com.greathack.homlin.pojo.process.Process;
import com.greathack.homlin.pojo.process.ProcessNode;
import com.greathack.homlin.pojo.process.ProcessNodeBranch;
import com.greathack.homlin.service.AdminPermissionService;
import com.greathack.homlin.service.AdminService;
import com.greathack.homlin.service.am.AmUnitService;
import com.greathack.homlin.service.auxiliary.AuxBzglService;
import com.greathack.homlin.service.process.ProcessManage;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxBzglService;
import com.greathack.homlin.serviceinterface.process.IApproval;
import com.greathack.utils.tools.Validation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author greathack
 *
 */
public class BzApproval implements IApproval {

    /*private static IAuxDaglDAO auxDaglDAO = (IAuxDaglDAO) DAOFactory.createDAO("IAuxDaglDAO");*/

    /* （非 Javadoc）
     * @see com.greathack.police.service.IApproval#eventProcessStart(long, long)
     */
    @Override
    public void eventProcessStart(long processId, long processNodeId) throws ServiceException {
        IProcessDAO processDAO = (IProcessDAO) DAOFactory.createDAO("IProcessDAO");
        //待审核用户
        List<String> pendingUserIdList = ProcessManage.getProcessNodePendingUsers(processNodeId);
        StringBuffer pendingUserIds = new StringBuffer();
        pendingUserIds.append("_");
        for (String userId : pendingUserIdList) {
            pendingUserIds.append(userId);
            pendingUserIds.append("_");
        }
        //根据流程ID获取流程对象
        Process process = processDAO.findById(processId);
        process.setHandledUserIds("_");
        process.setPendingUserIds(pendingUserIds.toString());
        processDAO.update(process);
    }

    /* （非 Javadoc）
     * @see com.greathack.police.service.IApproval#conditionAnalyze(long, com.greathack.police.approval.pojo.ProcessNodeBranch)
     */
    @Override
    public boolean conditionAnalyze(long processId, ProcessNodeBranch processNodeBranch) {
        IProcessDAO processDAO = (IProcessDAO) DAOFactory.createDAO("IProcessDAO");
        Process process = processDAO.findById(processId);
        JSONObject varsJson = JSON.parseObject(process.getVars());
        String applyUserId = varsJson.getString("applyUserId");

        String pnbcondition = processNodeBranch.getCond();//分支条件
        if (pnbcondition == null || pnbcondition.equals("")) {
            return true;
        }
        AdminService adminService = new AdminService();
        //申请人
        Admin appleUser = adminService.getAdminById(Long.valueOf(applyUserId));

        JSONObject condJson = JSON.parseObject(pnbcondition);
        boolean cond3 = false;
        boolean cond4 = false;
        boolean cond5 = false;

        //第三个条件
        String orgId = condJson.getString("orgId");
        if ("any".equals(orgId)) {
            cond3 = true;
        }

        //第四个条件
        String roleId = condJson.getString("roleId");
        if ("any".equals(roleId)) {
            cond4 = true;
        }
        AdminPermissionService adminPermissionService = new AdminPermissionService();
        try {
            List<String> roleIdList = adminPermissionService.getRoleIdsByUserId(applyUserId);
            if (roleIdList.contains(roleId)) {
                cond4 = true;
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        //第五个条件
		/*if(condJson.containsKey("posotion")){
			IAmGzxxDAO amGzxxDAO = (IAmGzxxDAO) DAOFactory.createDAO("IAmGzxxDAO");
			String posotion=condJson.getString("posotion");
			String org=condJson.getString("org");
			posotion=posotion.replace("，",",");
			posotion=","+posotion+",";
			AmGzxxSearchCriteria amGzxxCond=new AmGzxxSearchCriteria();
			amGzxxCond.setIdcard(appleUser.getIdcard());
			List<AmGzxx> amGzxxList=amGzxxDAO.findByConditions(amGzxxCond);
			if(amGzxxList.size()>0){
				AmGzxx amGzxx=amGzxxList.get(0);
				if(Objects.equals(org,"any")){
					if(posotion.indexOf(amGzxx.getZwmc())>-1){
						cond5=true;
					}
				}else {
					if(posotion.indexOf(amGzxx.getZwmc())>-1 && Objects.equals(amGzxx.getOrgId(),org)){
						cond5=true;
					}
				}
			}
		}else{
			cond5=true;
		}*/

        return cond3 && cond4 /*&& cond5*/;
    }

    /* （非 Javadoc）
     * @see com.greathack.police.service.IApproval#getUserList(long)
     */
    @Override
    public List<String> getUserList(long processNodeId) throws ServiceException {
        IProcessDAO processDAO = (IProcessDAO) DAOFactory.createDAO("IProcessDAO");
        List<String> userIdList = new ArrayList<String>();
        ProcessNode processNode = ProcessManage.getProcessNode(processNodeId);
        Process process = processDAO.findById(processNode.getProcessId());

        JSONObject varsJson = JSON.parseObject(process.getVars());
        String applyUserId = varsJson.getString("applyUserId");//申请人
        String orgId = varsJson.getString("orgId");//申请人部门
        String approvalRoleId = processNode.getApprovalRoleId();
        JSONObject json = JSON.parseObject(approvalRoleId);
        String approvalRoleType = json.getString("approvalRoleType");
        String roleId = json.getString("approvalRoleId");

        AdminPermissionService adminPermissionService = new AdminPermissionService();
        AdminService adminService = new AdminService();

        AmUnitService amUnitService = new AmUnitService();
        if (approvalRoleType.equals("self")) {//自己
            userIdList.add(applyUserId);
        } else if (approvalRoleType.equals("upLeader")) {//上级分管领导
            AmUnit org = amUnitService.findById(Long.valueOf(orgId));
            if (org != null) {
				/*List<AmUnit> parentList=amUnitService.getParents(org.getOrgId());
				//把自己的单位也算进去
				parentList.add(org);
				for(AmUnit amUnit:parentList){
					if(!Validation.isEmpty(org.getUpLeaderA())){
						userIdList.add(amUnit.getUpLeaderA());
					}
				}*/
                if (!Validation.isEmpty(org.getUpLeaderA())) {
                    userIdList.add(org.getUpLeaderA());
                }
                if (!Validation.isEmpty(org.getUpLeaderB())) {
                    userIdList.add(org.getUpLeaderB());
                }
            }
        } else if (approvalRoleType.equals("role")) {//角色
            try {
                userIdList = adminPermissionService.getUserIdsByRoleId(roleId);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        } else if (approvalRoleType.equals("users")) {
            JSONArray jsonArray = json.getJSONArray("approvalUserIds");
            for (Object obj : jsonArray) {
                userIdList.add(obj.toString());
            }
        }/*else if(approvalRoleType.equals("position")){
			IAmGzxxDAO amGzxxDAO = (IAmGzxxDAO) DAOFactory.createDAO("IAmGzxxDAO");
			String posotion=json.getString("posotion");
			String org=json.getString("org");
			posotion=posotion.replace("，",",");
			String[] arr=posotion.split(",");
			for(String str:arr){
				AmGzxxSearchCriteria criteriaCond=new AmGzxxSearchCriteria();
				if(!Objects.equals(org,"any")){
					criteriaCond.setOrgId(Long.valueOf(org));
				}
				criteriaCond.setZwmc(str);
				List<AmGzxx> amGzxxList=amGzxxDAO.findByConditions(criteriaCond);
				List<String> idcardList=new ArrayList<>();
				for(AmGzxx amGzxx:amGzxxList){
					idcardList.add(amGzxx.getIdcard());
				}
				AdminListResult adminListResult=adminService.getListByUserIdcards(idcardList);
				List<Admin> userList=adminListResult.getUserList();
				for(Admin user:userList){
					userIdList.add(user.getUserId().toString());
				}
			}
		}*/


        String relation = json.getString("relation");
        if (relation.equals("directSuperior")) {//直接上级（同一部门）
            List<Long> userIdList1 = new ArrayList<Long>();
            for (String userId : userIdList) {
                userIdList1.add(Long.parseLong(userId));
            }
            AdminListResult adminListResult = adminService.getListByUserIds(userIdList1);
            List<Admin> userList = adminListResult.getUserList();
			/*for(Admin user:userList){
				//如果审批人和申请人不是同一个部门就删除
				String workOrgId=user.getWorkOrgId(); //先判断请休假单位（实际工作单位），再判断绩效工资单位
				if(StringUtils.isNotEmpty(workOrgId) ){
					if(!orgId.equals(workOrgId)){
						userIdList.remove(user.getUserId().toString());
					}
				}else{
					if(!orgId.equals(user.getAmUnitId())){
						userIdList.remove(user.getUserId().toString());
					}
				}
			}*/
            for (String noExistUserId : adminListResult.getNoExistUserList()) {
                userIdList.remove(noExistUserId);
            }
        }
        if (relation.equals("directLineSuperior")) {//直系上级（上级部门）
            //上级部门列表
            List<AmUnit> parentList = amUnitService.getParents(Long.valueOf(orgId));
            AmUnit org = amUnitService.findById(Long.valueOf(orgId));
            //算上自己部门
            parentList.add(org);
            List<String> orgIdList = new ArrayList<String>();
            for (AmUnit amUnit : parentList) {
                orgIdList.add(amUnit.getOrgId().toString());
            }

            List<Long> userIdList1 = new ArrayList<Long>();
            for (String userId : userIdList) {
                userIdList1.add(Long.parseLong(userId));
            }
            AdminListResult adminListResult = adminService.getListByUserIds(userIdList1);
            List<Admin> userList = adminListResult.getUserList();
            for (Admin user : userList) {
                //如果审批人不在申请人的上级部门就删除
                if (!orgIdList.contains(user.getAmUnitId())) {
                    userIdList.remove(user.getUserId().toString());
                }
            }
            for (String noExistUserId : adminListResult.getNoExistUserList()) {
                userIdList.remove(noExistUserId);
            }
        }

        /*//除了提交审批节点(第一个节点)和审批人只有一个以外
        if (!processNode.getPreNodeCode().equals("start") && userIdList.size() > 1) {
            userIdList.remove(applyUserId);//自己不能审批自己
        }*/

        //如果最后找不到审批人，则由管理员代为审批
        if (userIdList.size() == 0) {
            userIdList.add("1");
        }

        //代理人处理开始
        List<Long> userIdList1 = new ArrayList<Long>();
        for (String userId : userIdList) {
            userIdList1.add(Long.parseLong(userId));
        }
        AdminListResult adminListResult = adminService.getListByUserIds(userIdList1);
        List<Admin> userList = adminListResult.getUserList();
		/*for(Admin admin:userList){
			if(admin.getAgentUserId()!=null){
				//如果有设置了代理人，就删除原来的审批人
				userIdList.remove(admin.getUserId().toString());
				userIdList.add(admin.getAgentUserId().toString());
			}
		}*/
        //代理人处理结束

        return userIdList;
    }

    /* （非 Javadoc）
     * @see com.greathack.police.service.IApproval#processNodeItemPass(long, long, long)
     */
    @Override
    public void processNodeItemPass(long processId, long processNodeId,
                                    long approvalItemId) throws ServiceException {
        IProcessDAO processDAO = (IProcessDAO) DAOFactory.createDAO("IProcessDAO");
        ProcessNode nextProcessNode = ProcessManage.getCurrentProcessNode(processId);
        if (nextProcessNode != null) {
            //已审核用户
            List<String> handledUserIdList = ProcessManage.getUserOfProcessIdHandled(processId);
            StringBuffer handledUserIds = new StringBuffer();
            handledUserIds.append("_");
            for (String userId : handledUserIdList) {
                handledUserIds.append(userId);
                handledUserIds.append("_");
            }
            //待审核用户
            long nextProcessNodeId = nextProcessNode.getProcessNodeId();
            List<String> pendingUserIdList = ProcessManage.getProcessNodePendingUsers(nextProcessNodeId);
            StringBuffer pendingUserIds = new StringBuffer();
            pendingUserIds.append("_");
            for (String userId : pendingUserIdList) {
                pendingUserIds.append(userId);
                pendingUserIds.append("_");
            }
            //根据流程ID获取流程对象
            Process process = processDAO.findById(processId);
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
        ProcessNode nextProcessNode = ProcessManage.getCurrentProcessNode(processId);
        if (nextProcessNode != null) {
            //已审核用户
            List<String> handledUserIdList = ProcessManage.getUserOfProcessIdHandled(processId);
            StringBuffer handledUserIds = new StringBuffer();
            handledUserIds.append("_");
            for (String userId : handledUserIdList) {
                handledUserIds.append(userId);
                handledUserIds.append("_");
            }
            //待审核用户
            long nextProcessNodeId = nextProcessNode.getProcessNodeId();
            List<String> pendingUserIdList = ProcessManage.getProcessNodePendingUsers(nextProcessNodeId);
            StringBuffer pendingUserIds = new StringBuffer();
            pendingUserIds.append("_");
            for (String userId : pendingUserIdList) {
                pendingUserIds.append(userId);
                pendingUserIds.append("_");
            }
            //根据流程ID获取流程对象
            Process process = processDAO.findById(processId);
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
        ProcessNode nextProcessNode = ProcessManage.getCurrentProcessNode(processId);
        if (nextProcessNode != null) {
            //已审核用户
            List<String> handledUserIdList = ProcessManage.getUserOfProcessIdHandled(processId);
            StringBuffer handledUserIds = new StringBuffer();
            handledUserIds.append("_");
            for (String userId : handledUserIdList) {
                handledUserIds.append(userId);
                handledUserIds.append("_");
            }
            //待审核用户
            long nextProcessNodeId = nextProcessNode.getProcessNodeId();
            List<String> pendingUserIdList = ProcessManage.getProcessNodePendingUsers(nextProcessNodeId);
            StringBuffer pendingUserIds = new StringBuffer();
            pendingUserIds.append("_");
            for (String userId : pendingUserIdList) {
                pendingUserIds.append(userId);
                pendingUserIds.append("_");
            }
            //根据流程ID获取流程对象
            Process process = processDAO.findById(processId);
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
        ProcessNode nextProcessNode = ProcessManage.getCurrentProcessNode(processId);
        if (nextProcessNode != null) {
            //已审核用户
            List<String> handledUserIdList = ProcessManage.getUserOfProcessIdHandled(processId);
            StringBuffer handledUserIds = new StringBuffer();
            handledUserIds.append("_");
            for (String userId : handledUserIdList) {
                handledUserIds.append(userId);
                handledUserIds.append("_");
            }
            //待审核用户
            long nextProcessNodeId = nextProcessNode.getProcessNodeId();
            List<String> pendingUserIdList = ProcessManage.getProcessNodePendingUsers(nextProcessNodeId);
            StringBuffer pendingUserIds = new StringBuffer();
            pendingUserIds.append("_");
            for (String userId : pendingUserIdList) {
                pendingUserIds.append(userId);
                pendingUserIds.append("_");
            }
            //根据流程ID获取流程对象
            Process process = processDAO.findById(processId);
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
        //流程通过后处理减员申报
        AuxBzglService auxBzglService = new AuxBzglService();
        AuxBzgl auxBzgl = auxBzglService.getByProcessId(processId);
        //流程通过原先的档案管理要改成离职状态
		auxBzgl.setState("4");
		auxBzglService.update(auxBzgl);

        /*AuxDaglService auxDaglService = new AuxDaglService();
        List<AuxDagl> dagl = auxDaglService.findIdcard(auxJygl.getIdcard());
        dagl.get(0).setState("2");*/

        //获取唯一身份证ID的档案信息记录 修改成离职状态
        /*List<AuxDagl> auxDaglList = auxDaglService.findIdcard(auxJygl.getIdcard());
        auxDaglList.get(0).setState("2");
        auxDaglService.updateState(auxDaglList.get(0));*/



        IProcessDAO processDAO = (IProcessDAO) DAOFactory.createDAO("IProcessDAO");
        //已审核用户
        List<String> handledUserIdList = ProcessManage.getUserOfProcessIdHandled(processId);
        StringBuffer handledUserIds = new StringBuffer();
        handledUserIds.append("_");
        for (String userId : handledUserIdList) {
            handledUserIds.append(userId);
            handledUserIds.append("_");
        }
        //根据流程ID获取流程对象
        Process process = processDAO.findById(processId);
        process.setHandledUserIds(handledUserIds.toString());
        process.setPendingUserIds("_");
        processDAO.update(process);
    }

    /* （非 Javadoc）
     * @see com.greathack.police.service.IApproval#processNoPass(long)
     */
    @Override
    public void processNoPass(long processId) {
		//流程不通过后处理减员申报
		IAuxBzglService auxBzglService=new AuxBzglService();
		AuxBzgl auxBzgl=auxBzglService.getByProcessId(processId);
		auxBzgl.setState("8");//
		auxBzglService.update(auxBzgl);

        IProcessDAO processDAO = (IProcessDAO) DAOFactory.createDAO("IProcessDAO");

        //已审核用户
        List<String> handledUserIdList = ProcessManage.getUserOfProcessIdHandled(processId);
        StringBuffer handledUserIds = new StringBuffer();
        handledUserIds.append("_");
        for (String userId : handledUserIdList) {
            handledUserIds.append(userId);
            handledUserIds.append("_");
        }
        //根据流程ID获取流程对象
        Process process = processDAO.findById(processId);
        process.setHandledUserIds(handledUserIds.toString());
        process.setPendingUserIds("_");
        processDAO.update(process);

    }


}
