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
        <link href="${baseOP}/content/ui/global/bootstrap-switch/css/bootstrap-switch.css" rel="stylesheet" />
        <link rel="stylesheet" href="${baseOP}/css/dashboard.css?1">
        <style>
			.unitPortrait
			{
				width: 156px;
				height: 60px;
			}
		</style>
    </head>
    <body>
    <section class="content-header">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-warning"></i>系统管理</a></li>
            <li class="active">信息修改</li>
        </ol>
     </section>
     <section class="form-well">
		<div class="row">
			<form id="searchForm" method="post" action="/student/student_info">
				<div class="row" style="margin-bottom: 10px;">
          <div class="col-sm-2">
            <input value="${studentDO.telephone}" name="telephone" id="telephone" class="form-control"  placeholder="请输入手机号"
              type="text">
          </div>
					<div class="col-sm-2">
						<input value="${studentDO.admissionCardCode}" name="admissionCardCode" id="admissionCardCode" class="form-control"  placeholder="请输入准考证号"
							type="text">
					</div>
					<div class="col-sm-2">
						<button type="submit" class="btn btn-default" onclick="return checkForm();">
							<span class="entypo-search"></span>&nbsp;&nbsp;搜索
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
				                <h3 class="box-title">学生信息</h3>
				                <div class="box-tools pull-right modal-open">
				                <#if student??>	
				                	<#if student.sourcePortrait??>
				                	<button class="btn btn-success" data-toggle="modal" onclick="resetStudentPortrait(${student.userId},'${student.telephone}')">重置人像</button>
				                	</#if>
				                	<button class="btn btn-success" data-toggle="modal" data-target="#update" data-sid="${student.id}" data-real_name="${student.realName}" data-telephone="${student.telephone}" data-admission_card_code="${student.admissionCardCode}" data-pilot_unit_name="${student.pilotUnitName}">修改手机号</button>
				                </#if>
				                </div>
				            </div>
				        </div> 
	                    
                    	<#if student??>						
			            <div class="col-md-4 col-sm-4 col-xs-12" align="center">
				    		<img width="auto" height="280px" src="${student.sourcePortrait}">
				    		</img>
				    		<#if !student.sourcePortrait??>
				    		<span style="line-height:210px; text-align:center;">用户未上传</span>
				    		</#if>
				    	</div>
			    		<div class="col-md-8 col-sm-8 col-xs-12">
			                <ul class="nav nav-stacked">
			                    <li><a href="javascript:;">姓名：${student.realName}</a></li>
			                    <li><a href="javascript:;">手机号：${student.telephone}</a></li>
			                    <li><a href="javascript:;">准考证号：${student.admissionCardCode}</a></li>
			                    <li><a href="javascript:;">${pilotUnitName}：${student.pilotUnitName}</a></li>
			                    <li><a href="javascript:;">上传设备：${student.uploadDevice}</a></li>
			                    <li><a href="javascript:;">上传时间：${student.uploadTime}</a></li>
			                </ul>
			    		</div>   
                      </#if>
                    </div>
                </div>
                <!-- END SAMPLE TABLE PORTLET-->
            </div>
        </div>
    </section>
<!-- 模态框（Modal） -->
<#include "/student/studentInfoUpdate.ftl"/>
<script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
<script src="${baseOP}/content/min/js/supershopui.common.js"></script>
<script src="${baseOP}/content/plugins/bootstrap-table/bootstrap-table.js"></script>
<script src="${baseOP}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="${baseOP}/content/ui/global/bootstrap-switch/js/bootstrap-switch.js"></script>
<script src="${baseOP}/js/common.js?${timeStamp}"></script>
<script src="${baseOP}/js/student/studentInfo.js?1${timeStamp}"></script>
  </body>
</html>