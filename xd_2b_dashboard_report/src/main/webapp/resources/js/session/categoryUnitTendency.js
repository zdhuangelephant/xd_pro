$(function () {
	
	$("#tab_10012", parent.document).addClass('active');
	//折线图
	var widthtable = $("#learnTimePercent-chart").parent().width()-50;
	$("#main_learnTimePercent").css({"width":widthtable,"height":"400px","padding-right":"10px"});
	$("#main_missionPercent").css({"width":widthtable,"height":"400px","padding-right":"10px"});
	$("#main_learnPercent").css({"width":widthtable,"height":"400px","padding-right":"10px"});
	$("#main_rightPercent").css({"width":widthtable,"height":"400px","padding-right":"10px"});
	// 定义全局变量
	learnPercentArr = new Array();
	missionPercentArr = new Array();
	learnTimePercentArr = new Array();
	rightPercentArr = new Array();
	dateTimeArry = new Array();
	var learnJsonData = $("#learnJsonData").val();
	if(!isEmpty(learnJsonData)){
		var learnJson = JSON.parse(learnJsonData);
		$.each(learnJson, function(key, val) {
			learnPercentArr.push(val.learnPercent);
			missionPercentArr.push(val.missionPercent);
			learnTimePercentArr.push(val.learnTimePercent);
			rightPercentArr.push(val.rightPercent);
			dateTimeArry.push(val.dateTime);
		});
	}
	$("#rightPercent").click();
	//柱状图
	avgScore();
});


function learnPercent() {
	// alert(dateTimeArry);
	var learnPercentChart = echarts.init(document
			.getElementById('main_learnPercent'));
	var learnPercent = {
		title : {
			text : '每日活跃度（%）'
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
function checkTime(_time, _maxTime) {
	if(parseInt(_time) > _maxTime){
		return checkTime(_time, _maxTime+60);
	}
	return _maxTime;
}
function learnTimePercent() {
	// 基于准备好的dom，初始化echarts实例
	// var myChart = echarts.init(document.getElementById('main'));
	var learnTimePercentChart = echarts.init(document
			.getElementById('main_learnTimePercent'));
	var timeArray = eval(learnTimePercentArr);
	var maxTime = 120;
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
			// smooth:true,
			// itemStyle: { normal: { areaStyle: { type:'default'}} },
			data : timeArray,
			itemStyle : {
				normal : {
					color : 'orange',// 点的颜色
					areaStyle: {normal: {}}
				}
			}

		} ]
	};
	// 使用刚指定的配置项和数据显示图表。
	// myChart.setOption(option);
	learnTimePercentChart.setOption(learnTimePercent);
}
function rightPercent() {
	// 基于准备好的dom，初始化echarts实例
	// var myChart = echarts.init(document.getElementById('main'));
	var rightPercentChart = echarts.init(document
			.getElementById('main_rightPercent'));
	// 指定图表的配置项和数据
	var rightPercent = {
		title : {
			text : '每日正确率(%)'
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
            max: 100
		},
		series : [ {
			name : '每日正确率',
			type : 'line',
			stack : '总量',
			// smooth:true,
			// itemStyle: { normal: { areaStyle: { type:'default'}} },
			data : eval(rightPercentArr),
			itemStyle : {
				normal : {
					color : 'orange',// 点的颜色
					areaStyle: {normal: {}}
				}
			}

		} ]
	};
	// 使用刚指定的配置项和数据显示图表。
	rightPercentChart.setOption(rightPercent);
}
function change(days,catId,pilotUnitId) {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/session/echarts_line",
		data : {
			"days":days,
			"catId":catId,
			"pilotUnitId":pilotUnitId
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
				rightPercentArr = [];
				dateTimeArry = [];
				var json = JSON.parse(data);
				$.each(json, function(key, val) {
					learnPercentArr.push(val.learnPercent);
					missionPercentArr.push(val.missionPercent);
					learnTimePercentArr.push(val.learnTimePercent);
					rightPercentArr.push(val.rightPercent);
					dateTimeArry.push(val.dateTime);
				});
				$("#rightPercent").click();
				} else {
					layer.msg('查找失败', {
						time : 2000, // 2s后自动关闭
						icon : 1
					});
				}
		},
	});
}

function avgScore(){
	//柱状图
	// 基于准备好的dom，初始化echarts实例
	var productArr = new Array();
	var avgScoreArr = new Array();
	var avgJsonData = $("#avgJsonData").val();
	if(isEmpty(avgJsonData)){
		return;
	}
	var avgJson = JSON.parse(avgJsonData);
	$.each(avgJson, function(key, val) {
		productArr.push(val.productName);
		avgScoreArr.push(val.avgScore);
	});
    var myChart = echarts.init(document.getElementById('main_avgScore'));
    avgScoreOption = {
		title: {
            text: '课程平均成绩(分)'
        },
        color: ['#3398DB'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
//        grid: {
//            left: '3%',
//            right: '4%',
//            bottom: '3%',
//            containLabel: true
//        },
        grid: { // 控制图的大小，调整下面这些值就可以，
        	left: '3%',
            right: '4%',
            bottom: '20%',
            containLabel: true,
            y2:40
        },
        xAxis : [
            {
                type : 'category',
                data : eval(productArr),
                axisTick: {
                    alignWithLabel: true
                },
                axisLabel:{  
                    interval:0,//横轴信息全部显示  
                    //rotate:-30,
                    //margin:10,
                    formatter : function(params){
                        var newParamsName = "";
                        var paramsNameNumber = params.length;
                        var provideNumber = 4;
                        var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
                        if (paramsNameNumber > provideNumber) {
                            for (var p = 0; p < rowNumber; p++) {
                                var tempStr = "";
                                var start = p * provideNumber;
                                var end = start + provideNumber;
                                if (p == rowNumber - 1) {
                                    tempStr = params.substring(start, paramsNameNumber);
                                } else {
                                    tempStr = params.substring(start, end) + "\n";
                                }
                                newParamsName += tempStr;
                            }

                        } else {
                            newParamsName = params;
                        }
                        return newParamsName;
                    }
               } 
            }
        ],
        yAxis : [
            {
                type : 'value',
    			min: 0,
                max: 100
            }
        ],
        series : [
            {
                name:'课程平均成绩',
                type:'bar',
                barWidth: '30',
                data:eval(avgScoreArr)
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(avgScoreOption);
}

/**
 * <li>Echarts 中axisLabel中值太长自动换行处理；经测试：360、IE7-IE11、google、火狐均能正常换行显示</li>
 * <li>处理echarts 柱状图 x 轴数据显示根据柱子间隔距离自动换行显示</li>
 * @param title			    将要换行处理x轴值
 * @param datas（xAxisDataLength	）  报表需显示的axisLabel个数（X轴显示的title数）
 * @param fontSize		    x轴上axisLabel字体大小，根据图表字体大小设置而定，此处内部默认为12
 * @param barContainerWidth	    柱状图初始化所在的外层容器的宽度
 * @param xWidth		    柱状图x轴左边的空白间隙 x 的值，详见echarts文档中grid属性，默认80
 * @param x2Width		    柱状图x轴右边的空白间隙 x2 的值，详见echarts文档中grid属性，默认80
 * @param insertContent		    每次截取后要拼接插入的内容， 不传则默认为换行符：\n
 * @returns titleStr		    截取拼接指定内容后的完整字符串
 * @author lixin
 */

function getEchartBarXAxisTitle(title, datas, fontSize, barContainerWidth, xWidth, x2Width, insertContent){
	
	if(!title || title.length == 0) {
		alert("截取拼接的参数值不能为空！");return false;
	}
	if(!datas || datas.length == 0) {
		alert("用于计算柱状图柱子个数的参数datas不合法！"); return false;
	}
	if(isNaN(barContainerWidth)) {
		alert("柱状图初始化所在的容器的宽度不是一个数字");return false;
	}
	if(!fontSize){
		fontSize = 12;
	}
	if(isNaN(xWidth)) {
		xWidth = 80;//默认与echarts的默认值一致
	}
	if(isNaN(x2Width)) {
		xWidth = 80;//默认与echarts的默认值一致
	}
	if(!insertContent) {
		insertContent = "\n";
	}
	
	//柱状图x轴宽度 = 图表初始化容器div宽度 - 柱状图x轴的左右空白间隙宽度(x + x2)
	var xAxisWidth =  parseInt(barContainerWidth) - (parseInt(xWidth) + parseInt(x2Width));
	//x轴单元格的个数（即为获取x轴的数据的条数）
	var barCount = datas.length;
	//统计x轴每个单元格的间隔								
	var preBarWidth = Math.floor(xAxisWidth / barCount);		
	//柱状图每个柱所在x轴间隔能容纳的字数 = 每个柱子的 x轴间隔宽度 / 每个字的宽度（12px）
	var preBarFontCount = Math.floor(preBarWidth / fontSize) ;	
	//为了x轴标题显示美观，每个标题显示留两个字的间隙，如：原本一个格能一样显示5个字，处理后一行就只显示3个字
	if(preBarFontCount > 3) {	
		preBarFontCount -= 2;
	} else if(preBarFontCount <= 3 && preBarFontCount >= 2) {//若每个间隔距离刚好能放两个或者字符时，则让其只放一个字符
		preBarFontCount -= 1;
	}
	
	var newTitle = "";		//拼接每次截取的内容，直到最后为完整的值
	var titleSuf = "";		//用于存放每次截取后剩下的部分
	var rowCount = Math.ceil(title.length / preBarFontCount);	//标题显示需要换行的次数 
	if(rowCount > 1) {		//标题字数大于柱状图每个柱子x轴间隔所能容纳的字数，则将标题换行
		for(var j = 1; j <= rowCount; j++) {
			if(j == 1) {
				
				newTitle += title.substring(0, preBarFontCount) + insertContent;
				titleSuf = title.substring(preBarFontCount);	//存放将截取后剩下的部分，便于下次循环从这剩下的部分中又从头截取固定长度
			} else {
				
				var startIndex = 0;
				var endIndex = preBarFontCount;
				if(titleSuf.length > preBarFontCount) {	//检查截取后剩下的部分的长度是否大于柱状图单个柱子间隔所容纳的字数
					
					newTitle += titleSuf.substring(startIndex, endIndex) + insertContent;
					titleSuf = titleSuf.substring(endIndex);	//更新截取后剩下的部分，便于下次继续从这剩下的部分中截取固定长度
				} else if(titleSuf.length > 0){
					newTitle += titleSuf.substring(startIndex);
				}
			}
		}
	} else {
		newTitle = title;
	}
	return newTitle;
}