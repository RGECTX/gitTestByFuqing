package com.greathack.homlin.utils.poi;

import com.greathack.homlin.pojo.dict.DictData;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @author huangjh
 * @date 2020-04-13
 */
public class CommonUtils {

    /**
     * 设置文件下载头
     * @param request
     * @param fileName
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    /**
     * 通过类型及code获得字典中文名
     * @param label 值如：1
     * @param type 类型如AM_STATE
     * @param defaultLabel
     * @param dictMap
     * @return
     */
    public static String getDictValue(String label, String type, String defaultLabel, Map<String, List<DictData>> dictMap){
        List<DictData> dictDataList=dictMap.get(type);
        if (org.apache.commons.lang.StringUtils.isNotBlank(type) && org.apache.commons.lang.StringUtils.isNotBlank(label)){
            for (DictData dictData : dictDataList){
                if (label.equals(dictData.getDataCode())){
                    return dictData.getDataName();
                }
            }
        }
        return defaultLabel;
    }
}
