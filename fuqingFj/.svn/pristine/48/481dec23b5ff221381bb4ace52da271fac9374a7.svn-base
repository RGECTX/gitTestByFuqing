var dictComplete=false;
var amUnitComplete=false;
var powerItemComplete=false;
function login(){
	$.ajax({
		type: "POST",
		url: servicePath + "/user/login?charset=utf-8",
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify({ "loginName": $("#loginName").val(), "password": $("#password").val() }),
		dataType: "json",
		success: function (data) {
			if (data.errCode == "0") {
				showTicket("登录成功");
				sessionStorage.adminInfo=JSON.stringify(data.admin);
				getAllDictList(data.admin.loginCode);
				getAllAmUnit(data.admin.loginCode);
				getAllPowerItem(data.admin.loginCode);
				var interval=setInterval(function(){
					if(dictComplete && amUnitComplete && powerItemComplete){
						clearInterval(interval);
						location.href='main.html?v=1.60';
					}
				},500);
			} else {
				showTicket("登录失败");
			}
		}
	})	
}



//获取所有字典数据列表
function getAllDictList(loginCode){
	$.ajax({
		type: "GET",
		url: servicePath + "/dict/getAllDictList?charset=utf-8&loginCode="+loginCode,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		success: function (data) {
			if (data.errCode == 0) {
				dictListToObject(data.dict);
				greathack.setSessionData('dictList', data.dict);
				dictComplete=true;
			} else if (data.errCode == 200061 || data.errCode == 200062) {
				showTicket("请先登录！",2000,function(){
					parent.location.href="index.html?v=1.60";
				});
			} else if (data.errCode == 200063) {
				showTicket("没有权限！",2000);
			} else {
				showTicket(data.errMsg, 2000);
			}
		}
	})
}


//字典数据列表，转为对象
function dictListToObject(dictObj){
	var dict={};
	for(var key in dictObj){
		var dictdataList=dictObj[key];
		var dictDataObj={};
		for(var i=0;i<dictdataList.length;i++){
			dictDataObj[dictdataList[i].dataCode]=dictdataList[i];
		}
		dict[key]=dictDataObj;
	}
	greathack.setSessionData('dict', dict);
}

//获取所有字典
/* function getAllDict(loginCode){
	$.ajax({
		type: "GET",
		url: servicePath + "/dict/getAllDict?charset=utf-8&loginCode="+loginCode,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		success: function (data) {
			if (data.errCode == 0) {
				greathack.setSessionData('dict', data.dict);
				dictComplete=true;
			} else if (data.errCode == 200061 || data.errCode == 200062) {
				showTicket("请先登录！",2000,function(){
					parent.location.href="index.html?v=1.60";
				});
			} else if (data.errCode == 200063) {
				showTicket("没有权限！",2000);
			} else {
				showTicket(data.errMsg, 2000);
			}
		}
	})
} */

//获取所有单位
function getAllAmUnit(loginCode){
	$.ajax({
		type: "GET",
		url: servicePath + "/amUnit/getAmUnitObject?charset=utf-8&loginCode="+loginCode,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		success: function (data) {
			if (data.errCode == 0) {
				greathack.setSessionData('amUnitObj', data.amUnitObject);
				amUnitComplete=true;
			} else if (data.errCode == 200061 || data.errCode == 200062) {
				showTicket("请先登录！",2000,function(){
					parent.location.href="index.html?v=1.60";
				});
			} else if (data.errCode == 200063) {
				showTicket("没有权限！",2000);
			} else {
				showTicket(data.errMsg, 2000);
			}
		}
	})
}

//获取所有的权限项目
function getAllPowerItem(loginCode) {
	$.ajax({
		type: "POST",
		url: servicePath + "/powerItem/search?charset=utf-8&loginCode="+loginCode,
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify({}),
		dataType: "json",
		success: function (data) {
			if (data.errCode == 0) {
				greathack.setSessionData("powerItemList",data.adminPowerItemList);
				powerItemComplete=true;
			} else if (data.errCode == 200061 || data.errCode == 200062) {
				showTicket("请先登录！",2000,function(){
					parent.location.href="index.html?v=1.60";
				});
			} else if (data.errCode == 200063) {
				showTicket("没有权限！",2000);
			} else {
				showTicket(data.errMsg, 2000);
			}
		}
	})
}


window.onload=function(){
	var adminLoginForm=$("#adminLoginForm").Validform({
		tiptype:function(msg,o,cssctl){
			//msg:提示信息;
			//o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素(或表单对象),type指示提示的状态,值为1、2、3、4, 1:正在检测/提交数据,2:通过验证,3:验证失败,4:提示ignore状态, curform为当前form对象;
			//cssctl:内置的提示信息样式控制函数,该函数需传入两个参数:显示提示信息的对象 和 当前提示的状态(既形参o中的type);
			//if(!o.obj.is("form")){//验证表单元素时o.obj为该表单元素,全部验证通过提交表单时o.obj为该表单对象;
			if(o.type==3){	
				showTicket(msg);
			}
		},
		ajaxPost:true,
		beforeSubmit:function(curform){
			login();
			showTicket("正在登录...",20000);
			return false;
		}
	});
}
