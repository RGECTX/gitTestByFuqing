package com.greathack.homlin.controller.system;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.attachment.Attachment;
import com.greathack.homlin.service.AdminLoginSessionService;
import com.greathack.homlin.serviceinterface.attachment.IAttachmentService;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.FileOption;
import com.greathack.utils.tools.TypeOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.greathack.utils.tools.Base64Utils;
import com.greathack.utils.validate.exception.ValidateException;
import com.greathack.homlin.exception.BizException;
import com.greathack.homlin.serviceinterface.IAdminPermissionService;
import com.greathack.homlin.serviceinterface.IAdminPowerItemService;
import com.greathack.homlin.serviceinterface.IRoleService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 *
 */
@Controller
@RequestMapping(value="/file")
public class FileController {

	private static Logger logger = LoggerFactory.getLogger(FileController.class);
	private BizException exception=new BizException(210001,"INVALID_PARAMS");
	@Autowired
	private IAttachmentService attachmentService;
	@Autowired
	private IAdminPermissionService adminPermissionService;
	@Autowired
	private IAdminPowerItemService adminPowerItemService;



	@ResponseBody
	@RequestMapping(value="/upload")
	public  Object upload(@RequestParam(value = "charset", required = false) String charset,
						  @RequestParam(value = "loginCode", required = false) String loginCode ,
						  @RequestParam(value = "instanceType", required = false) String instanceType ,
						  @RequestParam(value = "instanceId", required = false) String instanceId ,
						  @RequestParam("file") MultipartFile[] files,
						  HttpServletRequest request) throws ValidateException{
		String savePath="upload/"+ TypeOption.formatDateTime(new Date(),"yyyyMMdd") + "/" +instanceType;
		//?????????????????????????????????
		String realSavePath=request.getSession().getServletContext().getRealPath(savePath);
		mkdirs(realSavePath);

		Admin admin = AdminLoginSessionService.getLoginSession(loginCode).getAdmin();
		JSONArray attachmentList=new JSONArray();
		for(MultipartFile file:files){
			//??????????????????????????????
			String filename = file.getOriginalFilename();
			//?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????  c:\a\b\1.txt?????????????????????????????????????????????1.txt
			//????????????????????????????????????????????????????????????????????????????????????
			filename = filename.substring(filename.lastIndexOf("\\")+1);
			//??????????????????????????????
			String fileExtName = filename.substring(filename.lastIndexOf(".")+1);
			//???????????????????????????
			String saveFileName = getRandomFileName()+"."+fileExtName;
			try {
				file.transferTo(new File(realSavePath + "/" + saveFileName));
			} catch (IOException e) {
				throw new BizException(ErrCode.UPLOAD_FAIL,"UPLOAD_FAIL");
			}

			Attachment attachment=new Attachment();
			attachment.setInstanceType(instanceType);
			attachment.setInstanceId(instanceId);
			attachment.setFileName(filename);
			attachment.setFilePath(savePath + "/" + saveFileName);
			attachment.setFileSize(file.getSize());
			attachment.setMime(FileOption.getContentType(filename));
			attachment.setFileExtName(fileExtName);
			attachment.setSaveFileName(saveFileName);
			attachment.setFileRealPath(realSavePath + "/" + saveFileName);
			attachment.setInstanceType(instanceType);
			attachment.setCreateBy(admin.getUserId());
			attachment.setAppCode(SystemConfig.getConfigData("AM_ATTACHMENT_APP_CODE"));
			try {
				attachment=attachmentService.add(attachment);
				JSONObject jsonObject=(JSONObject)JSON.toJSON(attachment);
				jsonObject.remove("appCode");
				jsonObject.remove("attachmentType");
				jsonObject.remove("outKey1");
				jsonObject.remove("outKey2");
				jsonObject.remove("bak1");
				jsonObject.remove("bak2");
				attachmentList.add(jsonObject);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("attachmentList", attachmentList);
		return result;
	}

	@ResponseBody
	@RequestMapping(value="/getAttachmentList")
	public  Object getAttachmentList(@RequestParam(value = "charset", required = false) String charset,
									 @RequestParam(value = "loginCode", required = false) String loginCode ,
									 @RequestParam(value = "instanceType", required = true) String instanceType ,
									 @RequestParam(value = "instanceId", required = true) String instanceId ,
									 HttpServletRequest request) throws ValidateException{
		Attachment cond=new Attachment();
		cond.setAppCode(SystemConfig.getConfigData("AM_ATTACHMENT_APP_CODE"));
		cond.setInstanceType(instanceType);
		cond.setInstanceId(instanceId);
		List<Attachment> attachmentList=attachmentService.findByExample(cond);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("attachmentList", attachmentList);
		return result;
	}

	@ResponseBody
	@RequestMapping(value="/delAttachment")
	public  Object delAttachment(@RequestParam(value = "charset", required = false) String charset,
								 @RequestParam(value = "loginCode", required = false) String loginCode ,
								 @RequestParam(value = "attachmentId", required = true) Long attachmentId ,
								 HttpServletRequest request) throws ValidateException{

		attachmentService.delete(attachmentId);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		return result;
	}

	@ResponseBody
	@RequestMapping(value="/getBase64Code")
	public  Object getBase64Code(@RequestParam(value = "charset", required = false) String charset,
								 @RequestParam(value = "loginCode", required = false) String loginCode ,
								 @RequestParam(value = "filePath", required = false) String filePath ,
								 HttpServletRequest request) throws ValidateException{

		String fileStr="";
		try {
			//filePath="upload/leave/b7be4ed6-c3ef-4a53-af8f-5a28d8d9e6af.jpg";
			String imgPath=request.getSession().getServletContext().getRealPath(filePath);
			Thread.sleep(1000);
			fileStr = Base64Utils.encodeFile(imgPath);
		} catch (Exception e) {
			e.printStackTrace();
		}


		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("bast64", fileStr);
		return result;
	}



	@ResponseBody
	@RequestMapping(value="/getBase64CodeList")
	public  Object getBase64CodeList(@RequestBody String strJson,
									 @RequestParam(value = "charset", required = false) String charset,
									 @RequestParam(value = "loginCode", required = false) String loginCode ,
									 HttpServletRequest request ,
									 HttpServletResponse response) throws ValidateException{
		JSONArray filePathArray;
		try {
			filePathArray = JSON.parseArray(strJson);
		} catch (Exception e1) {
			throw exception;
		}
		List<String> filePathList=new ArrayList<String>();
		for(Object filePath:filePathArray){
			filePathList.add(request.getSession().getServletContext().getRealPath((String)filePath));
		}
		//List<String> base64List=new ArrayList<String>();
		List<String> base64List = Base64Utils.batchEncodeFile(filePathList);
		
		/*for(String filePath:filePathList){
			try {
				Thread.sleep(1000);
				String base64 = Base64Utils.encodeFile(filePath);			
				Thread.sleep(1000);
				base64List.add(base64);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}*/
		
		/*JSONObject json=new JSONObject();
		json.put("errCode", 0);
		json.put("errMsg", "SUCCESS");
		JSONArray jsonArray=new JSONArray();
		for(String base64:base64List){
			jsonArray.add(base64);
		}
		json.put("base64List", jsonArray);
		
		try {
			//response.setStatus(HttpStatus.OK.value()); //???????????????  
            response.setContentType(MediaType.APPLICATION_JSON_VALUE); //??????ContentType  
            response.setCharacterEncoding(request.getParameter("charset")); //????????????  
            response.setHeader("Cache-Control", "no-cache, must-revalidate");
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}*/

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errCode", 0);
		result.put("errMsg", "SUCCESS");
		result.put("base64List", base64List);
		return result;
		//return  new ModelAndView();
	}


	/**
	 * @Method: getRandomFileName
	 * @Description: ???????????????????????????????????????uuid
	 * @return uuid?????????
	 */
	private String getRandomFileName(){  //2.jpg
		//???????????????????????????????????????????????????????????????????????????????????????
		return UUID.randomUUID().toString();
	}


	/**
	 * ??????????????????
	 * @Method: mkdirs
	 * @param filePath ????????????????????????
	 *
	 */
	private void mkdirs(String filePath){
		//File??????????????????????????????????????????
		File file = new File(filePath);
		//?????????????????????
		if(!file.exists()){
			//????????????
			file.mkdirs();
		}
		return ;

	}
}
