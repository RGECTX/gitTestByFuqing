var title = "工资统计表";
layui.use(['form', 'table', "upload", 'laydate'], function() {
	var $ = layui.jquery;
	var table = layui.table;
	var form = layui.form;
	var laydate = layui.laydate;

	/*laydate.render({
		elem: '#startDate',
		type: 'date',
		value: getCurrentMonthFirst(),
		isInitValue: true
	});
	laydate.render({
		elem: '#endDate',
		type: 'date',
		value: getCurrentMonthLast(),
		isInitValue: true
	});*/

	var orgWhere = {
		orgId: adminInfo.amUnitId,
		state: 1
	};
	if (adminInfo.loginName === "652301350000453" || adminInfo.loginName === "admin") {
		orgWhere = {
			state: 1
		};
	}
	//选择单位
	$("#orgNames").click(function() {
		top.openSelectOrg({
			title: '选择单位',
			/*where: orgWhere,*/
			where:{},
			treeWhere: orgWhere,
			area: ['1024px', '768px'],
			page: true,
			limit: 10,
			limits: [10, 50, 100, 1000, 10000],
			selectType: 'checkbox', //radio,checkbox
			checkedData: [], //默认选中的数据，只对selectType为radio起作用
			disabledData: [], //默认禁用的数据
			compareField: 'id' //用于识别选中或禁用的字段
		}, window.name, function(returnData, layerIndex) {
			var orgNameList = [];
			var orgIdList = [];
			for (var i = 0; i < returnData.length; i++) {
				orgNameList.push(returnData[i].orgName);
				orgIdList.push(returnData[i].orgId);
			}
			$("#orgNames").val(orgNameList);
			$("#orgIds").val(orgIdList);
			top.layer.close(layerIndex);
		});
	});

	/*$(".nd").each(function () {
		var ndList = getYearArr(2019, null);
		for (var i = 0; i < ndList.length; i++) {
			seladditem(ndList[i], ndList[i], this);
		}
	})
	$(".yd").each(function () {
		for (var i = 1; i <= 12; i++) {
			seladditem(i, i, this);
		}
	})*/

	/*//年度选择事件
	form.on('select(ndEdit)', function (data) {
		var cDate = dateToString(new Date());
		var cWeek = getWeek(cDate);
		$(".zdEdit").each(function () {
			var lastWeek = getWeek(data.value + "-12-31");
			$(this).find("option:first").nextAll().remove();
			for (var i = 1; i <= lastWeek; i++) {
				seladditem(i, i, this);
			}
		})
		form.render('select');
	});*/



	//搜索用户“查询”按钮触发
	form.on('submit(searchBtn)', function(data) {
		var formData = form.val("searchForm");
		//获取checkbox[name='state']的值


		/*var arr = new Array();
		$("input:checkbox[name='state']:checked").each(function(i){
			arr[i] = $(this).val();
		});
		formData.ryStates = arr.join(",");//将数组合并成字符串
		delete formData["state"]; //删除key

		if (formData.ryStates === "") {
			alert("请选择需要查询的人员状态");
			return false;
		}*/

		search(formData)
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});

	function search(formData) {
		var url = servicePath + "/auxGzgl/getFjqktj?charset=utf-8&loginCode=" + adminInfo.loginCode;
		var layerIndex = layer.load();
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(formData),
			dataType: "json",
			success: function(data) {
				layer.close(layerIndex);
				if (data.errCode == 0) {
					document.getElementById("title").innerHTML = title;
					$("#tbodyId").empty();
					$("#tbodyId").append(
						'<tr class="cls-ws-n">' +
						'<th rowspan="2">序号</th>' +
						'<th rowspan="2">所在单位</th>' +
						'<th rowspan="2">姓名</th>' +
						/*'<th rowspan="2">状态</th>' +*/
						'<th rowspan="2">上报日期</th>' +
						'<th rowspan="2">审批状态</th>' +
						'<th rowspan="2">应发工资</th>' +
						/*'<th rowspan="2">年度</th>' +
						'<th rowspan="2">月度</th>' +
						'<th rowspan="2">缺勤</br>天数</th>' +
						'<th rowspan="2">身份证号</th>' +*/
					/*	'<th colspan="2">月度</th>' +
						'<th colspan="4">年龄</th>' +
						'<th colspan="5">学历</th>' +
						'<th colspan="3">政治面貌</th>' +
						'<th colspan="3">公安机关工作时间</th>' +
						'<th colspan="2">从事岗位</th>' +
						'<th colspan="2">招聘方式</th>' +*/
						/*'<th colspan="3">经费保障来源</th>' +*/
						'</tr>' +
						'<tr class="cls-ws-n">' +
						/*'<th class="lock">男</th>' +
						'<th class="lock">女</th>' +
						'<th class="lock">30岁</br>以下</th>' +
						'<th class="lock">31-40</br>岁</th>' +
						'<th class="lock">41-50</br>岁</th>' +
						'<th class="lock">51岁</br>以上</th>' +
						'<th class="lock">初中及</br>以下</th>' +
						'<th class="lock">高中</th>' +
						'<th class="lock">大专</th>' +
						'<th class="lock">本科</th>' +
						'<th class="lock">研究生</br>以上</th>' +
						'<th class="lock">中共党</br>员</th>' +
						'<th class="lock">共青团</br>员</th>' +
						'<th class="lock">群众</th>' +
						'<th class="lock">3年</br>以下</th>' +
						'<th class="lock">4-9年</th>' +
						'<th class="lock">10年</br>以上</th>' +
						'<th class="lock">文职</th>' +
						'<th class="lock">勤务</th>' +
						'<th class="lock">统一</br>招聘</th>' +
						'<th class="lock">用人单</br>位自行</br>招聘</th>' +*/
						/*'<th class="lock">市级</br>财政</th>' +
						'<th class="lock">单位</br>自筹</th>' +
						'<th class="lock">乡镇</br>财政</th>' +*/
						'</tr>'
					);
					var qqts = 0;
					var cntSum = 0;
					var sex1Sum = 0;
					var sex2Sum = 0;
					var age1Sum = 0;
					var age2Sum = 0;
					var age3Sum = 0;
					var age4Sum = 0;
					var xl896Sum = 0;
					var xl112Sum = 0;
					var xl12Sum = 0;
					var xl3Sum = 0;
					var xl1024Sum = 0;
					var zzmm3Sum = 0;
					var zzmm4Sum = 0;
					var zzmm8Sum = 0;
					var workAge1Sum = 0;
					var workAge2Sum = 0;
					var workAge3Sum = 0;
					var gwlb001Sum = 0;
					var gwlb002Sum = 0;
					var zpfs1Sum = 0;
					var zpfs2Sum = 0;
					var jfly1Sum = 0;
					var jfly2Sum = 0;
					var jfly4Sum = 0;
					$.each(data.auxGzglSearchCriteriaList, function(index, auxGzglSearchCriteriaList) {
						$("#tbodyId").append('<tr>' +
							'<td>' + (index + 1) + '</td>' +
							'<td>' + (auxGzglSearchCriteriaList.orgName || '') + '</td>' +
							'<td>' + (auxGzglSearchCriteriaList.xm || 0) + '</td>' +
							/*'<td>' + (amAttendanceSearchCriteria.isWorkFullHours || 0) + '</td>' +*/
							/*'<td>' + (auxGzglSearchCriteriaList.xb == 1 ? '男' : '女') + '</td>' +*/
							/*'<td>' + (auxGzglSearchCriteriaList.state == 1 ? '<font color=blue >在岗</font>' : '<font color=red >离职</font>') + '</td>' +*/
							'<td>' + (auxGzglSearchCriteriaList.sbDate || 0) + '</td>' +
							'<td>' + (auxGzglSearchCriteriaList.sbState == 4 ? '<font color=blue >通过</font>' : '<font color=blue >未通过</font>') + '</td>' +
							'<td>' + (auxGzglSearchCriteriaList.yfgz || 0) + '</td>' +
							/*'<td>' + (auxGzglSearchCriteriaList.nd || 0) + '</td>' +
							'<td>' + (auxGzglSearchCriteriaList.yd || 0) + '</td>' +
							'<td>' + (auxGzglSearchCriteriaList.qqTs || 0) + '</td>' +
							'<td>' + (auxGzglSearchCriteriaList.idcard || 0) + '</td>' +*/
						/*	'<td>' + (amAttendanceSearchCriteria.age3 || 0) + '</td>' +
							'<td>' + (amAttendanceSearchCriteria.age4 || 0) + '</td>' +
							'<td>' + (amAttendanceSearchCriteria.xl896 || 0) + '</td>' +
							'<td>' + (amAttendanceSearchCriteria.xl112 || 0) + '</td>' +
							'<td>' + (amAttendanceSearchCriteria.xl12 || 0) + '</td>' +
							'<td>' + (amAttendanceSearchCriteria.xl3 || 0) + '</td>' +
							'<td>' + (amAttendanceSearchCriteria.xl1024 || 0) + '</td>' +
							'<td>' + (amAttendanceSearchCriteria.zzmm3 || 0) + '</td>' +
							'<td>' + (amAttendanceSearchCriteria.zzmm4 || 0) + '</td>' +
							'<td>' + (amAttendanceSearchCriteria.zzmm8 || 0) + '</td>' +
							'<td>' + (amAttendanceSearchCriteria.workAge1 || 0) + '</td>' +
							'<td>' + (amAttendanceSearchCriteria.workAge2 || 0) + '</td>' +
							'<td>' + (amAttendanceSearchCriteria.workAge3 || 0) + '</td>' +
							'<td>' + (amAttendanceSearchCriteria.gwlb001 || 0) + '</td>' +
							'<td>' + (amAttendanceSearchCriteria.gwlb002 || 0) + '</td>' +
							'<td>' + (amAttendanceSearchCriteria.zpfs1 || 0) + '</td>' +
							'<td>' + (amAttendanceSearchCriteria.zpfs2 || 0) + '</td>' +
							'<td>' + (amAttendanceSearchCriteria.jfly1 || 0) + '</td>' +
							'<td>' + (amAttendanceSearchCriteria.jfly2 || 0) + '</td>' +
							'<td>' + (amAttendanceSearchCriteria.jfly4 || 0) + '</td>' +
							'<td>' + (amAttendanceSearchCriteria.jfly4 || 0) + '</td>' +*/
							'</tr>')

						qqts += Number(auxGzglSearchCriteriaList.qqTs);
						cntSum += Number(auxGzglSearchCriteriaList.xm);
						sex2Sum += Number(auxGzglSearchCriteriaList.sex2);
						age1Sum += Number(auxGzglSearchCriteriaList.age1);
						age2Sum += Number(auxGzglSearchCriteriaList.age2);
						age3Sum += Number(auxGzglSearchCriteriaList.age3);
						age4Sum += Number(auxGzglSearchCriteriaList.age4);
						xl896Sum += Number(auxGzglSearchCriteriaList.xl896);
						xl112Sum += Number(auxGzglSearchCriteriaList.xl112);
						xl12Sum += Number(auxGzglSearchCriteriaList.xl12);
						xl3Sum += Number(auxGzglSearchCriteriaList.xl3);
						xl1024Sum += Number(auxGzglSearchCriteriaList.xl1024);
						zzmm3Sum += Number(auxGzglSearchCriteriaList.zzmm3);
						zzmm4Sum += Number(auxGzglSearchCriteriaList.zzmm4);
						zzmm8Sum += Number(auxGzglSearchCriteriaList.zzmm8);
						workAge1Sum += Number(auxGzglSearchCriteriaList.workAge1);
						workAge2Sum += Number(auxGzglSearchCriteriaList.workAge2);
						workAge3Sum += Number(auxGzglSearchCriteriaList.workAge3);
						gwlb001Sum += Number(auxGzglSearchCriteriaList.gwlb001);
						gwlb002Sum += Number(auxGzglSearchCriteriaList.gwlb002);
						zpfs1Sum += Number(auxGzglSearchCriteriaList.zpfs1);
						zpfs2Sum += Number(auxGzglSearchCriteriaList.zpfs2);
						jfly1Sum += Number(auxGzglSearchCriteriaList.jfly1);
						jfly2Sum += Number(auxGzglSearchCriteriaList.jfly2);
						jfly4Sum += Number(auxGzglSearchCriteriaList.jfly4);
					})
					$("#tbodyId").append('<tr>' +
						'<td colspan="2">合计</td>' +
						'<td>' + " " + '</td>' +
						'<td>' + " " + '</td>' +
						'<td>' + " " + '</td>' +
						/*'<td>' + " " + '</td>' +*/
						'<td>' + " " + '</td>' +
						/*'<td>' + " " + '</td>' +*/
					/*	'<td>' + " " + '</td>' +
						'<td>' + qqts + '</td>' +
						'<td>' + " " + '</td>' +*/
						/*'<td>' + xl896Sum + '</td>' +
						'<td>' + xl112Sum + '</td>' +
						'<td>' + xl12Sum + '</td>' +
						'<td>' + xl3Sum + '</td>' +
						'<td>' + xl1024Sum + '</td>' +
						'<td>' + zzmm3Sum + '</td>' +
						'<td>' + zzmm4Sum + '</td>' +
						'<td>' + zzmm8Sum + '</td>' +
						'<td>' + workAge1Sum + '</td>' +
						'<td>' + workAge2Sum + '</td>' +
						'<td>' + workAge3Sum + '</td>' +
						'<td>' + gwlb001Sum + '</td>' +
						'<td>' + gwlb002Sum + '</td>' +
						'<td>' + zpfs1Sum + '</td>' +
						'<td>' + zpfs2Sum + '</td>' +
						'<td>' + jfly1Sum + '</td>' +
						'<td>' + jfly2Sum + '</td>' +
						'<td>' + jfly4Sum + '</td>' +
						'<td>' + jfly4Sum + '</td>' +
						'<td>' + jfly4Sum + '</td>' +*/
						'</tr>');
					eachFunc(); //表格td变色
				} else if (data.errCode == 200061 || data.errCode == 200062) {
					showTicket("请先登录！", 2000, function() {
						parent.location.href = "index.html?v=1.60";
					});
				} else if (data.errCode == 200063) {
					showTicket("没有权限！", 2000);
				} else {
					showTicket(data.errMsg, 2000);
				}
			}
		})
	}

	/* td变色方法 */
	function eachFunc() {
		$('#tableId tr').click(function() {
			var trs = $("table#tableId tr");
			var index = trs.index($(this).closest("tr"));

			if (index != 0) { //第一行不要改变横向颜色
				$(this).addClass('selected') //为选中项添加高亮
					.siblings().removeClass('selected') //去除其他项的高亮形式
					.end();
			}
		});

		$('table tr td').click(function() {
			$(".colorClass").each(function() {
				$(this).removeClass('selected1')
			});

			var myName = $(this).attr("name");
			$("[name='" + myName + "']").each(function() {
				$(this).addClass('selected1')
			});

		});
	}

})

//导出excel表格
function exportFunc() {
	//导出excel
	// 使用outerHTML属性获取整个table元素的HTML代码（包括<table>标签），然后包装成一个完整的HTML文档，设置charset为urf-8以防止中文乱码
	var html = "<html><head><meta charset='utf-8' /></head><body>" + document.getElementsByTagName("table")[0].outerHTML +
		"</body></html>";
	// 实例化一个Blob对象，其构造函数的第一个参数是包含文件内容的数组，第二个参数是包含文件类型属性的对象
	var blob = new Blob([html], {
		type: "application/vnd.ms-excel"
	});
	var a = document.getElementById("aId");
	// 利用URL.createObjectURL()方法为a元素生成blob URL
	a.href = URL.createObjectURL(blob);
	// 设置文件名
	a.download = title + ".xls";
}

/* 获取当前月份的第一天 */
function getCurrentMonthFirst() {
	var date = new Date();
	date.setDate(1);
	var month = parseInt(date.getMonth() + 1);
	var day = date.getDate();
	if (month < 10) {
		month = '0' + month
	}
	if (day < 10) {
		day = '0' + day
	}
	return date.getFullYear() + '-' + month + '-' + day;
}

/* 获取当前月份的最后一天 */
function getCurrentMonthLast() {
	var date = new Date();
	var currentMonth = date.getMonth();
	var nextMonth = ++currentMonth;
	var nextMonthFirstDay = new Date(date.getFullYear(), nextMonth, 1);
	var oneDay = 1000 * 60 * 60 * 24;
	var lastTime = new Date(nextMonthFirstDay - oneDay);
	var month = parseInt(lastTime.getMonth() + 1);
	var day = lastTime.getDate();
	if (month < 10) {
		month = '0' + month
	}
	if (day < 10) {
		day = '0' + day
	}
	return date.getFullYear() + '-' + month + '-' + day;
}
