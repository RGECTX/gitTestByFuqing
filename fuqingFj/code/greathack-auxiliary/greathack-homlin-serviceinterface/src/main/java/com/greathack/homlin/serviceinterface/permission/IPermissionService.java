package com.greathack.homlin.serviceinterface.permission;

import com.greathack.homlin.pojo.permission.HasPower;
import com.greathack.homlin.pojo.permission.Power;

import java.util.List;
import java.util.Map;


public interface IPermissionService {

    public static final int NO_POWER = 1;
    public static final int HAS_POWER = 2;
    public static final int NEVER_POWER = 4;

    /**
     * 判定用户是否拥有某个权限，包括角色所赋于的权限
     *
     * @param appCode   应用编码
     * @param userId    用户ID
     * @param powerCode 权限编码
     * @return 有权限返回true, 无权限返回false
     */
    public HasPower hasPower(String appCode, String userId, String powerCode);

    /**
     * 判定用户是否拥有某个权限，包括角色所赋于的权限
     *
     * @param appCode  应用编码
     * @param userId   用户ID
     * @param referer  请求来源
     * @param resource 请求资源
     * @return 有权限返回true, 无权限返回false
     */
    public HasPower hasPower(String appCode, String userId, String referer, String resource);

    /**
     * 获取用户全部权限，包括角色所赋于的权限
     *
     * @param appCode 应用编码
     * @param userId  用户ID
     * @return 用户拥有的全部权限
     */
    public Map<String, Integer> getAllPowers(String appCode, String userId);

    /**
     * 给用户重新分配角色
     *
     * @param appCode    应用编码
     * @param roleIdList 角色ID列表
     * @param userId     用户ID
     */
    public void assignRolesToUser(String appCode, String userId, List<String> roleIdList);

    /**
     * 给角色重新分配用户
     *
     * @param appCode    应用编码
     * @param userIdList 用户ID列表
     * @param roleId     角色ID
     */
    public void assignUsersToRole(String appCode, String roleId, List<String> userIdList);

    /**
     * 给用户重新分配权限
     *
     * @param appCode   应用编码
     * @param userId    用户ID
     * @param powerList 要分配的权限列表
     */
    public void assignPowersToUser(String appCode, String userId, List<Power> powerList);

    /**
     * 给角色重新分配权限
     *
     * @param appCode   应用编码
     * @param roleId    角色ID
     * @param powerList 要分配的权限列表
     */
    public void assignPowersToRole(String appCode, String roleId, List<Power> powerList);

    /**
     * 获取用户全部角色
     *
     * @param appCode 应用编码
     * @param userId  用户ID
     * @return 用户拥有的全部权限
     */
    public List<String> getAllRoleList(String appCode, String userId);

    /**
     * 获取角色全部用户列表
     *
     * @param appCode 应用编码
     * @param roleId  用户ID
     * @return 用户拥有的全部权限
     */
    public List<String> getUserIdListByRoleId(String appCode, String roleId);

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
     * 获取基于用户的权限,仅用户本身的权限
     *
     * @param appCode 应用编码
     * @param userId  用户ID
     * @return 用户拥有的全部权限
     */
    public Map<String, Integer> getPowersForUser(String appCode, String userId);

    /**
     * 获取基于角色的权限,仅角色本身的权限
     *
     * @param appCode 应用编码
     * @param roleId  角色ID
     * @return 角色拥有的全部权限
     */
    public Map<String, Integer> getPowersForRole(String appCode, String roleId);

}