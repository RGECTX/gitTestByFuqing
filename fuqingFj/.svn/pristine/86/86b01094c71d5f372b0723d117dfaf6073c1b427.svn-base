package com.greathack.homlin.service;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.permission.HasPower;
import com.greathack.homlin.pojo.permission.Power;
import com.greathack.homlin.service.permission.PermissionService;
import com.greathack.homlin.serviceinterface.IAdminPermissionService;
import com.greathack.homlin.serviceinterface.permission.IPermissionService;
import com.greathack.homlin.system.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class AdminPermissionService implements IAdminPermissionService {
    private static Logger logger = LoggerFactory.getLogger(AdminPermissionService.class);

    private static String ADMIN_PERMISSION_APP_CODE = SystemConfig.getConfigData("ADMIN_PERMISSION_APP_CODE");

    private IPermissionService permissionService = new PermissionService();

    @Override
    public HasPower hasPower(String userId, String powerCode) throws ServiceException {
        return permissionService.hasPower(ADMIN_PERMISSION_APP_CODE, userId, powerCode);
    }

    @Override
    public HasPower hasResourceAccessPower(String userId, String referer, String resource) throws ServiceException {
        return permissionService.hasPower(ADMIN_PERMISSION_APP_CODE, userId, referer, resource);
    }

    @Override
    public Map<String, Integer> getAllPowers(String userId) {
        return permissionService.getAllPowers(ADMIN_PERMISSION_APP_CODE, userId);
    }

    @Override
    public void assignRolesToUser(String userId, List<String> roleIdList) throws ServiceException {
        permissionService.assignRolesToUser(ADMIN_PERMISSION_APP_CODE, userId, roleIdList);
    }

    @Override
    public void assignUsersToRole(String roleId, List<String> userIdList) {
        permissionService.assignUsersToRole(ADMIN_PERMISSION_APP_CODE, roleId, userIdList);
    }

    @Override
    public void assignPowersToUser(String userId, List<Power> powerList) throws ServiceException {
        permissionService.assignPowersToUser(ADMIN_PERMISSION_APP_CODE, userId, powerList);

    }

    @Override
    public void assignPowersToRole(String roleId, List<Power> powerList) throws ServiceException {
        permissionService.assignPowersToRole(ADMIN_PERMISSION_APP_CODE, roleId, powerList);
    }

    @Override
    public List<String> getRoleIdsByUserId(String adminId) throws ServiceException {
        return permissionService.getAllRoleList(ADMIN_PERMISSION_APP_CODE, adminId);

    }

    @Override
    public List<String> getUserIdsByRoleId(String roleId) throws ServiceException {
        return permissionService.getUserIdListByRoleId(ADMIN_PERMISSION_APP_CODE, roleId);
    }

    @Override
    public Map<String, Integer> getPowersForRole(String roleId) {
        return permissionService.getPowersForRole(ADMIN_PERMISSION_APP_CODE, roleId);
    }

    @Override
    public void removeUser(String userId) {
        permissionService.removeUser(userId);

    }

    @Override
    public void removeRole(String roleId) {
        permissionService.removeRole(roleId);
    }

}
