layui.use(['util', 'element', 'form', 'table', 'layer'], function() {
	var $ = layui.jquery;
	var util = layui.util;
	var element = layui.element;
	var table = layui.table;
	var form = layui.form;
	var layer = layui.layer;
	var funPowerTreeComplete = false;
	var menuPowerTreeComplete = false;
	var loadingIndex = layer.load();
	var interval = setInterval(function() {
		if (funPowerTreeComplete && menuPowerTreeComplete) {
			clearInterval(interval);
			layer.close(loadingIndex);
		}
	}, 500);
	$(document).ready(function() {
		getMenuPowerTree();
		getFunPowerTree();
	});
	var roleTable = table.render({
		elem: '#roleList',
		url: servicePath + "/role/search?charset=utf-8&loginCode=" + adminInfo.loginCode,
		method: 'POST',
		contentType: "application/json; charset=utf-8",
		where: {
			"kwFields": 3,
			"keyword": ""
		},
		limit: 10,
		limits: [10, 50, 100, 1000, 10000],
		toolbar: '#toolbarDemo', //开启头部工具栏，并为其绑定左侧模板
		defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
			title: '提示',
			layEvent: 'LAYTABLE_TIPS',
			icon: 'layui-icon-tips'
		}],
		title: '角色列表',
		cols: [
			[{
				type: 'checkbox',
				fixed: 'left'
			}, {
				field: 'roleName',
				title: '角色名称',
				width: 300
			}, {
				field: 'roleType',
				title: '角色类别',
				width: 150,
				templet: function(d) {
					return getDictDataLabel("ROLE_TYPE", d.roleType);
				}
			}, {
				field: 'userList',
				title: '用户',
				minWidth: 800,
				templet: function(d) {
					var userList = d.userList;
					var users = "";
					if (userList == undefined) {
						return "";
					}
					for (var i = 0; i < userList.length; i++) {
						if (i < userList.length - 1) {
							users += d.userList[i].name + ",";
						} else {
							users += d.userList[i].name;
						}
					}
					return users;
				}
			}, {
				field: 'isSys',
				title: '系统内置',
				width: 100,
				templet: function(d) {
					return d.isSys == 1 ? "是" : "否";
				}
			}, {
				field: 'roleDescription',
				title: '描述',
				width: 200
			}, {
				fixed: 'right',
				title: '操作',
				toolbar: '#barDemo',
				width: 320
			}]
		],
		page: true,
		parseData: function(res) { //res 即为原始返回的数据
			return res;
		},
		height: 'full-105',
		response: {
			statusName: 'errCode' //规定数据状态的字段名称，默认：code
				,
			statusCode: 0 //规定成功的状态码，默认：0
				,
			msgName: 'errMsg' //规定状态信息的字段名称，默认：msg
				,
			countName: 'recordCount' //规定数据总数的字段名称，默认：count
				,
			dataName: 'roleList' //规定数据列表的字段名称，默认：data
		},
		request: {
			pageName: 'page' //页码的参数名称，默认：page
				,
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		}
	});

	//头工具栏事件
	table.on('toolbar(roleList)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch (obj.event) {
			//添加角色
			case 'addRole':
				document.getElementById("addRole").reset();
				layer.open({
					type: 1,
					title: '添加角色',
					area: ['600px', '500px'],
					content: $('#addRolePage'),
					btn: ['保存', '关闭'],
					yes: function(index, layero) {
						$("#addRoleSubmit").click();
					},
					btn2: function(index, layero) {
						layer.close(index);
					}
				});
				break;
			case 'getCheckLength':
				var data = checkStatus.data;
				layer.msg('选中了：' + data.length + ' 个');
				break;
			case 'isAll':
				layer.msg(checkStatus.isAll ? '全选' : '未全选');
				break;

				//自定义头工具栏右侧图标 - 提示
			case 'LAYTABLE_TIPS':
				layer.alert('这是工具栏右侧自定义的一个图标按钮');
				break;
		};
	});

	//监听行工具事件
	table.on('tool(roleList)', function(obj) {
		var data = obj.data;
		//console.log(obj)
		//设置权限
		if (obj.event === 'addPowersToRole') {
			var menuPowerTree = $.fn.zTree.getZTreeObj("menuPowerTree");
			var funPowerTree = $.fn.zTree.getZTreeObj("funPowerTree");
			menuPowerTree.checkAllNodes(false); //先取消所有的选中状态
			funPowerTree.checkAllNodes(false); //先取消所有的选中状态
			getRolePowerList(data.roleId);
			layer.open({
				type: 1,
				title: '设置权限',
				area: ['600px', '500px'],
				content: $('#setRolePower'),
				btn: ['保存', '关闭'],
				yes: function(index, layero) {
					var menuPowerTreeList = menuPowerTree.getCheckedNodes(true);
					var funPowerTreeList = funPowerTree.getCheckedNodes(true);
					var submitData = {
						roleId: data.roleId
					}
					var powerList = [];

					function setTree(data) { //遍历树  获取id数组
						for (var i in data) {
							if (!data[i].children && data[i].checked) { //只保存根节点
								powerList.push({
									powerCode: data[i].id,
									powerLevel: 2
								});
							}
							if (data[i].children) {
								setTree(data[i].children);
							}
						}
					}
					if (menuPowerTreeList.length > 0) {
						setTree(menuPowerTreeList[0].children);
					}
					if (funPowerTreeList.length > 0) {
						setTree(funPowerTreeList[0].children);
					}
					submitData.powerList = powerList;
					assignPowersToRole(submitData);
				},
				btn2: function(index, layero) {
					layer.close(index);
				}
			});
		}
		if (obj.event === 'del') {
			if (data.roleId == 1) {
				showTicket("超级管理员角色不能删除！", 2000);
				return;
			}
			layer.confirm('确定要删除本角色吗？', function(index) {
				obj.del();
				deleteRole(data.roleId);
				layer.close(index);
			});
		}
		//修改角色
		if (obj.event === 'edit') {
			document.getElementById("editRole").reset();
			form.val("editRole", data);
			layer.open({
				type: 1,
				title: '修改角色',
				area: ['600px', '500px'],
				content: $('#editRolePage'),
				btn: ['保存', '关闭'],
				yes: function(index, layero) {
					$("#editRoleSubmit").click();
				},
				btn2: function(index, layero) {
					layer.close(index);
				}
			});
		}
		//给角色分配人员
		if (obj.event === 'assignsUsersToRole') {
			var userList = data.userList;
			var checkedData = [];
			for (var i = 0; i < userList.length; i++) {
				checkedData.push(userList[i].userId);
			}
			top.openSelectRy({
				url: 'selectFjry.html',
				title: '选择人员',
				resource: '/user/selectAdmin',
				where: {
					state: 1,
					kwFields: 3,
					keyword: ""
				},
				treeWhere: {},
				area: ['1024px', '768px'],
				page: true,
				limit: 12,
				limits: [12, 30, 50, 100, 500, 1000, 5000],
				selectType: 'checkbox', //radio,checkbox
				checkedData: checkedData, //默认选中的数据，只对selectType为radio起作用
				disabledData: [], //默认禁用的数据
				compareField: 'userId', //用于识别选中或禁用的字段
				isRequire: false
			}, window.name, function(returnData, index) {
				var sumbitData = {};
				sumbitData.roleId = data.roleId;
				var userIdList = [];
				for (var i = 0; i < returnData.length; i++) {
					userIdList.push(returnData[i].userId);
				}
				sumbitData.userIdList = userIdList;
				assignsUsersToRole(sumbitData);
				top.layer.close(index);
			});
		}
	});

	//获取功能权限树
	function getFunPowerTree() {
		$.ajax({
			type: "POST",
			url: servicePath + "/powerItem/getFunPowerTree?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify({}),
			dataType: "json",
			success: function(data) {
				funPowerTreeComplete = true;
				var treeData = [{
					id: "0",
					title: "功能权限",
					spread: true,
					children: data.funPowerTree
				}];
				var setting = {
					check: {
						enable: true
					},
					data: {
						key: {
							children: "children",
							name: 'title'
						},
						simpleData: {
							enable: true,
							checked: 'checked',
							idKey: "id",
							rootPId: 0
						}
					}
				};
				if (data.errCode == 0) {
					$("#funPowerTree").empty();
					$.fn.zTree.init($("#funPowerTree"), setting, treeData).expandAll(true);
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！", 2000, function() {
						parent.location.href = "index.html?v=1.60";
					});
				}
			}
		})
	}

	//获取菜单权限树
	function getMenuPowerTree() {
		$.ajax({
			type: "POST",
			url: servicePath + "/powerItem/getMenuPowerTree?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify({}),
			dataType: "json",
			success: function(data) {
				menuPowerTreeComplete = true;
				var treeData = [{
					id: "0",
					title: "菜单权限",
					children: data.menuPowerTree
				}];
				var setting = {
					check: {
						enable: true
					},
					data: {
						key: {
							children: "children",
							name: 'title'
						},
						simpleData: {
							enable: true,
							checked: 'checked',
							idKey: "id",
							rootPId: 0
						}
					}
				};
				if (data.errCode == 0) {
					$("#menuPowerTree").empty();
					$.fn.zTree.init($("#menuPowerTree"), setting, treeData).expandAll(true);
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！", 2000, function() {
						parent.location.href = "index.html?v=1.60";
					});
				}
			}
		})
	}

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

	//添加角色
	function addRole(data) {
		var layerIndex = layer.load();
		$.ajax({
			type: "POST",
			url: servicePath + "/role/add?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("添加成功！", 2000, function() {
						layer.closeAll('page');
						document.getElementById("addRole").reset();
						roleTable.reload();
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

	//修改角色
	function editRole(data) {
		layerIndex = layer.load();
		$.ajax({
			type: "POST",
			url: servicePath + "/role/update?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("修改成功！", 2000, function() {
						layer.closeAll('page');
						roleTable.reload();
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


	//删除角色
	function deleteRole(roleId) {
		var layerIndex = layer.load();
		$.ajax({
			type: "GET",
			url: servicePath + "/role/delete?charset=utf-8&roleId=" + roleId + "&loginCode=" + adminInfo.loginCode,
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

	//给角色分配权限
	function assignPowersToRole(data) {
		layerIndex = layer.load();
		$.ajax({
			type: "POST",
			url: servicePath + "/permission/assignPowersToRole?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					reloadUser();
					showTicket("设置成功！", 2000, function() {
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
	};

	//给角色分配用户
	function assignsUsersToRole(data) {
		layerIndex = layer.load();
		$.ajax({
			type: "POST",
			url: servicePath + "/permission/assignsUsersToRole?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					roleTable.reload();
					showTicket("设置成功！", 2000, function() {
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
	};




	//获取角色权限
	function getRolePowerList(roleId) {
		var layerIndex = layer.load(0, {
			zIndex: 99999999
		});
		$.ajax({
			type: "GET",
			url: servicePath + "/permission/getRolePowerList?charset=utf-8&roleId=" + roleId + "&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
				if (data.errCode == 0) {
					var permission = data.permission;
					var checkeds = [];
					var menuPowerTree = $.fn.zTree.getZTreeObj("menuPowerTree");
					var funPowerTree = $.fn.zTree.getZTreeObj("funPowerTree");
					for (var key in permission) {
						if (permission[key] == 2) {
							checkeds.push(key);
							var node = menuPowerTree.getNodeByParam("id", key);
							var nodet = funPowerTree.getNodeByParam("id", key);
							if (node) {
								menuPowerTree.checkNode(node, true, true);
							}
							if (nodet) {
								funPowerTree.checkNode(nodet, true, true);
							}

						}
					}
					layer.close(layerIndex);
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

	//保存后，重新获取用户权限
	function reloadUser() {
		$.ajax({
			type: "GET",
			url: servicePath + "/user/getLoginInfo?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
				if (data.errCode == 0) {
					greathack.setSessionData("adminInfo", data.admin);
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！", 2000, function() {
						parent.location.href = "index.html?v=1.60";
					});
				}
			}
		})
	}

	//搜索角色
	function search() {
		var formData = form.val("searchForm");
		var data = {
			kwFields: 3,
			keyword: formData.keyword,
			roleType: formData.roleType,
			roleState: formData.roleState
		}
		roleTable.reload({
			where: data
		});
	}

	$(".roleType").each(function() {
		addDictDataToSelect(this, "ROLE_TYPE");
	})
	form.render("select");

	//添加角色“保存”按钮触发
	form.on('submit(addRole)', function(data) {
		addRole(form.val("addRole"));
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});

	//修改角色“保存”按钮触发
	form.on('submit(editRole)', function(data) {
		editRole(form.val("editRole"));
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});

	//搜索角色“查询”按钮触发
	form.on('submit(searchBtn)', function(data) {
		search();
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});
});
