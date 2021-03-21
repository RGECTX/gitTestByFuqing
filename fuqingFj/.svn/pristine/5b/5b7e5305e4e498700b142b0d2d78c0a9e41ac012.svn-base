package com.greathack.homlin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置类
 * 
 * @author ruoyi
 */
public class Global
{
    private static final Logger log = LoggerFactory.getLogger(Global.class);

    private static String NAME = "application.yml";

    /**
     * 当前对象实例
     */
    private static Global global;

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = new HashMap<String, String>();

    private Global()
    {
    }

    /**
     * 静态工厂方法
     */
    public static synchronized Global getInstance()
    {
        if (global == null)
        {
            global = new Global();
        }
        return global;
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath()
    {
        return  "C:/profile/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath()
    {
        return  "upload/";
    }
}
