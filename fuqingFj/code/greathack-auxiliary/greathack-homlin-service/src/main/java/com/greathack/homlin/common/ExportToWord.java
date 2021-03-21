/**
 * 
 */
package com.greathack.homlin.common;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

/**
 * @author greathack
 *
 */
public class ExportToWord {
	  private static Configuration configuration = null;  
	  static{
		  configuration = new Configuration(Configuration.VERSION_2_3_23);  
          configuration.setDefaultEncoding("UTF-8");  
	  }
       
     public String createWord(String templateDirPath,String templateFileName,String outputPath,Map<String, Object> dataMap,String docName){
    	 String url=outputPath+"/"+docName+".doc";
    	 File templateDir=new File(templateDirPath);
         try {
			configuration.setDirectoryForTemplateLoading(templateDir);;//模板文件所在路径
		} catch (IOException e2) {
			e2.printStackTrace();
		}
         Template t=null;  
         try {  
             t = configuration.getTemplate(templateFileName); //获取模板文件
         } catch (IOException e) {  
             e.printStackTrace();  
         }  
         File outFile = new File(url); //导出文件
         Writer out = null;  
         try {  
             out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));  
         } catch (FileNotFoundException e1) {  
             e1.printStackTrace();  
         }  
            
         try {  
             t.process(dataMap, out); //将填充数据填入模板文件并输出到目标文件 
         } catch (TemplateException e) {  
             e.printStackTrace();  
         } catch (IOException e) {  
             e.printStackTrace();
         }  
         try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
         return url;
     }
     
   public String createExcel(String templateDirPath,String templateFileName,String outputPath,Map<String, Object> dataMap,String xlsName){
  	 String url=outputPath+"/"+xlsName+".xls";
  	 File templateDir=new File(templateDirPath);
       try {
			configuration.setDirectoryForTemplateLoading(templateDir);;//模板文件所在路径
		} catch (IOException e2) {
			e2.printStackTrace();
		}
       Template t=null;  
       try {  
           t = configuration.getTemplate(templateFileName); //获取模板文件
       } catch (IOException e) {  
           e.printStackTrace();  
       }  
       File outFile = new File(url); //导出文件
       Writer out = null;  
       try {  
           out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));  
       } catch (FileNotFoundException e1) {  
           e1.printStackTrace();  
       } catch(UnsupportedEncodingException e2){
    	   e2.printStackTrace();  
       }
       
          
       try {  
           t.process(dataMap, out); //将填充数据填入模板文件并输出到目标文件 
       } catch (TemplateException e) {  
           e.printStackTrace();  
       } catch (IOException e) {  
           e.printStackTrace();
       }  
       try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
       return url;
   }
}
