$(function() {
	var widthtable = $("#learnTimePercent-chart").parent().width()-50;
	$("#main_learnTimePercent").css({"width":widthtable,"height":"400px","padding-right":"10px"});
	$("#main_missionPercent").css({"width":widthtable,"height":"400px","padding-right":"10px"});
	$("#main_learnPercent").css({"width":widthtable,"height":"400px","padding-right":"10px"});
	// 定义全局变量
	learnPercentArr = new Array();
	missionPercentArr = new Array();
	learnTimePercentArr = new Array();
	dateTimeArry = new Array();
	var jsonData = $("#jsonData").val();
	var json = JSON.parse(jsonData);
	if(!isEmpty(json)){
		$.each(json, function(key, val) {
			// alert(key +
			// ","
			// +val.learnPercent+","+val.missionPercent+","+val.learnTimePercent+","+val.dateTime);
			learnPercentArr.push(val.learnPercent);
			missionPercentArr.push(val.missionPercent);
			learnTimePercentArr.push(val.learnTimePercent);
			dateTimeArry.push(val.dateTime);
		});
	}
	$("#learnPercent").click();
});

function learnPercent() {
	// alert(dateTimeArry);
	var learnPercentChart = echarts.init(document
			.getElementById('main_learnPercent'));
	var learnPercent = {
		title : {
			text : '每日活跃度(%)'
		},
		tooltip : {
			trigger : 'axis'
//			axisPointer: {
//	            type: 'cross',
//	            label: {
//	                backgroundColor: '#6a7985'
//	            }
//	        }
		},
		grid : {
			left : '3%',
			right : '4%',
			bottom : '3%',
			containLabel : true
		},
		toolbox : {
			feature : {
				//saveAsImage : {}
			}
		},
		xAxis : {
			type : 'category',
			boundaryGap : false,
			data : eval(dateTimeArry)
		// data:['03-30','04-01']
		},
		yAxis : {
			type : 'value',
			min: 0,
            max: 100
		},
		series : [ {
			name : '每日活跃度',
			type : 'line',
			stack : '总量',
			data : eval(learnPercentArr),
			// data:[12,45],
			itemStyle : {
				normal : {
					color : 'orange',// 点的颜色
					areaStyle: {normal: {}}
				}
			}
		} ]
	};
	learnPercentChart.setOption(learnPercent);
}
function missionPercent() {
	var missionPercentChart = echarts.init(document
			.getElementById('main_missionPercent'));
	var missionPercent = {
		title : {
			text : '每日任务完成度(%)'
		},
		tooltip : {
			trigger : 'axis'
		},
		grid : {
			left : '3%',
			right : '4%',
			bottom : '3%',
			containLabel : true
		},
		toolbox : {
			feature : {
				//saveAsImage : {}
			}
		},
		xAxis : {
			type : 'category',
			boundaryGap : false,
			data : eval(dateTimeArry)
		},
		yAxis : {
			type : 'value',
			min: 0,
            max: 100
		},
		series : [ {
			name : '每日任务完成度',
			type : 'line',
			stack : '总量',
			data : eval(missionPercentArr),
			itemStyle : {
				normal : {
					color : 'orange',// 点的颜色
					areaStyle: {normal: {}}
				}
			}
		} ]
	};
	missionPercentChart.setOption(missionPercent);
}
function learnTimePercent() {
	// 基于准备好的dom，初始化echarts实例
	// var myChart = echarts.init(document.getElementById('main'));
	var learnTimePercentChart = echarts.init(document
			.getElementById('main_learnTimePercent'));
	var timeArray = eval(learnTimePercentArr);
	var maxTime = 120;
	function checkTime(_time, _maxTime) {
		if(parseInt(_time) > _maxTime){
			return checkTime(_time, _maxTime+60);
		}
		return _maxTime;
	}
	for(var time in timeArray) {
		maxTime = checkTime(timeArray[time], maxTime);
	}
	// 指定图表的配置项和数据
	var learnTimePercent = {
		title : {
			text : '每日平均学习时长（分钟）'
		},
		tooltip : {
			trigger : 'axis'
		},
		legend : {
		// data: ['每日平均学习时长']
		},
		grid : {
			left : '3%',
			right : '4%',
			bottom : '3%',
			containLabel : true
		},
		toolbox : {
			feature : {
				//saveAsImage : {}
			}
		},
		xAxis : {
			type : 'category',
			boundaryGap : false,
			data : eval(dateTimeArry)
		},
		yAxis : {
			type : 'value',
			min: 0,
            max: maxTime
		},
		series : [ {
			name : '每日平均学习时长',
			type : 'line',
			stack : '总量',
//			 smooth:true,
//			 itemStyle: { normal: { areaStyle: { type:'default'}} },
			data : timeArray,
			itemStyle : {
				normal : {
					color : 'orange',// 点的颜色
					areaStyle: {normal: {}},
					label : {
						show : false,
						formatter : '{b}：{c}',
						position : 'top',
						textStyle : {
							fontWeight : '700',
							fontSize : '12',
							color : '#f5bf58'
						}
					},
				// lineStyle:{
				// color:'#f5bf58'
				// }
				}
			}

		} ]
	};
	// 使用刚指定的配置项和数据显示图表。
	// myChart.setOption(option);
	learnTimePercentChart.setOption(learnTimePercent);
}

function change(days) {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/dashboard/echarts_line",
		data : {
			"days":days
		},
		dataType : "html",
		error : function() {
			layer.msg('查找失败', {
				time : 2000, // 2s后自动关闭
				icon : 1
			});
		},
		success : function(data) {
			if (!isEmpty(data)) {
				learnPercentArr = [];
				missionPercentArr = [];
				learnTimePercentArr = [];
				dateTimeArry = [];
				var json = JSON.parse(data);
				$.each(json, function(key, val) {
					learnPercentArr.push(val.learnPercent);
					missionPercentArr.push(val.missionPercent);
					learnTimePercentArr.push(val.learnTimePercent);
					dateTimeArry.push(val.dateTime);
				});
				$("#learnPercent").click();
				} else {
					layer.msg('查找失败', {
						time : 2000, // 2s后自动关闭
						icon : 1
					});
				}
		},
	});
}


function jumpToDetail(){
	addNewTabs({
		id: "10214",title: '汇总统计详情',close: true,url: '/dashboard/detail'}
	);
}



