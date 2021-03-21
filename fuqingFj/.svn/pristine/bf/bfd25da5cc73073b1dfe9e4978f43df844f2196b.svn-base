var processTemplateId=getParameter("processTemplateId");
layui.use(['tree', 'util','form','table','layer','upload','element'], function(){
  var $ = layui.jquery;
  var table = layui.table;
  var form = layui.form;
  var tree = layui.tree;
  var util = layui.util;
  var upload = layui.upload;
  var element = layui.element;
  
  var approvalUserIds=[];
  
  var nodeOption="add";
  var branchOption="add";
  
  var branchNodeTable=table.render({
    elem: '#procseeNodeBranchTemplateList'
    ,url:servicePath + "/processTemplate/getProcessNodeBranchTemplateList?charset=utf-8&loginCode="+adminInfo.loginCode
  	,method:'POST'
  	,contentType: "application/json; charset=utf-8"
  	//,where: JSON.stringify({"processNodeTemplateId": processNodeTemplateId})
    //,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
    ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
      title: '提示'
      ,layEvent: 'LAYTABLE_TIPS'
      ,icon: 'layui-icon-tips'
    }]
    ,title: '流程节点分支模板数据表'
    ,cols: [[
      {type: 'numbers', fixed: 'left',width:'4%',title: '序号'}
      ,{field:'cond', title:'条件',width:'70%',templet: function(d){
				var cond=JSON.parse(d.cond);
				var condStr="";
				if(cond.orgId=="any"){
					condStr+=" 且 所属单位为：任意单位 ";
				}else{
					condStr+=" 且 所属单位为："+getOrgName(cond.orgId)+" ";
				}
				
				if(cond.roleId=="any"){
					condStr+=" 且 申请人角色为：任意角色 ";
				}else{
					condStr+=" 且 申请人角色为："+roleObj[cond.roleId]+" ";
				}
				/* if(cond.upleaderInAfter=="true"){
								condStr+=" 且 分管领导为政委或局长<br>";
				}
				if(cond.upleaderInAfter=="false"){
								condStr+=" 且 分管领导不为政委或局长<br>";
				} */			  
				if(cond.position!=undefined){
					var position=cond.position;
					position=position.replace(/，/g," 或 ");
					position=position.replace(/,/g," 或 ");
					condStr+=" 且 在 " + (cond.org=='any'?' 任意单位 ':getOrgName(cond.org)) + " 任 " + position;
								
				}
				if(condStr==""){
					condStr="无";
				}		
				return condStr;
  		   }
  	   }
      ,{field:'nextNodeCode', title:'下个节点',width:'20%',templet: function(d){
  		   		return processNodeTemplateObj[d.nextNodeCode];
  		   }
  	   }
      ,{fixed: 'right', title:'操作',width:'114px', toolbar: '#barDemo'}
    ]]
    ,page: false
  	,parseData: function(res){ //res 即为原始返回的数据
  		return res;
  	 }
  	 ,height:'full-159'
  	 ,response: {
  		statusName: 'errCode' //规定数据状态的字段名称，默认：code
  		,statusCode: 0 //规定成功的状态码，默认：0
  		,msgName: 'errMsg' //规定状态信息的字段名称，默认：msg
  		,countName: 'recordCount' //规定数据总数的字段名称，默认：count
  		,dataName: 'processNodeBranchTemplateList' //规定数据列表的字段名称，默认：data
  	 }
  });
  
  //头工具栏事件
  table.on('toolbar(procseeNodeBranchTemplateList)', function(obj){
    var checkStatus = table.checkStatus(obj.config.id);
    switch(obj.event){
      case 'addUnit':
  		  layer.open({
  			  type: 1
  			  ,title:'添加单位'
  			  ,area:['600px','500px']
  			  ,content: $('#addUnitPage')
  			  ,btn: ['保存', '关闭']
  			  ,yes:function(index, layero){
  				  $("#addUnitSubmit").click();
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
  table.on('tool(procseeNodeBranchTemplateList)', function(obj){
    var data = obj.data;
    //console.log(obj)
    if(obj.event === 'del'){
      layer.confirm('确定要删除吗？', function(index){		  
        obj.del();
        deleteProcseeNodeBranchTemplate(data.branchId);
        layer.close(index);
      });
    } 
  	//修改节点分支
  	if(obj.event === 'edit'){
		branchOption="edit";
		document.getElementById("nodeBranchForm").reset();
		$("#processNodeTemplateId").find("option").remove();
		seladditem(nodeId,nodeName,document.getElementById("processNodeTemplateId"));
		var formData={};
		formData.branchId=data.branchId;
		formData.nextNode=data.nextNodeCode;
		formData.sort=data.sort;
		var cond=JSON.parse(data.cond);
		formData.orgId=cond.orgId;
		formData.condRole=cond.roleId;
		formData.condOrg=cond.org;
		formData.condPosition=cond.position;
  	  form.val("nodeBranchForm",formData);
	  
  	  layer.open({
  		  type: 1
  		  ,title:'修改节点分支'
  		  ,area:['600px','500px']
  		  ,content: $('#addNodeBranch')
  		  ,btn: ['保存', '关闭']
  		  ,yes:function(index, layero){
  			  $("#addNodeBranchSubmit").click();
  		  }
  		  ,btn2: function(index, layero){
  			layer.close(index);
  		  }
  	  });  
    }
  });
  
  
	var page=1,pageSize=10,layerIndex=null;
	var processTemplateId=getParameter("processTemplateId");
	var processNodeTemplateId;
	var form;
	var leaveTypeObj={};
	var orgCodeObj={};
	orgCodeObj.any="任意单位";
	var roleObj={};
	var processNodeTemplateObj={};
	layui.use('form', function(){
	  form = layui.form;
	});

	function addNode(){
		nodeOption="add";
	  initSelectZpr();//回复初始状态
	  layer.open({
		  type: 1
		  ,title:'添加审批节点'
		  ,area:['620px','540px']
		  ,content: $('#addNode')
		  ,btn: ['保存', '关闭']
		  ,yes:function(index, layero){
			  $("#addNodeSubmit").click();
		  }
		  ,btn2: function(index, layero){
			layer.close(index);
		  }
	  });  
	}
	
	function editNode(processNodeId){
		nodeOption="edit";
	  initSelectZpr();//回复初始状态
	  var processNodeTemplate=processNodeTemplateObj[processNodeId];
		var approvalRoleId=JSON.parse(processNodeTemplate.approvalRoleId);
		var afterParameter={};
		if(processNodeTemplate.afterParameter!=""){
			afterParameter=JSON.parse(processNodeTemplate.afterParameter);
		}
		var formData={};
		formData.processNodeId=processNodeTemplate.processNodeId;
		formData.processNodeName=processNodeTemplate.nodeName;
		formData.roleType=approvalRoleId.approvalRoleType;
		formData.approvalRoleId=approvalRoleId.approvalRoleId;
		formData.role=approvalRoleId.approvalRoleId;
		formData.relation=approvalRoleId.relation;
		formData.bindField=afterParameter.bindField;
		formData.processNodeType=processNodeTemplate.approvalType;
		if(processNodeTemplate.passRequire==2){
			formData.approvalType=1;
		}else{
			formData.approvalType=2;
		}
		formData.org=approvalRoleId.org;
		formData.position=approvalRoleId.position;
		formData.sort=processNodeTemplate.sort;
		var beforeParameter = JSON.parse(processNodeTemplate.beforeParameter);
		if(beforeParameter){
			formData.bindProgress=JSON.parse(processNodeTemplate.beforeParameter).amGzybProgress;
		}
		formData.nodeEventImpl = processNodeTemplate.nodeEventImpl;
		
		form.val("nodeForm",formData);
		if(formData.roleType=="users"){
			$("#approvalNames").html(getNamesByUserIds(approvalRoleId.approvalUserIds));
		}
		element.tabChange("roleTypeTab", formData.roleType);
	  layer.open({
		  type: 1
		  ,title:'修改审批节点'
		  ,area:['620px','540px']
		  ,content: $('#addNode')
		  ,btn: ['保存', '关闭']
		  ,yes:function(index, layero){
			  $("#addNodeSubmit").click();
		  }
		  ,btn2: function(index, layero){
			layer.close(index);
		  }
	  });  
	}
	
	//主评人选择框回复初始状态
	function initSelectZpr(){
		document.getElementById("nodeForm").reset();
		$("#approvalNames").html("");
		element.tabChange("roleTypeTab", "self");
	}
	
	function addNodeBranch(nodeId,nodeName){
		document.getElementById("nodeBranchForm").reset();
		$("#processNodeTemplateId").find("option").remove();
		seladditem(nodeId,nodeName,document.getElementById("processNodeTemplateId"));
		form.render('select');
	  layer.open({
		  type: 1
		  ,title:'添加节点分支条件'
		  ,area:['620px','540px']
		  ,content: $('#addNodeBranch')
		  ,btn: ['保存', '关闭']
		  ,yes:function(index, layero){
			  $("#addNodeBranchSubmit").click();
		  }
		  ,btn2: function(index, layero){
			layer.close(index);
		  }
	  });
	}

	//获取角色列表
	function getRoleList() {
		var url = servicePath + "/role/search?charset=utf-8&loginCode="+adminInfo.loginCode;
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify({ "page": 1, "pageSize": 1000,"roleType":1}),
			dataType: "json",
			success: function (data) {
				var html = '';
				if (data.errCode == 0) {
					var roleList = data.roleList;
					for (var i = 0; i < roleList.length; i++) {
						$(".roleList").append('<input type="radio" name="role" lay-skin="primary" title="'+roleList[i].roleName+'" value="'+roleList[i].roleId+'">');
						$(".condRoleList").append('<input type="radio" name="condRole" lay-skin="primary" title="'+roleList[i].roleName+'" value="'+roleList[i].roleId+'">');
						roleObj[roleList[i].roleId]=roleList[i].roleName;
						form.render();
					}
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！",2000,function(){
						parent.location.href="index.html?v=1.56";
					});
				}
			}
		})
	};

	//获取节点模板列表
	function getProcseeNodeTemplateList() {
		var url = servicePath + "/processTemplate/getProcessNodeTemplateList?charset=utf-8&loginCode="+adminInfo.loginCode+"&processTemplateId="+processTemplateId;
		$.ajax({
			type: "GET",
			url: url,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function (data) {
				var html = '';
				if (data.errCode == 0) {
					var processNodeTemplateList = data.processNodeTemplateList;
					for (var i = 0; i < processNodeTemplateList.length; i++) {
						//$("#tdAdd").before('<td><button type="button" class="layui-btn layui-btn-normal layui-btn-radius addNodeBranch" value="'+processNodeTemplateList[i].processNodeId+'">'+processNodeTemplateList[i].nodeName+'</button></td>');
						if(i==0 && processNodeTemplateList.length>1){
							$("#tdAdd").before('<span style="display: inline-block;position:sticky;"><button type="button" class="layui-btn layui-btn-normal layui-btn-radius addNodeBranch" value="'+processNodeTemplateList[i].processNodeId+'">'+processNodeTemplateList[i].nodeName+'</button><i value="'+processNodeTemplateList[i].processNodeId+'" class="layui-icon editNode" style="right:35px">&#xe642;</i></span>');
						}else{
							$("#tdAdd").before('<span style="display: inline-block;position:sticky;"><button type="button" class="layui-btn layui-btn-normal layui-btn-radius addNodeBranch" value="'+processNodeTemplateList[i].processNodeId+'">'+processNodeTemplateList[i].nodeName+'</button><i value="'+processNodeTemplateList[i].processNodeId+'" class="layui-icon editNode" style="right:35px">&#xe642;</i><i value="'+processNodeTemplateList[i].processNodeId+'" class="layui-icon delNode" style="right:15px">ဆ</i></span>');
						}
						seladditem(processNodeTemplateList[i].nodeCode,processNodeTemplateList[i].nodeName,document.getElementById("nextNode"));
						processNodeTemplateObj[processNodeTemplateList[i].nodeCode]=processNodeTemplateList[i].nodeName;
						processNodeTemplateObj[processNodeTemplateList[i].processNodeId]=processNodeTemplateList[i];
						processNodeTemplateObj.end="结束";
						form.render();
					}
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！",2000,function(){
						parent.location.href="index.html?v=1.56";
					});
				}
			}
		})
	};
	
	//删除节点分支模板
	function deleteProcseeNodeBranchTemplate(processNodeBranchTemplateId) {
		var url = servicePath + "/processTemplate/deleteProcessNodeBranchTemplate?charset=utf-8&loginCode="+adminInfo.loginCode+"&processNodeBranchTemplateId="+processNodeBranchTemplateId;
		$.ajax({
			type: "GET",
			url: url,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function (data) {
				if (data.errCode == 0) {
					showTicket("删除成功！",2000,function(){
						branchNodeTable.reload();
					});					
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！",2000,function(){
						parent.location.href="index.html?v=1.56";
					});
				} else if (data.errCode == 200063) {
					showTicket("没有权限！",2000);
				} else {
					showTicket(data.errMsg, 2000);
				}
				
			}
		})
	};

	//删除节点模板
	function deleteProcseeNodeTemplate(processNodeTemplateId) {
		var url = servicePath + "/processTemplate/deleteProcessNodeTemplate?charset=utf-8&loginCode="+adminInfo.loginCode+"&processNodeTemplateId="+processNodeTemplateId;
		$.ajax({
			type: "GET",
			url: url,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function (data) {
				if (data.errCode == 0) {
					showTicket("删除成功！",2000,function(){
						location.reload();
					});					
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！",2000,function(){
						parent.location.href="index.html?v=1.56";
					});
				} else if (data.errCode == 200063) {
					showTicket("没有权限！",2000);
				} else {
					showTicket(data.errMsg, 2000);
				}
				
			}
		})
	};

	//添加流程节点模板
	function addProcessNodeTemplate() {
		var formData=form.val("nodeForm");
		layerIndex=layer.load();
		var url = servicePath + "/processTemplate/addProcessNodeTemplate?charset=utf-8&loginCode="+adminInfo.loginCode;
		var approvalRoleId=$("input:radio[name='role']:checked").val();
		var approvalRoleType=$("input:radio[name='roleType']:checked").val();
		var relation=$("#relation").val();
		var approvalType=$("#approvalType").val();
		var processNodeType=$("#processNodeType").val();
		var passRequire=3;
		var passNum=1;
		if(approvalType==1){
			passRequire=2;
		}
		var approvalRole={};
		approvalRole.approvalRoleType=approvalRoleType;
		if(approvalRoleType=="users"){
			approvalRole.approvalUserIds=approvalUserIds;
		}else{
			approvalRole.approvalUserIds=undefined;
		}
		if(approvalRoleType=="role"){
			approvalRole.approvalRoleId=approvalRoleId;
		}else{
			approvalRole.approvalRoleId=undefined;
		}
		if(approvalRoleType=="position"){
			approvalRole.org=$("#org").val();
			approvalRole.position=$("#position").val();
		}else{
			approvalRole.org=undefined;
			approvalRole.position=undefined;			
		}		
		approvalRole.relation=relation;
		var afterParameter={"bindField":$('#bindField').val()};
		var data = {
			"nodeName":$('#processNodeName').val(),
			"approvalRoleId":approvalRole,
			"processTemplateId":processTemplateId,
			"passRequire":passRequire,
			"passNum":passNum,
			"approvalType":processNodeType,
			"nodeEventImpl":$('#nodeEventImpl').val(),
			"afterParameter":afterParameter,
			"sort":formData.sort
		}
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			dataType: "json",
			success: function (data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {					
					showTicket("添加成功！",2000,function(){
						location.reload();
					});
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！",2000,function(){
						parent.location.href="index.html?v=1.56";
					});
				} else if (data.errCode == 200063) {
					showTicket("没有权限！",2000);
				} else {
					showTicket(data.errMsg, 2000);
				}
			}
		})
	};
	
	//修改流程节点模板
	function editProcessNodeTemplate() {
		var formData=form.val("nodeForm");
		layerIndex=layer.load();
		var url = servicePath + "/processTemplate/updateProcessNodeTemplate?charset=utf-8&loginCode="+adminInfo.loginCode;
		var approvalRoleId=$("input:radio[name='role']:checked").val();
		var approvalRoleType=$("input:radio[name='roleType']:checked").val();
		var relation=$("#relation").val();
		var approvalType=$("#approvalType").val();
		var processNodeType=$("#processNodeType").val();
		var passRequire=3;
		var passNum=1;
		if(approvalType==1){
			passRequire=2;
		}
		var approvalRole={};
		approvalRole.approvalRoleType=approvalRoleType;
		if(approvalRoleType=="users"){
			approvalRole.approvalUserIds=approvalUserIds;
		}else{
			approvalRole.approvalUserIds=undefined;
		}
		if(approvalRoleType=="role"){
			approvalRole.approvalRoleId=approvalRoleId;
		}else{
			approvalRole.approvalRoleId=undefined;
		}
		if(approvalRoleType=="position"){
			approvalRole.org=$("#org").val();
			approvalRole.position=$("#position").val();
		}else{
			approvalRole.org=undefined;
			approvalRole.position=undefined;			
		}		
		approvalRole.relation=relation;
		var afterParameter={"bindField":$('#bindField').val()};
		var data = {
			"processNodeId":$('#processNodeId').val(),
			"nodeName":$('#processNodeName').val(),
			"approvalRoleId":approvalRole,
			"processTemplateId":processTemplateId,
			"passRequire":passRequire,
			"passNum":passNum,
			"approvalType":processNodeType,
			"nodeEventImpl":$('#nodeEventImpl').val(),
			"afterParameter":afterParameter,
			"sort":formData.sort
		}
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			dataType: "json",
			success: function (data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {					
					showTicket("修改成功！",2000,function(){
						location.reload();
					});
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！",2000,function(){
						parent.location.href="index.html?v=1.56";
					});
				} else if (data.errCode == 200063) {
					showTicket("没有权限！",2000);
				} else {
					showTicket(data.errMsg, 2000);
				}
			}
		})
	};


	//添加流程分支模板
	function addProcessNodeBranchTemplate() {
		var formData=form.val("nodeBranchForm");
		layerIndex=layer.load();
		var url = servicePath + "/processTemplate/addProcessNodeBranchTemplate?charset=utf-8&loginCode="+adminInfo.loginCode;
		var condition={};
		var orgId=$('#orgId').val();
		condition.orgId=orgId;
		var roleId=$("input:radio[name='condRole']:checked").val();
		condition.roleId=roleId;		
		
		var condOrg=$('#condOrg').val();
		var condPosition=$('#condPosition').val();
		if(condPosition!=""){
			condition.position=condPosition;
			condition.org=condOrg;
		}	
		//condition.upleaderInAfter=$('#upleaderInAfter').val();
		
		var data = {
			"processNodeTemplateId":$('#processNodeTemplateId').val(),
			"condition":condition,
			"nextNodeCode":formData.nextNode,
			"sort":formData.sort
		}
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			dataType: "json",
			success: function (data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {					
					showTicket("添加成功！",1000);
					branchNodeTable.reload();
					layer.closeAll("page");
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！",2000,function(){
						parent.location.href="index.html?v=1.56";
					});
				} else if (data.errCode == 200063) {
					showTicket("没有权限！",2000);
				} else {
					showTicket(data.errMsg, 2000);
				}
			}
		})
	};
	
	//修改流程分支模板
	function editProcessNodeBranchTemplate() {
		var formData=form.val("nodeBranchForm");
		layerIndex=layer.load();
		var url = servicePath + "/processTemplate/updateProcessNodeBranchTemplate?charset=utf-8&loginCode="+adminInfo.loginCode;
		var condition={};
		var orgId=$('#orgId').val();
		condition.orgId=orgId;
		var roleId=$("input:radio[name='condRole']:checked").val();
		condition.roleId=roleId;		
		
		var condOrg=$('#condOrg').val();
		var condPosition=$('#condPosition').val();
		if(condPosition!=""){
			condition.position=condPosition;
			condition.org=condOrg;
		}	
		//condition.upleaderInAfter=$('#upleaderInAfter').val();
		
		var data = {
			"branchId":$('#branchId').val(),
			"processNodeTemplateId":$('#processNodeTemplateId').val(),
			"condition":condition,
			"nextNodeCode":formData.nextNode,
			"sort":formData.sort
		}
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			dataType: "json",
			success: function (data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("修改成功！",1000);
					branchNodeTable.reload();
					layer.closeAll("page");
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！",2000,function(){
						parent.location.href="index.html?v=1.56";
					});
				} else if (data.errCode == 200063) {
					showTicket("没有权限！",2000);
				} else {
					showTicket(data.errMsg, 2000);
				}
			}
		})
	};
	
	
	getRoleList();
	getProcseeNodeTemplateList();
	var nodeForm=$("#nodeForm").Validform({
		tiptype:function(msg,o,cssctl){
			//msg:提示信息;
			//o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素(或表单对象),type指示提示的状态,值为1、2、3、4, 1:正在检测/提交数据,2:通过验证,3:验证失败,4:提示ignore状态, curform为当前form对象;
			//cssctl:内置的提示信息样式控制函数,该函数需传入两个参数:显示提示信息的对象 和 当前提示的状态(既形参o中的type);
			//if(!o.obj.is("form")){//验证表单元素时o.obj为该表单元素,全部验证通过提交表单时o.obj为该表单对象;
			if(o.type==3){	
				layui.use('layer', function(){
				  var layer = layui.layer;					  
				  layer.msg(msg);
				});		
			}
			//}
		},
		ajaxPost:true,
		beforeSubmit:function(curform){
			addProcessNodeTemplate();
			return false;
		}
	});
	var nodeBranchForm=$("#nodeBranchForm").Validform({
		tiptype:function(msg,o,cssctl){
			//msg:提示信息;
			//o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素(或表单对象),type指示提示的状态,值为1、2、3、4, 1:正在检测/提交数据,2:通过验证,3:验证失败,4:提示ignore状态, curform为当前form对象;
			//cssctl:内置的提示信息样式控制函数,该函数需传入两个参数:显示提示信息的对象 和 当前提示的状态(既形参o中的type);
			//if(!o.obj.is("form")){//验证表单元素时o.obj为该表单元素,全部验证通过提交表单时o.obj为该表单对象;
			if(o.type==3){	
				layui.use('layer', function(){
				  var layer = layui.layer;					  
				  layer.msg(msg);
				});		
			}
			//}
		},
		ajaxPost:true,
		beforeSubmit:function(curform){
			addProcessNodeBranchTemplate();
			return false;
		}
	});

    $(".addNode").click(function(){
        addNode();
    });
	
	$(document).delegate(".delNode","click",function(){
		var processNodeTemplateId=$(this).attr("value");
		layer.confirm('确定删除审批节点吗？', function(index) {
			deleteProcseeNodeTemplate(processNodeTemplateId);
		})
	});
	
	$(document).delegate(".editNode","click",function(){
	    editNode($(this).attr("value"));
	});
	
	$(document).delegate(".addNodeBranch","click",function(){
		branchOption="add";
	    addNodeBranch($(this).val(),$(this).text());
	});
	
	var nodeName="";
	var nodeId=null;
	$(document).delegate(".addNodeBranch","mouseover",function(){
		branchNodeTable.reload({
			url:servicePath + "/processTemplate/getProcessNodeBranchTemplateList?charset=utf-8&loginCode="+adminInfo.loginCode+"&processNodeTemplateId="+$(this).val()
		});
		nodeId=$(this).val();
		nodeName=$(this).text();
	    //getProcseeNodeBranchTemplateList($(this).val());
	});
	
	form.on('radio(roleType)', function(data){
		element.tabChange('roleTypeTab', data.value);
		if(data.value=="position"){
			$("#position").attr("lay-verify","required");
		}else{
			$("#position").attr("lay-verify","");
		}
	})
	
	//设置单位下拉列表框
	function setAmUnitDropList(){
		$.ajax({
			type: "POST",
			url: servicePath + "/amUnit/search?charset=utf-8&loginCode="+adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify({"page":1,"pageSize":1000}),
			dataType: "json",
			success: function (data) {
				if (data.errCode == 0) {
					var orgList=data.orgList;
					for(var i=0;i<orgList.length;i++){
						seladditem(orgList[i].orgId,orgList[i].orgName,document.getElementById("orgId"));
						seladditem(orgList[i].orgId,orgList[i].orgName,document.getElementById("org"));
						seladditem(orgList[i].orgId,orgList[i].orgName,document.getElementById("condOrg"));
					}
					form.render('select');
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！",2000,function(){
						parent.location.href="index.html?v=1.56";
					});
				}
			}
		})
	}
	//设置单位下拉列表框
	setAmUnitDropList();
	
	//根据用户ID列表获取用户名称串
	function getNamesByUserIds(userIdList){
		var names="";
		var url = servicePath + "/user/getListByUserIds?charset=utf-8&loginCode="+adminInfo.loginCode;
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(userIdList),
			dataType: "json",
			async:false,
			success: function (data) {
				if (data.errCode == 0) {
					var userList = data.userList;
					for (var i = 0; i < userList.length; i++) {
						names+="，"+userList[i].name;
					}
					if(names!=""){
						names=names.substr(1);
					}
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！",2000,function(){
						parent.location.href="index.html?v=1.55";
					});
				}
			}
		})
		return names;
	}
	
	//添加流程节点“保存”按钮触发
	form.on('submit(addNodeSubmit)', function(data){
		if(nodeOption=="add"){
			addProcessNodeTemplate();
		}else{
			editProcessNodeTemplate();
		}
	  return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});	//添加流程节点“保存”按钮触发
	form.on('submit(addNodeBranchSubmit)', function(data){
		if(branchOption=="add"){
			addProcessNodeBranchTemplate();
		}else{
			editProcessNodeBranchTemplate();
		}
	  return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});	
	
	$("#selectUser").click(function(){
			top.openSelectRy({
					url:'selectFjry.html',
					title:'选择审批人员',
					resource:'/user/selectAdmin',
					where:{state:1},
					treeWhere:{},
					area:['1024px','768px'],
					page:true,
					limit:12,
					limits:[12,30,50,100,1000,5000],
					selectType:'checkbox',//radio,checkbox
					checkedData:[],//默认选中的数据，只对selectType为checkbox起作用
					disabledData:[],//默认禁用的数据
					compareField:'userId',//用于识别选中或禁用的字段
					isRequire:true//是否必选
				},window.name,function(returnData,index){	
					console.log(returnData);
					for(var i=0;i<returnData.length;i++){
						approvalUserIds.push(returnData[i].userId);
					}
					$("#approvalNames").html(getSelectIds(returnData,"xm"));
					top.layer.close(index);
			});	  
	});
	
	if(processTemplateId=='3'){//减员管理
		$(".bindProgress").each(function(){
			  addDictDataToSelect(this,"JYGL_PROGRESS");
		})  
	} else if(processTemplateId=='4'){//工资月报
		$(".bindProgress").each(function(){
			  addDictDataToSelect(this,"GZYB_PROGRESS");
		})  
	} else if(processTemplateId=='5'){//绩效工资月报
		$(".bindProgress").each(function(){
			  addDictDataToSelect(this,"JXGZYB_PROGRESS");
		})  
	}  else if(processTemplateId=='82558031233024'){//考评指标审核
		$(".bindProgress").each(function(){
			  addDictDataToSelect(this,"KPZB_PROGRESS");
		})  
	}else {
		$(".bindProgress").each(function(){
			  addDictDataToSelect(this,"ZL_PROGRESS");
		})  
	}
	form.render('select');
	
});

window.onload=function(){
	
}