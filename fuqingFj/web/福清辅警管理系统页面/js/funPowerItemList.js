
layui.use(['tree', 'util','form','table'], function(){
  var $ = layui.jquery;
  var table = layui.table;
  var form = layui.form;
  var tree = layui.tree;
  var util = layui.util;
  
  var funPowerTable=table.render({
    elem: '#test'
    ,url: servicePath + "/powerItem/search?charset=utf-8&loginCode=" + adminInfo.loginCode
  	,method: 'POST'
  	,contentType: "application/json; charset=utf-8"
  	,where: {outKey1:3}
	,limit:10
	,limits:[10,50,100,1000,10000]
    ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
    ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
      title: '提示'
      ,layEvent: 'LAYTABLE_TIPS'
      ,icon: 'layui-icon-tips'
    }]
    ,title: '功能权限列表'
    ,cols: [[
      {type: 'checkbox', fixed: 'left'}
      ,{field:'itemName', title:'权限名称'}
      ,{field:'outKey1', title:'所属菜单'}
      ,{field:'powerCode', title:'权限编码'}
      ,{field:'referer', title:'请求来源'}
      ,{field:'resource', title:'请求资源',sort:true}
      ,{field:'parameter', title:'请求参数'}
      ,{field:'remark', title:'备注'}
      ,{fixed: 'right', title:'操作', toolbar: '#barDemo'}
    ]]
    ,page: true
	,parseData: function(res){ //res 即为原始返回的数据
		return res;
	 }
	,onlyIconControl:true
	,height:'full-131'
  	,response: {
  		statusName: 'errCode', //规定数据状态的字段名称，默认：code
  		statusCode: 0 ,//规定成功的状态码，默认：0
  		msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
  		countName: 'recordCount', //规定数据总数的字段名称，默认：count
  		dataName: 'adminPowerItemList' //规定数据列表的字段名称，默认：data
  	},
  	request: {
  		pageName: 'page', //页码的参数名称，默认：page
  		limitName: 'pageSize' //每页数据量的参数名，默认：limit
  	}
  });
  
  //头工具栏事件
  table.on('toolbar(test)', function(obj){
    var checkStatus = table.checkStatus(obj.config.id);
    switch(obj.event){
	  //添加角色
      case 'addFunPowerItem':
		  layer.open({
			  type: 1
			  ,title:'添加功能权限'
			  ,area:['600px','500px']
			  ,content: $('#addFunPowerItemPage')
			  ,btn: ['保存', '关闭']
			  ,yes:function(index, layero){
				  $("#addFunPowerItemSubmit").click();
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
  table.on('tool(test)', function(obj){
    var data = obj.data;
    //console.log(obj)
	//修改功能权限
	if(obj.event === 'edit'){
	  form.val("editFunPowerItem",data);
	  layer.open({
		  type: 1
		  ,title:'修改功能权限'
		  ,area:['600px','500px']
		  ,content: $('#editFunPowerItemPage')
		  ,btn: ['保存', '关闭']
		  ,yes:function(index, layero){
			  $("#editFunPowerItemSubmit").click();
		  }
		  ,btn2: function(index, layero){
			layer.close(index);
		  }
	  });  
    }
	if(obj.event === 'del'){
      layer.confirm('真的要删除功能权限吗？', function(index){
        obj.del();
        deleteFunPowerItem(data.powerItemId);
        layer.close(index);
      });
    }
  });
  
  
	//获取菜单树
	function getMenuTree() {
		$.ajax({
			type: "POST",
			url: servicePath + "/menu/getMenuTree?charset=utf-8&loginCode="+adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify({}),
			dataType: "json",
			success: function (data) {
				if(data.errCode==0){
				  tree.render({
					elem: '#menuTree' 
					,data: data.menuTree
					,onlyIconControl:true
					,click: function(obj){
						if(obj.data.children==undefined){
							$(".menuId").val(obj.data.id);
							$(".menuName").val(obj.data.menuName);
							funPowerTable.reload({
								where: {
									outKey1: obj.data.id
								}
							}); 
						}
					}
				  });
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！",2000,function(){
						parent.location.href="index.html?v=1.60";
					});
				}
			}
		})
	}
	getMenuTree();
    $("#menuTree").height($(".col2").height());
	
	
	
	
	//添加功能权限
	function addFunPowerItem(data) {
		var layerIndex=layer.load();
		$.ajax({
			type: "POST",
			url: servicePath + "/powerItem/add?charset=utf-8&loginCode="+adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			dataType: "json",
			success: function (data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("添加成功！",2000,function(){
						layer.closeAll('page');
						document.getElementById("addFunPowerItem").reset();
						funPowerTable.reload();					
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
	
	//修改功能权限
	function editFunPowerItem(data) {
		var layerIndex=layer.load();
		$.ajax({
			type: "POST",
			url: servicePath + "/powerItem/update?charset=utf-8&loginCode="+adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			dataType: "json",
			success: function (data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("修改成功！",2000,function(){
						layer.closeAll('page');
						document.getElementById("editFunPowerItem").reset();
						funPowerTable.reload();
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
	
  //删除功能权限
  function deleteFunPowerItem(powerItemId) {
  	var layerIndex=layer.load();
  	$.ajax({
  		type: "GET",
  		url: servicePath + "/powerItem/delete?charset=utf-8&powerItemId="+powerItemId+"&loginCode="+adminInfo.loginCode,
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

	//搜索角色
	function search(){
		var formData=form.val("searchForm");
		var data={
			itemName: formData.itemName == '' ? null : formData.itemName,
			powerCode: formData.powerCode == '' ? null : formData.powerCode
		}
		funPowerTable.reload({
			where:data
		});
	}
	
	
	
	//添加功能权限“保存”按钮触发
	form.on('submit(addFunPowerItemSubmit)', function(data){
	  addFunPowerItem(form.val("addFunPowerItem"));
	  return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});
	//修改功能权限“保存”按钮触发
	form.on('submit(editFunPowerItemSubmit)', function(data){
	  editFunPowerItem(form.val("editFunPowerItem"));
	  return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});
	//搜索权限项“查询”按钮触发
	form.on('submit(searchBtn)', function(data){
		search();
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});
});

window.onload=function(){
	
}