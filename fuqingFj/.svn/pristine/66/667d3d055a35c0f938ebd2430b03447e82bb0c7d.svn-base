layui.use(['form', 'table', 'jquery', 'laydate'], function() {
	var table = layui.table;
	var form = layui.form;
	var $ = layui.jquery;
	var laydate = layui.laydate;
	//日期
	lay('.date-time').each(function() {
		laydate.render({
			elem: this,
			format: 'yyyyMMdd',
			trigger: 'click'
		});
	});
	var dictTable = table.render({
		elem: '#dictList',
		url: servicePath + "/dict/search?charset=utf-8&loginCode=" + adminInfo.loginCode,
		method: 'POST',
		contentType: "application/json; charset=utf-8",
		limit:10,
		limits:[10,50,100,1000,10000],
		toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
			,
		defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
			title: '提示',
			layEvent: 'LAYTABLE_TIPS',
			icon: 'layui-icon-tips'
		}],
		title: '字典管理表',
		cols: [
			[{
				type: 'numbers',
				fixed: 'center',
				title: '序号' 
			}, {
				field: 'dictName',
				title: '字典名称'
			}, {
				field: 'dictCode',
				title: '字典编码',
				sort: true
			}, {
				fixed: 'right',
				title: '操作',
				toolbar: '#barDemo',
				minWidth: "320"
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
			dataName: 'dictList' //规定数据列表的字段名称，默认：data
		},
		request: {
			pageName: 'page', //页码的参数名称，默认：page
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		}
	});

	//头工具栏事件
	table.on('toolbar(dictList)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch (obj.event) {
			case 'add':
				layer.open({
					type: 1,
					title: '添加字典类型',
					area: ['600px', '400px'],
					content: $('#addPage'),
					btn: ['保存', '取消'],
					yes: function(index, layero) {
						$("#addSubmit").click();
					},
					btn2: function(index, layero) {
						layer.close(index);
					}
				});
				break;
			case 'getCheckData':
				var data = checkStatus.data;
				layer.alert(JSON.stringify(data));
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
	table.on('tool(dictList)', function(obj) {
		var data = obj.data;
		if (obj.event === 'view') {
			alert(data.email);
		}
		if (obj.event === 'dictDataList') {
			top.topTabAdd("字典数据", '<div class="layui-body"><iframe name="dictDataList.html?v=1.60&dictId=' + data.dictId +'&dictCode='+data.dictCode+
				'" src="dictDataList.html?v=1.60&dictId=' + data.dictId +'&dictCode='+data.dictCode+
				'" frameborder="0" class="layadmin-iframe"></iframe></div>', 'dictDataList.html?v=1.60&dictId=' + data.dictId)
		}
		if (obj.event === 'del') {
			layer.confirm('确定删除该字典吗？', function(index) {
				obj.del();
				del(data.dictId);
				layer.close(index);
			});
		}
		//修改字典类型
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

	//添加字典类型
	function add(data) {
		var layerIndex = layer.load();
		$.ajax({
			type: "POST",
			url: servicePath + "/dict/add?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("添加成功！", 1000, function() {
						layer.closeAll('page');
						document.getElementById("add").reset();
						dictTable.reload();
					});
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
	//修改字典类型
	function edit(data) {
		var layerIndex = layer.load();
		$.ajax({
			type: "POST",
			url: servicePath + "/dict/update?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("修改成功！", 1000, function() {
						layer.closeAll('page');
						dictTable.reload();
					});
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


	//删除字典类型
	function del(dictId) {
		var layerIndex = layer.load();
		$.ajax({
			type: "GET",
			url: servicePath + "/dict/delete?charset=utf-8&dictId=" + dictId + "&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("删除成功", 1000);
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
	};
	
	function search(){
		  var formData=form.val("searchForm");
		  var data={
			  kwFields:3
			  ,keyword:formData.keyword
		  }
		  dictTable.reload({
			  where:data
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

	//添加字典类型“保存”按钮触发
	form.on('submit(add)', function(data) {
		add(form.val("add"));
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});

	//修改字典类型“保存”按钮触发
	form.on('submit(edit)', function(data) {
		edit(form.val("edit"));
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});
	
	//搜索字典类型“查询”按钮触发
	form.on('submit(search)', function(data){
	  search()
	  return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});

});
