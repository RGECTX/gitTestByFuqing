layui.use(['tree', 'util', 'element', 'form', 'table', 'layer'], function() {
	var $ = layui.jquery;
	var tree = layui.tree;
	var util = layui.util;
	var element = layui.element;
	var table = layui.table;
	var form = layui.form;
	var layer = layui.layer;

	var unitTable = table.render({
		elem: '#unitList',
		url: servicePath + "/amUnit/search?charset=utf-8&loginCode=" + adminInfo.loginCode,
		method: 'POST',
		contentType: "application/json; charset=utf-8",
		where: {
			"kwFields": 11,
			"keyword": ""
		}
		/* ,initSort: {
			field: 'orgCode' //排序字段，对应 cols 设定的各字段名
			,type: 'asc' //排序方式  asc: 升序、desc: 降序、null: 默认排序
		} */
		,
		limit: 10,
		limits: [10, 50, 100, 1000, 100000],
		toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
			,
		defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
			title: '提示',
			layEvent: 'LAYTABLE_TIPS',
			icon: 'layui-icon-tips'
		}],
		title: '单位信息数据表',
		cols: [
			[{
				type: 'numbers',
				fixed: 'left',
				title: '序号' 
			}, {
				field: 'orgName',
				title: '单位名称',
				minWidth:200,
				sort: true
			}, {
				field: 'orgCode',
				title: '单位编码',
				width:150,
				sort: true,
				templet: function(d) {
					return String(d.orgCode);
				}
			}, {
				field: 'shortName',
				title: '单位简称',
				Width:200,
				sort: true
			}, {
				field: 'parentName',
				title: '上级单位',
				width:200,
				sort: true,
				templet: function(d) {
					return '<a href="#">' + (d.parentName == 'root' ? '-' : d.parentName) + '</a>'
				}
			}, {
				field: 'orgType',
				title: '单位类别',
				width:120,
				sort: true,
				templet: function(d) {
					return getDictDataLabel("ORG_TYPE", d.orgType);
				}
			}, {
				field: 'orgGroup',
				title: '单位组别',
				width:140,
				sort: true,
				templet: function(d) {
					return getDictDataLabel("ORG_GROUP", d.orgGroup);
				}
			}, {
				field: 'orgRank',
				title: '单位级别',
				width:105,
				sort: true,
				templet: function(d) {
					if (d.orgType == "8") {
						return getDictDataLabel("AM_JWS_RANK", d.orgRank);
					} else if (d.orgType == "16") {
						return getDictDataLabel("AM_JWZ_RANK", d.orgRank);
					} else {
						return getDictDataLabel("AM_ORG_RANK", d.orgRank);
					}

				}
			}, {
				field: 'orgState',
				title: '状态',
				align: 'center',
				width: 80,
				sort: true,
				templet: function(d) {
					return getDictDataLabel("ORG_STATE", d.orgState);
				}
			}, {
				fixed: 'right',
				title: '操作',
				toolbar: '#barDemo',
				width: 115
			}]
		],
		page: true,
		parseData: function(res) { //res 即为原始返回的数据
			return res;
		},
		height: 'full-121',
		response: {
			statusName: 'errCode' //规定数据状态的字段名称，默认：code
				,
			statusCode: 0 //规定成功的状态码，默认：0
				,
			msgName: 'errMsg' //规定状态信息的字段名称，默认：msg
				,
			countName: 'recordCount' //规定数据总数的字段名称，默认：count
				,
			dataName: 'orgList' //规定数据列表的字段名称，默认：data
		},
		request: {
			pageName: 'page' //页码的参数名称，默认：page
				,
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		}
	});

	//头工具栏事件
	table.on('toolbar(unitList)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch (obj.event) {
			case 'addUnit':
				$(".orgRank").find("option").remove();
				layer.open({
					type: 1,
					title: '添加单位',
					area: ['600px', '500px'],
					content: $('#addUnitPage'),
					btn: ['保存', '关闭'],
					yes: function(index, layero) {
						$("#addUnitSubmit").click();
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
	table.on('tool(unitList)', function(obj) {
		var data = obj.data;
		//console.log(obj)
		if (obj.event === 'disable') {
			// console.log(obj);
			layer.confirm('停用单位将会把所有的下级单位都一起停用了，确定要停用吗？', function(index) {
				disableUnit(data.orgId);
				layer.close(index);
			});
		}
		if (obj.event === 'enable') {
			layer.confirm('启用单位只会启用本单位，下级单位中有被停用的单位不会被启用，确定要启用吗？', function(index) {
				enableUnit(data.orgId);
				layer.close(index);
			});
		}
		//修改单位
		if (obj.event === 'edit') {
			$(".orgRank").find("option").remove();
			if (data.orgType == "8") { //警务室
				$(".orgRank").each(function() {
					addDictDataToSelect(this, "AM_JWS_RANK");
				})
			} else if (data.orgType == "16") { //警务站			
				$(".orgRank").each(function() {
					addDictDataToSelect(this, "AM_JWZ_RANK");
				})
			} else {
				$(".orgRank").each(function() {
					addDictDataToSelect(this, "AM_ORG_RANK");
				})
			}
			form.render('select');
			form.val("editUnit", data);
			layer.open({
				type: 1,
				title: '修改单位',
				area: ['600px', '500px'],
				content: $('#editUnitPage'),
				btn: ['保存', '关闭'],
				yes: function(index, layero) {
					$("#editUnitSubmit").click();
				},
				btn2: function(index, layero) {
					layer.close(index);
				}
			});
		}
	});

	//添加单位
	function addUnit(data) {
		var layerIndex = layer.load();
		$.ajax({
			type: "POST",
			url: servicePath + "/amUnit/add?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("添加成功！", 2000, function() {
						layer.closeAll('page');
						document.getElementById("addUnit").reset();
						unitTable.reload();
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

	//修改单位
	function editUnit(data) {
		layerIndex = layer.load();
		$.ajax({
			type: "POST",
			url: servicePath + "/amUnit/update?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("修改成功！", 2000, function() {
						layer.closeAll('page');
						unitTable.reload();
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


	//停用单位
	function disableUnit(orgId) {
		var layerIndex = layer.load();
		$.ajax({
			type: "GET",
			url: servicePath + "/amUnit/disable?charset=utf-8&orgId=" + orgId + "&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("停用成功！", 2000, function() {
						layer.closeAll('page');
						unitTable.reload();
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


	//启用单位
	function enableUnit(orgId) {
		var layerIndex = layer.load();
		$.ajax({
			type: "GET",
			url: servicePath + "/amUnit/enable?charset=utf-8&orgId=" + orgId + "&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("启用成功！", 2000, function() {
						layer.closeAll('page');
						unitTable.reload();
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

	//获取单位
	function getUnit(orgId) {
		var layerIndex = layer.load();
		$.ajax({
			type: "GET",
			url: servicePath + "/amUnit/getAmUnit?charset=utf-8&orgId=" + orgId + "&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					location.href = "unitList.html?v=1.60&parentId=" + data.unit.parentId + "&parentName=" + escape(data.unit.parentName);
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
						$(".parentId").each(function() {
							seladditem(orgList[i].orgId, orgList[i].orgName, this);
						})
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
							unitTable.reload({
								where: {
									orgId: obj.data.id
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

	//搜索单位
	function search() {
		var formData = form.val("searchForm");
		var data = {
			kwFields: 11,
			keyword: formData.keyword,
			orgType: formData.orgType == '' ? null : formData.orgType,
			orgGroup: formData.orgGroup == '' ? null : formData.orgGroup,
			orgLevel: formData.orgLevel == '' ? null : formData.orgLevel
		}
		unitTable.reload({
			where: data
		});
	}


	form.on('select(orgType)', function(data) {
		$(".orgRank").find("option").remove();
		if (data.value == "8") { //警务室
			$(".orgRank").each(function() {
				addDictDataToSelect(this, "AM_JWS_RANK");
			})
		} else if (data.value == "16") { //警务站
			$(".orgRank").each(function() {
				addDictDataToSelect(this, "AM_JWZ_RANK");
			})
		} else {
			$(".orgRank").each(function() {
				addDictDataToSelect(this, "AM_ORG_RANK");
			})
		}
		form.render('select');
	});

	//添加单位“保存”按钮触发
	form.on('submit(addUnit)', function(data) {
		addUnit(form.val("addUnit"));
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});

	//修改单位“保存”按钮触发
	form.on('submit(editUnit)', function(data) {
		editUnit(form.val("editUnit"));
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});

	//搜索单位“查询”按钮触发
	form.on('submit(searchBtn)', function(data) {
		search();
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});
	$(".orgType").each(function() {
		addDictDataToSelect(this, "ORG_TYPE");
	})
	$(".orgGroup").each(function() {
		addDictDataToSelect(this, "ORG_GROUP");
	})

	form.render('select');

	//设置上级单位下拉列表框
	setAmUnitDropList();

});
