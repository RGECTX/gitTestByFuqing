package com.greathack.homlin.serviceinterface;


import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.permission.HasPower;
import com.greathack.homlin.pojo.permission.Power;

import java.util.List;
import java.util.Map;

public interface IAdminPermissionService {


    /**
     * 判定用户是否拥有某个权限，包括角色所赋于的权限
     *
     * @param userId    用户ID
     * @param powerCode 权限编码
     * @return 有权限返回true, 无权限返回false
     */
    public HasPower hasPower(String userId, String powerCode) throws ServiceException;

    /**
     * 判定用户是否拥有某个权限，包括角色所赋于的权限
     *
     * @param userId   用户ID
     * @param referer  请求来源
     * @param resource 请求资源
     * @return 有权限返回true, 无权限返回false
     */
    public HasPower hasResourceAccessPower(String userId, String referer, String resource) throws ServiceException;

    /**
     * 获取用户全部权限，包括角色所赋于的权限
     *
     * @param userId 用户ID
     * @return 用户拥有的全部权限
     */
    public Map<String, Integer> getAllPowers(String userId);

    /**
     * 给用户重新分配角色
     *
     * @param roleIdList 角色ID列表
     * @param userId     用户ID
     */
    public void assignRolesToUser(String userId, List<String> roleIdList) throws ServiceException;

    /**
     * 给角色重新分配用户
     *
     * @param userIdList 用户ID列表
     * @param roleId     角色ID
     */
    public void assignUsersToRole(String roleId, List<String> userIdList);

    /**
     * 给用户重新分配权限
     *
     * @param userId    用户ID
     * @param powerList 要分配的权限列表
     */
    public void assignPowersToUser(String userId, List<Power> powerList) throws ServiceException;

    /**
     * 删除用户，清理已删除用户的数据
     *
     * @param userId 要删除的用户ID
     */
    public void removeUser(String userId);

    /**
     * 删除角色，清理已删除角色的数据
     *
     * @param roleId 要删除的角色ID
     */
    public void removeRole(String roleId);

    /**
     * 给角色重新分配权限
     *
     * @param roleId    角色ID
     * @param powerList 要分配的权限列表
     */
    public void assignPowersToRole(String roleId, List<Power> powerList) throws ServiceException;


    public List<String> getRoleIdsByUserId(String adminId) throws ServiceException;


    public List<String> getUserIdsByRoleId(String roleId) throws ServiceException;

    Map<String, Integer> getPowersForRole(String roleId);
}
