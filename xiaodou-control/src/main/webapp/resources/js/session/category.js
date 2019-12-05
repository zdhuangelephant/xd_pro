$(function () {
	$(".main").each(function() {
		chars($(this).attr("id"));
	});
	
//	$(".collapse").each(function() {
//		$('#'+$(this).attr("id")).on('show.bs.collapse', function () {
//			chars("main_"+$(this).attr("id"));
//			alert('嘿，当您展开时会提示本警告');
//			});
//	});
});
function collapse(catId){
	//$('.collapse').collapse('hide');
	$('#'+catId).collapse('toggle');
	//chars("main_"+catId);
}
function categoryUnitList(catId){
	addNewTabs({
		id:'10003',title: '试点单位列表',close: true,url: '/session/category_unit_list?catId='+catId}
	);
	//addTabs({id:'10004',title: '专业列表',close: true,url: '/session/category_list'});
}


function categoryUnitTendencyList(catId,pilotUnitId){
	addNewTabs({
		id:'10012',title: '趋势分析',close: true,url: '/session/category_unit_tendency_list?catId='+catId+'&pilotUnitId='+pilotUnitId}
	);
}
function checkTime(_time, _maxTime) {
	if(parseInt(_time) > _maxTime){
		return checkTime(_time, _maxTime+60);
	}
	return _maxTime;
}

function chars(id){
	var learnPercent = $("#learnPercent_"+id).val();
	var missionPercent = $("#missionPercent_"+id).val();
	var rightPercent = $("#rightPercent_"+id).val();
	var learnTimePercent = $("#learnTimePercent_"+id).val();
	var myChart = echarts.init(document.getElementById(id));
	
	var maxTime = 120;
	maxTime = checkTime(learnTimePercent, maxTime);
    // 指定图表的配置项和数据
    var option = {
        title: {
           // text: '基础雷达图'
        },
        tooltip: {},
        legend: {
           // data: ['预算分配（Allocated Budget）', '实际开销（Actual Spending）']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        radar: {
            // shape: 'circle',
            indicator: [
               { name: '活跃度（百分比）', max: 100 },
               { name: '任务完成度（百分比）', max: 100 },
               { name: '平均正确率（百分比）', max: 100 },
               { name: '平均学习时长(分钟)', max: maxTime }
            ]
        },
        series: [{
            name: '专业雷达图',
            type: 'radar',
            areaStyle: {normal: {}},
            data: [
                {
                    value: [learnPercent, missionPercent, rightPercent, learnTimePercent]
                    //name: '专业名称'
                }
            ],
            itemStyle : {
				normal : {
					color:'orange',//点的颜色
//							lineStyle:{//线的颜色
//								color:'#f5bf58'
//							}
				}
			}
        
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}
