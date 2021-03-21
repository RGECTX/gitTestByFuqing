/**
 * 
 */
package com.greathack.homlin.system;

import com.greathack.homlin.common.SnowflakeIdWorker;
import com.greathack.homlin.exception.ServiceException;
import redis.clients.jedis.JedisPool;

/**
 * ID生成器
 * 
 * @author greathack
 * 
 */
public class IdCreator {

    private static JedisPool jedisPool = SystemConfig.getJedisPool();

    private static SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
    /**
     * 生成ID
     * 
     * @return ID
     */
    public static long createId(String objectName) throws ServiceException {
    	/*Jedis jedis = null;
    	try {
    	    jedis = jedisPool.getResource();
    	    long itemTypeId = jedis.incr("greathack_homlin_"+objectName);
    	    return itemTypeId;
    	} catch (Exception e) {
    	    throw new ServiceException(500, "UNKNOW_ERROR");
    	} finally {
    	    if (jedis != null) {
    		jedis.close();
    	    }
    	}*/
		return idWorker.nextId();
    }
    /**
     * 生成ID
     *
     * @return ID
     */
    public static long createId() {
        return idWorker.nextId();
    }
    
}
