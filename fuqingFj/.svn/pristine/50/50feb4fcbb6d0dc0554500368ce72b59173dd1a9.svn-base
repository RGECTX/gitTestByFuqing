layui.use(['tree', 'util','element','form','table','layer'], function(){
  var $ = layui.jquery;
  var tree = layui.tree;
  var util = layui.util;
  var element = layui.element;
  var table = layui.table;
  var form = layui.form;
  var layer = layui.layer;
  var receiveList=[];
  
  
  var innerMessageTable=table.render({
    elem: '#innerMessageList'
    ,url:servicePath + "/receive/search?charset=utf-8&loginCode="+adminInfo.loginCode
    ,method:'POST'
    ,contentType: "application/json; charset=utf-8"
    ,where: { "kwFields": 11, "keyword": "","recipient":adminInfo.userId}
	,limit:10
	,limits:[10,50,100,1000,10000]
	,height : 300
    ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
    ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
      title: '提示'
      ,layEvent: 'LAYTABLE_TIPS'
      ,icon: 'layui-icon-tips'
    }]
    ,title: '消息列表'
    ,cols: [[
      {type: 'checkbox', fixed: 'left'}
      ,{field:'senderName',title:'发件人',width : 80,align: 'center',templet: function(d){
				return d.innerMessage.senderName;
		   }}
      ,{field:'title', title:'标题',align: 'center',templet: function(d){
				return d.innerMessage.title;
		   }}
      ,{field:'msgType', title:'消息类型',width : 90,align: 'center',templet: function(d){
				return getDictDataLabel("MSG_TYPE",d.innerMessage.msgType);
		   }}
      ,{field:'content', title:'消息内容',width : 90,align: 'center',templet: function(d){
				return d.innerMessage.content;
		   }}
      ,{field:'remark', title:'备注',align: 'center',templet: function(d){
				return d.innerMessage.remark;
		   }}
      ,{field:'createTime', title:'发送时间',width : 180,align: 'center',templet: function(d){
				return new Date(d.innerMessage.createTime).format("yyyy-MM-dd hh:mm:ss");
		   }}
      ,{field:'receiveType', title:'接收类型',width : 90,align: 'center',templet: function(d){
				return getDictDataLabel("RECEIVE_TYPE",d.receiveType);
		   }}
      ,{field:'receiveState', title:'是否已读',width : 90,align: 'center',templet: function(d){
				return getDictDataLabel("RECEIVE_STATE",d.receiveState);
		   }}
      ,{fixed: 'right', title:'操作', align: 'center',toolbar: '#barDemo'}
    ]]
    ,page: true
	,parseData: function(res){ //res 即为原始返回的数据
		return res;
	 }
	 //,height:'full-121'
	 ,response: {
		statusName: 'errCode' //规定数据状态的字段名称，默认：code
		,statusCode: 0 //规定成功的状态码，默认：0
		,msgName: 'errMsg' //规定状态信息的字段名称，默认：msg
		,countName: 'recordCount' //规定数据总数的字段名称，默认：count
		,dataName: 'receiveList' //规定数据列表的字段名称，默认：data
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
    if(obj.event === 'del'){
      layer.confirm('确定要删除消息吗？', function(index){
        obj.del();
        deleteMsg(data.innerMessageId);
        layer.close(index);
      });
    } 
	//查看消息
	if(obj.event === 'view'){
		if(data.receiveState==1){
			read(data.receiveId);
		}		
		var formData={};
		formData.senderName=data.innerMessage.senderName;
		formData.title=data.innerMessage.title;
		formData.msgType=data.innerMessage.msgType;
		formData.content=data.innerMessage.content;
		formData.remark=data.innerMessage.remark;
		form.val("editMsg",formData);
	  layer.open({
		  type: 1
		  ,title:'查看消息'
		  ,area:['600px','500px']
		  ,content: $('#editMsgPage')
		  ,btn: ['关闭']
		  ,yes:function(index, layero){
			  layer.close(index);
		  }
	  });  
    }
  });
  
  
  
  
  
  //删除消息
  function read(receiveId) {
  	var layerIndex=layer.load();
  	$.ajax({
  		type: "GET",
  		url: servicePath + "/receive/read?charset=utf-8&receiveId="+receiveId+"&loginCode="+adminInfo.loginCode,
  		contentType: "application/json; charset=utf-8",
  		dataType: "json",
  		success: function (data) {
  			layer.close(layerIndex);
  			if (data.errCode == 0) {
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
  	  addDictDataToSelect(this,"MSG_TYPE");
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
				where:{state:1,kwFields:3,keyword:""},
				treeWhere:{state:15},
				area:['1024px','768px'],
				page:false,
				limit:10,
				limits:[10,50,100,1000,10000],
				selectType:'checkbox',//radio,checkbox
				checkedData:[],//默认选中的数据，只对selectType为radio起作用
				disabledData:[],//默认禁用的数据
				compareField:'id',//用于识别选中或禁用的字段
				isRequire: true
			},window.name,function(returnData,layerIndex){
				var names="";
				receiveList=[];
				for(var i=0;i<returnData.length;i++){
					names+="，" + returnData[i].xm;
					var receive={};
					receive.receiveType=1;
					receive.recipient=returnData[i].userId;
					receive.recipientName=returnData[i].xm;
					receiveList.push(receive);
				}
				names=names.substr(1);
				$(".recipientNames").val(names);
				top.layer.close(layerIndex);
		});
  })
  
  
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
});