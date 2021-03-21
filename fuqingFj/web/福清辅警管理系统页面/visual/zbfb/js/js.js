 $(window).load(function(){$(".loading").fadeOut()})  
$(function () {
//获取占比分布全部数据
function getZbfb(){
	var orgId=getParameter("orgId");
	var url = servicePath + "/amReport/getZbfb?charset=utf-8&loginCode=" + adminInfo.loginCode+"&orgId="+orgId;
	$.ajax({
		type: "POST",
		url: url,
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify({ "id": 1}),
		dataType: "json",
		success: function(data) {
			//console.log(data.fUnit)
			// console.log(data)
			//var zldpcList=data.zldpcList;
			//var dpcSize=data.dpcSize;
			setFjs(data);
			
			echarts_1(data);//人员类别
			echarts_2(data);//民族
			echarts_3(data);//学历
			echarts_4(data);//年龄
			echarts_5(data);//招录批次
			echarts_6(data);//性别
			zb1(data);//中共党员
			zb2(data);//预备党员
			zb3(data);//共青团员
			zb4(data);//群众
		}
	})
}	
getZbfb();

//设置市局总辅警数，部门总辅警数
function setFjs(zbData) {
	var sjNumb=zbData.allNumb;
	var bmNumb=zbData.allUnitNumb.fjs;
	var titleStr=zbData.allUnitNumb.orgName;
	$("#titleId").text(titleStr+"辅警占比可视化图表");//设置标题
	$("#sjNumb").text(sjNumb);
	$("#bmNumb").text(bmNumb);
}
	
//人员类别	
function echarts_1(zbData) {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('echart1'));
	var allUnitNumb=zbData.allUnitNumb;
	var allFjs=zbData.allUnitNumb.fjs;
	option = {
		tooltip : {
			trigger: 'item',
			formatter:function(a){
				var percent=Math.round( a.data.value/allFjs*100)+ '%'
				return a.data.name+"："+a.data.value+"（"+percent+"）";
			}
			/* formatter: "{b} : {c} ({d}%)" */
		},
		legend: {
			right:10,
			top:30,
			height:140,
			itemWidth:10,
			itemHeight:10,
			itemGap:10,
			textStyle:{
				color: 'rgba(255,255,255,.6)',
				fontSize:12
			},
			orient:'vertical',
			data:['事业性岗位','巡逻队员','视频监看员','交通协管岗','辅警','转岗协管']
		},
	   calculable : true,
		series : [
			{
				name:' ',
				color: ['#62c98d', '#2f89cf', '#4cb9cf', '#53b666', '#62c98d', '#205acf', '#c9c862', '#c98b62', '#c962b9', '#7562c9','#c96262'],	
				type:'pie',
				radius : [30, 70],
				center : ['35%', '50%'],
				roseType : 'radius',
				label: {
					normal: {
						show: true
					},
					emphasis: {
						show: true
					}
				},

				lableLine: {
					normal: {
						show: false
					},
					emphasis: {
						show: true
					}
				},

				data:[
					{value:allUnitNumb.syxgw, name:'事业性岗位'},
					{value:allUnitNumb.xldy, name:'巡逻队员'},
					{value:allUnitNumb.spjky, name:'视频监看员'},
					{value:allUnitNumb.jtxgy, name:'交通协管岗'},
					{value:allUnitNumb.fujing, name:'辅警'},
					{value:allUnitNumb.zgfj, name:'转岗协警'}
				]
			},
		]
	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
	window.addEventListener("resize",function(){
		myChart.resize();
	});
}
//民族
function echarts_2(zbData) {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('echart2'));
	var allUnitNumb=zbData.allUnitNumb;
	var allFjs=zbData.allUnitNumb.fjs;
	//alert(allFjs);
	option = {
		tooltip : {
			trigger: 'item',
			/* formatter: "{b} : {c} ({d}%)", */
			formatter:function(a){
				//console.log(a.data);
				//console.log(a.data.name);
				/* var relVal = "";
				relVal = a[0]+"°C<br/>";
				relVal += a[1]+"°C"; */
				var percent=Math.round( a.data.value/allFjs*100)+ '%'
				//console.log(percent)
				return a.data.name+"："+a.data.value+"（"+percent+"）";
			}
		},
		legend: {
			right:10,
			top:30,
			height:140,
			itemWidth:10,
			itemHeight:10,
			itemGap:10,
			textStyle:{
				color: 'rgba(255,255,255,.6)',
				fontSize:12
			},
			orient:'vertical',
			data:['汉族','维吾尔族','哈萨克族','回族','其他']
		},
	   calculable : true,
		series : [
			{
				name:' ',
				color: ['#62c98d', '#205acf', '#c9c862', '#c98b62', '#c962b9', '#7562c9','#c96262'],	
				type:'pie',
				radius : [30, 70],
				center : ['35%', '50%'],
				roseType : 'radius',
				label: {
					normal: {
						show: true
					},
					emphasis: {
						show: true
					}
				},

				lableLine: {
					normal: {
						show: true
					},
					emphasis: {
						show: true
					}
				},

				data:[
					{value:allUnitNumb.hanzu, name:'汉族'},
					{value:allUnitNumb.wwez, name:'维吾尔族'},
					{value:allUnitNumb.hskz, name:'哈萨克族'},
					{value:allUnitNumb.huizu, name:'回族'},
					{value:allUnitNumb.qtz, name:'其他'}
				]
			},
		]
	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
	window.addEventListener("resize",function(){
		myChart.resize();
	});
}
//性别
function echarts_6(zbData) {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('echart6'));
	var allUnitNumb=zbData.allUnitNumb;
	var allFjs=zbData.allUnitNumb.fjs;
	option = {
		tooltip : {
			trigger: 'item',
			formatter:function(a){
				var percent=Math.round( a.data.value/allFjs*100)+ '%'
				return a.data.name+"："+a.data.value+"（"+percent+"）";
			}
		},
		legend: {
			right:10,
			top:30,
			height:140,
			itemWidth:10,
			itemHeight:10,
			itemGap:10,
			textStyle:{
				color: 'rgba(255,255,255,.6)',
				fontSize:12
			},
			orient:'vertical',
			data:['男','女']
		},
	   calculable : true,
		series : [
			{
				name:' ',
				color: ['#62c98d', '#205acf', '#c9c862', '#c98b62', '#c962b9', '#7562c9','#c96262'],	
				type:'pie',
				radius : [30, 70],
				center : ['35%', '50%'],
				roseType : 'radius',
				label: {
					normal: {
						show: true
					},
					emphasis: {
						show: true
					}
				},

				lableLine: {
					normal: {
						show: true
					},
					emphasis: {
						show: true
					}
				},

				data:[
					{value:allUnitNumb.nan, name:'男'},
					{value:allUnitNumb.nv, name:'女'}
				]
			},
		]
	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
	window.addEventListener("resize",function(){
		myChart.resize();
	});
}
//学历
function echarts_3(zbData) {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('echart3'));
	var allUnitNumb=zbData.allUnitNumb;
	var allFjs=zbData.allUnitNumb.fjs;
	option = {
		tooltip : {
			trigger: 'item',
			formatter:function(a){
				var percent=Math.round( a.data.value/allFjs*100)+ '%'
				return a.data.name+"："+a.data.value+"（"+percent+"）";
			}
		},
		legend: {
			right:10,
			top:30,
			height:140,
			itemWidth:10,
			itemHeight:10,
			itemGap:10,
			textStyle:{
				color: 'rgba(255,255,255,.6)',
				fontSize:12
			},
			orient:'vertical',
			data:['全日制本科','本科','全日制大专','大专','中专','高中及以下']
		},
	   calculable : true,
		series : [
			{
				name:' ',
				color: ['#62c98d', '#205acf', '#c9c862', '#c98b62', '#c962b9', '#7562c9','#c96262'],	
				type:'pie',
				radius : [30, 70],
				center : ['35%', '50%'],
				roseType : 'radius',
				label: {
					normal: {
						show: true
					},
					emphasis: {
						show: true
					}
				},

				lableLine: {
					normal: {
						show: true
					},
					emphasis: {
						show: true
					}
				},

				data:[
					{value:allUnitNumb.qrzbk, name:'全日制本科'},
					{value:allUnitNumb.fqrzbk, name:'本科'},
					{value:allUnitNumb.qrzdz, name:'全日制大专'},
					{value:allUnitNumb.fqrzdz, name:'大专'},
					{value:allUnitNumb.zhongzhuan, name:'中专'},
					{value:allUnitNumb.gzjyx, name:'高中及以下'}
				]
			},
		]
	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
	window.addEventListener("resize",function(){
		myChart.resize();
	});
}
function echarts_333() {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('echart3'));

    option = {
	    tooltip: {
        trigger: 'axis',
        axisPointer: {
            lineStyle: {
                color: '#dddc6b'
            }
        }
    },
    grid: {
        left: '10',
		top: '20',
        right: '30',
        bottom: '10',
        containLabel: true
    },

    xAxis: [{
        type: 'category',
        boundaryGap: false,
		axisLabel:  {
                textStyle: {
 					color: "rgba(255,255,255,.6)",
					fontSize:16,
                },
            },
        axisLine: {
			lineStyle: { 
				color: 'rgba(255,255,255,.1)'
			}

        },

	   data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']

		}, {

			axisPointer: {show: false},
			axisLine: {  show: false},
			position: 'bottom',
			offset: 20,

		   

		}],

		yAxis: [{
			type: 'value',
			axisTick: {show: false},
			axisLine: {
				lineStyle: {
					color: 'rgba(255,255,255,.1)'
				}
			},
		   axisLabel:  {
					textStyle: {
						color: "rgba(255,255,255,.6)",
						fontSize:16,
					},
				},

			splitLine: {
				lineStyle: {
					 color: 'rgba(255,255,255,.1)'
				}
			}
		}],
		series: [
			{
			name: '结算率',
			type: 'line',
			smooth: true,
			symbol: 'circle',
			symbolSize: 5,
			showSymbol: false,
			lineStyle: {
				
				normal: {
					color: '#dddc6b',
					width: 4
				}
			},
			areaStyle: {
				normal: {
					color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						offset: 0,
						color: 'rgba(221, 220, 107, 0.4)'
					}, {
						offset: 0.8,
						color: 'rgba(221, 220, 107, 0.1)'
					}], false),
					shadowColor: 'rgba(0, 0, 0, 0.1)',
				}
			},
				itemStyle: {
				normal: {
					color: '#dddc6b',
					borderColor: 'rgba(221, 220, 107, .1)',
					borderWidth: 12
				}
			},
			data: [3, 4, 3, 4, 3, 4, 3, 6, 2, 4, 2, 4]

		}, 

			 ]

	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
	window.addEventListener("resize",function(){
		myChart.resize();
	});
}
//年龄
function echarts_4(zbData) {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('echart4'));
	var allUnitNumb=zbData.allUnitNumb;
	option = {
		tooltip: {
			trigger: 'axis',
			axisPointer: {
				type: 'shadow'
			}
		},
		legend: {
			data: ['年龄段1'],
			align: 'right',
			right: '40%',
			top:'0%',
			textStyle: {
				color: "#fff",
				fontSize: '16',

			},
			itemWidth: 16,
			itemHeight: 16,
			itemGap: 35
		},
		grid: {
			left: '0%',
			top:'40px',
			right: '0%',
			bottom: '2%',
		   containLabel: true
		},
		xAxis: [{
			type: 'category',
				data: ['25岁以下(含)', '26-35岁', '36-45岁', '46-54岁', '55岁以上(含)','30岁以下','31-40岁','40岁以上'],
			axisLine: {
				show: true,
			 lineStyle: {
					color: "rgba(255,255,255,.1)",
					width: 1,
					type: "solid"
				},
			},
			
			axisTick: {
				show: false,
			},
			axisLabel:  {
					interval: 0,
				   // rotate:50,
					show: true,
					splitNumber: 15,
					textStyle: {
						color: "rgba(255,255,255,.6)",
						fontSize: '16',
					},
				},
		}],
		yAxis: [{
			type: 'value',
			axisLabel: {
			    /* formatter: '{value} %', */
				show:true,
				 textStyle: {
						color: "rgba(255,255,255,.6)",
						fontSize: '16',
					},
			},
			axisTick: {
				show: false,
			},
			axisLine: {
				show: true,
				lineStyle: {
					color: "rgba(255,255,255,.1	)",
					width: 1,
					type: "solid"
				},
			},
			splitLine: {
				lineStyle: {
				   color: "rgba(255,255,255,.1)",
				}
			}
		}],
		series: [{
			name: '年龄段',
			type: 'bar',
			data: [allUnitNumb.age25, allUnitNumb.age2635, allUnitNumb.age3654, allUnitNumb.age4654, allUnitNumb.age55,
				   allUnitNumb.age30,allUnitNumb.age3140,allUnitNumb.age40],
			barWidth:'15', //柱子宽度
		   // barGap: 1, //柱子之间间距
			itemStyle: {
				normal: {
					color:'#2f89cf',
					opacity: 1,
					barBorderRadius: 5,
				}
			}
		}
		]
	};
       

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
	window.addEventListener("resize",function(){
		myChart.resize();
	});
}
//招录批次
function echarts_5(zbData) {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('echart5'));
	var allDpcList=zbData.allDpcListNumb;
	//var dataNames=['批次1','批次2','批次3','批次4'];
	//var dataValues=[5,7,3,6];
	var dataNames=new Array(allDpcList.size);
	var dataValues=new Array(allDpcList.size);
	for(var i=0;i<allDpcList.length;i++){
		if(i<=3){//只加载四条数据
			dataNames[i]=allDpcList[i].dpch;
			dataValues[i]=allDpcList[i].dpcNumb;
		}
	}
	
	
	option = {
		tooltip: {
			trigger: 'axis',
			axisPointer: {
				type: 'shadow'
			}
		},
		legend: {
			data: ['招录批次1'],
			align: 'right',
			right: '40%',
			top:'0%',
			textStyle: {
				color: "#fff",
				fontSize: '16',

			},
			itemWidth: 16,
			itemHeight: 16,
			itemGap: 35
		},
		grid: {
			left: '0%',
			top:'40px',
			right: '0%',
			bottom: '2%',
		   containLabel: true
		},
		xAxis: [{
			type: 'category',
				data: dataNames,
			axisLine: {
				show: true,
			 lineStyle: {
					color: "rgba(255,255,255,.1)",
					width: 1,
					type: "solid"
				},
			},
			
			axisTick: {
				show: false,
			},
			axisLabel:  {
					interval: 0,
				   // rotate:50,
					show: true,
					splitNumber: 15,
					textStyle: {
						color: "rgba(255,255,255,.6)",
						fontSize: '16',
					},
				},
		}],
		yAxis: [{
			type: 'value',
			axisLabel: {
			   //formatter: '{value} %'
				show:true,
				 textStyle: {
						color: "rgba(255,255,255,.6)",
						fontSize: '16',
					},
			},
			axisTick: {
				show: false,
			},
			axisLine: {
				show: true,
				lineStyle: {
					color: "rgba(255,255,255,.1	)",
					width: 1,
					type: "solid"
				},
			},
			splitLine: {
				lineStyle: {
				   color: "rgba(255,255,255,.1)",
				}
			}
		}],
		series: [{
			name: '招录批次',
			type: 'bar',
			data: dataValues,
			barWidth:'15', //柱子宽度
		   // barGap: 1, //柱子之间间距
			itemStyle: {
				normal: {
					color:'#2f89cf',
					opacity: 1,
					barBorderRadius: 5,
				}
			}
		}
		]
	};
       

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
	window.addEventListener("resize",function(){
		myChart.resize();
	});
}

function echarts_555() {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('echart5'));
	option = {
	  //  backgroundColor: '#00265f',
		tooltip: {
			trigger: 'axis',
			axisPointer: {
				type: 'shadow'
			}
		},
		legend: {
			data: ['2017年', '2018年'],
			align: 'right',
			right: '40%',
			top:'0%',
			textStyle: {
				color: "#fff",
				fontSize: '16',

			},
	 
			itemGap: 35
		},
		grid: {
			left: '0%',
			top:'40px',
			right: '0%',
			bottom: '2%',
		   containLabel: true
		},
		xAxis: [{
			type: 'category',
				data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
			axisLine: {
				show: true,
			 lineStyle: {
					color: "rgba(255,255,255,.1)",
					width: 1,
					type: "solid"
				},
			},
			axisTick: {
				show: false,
			},
			axisLabel:  {
					interval: 0,
				   // rotate:50,
					show: true,
					splitNumber: 15,
					textStyle: {
						color: "rgba(255,255,255,.6)",
						fontSize: '16',
					},
				},
		}],
		yAxis: [{
			type: 'value',
			axisLabel: {
			   //formatter: '{value} %'
				show:true,
				 textStyle: {
						color: "rgba(255,255,255,.6)",
						fontSize: '16',
					},
			},
			axisTick: {
				show: false,
			},
			axisLine: {
				show: true,
				lineStyle: {
					color: "rgba(255,255,255,.1	)",
					width: 1,
					type: "solid"
				},
			},
			splitLine: {
				lineStyle: {
				   color: "rgba(255,255,255,.1)",
				}
			}
		}],
		series: [{
			name: '2017年',
			type: 'line',
			
			data: [2, 6, 3, 8, 5, 8, 10, 13, 8, 5, 6, 9],
		   
			itemStyle: {
				normal: {
					color:'#2f89cf',
					opacity: 1,
					
					barBorderRadius: 5,
				}
			}
		}, {
			name: '2018年',
			type: 'line',
			data: [5, 2, 6, 4, 5, 12, 5, 17, 9, 2, 6, 3],
			barWidth:'15',
		   // barGap: 1,
			itemStyle: {
				normal: {
					color:'#62c98d',
					opacity: 1,
					barBorderRadius: 5,
				}
			}
		},
		]
	};
   

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
	window.addEventListener("resize",function(){
		myChart.resize();
	});
}

function zb1(zbData) {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('zb1'));
	//var v1=60//结算数
	//var v2=40//未结算数
	//var v3=v1+v2//总订单数
	var allUnitNumb=zbData.allUnitNumb;
	var v1=allUnitNumb.zgdy;
	var v3=allUnitNumb.fjs;
	var v2=v3-v1;
	option = {	
		series: [{
			type: 'pie',
			radius: ['60%', '70%'],
			color:'#49bcf7',
			label: {
				normal: {
					position: 'center'
				}
			},
			data: [{
				value: v1,
				name: '中共党员',
				label: {
					normal: {
						formatter:Math.round( v1/v3*100)+ '%',
						textStyle: {
							fontSize: 30,
							color:'#fff',
						}
					}
				}
			}, 
				   {
				value: v2,
				label: {
					normal: {
					 formatter : function (params){
					return '中共党员'
				},
						textStyle: {
							color: '#aaa',
							fontSize: 16
						}
					}
				},
				itemStyle: {
					normal: {
						color: 'rgba(255,255,255,.2)'
					},
					emphasis: {
						color: '#fff'
					}
				},
			}]
		}]
	};
	myChart.setOption(option);
	window.addEventListener("resize",function(){
		myChart.resize();
	});
}
function zb2(zbData) {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('zb2'));
	var allUnitNumb=zbData.allUnitNumb;
	var v1=allUnitNumb.ybdy;
	var v3=allUnitNumb.fjs;
	var v2=v3-v1;
    option = {
		//animation: false,
		series: [{	
			type: 'pie',
		   radius: ['60%', '70%'],
			color:'#49bcf7',
			label: {
				normal: {
					position: 'center'
				}
			},
			data: [{
				value: v1,
				name: '预备党员',
				label: {
					normal: {
						formatter:Math.round( v1/v3*100)+ '%',
						textStyle: {
							fontSize: 24,
							color:'#fff',
						}
					}
				}
			}, {
				value: v2,
				label: {
					normal: {
					 formatter : function (params){
					return '预备党员'
				},
						textStyle: {
							color: '#aaa',
							fontSize: 16
						}
					}
				},
				itemStyle: {
					normal: {
						color: 'rgba(255,255,255,.2)'
					},
					emphasis: {
						color: '#fff'
					}
				},
			}]
		}]
	};
	myChart.setOption(option);
	window.addEventListener("resize",function(){
		myChart.resize();
	});
}
function zb3(zbData) {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('zb3'));
	var allUnitNumb=zbData.allUnitNumb;
	var v1=allUnitNumb.gqty;
	var v3=allUnitNumb.fjs;
	var v2=v3-v1;
	option = {	
		series: [{
			
			type: 'pie',
		   radius: ['60%', '70%'],
			color:'#62c98d',
			label: {
				normal: {
					position: 'center'
				}
			},
			data: [{
				value: v1,
				name: '共青团员',
				label: {
					normal: {
						formatter:Math.round( v1/v3*100)+ '%',
						textStyle: {
							fontSize: 24,
							color:'#fff',
						}
					}
				}
			}, {
				value: v2,
				label: {
					normal: {
					 formatter : function (params){
					return '共青团员'
				},
						textStyle: {
							color: '#aaa',
							fontSize: 16
						}
					}
				},
				itemStyle: {
					normal: {
						color: 'rgba(255,255,255,.2)'
					},
					emphasis: {
						color: '#fff'
					}
				},
			}]
		}]
	};
	myChart.setOption(option);
	window.addEventListener("resize",function(){
		myChart.resize();
	});
}
function zb4(zbData) {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('zb4'));
	var allUnitNumb=zbData.allUnitNumb;
	var v1=allUnitNumb.qunzhong;
	var v3=allUnitNumb.fjs;
	var v2=v3-v1;
	option = {	
		series: [{
				
				type: 'pie',
			   radius: ['60%', '70%'],
				color:'#29d08a',
				label: {
					normal: {
						position: 'center'
					}
				},
				data: [{
					value: v1,
					name: '群众',
					label: {
						normal: {
							formatter:Math.round( v1/v3*100)+ '%',
							textStyle: {
								fontSize: 24,
								color:'#fff',
							}
						}
					}
				}, {
					value: v2,
					label: {
						normal: {
						 formatter : function (params){
						return '群众'
					},
							textStyle: {
								color: '#aaa',
								fontSize: 16
							}
						}
					},
					itemStyle: {
						normal: {
							color: 'rgba(255,255,255,.2)'
						},
						emphasis: {
							color: '#fff'
						}
					},
				}]
			}]
		};
        myChart.setOption(option);
        window.addEventListener("resize",function(){
            myChart.resize();
        });
    }
})



		
		
		


		









