package com.greathack.homlin.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonUtils {
	
	public static String getJsonStrFromList(List list){
		
		if(isListNull(list)){
			throw new RuntimeException("list为空");
		}
		
		
		return JSON.toJSONString(list);
		
		
		
	}
	
	public static void main(String[] args) {
		/*List<Object> li = new ArrayList<Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("a", "a");
		map.put("b", "b");
		map.put("c", "c");
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("a", "a");
		map2.put("b", "b");
		map2.put("c", "c");
		
		li.add(map);
		li.add(map2);
		
		System.out.println(getJsonStrFromList(li));*/
		
		/*if(true){
			throw new RuntimeException("微信要求不能多于8");
		}*/
				
	}
	
	 /**
     * 判断List是否为空，包括只有一个[null]元素的情况
     * @param li
     * @return
     */
	public static boolean isListNull(List li){
		
		if(li== null){
			return true;
		}
		
		if(li.size()==1){
			return li.get(0)==null? true : false;
		}
		
		if(li.size()>0 && li!=null){
			
			return false;
		}
		return true;
	}
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isStrNull(String str){
		
		if(str==null || str.length()==0){
			return true;
		}
		return false;
	}
	
	/**
	 * 将JSON数组转成sql in 条件的字符串
	 * eg: ["a","b","c"] 转成 ('a','b','c')
	 * @param js
	 * @return
	 */
	public static String JSArray2SqlIn(JSONArray js){
		
		if(js!=null && js.size()>0){
			String s = js.toString();
			String replace = s.replace('[', '(');
			String replace1 = replace.replace('\"', '\'');
			String replace2 = replace1.replace(']', ')');
			return replace2;
		}
		return null;
		
	}
	
	public static JSONObject cutStr2JSArray(String str){

		Map<String,String> map = new HashMap<String,String>();
		if(str.contains("\n")){
			
			String[] split = str.split("\n");
//		System.out.println(":".equals("："));
			for (int i = 0; i < split.length; i++) {
				if(split[i].contains("：")){
					//汉字部分
					String s = split[i].substring(0, split[i].indexOf("：")+1);
					split[i] = split[i].replace(s, "");
					split[i] = split[i].replace(".DATA}}", "");
					split[i] = split[i].replace("{{", "");
					split[i] = split[i].replace(" ", "");
					String key = split[i];
					String chinese = s.replace("：", "");
					if(chinese!=null){
						
						map.put(key, chinese);
					}
				}
				if(split[i].contains("{") && !split[i].contains("：")){
					split[i] = split[i].replace(".DATA}}", "");
					split[i] = split[i].replace("{{", "");
					if("first".equals(split[i])){
						map.put("first", "标题");
					}else if("remark".equals(split[i])){
						map.put("remark", "备注");
					}
				}
			}
		}
		else if(str.contains("{")){
			str = str.replace(".DATA}}", "");
			str = str.replace("{{", "");
			str = str.replace(" ", "");
			map.put(str, "内容");
		}
		String jsonString2 = JSON.toJSONString(map);
		JSONObject parseObject = JSON.parseObject(jsonString2);
		return parseObject;
	}

}
