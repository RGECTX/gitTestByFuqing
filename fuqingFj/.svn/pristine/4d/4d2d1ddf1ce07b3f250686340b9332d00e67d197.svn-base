/**
 * 
 */
package com.greathack.homlin.serviceinterface.user;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.user.LinkItemType;

import java.util.List;

/**
 * @author greathack
 * 
 */
public interface ILinkItemTypeService {

    /**
     * 添加联系项目类型，校验同一个appCode下的itemTypeName不重复，如果重复抛出“联系项目类型已存在”异常；如果itemTypeName为空，则抛出“参数异常”异常
     * 
     * @param linkItemType
     *            要添加的联系项目类型
     * @return {@link LinkItemType}`
     * @throws ServiceException
     */
    public LinkItemType addLinkItemType(LinkItemType linkItemType)
	    throws ServiceException;

    /**
     * 删除联系项目类型，如果该项目类型已被使用，则抛出“联系项目类型被锁定”异常
     * 
     * @param linkItemTypeId
     *            联系项目类型ID
     * @throws ServiceException
     */
    public void delLinkItemType(long linkItemTypeId) throws ServiceException;
   

    /**
     * 修改联系项目类型名称<br>
     * 如果联系项目类型不存在抛出“联系项目类型不存在”异常；<br>
     * 如果同一个appCode下的itemTypeName重复，则抛出“联系项目类型名称已存在”异常；<br>
     * 如果itemTypeName为空，则抛出“参数异常”异常
     * 
     * @param linkItemType
     * @throws ServiceException
     */
    public void mdfyLinkItemType(LinkItemType linkItemType)
	    throws ServiceException;

    /**
     * 修改联系项目类型
     * 
     * @param linkItemTypeId
     *            联系项目类型ID
     * @param linkItemTypeName
     *            类型名称
     * @throws ServiceException
     */
    public void mdfyLinkItemType(long linkItemTypeId, String linkItemTypeName)
	    throws ServiceException;

    /**
     * 获取联系项目类型列表
     * 
     * @param appCode
     *            应用编码
     * @return 联系项目类型列表
     */
    public List<LinkItemType> getLinkItemTypeList(String appCode);

    /**
     * 根据itemTypeId查LinkItemType
     * 
     * @param itemTypeId
     * @return
     */
    public LinkItemType getLinkItemType(long itemTypeId)
	    throws ServiceException;

}
