layui.use(['tree', 'util', 'element', 'form', 'table', 'layer'], function() {
	var $ = layui.jquery;
	var tree = layui.tree;
	var util = layui.util;
	var element = layui.element;
	var table = layui.table;
	var form = layui.form;
	var layer = layui.layer;

	var url = servicePath + "/amQuotas/search?charset=utf-8&loginCode=" + adminInfo.loginCode;
	var amQuotasTable = table.render({
		elem: '#unitQuotasList',
		url: url,
		method: 'POST',
		contentType: "application/json; charset=utf-8",
		where: {
			"kwFields": 3,
			"keyword": ""
		},
		limit: 10,
		limits: [10, 50, 100, 1000, 100000],
		toolbar: '#toolbarDemo', //开启头部工具栏，并为其绑定左侧模板
		defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
			title: '提示',
			layEvent: 'LAYTABLE_TIPS',
			icon: 'layui-icon-tips'
		}],
		title: '辅警档案信息数据表',
		cols: [
			[{
					type: 'numbers',
					fixed: 'center',
					title: '序号' 
				}, {
					field: 'orgName',
					title: '单位名称',
					minWidth: 200,
					sort: true
				},
				{
					field: 'orgCode',
					title: '单位编码',
					width: 150,
					sort: true,
					templet: function(d) {
						return String(d.orgCode);
					}
				}, {
					field: 'orgType',
					title: '单位类别',
					width: 120,
					sort: true,
					templet: function(d) {
						return getDictDataLabel("ORG_TYPE", d.orgType);
					}
				}, {
					field: 'orgGroup',
					title: '单位组别',
					width: 140,
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
					field: 'quotasNum',
					title: '编制数',
					align: 'right',
					width: 90,
					edit: 'text',
					sort: true
				},
				{
					field: 'allocateNum',
					title: '实配数',
					align: 'right',
					edit: 'text',
					width: 90,
					sort: true
				},
				{
					field: 'jdNum',
					title: '借调数',
					edit: 'text',
					align: 'right',
					width: 90,
					sort: true
				},
				{
					field: 'sxNum',
					title: '实习数',
					edit: 'text',
					align: 'right',
					width: 90,
					sort: true
				}
				/* ,{field:'docNo', title:'文号'}
				,{field:'pzrq', title:'批准时间'} */
				, {
					fixed: 'right',
					title: '操作',
					toolbar: '#barDemo',
					align: 'center',
					width: 80
				}
			]
		],
		page: true,
		parseData: function(res) { //res 即为原始返回的数据
			return res;
		},
		height: 'full-151',
		response: {
			statusName: 'errCode', //规定数据状态的字段名称，默认：code
			statusCode: 0, //规定成功的状态码，默认：0
			msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
			countName: 'recordCount', //规定数据总数的字段名称，默认：count
			dataName: 'amQuotasList' //规定数据列表的字段名称，默认：data
		},
		request: {
			pageName: 'page', //页码的参数名称，默认：page
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		}
	});

	//头工具栏事件
	table.on('toolbar(unitQuotasList)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch (obj.event) {
			case 'batchSetQuotasAllocateNum':
				layer.confirm('确定要重新计算吗？会比较久，请耐心等候，不要做其他操作。', function(index) {
					batchSetQuotasAllocateNum();
					layer.close(index);
				});
				break;
		};
	});

	//监听行工具事件
	table.on('tool(unitQuotasList)', function(obj) {
		var data = obj.data;
		//console.log(obj)
		if (obj.event === 'edit11') {
			layer.prompt({
				formType: 2,
				value: data.email
			}, function(value, index) {
				obj.update({
					email: value
				});
				layer.close(index);
			});
		}
		//辅警编制
		if (obj.event === 'edit') {
			form.val("edit", data);
			layer.open({
				type: 1,
				title: '修改字典类型',
				area: ['600px', '500px'],
				content: $('#editPage'),
				btn: ['保存', '关闭'],
				yes: function(index, layero) {
					$("#editSubmit").click();
				},
				btn2: function(index, layero) {
					layer.close(index);
				}
			});
		}
	});

	//监听单元格编辑
	table.on('edit(unitQuotasList)', function(obj) {
		var value = obj.value //得到修改后的值
		var data = obj.data //得到所在行所有键值
		var field = obj.field; //得到字段
		editEvent(data, field, value)
		//layer.msg('[ID: '+ data.id +'] ' + field + ' 字段更改为：'+ value);
	});

	//列表直接修改辅警编制
	function editEvent(data1, field, value) {
		$.ajax({
			type: "POST",
			url: servicePath + "/amQuotas/update?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data1),
			dataType: "json",
			success: function(data) {
				if (data.errCode == 0) {
					if (field == 'quotasNum') {
						layer.msg('核定数：字段更改为：' + value);
					} else if (field == 'allocateNum') {
						layer.msg('已配数：字段更改为：' + value);
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

	//修改辅警编制
	function edit(data) {
		var layerIndex = layer.load();
		$.ajax({
			type: "POST",
			url: servicePath + "/amQuotas/update?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("修改成功！", 1000, function() {
						layer.closeAll('page');
						amQuotasTable.reload();
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

	//计算已配数
	function batchSetQuotasAllocateNum() {
		var layerIndex = layer.load();
		$.ajax({
			type: "GET",
			url: servicePath + "/amQuotas/batchSetQuotasAllocateNum?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("计算完成！", 1000, function() {
						amQuotasTable.reload();
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

	//修改辅警编制“保存”按钮触发
	form.on('submit(edit)', function(data) {
		edit(form.val("edit"));
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
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
							amQuotasTable.reload({
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

	$(".orgType").each(function() {
		addDictDataToSelect(this, "ORG_TYPE");
	})
	$(".orgGroup").each(function() {
		addDictDataToSelect(this, "ORG_GROUP");
	})
	form.render('select');

	//搜索报名信息
	function search() {
		var formData = form.val("searchForm");
		var data = {
			kwFields: 3,
			keyword: formData.keyword,
			orgType: formData.orgType == '' ? null : formData.orgType,
			orgGroup: formData.orgGroup == '' ? null : formData.orgGroup,
			orgLevel: formData.orgLevel == '' ? null : formData.orgLevel
		}
		amQuotasTable.reload({
			where: data
		});
	}

	//搜索用户“查询”按钮触发
	form.on('submit(searchBtn)', function(data) {
		search()
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});

});

window.onload = function() {

}
