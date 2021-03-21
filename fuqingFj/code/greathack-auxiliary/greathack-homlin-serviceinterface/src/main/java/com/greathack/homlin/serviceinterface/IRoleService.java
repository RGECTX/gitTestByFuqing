package com.greathack.homlin.serviceinterface;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Role;
import com.greathack.homlin.pojo.RoleSearchCriteria;

import java.util.List;

public interface IRoleService {

	Role addRole(Role role) throws ServiceException;
	
	void updateRole(Role role);
	
	void deleteRole(Long roleId);

	Role findById(Long roleId);

	List<Role> search(RoleSearchCriteria criteria);

	Long getSearchResultCount(RoleSearchCriteria criteria);
	
}
