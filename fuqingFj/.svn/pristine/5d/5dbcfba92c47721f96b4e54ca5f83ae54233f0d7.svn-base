var gzybId = getParameter('gzybId');
var sbState = getParameter('sbState');
var month = getParameter('month');
var amPayrollTable;
layui.use(['form', 'table', 'jquery'], function() {
	var table = layui.table;
	var form = layui.form;
	var $ = layui.jquery;
	var jbxxIdList = [];

	amPayrollTable = table.render({
		elem: '#amPayrollList',
		url: servicePath + "/auxPay/search?charset=utf-8&loginCode=" + adminInfo.loginCode,
		method: 'POST',
		where: {
			"gzybId": gzybId
		},
		contentType: "application/json; charset=utf-8",
		limit: 10,
		limits: [10, 50, 100, 1000, 10000],
		toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
			,
		defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
			title: '提示',
			layEvent: 'LAYTABLE_TIPS',
			icon: 'layui-icon-tips'
		}],
		title: '工资记录数据表',
		cols: [
			[{
				type: 'numbers',
				fixed: 'center'
			}, {
				field: 'xm',
				title: '姓名',
				fixed: 'left',
				sort: true,
				width: 120
			}, {
				field: 'idcard',
				title: '身份证号码',
				width: 180,
				sort: true,
				templet: function(d) {
					return "\t" + d.idcard;
				}
			}, {
				field: 'orgName',
				title: '工作单位名称',
				width: 150,
				sort: true
			}, {
				field: 'mobile',
				title: '手机号码',
				width: 120,
				sort: true,
				templet: function(d) {
					return "\t" + d.mobile;
				}
			}, {
				field: 'gzkh',
				title: '工资卡号',
				width: 180,
				sort: true,
				templet: function(d) {
					return "\t" + d.gzkh;
				}
			}, {
				field: 'khhmc',
				title: '开户行名称',
				width: 150,
				sort: true
			},  {
				field: 'sbjf',
				title: '社保缴费',
				align: 'right',
				width: 105,
				sort: true,
				templet: function(d) {
					return d.sbjf || '0';
				}
			}, {
				field: 'gwgz',
				title: '岗位工资',
				width: 105,
				align: 'right',
				sort: true,
				templet: function(d) {
					return d.gwgz || '0';
				}
			}, {
				field: 'qtkk',
				title: '应扣工资',
				align: 'right',
				width: 105,
				sort: true,
				templet: function(d) {
					return d.qtkk || '0';
				}
			}, {
				field: 'sfgz',
				title: '实发总额',
				align: 'right',
				width: 105,
				sort: true,
				templet: function(d) {
					return d.sfgz || '0';
				}
			}, {
				field: 'remark',
				minWidth: 200,
				title: '备注',
				templet: function(d) {
					return d.remark == null || d.remark == '' ? "" : d.remark.replace(/[\r\n]/g, '');
				}
			}, {
				fixed: 'right',
				title: '操作',
				toolbar: '#barDemo',
				width: 180
			}]
		],
		page: true,
		parseData: function(res) { //res 即为原始返回的数据
			return res;
		},
		height: 'full-105',
		response: {
			statusName: 'errCode', //规定数据状态的字段名称，默认：code
			statusCode: 0, //规定成功的状态码，默认：0
			msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
			countName: 'recordCount', //规定数据总数的字段名称，默认：count
			dataName: 'auxPayList' //规定数据列表的字段名称，默认：data
		},
		request: {
			pageName: 'page', //页码的参数名称，默认：page
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		},
		done: function(res, curr, count) {
			var amPayrollList = res.auxPayList;
			for (var i = 0; i < amPayrollList.length; i++) {
				jbxxIdList.push(amPayrollList[i].jbxxId);
			}
		}
	});

	//头工具栏事件
	table.on('toolbar(amPayrollList)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch (obj.event) {
			case 'selectRy':
				top.openSelectDaryID({
					url: 'auxiliary/selectDary.html',
					title: '选择人员',
					resource: '/auxDagl/search',
					where: {
						/*orgId: adminInfo.amUnitId,
						state: 47*/
					},
					treeWhere: {
						/*orgId: adminInfo.amUnitId,
						state: 15*/
					},
					area: ['1024px', '768px'],
					page: true,
					limit: 10,
					limits: [10, 50, 100, 1000, 10000],
					selectType: 'checkbox', //radio,checkbox
					checkedData: [], //默认选中的数据，只对selectType为radio起作用
					disabledData: [jbxxIdList], //默认禁用的数据
					compareField: '' //用于识别选中或禁用的字段
				}, window.name, function(returnData, layerIndex) {
					checkedData = returnData;
					var dataList = [];
					if (gzybId == null) {
						showTicket("辅警工资月报id必填", 2000);
					}
					for (var i = 0; i < returnData.length; i++) {
						var obj = {
							gzybId: gzybId,
							jbxxId: returnData[i].daId,
							idcard: returnData[i].idcard,
							orgId: returnData[i].orgId,
							orgName: returnData[i].orgName,
							mobile: returnData[i].lxfs,
							xm: returnData[i].xm
						};
						dataList.push(obj);
					}
					addAmPayrollList(dataList);
					top.layer.close(layerIndex);
				});
				break;
				/*
			case 'updateWwkhgz':
				var layerIndex = layer.load();
				var params = {
					"gzybId": gzybId
				};
				$.ajax({
					type: "POST",
					url: servicePath + "/amPayroll/updateWwkhgz?charset=utf-8&loginCode=" + adminInfo.loginCode,
					contentType: "application/json; charset=utf-8",
					data: JSON.stringify(params),
					dataType: "json",
					success: function(data) {
						layer.close(layerIndex);
						if (data.errCode == 0) {
							showTicket("更新成功", 1000);
						} else if (data.errCode == 200061 || data.errCode == 200062) {
							showTicket("请先登录！", 2000, function() {
								parent.location.href = "index.html?v=1.0";
							});
						} else if (data.errCode == 200063) {
							showTicket("没有权限！", 2000);
						} else {
							showTicket(data.errMsg, 2000);
						}
						amPayrollTable.reload();
					}
				})
				break;
				*/
			case 'importDataBtn':
				document.getElementById("importDataForm").reset();
				layer.open({
					type: 1,
					title: '导入',
					area: ['400px', '380px'],
					content: $('#importDataPage'),
					btn: ['导入', '取消'],
					yes: function(index, layero) {
						$("#importDataSubmit").click();
					},
					btn2: function(index, layero) {
						layer.close(index);
					}
				});
				break;
			   
		};
	});

	//监听行工具事件
	table.on('tool(amPayrollList)', function(obj) {
		var data = obj.data;
		if (obj.event === 'edit') {
			top.topTabAdd("修改工资记录", '<div class="layui-body"><iframe name="am/editAmPayroll.html?v=1.60&id=' + data.id +
				'" src="am/editAmPayroll.html?v=1.60&id=' + data.id + '&iframeName=' + escape(window.name) +
				'" frameborder="0" class="layadmin-iframe"></iframe></div>', 'am/editAmPayroll.html?v=1.60&id=' + data.id)
		}
		if (obj.event === 'del') {
			layer.confirm('确定删除工资记录吗？', function(index) {
				obj.del();
				del(data.id);
				layer.close(index);
			});
		}

		if (obj.event === 'view') {
			top.topTabAdd("查看工资记录",
				'<div class="layui-body"><iframe src="am/viewAmPayroll.html?v=1.60&id=' + data.id +
				'" frameborder="0" class="layadmin-iframe"></iframe></div>',
				'am/viewAmPayroll.html?v=1.60&id=' + data.id)
		}
	});
	
	//搜索“查询”按钮触发
	form.on('submit(searchSubmit)', function(data) {
		search();
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});
	
	/* 导入信息“保存”按钮触发 */
	form.on('submit(importDataSubmit)', function (data) {
	    importData();
	    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});
	
	//选择单位
	$("#orgNames").click(function() {
		top.openSelectOrg({
			title: '选择单位',
	        where:{state:1},
	        treeWhere:{state:1},
			area: ['1024px', '768px'],
			page: true,
			limit: 10,
			limits: [10, 50, 100, 1000, 10000],
			selectType: 'checkbox', //radio,checkbox
			checkedData: [], //默认选中的数据，只对selectType为radio起作用
			disabledData: [], //默认禁用的数据
			compareField: 'id' //用于识别选中或禁用的字段
		}, window.name, function(returnData, layerIndex) {
			var orgNameList = [];
			var orgIdList = [];
			for (var i = 0; i < returnData.length; i++) {
				orgNameList.push(returnData[i].orgName);
				orgIdList.push(returnData[i].orgId);
			}
			$("#orgNames").val(orgNameList);
			$("#orgIds").val(orgIdList);
			top.layer.close(layerIndex);
		});
	});
	
	/* 下载模板 */
	$("#downloadTemplate").click(function () {
	    window.location.href = servicePath + "/amPayroll/download?charset=utf-8&loginCode=" + adminInfo.loginCode;
	});
	
	function addAmPayrollList(dataList) {
		var layerIndex = layer.load();
		var url = servicePath + "/auxPay/add?charset=utf-8&loginCode=" + adminInfo.loginCode;
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataList),
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("保存成功", 1000);
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！", 2000, function() {
						parent.location.href = "index.html?v=1.60";
					});
				} else if (data.errCode == 200063) {
					showTicket("没有权限！", 2000);
				} else {
					showTicket(data.errMsg, 2000);
				}
				amPayrollTable.reload();
			}
		})
	}

	function del(id) {
		var layerIndex = layer.load();
		$.ajax({
			type: "GET",
			url: servicePath + "/amPayroll/delete?charset=utf-8&id=" + id + "&loginCode=" + adminInfo.loginCode,
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

	function search() {
		var formData = form.val("searchForm");
		formData.gzybId = gzybId;
		amPayrollTable.reload({
			where: formData
		});
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
	
	/* 导入信息 */
	function importData() {
	    var layerIndex = layer.load();
	    if ($("#file")[0].files[0] == undefined) {
	        layer.close(layerIndex);
	        showTicket("请先选择文件！", 1500);
	        return false;
	    }
	    var formdata = new FormData()
	    formdata.append('file', $("#file")[0].files[0]);
	    var isUpdate = $('input[name="isUpdate"]:checked').val();
	    $.ajax({
	        type: "POST",
	        url: servicePath + "/amPayroll/import?charset=utf-8&loginCode=" + adminInfo.loginCode + "&isUpdate=" + isUpdate+"&gzybId=" + gzybId,
	        processData: false, //告诉jQuery不要去处理发送的数据
	        contentType: false,// 告诉jQuery不要去设置Content-Type请求头
	        data: formdata,
	        success: function (data) {
	            layer.close(layerIndex);
	            if (data.errCode == 0) {
	                showTicket("执行完毕！", 1000, function () {
	                    layer.alert(data.msg)
	                    layer.closeAll('page');
	                    document.getElementById("importDataForm").reset();
	                    amPayrollTable.reload();
	                });
	            } else if (data.errCode == 200061 || data.errCode == 200062) {
	                showTicket("请先登录！", 2000, function () {
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
	
});
