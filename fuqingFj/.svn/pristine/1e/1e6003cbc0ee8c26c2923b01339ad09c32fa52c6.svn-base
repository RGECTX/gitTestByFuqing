layui.use(['tree', 'util', 'element', 'form', 'table', 'layer'], function() {
	var $ = layui.jquery;
	var tree = layui.tree;
	var util = layui.util;
	var element = layui.element;
	var table = layui.table;
	var form = layui.form;
	var layer = layui.layer;

	var currentUnitId = null;
	var currentUnitName = null;

	var adminTable = table.render({
		elem: '#adminList',
		url: servicePath + "/user/search?charset=utf-8&loginCode=" + adminInfo.loginCode,
		method: 'POST',
		contentType: "application/json; charset=utf-8",
		where: {
			"kwFields": 3,
			"keyword": ""
		},
		limit: 10,
		limits: [10, 50, 100, 1000, 10000],
		toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
			,
		defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
			title: '提示',
			layEvent: 'LAYTABLE_TIPS',
			icon: 'layui-icon-tips'
		}],
		title: '用户列表',
		cols: [
			[{
				type: 'checkbox',
				fixed: 'left'
			}, {
				field: 'loginName',
				title: '登录名',
				width: 100,
				fixed: 'left'
			}, {
				field: 'name',
				title: '姓名',
				width: 100
			}, {
				field: 'unitName',
				title: '单位',
				width: 150
			}, {
				field: 'sex',
				title: '性别',
				width: 80,
				sort: 'true',
				align: 'center',
				templet: function(d) {
					return getDictDataLabel("SEX", d.sex);
				}
			}, {
				field: 'idcard',
				title: '身份证号',
				width: 180
			}, {
				field: 'mobile',
				title: '手机号',
				width: 120
			}, {
				field: 'roleList',
				title: '角色',
				minWidth: "500",
				templet: function(d) {
					var roleList = d.roleList;
					var roles = "";
					if (roleList == undefined) {
						return "";
					}
					for (var i = 0; i < roleList.length; i++) {
						if (i < roleList.length - 1) {
							roles += d.roleList[i].roleName + ",";
						} else {
							roles += d.roleList[i].roleName;
						}
					}
					return roles;
				}
			}, {
				field: 'state',
				title: '状态',
				sort: true,
				templet: function(d) {
					if (d.state == '1') {
						return "正常";
					} else if (d.state == '2') {
						return "禁用";
					} else {
						return;
					}
				}
			}, {
				fixed: 'right',
				title: '操作',
				align: 'center',
				width: 220,
				toolbar: '#barDemo'
			}]
		],
		page: true,
		parseData: function(res) { //res 即为原始返回的数据
			return res;
		},
		height: 'full-131',
		response: {
			statusName: 'errCode', //规定数据状态的字段名称，默认：code
			statusCode: 0, //规定成功的状态码，默认：0
			msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
			countName: 'recordCount', //规定数据总数的字段名称，默认：count
			dataName: 'userList' //规定数据列表的字段名称，默认：data
		},
		request: {
			pageName: 'page', //页码的参数名称，默认：page
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		},
		done: function(res, curr, count) {

		}
	});

	//头工具栏事件
	table.on('toolbar(adminList)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch (obj.event) {
			//添加用户
			case 'addUser':
				$("#add_amUnitId").val(currentUnitId);
				form.render("select");
				layer.open({
					type: 1,
					title: '添加用户',
					area: ['600px', '500px'],
					content: $('#addUserPage'),
					btn: ['保存', '关闭'],
					yes: function(index, layero) {
						$("#addAdminSubmit").click();
					},
					btn2: function(index, layero) {
						layer.close(index);
					}
				});
				break;
			case 'resetPwd':
				if (checkStatus.data.length == 0) {
					showTicket('没有选中记录', 1000);
					return;
				}
				resetPwd(checkStatus.data);
				break;
		};
	});

	//监听行工具事件
	table.on('tool(adminList)', function(obj) {
		var data = obj.data;
		//设置角色
		if (obj.event === 'addRolesToUser') {
			// console.log(data.roleList);
			document.getElementById("adminRoles").reset();
			getRoleList(data.roleList);
			layer.open({
				type: 1,
				title: '设置角色',
				area: ['600px', '500px'],
				content: $('#setUserRole'),
				btn: ['保存', '关闭'],
				yes: function(index, layero) {
					saveRole(data.userId);
				},
				btn2: function(index, layero) {
					layer.close(index);
				}
			});
		}
		if (obj.event === 'del') {
			if (data.userId == 1) {
				showTicket("超级管理员不能删除！", 2000);
				return;
			}
			layer.confirm('确定要删除本用户吗？', function(index) {
				obj.del();
				deleteAdmin(data.userId);
				layer.close(index);
			});
		}
		//修改用户
		if (obj.event === 'edit') {
			document.getElementById("editAdmin").reset();
			form.val("editAdmin", data);
			form.render("select");
			layer.open({
				type: 1,
				title: '修改用户',
				area: ['600px', '500px'],
				content: $('#editUserPage'),
				btn: ['保存', '关闭'],
				yes: function(index, layero) {
					$("#editAdminSubmit").click();
				},
				btn2: function(index, layero) {
					layer.close(index);
				}
			});
		}
	});

	//获取单位树
	function getAmUnitTree() {
		$.ajax({
			type: "POST",
			url: servicePath + "/amUnit/getAmUnitTree?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify({}),
			dataType: "json",
			success: function(data) {
				if (data.errCode == 0) {
					tree.render({
						elem: '#amUnitTree',
						data: data.orgTree,
						onlyIconControl: true,
						click: function(obj) {
							currentUnitId = obj.data.id;
							currentUnitName = obj.data.title;
							adminTable.reload({
								where: {
									"amUnitId": obj.data.id
								}
							});
						}
					});
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！", 2000, function() {
						parent.location.href = "index.html?v=1.60";
					});
				}
			}
		})
	}
	getAmUnitTree();
	$("#amUnitTree").height($(".col2").height());

	//设置单位下拉列表框
	function setAmUnitDropList() {
		$.ajax({
			type: "POST",
			url: servicePath + "/amUnit/search?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify({
				"page": 1,
				"pageSize": 1000
			}),
			dataType: "json",
			success: function(data) {
				if (data.errCode == 0) {
					var orgList = data.orgList;
					for (var i = 0; i < orgList.length; i++) {
						seladditem(orgList[i].orgId, orgList[i].orgName, document.getElementById("add_amUnitId"));
						seladditem(orgList[i].orgId, orgList[i].orgName, document.getElementById("edit_amUnitId"));
					}
					form.render('select');
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！", 2000, function() {
						parent.location.href = "index.html?v=1.60";
					});
				}
			}
		})
	}
	//设置单位下拉列表框
	setAmUnitDropList();

	//添加用户
	function addAdmin(data) {
		var layerIndex = layer.load();
		$.ajax({
			type: "POST",
			url: servicePath + "/user/add?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("添加成功！", 2000, function() {
						layer.closeAll('page');
						document.getElementById("addAdmin").reset();
						adminTable.reload();
					});
				} else if (data.errCode == 200050) {
					showTicket("用户已存在", 2000);
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

	//批量重置密码
	function resetPwd(checkedData) {
		var loginNameList = [];
		for (var i = 0; i < checkedData.length; i++) {
			loginNameList.push(checkedData[i].loginName);
		}
		var layerIndex = layer.load();
		$.ajax({
			type: "POST",
			url: servicePath + "/user/resetPwd?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(loginNameList),
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("重置密码成功！", 2000);
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

	//修改用户
	function editAdmin(data) {
		var layerIndex = layer.load();
		$.ajax({
			type: "POST",
			url: servicePath + "/user/update?charset=utf-8&loginCode=" + adminInfo.loginCode,
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

	//删除用户
	function deleteAdmin(userId) {
		var layerIndex = layer.load();
		$.ajax({
			type: "GET",
			url: servicePath + "/user/delete?charset=utf-8&userId=" + userId + "&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("删除成功", 1000);
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
	};

	//搜索用户
	function search() {
		var formData = form.val("searchForm");
		var data = {
			kwFields: 3,
			keyword: formData.keyword,
			state: formData.state
		}
		adminTable.reload({
			where: data
		});
	}

	var rolesObj = null;

	function getRolesObj(roleList) {
		rolesObj = {};
		for (var i = 0; i < roleList.length; i++) {
			rolesObj[roleList[i].roleId] = roleList[i];
		}
	}

	//获取角色列表
	function getRoleList(roleCheckedList) {
		$.ajax({
			type: "POST",
			url: servicePath + "/role/search?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify({
				"page": 1,
				"pageSize": 1000
			}),
			dataType: "json",
			success: function(data) {
				if (data.errCode == 0) {
					getRolesObj(roleCheckedList);
					$("#glRole").html("");
					$("#spRole").html("");
					var roleList = data.roleList;
					for (var i = 0; i < roleList.length; i++) {
						if (rolesObj[roleList[i].roleId] != undefined) {
							if (roleList[i].roleType == "1") {
								$("#spRole").append('<input type="checkbox" name="role" lay-filter="role" lay-skin="primary" title="' +
									roleList[i].roleName + '"  value="' + roleList[i].roleId + '" checked>');
							} else {
								$("#glRole").append('<input type="checkbox" name="role" lay-filter="role" lay-skin="primary" title="' +
									roleList[i].roleName + '"  value="' + roleList[i].roleId + '" checked>');
							}
						} else {
							if (roleList[i].roleType == "1") {
								$("#spRole").append('<input type="checkbox" name="role" lay-filter="role" lay-skin="primary" title="' +
									roleList[i].roleName + '"  value="' + roleList[i].roleId + '">');
							} else {
								$("#glRole").append('<input type="checkbox" name="role" lay-filter="role" lay-skin="primary" title="' +
									roleList[i].roleName + '"  value="' + roleList[i].roleId + '">');
							}
						}
					}
					form.render("checkbox");
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！", 2000, function() {
						parent.location.href = "index.html?v=1.60";
					});
				}
			}
		})
	}

	//保存用户角色
	function saveRole(userId) {
		var layerIndex = layer.load();
		var roleIdList = new Array();
		$("input:checkbox[name='role']:checked").each(function(i) { // 遍历name=standard的多选框
			roleIdList[i] = $(this).attr('value');
		});
		var url = servicePath + "/permission/assignRolesToUser?charset=utf-8&loginCode=" + adminInfo.loginCode;
		var data = {
			"userId": userId,
			"roleIdList": roleIdList,
		}
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("保存成功！", 1000, function() {
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
	};

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
		},
		editRepassword: function(value, item) {
			if ($('#edit_password').val() != $('#edit_repassword').val()) {
				return "两次密码输入不一致，请重新输入";
			}
		},
		editPassword: function(value, item) {
			if (value != "") {
				if (!(/^[\S]{6,12}$/.test(value))) {
					return '密码必须6到12位，且不能出现空格';
				}
			}
		}
	});

	$(".sex").each(function() {
		addDictDataToSelect(this, "SEX");
	})

	//添加用户“保存”按钮触发
	form.on('submit(addAdmin)', function(data) {
		addAdmin(form.val("addAdmin"));
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});

	//修改用户“保存”按钮触发
	form.on('submit(editAdmin)', function(data) {
		editAdmin(form.val("editAdmin"));
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});

	//搜索用户“查询”按钮触发
	form.on('submit(searchBtn)', function(data) {
		search();
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});

});
