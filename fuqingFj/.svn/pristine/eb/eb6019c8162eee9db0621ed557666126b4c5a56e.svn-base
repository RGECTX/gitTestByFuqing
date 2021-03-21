layui.use(['element', 'form', 'table', 'layer'], function() {
	var $ = layui.jquery;
	var element = layui.element;
	var table = layui.table;
	var form = layui.form;
	var layer = layui.layer;

	$(".layadmin-shortcut").find("a").click(function() {
		top.topTabAdd($(this).find("cite").html(), '<div class="layui-body"><iframe name="' + $(this).attr("lay-href") +
			'" src="' + $(this).attr("lay-href") + '" frameborder="0" class="layadmin-iframe"></iframe></div>', $(this).attr(
				"lay-href"));
	});

	$(".layadmin-pending").find("a").click(function() {
		top.topTabAdd($(this).find("h3").html(), '<div class="layui-body"><iframe name="' + $(this).attr("lay-href") +
			'" src="' + $(this).attr("lay-href") + '" frameborder="0" class="layadmin-iframe"></iframe></div>', $(this).attr(
				"lay-href"));
	});

	//首页待处理
	$.ajax({
		type: "POST",
		url: servicePath + "/console/toDo?charset=utf-8&loginCode=" + adminInfo.loginCode,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		success: function(data) {
			$("#dwjs").html(data.dwjs);
			$("#jydcl").html(data.jydcl);
			$("#gzsh").html(data.gzsh);
			$("#gssh").html(data.gssh);
			$("#rytj").html(data.rytj);
		}
	})

    var noticeTable = table.render({
		elem:'#noticeList',
		url: servicePath + "/auxNotice/search?charset=utf-8&loginCode=" + adminInfo.loginCode,
		method: 'POST',
		contentType:"application/json;charset=utf-8",
		where: {
			"state":1
		},
		limit: 10,
		limits:[10,50,100,1000,10000],
		defaultToolbar: ['filter','exports','print',{
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
			},{
				field: 'noticeTitle',
				title: '通知标题',
				sort: true,
				width: 120
			},{
				field: 'noticeText',
				title: '通知内容',
				sort: true,
				minwidth: 200
			},{
				field: 'remark',
				title: '备注',
				sort: true,
				width: 100,
			},{
				field: 'createTime',
				title: '创建时间',
				sort: true,
				width: 160,
				templet:function (d){
					if (d.createTime){
						return layui.util.toDateString(d.createTime);
					}else {
						return '';
					}
				}
			},{
				fixed: 'right',
				title: '操作',
				toolbar: '#noticeBarDemo',
				width: 70
			}]
		],
		page: true,
		parseData: function (res){//res即为原始返回的数据
			return res;
		},
		height: 'full-380',
		response: {
			statusName: 'errCode',//规定数据状态的字段名称,默认：code
			statusCode: 0,//规定成功的状态码，默认0
			msgName: 'errMsg',//规定状态信息的字段名称，默认msg
			countName: 'recordCount',//规定数据总数的字段名称，默认count
			dataName: 'auxNoticeList' //规定数据列表的字段名称
		},
		request: {
			pageName: 'page',//页面的参数名称，默认page
			limitName: 'pageSize',//页面数据量的参数名,默认:limit
		}
	});

	//监听行工具事件
	table.on('tool(noticeList)',function (obj){
		var data=obj.data;
		if (obj.event==='noticeViewBtn'){
			getAttachmentList("AuxNotice",data.noticeId,'noticeViewPage');
			form.val("noticeViewForm",data);
			layer.open({
				type: 1,
				title: '查看通知通告',
				area: ['700px','600px'],
				content: $('#noticeViewPage'),
				btn: ['关闭'],
				yes: function (index,layero){
					layer.close(index);
				}
			});
		}
	});

	//获取附件列表显示于查看
	function getAttachmentList(instanceType,instanceId,typePage){
		var layerIndex=layer.load();
		$.ajax({
			type: 'GET',
			url: servicePath + "/file/getAttachmentList?charset=utf-8&instanceType="+ instanceType +"&instanceId="+
				instanceId + "&loginCode="+ adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function (data){
				layer.close(layerIndex);
				if(data.errCode==0){
					var uploadList = "#"+typePage+ " #view_uploadList";
					$(uploadList).html("");
					var attachmentList = data.attachmentList;
					for (var i=0;i<attachmentList.length;i++){
						var tr = $(['<tr>','<td><a href="' +servicePath+'/'+attachmentList[i].filePath+' " target="_blank">'+
						attachmentList[i].fileName+'</a></td>','<td>'+(attachmentList[i].fileSize /1014).toFixed(1)+
						'kb</td>','<td>已存在</td>','<td>',
						'<button class="layui-btn layui-btn-xs viewFile layui-hide" attachmentId="'+attachmentList[i].id+
						'">查看</button>','</td>','</tr>'
						].join(''));
						$(uploadList).append(tr);
					}
				}else if (data.errCode==200061 || data.errCode== 200062){
					showTicket("请先登录！",2000,function (){
						parent.location.href="index.html?v=1.60";
					});
				}else if (data.errCode==200063){
					showTicket("没有权限！",2000);
				}else {
					showTicket(data.errMsg,2000);
				}
			}
		})
	};
});
