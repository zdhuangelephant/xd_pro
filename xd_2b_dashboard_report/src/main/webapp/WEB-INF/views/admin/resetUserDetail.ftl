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
        <link href="${baseOP}/css/dashboard.css" rel="stylesheet" />
    </head>
    <body>
    <section class="content-header">
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i>首页</a></li>
                <li class="active">修改账号信息</li>
            </ol>
        </section>
    <!-- Main content -->
    <section class="table-well">
        <div class="row">
            <div class="col-md-12">
                <!-- BEGIN SAMPLE TABLE PORTLET-->
                <div class="box">
                    <div class="box-body">
						<form id="saveForm" class="form-horizontal" role="form" action="/admin/do_reset_user_detail">
						<div class="form-group">
								<label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label">${pilotUnitName}名称:</label>
								<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
									<input disabled=true class="form-control" name="unitName" value="${adminUser.unitName}" type="text">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label">负责人:</label>
								<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
									<input class="form-control" name="realName" value="${adminUser.realName}" type="text">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label">联系电话:</label>
								<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
									<input id="telephone" name="telephone" class="form-control" value="${adminUser.telephone}" type="text">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label">邮箱地址:</label>
								<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
									<input id="card_no" name="email" class="form-control" value="${adminUser.email}" type="text">
								</div>
							</div>
							修改后需要重新登陆
							<div class="modal-footer">
				                <button type="reset" class="btn btn-default" >取消</button>
				                <button type="button" id="saveAdmin" class="btn btn-primary" >确定</button>
            				</div>
						</form>
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
        <script src="${baseOP}/js/common.js?1"></script>
        <script src="${baseOP}/js/admin/resetUserDetail.js"></script>
    </body>
<script>
    function rowStyle(row, index) {
        var classes = ['active', 'success', 'info', 'warning', 'danger'];
        if (index % 2 === 0 && index / 2 < classes.length) {
            return {
                classes: classes[index / 2]
            };
        }
        return {};
    }
</script>
</html>