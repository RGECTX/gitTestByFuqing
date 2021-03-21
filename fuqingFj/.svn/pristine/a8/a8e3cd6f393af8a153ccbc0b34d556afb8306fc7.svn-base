package com.greathack.homlin.service;

import com.alibaba.fastjson.JSON;
import com.greathack.homlin.pojo.AdminLoginSession;
import com.greathack.homlin.system.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

public class AdminLoginSessionService {

    private static Logger logger = LoggerFactory.getLogger(AdminLoginSessionService.class);
    
    /**
     * 获取redis缓存上的AdminLoginSession
     * @return 返回json格式的字符串
     */
    public static AdminLoginSession getLoginSession(String loginCode){
        Jedis jedis=null;
        AdminLoginSession adminLoginSession=null;
        try {
            String loginCodeKey="greathack_homlin_loginCode_"+loginCode;
            jedis= SystemConfig.getJedisPool().getResource();
            String adminLoginSessionStr=jedis.get(loginCodeKey);
            if(adminLoginSessionStr==null){
                return null;
            }
            adminLoginSession=JSON.parseObject(adminLoginSessionStr, AdminLoginSession.class);
        } catch (Exception e) {
            logger.debug("redis有异常："+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }       
        return adminLoginSession;
    }
    
    /**
     * 根据登录名获取redis缓存上的AdminLoginCode
     * @return 返回json格式的字符串
     */
    public static String getLoginCode(String loginName){
        Jedis jedis=null;
        String adminLoginCode=null;
        try {
            String loginNameKey="greathack_homlin_admin_"+loginName;
            jedis= SystemConfig.getJedisPool().getResource();
            adminLoginCode=jedis.get(loginNameKey);
            if(adminLoginCode==null){
                return null;
            }
            return adminLoginCode;
        } catch (Exception e) {
            logger.debug("redis有异常："+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }       
        return adminLoginCode;
    }
    
    /**
     * 把json格式的AdminLoginSession存入redis缓存
     * @param adminLoginSession 
     */
    public static void saveLoginSession(String loginCode, AdminLoginSession adminLoginSession){
        Jedis jedis=null;
        try {
            int loginExpire=Integer.valueOf(SystemConfig.getConfigData("LOGIN_EXPIRE"));
            String loginNameKey="greathack_homlin_admin_"+adminLoginSession.getAdmin().getLoginName();
            String loginCodeKey="greathack_homlin_loginCode_"+loginCode;
            jedis= SystemConfig.getJedisPool().getResource();
            jedis.set(loginCodeKey, JSON.toJSONString(adminLoginSession));
            jedis.set(loginNameKey, loginCode);
            jedis.expire(loginCodeKey, loginExpire);//超时
            jedis.expire(loginNameKey, loginExpire);//超时
        } catch (Exception e) {
            logger.debug("redis有异常："+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }   
    }
    
    /**
     * 从redis缓存中删除adminLoginSession
     * @param loginCode 登录码
     */
    public static void deleteLoginSession(String loginCode){
        Jedis jedis=null;
        try {
            String loginCodeKey="greathack_homlin_loginCode_"+loginCode;
            jedis= SystemConfig.getJedisPool().getResource();
            jedis.del(loginCodeKey);
        } catch (Exception e) {
            logger.debug("redis有异常："+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }   
    }
    
    /**
     * 从redis缓存中删除adminLoginCode
     * @param loginName 登录名
     */
    public static void deleteLoginName(String loginName){
        Jedis jedis=null;
        try {
            String loginNameKey="greathack_homlin_loginCode_"+loginName;
            jedis= SystemConfig.getJedisPool().getResource();
            jedis.del(loginNameKey);
        } catch (Exception e) {
            logger.debug("redis有异常："+e.getMessage());
        }finally{
            if(jedis!=null){
                jedis.close();
            }
        }   
    }
}
