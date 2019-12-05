<#include "/common/common.ftl">
<!DOCTYPE html>
<script src="${rc.contextPath}/media/js/jquery-1.8.3.min.js" type="text/javascript"></script> 
<!-- BEGIN BODY -->

<body class="page-header-fixed">
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN PAGE CONTAINER-->        
			<div class="container-fluid">
				<!-- BEGIN PAGE HEADER-->
				<div class="row-fluid">
					<div class="span12">
						<!-- BEGIN PAGE TITLE & BREADCRUMB-->
						<h3 class="page-title">
							日志收集
						</h3>
						<ul class="breadcrumb">
							<li>
								<i class="icon-home"></i>
								<a href="index.html">${project.projectName}</a> 
								<i class="icon-angle-right"></i>
							</li>
							<li>
								<a href="#">${project.excutePoint}</a>
							</li>
						</ul>
                <!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->

				<!-- BEGIN PAGE CONTENT-->
				<div class="row-fluid">
					<input id="projectName" type="hidden" value="${project.projectName}" />
					<input id="excutePoint" type="hidden" value="${project.excutePoint}" />
				  <label style="float:left" class="control-label">服务器:</label>
					<div  style="float:left" class="control">
						<select id="server" class="medium m-wrap" >
						<option value="">全部</option>
						<#list serverList as server>
							<option value="${server}">${server}</option>
						</#list>
						</select>
					</div>	&nbsp;<input type="hidden" id="interval" value="60000">
					<a href="#" onClick="initAll()" class="btn blue">查看</a>&nbsp;&nbsp;
					<a href="/log/log_list?projectId=${project.projectId}&projectName=${project.projectName}&excutePoint=${project.excutePoint}" class="btn blue">日志列表</a>
					<div class="span12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						    <div id="container" style="width:1000px;height:400px"></div>	
						<!-- END EXAMPLE TABLE PORTLET-->				
                           <div id="containerOneDay" style="width:1000px;height:400px"></div>
						   <input type="hidden" id="preDay"/>
						   <input type="hidden" id="nextDay"/>
						   <a href="javascript:void(0);" onClick="initPreDay()" class="btn blue">上一天</a>&nbsp;&nbsp;
						   <a href="javascript:void(0);" onClick="initNextDay()" class="btn blue">下一天</a>&nbsp;&nbsp;
						   <div id="containerOneMonth" style="width:1000px;height:400px"></div>	
						   <input type="hidden" id="preMonth"/>
						   <input type="hidden" id="nextMonth"/>
						   <a href="javascript:void(0);" onClick="initPreMonth()" class="btn blue">上一月</a>&nbsp;&nbsp;
						   <a href="javascript:void(0);" onClick="initNextMonth()" class="btn blue">下一月</a>&nbsp;&nbsp;
					</div>

				</div>

				</div>

				<!-- END PAGE CONTENT -->

			<!-- END PAGE CONTAINER-->
		</div>          
                  
		<!-- END PAGE -->
	</div>

	<!-- END CONTAINER -->

<!-- END BODY -->
</body> 
	


<script language="JavaScript">
var chart;
function initAll(){
  init();
  initDay(0);
  initMonth(0);
}
function init(){
  $.post("/log/getStaticLogInfo", {serverName:$("#server").val(),projectName:$("#projectName").val(),excutePoint:$("#excutePoint").val()},
	                    function(data){
	                        var data=JSON.parse(data);     	
                            staticChart(data);
	                    });
}
function staticChart(tt){
  chart = {
      type: 'spline',
	  animation: Highcharts.svg, // don't animate in IE < IE 10.
      marginRight: 10,
	  events: {
         load: function () {
            // set up the updating of the chart each second
            var series = this.series[0];
            var series1 = this.series[1];
            var series2 = this.series[2];
            setInterval(function(){
    	     $.post("/log/getOutInInfo", {serverName:$("#server").val(),projectName:$("#projectName").val(),excutePoint:$("#excutePoint").val()},
	                    function(data){
	                        var u=JSON.parse(data);    
      					     var x = u.x; // current time
                             var y = u.y;
                             var z=u.z;
                             var r=u.r;
                          series.addPoint([x,y], true, true);
                          series1.addPoint([x,z], true, true);
                          series2.addPoint([x,r], true, true);
                          chart.series[0].chart;
	                    });
            
            }, $("#interval").val());
         }
      }
   };
   var title = {
      text: '实时接口访问统计图'   
   };   
   var xAxis = {
      type: 'datetime',
      tickPixelInterval: 150
   };
   var yAxis = {
      title: {
         text: '条数'
      },
      plotLines: [{
         value: 0,
         width: 1,
         color: '#808080'
      }]
   };
   var tooltip = {
      formatter: function () {
      return '<b>' + this.series.name + '</b><br/>' +
         Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
         Highcharts.numberFormat(this.y, 2);
      }
   };
   var plotOptions = {
      area: {
         pointStart: 1940,
         marker: {
            enabled: false,
            symbol: 'circle',
            radius: 2,
            states: {
               hover: {
                 enabled: true
               }
            }
         }
      }
   };
   var legend = {
      enabled: false
   };
   var exporting = {
      enabled: false
   };
   var series= [{
      name: '时间',
      data: (function () {
         // generate an array of random data
         var data = [],i;
         for (i = 0; i <= 19; i += 1) {
            data.push({
               x: tt.x[i],
               y: tt.y[0].data[i],
               z: tt.y[1].data[i],
               r: tt.y[2].data[i]
            });
         }
         return data;
      }())    
   },{
      name: '时间',
      data: (function () {
         // generate an array of random data
         var data = [],i;
         for (i = 0; i <= 19; i += 1) {
            data.push({
               x: tt.x[i],
               y: tt.y[1].data[i],
               z: tt.y[2].data[i],
            });
         }
         return data;
      }())    
   }];   
      
   var json = {};   
   json.chart = chart; 
   json.title = title;     
   json.tooltip = tooltip;
   json.xAxis = xAxis;
   json.yAxis = yAxis; 
   json.legend = legend;  
   json.exporting = exporting;   
   json.series = series;
   json.plotOptions = plotOptions;
   
   
   Highcharts.setOptions({
      global: {
         useUTC: false
      }
   });
   $('#container').highcharts(json);
}

function initPreDay(){
	var preDay = $("#preDay").val();
	if(null == preDay || typeof (preDay) == "undefined" || isNaN(preDay)){
		initDay(0);
	}else{
		initDay(parseInt(preDay));
	}
}
function initNextDay(){
	var nextDay = $("#nextDay").val();
	if(null == nextDay || typeof (nextDay) == "undefined" || isNaN(nextDay)){
		initDay(0);
	}else{
		initDay(parseInt(nextDay));
	}
}
function initDay(targetTime){
    $.post("/log/getOneDayLogInfo", {serverName:$("#server").val(),projectName:$("#projectName").val(),excutePoint:$("#excutePoint").val(),targetTime:targetTime},
	                    function(data){
	                        var u=JSON.parse(data);
							$("#preDay").val(u.preDay);
							$("#nextDay").val(u.nextDay);
                            oneDayChart(u);
	                    });
   }
function oneDayChart(data) {
  $('#containerOneDay').highcharts({
    title: {
        text: '24小时日志表(每5分钟)',
        x: -20
    },
    xAxis: {
        categories: data.x
    },
    yAxis: {
        title: {
            text: '条数'
        },
        plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
        }]
    },
    tooltip: {
        valueSuffix: '条'
    },
    legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle',
        borderWidth: 0
    },
    series: data.y
});
};

function initPreMonth(){
	var preMonth = $("#preMonth").val();
	if(null == preMonth || typeof (preMonth) == "undefined" || isNaN(preMonth)){
		initMonth(0);
	}else{
		initMonth(parseInt(preMonth));
	}
}
function initNextMonth(){
	var nextMonth = $("#nextMonth").val();
	if(null == nextMonth || typeof (nextMonth) == "undefined" || isNaN(nextMonth)){
		initMonth(0);
	}else{
		initMonth(parseInt(nextMonth));
	}
}
function initMonth(targetTime){
    $.post("/log/getOneMonthLogInfo", {serverName:$("#server").val(),projectName:$("#projectName").val(),excutePoint:$("#excutePoint").val(),targetTime:targetTime},
	                    function(data){
	                        var u=JSON.parse(data);     
							$("#preMonth").val(u.preMonth);
							$("#nextMonth").val(u.nextMonth);
                            oneMonthChart(u);
	                    });
   }
function oneMonthChart(data) {
  $('#containerOneMonth').highcharts({
    title: {
        text: '月度日志表(每天)',
        x: -20
    },
    xAxis: {
        categories: data.x
    },
    yAxis: {
        title: {
            text: '条数'
        },
        plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
        }]
    },
    tooltip: {
        valueSuffix: '条'
    },
    legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle',
        borderWidth: 0
    },
    series: data.y
});
};
initAll();
</script>
</html>
