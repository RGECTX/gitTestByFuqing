package com.greathack.homlin.service.org;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.org.IOrganizationDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.org.Organization;
import com.greathack.homlin.pojo.org.OrganizationSearchCriteria;
import com.greathack.homlin.serviceinterface.org.IOrganizationService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.TypeOption;
import com.greathack.utils.tools.Utils;
import com.greathack.utils.tools.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class OrganizationService implements IOrganizationService {

    private static Logger logger = LoggerFactory.getLogger(OrganizationService.class);

    // 注入orgDAO
    private IOrganizationDAO orgDAO = (IOrganizationDAO) DAOFactory.createDAO("IOrganizationDAO");


    /**
     * 添加组织机构
     *
     * @param org
     * @return
     * @throws ServiceException
     * @author lbb
     * @date 2017-3-20
     */
    public Organization addOrg(Organization org) throws ServiceException {
        if (org == null) {
            return null;
        }
        if (Validation.isEmpty(org.getAppCode())) {
            throw new ServiceException(110001, "INVALID_PARAMS");
        }
        if (org.getParentId() == null) {
            org.setParentId(0L);
        }

        if (org.getOrgState() == null) {
            org.setOrgState(Organization.ORG_STATE_NORMAL);
        } else {
            List<Long> orgStateList = Utils.longRecountToArrayList(org.getOrgState());
            if (!orgStateList.contains(Long.valueOf(org.getOrgState().toString()))) {
                logger.info("orgState 不在取值范围内");
                throw new ServiceException(110001, "INVALID_PARAMS");
            }
        }

        if (org.getParentId() == 0) {
            org.setParentName("root");
            org.setParents("0");
            org.setOrgLevel(1);
        } else {
            Organization parent = orgDAO.findById(org.getParentId());
            if (parent == null) {
                throw new ServiceException(110001, "INVALID_PARAMS");
            }
            org.setParentName(parent.getOrgName());
            org.setParents(parent.getParents() + "," + parent.getOrgId());
            org.setOrgLevel(parent.getOrgLevel() + 1);
        }

        if (org.getSortNum() == null) {
            org.setSortNum(0L);
        }
        org.setOrgId(IdCreator.createId("Organization"));
        org.setCreateTime(Common.getCurrentTime());
        orgDAO.add(org);
        return org;
    }

    /**
     * 删除组织机构
     *
     * @param orgId
     * @return
     * @throws ServiceException
     * @author lbb
     * @date 2017-3-22
     */
    public void delOrg(long orgId) throws ServiceException {
        orgDAO.delete(orgId);
    }

    /**
     * 停用组织机构
     *
     * @param orgId
     * @return
     * @throws ServiceException
     * @author
     * @date
     */
    public void disableOrg(long orgId) throws ServiceException {
        Organization org = orgDAO.findById(orgId);
        if (org == null) {
            throw new ServiceException(110002, "ORG_DOES_NOT_EXIST");
        }
        org.setSubOrgList(getDescendants(org.getOrgId()));
        List<Organization> cates = new ArrayList<Organization>();
        //停用组织机构会把当前的组织机构以及它的所有下级机构停用
        for (Organization item : org.getSubOrgList()) {
            if (item.getOrgState() == Organization.ORG_STATE_NORMAL) {
                item.setOrgState(Organization.ORG_STATE_STOPED);
                cates.add(item);
            }
        }

        org.setSubOrgList(cates);
        org.setOrgState(Organization.ORG_STATE_STOPED);
        orgDAO.update(org);
    }

    /**
     * 启用组织机构
     *
     * @param orgId
     * @return
     * @throws ServiceException
     * @author
     * @date
     */
    public void enableOrg(long orgId) throws ServiceException {
        Organization org = orgDAO.findById(orgId);
        if (org == null) {
            throw new ServiceException(110002, "ORG_DOES_NOT_EXIST");
        }
        org.setSubOrgList(getParents(org.getOrgId()));
        List<Organization> cates = new ArrayList<Organization>();
        //启用组织机构会把当前的组织机构以及它的所有直系上级机构启用
        for (Organization item : org.getSubOrgList()) {
            if (item.getOrgState() == Organization.ORG_STATE_STOPED) {
                item.setOrgState(Organization.ORG_STATE_NORMAL);
                cates.add(item);
            }
        }
        org.setSubOrgList(cates);
        org.setOrgState(Organization.ORG_STATE_NORMAL);
        orgDAO.update(org);
    }

    /**
     * 修改组织机构
     *
     * @param org
     * @return
     * @throws ServiceException
     * @author lbb
     * @date 2017-3-22
     */
    public void mdfyOrg(Organization org) throws ServiceException {
        if (org == null) {
            throw new ServiceException(110002, "ORG_DOES_NOT_EXIST");
        }
        if (org.getParentId() == null) {
            org.setParentId(0L);
        }
        Organization temp = orgDAO.findById(org.getOrgId());
        if (temp == null) {
            throw new ServiceException(110002, "ORG_DOES_NOT_EXIST");
        }
        if (org.getParentId() == 0) {
            org.setParentName("root");
            org.setParents("0");
            org.setOrgLevel(1);
        } else {
            Organization parent = orgDAO.findById(org.getParentId());
            if (parent == null) {
                throw new ServiceException(110001, "INVALID_PARAMS");
            }
            org.setParentName(parent.getOrgName());
            org.setParents(parent.getParents() + "," + parent.getOrgId());
            org.setOrgLevel(parent.getOrgLevel() + 1);
        }


        org.setSubOrgList(getDescendants(org.getOrgId()));
        List<Organization> cates = new ArrayList<Organization>();
        //父类ID变了，或者分类名称变了
        if (!org.getParentId().equals(temp.getParentId()) || !org.getOrgName().equals(temp.getOrgName())) {
            for (Organization item : org.getSubOrgList()) {
                if (item.getParentId().equals(temp.getOrgId())) {//如是要做分类名称变了，则要改变所有子类的父类名称
                    item.setParentName(org.getOrgName());
                }
                item.setParents(item.getParents().replaceFirst(temp.getParents() + ",", org.getParents() + ","));
                item.setOrgLevel(org.getOrgLevel() + 1);//父类变了，子类的层级也跟着变
                cates.add(item);
            }
        }

        org.setSubOrgList(cates);

        org.setAppCode(temp.getAppCode());//appCode不变
        org.setOrgState(temp.getOrgState());//orgState不变
        org.setSortNum(temp.getSortNum());//sortNum不变
        org.setCreateTime(temp.getCreateTime());//createTime不变

        orgDAO.update(org);
    }

    @Override
    public void mdfyState(long orgId, int orgState) throws ServiceException {
	    /*if(orgState<4){
	        logger.info("orgState 不在取值范围内");
            throw new ServiceException(110001, "INVALID_PARAMS");
	    }*/
        List<Long> orgStateList = Utils.longRecountToArrayList(orgState);
        if (!orgStateList.contains(Long.valueOf(orgState))) {
            logger.info("orgState 不在取值范围内");
            throw new ServiceException(110001, "INVALID_PARAMS");
        }
        Organization org = orgDAO.findById(orgId);
        if (org == null) {
            throw new ServiceException(110002, "ORG_DOES_NOT_EXIST");
        }
        org.setOrgState(orgState);
        orgDAO.update(org);
    }

    /**
     * 修改组织机构排序
     */
    public void sort(List<Long> orgIdList) {
        if (orgIdList == null) {
            return;
        }
        for (int i = 0; i <= orgIdList.size() - 1; i++) {
            Long sort = (long) orgIdList.size();
            sort = sort - i;
            Organization cate = (Organization) orgDAO.findById(orgIdList.get(i));
            if (cate == null) {
                continue;
            }
            cate.setSortNum(sort);
            orgDAO.update(cate);
        }

    }

    @Override
    public List<Organization> findOrgName(Long orgId) {
        return orgDAO.findOrgName(orgId);
    }

    @Override
    public Organization getOrg(long orgId) {
        return orgDAO.findById(orgId);
    }

    @Override
    public List<Organization> getOrgList(String appCode) {
        Organization condition = new Organization();
        condition.setAppCode(TypeOption.nullToEmpty(appCode));
        return orgDAO.findByExample(condition);
    }

    @Override
    public List<Organization> getParents(long orgId) {
        List<Organization> orgList = new ArrayList<Organization>();
        Organization org = getOrg(orgId);
        if (org == null) {
            return orgList;
        }
        OrganizationSearchCriteria criteria = new OrganizationSearchCriteria();
        criteria.setAppCode(org.getAppCode());
        criteria.setParents(org.getParents());
        criteria.setPageSize(1000);
        return search(criteria);
    }

    @Override
    public List<Organization> getDescendants(long orgId) {
        return orgDAO.getDescendants(orgId);
    }

    @Override
    public Organization getParent(long orgId) {
        Organization org = getOrg(orgId);
        if (org == null) {
            return null;
        }
        return getOrg(org.getParentId());
    }

    @Override
    public List<Organization> getChildren(long orgId) {
        Organization condition = new Organization();
        condition.setParentId(orgId);
        return orgDAO.findByExample(condition);
    }


    @Override
    public List<Organization> search(OrganizationSearchCriteria criteria) {
        List<Organization> orgList = new ArrayList<Organization>();
        if (criteria == null) {
            return orgList;
        }
        return orgDAO.search(criteria);
    }

    @Override
    public long getSearchResultCount(OrganizationSearchCriteria criteria) {
        if (criteria == null) {
            return 0;
        }
        String key = Integer.toString(criteria.hashCode());
        String count = "0";
        Jedis jedis = null;
        try {
            jedis = SystemConfig.getJedisPool().getResource();
            if (jedis.exists(key)) {
                count = jedis.get(key);
                //重新设置超时
                jedis.expire(key, Integer.parseInt(SystemConfig.getConfigData("RESULT_COUNT_EXPIRE")));
            } else {
                count = Long.toString(orgDAO.getSearchResultCount(criteria));
                jedis.set(key, count);
                //设置超时
                jedis.expire(key, Integer.parseInt(SystemConfig.getConfigData("RESULT_COUNT_EXPIRE")));
            }
        } catch (Exception e) {
            logger.debug("redis有异常");
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return Long.parseLong(count);
    }

}
