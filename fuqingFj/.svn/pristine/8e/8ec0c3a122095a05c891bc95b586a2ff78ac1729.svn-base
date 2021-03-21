var setting=JSON.parse(unescape(getParameter('setting')));
var compareField=setting.compareField;
var checkedDataObj={};
var checkedDataList=setting.checkedData;
if(checkedDataList!=undefined && checkedDataList.length>0){				
	for(var i=0;i<checkedDataList.length;i++){
		checkedDataObj[checkedDataList[i]]=true;
	}				
}
var disabledDataObj={};
var disabledData=setting.disabledData;
if(disabledData!=undefined && disabledData.length>0){				
	for(var i=0;i<disabledData.length;i++){
		disabledDataObj[disabledData[i]]=true;
	}
}
var table; 
var checkedData=[];//回调返回值
var list=[];

function getCheckedData(){
	return table.checkStatus('orgTable').data;
}

layui.use(['tree', 'util', 'form', 'table', 'layer', 'upload'], function() {
	var $ = layui.jquery;
	table = layui.table;
	var form = layui.form;
	var tree = layui.tree;
	var util = layui.util;
	var upload = layui.upload;

	var orgTable = table.render({
		id:'orgTable',
		elem: '#orgTable',
		url: servicePath + setting.resource + "?charset=utf-8&loginCode=" + adminInfo.loginCode,
		method: 'POST',
		contentType: "application/json; charset=utf-8",
		where: setting.where,
		limit:setting.limit,
		limits:setting.limits,
		title: '单位列表',
		cols: [
			[	{
					type: setting.selectType,
					fixed: 'center'
				}, {
					field: 'orgName',
					title: '单位名称'
				},
				{
					field: 'orgCode',
					title: '单位编码'
				}
				,{field:'parentName', 
					title:'上级单位'
				}
			]
		],
		page: setting.page,
		parseData: function(res) { //res 即为原始返回的数据
			return res;
		},
		height: 'full-151',
		response: {
			statusName: 'errCode', //规定数据状态的字段名称，默认：code
			statusCode: 0, //规定成功的状态码，默认：0
			msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
			countName: 'recordCount', //规定数据总数的字段名称，默认：count
			dataName: 'list' //规定数据列表的字段名称，默认：data
		},
		request: {
			pageName: 'page', //页码的参数名称，默认：page
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		},
		done: function(res, curr, count){			
			list=res.list;
			var selectAll=true;//是否全选,如果全选需要另外处理
			if(setting.selectType=='checkbox'){
				for(var i=0;i<list.length;i++){
					var index=list[i]["LAY_TABLE_INDEX"];
					if(!checkedDataObj[list[i][compareField]]){
						selectAll=false;
					}
				}
			}
			
			//如果全选需要另外处理
			if(selectAll&&list.length>0){
				$(".layui-table-header").find('th[data-field="0"] .layui-form-checkbox').click();
				$('input[lay-filter="layTableAllChoose"]').click();
				
				for(var i=0;i<list.length;i++){
					var index=list[i]["LAY_TABLE_INDEX"];
					if(disabledDataObj[list[i][compareField]]){
						$('tr[data-index=' + index + '] input[type="checkbox"]').prop('disabled', true);
						$('tr[data-index=' + index + '] input[type="checkbox"]').next().addClass('layui-btn-disabled');
					}
				}
				
			}else{
				for(var i=0;i<list.length;i++){
					var index=list[i]["LAY_TABLE_INDEX"];
					if(checkedDataObj[list[i][compareField]]){
						$(".layui-table-body [class!='layui-table-body layui-table-main']").find('tr[data-index=' + index + '] .layui-form-checkbox').click();
					}
					
					if(disabledDataObj[list[i][compareField]]){
						$('tr[data-index=' + index + '] input[type="checkbox"]').prop('disabled', true);
						$('tr[data-index=' + index + '] input[type="checkbox"]').next().addClass('layui-btn-disabled');
					}
				}
			}
			if(setting.selectType=='radio'){
				for(var i=0;i<list.length;i++){
					var index=list[i]["LAY_TABLE_INDEX"];
					if(disabledDataObj[list[i][compareField]]){
						$('tr[data-index=' + index + '] input[type="radio"]').prop('disabled', true);
						$('tr[data-index=' + index + '] input[type="radio"]').next().addClass('layui-btn-disabled');
					}
				}
			}
			form.render('checkbox');			
		}
	});

	//监听选择事件
	table.on('checkbox(orgTable)', function(obj) {
		if (obj.type === 'all') {
			if(obj.checked==true){
				for(var i=0;i<list.length;i++){
					var index=list[i]["LAY_TABLE_INDEX"];
					//console.log(checkedDataObj[list[i][compareField]],disabledDataObj[list[i][compareField]]);
					//不在选中里面，而且是禁用状态的
					if(checkedDataObj[list[i][compareField]]==undefined && disabledDataObj[list[i][compareField]]){
						//先解除禁用
						//$('tr[data-index=' + index + '] input[type="checkbox"]').prop('disabled', false);
						//再点击
						//2:选中值改变
						$(".layui-table-body [class!='layui-table-body layui-table-main']").find('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', false);
						$('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', false);
						table.cache.orgTable[index].LAY_CHECKED=false;
						//$(".layui-table-body [class!='layui-table-body layui-table-main']").find('tr[data-index=' + index + '] .layui-form-checkbox').click();
						//1:选中外观改变
						//$('tr[data-index=' + index + '] input[type="checkbox"]').click();
						//再禁用
						//$('tr[data-index=' + index + '] input[type="checkbox"]').prop('disabled', true);
						//$('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', false);
					}
				}
			}
			if(obj.checked==false){
				for(var i=0;i<list.length;i++){
					var index=list[i]["LAY_TABLE_INDEX"];
					//在选中里面，而且是禁用状态的
					if(checkedDataObj[list[i][compareField]] && disabledDataObj[list[i][compareField]]){
						//$(".layui-table-body [class!='layui-table-body layui-table-main']").find('tr[data-index=' + index + '] .layui-form-checkbox').click();
						//$('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
						$(".layui-table-body [class!='layui-table-body layui-table-main']").find('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
						$('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
						table.cache.orgTable[index].LAY_CHECKED=true;
					}
				}
			}
			form.render('checkbox');
		}

	});

	//头工具栏事件
	table.on('toolbar(orgTable)', function(obj) {
		var checkStatus = table.checkStatus('orgTable');
		switch (obj.event) {
			case 'addArchive':
				top.topTabAdd("添加辅警档案",
					'<div class="layui-body"><iframe name="addArchive.html?v=1.60" src="addArchive.html?v=1.60" frameborder="0" class="layadmin-iframe"></iframe></div>',
					'addArchive.html?v=1.60')
				break;
			case 'getCheckLength':
				var data = checkStatus.data;
				layer.msg('选中了：' + data.length + ' 个');
				break;
			case 'isAll':
				layer.msg(checkStatus.isAll ? '全选' : '未全选');
				break;
		};
	});

	//监听行工具事件
	table.on('tool(orgTable)', function(obj) {
		var data = obj.data;
		if (obj.event === 'del') {
		}

	});


	//获取单位树
	function getAmUnitTree() {
		$.ajax({
			type: "POST",
			url: servicePath + "/amUnit/getAmUnitTreeForSelect?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(setting.treeWhere),
			dataType: "json",
			success: function(data) {
				if (data.errCode == 0) {
					tree.render({
						elem: '#amUnitTree',
						data: data.orgTree,
						onlyIconControl: true,
						click: function(obj) {
							orgTable.reload({
								where: {
									orgId: obj.data.id
								}
							});
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
	getAmUnitTree();
	$("#amUnitTree").height($(".col2").height());

	$(".xb").each(function(){
		  addDictDataToSelect(this,"SEX");
	})  
	$(".mz").each(function(){
		  addDictDataToSelect(this,"MZ");
	}) 
	form.render('select');

	//搜索单位信息
	/* function search() {
		var formData = form.val("searchForm");
		var data = {
			xb: formData.xb,
			xm: formData.xm,
			mz: formData.mz
		}
		orgTable.reload({
			where: data
		});
	} */
	
  //搜索单位
  function search(){
	  var formData=form.val("searchForm");
	  var data={
		  kwFields:11
		  ,keyword:formData.keyword
		  /* ,orgType:formData.orgType==''?undefined:formData.orgType
		  ,orgGroup:formData.orgGroup==''?undefined:formData.orgGroup
		  ,orgLevel:formData.orgLevel==''?undefined:formData.orgLevel */
	  }
	  orgTable.reload({
		  where:data
	  });
  }
	//搜索单位“查询”按钮触发
	form.on('submit(searchBtn)', function(data) {
		search()
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});

});

window.onload = function() {

}
