var id = getParameter('id');
layui.use(['form', 'table', 'laydate', 'element', 'upload'], function() {
	var $ = layui.jquery;
	var laydate = layui.laydate;
	var table = layui.table;
	var form = layui.form;
	var element = layui.element;
	var upload = layui.upload;

	var daId = getParameter("daId");

	$(".khjl").each(function() {
		addDictDataToSelect(this, "AUX_NDKH");
	})

	$(".kinshipTerm").each(function() {
		addDictDataToSelect(this, "APPELLATION");
	})

	$(".nowStatus").each(function() {
		addDictDataToSelect(this, "AM_REALTION_NOW_STATUS");
	})

	$(".healthState").each(function() {
		addDictDataToSelect(this, "AM_HEALTH_STATE");
	})

	$(".kjysgbFlag").each(function() {
		addDictDataToSelect(this, "KJYSGB_FLAG");
	})

	$(".cgjFlag").each(function() {
		addDictDataToSelect(this, "CGJ_FLAG");
	})

	$(".fzFlag").each(function() {
		addDictDataToSelect(this, "FZ_FLAG");
	})
	$(".sysjFlag").each(function() {
		addDictDataToSelect(this, "SYSJ_FLAG");
	})
	$(".rhflFlag").each(function() {
		addDictDataToSelect(this, "RHFL_FLAG");
	})

	$(".bjgzFlag").each(function() {
		addDictDataToSelect(this, "BJGZ_FLAG");
	})

	$(".gzddssfw").each(function() {
		addDictDataToSelect(this, "DDSSFW");
	})

	$(".jzddssfw").each(function() {
		addDictDataToSelect(this, "DDSSFW");
	})

	$(".fjType").each(function () {
		addDictDataToSelect(this, "AUX_FJLB");
	})
	$(".xb").each(function () {
		addDictDataToSelect(this, "SEX");
	})
	$(".state").each(function () {
		addDictDataToSelect(this, "AUX_DASTATE");
	})

	//=========基本信息start===================
	/*$(".xb").each(function() {
		addDictDataToSelect(this, "SEX");
	})*/
	$(".mz").each(function() {
		addDictDataToSelect(this, "MZ");
	})
	$(".jz").each(function() {
		addDictDataToSelect(this, "DRIVER_CARD");
	})
	$(".fby").each(function() {
		addDictDataToSelect(this, "YES_NO");
	})
	$(".isdy").each(function () {
		addDictDataToSelect(this, "YES_NO");
	})
	$(".isbx").each(function () {
		addDictDataToSelect(this, "YES_NO");
	})
	$(".hyzk").each(function() {
		addDictDataToSelect(this, "HYZK");
	})
	$(".zzmm").each(function() {
		addDictDataToSelect(this, "AM_ZZMM");
	})
	$(".xl").each(function() {
		addDictDataToSelect(this, "AM_XL");
	})
	$(".hjdlx").each(function() {
		addDictDataToSelect(this, "AM_HJDLX");
	})
	$(".rylb").each(function() {
		addDictDataToSelect(this, "AM_RYLB");
	})
	/*$(".state").each(function() {
		addDictDataToSelect(this, "AM_STATE");
	})*/
	$(".zj").each(function() {
		addDictDataToSelect(this, "AM_ZJ");
	})
	$(".gwlb").each(function() {
		addDictDataToSelect(this, "AM_GWLB");
	})
	$(".gwjb").each(function() {
		addDictDataToSelect(this, "AM_GWJB");
	})
	$(".xj").each(function() {
		addDictDataToSelect(this, "AM_XJ");
	})
	$(".zpfs").each(function() {
		addDictDataToSelect(this, "AM_ZPFS");
	})
	$(".jfly").each(function() {
		addDictDataToSelect(this, "AM_JFLY");
	})
	/*$(".jx").each(function() {
		addDictDataToSelect(this, "AM_JX");
	})*/
	$(".xx").each(function() {
		addDictDataToSelect(this, "BLOOD_TYPE");
	})
	$(".zwmc").each(function() {
		addDictDataToSelect(this, "AM_ZFZ");
	})
	$(".eduLevel").each(function() {
		addDictDataToSelect(this, "AM_XL");
	})
	// 学历简历
	$(".degree").each(function() {
		addDictDataToSelect(this, "AM_XW");
	})
	// 合同信息
	$(".contractCategory").each(function() {
		addDictDataToSelect(this, "CONTRACT_CATEGORY");
	})
	$(".contractState").each(function() {
		addDictDataToSelect(this, "CONTRACT_STATE");
	})
	//年度考核

	//受奖情况
	$(".sjlb").each(function () {
		addDictDataToSelect(this, "AUX_SJLB");
	})

	form.render('select');

	//日期选择器
	lay('.date').each(function() {
		laydate.render({
			elem: this,
			format: 'yyyy-MM-dd',
			trigger: 'click'
		});
	});
	lay('.month').each(function() {
		laydate.render({
			elem: this,
			type: 'month',
			format: 'yyyyMM',
			trigger: 'click'
		});
	});
	lay('.year').each(function() {
		laydate.render({
			elem: this,
			type: 'year',
			trigger: 'click'
		});
	});

	//设置单位下拉列表框
	setAmUnitDropList();

	/*getJbxxObject();*/
	getGzxxObject();
	getAttachmentList("AuxDagl", daId);

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
						getJbxxObject();
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

	/*//设置单位下拉列表框
	function setAmUnitDropList() {
		$.ajax({
			type: "POST",
			url: servicePath + "/amUnit/search?charset=utf-8&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify({
				"orgLevel": 2,
				"page": 1,
				"pageSize": 1000
			}),
			dataType: "json",
			success: function(data) {
				if (data.errCode == 0) {
					var orgList = data.orgList;
					for (var i = 0; i < orgList.length; i++) {
						seladditem(orgList[i].orgId, orgList[i].orgName, document.getElementById("jdOrgId"));
					}
					form.render('select');
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！", 2000, function() {
						parent.location.href = "index.html?v=1.60";
					});
				}
			}
		})
	}*/

	//查看基本信息
	function getJbxxObject() {
		var url = servicePath + "/auxDagl/getDagl?charset=utf-8&loginCode=" + adminInfo.loginCode + "&daId=" + daId;
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify({
				"daId": daId
			}),
			dataType: "json",
			success: function(data) {
				form.val("view", data.auxDagl);
			}
		})
	}


	//查看工作信息
	function getGzxxObject() {
		var url = servicePath + "/amGzxx/findByJbxxId?charset=utf-8&loginCode=" + adminInfo.loginCode + "&jbxxId=" + id;
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
				form.val("updateGzxx", {
					id: data.amGzxx.id,
					zwmc: data.amGzxx.zwmc,
					zj: data.amGzxx.zj,
					gwlb: data.amGzxx.gwlb,
					gwjb: data.amGzxx.gwjb,
					xj: data.amGzxx.xj,
					jh: data.amGzxx.jh,
					// jx: data.amGzxx.jx,
					jrdtrq: formatDate8To10(data.amGzxx.jrdtrq + ""),
					cjgarq: formatDate8To10(data.amGzxx.cjgarq + ""),
					cjgzrq: formatDate8To10(data.amGzxx.cjgzrq + ""),
					rzrq: formatDate8To10(data.amGzxx.rzrq + ""),
					zjqsrq: formatDate8To10(data.amGzxx.zjqsrq + ""),
					gwtzjl: data.amGzxx.gwtzjl,
					gzkh: data.amGzxx.gzkh,
					khhmc: data.amGzxx.khhmc,
					sbjf: data.amGzxx.sbjf,
					zfgjjjf: data.amGzxx.zfgjjjf,
					zpfs: data.amGzxx.zpfs,
					jfly: data.amGzxx.jfly
				})
				form.render("select");
			}
		})
	}
	//=========基本信息end===================

	 //=========亲属关系start===================
	var relationTable = table.render({
		elem: '#relationElem',
		url: '',
		method: 'POST',
		contentType: "application/json; charset=utf-8",
		where: {},
		toolbar: '#toolbarRelation' ,//开启头部工具栏，并为其绑定左侧模板
		defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
			title: '提示',
			layEvent: 'LAYTABLE_TIPS',
			icon: 'layui-icon-tips'
		}],
		title: '亲属关系数据表',
		cols: [
			[{
					type: 'numbers',
					fixed: 'center',
					title: '序号'
				}, {
					field: 'qsname',
					title: '姓名',
					minWidth: 150
				}, {
					field: 'kinshipTerm',
					title: '称谓',
					width: 80,
					sort: true,
					templet: function(d) {
						return getDictDataLabel("APPELLATION", d.kinshipTerm);
					}
				}
				, {
					field: 'ownIdcard',
					title: '身份证号',
					width: 180,
					sort: true
				}, {
					field: 'birthdayStr',
					title: '出生日期',
					minWidth: "120",
					align: 'center'/*,
					templet: function(d) {
						var birthday = d.birthday + "";
						return formatDate8To10(birthday)
					}*/
				}, {
					field: 'workUnit',
					title: '工作单位',
					minWidth: "150",
					sort: true
				}, {
					field: 'post',
					minWidth: "150",
					title: '职务',
				}, {
					field: 'eduLevel',
					title: '学历',
					minWidth: 120,
					templet: function(d) {
						return getDictDataLabel("AM_XL", d.eduLevel);
					}
				}, {
					field: 'nowStatus',
					title: '现状',
					minWidth: 120,
					templet: function(d) {
						return getDictDataLabel("AM_REALTION_NOW_STATUS", d.nowStatus);
					}
				}, {
					field: 'healthState',
					title: '健康状况',
					minWidth: 120,
					templet: function(d) {
						return getDictDataLabel("AM_HEALTH_STATE", d.healthState);
					}
				}, {
					field: 'others',
					title: '其他',
					minWidth: 150
				}, {
					fixed: 'right',
					title: '操作',
					toolbar: '#barRelation',
					width: 80
				}
			]
		],
		page: false,
		parseData: function(res) { //res 即为原始返回的数据
			return res;
		},
		data: [],
		height: 'full-205',
		response: {
			statusName: 'errCode', //规定数据状态的字段名称，默认：code
			statusCode: 0, //规定成功的状态码，默认：0
			msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
			countName: 'recordCount', //规定数据总数的字段名称，默认：count
			dataName: 'auxQsgxList' //规定数据列表的字段名称，默认：data
		},
		request: {
			pageName: 'page', //页码的参数名称，默认：page
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		}
	});
	//亲属关系头工具栏事件
	table.on('toolbar(relationElem)', function(obj) {});
	//亲属关系、监听行工具事件
	table.on('tool(relationElem)', function(obj) {
		var data = obj.data;
		if (obj.event === 'viewQsgx') {

			data.bjOrgName=getOrgName(data.bjOrgId)
			form.val("viewQsgxForm", data);
			change(1,editGzdd);
			change(1,editJzdd);
			form.val("viewQsgxForm", data);
			change(2,editGzdd);
			change(2,editJzdd);



			form.val("viewQsgxForm", data);
			layer.open({
				type: 1,
				title: '查看亲属关系',
				area: ['800px', '500px'],
				content: $('#viewQsgxPage'),
				btn: ['关闭'],
				yes: function(index, layero) {
					layer.close(index);
				}
			});
		}
	});
	//=========亲属关系end===================

	//=========受奖情况start===================
	var auxSjqkTable = table.render({
		elem: '#sjqkElem',
		/*url: servicePath + "/relation/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,*/
		url: '',
		method: 'POST',
		contentType: "application/json; charset=utf-8",
		where: {},
		toolbar: '#toolbarSjqk',//开启头部工具栏，并为其绑定左侧模板
		defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
			title: '提示',
			layEvent: 'LAYTABLE_TIPS',
			icon: 'layui-icon-tips'
		}],
		title: '受奖情况信息数据表',
		cols: [
			[{
				/*type: 'checkbox',
                fixed: 'center'*/
				type: 'numbers',
				fixed: 'center',
				title: '序号'
			}, {
				field: 'sjrq',
				title: '受奖日期',
				width: 120,
				align: 'center',
				templet: function (d) {
					return formatDate8To10(d.sjrq + "");
				}
			}, {
				field: 'sjlb',
				title: '受奖类别',
				width: 120,
				align: 'center',
				templet: function (d) {
					return getDictDataLabel("AUX_SJLB", d.sjlb);
				}
			}, {
				field: 'sjmc',
				title: '受奖名称',
				width: 120,
				align: 'center'
			}, {
				field: 'sjyy',
				title: '受奖原因',
				width: 120,
				align: 'center'
			}, {
				field: 'pzdw',
				title: '批准单位',
				width: 120,
				align: 'center'
			}, {
				fixed: 'right',
				title: '操作',
				toolbar: '#barSjqk',
				minWidth: "130"
			}
			]
		],
		page: false,
		parseData: function (res) { //res 即为原始返回的数据
			return res;
		},
		data: [],
		height: 'full-205',
		response: {
			statusName: 'errCode', //规定数据状态的字段名称，默认：code
			statusCode: 0, //规定成功的状态码，默认：0
			msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
			countName: 'recordCount',  //规定数据总数的字段名称，默认：count
			dataName: 'auxSjqkList' //规定数据列表的字段名称，默认：data
		},
		request: {
			pageName: 'page', //页码的参数名称，默认：page
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		}
	});

	//受奖情况头工具栏事件
	table.on('toolbar(sjqkElem)', function(obj) {});
	//受奖情况监听行工具事件
	table.on('tool(sjqkElem)', function(obj) {
		var data = obj.data;
		if (obj.event === 'viewSjqk') {
			form.val("viewSjqkForm", data);
			sjqkgetAttachmentList(data.id);
			layer.open({
				type: 1,
				title: '查看受奖情况',
				area: ['800px', '500px'],
				content: $('#viewSjqkPage'),
				btn: ['关闭'],
				yes: function(index, layero) {
					layer.close(index);
				}
			});
		}
	});

	//=========工作简历start===================
	var jobResumeTable = table.render({
		elem: '#jobResumeElem',
		url: '',
		method: 'POST',
		contentType: "application/json; charset=utf-8",
		where: {},
		toolbar: '#toolbarJobResume', //开启头部工具栏，并为其绑定左侧模板
		defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
			title: '提示',
			layEvent: 'LAYTABLE_TIPS',
			icon: 'layui-icon-tips'
		}],
		title: '工作简历数据表',
		cols: [
			[{
				type: 'numbers',
				fixed: 'center',
				title: '序号'
			}, {
				field: 'workUnit',
				title: '工作单位',
				width: 200,
				sort: true
			}, {
				field: 'post',
				title: '职务',
				width: 120
			}, {
				field: 'startDate',
				title: '开始日期',
				width: 120,
				align: 'center',
				templet: function(d) {
					return formatDate8To10(d.startDate + "");
				}
			}, {
				field: 'endDate',
				title: '结束日期',
				align: 'center',
				width: 120,
				templet: function(d) {
					return formatDate8To10(d.endDate + "");
				}
			}, {
				field: 'remark',
				title: '备注',
				minWidth: 150
			}, {
				fixed: 'right',
				title: '操作',
				toolbar: '#barJobResume',
				width: 80
			}]
		],
		page: false,
		parseData: function(res) { //res 即为原始返回的数据
			return res;
		},
		data: [],
		height: 'full-205',
		response: {
			statusName: 'errCode', //规定数据状态的字段名称，默认：code
			statusCode: 0, //规定成功的状态码，默认：0
			msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
			countName: 'recordCount', //规定数据总数的字段名称，默认：count
			dataName: 'jobResumeList' //规定数据列表的字段名称，默认：data
		},
		request: {
			pageName: 'page', //页码的参数名称，默认：page
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		}
	});
	//工作简历头工具栏事件
	table.on('toolbar(jobResumeElem)', function(obj) {});
	//工作简历、监听行工具事件
	table.on('tool(jobResumeElem)', function(obj) {
		var data = obj.data;
		if (obj.event === 'viewJobResume') {
			data.startDate = formatDate8To10(data.startDate + "");
			data.endDate = formatDate8To10(data.endDate + "");
			form.val("viewJobResumeForm", data);
			layer.open({
				type: 1,
				title: '查看工作简历',
				area: ['800px', '500px'],
				content: $('#viewJobResumePage'),
				btn: ['关闭'],
				yes: function(index, layero) {
					layer.close(index);
				}
			});
		}
	});
	//=========工作简历end===================

	//=========学历简历start===================
	var eduResumeTable = table.render({
		elem: '#eduResumeElem',
		url: '',
		method: 'POST',
		contentType: "application/json; charset=utf-8",
		where: {},
		toolbar: '#toolbarEduResume', //开启头部工具栏，并为其绑定左侧模板
		defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
			title: '提示',
			layEvent: 'LAYTABLE_TIPS',
			icon: 'layui-icon-tips'
		}],
		title: '学历简历数据表',
		cols: [
			[{
				type: 'numbers',
				fixed: 'center',
				title: '序号'
			}, {
				field: 'university',
				title: '毕业院校',
				width: 150,
				sort: true
			}, {
				field: 'specialitie',
				title: '专业',
				width: 120
			}, {
				field: 'startDate',
				title: '开始日期',
				align: 'center',
				width: 120,
				templet: function(d) {
					return formatDate8To10(d.startDate + "")
				}
			}, {
				field: 'endDate',
				title: '结束日期',
				align: 'center',
				width: 120,
				templet: function(d) {
					return formatDate8To10(d.endDate + "")
				}
			}, {
				field: 'eduLevel',
				title: '学历',
				align: 'center',
				width: 120,
				sort: true,
				templet: function(d) {
					return getDictDataLabel("AM_XL", d.eduLevel);
				}
			}, {
				field: 'degree',
				title: '学位',
				align: 'center',
				width: 120,
				sort: true,
				templet: function(d) {
					return getDictDataLabel("AM_XW", d.degree);
				}
			}, {
				field: 'remark',
				title: '备注',
				minWidth: 150
			}, {
				fixed: 'right',
				title: '操作',
				toolbar: '#barEduResume',
				width: 80
			}]
		],
		page: false,
		parseData: function(res) { //res 即为原始返回的数据
			return res;
		},
		data: [],
		height: 'full-205',
		response: {
			statusName: 'errCode', //规定数据状态的字段名称，默认：code
			statusCode: 0, //规定成功的状态码，默认：0
			msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
			countName: 'recordCount', //规定数据总数的字段名称，默认：count
			dataName: 'eduResumeList' //规定数据列表的字段名称，默认：data
		},
		request: {
			pageName: 'page', //页码的参数名称，默认：page
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		}
	});
	//学历简历头工具栏事件
	table.on('toolbar(eduResumeElem)', function(obj) {});
	//学历简历、监听行工具事件
	table.on('tool(eduResumeElem)', function(obj) {
		var data = obj.data;
		if (obj.event === 'viewEduResume') {
			data.startDate = formatDate8To10(data.startDate + "");
			data.endDate = formatDate8To10(data.endDate + "");
			form.val("viewEduResumeForm", data);
			getEduAttachmentList(data.id);
			layer.open({
				type: 1,
				title: '查看学历简历',
				area: ['800px', '500px'],
				content: $('#viewEduResumePage'),
				btn: ['关闭'],
				yes: function(index, layero) {
					layer.close(index);
				}
			});
		}

	});
	//=========学历简历end===================

	//=========年度考核start===================
	var amNdkhTable = table.render({
		elem: '#amNdkhElem',
		url: '',
		method: 'POST',
		contentType: "application/json; charset=utf-8",
		where: {},
		toolbar: '#toolbarAmNdkh', //开启头部工具栏，并为其绑定左侧模板
		defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
			title: '提示',
			layEvent: 'LAYTABLE_TIPS',
			icon: 'layui-icon-tips'
		}],
		title: '年度考核数据表',
		cols: [
			[{
				type: 'numbers',
				fixed: 'center',
				title: '序号'
			}, {
				field: 'khnd',
				title: '考核年度',
				width: 120
			}, {
				field: 'khjl',
				title: '考核结论',
				width: 120,
				sort: true,
				templet: function(d) {
					return getDictDataLabel("AUX_NDKH", d.khjl);
				}
			}, {
				field: 'remark',
				title: '备注',
				minWidth: 200
			}, {
				fixed: 'right',
				title: '操作',
				toolbar: '#barAmNdkh',
				width: 80
			}]
		],
		page: false,
		parseData: function(res) { //res 即为原始返回的数据
			return res;
		},
		data: [],
		height: 'full-205',
		response: {
			statusName: 'errCode', //规定数据状态的字段名称，默认：code
			statusCode: 0, //规定成功的状态码，默认：0
			msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
			countName: 'recordCount', //规定数据总数的字段名称，默认：count
			dataName: 'amNdkhList' //规定数据列表的字段名称，默认：data
		},
		request: {
			pageName: 'page', //页码的参数名称，默认：page
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		}
	});
	//年度考核头工具栏事件
	table.on('toolbar(amNdkhElem)', function(obj) {});
	//年度考核、监听行工具事件
	table.on('tool(amNdkhElem)', function(obj) {
		var data = obj.data;
		if (obj.event === 'viewAmNdkh') {
			form.val("viewAmNdkhForm", data);
			layer.open({
				type: 1,
				title: '查看年度考核',
				area: ['800px', '500px'],
				content: $('#viewAmNdkhPage'),
				btn: ['关闭'],
				yes: function(index, layero) {
					layer.close(index);
				}
			});
		}
	});
	//=========年度考核end===================

	//=========月度考核start===================
	var amYdkhTable = table.render({
		elem: '#amYdkhElem',
		url: '',
		method: 'POST',
		contentType: "application/json; charset=utf-8",
		where: {},
		toolbar: '#toolbarAmYdkh', //开启头部工具栏，并为其绑定左侧模板
		defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
			title: '提示',
			layEvent: 'LAYTABLE_TIPS',
			icon: 'layui-icon-tips'
		}],
		title: '月度考核数据表',
		cols: [
			[{
				type: 'numbers',
				fixed: 'center',
				title: '序号'
			}, {
				field: 'khyd',
				title: '考核月度',
				width: 120
			}, {
				field: 'khjl',
				title: '考核结论',
				width: 120,
				sort: true,
				templet: function(d) {
					return getDictDataLabel("AUX_NDKH", d.khjl);
				}
			}, {
				field: 'remark',
				title: '备注',
				minWidth: 200
			}, {
				fixed: 'right',
				title: '操作',
				toolbar: '#barAmYdkh',
				width: 80
			}]
		],
		page: false,
		parseData: function(res) { //res 即为原始返回的数据
			return res;
		},
		data: [],
		height: 'full-205',
		response: {
			statusName: 'errCode', //规定数据状态的字段名称，默认：code
			statusCode: 0, //规定成功的状态码，默认：0
			msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
			countName: 'recordCount', //规定数据总数的字段名称，默认：count
			dataName: 'amYdkhList' //规定数据列表的字段名称，默认：data
		},
		request: {
			pageName: 'page', //页码的参数名称，默认：page
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		}
	});
	//月度考核头工具栏事件
	table.on('toolbar(amYdkhElem)', function(obj) {});
	//月度考核、监听行工具事件
	table.on('tool(amYdkhElem)', function(obj) {
		var data = obj.data;
		if (obj.event === 'viewAmYdkh') {
			form.val("viewAmYdkhForm", data);
			layer.open({
				type: 1,
				title: '查看月度考核',
				area: ['800px', '500px'],
				content: $('#viewAmYdkhPage'),
				btn: ['关闭'],
				yes: function(index, layero) {
					layer.close(index);
				}
			});
		}
	});
	//=========月度考核end===================

	/* //=========合同信息start===================
	var contractTable = table.render({
		elem: '#contractElem',
		url: '',
		method: 'POST',
		contentType: "application/json; charset=utf-8",
		where: {},
		toolbar: '#toolbarContract', //开启头部工具栏，并为其绑定左侧模板
		defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
			title: '提示',
			layEvent: 'LAYTABLE_TIPS',
			icon: 'layui-icon-tips'
		}],
		title: '合同信息数据表',
		cols: [
			[{
					field: 'category',
					title: '合同类别',
					minWidth: "150",
					sort: true,
					templet: function(d) {
						return getDictDataLabel("CONTRACT_CATEGORY", d.category);
					}
				}, {
					field: 'contractNo',
					title: '合同编号',
					minWidth: "120"
				},
				{
					field: 'contractName',
					title: '合同名称',
					minWidth: "160",
					sort: true
				}, {
					field: 'state',
					title: '合同状态',
					minWidth: "120",
					templet: function(d) {
						return getDictDataLabel("CONTRACT_STATE", d.state);
					}
				}, {
					field: 'signDate',
					title: '签订日期',
					align: 'center',
					minWidth: "120",
					templet: function(d) {
						var signDate = d.signDate + "";
						return formatDate8To10(signDate)
					}
				}, {
					field: 'effectiveDate',
					title: '生效日期',
					align: 'center',
					minWidth: "120",
					templet: function(d) {
						var effectiveDate = d.effectiveDate + "";
						return formatDate8To10(effectiveDate)
					}
				}, {
					field: 'terminationDate',
					title: '终止日期',
					align: 'center',
					minWidth: "120",
					templet: function(d) {
						var terminationDate = d.terminationDate + "";
						return formatDate8To10(terminationDate)
					}
				}, {
					field: 'expirationDate',
					title: '失效日期',
					align: 'center',
					minWidth: "120",
					templet: function(d) {
						var expirationDate = d.expirationDate + "";
						return formatDate8To10(expirationDate)
					}
				},
				{
					field: 'reason',
					title: '终止原因',
					minWidth: "150",
					sort: true
				}, {
					field: 'remark',
					title: '备注',
					minWidth: "150"
				}
			]
		],
		page: false,
		parseData: function(res) { //res 即为原始返回的数据
			return res;
		},
		data: [],
		height: 'full-205',
		response: {
			statusName: 'errCode', //规定数据状态的字段名称，默认：code
			statusCode: 0, //规定成功的状态码，默认：0
			msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
			countName: 'recordCount', //规定数据总数的字段名称，默认：count
			dataName: 'contractList' //规定数据列表的字段名称，默认：data
		},
		request: {
			pageName: 'page', //页码的参数名称，默认：page
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		}
	});
	//合同信息头工具栏事件
	table.on('toolbar(contractElem)', function(obj) {});
	//合同信息、监听行工具事件
	table.on('tool(contractElem)', function(obj) {
		var data = obj.data;
		if (obj.event === 'viewContract') {
			data.startDate = formatDate8To10(data.startDate + "");
			data.endDate = formatDate8To10(data.endDate + "");
			form.val("viewContractForm", data);
			layer.open({
				type: 1,
				title: '查看合同信息',
				area: ['800px', '600px'],
				content: $('#viewContractPage'),
				btn: ['关闭'],
				yes: function(index, layero) {
					layer.close(index);
				}
			});
		}
	});
	//=========合同信息end=================== */

	/* //=========被装信息start===================
	var amBzxxTable = table.render({
		elem: '#amBzxxElem',
		url: '',
		method: 'POST',
		contentType: "application/json; charset=utf-8",
		where: {},
		toolbar: '#toolbarAmBzxx', //开启头部工具栏，并为其绑定左侧模板
		defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
			title: '提示',
			layEvent: 'LAYTABLE_TIPS',
			icon: 'layui-icon-tips'
		}],
		title: '被装信息数据表',
		cols: [
			[{
				field: 'commodityName',
				title: '被装名称',
				minWidth: "150",
				sort: true
			}, {
				field: 'commodityType',
				title: '装备类别',
				width: 150,
				sort: true,
				templet: function(d) {
					return getDictDataLabel("COMMODITY_TYPE", d.commodityType);
				}
			}, {
				field: 'skuCode',
				title: '规格',
				minWidth: "120"
			}, {
				field: 'unit',
				title: '单价',
				minWidth: "120"
			}]
		],
		page: false,
		parseData: function(res) { //res 即为原始返回的数据
			return res;
		},
		data: [],
		height: 'full-205',
		response: {
			statusName: 'errCode', //规定数据状态的字段名称，默认：code
			statusCode: 0, //规定成功的状态码，默认：0
			msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
			countName: 'recordCount', //规定数据总数的字段名称，默认：count
			dataName: 'amBzxxList' //规定数据列表的字段名称，默认：data
		},
		request: {
			pageName: 'page', //页码的参数名称，默认：page
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		}
	});
	//=========被装信息end=================== */

	//关闭页面
	$(document).on('click', '#closeAdmin', function() {
		top.topTabDelete("auxiliary/viewArchive.html?v=1.60");
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});

	//Tab选项卡点击切换时触发
	element.on('tab(tabFilter)', function(data) {
		if (data.index == 1) { //工作简历
			jobResumeTable.reload({
				url: servicePath + "/jobResume/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,
				where: {
					/*instanceId: $("#id").val()*/
					instanceId: daId
				}
			});
		} else if (data.index == 2) { //学历简历
			eduResumeTable.reload({
				url: servicePath + "/eduResume/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,
				where: {
					/*instanceId: $("#id").val()*/
					instanceId: daId
				}
			});
		} else if (data.index == 3) { //年度考核
			amNdkhTable.reload({
				url: servicePath + "/auxNdkh/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,
				where: {
					/*instanceId: $("#id").val()*/
					instanceId: daId
				}
			});
		} else if (data.index == 4) { //月度考核
			amYdkhTable.reload({
				url: servicePath + "/auxYdkh/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,
				where: {
					/*instanceId: $("#id").val()*/
					instanceId: daId
				}
			});
		} else if (data.index == 5) { //亲属关系
			relationTable.reload({
				url: servicePath + "/auxQsgx/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,
				where: {
					/*instanceId: $("#id").val()*/
					instanceId: daId
				}
			});
		} else if (data.index == 6) { //受奖情况
			auxSjqkTable.reload({
				url: servicePath + "/auxSjqk/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,
				where: {
					/*instanceId: $("#id").val()*/
					instanceId: daId
				}
			});
		}
		/*else if(data.index==1){//亲属关系
			relationTable.reload({
				url:servicePath + "/relation/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,
				where:{
				  instanceId:$("#id").val()
				}
			});
		} else if(data.index==4){//合同信息
			contractTable.reload({
				url:servicePath + "/contract/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,
				where:{
				  instanceId:$("#id").val()
				}
			});
		}else if(data.index==5){//被装信息
			amBzxxTable.reload({
				url:servicePath + "/amBzxx/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,
				where:{
				  instanceId:$("#id").val()
				}
			});
		} */
	});

/*	var addGzdd = ["add_gzddSheng", "add_gzddShi", "add_gzddXian"]; //三个select的id
	var addJzdd = ["add_jzddSheng", "add_jzddShi", "add_jzddXian"]; //三个select的id*/
	var editGzdd = ["edit_gzddSheng", "edit_gzddShi", "edit_gzddXian"]; //三个select的id
	var editJzdd = ["edit_jzddSheng", "edit_jzddShi", "edit_jzddXian"]; //三个select的id
/*	initArea(addGzdd, 'addGzdd');
	initArea(addJzdd, 'addJzdd');*/
	initArea(editGzdd, 'editGzdd');
	initArea(editJzdd, 'editJzdd');

	//获取附件列表
	function getAttachmentList(instanceType,instanceId) {
		var layerIndex = layer.load();
		$.ajax({
			type: "GET",
			url: servicePath + "/file/getAttachmentList?charset=utf-8&instanceType="+instanceType+"&instanceId="+instanceId+"&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					$("#view_uploadList").html("");
					var attachmentList=data.attachmentList;
					if (attachmentList.length > 0) {
						// 人员照片
						$('.imgRen').attr('src', servicePath + '/' + attachmentList[0].filePath)
					}
					for(var i=0;i<attachmentList.length;i++){
						var tr = $(['<tr>'
						  ,'<td><a href="'+servicePath+'/'+attachmentList[i].filePath+'" target="_blank">'+ attachmentList[i].fileName +'</a></td>'
						  ,'<td>'+ (attachmentList[i].fileSize/1014).toFixed(1) +'kb</td>'
						  ,'<td>已存在</td>'
						 /* ,'<td>'
							,'<button class="layui-btn layui-btn-xs viewFile layui-hide" attachmentId="'+attachmentList[i].id+'">查看</button>'
						  ,'</td>'*/
						,'</tr>'].join(''));
						$("#view_uploadList").append(tr);
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

	//获取附件列表查看
	function sjqkgetAttachmentList(instanceId) {
		var layerIndex = layer.load();
		$.ajax({
			type: "GET",
			url: servicePath + "/file/getAttachmentList?charset=utf-8&instanceType=AuxSjqk&instanceId="+instanceId+"&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					$("#view_sjqk_uploadList").html("");
					var attachmentList=data.attachmentList;
					for(var i=0;i<attachmentList.length;i++){
						var tr = $(['<tr>'
							,'<td><a href="'+servicePath+'/'+attachmentList[i].filePath+'" target="_blank">'+ attachmentList[i].fileName +'</a></td>'
							,'<td>'+ (attachmentList[i].fileSize/1014).toFixed(1) +'kb</td>'
							,'<td>已存在</td>'
							/*,'<td>'
							,'<button class="layui-btn layui-btn-xs viewFile layui-hide" attachmentId="'+attachmentList[i].id+'">查看</button><button class="layui-btn layui-btn-xs layui-btn-danger deleteFile" attachmentId="'+attachmentList[i].id+'">删除</button>'
							,'</td>'*/
							,'</tr>'].join(''));
						$("#view_sjqk_uploadList").append(tr);
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

	//学历简历获取附件列表查看
	function getEduAttachmentList(instanceId) {
		var layerIndex = layer.load();
		$.ajax({
			type: "GET",
			url: servicePath + "/file/getAttachmentList?charset=utf-8&instanceType=EduResume&instanceId="+instanceId+"&loginCode=" + adminInfo.loginCode,
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					$("#view_edu_uploadList").html("");
					var attachmentList=data.attachmentList;
					for(var i=0;i<attachmentList.length;i++){
						var tr = $(['<tr>'
							,'<td><a href="'+servicePath+'/'+attachmentList[i].filePath+'" target="_blank">'+ attachmentList[i].fileName +'</a></td>'
							,'<td>'+ (attachmentList[i].fileSize/1014).toFixed(1) +'kb</td>'
							,'<td>已存在</td>'
							/*,'<td>'
							,'<button class="layui-btn layui-btn-xs viewFile layui-hide" attachmentId="'+attachmentList[i].id+'">查看</button><button class="layui-btn layui-btn-xs layui-btn-danger deleteFile" attachmentId="'+attachmentList[i].id+'">删除</button>'
							,'</td>'*/
							,'</tr>'].join(''));
						$("#view_edu_uploadList").append(tr);
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


});

//关闭当前tab页面
/*function closeTab() {
	var id = getParameter('id');
	top.topTabDelete("am/viewArchive.html?v=1.60&id=" + id);
}*/

function closeTab() {
	var daId = getParameter("daId");
	top.topTabDelete("auxiliary/viewArchive.html?v=1.74&daId=" + getParameter('daId'));
}
