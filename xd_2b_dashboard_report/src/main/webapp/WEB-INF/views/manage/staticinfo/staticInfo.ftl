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
        <link href="${baseOP}/css/dashboard.css" rel="stylesheet" />
    </head>
    <body>
    <section class="content-header">
            <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-circle-o"></i>人员管理</a></li>
            <li class="active">常量管理</li>
            </ol>
        </section>
    <!-- Main content -->
    <input type="hidden" value="${goEasySubscribekey}" id="goEasySubscribekey">
    <section class="table-well">
        <div class="row">
            <div class="col-md-12">
                <!-- BEGIN SAMPLE TABLE PORTLET-->
                <div class="box">
                    <div class="box-body">
						<form id="updateForm" class="form-horizontal" role="form" action="/admin/do_reset_user_detail">
							<div class="col-sm-3">
							<input type="hidden" value="${staticInfo.id}" name="id"/>
								<label>考期:</label>
								<div class="input-group date">
									<input id="examDate" class="form-control" name="examDate" value="${staticInfo.examDate}" type="text">
				                </div>
				                <!-- /.input group -->
				            </div>
							<div class="col-sm-3">
								<label >考期开始时间:</label>
				                <div class="input-group date">
				                    <div class="input-group-addon">
				                        <i class="fa fa-calendar"></i>
				                    </div>
				                    <input value="${staticInfo.beginApplyTime}" name="beginApplyTime" type="text" class="form-control pull-right" id="datepicker1" placeholder="日期">
				                </div>
				                <!-- /.input group -->
				            </div>
								<!-- Date -->
				            <div class="col-sm-3">
								<label>考期结束时间:</label>
				                <div class="input-group date">
				                    <div class="input-group-addon">
				                        <i class="fa fa-calendar"></i>
				                    </div>
				                    <input value="${staticInfo.endApplyTime}" name="endApplyTime" type="text" class="form-control pull-right" id="datepicker2" placeholder="日期">
				                </div>
				                <!-- /.input group -->
				            </div>
							<div class="modal-footer">
				                <button type="button" onclick="updateStaticInfo();" class="btn btn-primary" >更新</button>
            				</div>
						</form>
                    </div>
                </div>
                <!-- END SAMPLE TABLE PORTLET-->
            </div>
        </div>
        <button type="button" id="batchScoreByApply" onclick="batchScoreByApply();" class="btn btn-primary" >同步正常报名数据到分数数据</button>
    </section>
    
    <!-- Main content -->
    <section class="table-well"">
    	<div class="row">
    		<div class="col-md-5 col-sm-5 col-xs-5">
                <ul class="nav nav-stacked">
                    <li><a href="javascript:;">同步用户URL：/quartz/student</a></li>
                    <li><a href="javascript:;">同步课程URL：/quartz/apply</a></li>
                    <li><a href="javascript:;">同步准考证URL：/quartz/admissionCardCode</a></li>
                </ul>
    		</div>
    		<div class="col-md-3 col-sm-3 col-xs-3">
                <ul class="nav nav-stacked">
                	<li><button type="button" id="quartzStudent" class="btn btn-primary" onclick="quartzStudent();" >同步</button></li>
                    <li><button type="button" id="quartzApply" class="btn btn-primary" onclick="quartzApply()" >同步</button></li>
                    <li><button type="button" id="quartzCard" class="btn btn-primary" onclick="quartzCard()" >同步</button></li>
                </ul>
	    	</div>
	    	<div class="col-md-4 col-sm-4 col-xs-4">
                <ul class="nav nav-stacked">
                	<li><button type="button" id="student" class="btn btn-warning">过程监控</button></li>
                    <li><button type="button" id="apply" class="btn btn-warning">过程监控</button></li>
                    <li<button type="button" id="admissionCardCode" class="btn btn-warning">过程监控</button></li>
                </ul>
	    	</div>
    	</div>
    	
        <div class="row">
            <div class="col-md-12">
                <!-- BEGIN SAMPLE TABLE PORTLET-->
                <div class="box">
                    <div class="box-body">
 		<div class="box box-success box-solid" style="margin-bottom: 0px;">
            <div class="box-header">
                <h3 class="box-title">业务库操作列表</h3>
                <!-- /.box-tools -->
                <div class="box-tools pull-right modal-open">
                	<input id="syncType" type="hidden" value="${syncLogDO.syncType}"/>
                	<select id="syncType1" onchange="change(this.options[this.options.selectedIndex].value);" type="text" class="form-control" style="float:left;">
						<option value="" selected="selected">全部类型</option>
						<option value="1">同步用户</option>
						<option value="2">同步课程</option>
						<option value="3">同步准考证</option>
					</select>
                </div>
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
		                        	<th class="text-center">同步类型</th>
		                        	<th class="text-center">同步时间</th>
		                        	<th class="text-center">执行人</th>
		                            <th class="text-center">操作结果</th>
		                        </tr>
		                    </thead>
		                    <tbody>
		                    	<#if listSyncLog??>
								<#list listSyncLog as syncLog>
								<#if syncLog??>
									<tr class="tr-parent">
										<td class="text-center">
										<#if syncLog.syncType == 1>
											同步学生
										<#elseif syncLog.syncType == 2>
											同步课程
										<#elseif syncLog.syncType == 3>
											同步准考证号
										</#if>
										</td>
										<td class="text-center">${syncLog.syncTime}</td>
										<td class="text-center">${syncLog.syncAdminName}</td>
										<td class="text-center">
											<#if syncLog.syncResult == '0'>
												同步消息未完成
												<!--${syncLog.syncResultMsg}
												已完成${syncLog.completeCount}/${syncLog.totalCount}个-->
											<#elseif syncLog.syncResult == '1' >
												<a class="btn btn-info" href="/manage/download_excel?syncType=${syncLog.syncType}&syncId=${syncLog.syncId}&syncTime=${syncLog.syncTime}">文件下载</a>
											<#else>
												无数据
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
        <script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
        <script src="${baseOP}/content/min/js/supershopui.common.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/bootstrap-table.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
        <!-- bootstrap datepicker -->
		<script src="${baseOP}/content/plugins/datepicker/bootstrap-datepicker.js"></script>
        <!-- bootstrap datepicker -->
        <script src="${baseOP}/js/common.js?1${timeStamp}"></script>
        <script type="text/javascript" src="${baseOP}/js/goeasy.js"></script>
        <script src="${baseOP}/js/manage/staticinfo.js?${timeStamp}"></script>
    </body>
</html>