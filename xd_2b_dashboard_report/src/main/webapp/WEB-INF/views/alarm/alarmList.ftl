<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->
    <head>
        <meta charset="utf-8" />
        <#include "/common/title.ftl"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <!-- Bootstrap 3.3.6 -->
        <link rel="stylesheet" href="${baseOP}/content/ui/global/bootstrap/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link href="${baseOP}/content/ui/global/font-awesome/css/font-awesome.css" rel="stylesheet" />
        <!-- Theme style -->
        <link rel="stylesheet" href="${baseOP}/content/adminlte/dist/css/AdminLTE.css">
        <link rel="stylesheet" href="${baseOP}/content/adminlte/dist/css/skins/_all-skins.min.css">
        <link href="${baseOP}/content/min/css/supershopui.common.min.css" rel="stylesheet"/>
        <link href="${baseOP}/content/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet" />
        <!-- bootstrap datepicker -->
  		<link rel="stylesheet" href="${baseOP}/content/plugins/datepicker/datepicker3.css">
        <link rel="stylesheet" href="${baseOP}/css/dashboard.css?1">
    </head>
    <body>
    <section class="content-header">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-warning"></i>预警管理</a></li>
            <li class="active">预警列表</li>
        </ol>
     </section>
     <section class="form-well"  style="padding: 10px 10px 10px 20px;">
        <div class="row">
        <form id="searchForm" method="post" action="/alarm/alarm_list">
			<div class="row">
				<div class="col-sm-2">
					<input id="pageSize" name="pageSize" type="hidden"/>
					<input id="pageNo" name="pageNo" type="hidden"/>
					<input type="hidden" id="alarmLevel" value="${alarmRecordDTO.alarmLevel}"/>
					<select id="alarmLevel1" name="alarmLevel" type="text" class="filter-status form-control">
						<option value="">全部预警级别</option>
						<#list alarmLevelEnumList as alarmLevel>
							<option value="${alarmLevel}">${alarmLevel.desc}</option>
						</#list>
					</select>
				</div>
				<!-- Date -->
                <div class="col-sm-3">
                    <div class="input-group date">
                        <div class="input-group-addon">
                            <i class="fa fa-calendar"></i>
                        </div>
                        <input value="${alarmRecordDTO.alarmTime}" name="alarmTime" type="text" class="form-control pull-right" id="datepicker" placeholder="日期">
                    </div>
                    <!-- /.input group -->
                </div>
                <!-- /.form group -->
				<div class="col-sm-2">
					<input type="hidden" id="alarmType" value="${alarmRecordDTO.alarmType}"/>
					<select id="alarmType1" name="alarmType" type="text" class="filter-status form-control">
						<option value="">全部预警类型</option>
						<#list alarmTypeEnumList as alarmType>
							<option value="${alarmType}">${alarmType.desc}</option>
						</#list>
					</select>
				</div>
				<div class="col-sm-2">
					<button type="submit" class="btn btn-default">
						<span class="entypo-search"></span>搜索
					</button>
				</div>
			</div>
		</form>
		
	</div>				
	</section>
    <!-- Main content -->
    <section class="table-well">
        <div class="row">
            <div class="col-md-12">
                <!-- BEGIN SAMPLE TABLE PORTLET-->
                <div class="box">
                    <div class="box-body">
						
						
        <div class="box box-success box-solid" style="margin-bottom: 0px;">
            <div class="box-header">
                <h3 class="box-title">预警管理列表</h3>
                <!-- /.box-tools -->
            </div>
        </div>                   
                        <table class="table-scrollable table table-hover" id="table"
                               data-toggle="table"
                               data-pagination="true"
                               data-advanced-search="true"
                               data-id-table="advancedTable"
                               data-row-style="rowStyle">
                            <thead>
                                <tr>
                                	<th class="text-center" data-sortable="true">序号</th>
                                	<th class="text-center">
                                	<#if adminUser.role == 3>
										班级名称
									<#else>
										${pilotUnitName}名称
									</#if>
                                	</th>
									<th class="text-center">准考证号</th>
                                	<th class="text-center">姓名</th>
									<th class="text-center">预警时间</th>
									<th class="text-center">预警级别</th>
									<th class="text-center">类型</th>
									<th class="text-center">操作</th>
                                </tr>
                            </thead>
                            <tbody>
								<#if listAlarmRecord??>
								<#list listAlarmRecord as alarm>
								<#if alarm??>
									<tr>
										<td class="text-center">${alarm_index+1}</td>
										<td class="text-center">
										<#if adminUser.role == 3>
											${alarm.className}
										<#else>
											${alarm.pilotUnitName}
										</#if>
										</td>
										<td class="text-center">${alarm.admissionCardCode}</td>
										<td class="text-center">${alarm.studentName}</td>
										<td class="text-center">${alarm.alarmTime}</td>
										<td class="text-center">${alarm.alarmLevel.desc}</td>
										<td class="text-center">${alarm.alarmType.desc}</td>
										<td class="text-center">
										<#if alarm.readStatus == 0>
											<a class="btn btn-danger" 
										<#else >
											<a class="btn btn-info" 
										</#if>
										<#if alarm.triggerType == 1>
											href="/alarm/raw_data_list?alarmId=${alarm.id}">
											
										<#else>
											href="/alarm/login_info?alarmId=${alarm.id}">
										</#if>
										查看报告详情</a>
										</td>
									</tr>
								</#if>
								</#list>
								</#if>
								</tbody>
						</table>
                    </div>
           			<div class="fixed-table-pagination">
	       	<div class="pull-left pagination-detail">
	       		<span class="pagination-info">
	       		<#if totalCount ==0>
	       		总共 ${totalCount}条记录
	       		<#else>
	       		显示第${(pageNo-1)*pageSize+1}到第${(pageNo-1)*pageSize +listAlarmRecord?size}条记录，总共 ${totalCount}条记录
	       		</#if>
	         	</span>
	       		<span class="page-list">
	       		每页显示 
	       		<span class="btn-group dropup">
	       			<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
		       			<span class="page-size">${pageSize}</span>
		       			<span class="caret"></span>
	       			</button>
	       			<ul class="dropdown-menu" role="menu">
	       				<li <#if pageSize==10>class="active"</#if>><a onclick="toPageSize(10);" href="javascript:void(0)">10</a></li>
	       				<li <#if pageSize==25>class="active"</#if>><a onclick="toPageSize(25);" href="javascript:void(0)">25</a></li>
	       				<li <#if pageSize==100>class="active"</#if>><a onclick="toPageSize(100);" href="javascript:void(0)">100</a></li>
	       			</ul>
	       		</span>
	       		 条记录
	       		</span>
	       	</div>
	       	<div class="pull-right pagination">
	        <ul id="pagination-demo-v1_0" class="pagination"></ul>
	       	</div>
        </div>
                    
                </div>
                <!-- END SAMPLE TABLE PORTLET-->
            </div>
        </div>
    </section>
        <script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
        <script src="${baseOP}/content/min/js/supershopui.common.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
        <!-- bootstrap datepicker -->
		<script src="${baseOP}/content/plugins/datepicker/bootstrap-datepicker.js"></script>
        <script src="${baseOP}/js/common.js?${timeStamp}"></script>
        <script src="${baseOP}/js/alarm/alarm.js?${timeStamp}"></script>
		<script src="${baseOP}/js/jquery.twbsPagination.js" type="text/javascript"></script>
        <script>
		$(function(){
				$('#pagination-demo-v1_0').twbsPagination({
					totalPages : ${totalPage} > 1 ? ${totalPage} : 1,
					startPage : ${pageNo},
					visiblePages : 5,
					version : '1.0',
					onPageClick : function(event, page) {
						$("#pageNo").val(page);
						$("#pageSize").val(${pageSize});
						$('#searchForm').submit();
					}
				});
		});
		function toPageSize(pageSize){
			App.blockUI({
		        target: '.pace-done'
		    });
			$("#pageSize").val(pageSize);
			$("#searchForm").submit();
		}
		</script>
    </body>
</html>