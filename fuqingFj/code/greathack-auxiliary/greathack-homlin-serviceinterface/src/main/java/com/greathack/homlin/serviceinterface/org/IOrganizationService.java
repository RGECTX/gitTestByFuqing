package com.greathack.homlin.serviceinterface.org;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.org.Organization;
import com.greathack.homlin.pojo.org.OrganizationSearchCriteria;

import java.util.List;


public interface IOrganizationService {

    /**
     * @param orgId
     * @return
     * @throws
     * @Title : 获取组织机构信息
     * @Description: 获取组织机构信息
     * @version
     * @author wbc
     * @date 2017-3-20
     */
    Organization getOrg(long orgId);

    /**
     * @param appCode
     * @return
     * @throws
     * @Title : getOrgList
     * @Description: 获取所有组织机构列表
     * @version
     * @author wbc
     * @date 2017-3-20
     */
    List<Organization> getOrgList(String appCode);

    /**
     * @param orgId
     * @return
     * @throws
     * @Title : 获取直系上级组织机构列表
     * @Description: 获取直系上级组织机构列表
     * @version
     * @author wbc
     * @date 2017-3-20
     */
    List<Organization> getParents(long orgId);

    /**
     * @param orgId
     * @return
     * @throws
     * @Title : 获取子孙组织机构列表
     * @Description: 获取子孙组织机构列表
     * @version
     * @author wbc
     * @date 2017-3-20
     */
    List<Organization> getDescendants(long orgId);

    /**
     * @param orgId
     * @return
     * @throws
     * @Title : 获取父组织机构
     * @Description: 获取父组织机构
     * @version
     * @author wbc
     * @date 2017-3-20
     */
    Organization getParent(long orgId);

    /**
     * @param orgId
     * @return
     * @throws
     * @Title : 获取子组织机构列表(按sortNum倒序排序)
     * @Description: 获取子组织机构列表
     * @version
     * @author wbc
     * @date 2017-3-20
     */
    List<Organization> getChildren(long orgId);


    /**
     * @param orgSearchCriteria
     * @return
     * @throws
     * @Title : 搜索
     * @Description: 搜索
     * @version
     * @author wbc
     * @date 2017-3-20
     */
    List<Organization> search(OrganizationSearchCriteria orgSearchCriteria);

    long getSearchResultCount(OrganizationSearchCriteria criteria);

    /**
     * 添加组织机构
     *
     * @param org
     * @return
     * @throws ServiceException
     * @author lbb
     * @date 2017-3-20
     */
    Organization addOrg(Organization org) throws ServiceException;

    /**
     * 删除组织机构
     *
     * @param orgId
     * @throws ServiceException
     * @author lbb
     * @date 2017-3-22
     */
    void delOrg(long orgId) throws ServiceException;

    /**
     * 停用组织机构
     *
     * @param orgId
     * @return
     * @throws ServiceException
     * @author
     * @date
     */
    void disableOrg(long orgId) throws ServiceException;

    /**
     * 启用组织机构
     *
     * @param orgId
     * @return
     * @throws ServiceException
     * @author
     * @date
     */
    void enableOrg(long orgId) throws ServiceException;

    /**
     * 修改组织机构
     *
     * @param org
     */
    void mdfyOrg(Organization org) throws ServiceException;

    /**
     * 修改组织机构状态
     *
     * @param orgState
     */
    void mdfyState(long orgId, int orgState) throws ServiceException;

    /**
     * 修改组织机构排序
     *
     * @param orgIdList
     */
    void sort(List<Long> orgIdList);

    /**
     * 通过部门ID查找组织名
     *
     * @param orgId
     */
    List<Organization> findOrgName(Long orgId);

}
