layui.use(['form', 'table', 'jquery', 'upload'], function() {
	var table = layui.table;
	var form = layui.form;
	var $ = layui.jquery;
	var upload = layui.upload;

	var auxNoticeTable = table.render({
		elem: '#auxNoticeList',
		url: servicePath + "/auxNotice/search?charset=utf-8&loginCode=" + adminInfo.loginCode,
		method: 'POST',
		contentType: "application/json; charset=utf-8",
		where: {},
		limit: 10,
		limits: [10, 50, 100, 1000, 10000],
		toolbar: '#toolbarDemo', //开启头部工具栏，并为其绑定左侧模板
		defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
			title: '提示',
			layEvent: 'LAYTABLE_TIPS',
			icon: 'layui-icon-tips'
		}],
		title: '通知通告数据表',
		cols: [
			[{
				type: 'numbers',
				fixed: 'center',
				title: '序号'
			}, {
				field: 'noticeTitle',
				title: '通知标题',
				sort: true,
				width: 300
			}, {
				field: 'noticeText',
				title: '通知内容',
				sort: true,
				minwidth: 800
			}, {
				field: 'remark',
				title: '备注',
				sort: true,
				width: 200
			}, {
				field: 'createTime',
				title: '创建时间',
				sort: true,
				width: 160,
				templet: function(d){
					if(d.createTime){
						return layui.util.toDateString(d.createTime);
					}else {
						return '';
					}
				}
			}, {
				field: 'state',
				title: '状态',
				width: 100,
				fixed: 'right',
				align: 'center',
				sort: 'true',
				templet: '#switchTpl',
				unresize: true
			}, {
				fixed: 'right',
				title: '操作',
				toolbar: '#barDemo',
				width: 200
			}]
		],
		page: true,
		parseData: function(res) { //res 即为原始返回的数据
			return res;
		},
		height: 'full-51',
		response: {
			statusName: 'errCode', //规定数据状态的字段名称，默认：code
			statusCode: 0, //规定成功的状态码，默认：0
			msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
			countName: 'recordCount', //规定数据总数的字段名称，默认：count
			dataName: 'auxNoticeList' //规定数据列表的字段名称，默认：data
		},
		request: {
			pageName: 'page', //页码的参数名称，默认：page
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		}
	});

	//头工具栏事件
	table.on('toolbar(auxNoticeList)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch (obj.event) {
			case 'addBtn':
				document.getElementById("addForm").reset();
				layer.open({
					type: 1,
					title: '添加通知通告',
					area: ['700px', '600px'],
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
		}
	});

	//监听行工具事件
	table.on('tool(auxNoticeList)', function(obj) {
		var data = obj.data;
		if (obj.event === 'editBtn') {
			getAttachmentList("AuxNotice",data.noticeId,'editPage');
			form.val("editForm", data);
			layer.open({
				type: 1,
				title: '修改通知通告',
				area: ['700px', '600px'],
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
		if (obj.event === 'delBtn') {
			layer.confirm('确定删除通知通告吗？', function(index) {
				obj.del();
				del(data.noticeId);
				layer.close(index);
			});
		}
		if (obj.event === 'viewBtn') {
			getAttachmentList("AuxNotice",data.noticeId,'viewPage');
			form.val("viewForm", data);
			layer.open({
				type: 1,
				title: '查看通知通告',
				area: ['700px', '600px'],
				content: $('#viewPage'),
				btn: ['关闭'],
				yes: function(index, layero) {
					layer.close(index);
				}
			});
		}
	});

	//添加数据“保存”按钮触发
	form.on('submit(addSubmit)', function(data) {
		var formData = form.val("addForm");
		add(formData);
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});

	//修改数据“保存”按钮触发
	form.on('submit(editSubmit)', function(data) {
		var formData = form.val("editForm");
		edit(formData);
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});

	//搜索“查询”按钮触发
	form.on('submit(searchBtn)', function(data) {
		var formData = form.val("searchForm");
		var data = {
			kwFields: 7,
			keyword: formData.keyword,
			state: formData.state
		}
		search(data)
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});

	form.on('switch(stateSwitch)', function(obj) {
		var formData = {
			noticeId: this.value,
			state: obj.elem.checked == true ? 1 : 2
		}
		state(formData);
	});

	//搜索
	function search(data) {
		auxNoticeTable.reload({
			where: data
		});
	}

	function add(formData) {
		var layerIndex = layer.load();
		$.ajax({
			type: "POST",
			url: servicePath + "/auxNotice/add?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(formData),
			dataType: "json",
			success: function(res) {
				layer.close(layerIndex);
				if (res.errCode == 0) {
					if($("#add_uploadList").find("tr").length==0){
						showTicket("添加成功！", 2000, function() {
							layer.closeAll('page');
							document.getElementById("addForm").reset();
							auxNoticeTable.reload();
						});
					}else{
						addUpload.reload({
							url: servicePath + "/file/upload?charset=utf-8&instanceType=AuxNotice&instanceId="+res.auxNotice.noticeId+"&loginCode=" + adminInfo.loginCode
						});
						addUpload.upload();
					}
				} else if (res.errCode == 200061 || res.errCode == 200062) {
					showTicket("请先登录！", 2000, function() {
						parent.location.href = "index.html?v=1.60";
					});
				} else if (res.errCode == 200063) {
					showTicket("没有权限！", 2000);
				} else {
					showTicket(res.errMsg, 2000);
				}

			}
		})
	}

	function edit(formData) {
		var layerIndex = layer.load();
		$.ajax({
			type: "POST",
			url: servicePath + "/auxNotice/update?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(formData),
			dataType: "json",
			success: function(res) {
				layer.close(layerIndex);
				if (res.errCode == 0) {
					if($("#edit_uploadList").find("tr[id]").length==0){
						showTicket("修改成功！", 2000, function() {
							layer.closeAll('page');
							auxNoticeTable.reload();
						});
					} else {
						editUpload.reload({
							url: servicePath + "/file/upload?charset=utf-8&instanceType=AuxNotice&instanceId="+res.auxNotice.noticeId+"&loginCode=" + adminInfo.loginCode
						});
						editUpload.upload();	
					}
				} else if (res.errCode == 200061 || res.errCode == 200062) {
					showTicket("请先登录！", 2000, function() {
						parent.location.href = "index.html?v=1.60";
					});
				} else if (res.errCode == 200063) {
					showTicket("没有权限！", 2000);
				} else {
					showTicket(res.errMsg, 2000);
				}
			}
		})
	}

	function del(noticeId) {
		var layerIndex = layer.load();
		$.ajax({
			type: "GET",
			url: servicePath + "/auxNotice/delete?charset=utf-8&loginCode=" + adminInfo.loginCode + "&noticeId=" +
				noticeId,
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

	/**
	 * @param {AuxNotice} formData
	 */
	function state(formData) {
		var layerIndex = layer.load();
		$.ajax({
			type: "POST",
			url: servicePath + "/auxNotice/state?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(formData),
			dataType: "json",
			success: function(res) {
				layer.close(layerIndex);
				if (res.errCode == 0) {
					showTicket("修改成功！", 1000, function() {
						layer.closeAll('page');
						auxNoticeTable.reload();
					});
				} else if (res.errCode == 200061 || res.errCode == 200062) {
					showTicket("请先登录！", 2000, function() {
						parent.location.href = "index.html?v=1.60";
					});
				} else if (res.errCode == 200063) {
					showTicket("没有权限！", 2000);
				} else {
					showTicket(res.errMsg, 2000);
				}
			}
		})
	}
	
	//删除附件
	$("#edit_uploadList").delegate('.deleteFile','click', function(){
	    delAttachment($(this).attr("attachmentId"));
		$(this).parents("tr").remove();
	});
	
	//删除附件
	$("#add_uploadList").delegate('.deleteFile','click', function(){
	    delAttachment($(this).attr("attachmentId"));
		$(this).parents("tr").remove();
	});
	
	var addUpload=fileUpload($("#add_uploadList"),'#add_selectFileBtn',"AuxNotice",1);
	
	var editUpload=fileUpload($("#edit_uploadList"),'#edit_selectFileBtn',"AuxNotice",1);
	
	//container：容器的JQ对象
	//instanceType：主体类型
	//instanceId：主体ID
	function fileUpload(container,elem,instanceType,instanceId){
		//多文件列表上传
		var uploadListIns = upload.render({
			elem: elem
			,url: servicePath + "/file/upload?charset=utf-8&instanceType="+instanceType+"&instanceId="+instanceId+"&loginCode=" + adminInfo.loginCode
			,accept: 'file'
			,multiple: true
			,auto: false
			//,bindAction: '#edit_uploadAction'
			,choose: function(obj){   
			  var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
			  //读取本地文件
			  obj.preview(function(index, file, result){
				var tr = $(['<tr id="upload-'+ index +'">'
				  ,'<td>'+ file.name +'</td>'
				  ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
				  ,'<td>等待上传</td>'
				  ,'<td>'
					,'<button class="layui-btn layui-btn-xs reupload layui-hide">重传</button>'
					,'<button class="layui-btn layui-btn-xs layui-btn-danger deleteTr">删除</button>'
				  ,'</td>'
				,'</tr>'].join(''));
				
				//单个重传
				tr.find('.reupload').on('click', function(){
				  obj.upload(index, file);
				});
				
				//删除
				tr.find('.deleteTr').on('click', function(){
				  delete files[index]; //删除对应的文件
				  tr.remove();
				  uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
				});
				container.append(tr);
			  });
			}
			,done: function(res, index, upload){
			  if(res.errCode == 0){ //上传成功
				var tr = container.find('tr#upload-'+ index)
				,tds = tr.children();
				tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
				tds.eq(3).html('<button class="layui-btn layui-btn-xs viewFile layui-hide" attachmentId="'+res.attachmentList[0].id+'">查看</button><button class="layui-btn layui-btn-xs layui-btn-danger deleteTr" attachmentId="'+res.attachmentList[0].id+'">删除</button>'); //操作变成查看
				return delete this.files[index]; //删除文件队列已经上传成功的文件
			  }
			  this.error(index, upload);
			}
			,error: function(index, upload){
			  var tr = container.find('tr#upload-'+ index)
			  ,tds = tr.children();
			  tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
			  tds.eq(3).find('.reupload').removeClass('layui-hide'); //显示重传
			}
			,allDone: function(obj){ //当文件全部被提交后，才触发
			    showTicket("保存成功！", 1000, function() {
			    	layer.closeAll('page');
			    	document.getElementById("addForm").reset();
			    	auxNoticeTable.reload();
			    });
			}			
		});
		return uploadListIns;
	}
	
	//获取附件列表
	function getAttachmentList(instanceType,instanceId,typePage) {
		var layerIndex = layer.load();
		$.ajax({
			type: "GET",
			url: servicePath + "/file/getAttachmentList?charset=utf-8&instanceType="+instanceType+"&instanceId="+instanceId+"&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					if(typePage=='editPage'){
						$("#edit_uploadList").html("");
						var attachmentList=data.attachmentList;
						for(var i=0;i<attachmentList.length;i++){						
							var tr = $(['<tr>'
							  ,'<td><a href="'+servicePath+'/'+attachmentList[i].filePath+'" target="_blank">'+ attachmentList[i].fileName +'</a></td>'
							  ,'<td>'+ (attachmentList[i].fileSize/1014).toFixed(1) +'kb</td>'
							  ,'<td>已存在</td>'
							  ,'<td>'
								,'<button class="layui-btn layui-btn-xs viewFile layui-hide" attachmentId="'+attachmentList[i].id+'">查看</button><button class="layui-btn layui-btn-xs layui-btn-danger deleteFile" attachmentId="'+attachmentList[i].id+'">删除</button>'
							  ,'</td>'
							,'</tr>'].join(''));
							$("#edit_uploadList").append(tr);
						}
					} else if(typePage=='viewPage') {
						$("#view_uploadList").html("");
						var attachmentList=data.attachmentList;
						for(var i=0;i<attachmentList.length;i++){						
							var tr = $(['<tr>'
							  ,'<td><a href="'+servicePath+'/'+attachmentList[i].filePath+'" target="_blank">'+ attachmentList[i].fileName +'</a></td>'
							  ,'<td>'+ (attachmentList[i].fileSize/1014).toFixed(1) +'kb</td>'
							  ,'<td>已存在</td>'
							  ,'<td>'
								,'<button class="layui-btn layui-btn-xs viewFile layui-hide" attachmentId="'+attachmentList[i].id+'">查看</button>'
							  ,'</td>'
							,'</tr>'].join(''));
							$("#view_uploadList").append(tr);
						}
					}
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！",2000,function(){
						parent.location.href="../index.html?v=1.60";
					});
				} else {
					showTicket(data.errMsg, 2000);
				}
			}
		})
	};
	
	//删除附件
	function delAttachment(attachmentId) {
		var layerIndex = layer.load();
		$.ajax({
			type: "GET",
			url: servicePath + "/file/delAttachment?charset=utf-8&attachmentId=" + attachmentId + "&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("删除成功", 1000);
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！",2000,function(){
						parent.location.href="../index.html?v=1.60";
					});
				} else if (data.errCode == 200063) {
					showTicket("没有权限！",2000);
				} else {
					showTicket(data.errMsg, 2000);
				}
			}
		})
	};
	
	

});
