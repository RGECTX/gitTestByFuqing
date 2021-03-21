package com.greathack.homlin.service.system;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.SearchResult;
import com.greathack.homlin.pojo.category.Category;
import com.greathack.homlin.pojo.category.CategorySearchCriteria;
import com.greathack.homlin.pojo.system.Menu;
import com.greathack.homlin.pojo.system.MenuSearchCriteria;
import com.greathack.homlin.serviceinterface.category.ICategoryService;
import com.greathack.homlin.serviceinterface.system.IMenuService;
import com.greathack.homlin.system.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuService implements IMenuService {
    private static Logger logger = LoggerFactory.getLogger(MenuService.class);

    private static String MENU_APP_CODE= SystemConfig.getConfigData("MENU_APP_CODE");

    private static Map<Long, Menu> menus=null;

    @Autowired
    private ICategoryService categoryService;

    @Override
    public Map<Long, Menu> getMenus() {
        if(menus==null){
            MenuSearchCriteria criteria=new MenuSearchCriteria();
            criteria.setPageSize(10000);
            menus=new HashMap<Long, Menu>();
            List<Menu> menuList= search(criteria).getRecords();
            for(Menu menu:menuList){
                menus.put(menu.getMenuId(), menu);
            }
        }
        return menus;
    }

    @Override
    public Menu getMenu(long menuId) {
        Category cate=categoryService.getCategory(menuId);
        if(cate!=null){
            return cateToMenu(cate);
        }
        return null;
    }

    @Override
    public List<Menu> getMenuList() {
        List<Category> categoryList=categoryService.getCategoryList(MENU_APP_CODE);
        List<Menu> menuList=new ArrayList<Menu>();
        for(Category cate:categoryList){
            menuList.add(cateToMenu(cate));
        }
        return menuList;
    }

    @Override
    public List<Menu> getParents(long menuId) {
        List<Category> categoryList=categoryService.getParents(menuId);
        List<Menu> menuList=new ArrayList<Menu>();
        for(Category cate:categoryList){
            menuList.add(cateToMenu(cate));
        }
        return menuList;
    }

    @Override
    public List<Menu> getDescendants(long menuId) {
        List<Category> categoryList=categoryService.getDescendants(menuId);
        List<Menu> menuList=new ArrayList<Menu>();
        for(Category cate:categoryList){
            menuList.add(cateToMenu(cate));
        }
        return menuList;
    }

    @Override
    public Menu getParent(long menuId) {
        Category cate=categoryService.getParent(menuId);
        if(cate!=null){
            return cateToMenu(cate);
        }
        return null;
    }

    @Override
    public List<Menu> getChildren(long menuId) {
        List<Category> categoryList=categoryService.getChildren(menuId);
        List<Menu> menuList=new ArrayList<Menu>();
        for(Category cate:categoryList){
            menuList.add(cateToMenu(cate));
        }
        return menuList;
    }

    @Override
    public SearchResult<Menu> search(MenuSearchCriteria menuSearchCriteria) {
        SearchResult<Menu> searchResult=new SearchResult<Menu>();
        searchResult.setRecordCount(0);
        searchResult.setPage(Math.floorDiv(menuSearchCriteria.getStartLine(), menuSearchCriteria.getPageSize())+1);
        searchResult.setPageSize(menuSearchCriteria.getPageSize());

        CategorySearchCriteria criteria=new CategorySearchCriteria();
        criteria.setAppCode(MENU_APP_CODE);
        criteria.setParentId(menuSearchCriteria.getParentId());
        criteria.setKeyword(menuSearchCriteria.getKeyword());
        criteria.setKwFieldList(menuSearchCriteria.getKwFieldList());
        criteria.setSortField(menuSearchCriteria.getSortField());
        criteria.setPageSize(menuSearchCriteria.getPageSize());
        criteria.setStartLine(menuSearchCriteria.getStartLine());
        List<Category> categoryList=categoryService.search(criteria);

        List<Menu> menuList=new ArrayList<Menu>();
        for(Category cate:categoryList){
            menuList.add(cateToMenu(cate));
        }
        searchResult.setRecordCount(categoryService.getSearchResultCount(criteria));
        searchResult.setRecords(menuList);
        return searchResult;
    }

    @Override
    public Menu addMenu(Menu menu) throws ServiceException {
        Category category=menuToCate(menu);
        category=categoryService.addCategory(category);
        menu=cateToMenu(category);
        getMenus().put(category.getCategoryId(),menu);
        return menu;
    }

    @Override
    public void delMenu(long menuId) throws ServiceException {
        categoryService.delCategory(menuId);
        getMenus().remove(menuId);
    }

    @Override
    public void mdfyMenu(Menu menu) throws ServiceException {
        Category category=menuToCate(menu);
        categoryService.mdfyCategory(category);
        menu=cateToMenu(categoryService.getCategory(category.getCategoryId()));
        getMenus().put(category.getCategoryId(),menu);
    }

    @Override
    public void sort(List<Long> menuIdList) {
        categoryService.sort(menuIdList);
    }

    @Override
    public boolean isLeaf(long menuId) {
        return categoryService.isLeaf(menuId);
    }

    @Override
    public Menu getMenuByCode(String appCode, String menuCode) {
        Category category=categoryService.getCategoryByCode(MENU_APP_CODE,menuCode);
        return cateToMenu(category);
    }

    private Category menuToCate(Menu menu){
        if(menu==null){
            return null;
        }
        Category cate=new Category();
        cate.setAppCode(MENU_APP_CODE);
        cate.setCategoryCode(menu.getMenuCode());
        cate.setCategoryId(menu.getMenuId());
        cate.setCategoryName(menu.getMenuName());
        cate.setParentId(menu.getParentId());
        cate.setSortNum(menu.getSortNum());
        cate.setBak1(menu.getRemark());
        return cate;
    }

    private Menu cateToMenu(Category cate){
        if(cate==null){
            return null;
        }
        Menu menu=new Menu();
        menu.setMenuCode(cate.getCategoryCode());
        menu.setMenuId(cate.getCategoryId());
        menu.setMenuName(cate.getCategoryName());
        menu.setParentId(cate.getParentId());
        menu.setParentName(cate.getParentName());
        menu.setRemark(cate.getBak1());
        menu.setSortNum(cate.getSortNum());
        return menu;
    }
}
