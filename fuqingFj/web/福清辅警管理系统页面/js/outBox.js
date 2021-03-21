

layui.use(['tree', 'util','element','form','table','layer', 'upload'], function(){
  var $ = layui.jquery;
  var tree = layui.tree;
  var util = layui.util;
  var element = layui.element;
  var table = layui.table;
  var form = layui.form;
  var layer = layui.layer;
  var upload = layui.upload;
  var receiveList=[];
  var receiveIdList=[];
  var msg="";
  
  var innerMessageTable=table.render({
    elem: '#innerMessageList'
    ,url:servicePath + "/innerMessage/search?charset=utf-8&loginCode="+adminInfo.loginCode
    ,method:'POST'
    ,contentType: "application/json; charset=utf-8"
    ,where: { "kwFields": 11, "keyword": ""}
	,limit:10
	,limits:[10,50,100,1000,10000]
    ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
    ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
      title: '提示'
      ,layEvent: 'LAYTABLE_TIPS'
      ,icon: 'layui-icon-tips'
    }]
    ,title: '消息列表'
    ,cols: [[
      {type: 'checkbox', fixed: 'left'}
      ,{field:'title', title:'标题'}
      ,{field:'msgType', title:'消息类型',templet: function(d){
				return getDictDataLabel("MSG_TYPE",d.msgType);
		   }}
      ,{field:'content', title:'消息内容'}
      ,{field:'remark', title:'备注'}
      ,{field:'msgState', title:'状态',templet: function(d){
				return getDictDataLabel("MSG_STATE",d.msgState);
		   }}
      ,{fixed: 'right', title:'操作', toolbar: '#barDemo'}
    ]]
    ,page: true
	,parseData: function(res){ //res 即为原始返回的数据
		return res;
	 }
	 //,height:'full-105'
	 ,response: {
		statusName: 'errCode' //规定数据状态的字段名称，默认：code
		,statusCode: 0 //规定成功的状态码，默认：0
		,msgName: 'errMsg' //规定状态信息的字段名称，默认：msg
		,countName: 'recordCount' //规定数据总数的字段名称，默认：count
		,dataName: 'innerMessageList' //规定数据列表的字段名称，默认：data
	 } 
	 ,request: {
		pageName: 'page' //页码的参数名称，默认：page
		,limitName: 'pageSize' //每页数据量的参数名，默认：limit
	 }
  });
  
  //头工具栏事件
  table.on('toolbar(innerMessageList)', function(obj){
    var checkStatus = table.checkStatus(obj.config.id);
    switch(obj.event){
	  //添加消息
      case 'addMsg':
		  document.getElementById("addMsg").reset();
		  $(".btn_cleanUser").click();
		  layer.open({
			  type: 1
			  ,title:'添加消息'
			  ,area:['600px','500px']
			  ,content: $('#addMsgPage')
			  ,btn: ['保存为草稿','发送', '关闭']
			  ,yes:function(index, layero){
				  $(".msgState").val("1");
				  $("#addMsgSubmit").click();
			  }
			  ,btn2: function(index, layero){
				  $(".msgState").val("2");
				  $("#addMsgSubmit").click();
			  }
			  ,btn3: function(index, layero){
				layer.close(index);
			  }
		  });  
		  break;
	  //返回上级消息
      case 'goToParent':
		getMsg(parentObj.parentId);
      break;
      case 'isAll':
        layer.msg(checkStatus.isAll ? '全选': '未全选');
      break;
      
      //自定义头工具栏右侧图标 - 提示
      case 'LAYTABLE_TIPS':
        layer.alert('这是工具栏右侧自定义的一个图标按钮');
      break;
    };
  });
  
  //监听行工具事件
  table.on('tool(innerMessageList)', function(obj){
    var data = obj.data;
    //console.log(obj)
    if(obj.event === 'send'){
      layer.confirm('确定要发送消息吗？', function(index){
        sendMsg(data.innerMessageId);
        layer.close(index);
      });
    } 
    if(obj.event === 'del'){
      layer.confirm('确定要删除消息吗？', function(index){
        obj.del();
        deleteMsg(data.innerMessageId);
        layer.close(index);
      });
    } 
	//修改消息
	if(obj.event === 'edit'){
	  getAttachmentList(data.innerMessageId);
	  form.val("editMsg",data);
	  receiveList=data.receiveList;
	  // console.log(data);
	  var names="";
	  for(var i=0;i<receiveList.length;i++){
	  	names+="，" + receiveList[i].recipientName;
		receiveIdList.push(receiveList[i].recipient);
	  }
	  names=names.substr(1);
	  $(".recipientNames").val(names);
	  layer.open({
		  type: 1
		  ,title:'修改消息'
		  ,area:['600px','500px']
		  ,content: $('#editMsgPage')
		  ,btn: ['保存为草稿','发送', '关闭']
		  ,yes:function(index, layero){
			  $(".msgState").val("1");
			  $("#editMsgSubmit").click();
		  }
		  ,btn2: function(index, layero){
			  $(".msgState").val("2");
			  $("#editMsgSubmit").click();
		  }
		  ,btn3: function(index, layero){
			layer.close(index);
		  }
	  });  
    }
  });
  
  
  
  //添加消息
  function addMsg(data) {
  	var layerIndex=layer.load();
  	$.ajax({
  		type: "POST",
  		url: servicePath + "/innerMessage/add?charset=utf-8&loginCode="+adminInfo.loginCode,
  		contentType: "application/json; charset=utf-8",
  		data: JSON.stringify(data),
  		dataType: "json",
  		success: function (data) {
  			layer.close(layerIndex);
  			if (data.errCode == 0) {
				msg="";
				if(data.innerMessage.msgState==1){
					msg="保存成功！";
				}
				if(data.innerMessage.msgState==2){
					msg="发送成功！";
				}
				if($("#add_uploadList").find("tr").length==0){
					showTicket(msg,2000,function(){
						document.getElementById("addMsg").reset();
						$(".btn_cleanUser").click();
						innerMessageTable.reload();				
						layer.closeAll('page');	
					});
				}else{
					addUpload.reload({
						url: servicePath + "/file/upload?charset=utf-8&instanceType=AmInnerMessage&instanceId="+data.innerMessage.innerMessageId+"&loginCode=" + adminInfo.loginCode
					});
					addUpload.upload();						
				}
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
  
  //修改消息
  function editMsg(data) {
	var updateData=data;
  	layerIndex=layer.load();
  	$.ajax({
  		type: "POST",
  		url: servicePath + "/innerMessage/update?charset=utf-8&loginCode="+adminInfo.loginCode,
  		contentType: "application/json; charset=utf-8",
  		data: JSON.stringify(data),
  		dataType: "json",
  		success: function (data) {
			layer.close(layerIndex);
  			if (data.errCode == 0) {
				msg="";
				if(updateData.msgState==1){
					msg="保存成功！";
				}
				if(updateData.msgState==2){
					msg="发送成功！";
				}
				if($("#edit_uploadList").find("tr[id]").length==0){
					showTicket(msg,2000,function(){
						$(".btn_cleanUser").click();
						innerMessageTable.reload();
						layer.closeAll('page');
					});
				}else{
					editUpload.reload({
						url: servicePath + "/file/upload?charset=utf-8&instanceType=AmInnerMessage&instanceId="+updateData.innerMessageId+"&loginCode=" + adminInfo.loginCode
					});
					editUpload.upload();				
				}
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
  
  
  //删除消息
  function deleteMsg(innerMessageId) {
  	var layerIndex=layer.load();
  	$.ajax({
  		type: "GET",
  		url: servicePath + "/innerMessage/delete?charset=utf-8&innerMessageId="+innerMessageId+"&loginCode="+adminInfo.loginCode,
  		contentType: "application/json; charset=utf-8",
  		dataType: "json",
  		success: function (data) {
  			layer.close(layerIndex);
  			if (data.errCode == 0) {
  				showTicket("删除成功",1000);
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
  
  
  //发送消息
  function sendMsg(innerMessageId) {
  	var layerIndex=layer.load();
  	$.ajax({
  		type: "GET",
  		url: servicePath + "/innerMessage/send?charset=utf-8&innerMessageId="+innerMessageId+"&loginCode="+adminInfo.loginCode,
  		contentType: "application/json; charset=utf-8",
  		dataType: "json",
  		success: function (data) {
  			layer.close(layerIndex);
  			if (data.errCode == 0) {
  				showTicket("发送成功",1000);
				innerMessageTable.reload();	
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
  
  //获取消息
  function getMsg(innerMessageId) {
  	var layerIndex=layer.load();
  	$.ajax({
  		type: "GET",
  		url: servicePath + "/innerMessage/getMsg?charset=utf-8&innerMessageId="+innerMessageId+"&loginCode="+adminInfo.loginCode,
  		contentType: "application/json; charset=utf-8",
  		dataType: "json",
  		success: function (data) {
  			layer.close(layerIndex);
  			if (data.errCode == 0) {
  				location.href="innerMessageList.html?v=1.60&parentId="+data.innerMessage.parentId+"&parentName="+escape(data.innerMessage.parentName);
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
  
  
  $(".msgType").each(function(){
  	  addDictDataToSelect(this,"MSG_TYPE",[1,2,4]);
  })  
  form.render("select");
  
  //搜索消息
  function search(){
	  var formData=form.val("searchForm");
	  var data={
		  kwFields:7
		  ,keyword:formData.keyword
	  }
	  innerMessageTable.reload({
		  where:data
	  });
  }
  
  $(".recipientNames").click(function(){
		top.openSelectRy({
				url:'selectFjry.html',
				title:'选择收件人',
				resource:'/user/selectAdmin',
				where:{state:1},
				treeWhere:{state:15,kwFields:3,keyword:""},
				area:['1024px','768px'],
				page:true,
				limit:10,
				limits:[10,50,100,1000,10000],
				selectType:'checkbox',//radio,checkbox
				checkedData:[],//默认选中的数据，只对selectType为checkbox起作用
				disabledData:receiveIdList,//默认禁用的数据
				compareField:'userId',//用于识别选中或禁用的字段
				isRequire: true
			},window.name,function(returnData,layerIndex){
				var names=$(".recipientNames").val();				
				if(names=="全部"){
					$(".recipientNames").val("");
					names="";
					receiveList=[];
					receiveIdList=[];
				}
				for(var i=0;i<returnData.length;i++){
					names+="，" + returnData[i].xm;
					var receive={};
					receive.receiveType=1;
					receive.recipient=returnData[i].userId;
					receive.recipientName=returnData[i].xm;
					receiveList.push(receive);
					receiveIdList.push(returnData[i].userId);
				}
				if($(".recipientNames").val()==""){
					names=names.substr(1);
				}
				$(".recipientNames").val(names);
				top.layer.close(layerIndex);
		});
  })
  
  $("#allRy").click(function(){
	  $(".recipientNames").val("全部");
	  receiveList=[];
	  var receive={};
	  receive.receiveType=1;
	  receive.recipient=0;
	  receive.recipientName="全部";
	  receiveList.push(receive);
	  receiveIdList=[];	  
	  receiveIdList.push(0);
  })
  
  $(".btn_cleanUser").click(function(){
	  $(".recipientNames").val("");
	  receiveList=[];
	  receiveIdList=[];
  });
  
  //添加消息“保存”按钮触发
  form.on('submit(addMsg)', function(data){
	  var formData=form.val("addMsg");
	  formData.receiveList=receiveList;
      addMsg(formData);
      return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
  });		
  
  //修改消息“保存”按钮触发
  form.on('submit(editMsg)', function(data){
	  var formData=form.val("editMsg");
	  // console.log(receiveList);
	  formData.receiveList=receiveList;
      editMsg(formData);
      return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
  });	
  
  //搜索消息“查询”按钮触发
  form.on('submit(searchBtn)', function(data){
	search();
    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
  });
  
  
  
	//container：容器的JQ对象
	//instanceType：主体类型
	//instanceId：主体ID
	function fileUpload(container,elem,instanceType,instanceId,msgState){
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
				showTicket(msg,2000,function(){					
					$(".btn_cleanUser").click();
					document.getElementById("addMsg").reset();
					innerMessageTable.reload();	
					layer.closeAll('page');
				});
			}			
		});
		return uploadListIns;
	}
	
	var addUpload=fileUpload($("#add_uploadList"),'#add_selectFileBtn',"AmInnerMessage",1);
	
	var editUpload=fileUpload($("#edit_uploadList"),'#edit_selectFileBtn',"AmInnerMessage",1);
	
	
	
	//获取附件列表
	function getAttachmentList(instanceId) {
		var layerIndex = layer.load();
		$.ajax({
			type: "GET",
			url: servicePath + "/file/getAttachmentList?charset=utf-8&instanceType=AmInnerMessage&instanceId="+instanceId+"&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
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
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！",2000,function(){
						parent.location.href="index.html?v=1.60";
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
});