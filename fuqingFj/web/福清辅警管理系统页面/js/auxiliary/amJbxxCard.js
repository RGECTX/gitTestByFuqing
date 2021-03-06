var daId = getParameter('daId');
layui.use(['form', 'table', 'jquery'], function() {
	var table = layui.table;
	var form = layui.form;
	var $ = layui.jquery;
	var jbxxIdList=[];
	
	function exportJbxxCard() {
		var url = servicePath + "/auxDagl/exportJbxxCard?charset=utf-8&loginCode=" + adminInfo.loginCode+"&daId="+daId;
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify({"daId":daId}),
			dataType: "json",
			success: function(data) {
				if (data.errCode == 0) {
					var auxDagl=data.auxDagl;
					var jobResumeList=data.jobResumeList;
					var eduResumeList=data.eduResumeList;
					/*var amHjxx=data.amHjxx;
					var amGzxx=data.amGzxx;
					var amJtqk=data.amJtqk;
					var amStqk=data.amStqk;*/
					$("#xm").text(auxDagl.xm||"");//姓名
					$("#xb").text(getDictDataLabel("SEX", auxDagl.xb));//性别
					$("#idcard").text(auxDagl.idcard||"");//身份证号
					/*$("#csrq").text(formatDate8To10(amJbxx.csrq+''));//出生日期*/
					$("#mz").text(getDictDataLabel("MZ", auxDagl.mz));//民族
					$("#jgszss").text(auxDagl.jgszss||"");//籍贯
					$("#hjdz").text(auxDagl.hjdz||"");//户籍地
					$("#lxfs").text(auxDagl.lxfs||"");//联系方式
					$("#hyzk").text(getDictDataLabel("HYZK", auxDagl.hyzk));//婚姻状况
					/*if(amGzxx.cjgzrq!=null){
						$("#cjgzrq").text(formatDate8To10(amGzxx.cjgzrq+'') );//参加工作时间
					}*/
					$("#zzmm").text(getDictDataLabel("AM_ZZMM", auxDagl.zzmm));//政治面貌
					$("#jz").text(getDictDataLabel("DRIVER_CARD", auxDagl.jz));//驾照
					$("#fjType").text(getDictDataLabel("AUX_FJLB", auxDagl.fjType));//辅警类别
					$("#isbx").text(getDictDataLabel("YES_NO", auxDagl.isbx));//由局购买保险
					/*$("#zy").text(amJbxx.zy);//专业*/
					/*if(amJbxx.techang!=null){
						$("#techang").text(amJbxx.techang);//特长
					}*/
					if(auxDagl.byrq!=null){
						$("#byrq").text(formatDate10To8(auxDagl.byrq+'') );//毕业日期
					}
					$("#zy").text(auxDagl.zy||"");//专业
					$("#byys").text(auxDagl.byys||"");//毕业院校
					$("#xjtzz").text(auxDagl.xjtzz||"");//现住地址

					/*$("#xl").text(getDictDataLabel("AM_XL", amJbxx.xl));//学历
					$("#byys").text(amJbxx.byys+"（"+amJbxx.zy+"）");//毕业院校及专业
					$("#orgId").text(data.orgName+"  "+amGzxx.zwmc);//工作单位及职务*/

					var xljl="";//学历简历
					for(var j=0;j<eduResumeList.length;j++){
						var edu=eduResumeList[j];
						xljl=xljl+"毕业院校："+ edu.university+"、专业：" +edu.specialitie+
							"、学习日期："+ formatDate8To10(edu.startDate+'')+"~"+ formatDate8To10(edu.endDate+'')+"、学历："+getDictDataLabel("AM_XL", edu.eduLevel)+"、学位："+getDictDataLabel("AM_XW", edu.degree)+"<br/>";
					}
					$("#xljl").html(xljl);
					
					var gzjl="";//工作简历
					for(var i=0;i<jobResumeList.length;i++){
						var jobResume=jobResumeList[i];
						gzjl=gzjl+"工作日期："+ formatDate8To10(jobResume.startDate+'') +" ~ "+ formatDate8To10(jobResume.endDate+'') +"   "
							+jobResume.workUnit+" "+jobResume.post+"<br/>";
					}
					$("#gzjl").html(gzjl);

					$("#remark").text(auxDagl.remark);//现住地址

					
					
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
	
	exportJbxxCard();
	
	$("#printBtn").click(function(){
		$("#printBtn").hide();
		window.print();
	});
	
	
});

