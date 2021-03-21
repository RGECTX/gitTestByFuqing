/**
 * 
 */
package com.greathack.homlin.service.am;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.AmUnit;
import com.greathack.homlin.pojo.AmUnitSearchCriteria;
import com.greathack.homlin.pojo.SearchResult;
import com.greathack.homlin.pojo.am.AmQuotas;
import com.greathack.homlin.pojo.org.Organization;
import com.greathack.homlin.pojo.org.OrganizationSearchCriteria;
import com.greathack.homlin.service.org.OrganizationService;
import com.greathack.homlin.serviceinterface.am.IAmQuotasService;
import com.greathack.homlin.serviceinterface.am.IAmUnitService;
import com.greathack.homlin.serviceinterface.org.IOrganizationService;
import com.greathack.homlin.system.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author greathack
 *
 */
public class AmUnitService implements IAmUnitService {
	
	private static Logger logger = LoggerFactory.getLogger(AmUnitService.class);
	private IOrganizationService organizationService=new OrganizationService();
	private IAmQuotasService amQuotasService=new AmQuotasService();

    private static String AM_UNIT_APP_CODE = SystemConfig.getConfigData("AM_UNIT_APP_CODE");
	
    private static Map<Long, AmUnit> orgs=null;

	public Map<Long, AmUnit> getOrgs() {
		if(orgs==null){
    		AmUnitSearchCriteria criteria=new AmUnitSearchCriteria();
        	criteria.setPageSize(10000); 
        	orgs=new HashMap<Long, AmUnit>();
    		List<AmUnit> orgList= new AmUnitService().search(criteria).getRecords();
    		for(AmUnit org:orgList){
    			orgs.put(org.getOrgId(), org);
    		}
    	}
		return orgs;
	}

	/* （非 Javadoc）
	 * @see com.greathack.leave.serviceinterface.IOrgService#search(com.greathack.leave.pojo.OrganizationSearchCriteria)
	 */
	@Override
	public SearchResult<AmUnit> search(AmUnitSearchCriteria criteria) {
		SearchResult<AmUnit> searchResult=new SearchResult<AmUnit>();
        searchResult.setRecordCount(0);
        searchResult.setPage(Math.floorDiv(criteria.getStartLine(), criteria.getPageSize())+1);
        searchResult.setPageSize(criteria.getPageSize());
        
        OrganizationSearchCriteria orgSearchCriteria=new OrganizationSearchCriteria();
        orgSearchCriteria.setAppCode(AM_UNIT_APP_CODE);
        orgSearchCriteria.setParentId(criteria.getParentId());
		orgSearchCriteria.setOrgId(criteria.getOrgId());
        orgSearchCriteria.setOrgLevel(criteria.getOrgLevel());
        orgSearchCriteria.setOrgType(criteria.getOrgType());
		orgSearchCriteria.setOrgRank(criteria.getOrgRank());
        orgSearchCriteria.setOutKey1(criteria.getOrgGroup());
        orgSearchCriteria.setKwFieldList(criteria.getKwFieldList());
        orgSearchCriteria.setKeyword(criteria.getKeyword());
        orgSearchCriteria.setStartLine(criteria.getStartLine());
        orgSearchCriteria.setPageSize(searchResult.getPageSize());
        orgSearchCriteria.setOrderBy(criteria.getOrderBy());
		orgSearchCriteria.setFilterList(criteria.getFilterList());
        
        List<Organization> orgList=organizationService.search(orgSearchCriteria);
        List<AmUnit> amUnitList=new ArrayList<AmUnit>();
        if(orgList.size()>0){
            for(Organization org:orgList){
            	amUnitList.add(orgToAmUnit(org));
            }
        }
        searchResult.setRecordCount(organizationService.getSearchResultCount(orgSearchCriteria));
        searchResult.setRecords(amUnitList);
        return searchResult;
	}

	/* （非 Javadoc）
	 * @see com.greathack.leave.serviceinterface.IOrgService#add(com.greathack.leave.pojo.Organization)
	 */
	@Override
	public AmUnit add(AmUnit amUnit) {
		if(amUnit==null){
			return null;
		}
		Organization org = amUnitToOrg(amUnit);
		try {
			org.setAppCode(AM_UNIT_APP_CODE);
			org=organizationService.addOrg(org);
			if(org!=null){
				//增加一条单位则增加一条辅警编制
				AmQuotas amQuotas=new AmQuotas();
				amQuotas.setCreateTime(Common.getCurrentTime());
				amQuotas.setOrgId(org.getOrgId());
				amQuotasService.add(amQuotas);
				amUnit=orgToAmUnit(org);
				getOrgs().put(org.getOrgId(),amUnit);
				return amUnit;
			}else{
				return null;
			}
		} catch (ServiceException e) {
			return null;
		}
	}

	/* （非 Javadoc）
	 * @see com.greathack.leave.serviceinterface.IOrgService#delete(java.lang.String)
	 */
	@Override
	public void delete(Long orgId) throws ServiceException {
		organizationService.delOrg(orgId);
		getOrgs().remove(orgId);
	}

	@Override
	public void disable(long orgId) throws ServiceException {
		organizationService.disableOrg(orgId);
		getOrgs().put(orgId,findById(orgId));
	}

	@Override
	public void enable(long orgId) throws ServiceException {
		organizationService.enableOrg(orgId);
		getOrgs().put(orgId,findById(orgId));
	}

	/* （非 Javadoc）
	 * @see com.greathack.leave.serviceinterface.IOrgService#update(com.greathack.leave.pojo.Organization)
	 */
	@Override
	public void update(AmUnit amUnit) throws ServiceException {
		Organization org = amUnitToOrg(amUnit);
		organizationService.mdfyOrg(org);
		getOrgs().put(amUnit.getOrgId(),findById(amUnit.getOrgId()));
	}

	/* （非 Javadoc）
	 * @see com.greathack.leave.serviceinterface.IOrgService#findById(java.lang.String)
	 */
	@Override
	public AmUnit findById(Long orgId) throws ServiceException {
		Organization org=organizationService.getOrg(orgId);
		if(org!=null){
			return orgToAmUnit(org);
		}else{
			return null;
		}			
	}
	
	@Override
	public List<AmUnit> getParents(Long orgId) {
		List<Organization> parentList=organizationService.getParents(orgId);
        List<AmUnit> orgList=new ArrayList<AmUnit>();
        if(parentList.size()>0){
            for(Organization org:parentList){
            	orgList.add(orgToAmUnit(org));
            }
        }
		return orgList;
	}

	@Override
	public List<AmUnit> getChildren(long orgId) {
		List<Organization> children=organizationService.getChildren(orgId);
        List<AmUnit> orgList=new ArrayList<AmUnit>();
        if(children.size()>0){
            for(Organization org:children){
            	orgList.add(orgToAmUnit(org));
            }
        }
		return orgList;
	}

	public AmUnit orgToAmUnit(Organization org){
		AmUnit amUnit=new AmUnit();
		amUnit.setCreateTime(org.getCreateTime());
		amUnit.setCreateBy(org.getCreateBy());
		amUnit.setOrgLevel(org.getOrgLevel());
		amUnit.setOrgGroup(org.getOutKey1());
		amUnit.setOrgType(org.getOrgType());
		amUnit.setOrgRank(org.getOrgRank());
		amUnit.setOrgCode(org.getOrgCode());		
		amUnit.setOrgId(org.getOrgId());
		amUnit.setOrgName(org.getOrgName());
		amUnit.setOrgState(org.getOrgState());
		amUnit.setParentId(org.getParentId());
		amUnit.setParentName(org.getParentName());
		amUnit.setRemark(org.getRemark());
		amUnit.setSortNum(org.getSortNum());
		amUnit.setShortName(org.getShortName());
		amUnit.setParents(org.getParents());
		//JSONObject json=JSON.parseObject(org.getBak1());
		//amUnit.setUpLeader(json.getString("upLeader"));
		return amUnit;
	}
	
	public Organization amUnitToOrg(AmUnit amUnit){
		Organization org=new Organization();
		org.setCreateTime(amUnit.getCreateTime());
		org.setCreateBy(amUnit.getCreateBy());
		org.setOutKey1(amUnit.getOrgGroup());
		org.setShortName(amUnit.getShortName());
		org.setOrgCode(amUnit.getOrgCode());
		org.setOrgId(amUnit.getOrgId());
		org.setOrgName(amUnit.getOrgName());
		org.setOrgState(amUnit.getOrgState());
		org.setParentId(amUnit.getParentId());
		org.setRemark(amUnit.getRemark());
		org.setSortNum(amUnit.getSortNum());
		org.setOrgType(amUnit.getOrgType());
		org.setOrgRank(amUnit.getOrgRank());
		//JSONObject json=new JSONObject();
        //json.put("upLeader", amUnit.getUpLeader());
        //org.setBak1(json.toJSONString());
		return org;
	}
	
	

}
