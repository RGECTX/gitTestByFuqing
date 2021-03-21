package com.greathack.homlin.system;

import com.greathack.homlin.common.SpringContextHolder;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.system.IConfigDAO;
import com.greathack.homlin.pojo.system.Config;
import com.greathack.utils.redis.JedisPoolBuilder;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class SystemConfig {

	private static SystemConfigBean systemConfigBean=(SystemConfigBean) SpringContextHolder.getBean("systemConfig");
	private static Map<String, Config> configs = new HashMap<String, Config>();
	private static JedisPool jedisPool=null;
	static{
		ResourceBundle bundle = ResourceBundle.getBundle("redis");
		if (bundle == null){
			throw new IllegalArgumentException("[redis.properties] is not found");
		}
		jedisPool=JedisPoolBuilder.create(bundle.getString("serverAddr"))
				.setMaxActive(Integer.valueOf(bundle.getString("maxActive")))
				.setMaxIdle(Integer.valueOf(bundle.getString("maxIdle")))
				.setMaxWaitMillis(Long.valueOf(bundle.getString("maxWaitMillis")))
				.setTestOnBorrow(Boolean.valueOf(bundle.getString("testOnBorrow")))
				.setPort(Integer.valueOf(bundle.getString("port")))
				.setPassword(bundle.getString("password"))
				.setDatabase(Integer.valueOf(bundle.getString("database")))
				.setSoftMinEvictableIdleTimeMillis(Long.valueOf(bundle.getString("softMinEvictableIdleTimeMillis")))
				.setTimeout(Integer.valueOf(bundle.getString("timeout")))
				.builder();
	}

	public static SystemConfigBean getSystemConfig(){
		return systemConfigBean;
	}

	public static JedisPool getJedisPool(){
		return jedisPool;
	}

	/*public static Map<String, String> getLeaveTypes() {
		return leaveTypes;
	}*/
	public static Config getConfig(String dataKey){
		if (!configs.containsKey(dataKey)) {
			loadConfigs();//加载配置
		}
		if(!configs.containsKey(dataKey)){
			return null;
		}
		return configs.get(dataKey);
	}

	public static String getConfigData(String dataKey){
		Config config=getConfig(dataKey);
		if(config==null){
			return null;
		}
		return config.getData();
	}

	/**
	 * 修改参数
	 * @param dataKey
	 * @param data
	 */
	public static void setConfig(String dataKey,String data){
		IConfigDAO configDAO=(IConfigDAO) DAOFactory.createDAO("IConfigDAO");
		Config config=(Config)configDAO.findById(dataKey);
		config.setData(data);
		configDAO.update(config);
		configs.put(dataKey, config);
	}


	/**
	 * 加载配置
	 */
	public static void loadConfigs(){
		IConfigDAO configDAO=(IConfigDAO) DAOFactory.createDAO("IConfigDAO");
		List<Config> configList=configDAO.findAll();
		for(Config config:configList){
			configs.put(config.getDataKey(), config);
		}
	}

}
