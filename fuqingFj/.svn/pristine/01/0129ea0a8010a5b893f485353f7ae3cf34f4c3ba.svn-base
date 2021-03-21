
layui.use(['tree', 'util','element','form','table','layer'], function(){
  var $ = layui.jquery;
  var tree = layui.tree;
  var util = layui.util;
  var element = layui.element;
  var table = layui.table;
  var form = layui.form;
  var layer = layui.layer;
  
  var processTemplateTable=table.render({
    elem: '#processTemplateList'
    ,url:servicePath + "/processTemplate/getProcessTemplateList?charset=utf-8&loginCode="+adminInfo.loginCode
    ,method:'POST'
    ,contentType: "application/json; charset=utf-8"
    ,where: { "kwFields": 3, "keyword": "" }
	,limit:14
	,limits:[14,30,50,100]
    ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
    ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
      title: '提示'
      ,layEvent: 'LAYTABLE_TIPS'
      ,icon: 'layui-icon-tips'
    }]
    ,title: '流程列表'
    ,cols: [[
      {type: 'checkbox', fixed: 'left'}
      ,{field:'processName', title:'流程名称'}
      ,{field:'templateCode', title:'流程编码'}
      ,{field:'category', title:'流程类型',templet: function(d){
		   		return getDictDataLabel("PROCESS_TYPE",d.category);
		   }
	   }
      ,{field:'approvalImpl', title:'审批Bean名称'}
      ,{field:'createTime', title:'创建时间',templet: function(d){
		   		return new Date(d.createTime).format("yyyy-MM-dd hh:mm:ss");
		   }
	  }
      ,{fixed: 'right', title:'操作', toolbar: '#barDemo'}
    ]]
    ,page: true
	,parseData: function(res){ //res 即为原始返回的数据
		return res;
	 }
	 ,height:'full-105'
	 ,response: {
		statusName: 'errCode' //规定数据状态的字段名称，默认：code
		,statusCode: 0 //规定成功的状态码，默认：0
		,msgName: 'errMsg' //规定状态信息的字段名称，默认：msg
		,countName: 'recordCount' //规定数据总数的字段名称，默认：count
		,dataName: 'processTemplateList' //规定数据列表的字段名称，默认：data
	 } 
	 ,request: {
		pageName: 'page' //页码的参数名称，默认：page
		,limitName: 'pageSize' //每页数据量的参数名，默认：limit
	 }
  });
  
  //头工具栏事件
  table.on('toolbar(processTemplateList)', function(obj){
    var checkStatus = table.checkStatus(obj.config.id);
    switch(obj.event){
	  //添加流程
      case 'addProcessTemplate':
		  layer.open({
			  type: 1
			  ,title:'添加流程'
			  ,area:['600px','500px']
			  ,content: $('#addProcessTemplatePage')
			  ,btn: ['保存', '关闭']
			  ,yes:function(index, layero){
				  $("#addProcessTemplateSubmit").click();
			  }
			  ,btn2: function(index, layero){
				  layer.close(index);
			  }
		  });  
		  break;
      case 'getCheckLength':
        var data = checkStatus.data;
        layer.msg('选中了：'+ data.length + ' 个');
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
  table.on('tool(processTemplateList)', function(obj){
    var data = obj.data;
    //console.log(obj)
	//配置流程节点
    if(obj.event === 'addProcessNodeTemplate'){
		top.topTabAdd("流程节点配置",'<div class="layui-body"><iframe name="addProcessNodeTemplate.html?v=1.58&processTemplateId='+data.processTemplateId+'" src="addProcessNodeTemplate.html?v=1.58&processTemplateId='+data.processTemplateId+'" frameborder="0" class="layadmin-iframe"></iframe></div>','addProcessNodeTemplate.html?v=1.58&processTemplateId='+data.processTemplateId)
    } 
    if(obj.event === 'del'){
      if(data.processTemplateId==1){
		  showTicket("超级管理员流程不能删除！",2000);
		  return;
      }
      layer.confirm('确定要删除本流程吗？', function(index){
          obj.del();
      	  deleteProcessTemplate(data.processTemplateId);
          layer.close(index);
      });
    } 
	//修改流程
	if(obj.event === 'edit'){
	  form.val("editProcessTemplate",data);
	  layer.open({
		  type: 1
		  ,title:'修改流程'
		  ,area:['600px','500px']
		  ,content: $('#editProcessTemplatePage')
		  ,btn: ['保存', '关闭']
		  ,yes:function(index, layero){
			  $("#editProcessTemplateSubmit").click();
		  }
		  ,btn2: function(index, layero){
			layer.close(index);
		  }
	  });  
    }
  });  
  
  
  function showTicket(msg,seconds,func){
  	var s=2000;
  	if(!isNaN(seconds)){
  		s=seconds;
  	}
  	layer.msg(msg,{time:seconds});
  	setTimeout(function(){
  		if(func!=undefined){
  			func();
  		}
  	},s);
  }
  
  //添加流程
  function addProcessTemplate(data) {
  	var layerIndex=layer.load();
  	$.ajax({
  		type: "POST",
  		url: servicePath + "/processTemplate/addProcessTemplate?charset=utf-8&loginCode="+adminInfo.loginCode,
  		contentType: "application/json; charset=utf-8",
  		data: JSON.stringify(data),
  		dataType: "json",
  		success: function (data) {
			layer.close(layerIndex);
  			if (data.errCode == 0) {
  				showTicket("添加成功！",2000,function(){
					layer.closeAll('page');
					document.getElementById("addProcessTemplate").reset();
  					processTemplateTable.reload();					
  				});
  			} else if (data.errCode == 200061 || data.errCode == 200062) {
  				showTicket("请先登录！",2000,function(){
  					parent.location.href="index.html?v=1.58";
  				});
  			} else if (data.errCode == 200063) {
  				showTicket("没有权限！",2000);
  			} else {
  				showTicket(data.errMsg, 2000);
  			}
  		}
  	})
  }
  
  //修改流程
  function editProcessTemplate(data) {
  	layerIndex=layer.load();
  	$.ajax({
  		type: "POST",
  		url: servicePath + "/processTemplate/updateProcessTemplate?charset=utf-8&loginCode="+adminInfo.loginCode,
  		contentType: "application/json; charset=utf-8",
  		data: JSON.stringify(data),
  		dataType: "json",
  		success: function (data) {
			layer.close(layerIndex);
  			if (data.errCode == 0) {
				showTicket("修改成功！",2000,function(){
					layer.closeAll('page');
					processTemplateTable.reload();
				});
  			} else if (data.errCode == 200061 || data.errCode == 200062) {
  				showTicket("请先登录！",2000,function(){
  					parent.location.href="index.html?v=1.58";
  				});
  			} else if (data.errCode == 200063) {
  				showTicket("没有权限！",2000);
  			} else {
  				showTicket(data.errMsg, 2000);
  			}
  		}
  	})
  };  
  
  //添加流程“保存”按钮触发
  form.on('submit(addProcessTemplate)', function(data){
    addProcessTemplate(form.val("addProcessTemplate"));
    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
  });	
  
  //修改流程“保存”按钮触发
  form.on('submit(editProcessTemplate)', function(data){
    editProcessTemplate(form.val("editProcessTemplate"));
    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
  });	
  
  //搜索流程“查询”按钮触发
  form.on('submit(searchBtn)', function(data){
	search();
    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
  });
  
  $(".category").each(function () {
      addDictDataToSelect(this, "PROCESS_TYPE");
  })
  form.render("select");
});