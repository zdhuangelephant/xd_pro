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
        <!--表单相关-->
    	<link rel="stylesheet" href="${baseOP}/content/plugins/daterangepicker/daterangepicker.css">
        <!-- bootstrap datepicker -->
  		<link rel="stylesheet" href="${baseOP}/content/plugins/datepicker/datepicker3.css">
  		<link href="${baseOP}/css/dashboard.css" rel="stylesheet" />
    </head>
    <body>
    <section class="content-header">
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-warning"></i>预警管理</a></li>
                <li class="active">报告详情</li>
            </ol>
    </section>
    <!-- Main content -->
<div id="print">
<section class="table-well">
<div class="row">
<div class="col-md-12">
	<div class="box">
        <div class="box-body">
        	<div class="box box-success box-solid" style="margin-bottom: 0px;">
	            <div class="box-header">
	                <h3 class="box-title">预警详情</h3>
	                <!-- /.box-tools -->
	            </div>
	        </div> 
			<div class="col-md-6 col-sm-6 col-xs-12">
		        <ul class="nav nav-stacked">
		            <li><a href="javascript:volid(0);">预警级别 ：${alarmRecord.alarmLevel.desc}</a></li>
		            <li><a href="javascript:volid(0);">预警时间 ：${alarmRecord.alarmTime}</a></li>
		        </ul>
			</div>
			<div class="col-md-6 col-sm-6 col-xs-12">
		        <ul class="nav nav-stacked">
		            <li><a href="javascript:volid(0);">预警类型 ：${alarmRecord.alarmType.desc}</a></li>
		            <li><a href="javascript:volid(0);">预警处理 ：${alarmRecord.pretreatment.desc}</a></li>
		        </ul>
			</div>
		</div>
	</div>
</div>
</div>    
</section>

<section class="table-well">
<div class="row">
    <div class="col-md-12">
        <!-- BEGIN SAMPLE TABLE PORTLET-->
        <div class="box">
            <div class="box-body">
       			<div class="box box-success box-solid" style="margin-bottom: 0px;">
		            <div class="box-header">
		                <h3 class="box-title">预警账号列表</h3>
		                <!-- /.box-tools -->
		            </div>
		        </div> 
                <table class="table-scrollable table table-hover" id="table_1"
                       data-toggle="table"
                       data-advanced-search="true"
                       data-id-table="advancedTable"
                 ·	      data-row-style="rowStyle">
                    <thead>
                        <tr>
                        <#if adminUser.role==3>
                        	<th class="text-center" data-sortable="false">班级名称</th>
                        <#else>
                            <th class="text-center" data-sortable="false">${pilotUnitName}</th>
                        </#if>
                            <th class="text-center" data-sortable="false">姓名</th>
                            <th class="text-center" data-sortable="false">性别</th>
                            <th class="text-center" data-sortable="false">准考证号</th>
                            <th class="text-center" data-sortable="false">手机号</th>
                        </tr>
                    </thead>
                    <tbody>
						<#if studentDO??>
				        <tr>
				        	<#if adminUser.role==3>
				        		<td class="text-center">${studentDO.className}</td>
				        	<#else>
				        		<td class="text-center">${studentDO.pilotUnitName}</td>
				        	</#if>
							<td class="text-center">${studentDO.realName}</td>
							<td class="text-center">
							<#if studentDO.gender == 1>
							男
							<#elseif studentDO.gender == 2>
							女
							<#else>
							不明
							</#if>
							</td>
							<td class="text-center">${studentDO.admissionCardCode}</td>
							<td class="text-center">${studentDO.telephone}</td>
				        </tr>
				        </#if>
					</tbody>
                </table>
            </div>
        </div>
        <!-- END SAMPLE TABLE PORTLET-->
    </div>
</div>
</section>

<#if isLearnAlerm == '1'>
	<!-- Main content -->
    <section class="table-well">
        <div class="row">
            <div class="col-md-12">
                <!-- BEGIN SAMPLE TABLE PORTLET-->
                <div class="box">
                    <div class="box-body">

 		<div class="box box-success box-solid" style="margin-bottom: 0px;">
            <div class="box-header">
                <h3 class="box-title">成绩列表</h3>
            </div>
        </div>                   
                         <table class="table-scrollable table table-hover" id="table"
                               data-toggle="table"
                               data-advanced-search="true"
                               data-id-table="advancedTable"
                                data-row-style="rowStyle">
                            <thead>
                                <tr>
                                	<th class="text-center" data-sortable="true">序号</th>
                                    <th class="text-center" data-sortable="false">
									<#if adminUser.role == 3>
										班级名称
									<#else>
										${pilotUnitName}名称
									</#if>
									</th>
                                    <th class="text-center" data-sortable="false">姓名</th>
                                    <th class="text-center" data-sortable="false">准考证号</th>
                                    <th class="text-center" data-sortable="false">专业</th>
                                    <th class="text-center" data-sortable="false">课程名称</th>
                                    <th class="text-center" data-sortable="false">结课时间</th>
                                    <th class="text-center" data-sortable="false">成绩</th>
                                    <th class="text-center" data-sortable="false">学习记录</th>
                                </tr>
                            </thead>
                            <tbody>
								<#if listScore??>
								<#list listScore as score>
								<#if score??>
									<tr id="st${score.id}" class="tr-parent">
										<td class="text-center">${score_index+1}</td>
										<td>
										<#if adminUser.role == 3>
											${score.className}
										<#else>
											${score.pilotUnitName}
										</#if>
										</td>
										<td class="text-center">${score.studentName}</td>
										<td class="text-center">${score.admissionCardCode}</td>
										<td>${score.catName}</td>
										<td>${score.productName}</td>
										<td>${score.endTime}</td>
										<td class="text-center">
											<a href="/score/score_detail?scoreId=${score.id}">${score.score}</a>
										</td>
										<td class="text-center">
											<a class="btn btn-info" href="/score/learn_record_list?studentId=${score.studentId}&productId=${score.productId}">查看详情</a>
										</td>
									</tr>
									</#if>
									</#list>
									</#if>
								</tbody>
						</table>
                    </div>
                </div>
                <!-- END SAMPLE TABLE PORTLET-->
            </div>
        </div>
    </section>
</#if>
                        

<section class="table-well">
        <div class="row">
            <div class="col-md-12">
                <!-- BEGIN SAMPLE TABLE PORTLET-->
                <div class="box">
                    <div class="box-body">
                    
                    <div class="box box-success box-solid">
			            <div class="box-header">
			                <h3 class="box-title">登录日志详情</h3>
			            </div>
			        </div>
                    
                        <table class="table-scrollable table table-hover" id="table_2"
                               data-toggle="table"
                               data-pagination="true"
                               data-advanced-search="true"
                               data-id-table="advancedTable"
                               data-row-style="rowStyle">
                            <thead>
                                <tr>
                                	<th class="text-center" data-sortable="true">序号</th>
                                    <th class="text-center" data-sortable="false">准考证号</th>
                                    <th class="text-center" data-sortable="false">姓名</th>
                                    <th class="text-center" data-sortable="false">登录时间</th>
                                    <th class="text-center" data-sortable="false">登录设备</th>
                                    <th class="text-center" data-sortable="false">设备标识</th>
                                    <th class="text-center" data-sortable="false">登录地区</th>		
                                </tr>
                            </thead>
                            <tbody>
								<#if listLoginInfo??>
								<#list listLoginInfo as loginInfo>
								<#if loginInfo??>
									<tr>
										<td class="text-center">${loginInfo_index+1}</td>
										<td class="text-center">${loginInfo.admissionCardCode}</td>
										<td class="text-center">${loginInfo.studentName}</td>
										<td class="text-center">${loginInfo.loginTime}</td>
										<td class="text-center">${loginInfo.deviceId}</td>
										<td class="text-center">${loginInfo.systemType}</td>
										<td class="text-center">${loginInfo.area}</td>
									</tr>
								</#if>
								</#list>
								</#if>
							</tbody>
						</table>
                    </div>
                </div>
                <!-- END SAMPLE TABLE PORTLET-->
            </div>
        </div>
    </section>
    </div>
	<div class="box-header with-border">
<!--         <button type="button" class="btn btn-primary" onclick="$('#print').jqprint();">预警报告打印预览</button> -->
        <button class="btn btn-success" data-toggle="modal" data-target="#previewLoginInfoPrint">打印预览</button>

    </div>
    	<#include "/alarm/printLoginInfoList.ftl">
        <script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
        <script src="${baseOP}/content/min/js/supershopui.common.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/bootstrap-table.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
        <!-- date-range-picker -->
		<script src="${baseOP}/content/plugins/daterangepicker/daterangepicker.js"></script>
		<!-- bootstrap datepicker -->
		<script src="${baseOP}/content/plugins/datepicker/bootstrap-datepicker.js"></script>
        <script src="${baseOP}/js/common.js?${timeStamp}"></script>
        <script src="${baseOP}/js/alarm/rawDataList.js?${timeStamp}"></script>
        <script src="${baseOP}/js/jquery-migrate-1.2.1.min.js"></script>
        <script src="${baseOP}/js/jqprint/jquery.jqprint-0.3.js"></script>
        
    </body>

</html>