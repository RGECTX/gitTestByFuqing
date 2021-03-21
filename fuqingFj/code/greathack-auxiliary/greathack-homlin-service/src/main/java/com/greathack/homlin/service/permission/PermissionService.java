/**
 *
 */
package com.greathack.homlin.service.permission;

import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.permission.IPowerInRoleDAO;
import com.greathack.homlin.daointerface.permission.IPowerInUserDAO;
import com.greathack.homlin.daointerface.permission.IPowerItemDAO;
import com.greathack.homlin.daointerface.permission.IRoleOfUserDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.permission.*;
import com.greathack.homlin.serviceinterface.permission.IPermissionService;
import com.greathack.homlin.system.IdCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class PermissionService implements IPermissionService {

    private IPowerInUserDAO powerInUserDAO = (IPowerInUserDAO) DAOFactory.createDAO("IPowerInUserDAO");
    private IRoleOfUserDAO roleOfUserDAO = (IRoleOfUserDAO) DAOFactory.createDAO("IRoleOfUserDAO");
    private IPowerInRoleDAO powerInRoleDAO = (IPowerInRoleDAO) DAOFactory.createDAO("IPowerInRoleDAO");
    private IPowerItemDAO powerItemDAO = (IPowerItemDAO) DAOFactory.createDAO("IPowerItemDAO");

    /* (non-Javadoc)
     * @see com.greathack.permission.service.IPowerCenterService#hasPower(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public HasPower hasPower(String appCode, String userId, String powerCode) {
        HasPower hasPower = new HasPower();
        Map<String, Integer> userAllPowers = getAllPowers(appCode, userId);
        if (userAllPowers.containsKey(powerCode)) {
            if (userAllPowers.get(powerCode) == HAS_POWER) {
                hasPower.setHasPower(true);

                PowerItem instance = new PowerItem();
                instance.setAppCode(appCode);
                instance.setPowerCode(powerCode);
                List<PowerItem> powerItemList = powerItemDAO.findByExample(instance);
                hasPower.setPowerItemList(powerItemList);
                return hasPower;
            }
        }
        hasPower.setHasPower(false);
        return hasPower;
    }


    @Override
    public HasPower hasPower(String appCode, String userId, String referer, String resource) {
        PowerItem condition = new PowerItem();
        condition.setAppCode(appCode);
        condition.setReferer(referer);
        condition.setResource(resource);
        List<PowerItem> powerItemList = powerItemDAO.findByExample(condition);
        if (powerItemList == null || powerItemList.size() == 0) {
            HasPower hasPower = new HasPower();
            hasPower.setHasPower(false);
            return hasPower;
        } else {
            Map<String, Integer> userAllPowers = getAllPowers(appCode, userId);
            HasPower hasPower = new HasPower();
            for (int i = 0; i < powerItemList.size(); i++) {
                String powerCode = powerItemList.get(i).getPowerCode();
                if (userAllPowers.containsKey(powerCode)) {
                    if (userAllPowers.get(powerCode) != HAS_POWER) {
                        powerItemList.remove(i);
                        i--;
                    }
                } else {
                    powerItemList.remove(i);
                    i--;
                }
            }

            if (powerItemList.size() == 0) {
                hasPower.setHasPower(false);
            } else {
                hasPower.setHasPower(true);
                hasPower.setPowerItemList(powerItemList);
            }
            return hasPower;
        }
    }

    /* (non-Javadoc)
     * @see com.greathack.permission.service.IPowerCenterService#getAllPowers(java.lang.String, java.lang.String)
     */
    @Override
    public Map<String, Integer> getAllPowers(String appCode, String userId) {
        Map<String, Integer> powerMap = new HashMap<String, Integer>();
        PowerInUser condition1 = new PowerInUser();
        condition1.setAppCode(appCode);
        condition1.setUserId(userId);
        List<PowerInUser> powerInUsers = powerInUserDAO.findByExample(condition1);
        for (PowerInUser powerInUser : powerInUsers) {//基于用户的权限
            if (powerMap.get(powerInUser.getPowerCode()) == null
                    || powerInUser.getPowerLevel() > powerMap.get(powerInUser.getPowerCode())) {//级别大的权限覆盖级别小的
                powerMap.put(powerInUser.getPowerCode(), powerInUser.getPowerLevel());
            }
        }


        RoleOfUser condition2 = new RoleOfUser();
        condition2.setAppCode(appCode);
        condition2.setUserId(userId);
        List<RoleOfUser> roleOfUsers = roleOfUserDAO.findByExample(condition2);//找到用户的所有角色

        for (RoleOfUser roleOfUser : roleOfUsers) {//遍历每个角色
            PowerInRole condition3 = new PowerInRole();
            condition3.setAppCode(appCode);
            condition3.setRoleId(roleOfUser.getRoleId());
            List<PowerInRole> powerInRoles = powerInRoleDAO.findByExample(condition3);
            for (PowerInRole powerInRole : powerInRoles) {//基于角色的权限
                if (powerMap.get(powerInRole.getPowerCode()) == null
                        || powerInRole.getPowerLevel() > powerMap.get(powerInRole.getPowerCode())) {//级别大的权限覆盖级别小的
                    powerMap.put(powerInRole.getPowerCode(), powerInRole.getPowerLevel());
                }
            }
        }
        return powerMap;
    }

    @Override
    public Map<String, Integer> getPowersForUser(String appCode, String userId) {
        Map<String, Integer> powerMap = new HashMap<String, Integer>();
        PowerInUser condition1 = new PowerInUser();
        condition1.setAppCode(appCode);
        condition1.setUserId(userId);
        List<PowerInUser> powerInUsers = powerInUserDAO.findByExample(condition1);
        for (PowerInUser powerInUser : powerInUsers) {//基于用户的权限
            if (powerMap.get(powerInUser.getPowerCode()) == null
                    || powerInUser.getPowerLevel() > powerMap.get(powerInUser.getPowerCode())) {//级别大的权限覆盖级别小的
                powerMap.put(powerInUser.getPowerCode(), powerInUser.getPowerLevel());
            }
        }
        return powerMap;
    }

    @Override
    public Map<String, Integer> getPowersForRole(String appCode, String roleId) {
        Map<String, Integer> powerMap = new HashMap<String, Integer>();
        PowerInRole condition3 = new PowerInRole();
        condition3.setAppCode(appCode);
        condition3.setRoleId(roleId);
        List<PowerInRole> powerInRoles = powerInRoleDAO.findByExample(condition3);
        for (PowerInRole powerInRole : powerInRoles) {//基于角色的权限
            if (powerMap.get(powerInRole.getPowerCode()) == null
                    || powerInRole.getPowerLevel() > powerMap.get(powerInRole.getPowerCode())) {//级别大的权限覆盖级别小的
                powerMap.put(powerInRole.getPowerCode(), powerInRole.getPowerLevel());
            }
        }
        return powerMap;
    }

    /* (non-Javadoc)
     * @see com.greathack.permission.service.IPowerCenterService#assignRolesToUser(java.lang.String, java.lang.String, java.lang.String[])
     */
    @Override
    public void assignRolesToUser(String appCode, String userId, List<String> roleIdList) {
        roleOfUserDAO.clean(appCode, userId);//根据userId批量删除
        if (roleIdList == null || roleIdList.size() == 0) {
            return;
        }
        ArrayList<RoleOfUser> roleOfUsers = new ArrayList<RoleOfUser>();
        for (String roleId : roleIdList) {//重新分配用户角色
            RoleOfUser roleOfUser = new RoleOfUser();
            try {
                roleOfUser.setRoleOfUserId(IdCreator.createId("RoleOfUser"));
                roleOfUser.setAppCode(appCode);
                roleOfUser.setRoleId(roleId);
                roleOfUser.setUserId(userId);
                roleOfUsers.add(roleOfUser);
            } catch (ServiceException e) {
                continue;
            }
        }
        roleOfUserDAO.addBatch(roleOfUsers);
    }

    @Override
    public void assignUsersToRole(String appCode, String roleId, List<String> userIdList) {
        roleOfUserDAO.deleteByRoleId(roleId);//根据roleId批量删除
        if (userIdList == null || userIdList.size() == 0) {
            return;
        }
        ArrayList<RoleOfUser> roleOfUsers = new ArrayList<RoleOfUser>();
        for (String userId : userIdList) {//重新分配角色用户
            RoleOfUser roleOfUser = new RoleOfUser();
            try {
                roleOfUser.setRoleOfUserId(IdCreator.createId("RoleOfUser"));
                roleOfUser.setAppCode(appCode);
                roleOfUser.setRoleId(roleId);
                roleOfUser.setUserId(userId);
                roleOfUsers.add(roleOfUser);
            } catch (ServiceException e) {
                continue;
            }
        }
        roleOfUserDAO.addBatch(roleOfUsers);
    }

    /* (non-Javadoc)
     * @see com.greathack.permission.service.IPowerCenterService#assignPowersToUser(java.lang.String, java.lang.String, java.util.List)
     */
    @Override
    public void assignPowersToUser(String appCode, String userId, List<Power> powerList) {
        powerInUserDAO.clean(appCode, userId);//根据userId批量删除
        if (powerList == null || powerList.size() == 0) {
            return;
        }
        ArrayList<PowerInUser> powerInUsers = new ArrayList<PowerInUser>();
        for (Power power : powerList) {//重新分配用户权限
            try {
                PowerInUser powerInUser = new PowerInUser();
                powerInUser.setPowerInUserId(IdCreator.createId("PowerInUser"));
                powerInUser.setAppCode(appCode);
                powerInUser.setUserId(userId);
                powerInUser.setPowerCode(power.getPowerCode());
                powerInUser.setPowerLevel(power.getPowerLevel());
                powerInUsers.add(powerInUser);
            } catch (ServiceException e) {
                continue;
            }
        }
        powerInUserDAO.addBatch(powerInUsers);
    }

    /* (non-Javadoc)
     * @see com.greathack.permission.service.IPowerCenterService#assignPowersToRole(java.lang.String, java.lang.String, java.util.Map)
     */
    @Override
    public void assignPowersToRole(String appCode, String roleId, List<Power> powerList) {
        powerInRoleDAO.clean(appCode, roleId);//根据roleId批量删除
        if (powerList == null || powerList.size() == 0) {
            return;
        }
        ArrayList<PowerInRole> powerInRoles = new ArrayList<PowerInRole>();
        for (Power power : powerList) {//重新分配用户权限
            PowerInRole powerInRole = new PowerInRole();
            try {
                powerInRole.setPowerInRoleId(IdCreator.createId("PowerInRole"));
                powerInRole.setAppCode(appCode);
                powerInRole.setRoleId(roleId);
                powerInRole.setPowerCode(power.getPowerCode());
                powerInRole.setPowerLevel(power.getPowerLevel());
                powerInRoles.add(powerInRole);
            } catch (ServiceException e) {
                continue;
            }
        }
        powerInRoleDAO.addBatch(powerInRoles);
    }

    @Override
    public List<String> getAllRoleList(String appCode, String userId) {
        RoleOfUser condition = new RoleOfUser();
        condition.setAppCode(appCode);
        condition.setUserId(userId);
        List<RoleOfUser> roleOfUserList = roleOfUserDAO.findByExample(condition);
        List<String> roleIdList = new ArrayList<String>();
        for (RoleOfUser roleOfUser : roleOfUserList) {
            roleIdList.add(roleOfUser.getRoleId());
        }
        return roleIdList;
    }


    @Override
    public List<String> getUserIdListByRoleId(String appCode, String roleId) {
        RoleOfUser condition = new RoleOfUser();
        condition.setAppCode(appCode);
        condition.setRoleId(roleId);
        List<RoleOfUser> roleOfUserList = roleOfUserDAO.findByExample(condition);
        List<String> userIdList = new ArrayList<String>();
        for (RoleOfUser roleOfUser : roleOfUserList) {
            userIdList.add(roleOfUser.getUserId());
        }
        return userIdList;
    }


    @Override
    public void removeUser(String userId) {
        roleOfUserDAO.deleteByUserId(userId);
        powerInUserDAO.deleteByUserId(userId);
    }


    @Override
    public void removeRole(String roleId) {
        roleOfUserDAO.deleteByRoleId(roleId);
        powerInRoleDAO.deleteByRoleId(roleId);
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        //System.out.println(PowerCenter.hasMenuPower(1, "powerCode1"));

    }

}
