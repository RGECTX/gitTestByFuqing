/**
 * 
 */
package com.greathack.homlin.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author greathack
 *
 */
public class SpringContextHolder implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;

	/**
     * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
     */
	/* （非 Javadoc）
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextHolder.applicationContext = applicationContext; 
	}
	
	 /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
    }
 
    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
	checkApplicationContext();
	return (T) applicationContext.getBean(name);
    }
 
    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
	checkApplicationContext();
		return (T) applicationContext.getBeansOfType(clazz);
    }
    private static void checkApplicationContext() {
		if (applicationContext == null) {
		    throw new IllegalStateException(
			    "applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
    }

}
