package com.greathack.homlin.service.category;

import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.category.ICategoryDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.category.Category;
import com.greathack.homlin.pojo.category.CategorySearchCriteria;
import com.greathack.homlin.serviceinterface.category.ICategoryService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.TypeOption;
import com.greathack.utils.tools.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryService implements ICategoryService {

	private static Logger logger = LoggerFactory.getLogger(CategoryService.class);

	// 注入categoryDAO
	private ICategoryDAO categoryDAO = (ICategoryDAO) DAOFactory.createDAO("ICategoryDAO");


	/**
	 * 添加类别
	 * 
	 * @param category
	 * @param Category
	 * @return
	 * @throws ServiceException
	 * @author lbb
	 * @date 2017-3-20
	 */
	public Category addCategory(Category category) throws ServiceException {
		if(category==null){
			return null;
		}
		if(Validation.isEmpty(category.getAppCode())){
			throw new ServiceException(60001,"INVALID_PARAMS");
		}
		if(Validation.isEmpty(category.getCategoryName())){
			throw new ServiceException(60001,"INVALID_PARAMS");
		}
        if(category.getParentId()==null){
            category.setParentId(0L);
        }
		
		if(category.getParentId()==0){
			category.setParentName("root");
			category.setParents("0");
		}else{
			Category parent=categoryDAO.findById(category.getParentId());
			if(parent==null){
				throw new ServiceException(60001,"INVALID_PARAMS");
			}
			category.setParentName(parent.getCategoryName());
			category.setParents(parent.getParents()+","+parent.getCategoryId());
		}	
		
		if(category.getCategoryCode()!=null){
			Category instance=new Category();
			instance.setAppCode(category.getAppCode());
			instance.setCategoryCode(category.getCategoryCode());
			List<Category> categoryList=categoryDAO.findByExample(instance);
			if(categoryList.size()>0){
				throw new ServiceException(60006,"CATEGORY_EXIST");
			}
		}
		
		if (category.getSortNum() == null) {
			category.setSortNum(0L);
		}
		category.setCategoryId(IdCreator.createId("Category"));
		categoryDAO.add(category);
		return category;
	}

	/**
	 * 删除类别
	 * 
	 * @param categoryId
	 * @return
	 * @throws ServiceException
	 * @author lbb
	 * @date 2017-3-22
	 */
	public void delCategory(long categoryId) throws ServiceException {
		List<Category> children=getChildren(categoryId);
		if(children.size()>0){
			throw new ServiceException(60003, "NOT_A_LEAF_NODE");
		}
		categoryDAO.delete(categoryId);
	}

	/**
	 * 修改类别
	 * 
	 * @param category
	 * @return
	 * @throws ServiceException
	 * @author lbb
	 * @date 2017-3-22
	 */
	public void mdfyCategory(Category category) throws ServiceException {
		if(category==null){
			throw new ServiceException(60002, "CATEGORY_DOES_NOT_EXIST");
		}
		if(category.getParentId()==null){
			throw new ServiceException(60001,"INVALID_PARAMS");
		}
		if(category.getCategoryName()==null){
			throw new ServiceException(60001,"INVALID_PARAMS");
		}
		Category temp=categoryDAO.findById(category.getCategoryId());
		if(temp==null){
			throw new ServiceException(60002, "CATEGORY_DOES_NOT_EXIST");
		}
		if(category.getParentId()==0){
			category.setParentName("root");
			category.setParents("0");
		}else{
			Category parent=categoryDAO.findById(category.getParentId());
			if(parent==null){
				throw new ServiceException(60001,"INVALID_PARAMS");
			}
			category.setParentName(parent.getCategoryName());
			category.setParents(parent.getParents()+","+parent.getCategoryId());
		}	
		
		if(category.getCategoryCode()!=null){
			if(!Objects.equals(temp.getCategoryCode(),category.getCategoryCode())){
				Category instance=new Category();
				instance.setAppCode(category.getAppCode());
				instance.setCategoryCode(category.getCategoryCode());
				List<Category> categoryList=categoryDAO.findByExample(instance);
				if(categoryList.size()>0){
					throw new ServiceException(60006,"CATEGORY_EXIST");
				}
			}
		}		
		
		category.setSubCategoryList(getDescendants(category.getCategoryId()));
		List<Category> cates=new ArrayList<Category>();
		//父类ID变了，或者分类名称变了
		if(!category.getParentId().equals(temp.getParentId()) || !category.getCategoryName().equals(temp.getCategoryName())){			
			for(Category item:category.getSubCategoryList()){
				if(item.getParentId().equals(temp.getCategoryId())){//如是要做分类名称变了，则要改变所有子类的父类名称
					item.setParentName(category.getCategoryName());
				}
				item.setParents(item.getParents().replaceFirst(temp.getParents()+",", category.getParents()+","));
				cates.add(item);
			}
		}
		
		category.setSubCategoryList(cates);	
		
		category.setAppCode(temp.getAppCode());//appCode不变

		categoryDAO.update(category);
	}

	/**
	 * 修改类别排序 lbb 20170328
	 */
	public void sort(List<Long> categoryIdList) {
		if(categoryIdList==null){
			return;
		}
		for (int i = 0; i <= categoryIdList.size() - 1; i++) {
			Long sort = (long) categoryIdList.size();
			sort = sort - i;
			Category cate = (Category) categoryDAO.findById(categoryIdList.get(i));
			if (cate == null) {
				continue;
			}
			cate.setSortNum(sort);
			categoryDAO.update(cate);
		}

	}
	
	@Override
	public Category getCategory(long categoryId) {
		return categoryDAO.findById(categoryId);
	}

	@Override
	public Category getCategoryByCode(String appCode, String categoryCode) {
		if(categoryCode!=null){
			Category condition = new Category();
			condition.setAppCode(TypeOption.nullToEmpty(appCode));
			condition.setCategoryCode(categoryCode);
			List<Category> categoryList=categoryDAO.findByExample(condition);
			if(categoryList.size()>0){
				return categoryList.get(0);
			}
		}
		return null;
	}

	@Override
	public List<Category> getCategoryList(String appCode) {
		Category condition = new Category();
		condition.setAppCode(TypeOption.nullToEmpty(appCode));
		return categoryDAO.findByExample(condition);
	}

	@Override
	public List<Category> getParents(long categoryId) {
		List<Category> categoryList=new ArrayList<Category>();
		Category category = getCategory(categoryId);
		if (category == null) {
			return categoryList;
		}
		CategorySearchCriteria criteria=new CategorySearchCriteria();
		criteria.setAppCode(category.getAppCode());
		criteria.setParents(category.getParents());
		criteria.setPageSize(-1);
		return search(criteria);
	}

	@Override
	public List<Category> getDescendants(long categoryId) {
		return categoryDAO.getDescendants(categoryId);
	}

	@Override
	public Category getParent(long categoryId) {
		Category category = getCategory(categoryId);
		if (category == null) {
			return null;
		}
		return getCategory(category.getParentId());
	}

	@Override
	public List<Category> getChildren(long categoryId) {
		Category condition = new Category();
		condition.setParentId(categoryId);
		return categoryDAO.findByExample(condition);
	}
	

	@Override
    public boolean isLeaf(long categoryId) {
	    List<Category> children=getChildren(categoryId);
        if(children.size()>0){
            return false;
        }
        return true;
    }

    @Override
	public List<Category> search(CategorySearchCriteria criteria) {
		List<Category> categoryList=new ArrayList<Category>();
		if(criteria==null){
			return categoryList;
		}
		return categoryDAO.search(criteria);
	}
	
	@Override
	public long getSearchResultCount(CategorySearchCriteria criteria) {
		if(criteria==null){
			return 0;
		}
		String key=Integer.toString(criteria.hashCode());
		String count="0";
		Jedis jedis=null;
		try {
			jedis= SystemConfig.getJedisPool().getResource();
			if(jedis.exists(key)){
				count=jedis.get(key);
				//重新设置超时
				jedis.expire(key, Integer.parseInt(SystemConfig.getConfigData("RESULT_COUNT_EXPIRE")));
			}else{
				count=Long.toString(categoryDAO.getSearchResultCount(criteria));
				jedis.set(key, count);
				//设置超时
				jedis.expire(key, Integer.parseInt(SystemConfig.getConfigData("RESULT_COUNT_EXPIRE")));
			}			
		} catch (Exception e) {
			logger.debug("redis有异常");
		}finally{
			if(jedis!=null){
				jedis.close();
			}			
		}	
		return Long.parseLong(count);
	}	

}
