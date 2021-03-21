var parentObj={
	parentId:0,
	parentName:'无'
}
var parentId=getParameter('parentId');
if(parentId!=null){
	parentObj={
		parentId:parentId,
		parentName:unescape(getParameter('parentName'))
	}
}

layui.use(['tree', 'util','element','form','table','layer'], function(){
  var $ = layui.jquery;
  var tree = layui.tree;
  var util = layui.util;
  var element = layui.element;
  var table = layui.table;
  var form = layui.form;
  var layer = layui.layer;
  
  
  
  var menuTable=table.render({
    elem: '#menuList'
    ,url:servicePath + "/menu/search?charset=utf-8&loginCode="+adminInfo.loginCode
    ,method:'POST'
    ,contentType: "application/json; charset=utf-8"
    ,where: { "kwFields": 7, "keyword": "",parentId:parentObj.parentId}
	,limit:10
	,limits:[10,50,100,1000,10000]
    ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
    ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
      title: '提示'
      ,layEvent: 'LAYTABLE_TIPS'
      ,icon: 'layui-icon-tips'
    }]
    ,title: '菜单列表'
    ,cols: [[
      {type: 'checkbox', fixed: 'left'}
      ,{field:'menuName', title:'菜单名称'}
      ,{field:'menuCode', title:'菜单编码'}
      ,{field:'parentName', title:'上级菜单'}
      ,{field:'remark', title:'描述'}
      ,{fixed: 'right', title:'操作', toolbar: '#barDemo'}
    ]]
    ,page: true
	,parseData: function(res){ //res 即为原始返回的数据
		if(parentObj.parentId==0){
			  $("#goToParent").hide();
		}else{
			  $("#goToParent").show();
		}
		return res;
	 }
	 ,height:'full-105'
	 ,response: {
		statusName: 'errCode' //规定数据状态的字段名称，默认：code
		,statusCode: 0 //规定成功的状态码，默认：0
		,msgName: 'errMsg' //规定状态信息的字段名称，默认：msg
		,countName: 'recordCount' //规定数据总数的字段名称，默认：count
		,dataName: 'menuList' //规定数据列表的字段名称，默认：data
	 } 
	 ,request: {
		pageName: 'page' //页码的参数名称，默认：page
		,limitName: 'pageSize' //每页数据量的参数名，默认：limit
	 }
  });
  
  //头工具栏事件
  table.on('toolbar(menuList)', function(obj){
    var checkStatus = table.checkStatus(obj.config.id);
    switch(obj.event){
	  //添加菜单
      case 'addMenu':
		  seladditem(parentObj.parentId,parentObj.parentName,document.getElementById("add_parentId"));
		  form.render('select','addMenu')
		  layer.open({
			  type: 1
			  ,title:'添加菜单'
			  ,area:['600px','500px']
			  ,content: $('#addMenuPage')
			  ,btn: ['保存', '关闭']
			  ,yes:function(index, layero){
				  $("#addMenuSubmit").click();
			  }
			  ,btn2: function(index, layero){
				layer.close(index);
			  }
		  });  
		  break;
	  //返回上级菜单
      case 'goToParent':
		getMenu(parentObj.parentId);
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
  table.on('tool(menuList)', function(obj){
    var data = obj.data;
    //console.log(obj)
	//进入下级菜单
    if(obj.event === 'goToMenuList'){		
		location.href="menuList.html?v=1.60&parentId="+data.menuId+"&parentName="+escape(data.menuName);
    } 
    if(obj.event === 'del'){
      layer.confirm('真的要删除菜单吗？', function(index){
        obj.del();
        deleteMenu(data.menuId);
        layer.close(index);
      });
    } 
	//修改菜单
	if(obj.event === 'edit'){
	  form.val("editMenu",data);
	  layer.open({
		  type: 1
		  ,title:'修改菜单'
		  ,area:['600px','500px']
		  ,content: $('#editMenuPage')
		  ,btn: ['保存', '关闭']
		  ,yes:function(index, layero){
			  $("#editMenuSubmit").click();
		  }
		  ,btn2: function(index, layero){
			layer.close(index);
		  }
	  });  
    }
  });
  
  
  
  //添加菜单
  function addMenu(data) {
  	var layerIndex=layer.load();
  	$.ajax({
  		type: "POST",
  		url: servicePath + "/menu/add?charset=utf-8&loginCode="+adminInfo.loginCode,
  		contentType: "application/json; charset=utf-8",
  		data: JSON.stringify(data),
  		dataType: "json",
  		success: function (data) {
  			layer.close(layerIndex);
  			if (data.errCode == 0) {
  				showTicket("添加成功！",2000,function(){
  					layer.closeAll('page');
  					document.getElementById("addMenu").reset();
  					menuTable.reload();					
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
  
  //修改菜单
  function editMenu(data) {
  	layerIndex=layer.load();
  	$.ajax({
  		type: "POST",
  		url: servicePath + "/menu/update?charset=utf-8&loginCode="+adminInfo.loginCode,
  		contentType: "application/json; charset=utf-8",
  		data: JSON.stringify(data),
  		dataType: "json",
  		success: function (data) {
			layer.close(layerIndex);
  			if (data.errCode == 0) {
				showTicket("修改成功！",2000,function(){
					layer.closeAll('page');
					menuTable.reload();
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
  };  
  
  
  //删除菜单
  function deleteMenu(menuId) {
  	var layerIndex=layer.load();
  	$.ajax({
  		type: "GET",
  		url: servicePath + "/menu/delete?charset=utf-8&menuId="+menuId+"&loginCode="+adminInfo.loginCode,
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
  
  //获取菜单
  function getMenu(menuId) {
  	var layerIndex=layer.load();
  	$.ajax({
  		type: "GET",
  		url: servicePath + "/menu/getMenu?charset=utf-8&menuId="+menuId+"&loginCode="+adminInfo.loginCode,
  		contentType: "application/json; charset=utf-8",
  		dataType: "json",
  		success: function (data) {
  			layer.close(layerIndex);
  			if (data.errCode == 0) {
  				location.href="menuList.html?v=1.60&parentId="+data.menu.parentId+"&parentName="+escape(data.menu.parentName);
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
  
  //搜索菜单
  function search(){
	  var formData=form.val("searchForm");
	  var data={
		  kwFields:7
		  ,keyword:formData.keyword
	  }
	  menuTable.reload({
		  where:data
	  });
  }
  
  
  //添加菜单“保存”按钮触发
  form.on('submit(addMenu)', function(data){
    addMenu(form.val("addMenu"));
    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
  });		
  
  //修改菜单“保存”按钮触发
  form.on('submit(editMenu)', function(data){
    editMenu(form.val("editMenu"));
    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
  });	
  
  //搜索菜单“查询”按钮触发
  form.on('submit(searchBtn)', function(data){
	search();
    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
  });
});