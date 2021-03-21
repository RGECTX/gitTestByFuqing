// JavaScript Document
layui.use(['element', 'form', 'layer'], function() {
	var $ = layui.jquery;
	var element = layui.element;
	var form = layui.form;
	var layer = layui.layer;
	element.on('nav(layadmin-system-side-menu)', function(elem) {
		if (elem.attr("lay-href") == undefined || elem.attr("lay-href") == "") {
			return;
		}
		if (!isTabOpened("LAY_app_tabsheader", elem.attr("lay-href"))) {
			var tabTitle = elem.attr("tabTitle");
			if (tabTitle == undefined || tabTitle == "") {
				tabTitle = elem.text();
			}
			element.tabAdd('layadmin-layout-tabs', {
				title: tabTitle,
				content: '<div class="layui-body"><iframe name="' + elem.attr("lay-href") + '" src="' + elem.attr("lay-href") +
					'" frameborder="0" class="layadmin-iframe"></iframe></div>' //支持传入html
					,
				id: elem.attr("lay-href")
			});
		}
		element.tabChange('layadmin-layout-tabs', elem.attr("lay-href"));
	});
	$("#name").html(adminInfo.name);

	//修改基本信息
	function editUser(data) {
		var layerIndex = layer.load();
		$.ajax({
			type: "POST",
			url: servicePath + "/user/updateSelf?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("修改成功！", 2000, function() {
						layer.closeAll('page');
						adminTable.reload();
					});
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！", 2000, function() {
						parent.location.href = "index.html?v=1.60";
					});
				} else if (data.errCode == 200063) {
					showTicket("没有权限！", 2000);
				} else {
					showTicket(data.errMsg, 2000);
				}
			}
		})
	}

	//修改密码
	function mdfyPwd(data) {
		var layerIndex = layer.load();
		$.ajax({
			type: "POST",
			url: servicePath + "/user/mdfyOwnPwd?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("修改成功！", 2000, function() {
						layer.closeAll('page');
					});
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！", 2000, function() {
						parent.location.href = "index.html?v=1.60";
					});
				} else if (data.errCode == 200063) {
					showTicket("没有权限！", 2000);
				} else {
					showTicket(data.errMsg, 2000);
				}
			}
		})
	}

	form.verify({
		loginName: function(value, item) { //value：表单的值、item：表单的DOM对象
				if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
					return '登录名不能有特殊字符';
				}
				if (/(^\_)|(\__)|(\_+$)/.test(value)) {
					return '登录名首尾不能出现下划线\'_\'';
				}
			}

			//我们既支持上述函数式的方式，也支持下述数组的形式
			//数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
			,
		password: [
			/^[\S]{6,12}$/, '密码必须6到12位，且不能出现空格'
		],
		repassword: function(value, item) {
			if ($('#password').val() != $('#repassword').val()) {
				return "两次密码输入不一致，请重新输入";
			}
		}
	});

	function showTicket(msg, seconds, func) {
		var s = 2000;
		if (!isNaN(seconds)) {
			s = seconds;
		}
		layer.msg(msg, {
			time: seconds
		});
		setTimeout(function() {
			if (func != undefined) {
				func();
			}
		}, s);
	}

	var checkboxIndex = $("#LAY_app_flexible").checkbox({
		onChange: function(self, isChecked) {
			if (isChecked == "true") {
				$("#LAY_app_flexible").removeClass("layui-icon-shrink-right");
				$("#LAY_app_flexible").addClass("layui-icon-spread-left");
				$(".layui-side-menu").hide();
				$("#LAY_app_tabs").css("left", "0px");
				$(".layui-body").css("left", "0px");
				$(".layui-layout-left").css("left", "0px");
			} else {
				$("#LAY_app_flexible").removeClass("layui-icon-spread-left");
				$("#LAY_app_flexible").addClass("layui-icon-shrink-right");
				$(".layui-side-menu").show();
				$("#LAY_app_tabs").css("left", "220px");
				$(".layui-body").css("left", "220px");
				$(".layui-layout-left").css("left", "220px");
			}
		} //改变时执行,没变化不会执行
	});
	$("#LAY_app_flexible").on("click", function() {
		checkboxIndex.toggle(this);
	});
	$("#editUserBtn").on("click", function() {
		var formData = {};
		formData.loginName = adminInfo.loginName;
		formData.name = adminInfo.name;
		formData.sex = adminInfo.sex;
		form.val("editUserForm", formData);
		layer.open({
			type: 1,
			title: '修改个人资料',
			area: ['370px', '300px'],
			content: $('#editUserPage'),
			btn: ['保存', '关闭'],
			yes: function(index, layero) {
				$("#editUserSubmit").click();
			},
			btn2: function(index, layero) {
				layer.close(index);
			}
		});
	});
	$("#mdfyPwdBtn").on("click", function() {
		layer.open({
			type: 1,
			title: '修改密码',
			area: ['350px', '300px'],
			content: $('#mdfyPwdPage'),
			btn: ['保存', '关闭'],
			yes: function(index, layero) {
				$("#mdfyPwdPageSubmit").click();
			},
			btn2: function(index, layero) {
				layer.close(index);
			}
		});
	});

	//修改个人信息“保存”按钮触发
	form.on('submit(editUserSubmit)', function(data) {
		editUser(form.val("editUserForm"));
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});

	//修改密码“保存”按钮触发
	form.on('submit(mdfyPwdPageSubmit)', function(data) {
		var formData = form.val("mdfyPwdForm");
		mdfyPwd({
			"newPassword": formData.password,
			"password": formData.oldPassword
		});
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});

	$("#message").click(function() {
		topTabAdd(
			"收件箱", '<div class="layui-body"><iframe name="' + $("#message").attr("lay-href") + '" src="' + $("#message").attr(
				"lay-href") + '" frameborder="0" class="layadmin-iframe"></iframe></div>' //支持传入html
			, $("#message").attr("lay-href")
		);
	});

	$(".sex").each(function() {
		addDictDataToSelect(this, "SEX");
	})

});

//打开顶级标签页
function topTabAdd(title, content, layid) {
	var element = layui.element;
	if (!isTabOpened("LAY_app_tabsheader", layid)) {
		element.tabAdd('layadmin-layout-tabs', {
			title: title,
			content: content //支持传入html
				,
			id: layid
		});
	}
	element.tabChange('layadmin-layout-tabs', layid);
}

//关闭顶级标签页
function topTabDelete(layid) {
	var element = layui.element;
	element.tabDelete('layadmin-layout-tabs', layid);
}

//根据窗口名称，获取窗口对象
function getIframeWindow(iframeName) {
	return window[iframeName];
}


//判断是否打开了tab
function isTabOpened(tabsHeadId, openLayId) {
	var temp = false;
	$("#" + tabsHeadId).find("li").each(function(index, element) {
		if ($(this).attr("lay-id") == openLayId) {
			temp = true;
		}
	});
	return temp;
}


//减员人员选择框
function openSelectRpsID(options,windowName,callback){
	var setting={
		url:'auxiliary/selectJyry.html',
		title:'选择减员人员',
		resource:'/auxDagl/search',
		where:{state:11},
		treeWhere:{},
		area:['1024px','768px'],
		page:true,
		limit:12,
		limits:[12,30,50,100,10000],
		selectType:'checkbox',//radio,checkbox
		checkedData:[],//默认选中的数据，只对selectType为checkbox起作用
		disabledData:[],//默认禁用的数据
		compareField:''//用于识别选中或禁用的字段
	}
	$.extend( setting, options );
	layer.open({
		type: 2
		,title:setting.title
		,area:setting.area
		,content: setting.url + '?v=1.60&setting=' + escape(JSON.stringify(setting))
		,btn: ['添加', '关闭']
		,yes:function(index, layero){
			var returnData=window["layui-layer-iframe" + index].getCheckedData();
			if(returnData.length==0){
				showTicket("请先选择数据！",2000);
				return;
			}
			callback(returnData,index);
		}
		,btn2: function(index, layero){
			layer.close(index);
		}
	});
}

//档案人员选择框
function openSelectDaryID(options,windowName,callback){
	var setting={
		url:'auxiliary/selectDary.html',
		title:'选择档案人员',
		resource:'/auxDagl/search',
		where:{state:1},
		treeWhere:{},
		area:['1024px','768px'],
		page:true,
		limit:12,
		limits:[12,30,50,100,10000],
		selectType:'checkbox',//radio,checkbox
		checkedData:[],//默认选中的数据，只对selectType为checkbox起作用
		disabledData:[],//默认禁用的数据
		compareField:''//用于识别选中或禁用的字段
	}
	$.extend( setting, options );
	layer.open({
		type: 2
		,title:setting.title
		,area:setting.area
		,content: setting.url + '?v=1.60&setting=' + escape(JSON.stringify(setting))
		,btn: ['添加', '关闭']
		,yes:function(index, layero){
			var returnData=window["layui-layer-iframe" + index].getCheckedData();
			if(returnData.length==0){
				showTicket("请先选择数据！",2000);
				return;
			}
			callback(returnData,index);
		}
		,btn2: function(index, layero){
			layer.close(index);
		}
	});
}


//打开人员选择框
function openSelectRy(options, windowName, callback) {
	var setting = {
		url: 'selectFjry.html',
		title: '选择人员',
		resource: '/amJbxx/selectJbxx',
		where: {
			state: 11
		},
		treeWhere: {},
		area: ['1024px', '768px'],
		page: true,
		limit: 12,
		limits: [12, 30, 50, 100, 10000],
		selectType: 'checkbox', //radio,checkbox
		checkedData: [], //默认选中的数据，只对selectType为checkbox起作用
		disabledData: [], //默认禁用的数据
		compareField: '', //用于识别选中或禁用的字段
		isRequire: true //是否必选
	}
	$.extend(setting, options);
	layer.open({
		type: 2,
		title: setting.title,
		area: setting.area,
		content: setting.url + '?v=1.60&setting=' + escape(JSON.stringify(setting)),
		btn: ['添加', '关闭'],
		yes: function(index, layero) {
			var returnData = window["layui-layer-iframe" + index].getCheckedData();
			if (setting.isRequire && returnData.length == 0) {
				showTicket("请先选择数据！", 2000);
				return;
			}
			callback(returnData, index);
		},
		btn2: function(index, layero) {
			layer.close(index);
		}
	});
}

//打开单位选择框
function openSelectOrg(options, windowName, callback) {
	var setting = {
		url: 'selectOrg.html',
		title: '选择单位',
		resource: '/amUnit/selectOrg',
		where: {
			state: 1
		},
		treeWhere: {
			state: 1
		},
		area: ['1024px', '768px'],
		page: true,
		limit: 12,
		limits: [12, 30, 50, 100, 10000],
		selectType: 'checkbox', //radio,checkbox
		checkedData: [], //默认选中的数据，只对selectType为checkbox起作用
		disabledData: [], //默认禁用的数据
		compareField: '' //用于识别选中或禁用的字段
	}
	$.extend(setting, options);
	layer.open({
		type: 2,
		title: setting.title,
		area: setting.area,
		content: setting.url + '?v=1.60&setting=' + escape(JSON.stringify(setting)),
		btn: ['添加', '关闭'],
		yes: function(index, layero) {
			var returnData = window["layui-layer-iframe" + index].getCheckedData();
			if (returnData.length == 0) {
				showTicket("请先选择数据！", 2000);
				return;
			}
			callback(returnData, index);
		},
		btn2: function(index, layero) {
			layer.close(index);
		}
	});
}


//获取未读信息
function getMsgList() {
	$.ajax({
		type: "POST",
		url: servicePath + "/receive/search?charset=utf-8&loginCode=" + adminInfo.loginCode,
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify({
			"receiveState": 1,
			"recipient": adminInfo.userId
		}),
		dataType: "json",
		success: function(data) {
			if (data.errCode == 0) {
				if (data.recordCount > 0) {
					$("#message").find("span").addClass("layui-badge-dot");
				} else {
					$("#message").find("span").removeClass("layui-badge-dot");
				}
			} else if (data.errCode == 200061 || data.errCode == 200062) {
				showTicket("请先登录！", 2000, function() {
					parent.location.href = "index.html?v=1.60";
				});
			} else if (data.errCode == 200063) {
				showTicket("没有权限！", 2000);
			} else {
				showTicket(data.errMsg, 2000);
			}
		}
	})
}

setInterval(function() {
	getMsgList();
}, 60000);


window.onload = function() {
	$(".siteName").html(siteName);
	//topTabAdd("test","测试","sss");
	//topTabDelete("sss");
}