// 辅警总人数
$.ajax({
 	type: "POST",
 	url: servicePath + "/amKshPt/people?charset=utf-8&loginCode="+adminInfo.loginCode,
 	contentType: "application/json; charset=utf-8",
 	success: function(data) {
		$("#people").text(data[0].peopleCount)
	}
 })
 // 辅警减员人数
 $.ajax({
  	type: "POST",
  	url: servicePath + "/amKshPt/reducePeople?charset=utf-8&loginCode="+adminInfo.loginCode,
  	contentType: "application/json; charset=utf-8",
  	success: function(data) {
 		$("#reducePeople").text(data[0].peopleCount)
 	}
  })
 // 疆内、疆外、男、女
 $.ajax({
  	type: "POST",
  	url: servicePath + "/amKshPt/peopleStructure?charset=utf-8&loginCode="+adminInfo.loginCode,
  	contentType: "application/json; charset=utf-8",
  	success: function(data) {
		$("#peopleWithin").text(data.peopleWithin)
		$("#peopleOuter").text(data.peopleOuter)
		$("#peopleMan").text(data.peopleMan)
		$("#peopleWoman").text(data.peopleWoman)
 	}
  })
 // 疆内外环比、男女环比
 $.ajax({
  	type: "POST",
  	url: servicePath + "/amKshPt/comparative?charset=utf-8&loginCode="+adminInfo.loginCode,
  	contentType: "application/json; charset=utf-8",
  	success: function(data) {
 		$("#manWoman").text(data.manWonan)
 		$("#withinOuter").text(data.withinOuter)
 	}
  })

//    实时新增辅警列表
$.ajax({
	type: "POST",
	url: servicePath + "/amKshPt/todaysNew?charset=utf-8&loginCode="+adminInfo.loginCode,
	contentType: "application/json; charset=utf-8",
	success: function(data) {
		// console.log(data)
		for(var i=0;i<data.length;i++){
			var str = data[i].xm+"-"+data[i].nl+"-"+data[i].strXb+"-"+data[i].jgszss+"-"+getOrgName(data[i].orgId);
			$("#jrxzfjlb").append('<li class="clearfix"><span>'+str+'</span></li>')
		}

		
	}
})

//    实时实时减员记录
$.ajax({
	type: "POST",
	url: servicePath + "/amKshPt/depletion?charset=utf-8&loginCode="+adminInfo.loginCode,
	contentType: "application/json; charset=utf-8",
	success: function(data) {
		for(var i=0;i<data.length;i++){
			$("#jyjl").append('<li><p>'+data[i].xm+"-"+data[i].strJyyy+"-"+data[i].strCreateTime+"-"+getOrgName(data[i].orgId)+'</p></li>')
		}
	}
})
  
function activeSeven(){
	$("#seven").attr("class","active");
	$("#fifteen").removeAttr("class");
	$("#thirtyDays").removeAttr("class");
	$("#fifteenShow").hide();
	$("#thirtyDaysShow").hide();
	$("#sevenShow").show();
	
}

function activeFifteen(){
	$("#seven").removeAttr("class");
	$("#fifteen").attr("class","active");
	$("#thirtyDays").removeAttr("class");
	$("#sevenShow").hide();
	$("#fifteenShow").show();
	$("#thirtyDaysShow").hide();
}

function activeSevenThirtyDays(){
	$("#seven").removeAttr("class");
	$("#fifteen").removeAttr("class");
	$("#thirtyDays").attr("class","active");
	$("#sevenShow").hide();
	$("#fifteenShow").hide();
	$("#thirtyDaysShow").show();
}








