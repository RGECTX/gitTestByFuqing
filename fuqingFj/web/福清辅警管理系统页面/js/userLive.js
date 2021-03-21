//存data.user
if(adminInfo==null){
	parent.location.href='index.html?v=1.60';
	setTimeout("parent.location.href='./index.html?v=1.60&'",1000);
}

layui.use(['tree', 'util','element','form','table','layer'], function(){
	var $ = layui.jquery;
	
	var style = document.createElement("style");
	style.type = "text/css";
	var adminPowerItemList=greathack.getSessionData("powerItemList");
	var powers=adminInfo.powers;	
	style.appendChild(document.createTextNode(".layui-table-cell{display:inline-block;}"));
	style.appendChild(document.createTextNode(".layui-btn-container{display:inline-block;}"));
	for(var i=0;i<adminPowerItemList.length;i++){
		if(powers[adminPowerItemList[i].powerCode]!=2){
			style.appendChild(document.createTextNode("."+adminPowerItemList[i].powerCode+"{display:none !important;}"));
		}		
	}
	
	for(var key in adminInfo.powers){
		if(powers[key]==2){
			style.appendChild(document.createTextNode("."+key+"{display:inherit !important;}"));
		}
	} 
	var head = document.getElementsByTagName("head")[0];
	head.appendChild(style);		
	
}) 


	
//退出登陆
function logout(){
	$.ajax({
		type: "GET",
		url: servicePath+'/user/logout?charset=utf-8&loginCode='+adminInfo.loginCode,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		success: function(data) {
			adminInfo=null;
			sessionStorage.adminInfo=null;
			layui.use('layer', function(){
			  var layer = layui.layer;					  
			  layer.msg('退出成功');
			});	
			window.location.href="./index.html?v=1.60";
		},
		error: function(data){
			adminInfo=null;
			sessionStorage.adminInfo=null;
			layui.use('layer', function(){
			  var layer = layui.layer;					  
			  layer.msg('退出成功');
			});	
			window.location.href="./index.html?v=1.60";
		}
	})
}