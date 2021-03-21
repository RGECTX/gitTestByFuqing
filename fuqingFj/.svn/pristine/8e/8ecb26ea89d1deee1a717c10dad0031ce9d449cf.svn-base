/**
 * 
 */
package com.greathack.homlin.service.system;

import com.alibaba.fastjson.JSON;
import com.greathack.homlin.pojo.system.AccessToken;
import com.greathack.homlin.system.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.UUID;

/**
 * @author greathack
 *
 */
public class TokenService {
	
	private static Logger logger = LoggerFactory.getLogger(TokenService.class);
			
	/**
	 * 更新提供给应用的token（appToken）<br>
	 * 如果存在旧的appToken，就把旧的appToken删除
	 * @param appCode 应用编码
	 * @return 新appToken
	 */
	public static String updateToken(String appCode,int tokenExpire){
		String accessToken=UUID.randomUUID().toString().replace("-", "");
		Jedis jedis=null;
		try {
			jedis= SystemConfig.getJedisPool().getResource();
			Set<String> keys=jedis.keys(appCode+"-*");//模糊查找systemCode，一般最多只能找到一个
			for(String key:keys){
				if(jedis.exists(key)){
					jedis.del(key);//删除旧token记录
				}
			}
			StringBuffer strBuf=new StringBuffer(appCode);
			strBuf.append("-");
			strBuf.append(accessToken);
			String tokenKey=strBuf.toString();
			jedis.set(tokenKey, appCode);
			jedis.expire(tokenKey, tokenExpire);//超时
		} catch (Exception e) {
			logger.debug("redis有异常");
		}finally{
			if(jedis!=null){
				jedis.close();
			}			
		}		
		return accessToken;
	}
	
	/**
	 * 获取appToken所对应的应用编码，超时或不存在返回null
	 * @param token 令牌号码
	 * @return 应用编码
	 */
	public static String getAppCode(String token){
		Jedis jedis=null;
		String appCode=null;
		try {
			jedis= SystemConfig.getJedisPool().getResource();
			Set<String> keys=jedis.keys("*-"+token);//模糊查找token，一般最多只能找到一个
			if(keys.isEmpty()){
				return null;
			}
			String key=keys.iterator().next();//找到token的完整key
			appCode = jedis.get(key);
		} catch (Exception e) {
			logger.debug("redis有异常");
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}		
		return appCode;
	}
	
	/********************************************************************************************************************************************/
	

    /**
     * 获取redis缓存上的accessToken
     * 
     * @return 返回json格式的字符串
     */
    private static AccessToken getAccessTokenFromRedis(String appCode) {
        Jedis jedis = null;
        AccessToken accessToken = null;
        try {
            jedis = SystemConfig.getJedisPool().getResource();
            String accessTokenStr = jedis.get(appCode);
            if (accessTokenStr == null) {
                return null;
            }
            accessToken = JSON.parseObject(accessTokenStr, AccessToken.class);
        } catch (Exception e) {
            logger.debug("redis有异常");   
            if (jedis != null) {
                jedis.close();
            }
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return accessToken;
    }

    /**
     * 把json格式的accessToken存入redis缓存
     * 
     * @param accessToken
     *            json格式的accessToken
     */
    private static void saveAccessToken(String appCode, AccessToken accessToken) {
        Jedis jedis = null;
        try {
            jedis = SystemConfig.getJedisPool().getResource();
            jedis.set(appCode, JSON.toJSONString(accessToken));
        } catch (Exception e) {         
            System.out.print(e.getMessage());
            logger.debug("redis有异常");
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
