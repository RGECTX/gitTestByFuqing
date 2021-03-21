package com.greathack.homlin.common;

import com.greathack.homlin.exception.ServiceException;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WXUtils { 
	
	
	
	
	
	
	/**
     * 
     * @param action
     * @param json
	 * @throws ServiceException
     */
    public  static String connectWXInterface(String action,String json) throws ServiceException {

    	URL url;
    	String result =null;
    	try {

    		url = new URL(action);
    		HttpURLConnection http = (HttpURLConnection) url.openConnection();

    		http.setRequestMethod("POST");
    		http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    		http.setDoOutput(true);
    		http.setDoInput(true);

    		System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
    		System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
    		http.connect();
    		if(!CommonUtils.isStrNull(json)){
    			
    			OutputStream os = http.getOutputStream();
    			os.write(json.getBytes("UTF-8"));// 传入参数
    			os.flush();
        		os.close();
    		}

    		InputStream is = http.getInputStream();
    		int size = is.available();
    		byte[] jsonBytes = new byte[size];
    		is.read(jsonBytes);
    		result = new String(jsonBytes, "UTF-8");
    		
    		System.out.println("请求返回结果:"+result);
    	

    	} catch (Exception e) {

    		e.printStackTrace();

    	}
    	
    	return result;

    }
    
    
    
    /*public static void main(String[] args) {
    	
    	
    	String toUser="oJvwZ1bmETRclhJk7fnhWTqE7egU";
    	String content="你好";
    	String json = "{\"touser\": \""+toUser+"\",\"msgtype\": \"text\", \"text\": {\"content\": \""+content+"\"}}";
        
    	//获取口令
        String accessToken = getWXToken().getAccessToken();
        //获取请求路径
        String action = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accessToken;

        System.out.println("json:"+json);

        try {

        	connectWXInterface(action,json);

        } catch (Exception e) {

            e.printStackTrace();

        }
    	
	}*/
    
 }


