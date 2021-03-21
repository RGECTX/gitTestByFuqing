/**
 *
 */
package com.greathack.homlin.daointerface.org;

import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.org.Organization;
import com.greathack.homlin.pojo.org.OrganizationSearchCriteria;

import java.util.List;


/**
 * @author greathack
 *
 */
public interface IOrganizationDAO extends BaseDAO<Organization> {

    /**
     * @Title : 搜索
     * @Description: 搜索
     * @version
     * @param orgSearchCriteria
     * @return
     * @throws
     * @author wbc
     * @date 2017-3-21
     */
    List<Organization> search(OrganizationSearchCriteria orgSearchCriteria);

    long getSearchResultCount(OrganizationSearchCriteria criteria);

    /**
     * @Title : getDescendants
     * @Description: 根据ID 获取子孙类别列表
     * @version
     * @param orgId
     * @return
     * @throws
     * @author wbc
     * @date 2017-3-21
     */
    List<Organization> getDescendants(long orgId);

    /*通过orgCode加载部门及部门以下的部门*/
    List<Organization> findByParentId(Organization organization);

    List<Organization> searchByExample(OrganizationSearchCriteria orgSearchCriteria);

    Organization findByOrgCode(String orgCode);

    List<Organization> findOrgName(long orgId);

}
