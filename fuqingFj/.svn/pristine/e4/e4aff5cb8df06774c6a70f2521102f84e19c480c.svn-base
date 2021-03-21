
package com.greathack.homlin.common;

import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.security.AES;
import com.greathack.utils.tools.TypeOption;

/**
 * 
 * @Description: TODO
 * @author greathack
 * @date 2017年4月25日
 * @version V1.0
 */
public class Common {
	
	/**
	 * 
	 * @Title: outIdToId
	 * @Description: 外部ID转成内部ID
	 * @param outId 外部ID
	 * @param appCode 应用编码
	 * @return 内部ID
	 * @author greathack
	 * @date 2017年4月27日
	*/
	public static long outIdToId(String outId,String appCode) throws Exception{
		
		return Long.parseLong(AES.hexStrDecryptToStr(outId, AES.getSecretKey(appCode)));		
	}
	
	/**
	 * 
	 * @Title: idToOutId
	 * @Description: 内部ID转成外部ID
	 * @param id 内部ID
	 * @param appCode 应用编码
	 * @return String
	 * @author greathack
	 * @date 2017年4月27日
	*/
	public static String idToOutId(long id,String appCode){
		String outId=null;
		try {
			outId= AES.encryptToHexStr(Long.toString(id), AES.getSecretKey(appCode));
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return outId;
	}
    
    /**
     * 
     * @Title: getCurrentTime
     * @Description: 获取当前时间戳
     * @return 当前时间戳
     * @author greathack
     * @date 2017年5月3日
    */
    public static long getCurrentTime(){
    	return System.currentTimeMillis();
    }
	
    public static long getAccessTokenExpireTime(){
	    int tokenExpire=TypeOption.nullToIntZero(Integer.valueOf(SystemConfig.getConfigData("TOKEN_EXPIRE")));
	    return Common.getCurrentTime()+tokenExpire*1000;
	}
    
    public static long getLoginCodeExpireTime(){
        int loginExpire=TypeOption.nullToIntZero(Integer.valueOf(SystemConfig.getConfigData("LOGIN_EXPIRE")));
        return Common.getCurrentTime()+loginExpire*1000;
    }
	

}
