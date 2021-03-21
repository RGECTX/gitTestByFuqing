/**
 *
 */
package com.greathack.homlin.service.auxiliary.zlglProcess;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.process.IProcessDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.AdminListResult;
import com.greathack.homlin.pojo.AmUnit;
import com.greathack.homlin.pojo.auxiliary.AuxZljd;
import com.greathack.homlin.pojo.process.Process;
import com.greathack.homlin.pojo.process.ProcessNode;
import com.greathack.homlin.pojo.process.ProcessNodeBranch;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.service.AdminPermissionService;
import com.greathack.homlin.service.AdminService;
import com.greathack.homlin.service.am.AmUnitService;
import com.greathack.homlin.service.auxiliary.AuxZljdService;
import com.greathack.homlin.service.process.ProcessManage;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxZljdService;
import com.greathack.homlin.serviceinterface.process.IApproval;
import com.greathack.utils.tools.Validation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author greathack
 *
 */
public class ZlglApproval implements IApproval {

    /* （非 Javadoc）
     * @see com.greathack.police.service.IApproval#eventProcessStart(long, long)
     */

    /**
     * 流程开始时对数据进行处理
     * @param processId 流程ID
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

    /**
     * 判定条件解析
     * @param processId 流程ID
     * @param processNodeBranch 流程节点分支条件ID
     * @return 判定结果
     */
    @Override
    public boolean conditionAnalyze(long processId, ProcessNodeBranch processNodeBranch) {
        /*IProcessDAO processDAO = (IProcessDAO) DAOFactory.createDAO("IProcessDAO");
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
        *//*boolean cond5 = false;*//*

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
		if(condJson.containsKey("posotion")){
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

        /*return cond3 && cond4 *//*&& cond5*//*;*/

        return true;
    }

    /* （非 Javadoc）
     * @see com.greathack.police.service.IApproval#getUserList(long)
     */

    /**
     * 获取有权限审批该节点的用户
     * 根据节点ID获取审批该节点的角色，进而获取角色用户
     * 再根据用户的身份，如从属于哪个分公司，来筛选出能审批该节点的用户
     * @param processNodeId 流程节点ID
     * @return 返回有权限审批该节点的用户Id的数组
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

        //除了提交审批节点(第一个节点)和审批人只有一个以外
        /*if (!processNode.getPreNodeCode().equals("start") && userIdList.size() > 1) {
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

    /**
     * 节点项目审批通过后，对数据进行处理
     * @param approvalItemId 审批流程节点项目ID
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

    /**
     * 节点项目审批不通过后，对数据进行处理
     * @param processId 流程ID
     * @param approvalItemId 审批流程节点项目ID
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

    /**
     * 节点审批通过后，对数据进行处理
     * @param processId 流程ID
     * @param processNodeId 审批流程节点ID
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

    /**
     * 节点审批不通过后，对数据进行处理
     * @param processId 流程ID
     * @param processNodeId 审批流程节点ID
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

    /**
     * 审批通过后，对数据进行处理
     * @param processId 审批流程ID
     */
    @Override
    public void processPass(long processId) throws ServiceException {
        //流程结束后处理招录建档
        AuxZljdService auxZljdService = new AuxZljdService();
        AuxZljd auxZljd = auxZljdService.getByProcessId(processId);
        auxZljd.setState("4");//审批状态，1、审批中，2、审批通过，4、审批不通过
        auxZljdService.update(auxZljd);
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

        //招录建档审批后提交档案管理
        /*Long zlId = auxZljd.getZlId();
        auxZljdService.zlAddById(zlId);*/
    }

    /* （非 Javadoc）
     * @see com.greathack.police.service.IApproval#processNoPass(long)
     */

    /**
     * 审批不通过后，对数据进行处理
     * @param processId 审批流程ID
     */
    @Override
    public void processNoPass(long processId){
        //流程结束后处理工资月报逻辑
        IAuxZljdService auxZljdService = new AuxZljdService();
        AuxZljd auxZljd = auxZljdService.getByProcessId(processId);
        auxZljd.setState("8");//审批状态，1、审批中，2、审批通过，4、审批不通过
        auxZljdService.update(auxZljd);

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
