<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <#include "/common/title.ftl"/>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="${baseOP}/content/ui/global/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link href="${baseOP}/content/ui/global/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <!-- Theme style -->
    <link rel="stylesheet" href="${baseOP}/content/adminlte/dist/css/AdminLTE.css">
    <link rel="stylesheet" href="${baseOP}/content/adminlte/dist/css/skins/_all-skins.min.css">
    <link href="${baseOP}/content/min/css/supershopui.common.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${baseOP}/content/plugins/iCheck/flat/blue.css">
    <!-- Morris chart -->
    <link rel="stylesheet" href="${baseOP}/content/plugins/morris/morris.css">
    <!-- jvectormap -->
    <link rel="stylesheet" href="${baseOP}/content/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
    <!-- Date Picker -->
    <link rel="stylesheet" href="${baseOP}/content/plugins/datepicker/datepicker3.css">
    <!-- Daterange picker -->
    <link rel="stylesheet" href="${baseOP}/content/plugins/daterangepicker/daterangepicker.css">
    <!-- bootstrap wysihtml5 - text editor -->
    <link rel="stylesheet" href="${baseOP}/content/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
    <link rel="stylesheet" href="${baseOP}/css/dashboard.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

	<meta charset="utf-8" />
</head>
<body>
<section class="content-header">
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-area-chart"></i>学习监督</a></li>
        <li><a href="#">${catName}</a></li>
        <li class="active">${pilotUnitName}</li>
    </ol>
</section>
<!-- Main content -->
<section class="content">
<!-- Small boxes (Stat box) -->
<#if adminUser.role == 3>
<div class="row">
    <div class="col-md-12">
        <!-- BEGIN SAMPLE TABLE PORTLET-->
        <div class="box">
            <div class="box-body">
                <table class="table-scrollable table table-hover" id="table"
                               data-toggle="table"
                               data-pagination="true"
                               data-advanced-search="true"
                               data-id-table="advancedTable"
                               data-row-style="rowStyle">
                    <thead>
                        <tr>
                            <th class="text-center" data-sortable="true">序号</th>
                            <th class="text-center" >${pilotUnitName}</th>
                            <th class="text-center">学生人数</th>
                            <th class="text-center">正确率</th>
                            <th class="text-center">活跃度</th>
                            <th class="text-center">任务完成度</th>
                            <th class="text-center">平均学习时长(分钟)</th>
                            <th class="text-center">预警数量</th>
                        </tr>
                    </thead>
                    <tbody>
						<#list listCategoryUnitSessionPercentVO as categoryUnit>
						<#if categoryUnit??>
				        <tr>
				        	<td class="text-center">${categoryUnit_index+1}</td>
				        	<td class="text-center">${categoryUnit.pilotUnitName}</td>
							<td class="text-center">${categoryUnit.studentCount}</td>
							<td class="text-center">${categoryUnit.rightPercent}</td>
							<td class="text-center">${categoryUnit.learnPercent}</td>
							<td class="text-center">${categoryUnit.missionPercent}</td>
							<td class="text-center">${categoryUnit.learnTimePercent}</td>
							<td class="text-center">${categoryUnit.alarmCount}</td>
				        </tr>
				        </#if>
				        </#list>
					</tbody>
                </table>
            </div>
        </div>
        <!-- END SAMPLE TABLE PORTLET-->
    </div>
</div>
</#if>


<!-- Main row -->
<div class="row">
<!-- Left col -->
<section class="col-md-12 connectedSortable">
<!-- Custom tabs (Charts with tabs)-->
<div class="box box-warning box-solid">
	<div class="box-header with-border text-center">
        <h3 class="box-title" style="margin:10px">趋势分析</h3>
        <!-- /.box-tools -->
        <ul class="nav nav-tabs pull-right">
        <li>
		<select onchange="change(this.options[this.options.selectedIndex].value,${catId},${pilotUnitId})"; id="catId" name="courseId" type="text" class="form-control" style="float:left;">
			<option value="7" selected="selected">过去7天</option>
			<option value="30">过去30天</option>
			<option value="60">过去60天</option>
		</select>
		</li>
    </ul>
    </div>
<div class="nav-tabs-custom">
    <!-- Tabs within a box -->
    <ul class="nav nav-tabs">
        <li class="text-center" style="width: 25%;"><a id="rightPercent" onclick="rightPercent();" href="#rightPercent-chart" data-toggle="tab">每日正确率</a></li>
        <li class="text-center" style="width: 23%;"><a id="learnPercent" onclick="learnPercent();" href="#learnPercent-chart" data-toggle="tab">每日活跃度</a></li>
        <li class="text-center" style="width: 23%;"><a onclick="missionPercent();" href="#missionPercent-chart" data-toggle="tab">每日任务完成度</a></li>
    	<li class="text-center pull-right" style="width: 25%;"><a onclick="learnTimePercent();" href="#learnTimePercent-chart" data-toggle="tab">每日平均学习时长</a></li>
    </ul>
    
   <input type="hidden" value='${learnJsonData}' id="learnJsonData">
    <div class="tab-content no-padding">
        <!-- Morris chart - Sales -->
        <div class="chart tab-pane in active" id="learnTimePercent-chart" style="position: relative">
        <!--<iframe src="/dashboard/echarts_line?name=''" width="100%" height="100%"></iframe>-->
			<section class="content">
			    <div class="row">
			        <div class="col-md-12">
			            <!-- AREA CHART -->
			            <div class="box box-primary">
			                <div class="box-body">
			                    <div class="chart">
			                        <div id="main_learnTimePercent" ></div>
			                    </div>
			                </div>
			                <!-- /.box-body -->
			            </div>
			            <!-- /.box -->
			        </div>
			    </div>
			    <!-- /.row -->
			</section>
        </div>
        <div class="chart tab-pane active" id="missionPercent-chart" style="position: relative;">
			<section class="content">
			    <div class="row">
			        <div class="col-md-12">
			            <!-- AREA CHART -->
			            <div class="box box-primary">
			                <div class="box-body">
			                    <div class="chart">
			                        <div id="main_missionPercent"></div>
			                    </div>
			                </div>
			                <!-- /.box-body -->
			            </div>
			            <!-- /.box -->
			        </div>
			    </div>
			    <!-- /.row -->
			</section>
        </div>
        <div class="chart tab-pane active" id="learnPercent-chart" style="position: relative;">
			<section class="content">
			    <div class="row">
			        <div class="col-md-12">
			            <!-- AREA CHART -->
			            <div class="box box-primary">
			                <div class="box-body">
			                    <div class="chart">
			                        <div id="main_learnPercent"></div>
			                    </div>
			                </div>
			                <!-- /.box-body -->
			            </div>
			            <!-- /.box -->
			        </div>
			    </div>
			    <!-- /.row -->
			</section>
        </div>
        <div class="chart tab-pane active" id="rightPercent-chart" style="position: relative;">
			<section class="content">
			    <div class="row">
			        <div class="col-md-12">
			            <!-- AREA CHART -->
			            <div class="box box-primary">
			                <div class="box-body">
			                    <div class="chart">
			                        <div id="main_rightPercent"></div>
			                    </div>
			                </div>
			                <!-- /.box-body -->
			            </div>
			            <!-- /.box -->
			        </div>
			    </div>
			    <!-- /.row -->
			</section>
        </div>
        
        
    </div>
</div>
<!-- /.nav-tabs-custom -->
</section>
<!-- right col -->
</div>
<!-- /.row (main row) -->






</section>
<div class="row">
    <div class="col-md-12">
        <!-- AREA CHART -->
        <div class="box box-primary">
            <div class="box-body">
                <div class="chart">
                	<input type="hidden" id="avgJsonData" value='${avgJsonData}'>
                    <div id="main_avgScore" style="width: 100%; height: 800px;"></div>
                </div>
            </div>
            <!-- /.box-body -->
        </div>
        <!-- /.box -->
    </div>
</div>
<!-- /.row -->

<!-- Bootstrap 3.3.6 -->
    <script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
    <!-- Bootstrap 3.3.6 -->
    <script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
	<script src="${baseOP}/content/min/js/supershopui.common.js"></script>

    <script src="${baseOP}/content/plugins/jQueryUI/jquery-ui.min.js"></script>
    <script src="${baseOP}/content/plugins/raphael/raphael.min.js"></script>
    <script src="${baseOP}/content/plugins/morris/morris.js"></script>
    <!-- Sparkline -->
    <script src="${baseOP}/content/plugins/sparkline/jquery.sparkline.min.js"></script>
    <!-- jvectormap -->
    <script src="${baseOP}/content/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
    <script src="${baseOP}/content/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
    <!-- jQuery Knob Chart -->
    <script src="${baseOP}/content/plugins/knob/jquery.knob.js"></script>
    <!-- daterangepicker -->
    <script src="${baseOP}/content/plugins/daterangepicker/daterangepicker.js"></script>
    <!-- datepicker -->
    <script src="${baseOP}/content/plugins/datepicker/bootstrap-datepicker.js"></script>
    <!-- Bootstrap WYSIHTML5 -->
    <script src="${baseOP}/content/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
    <!-- AdminLTE dashboard demo (This is only for demo purposes) -->
	<script src="${baseOP}/js/echarts.min.js" charset="utf-8"></script>
    <script type="text/javascript">
  		$.widget.bridge('uibutton', $.ui.button);
    </script>
    <script src="${baseOP}/js/common.js?${timeStamp}"></script>
    <script src="${baseOP}/js/session/categoryUnitTendency.js?${timeStamp}"></script>
</body>
</html>
