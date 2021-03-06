var iframeName = getParameter("iframeName");
var da_id = getParameter("daId");

layui.use(['tree', 'util', 'form', 'table', 'layer', 'upload'], function() {
	var $ = layui.jquery;
	var table = layui.table;
	var form = layui.form;
	var tree = layui.tree;
	var util = layui.util;
	var upload = layui.upload;
	
	var myOrgId = '';

    amArchiveTable = table.render({
        elem: '#test',
        url: servicePath + "/auxDagl/search?charset=utf-8&loginCode=" + adminInfo.loginCode,
        method: 'POST',
        contentType: "application/json; charset=utf-8",
        /*where: {"state": "47"},*/
		where: {
			"kwFields": 3,
			"keyword": ""
		},
        limit: 12,
        limits: [12, 30, 50, 100, 1000],
		loading:true,
        toolbar: '#toolbarDemo', //开启头部工具栏，并为其绑定左侧模板
        defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        title: '辅警档案信息数据表',
        cols: [
            [{
				type: 'checkbox',
				fixed: 'center'
            },  {
				field: 'state',
				title: '状态',
				width: 120,
				align: "center",
				templet: function (d) {
					if (d.state == '1') {
						return '<font color=green >在岗</font>'
					} else if (d.state == '2') {
						return '<font color=red >离职</font>'
					}else {
						return '<font color=blue >未上报</font>';
					}
				}
			},{
                field: 'xm',
                title: '姓名',
				align: 'center',
                width: 120,
                sort: true
            }, {
				field: 'xb',
				title: '性别',
				width: 80,
				align: "center",
				templet: function (d) {
					return getDictDataLabel("SEX", d.xb);
				}
			}, {
				field: 'orgId',
				title: '工作单位名称',
				width: 250,
				align: "center",
				templet:function (d) {
					return getOrgName(d.orgId);
				}
			},/*{
				field: 'idcard',
				title: '身份证号',
				width: 200,
				align: "center"
			},{
				field: 'xl',
				title: '最高学历',
				align: 'center',
				width: 120,
				sort: true,
				templet: function (d) {
					return getDictDataLabel("AM_XL", d.xl);
				}
			}, {
				field: 'jgszss',
				title: '籍贯',
				align: 'center',
				width: 120,
				sort: true
			}, {
				field: 'hjdz',
				title: '户籍地址',
				align: 'center',
				width: 200,
			}, {
				field: 'xjtzz',
				title: '现住地址',
				align: 'center',
				width: 200,
			},{
				field: 'isdy',
				title: '是否党员',
				align: 'center',
				width: 120,
				sort: true,
				templet: function (d) {
					return getDictDataLabel("YES_NO", d.isdy);
				}
			},{
				field: 'fby',
				title: '是否退伍军人',
				align: 'center',
				width: 150,
				sort: true,
				templet: function (d) {
					return getDictDataLabel("YES_NO", d.fby);
				}
			},{
				field: 'lxfs',
				width: 150,
				title: '联系方式',
				align: 'center',
				templet: function (d) {
					return String(d.lxfs);
				}
			},{
				field: 'rzrq',
				width: 150,
				title: '入职日期',
				align: 'center'
			},{
				field: 'xjtzz',
				width: 200,
				title: '现具体职责',
				align: 'center'
			}, {
				field: 'isbx',
				title: '由局购买保险',
				align: 'center',
				width: 150,
				sort: true,
				templet: function (d) {
					return getDictDataLabel("YES_NO", d.isbx);
				}
			},*/ {
				field: 'fjbh',
				title: '辅警编号',
				align: 'center',
				width: 200,
			},{
				field: 'fjType',
				title: '辅警类别',
				width: 180,
				align: "center",
				templet: function (d) {
					return getDictDataLabel("AUX_FJLB", d.fjType);
				}
			}, /*
				{
				field: 'mz',
				title: '民族',
				align: 'center',
				sort: true,
				width: 120,
				templet: function (d) {
					return getDictDataLabel("MZ", d.mz);
				}
			},{
				field: 'jz',
				title: '驾照',
				align: 'center',
				width: 80,
				sort: true,
				templet: function (d) {
					return getDictDataLabel("DRIVER_CARD", d.jz);
				}
			},{
				field: 'zzmm',
				title: '政治面貌',
				align: 'center',
				width: 120,
				sort: true,
				templet: function (d) {
					return getDictDataLabel("AM_ZZMM", d.zzmm);
				}
			},{
				field: 'byys',
				title: '毕业院校',
				align: 'center',
				width: 200,
			},{
				field: 'zy',
				title: '专业',
				align: 'center',
				width: 200,
			}, {
				field: 'xjtzz',
				title: '现家庭住址',
				align: 'center',
				width: 300,
			},  {
				field: 'hyzk',
				title: '婚姻状况',
				width: 120,
				align: 'center',
				sort: true,
				templet: function (d) {
					return getDictDataLabel("HYZK", d.hyzk);
				}
			},  {
				field: 'aihao',
				title: '爱好',
				align: 'center',
				width: 200,
			}, {
				field: 'techang',
				title: '特长',
				align: 'center',
				width: 200,
			},  {
				field: 'remark',
				title: '备注',
				width: 200,
				align: "center"
			},*/{
                fixed: 'right',
                title: '操作',
                toolbar: '#barDemo',
                width: 184
            }
            ]
        ],
        page: true,
        parseData: function (res) { //res 即为原始返回的数据
            return res;
        },
        height: 'full-151',
        response: {
            statusName: 'errCode', //规定数据状态的字段名称，默认：code
            statusCode: 0, //规定成功的状态码，默认：0
            msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
            countName: 'recordCount', //规定数据总数的字段名称，默认：count
            dataName: 'auxDaglList' //规定数据列表的字段名称，默认：data
        },
        request: {
            pageName: 'page', //页码的参数名称，默认：page
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
    });

	//头工具栏事件
	table.on('toolbar(test)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch (obj.event) {
			case 'addArchive':
				top.topTabAdd("添加辅警档案",
					'<div class="layui-body"><iframe name="auxiliary/addArchive.html?v=1.74" src="auxiliary/addArchive.html?v=1.74&iframeName='+window.name+'" frameborder="0" class="layadmin-iframe"></iframe></div>',
					'auxiliary/addArchive.html?v=1.74')

				/*top.topTabAdd("添加辅警档案",
					'<div class="layui-body"><iframe src="auxiliary/addArchive.html?v=1.74' + '" frameborder="0" class="layadmin-iframe"></iframe></div>',
					'auxiliary/addArchive.html?v=1.74')*/
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
				
			case 'importData':
				document.getElementById("importData").reset();
				layer.open({
					type: 1,
					title: '导入',
					area: ['800px', '620px'],
					content: $('#importDataPage'),
					btn: ['导入', '取消'],
					yes: function(index, layero) {
						$("#importDataSubmit").click();
					},
					btn2: function(index, layero) {
						layer.close(index);
					}
				});
				break;	
			case 'exportData':
				layer.open({
					type: 1,
					title: '导出',
					area: ['700px', '500'],
					content: $('#exportDataPage'),
					btn: ['取消'],
					btn2: function(index, layero) {
						layer.close(index);
					}
				});
				break;

			case 'del':
				var data = checkStatus.data;
				if (data.length == 0) {
					layer.msg('请先选择数据');
					return;
				}
				var ids = "";
				for (var i = 0; i < data.length; i++) {
					var myIds = data[i].daId;
					if (ids == '') {
						ids = ids + myIds;
					} else {
						ids = ids + "," + myIds;
					}
				}

				layer.confirm('确定删除选中的信息吗', function (index) {
					var layerIndex = layer.load();
					var url = servicePath + "/auxDagl/delete?charset=utf-8&loginCode=" + adminInfo.loginCode + "&ids=" + ids;
					$.ajax({
						type: "POST",
						url: url,
						contentType: "application/json; charset=utf-8",
						data: JSON.stringify(data),
						dataType: "json",
						success: function (data) {
							layer.close(layerIndex);
							amArchiveTable.reload();
							if (data.errCode == 0) {
								showTicket("删除成功", 1000);
							} else if (data.errCode == 200061 || data.errCode == 200062) {
								showTicket("请先登录！", 2000, function () {
									parent.location.href = "../index.html?v=1.60";
								});
							} else if (data.errCode == 200063) {
								showTicket("没有权限！", 2000);
							} else {
								showTicket(data.errMsg, 2000);
							}
						}
					})
				});
				break;

			case 'exportJbxxCard':
				var data = checkStatus.data;
				if(data.length!=1){
					layer.msg('请选中一条记录！');
					return;
				}
				//layer.msg('选中了：' + data.length + ' 个');
				window.open("amJbxxCard.html?daId="+data[0].daId,"_blank","fullscreen=1")
				break;

		};
	});

	//监听行工具事件
	table.on('tool(test)', function(obj) {
		var data = obj.data;
		//console.log(obj)

		if (obj.event === 'view') {
			top.topTabAdd("查看辅警档案", '<div class="layui-body"><iframe name="auxiliary/viewArchive.html?v=1.74&daId=' + data.daId +
				'" src="auxiliary/viewArchive.html?v=1.74&daId=' + data.daId +
				'" frameborder="0" class="layadmin-iframe"></iframe></div>', 'auxiliary/viewArchive.html?v=1.74&daId='+data.daId)
		}
		if (obj.event === 'editArchive') {
				top.topTabAdd("修改辅警档案",
					'<div class="layui-body"><iframe name="auxiliary/editArchive.html?v=1.74&daId=' + data.daId +
					'" src="auxiliary/editArchive.html?v=1.74&daId=' + data.daId +'&iframeName='+window.name+
					'" frameborder="0" class="layadmin-iframe"></iframe></div>',
					'auxiliary/editArchive.html?v=1.74&daId='+data.daId)
		}

		if (obj.event === 'del') {
			layer.confirm('确定删除人员档案吗？', function(index) {
				obj.del();
				var layerIndex = layer.load();
				var id=obj.data.daId;
				var url = servicePath + "/amJbxx/delete?charset=utf-8&loginCode=" + adminInfo.loginCode + "&id=" + id;
				$.ajax({
					type: "POST",
					url: url,
					contentType: "application/json; charset=utf-8",
					data: JSON.stringify(data),
					dataType: "json",
					success: function(data) {
						layer.close(layerIndex);
						if (data.errCode == 0) {
							
							showTicket("删除成功", 1000);
							/* showTicket("删除成功！", 2000, function() {
								obj.del();
							}); */
							layer.close(layerIndex);

						} else if (data.errCode == 200061 || data.errCode == 200062) {
							showTicket("请先登录！",2000,function(){
								parent.location.href="index.html?v=1.74";
							});
						} else if (data.errCode == 200063) {
							showTicket("没有权限！",2000);
						} else {
							showTicket(data.errMsg, 2000);
						}
					}
				})
				delGzxx(id);//工作
				delXlxx(id);//学历
				delNdxx(id);
				delYdxx(id);
				delQsxx(id);
				delSjqk(id);

				//obj.del();
				layer.close(index);
			});
		}
	});
	//删除工作信息
	function delGzxx(id){
		var url = servicePath + "/jobResume/delete?charset=utf-8&loginCode=" + adminInfo.loginCode + "&id=" + id;
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
			}
		})
	}
	//删除学历信息
	function delXlxx(id){
		var url = servicePath + "/eduResume/delete?charset=utf-8&loginCode=" + adminInfo.loginCode + "&id=" + id;
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
			}
		})
	}
	//删除年度信息
	function delNdxx(id){
		var url = servicePath + "/auxNdkh/delete?charset=utf-8&loginCode=" + adminInfo.loginCode + "&id=" + id;
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
			}
		})
	}
	//删除月度信息
	function delYdxx(id){
		var url = servicePath + "/auxYdkh/delete?charset=utf-8&loginCode=" + adminInfo.loginCode + "&id=" + id;
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
			}
		})
	}
	//删除亲属信息
	function delQsxx(id){
		var url = servicePath + "/auxQsgx/delete?charset=utf-8&loginCode=" + adminInfo.loginCode + "&id=" + id;
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
			}
		})
	}
	//删除授奖情况
	function delSjqk(id){
		var url = servicePath + "/auxSjqk/delete?charset=utf-8&loginCode=" + adminInfo.loginCode + "&id=" + id;
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
			}
		})
	}


	/*//删除家庭情况
	function delJtqk(id){
		var url = servicePath + "/amJtqk/delete?charset=utf-8&loginCode=" + adminInfo.loginCode + "&id=" + id;
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
			}
		})
	}
	//删除身体情况
	function delStqk(id){
		var url = servicePath + "/amStqk/delete?charset=utf-8&loginCode=" + adminInfo.loginCode + "&id=" + id;
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
			}
		})
	}
	//删除其他信息
	function delQtxx(id){
		var url = servicePath + "/amQtxx/delete?charset=utf-8&loginCode=" + adminInfo.loginCode + "&id=" + id;
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
			}
		})
	}
	//删除户籍信息
	function delHjxx(id){
		var url = servicePath + "/amHjxx/delete?charset=utf-8&loginCode=" + adminInfo.loginCode + "&id=" + id;
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
			}
		})
	}*/
	
	//清理临时查询
	function clean() {
		$.ajax({
			type: "GET",
			url: servicePath + "/amArchiveAdvSearch/clean?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
				if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！",2000,function(){
						parent.location.href="index.html?v=1.74";
					});
				}
			}
		})
	}
	

	//拖拽上传
	upload.render({
		elem: '#attachment',
		url: '/upload/',
		multiple: true,
		accept: 'file',
		done: function(res) {
			// console.log(res)
		}
	});

	//获取单位树
	function getAmUnitTree() {
		$.ajax({
			type: "POST",
			url: servicePath + "/amUnit/getAmUnitTree?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify({}),
			dataType: "json",
			success: function(data) {
				if (data.errCode == 0) {
					tree.render({
						elem: '#amUnitTree',
						data: data.orgTree,
						onlyIconControl: true,
						click: function(obj) {
							var formData = form.val("searchForm");
							var whereDate={};
							if(formData.onlyCurrnetOrg=="true"){
								whereDate={"orgCode": obj.data.orgCode,"orgId": null};
								myOrgId=null;//定义导出条件orgId
							}else{
								whereDate={"orgCode": null,"orgId": obj.data.id};
								myOrgId=obj.data.id;//定义导出条件orgId
							}
							amArchiveTable.reload({
								where: whereDate
							});
						}
					});
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！",2000,function(){
						parent.location.href="index.html?v=1.74";
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
	$(".state").each(function(){
		addDictDataToSelect(this,"AUX_DASTATE");
	})
	/*$(".mz").each(function(){
		  addDictDataToSelect(this,"MZ");
	}) */
	/*$(".state").each(function(){
		  addDictDataToSelect(this,"AM_STATE",["16"]);
	})*/
	$(".fjType").each(function () {
		addDictDataToSelect(this, "AUX_FJLB");
	})
	form.render('select');

	//搜索人员信息
	function search() {
		var formData = form.val("searchForm");
		var data = {
		/*	xb: formData.xb,
			xm: formData.xm,
			idcard: formData.idcard,
/!*			mz: formData.mz,*!/
			state: formData.state*/

			orgName: formData.orgName,
			orgId: formData.orgId,
			state: formData.state,
			idcard: formData.idcard,
			fjbh:formData.fjbh,
			xm:formData.xm

		}
		amArchiveTable.reload({
			url: servicePath + "/auxDagl/search?charset=utf-8&loginCode=" + adminInfo.loginCode,
			where: data
		});
	}
	
	function advSearch(expressionId){
		amArchiveTable.reload({
			url: servicePath + "/amJbxx/advSearch?charset=utf-8&loginCode=" + adminInfo.loginCode,
			where: {"expressionId":expressionId}
		});
	}
	//单独查询亲属关系
	function searchRelation() {
		var formData = form.val("searchForm");
		var data = {
			idcard: formData.idcard
		}
		table.render({
			elem: '#test',
			url: servicePath + "/relation/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,
			method: 'POST',
			contentType: "application/json; charset=utf-8",
			where: data,
			toolbar: '#toolbarRelation' //开启头部工具栏，并为其绑定左侧模板
			,
			defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
				title: '提示',
				layEvent: 'LAYTABLE_TIPS',
				icon: 'layui-icon-tips'
			}],
			title: '亲属关系信息数据表',
			cols: [
				[{
					type: 'checkbox',
					fixed: 'center'
				}, {
					field: 'name',
					title: '姓名',
					minWidth:150
				}, {
					field: 'kinshipTerm',
					title: '称谓',
					width:80,
					sort: true,
					templet: function(d){
						return getDictDataLabel("APPELLATION",d.kinshipTerm);
					}
				}
					/* ,{field:'mz', title:'族别', sort: true} */
					, {
					field: 'ownIdcard',
					title: '身份证号',
					width:180,
					sort: true
				}, {
					field: 'birthday',
					title: '出生日期',
					minWidth: "120",
					align:'center',
					templet: function(d) {
						var birthday=d.birthday+"";
						return formatDate8To10(birthday)
					}
				}
					, {
					field: 'workUnit',
					title: '工作单位',
					minWidth: "150",
					sort: true
				}
					, {
					field: 'post',
					minWidth: "150",
					title: '职务',
				}, {
					field: 'eduLevel',
					title: '学历',
					minWidth:120,
					templet: function(d){
						return getDictDataLabel("AM_XL",d.eduLevel);
					}
				}, {
					field: 'nowStatus',
					title: '现状',
					minWidth:120,
					templet: function(d){
						return getDictDataLabel("AM_REALTION_NOW_STATUS",d.nowStatus);
					}
				}
					, {
					field: 'healthState',
					title: '健康状况',
					minWidth:120/* ,
					templet: function(d){
						return getDictDataLabel("AM_HEALTH_STATE",d.healthState);
					} */
				}
					, {
					field: 'others',
					title: '其他',
					minWidth:150
				}
				]
			],
			page: false, /* 不分页 */
			parseData: function(res) { //res 即为原始返回的数据
				return res;
			},
			data:[],
			height: 'full-205',
			response: {
				statusName: 'errCode', //规定数据状态的字段名称，默认：code
				statusCode: 0, //规定成功的状态码，默认：0
				msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
				countName: 'recordCount',  //规定数据总数的字段名称，默认：count
				dataName: 'relationList' //规定数据列表的字段名称，默认：data
			},
			request: {
				pageName: 'page', //页码的参数名称，默认：page
				limitName: 'pageSize' //每页数据量的参数名，默认：limit
			}
		});
	}
	
	//搜索人员“查询”按钮触发
	form.on('submit(searchBtn)', function(data) {
		search()
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});
	//搜索人员“高级查询”按钮触发
	form.on('submit(advSearchBtn)', function(data) {
		clean();
		top.openRyAdvSearch({
		url:'amArchiveAdvSearch.html',
		title:'人员高级搜索',
		resource:'/amArchiveAdvSearch/searchCriteria',
		where:{expressionId:0},
		treeWhere:{},
		area:['1024px','768px']
	},window.name,function(returnData,layerIndex){
			if(returnData.success==true){
				advSearch(returnData.expressionId);
			}
			top.layer.close(layerIndex);
		});
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});
	
	/* 下载模板 */
	$("#downloadJbxx").click(function(){//基本信息
		 window.location.href = servicePath +  "/auxDagl/download?charset=utf-8&loginCode=" + adminInfo.loginCode+"&downloadType=0";
	});
	$("#downloadGzjl").click(function(){//工作简历
		window.location.href = servicePath +  "/auxDagl/download?charset=utf-8&loginCode=" + adminInfo.loginCode+"&downloadType=1";
	});

	$("#downloadXljl").click(function(){
		window.location.href = servicePath +  "/auxDagl/download?charset=utf-8&loginCode=" + adminInfo.loginCode+"&downloadType=2";
	});

	$("#downloadQsgx").click(function(){
		 window.location.href = servicePath +  "/auxDagl/download?charset=utf-8&loginCode=" + adminInfo.loginCode+"&downloadType=3";
	});

	/*$("#downloadHtxx").click(function(){
		 window.location.href = servicePath +  "/auxDagl/download?charset=utf-8&loginCode=" + adminInfo.loginCode+"&downloadType=4";
	});*/
	
	/* 导出选择 */
	$("#exportJbxx").click(function(){//基本信息
		exportFunction(0);
	});
	$("#exportQsgx").click(function(){//亲属关系
		exportFunction(1);
	});
	$("#exportGzjl").click(function(){//工作简历
		exportFunction(2);
	});
	$("#exportXljl").click(function(){//学历简历
		exportFunction(3);
	});
	/*$("#exportHtxx").click(function(){
		exportFunction(4);
	});*/
	
	//导出excel方法
	function exportFunction(exportType){
		var msg="基本信息";
		if(exportType==1){
			msg="亲属关系信息";
		}else if(exportType==2){
			msg="工作简历信息";
		}else if(exportType==3){
			msg="学历简历信息";
		}/*else if(exportType==4){
			msg="合同信息";
		}*/
		layer.confirm('确定要导出'+msg+'吗？', function(index) {
			var layerIndex = layer.load();
			var formData = form.val("searchForm");
			var data = {
				xb: formData.xb,
				xm: formData.xm,
				idcard: formData.idcard,
				mz: formData.mz,
				state: formData.state,
				orgId: formData.orgId
				/*orgId: myOrgId*/
			}
			var url = servicePath + "/auxDagl/export?charset=utf-8&loginCode=" + adminInfo.loginCode
								+"&exportType="+exportType;
			$.ajax({
				type: "POST",
				url: url,
				contentType: "application/json; charset=utf-8",
				data: JSON.stringify(data),
				dataType: "json",
				success: function(data) {
					layer.close(layerIndex);
					if (data.code == "0") {
						showTicket("导出成功", 1000);
						window.location.href = servicePath + "/common/download?charset=utf-8&loginCode=" 
								+ adminInfo.loginCode+"&fileName=" + encodeURI(data.msg) + "&delete=" + true;
						layer.close(layerIndex);
					} else if (data.errCode == 200061 || data.errCode == 200062) {
						showTicket("请先登录！",2000,function(){
							parent.location.href="index.html?v=1.74";
						});
					} else if (data.errCode == 200063) {
						showTicket("没有权限！",2000);
					} else {
						showTicket(data.errMsg, 2000);
					}
				}
			})
			layer.close(index);
		});
	}
	
	//导入基本信息“保存”按钮触发
	form.on('submit(importData)', function(data) {
		importData();
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});
	//导入基本信息
	function importData() {
		var layerIndex = layer.load();
		if($("#file")[0].files[0]==undefined){
			layer.close(layerIndex);
			showTicket("请先选择文件！", 1500);
			return false;
		}
		var formdata=new FormData()
		formdata.append('file',$("#file")[0].files[0])
		var importType=$('input[name="importType"]:checked').val();
		var isJbxxCover=$('input[name="isJbxxCover"]:checked').val();
		var isDelete=$('input[name="isDelete"]:checked').val();
		$.ajax({
			type: "POST",
			url: servicePath + "/auxDagl/import?charset=utf-8&loginCode=" + adminInfo.loginCode
					+"&isJbxxCover="+isJbxxCover+"&importType="+importType+"&isDelete="+isDelete,
			processData:false, //告诉jQuery不要去处理发送的数据
			contentType:false,// 告诉jQuery不要去设置Content-Type请求头
			data:formdata,
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					showTicket("执行完毕！", 1000, function() {
						layer.alert(data.msg)
						layer.closeAll('page');
						document.getElementById("importData").reset();
						amArchiveTable.reload();
					});
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！",2000,function(){
						parent.location.href="index.html?v=1.74";
					});
				} else if (data.errCode == 200063) {
					showTicket("没有权限！",2000);
				} else {
					showTicket(data.errMsg, 2000);
				}
	
			}
		})
	}

	//设置单位下拉列表框
	function setAmUnitDropList() {
		$.ajax({
			type: "POST",
			url: servicePath + "/amUnit/search?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify({
				"page": 1,
				"pageSize": 1000
			}),
			dataType: "json",
			success: function (data) {
				if (data.errCode == 0) {
					var orgList = data.orgList;
					for (var i = 0; i < orgList.length; i++) {
						$(".orgId").each(function () {
							seladditem(orgList[i].orgId, orgList[i].orgName, this);
						})
					}
					form.render('select');
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！", 2000, function () {
						parent.location.href = "index.html?v=1.59";
					});
				}
			}
		})
	}

	//设置单位下拉列表框
	setAmUnitDropList();



});

window.onload = function() {

}


