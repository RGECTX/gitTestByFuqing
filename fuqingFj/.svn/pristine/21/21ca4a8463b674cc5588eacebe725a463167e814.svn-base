/*
全局用到的变量、函数、类、事件等
*/
var adminInfo=null;
if(sessionStorage.adminInfo!=undefined){
	adminInfo=JSON.parse(sessionStorage.adminInfo);
}

//提示
function showTicket(msg,seconds,func){
	var s=2000;
	if(!isNaN(seconds)){
		s=seconds;
	}
	layui.use('layer', function(){
	  var layer = layui.layer;					  
	  layer.msg(msg,{
			time:seconds,
			zIndex: layer.zIndex, //重点1
			success: function(layero){
				layer.setTop(layero); //重点2
		    }
		  });
	});
	setTimeout(function(){
		if(func!=undefined){
			func();
		}
	},s);
}

//创建字典下拉列表框
//selectObj:下拉列表框的对象,
//dictCode:字典编码
//excludeList:要排除不显示的字典数据编码列表
/* function addDictDataToSelect(selectObj,dictCode,excludeList){
	var list=[];
	if(excludeList!=undefined && excludeList!=null){
		list=excludeList;
	}
	var dictDataObj=greathack.getSessionData("dict")[dictCode];
	for(var key in dictDataObj){
		if(!isInList(key,list)){
			seladditem(key,dictDataObj[key].dataName,selectObj);
		}
	}	
} */



//创建字典下拉列表框
//selectObj:下拉列表框的对象,
//dictCode:字典编码
//excludeList:要排除不显示的字典数据编码列表
function addDictDataToSelect(selectObj,dictCode,excludeList){
	var list=[];
	if(excludeList!=undefined && excludeList!=null){
		list=excludeList;
	}
	var dictDataList=greathack.getSessionData("dictList")[dictCode];
	for(var i=0;i<dictDataList.length;i++){
		if(!isInList(dictDataList[i].dataCode,list)){
			seladditem(dictDataList[i].dataCode,dictDataList[i].dataName,selectObj);
		}
	}	
}

//创建字典多选下拉列表框数据
//dictCode:字典编码
//excludeList:要排除不显示的字典数据编码列表
function getDictDataToMuiSelect(dictCode,excludeList){
	var resultList=[];
	var list=[];
	if(excludeList!=undefined && excludeList!=null){
		list=excludeList;
	}
	var dictDataList=greathack.getSessionData("dictList")[dictCode];
	for(var i=0;i<dictDataList.length;i++){
		if(!isInList(dictDataList[i].dataCode,list)){
			var result={};
			result.name=dictDataList[i].dataName;
			result.value=dictDataList[i].dataCode;
			resultList.push(result);
		}
	}	
	return resultList;
}

//获取字典值对应的标签
//dictCode:字典编码
//dictDataVal:字典值也就是dataCode
function getDictDataLabel(dictCode,dictDataVal){
	if(dictDataVal==undefined || dictDataVal==null || dictDataVal==""){
		return "";
	}
	var dictCodeObj = greathack.getSessionData("dict")[dictCode];
	if(dictCodeObj==undefined || dictCodeObj[dictDataVal]==undefined || dictCodeObj[dictDataVal].dataName==undefined){
		return "";
	}
	return dictCodeObj[dictDataVal].dataName;
}



//获取字典值对应的字典数据对象
//dictCode:字典编码
//dictDataVal:字典值也就是dataCode
function getDictDataObj(dictCode,dictDataVal){
	if(dictDataVal==undefined || dictDataVal==null || dictDataVal==""){
		return null;
	}
	var dictCodeObj = greathack.getSessionData("dict")[dictCode];
	if(dictCodeObj==undefined || dictCodeObj[dictDataVal]==undefined || dictCodeObj[dictDataVal].dataName==undefined){
		return null;
	}
	return dictCodeObj[dictDataVal];
}


  
//树数据转列表
function treeDataToList(treeData){
  var dataList=[];
  for(var i=0;i<treeData.length;i++){		  
	  dataList.push(treeData[i]);
	  if(treeData[i].children!=undefined){
		  var childList=treeDataToList(treeData[i].children);		  
		  for(var j=0;j<childList.length;j++){
			  dataList.push(childList[j]);
		  }
	  }
  }
  return dataList;
}

//获取单位名称
function getOrgName(orgId){
	if(orgId==0){
		return "非定向";
	}
	var org=greathack.getSessionData("amUnitObj")[orgId];
	if(org!=undefined){
		return org.orgName;
	}
	return "";
}


  //判断是否在黑名单:1、在黑名单，2、不在黑名单，4、出现异常
  function hmdCheck(idCard) {
	var isIdcardInHmd=null;
  	layui.jquery.ajax({
  		type: "GET",
  		url: servicePath + "/amHmd/isIdcardInHmd?charset=utf-8&idCard="+idCard+"&loginCode="+adminInfo.loginCode,
  		contentType: "application/json; charset=utf-8",
  		dataType: "json",
		async: false,
  		success: function (data) {
  			if(data.errCode==0){
				if(data.isIdcardInHmd){
					isIdcardInHmd=1;
				}else{
					isIdcardInHmd=2;
				}
  			}else{
				isIdcardInHmd=4;
			}
  		}
  	})
	return isIdcardInHmd;
  }