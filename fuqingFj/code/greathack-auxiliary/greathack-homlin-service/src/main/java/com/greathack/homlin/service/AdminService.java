package com.greathack.homlin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.Admin.State;
import com.greathack.homlin.pojo.AdminListResult;
import com.greathack.homlin.pojo.AdminSearchCriteria;
import com.greathack.homlin.pojo.SearchResult;
import com.greathack.homlin.pojo.login.LoginInstance;
import com.greathack.homlin.pojo.login.LoginType;
import com.greathack.homlin.pojo.user.User;
import com.greathack.homlin.pojo.user.UserSearchCriteria;
import com.greathack.homlin.service.login.LoginInstanceService;
import com.greathack.homlin.service.login.LoginService;
import com.greathack.homlin.service.user.UserService;
import com.greathack.homlin.serviceinterface.IAdminService;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AdminService implements IAdminService {

    private static Logger logger = LoggerFactory.getLogger(AdminService.class);
    private static String ADMIN_APP_CODE = SystemConfig.getConfigData("ADMIN_APP_CODE");
    private static String ADMIN_LOGIN_APP_CODE = SystemConfig.getConfigData("ADMIN_LOGIN_APP_CODE");

    // @Autowired
    //private IAdminPowerItemService adminPowerItemService;

    //@Autowired
    private UserService userService = new UserService();

    //@Autowired
    private LoginInstanceService loginInstanceService = new LoginInstanceService();

    //@Autowired
    private LoginService loginService = new LoginService();

//    @Autowired
//    private IAdminPermissionService adminPermissionService;

//    @Autowired
//    private IRoleService roleService;

    private User adminToUser(Admin admin) {
        User user = new User();
        user.setUserState(admin.getState());
        user.setCreateTime(admin.getCreateTime());
        user.setOutKey1(admin.getAmUnitId());
        JSONObject json = new JSONObject();
        json.put("lastLoginTime", admin.getLastLoginTime());
        user.setBak1(json.toJSONString());
        user.setNickName(admin.getLoginName());
        user.setSex(admin.getSex());
        user.setUserId(admin.getUserId());
        user.setUserName(admin.getName());
        user.setMobile(admin.getMobile());
        user.setIdcard(admin.getIdcard());
        return user;
    }

    private Admin userToAdmin(User user) {
        Admin admin = new Admin();
        admin.setState(user.getUserState());
        admin.setCreateTime(user.getCreateTime());
        admin.setAmUnitId(user.getOutKey1());
        JSONObject json = JSON.parseObject(user.getBak1());
        admin.setLastLoginTime(json.getLong("lastLoginTime"));
        admin.setLoginName(user.getNickName());
        admin.setSex(user.getSex());
        admin.setUserId(user.getUserId());
        admin.setName(user.getUserName());
        admin.setMobile(user.getMobile());
        admin.setIdcard(user.getIdcard());
        //admin.setRoleId(user.getOutKey2());
        return admin;
    }

    @Override
    public Admin add(Admin admin) throws ServiceException {

        if (Validation.isEmpty(admin.getLoginName())) {
            throw new ServiceException(200001, "INVALID_PARAMS");
        }
        if (Validation.isEmpty(admin.getPassword())) {
            throw new ServiceException(200001, "INVALID_PARAMS");
        }

        UserSearchCriteria criteria = new UserSearchCriteria();
        criteria.setAppCode(ADMIN_APP_CODE);
        criteria.setKeyword(admin.getLoginName());
        criteria.setKwFields(UserSearchCriteria.KW_FIELDS_NICK_NAME);
        List<User> userList = userService.searchUsers(criteria);
        if (userList.size() > 0) {
            throw new ServiceException(200050, "USER_EXIST");
        }

        admin.setState(State.normal.value());
        User user = adminToUser(admin);
        user.setAppCode(ADMIN_APP_CODE);
        user = userService.addUser(user);
        admin.setUserId(user.getUserId());
        admin.setCreateTime(user.getCreateTime());
        //admin=userToAdmin(user);
        LoginInstance loginInstance = new LoginInstance();
        loginInstance.setAppCode(ADMIN_LOGIN_APP_CODE);
        loginInstance.setInstanceId(String.valueOf(admin.getUserId()));

        //用户名和密码登录
        LoginType loginType = new LoginType();
        loginType.setAppCode(ADMIN_LOGIN_APP_CODE);
        loginType.setLoginName("LoginName_" + admin.getLoginName());
        loginType.setPassword(admin.getPassword());

        List<LoginType> loginTypeList = new ArrayList<LoginType>();
        loginTypeList.add(loginType);
        try {
            loginInstanceService.registerTx(ADMIN_LOGIN_APP_CODE, String.valueOf(admin.getUserId()), loginTypeList);
        } catch (ServiceException e) {
            return null;
        }

        return admin;
    }


    /* (non-Javadoc)
     * @see com.greathack.enqugo.serviceinterface.IAdminService#delAdmin(java.lang.String)
     */
    @Override
    public void delete(Long adminId) throws ServiceException {
        loginInstanceService.delLoginInstance(ADMIN_LOGIN_APP_CODE, String.valueOf(adminId));
        userService.delUser(Long.valueOf(adminId));
    }

    /* (non-Javadoc)
     * @see com.greathack.enqugo.serviceinterface.IAdminService#mdfyAdmin(com.greathack.enqugo.pojo.Admin)
     */
    @Override
    public void mdfy(Admin admin) throws ServiceException {

//    	boolean hasPower = adminPermissionService.hasPower(loginAdmin.getAdminId(), powerCode);
//    	if(!hasPower){
//    		 throw new ServiceException(200005,"NO_POWER");
//    	}
        userService.mdfyUser(adminToUser(admin));
    }

    /* (non-Javadoc)
     * @see com.greathack.enqugo.serviceinterface.IAdminService#mdfyPwd(java.lang.String, java.lang.String)
     */
    @Override
    public void mdfyPwd(String loginName, String newPwd) throws ServiceException {
        loginInstanceService.mdfyPassword(ADMIN_LOGIN_APP_CODE, "LoginName_" + loginName, newPwd);
    }

    /* (non-Javadoc)
     * @see com.greathack.enqugo.serviceinterface.IAdminService#mdfyAdminState(java.lang.String, int)
     */
    public void mdfyState(Long adminId, State adminState) throws ServiceException {
        userService.mdfyUserState(Long.valueOf(adminId), adminState.value());
    }

    @Override
    public LoginInstance login(String loginName, String password) throws ServiceException {
        return loginService.login(ADMIN_LOGIN_APP_CODE, "LoginName_" + loginName, password, "");
    }

    @Override
    public LoginInstance loginByMobile(String mobile) throws ServiceException {
        return loginService.login(ADMIN_LOGIN_APP_CODE, mobile, mobile, "");
    }

    /* (non-Javadoc)
     * @see com.greathack.enqugo.serviceinterface.IAdminService#logout(java.lang.String)
     */
    @Override
    public void logout(String loginCode) throws ServiceException {
        loginService.logout(loginCode, 2);
    }

    /* (non-Javadoc)
     * @see com.greathack.enqugo.serviceinterface.IAdminService#getAdmin(java.lang.String)
     */
    @Override
    public Admin getAdmin(String loginCode) {
        if (loginCode == null) {
            return null;
        }
        try {
            String instanceId = loginInstanceService.getInstanceId(loginCode);
            User user = userService.getUser(Long.valueOf(instanceId));
            if (user == null) {
                return null;
            }
            Admin admin = userToAdmin(user);
            return admin;
        } catch (ServiceException e) {
            return null;
        }
    }

    @Override
    public Admin getAdminById(Long adminId) {
        User user = userService.getUser(Long.valueOf(adminId));
        if (user == null) {
            return null;
        }
        Admin admin = userToAdmin(user);
        return admin;
    }

    @Override
    public AdminListResult getListByUserIds(List<Long> userIdList) {
        List<User> userList=userService.getUserListByIds(userIdList);
        AdminListResult adminListResult=new AdminListResult();
        List<Admin> adminList=new ArrayList<Admin>();
        List<Long> existUserList = new ArrayList<>();
        if(userList.size()>0){
            for(User user:userList){
                adminList.add(userToAdmin(user));
                existUserList.add(user.getUserId());
            }
        }
        adminListResult.setUserList(adminList);

        List<String> noExistUserList = new ArrayList<>();
        for (Long useId : userIdList) {
            if (!existUserList.contains(useId)) {
                noExistUserList.add(String.valueOf(useId));
            }
        }
        adminListResult.setNoExistUserList(noExistUserList);

        return adminListResult;
    }

    /* (non-Javadoc)
     * @see com.greathack.enqugo.serviceinterface.IAdminService#searchAdmins(com.greathack.enqugo.pojo.AdminSearchCriteria)
     */
    @Override
    public SearchResult<Admin> searchAdmins(AdminSearchCriteria criteria) {
        SearchResult<Admin> searchResult = new SearchResult<Admin>();
        searchResult.setRecordCount(0);
        searchResult.setPage(Math.floorDiv(criteria.getStartLine(), criteria.getPageSize()) + 1);
        searchResult.setPageSize(criteria.getPageSize());

        UserSearchCriteria userSearchCriteria = new UserSearchCriteria();
        userSearchCriteria.setAppCode(ADMIN_APP_CODE);
        userSearchCriteria.setUserState(criteria.getState());
        userSearchCriteria.setOrgId(criteria.getAmUnitId());
        userSearchCriteria.setOrgCode(criteria.getOrgCode());
        userSearchCriteria.setKwFieldList(criteria.getKwFieldList());
        userSearchCriteria.setKeyword(criteria.getKeyword());
        userSearchCriteria.setFilterList(criteria.getFilterList());

        userSearchCriteria.setStartLine(criteria.getStartLine());
        userSearchCriteria.setPageSize(searchResult.getPageSize());
        List<User> userList = userService.searchUsers(userSearchCriteria);
        List<Admin> adminList = new ArrayList<Admin>();
        if (userList.size() > 0) {
            for (User user : userList) {
                adminList.add(userToAdmin(user));
            }
        }
        searchResult.setRecordCount(userService.getSearchResultCount(userSearchCriteria));
        searchResult.setRecords(adminList);
        return searchResult;
    }

    @Override
    public List<Admin> getListByUserIdcard(String idcard) {
        User cond = new User();
        cond.setAppCode(ADMIN_APP_CODE);
        cond.setNickName(idcard);
        List<User> userList=userService.findByExample(cond);
        List<Admin> adminList=new ArrayList<Admin>();
        if(userList.size()>0){
            for(User user:userList){
                adminList.add(userToAdmin(user));
            }
        }
        return adminList;
    }

    public static void main(String[] args) {
        AdminService adminService = new AdminService();
    }

}
