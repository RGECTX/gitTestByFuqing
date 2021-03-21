package com.greathack.homlin.service;

import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.IRoleDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Role;
import com.greathack.homlin.pojo.RoleSearchCriteria;
import com.greathack.homlin.serviceinterface.IAdminPermissionService;
import com.greathack.homlin.serviceinterface.IRoleService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.utils.tools.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleService implements IRoleService {
	
	private static Logger logger = LoggerFactory.getLogger(RoleService.class);
	private static IRoleDAO roleDAO = (IRoleDAO) DAOFactory.createDAO("IRoleDAO");
    private static Map<Long,String> roles=null;
    {
    	if(roles==null){
			RoleSearchCriteria criteria=new RoleSearchCriteria();
			criteria.setPageSize(10000);
			roles=new HashMap<Long,String>();
			List<Role> roleList= roleDAO.search(criteria);;
			for(Role role:roleList){
				roles.put(role.getRoleId(), role.getRoleName());
			}
		}
	}
	
	public static Map<Long, String> getRoles() {
		return roles;
	}

	@Autowired
    private IAdminPermissionService adminPermissionService;

	public Role addRole(Role role) throws ServiceException {
		
		if(role.getRoleState()==null){
			role.setRoleState(1);
		}
		
		List<Long> roleStateList=Utils.longRecountToArrayList(role.getRoleState());
		if(!roleStateList.contains(Long.valueOf(Integer.valueOf(role.getRoleState()).toString()))){
			logger.info("roleState 不在取值范围内");
			throw new ServiceException(160001, "INVALID_PARAMS");
		}
		try {
			role.setRoleId(IdCreator.createId("role"));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		roleDAO.add(role);
		RoleService.getRoles().put(role.getRoleId(), role.getRoleName());
		return role;
	}

	public void updateRole(Role role) {
		roleDAO.update(role);
		RoleService.getRoles().put(role.getRoleId(), role.getRoleName());
		
	}

	public void deleteRole(Long roleId) {
		adminPermissionService.removeRole(String.valueOf(roleId));
		roleDAO.delete(roleId);
		
	}

	public Role findById(Long roleId) {
		
		return roleDAO.findById(roleId);
	}

	public List<Role> search(RoleSearchCriteria criteria) {
	
		return roleDAO.search(criteria);
		
	}

	public Long getSearchResultCount(RoleSearchCriteria criteria) {
		Long searchResultCount = roleDAO.getSearchResultCount();
		return searchResultCount;
	}
	
	

}
