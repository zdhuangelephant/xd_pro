
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "/common/title.ftl"/>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet"
	href="${baseOP}/content/ui/global/bootstrap/css/bootstrap.min.css">
<!-- Font Awesome -->
<link
	href="${baseOP}/content/ui/global/font-awesome/css/font-awesome.css"
	rel="stylesheet" />
<!-- Theme style -->
<link rel="stylesheet"
	href="${baseOP}/content/adminlte/dist/css/AdminLTE.css">
<link rel="stylesheet"
	href="${baseOP}/content/adminlte/dist/css/skins/_all-skins.min.css">
<link href="${baseOP}/content/min/css/supershopui.common.min.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="${baseOP}/content/plugins/iCheck/flat/blue.css">
<!-- Morris chart -->
<link rel="stylesheet"
	href="${baseOP}/content/plugins/morris/morris.css">
<!-- jvectormap -->
<link rel="stylesheet"
	href="${baseOP}/content/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
<!-- Date Picker -->
<link rel="stylesheet"
	href="${baseOP}/content/plugins/datepicker/datepicker3.css">
<!-- Daterange picker -->
<link rel="stylesheet"
	href="${baseOP}/content/plugins/daterangepicker/daterangepicker.css">
<!-- bootstrap wysihtml5 - text editor -->
<link rel="stylesheet"
	href="${baseOP}/content/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
<link rel="stylesheet" href="${baseOP}/css/dashboard.css?1">
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
		<!--
    <h1>
        <small></small>
    </h1>
    -->
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i> 首页</a></li>
			<li class="active">控制台</li>
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
		<!-- Small boxes (Stat box) -->
		<#if adminUser.role ==1 || adminUser.role ==2 || adminUser.role ==3>
		<div name="row">
			<!-- <div class="col-md-12"> -->
			<div class="box box-success box-solid">
				<div class="box-header with-border text-center"
					style="text-align: -webkit-left;">
					<h3 class="box-title">每日概览(最近更新时间${summary.updateTime?string('yyyy-MM-dd HH:mm') })</h3>
					<div class="box-tools pull-right modal-open">
					<a href="javascript:;" onclick="jumpToDetail();">
							<button type="button" class="btn btn-success pull-right">查看详情</button>
					</a>
					
					</div>
				</div>
				<div class="row"></div>
			</div>
			<!-- </div> -->
			<!-- <div class="col-md-12"> -->
			<div class="nav-tabs-custom">
				<div class="tab-content no-padding">
					<!-- Morris chart - Sales -->
					<div class="chart tab-pane in active" id="learnTimePercent-chart"
						style="position: relative">
						<section class="content">
							<div class="row">
								<div class="col-md-12">
									<!-- AREA CHART -->
									<div class="box box-primary"
										style="border-top: 0px solid #d2d6de;">
										<div class="box-body">
											<div class="chart">
												<div id="main_chart_area" style="height: 200px">
													<!-- 1 -->
													<div class="inline-div w19">
														<div class="passedCount">
															<strong>
															<#if summary??>
															    ${summary.totalStudents }
															<#else>
																0
															</#if>人
															</strong>
														</div>
														<p>学生总人数</p>
													</div>
													<!-- 2 -->
													<div class="inline-div">
														<div class="passedCount">
															<strong>
																<#if summary??>
																    ${summary.totalSubjectsAndStus }
																<#else>
																	0
																</#if>科次
															</strong>
														</div>
														<p>报名总科次</p>
													</div>
													<!-- 3 -->
													<div class="inline-div">
														<div class="passedCount">
															<strong>
																<#if summary??>
																    ${summary.learnPercent?string("#.##")}
																<#else>
																	0
																</#if> %
															</strong>
														</div>
														<p>学习使用科次占比</p>
													</div>
													<!-- 4 -->
													<div class="inline-div">
														<div class="passedCount">
															<strong>
																<#if summary??>
																    ${summary.passPercent?string("#.##") }
																<#else>
																	0
																</#if> %
															</strong>
														</div>
														<p>及格科次占比</p>
													</div>
													<!-- 5 -->
													<div class="inline-div w19">
														<div class="passedCount">
															<strong>
																<#if summary??>
																    ${summary.zeroPercent?string("#.##")}
																<#else>
																	0
																</#if> %
															</strong>
														</div>
														<p>0分科次占比</p>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</section>
					</div>
				</div>
			</div>
			<!-- </div> -->
		</div>
		</#if>
		
		<#if adminUser.role ==1 || adminUser.role ==2>
		<div class="row">
			<div style="width: 50%;" class="col-md-3 col-sm-6 col-xs-12">
				<div class="box box-success box-solid">
					<div class="box-header with-border text-center"
						style="text-align: -webkit-left;">
						<h3 class="box-title">上周学霸榜</h3>
						<!-- /.box-tools -->
					</div>
					<!-- /.box-header -->
					<div class="row">
						<!-- BEGIN SAMPLE TABLE PORTLET-->
						<table class="table-scrollable table table-hover">
							<thead>
								<tr height="60">
									<th class="text-center" data-sortable="false">排名</th>
									<th class="text-center">头像</th>
									<th class="text-center">考生姓名</th>
									<th class="text-center">${pilotUnitName}名称</th>
									<th class="text-center">学习时长(分钟)</th>
								</tr>
							</thead>
							<tbody>
								<#if listLastWeekLearnTimeVO??> <#list listLastWeekLearnTimeVO
								as vo>
								<tr height="60">
									<td class="text-center"><#if vo.rank ==1> <img
										src="${baseOP}/image/dashboard/1.png"></img> <#elseif vo.rank
										==2> <img src="${baseOP}/image/dashboard/2.png"></img>
										<#elseif vo.rank ==3> <img
										src="${baseOP}/image/dashboard/3.png"></img> </#if>
									</td>
									<td class="text-center">
										<!--class="direct-chat-img"--> <img width="auto" height="30px"
										src="${vo.portrait}"></img> <!--<img width="auto" height="30px" src="${baseOP}/image/common/student-portrait.png"/>-->
									</td>
									<td class="text-center">${vo.realName}</td>
									<td class="text-center">${vo.pilotUnitName}</td>
									<td class="text-center">${vo.learnTime}</td>
								</tr>
								</#list> </#if>
							</tbody>
						</table>
						<!-- END SAMPLE TABLE PORTLET-->
					</div>
				</div>
				<!-- /.box -->
			</div>
			<!-- /.col -->
			<div style="width: 50%;" class="col-md-3 col-sm-6 col-xs-12">
				<div class="box box-success box-solid">
					<div class="box-header with-border text-center"
						style="text-align: -webkit-left;">
						<h3 class="box-title">上周${pilotUnitName}榜</h3>
						<!-- /.box-tools -->
					</div>
					<!-- /.box-header -->
					<!-- /.box-body -->
					<div class="row">
						<div class="col-md-12">
							<!-- BEGIN SAMPLE TABLE PORTLET-->
							<table class="table-scrollable table table-hover">
								<thead>
									<tr height="60">
										<th class="text-center">趋势</th>
										<th class="text-center" data-sortable="false">排名</th>
										<th class="text-center">${pilotUnitName}头像</th>
										<th class="text-center">${pilotUnitName}名称</th>
										<th class="text-center">任务完成度</th>
									</tr>
								</thead>
								<tbody>
									<#if listLastWeekMissionPercentVO??> <#list
									listLastWeekMissionPercentVO as vo>
									<tr height="60">
										<td class="text-center"><#if vo.tendency ==1> <img
											src="${baseOP}/image/dashboard/up.png"></img> <#elseif
											vo.tendency ==0> <img
											src="${baseOP}/image/dashboard/equal.png"></img> <#elseif
											vo.tendency ==-1> <img
											src="${baseOP}/image/dashboard/down.png"></img> </#if>
										</td>
										<td class="text-center"><#if vo.rank ==1> <img
											src="${baseOP}/image/dashboard/1.png"></img> <#elseif vo.rank
											==2> <img src="${baseOP}/image/dashboard/2.png"></img>
											<#elseif vo.rank ==3> <img
											src="${baseOP}/image/dashboard/3.png"></img> </#if>
										</td>
										<td class="text-center">
											<!--class="direct-chat-img"--> <img width="auto"
											height="30px" src="${vo.pilotUnitPortrait}"></img> <!--<img width="auto" height="30px" src="${baseOP}/image/common/plilt-unit-portrait.png"/>-->
										</td>
										<td class="text-center">${vo.pilotUnitName}</td>
										<td class="text-center">${vo.missionPercent}</td>
									</tr>
									</#list> </#if>
								</tbody>
							</table>
							<!-- END SAMPLE TABLE PORTLET-->
						</div>
					</div>
				</div>
				<!-- /.box -->
			</div>
			<!-- /.col -->
		</div>
		<#elseif adminUser.role == 3>
		<div name="row">
			<div class="box box-success box-solid">
				<div class="box-header with-border text-center">
					<h3 class="box-title">上周学霸榜</h3>
					<!-- /.box-tools -->
				</div>
			</div>
			<div class="row"></div>
			<!-- /.box-body -->
		</div>
		<!-- /.box -->
		</div>
		<!-- BEGIN SAMPLE TABLE PORTLET-->
		<table class="table-scrollable table table-hover">
			<thead>
				<tr>
					<th class="text-center" data-sortable="false">排名</th>
					<th class="text-center">头像</th>
					<th class="text-center">考生姓名</th>
					<th class="text-center">班级名称</th>
					<th class="text-center">学习时长(分钟)</th>
					<th class="text-center">答题数</th>
					<th class="text-center">正确率</th>
				</tr>
			</thead>
			<tbody>
				<#if listLastWeekLearnTimeVO??> <#list listLastWeekLearnTimeVO as
				vo>
				<tr>
					<td class="text-center"><#if vo.rank ==1> <img
						src="${baseOP}/image/dashboard/1.png"></img> <#elseif vo.rank ==2>
						<img src="${baseOP}/image/dashboard/2.png"></img> <#elseif vo.rank
						==3> <img src="${baseOP}/image/dashboard/3.png"></img> </#if>
					</td>
					<td class="text-center">
						<!--class="direct-chat-img"--> <!--<img width="auto" height="30px" src="${vo.portrait}"></img>-->
						<img width="auto" height="30px"
						src="${baseOP}/image/common/student-portrait.png" />
					</td>
					<td class="text-center">${vo.realName}</td>
					<td class="text-center">${vo.className}</td>
					<td class="text-center">${vo.learnTime}</td>
					<td class="text-center">${vo.answerCount}</td>
					<td class="text-center">${vo.rightPercent}</td>
				</tr>
				</#list> </#if>
			</tbody>
		</table>
		<!-- END SAMPLE TABLE PORTLET-->
		</div>
		<!-- /.box-body -->
		</div>
		<!-- /.box -->
		</div>
		<!-- /.col -->
		</div>
		</#if>
		<!-- /.row -->
		<!-- Main row -->
		<div class="row">
			<!-- Left col -->
			<section class="col-md-12 connectedSortable">
				<!-- Custom tabs (Charts with tabs)-->

				<div class="box box-warning box-solid">
					<div class="box-header with-border text-center"
						style="text-align: -webkit-left;">
						<h3 class="box-title" style="margin: 10px">趋势分析</h3>
						<!-- /.box-tools -->
						<ul class="nav nav-tabs pull-right">
							<li><select
								onchange="change(this.options[this.options.selectedIndex].value)"
								; id="catId" name="courseId" type="text" class="form-control"
								style="float: left;">
									<option value="7" selected="selected">过去7天</option>
									<option value="30">过去30天</option>
									<option value="60">过去60天</option>
							</select></li>
						</ul>
					</div>
					<div class="nav-tabs-custom">
						<!-- Tabs within a box -->
						<ul class="nav nav-tabs">
							<li class="text-center" style="width: 33%;"><a
								id="learnPercent" onclick="learnPercent();"
								href="#learnPercent-chart" data-toggle="tab">每日活跃度</a></li>
							<li class="text-center" style="width: 32%;"><a
								onclick="missionPercent();" href="#missionPercent-chart"
								data-toggle="tab">每日任务完成度</a></li>
							<li class="text-center pull-right" style="width: 33%;"><a
								onclick="learnTimePercent();" href="#learnTimePercent-chart"
								data-toggle="tab">每日平均学习时长</a></li>

						</ul>
						<input type="hidden" value='${jsonData}' id="jsonData">
						<div class="tab-content no-padding">
							<!-- Morris chart - Sales -->
							<div class="chart tab-pane in active" id="learnTimePercent-chart"
								style="position: relative">
								<!--<iframe src="/dashboard/echarts_line?name=''" width="100%" height="100%"></iframe>-->
								<section class="content">
									<div class="row">
										<div class="col-md-12">
											<!-- AREA CHART -->
											<div class="box box-primary">
												<div class="box-body">
													<div class="chart">
														<div id="main_learnTimePercent"></div>
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
							<div class="chart tab-pane active" id="missionPercent-chart"
								style="position: relative;">
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
							<div class="chart tab-pane active" id="learnPercent-chart"
								style="position: relative;">
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
						</div>
					</div>
					<!-- /.nav-tabs-custom -->
				</div>
			</section>
			<!-- right col -->
		</div>
		<!-- /.row (main row) -->


	</section>
	<!-- ./wrapper -->

	<!-- Bootstrap 3.3.6 -->
	<script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
	<script src="${baseOP}/content/min/js/supershopui.common.js"></script>

	<script src="${baseOP}/content/plugins/jQueryUI/jquery-ui.min.js"></script>
	<script src="${baseOP}/content/plugins/raphael/raphael.min.js"></script>
	<script src="${baseOP}/content/plugins/morris/morris.js"></script>
	<!-- Sparkline -->
	<script
		src="${baseOP}/content/plugins/sparkline/jquery.sparkline.min.js"></script>
	<!-- jvectormap -->
	<script
		src="${baseOP}/content/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
	<script
		src="${baseOP}/content/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
	<!-- jQuery Knob Chart -->
	<script src="${baseOP}/content/plugins/knob/jquery.knob.js"></script>
	<!-- daterangepicker -->
	<script
		src="${baseOP}/content/plugins/daterangepicker/daterangepicker.js"></script>
	<!-- datepicker -->
	<script
		src="${baseOP}/content/plugins/datepicker/bootstrap-datepicker.js"></script>
	<!-- Bootstrap WYSIHTML5 -->
	<script
		src="${baseOP}/content/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
	<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
	<script src="${baseOP}/js/echarts.min.js" charset="utf-8"></script>
	<script type="text/javascript">
		$.widget.bridge('uibutton', $.ui.button);
	</script>
	<script src="${baseOP}/js/common.js"></script>
	<script src="${baseOP}/js/dashboard.js?${timeStamp}"></script>
	<!-- page script -->
</body>
</html>
