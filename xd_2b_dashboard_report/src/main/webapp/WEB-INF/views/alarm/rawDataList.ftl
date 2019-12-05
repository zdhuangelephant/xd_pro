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
                       data-row-style="rowStyle">
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
<section class="table-well">
		<!--
		<div class="box-header with-border">
            <h3 class="box-title">人像识别记录</h3>
            <div class="box-tools pull-right">
                <button type="button" class="btn btn-info" onclick="modalOpen(${alarmRecord.id});">浏览全部照片</button>
            </div>
        </div>
        -->
        <div class="row">
            <div class="col-md-12">
                <!-- BEGIN SAMPLE TABLE PORTLET-->
                <div class="box">
                    <div class="box-body">
                    
                    <div class="box box-success box-solid">
			            <div class="box-header">
			                <h3 class="box-title">人像识别记录</h3>
				            <div class="box-tools pull-right">
				                <button type="button" class="btn btn-success" onclick="modalOpen(${alarmRecord.id});">浏览全部照片</button>
				            </div>
			            </div>
			        </div>
        	    	<div class="col-md-4 col-sm-4 col-xs-12" align="center">
			    		<img src="${studentDO.sourcePortrait}" width="auto" height="120px"></img>
			    		<!--<img src="${baseOP}/image/common/portrait.jpg" width="auto" height="120px"/>-->
			    	</div>
		    		<div class="col-md-4 col-sm-4 col-xs-12">
		                <ul class="nav nav-stacked">
		                    <li><a href="javascript:volid(0);">人脸采集方式 ：${studentDO.collectWay}</a></li>
		                    <li><a href="javascript:volid(0);">人脸上传时间 ：${studentDO.uploadTime}</a></li>
		                    <li><a href="javascript:volid(0);">人脸上传设备 ：${studentDO.uploadDevice}</a></li>
		                </ul>
		    		</div>
		    		<div class="col-md-4 col-sm-4 col-xs-12">
		                <ul class="nav nav-stacked">
		                    <li><a href="javascript:volid(0);">专业 ：${listRawDataFaceRecognition[0].catName}</a></li>
		                    <li><a href="javascript:volid(0);">课程 ：${listRawDataFaceRecognition[0].productName}</a></li>
		                    <li><a href="javascript:volid(0);">测试点 ：${listRawDataFaceRecognition[0].testPoint}</a></li>
		                </ul>
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
                                    <th class="text-center" data-sortable="false">采集图片</th>
                                    <th class="text-center" data-sortable="false">采集时间</th>
                                    <th class="text-center" data-sortable="false">相似度</th>
                                    <th class="text-center" data-sortable="false">系统预判</th>
                                </tr>
                            </thead>
                            <tbody>
								<#if listRawDataFaceRecognition??>
								<#list listRawDataFaceRecognition as face>
								<#if face??>
									<tr id="st${face.id}" class="tr-parent">
										<td class="text-center">${face_index+1}</td>
										<td class="text-center">
											<img width="50px" height="auto" src="${face.collectPortrait}"></img>
											<!--<img width="50px" height="auto" src="${baseOP}/image/common/portrait.jpg"/>-->
										</td>
										<td class="text-center">${face.collectTime}</td>
										<td class="text-center">${face.similarity}%</td>
										<td class="text-center">
										<#if face.result==1>是本人
										<#else>不是本人
										</#if>
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
    </div>
	<div class="box-header with-border">
        <!-- <button type="button" class="btn btn-primary" onclick="$('#print').jqprint();">预警报告打印预览</button> -->
        <button class="btn btn-success" data-toggle="modal" data-target="#previewAlarmPrint">打印预览</button>
        
    </div>
    <#include "/alarm/printAlarmList.ftl"/>
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