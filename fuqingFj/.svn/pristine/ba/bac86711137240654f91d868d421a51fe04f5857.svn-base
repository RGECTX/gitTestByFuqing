package com.greathack.homlin.service.user;

import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.user.ILinkItemDAO;
import com.greathack.homlin.daointerface.user.ILinkItemTypeDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.user.LinkItem;
import com.greathack.homlin.pojo.user.LinkItemType;
import com.greathack.homlin.serviceinterface.user.ILinkItemTypeService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.utils.tools.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 类说明 :
 * 
 * @author 作者 wbc
 * @version 创建时间：2017-2-20 上午11:43:40
 * 
 */
public class LinkItemTypeService implements ILinkItemTypeService {

	private static Logger logger = LoggerFactory
			.getLogger(LinkItemTypeService.class);

	// 注入ILinkItemTypeDAO
	private ILinkItemTypeDAO linkItemTypeDAO = (ILinkItemTypeDAO) DAOFactory.createDAO("ILinkItemTypeDAO");

	// 注入ILinkItemDAO
	private ILinkItemDAO linkItemDAO = (ILinkItemDAO) DAOFactory.createDAO("ILinkItemDAO");

	public LinkItemType addLinkItemType(LinkItemType linkItemType)	throws ServiceException {

		if (linkItemType == null) {
			return null;
		}

		// appCode必填
		if (Validation.isEmpty(linkItemType.getAppCode())) {
			throw new ServiceException(30009, "APP_CODE_IS_REQUIRED");
		}

		// itemTypeName必填
		if (Validation.isEmpty(linkItemType.getItemTypeName())) {
			throw new ServiceException(30010, "ITEM_TYPE_NAME_REQUIRED");
		}	

		if (!isExist(linkItemType.getAppCode(),linkItemType.getItemTypeName())) {
			// 增加记录
			linkItemType.setItemTypeId(IdCreator.createId("LinkItemType"));
			linkItemTypeDAO.add(linkItemType);
			return linkItemType;
		}else{//联系项目类型已存在
			throw new ServiceException(30002, "LINK_ITEM_TYPE_EXIST");
		}
	}

	public void delLinkItemType(long linkItemTypeId) throws ServiceException {

		LinkItemType linkItemType = (LinkItemType) linkItemTypeDAO
				.findById(linkItemTypeId);
		if (linkItemType != null) {
			// 如果该项目类型已被使用，则抛出“联系项目类型被锁定”异常
			if (isUsed(linkItemType)) {
				throw new ServiceException(30003, "LINK_ITEM_TYPE_USED");
			}
			linkItemTypeDAO.delete(linkItemTypeId);
		}

	}

	public void mdfyLinkItemType(LinkItemType linkItemType)
			throws ServiceException {

		if (linkItemType == null) {
			throw new ServiceException(30001, "INVALID_PARAMS");
		}

		// itemTypeName必填
		if (Validation.isEmpty(linkItemType.getItemTypeName())) {
			throw new ServiceException(30010, "ITEM_TYPE_NAME_REQUIRED");
		}

		LinkItemType result = getLinkItemType(linkItemType.getItemTypeId());
		// 如果联系项目类型不存在抛出“联系项目类型不存在”异常；
		if (result == null) {
			throw new ServiceException(30004, "LINK_ITEM_TYPE_NOT_EXIST");
		}

		// 如果同一个appCode下的itemTypeName重复，则抛出“联系项目类型名称已存在”异常；
		if (result.getItemTypeName().equals(linkItemType.getItemTypeName())) {
			// 名字跟本条记录的名字相同，说明是同一记录 无须修改
		} else {
			if (isExist(result.getAppCode(),linkItemType.getItemTypeName())) {
				throw new ServiceException(30002, "LINK_ITEM_TYPE_EXIST");
			}
			result.setItemTypeName(linkItemType.getItemTypeName());
			linkItemTypeDAO.update(result);
		}

	}

	public void mdfyLinkItemType(long linkItemTypeId, String linkItemTypeName)
			throws ServiceException {
		if (linkItemTypeId < 0) {
			throw new ServiceException(30001, "INVALID_PARAMS");
		}
		// itemTypeName必填
		if (Validation.isEmpty(linkItemTypeName)) {
			throw new ServiceException(30010, "ITEM_TYPE_NAME_REQUIRED");
		}

		LinkItemType result = getLinkItemType(linkItemTypeId);
		// 如果联系项目类型不存在抛出“联系项目类型不存在”异常
		if (result == null) {
			throw new ServiceException(30004, "LINK_ITEM_TYPE_NOT_EXIST");
		}

		// 如果同一个appCode下的itemTypeName重复，则抛出“联系项目类型名称已存在”异常；
		if (result.getItemTypeName().equals(linkItemTypeName)) {
			// 名字跟本条记录的名字相同，说明是同一记录 无须修改
		} else {
			if (isExist(result.getAppCode(),linkItemTypeName)) {
				throw new ServiceException(30002, "LINK_ITEM_TYPE_EXIST");
			}
			result.setItemTypeName(linkItemTypeName);
			linkItemTypeDAO.update(result);
		}
	}

	public List<LinkItemType> getLinkItemTypeList(String appCode) {
		List<LinkItemType> list=new ArrayList<LinkItemType>();
		if (Validation.isEmpty(appCode)) {
			return list;
		}

		LinkItemType condition=new LinkItemType();
		condition.setAppCode(appCode);
		return linkItemTypeDAO.findByExample(condition);
	}

	/**
	 * 判断项目类型是否已被使用
	 * 
	 * @param linkItemType
	 * @return
	 * @throws ServiceException
	 */
	private Boolean isUsed(LinkItemType linkItemType) throws ServiceException {

		if (linkItemType == null) {
			throw new ServiceException(30001, "INVALID_PARAMS");
		}
		if (linkItemType.getItemTypeId() == null
				|| linkItemType.getItemTypeId() < 0) {
			throw new ServiceException(30001, "INVALID_PARAMS");
		}
		// 查询是否被使用
		List<LinkItem> linkItemList = linkItemDAO.findByItemTypeId(linkItemType
				.getItemTypeId());
		if (linkItemList.size() == 0) {
			return false;
		}

		return true;
	}

	public LinkItemType getLinkItemType(long itemTypeId)
			throws ServiceException {
		return (LinkItemType) linkItemTypeDAO.findById(itemTypeId);
	}
	
	/**
	 * 
	 * @Title: isExist
	 * @Description: 联系项目类型是否已存在
	 * @param appCode 应用编码
	 * @param linkItemTypeName  联系项目类型名称
	 * @return 存在返回true,不存在返回false 
	 * @author greathack
	 * @date 2017年4月25日
	*/
	private boolean isExist(String appCode,String linkItemTypeName){
		LinkItemType condition=new LinkItemType();
		condition.setAppCode(appCode);
		condition.setItemTypeName(linkItemTypeName);
		List<LinkItemType> linkItemTypeList = linkItemTypeDAO.findByExample(condition);
		if(linkItemTypeList.size()==0){
			return false;
		}
		return true;
	}
}
