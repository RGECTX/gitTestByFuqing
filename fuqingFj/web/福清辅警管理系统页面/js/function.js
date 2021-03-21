// JavaScript Document

function checkDropListItem(obj,itemvalue)
{
	/*
	默认选中下拉列表框中的具有某个值的一项
	obj:下拉列表框对象
	itemvalue:该项值
	*/
	for(i=0;i<obj.options.length;i++)
	{
		
		if(obj.options[i].value==itemvalue)
		   obj.selectedIndex=i;
	}
}

function seladditem(thevalue,thetext,obj)
{
	if(obj==undefined){
		return;
	}
	/*
	添加值到下拉列表框
	thevalue:值
	thetext:显示文本
	obj:下拉列表框对象
	*/
	var oOption = document.createElement("OPTION");
	oOption.value=thevalue;
	oOption.text=thetext;
	obj.add(oOption);
}

function strToDropList(Liststr,obj)
{
   /*
   把图片列表串中的串转为下拉列表
   Liststr:要转换成下拉列表的字符串,用|分隔
   obj:下拉列表框对象
   */
   var nListstr;
   nListstr = Liststr.split("|");
   for(i=1;i<nListstr.length;i++)
   {
	   seladditem(nListstr[i],nListstr[i],obj);
   }
}

function blRdoChecked(form,sDspStr)		//是否有选择记录
{
	//sDspStr:	CheckBox的name  局限于某一部分的CheckBox,不至于影响到其他的CheckBox
	
	var bl=false;
	items=form.all.tags("input");  //检查input输入
	for (i=0;i<items.length;i++)					
		if (items(i).type=="checkbox"&&items(i).name.toUpperCase()==sDspStr.toUpperCase())
			if (items(i).checked)bl=true;
	return bl;
}

function allRdoIDChecked(form,blRadioAllChecked,sDspStr)			//全选-取消函数
{
	//sDspStr:	CheckBox的name  局限于某一部分的CheckBox,不至于影响到其他的CheckBox
	
	items=form.all.tags("input");  		//取消
	if(blRadioAllChecked)
	{
		for (i=0;i<items.length;i++)
		if (items(i).type=="checkbox"&&items(i).name.toUpperCase()==sDspStr.toUpperCase())
				items(i).checked=false;
		return false;
	}
	else
	{
		for (i=0;i<items.length;i++)	//全选	
		if (items(i).type=="checkbox"&&items(i).name.toUpperCase()==sDspStr.toUpperCase())
				items(i).checked=true;
		return true;
	}
}

//检查输入是否价格(带一个小数点),不是的话删除最后一个输入,直到是为止
function checkPrice(obj){
	var reg=/^(\d+[\s,]*)+\.?\d?$/;
	while($(obj).val().length>0 && !reg.test($(obj).val())){
		$(obj).val($(obj).val().substring(0,$(obj).val().length-1));
	}
}

function openWin(url,W_top,W_left,W_height,Wwidth,fullscreen)
{
	/*
	打开新窗口
	url:窗口地址
	fullscreen:1为全屏,0为不全屏
	*/
	switch(fullscreen){
	    case 0:
			return window.open(url,"_blank","left="+W_left+", top="+W_top+",height="+W_height+", width="+Wwidth+", toolbar=no , menubar=no, scrollbars=no, resizable=no, location=no, status=no");
			break;
		case 1:
			return window.open(url,"_blank","fullscreen=1");
			break;
	}
}


function checkAllByName(ElementName,obj)
{
	/*
	利用复选框的Name属性来选择或取消同名的复选框
	ElementName:元素名称
	obj:主导复选框
	*/
	var items;
	items=document.getElementsByName(ElementName);
	if(obj.checked==true){
		for(i=0;i<items.length;i++)
		{
			items[i].checked=true;
		}
	}else{
		for(i=0;i<items.length;i++)
		{
			items[i].checked=false;
		}
	}
}


function imgInit(objs,picwidth,pichight){
/*
	初始化图片,在body onload里调用
*/
	for(i=0;i<objs.length;i++){
		thezoom(objs[i],picwidth,pichight);
	}
}

function thezoom(obj,picwidth,pichight)
{
	//按比例调整图片
	//obj:图片id
	//picwidth:调整后的图片宽度
	//pichight:调整后的图片高度
	var zoom,zoom1,zoom2;
	zoom1=picwidth/obj.width;
	zoom2=pichight/obj.height;
	if(zoom1<zoom2){
		zoom=zoom1;
		obj.height=obj.height*zoom;
		obj.width=picwidth;
	}else{
		zoom=zoom2;
		obj.width=obj.width*zoom;
		obj.height=pichight;
	}
	if(obj.width==0){
		obj.width=picwidth;
		obj.height=pichight;
	}
}

function thezoom2(obj,picwidth,pichight)
{
	obj.onload = function() {
		//按给定大小调整图片到充满给定大小,超出部分隐藏
		var zoom,zoom1,zoom2,zoom3;
		zoom1=picwidth/pichight;
		zoom2=obj.width/obj.height;
		zoom3=obj.height/obj.width;
		if(zoom1<zoom2){
			obj.height=pichight;
			obj.width=pichight*zoom2;
			obj.style.left=-1*(obj.width-picwidth)/2+"px";
			//obj.style.top="0px";
		}else{
			obj.width=picwidth;
			obj.height=picwidth*zoom3;
			obj.style.top=-1*(obj.height-pichight)/2+"px";
			//obj.style.left="0px";
		}
    }
	
}

//写入cookie
function setCookie(c_name, value, expiredays) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + expiredays);
    //alert(exdate.getDate() + expiredays);
    document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "": ";expires=" + exdate.toGMTString()) + ";path=/;";
}

//获取cookie
function getCookie(c_name) {
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=");
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1;
            c_end = document.cookie.indexOf(";", c_start);
            if (c_end == -1) c_end = document.cookie.length;
            return unescape(document.cookie.substring(c_start, c_end));
        }
    }
    return null;
}

/**
* 删除左右两端的空格
*/
String.prototype.trim=function()
{
     return this.replace(/(^\s*)|(\s*$)/g,"");
}
/**
* 删除左边的空格
*/
String.prototype.ltrim=function()
{
     return this.replace(/(^\s*)/g,"");
}
/**
* 删除右边的空格
*/
String.prototype.rtrim=function()
{
     return this.replace(/(\s*$)/g,"");
}

/**
* 日期格式化
* D放在要变成今天、昨天的地方,在D之间的都会去掉,只留下D之后的格式
* 如果有D,时间却不是今天或昨天,D会去掉,按照没有D的格式转化
*/
Date.prototype.format =function(format)
{
	var o = {
		"M+" : this.getMonth()+1, //month
		"d+" : this.getDate(),    //day
		"h+" : this.getHours(),   //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3),  //quarter刻
		"S+" : this.getMilliseconds() //millisecond
	};
	var D=function (thisDate){
			var date=new Date().toDateString();
			var todayBegin=Date.parse(date);
			var todayEnd=todayBegin+24*60*60*1000;
			var yesterdayBegin=todayBegin-24*60*60*1000;
			var thisTime=thisDate.getTime();					
			if(thisTime>yesterdayBegin && thisTime<todayBegin){
				return "昨天";
			}
			if(thisTime>todayBegin && thisTime<todayEnd){
				return "今天";
			}
			return "";
		};
	if (/(D+)/.test(format)){
		var dayStr=D(this);
		if(dayStr!=""){
			format = format.substr(format.indexOf("D"));
		}
		format = format.replace(RegExp.$1, dayStr);
	}
	if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	if (/(S+)/.test(format)) format = format.replace(RegExp.$1, ("000"+this.getMilliseconds()).substr(("000"+this.getMilliseconds()).length-3,RegExp.$1.length));
    for (var k in o) if (new RegExp("(" + k + ")").test(format)) format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}
var a=new Date();
//a.setTime(a.getTimezoneOffset()*60*1000);
//alert(a.format("yyyy-MM-dd D hh:mm:ss"));

//把8年的日期(格式如20181031)转为Date
function intDateToDate(intDate){
	var yearStr=intDate.toString().substr(0,4);
	var monthStr= intDate.toString().substr(4,2);
	var dayStr= intDate.toString().substr(6,2);
	return new Date(yearStr+"-"+monthStr+"-"+dayStr);
}

function isWechat() {//判断是不是微信打开
	var ua = navigator.userAgent.toLowerCase();
	if (ua.match(/MicroMessenger/i) == "micromessenger") {
		return true;
	} else {
		return false;
	}
}

/*--------------------实现(返回 $_GET 对象, 仿PHP模式)----------------------*/
var $_GET = (function(){
    var url = window.document.location.href.toString();
    var u = url.split("?");
    if(typeof(u[1]) == "string"){
        u = u[1].split("&");
        var get = {};
        for(var i in u){
            var j = u[i].split("=");
            get[j[0]] = j[1];
        }
        return get;
    } else {
        return {};
    }
})();
 
/*使用时, 可以直接 $_GET['get参数'], 就直接获得GET参数的值*/

//获取Get参数
function getParameter(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

function getPar(par){
    //获取当前URL
    var local_url = document.location.href; 
    //获取要取得的get参数位置
    var get = local_url.indexOf(par +"=");
    if(get == -1){
        return false;   
    }   
    //截取字符串
    var get_par = local_url.slice(par.length + get + 1);    
    //判断截取后的字符串是否还有其他get参数
    var nextPar = get_par.indexOf("&");
    if(nextPar != -1){
        get_par = get_par.slice(0, nextPar);
    }
    return get_par;
}

//utf16转成utf8用于jquery-qrcode生成二维码中,中文的处理
function utf16to8(str) {   
    var out, i, len, c;   
    out = "";   
    len = str.length;   
    for(i = 0; i < len; i++) {   
    c = str.charCodeAt(i);   
    if ((c >= 0x0001) && (c <= 0x007F)) {   
        out += str.charAt(i);   
    } else if (c > 0x07FF) {   
        out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));   
        out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));   
        out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));   
    } else {   
        out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));   
        out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));   
    }   
    }   
    return out;   
}  


//获取浏览器版本
function getBrowserVersions(){
	var u = navigator.userAgent, app = navigator.appVersion;
    return {//移动终端浏览器版本信息
     trident: u.indexOf('Trident') > -1, //IE内核
     presto: u.indexOf('Presto') > -1, //opera内核
     webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
     gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
     mobile: !!u.match(/AppleWebKit.*Mobile/i) || !!u.match(/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/), //是否为移动终端
     ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
     android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
     iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
     iPad: u.indexOf('iPad') > -1, //是否iPad
     webApp: u.indexOf('Safari') == -1 //是否web应该程序,没有头部与底部
    };
}

//是否IOS系统
function isIOS(){
	var browserVersions=getBrowserVersions();
	if (browserVersions.iPhone || browserVersions.iPad || browserVersions.ios) {
		return true;
	}else{
		return false;	
	}
}

//是否安卓系统
function isAndroid(){
	var browserVersions=getBrowserVersions();
	if (browserVersions.android) {
		return true;
	}else{
		return false;	
	}
}

//是否手机端
function isMobile(){
	var browserVersions=getBrowserVersions();
	if(browserVersions.mobile){
		return true;
	}else{
		return false;	
	}
}

//获取当前路径,不包括后面的参数及文件名
function getCurrentPath(){
	return location.href.substr(0,location.href.lastIndexOf("/"))
}

//获取JSON对象长度
/*Object.prototype.getLength =function (){

	var jsonLength = 0;
	
	for(var item in this){
	
		jsonLength++;
	
	}
	return jsonLength;

}*/
//获取JSON对象长度
function getJSONLength(json){

	var jsonLength = 0;
	
	for(var item in json){
	
		jsonLength++;
	
	}
	return jsonLength;

}

//触摸事件
var touchEvents = {
	touchstart: isMobile()?"touchstart":"mousedown",
	touchmove: isMobile()?"touchmove":"mousemove",
	touchend: isMobile()?"touchend":"mouseup",
	touchcancel: isMobile()?"touchcancel":"mouseup"	
};

//获取中文长度
function chineseStrLength(str) {
    var len = 0;
    for (var i = 0; i < str.length; i++) {
        var c = str.charCodeAt(i);
        
        if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
            len++;//单字节加1 
        } else {
            len += 2;//双字节加2
        }
    }
    return len;
}

//8位日期字符串转化成10位
function formatDate8To10(str) {
    if (str != null && str != ''&&str.length==8) {
    	return str.substring(0,4)+"-"+str.substring(4,6)+"-"+str.substring(6,8)
    } else {
    	return "";
    }
}

//10位日期字符串转化成8位
function formatDate10To8(str) {
    if (str != null && str != ''&&str.length==10) {
    	return str.replace(/-/g,"");
    } else {
    	return "";
    }
}

//6位日期字符串转化成7位
function formatDate6To7(str) {
    if (str != null && str != ''&&str.length==6) {
    	return str.substring(0,4)+"-"+str.substring(4,6)
    } else {
    	return "";
    }
}



//获得列表选择的ids
function getSelectIds(dataList){
	var ids="";
	for(var i=0;i<dataList.length;i++){
	    var id = dataList[i].id;
		if(ids==''){
			ids=id;
		}else{
			ids=ids+","+id;
		}
	}
	return ids;
}

var greathack={
	setSessionData:function(table,setting){
		sessionStorage.setItem(table, JSON.stringify(setting));
	},
	getSessionData:function(table){
		return JSON.parse(sessionStorage.getItem(table));
	},
	setData:function(table,setting){
		localStorage.setItem(table, JSON.stringify(setting));
	},
	getData:function(table){
		return JSON.parse(localStorage.getItem(table));
	}
}

function emptyToNull(str){
	return str==""?null:str;
}

//指定值是否在列表中
function isInList(value,list){
	for(var i=0;i<list.length;i++){
		if(list[i]==value){
			return true;
		}
	}
	return false;
}

/*
 startYear 开始年份 参数为空时为当前年份前5年
 endYear 结束年份 参数为空时为当前年份
*/
function getYearArr(startYear,endYear){
	var yearArr=[],
		prDate = new Date(),
		presentYear = prDate.getFullYear();//当前年份
	if ( !endYear ) { //为空为当前年份
		endYear = presentYear;
　　}
　　if ( !startYear ) { //为空为当前年前5年
　　　　startYear = presentYear-5;
　　}
　　for ( var i = endYear; i >= startYear; i--) {
　　　　yearArr.push(i);
　　}
　　return yearArr;
}

//根据传入日期获得当前日期在本年度第几周，本年度第几周，如果周跨年，就算在上一年度
/* function getWeek(dt) {
	var year=dt.substring(0,4);
	var firstYearDay=year+"-01-01";
	var week = new Date(firstYearDay).getDay();
	var num=0;
	if(week==1){
	}else{
		var firstDay=2+(7-week);
		firstYearDay=year+"-01-0"+firstDay;
	}
	
	let d1 = new Date(dt);
	let d2 = new Date(firstYearDay);
	//d2.setMonth(0);
	//d2.setDate(1);
	if(d2>d1){
		num=0;
	}else{
		let rq = d1 - d2;
		let days = Math.ceil(rq / (24 * 60 * 60 * 1000));
		let num1 = Math.ceil((days+1) / 7);
		if(num1==0){
			num=num1+1;
		}else{
			num=num1;
		}
	}
	
	return num;
} */

function getWeek(dt) {
    var now = new Date(dt),
    year = now.getFullYear(),
    month = now.getMonth(),
    days = now.getDate();
    //那一天是那一年中的第多少天
    for (var i = 0; i < month; i++) {
        days += getMonthDays(year, i);
    }
 
    //那一年第一天是星期几
    var yearFirstDay = new Date(year, 0, 1).getDay() || 7;

    var week = null;
    if (yearFirstDay == 1) {
        week = Math.ceil(days / yearFirstDay);
    } else {
        days -= (7 - yearFirstDay + 1);
        week = Math.ceil(days / 7) + 1;
    }
 
    return week;
}

/**
* 判断年份是否为润年
*
* @param {Number} year
*/
 function isLeapYear(year) {
	return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
 }

/**
 * 获取某一年份的某一月份的天数
 *
 * @param {Number} year
 * @param {Number} month
 */
function getMonthDays(year, month) {
    return [31, null, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31][month] || (isLeapYear(year) ? 29 : 28);
}

//日期格式转为2012-01-05格式
function dateToString(date){ 
	  var year = date.getFullYear(); 
	  var month =(date.getMonth() + 1).toString(); 
	  var day = (date.getDate()).toString();  
	  if (month.length == 1) { 
		  month = "0" + month; 
	  } 
	  if (day.length == 1) { 
		  day = "0" + day; 
	  }
	  var dateTime = year + "-" + month + "-" + day;
	  return dateTime; 
}