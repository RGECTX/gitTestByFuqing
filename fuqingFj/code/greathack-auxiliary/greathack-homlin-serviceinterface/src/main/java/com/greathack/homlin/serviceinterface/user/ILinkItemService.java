/**
 * 
 */
package com.greathack.homlin.serviceinterface.user;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.user.LinkItem;
import com.greathack.homlin.pojo.user.LinkItemSearchCriteria;

import java.util.List;


/**
 * @author greathack
 * 
 */
public interface ILinkItemService {

    /**
     * 添加用户联系项目<br>
     * 如果userId或itemTypeId为空则抛出“参数异常”异常
     * 
     * @param linkItem
     *            用户联系项目
     * @return
     * @throws ServiceException
     */
    public LinkItem addLinkItem(LinkItem linkItem) throws ServiceException;

    /**
     * 删除用户联系项目
     * 
     * @param linkItemId
     *            联系项目ID
     */
    public void delLinkItem(long linkItemId) throws ServiceException;

    /**
     * 删除用户全部联系项目<br>
     * 如果用户不存在则抛出“用户不存在”异常；
     * 
     * @param userId
     *            用户ID
     */
    public void delUserLinkItems(long userId) throws ServiceException;

    /**
     * 修改用户联系项目 修改除了linkItemId,outId,userId等属性以外的其它信息，属性为空的不参与修改<br>
     * 如果用户联系项目不存在则抛出“用户联系项目不存在”异常；
     * 
     * @param linkItem
     *            要修改的用户联系项目
     * @throws ServiceException
     */
    public void mdfyLinkItem(LinkItem linkItem) throws ServiceException;

    /**
     * 设为默认 同一个项目类型只能有一个默认，设为默认后，其他的同一类型的联系项目全部变为非默认<br>
     * 如果用户联系项目不存在则抛出“用户联系项目不存在”异常；
     * 
     * @param linkItemId
     *            联系项目ID
     * @throws ServiceException
     */
    public void setDefault(long linkItemId) throws ServiceException;

    /**
     * 获取用户联系项目
     * 
     * @param linkItemId
     *            联系项目ID
     * @return 用户联系项目
     */
    public LinkItem getLinkItem(long linkItemId) throws ServiceException;

    /**
     * 搜索用户联系项目
     * 
     * @param criteria
     *            搜索条件
     * @return
     */
    public List<LinkItem> searchLinkItems(LinkItemSearchCriteria criteria);
    
    public long getSearchResultCount(LinkItemSearchCriteria criteria);

}
